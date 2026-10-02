package com.mrsac.lettervalidation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LetterDetails {

    //Basic letter Information
    private String date;
    private String referenceNumber;
    private String recipient;
    private String subject;
    private String salutation;
    private String sender;
    private String organization;
    private String department;
    private String body;

    //Content related information
    private String requestType;
    private String purpose;
    private String geographicArea;
    private String timePeriod;
    private List<String> dataRequirements;
    private String projectActivity;
    private String requestedAction;
}
