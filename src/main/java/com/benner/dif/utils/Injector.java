package com.benner.dif.utils;


import com.benner.dif.utils.annotations.Autowire;
import com.benner.dif.utils.annotations.Bean;
import com.benner.dif.utils.annotations.Configuration;
import com.benner.dif.utils.annotations.Service;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 8/10/17
 * Time: 11:36 PM
 */
public class Injector<T> {

    private static Injector _injector;
    private Map<String, T> beansMap = new ConcurrentHashMap<>();

    public Injector() {
    }

    public static Injector getInstance() {
        if (_injector == null) {
            _injector = new Injector();
            return _injector;
        } else {
            return _injector;
        }
    }


    public Iterable<Class> scanForClasses(String packageName) throws ClassNotFoundException,
            IOException, URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            URI uri = new URI(resource.toString());
            dirs.add(new File(uri.getPath()));
        }
        List<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }

        return classes;
    }

    private void buildBeanList(Class clazz) throws Exception {

        if (clazz.getAnnotations().length == 0) return;
        final Method[] methods = clazz.getDeclaredMethods();
        T configuration = (T) clazz.newInstance();
        for (final Method method : methods) {
            if (isPublicInstance(method) &&
                    method.isAnnotationPresent(Bean.class)
                    ) {
                Bean b = method.getAnnotation(Bean.class);
                final T bean;
                try {
                    bean = reflectGetter(method).apply(configuration);
                } catch (final Throwable e) {
                    throw new ExceptionInInitializerError(e);
                }
                final String beanName = b.name().length() > 1 ? b.name() :
                        method.getName();
                beansMap.put(beanName, bean);
            }
        }

    }

    public Map getBeanMap() {
        return beansMap;
    }

    private boolean isPublicInstance(final Method method) {
        return Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers());
    }

    private Function<T, T> reflectGetter(final Method method) throws Throwable {
        final MethodType invokedType = MethodType.methodType(Function.class);
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        final MethodHandle implementation = lookup.unreflect(method);
        final CallSite site = LambdaMetafactory.metafactory(lookup,
                "apply",
                invokedType,
                MethodType.methodType(Object.class, Object.class),
                implementation,
                implementation.type());
        return (Function<T, T>) site.getTarget().invokeExact();
    }


    public List<Class> configureBeansAndGetServices(List<Class> classes) throws Exception {
        List<Class> services = new ArrayList<>();
        for (Class clazz : classes) {
            if (clazz.isAnnotationPresent(Configuration.class)) {
                buildBeanList(clazz);
            }
            if (clazz.isAnnotationPresent(Service.class)) {
                services.add(clazz);
            }
        }
        return services;
    }

    private List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    private String getBeanName(String typeName) {
        return typeName.substring(0, 1).toLowerCase()
                + typeName.substring(1);
    }

    public void inject(Class t) {
        if (!t.isAnnotationPresent(Service.class)) {
            return;
        }

        final Field[] fields = t.getDeclaredFields();
        String serviceName = getBeanName(t.getSimpleName());
        Optional service = Optional.ofNullable(beansMap.get(serviceName));

        for (Field f : fields) {
            if (f.isAnnotationPresent(Autowire.class)) {
                try {
                    f.setAccessible(true);
                    if (service.isPresent() &&
                            Optional.ofNullable(f.get(service.get())).isPresent()) return;
                    if (!service.isPresent()) service = Optional.of(t.newInstance());

                    String typeName = getBeanName(f.getName());
                    Optional o = Optional.ofNullable(beansMap.get(typeName));
                    if (!o.isPresent()) {
                        o = Optional.ofNullable(beansMap.get(getBeanName(f.getType().getSimpleName())));
                        if (!o.isPresent()) {
                            o = Optional.ofNullable(f.getType().newInstance());
                        }
                    }

                    if (o.isPresent()) {
                        f.set(service.get(), o.get());
                        beansMap.put(serviceName, (T) service.get());
                        //   inject(o.get().getClass());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void injectBeans(List<Class> classes) {
        for (Class t : classes) {
            inject(t);
        }
    }

}
