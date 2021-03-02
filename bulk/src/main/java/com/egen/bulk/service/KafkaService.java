package com.egen.bulk.service;

import org.springframework.stereotype.Service;

@Service
public interface KafkaService {
    String publishMessage(String topic, Object anyObject);
}
