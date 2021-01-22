package com.quaero.quaerosmartplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quaero.quaerosmartplatform.domain.entity.OPOR;
import com.quaero.quaerosmartplatform.domain.mapper.OPORMapper;
import com.quaero.quaerosmartplatform.service.OPORService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuhanzhang@
 * @since 2021-01-18
 */
@Service
public class OPORServiceImpl extends ServiceImpl<OPORMapper, OPOR> implements OPORService {

    @Override
    public List<OPOR> oporList() {
        return baseMapper.oporList();
    }
}
