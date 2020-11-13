package com.quaero.quaerosmartplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quaero.quaerosmartplatform.domain.entity.User;
import com.quaero.quaerosmartplatform.domain.mapper.UserMapper;
import com.quaero.quaerosmartplatform.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
