package com.alice.shiroai.service.impl;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
public class ChatClientService {
    private final ChatClient Momoi;

    @Value("classpath:/prompts/alice-system-message.st")
    private Resource systemResource;

    public ChatClientService(ChatClient Momoi) {
        this.Momoi = Momoi;
    }

    /**
     * 使用Momoi进行聊天（直接使用链式编程传入参数）
     *
     * @param message
     * @return
     */
    public Flux<String> chatWithOpenAiByMomoi(String message) {

        // 直接使用链式编程传入参数
        Flux<String> midori = Momoi.prompt()
                .user(message)
                .system(systemResource)
                .stream()
                .content();
        return midori;
    }

}
