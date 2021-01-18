package com.quaero.quaerosmartplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quaero.quaerosmartplatform.domain.entity.Authority;
import com.quaero.quaerosmartplatform.domain.entity.UserAuthority;
import com.quaero.quaerosmartplatform.domain.mapper.UserAuthorityMapper;
import com.quaero.quaerosmartplatform.service.UserAuthorityService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-11
 */
@Service
public class UserAuthorityServiceImpl extends ServiceImpl<UserAuthorityMapper, UserAuthority> implements UserAuthorityService {

    @Override
    public List<Authority> getUserAuthority(String userCode) {
        return baseMapper.getUserAuthority(userCode);
    }
}
