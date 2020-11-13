package com.quaero.quaerosmartplatform.domain.dto;

import lombok.Data;

/**
 * @since wuhanzhang
 * @since 2020/11/11
 */
@Data
public class LoginUser {

    private String username;
    private String password;
    private Integer rememberMe;
}
