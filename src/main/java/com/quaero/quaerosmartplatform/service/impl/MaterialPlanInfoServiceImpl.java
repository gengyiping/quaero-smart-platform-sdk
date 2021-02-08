package com.quaero.quaerosmartplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quaero.quaerosmartplatform.domain.dto.MaterialPlanListDto;
import com.quaero.quaerosmartplatform.domain.entity.MaterialPlanInfo;
import com.quaero.quaerosmartplatform.domain.mapper.MaterialPlanInfoMapper;
import com.quaero.quaerosmartplatform.domain.vo.MaterialPlanByDateListVo;
import com.quaero.quaerosmartplatform.domain.vo.MaterialPlanListVo;
import com.quaero.quaerosmartplatform.service.MaterialPlanInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021-01-13
 */
@Service
public class MaterialPlanInfoServiceImpl extends ServiceImpl<MaterialPlanInfoMapper, MaterialPlanInfo> implements MaterialPlanInfoService {

    @Override
    public List<MaterialPlanListVo> MATERIAL_PLAN_LIST_VOS(MaterialPlanListDto listDto) {
        return baseMapper.MATERIAL_PLAN_LIST_VOS(listDto);
    }

    @Override
    public List<MaterialPlanByDateListVo> MATERIAL_PLAN_BY_DATE_LIST_VOS(MaterialPlanListDto listDto) {
        return baseMapper.MATERIAL_PLAN_BY_DATE_LIST_VOS(listDto);
    }

    @Override
    public void materialPlanScheduled() {
        baseMapper.materialPlanScheduled();
    }
}
