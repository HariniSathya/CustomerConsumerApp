package com.example.CustomerConsumerApp;

import com.example.CustomerConsumerApp.entity.CustomerEntity;
import com.example.CustomerConsumerApp.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SpringBootApplication
public class CustomerConsumerAppApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(CustomerConsumerAppApplication.class);
	private final CustomerService customerService;

	public CustomerConsumerAppApplication(CustomerService customerService) {
		this.customerService = customerService;
	}

	public static void main(String[] args) {
		SpringApplication.run(CustomerConsumerAppApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// Demonstrate the API methods
		//createCustomer(new CustomerEntity("Elena", "", "Green", "Elena.Green@email.com", "9876543210"));
		getCustomerById("4");
		getAllCustomers();
		updateCustomer(new CustomerEntity(5L, "Sheldon", "Cooper", "Lee", "sheldon.cooper@example.com", "8327252110"));
		deleteCustomer("9");
	}

	private void createCustomer(CustomerEntity customer) {
		var response = customerService.createCustomer(customer);
		if (response != null && response.isSuccess()) {
			logger.info("Customer Created: " + response.getData());
		} else {
			logger.error("Failed to Create Customer: " + (response != null ? response.getMessage() : "Unknown error"));
		}
	}

	private void getCustomerById(String customerId) {
		var response = customerService.getCustomerById(customerId);
		if (response != null && response.isSuccess()) {
			logger.info("Fetched Customer: " + response.getData());
		} else {
			logger.error("Customer Not Found: " + (response != null ? response.getMessage() : "Unknown error"));
		}
	}

	private void getAllCustomers() {
		var response = customerService.getAllCustomers();
		if (response != null && response.isSuccess()) {
			logger.info("All Customers: ");
			response.getData().forEach(customer -> logger.info(customer.toString()));
		} else {
			logger.error("Failed to Fetch Customers: " + (response != null ? response.getMessage() : "Unknown error"));
		}
	}

	private void updateCustomer(CustomerEntity customer) {
		var response = customerService.updateCustomer(customer);
		if (response != null && response.isSuccess()) {
			logger.info("Customer Updated: " + response.getData());
		} else {
			logger.error("Failed to Update Customer: " + (response != null ? response.getMessage() : "Unknown error"));
		}
	}

	private void deleteCustomer(String customerId) {
		var response = customerService.deleteCustomer(customerId);
		if (response != null && response.isSuccess()) {
			logger.info("Customer Deleted: " + response.getData());
		} else {
			logger.error("Failed to Delete Customer: " + (response != null ? response.getMessage() : "Unknown error"));
		}
	}
}
