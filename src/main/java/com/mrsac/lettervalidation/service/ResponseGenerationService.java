package com.mrsac.lettervalidation.service;

import com.mrsac.lettervalidation.dto.ContentValidationResult;
import com.mrsac.lettervalidation.dto.GeneratedResponse;
import com.mrsac.lettervalidation.dto.LetterFormatValidationResult;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ResponseGenerationService {

    private final ChatClient chatClient;

    public ResponseGenerationService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public GeneratedResponse generateResponse(
            LetterFormatValidationResult formatResult,
            String contentResult) {

        String response = chatClient
                .prompt()
                .system("""
                        You are a response-generation system for an
                        organization handling geospatial and remote
                        sensing related requests.

                        Your task is to generate the actual response
                        message that the organization should send to
                        a client based ONLY on the provided validation
                        results.

                        IMPORTANT RULES:

                        1. If the format or content is invalid,
                           clearly explain what the client needs
                           to correct.

                        2. If both format and content are valid,
                           simply state that the submitted request
                           is valid and can proceed for further
                           processing.

                        3. Do NOT invent additional requirements.

                        4. Do NOT change or reinterpret the
                           validation results.

                        5. Do NOT include:
                           - Subject lines
                           - Greetings or salutations
                           - Client name placeholders
                           - Sender name placeholders
                           - Organization placeholders
                           - Signatures
                           - Contact information
                           - "Best regards"
                           - "Yours faithfully"
                           - Any other letter-format elements

                        6. Do not create a letter template.

                        7. Do not mention AI, LLMs, RAG, prompts,
                           validation systems, or internal
                           implementation details.

                        8. If issues exist, explain clearly what
                           information or correction is required.

                        9. If multiple issues exist, present them
                           as a numbered list.

                        10. Be professional, concise, polite,
                            and easy to understand.

                        11. End by telling the client to correct
                            the identified issues and resubmit the
                            request when the request is invalid.

                        Return ONLY the client-facing response
                        as plain text.
                        """)
                .user("""
                        Generate the appropriate client-facing
                        response based ONLY on these validation
                        results.

                        FORMAT VALIDATION:
                        ------------------
                        Format valid:
                        %s

                        Missing fields:
                        %s

                        Format issues:
                        %s
                        ------------------

                        CONTENT VALIDATION:
                        -------------------
                        %s
                        -------------------

                        Determine the appropriate response based
                        strictly on these results.
                        """.formatted(
                        formatResult.isFormatValid(),
                        formatResult.getMissingFields(),
                        formatResult.getIssues(),
                        contentResult
                ))
                .call()
                .content();



        return GeneratedResponse.builder()
                .response(response)
                .build();
    }
}