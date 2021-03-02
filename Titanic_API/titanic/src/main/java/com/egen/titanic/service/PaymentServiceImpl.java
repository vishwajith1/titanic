package com.egen.titanic.service;

import com.egen.titanic.entity.Payment;
import com.egen.titanic.service.PaymentService;
import com.egen.titanic.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Optional<Payment> getById(Integer id) {
        return paymentRepository.findById(id);
    }

    @Override
    public Payment saveOrUpdate(Payment payment) {
        return paymentRepository.saveAndFlush(payment);
    }

    @Override
    public void deleteById(Integer id){
        paymentRepository.deleteById(id);
    }
}
