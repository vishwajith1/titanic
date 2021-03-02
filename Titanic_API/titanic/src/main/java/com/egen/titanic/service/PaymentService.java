package com.egen.titanic.service;

import com.egen.titanic.entity.Payment;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PaymentService {

    Optional<Payment> getById(Integer id);

    Payment saveOrUpdate(Payment payment);

    void deleteById(Integer id);
}
