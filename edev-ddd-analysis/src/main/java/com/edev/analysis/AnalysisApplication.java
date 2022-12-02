package com.edev.analysis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan(basePackages={"com.edev"})
@ImportResource(locations={"classpath*:applicationContext-*.xml"})
@MapperScan("com.edev.support.dao")
public class AnalysisApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnalysisApplication.class, args);
        System.out.println("........................................");
        System.out.println("....The Analysis Application started....");
        System.out.println("........................................");
    }
}
