package com.mrsac.lettervalidation.service;

import com.mrsac.lettervalidation.dto.ContentValidationResult;
import com.mrsac.lettervalidation.dto.LetterDetails;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;


@Service
public class ContentValidationService {

    private final ChatClient chatClient;
    private final KnowledgeBaseRetriever knowledgeBaseRetriever;

    public ContentValidationService(
            ChatClient.Builder chatClientBuilder,
            KnowledgeBaseRetriever knowledgeBaseRetriever) {

        this.chatClient = chatClientBuilder.build();
        this.knowledgeBaseRetriever = knowledgeBaseRetriever;
    }

    public String validateContent(LetterDetails letterDetails) {

        String knowledgeBase =
                knowledgeBaseRetriever.retrieveRules(
                        letterDetails.getRequestType());

        return chatClient
                .prompt()
                .system("""
                        You are a content validation system for an
                        organization handling geospatial and remote
                        sensing related requests.

                        Your task is to determine whether the
                        information contained in a letter satisfies
                        the applicable validation rules provided in
                        the knowledge base.

                        IMPORTANT RULES:

                        1. Use ONLY the provided knowledge base
                           when determining whether the request
                           satisfies the requirements.

                        2. Do NOT invent missing information.

                        3. Do NOT assume information that is not
                           explicitly present.

                        4. Apply only rules relevant to the request.

                        5. Do NOT mark optional information as missing.

                        6. If a rule is not applicable, do not report
                           it as a failure.

                        7. If required information is missing, clearly
                           list it under MISSING INFORMATION.

                        8. If information is contradictory or
                           inconsistent, clearly explain it under
                           ISSUES.

                        9. The request type has already been extracted.
                           Do not change or reinterpret it.

                        10. Validate the extracted letter information
                            against the knowledge base only.

                        11. Determine CONTENT VALID as true only when
                            all applicable required content
                            requirements are satisfied.

                        RETURN THE RESULT IN EXACTLY THIS FORMAT:

                        CONTENT VALID: true/false

                        MISSING INFORMATION:
                        - item 1
                        - item 2

                        ISSUES:
                        - issue 1
                        - issue 2

                        If there is no missing information, write:
                        None

                        If there are no issues, write:
                        None

                        Do not return JSON.
                        Do not use markdown code blocks.
                        Do not add explanations outside this format.

                        KNOWLEDGE BASE:
                        ==============================
                        %s
                        ==============================
                        """.formatted(knowledgeBase))
                .user("""
                        Validate the following extracted letter
                        information against the knowledge base.

                        REQUEST TYPE:
                        %s

                        PURPOSE:
                        %s

                        GEOGRAPHIC AREA:
                        %s

                        TIME PERIOD:
                        %s

                        DATA REQUIREMENTS:
                        %s

                        PROJECT ACTIVITY:
                        %s

                        REQUESTED ACTION:
                        %s

                        Determine whether the content satisfies
                        all applicable requirements from the
                        knowledge base.

                        Do not invent information that is not
                        explicitly present in the extracted
                        letter information.
                        """.formatted(
                        letterDetails.getRequestType(),
                        letterDetails.getPurpose(),
                        letterDetails.getGeographicArea(),
                        letterDetails.getTimePeriod(),
                        letterDetails.getDataRequirements(),
                        letterDetails.getProjectActivity(),
                        letterDetails.getRequestedAction()
                ))
                .call()
                .content();
    }
}
