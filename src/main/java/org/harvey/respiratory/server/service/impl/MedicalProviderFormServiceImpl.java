package org.harvey.respiratory.server.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.harvey.respiratory.server.dao.MedicalProviderFormMapper;
import org.harvey.respiratory.server.exception.BadRequestException;
import org.harvey.respiratory.server.exception.DaoException;
import org.harvey.respiratory.server.exception.ResourceNotFountException;
import org.harvey.respiratory.server.pojo.entity.MedicalProvider;
import org.harvey.respiratory.server.pojo.entity.MedicalProviderForm;
import org.harvey.respiratory.server.service.MedicalProviderFormService;
import org.harvey.respiratory.server.service.MedicalProviderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 12:37
 * @see MedicalProviderForm
 * @see MedicalProviderFormMapper
 * @see MedicalProviderFormService
 */
@Service
public class MedicalProviderFormServiceImpl extends
        ServiceImpl<MedicalProviderFormMapper, MedicalProviderForm> implements MedicalProviderFormService {

    @Override
    public Integer register(MedicalProviderForm form) {
        boolean saved = super.save(form);
        if (saved) {
            log.debug("成功保存");
            return form.getId();
        } else {
            throw new DaoException(DaoException.Operation.SAVE_FAIL, "未能保存机构, 未知原因");
        }
    }

    @Override
    public void update(MedicalProviderForm newForm) {
        boolean updated = super.updateById(newForm);
        if (updated) {
            log.debug("更新成功");
        } else {
            throw new DaoException(DaoException.Operation.UPDATE_FAIL, "未能更新机构, 未知原因");
        }
    }

    @Override
    public void delete(long id) {
        boolean deleted = super.removeById(id);
        if (deleted) {
            log.debug("删除成功");
        } else {
            throw new DaoException(DaoException.Operation.DELETE_FAIL, "未能删除机构, 未知原因");
        }
    }


    @Resource
    private MedicalProviderService medicalProviderService;

    @Override
    public MedicalProviderForm querySelf(long userId) {
        MedicalProvider medicalProvider = medicalProviderService.selectByUser(userId);
        if (medicalProvider == null) {
            throw new ResourceNotFountException(
                    "不能" + userId + "通过的身份证查询到医疗提供者信息");
        }
        Integer formId = medicalProvider.getFormId();
        return queryById(formId);
    }

    @Override
    public List<MedicalProviderForm> queryAny(Page<MedicalProviderForm> page) {
        Page<MedicalProviderForm> formPage = super.lambdaQuery().page(page);
        return formPage.getRecords();
    }

    @Override
    public MedicalProviderForm queryById(int formId) {
        MedicalProviderForm one = super.getById(formId);
        if (one == null) {
            throw new ResourceNotFountException("不能通过id " + formId + " 查询到医疗提供机构的信息");
        }
        return one;
    }

    @Override
    public List<MedicalProviderForm> queryByName(String name) {
        if (name.isEmpty()) {
            throw new BadRequestException("机构退化成全查, 这不好");
        }
        // TODO 还得是倒排索引
        return super.lambdaQuery().like(MedicalProviderForm::getName, name).list();
    }

    @Override
    public List<MedicalProviderForm> queryByAddress(String address) {
        if (address.isEmpty()) {
            throw new BadRequestException("机构退化成全查, 这不好");
        }
        // TODO 还得是倒排索引
        return super.lambdaQuery().like(MedicalProviderForm::getAddress, address).list();
    }
}