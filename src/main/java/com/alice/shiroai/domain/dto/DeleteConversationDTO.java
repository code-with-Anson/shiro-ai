package com.alice.shiroai.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "删除会话-数据传输对象")
public class DeleteConversationDTO {

    @Schema(description = "会话id-uuid")
    private String conversationId;
}