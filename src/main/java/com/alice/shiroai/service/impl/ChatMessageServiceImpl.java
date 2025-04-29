package com.alice.shiroai.service.impl;

import com.alice.shiroai.domain.po.ChatMessage;
import com.alice.shiroai.mapper.ChatMessageMapper;
import com.alice.shiroai.service.IChatMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage> implements IChatMessageService {
    private final ChatClient Momoi;

    @Value("classpath:/prompts/alice-system-message.st")
    private Resource systemResource;

    public ChatMessageServiceImpl(ChatClient Momoi) {
        this.Momoi = Momoi;
    }

    /**
     * 使用Momoi进行聊天（直接使用链式编程传入参数）
     *
     * @param message
     * @return
     */
    @Override
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
