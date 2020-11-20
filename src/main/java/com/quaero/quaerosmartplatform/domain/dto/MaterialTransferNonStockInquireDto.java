package com.quaero.quaerosmartplatform.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 非库存转移物料查询请求参
 *
 * @author wuhanzhang
 * @since 2020/11/06
 */
@Data
public class MaterialTransferNonStockInquireDto extends MaterialTransferStockInquireDto{

    @ApiModelProperty(value = "单据类型")
    private String doctype;
    @ApiModelProperty(value = "来源单号")
    private String baseEntry;
    @ApiModelProperty(value = "行号")
    private String baseline;
}
