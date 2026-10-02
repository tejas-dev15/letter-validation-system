package com.mrsac.lettervalidation.service;

import com.mrsac.lettervalidation.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class LetterValidationService {

    private final LetterExtractionService letterExtractionService;
    private final ContentValidationService contentValidationService;
    private final ResponseGenerationService responseGenerationService;

    public GeneratedResponse validate(String text) {

       LetterFormatValidationResult formatResult =
               letterExtractionService.validateFormat(text);

        LetterDetails details = letterExtractionService.extractLetterDetails(text);

        String contentResult =
                contentValidationService.validateContent(details);

        return responseGenerationService.generateResponse(formatResult, contentResult);



    }
}