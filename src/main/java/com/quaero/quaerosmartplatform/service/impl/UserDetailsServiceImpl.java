package com.quaero.quaerosmartplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quaero.quaerosmartplatform.domain.bo.SecurityUserDetails;
import com.quaero.quaerosmartplatform.domain.entity.User;
import com.quaero.quaerosmartplatform.domain.entity.UserAuthority;
import com.quaero.quaerosmartplatform.domain.enumeration.ValidityEnum;
import com.quaero.quaerosmartplatform.service.AuthorityService;
import com.quaero.quaerosmartplatform.service.UserAuthorityService;
import com.quaero.quaerosmartplatform.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * 获取用户相关信息
 * @author wuhanzhang
 *
 */
@Log4j2
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthorityService userAuthorityService;
    @Autowired
    private AuthorityService authorityService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        // 根据用户名查找用户
        User user = userService.getOne(new QueryWrapper<>(User.builder().Code(name).build()));
        if (user == null) {
            // 实际当用户不存在时，应该页面显示错误信息，并跳转到登录界面
            throw new UsernameNotFoundException("该用户不存在！");
        }
        //根据用户id获取用户权限 003_1
        List<UserAuthority> userAuthorities = userAuthorityService.list(
                new QueryWrapper<>(UserAuthority.builder().Code(user.getCode()).uCk(ValidityEnum.VALID).build()));
        // 填充权限
        Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (UserAuthority userAuthority : userAuthorities) {
            authorities.add(new SimpleGrantedAuthority(userAuthority.getUName()));
        }
        //填充权限菜单
        //List<Authority> menus=authorityService.getRoleMenuByRoles(roles);

        user.setUPassword(new BCryptPasswordEncoder().encode(user.getUPassword()));
        return new SecurityUserDetails(user,authorities);
    }
}
