package com.quaero.quaerosmartplatform.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quaero.quaerosmartplatform.domain.entity.Authority;
import com.quaero.quaerosmartplatform.domain.entity.UserAuthority;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-11
 */
public interface UserAuthorityMapper extends BaseMapper<UserAuthority> {

    List<Authority> getUserAuthority(String userCode);
}
