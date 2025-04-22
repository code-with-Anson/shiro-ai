package com.alice.shiroai.service;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;


@Service
public class ChatService {

    // 注入OpenAiChatModel
    private final OpenAiChatModel openAiChatModel;
    @Value("classpath:/prompts/alice-system-message.st")
    private Resource systemResource;

    public ChatService(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    /**
     * 流式聊天
     *
     * @param message 消息
     * @return 响应
     * Author: Anson
     */
    public Flux<String> fluxchat(String message) {
        Message usermessage = new UserMessage(message);
        Message systemMessage = new SystemMessage(systemResource);
        List<Message> messages = new ArrayList<>();
        messages.add(systemMessage);
        messages.add(usermessage);

        // 处理聊天逻辑
        return openAiChatModel.stream(String.valueOf(messages));
    }
}
