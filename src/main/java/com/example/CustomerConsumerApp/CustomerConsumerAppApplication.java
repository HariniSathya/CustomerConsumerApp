package com.example.CustomerConsumerApp;

import com.example.CustomerConsumerApp.entity.CustomerEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
		getCustomerById(String.valueOf(4L));
		getAllCustomers();
	}


	public void createCustomer(CustomerEntity customer) {
		String url = BASE_URL + "/save";
		ResponseEntity<CustomerEntity> response = restTemplate.postForEntity(url, customer, CustomerEntity.class);
		System.out.println("Created Customer: " + response.getBody().toString());
	}

	public void getCustomerById(String customerId) {
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
			System.out.println("Fetched Customer: " + customer.get().toString());
		} else {
			System.out.println("Customer not found for ID: " + customerId);
		}
	}

	public void getAllCustomers() {
		String url = BASE_URL + "/getAllCustomers";
		ResponseEntity<CustomerEntity[]> response = restTemplate.getForEntity(url, CustomerEntity[].class);
		CustomerEntity[] customers = response.getBody();
		System.out.println("All Customers: ");
		for (CustomerEntity customer : customers) {
			System.out.println(customer.toString());
		}
	}


}
