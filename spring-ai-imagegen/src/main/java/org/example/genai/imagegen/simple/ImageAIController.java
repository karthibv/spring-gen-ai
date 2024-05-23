package org.example.genai.imagegen.simple;

import com.google.protobuf.Message;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.image.*;
import org.springframework.ai.openai.OpenAiImageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ImageAIController {

    private final ChatClient chatClient;
    private final ImageClient imageClient;

    @Value("classpath:/prompts/image-prompt.txt")
    private Resource imagePrompt;

    public ImageAIController(ChatClient chatClient, ImageClient imageClient) {
        this.chatClient = chatClient;
        this.imageClient = imageClient;
    }


    @GetMapping("/ai/generate/image")
    public String generateImage(
            @RequestParam(name = "animal",defaultValue = "dog") String animal,
            @RequestParam(name = "activity",defaultValue = "running") String activity,
            @RequestParam(name = "mood",defaultValue = "happy") String mood) {

        PromptTemplate promptTemplate = new PromptTemplate(imagePrompt);
        Prompt prompt = promptTemplate.create(Map.of("animal", animal, "activity", activity, "mood", mood));
        System.err.println(prompt.toString());
        ChatResponse response = chatClient.call(prompt);

        String generatedImagePrompt = response.getResult().toString();
        System.err.println("AI responded: generatedImagePrompt");
        ImageOptions imageOptions = ImageOptionsBuilder.builder().withModel("dall-e-3")
                        .build();

        ImagePrompt imagePrompt = new ImagePrompt(generatedImagePrompt, imageOptions);
        ImageResponse imageResponse = imageClient.call(imagePrompt);
        String imageUrl = imageResponse.getResult().getOutput().getUrl();
        System.err.println("imageUrl "+imageUrl);
        return "redirect:"+imageUrl;


    }

}
