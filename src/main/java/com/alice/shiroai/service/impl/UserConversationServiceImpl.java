package com.alice.shiroai.service.impl;

import com.alice.shiroai.domain.dto.CreateConversationDTO;
import com.alice.shiroai.domain.dto.PageDTO;
import com.alice.shiroai.domain.dto.UpdateConversationTopicDTO;
import com.alice.shiroai.domain.po.ChatMessage;
import com.alice.shiroai.domain.po.UserConversation;
import com.alice.shiroai.mapper.UserConversationMapper;
import com.alice.shiroai.service.IChatMessageService;
import com.alice.shiroai.service.IUserConversationService;
import com.alice.shiroai.utils.R;
import com.alice.shiroai.utils.UserContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Anson
 * @since 2025-04-30
 */
@Service
@RequiredArgsConstructor
public class UserConversationServiceImpl extends ServiceImpl<UserConversationMapper, UserConversation> implements IUserConversationService {

    private final IChatMessageService chatMessageService;

    @Override
    public R<String> createConversation(CreateConversationDTO createConversationDTO) {
        Long userId = null;
        if (UserContext.getUser() != null) {
            userId = UserContext.getUser();
        }


        // 创建新的会话实体
        UserConversation newUserConversation = new UserConversation();
        newUserConversation.setUserId(userId);
        newUserConversation.setConversationId(createConversationDTO.getConversationId());
        newUserConversation.setTopic(createConversationDTO.getTopic());

        LambdaQueryWrapper<UserConversation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(UserConversation::getConversationId, createConversationDTO.getConversationId())
                .eq(UserConversation::getUserId, userId);

        if (this.exists(queryWrapper)) {
            // 会话已存在，返回错误信息
            return R.failure("已存在该uuid，重新创建一下试试哦");
        } else {
            // 会话不存在，正常创建
            this.save(newUserConversation);
        }
        return R.success("成功创建会话");
    }

    @Override
    public R<Page<UserConversation>> getUserConversations(PageDTO pageDTO) {
        Long userId = UserContext.getUser();
        if (userId == null) {
            return R.failure("用户未登录");
        }

        Page<UserConversation> page = new Page<>(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        LambdaQueryWrapper<UserConversation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserConversation::getUserId, userId)
                .orderByDesc(UserConversation::getCreateTime);

        Page<UserConversation> userConversationsPage = this.page(page, queryWrapper);

        return R.success(userConversationsPage);
    }

    @Override
    public R<String> updateConversationTopic(UpdateConversationTopicDTO updateDTO) {
        Long userId = UserContext.getUser();
        if (userId == null) {
            return R.failure("用户未登录");
        }

        LambdaUpdateWrapper<UserConversation> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserConversation::getConversationId, updateDTO.getConversationId())
                .eq(UserConversation::getUserId, userId)
                .set(UserConversation::getTopic, updateDTO.getTopic());

        boolean updated = this.update(updateWrapper);
        if (!updated) {
            return R.failure("会话不存在");
        }

        return R.success("会话主题更新成功");
    }

    @Override
    public R<String> deleteConversation(String conversationId) {
        Long userId = UserContext.getUser();
        if (userId == null) {
            return R.failure("用户未登录");
        }

        // 首先确认会话存在且属于当前用户
        LambdaQueryWrapper<UserConversation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserConversation::getConversationId, conversationId)
                .eq(UserConversation::getUserId, userId);

        UserConversation conversation = this.getOne(queryWrapper);
        if (conversation == null) {
            return R.failure("会话不存在");
        }

        // 1. 先删除关联的聊天消息
        LambdaQueryWrapper<ChatMessage> messageQueryWrapper = new LambdaQueryWrapper<>();
        messageQueryWrapper.eq(ChatMessage::getConversationId, conversationId);
        chatMessageService.remove(messageQueryWrapper);

        // 2. 再删除会话记录
        boolean removed = this.remove(queryWrapper);
        if (!removed) {
            return R.failure("删除会话失败");
        }

        return R.success("会话删除成功");
    }
}
