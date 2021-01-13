package com.quaero.quaerosmartplatform.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.quaero.quaerosmartplatform.domain.enumeration.IntegrityMarkEnum;
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
 * 物料流转物理位置数据表
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("[dbo].[@JX_PDA001]")
public class MaterialFlow implements Serializable {

    private static final long serialVersionUID=1L;
    @TableId(value = "U_ID", type = IdType.AUTO)
    private Long uId;
    //物料条码	条码组成（料号/批号）
    @TableField("U_BarCode")
    private String uBarcode;
    //料号
    @TableField("U_ItemCode")
    private String uItemcode;
    //批号
    @TableField("U_DisNum")
    private String uDisnum;
    //行号
    @TableField("U_BaseLine")
    private String uBaseline;
    //单据类型	jxpda002 entity
    @TableField("U_DocType")
    private String uDoctype;
    //来源单号
    @TableField("U_BaseEntry")
    private String uBaseentry;
    //固定位置编码	唯一可识别
    @TableField("U_GDWZ")
    private String uGdwz;
    //移动位置编码	唯一可识别
    @TableField("U_YDWZ")
    private String uYdwz;
    //完整性标识	0:清空  1:部分，该位置只有部分    2:完整，只有当前位置存放
    @TableField("U_WZBS")
    private IntegrityMarkEnum uWzbs;
    //供应商代号
    @TableField("U_CardCode")
    private String uCardcode;
    //数量	预留，暂不不管数量，只管有和无
    @TableField("U_QTY")
    private BigDecimal uQty;
    //操作员
    @TableField("U_Creator")
    private String uCreator;
    //日期时间
    @TableField("U_DocDate")
    private Date uDocdate;
    //有效性标识	Y:有效；N：无效
    @TableField("U_Active")
    private ValidityEnum uActive;


}
