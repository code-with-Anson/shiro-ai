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
 *
 * </p>
 *
 * @author Anson
 * @since 2025-04-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_conversation")
@Schema(description = "UserConversation对象")
public class UserConversation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Schema(description = "用户id (雪花 ID)")
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;
    @Schema(description = "对话id")
    private String conversationId;
    @Schema(description = "会话主题")
    private String topic;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "修改时间")
    private LocalDateTime updateTime;
}
