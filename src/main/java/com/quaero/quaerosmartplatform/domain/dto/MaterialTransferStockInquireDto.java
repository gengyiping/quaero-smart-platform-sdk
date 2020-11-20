package com.quaero.quaerosmartplatform.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 库存转移物料查询请求参
 *
 * @author wuhanzhang
 * @since 2020/11/06
 */
@Data
public class MaterialTransferStockInquireDto {

    @ApiModelProperty(value = "料号")
    private String itemCode;
    @ApiModelProperty(value = "批号")
    private String disNum;
}
