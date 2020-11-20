package com.quaero.quaerosmartplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quaero.quaerosmartplatform.domain.dto.MaterialTransferBatchUpdateDto;
import com.quaero.quaerosmartplatform.domain.dto.MaterialTransferStockUpdateDto;
import com.quaero.quaerosmartplatform.domain.entity.WarehouseLocation;
import com.quaero.quaerosmartplatform.domain.enumeration.IntegrityMarkEnum;
import com.quaero.quaerosmartplatform.domain.enumeration.ValidityIndicatorEnum;
import com.quaero.quaerosmartplatform.domain.mapper.WarehouseLocationMapper;
import com.quaero.quaerosmartplatform.exceptions.BusinessException;
import com.quaero.quaerosmartplatform.service.WarehouseLocationService;
import com.quaero.quaerosmartplatform.utils.Constants;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

;
;
;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-03
 */
@Service
public class WarehouseLocationServiceImpl extends ServiceImpl<WarehouseLocationMapper, WarehouseLocation> implements WarehouseLocationService {

    /**
     * 确认库存转移
     *
     * @param dto
     */
    @Override
    public void confirmStockMaterialTransfer(MaterialTransferStockUpdateDto dto) {
        List<String> idsList = Arrays.stream(dto.getUIds().split(",")).collect(Collectors.toList());
        List<WarehouseLocation> list = baseMapper.selectBatchIds(idsList);
        list.forEach(warehouseLocation -> {
            if (dto.getQty().compareTo(warehouseLocation.getUQty()) > 0)
                throw new BusinessException("移动数量大于该位置数量");
            //移动
            doMaterialStock(warehouseLocation, dto);
            //原位置状态处理
            warehouseLocation.setUWzbs(dto.isWzbs() ? IntegrityMarkEnum.NONE : IntegrityMarkEnum.SECTION);
            warehouseLocation.setActive(dto.isWzbs() ? ValidityIndicatorEnum.INVALID : ValidityIndicatorEnum.VALID);
            warehouseLocation.setUQty(dto.isWzbs() ? BigDecimal.ZERO : warehouseLocation.getUQty().subtract(dto.getQty()));
            baseMapper.updateById(warehouseLocation);
        });
    }

    /**
     * 库存 转移动作
     *
     * @param ol        原位置对象
     * @param dto  更新参
     */
    private void doMaterialStock(WarehouseLocation ol, MaterialTransferStockUpdateDto dto) {
        WarehouseLocation nl = WarehouseLocation.builder()
                .ItemCode(ol.getItemCode())
                .DisNum(ol.getDisNum())
                .WhsCode(ol.getWhsCode())
                .uWzbs(dto.isWzbs() ? IntegrityMarkEnum.ALL : IntegrityMarkEnum.SECTION)
                .uQty(dto.getQty())
                .Active(ValidityIndicatorEnum.VALID)
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
     * 库存 批转移
     * @param dto
     */
    @Override
    public void confirmStockMaterialBatchTransfer(MaterialTransferBatchUpdateDto dto) {

    }
}
