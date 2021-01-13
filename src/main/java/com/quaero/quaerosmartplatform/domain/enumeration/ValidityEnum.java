package com.quaero.quaerosmartplatform.domain.enumeration;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 有效性枚举
 * Y:有效；N：无效
 */
public enum ValidityEnum implements IEnum<String> {

    /**
     * 有效
     */
    VALID("Y","有效"),
    /**
     * 无效
     */
    INVALID("N","无效"),

    NO("NO","无效");

    private String value;
    private String desc;

    ValidityEnum(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @JsonValue
    public String getDesc(){
        return this.desc;
    }
}
