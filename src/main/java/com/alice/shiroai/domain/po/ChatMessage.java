package com.alice.shiroai.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 聊天消息记录表
 * </p>
 *
 * @author Anson
 * @since 2025-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chat_message")
@Schema(description = "聊天消息记录表")
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "消息主键 (雪花 ID)")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "会话 ID")
    private String conversationId;

    @Schema(description = "用户 ID")
    private String userId;

    @Schema(description = "消息角色 (用户/助手)")
    private String role;

    @Schema(description = "消息内容")
    private String content;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;


}