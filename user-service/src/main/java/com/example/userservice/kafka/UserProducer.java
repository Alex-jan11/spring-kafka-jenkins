package com.example.userservice.kafka;

import com.example.userservice.dto.UserCreatedEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProducer {
    private final KafkaTemplate<String, UserCreatedEventDTO> kafkaTemplate;

    public void sendUserCreatedEvent(UserCreatedEventDTO userCreatedEventDTO) {
        kafkaTemplate.send("user.created", userCreatedEventDTO);
    }
}
