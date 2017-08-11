package com.benner.dif.test;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 8/11/17
 * Time: 2:46 AM
 */
public class AnotherService {
    private String niceProperty;

    public AnotherService(){

        setNiceProperty("woof");
    }

    public void runPrint(){
        for(int i=0;i<10;i++)
            System.out.println(getNiceProperty());
    }

    public String getNiceProperty() {
        return niceProperty;
    }

    public void setNiceProperty(String niceProperty) {
        this.niceProperty = niceProperty;
    }
}
