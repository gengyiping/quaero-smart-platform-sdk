package com.quaero.quaerosmartplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quaero.quaerosmartplatform.domain.dto.MaterialTransferBatchUpdateDto;
import com.quaero.quaerosmartplatform.domain.dto.MaterialTransferStockUpdateDto;
import com.quaero.quaerosmartplatform.domain.entity.WarehouseLocation;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-03
 */
public interface WarehouseLocationService extends IService<WarehouseLocation> {

    /**
     * 库存转移
     * @param dto 库存转移参
     */
    void confirmStockMaterialTransfer(MaterialTransferStockUpdateDto dto);

    /**
     * 库存批转移
     * @param dto 库存转移参
     */
    void confirmStockMaterialBatchTransfer(MaterialTransferBatchUpdateDto dto);
}
