package com.alice.shiroai.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "聊天请求数据传输对象")
public class ChatRequestDTO {
    @Schema(description = "用户输入的消息内容", example = "讲个笑话")
    private String message;

    @Schema(description = "会话 ID", example = "1234567890")
    private String conversationId;
    
}
