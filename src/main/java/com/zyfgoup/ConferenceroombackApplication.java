package com.zyfgoup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class ConferenceroombackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConferenceroombackApplication.class, args);
    }

}
