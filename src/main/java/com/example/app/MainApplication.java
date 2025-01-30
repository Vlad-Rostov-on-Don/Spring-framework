package com.example.app;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class MainApplication implements CommandLineRunner {
    private final CacheProxy cacheProxy;
    private Calculator calculator;

    public MainApplication(CacheProxy cacheProxy, Calculator calculator) {
        this.cacheProxy = cacheProxy;
        this.calculator = calculator;
    }

    @PostConstruct
    public void init() {
        this.calculator = cacheProxy.cache(calculator);
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println(calculator.fibonachi(10));
        System.out.println(calculator.fibonachi(10)); // Должен загрузиться из кеша
    }
}