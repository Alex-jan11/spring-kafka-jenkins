package com.example.orderservice.dto;

import lombok.Data;

@Data
public class UserCreatedEventDTO {
    private Long id;
    private String name;
    private String email;

}
