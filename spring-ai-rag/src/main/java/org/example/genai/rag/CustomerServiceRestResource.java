package org.example.genai.rag;

import org.apache.commons.lang3.tuple.Pair;
import org.example.genai.rag.service.ChatService;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/bot-service")
public class CustomerServiceRestResource {

    private final ChatService chatService;

    public CustomerServiceRestResource(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chats")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        Pair<UUID, String> result = chatService.chat(UUID.randomUUID(), request.getMessage());
        return new ChatResponse(result.getLeft(), result.getRight());
    }

}