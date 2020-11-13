package com.quaero.quaerosmartplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quaero.quaerosmartplatform.domain.entity.Location;
import com.quaero.quaerosmartplatform.domain.mapper.LocationMapper;
import com.quaero.quaerosmartplatform.service.LocationService;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-11
 */
@Service
public class LocationServiceImpl extends ServiceImpl<LocationMapper, Location> implements LocationService {

}
