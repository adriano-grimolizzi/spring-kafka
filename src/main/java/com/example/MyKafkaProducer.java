package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyKafkaProducer {

  private static final String TOPIC = "my-topic";

  @Autowired private KafkaTemplate<String, String> kafkaTemplate;

  public void sendMessage(String message) {
    log.info("Sending message: \"{}\" to topic: \"{}\"", message, TOPIC);
    kafkaTemplate.send(TOPIC, message);
  }
}
