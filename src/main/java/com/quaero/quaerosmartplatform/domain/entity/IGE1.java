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
 * 发货单信息
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("[dbo].[IGE1]")
public class IGE1 implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId("DocEntry")
    private Integer DocEntry;

    @TableField("LineNum")
    private Integer LineNum;

    @TableField("Dscription")
    private String Dscription;

    @TableField("Quantity")
    private BigDecimal Quantity;

}
