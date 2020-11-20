package com.quaero.quaerosmartplatform.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 送检单信息
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("[dbo].[@CH_WMS_GOODSIN_1]")
public class ChWmsGoodsin1 implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId("DocEntry")
    private Integer DocEntry;

    @TableField("LineId")
    private Integer LineId;

    @TableField("U_ItemName")
    private String uItemname;

    @TableField("U_Quantity")
    private BigDecimal uQuantity;

}
