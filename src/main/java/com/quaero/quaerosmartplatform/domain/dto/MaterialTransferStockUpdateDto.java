package com.quaero.quaerosmartplatform.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 确认库存物料转移请求参
 *
 * @author wuhanzhang
 * @since 2020/11/06
 */
@Data
@ApiModel
public class MaterialTransferStockUpdateDto extends MaterialTransferStockInquireDto {

    @NotBlank(message = "id不能空")
    @ApiModelProperty(value = "id 多个id用逗号拼接")
    private String uIds;
    @NotBlank(message = "原位置不能空")
    @ApiModelProperty(value = "原位置")
    private String oLocation;
    @NotBlank(message = "目标位置不能空")
    @ApiModelProperty(value = "目标位置")
    private String tLocation;
    @NotBlank(message = "是否全部转移不能空")
    @ApiModelProperty(value = "是否全部转移 全部:true 部分:false")
    private boolean wzbs;
    //数量	预留，暂不不管数量，只管有和无
    @NotBlank(message = "数量不能为空")
    @ApiModelProperty(value = "数量")
    private BigDecimal qty;
}
