package com.quaero.quaerosmartplatform.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.quaero.quaerosmartplatform.domain.enumeration.ValidityIndicatorEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 用户权限表
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("[dbo].[@JX_PDA003_1]")
public class UserAuthority implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId("Code")
    private String Code;

    @TableField("LineId")
    private Integer LineId;

    @TableField("Object")
    private String Object;

    @TableField("LogInst")
    private Integer LogInst;

    @TableField("U_CK")
    private ValidityIndicatorEnum uCk;

    @TableField("U_Code")
    private String uCode;

    @TableField("U_Name")
    private String uName;


}
