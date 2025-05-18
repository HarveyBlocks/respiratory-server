package org.harvey.respiratory.server.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.harvey.respiratory.server.Constants;
import org.harvey.respiratory.server.dao.UserSecurityMapper;
import org.harvey.respiratory.server.exception.BadRequestException;
import org.harvey.respiratory.server.exception.ResourceNotFountException;
import org.harvey.respiratory.server.exception.UnauthorizedException;
import org.harvey.respiratory.server.pojo.dto.LoginFormDto;
import org.harvey.respiratory.server.pojo.dto.RegisterFormDto;
import org.harvey.respiratory.server.pojo.dto.UserDto;
import org.harvey.respiratory.server.pojo.entity.MedicalProvider;
import org.harvey.respiratory.server.pojo.entity.UserSecurity;
import org.harvey.respiratory.server.properties.JwtProperties;
import org.harvey.respiratory.server.service.MedicalProviderService;
import org.harvey.respiratory.server.service.UserSecurityService;
import org.harvey.respiratory.server.util.*;
import org.harvey.respiratory.server.util.identifier.IdentifierIdPredicate;
import org.springframework.aop.framework.AopContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-06-01 14:10
 */
@Service
public class UserSecurityServiceImpl extends ServiceImpl<UserSecurityMapper, UserSecurity> implements
        UserSecurityService {
    private final RedissonLock<UserDto> redissonLock;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private JwtTool jwtTool;
    @Resource
    private JwtProperties jwtProperties;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public UserSecurityServiceImpl(RedissonLock<UserDto> redissonLock) {
        this.redissonLock = redissonLock;
    }

    private static Map<String, String> user2Map(UserDto user) {
        return Map.of(
                "id", user.getId().toString(),
                "nickName", user.getName(),
                "role", user.getRole().toString(),
                TIME_FIELD, Constants.RESTRICT_REQUEST_TIMES
        );
    }

    @Override
    public String sendCode(String phone) {
        String code = null;
        if (RegexUtils.isPhoneEffective(phone)) {
            code = RandomUtil.randomNumbers(/*length*/ 6);
            // 发送短信验证码
            // 搞个假的
            log.debug("\n尊敬的" + phone + "用户:\n\t您的短信验证码是: " + code);
        }
        return code;
    }

    @Override
    public UserSecurity loginByCode(String codeCache, String phone, String code) {

        // code的长度已经正确,code不为null
        if (!code.equals(codeCache)) {
            return null;
        }

        // 如果验证码手机号一致, 去数据库查找用户
        UserSecurity user = selectByPhone(phone);

        // 判断用户是否存在
        if (user == null) {
            // 不存在就创建新用户并保存
            user = new UserSecurity();
            user.setPhone(phone);
            //newUser.setId()主键会自增, 不必管他
            // user.setIcon(UserSecurity.DEFAULT_ICON);//头像使用默认的
            user.setName(UserSecurity.DEFAULT_NAME);//昵称使用默认的
            // 随机生成或直接为null,为null就百分百无法通过密码登录了.
            // 随机可能被猜中?
            user.setPassword(null);
            user.setUpdateTime(LocalDateTime.now());
            //这里就先不要增改扰了人家数据库清静
            baseMapper.insert(user);
            // user为null, user的id怎么确认? 再查一次? 太反人类了吧
            user = selectByPhone(phone);
        }
        // log.debug(String.valueOf(user));
        // 返回user
        return user;
    }

    @Override
    public UserSecurity loginByPassword(String phone, String password) {
        if (!RegexUtils.isPasswordEffective(password)) {
            throw new BadRequestException("密码格式不正确,应是4~32位的字母、数字、下划线");
        }
        // 依据电话号码从service取数据
        UserSecurity user = selectByPhone(phone);
        // 取出来的数据和密码作比较
        if (user == null) {
            UserSecurity nullUser = new UserSecurity();
            nullUser.setId(-1L);
            return nullUser;//用户名不存在
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            // password经过检验, 非null, 数据库里的password可能是null
            throw new BadRequestException("用户名或密码错误");
        }
        // log.debug(String.valueOf(user));
        // 正确则返回user值
        return user;
    }

    @Override
    public String chooseLoginWay(LoginFormDto loginForm) {
        UserSecurity user /* = null*/;
        String phone = loginForm.getPhone();
        String code = loginForm.getCode();
        String password = loginForm.getPassword();
        if (!RegexUtils.isPhoneEffective(phone)
            // 网上说参数校验放在controller, 这算参数校验吗?
        ) {
            throw new UnauthorizedException("请正确输入电话号");
        }
        if ((password == null || password.isEmpty()) == (code == null || code.isEmpty())) {
            // 无法决定是密码登录还是验证码登录的情况
            throw new BadRequestException("请正确输入验证码或密码");
        }
        if (code != null && !code.isEmpty()) {
            if (code.length() != 6) {
                throw new BadRequestException("请输入正确格式的验证码");
            }
            // 使用验证码登录
            String codeCache = stringRedisTemplate.opsForValue().get(RedisConstants.LOGIN_CODE_KEY + phone);
            if (codeCache == null || codeCache.isEmpty()) {
                throw new BadRequestException("该手机未申请验证码, 请确认手机号或是否已经请求验证码");
            }
            user = this.loginByCode(codeCache, phone, code);
            if (user == null) {
                throw new BadRequestException("验证码不正确");
            } else {
                // 如果成功了, 就删除Redis缓存
                stringRedisTemplate.delete(RedisConstants.LOGIN_CODE_KEY + phone);
                // 否则不删除会话,给用户一个再次输入验证码的机会
            }
        } else /*if(password!=null&&!password.isEmpty())*/ {
            user = this.loginByPassword(phone, password);
            if (user == null) {
                throw new UnauthorizedException("密码不正确");
            } else if (user.getId().equals(-1L)) {
                throw new UnauthorizedException("该用户不存在");
            }
        }


        // session.setAttribute(Constants.USER_SESSION_KEY,new UserDto(user.getId(),user.getNickName(),user.getIcon()));
        // 将用户DTO存入Redis
        String token =// 生成随机Token,hutool工具包
                jwtTool.createToken(user.getId(), jwtProperties.getTokenTTL());//true表示不带中划线;


        saveToRedis(new UserDto(user), token);
        // 返回token
        return token;
    }

    @Override
    @Transactional
    public String register(RegisterFormDto registerForm) {
        UserSecurity registerUser = new UserSecurity();
        String phone = registerForm.getPhone();
        registerUser.setPhone(phone);
        registerUser.setPassword(passwordEncoder.encode(registerForm.getPassword()));
        registerUser.setName(registerForm.getName());
        boolean saved = save(registerUser);
        if (!saved) {
            log.error("保存失败错误");
            return null;
        }
        // 依据电话号码从service取数据
        UserSecurity user = selectByPhone(registerUser.getPhone());

        // 取出来的数据和密码作比较
        if (user == null) {
            return null;
        }

        // 将用户DTO存入Redis
        String token = jwtTool.createToken(user.getId(), jwtProperties.getTokenTTL());
        saveToRedis(new UserDto(user), token);
        return token;
    }

    @Resource
    private IdentifierIdPredicate identifierIdPredicate;
    @Resource
    private MedicalProviderService medicalProviderService;

    @Override
    @Transactional
    public void updateUser(UserDto userDTO, String token) {
        // 更新实体数据
        UserSecurity user = this.getById(UserHolder.currentUserId());
        String name = userDTO.getName();
        if (!StrUtil.isEmpty(name)) {
            user.setName(name);
        }


        String identityCardId = userDTO.getIdentityCardId();
        if (identityCardId != null && identifierIdPredicate.test(identityCardId)) {
            user.setIdentityCardId(identityCardId);
            MedicalProvider medicalProvider = medicalProviderService.selectByIdentityCardId(identityCardId);
            if (medicalProvider == null) {
                // 可能是患者了
                user.setRoleId(Constants.DEFAULT_USER_ROLE_AFTER_REAL_NAME);
            } else {
                // 医疗的id
                user.setRoleId(medicalProvider.getRoleId());
            }
        }

        user.setUpdateTime(LocalDateTime.now());
        // 更新

        String tokenKey = RedisConstants.QUERY_USER_KEY + jwtTool.parseToken(token);
        stringRedisTemplate.delete(tokenKey);
        // 更新数据库
        UserSecurityService userService = (UserSecurityService) AopContext.currentProxy();
        boolean update = userService.updateById(user);
        if (!update) {
            throw new BadRequestException("更新失败,无此用户");
        }
    }

    private void saveToRedis(UserDto userDTO, String token) {
        if (userDTO == null) {
            throw new BadRequestException("用户信息为null");
        }
        String tokenKey = RedisConstants.QUERY_USER_KEY + jwtTool.parseToken(token);
        saveUser2Redis(tokenKey, user2Map(userDTO), RedisConstants.QUERY_USER_TTL);
    }

    @Override
    public UserSecurity selectByPhone(String phone) {
        LambdaQueryWrapper<UserSecurity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select().eq(UserSecurity::getPhone, phone);
        return baseMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public UserSecurity selectByIdentityCardId(String identityCardId) {
        LambdaQueryWrapper<UserSecurity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select().eq(UserSecurity::getIdentityCardId, identityCardId);
        return baseMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public UserDto queryUserByIdWithRedisson(Long userId) throws InterruptedException {
        log.debug("queryMutexFixByLock");
        String key = RedisConstants.QUERY_USER_KEY + userId;
        // 从缓存查
        log.debug("用户:" + userId + "从缓存查");
        Map<Object, Object> userFieldMap = stringRedisTemplate.opsForHash().entries(key);
        if (userFieldMap.isEmpty()) {
            // Redis里没有数据
            log.debug("缓存不存在用户:" + userId);
            String lockKey = RedisConstants.USER_LOCK_KEY + UserHolder.currentUserId();
            return redissonLock.asynchronousLock(lockKey, () -> getFromDbAndWriteToCache(userId, key));
        } else if (((String) userFieldMap.get("id")).isEmpty()) {
            log.warn("Redis中存在的假数据" + userId);
            return null;
        }
        // 在Redis中有正常的数据
        // 第三个参数: 是否忽略转换过程中产生的异常
        userFieldMap.remove(UserSecurityService.TIME_FIELD);
        try {
            return BeanUtil.fillBeanWithMap(userFieldMap, new UserDto(), false);
        } catch (Exception e) {
            log.error("在转化UserFieldMap时出现错误错误" + userFieldMap);
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserSecurity selectById(long userId) {
        UserSecurity userSecurity = super.getById(userId);
        if (userSecurity == null) {
            throw new ResourceNotFountException("不能依据id " + userId + " 查询到用户");
        }
        return userSecurity;
    }

    /**
     * 解决穿透专用
     *
     * @param id  id
     * @param key key
     * @return shop
     */
    private UserDto getFromDbAndWriteToCache(Long id, String key) {
        // 缓存不存在
        // 使用缓存空对象的逻辑
        log.debug("getFromDbAndWriteToCache");
        UserDto userDTO = null;
        Long ttl = RedisConstants.CACHE_NULL_TTL;
        Map<String, String> userFieldMap = Map.of("id", "");
        // 数据库查
        log.debug("从数据库查用户:" + id);
        UserSecurity user = this.getById(id);
        if (user != null) {
            userDTO = new UserDto(user);
            // 存在,写入Cache,更改TTL
            log.debug("数据库中存在用户:" + id);
            userFieldMap = user2Map(userDTO);
            ttl = RedisConstants.QUERY_USER_TTL;
        } else {
            // 是虚假的用户,存入Redis,防止虚空索敌
            log.warn(id + "是虚假的用户,Redis中存入假数据中");
        }
        saveUser2Redis(key, userFieldMap, ttl);
        return userDTO;
    }

    private void saveUser2Redis(String key, Map<String, String> map2Redis, Long ttl) {
        stringRedisTemplate.opsForHash().putAll(key, map2Redis);
        if (-1 != ttl) {
            stringRedisTemplate.expire(key, plusRandomSec(ttl), TimeUnit.SECONDS);
        }
    }

    /**
     * 通过随机改变ttl, 防止雪崩
     *
     * @return 增加了随机值的ttl
     */
    private Long plusRandomSec(Long ttl) {
        long random;
        if (ttl <= 10L) {
            random = 0;
        } else {
            long exSec = ttl / 10;
            random = RandomUtil.randomLong(-exSec, exSec);
        }
        LocalDateTime time = LocalDateTime.now().plusSeconds(ttl + random);
        return time.toEpochSecond(ZoneOffset.of("+8"));
    }


}
