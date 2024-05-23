package org.example.genai.rag;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ChatBot {

    private final ChatClient chatClient;

    public ChatBot(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String chat(String userMessage, List<ChatMessage> previousChats, List<String> relatedDocs, String contextMessage) {
        String docsAsString = relatedDocs.stream().collect(Collectors.joining(System.lineSeparator()));
        Map<String, Object> documentsMap = new HashMap<>();
        documentsMap.put("documents", docsAsString);
        Message systemMessage = new SystemPromptTemplate(contextMessage).createMessage(documentsMap);

        List<Message> previousMessages = new ArrayList<>();
        for (ChatMessage chatMessage : previousChats) {
            previousMessages.add(new UserMessage(chatMessage.getQuestion()));
            previousMessages.add(new AssistantMessage(chatMessage.getAnswer()));
        }
        previousMessages.add(systemMessage);
        previousMessages.add(new UserMessage(userMessage));
        Prompt prompt = new Prompt(previousMessages);
        ChatResponse chatResponse = chatClient.call(prompt);
        return chatResponse.getResult().getOutput().getContent();
    }
}