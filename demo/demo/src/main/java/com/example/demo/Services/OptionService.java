package com.example.demo.Services;

import com.example.demo.Models.Option;
import com.example.demo.Repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OptionService {

    @Autowired
    private OptionRepository optionRepository;

    // Add an option
    public Option createOption(Option option) {
        return optionRepository.save(option);
    }

    // Retrieve options by question ID
    public List<Option> getOptionsByQuestionId(Long questionId) {
        return optionRepository.findByQuestionId(questionId);
    }

    // Get a specific option by its ID
    public Option getOptionById(Long id) {
        Optional<Option> option = optionRepository.findById(id); // Use the repository's `findById` method
        return option.orElse(null); // Return the option if present, otherwise null
    }

    // Delete an option by its ID
    public void deleteOption(Long id) {
        optionRepository.deleteById(id);
    }
}
