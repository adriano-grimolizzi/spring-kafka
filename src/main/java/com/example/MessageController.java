package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/messages")
public class MessageController {

  @Autowired private MyKafkaProducer producer;

  @PostMapping
  public ResponseEntity<Void> sendMessage(@RequestBody String message) {
    log.info("Sending message: \"{}\"", message);
    producer.sendMessage(message);
    return ResponseEntity.ok().build();
  }
}
