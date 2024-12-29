package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OptionDto {
    private Long id;
    private String text;
    private Boolean isCorrect;
    private Long questionId;
}
