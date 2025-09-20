package com.github.chat_ai.service;

import java.io.IOException;

public interface DocIngestionService {
     void storeDocument(String filePath) throws IOException;
}
