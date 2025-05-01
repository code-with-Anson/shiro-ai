package com.alice.shiroai.controller;

import com.alice.shiroai.service.IChatMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
@Tag(name = "ai聊天控制器")
@Slf4j
public class ChatMessageController {
    private final IChatMessageService IchatMessageService;

    public ChatMessageController(IChatMessageService IchatMessageService) {
        this.IchatMessageService = IchatMessageService;
    }

    @PostMapping(value = "/flux-ChatClient/OpenAi-momoi", produces = "text/html;charset=UTF-8")
    @Operation(summary = "链式实现ChatClient聊天接口(POST方式)")
    public Flux<String> chatWithOpenAiByMomoi(@RequestParam(defaultValue = "讲个笑话", required = false) String message) {
        log.info("链式构成ChatClient/OpenAi聊天接口被调用，消息内容：{}", message);
        Flux<String> fluxchat = IchatMessageService.chatWithOpenAiByMomoi(message);
        log.info("{}调用完成", "momoi");
        return fluxchat;
    }

    @PostMapping(value = "/query/history")
    @Operation(summary = "获取用户历史聊天主题")
    public String chatWithOpenAiByMomoiPost(@RequestBody String message) {
        return "233";
    }
}