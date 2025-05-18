package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.harvey.respiratory.server.dao.HealthcareMapper;
import org.harvey.respiratory.server.pojo.entity.Healthcare;
import org.harvey.respiratory.server.service.HealthcareService;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 22:38
 * @see Healthcare
 * @see HealthcareMapper
 * @see HealthcareService
 */
@Slf4j
@Service
public class HealthcareServiceImpl extends ServiceImpl<HealthcareMapper, Healthcare> implements HealthcareService {

    @Override
    public void register(Healthcare healthcare) {
        // 强制设置为0, 防止请求不好的东西
        healthcare.setBalance(0);
        boolean saved = this.save(healthcare);
        if (saved) {
            log.debug("成功添加医保号{}", healthcare.getHealthcareId());
        } else {
            log.error("添加医保号{}失败. ", healthcare.getHealthcareId());
        }
    }

    @Override
    public Healthcare queryByCode(long healthcareCode) {
        Healthcare one = super.lambdaQuery().eq(Healthcare::getHealthcareCode, healthcareCode).one();
        if (one == null) {
            log.warn("依据医保号{}未查询到医保", healthcareCode);
            return null;
        }
        return one;
    }

    @Override
    public Healthcare queryById(long healthcareId) {
        Healthcare healthcare = super.getById(healthcareId);
        if (healthcare == null) {
            log.warn("依据医保id{}未查询到医保", healthcareId);
            return null;
        }
        return healthcare;
    }
}