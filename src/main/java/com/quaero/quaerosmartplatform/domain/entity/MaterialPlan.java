package com.quaero.quaerosmartplatform.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 计划到料表
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021-01-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("[dbo].[@JX_PDA008]")
public class MaterialPlan implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId("DocEntry")
    private Integer DocEntry;

    @TableField("DocNum")
    private Integer DocNum;

    /*@TableField("Period")
    private Integer Period;

    @TableField("Instance")
    private Integer Instance;

    @TableField("Series")
    private Integer Series;

    @TableField("Handwrtten")
    private String Handwrtten;

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

    @TableField("Status")
    private String Status;

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

    @TableField("RequestStatus")
    private String RequestStatus;

    @TableField("Creator")
    private String Creator;

    @TableField("Remark")
    private String Remark;*/


}
