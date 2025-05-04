package com.alice.shiroai.service;

import com.alice.shiroai.domain.dto.ChatRequestDTO;
import com.alice.shiroai.domain.dto.ConversationMessageQueryDTO;
import com.alice.shiroai.domain.po.ChatMessage;
import com.alice.shiroai.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * <p>
 * 聊天消息记录表 服务类
 * </p>
 *
 * @author Anson
 * @since 2025-04-23
 */

public interface IChatMessageService extends IService<ChatMessage> {
    Flux<String> chatWithOpenAiByMomoi(ChatRequestDTO chatRequestDTO);

    // 添加新方法
    R<List<ChatMessage>> getMessagesByConversationId(ConversationMessageQueryDTO queryDTO);
}
