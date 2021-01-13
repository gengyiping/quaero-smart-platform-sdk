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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("[dbo].[@JX_PDA007]")
public class WarehouseLocation implements Serializable {

    private static final long serialVersionUID=1L;
    //id
    @TableId(value = "U_ID", type = IdType.AUTO)
    private Long uId;
    //料号
    @TableField("U_ItemCode")
    private String ItemCode;
    //仓库
    @TableField("U_WhsCode")
    private String WhsCode;
    //批号
    @TableField("U_DisNum")
    private String DisNum;
    //有效性标识	Y:有效  N：无效 N0：无效（上了PDA可能不用N0状态）
    @TableField("U_Active")
    private ValidityEnum Active;
    //固定位置
    @TableField("U_GDWZ")
    private String uGdwz;
    //移动位置
    @TableField("U_YDWZ")
    private String uYdwz;
    //完整性标识	0:清空  1:部分，该位置只有部分    2:完整，只有当前位置存放
    @TableField("U_WZBS")
    private IntegrityMarkEnum uWzbs;
    //数量	预留，暂不不管数量，只管有和无
    @TableField("U_QTY")
    private BigDecimal uQty;

    @TableField("U_Creator")
    private String uCreator;

    @TableField("U_DocDate")
    private Date uDocdate;

}
