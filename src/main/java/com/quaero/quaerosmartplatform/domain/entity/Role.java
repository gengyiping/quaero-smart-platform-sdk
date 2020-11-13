package com.quaero.quaerosmartplatform.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("[dbo].[@JX_PDA005]")
public class Role implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId("Code")
    private String Code;

    @TableField("Name")
    private String Name;

    @TableField("DocEntry")
    private Integer DocEntry;

    @TableField("Canceled")
    private String Canceled;

    @TableField("Object")
    private String Object;

    @TableField("LogInst")
    private Integer LogInst;

    @TableField("UserSign")
    private Integer UserSign;

    @TableField("Transfered")
    private String Transfered;

    @TableField("CreateDate")
    private Date CreateDate;

    @TableField("CreateTime")
    private Integer CreateTime;

    @TableField("UpdateDate")
    private Date UpdateDate;

    @TableField("UpdateTime")
    private Integer UpdateTime;

    @TableField("DataSource")
    private String DataSource;


}
