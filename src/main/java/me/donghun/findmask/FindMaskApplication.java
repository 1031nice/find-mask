package me.donghun.findmask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FindMaskApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(FindMaskApplication.class);
        app.setWebApplicationType(WebApplicationType.SERVLET);
        app.run(args);
    }

}
