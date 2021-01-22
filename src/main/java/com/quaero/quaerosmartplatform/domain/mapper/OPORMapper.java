package com.quaero.quaerosmartplatform.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quaero.quaerosmartplatform.domain.entity.OPOR;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021-01-18
 */
public interface OPORMapper extends BaseMapper<OPOR> {

    List<OPOR> oporList();
}
