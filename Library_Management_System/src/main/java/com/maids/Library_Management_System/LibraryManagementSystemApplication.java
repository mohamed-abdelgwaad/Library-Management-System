package com.maids.Library_Management_System;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.maids.Library_Management_System.Models.Role;
import com.maids.Library_Management_System.Repositories.RoleRepository;

@SpringBootApplication
@EnableCaching
public class LibraryManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSystemApplication.class, args);
	}
	 @Bean
	    CommandLineRunner run(RoleRepository roleRepository){
	        return args ->{
	            if(roleRepository.findByAuthority("ADMIN").isPresent()) return;
	             roleRepository.save(new Role("ADMIN"));
	            roleRepository.save(new Role("USER"));

	        };
}}
