package com.quaero.quaerosmartplatform.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 单据类型 枚举表
 * 10	交货
 * 11	退货
 * 20	采购收货单
 * 21	采购退货
 * 30	收货
 * 31	发货
 * 32	库存转储
 * 40	送检单
 * 41	检验报告
 * 50	生产订单
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("[dbo].[@JX_PDA002]")
public class DocType implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId("U_Code")
    private String uCode;

    @TableField("U_Name")
    private String uName;


}
