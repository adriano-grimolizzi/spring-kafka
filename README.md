# Spring Kafka Demo

A simple demo that sends a Kafka message with a producer and receives it in a consumer.

## Features: 
- a `docker-compose.yml` file to set up a Kafka broker, configured to run in KRaft - Zookeeper is not used
- a REST controller that initiates the sending of the message with a POST
- Lombok for getters, setters and 
- Spring DevTools for hot reload
- Unit and integration tests
