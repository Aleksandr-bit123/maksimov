package com.diploma.maksimov;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Slf4j
@SpringBootApplication
public class MaksimovApplication implements ApplicationListener<ApplicationEvent> {


    public static void main(String[] args) {

        SpringApplication.run(MaksimovApplication.class, args);

    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            ((ContextRefreshedEvent) event).getApplicationContext().getBean(RequestMappingHandlerMapping.class).getHandlerMethods().entrySet().forEach(x->log.info(x.toString()));
        }
    }

}
