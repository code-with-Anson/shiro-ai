package com.alice.shiroai.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "分页查询DTO")
public class PageDTO {
    @Schema(description = "当前页", example = "1")
    private Long currentPage = 1L; // 默认当前页为1
    @Schema(description = "分页大小", example = "10")
    private Long pageSize = 10L;   // 默认分页大小为5
}
