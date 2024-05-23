package org.example.genai.rag.repository;

import org.example.genai.rag.ChatMessage;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ChatRepository {

    private static final int limitOfChatsToLoad = 50;
    private final Map<String, Deque<ChatMessage>> chatStore = new ConcurrentHashMap<>();

    public List<ChatMessage> getPreviousChats(UUID id) {
        Deque<ChatMessage> chatMessages = chatStore.getOrDefault(id.toString(), new LinkedList<>());
        return new ArrayList<>(chatMessages).subList(Math.max(chatMessages.size() - limitOfChatsToLoad, 0), chatMessages.size());
    }

    public void saveChat(UUID chatId, String userMessage, String response) {
        Deque<ChatMessage> chatMessages = chatStore.computeIfAbsent(chatId.toString(), k -> new LinkedList<>());
        chatMessages.add(new ChatMessage(userMessage, response));
        // Note: In-memory store does not support expiration like Redis
    }
}
