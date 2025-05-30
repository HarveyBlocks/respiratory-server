package org.harvey.respiratory.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import lombok.NonNull;
import org.harvey.respiratory.server.pojo.dto.QueryBalanceDto;
import org.harvey.respiratory.server.pojo.entity.Healthcare;

/**
 * 医保
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 22:37
 */
public interface HealthcareService extends IService<Healthcare> {
    /**
     * @param healthcare balance 强制为0
     */
    void register(Healthcare healthcare);

    @NonNull
    Healthcare queryByCode(String healthcareCode);

    @NonNull

    Healthcare queryById(long healthcareId);

    @NonNull
    Healthcare query(QueryBalanceDto queryBalanceDto);

    void updateBalance(long healthcareId, int newBalance);
}
