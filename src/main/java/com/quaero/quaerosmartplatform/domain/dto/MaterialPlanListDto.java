package com.quaero.quaerosmartplatform.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 按料号计划到料查询列表请求参
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021/1/19 8:33
 */
@Data
@ApiModel
public class MaterialPlanListDto {
    @ApiModelProperty(value = "供应商代号")
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
}
