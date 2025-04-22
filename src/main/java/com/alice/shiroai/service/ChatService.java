package com.alice.shiroai.service;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

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
        String text = response.getResult().getOutput().getText();
        return text;
    }

    /**
     * 流式聊天
     *
     * @param message 消息
     * @return 响应
     */
    public Flux<String> fluxchat(String message) {
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemResource);
        Message usermessage = new UserMessage(message);
        Message systemMessage = new SystemMessage(systemResource);
        List<Message> messages = new ArrayList<>();
        messages.add(systemMessage);
        messages.add(usermessage);
        Flux<ChatResponse> response = openAiChatModel.stream(new Prompt(
                messages,
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
