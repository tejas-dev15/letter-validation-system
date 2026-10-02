package com.mrsac.lettervalidation.controller;

import com.mrsac.lettervalidation.dto.ContentValidationResult;
import com.mrsac.lettervalidation.dto.LetterDetails;
import com.mrsac.lettervalidation.entity.Document;
import com.mrsac.lettervalidation.service.ContentValidationService;
import com.mrsac.lettervalidation.service.DocumentService;
import com.mrsac.lettervalidation.service.KnowledgeBaseService;
import com.mrsac.lettervalidation.service.LetterExtractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/letters")
@RequiredArgsConstructor
public class LetterExtractionController {

    private final LetterExtractionService letterExtractionService;
    private final KnowledgeBaseService knowledgeBaseService;
    private final ContentValidationService contentValidationService;
    private final DocumentService documentService;

    @PostMapping("/extract")
    public LetterDetails extract(@RequestBody String letterText){
        return letterExtractionService.extractLetterDetails(letterText);
    }

    @GetMapping("/extract-KB")
    public String knowledgeBase() throws IOException {
        return knowledgeBaseService.extractKnowledgeBase();
    }

    @GetMapping("/validate-content")
    public String validate(@RequestBody LetterDetails letterDetails){
        return contentValidationService.validateContent(letterDetails);
    }

    @GetMapping("/extract-LetterDetails/{id}")
    public LetterDetails extractLetterDetails(@PathVariable long id){
       Document Letter = documentService.getDocumentById(id);
       return letterExtractionService.extractLetterDetails(Letter.getExtractedText());
    }
}
