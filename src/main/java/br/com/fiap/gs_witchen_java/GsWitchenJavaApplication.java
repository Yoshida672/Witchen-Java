package br.com.fiap.gs_witchen_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableAsync
public class GsWitchenJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GsWitchenJavaApplication.class, args);
    }

}
