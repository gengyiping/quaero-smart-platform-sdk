package com.quaero.quaerosmartplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quaero.quaerosmartplatform.domain.dto.MaterialTransferUpdateDto;
import com.quaero.quaerosmartplatform.domain.entity.MaterialFlow;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-03
 */
public interface MaterialFlowService extends IService<MaterialFlow> {

    void confirmMaterialTransfer(MaterialTransferUpdateDto dto);
}
