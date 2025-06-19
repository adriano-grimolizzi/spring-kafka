package com.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyKafkaConsumer {

  @Bean
  public NewTopic topic() {
    return TopicBuilder //
        .name("my-topic")
        .partitions(10)
        .replicas(1)
        .build();
  }

  @KafkaListener(id = "my-id", topics = "my-topic", groupId = "my-group")
  public void listen(String message) {
    log.info("Received message: \"{}\"", message);
  }
}
