package com.example.CustomerConsumerApp;

import com.example.CustomerConsumerApp.entity.CustomerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

		private static final String BASE_URL = "http://localhost:8080/customer-service";
		public static void main(String[] args) {
		SpringApplication.run(CustomerConsumerAppApplication.class, args);
		}

		private RestTemplate restTemplate = new RestTemplate();


	@Override
	public void run(String... args) throws Exception {
		// Test the API methods
		createCustomer(new CustomerEntity("Allen ", "", "Shaw", "Allen.shaw@email.com", "9876543210"));
		getCustomerById(String.valueOf(4L));
		getAllCustomers();
		updateCustomer(new CustomerEntity(5L, "Harini ", "Sathya", "", "harini.sathyanarayanan@example.com", "8327252110"));
		deleteCustomer(String.valueOf(9L));
	}


	public void createCustomer(CustomerEntity customer) {
		logger.info("Inside Create Customer Method");

		String url = BASE_URL + "/save";
		ResponseEntity<CustomerEntity> response = restTemplate.postForEntity(url, customer, CustomerEntity.class);

		logger.info("Created Customer: " + response.getBody().toString());
	}

	public void getCustomerById(String customerId) {

		logger.info("Inside Get Customer By Id Method");

		String url = BASE_URL + "/getCustomer";
		Map<String, String> params = new HashMap<>();
		params.put("id", customerId);

		ResponseEntity<Optional<CustomerEntity>> response = restTemplate.exchange(
				url + "?id={id}",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<Optional<CustomerEntity>>() {
				},
				params
		);

		Optional<CustomerEntity> customer = response.getBody();
		if (customer.isPresent()) {
			logger.info("Fetched Customer: " + customer.get().toString());
		} else {
			logger.info("Customer not found for ID: " + customerId);
		}
	}

	public void getAllCustomers() {

		logger.info("Inside Get All Customers Method");

		String url = BASE_URL + "/getAllCustomers";
		ResponseEntity<CustomerEntity[]> response = restTemplate.getForEntity(url, CustomerEntity[].class);
		CustomerEntity[] customers = response.getBody();

		logger.info("All Customers: ");

		for (CustomerEntity customer : customers) {
			logger.info(customer.toString());
		}
	}

	public void updateCustomer(CustomerEntity customer) {

		logger.info("Inside Update Customer Method");

		String url = BASE_URL + "/updateCustomer";

		try{
			ResponseEntity<CustomerEntity> response = restTemplate.exchange(
					url,
					HttpMethod.PUT,
					new HttpEntity<>(customer),
					CustomerEntity.class
			);
			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				logger.info("Updated Customer: " + response.getBody().toString());
			} else {
				logger.info("Failed to update Customer with id" + customer.getId() );
			}
		} catch (HttpClientErrorException.NotFound e) {
			logger.info("Customer with ID " + customer.getId() + " not found.");
		} catch (HttpClientErrorException e) {
			logger.info("Failed to update Customer. Error: " + e.getStatusCode() + " - " + e.getMessage());
		}
	}

	public void deleteCustomer(String customerId) {

		logger.info("Inside Delete Customer Method");

		String url = BASE_URL + "/deleteCustomer";
		Map<String, String> params = new HashMap<>();
		params.put("id", customerId);

		ResponseEntity<String> response = restTemplate.exchange(
				url + "?id={id}",
				HttpMethod.DELETE,
				null,
				new ParameterizedTypeReference<String>() {
				},
				params
		);

		if (response.getStatusCode().is2xxSuccessful()) {
			logger.info(response.getBody());
		} else {
			logger.info("Failed to delete customer with ID: " + customerId);
		}
	}
}
