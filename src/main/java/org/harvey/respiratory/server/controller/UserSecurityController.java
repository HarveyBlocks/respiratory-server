package org.harvey.respiratory.server.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.Constants;
import org.harvey.respiratory.server.exception.BadRequestException;
import org.harvey.respiratory.server.exception.ResourceNotFountException;
import org.harvey.respiratory.server.exception.UnauthorizedException;
import org.harvey.respiratory.server.pojo.dto.LoginFormDto;
import org.harvey.respiratory.server.pojo.dto.RegisterFormDto;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.UserSecurity;
import org.harvey.respiratory.server.pojo.enums.Role;
import org.harvey.respiratory.server.pojo.vo.NullPlaceholder;
import org.harvey.respiratory.server.pojo.vo.Result;
import org.harvey.respiratory.server.properties.ConstantsProperties;
import org.harvey.respiratory.server.service.UserSecurityService;
import org.harvey.respiratory.server.util.RedisConstants;
import org.harvey.respiratory.server.util.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-06-01 14:09
 */
@Slf4j
@RestController
@Api(tags = "用户登录校验")
@RequestMapping("/user")
// @EnableConfigurationProperties(ConstantsProperties.class)
public class UserSecurityController {
    @Resource
    private UserSecurityService userSecurityService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ConstantsProperties constantsProperties;

    /**
     * 发送手机验证码
     */
    @PostMapping("/code")
    @ApiOperation("发送验证码")
    public Result<NullPlaceholder> sendCode(@RequestBody @ApiParam("phone") String phone) {
        // 发送短信验证码并保存验证码
        String code = userSecurityService.sendCode(phone);
        if (code == null) {
            throw new BadRequestException("手机号不合法");
        }

        //session.setAttribute(CODE_SESSION_KEY,code);
        //session.setAttribute(PHONE_SESSION_KEY,phone);
        // 记得设置有效期
        stringRedisTemplate.opsForValue()
                .set(RedisConstants.LOGIN_CODE_KEY + phone, code, RedisConstants.LOGIN_CODE_TTL, TimeUnit.MINUTES);

        return Result.ok();
    }

    /**
     * 登录功能
     *
     * @param loginForm 登录参数，包含手机号、验证码；或者手机号、密码
     */
    @PostMapping("/login")
    @ApiOperation("登录")
    public Result<NullPlaceholder> login(
            @RequestBody @ApiParam("需要用户登录的Json,密码和验证码二选一") LoginFormDto loginForm,
            HttpServletResponse response) {
        //实现登录功能
        // System.out.println(result);
        if (UserHolder.getUser() != null) {
            return new Result<>(400, "请不要重复登录");
        }
        String token = userSecurityService.chooseLoginWay(loginForm);
        response.setHeader(Constants.AUTHORIZATION_HEADER, token);
        return Result.ok();
    }

    /**
     * 注册功能
     *
     * @param registerForm 注册参数，包含手机号、密码
     */
    @PostMapping("/register")
    @ApiOperation("注册")
    public Result<NullPlaceholder> register(
            @RequestBody @ApiParam("需要用户注册的Json,使用密码") RegisterFormDto registerForm,
            HttpServletResponse response) {
//        System.err.println("hi");
        //实现注册功能
        UserSecurity user = userSecurityService.selectByPhone(registerForm.getPhone());
        if (user != null) {
            return new Result<>(400, "用户已存在");
        }
        String token = userSecurityService.register(registerForm);
        if (token == null) {
            return new Result<>(500, "保存失败");
        }
        response.setHeader(Constants.AUTHORIZATION_HEADER, token);
        return Result.ok();
    }

    /**
     * 登出功能
     *
     * @return 无
     */
    @ApiOperation("登出")
    @PostMapping("/logout")
    public Result<NullPlaceholder> logout(HttpServletResponse response) {
        String tokenKey = RedisConstants.QUERY_USER_KEY + UserHolder.currentUserId();
        stringRedisTemplate.delete(tokenKey);
        response.setStatus(401);
        return Result.ok();
    }

    @ApiOperation("获取当前登录的用户并返回")
    @GetMapping("/me")
    public Result<UserDto> me() {
        // 获取当前登录的用户并返回
        return new Result<>(UserHolder.getUser());
    }

    /**
     * UserController 根据id查询用户
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询用户")
    public Result<UserDto> queryUserById(@PathVariable("id") Long userId) {
        UserDto userDTO;
        try {
            userDTO = userSecurityService.queryUserByIdWithRedisson(userId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (userDTO == null) {
            throw new ResourceNotFountException("用户" + userId + "不存在");
        }
        return new Result<>(userDTO);

    }

    @GetMapping("/create")
    @ApiOperation(value = "测试用接口,生成虚假的User", notes = "生成100个虚假的用户,存入Redis")
    public Result<UserDto> createUser() {
        for (int i = 0; i < 1; i++) {
            Map<String, String> map = new HashMap<>();
            int token = i + 10000;
            System.out.println(token);
            String key = RedisConstants.QUERY_USER_KEY + token;
            map.put(UserSecurityService.ID_FIELD, String.valueOf(token));
            map.put(UserSecurityService.NAME_FIELD, UserSecurity.DEFAULT_NAME);
            stringRedisTemplate.opsForHash().putAll(key, map);
        }
        return new Result<>(
                new UserDto(Role.UNKNOWN, 1L, "name", "350121200410080032"/*TODO 测试数据不好, 因为验证位没有被验证*/));
    }

    @ApiOperation(value = "更新用户信息", notes = "没有更新的部分就传null或空字符串,不用传ID")
    @PutMapping("/update")
    public Result<NullPlaceholder> update(
            @RequestBody @ApiParam("需要用户注册的Json,使用密码") UserDto userDTO,
            @ApiParam(hidden = true) HttpServletRequest request) {
        // 删除原有头像
        UserDto user = UserHolder.getUser();
        if (user == null) {
            throw new UnauthorizedException("未知的角色");
        }
        Role role = user.getRole();

        // 怎么查询权限有没有呢? 当前用户是不是TODO
        if (role.ordinal() >= Role.CHARGE_DOCTOR.ordinal()) {
            throw new UnauthorizedException("没有更新的权限");
        }

        userSecurityService.updateUser(userDTO, request.getHeader(Constants.AUTHORIZATION_HEADER));
        return Result.ok();
    }


}