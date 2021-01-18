package com.quaero.quaerosmartplatform.controller;

import com.quaero.quaerosmartplatform.annotations.ResponseResult;
import com.quaero.quaerosmartplatform.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
public class LoginController{

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

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

    @GetMapping("/home")
    @ApiOperation(value = "主界面", notes = "主界面权限等账号信息")
    public HomeInfo home(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> list = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : authorities) {
            list.add(grantedAuthority.getAuthority());
        }
        HomeInfo homeInfo = new HomeInfo();
        homeInfo.setList(list);
        homeInfo.setName(userService.getById(authentication.getName()).getName());
        return homeInfo;
    }

    @Data
    private static class HomeInfo {
        @ApiModelProperty(value = "用户名称")
        private String name;
        //@ApiModelProperty(value = "角色名称")
        //private String roleName;
        @ApiModelProperty(value = "权限界面")
        List<String> list;
    }
}
