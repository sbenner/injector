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
public class MyTestService {

    @Autowire
    private String beanA;

    @Autowire
    private String beanB;


    public MyTestService(){
    }


    public String getPropertyA() {
        return beanA;
    }

    public String getPropertyB() {
        return beanB;
    }


}
