package com.quaero.quaerosmartplatform.domain.enumeration;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 完整性标识
 * 0:清空  1:部分，该位置只有部分    2:完整，只有当前位置存放
 */
public enum IntegrityMarkEnum implements IEnum<String> {

    /**
     * 清空
     */
    NONE("0","清空"),
    /**
     * 部分
     */
    SECTION("1","部分"),
    /**
     * 完整
     */
    ALL("2","完整");

    private String value;
    private String desc;

    IntegrityMarkEnum(final String value,final String desc) {
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
