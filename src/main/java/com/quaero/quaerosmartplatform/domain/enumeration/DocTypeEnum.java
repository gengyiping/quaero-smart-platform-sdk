package com.quaero.quaerosmartplatform.domain.enumeration;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 单据类型
 */
public enum DocTypeEnum implements IEnum<String> {
    DELIVERY_FORM("10", "交货"),
    RETURN_FORM("11", "退货"),
    PURCHASE_RECEIPT_FORM("20", "采购收货单"),
    PURCHASE_RETURN_FORM("21", "采购退货单"),
    RECEIPT_FORM("30", "收货单"),
    SHIP_FORM("31", "发货"),
    STOCK_TRANSFER_FORM("32", "库存转储"),
    INSPECTION_FORM("40", "送检单"),
    INSPECTION_REPORT_FORM("41", "检验报告"),
    PRODUCTION_ORDER_FORM("50", "生产订单");

    private String value;
    private String desc;

    DocTypeEnum(final String value,final String desc) {
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
