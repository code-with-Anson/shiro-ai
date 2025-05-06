package com.alice.shiroai.controller;

import com.alice.shiroai.domain.dto.ChatRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/test/ai")
@Tag(name = "测试用ai聊天控制器")
@Slf4j
public class testController {
    private final OpenAiChatModel Hinachan;

    public testController(OpenAiChatModel hinachan) {
        Hinachan = hinachan;
    }

    @GetMapping(value = "/flux-ChatClient/OpenAi-momoi", produces = "text/event-stream;charset=UTF-8")
    @Operation(summary = "临时测试接口，放开网关鉴权token时使用")
    public Flux<String> WithOpenAiByMomoi() {
        ChatRequestDTO chatRequestDTO = new ChatRequestDTO();
        chatRequestDTO.setMessage("你好,请你给我200字的笑话");
        return Hinachan.stream(chatRequestDTO.getMessage());
    }
}
