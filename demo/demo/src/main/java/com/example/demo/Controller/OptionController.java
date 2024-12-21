package com.example.demo.Controller;

import com.example.demo.Models.Option;
import com.example.demo.Services.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/options")
public class OptionController {

    @Autowired
    private OptionService optionService;

    // Create a new option for a question
    @PostMapping
    public ResponseEntity<Option> createOption(@RequestBody Option option) {
        Option createdOption = optionService.createOption(option);
        return ResponseEntity.ok(createdOption);
    }

    // Get all options for a specific question
    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<Option>> getOptionsByQuestion(@PathVariable Long questionId) {
        List<Option> options = optionService.getOptionsByQuestionId(questionId); // Fixed method call
        return ResponseEntity.ok(options);
    }

    // Get a specific option by ID
    @GetMapping("/{id}")
    public ResponseEntity<Option> getOptionById(@PathVariable Long id) {
        Option option = optionService.getOptionById(id); // Fixed method call
        return option != null ? ResponseEntity.ok(option) : ResponseEntity.notFound().build();
    }
}
