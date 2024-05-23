package org.example.genai.rag.config;


import org.example.genai.rag.ChatBot;
import org.example.genai.rag.repository.ChatRepository;
import org.example.genai.rag.repository.DocsRepository;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
import java.util.function.Supplier;

@Configuration
public class AppConfig {

    private static final String defaultContextMessage = "You're playing a role of a virtual customer agent in a company called XMWV.\n" +
            "At XMWV offers many technology solution and software as a services , also provides AI solution and  \n" +
            "experiences and personalized Software as a Services.\n" +
            "Please use Use the information from the DOCUMENTS section to deliver precise responses, presenting the knowledge\n" +
            "as if it was innate. If uncertain, plainly admit to not knowing and explain that you are a virtual agent.\n" +
            "Ask always for the user's name and use it in the conversation.\n" +
            "\n" +
            "DOCUMENTS:\n" +
            "{documents}\n";

    @Bean
    public ChatRepository chatRepository() {
        return new ChatRepository(); // replace with your actual ChatRepository implementation
    }
    @Bean
    VectorStore vectorStore(EmbeddingClient embeddingClient) {
        // It's like you using H2 database for your application when making some demo
        // VectorStore is also the same case
        return new SimpleVectorStore(embeddingClient);
    }

    @Bean
    public Supplier<UUID> generateId() {
        return UUID::randomUUID;
    }

    @Bean
    public String contextMessage() {
        return defaultContextMessage;



    }

}