package br.com.fiap.gs_witchen_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching

public class GsWitchenJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GsWitchenJavaApplication.class, args);
    }

}
