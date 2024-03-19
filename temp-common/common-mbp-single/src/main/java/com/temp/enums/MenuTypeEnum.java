package com.temp.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.temp.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 菜单类型枚举
 *
 * @author admin
 * @since 2022/4/23
 */

public enum MenuTypeEnum implements IBaseEnum<String> {

    TABLE("目录", "TABLE"),
    MENU("菜单", "MENU"),
    BUTTON("按钮", "BUTTON");


    @Getter
    // @JsonValue //  表示对枚举序列化时返回此字段
    private String label;

    @Getter
    @EnumValue //  Mybatis-Plus 提供注解表示插入数据库时插入该值
    private String value;

    MenuTypeEnum(String label, String value) {
        this.value = value;
        this.label = label;
    }


}
