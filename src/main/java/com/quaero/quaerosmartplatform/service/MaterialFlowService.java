package com.quaero.quaerosmartplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quaero.quaerosmartplatform.domain.dto.MaterialTransferBatchUpdateDto;
import com.quaero.quaerosmartplatform.domain.dto.MaterialTransferNonStockUpdateDto;
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

    /**
     * 非库存转移
     * @param dto 非库存转移参
     */
    void confirmNonStockMaterialTransfer(MaterialTransferNonStockUpdateDto dto);

    /**
     * 非库存批转移
     * @param dto 非库存批转移参
     */
    void confirmNonStockMaterialBatchTransfer(MaterialTransferBatchUpdateDto dto);
}
