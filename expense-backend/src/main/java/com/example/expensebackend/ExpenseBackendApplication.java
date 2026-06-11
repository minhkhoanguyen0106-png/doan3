package com.example.expensebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Annotation chinh de bat auto config, component scan va cau hinh Spring Boot.
public class ExpenseBackendApplication {

    // Ham main la diem bat dau khi chay backend.
    public static void main(String[] args) {
        SpringApplication.run(ExpenseBackendApplication.class, args); // Khoi dong ung dung Spring Boot.
    }
}
