package com.mrsac.lettervalidation.controller;

import com.mrsac.lettervalidation.dto.GeneratedResponse;
import com.mrsac.lettervalidation.entity.Document;
import com.mrsac.lettervalidation.service.DocumentService;
import com.mrsac.lettervalidation.service.LetterValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/response")
@RequiredArgsConstructor
public class ResponseController {

    private final DocumentService documentService;
    private final LetterValidationService letterValidationService;

    @GetMapping("{id}")
    public GeneratedResponse generatedResponse(@PathVariable Long id){
        Document document = documentService.getDocumentById(id);

        GeneratedResponse response =
               letterValidationService.validate(document.getExtractedText());
        return response;
    }
}
