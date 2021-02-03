package com.quaero.quaerosmartplatform.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 计划到料查询结果 按供应商代号 日期
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021/1/25 9:42
 */
@Data
@ApiModel
public class MaterialPlanByCardCodeVo {
    @ApiModelProperty("料号")
    private String itemCode;
    @ApiModelProperty("名称规格")
    private String itemName;
    @ApiModelProperty("未交数量总和")
    private BigDecimal unpaidQuantity;
    @ApiModelProperty("列表")
    private List<MaterialPlanListVo> list;
}
