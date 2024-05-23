package org.example.genai.rag;

import java.util.UUID;

public class ChatResponse {
    private UUID chatId;
    private String message;

    // constructor, getters and setters

    public ChatResponse(UUID chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}