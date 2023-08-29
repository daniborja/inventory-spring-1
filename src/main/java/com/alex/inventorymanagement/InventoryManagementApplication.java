package com.alex.inventorymanagement;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryManagementApplication {

    @Bean
    public ModelMapper modelMapper() {
        // register as a @Bean to inject
        return new ModelMapper();
    }


    public static void main(String[] args) {
        SpringApplication.run(InventoryManagementApplication.class, args);
    }

}
