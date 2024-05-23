package org.example.genai.openai.outparser;


import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class JokeController {

    private String promptTemplate;

    private final ChatClient chatClient;

    public JokeController(ChatClient chatClient,
                          @Value("${app.promptTemplate}") String promptTemplate) {
        this.chatClient = chatClient;
        this.promptTemplate = promptTemplate;
    }
    @GetMapping("/ai/joke")
    public JokeResponse tellJoke(@RequestParam(value="subject",defaultValue = "cinema") String subject) {
        BeanOutputParser<JokeResponse> parser =
                new BeanOutputParser<>(JokeResponse.class);
        String format = parser.getFormat();
        System.err.println("format "+ format);

        PromptTemplate pt = new PromptTemplate(promptTemplate);
        Prompt renderedPrompt = pt.create(Map.of("subject", subject, "format", format));
        ChatResponse response = chatClient.call(renderedPrompt);

        Usage usage = response.getMetadata().getUsage();
        System.err.println("Usage: " + usage.getPromptTokens() + " : " + usage.getGenerationTokens() + ": " + usage.getTotalTokens());
        System.err.println("Output "+response.getResult().getOutput().getContent());

        return parser.parse(response.getResult().getOutput().getContent());
    }

}
