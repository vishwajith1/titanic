package com.egen.titanic.service;

import com.egen.titanic.service.KafkaService;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaServiceImpl implements KafkaService {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    Gson gson;

    @Override
    public String publishMessage(String topic, Object anyObject) {
        String convertedString = null;
        try {
            convertedString = gson.toJson(anyObject);
            kafkaTemplate.send(topic, convertedString);
            log.info("Data Sent to Kafka: " + convertedString);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return convertedString;
    }

//    @KafkaListener(topics = {"CREATE","MODIFY","CANCEL"}, groupId = "group-id")
//    public void listen(String message) {
//        log.info("Data Received From Kafka: " + message);
//    }
}
