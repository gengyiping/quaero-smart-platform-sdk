package com.quaero.quaerosmartplatform.domain.bo;

import com.quaero.quaerosmartplatform.domain.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 实现 UserDetails 接口，扩展属性
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SecurityUserDetails extends User implements UserDetails {

    private static final long serialVersionUID = -9005214545793249372L;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getUPassword();
    }

    @Override
    public String getUsername() {
        return super.getCode();
    }

    public SecurityUserDetails(User user, Collection<? extends GrantedAuthority> authorities){
        this.authorities = authorities;
        this.setName(user.getName());
        this.setUPassword(user.getUPassword());
        this.setCode(user.getCode());
        //String encode = new BCryptPasswordEncoder().encode("123456");
        //this.setAuthorities(authorities);
    }

    /**
     * 账户是否过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 是否禁用
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否启用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
