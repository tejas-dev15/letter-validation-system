package com.mrsac.lettervalidation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ContentValidationResult {

    private boolean ContentValid;

    private String requestType;

    private List<String> missingInformation;

    private List<String> issues;

    private List<String> extractedRequirements;
}