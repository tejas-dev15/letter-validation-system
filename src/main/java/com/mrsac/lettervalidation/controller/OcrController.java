package com.mrsac.lettervalidation.controller;

import com.mrsac.lettervalidation.service.OcrService;
import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ocr")
public class OcrController {

    private final OcrService ocrService;

    @GetMapping("/test")
    public String testOcr(@RequestParam String filePath) throws TesseractException {

        return ocrService.extractText(filePath);
    }
}
