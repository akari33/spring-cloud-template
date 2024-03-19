package com.temp.common.enums;

import com.temp.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 状态枚举(通用)
 *
 * @author
 */
public enum StatusEnum implements IBaseEnum<String> {
    ENABLE("启用", "ENABLE"),
    DISABLE("禁用", "DISABLE");

    @Getter
    private String label;

    @Getter
    private String value;

    StatusEnum(String label, String value) {
        this.label = label;
        this.value = value;
    }

}
