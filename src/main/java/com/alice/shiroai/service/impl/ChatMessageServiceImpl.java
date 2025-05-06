package com.alice.shiroai.service.impl;

import com.alice.shiroai.domain.dto.ChatRequestDTO;
import com.alice.shiroai.domain.dto.ConversationMessageQueryDTO;
import com.alice.shiroai.domain.po.ChatMessage;
import com.alice.shiroai.domain.po.UserConversation;
import com.alice.shiroai.exception.ConversationDontExistException;
import com.alice.shiroai.mapper.ChatMessageMapper;
import com.alice.shiroai.mapper.UserConversationMapper;
import com.alice.shiroai.service.IChatMessageService;
import com.alice.shiroai.utils.R;
import com.alice.shiroai.utils.UserContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage> implements IChatMessageService {
    private final ChatClient Momoi;
    private final UserConversationMapper userConversationMapper; // 直接注入Mapper

    @Value("classpath:/prompts/alice-system-message.st")
    private Resource systemResource;

    public ChatMessageServiceImpl(ChatClient Momoi, UserConversationMapper userConversationMapper) {
        this.Momoi = Momoi;

        this.userConversationMapper = userConversationMapper;
    }

    /**
     * 使用Momoi进行聊天（直接使用链式编程传入参数）
     *
     * @param chatRequestDTO
     * @return
     */
    @Override
    public Flux<String> chatWithOpenAiByMomoi(ChatRequestDTO chatRequestDTO) {
        Long userId = UserContext.getUser();
        String userIdStr = userId != null ? userId.toString() : null;
        String conversationId = chatRequestDTO.getConversationId();
        String userMessage = chatRequestDTO.getMessage();

        // 首先确认会话存在且属于当前用户
        boolean isExists = isExists(conversationId, userIdStr);
        if (!isExists) {
            throw new ConversationDontExistException("会话不存在或不属于当前用户");
        }

        // 1. 保存用户消息到数据库
        ChatMessage userChatMessage = new ChatMessage()
                .setConversationId(conversationId)
                .setUserId(userIdStr)
                .setRole("user")
                .setContent(userMessage);
        this.save(userChatMessage);


        // 2. 获取该会话的历史消息
        List<Message> messages = new ArrayList<>();
        if (conversationId != null && !conversationId.startsWith("temp-")) {
            // 查询历史消息
            LambdaQueryWrapper<ChatMessage> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ChatMessage::getConversationId, conversationId)
                    .orderByAsc(ChatMessage::getCreatedTime);

            List<ChatMessage> historyMessages = this.list(queryWrapper);

            if (!CollectionUtils.isEmpty(historyMessages)) {
                log.info("已加载{}条历史消息", historyMessages.size());

                // 将历史消息转换为Spring AI的Message格式
                for (ChatMessage message : historyMessages) {
                    if ("user".equals(message.getRole())) {
                        messages.add(new UserMessage(message.getContent()));
                    } else if ("assistant".equals(message.getRole())) {
                        messages.add(new AssistantMessage(message.getContent()));
                    }
                }
            }

        }

        // 4. 添加当前用户消息
        messages.add(new UserMessage(userMessage));

        // 5. 创建包含历史消息的Prompt
        Prompt prompt = new Prompt(messages);

        // 6. 发送请求到AI并获取流式响应
        StringBuilder fullResponse = new StringBuilder();

        Flux<String> responseFlux = Momoi.prompt(prompt).stream().content();

        return responseFlux.doOnNext(chunk -> {
            fullResponse.append(chunk);
        }).doOnComplete(() -> {
            // 7. 保存助手回复到数据库
            ChatMessage assistantChatMessage = new ChatMessage()
                    .setConversationId(conversationId)
                    .setUserId(userIdStr)
                    .setRole("assistant")
                    .setContent(fullResponse.toString());
            this.save(assistantChatMessage);
            log.info("AI回复已保存到数据库，会话ID: {}", conversationId);
        });

    }

    @Override
    public R<List<ChatMessage>> getMessagesByConversationId(ConversationMessageQueryDTO queryDTO) {
        Long userId = UserContext.getUser();
        String userIdStr = userId != null ? userId.toString() : null;
        String conversationId = queryDTO.getConversationId();

        // 验证会话是否存在且属于当前用户
        boolean isExists = isExists(conversationId, userIdStr);
        if (!isExists) {
            throw new ConversationDontExistException("会话不存在或不属于当前用户");
        }

        // 查询该会话的所有消息
        LambdaQueryWrapper<ChatMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatMessage::getConversationId, conversationId)
                .orderByAsc(ChatMessage::getCreatedTime); // 按时间顺序排序

        List<ChatMessage> messages = this.list(queryWrapper);

        log.info("查询到会话ID: {}的消息{}条", conversationId, messages.size());
        return R.success(messages);
    }

    private boolean isExists(String conversationId, String userId) {
        LambdaQueryWrapper<UserConversation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserConversation::getConversationId, conversationId)
                .eq(UserConversation::getUserId, Long.valueOf(userId));

        return userConversationMapper.exists(queryWrapper);
    }
}
