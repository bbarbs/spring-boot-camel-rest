package com.demo.service.impl;

import com.demo.model.Customer;
import com.demo.repository.CustomerRepository;
import com.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @Override
    public Customer addCustomer(Customer entity) {
        return this.customerRepository.saveAndFlush(entity);
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return this.customerRepository.findOne(customerId);
    }

    @Override
    public Customer updateCustomerById(Long id, Customer entity) {
        Customer cust = this.customerRepository.findOne(id);
        cust.setFirstname(entity.getFirstname());
        cust.setLastname(entity.getLastname());
        return this.customerRepository.saveAndFlush(cust);
    }

    @Override
    public void removeCustomerById(Long id) {
        this.customerRepository.delete(id);
    }
}
