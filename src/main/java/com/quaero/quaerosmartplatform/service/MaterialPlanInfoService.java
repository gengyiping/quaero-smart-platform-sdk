package com.quaero.quaerosmartplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quaero.quaerosmartplatform.domain.dto.MaterialPlanListDto;
import com.quaero.quaerosmartplatform.domain.entity.MaterialPlanInfo;
import com.quaero.quaerosmartplatform.domain.vo.MaterialPlanByDateListVo;
import com.quaero.quaerosmartplatform.domain.vo.MaterialPlanListVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021-01-13
 */
public interface MaterialPlanInfoService extends IService<MaterialPlanInfo> {

    List<MaterialPlanListVo> MATERIAL_PLAN_LIST_VOS(MaterialPlanListDto listDto);

    List<MaterialPlanByDateListVo> MATERIAL_PLAN_BY_DATE_LIST_VOS(MaterialPlanListDto listDto);

    void materialPlanScheduled();
}
