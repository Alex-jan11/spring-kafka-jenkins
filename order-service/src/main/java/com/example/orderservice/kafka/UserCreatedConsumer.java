package com.example.orderservice.kafka;

import com.example.orderservice.dto.UserCreatedEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserCreatedConsumer {

    @KafkaListener(topics = "user-created", groupId = "order-service-group", containerFactory = "userCreatedKafkaListenerContainerFactory")
    public void consume(UserCreatedEventDTO userCreatedEventDTO) {
        log.info("User created event received: {}", userCreatedEventDTO);
        // Here you can add logic to handle the user created event, such as updating the order service state
        // or triggering other actions based on the user creation.
    }
}
