package com.quaero.quaerosmartplatform.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.quaero.quaerosmartplatform.domain.enumeration.ValidityEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 计划到料行 内容表
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021-01-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("[dbo].[@JX_PDA008_1]")
public class MaterialPlanInfo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 计划到料单号
     */
    @TableId("DocEntry")
    private Integer DocEntry;

    @TableField("LineId")
    private Integer LineId;

    /*@TableField("VisOrder")
    private Integer VisOrder;

    @TableField("Object")
    private String Object;

    @TableField("LogInst")
    private Integer LogInst;*/

    /**
     * 完成送检（Y/N）
     */
    @TableField("U_Sjbs")
    private ValidityEnum uSjbs;

    /**
     * 收料确认（Y/N）  
     */
    @TableField("U_Slbs")
    private ValidityEnum uSlbs;

    /**
     * 计划过期（Y/N）
     */
    @TableField("U_Gqbs")
    private ValidityEnum uGqbs;

    /**
     * 料号 
     */
    @TableField("U_ItemCode")
    private String uItemcode;

    /**
     * 名称
     */
    @TableField("U_ItemName")
    private String uItemname;

    /**
     * 来源类型
     */
    @TableField("U_BaseType")
    private String uBasetype;

    /**
     * 来源单号
     */
    @TableField("U_BaseEntry")
    private String uBaseentry;

    /**
     * 来源行号
     */
    @TableField("U_BaseLine")
    private String uBaseline;

    /**
     * 计划到料数量
     */
    @TableField("U_PlannedQty")
    private String uPlannedqty;

    /**
     * 计划到料日期
     */
    @TableField("U_DueDate")
    private Date uDuedate;

    /**
     * 信息填写人 账号
     */
    @TableField("U_UserSign")
    private String uUsersign;

    /**
     * 信息填写人名称
     */
    @TableField("U_Name")
    private String uName;

    /**
     * 填写日期  
     */
    @TableField("U_Taxdate")
    private Date uTaxdate;

    /**
     * 当时缺料数 
     */
    @TableField("U_Pmcqty")
    private String uPmcqty;

    /**
     * pmc允许收料（Y/N）
     */
    @TableField("U_Pmcbs")
    private ValidityEnum uPmcbs;

    /**
     * pmc收料要求  
     */
    @TableField("U_Pmcrem")
    private String uPmcrem;

    /**
     * 计划收料人 
     */
    @TableField("U_Name2")
    private String uName2;

    /**
     * 计划收料时间
     */
    @TableField("U_DocDueDate")
    private Date uDocduedate;

    /**
     * 收料确认登录账号 
     */
    @TableField("U_UserSign3")
    private String uUsersign3;

    /**
     * 收料人 
     */
    @TableField("U_Name3")
    private String uName3;

    /**
     * 收料确认日期  
     */
    @TableField("U_DocDate")
    private Date uDocdate;

    /**
     * 收料物理位置
     */
    @TableField("U_Wlwz")
    private String uWlwz;

    /**
     * 确认收料方式  
     */
    @TableField("U_Slfs")
    private String uSlfs;

    /**
     * 1：点数信息 
     */
    @TableField("U_Ds")
    private String uDs;

    /**
     * 2：称重信息   
     */
    @TableField("U_Cz")
    private String uCz;

    /**
     * 3：外包信息  
     */
    @TableField("U_Wb")
    private String uWb;

    /**
     * 照片路径
     */
    @TableField("U_Lj")
    private String uLj;

    /**
     * 张数 
     */
    @TableField("U_Pic")
    private String uPic;

    /**
     * 送检单号
     */
    @TableField("U_Sjdh")
    private String uSjdh;


}
