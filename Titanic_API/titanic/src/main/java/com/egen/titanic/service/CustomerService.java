package com.egen.titanic.service;

import com.egen.titanic.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CustomerService {
    Optional<Customer> getById(Integer id);

    Customer saveOrUpdate(Customer customer);

    void deleteById(Integer id);
}
