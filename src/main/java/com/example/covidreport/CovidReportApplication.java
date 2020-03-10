package com.example.covidreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CovidReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(CovidReportApplication.class, args);
	}

}
