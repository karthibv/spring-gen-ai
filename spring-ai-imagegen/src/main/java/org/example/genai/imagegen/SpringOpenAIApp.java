package org.example.genai.imagegen;
import org.springframework.ai.image.ImageClient;
import org.springframework.ai.openai.OpenAiImageClient;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringOpenAIApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringOpenAIApp.class, args);
    }

    @Bean
    ImageClient imageClient(@Value("${spring.ai.openai.api-key}") String apiKey) {
        return new OpenAiImageClient(new OpenAiImageApi(apiKey));
    }
}

