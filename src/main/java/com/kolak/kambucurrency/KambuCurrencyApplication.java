package com.kolak.kambucurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
public class KambuCurrencyApplication {

    public static void main(String[] args) {
        SpringApplication.run(KambuCurrencyApplication.class, args);
    }


    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


}
