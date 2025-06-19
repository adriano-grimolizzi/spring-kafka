package com.example;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@ContextConfiguration(classes = MessageController.class)
public class MessageControllerTests {

  private static final String URL = "/api/messages";

  @Autowired private MockMvc mvc;
  @MockitoBean private MyKafkaProducer producer;

  @Test
  @DisplayName("should send message")
  void shouldSendMessage() throws Exception {
    String message = "Overture - From Whiplash";

    mvc.perform(
            post(URL) //
                .content(message)
                .contentType(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk());

    verify(producer).sendMessage(message);
  }
}
