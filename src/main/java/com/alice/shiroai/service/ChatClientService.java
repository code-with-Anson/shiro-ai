package com.alice.shiroai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;


@Service
public class ChatClientService {
    // 注入OpenAiChatModel
    private final OpenAiChatModel openAiChatModel;
    private final ChatClient alice; // 只声明，不初始化

    @Value("classpath:/prompts/alice-system-message.st")
    private Resource systemResource;

    public ChatClientService(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
        this.alice = ChatClient.create(this.openAiChatModel); // 在这里初始化 alice
    }

    /**
     * 使用alice进行聊天（将数组列表消息使用Prompt自带的转换功能转换）
     *
     * @param message
     * @return
     */
    public Flux<String> chatWithOpenAi(String message) {
        Message usermessage = new UserMessage(message);
        Message systemMessage = new SystemMessage(systemResource);
        List<Message> messages = new ArrayList<>();
        messages.add(systemMessage);
        messages.add(usermessage);
        Prompt prompt = new Prompt(messages);

        Flux<String> kei = alice.prompt(prompt)
                .stream()
                .content();
        return kei;
    }

    /**
     * 使用Momoi进行聊天（直接使用链式编程传入参数）
     *
     * @param message
     * @return
     */
    public Flux<String> chatWithOpenAiByMomoi(String message) {

        ChatClient momoi = ChatClient.create(openAiChatModel);
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .temperature(1.0)
                .build();

        // 直接使用链式编程传入参数
        Flux<String> modori = momoi.prompt(message)
                .system(systemResource)
                .user(message)
                .options(options)
                .stream()
                .content();
        return modori;
    }
}
