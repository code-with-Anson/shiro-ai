package com.alice.shiroai.controller;

import com.alice.shiroai.domain.dto.ChatRequestDTO;
import com.alice.shiroai.service.IChatMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public Flux<String> chatWithOpenAiByMomoi(@RequestBody ChatRequestDTO chatRequestDTO) {
        log.info("带上下文聊天接口被调用，会话ID: {}, 消息内容: {}",
                chatRequestDTO.getConversationId(), chatRequestDTO.getMessage());
        return IchatMessageService.chatWithOpenAiByMomoi(chatRequestDTO);
    }
}