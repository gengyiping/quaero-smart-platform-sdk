package com.quaero.quaerosmartplatform.controller;

import com.quaero.quaerosmartplatform.annotations.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 登录
 * </p>
 *
 * @author wuhanzhang@
 * @since 2020-11-03
 */
@RestController
@ResponseResult
@Api(tags = "登录相关接口")
@RequestMapping("/api")
public class LoginController {

    @PostMapping("/login")
    @ApiOperation(value = "登入身份验证", notes = "登入")
    public void login(String username, String password) {
        // TODO 这里面不需要写任何代码，由UserDeatilsService去处理
    }

    @GetMapping("/logout")
    @ApiOperation(value = "登出", notes = "登出")
    public void logout() {
        // TODO 这里面不需要写任何代码，由UserDeatilsService去处理
    }
}
