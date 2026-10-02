package com.mrsac.lettervalidation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LetterFormatValidationResult {

    private boolean formatValid;

    private List<String> missingFields;

    private List<String> issues;
}
