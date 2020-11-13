package com.quaero.quaerosmartplatform.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

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
@TableName("[dbo].[@JX_KWSJ]")
public class WarehouseLocation implements Serializable {

    private static final long serialVersionUID=1L;
    //料号
    @TableField("ItemCode")
    private String ItemCode;
    //仓库
    @TableField("WhsCode")
    private String WhsCode;
    //库位
    @TableField("KW")
    private String kw;
    //批号
    @TableField("DisNum")
    private String DisNum;
    //有效性标识	Y:有效  N：无效 N0：无效（上了PDA可能不用N0状态）
    @TableField("Active")
    private String Active;

    @TableField("NewKW")
    private String NewKW;


}
