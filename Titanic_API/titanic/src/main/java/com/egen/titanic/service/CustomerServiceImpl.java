package com.egen.titanic.service;

import com.egen.titanic.entity.Customer;
import com.egen.titanic.service.CustomerService;
import com.egen.titanic.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Optional<Customer> getById(Integer id) {
        return customerRepository.findById(id);
    }

    public Customer saveOrUpdate(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    public void deleteById(Integer id) {
        customerRepository.deleteById(id);
    }
}
