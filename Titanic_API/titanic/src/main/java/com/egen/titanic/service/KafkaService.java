package com.egen.titanic.service;

import org.springframework.stereotype.Service;

@Service
public interface KafkaService {
    String publishMessage(String topic, Object anyObject);
}
