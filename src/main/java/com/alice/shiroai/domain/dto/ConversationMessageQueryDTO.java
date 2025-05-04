package com.alice.shiroai.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ConversationMessageQueryDTO {
    @Schema(description = "会话ID", required = true)
    private String conversationId;
}