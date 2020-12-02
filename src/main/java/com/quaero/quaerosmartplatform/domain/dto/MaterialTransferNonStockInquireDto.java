package com.quaero.quaerosmartplatform.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 非库存转移物料查询请求参
 *
 * @author wuhanzhang
 * @since 2020/11/06
 */
@Getter
public class MaterialTransferNonStockInquireDto extends MaterialTransferStockInquireDto{

    @ApiModelProperty(value = "单据类型")
    private String doctype;
    @ApiModelProperty(value = "来源单号")
    private String baseEntry;
    @ApiModelProperty(value = "行号")
    private String baseline;

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public void setBaseEntry(String baseEntry) {
        this.baseEntry = baseEntry.replaceFirst("^0*", "");
    }

    public void setBaseline(String baseline) {
        this.baseline = baseline.replaceFirst("^0*", "");
    }
}
