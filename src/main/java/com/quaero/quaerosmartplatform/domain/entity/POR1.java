package com.quaero.quaerosmartplatform.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 采购订单行表
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021-01-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("[dbo].[POR1]")
public class POR1 implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId("DocEntry")
    private Integer DocEntry;

    @TableField("LineNum")
    private Integer LineNum;

    @TableField("TargetType")
    private Integer TargetType;

    @TableField("TrgetEntry")
    private Integer TargetEntry;

    @TableField("ItemCode")
    private String ItemCode;

    @TableField("Dscription")
    private String Dscription;

    @TableField("Quantity")
    private String Quantity;

    @TableField("ShipDate")
    private Date ShipDate;

    @TableField("OpenQty")
    private String OpenQty;

}
