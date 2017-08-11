package com.benner.dif.test;

import com.benner.dif.utils.annotations.Bean;
import com.benner.dif.utils.annotations.Configuration;

@Configuration
public class ConfigurationService {

    @Bean
    public String beanA(){
        return "A";
    }

    @Bean
    public String beanB(){
        return "B";
    }

}
