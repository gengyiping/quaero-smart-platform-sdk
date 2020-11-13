package com.quaero.quaerosmartplatform.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

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

    private String uCk;

    @TableField("U_Code")
    private String uCode;

    @TableField("U_Name")
    private String uName;


}
