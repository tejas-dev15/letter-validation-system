package com.mrsac.lettervalidation.service;

import com.mrsac.lettervalidation.dto.DocumentUploadResponse;
import com.mrsac.lettervalidation.entity.Document;
import com.mrsac.lettervalidation.entity.DocumentStatus;
import com.mrsac.lettervalidation.exception.InvalidDocumentException;
import com.mrsac.lettervalidation.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.mrsac.lettervalidation.entity.DocumentStatus.OCR_COMPLETED;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final OcrService ocrService;

    private final Path uploadDirectory = Paths.get("uploads");

    public DocumentUploadResponse uploadDocument(MultipartFile file) throws IOException{

        if(file == null ||file.isEmpty()){
            throw new InvalidDocumentException("uploaded file is empty");
        }

        if(file.getSize() > 10 * 1024 * 1024){
            throw new InvalidDocumentException("File size should not exceed 10 MB");
        }

        String contentType = file.getContentType();

        if(!isAllowedContentType(contentType)){
            throw new InvalidDocumentException("Only PDF, JPEG and PNG files are allowed");
        }

        Files.createDirectories(uploadDirectory);

        String originalFileName = file.getOriginalFilename();

        String storedFileName = UUID.randomUUID() + "_" + originalFileName;

        Path filePath = uploadDirectory.resolve(storedFileName);

        Files.copy(file.getInputStream() ,filePath);

        Document document = Document.builder()
                .originalFileName(originalFileName)
                .fileType(file.getContentType())
                .filePath(filePath.toString())
                .status(DocumentStatus.UPLOADED)
                .uploadedAt(LocalDateTime.now())
                .build();

        Document savedDocument = documentRepository.save(document);

        try{
            String extractedText = ocrService.extractText(filePath.toString());

            savedDocument.setExtractedText(extractedText);
            savedDocument.setStatus(OCR_COMPLETED);
            System.out.println("OCR RESULT:");
            System.out.println(extractedText);

            documentRepository.save(savedDocument);

        }catch(TesseractException e){

            savedDocument.setStatus(DocumentStatus.FAILED);
            documentRepository.save(savedDocument);

            throw new RuntimeException("OCR processing failed", e);
        }

        return DocumentUploadResponse.builder()
                .id(savedDocument.getId())
                .fileName(savedDocument.getOriginalFileName())
                .status(savedDocument.getStatus())
                .build();

    }

    public boolean isAllowedContentType(String contentType){
        return "application/pdf".equals(contentType)
                || "image/png".equals(contentType)
                || "image/jpeg".equals(contentType);
    }

    public Document getDocumentById(long id){
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not Found"));
    }

    public void deleteDocumentById(long id){
        documentRepository.deleteById(id);
    }
}
