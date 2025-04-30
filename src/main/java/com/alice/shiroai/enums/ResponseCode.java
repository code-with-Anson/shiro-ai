package com.alice.shiroai.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS(20039, "成功"),
    FAILURE(50039, "失败");

    @EnumValue
    private final int value;
    @JsonValue
    private final String description;


    ResponseCode(int value, String description) {
        this.value = value;
        this.description = description;
    }
}
