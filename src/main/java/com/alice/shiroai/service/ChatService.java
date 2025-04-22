package com.alice.shiroai.service;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatService {

    private final OpenAiChatModel openAiChatModel;
    @Value("classpath:/prompts/alice-system-message.st")
    private Resource systemResource;

    public ChatService(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    public String chat(String message) {
        ChatResponse response = openAiChatModel.call(new Prompt(
                message,
                ChatOptions.builder()
                        .temperature(0.7)
                        .build()
        ));

        // 处理聊天逻辑
        return response.getResult().getOutput().getText();
    }

    public Flux<String> fluxchat(String message) {
        Flux<ChatResponse> response = openAiChatModel.stream(new Prompt(
                message,
                ChatOptions.builder()
                        .temperature(0.7)
                        .build()
        ));

        // 处理聊天逻辑
        return response.map(chatResponse -> chatResponse.getResult().getOutput().getText())
                .doOnError(throwable -> {
                    // 处理错误
                    System.err.println("Error: " + throwable.getMessage());
                })
                .doOnComplete(() -> {
                    // 完成时的处理
                    System.out.println("Alice Done:" + message);
                });
    }
}
