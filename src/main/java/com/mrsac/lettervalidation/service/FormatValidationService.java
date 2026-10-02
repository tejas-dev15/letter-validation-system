package com.mrsac.lettervalidation.service;

import com.mrsac.lettervalidation.dto.LetterFormatValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormatValidationService {

    private final LetterExtractionService letterExtractionService;

    public LetterFormatValidationResult validateFormat(String text) {
        LetterFormatValidationResult formatResult =
                letterExtractionService.validateFormat(text);

        return formatResult;
    }
}
