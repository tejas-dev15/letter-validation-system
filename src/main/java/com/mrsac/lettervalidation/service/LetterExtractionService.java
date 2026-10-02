package com.mrsac.lettervalidation.service;


import com.mrsac.lettervalidation.dto.LetterDetails;
import com.mrsac.lettervalidation.dto.LetterFormatValidationResult;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class LetterExtractionService {

    private final ChatClient chatClient;

    public LetterExtractionService(ChatClient.Builder chatClientBuilder){
        this.chatClient = chatClientBuilder.build();
    }

    public LetterDetails extractLetterDetails(String letterText){

        return chatClient.prompt()
                .system("""
                        You are an information extraction system
                        for an organization that handles geospatial
                        and remote sensing related data requests.

                        Your task is to extract information from
                        the provided letter.

                        IMPORTANT RULES:

                        1. Extract only information explicitly present
                           in the letter.

                        2. Never invent or assume information.

                        3. If information is not present, return null.

                        4. Do not use external knowledge.

                        5. requestType must be one of:
                           SATELLITE_DATA
                           GIS_VECTOR_DATA
                           LAND_USE_LAND_COVER_DATA
                           TECHNICAL_MAPPING_SUPPORT
                           GEOSPATIAL_ANALYSIS_SUPPORT
                           OTHER

                        6. dataRequirements should contain the specific
                           data or information requested by the sender.

                        7. requestedAction should describe what the
                           sender is asking the organization to do.

                        Extract the information into the LetterDetails
                        structure.
                        """)
                .user("""
                        Extract the details from the following letter:

                        ---
                        %s
                        ---
                        """.formatted(letterText))
                .call()
                .entity(LetterDetails.class);
    }

    public LetterFormatValidationResult validateFormat(
            String letterText) {

        return chatClient
                .prompt()
                .system("""
                        You are a formal letter format validation system.

                        Analyze the structure of the provided letter.

                        Required sections are:

                        1. Date
                        2. Recipient
                        3. Subject
                        4. Salutation
                        5. Body
                        6. Closing
                        7. Sender

                        Reference number is optional.

                        IMPORTANT RULES:

                        - Identify sections based on their meaning,
                          not exact wording or formatting.

                        - Accept reasonable variations in spacing,
                          punctuation, wording, and layout.

                        - Accept legitimate formal closings such as
                          "Yours Faithfully", "Yours Sincerely",
                          "Regards", and "Thanking you".

                        - Do not mark the reference number as missing.

                        - Do not invent missing sections.

                        - If a required section is missing, add it to
                          missingFields.

                        - If a structural problem exists, explain it
                          in issues.

                        - formatValid is true only when all required
                          sections are present and there are no
                          significant structural problems.

                        Return the result using the requested
                        LetterFormatValidationResult structure.
                        """)
                .user("""
                        Analyze the format of this letter:

                        ---
                        %s
                        ---
                        """.formatted(letterText))
                .call()
                .entity(LetterFormatValidationResult.class);
    }


}

