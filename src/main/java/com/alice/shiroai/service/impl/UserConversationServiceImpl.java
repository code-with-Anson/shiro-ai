package com.alice.shiroai.service.impl;

import com.alice.shiroai.domain.dto.CreateConversationDTO;
import com.alice.shiroai.domain.po.UserConversation;
import com.alice.shiroai.mapper.UserConversationMapper;
import com.alice.shiroai.service.IUserConversationService;
import com.alice.shiroai.utils.R;
import com.alice.shiroai.utils.UserContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class UserConversationServiceImpl extends ServiceImpl<UserConversationMapper, UserConversation> implements IUserConversationService {

    @Override
    public R<String> createConversation(CreateConversationDTO createConversationDTO) {
        Long userId = UserContext.getUser();
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
}
