package com.alice.shiroai.controller;


import com.alice.shiroai.domain.dto.CreateConversationDTO;
import com.alice.shiroai.domain.dto.DeleteConversationDTO;
import com.alice.shiroai.domain.dto.PageDTO;
import com.alice.shiroai.domain.dto.UpdateConversationTopicDTO;
import com.alice.shiroai.domain.po.UserConversation;
import com.alice.shiroai.service.IUserConversationService;
import com.alice.shiroai.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Anson
 * @since 2025-04-30
 */
@RestController
@Tag(name = "用户会话关系控制器")
@RequestMapping("/ai/user-conversation")
@RequiredArgsConstructor
public class UserConversationController {

    private final IUserConversationService userConversationService;

    @PostMapping("/create")
    @Operation(summary = "创建用户会话")
    public R<String> createConversation(@RequestBody CreateConversationDTO createConversationDTO) {
        return userConversationService.createConversation(createConversationDTO);
    }

    @PostMapping("/history")
    @Operation(summary = "分页获取用户历史会话记录")
    public R<Page<UserConversation>> getUserConversations(@RequestBody PageDTO pageDTO) {
        return userConversationService.getUserConversations(pageDTO);
    }

    @PostMapping("/update")
    @Operation(summary = "更新用户会话主题")
    public R<String> updateConversationTopic(@RequestBody UpdateConversationTopicDTO updateDTO) {
        return userConversationService.updateConversationTopic(updateDTO);
    }

    @PostMapping("/delete")
    @Operation(summary = "批量删除用户会话")
    public R<String> deleteConversations(@RequestBody DeleteConversationDTO deleteDTO) {
        return userConversationService.batchDeleteConversations(deleteDTO);
    }
}



















