package com.alice.shiroai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class AiConfig {

    @Value("classpath:/prompts/alice-system-message.st")
    private Resource systemResource;


    @Bean
    public ChatMemory memory() {
        return new InMemoryChatMemory();
    }

    @Bean
    public ChatClient Momoi(OpenAiChatModel openAiChatModel, ChatMemory chatMemory) {
        return ChatClient.builder(openAiChatModel)
                .defaultSystem(systemResource)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory) // 使用内存聊天记录
                )
                .build();
    }
}
