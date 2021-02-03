package com.quaero.quaerosmartplatform.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 计划到料查询列表
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021/1/25 8:57
 */
@Data
@ApiModel
public class MaterialPlanByDateListVo {
    @ApiModelProperty("供应商编号")
    private String cardCode;
    @ApiModelProperty("供应商名称")
    private String cardName;
    @ApiModelProperty("料号")
    private String itemCode;
    @ApiModelProperty("名称规格")
    private String itemName;
    @ApiModelProperty("计划到料数")
    private BigDecimal plannedQty;
    @ApiModelProperty("计划到料日期")
    private Date dueDate;
    @ApiModelProperty("交货日期 缺料日期")
    private Date shipDate;
    @ApiModelProperty("缺料数")
    private BigDecimal PmcQty;
}
