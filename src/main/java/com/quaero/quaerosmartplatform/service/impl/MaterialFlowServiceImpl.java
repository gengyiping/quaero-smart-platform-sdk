package com.quaero.quaerosmartplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
import com.quaero.quaerosmartplatform.utils.Constants;
import com.quaero.quaerosmartplatform.utils.StringUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
                    .uCreator(SecurityContextHolder.getContext().getAuthentication().getName())
                    .uDocdate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                    .build());
        } else {
            if (StringUtil.isEmpty(dto.getUIds())) throw new BusinessException("传入id为空");
            List<String> idsList = Arrays.stream(dto.getUIds().split(",")).collect(Collectors.toList());
            List<MaterialFlow> list = baseMapper.selectBatchIds(idsList);
            list.forEach(materialFlow -> {
                if (dto.getQty().compareTo(materialFlow.getUQty()) > 0)
                    throw new BusinessException("移动数量大于该位置数量");
                //转移
                doMaterialNonStock(materialFlow, dto);
                //原位置状态处理
                materialFlow.setUWzbs(dto.isWzbs() ? IntegrityMarkEnum.NONE : IntegrityMarkEnum.SECTION);
                materialFlow.setUActive(dto.isWzbs() ? ValidityIndicatorEnum.INVALID : ValidityIndicatorEnum.VALID);
                materialFlow.setUQty(dto.isWzbs() ? BigDecimal.ZERO : materialFlow.getUQty().subtract(dto.getQty()));
                baseMapper.updateById(materialFlow);
            });
        }
    }

    /**
     * 非库存 转移动作
     *
     * @param ol        原位置对象
     * @param dto       移动参
     */
    private void doMaterialNonStock(MaterialFlow ol, MaterialTransferNonStockUpdateDto dto) {
        MaterialFlow nl = MaterialFlow.builder()
                .uBarcode(dto.getItemCode() + dto.getDisNum())
                .uItemcode(ol.getUItemcode())
                .uDisnum(ol.getUDisnum())
                .uBaseentry(ol.getUBaseentry())
                .uBaseline(ol.getUBaseline())
                .uDoctype(ol.getUDoctype())
                .uWzbs(dto.isWzbs() ? IntegrityMarkEnum.ALL : IntegrityMarkEnum.SECTION)
                .uQty(dto.getQty())
                .uActive(ValidityIndicatorEnum.VALID)
                .uCardcode(ol.getUCardcode())
                .uCreator(SecurityContextHolder.getContext().getAuthentication().getName())
                .uDocdate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .build();
        //目标位置为移动位置
        if (dto.getTLocation().startsWith(Constants.KW_PREFIX)) {
            nl.setUGdwz(ol.getUGdwz());
            nl.setUYdwz(dto.getTLocation());
        } else {
            nl.setUGdwz(dto.getTLocation());
        }
        //新位置对象
        baseMapper.insert(nl);
    }


    /**
     * 非库存 批转移
     * @param dto
     */
    @Override
    public void confirmNonStockMaterialBatchTransfer(MaterialTransferBatchUpdateDto dto) {
        //非库存
        List<MaterialFlow> list = baseMapper.selectList(new QueryWrapper<>(
                MaterialFlow.builder()
                        .uYdwz(dto.getLocation())
                        .uActive(ValidityIndicatorEnum.VALID)
                        .build()));
        list.forEach(m -> {
            //旧的状态改变
            m.setUActive(ValidityIndicatorEnum.INVALID);
            baseMapper.update(m, new UpdateWrapper<>(MaterialFlow.builder()
                    .uYdwz(dto.getLocation())
                    .uActive(ValidityIndicatorEnum.VALID)
                    .build()));
            //创建新的
            m.setUActive(ValidityIndicatorEnum.VALID);
            m.setUGdwz(dto.getTargetLocation());
            m.setUYdwz(null);
            m.setUCreator(SecurityContextHolder.getContext().getAuthentication().getName());
            m.setUDocdate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            baseMapper.insert(m);
        });
    }
}
