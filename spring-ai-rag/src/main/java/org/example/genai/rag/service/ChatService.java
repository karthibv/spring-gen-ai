package org.example.genai.rag.service;

import org.apache.commons.lang3.tuple.Pair;
import org.example.genai.rag.ChatBot;
import org.example.genai.rag.ChatMessage;
import org.example.genai.rag.repository.ChatRepository;
import org.example.genai.rag.repository.DocsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;


@Service
public class ChatService {



    private final ChatRepository chatRepository;
    private final DocsRepository docsRepository;
    private final ChatBot chatBot;
    private final Supplier<UUID> generateId;
    private final String contextMessage;

    @Autowired
    public ChatService(ChatRepository chatRepository, DocsRepository docsRepository, ChatBot chatBot, Supplier<UUID> generateId, String contextMessage) {
        this.chatRepository = chatRepository;
        this.docsRepository = docsRepository;
        this.chatBot = chatBot;
        this.generateId = generateId;
        this.contextMessage = contextMessage;
    }



    public Pair<UUID, String> chat(UUID chatId, String userMessage) {
        UUID id = chatId != null ? chatId : generateId.get();
        List<ChatMessage> previousChats = chatRepository.getPreviousChats(id);
        List<String> relatedDocs = docsRepository.getRelatedDocsFor(userMessage);
        String response = chatBot.chat(userMessage, previousChats, relatedDocs, contextMessage);
        chatRepository.saveChat(id, userMessage, response);
        return Pair.of(id, response);
    }
}