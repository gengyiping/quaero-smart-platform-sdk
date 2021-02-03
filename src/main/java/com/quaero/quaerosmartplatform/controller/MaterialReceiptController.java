package com.quaero.quaerosmartplatform.controller;

import com.quaero.quaerosmartplatform.annotations.ResponseResult;
import com.quaero.quaerosmartplatform.domain.dto.MaterialPlanListDto;
import com.quaero.quaerosmartplatform.domain.vo.*;
import com.quaero.quaerosmartplatform.exceptions.ParameterInvalidException;
import com.quaero.quaerosmartplatform.service.MaterialPlanInfoService;
import com.quaero.quaerosmartplatform.utils.RedisUtil;
import com.quaero.quaerosmartplatform.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 收料确认
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021/1/25 8:15
 */
@RestController
@ResponseResult
@Api(tags = "收料确认相关接口")
//@PreAuthorize("hasAuthority('AAA')")
@RequestMapping("/api/materialReceipt")
public class MaterialReceiptController {

    private final MaterialPlanInfoService materialPlanInfoService;
    private final RedisUtil redisUtil;

    public MaterialReceiptController(MaterialPlanInfoService materialPlanInfoService, RedisUtil redisUtil) {
        this.materialPlanInfoService = materialPlanInfoService;
        this.redisUtil = redisUtil;
    }

    @PostMapping("/planListByItemCode")
    @ApiOperation("按料号计划到料查询")
    public MaterialPlanInfoVo planListByItemCode(@RequestBody MaterialPlanListDto listDto) {
        MaterialPlanInfoVo infoVo = new MaterialPlanInfoVo();
        List<MaterialPlanListVo> listVos = materialPlanInfoService.MATERIAL_PLAN_LIST_VOS(listDto);
        if (listVos.size() == 0) {
            infoVo.setFlag(true);
            //提醒前端 增加条件或者在途未交按钮打开
            return infoVo;
        }
        infoVo.setFlag(false);
        if (StringUtil.isNotEmpty(listDto.getCareCode())) {
            if (listDto.getArrivalDateAfter() != null
                    && listDto.getArrivalDateBefore() != null) {
                //按供应商代号、料号、日期
                if (StringUtil.isNotEmpty(listDto.getItemCode())) {
                    infoVo.setListVos(listVos);
                    infoVo.setItemCode(listVos.get(0).getItemCode());
                    infoVo.setItemName(listVos.get(0).getItemName());
                } else {
                    //按供应商代号和日期
                    List<MaterialPlanByCardCodeVo> cardCodeVos = new ArrayList<>();
                    Map<String, List<MaterialPlanListVo>> map = listVos.stream().collect(Collectors.groupingBy(MaterialPlanListVo::getItemCode));
                    for (Map.Entry<String, List<MaterialPlanListVo>> entry : map.entrySet()) {
                        MaterialPlanByCardCodeVo cardCodeVo = new MaterialPlanByCardCodeVo();
                        cardCodeVo.setList(entry.getValue());
                        cardCodeVo.setUnpaidQuantity(entry.getValue().stream().map(MaterialPlanListVo::getUnpaidQuantity).reduce(BigDecimal.ZERO, BigDecimal::add));
                        cardCodeVo.setItemCode(entry.getKey());
                        cardCodeVo.setItemName(entry.getValue().get(0).getItemName());
                        cardCodeVos.add(cardCodeVo);
                    }
                    infoVo.setCardCodeVos(cardCodeVos);
                }
                infoVo.setCardCode(listVos.get(0).getCardCode());
                infoVo.setCardName(listVos.get(0).getCardName());
                infoVo.setArrivalDateAfter(listDto.getArrivalDateAfter());
                infoVo.setArrivalDateBefore(listDto.getArrivalDateBefore());
                return infoVo;
            }
            //按供应商代号和料号
            if (StringUtil.isNotEmpty(listDto.getItemCode())) {
                throw new ParameterInvalidException("请输入计划到料日期");
            }
        } else {
            if (StringUtil.isNotEmpty(listDto.getItemCode())) {
                //按料号
                List<MaterialPlanByItemCodeVo> itemCodeVos = new ArrayList<>();
                Map<String, List<MaterialPlanListVo>> map = listVos.stream().collect(Collectors.groupingBy(MaterialPlanListVo::getCardCode));
                for (Map.Entry<String, List<MaterialPlanListVo>> entry : map.entrySet()) {
                    MaterialPlanByItemCodeVo itemCodeVo = new MaterialPlanByItemCodeVo();
                    itemCodeVo.setList(entry.getValue());
                    itemCodeVo.setUnpaidQuantity(entry.getValue().stream().map(MaterialPlanListVo::getUnpaidQuantity).reduce(BigDecimal.ZERO, BigDecimal::add));
                    itemCodeVo.setCardCode(entry.getKey());
                    itemCodeVo.setCardName(entry.getValue().get(0).getCardName());
                    itemCodeVos.add(itemCodeVo);
                }
                infoVo.setItemCodeVos(itemCodeVos);
                infoVo.setItemCode(listVos.get(0).getItemCode());
                infoVo.setItemName(listVos.get(0).getItemName());
                return infoVo;
            } else //按日期
                if (listDto.getArrivalDateAfter() != null
                        && listDto.getArrivalDateBefore() != null) {
                    List<MaterialPlanByDateVo> vos = new ArrayList<>();
                    Map<String, List<MaterialPlanByDateListVo>> map = materialPlanInfoService.MATERIAL_PLAN_BY_DATE_LIST_VOS(listDto)
                            .stream().collect(Collectors.groupingBy(MaterialPlanByDateListVo::getCardCode));
                    for (Map.Entry<String, List<MaterialPlanByDateListVo>> entry : map.entrySet()) {
                        MaterialPlanByDateVo dateVo = new MaterialPlanByDateVo();
                        dateVo.setList(entry.getValue());
                        dateVo.setItemCount(entry.getValue().size());
                        dateVo.setCardCode(entry.getKey());
                        dateVo.setCardName(entry.getValue().get(0).getCardName());
                        vos.add(dateVo);
                    }
                    infoVo.setCardCount(map.size());
                    infoVo.setDateVos(vos);
                    infoVo.setArrivalDateAfter(listDto.getArrivalDateAfter());
                    infoVo.setArrivalDateBefore(listDto.getArrivalDateBefore());
                    return infoVo;
                }
        }
        return null;
    }


}
