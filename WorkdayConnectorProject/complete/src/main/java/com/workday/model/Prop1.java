package com.workday.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profile("sandbox")
public class Prop1 {

    @Value("${test}")
    private String test;


    @Value("${test1}")
    private String test1;

    @PostConstruct
    void init(){
        System.out.println("Prop1:+"+test+" "+test1);
    }

}
