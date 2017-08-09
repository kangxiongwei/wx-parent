package com.kxw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by kangxiongwei on 2017/8/9 10:12.
 */
@SpringBootApplication
//same as @Configuration @EnableAutoConfiguration @ComponentScan
@ImportResource("classpath*:beans.xml")
public class Application {

    /**
     * 启动spring-boot
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
