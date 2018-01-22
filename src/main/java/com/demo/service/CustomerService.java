package com.demo.service;

import com.demo.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer addCustomer(Customer entity);

    Customer getCustomerById(Long customerId);

    Customer updateCustomerById(Long id, Customer entity);

    void removeCustomerById(Long id);
}
