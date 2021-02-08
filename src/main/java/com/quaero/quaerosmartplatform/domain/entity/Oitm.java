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

    //缺少昂贵物料
    /**
     * 是否研发物料 Y N
     */
    @TableField("U_SFYFWL")
    private String SFYFWL;

    /**
     * 是否免检物料 Y N
     */
    @TableField("U_MJWL")
    private String MJWL;

    /**
     * 是否物料等级
     */
    @TableField("U_WLDJ")
    private String WLDJ;

}
