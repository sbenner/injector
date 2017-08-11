package com.benner.dif.test;

import com.benner.dif.utils.annotations.Autowire;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 8/11/17
 * Time: 2:12 AM
 */
public class MyTestService1 {

    @Autowire
    AnotherService anotherService;

    private String propertyAA;
    private String propertyBB;

    public MyTestService1(){
        setPropertyAA("cool thing12");
        setPropertyBB("nasty thing12");
    }

    public void printNameOfA(){
        System.out.println(getPropertyAA());
    }

    public void userPropertyOfAnotherClass(){
        anotherService.runPrint();
    }

    public String getPropertyAA() {
        return propertyAA;
    }

    public void setPropertyAA(String propertyAA) {
        this.propertyAA = propertyAA;
    }

    public String getPropertyBB() {
        return propertyBB;
    }

    public void setPropertyBB(String propertyBB) {
        this.propertyBB = propertyBB;
    }
}
