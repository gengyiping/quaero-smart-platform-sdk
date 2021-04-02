package com.quaero.quaerosmartplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quaero.quaerosmartplatform.domain.dto.MaterialPlanUnpaidListDto;
import com.quaero.quaerosmartplatform.domain.dto.MaterialUnPlanListDto;
import com.quaero.quaerosmartplatform.domain.entity.OWOR;
import com.quaero.quaerosmartplatform.domain.mapper.OWORMapper;
import com.quaero.quaerosmartplatform.domain.vo.MaterialPlanUnpaidListVo;
import com.quaero.quaerosmartplatform.domain.vo.MaterialUnPlanListVo;
import com.quaero.quaerosmartplatform.service.OWORService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021-01-18
 */
@Service
public class OWORServiceImpl extends ServiceImpl<OWORMapper, OWOR> implements OWORService {

    @Override
    public List<MaterialPlanUnpaidListVo> unpaidList(MaterialPlanUnpaidListDto materialPlanUnpaidListDto) {
        return baseMapper.unpaidList(materialPlanUnpaidListDto);
    }

    @Override
    public List<MaterialUnPlanListVo> unPlanUnpaidList(MaterialUnPlanListDto dto) {
        return baseMapper.unPlanUnpaidList(dto);
    }
}
