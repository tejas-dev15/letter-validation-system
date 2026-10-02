package com.mrsac.lettervalidation.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class KnowledgeBaseService {

    public String extractKnowledgeBase() throws IOException{

        ClassPathResource resource = new
                ClassPathResource("Knowledge_Base\\mrsac_dummy_knowledge_base.txt");

        return resource.getContentAsString(StandardCharsets.UTF_8);
    }
}
