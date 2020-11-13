package com.quaero.quaerosmartplatform.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 确认物料查询请求参
 *
 * @author wuhanzhang
 * @since 2020/11/06
 */
@Data
public class MaterialTransferSearchDto {

    @ApiModelProperty(value = "料号")
    private String itemCode;
    @ApiModelProperty(value = "批号")
    private String disNum;
    @ApiModelProperty(value = "单据类型")
    private String doctype;
    @ApiModelProperty(value = "来源单号")
    private String baseEntry;
}
