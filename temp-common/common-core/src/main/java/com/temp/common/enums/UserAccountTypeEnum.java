package com.temp.common.enums;

import com.temp.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 描述：用户账户类型枚举
 *
 * @author
 */
public enum UserAccountTypeEnum implements IBaseEnum<String> {
    SYSTEM("系统用户", "system"),
    TYPE1("类型1", "type1"),
    TYPE2("类型2", "type2");

    @Getter
    private String label;

    @Getter
    private String value;

    UserAccountTypeEnum(String label, String value) {
        this.label = label;
        this.value = value;
    }
}
