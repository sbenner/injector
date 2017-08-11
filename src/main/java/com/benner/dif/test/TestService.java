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
            com.benner.dif.utils.Injector i = Injector.getInstance();
            List<Class> scannedPackage = (List<Class>) i.scanForClasses("com.benner.dif.test");
            List<Class> serviceList = i.configureBeansAndGetServices(scannedPackage);
            i.injectBeans(serviceList);

            MyTestService1 myTestService1 = (MyTestService1)i.getBeanMap().get("myTestService1");


            TestService testService = (TestService)i.getBeanMap().get("testService");

            System.out.println(testService.testService.getPropertyA());
            System.out.println(myTestService1.getPropertyAA());


        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
