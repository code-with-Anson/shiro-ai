package com.alice.shiroai.service.impl;

import com.alice.shiroai.domain.po.ChatMessage;
import com.alice.shiroai.mapper.ChatMessageMapper;
import com.alice.shiroai.service.IChatMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 聊天消息记录表 服务实现类
 * </p>
 *
 * @author Anson
 * @since 2025-04-23
 */
@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage> implements IChatMessageService {

}
