package com.alice.shiroai.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "更新会话主题-数据传输对象")
public class UpdateConversationTopicDTO {

    @Schema(description = "会话id-uuid")
    private String conversationId;
    
    @Schema(description = "新的会话主题")
    private String topic;
}