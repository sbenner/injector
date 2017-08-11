package com.benner.dif.test;

import com.benner.dif.utils.annotations.Autowire;
import com.benner.dif.utils.annotations.Service;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 8/11/17
 * Time: 2:12 AM
 */
@Service
public class MyTestService1 {

    @Autowire
    MyTestService myTestService;



    public MyTestService1(){
    }

    public void printClassName(){
        System.out.println(this.getClass().getName());
    }

    public String getPropertyAA() {
        return myTestService.getPropertyA();
    }


    public String getPropertyBB() {
        return myTestService.getPropertyB();
    }


}
