package com.mrsac.lettervalidation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class KnowledgeBaseRetriever {

    private final KnowledgeBaseService knowledgeBaseService;

    public String retrieveRules(String requestType){
        try{
            return knowledgeBaseService.extractKnowledgeBase();
        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve knowledgeBase",e);
        }
    }
}
