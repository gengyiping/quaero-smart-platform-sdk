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
 * 生产订单表
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021-01-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("[dbo].[OWOR]")
public class OWOR implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId("DocEntry")
    private Integer DocEntry;

    @TableField("DocNum")
    private Integer DocNum;

    @TableField("Series")
    private Integer Series;

    @TableField("ItemCode")
    private String ItemCode;

    @TableField("Status")
    private String Status;

    @TableField("Type")
    private String Type;

    @TableField("PlannedQty")
    private String PlannedQty;

    @TableField("CmpltQty")
    private String CmpltQty;

    @TableField("RjctQty")
    private String RjctQty;

    @TableField("PostDate")
    private Date PostDate;

    @TableField("DueDate")
    private Date DueDate;

    @TableField("UserSign")
    private Integer UserSign;

    @TableField("Comments")
    private String Comments;

    @TableField("CloseDate")
    private Date CloseDate;

    @TableField("RlsDate")
    private Date RlsDate;

    @TableField("Project")
    private String Project;

    @TableField("ProdName")
    private String ProdName;

}
