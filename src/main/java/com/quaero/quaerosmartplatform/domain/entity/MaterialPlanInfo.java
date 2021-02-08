package com.quaero.quaerosmartplatform.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.quaero.quaerosmartplatform.domain.enumeration.ValidityEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("[dbo].[@JX_PDA009]")
public class MaterialPlanInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "U_ID", type = IdType.AUTO)
    private Long uId;

    /**
     * 完成送检（Y/N）
     */
    @TableField("U_SJBS")
    private ValidityEnum uSJBS;

    /**
     * 收料确认（Y/N）
     */
    @TableField("U_SLBS")
    private ValidityEnum uSLBS;

    /**
     * 计划过期（Y/N）
     */
    @TableField("U_GQBS")
    private ValidityEnum uGQBS;

    /**
     * 料号 
     */
    @TableField("U_ItemCode")
    private String uItemCode;

    /**
     * 名称
     */
    @TableField("U_ItemName")
    private String uItemName;

    /**
     * 来源类型
     */
    @TableField("U_BaseType")
    private String uBaseType;

    /**
     * 来源单号
     */
    @TableField("U_BaseEntry")
    private String uBaseEntry;

    /**
     * 来源行号
     */
    @TableField("U_BaseLine")
    private String uBaseLine;

    /**
     * 计划到料数量
     */
    @TableField("U_PlannedQty")
    private BigDecimal uPlannedQty;

    /**
     * 计划到料日期
     */
    @TableField("U_DueDate")
    private Date uDueDate;

    /**
     * 信息填写人 账号
     */
    @TableField("U_UserSign")
    private String uUserSign;

    /**
     * 信息填写人名称
     */
    @TableField("U_Name")
    private String uName;

    /**
     * 填写日期  
     */
    @TableField("U_TaxDate")
    private Date uTaxDate;

    /**
     * 当时缺料数 
     */
    @TableField("U_PMCQTY")
    private BigDecimal uPmcQty;

    /**
     * pmc允许收料（Y/N）
     */
    @TableField("U_PMCBS")
    private ValidityEnum uPmcBS;

    /**
     * pmc收料要求  
     */
    @TableField("U_PMCREM")
    private String uPmcRem;

    /**
     * 计划收料人 
     */
    @TableField("U_Name2")
    private String uName2;

    /**
     * 计划收料时间
     */
    @TableField("U_DocDueDate")
    private Date uDocDueDate;

    /**
     * 收料确认登录账号 
     */
    @TableField("U_UserSign3")
    private String uUserSign3;

    /**
     * 收料人 
     */
    @TableField("U_Name3")
    private String uName3;

    /**
     * 收料确认日期  
     */
    @TableField("U_DocDate")
    private Date uDocDate;

    /**
     * 收料物理位置
     */
    @TableField("U_WLWZ")
    private String uWLWZ;


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
    @TableField("U_SJDH")
    private String uSJDH;

    /**
     * 到料方式
     */
    @TableField("U_DLFS")
    private String uDLFS;

    /**
     * 物流信息
     */
    @TableField("U_WLXX")
    private String uWLXX;

    /**
     * 预交日期
     */
    @TableField("U_ShipDate")
    private Date UShipDate;

    /**
     * 供应商代号
     */
    @TableField("U_CardCode")
    private String UCardCode;

    /**
     * 供应商名称
     */
    @TableField("U_CardName")
    private String UCardName;
}
