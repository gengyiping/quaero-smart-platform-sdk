package com.quaero.quaerosmartplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quaero.quaerosmartplatform.domain.dto.MaterialTransferBatchUpdateDto;
import com.quaero.quaerosmartplatform.domain.dto.MaterialTransferStockUpdateDto;
import com.quaero.quaerosmartplatform.domain.entity.WarehouseLocation;
import com.quaero.quaerosmartplatform.domain.enumeration.IntegrityMarkEnum;
import com.quaero.quaerosmartplatform.domain.enumeration.ValidityIndicatorEnum;
import com.quaero.quaerosmartplatform.domain.mapper.WarehouseLocationMapper;
import com.quaero.quaerosmartplatform.exceptions.BusinessException;
import com.quaero.quaerosmartplatform.service.UserService;
import com.quaero.quaerosmartplatform.service.WarehouseLocationService;
import com.quaero.quaerosmartplatform.utils.Constants;
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
@Transactional
@RequiredArgsConstructor
public class WarehouseLocationServiceImpl extends ServiceImpl<WarehouseLocationMapper, WarehouseLocation> implements WarehouseLocationService {

    @NonNull
    private UserService userService;

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
            //移动
            doMaterialStock(warehouseLocation, dto.isWzbs(), dto.getQty(), dto.getTLocation());
        });
    }

    /**
     * 库存 批转移
     * @param dto
     */
    @Override
    public void confirmStockMaterialBatchTransfer(MaterialTransferBatchUpdateDto dto) {
        List<WarehouseLocation> list = baseMapper.selectList(new QueryWrapper<>(WarehouseLocation.builder()
                .uYdwz(dto.getLocation()).Active(ValidityIndicatorEnum.VALID).build()));
        list.forEach(warehouseLocation -> {
            doMaterialStock(warehouseLocation, true, warehouseLocation.getUQty(),dto.getTargetLocation());
        });
    }

    /**
     * 库存 转移动作
     *
     * @param ol        原位置对象
     * @param wzbs      完整标识
     * @param qty       数量
     * @param tl        目标位置
     */
    private void doMaterialStock(WarehouseLocation ol, boolean wzbs, BigDecimal qty, String tl) {
        if (qty.compareTo(ol.getUQty()) > 0)
            throw new BusinessException("移动数量大于该位置数量");
        WarehouseLocation nl = WarehouseLocation.builder()
                .ItemCode(ol.getItemCode())
                .DisNum(ol.getDisNum())
                .WhsCode(ol.getWhsCode())
                .uWzbs(wzbs ? IntegrityMarkEnum.ALL : IntegrityMarkEnum.SECTION)
                .uQty(qty)
                .Active(ValidityIndicatorEnum.VALID)
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
        ol.setActive(wzbs ? ValidityIndicatorEnum.INVALID : ValidityIndicatorEnum.VALID);
        ol.setUQty(wzbs ? BigDecimal.ZERO : ol.getUQty().subtract(qty));
        baseMapper.updateById(ol);
    }
}
