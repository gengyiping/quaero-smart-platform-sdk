package com.quaero.quaerosmartplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quaero.quaerosmartplatform.domain.entity.Authority;
import com.quaero.quaerosmartplatform.domain.entity.UserAuthority;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-11
 */
public interface UserAuthorityService extends IService<UserAuthority> {

    List<Authority> getUserAuthority(String userCode);
}
