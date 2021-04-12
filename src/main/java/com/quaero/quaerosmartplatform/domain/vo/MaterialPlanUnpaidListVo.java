package com.quaero.quaerosmartplatform.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 按料号计划到料未交查询列表返回参
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021/1/19 8:33
 */
@Data
@ApiModel
public class MaterialPlanUnpaidListVo {
    @ApiModelProperty("来源单号")
    private Integer docEntry;
    @ApiModelProperty("来源行号")
    private Integer lineNum;
    @ApiModelProperty("料号")
    private String itemCode;
    @ApiModelProperty("名称规格")
    private String dscription;
    @ApiModelProperty("未交数量总和")
    private BigDecimal unpaidQuantity;
    @ApiModelProperty("交货日期 要求到料日期")
    private Date shipDate;
    @ApiModelProperty("业务员")
    private String slpName;
    @ApiModelProperty("供应商编号")
    private String cardCode;
    @ApiModelProperty("供应商名称")
    private String cardName;
    @ApiModelProperty("来源类型")
    private String objType;
    @ApiModelProperty("PMC指定交货方式")
    private String pmcZD;
    @ApiModelProperty("最早缺料日期")
    private String zzql;
    @ApiModelProperty("建议交付数量")
    private BigDecimal jyjfQTY;
    @ApiModelProperty("计划到料数")
    private BigDecimal plannedQty;
    @ApiModelProperty("计划到料日期")
    private Date dueDate;
    @ApiModelProperty("到料方式")
    private String dlfs;
    @ApiModelProperty("物流信息")
    private String wlxx;
}
