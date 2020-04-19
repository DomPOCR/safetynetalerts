package com.safetynet.safetyalerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SafetynetalertsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetynetalertsApplication.class, args);
	}

	// Solution pour ajouter le endpoint de Trace
	@Bean
	InMemoryHttpTraceRepository httptrace() {

		return new InMemoryHttpTraceRepository();

	}

}
