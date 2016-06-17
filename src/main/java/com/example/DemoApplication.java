package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	private AppConf appConf;

	@PostConstruct
	public void init(){
		appConf.generateDocuments();
	}

	public static void main(String[] args) {SpringApplication.run(DemoApplication.class, args);}
}
