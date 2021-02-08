package com.quaero.quaerosmartplatform.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 无计划到料未交查询结果请求参
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021/1/19 8:33
 */
@Data
@ApiModel
public class MaterialUnPlanListDto {
    @ApiModelProperty(value = "供应商代号")
    private String cardCode;
    @ApiModelProperty(value = "料号")
    @NotBlank(message = "料号不能空")
    private String itemCode;
    @ApiModelProperty(value = "业务员")
    private String salesmanName;
}
