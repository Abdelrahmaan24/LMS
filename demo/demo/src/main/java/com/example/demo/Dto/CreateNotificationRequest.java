package com.example.demo.Dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class CreateNotificationRequest {
    private String message;
    private String type;
}
