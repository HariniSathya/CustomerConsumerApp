package com.example.CustomerConsumerApp.service;

import com.example.CustomerConsumerApp.entity.ApiResponse;
import com.example.CustomerConsumerApp.entity.CustomerEntity;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CustomerService {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:8080/customer-service";

    public CustomerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public  ApiResponse<CustomerEntity> createCustomer(CustomerEntity customer) {
        String url = BASE_URL + "/save";
        ;
        ResponseEntity<ApiResponse<CustomerEntity>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(customer),
                new ParameterizedTypeReference<>() {}
        );;

        return response.getBody();
    }

    public ApiResponse<CustomerEntity> getCustomerById(String customerId) {
        String url = BASE_URL + "/getCustomer?id={id}";
        ResponseEntity<ApiResponse<CustomerEntity>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {},
                customerId
        );
        return response.getBody();
    }

    public ApiResponse<List<CustomerEntity>> getAllCustomers() {
        String url = BASE_URL + "/getAllCustomers";
        ResponseEntity<List<CustomerEntity>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CustomerEntity>>() {}
        );
        ApiResponse<List<CustomerEntity>> apiResponse;
        if(response.getBody() !=null ) {
            apiResponse = new ApiResponse<>(true, "Fetched the list", response.getBody());
        }else{
            apiResponse = new ApiResponse<>(false, "No customers available to fetch", response.getBody());

        }
        return apiResponse;
    }

    public ApiResponse<CustomerEntity> updateCustomer(CustomerEntity customer) {
        String url = BASE_URL + "/updateCustomer";
        ResponseEntity<CustomerEntity> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(customer),
                new ParameterizedTypeReference<>() {}
        );
        ApiResponse<CustomerEntity> apiResponse;
        if(response.getBody() !=null ) {
            apiResponse = new ApiResponse<>(true, "Updated the customer", response.getBody());
        }else{
            apiResponse = new ApiResponse<>(false, "customer not found", response.getBody());
        }
        return apiResponse;
    }

    public ApiResponse<String> deleteCustomer(String customerId) {
        String url = BASE_URL + "/deleteCustomer?id={id}";
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<>() {},
                customerId
        );

        ApiResponse<String> apiResponse;
        if(response.getBody().contains("deleted") ) {
            apiResponse = new ApiResponse<>(true, "Deleted the customer", response.getBody());
        }else{
            apiResponse = new ApiResponse<>(false, "customer not found", response.getBody());
        }
        return apiResponse;
    }
}

