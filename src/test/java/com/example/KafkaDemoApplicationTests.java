package com.example;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

@SpringBootTest
@EmbeddedKafka(
    partitions = 1,
    topics = {"my-topic"})
@TestPropertySource(
    properties = {
      "spring.kafka.consumer.group-id=my-group",
      "spring.kafka.consumer.auto-offset-reset=earliest",
      "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"
    })
class KafkaDemoApplicationTests {

  @Autowired private MyKafkaProducer producer;
  @MockitoSpyBean private MyKafkaConsumer consumer;

  private static final AtomicBoolean messageReceived = new AtomicBoolean(false);
  private static String messageReceivedText = "";

  @BeforeEach
  void resetFlag() {
    messageReceived.set(false);
  }

  @Test
  @DisplayName("should send and receive message")
  void shouldSendAndReceiveMessage() {
    var testMessage = "Take Five - Paul Desmond";

    doAnswer(
            invocation -> {
              messageReceivedText = invocation.getArgument(0);
              messageReceived.set(true);
              return null;
            })
        .when(consumer)
        .listen(testMessage);

    producer.sendMessage(testMessage);

    await().atMost(10, TimeUnit.SECONDS).untilTrue(messageReceived);

    assertTrue(messageReceived.get());
    assertEquals(testMessage, messageReceivedText);
  }
}
