package com.quaero.quaerosmartplatform.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quaero.quaerosmartplatform.domain.dto.MaterialPlanUnpaidListDto;
import com.quaero.quaerosmartplatform.domain.dto.MaterialUnPlanListDto;
import com.quaero.quaerosmartplatform.domain.entity.POR1;
import com.quaero.quaerosmartplatform.domain.vo.MaterialPlanUnpaidListVo;
import com.quaero.quaerosmartplatform.domain.vo.MaterialUnPlanListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021-01-18
 */
public interface POR1Mapper extends BaseMapper<POR1> {

    List<MaterialPlanUnpaidListVo> unpaidList(@Param("listDto") MaterialPlanUnpaidListDto listDto);

    List<MaterialUnPlanListVo> unPlanUnpaidList(@Param("listDto") MaterialUnPlanListDto listDto);

}
