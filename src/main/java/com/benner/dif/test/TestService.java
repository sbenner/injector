package com.benner.dif.test;

import com.benner.dif.utils.Injector;
import com.benner.dif.utils.annotations.Autowire;
import com.benner.dif.utils.annotations.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 8/11/17
 * Time: 2:12 AM
 */

@Service
public class TestService {

    @Autowire
    MyTestService testService;

    @Autowire
    MyTestService1 testService1;


    public TestService() {
    }


    public static void main(String[] args) {
        try {
            com.benner.dif.utils.Injector injector = Injector.getInstance();

//   if we need args[] or Properties for @Configuration class we set a bean
//            ImportOptions options =
//                    new ImportOptions(args);
            //Properties p = new Properties();
            //
//            injector.getBeanMap().put("importOptions",options);
//            //inject options where needed
//
            List<Class> scannedPackage = (List<Class>) injector.scanForClasses("com.benner.dif.test");
            List<Class> services = injector.configureBeansAndGetServices(scannedPackage);
            injector.initServices(services);
            injector.injectBeans(services);

            MyTestService1 myTestService1 = (MyTestService1)injector.getBeanMap().get("myTestService1");


            TestService testService = (TestService)injector.getBeanMap().get("testService");

            System.out.println(testService.testService.getPropertyA());
            System.out.println(myTestService1.getPropertyAA());


        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
