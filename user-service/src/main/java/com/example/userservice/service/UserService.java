package com.example.userservice.service;

import com.example.userservice.dto.UserCreatedEventDTO;
import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.dto.UserResponseDTO;
import com.example.userservice.kafka.UserProducer;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserProducer userProducer;

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO);
        userRepository.save(user);
        UserResponseDTO userResponseDTO = userMapper.toResponseDTO(user);

        // trimite un eveniment kafka pentru a notifica alte microservicii
        userProducer.sendUserCreatedEvent(
            new UserCreatedEventDTO(user.getId(), user.getName(), user.getEmail())
        );

        return userResponseDTO;
    }
}
