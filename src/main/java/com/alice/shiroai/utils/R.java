package com.alice.shiroai.utils;


import com.alice.shiroai.enums.ResponseCode;
import com.alice.shiroai.exception.BaseException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "统一响应包装类")
public class R<T> {
    @Schema(description = "返回给前端的：状态码")
    private int code;
    @Schema(description = "返回给前端的：说明信息")
    private String msg;
    @Schema(description = "返回给前端的：数据对象")
    private T data;

    // 全参构造器
    public R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 静态方法：成功响应
    public static <T> R<T> success(T data) {
        return new R<>(ResponseCode.SUCCESS.getValue(), ResponseCode.SUCCESS.getDescription(), data);
    }

    // 静态方法：失败响应
    public static <T> R<T> failure(String message) {
        return new R<>(ResponseCode.FAILURE.getValue(), message, null);
    }

    public static <T> R<T> failure(BaseException e) {
        return new R<>(e.getCode(), e.getMessage(), null);
    }
}
