package com.alice.shiroai.controller;

import com.alice.shiroai.service.impl.ChatMessageServiceImpl;
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
public class ChatMessageController {
    private final ChatMessageServiceImpl chatMessageServiceImpl;

    public ChatMessageController(ChatMessageServiceImpl chatMessageServiceImpl) {
        this.chatMessageServiceImpl = chatMessageServiceImpl;
    }

    @GetMapping(value = "/flux-ChatClient/OpenAi-momoi", produces = "text/html;charset=UTF-8")
    @Operation(summary = "链式实现ChatClient聊天接口")
    public Flux<String> chatWithOpenAiByMomoi(@RequestParam(value = "message", defaultValue = "给我讲个笑话？") String message) {
        log.info("链式构成ChatClient/OpenAi聊天接口被调用，消息内容：{}", message);
        Flux<String> fluxchat = chatMessageServiceImpl.chatWithOpenAiByMomoi(message);
        log.info("{}调用完成", "momoi");
        return fluxchat;
    }
}