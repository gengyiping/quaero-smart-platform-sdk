package com.quaero.quaerosmartplatform.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 无计划到料查询列表返回参
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021/1/19 8:33
 */
@Data
@ApiModel
public class MaterialUnPlanListVo {
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
