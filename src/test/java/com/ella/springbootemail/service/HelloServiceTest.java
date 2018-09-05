package com.ella.springbootemail.service;

import com.ella.springbootemail.hello.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author ella
 * @date 2018/9/4
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloServiceTest {

    @Resource
    HelloService helloService;

    @Test
    public void sayHelloTest(){
        helloService.sayHello();
    }
}
