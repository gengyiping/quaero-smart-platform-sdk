package com.quaero.quaerosmartplatform.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 计划到料查询结果
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
public class MaterialPlanInfoVo {
    @ApiModelProperty("供应商数量")
    private int cardCount;
    @ApiModelProperty("供应商编号")
    private String cardCode;
    @ApiModelProperty("供应商名称")
    private String cardName;
    @ApiModelProperty(value = "到料日期之后")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date arrivalDateAfter;
    @ApiModelProperty(value = "到料日期之前")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date arrivalDateBefore;
    @ApiModelProperty("按日期结果")
    private List<MaterialPlanByDateVo> dateVos;

    @ApiModelProperty("名称规格")
    private String itemName;
    @ApiModelProperty("料号")
    private String itemCode;
    @ApiModelProperty("按料号结果")
    private List<MaterialPlanByItemCodeVo> itemCodeVos;

    @ApiModelProperty("按供应商代号+日期 结果")
    private List<MaterialPlanByCardCodeVo> cardCodeVos;

    @ApiModelProperty("未交数量总和")
    private BigDecimal unpaidQuantity;
    @ApiModelProperty("按供应商代号+日期+料号结果")
    private List<MaterialPlanListVo> listVos;

    @ApiModelProperty("在途未交标志")
    private boolean flag;
}
