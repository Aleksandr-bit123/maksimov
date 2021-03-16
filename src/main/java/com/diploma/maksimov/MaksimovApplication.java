package com.diploma.maksimov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
public class MaksimovApplication implements ApplicationListener<ApplicationEvent> {

    public static void main(String[] args) {
        SpringApplication.run(MaksimovApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            ((ContextRefreshedEvent) event).getApplicationContext().getBean(RequestMappingHandlerMapping.class).getHandlerMethods().entrySet().forEach(System.out::println);
        }
    }

}
