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
 * 权限界面表
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("[dbo].[@JX_PDA004]")
public class Authority implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId("Code")
    private String Code;

    @TableField("Name")
    private String Name;

    @TableField("U_Url")
    private String UUrl;
}
