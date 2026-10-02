package com.mrsac.lettervalidation.controller;

import com.mrsac.lettervalidation.dto.LetterFormatValidationResult;
import com.mrsac.lettervalidation.entity.Document;
import com.mrsac.lettervalidation.service.FormatValidationService;
import com.mrsac.lettervalidation.service.LetterExtractionService;
//import com.mrsac.lettervalidation.service.LetterValidationService;
import com.mrsac.lettervalidation.dto.DocumentUploadResponse;
import com.mrsac.lettervalidation.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    //private final LetterValidationService letterValidationService;
    private final FormatValidationService formatValidationService;

    @PostMapping("/save-letter")
    public ResponseEntity<DocumentUploadResponse> uploadDocument
            (@RequestParam("file")MultipartFile file) throws IOException {

           DocumentUploadResponse response = documentService.uploadDocument(file);

           return ResponseEntity.ok(response);
    }

    @GetMapping("{id}/validate-format")
    public ResponseEntity<LetterFormatValidationResult>
    validateDocument(@PathVariable long id){

        Document document = documentService.getDocumentById(id);

        LetterFormatValidationResult result = formatValidationService.validateFormat(document.getExtractedText());

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteDocument(@PathVariable long id){
        documentService.deleteDocumentById(id);
    }
}
