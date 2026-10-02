package com.mrsac.lettervalidation.dto;

import com.mrsac.lettervalidation.entity.DocumentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class DocumentUploadResponse {

    private Long id;
    private String fileName;
    private DocumentStatus status;
}
