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
public class MaterialPlanListVo {
    private long uId;
    @ApiModelProperty("来源单号")
    private Integer baseEntry;
    @ApiModelProperty("来源行号")
    private Integer baseLine;
    @ApiModelProperty("计划到料数")
    private BigDecimal plannedQty;
    @ApiModelProperty("计划到料日期")
    private Date dueDate;
    @ApiModelProperty("未交数量")
    private BigDecimal unpaidQuantity;
    @ApiModelProperty("预交日期")
    private Date shipDate;
    @ApiModelProperty("名称规格")
    private String itemName;
    @ApiModelProperty("料号")
    private String itemCode;
    @ApiModelProperty("供应商编号")
    private String cardCode;
    @ApiModelProperty("供应商名称")
    private String cardName;
    @ApiModelProperty("物理位置")
    private String uwlwz;
    @ApiModelProperty("点数信息")
    private String uds;
    @ApiModelProperty("称重信息")
    private String ucz;
    @ApiModelProperty("外包信息")
    private String uwb;
    @ApiModelProperty("图片路径")
    private String ulj;
    @ApiModelProperty("图片数量")
    private String uPic;
}
