package com.quaero.quaerosmartplatform.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.quaero.quaerosmartplatform.domain.enumeration.IntegrityMarkEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 确认物料转移请求参
 *
 * @author wuhanzhang
 * @since 2020/11/06
 */
@Data
@ApiModel
public class MaterialTransferUpdateDto extends MaterialTransferSearchDto{

    @NotBlank(message = "原位置不能空")
    @ApiModelProperty(value = "原位置")
    private String oLocation;
    @NotBlank(message = "目标位置不能空")
    @ApiModelProperty(value = "目标位置")
    private String tLocation;
    //数量	预留，暂不不管数量，只管有和无
    @NotBlank(message = "数量不能为空")
    @ApiModelProperty(value = "数量")
    private BigDecimal qty;
}
