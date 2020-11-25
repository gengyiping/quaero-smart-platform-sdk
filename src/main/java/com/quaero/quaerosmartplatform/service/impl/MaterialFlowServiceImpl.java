package com.quaero.quaerosmartplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quaero.quaerosmartplatform.domain.dto.MaterialTransferBatchUpdateDto;
import com.quaero.quaerosmartplatform.domain.dto.MaterialTransferNonStockUpdateDto;
import com.quaero.quaerosmartplatform.domain.entity.IGE1;
import com.quaero.quaerosmartplatform.domain.entity.MaterialFlow;
import com.quaero.quaerosmartplatform.domain.enumeration.DocTypeEnum;
import com.quaero.quaerosmartplatform.domain.enumeration.IntegrityMarkEnum;
import com.quaero.quaerosmartplatform.domain.enumeration.ValidityIndicatorEnum;
import com.quaero.quaerosmartplatform.domain.mapper.IGE1Mapper;
import com.quaero.quaerosmartplatform.domain.mapper.MaterialFlowMapper;
import com.quaero.quaerosmartplatform.exceptions.BusinessException;
import com.quaero.quaerosmartplatform.service.MaterialFlowService;
import com.quaero.quaerosmartplatform.service.UserService;
import com.quaero.quaerosmartplatform.utils.Constants;
import com.quaero.quaerosmartplatform.utils.StringUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-03
 */
@Service
@Transactional
@RequiredArgsConstructor
public class MaterialFlowServiceImpl extends ServiceImpl<MaterialFlowMapper, MaterialFlow> implements MaterialFlowService {

    @NonNull
    private IGE1Mapper ige1Mapper;
    @NonNull
    private UserService userService;

    /**
     * 确认非库存转移
     *
     * @param dto
     */
    @Override
    public void confirmNonStockMaterialTransfer(MaterialTransferNonStockUpdateDto dto) {
        //发货单第一笔存数据库
        if (dto.getDoctype().equals(DocTypeEnum.SHIP_FORM.getValue()) && StringUtil.isEmpty(dto.getUIds())) {
            baseMapper.insert(MaterialFlow.builder()
                    .uActive(ValidityIndicatorEnum.VALID)
                    .uBarcode(dto.getItemCode() + dto.getDisNum())
                    .uItemcode(dto.getItemCode())
                    .uDisnum(dto.getDisNum())
                    .uGdwz(dto.getTLocation().startsWith(Constants.KW_PREFIX) ? null : dto.getTLocation())
                    .uYdwz(dto.getTLocation().startsWith(Constants.KW_PREFIX) ? dto.getTLocation() : null)
                    .uWzbs(dto.isWzbs() ? IntegrityMarkEnum.ALL : IntegrityMarkEnum.SECTION)
                    .uDoctype(dto.getDoctype())
                    .uBaseentry(dto.getBaseEntry())
                    .uBaseline(dto.getBaseline())
                    .uQty(ige1Mapper.selectOne(new QueryWrapper<>(IGE1.builder()
                            .DocEntry(Integer.parseInt(dto.getBaseEntry()))
                            .LineNum(Integer.parseInt(dto.getBaseline()))
                            .build()))
                            .getQuantity())
                    .uCreator(userService.getCurrentUser().getName())
                    .uDocdate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                    .build());
        } else {
            if (StringUtil.isEmpty(dto.getUIds())) throw new BusinessException("传入id为空");
            List<String> idsList = Arrays.stream(dto.getUIds().split(",")).collect(Collectors.toList());
            List<MaterialFlow> list = baseMapper.selectBatchIds(idsList);
            list.forEach(materialFlow -> {
                //转移
                doMaterialNonStock(materialFlow, dto.isWzbs(), dto.getQty(), dto.getTLocation());
            });
        }
    }

    /**
     * 非库存 批转移
     * @param dto
     */
    @Override
    public void confirmNonStockMaterialBatchTransfer(MaterialTransferBatchUpdateDto dto) {
        //非库存
        List<MaterialFlow> list = baseMapper.selectList(new QueryWrapper<>(MaterialFlow.builder()
                        .uYdwz(dto.getLocation()).uActive(ValidityIndicatorEnum.VALID).build()));
        list.forEach(m -> {
            doMaterialNonStock(m, true, m.getUQty(), dto.getTargetLocation());
        });
    }

    /**
     * 非库存 转移动作
     *
     * @param ol        原位置对象
     * @param wzbs      完整标识
     * @param qty       数量
     * @param tl        目标位置
     */
    private void doMaterialNonStock(MaterialFlow ol, boolean wzbs, BigDecimal qty, String tl) {
        if (qty.compareTo(ol.getUQty()) > 0)
            throw new BusinessException("移动数量大于该位置数量");
        MaterialFlow nl = MaterialFlow.builder()
                .uBarcode(ol.getUBarcode())
                .uItemcode(ol.getUItemcode())
                .uDisnum(ol.getUDisnum())
                .uBaseentry(ol.getUBaseentry())
                .uBaseline(ol.getUBaseline())
                .uDoctype(ol.getUDoctype())
                .uWzbs(wzbs ? IntegrityMarkEnum.ALL : IntegrityMarkEnum.SECTION)
                .uQty(qty)
                .uActive(ValidityIndicatorEnum.VALID)
                .uCardcode(ol.getUCardcode())
                .uCreator(userService.getCurrentUser().getName())
                .uDocdate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .build();
        //目标位置为移动位置
        if (tl.startsWith(Constants.KW_PREFIX)) {
            nl.setUGdwz(ol.getUGdwz());
            nl.setUYdwz(tl);
        } else {
            nl.setUGdwz(tl);
        }
        //新位置对象
        baseMapper.insert(nl);
        //原位置状态处理
        ol.setUWzbs(wzbs ? IntegrityMarkEnum.NONE : IntegrityMarkEnum.SECTION);
        ol.setUActive(wzbs ? ValidityIndicatorEnum.INVALID : ValidityIndicatorEnum.VALID);
        ol.setUQty(wzbs ? BigDecimal.ZERO : ol.getUQty().subtract(qty));
        baseMapper.updateById(ol);
    }
}
