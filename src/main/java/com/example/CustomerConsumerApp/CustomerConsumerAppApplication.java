package com.example.CustomerConsumerApp;

import com.example.CustomerConsumerApp.entity.CustomerEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CustomerConsumerAppApplication implements CommandLineRunner {
		private static final String BASE_URL = "http://localhost:8080/customer-service";
		public static void main(String[] args) {
		SpringApplication.run(CustomerConsumerAppApplication.class, args);
		}

		private RestTemplate restTemplate = new RestTemplate();


	@Override
	public void run(String... args) throws Exception {
		// Test the API methods
		createCustomer(new CustomerEntity("Allen ", "", "Shaw", "Allen.shaw@email.com", "9876543210"));
	}


	public void createCustomer(CustomerEntity customer) {
		String url = BASE_URL + "/save";
		ResponseEntity<CustomerEntity> response = restTemplate.postForEntity(url, customer, CustomerEntity.class);
		System.out.println("Created Customer: " + response.getBody().toString());
	}



}
