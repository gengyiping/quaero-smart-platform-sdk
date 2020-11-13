package com.quaero.quaerosmartplatform.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("[dbo].[@JX_PDA005_1]")
public class UserRole implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId("Code")
    private String Code;

    @TableField("LineId")
    private Integer LineId;

    @TableField("Object")
    private String Object;

    @TableField("LogInst")
    private Integer LogInst;

    @TableField("U_WinCode")
    private String uWincode;

    @TableField("U_WinName")
    private String uWinname;


}
