package com.quaero.quaerosmartplatform.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quaero.quaerosmartplatform.domain.dto.MaterialPlanListDto;
import com.quaero.quaerosmartplatform.domain.entity.MaterialPlanInfo;
import com.quaero.quaerosmartplatform.domain.vo.MaterialPlanByDateListVo;
import com.quaero.quaerosmartplatform.domain.vo.MaterialPlanListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021-01-13
 */
public interface MaterialPlanInfoMapper extends BaseMapper<MaterialPlanInfo> {

    List<MaterialPlanListVo> MATERIAL_PLAN_LIST_VOS(@Param("listDto")MaterialPlanListDto listDto);

    List<MaterialPlanByDateListVo> MATERIAL_PLAN_BY_DATE_LIST_VOS(@Param("listDto")MaterialPlanListDto listDto);
}
