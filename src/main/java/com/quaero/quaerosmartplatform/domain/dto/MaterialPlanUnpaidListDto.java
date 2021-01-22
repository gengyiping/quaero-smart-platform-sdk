package com.quaero.quaerosmartplatform.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 按料号计划到料未交查询列表请求参
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021/1/19 8:33
 */
@Data
@ApiModel
public class MaterialPlanUnpaidListDto {
    @ApiModelProperty(value = "供应商代号")
    @NotBlank(message = "供应商代号不能空")
    private String careCode;
    @ApiModelProperty(value = "料号")
    private String itemCode;
    @ApiModelProperty(value = "业务员")
    private String salesmanName;
    @ApiModelProperty(value = "到料日期之后")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date arrivalDateAfter;
    @ApiModelProperty(value = "到料日期之前")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date arrivalDateBefore;
    @ApiModelProperty(value = "订单类型 采购订单未交0 生产订单未交1")
    @NotNull(message = "订单未交类型不能空")
    private Integer orderType;
}
