package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.harvey.respiratory.server.dao.MedicalProviderJobMapper;
import org.harvey.respiratory.server.exception.BadRequestException;
import org.harvey.respiratory.server.exception.DaoException;
import org.harvey.respiratory.server.exception.ResourceNotFountException;
import org.harvey.respiratory.server.pojo.entity.MedicalProvider;
import org.harvey.respiratory.server.pojo.entity.MedicalProviderJob;
import org.harvey.respiratory.server.service.MedicalProviderJobService;
import org.harvey.respiratory.server.service.MedicalProviderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 12:38
 * @see MedicalProviderJob
 * @see MedicalProviderJobMapper
 * @see MedicalProviderJobService
 */
@Service
public class MedicalProviderJobServiceImpl extends ServiceImpl<MedicalProviderJobMapper, MedicalProviderJob> implements
        MedicalProviderJobService {
    @Override
    public Integer register(MedicalProviderJob job) {
        boolean saved = super.save(job);
        if (saved) {
            log.debug("成功保存职业");
            return job.getId();
        } else {
            throw new DaoException(DaoException.Operation.SAVE_FAIL, "未能保存职业, 未知原因");
        }
    }

    @Override
    public void update(MedicalProviderJob newJob) {
        boolean updated = super.updateById(newJob);
        if (updated) {
            log.debug("更新职业成功");
        } else {
            throw new DaoException(DaoException.Operation.UPDATE_FAIL, "未能更新职业, 未知原因");
        }
    }

    @Override
    public void delete(long id) {
        boolean deleted = super.removeById(id);
        if (deleted) {
            log.debug("删除职业成功");
        } else {
            // TODO 其实可能是因为并发环境下同时删除产生的重复删的问题
            throw new DaoException(DaoException.Operation.DELETE_FAIL, "未能删除职业, 未知原因");
        }
    }


    @Resource
    private MedicalProviderService medicalProviderService;

    @Override
    public MedicalProviderJob querySelf(long userId) {
        MedicalProvider medicalProvider = medicalProviderService.selectByUser(userId);
        if (medicalProvider == null) {
            throw new ResourceNotFountException(
                    "不能" + userId + "通过的身份证查询到医疗提供者信息");
        }
        Integer jobId = medicalProvider.getJobId();
        return queryById(jobId);
    }

    @Override
    public List<MedicalProviderJob> queryAny(Page<MedicalProviderJob> page) {
        Page<MedicalProviderJob> jobPage = super.lambdaQuery().page(page);
        return jobPage.getRecords();
    }

    @Override
    public MedicalProviderJob queryById(int jobId) {
        MedicalProviderJob one = super.getById(jobId);
        if (one == null) {
            throw new ResourceNotFountException("不能通过id " + jobId + " 查询到医疗提供职业的信息");
        }
        return one;
    }

    @Override
    public List<MedicalProviderJob> queryByName(String name) {
        if (name.isEmpty()) {
            throw new BadRequestException("职业退化成全查, 这不好");
        }
        // TODO 还得是倒排索引
        return super.lambdaQuery().like(MedicalProviderJob::getName, name).list();
    }

}