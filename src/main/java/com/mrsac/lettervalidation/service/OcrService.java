package com.mrsac.lettervalidation.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class OcrService {

    private final Tesseract tesseract;

    public OcrService(){
        tesseract = new Tesseract();

        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");

        tesseract.setLanguage("eng");
    }

    public String extractText(String filePath) throws TesseractException{
        File file = new File(filePath);

        String fileName = file.getName().toLowerCase();

        if(fileName.endsWith(".pdf")){
           return extractTextFromPdf(file);
        }

        return tesseract.doOCR(file);
    }


    public String extractTextFromPdf(File pdfFile) throws TesseractException{

        StringBuilder extractText = new StringBuilder();

        try(PDDocument document = Loader.loadPDF(pdfFile)) {

            PDFRenderer renderer = new PDFRenderer(document);

            for(int page =0; page<document.getNumberOfPages(); page++){

                BufferedImage image = renderer.renderImageWithDPI(page, 300);

                String pageText = tesseract.doOCR(image);

                extractText.append(pageText).append("\n");
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to process PDF",e);
        }
        return extractText.toString();
    }
}
