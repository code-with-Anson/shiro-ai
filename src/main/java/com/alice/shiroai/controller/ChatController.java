package com.alice.shiroai.controller;

import com.alice.shiroai.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
@Tag(name = "ai聊天控制器")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping(value = "/alice", produces = "text/html;charset=UTF-8")
    @Operation(summary = "聊天接口")
    public String chat(@RequestParam(value = "传递的问题", defaultValue = "你好。请问你是？") String message) {
        System.out.println();
        String res = chatService.chat(message);
        return res;
    }

    @GetMapping(value = "/flux-alice", produces = "text/html;charset=UTF-8")
    @Operation(summary = "聊天接口")
    public Flux<String> fluxchat(@RequestParam(value = "message", defaultValue = "给我讲个笑话？") String message) {
        System.out.println();
        Flux<String> fluxchat = chatService.fluxchat(message);
        return fluxchat;
    }
}