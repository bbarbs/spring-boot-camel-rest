package com.demo.route;

import com.demo.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class CustomerRouteTest {

    private static final String URL = "http://localhost:8090/api/customers";

    @Autowired
    TestRestTemplate restTemplate; // Must configure springbootTest annotation to webEnvironment so we can inject TestRestTemplate

    @Test
    public void testShouldAddCustomer() {
        Customer body = new Customer();
        body.setFirstname("Test");
        body.setLastname("Test");
        ResponseEntity<Customer> postCustomer = this.restTemplate.postForEntity(
                URL,
                body,
                Customer.class
        );
        assertThat(postCustomer.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(postCustomer.getBody());
    }

    @Test
    public void testShouldGetCustomers() {
        // Will return empty array.
        ResponseEntity<List<Customer>> getCustomers = this.restTemplate.exchange(
                URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Customer>>() {
                }
        );
        assertThat(getCustomers.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getCustomers.getBody()).isEqualTo(new ArrayList<>());
    }

    @Test
    public void testShouldGetCustomerById() {
        // Will return null value.
        ResponseEntity<Customer> getCustomerById = this.restTemplate.exchange(
                URL + "/{customerId}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Customer>() {
                },
                1L // Customer id value.
        );
        assertThat(getCustomerById.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNull(getCustomerById.getBody());
    }
}

