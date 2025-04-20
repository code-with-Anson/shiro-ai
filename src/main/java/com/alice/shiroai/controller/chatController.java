package com.alice.shiroai.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
@Tag(name = "ai聊天控制器")

public class chatController {

    private final OpenAiChatModel openAiChatModel;
    @Value("classpath:/prompts/alice-system-message.st")
    private Resource systemResource;

    public chatController(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    @GetMapping("/chat")
    @Operation(summary = "聊天接口")
    public ChatResponse chat(@RequestParam(value = "传递的问题", defaultValue = "你好。请问你是？") String message) {
        UserMessage userMessage = new UserMessage(message);
        // 创建系统提示模板
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(this.systemResource);

        Message systemMessage = systemPromptTemplate.createMessage();

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        // 调用AI模型进行聊天

        return openAiChatModel.call(prompt);

    }
}