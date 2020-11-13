package com.quaero.quaerosmartplatform.domain.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 物料表
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("[dbo].[OITM]")
public class Oitm implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId("ItemCode")
    private String ItemCode;

    @TableField("ItemName")
    private String ItemName;

}
