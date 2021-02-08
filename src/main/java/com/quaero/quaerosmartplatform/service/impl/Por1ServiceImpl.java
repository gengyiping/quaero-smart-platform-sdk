package com.quaero.quaerosmartplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quaero.quaerosmartplatform.domain.dto.MaterialPlanUnpaidListDto;
import com.quaero.quaerosmartplatform.domain.dto.MaterialUnPlanListDto;
import com.quaero.quaerosmartplatform.domain.entity.POR1;
import com.quaero.quaerosmartplatform.domain.mapper.POR1Mapper;
import com.quaero.quaerosmartplatform.domain.vo.MaterialPlanUnpaidListVo;
import com.quaero.quaerosmartplatform.domain.vo.MaterialUnPlanListVo;
import com.quaero.quaerosmartplatform.service.POR1Service;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  采购订单行表服务实现类
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021-01-18
 */
@Service
public class Por1ServiceImpl extends ServiceImpl<POR1Mapper, POR1> implements POR1Service {

    @Override
    public List<MaterialPlanUnpaidListVo> unpaidList(MaterialPlanUnpaidListDto materialPlanUnpaidListDto) {
        return baseMapper.unpaidList(materialPlanUnpaidListDto);
    }

    @Override
    public List<MaterialUnPlanListVo> unPlanUnpaidList(MaterialUnPlanListDto dto) {
        return baseMapper.unPlanUnpaidList(dto);
    }
}
