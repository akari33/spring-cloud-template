package com.temp.common.enums;

import com.temp.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 状态枚举(通用)
 *
 * @author
 */
public enum WhetherEnum implements IBaseEnum<Integer> {
    YES("ENABLE", 0),
    NO("DISABLE", 1);

    @Getter
    private String label;

    @Getter
    private Integer value;

    WhetherEnum(String label, Integer value) {
        this.label = label;
        this.value = value;
    }

}
