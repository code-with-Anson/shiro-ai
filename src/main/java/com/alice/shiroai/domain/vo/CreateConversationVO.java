package com.alice.shiroai.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "创建对话-视图对象")
public class CreateConversationVO {
    
    @Schema(description = "会话id-uuid")
    private String conversationId;

    @Schema(description = "会话主题")
    private String topic;
}

