package com.quaero.quaerosmartplatform.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 批物料转移请求参
 *
 * @author wuhanzhang
 * @since 2020/11/06
 */
@Data
public class MaterialTransferBatchUpdateDto {

    @NotBlank(message = "位置不能空")
    @ApiModelProperty(value = "位置条码")
    private String location;
    @NotBlank(message = "目的地位置不能空")
    @ApiModelProperty(value = "目的地条码")
    private String targetLocation;
}
