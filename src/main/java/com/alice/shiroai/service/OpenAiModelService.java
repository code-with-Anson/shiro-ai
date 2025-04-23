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

/**
 * 这个接口用来演示如何使用Spring AI的OpenAiChatModel进行流式聊天
 */
@Service
public class OpenAiModelService {

    // 注入OpenAiChatModel
    private final OpenAiChatModel openAiChatModel;
    @Value("classpath:/prompts/alice-system-message.st")
    private Resource systemResource;

    public OpenAiModelService(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    /**
     * 流式聊天接口
     *
     * @param message 用户消息
     * @return 返回流式聊天结果
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
