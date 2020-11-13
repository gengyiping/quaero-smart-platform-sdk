package com.quaero.quaerosmartplatform.controller;


import com.quaero.quaerosmartplatform.annotations.ResponseResult;
import com.quaero.quaerosmartplatform.domain.dto.MaterialTransferSearchDto;
import com.quaero.quaerosmartplatform.domain.dto.MaterialTransferUpdateDto;
import com.quaero.quaerosmartplatform.domain.entity.MaterialFlow;
import com.quaero.quaerosmartplatform.domain.entity.WarehouseLocation;
import com.quaero.quaerosmartplatform.domain.enumeration.DocTypeEnum;
import com.quaero.quaerosmartplatform.domain.enumeration.ValidityIndicatorEnum;
import com.quaero.quaerosmartplatform.exceptions.ParameterInvalidException;
import com.quaero.quaerosmartplatform.service.MaterialFlowService;
import com.quaero.quaerosmartplatform.service.OitmService;
import com.quaero.quaerosmartplatform.service.WarehouseLocationService;
import com.quaero.quaerosmartplatform.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 物料转移 7-1
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-03
 */
@RestController
@ResponseResult
@Api(tags = "物料转移相关接口")
@RequestMapping("/api/material/transfer")
public class MaterialTransferController {

    @Autowired
    private MaterialFlowService materialFlowService;
    @Autowired
    private WarehouseLocationService warehouseLocationService;
    @Autowired
    private OitmService oitmService;

    @PostMapping("/")
    @ApiOperation("查询物料接口")
    public List<MaterialTransferInfo> getMaterialTransfer(@RequestBody MaterialTransferSearchDto dto) {
        if (StringUtil.isEmpty(dto.getBaseEntry()) && StringUtil.isEmpty(dto.getDisNum())
                && StringUtil.isEmpty(dto.getDoctype()) && StringUtil.isEmpty(dto.getItemCode())) {
            throw new ParameterInvalidException("参数都为空");
        }
        List<MaterialTransferInfo> infos = new ArrayList<>();
        if (dto.getDoctype().equals(DocTypeEnum.INSPECTION_FORM.getValue())) {
            List<MaterialFlow> list = materialFlowService.lambdaQuery()
                    .like(StringUtil.isNotEmpty(dto.getItemCode()), MaterialFlow::getUItemcode, dto.getItemCode())
                    .like(StringUtil.isNotEmpty(dto.getDisNum()) && !dto.getDisNum().equals("XXXX"), MaterialFlow::getUDisnum, dto.getDisNum())
                    .like(StringUtil.isNotEmpty(dto.getDoctype()), MaterialFlow::getUDoctype, dto.getDoctype())
                    .like(StringUtil.isNotEmpty(dto.getBaseEntry()), MaterialFlow::getUBaseentry, dto.getBaseEntry())
                    .eq(MaterialFlow::getUActive, ValidityIndicatorEnum.VALID)
                    .list();
            list.forEach(l -> {
                final MaterialTransferInfo info = new MaterialTransferInfo();
                info.setItemCode(l.getUItemcode());
                info.setDisNum(l.getUDisnum());
                info.setCreator(l.getUCreator());
                info.setDocDate(l.getUDocdate());
                info.setQty(l.getUQty());
                info.setItemName(oitmService.getBaseMapper().selectById(l.getUItemcode()).getItemName());
                infos.add(info);
            });
        } else if (dto.getDoctype().equals(DocTypeEnum.STOCK_TRANSFER_FORM.getValue())){
            List<WarehouseLocation> list = warehouseLocationService.lambdaQuery()
                    .like(StringUtil.isNotEmpty(dto.getItemCode()), WarehouseLocation::getItemCode, dto.getItemCode())
                    .like(StringUtil.isNotEmpty(dto.getDisNum()) && !dto.getDisNum().equals("XXXX"), WarehouseLocation::getDisNum, dto.getDisNum())
                    .eq(WarehouseLocation::getActive, ValidityIndicatorEnum.VALID)
                    .list();
            list.forEach(l -> {
                final MaterialTransferInfo info = new MaterialTransferInfo();
                info.setItemCode(l.getItemCode());
                info.setDisNum(l.getDisNum());
                info.setCreator(null);
                info.setDocDate(null);
                info.setQty(BigDecimal.ZERO);
                info.setItemName(oitmService.getBaseMapper().selectById(l.getItemCode()).getItemName());
                infos.add(info);
            });
        }
        return infos;
    }

    @Data
    public static class MaterialTransferInfo{
        @ApiModelProperty(value = "料号")
        private String itemCode;
        @ApiModelProperty(value = "批号")
        private String disNum;
        @ApiModelProperty(value = "名称规格")
        private String itemName;
        @ApiModelProperty(value = "数量")
        private BigDecimal qty;
        @ApiModelProperty(value = "操作员")
        private String creator;
        @ApiModelProperty(value = "操作时间")
        private Date docDate;
    }

    @PatchMapping("/")
    @ApiOperation("确认转移物料接口")
    public void confirmMaterialTransfer(@RequestBody MaterialTransferUpdateDto materialTransferUpdateDto) {
        materialFlowService.confirmMaterialTransfer(materialTransferUpdateDto);
    }

}

