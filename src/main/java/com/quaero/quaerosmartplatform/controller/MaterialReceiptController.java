package com.quaero.quaerosmartplatform.controller;

import com.quaero.quaerosmartplatform.annotations.ResponseResult;
import com.quaero.quaerosmartplatform.domain.dto.MaterialPlanListDto;
import com.quaero.quaerosmartplatform.domain.dto.MaterialUnPlanListDto;
import com.quaero.quaerosmartplatform.domain.entity.MaterialPlanInfo;
import com.quaero.quaerosmartplatform.domain.entity.Oitm;
import com.quaero.quaerosmartplatform.domain.entity.User;
import com.quaero.quaerosmartplatform.domain.enumeration.ResultCode;
import com.quaero.quaerosmartplatform.domain.enumeration.ValidityEnum;
import com.quaero.quaerosmartplatform.domain.vo.*;
import com.quaero.quaerosmartplatform.exceptions.BusinessException;
import com.quaero.quaerosmartplatform.exceptions.DataNotFoundException;
import com.quaero.quaerosmartplatform.exceptions.ParameterInvalidException;
import com.quaero.quaerosmartplatform.service.MaterialPlanInfoService;
import com.quaero.quaerosmartplatform.service.OitmService;
import com.quaero.quaerosmartplatform.service.POR1Service;
import com.quaero.quaerosmartplatform.service.UserService;
import com.quaero.quaerosmartplatform.utils.RedisUtil;
import com.quaero.quaerosmartplatform.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
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
    private final OitmService oitmService;
    private final UserService userService;
    private final POR1Service por1Service;
    private final RedisUtil redisUtil;

    @Value("${customize.redisTime}")
    private long redisTime;

    public MaterialReceiptController(MaterialPlanInfoService materialPlanInfoService, OitmService oitmService, UserService userService, RedisUtil redisUtil, POR1Service por1Service) {
        this.materialPlanInfoService = materialPlanInfoService;
        this.oitmService = oitmService;
        this.userService = userService;
        this.por1Service = por1Service;
        this.redisUtil = redisUtil;
    }

    @PostMapping("/planListByOrder")
    @ApiOperation("按订单计划到料查询")
    public MaterialPlanInfoVo planListByOrder(@RequestBody MaterialPlanListDto listDto) {
        MaterialPlanInfoVo infoVo = new MaterialPlanInfoVo();
        List<MaterialPlanListVo> listVos = materialPlanInfoService.MATERIAL_PLAN_LIST_VOS(listDto);
        if (listVos.size() == 0) {
            infoVo.setFlag(true);
            //提醒前端 增加条件或者在途未交按钮打开
            return infoVo;
        }
        for (MaterialPlanListVo vo : listVos) {
            MaterialPlanInfo redisVo = (MaterialPlanInfo) redisUtil.hget("planList", vo.getBaseEntry() + "," + vo.getBaseLine());
            if (redisVo != null) {
                vo.setUwlwz(redisVo.getUWLWZ());
                vo.setUcz(redisVo.getUCz());
                vo.setUds(redisVo.getUDs());
                vo.setUwb(redisVo.getUWb());
                vo.setUlj(redisVo.getULj());
                vo.setUPic(redisVo.getUPic());
            }
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

    @GetMapping("/planInitByOrder")
    @ApiOperation("按订单计划到料信息页面初始化")
    public MaterialPlanInitByOrder planInitByOrder(@ApiParam("id") @RequestParam() String id) {
        MaterialPlanInfo info = materialPlanInfoService.getById(id);
        if (info == null)
            throw new DataNotFoundException("该数据不存在");
        MaterialPlanInitByOrder initInfo = new MaterialPlanInitByOrder();
        BeanUtils.copyProperties(info, initInfo);
        Oitm oitm = oitmService.getById(info.getUItemCode());
        if ("Y".equals(oitm.getMJWL()))
            initInfo.setType("免、");
        if ("Y".equals(oitm.getSFYFWL()))
            initInfo.setType(initInfo.getType() + "研");
        initInfo.setProtectionLevel(oitm.getWLDJ());
        MaterialPlanInfo redisVo = (MaterialPlanInfo) redisUtil.hget("planList", info.getUBaseEntry() + "," + info.getUBaseLine());
        if (redisVo != null) {
            initInfo.setUWLWZ(redisVo.getUWLWZ());
            initInfo.setUCz(redisVo.getUCz());
            initInfo.setUDs(redisVo.getUDs());
            initInfo.setUWb(redisVo.getUWb());
            initInfo.setULj(redisVo.getULj());
            initInfo.setUPic(redisVo.getUPic());
        }
        return initInfo;
    }

    @Data
    public static class MaterialPlanInitByOrder extends MaterialPlanInfo {
        @ApiModelProperty("防护等级")
        private String protectionLevel;
        @ApiModelProperty("类型 免贵研")
        private String type;
    }

    @PutMapping("/planEditByOrder")
    @ApiOperation("按订单计划到料信息编辑")
    public void planEditByOrder(@RequestBody @Validated MaterialPlanEditByOrder edit) {
        MaterialPlanInfo info = materialPlanInfoService.getById(edit.getId());
        if (info == null)
            throw new DataNotFoundException("该数据不存在");
        info.setUWLWZ(edit.getUwlwz());
        info.setUDs(edit.getUds());
        info.setUCz(edit.getUcz());
        info.setUWb(edit.getUwb());
        if (edit.getImages().length > 0) {
            info.setUPic(String.valueOf(edit.getImages().length));
            info.setULj(StringUtils.join(edit.getImages(), ","));
        }
        redisUtil.hset("planList", info.getUBaseEntry() + "," + info.getUBaseLine(), info, redisTime);
    }

    @PutMapping("/planConfirmedByOrder")
    @ApiOperation("按订单计划确认到料")
    public void planConfirmedByOrder(@Validated @RequestBody int[] Ids, Authentication authentication) {
        for (int id : Ids) {
            MaterialPlanInfo info = materialPlanInfoService.getById(id);
            MaterialPlanInfo redisVo = (MaterialPlanInfo) redisUtil.hget("planList", info.getUBaseEntry() + "," + info.getUBaseLine());
            info.setUSLBS(ValidityEnum.VALID);
            User user = userService.getById(authentication.getName());
            info.setUUserSign3(authentication.getName());
            info.setUName3(user.getName());
            info.setUDocDate(new Date());
            info.setUWLWZ(redisVo.getUWLWZ());
            info.setUCz(redisVo.getUCz());
            info.setUDs(redisVo.getUDs());
            info.setUWb(redisVo.getUWb());
            info.setULj(redisVo.getULj());
            info.setUPic(redisVo.getUPic());
            materialPlanInfoService.saveOrUpdate(info);
            redisUtil.hdel("planList", info.getUBaseEntry() + "," + info.getUBaseLine());
        }
    }

    @Data
    public static class MaterialPlanEditByOrder {
        @ApiModelProperty("id")
        @NotBlank
        private String id;
        @ApiModelProperty("物理位置")
        @NotBlank
        private String uwlwz;
        @ApiModelProperty("点数信息")
        private String uds;
        @ApiModelProperty("称重信息")
        private String ucz;
        @ApiModelProperty("外包信息")
        private String uwb;
        @ApiModelProperty("图片路径")
        private String[] images;
    }

    @PostMapping(value = "/upload", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ApiOperation("图片上传")
    public String[] imageUpload(@RequestParam(name = "files", value = "files") MultipartFile[] files) throws IOException {
        String[] paths = new String[files.length];
        int i = 0;
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String oldFileName = file.getOriginalFilename();
                String path = "C:\\images";
                String randomStr = UUID.randomUUID().toString();
                String newFileName = randomStr + oldFileName.substring(oldFileName.lastIndexOf("."));
                File f = new File(path, newFileName);
                if (!f.getParentFile().exists()) {
                    f.getParentFile().mkdirs();
                }
                file.transferTo(f);
                paths[i] = path + "/" + newFileName;
                i++;
            }
        }
        return paths;
    }

    @PostMapping("/unPlanListByOrder")
    @ApiOperation("无计划到料未交查询结果")
    public MaterialUnPlanInfoVo unPlanListByOrderWithItemCode(@Validated @RequestBody MaterialUnPlanListDto dto) {
        MaterialUnPlanInfoVo infoVo = new MaterialUnPlanInfoVo();
        List<MaterialUnPlanListVo> list = por1Service.unPlanUnpaidList(dto);
        for (MaterialUnPlanListVo vo : list) {
            if (null == redisUtil.hget("unPlanList", vo.getDocEntry() + "," + vo.getLineNum())) {
                redisUtil.hset("unPlanList", vo.getDocEntry() + "," + vo.getLineNum(), vo, redisTime);
            }
        }
        Map<Object,Object> map = redisUtil.hmget("unPlanList");
        List<MaterialUnPlanListVo> mapList = new ArrayList<>();
        for (Object vo : map.values()){
            mapList.add((MaterialUnPlanListVo)vo);
        }
        if (StringUtil.isNotEmpty(dto.getSalesmanName())) {
            mapList = mapList.stream().filter(e -> e.getSlpName().equals(dto.getSalesmanName())).collect(Collectors.toList());
        }
        if (StringUtil.isEmpty(dto.getCardCode())) {
            //按料号
            List<MaterialPlanByItemCodeVo> itemCodeVos = new ArrayList<>();
            Map<String, List<MaterialUnPlanListVo>> filterMap = mapList.stream().filter(m -> m.getItemCode().equals(dto.getItemCode())).collect(Collectors.groupingBy(MaterialUnPlanListVo::getCardCode));
            if (filterMap.size() == 0) {
                throw new BusinessException(ResultCode.RESULT_DATA_NONE);
            }
            for (Map.Entry<String, List<MaterialUnPlanListVo>> entry : filterMap.entrySet()) {
                MaterialPlanByItemCodeVo itemCodeVo = new MaterialPlanByItemCodeVo();
                itemCodeVo.setUnPlanList(entry.getValue());
                itemCodeVo.setUnpaidQuantity(entry.getValue().stream().map(MaterialUnPlanListVo::getUnpaidQuantity).reduce(BigDecimal.ZERO, BigDecimal::add));
                itemCodeVo.setCardCode(entry.getKey());
                itemCodeVo.setCardName(entry.getValue().get(0).getCardName());
                itemCodeVos.add(itemCodeVo);
            }
            infoVo.setItemCodeVos(itemCodeVos);
            infoVo.setItemCode(itemCodeVos.get(0).getUnPlanList().get(0).getItemCode());
            infoVo.setItemName(itemCodeVos.get(0).getUnPlanList().get(0).getDscription());
        } else {
            //按料号和供应商代号
            mapList = mapList.stream().filter(m -> m.getItemCode().equals(dto.getItemCode()) && m.getCardCode().equals(dto.getCardCode())).collect(Collectors.toList());
            if (mapList.size() == 0) {
                throw new BusinessException(ResultCode.RESULT_DATA_NONE);
            }
            infoVo.setListVos(mapList);
            BeanUtils.copyProperties(mapList.get(0), infoVo);
            infoVo.setItemName(mapList.get(0).getDscription());
            infoVo.setUnpaidQuantity(mapList.stream().map(MaterialUnPlanListVo::getUnpaidQuantity).reduce(BigDecimal.ZERO, BigDecimal::add));
        }
        return infoVo;
    }

    @GetMapping("/UnPlanInitByOrder")
    @ApiOperation("按订单无计划到料信息页面初始化")
    public MaterialPlanInitByOrder unPlanInitByOrder(@ApiParam("单号") @RequestParam() String baseEntry,
                                                     @ApiParam("行号") @RequestParam() String baseLine) {
        MaterialUnPlanListVo info = (MaterialUnPlanListVo) redisUtil.hget("unPlanList", baseEntry + "," + baseLine);
        if (info == null)
            throw new DataNotFoundException("该数据不存在");
        MaterialPlanInitByOrder initInfo = new MaterialPlanInitByOrder();
        initInfo.setUBaseEntry(String.valueOf(info.getDocEntry()));
        initInfo.setUBaseLine(String.valueOf(info.getLineNum()));
        initInfo.setUBaseType(info.getObjType());
        initInfo.setUCardCode(info.getCardCode());
        initInfo.setUCardName(info.getCardName());
        initInfo.setUCz(info.getUcz());
        initInfo.setUDs(info.getUds());
        initInfo.setUItemCode(info.getItemCode());
        initInfo.setUItemName(info.getDscription());
        initInfo.setUShipDate(info.getShipDate());
        initInfo.setUPic(info.getUPic());
        initInfo.setUWb(info.getUwb());
        initInfo.setUWLWZ(info.getUwlwz());
        initInfo.setULj(info.getUlj());
        Oitm oitm = oitmService.getById(info.getItemCode());
        if ("Y".equals(oitm.getMJWL()))
            initInfo.setType("免、");
        if ("Y".equals(oitm.getSFYFWL()))
            initInfo.setType(initInfo.getType() + "研");
        initInfo.setProtectionLevel(oitm.getWLDJ());
        return initInfo;
    }

    @PutMapping("/unPlanEditByOrder")
    @ApiOperation("按订单无计划到料信息编辑")
    public void unPlanEditByOrder(@RequestBody @Validated MaterialUnPlanEditByOrder edit) {
        MaterialUnPlanListVo info = (MaterialUnPlanListVo) redisUtil.hget("unPlanList", edit.getBaseEntry() + "," + edit.getBaseLine());
        if (info == null)
            throw new DataNotFoundException("该数据不存在");
        info.setUwlwz(edit.getUwlwz());
        info.setUds(edit.getUds());
        info.setUcz(edit.getUcz());
        info.setUwb(edit.getUwb());
        if (edit.getImages().length>0) {
           info.setUlj(StringUtils.join(edit.getImages(), ","));
           info.setUPic(String.valueOf(edit.getImages().length));
        }
        redisUtil.hset("unPlanList", edit.getBaseEntry() + "," + edit.getBaseLine(), info, redisTime);
    }

    @Data
    public static class MaterialUnPlanEditByOrder {
        @ApiModelProperty("单号")
        @NotBlank
        private String baseEntry;
        @ApiModelProperty("行号")
        @NotBlank
        private String baseLine;
        @ApiModelProperty("物理位置")
        @NotBlank
        private String uwlwz;
        @ApiModelProperty("点数信息")
        private String uds;
        @ApiModelProperty("称重信息")
        private String ucz;
        @ApiModelProperty("外包信息")
        private String uwb;
        @ApiModelProperty("图片路径")
        private String[] images;
    }

    @PutMapping("/unPlanConfirmedByOrder")
    @ApiOperation("按订单无计划确认到料")
    public void unPlanConfirmedByOrder(@Validated @RequestBody List<MaterialUnPlanPutByOrder> puts, Authentication authentication) {
        for (MaterialUnPlanPutByOrder put : puts) {
            MaterialUnPlanListVo vo = (MaterialUnPlanListVo) redisUtil.hget("unPlanList", put.getDocEntry() + "," + put.getLineNum());
            if (vo == null) {
                throw new DataNotFoundException("对象不存在，请重新查询进入");
            }
            String name = userService.getById(authentication.getName()).getName();
            materialPlanInfoService.save(MaterialPlanInfo.builder()
                    .uSJBS(ValidityEnum.INVALID)
                    .uSLBS(ValidityEnum.VALID)
                    .uGQBS(ValidityEnum.INVALID)
                    .uItemCode(vo.getItemCode())
                    .uItemName(vo.getDscription())
                    .uBaseEntry(String.valueOf(vo.getDocEntry()))
                    .uBaseLine(String.valueOf(vo.getLineNum()))
                    .uBaseType(vo.getObjType())
                    .uPmcQty(vo.getUnpaidQuantity())
                    .UShipDate(vo.getShipDate())
                    .UCardName(vo.getCardName())
                    .UCardCode(vo.getCardCode())
                    .uUserSign(authentication.getName())
                    .uName(name)
                    .uUserSign3(authentication.getName())
                    .uName3(name)
                    .uDocDate(new Date())
                    .uTaxDate(new Date())
                    .uWLWZ(vo.getUwlwz())
                    .uDs(vo.getUds())
                    .uCz(vo.getUcz())
                    .uWb(vo.getUwb())
                    .uPic(vo.getUPic())
                    .uLj(vo.getUlj())
                    .build());
            redisUtil.hdel("unPlanList", put.getDocEntry() + "," + put.getLineNum());
        }
    }

    @Data
    public static class MaterialUnPlanPutByOrder {
        @ApiModelProperty("来源单号")
        @NotBlank(message = "来源单号不能空")
        private String docEntry;
        @ApiModelProperty("来源行号")
        @NotBlank(message = "来源行号不能空")
        private String lineNum;
    }
}
