package com.benner.dif.test;

import com.benner.dif.utils.Injector;
import com.benner.dif.utils.annotations.Autowire;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 8/11/17
 * Time: 2:12 AM
 */
public class TestModel {

    @Autowire
    MyTestService testService;

    @Autowire
    MyTestService1 testService1;


    public TestModel() {
    }


    public void anotherProperty() {
        testService.userPropertyOfAnotherClass();
    }

    public void usingTestService() {
        testService.printNameOfA();
        testService1.printNameOfA();
    }

    public static void main(String[] args) {
        com.benner.dif.utils.Injector i = Injector.getInstance();
        TestModel tm = new TestModel();


        tm.usingTestService();
        tm.anotherProperty();
    }


}
