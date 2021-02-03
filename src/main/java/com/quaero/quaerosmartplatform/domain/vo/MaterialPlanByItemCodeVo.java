package com.quaero.quaerosmartplatform.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 计划到料查询结果 按料号
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021/1/25 9:42
 */
@Data
@ApiModel
public class MaterialPlanByItemCodeVo {
    @ApiModelProperty("供应商编号")
    private String cardCode;
    @ApiModelProperty("供应商名称")
    private String cardName;
    @ApiModelProperty("未交数量总和")
    private BigDecimal unpaidQuantity;
    @ApiModelProperty("列表")
    private List<MaterialPlanListVo> list;
}
