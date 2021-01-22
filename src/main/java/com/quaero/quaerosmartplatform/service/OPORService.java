package com.quaero.quaerosmartplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quaero.quaerosmartplatform.domain.entity.OPOR;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021-01-18
 */
public interface OPORService extends IService<OPOR> {

    List<OPOR> oporList();
}
