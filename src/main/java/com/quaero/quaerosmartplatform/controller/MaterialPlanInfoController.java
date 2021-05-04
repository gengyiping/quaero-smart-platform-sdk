package com.quaero.quaerosmartplatform.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.quaero.quaerosmartplatform.annotations.ResponseResult;
import com.quaero.quaerosmartplatform.domain.dto.MaterialPlanUnpaidListDto;
import com.quaero.quaerosmartplatform.domain.entity.MaterialPlanInfo;
import com.quaero.quaerosmartplatform.domain.entity.OPOR;
import com.quaero.quaerosmartplatform.domain.enumeration.ResultCode;
import com.quaero.quaerosmartplatform.domain.enumeration.ValidityEnum;
import com.quaero.quaerosmartplatform.domain.vo.MaterialPlanUnpaidListVo;
import com.quaero.quaerosmartplatform.exceptions.BusinessException;
import com.quaero.quaerosmartplatform.exceptions.DataNotFoundException;
import com.quaero.quaerosmartplatform.service.*;
import com.quaero.quaerosmartplatform.utils.RedisUtil;
import com.quaero.quaerosmartplatform.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 计划到料 1-1
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021/1/13 16:08
 */
@RestController
@ResponseResult
@Api(tags = "计划到料相关接口")
//@PreAuthorize("hasAuthority('AAA')")
@RequestMapping("/api/materialPlan")
public class MaterialPlanInfoController {

    private final MaterialPlanInfoService materialPlanInfoService;
    private final OPORService oporService;
    private final RedisUtil redisUtil;
    private final POR1Service por1Service;
    private final UserService userService;
    private final OWORService oworService;

    @Value("${customize.redisTime}")
    private long redisTime;

    public MaterialPlanInfoController(MaterialPlanInfoService materialPlanInfoService, OPORService oporService, RedisUtil redisUtil, POR1Service por1Service, UserService userService, OWORService oworService) {
        this.materialPlanInfoService = materialPlanInfoService;
        this.oporService = oporService;
        this.redisUtil = redisUtil;
        this.por1Service = por1Service;
        this.userService = userService;
        this.oworService = oworService;
    }

    @PostMapping("/unpaidListByOrder")
    @ApiOperation("按订单计划到料未交查询列表")
    public List<MaterialPlanUnpaidListVo> unpaidListByOrder(@Validated @RequestBody MaterialPlanUnpaidListDto unpaidListDto) {
        List<MaterialPlanUnpaidListVo> listVos;
        if (unpaidListDto.getOrderType() == 0) {
            listVos = por1Service.unpaidList(unpaidListDto);
        } else {
            listVos = oworService.unpaidList(unpaidListDto);
        }
        if (listVos.size() <= 0) {
            throw new BusinessException(ResultCode.RESULT_DATA_NONE);
        }
        listVos.forEach(l -> {
            MaterialPlanUnpaidListVo vo = (MaterialPlanUnpaidListVo) redisUtil.hget("unpaidListByOrder", l.getDocEntry() + "," + l.getLineNum());
            if (vo == null) {
                redisUtil.hset("unpaidListByOrder", l.getDocEntry() + "," + l.getLineNum(), l, redisTime);
            } else {
                l.setPlannedQty(vo.getPlannedQty());
                l.setDueDate(vo.getDueDate());
                l.setWlxx(vo.getWlxx());
                l.setDlfs(vo.getDlfs());
            }
        });
        return listVos;
    }

    @PostMapping("/unpaidEditByOrder")
    @ApiOperation("按订单计划到料信息输入")
    public void unpaidEdit(@Validated @RequestBody MaterialUnpaidEdit edit) {
        MaterialPlanUnpaidListVo vo = (MaterialPlanUnpaidListVo) redisUtil.hget("unpaidListByOrder", edit.getDocEntry() + "," + edit.getLineNum());
        if (vo == null) {
            throw new DataNotFoundException("对象不存在，请重新查询进入");
        }
        vo.setPlannedQty(edit.getPlannedQty());
        vo.setDueDate(edit.getDueDate());
        vo.setWlxx(edit.getWlxx());
        vo.setDlfs(edit.getDlfs());
        redisUtil.hset("unpaidListByOrder", vo.getDocEntry() + "," + vo.getLineNum(), vo, redisTime);
    }

    @PutMapping("/unpaidByOrder")
    @ApiOperation("按订单计划到料提交到料计划")
    public void unpaidByOrder(@Validated @RequestBody List<MaterialUnpaidPutByOrder> puts, Authentication authentication) throws ParseException {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        for (MaterialUnpaidPutByOrder put : puts) {
            MaterialPlanUnpaidListVo vo = (MaterialPlanUnpaidListVo) redisUtil.hget("unpaidListByOrder", put.getDocEntry() + "," + put.getLineNum());
            if (vo == null) {
                throw new DataNotFoundException("对象不存在，请重新查询进入");
            }
            if (vo.getDueDate() == null){
                throw new DataNotFoundException("计划到料日期不能空");
            }
            if(vo.getPlannedQty() == null){
                throw new DataNotFoundException("计划到料数不能空");
            }
            MaterialPlanInfo info = MaterialPlanInfo.builder()
                    .uBaseEntry(String.valueOf(vo.getDocEntry()))
                    .uBaseLine(String.valueOf(vo.getLineNum()))
                    .uSJBS(ValidityEnum.INVALID)
                    .uSLBS(ValidityEnum.INVALID)
                    .uGQBS(ValidityEnum.INVALID)
                    .build();
            MaterialPlanInfo insert= MaterialPlanInfo.builder()
                    .uSJBS(ValidityEnum.INVALID)
                    .uSLBS(ValidityEnum.INVALID)
                    .uGQBS(ValidityEnum.INVALID)
                    .uItemCode(vo.getItemCode())
                    .uItemName(vo.getDscription())
                    .uBaseEntry(String.valueOf(vo.getDocEntry()))
                    .uBaseLine(String.valueOf(vo.getLineNum()))
                    .uBaseType(vo.getObjType())
                    .uPlannedQty(vo.getPlannedQty())
                    .uDueDate(vo.getDueDate())
                    .uPmcQty(vo.getUnpaidQuantity())
                    .UShipDate(vo.getShipDate())
                    .UCardName(vo.getCardName())
                    .UCardCode(vo.getCardCode())
                    .uUserSign(authentication.getName())
                    .uName(userService.getById(authentication.getName()).getName())
                    .uTaxDate(new Date())
                    .uDLFS(vo.getDlfs())
                    .uWLXX(vo.getWlxx())
                    .UPMCZD(vo.getPmcZD())
                    .UZZQL(StringUtil.isEmpty(vo.getZzql())?null:ft.parse(vo.getZzql()))
                    .UJYJFQTY(vo.getJyjfQTY())
                    .build();
            if (materialPlanInfoService.count(new QueryWrapper<>(info)) > 0) {
                insert.setUId(materialPlanInfoService.list(new QueryWrapper<>(info)).get(0).getUId());
            }
            materialPlanInfoService.saveOrUpdate(insert);
            redisUtil.hdel("unpaidListByOrder", put.getDocEntry() + "," + put.getLineNum());
        }
    }

    @Data
    public static class MaterialUnpaidPutByOrder {
        @ApiModelProperty("来源单号")
        @NotBlank(message = "来源单号不能空")
        private String docEntry;
        @ApiModelProperty("来源行号")
        @NotBlank(message = "来源行号不能空")
        private String lineNum;
    }

    @Data
    public static class MaterialUnpaidEdit {
        @ApiModelProperty("来源单号")
        @NotBlank(message = "来源单号不能空")
        private String docEntry;
        @ApiModelProperty("来源行号")
        @NotBlank(message = "来源行号不能空")
        private String lineNum;
        @ApiModelProperty("计划到料数")
        @NotNull(message = "计划到料数不能空")
        private BigDecimal plannedQty;
        @ApiModelProperty("计划到料日期")
        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "计划到料日期不能空")
        private Date dueDate;
        @ApiModelProperty("到料方式")
        @NotBlank(message = "到料方式不能空")
        private String dlfs;
        @ApiModelProperty("物流信息")
        private String wlxx;
    }

    @GetMapping("/supplierList")
    @ApiOperation("供应商列表接口")
    public List<OPOR> supplierList(@RequestParam String cardCode) {
        List<OPOR> list = (List<OPOR>) redisUtil.get("supplierList");
        if (list == null) {
            list = oporService.oporList();
            redisUtil.set("supplierList", list, redisTime);
        }
        return list.stream().filter(s -> s.getCardCode().startsWith(cardCode)).collect(Collectors.toList());
    }

}
