package com.quaero.quaerosmartplatform.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 供应商表
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021-01-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("[dbo].[OPOR]")
public class OPOR implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("CardCode")
    private String CardCode;

    @TableField("CardName")
    private String CardName;

}
