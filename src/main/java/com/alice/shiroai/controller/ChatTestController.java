package com.alice.shiroai.controller;

import com.alice.shiroai.service.ChatClientService;
import com.alice.shiroai.service.OpenAiModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
@Tag(name = "ai聊天控制器")
@Slf4j
public class ChatTestController {
    private final OpenAiModelService openAiModelService;
    private final ChatClientService chatClientService;

    public ChatTestController(OpenAiModelService openAiModelService, ChatClientService chatClientService) {
        this.openAiModelService = openAiModelService;
        this.chatClientService = chatClientService;
    }

    /**
     * OpenAiModel聊天接口
     *
     * @param message 用户消息
     * @return 返回流式聊天结果
     */
    @GetMapping(value = "/flux-OpenAiModel", produces = "text/html;charset=UTF-8")
    @Operation(summary = "OpenAiModel聊天接口")
    public Flux<String> fluxchat(@RequestParam(value = "message", defaultValue = "给我讲个笑话？") String message) {
        log.info("OpenAiModel聊天接口被调用，消息内容：{}", message);
        Flux<String> fluxchat = openAiModelService.fluxchat(message);
        return fluxchat;
    }


    /**
     * ChatClient聊天接口
     *
     * @param message 用户消息
     * @return 返回流式聊天结果
     */
    @GetMapping(value = "/flux-ChatClient/OpenAi", produces = "text/html;charset=UTF-8")
    @Operation(summary = "ChatClient聊天接口")
    public Flux<String> chatWithOpenAiByChatClient(@RequestParam(value = "message", defaultValue = "给我讲个笑话？") String message) {
        log.info("ChatClient/OpenAi聊天接口被调用，消息内容：{}", message);
        Flux<String> fluxchat = chatClientService.chatWithOpenAi(message);
        log.info("Alice调用完成");
        return fluxchat;
    }


    /**
     * 链式编程ChatClient聊天接口
     *
     * @param message 用户消息
     * @return 返回流式聊天结果
     */
    @GetMapping(value = "/flux-ChatClient/OpenAi-momoi", produces = "text/html;charset=UTF-8")
    @Operation(summary = "链式实现ChatClient聊天接口")
    public Flux<String> chatWithOpenAiByChatClientBymomoi(@RequestParam(value = "message", defaultValue = "给我讲个笑话？") String message) {
        log.info("链式构成ChatClient/OpenAi聊天接口被调用，消息内容：{}", message);
        Flux<String> fluxchat = chatClientService.chatWithOpenAiByMomoi(message);
        log.info("Momoi调用完成");
        return fluxchat;
    }
}