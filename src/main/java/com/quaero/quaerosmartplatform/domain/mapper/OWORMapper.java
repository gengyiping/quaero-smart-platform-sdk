package com.quaero.quaerosmartplatform.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quaero.quaerosmartplatform.domain.dto.MaterialPlanUnpaidListDto;
import com.quaero.quaerosmartplatform.domain.dto.MaterialUnPlanListDto;
import com.quaero.quaerosmartplatform.domain.entity.OWOR;
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
public interface OWORMapper extends BaseMapper<OWOR> {

    List<MaterialPlanUnpaidListVo> unpaidList(@Param("listDto") MaterialPlanUnpaidListDto listDto);

    List<MaterialUnPlanListVo> unPlanUnpaidList(@Param("listDto") MaterialUnPlanListDto listDto);
}
