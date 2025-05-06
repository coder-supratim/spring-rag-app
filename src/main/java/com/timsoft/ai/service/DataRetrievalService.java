package com.timsoft.ai.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataRetrievalService {
    private RedisVectorStore vectorStore;




    public List<Document> searchData(String query) {
        return vectorStore.similaritySearch(query);
    }
}
