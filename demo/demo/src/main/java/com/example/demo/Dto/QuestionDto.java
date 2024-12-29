package com.example.demo.Dto;


import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuestionDto {

    private String questionText;
    private Long quizId;
    private List<OptionDto> options;
}