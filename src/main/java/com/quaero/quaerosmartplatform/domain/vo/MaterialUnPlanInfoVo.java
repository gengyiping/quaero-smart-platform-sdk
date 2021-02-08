package com.quaero.quaerosmartplatform.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 无计划到料查询结果
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021/1/25 8:45
 */
@Data
@ApiModel
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialUnPlanInfoVo {
    @ApiModelProperty("名称规格")
    private String itemName;
    @ApiModelProperty("料号")
    private String itemCode;
    @ApiModelProperty("按料号结果")
    private List<MaterialPlanByItemCodeVo> itemCodeVos;

    @ApiModelProperty("供应商编号")
    private String cardCode;
    @ApiModelProperty("供应商名称")
    private String cardName;
    @ApiModelProperty("未交数量总和")
    private BigDecimal unpaidQuantity;
    @ApiModelProperty("按供应商代号+料号结果")
    private List<MaterialUnPlanListVo> listVos;

}
