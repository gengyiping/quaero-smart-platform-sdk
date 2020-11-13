package com.quaero.quaerosmartplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quaero.quaerosmartplatform.domain.dto.MaterialTransferUpdateDto;
import com.quaero.quaerosmartplatform.domain.entity.MaterialFlow;
import com.quaero.quaerosmartplatform.domain.enumeration.DocTypeEnum;
import com.quaero.quaerosmartplatform.domain.enumeration.IntegrityMarkEnum;
import com.quaero.quaerosmartplatform.domain.enumeration.ResultCode;
import com.quaero.quaerosmartplatform.domain.enumeration.ValidityIndicatorEnum;
import com.quaero.quaerosmartplatform.domain.mapper.MaterialFlowMapper;
import com.quaero.quaerosmartplatform.exceptions.ParameterInvalidException;
import com.quaero.quaerosmartplatform.service.MaterialFlowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

;
;
;import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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
public class MaterialFlowServiceImpl extends ServiceImpl<MaterialFlowMapper, MaterialFlow> implements MaterialFlowService {

    @Override
    public void confirmMaterialTransfer(MaterialTransferUpdateDto dto) {
        if (dto.getDoctype().equals(DocTypeEnum.INSPECTION_FORM.getValue())) {

        }
        MaterialFlow search = MaterialFlow.builder()
                .uItemcode(dto.getItemCode())
                .uDisnum(dto.getDisNum())
                .uDoctype(dto.getDoctype())
                .uBaseentry(dto.getBaseEntry())
                .uActive(ValidityIndicatorEnum.VALID)
                .build();
        //原位置对象
        MaterialFlow ol = baseMapper.selectOne(new QueryWrapper<>(search));
        if (dto.getQty().compareTo(ol.getUQty()) > 0) {
            throw new ParameterInvalidException(ResultCode.PARAM_IS_INVALID, "移动数量大于该位置库存");
        }
        //todo 位置逻辑判断
        //新位置对象
        baseMapper.insert(MaterialFlow.builder()
                .uItemcode(dto.getItemCode())
                .uDisnum(dto.getDisNum())
                .uBaseentry(dto.getBaseEntry())
                .uWzbs(dto.getQty().compareTo(ol.getUQty()) == 0 ? IntegrityMarkEnum.ALL : IntegrityMarkEnum.SECTION)
                .uQty(dto.getQty())
                .uActive(ValidityIndicatorEnum.VALID)
                .uCardcode(null)
                .uCreator("1111")
                .uDocdate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .build());
        //原位置处理
        ol.setUWzbs(dto.getQty().compareTo(ol.getUQty()) == 0 ? IntegrityMarkEnum.NONE : IntegrityMarkEnum.SECTION);
        ol.setUActive(dto.getQty().compareTo(ol.getUQty()) == 0 ? ValidityIndicatorEnum.INVALID : ValidityIndicatorEnum.VALID);
        ol.setUQty(ol.getUQty().subtract(dto.getQty()));
        ol.setUCreator("1111");
        ol.setUDocdate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        baseMapper.update(ol, new UpdateWrapper<>(search));
    }
}
