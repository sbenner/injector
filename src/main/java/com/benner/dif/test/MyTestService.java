package com.benner.dif.test;

import com.benner.dif.utils.annotations.Autowire;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 8/11/17
 * Time: 2:12 AM
 */
public class MyTestService {

    @Autowire
    AnotherService anotherService;

    private String propertyA;
    private String propertyB;

    public MyTestService(){
        setPropertyA("cool thing");
        setPropertyB("nasty thing");
    }

    public void printNameOfA(){
        System.out.println(getPropertyA());
    }

    public void userPropertyOfAnotherClass(){
        anotherService.runPrint();
    }

    public String getPropertyA() {
        return propertyA;
    }

    public void setPropertyA(String propertyA) {
        this.propertyA = propertyA;
    }

    public String getPropertyB() {
        return propertyB;
    }

    public void setPropertyB(String propertyB) {
        this.propertyB = propertyB;
    }
}
