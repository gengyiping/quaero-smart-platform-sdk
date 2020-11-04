package com.quaero.quaerosmartplatform.domain.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.quaero.quaerosmartplatform.domain.enumeration.DocType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 仓库库位数据表
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("@JX_PDA001")
public class JxPda001 implements Serializable {

    private static final long serialVersionUID=1L;
    //物料条码	条码组成（料号/批号）
    @TableField("U_BarCode")
    private String uBarcode;
    //料号
    @TableField("U_ItemCode")
    private String uItemcode;
    //批号
    @TableField("U_DisNum")
    private String uDisnum;
    //固定位置编码	唯一可识别
    @TableField("U_FDWZ")
    private String uGdwz;
    //移动位置编码	唯一可识别
    @TableField("U_YDWZ")
    private String uYdwz;
    //完整性标识	0:清空  1:部分，该位置只有部分    2:完整，只有当前位置存放
    @TableField("U_WZBS")
    private String uWzbs;
    //单据类型	到料通知单/发料单/送检单/入库单/配送请求单/其他
    @TableField("U_DocType")
    private DocType uDoctype;
    //来源单号
    @TableField("U_BaseEntry")
    private String uBaseentry;
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
    private String uActive;


}
