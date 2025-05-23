package org.harvey.respiratory.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import lombok.NonNull;
import org.harvey.respiratory.server.Constants;
import org.harvey.respiratory.server.pojo.dto.LoginFormDto;
import org.harvey.respiratory.server.pojo.dto.RegisterFormDto;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.UserSecurity;
import org.springframework.transaction.annotation.Transactional;
import reactor.util.annotation.Nullable;

/**
 * 用户业务的实现
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-06-01 14:10
 */
public interface UserSecurityService extends IService<UserSecurity> {
    String TIME_FIELD = Constants.TIME_FIELD;
    String ID_FIELD = "id";
    String NAME_FIELD = "name";

    /**
     * 生成校验码并发送
     *
     * @param phone 手机号
     * @return 校验码
     */
    @Nullable
    String sendCode(String phone);

    @NonNull
    UserSecurity selectByPhone(String phone);

    /**
     * 用验证码登录验证
     *
     * @param codeCache 会话保存的验证码
     * @param phone     新请求的手机号
     * @param code      新请求的验证码
     * @return 用户信息
     */
    @NonNull
    UserSecurity loginByCode(String codeCache, String phone, String code);

    /**
     * 校验用户名密码
     *
     * @param phone    电话号码, 也做账号
     * @param password 密码
     * @return 若返回id为-1的则为不存在用户,<br>
     * 若返回null则为用户名密码错误,<br>
     * 否则返回正确查到的用户<br>
     */
    @NonNull
    UserSecurity loginByPassword(String phone, String password);


    @NonNull
    String chooseLoginWay(LoginFormDto loginForm);

    @NonNull
    String register(RegisterFormDto registerForm);

    @Transactional
    void updateUser(UserDto userDTO, String token);

    @NonNull
    UserSecurity selectByIdentityCardId(String identityCardId);

    @Nullable
    UserDto queryUserByIdWithRedisson(Long userId) throws InterruptedException;


    @NonNull
    UserSecurity selectById(long userId);
}
