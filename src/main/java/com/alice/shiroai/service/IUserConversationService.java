package com.alice.shiroai.service;

import com.alice.shiroai.domain.dto.CreateConversationDTO;
import com.alice.shiroai.domain.dto.PageDTO;
import com.alice.shiroai.domain.dto.UpdateConversationTopicDTO;
import com.alice.shiroai.domain.po.UserConversation;
import com.alice.shiroai.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Anson
 * @since 2025-04-30
 */
public interface IUserConversationService extends IService<UserConversation> {
    R<String> createConversation(CreateConversationDTO createConversationDTO);

    R<Page<UserConversation>> getUserConversations(PageDTO pageDTO);

    R<String> updateConversationTopic(UpdateConversationTopicDTO updateDTO);

    R<String> deleteConversation(String conversationId);

}
