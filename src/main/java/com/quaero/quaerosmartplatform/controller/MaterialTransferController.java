package com.quaero.quaerosmartplatform.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quaero.quaerosmartplatform.annotations.ResponseResult;
import com.quaero.quaerosmartplatform.domain.dto.*;
import com.quaero.quaerosmartplatform.domain.entity.ChWmsGoodsin1;
import com.quaero.quaerosmartplatform.domain.entity.IGN1;
import com.quaero.quaerosmartplatform.domain.entity.MaterialFlow;
import com.quaero.quaerosmartplatform.domain.entity.WarehouseLocation;
import com.quaero.quaerosmartplatform.domain.enumeration.DocTypeEnum;
import com.quaero.quaerosmartplatform.domain.enumeration.ResultCode;
import com.quaero.quaerosmartplatform.domain.enumeration.ValidityIndicatorEnum;
import com.quaero.quaerosmartplatform.exceptions.BusinessException;
import com.quaero.quaerosmartplatform.service.*;
import com.quaero.quaerosmartplatform.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@PreAuthorize("hasAuthority('AAA')")
@RequestMapping("/api/materialTransfer")
public class MaterialTransferController {

    @Autowired
    private MaterialFlowService materialFlowService;
    @Autowired
    private WarehouseLocationService warehouseLocationService;
    @Autowired
    private OitmService oitmService;
    @Autowired
    private IGN1Service ign1Service;
    @Autowired
    private ChWmsGoodsin1Service chWmsGoodsin1Service;

    /////////////转移相关////////////

    @PostMapping("/stockInquire")
    @ApiOperation("库存转移查询物料接口")
    public List<MaterialTransferInfo> stockInquire(@RequestBody MaterialTransferStockInquireDto dto) {
        if (StringUtil.isEmpty(dto.getDisNum()) && StringUtil.isEmpty(dto.getItemCode())) {
            throw new BusinessException(ResultCode.PARAM_IS_BLANK);
        }
        List<MaterialTransferInfo> infos = new ArrayList<>();
        List<WarehouseLocation> list = warehouseLocationService.lambdaQuery()
                .like(StringUtil.isNotEmpty(dto.getItemCode()), WarehouseLocation::getItemCode, dto.getItemCode())
                .like(StringUtil.isNotEmpty(dto.getDisNum()) && !dto.getDisNum().equals("****"), WarehouseLocation::getDisNum, dto.getDisNum())
                .eq(WarehouseLocation::getActive, ValidityIndicatorEnum.VALID)
                .list();
        if (list.size() <= 0) {
            throw new BusinessException(ResultCode.RESULT_DATA_NONE);
        }
        list.forEach(l -> {
            final MaterialTransferInfo info = new MaterialTransferInfo();
            info.setUId(l.getUId());
            info.setItemCode(l.getItemCode());
            info.setDisNum(l.getDisNum());
            info.setQty(l.getUQty());
            info.setCreator(l.getUCreator());
            info.setDocDate(l.getUDocdate());
            info.setOl(StringUtil.isEmpty(l.getUYdwz()) ? l.getUGdwz() : l.getUYdwz());
            info.setItemName(oitmService.getById(l.getItemCode()).getItemName());
            infos.add(info);
        });
        return infos;
    }

    @PostMapping("/nonStockInquire")
    @ApiOperation("非库存转移查询物料接口")
    public List<MaterialTransferInfo> nonStockInquire(@RequestBody MaterialTransferNonStockInquireDto dto) {
        if (StringUtil.isEmpty(dto.getBaseEntry()) && StringUtil.isEmpty(dto.getDisNum())
                && StringUtil.isEmpty(dto.getDoctype()) && StringUtil.isEmpty(dto.getItemCode())
                && StringUtil.isEmpty(dto.getBaseline())) {
            throw new BusinessException(ResultCode.PARAM_IS_BLANK);
        }
        List<MaterialTransferInfo> infos = new ArrayList<>();
        List<MaterialFlow> list = materialFlowService.lambdaQuery()
                .like(StringUtil.isNotEmpty(dto.getItemCode()), MaterialFlow::getUItemcode, dto.getItemCode())
                .like(StringUtil.isNotEmpty(dto.getDisNum()) && !dto.getDisNum().equals("XXXX"), MaterialFlow::getUDisnum, dto.getDisNum())
                .like(StringUtil.isNotEmpty(dto.getDoctype()), MaterialFlow::getUDoctype, dto.getDoctype())
                .like(StringUtil.isNotEmpty(dto.getBaseEntry()), MaterialFlow::getUBaseentry, dto.getBaseEntry())
                .like(StringUtil.isNotEmpty(dto.getBaseline()), MaterialFlow::getUBaseline, dto.getBaseline())
                .eq(MaterialFlow::getUActive, ValidityIndicatorEnum.VALID)
                .list();
        if (list.size() <= 0) {
            throw new BusinessException(ResultCode.RESULT_DATA_NONE);
        }
        list.forEach(l -> {
            final MaterialTransferInfo info = new MaterialTransferInfo();
            info.setUId(l.getUId());
            info.setItemCode(l.getUItemcode());
            info.setDisNum(l.getUDisnum());
            info.setQty(l.getUQty());
            info.setCreator(l.getUCreator());
            info.setDocDate(l.getUDocdate());
            info.setOl(StringUtil.isEmpty(l.getUYdwz()) ? l.getUGdwz() : l.getUYdwz());
            if (dto.getDoctype().equals(DocTypeEnum.INSPECTION_FORM.getValue())) {
                ChWmsGoodsin1 chWmsGoodsin1 = chWmsGoodsin1Service.getOne(new QueryWrapper<>(ChWmsGoodsin1.builder()
                        .DocEntry(Integer.parseInt(l.getUBaseentry()))
                        .LineId(Integer.parseInt(l.getUBaseline()))
                        .build()));
                if (chWmsGoodsin1 != null) {
                    info.setDocNum(chWmsGoodsin1.getUQuantity());
                    info.setItemName(chWmsGoodsin1.getUItemname());
                }
            } else if (dto.getDoctype().equals(DocTypeEnum.SHIP_FORM.getValue())) {
                IGN1 ign1 = ign1Service.getOne(new QueryWrapper<>(IGN1.builder()
                        .DocEntry(Integer.parseInt(l.getUBaseentry()))
                        .LineNum(Integer.parseInt(l.getUBaseline()))
                        .build()));
                if (ign1 != null) {
                    info.setDocNum(ign1.getQuantity());
                    info.setItemName(ign1.getDscription());
                }
            }
            infos.add(info);
        });
        return infos;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MaterialTransferInfo {
        @ApiModelProperty(value = "id")
        private Long uId;
        @ApiModelProperty(value = "料号")
        private String itemCode;
        @ApiModelProperty(value = "批号")
        private String disNum;
        @ApiModelProperty(value = "名称规格")
        private String itemName;
        @ApiModelProperty(value = "原位置")
        private String ol;
        @ApiModelProperty(value = "单据数量")
        private BigDecimal DocNum;
        @ApiModelProperty(value = "数量")
        private BigDecimal qty;
        @ApiModelProperty(value = "操作员")
        private String creator;
        @ApiModelProperty(value = "操作时间")
        private Date docDate;
    }

    @PostMapping("/stock")
    @ApiOperation("确认库存转移物料接口")
    public void confirmStock(@RequestBody MaterialTransferStockUpdateDto dto) {
        warehouseLocationService.confirmStockMaterialTransfer(dto);
    }

    @PostMapping("/nonStock")
    @ApiOperation("确认非库存转移物料接口")
    public void confirmNonStock(@RequestBody MaterialTransferNonStockUpdateDto dto) {
        materialFlowService.confirmNonStockMaterialTransfer(dto);
    }

    /////////////批转移相关////////////

    @PostMapping("/stockBatchInquire")
    @ApiOperation("库存批转移查找接口")
    public MaterialTransferBatchInfo stockBatchInquire(@RequestBody MaterialTransferBatchSearchDto dto) {
        //库存怎么做
        List<WarehouseLocation> list = warehouseLocationService.lambdaQuery()
                .eq(WarehouseLocation::getUYdwz, dto.getLocation())
                .eq(WarehouseLocation::getActive, ValidityIndicatorEnum.VALID).list();
        MaterialTransferBatchInfo info = new MaterialTransferBatchInfo();
        if (list.size() > 0) {
            info.setDisTotal(list.size());
            info.setDocTotal(list.size());
            info.setItemTotal(list.size());
            List<MaterialTransferInfo> infoList = new ArrayList<>();
            list.forEach(m ->
                    infoList.add(MaterialTransferInfo.builder()
                            .uId(m.getUId())
                            .itemCode(m.getItemCode())
                            .disNum(m.getDisNum())
                            .itemName(oitmService.getBaseMapper().selectById(m.getItemCode()).getItemName())
                            .creator(m.getUCreator())
                            .docDate(m.getUDocdate())
                            .build())
            );
            info.setList(infoList);
        }
        return info;
    }

    @PostMapping("/nonStockBatchInquire")
    @ApiOperation("非库存批转移查找接口")
    public MaterialTransferBatchInfo nonStockBatchInquire(@RequestBody MaterialTransferBatchSearchDto dto) {
        //非库存
        List<MaterialFlow> list = materialFlowService.lambdaQuery()
                .eq(MaterialFlow::getUYdwz, dto.getLocation())
                .eq(MaterialFlow::getUActive, ValidityIndicatorEnum.VALID).list();
        MaterialTransferBatchInfo info = new MaterialTransferBatchInfo();
        if (list.size() > 0) {
            info.setDisTotal(list.size());
            info.setDocTotal(list.size());
            info.setItemTotal(list.size());
            List<MaterialTransferInfo> infoList = new ArrayList<>();
            list.forEach(m ->
                    infoList.add(MaterialTransferInfo.builder()
                            .uId(m.getUId())
                            .itemCode(m.getUItemcode())
                            .disNum(m.getUDisnum())
                            .itemName(oitmService.getBaseMapper().selectById(m.getUItemcode()).getItemName())
                            .creator(m.getUCreator())
                            .docDate(m.getUDocdate())
                            .build())
            );
            info.setList(infoList);
        }
        return info;
    }

    @Data
    private static class MaterialTransferBatchInfo {
        @ApiModelProperty(value = "单据总数")
        private int docTotal;
        @ApiModelProperty(value = "物料种数")
        private int itemTotal;
        @ApiModelProperty(value = "批次数量")
        private int disTotal;
        @ApiModelProperty(value = "物料详细信息")
        List<MaterialTransferInfo> list;
    }

    @PostMapping("/stockBatch")
    @ApiOperation("确认库存批转移物料接口")
    public void confirmStockBatch(@RequestBody MaterialTransferBatchUpdateDto dto) {
        warehouseLocationService.confirmStockMaterialBatchTransfer(dto);
    }

    @PostMapping("/nonStockBatch")
    @ApiOperation("确认非库存批转移物料接口")
    public void confirmNonStockBatch(@RequestBody MaterialTransferBatchUpdateDto dto) {
        materialFlowService.confirmNonStockMaterialBatchTransfer(dto);
    }
}

