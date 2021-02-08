package com.quaero.quaerosmartplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quaero.quaerosmartplatform.domain.dto.MaterialPlanUnpaidListDto;
import com.quaero.quaerosmartplatform.domain.dto.MaterialUnPlanListDto;
import com.quaero.quaerosmartplatform.domain.entity.POR1;
import com.quaero.quaerosmartplatform.domain.vo.MaterialPlanUnpaidListVo;
import com.quaero.quaerosmartplatform.domain.vo.MaterialUnPlanListVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021-01-18
 */
public interface POR1Service extends IService<POR1> {

    List<MaterialPlanUnpaidListVo> unpaidList(MaterialPlanUnpaidListDto materialPlanUnpaidListDto);

    List<MaterialUnPlanListVo> unPlanUnpaidList(MaterialUnPlanListDto dto);
}
