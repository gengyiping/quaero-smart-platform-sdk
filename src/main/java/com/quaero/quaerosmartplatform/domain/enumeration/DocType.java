package com.quaero.quaerosmartplatform.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * Created by wuhanzhang on 2020/11/4.
 * Description: 单据类型
 */
public enum DocType {
    /**
     * 到料通知单
     */
    INCOMING_NOTICE_FORM(0),
    /**
     * 发料单
     */
    ISSUE_FORM(1),
    /**
     * 送检单
     */
    INSPECTION_FORM(2),
    /**
     * 入库单
     */
    WAREHOUSING_FORM(3),
    /**
     * 配送请求单
     */
    DELIVERY_REQUEST_FORM(4),
    /**
     * 其他
     */
    OTHER_FORM(5);

    @Getter
    @JsonValue
    private Integer intValue;

    DocType(int i) {
        intValue = i;
    }
}
