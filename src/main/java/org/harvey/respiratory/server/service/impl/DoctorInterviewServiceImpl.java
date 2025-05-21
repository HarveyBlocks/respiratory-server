package org.harvey.respiratory.server.service.impl;

import org.harvey.respiratory.server.exception.BadRequestException;
import org.harvey.respiratory.server.exception.ServerException;
import org.harvey.respiratory.server.exception.UnauthorizedException;
import org.harvey.respiratory.server.pojo.dto.InterviewDto;
import org.harvey.respiratory.server.pojo.entity.*;
import org.harvey.respiratory.server.pojo.enums.Role;
import org.harvey.respiratory.server.service.*;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 医生就诊
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-21 15:28
 */
@Service
public class DoctorInterviewServiceImpl implements DoctorInterviewService {

    @Resource
    private DiseaseService diseaseService;
    @Resource
    private VisitDoctorService visitDoctorService;

    @Resource
    private DrugService drugService;

    @Resource
    private MedicalProviderService medicalProviderService;
    @Resource
    private MedicalProviderJobService medicalProviderJobService;
    @Resource
    private MedicalProviderDepartmentService medicalProviderDepartmentService;
    @Resource
    private RoleService roleService;


    @Resource
    private ExpenseRecordService expenseRecordService;

    @Resource
    private SpecificUsingDrugRecordService specificUsingDrugRecordService;

    @Resource
    private SymptomaticPresentationService symptomaticPresentationService;

    @Resource
    private DiseaseDiagnosisIntermediationService diseaseDiagnosisIntermediationService;

    private static DoctorInterviewService currentProxy() {
        return (DoctorInterviewService) AopContext.currentProxy();
    }

    @Override
    public void interview(InterviewDto interviewDto, String identityCardId) {
        Long visitDoctorId = interviewDto.getVisitDoctorId();
        List<ExpenseRecord> expenseRecordList = new ArrayList<>();
        if (visitDoctorId == null) {
            throw new BadRequestException("需要visit doctor id");
        }
        VisitDoctor inDb = visitDoctorService.queryById(visitDoctorId);
        if (inDb.isInterviewed()) {
            // 已经完成了问诊了
            throw new BadRequestException("请不要重复提交问诊结果");
        }
        // 获取数据库中病患id和医生id
        Long patientId = inDb.getPatientId();
        Long medicalProviderId = inDb.getMedicalProviderId();
        // 查询出就诊医生的具体信息, 医生职位和所在科室有关费用
        MedicalProvider medicalProvider = medicalProviderService.queryById(medicalProviderId);
        // 这个工作一定要问诊的这个医生做
        validInterviewRole(medicalProvider.getIdentityCardId(), identityCardId);
        // 添加医生的有关的费用
        createMedicalProviderExpenseRecord(medicalProvider, visitDoctorId, expenseRecordList);
        // 映射症状dto->entity
        List<SymptomaticPresentation> symptomaticPresentationList = getSymptomaticPresentationList(
                interviewDto.getSymptomaticPresentationList(), visitDoctorId);
        // 取出药物使用dto
        List<InterviewDto.SpecificUsingDrugRecordDto> usingDrugDto = interviewDto.getSpecificUsingDrugRecordDtoList();
        if (usingDrugDto == null) {
            throw new BadRequestException("usingDrugDto 不能为null");
        }
        // 从药物使用中取出药物id,查询出药物单价, 与药物数量相乘, 并生成费用记录
        createDrugExpenseRecord(usingDrugDto, visitDoctorId, expenseRecordList);
        // 生成药物具体使用的记录, 数据库entity
        List<SpecificUsingDrugRecord> usingDrugRecords = getSpecificUsingDrugRecords(
                usingDrugDto, visitDoctorId, patientId);
        // 将所有费用记录相加, 获取总费用
        int totalPrice = summarizeExpense(expenseRecordList);
        // 如果没有概述, 就依据确证的病症生成概述
        String briefDescription = getBriefDescription(interviewDto);
        // 生成visit doctor 实体
        VisitDoctor visitDoctor = interviewDto.buildVisitDoctor(briefDescription, totalPrice);
        // 药品ids
        List<Integer> diseaseIds = interviewDto.getDiseaseIds();
        // 接下来是事务, 插入
        currentProxy().transitionallySaveRecords(
                visitDoctor.getId(), expenseRecordList, usingDrugRecords, symptomaticPresentationList, diseaseIds);
    }


    /**
     * 这个工作一定要问诊的这个医生做
     */
    private void validInterviewRole(String medicalProviderIdentityCardId, String identityCardId) {
        if (medicalProviderIdentityCardId.equals(identityCardId)) {
            Role role = roleService.queryRole(identityCardId);
            switch (role) {
                case UNKNOWN:
                    throw new UnauthorizedException("未知用户");
                case PATIENT:
                    throw new UnauthorizedException("患者没有添加信息的权限");
                case NORMAL_DOCTOR:
                    throw new UnauthorizedException("只有此次问诊的目标医生有权限进行问诊");
                case CHARGE_DOCTOR:
                case MEDICATION_DOCTOR:
                case DEVELOPER:
                case DATABASE_ADMINISTRATOR:
                    break;
                default:
                    throw new ServerException("Unexpected role value: " + role);
            }
        }
    }


    /**
     * 添加医生的有关的费用
     */
    private void createMedicalProviderExpenseRecord(
            MedicalProvider medicalProvider, Long visitDoctorId, List<ExpenseRecord> expenseRecordList) {
        // 5.2 计算此次问诊的医生费用, 并生成费用记录
        Integer jobId = medicalProvider.getJobId();
        MedicalProviderJob job = medicalProviderJobService.queryById(jobId);
        ExpenseRecord expenseRecordOnJob = new ExpenseRecord(null, visitDoctorId, "医生职位问诊费用",
                job.getExpenseEveryVisit(), 1, job.getName()
        );
        expenseRecordList.add(expenseRecordOnJob);
        Integer departmentId = medicalProvider.getDepartmentId();
        MedicalProviderDepartment department = medicalProviderDepartmentService.queryById(departmentId);
        ExpenseRecord expenseRecordOnDepartment = new ExpenseRecord(null, visitDoctorId, "医生科室问诊费用",
                department.getExpenseEveryVisit(), 1, department.getName()
        );
        expenseRecordList.add(expenseRecordOnDepartment);
    }

    /**
     * 映射症状dto->entity
     */
    private static List<SymptomaticPresentation> getSymptomaticPresentationList(
            List<InterviewDto.SymptomaticPresentationDto> symptomaticPresentationDtos, Long visitDoctorId) {
        if (symptomaticPresentationDtos == null) {
            throw new BadRequestException("symptomaticPresentationList 不能为null");
        }
        return symptomaticPresentationDtos.stream()
                .map(sp -> sp.buildSymptomaticPresentation(visitDoctorId))
                .collect(Collectors.toList());
    }


    /**
     * 从药物使用中取出药物id,查询出药物单价, 与药物数量相乘, 并生成费用记录
     */
    private void createDrugExpenseRecord(
            List<InterviewDto.SpecificUsingDrugRecordDto> usingDrugDto,
            Long visitDoctorId,
            List<ExpenseRecord> expenseRecordList) {
        Set<Integer> drugIds = usingDrugDto.stream()
                .map(InterviewDto.SpecificUsingDrugRecordDto::getDrugId)
                .collect(Collectors.toSet());
        Map<Integer, Drug> drugMap = drugService.queryByIds(drugIds);
        for (InterviewDto.SpecificUsingDrugRecordDto dto : usingDrugDto) {
            // 从药物使用中取出药物id, 查询出药物单价, 与药物数量相乘, 并生成费用记录
            Integer drugId = dto.getDrugId();
            Integer count = dto.getCount();
            Drug drug = drugMap.get(drugId);
            ExpenseRecord expenseRecordOnDrug = new ExpenseRecord(null, visitDoctorId, "药品费用",
                    drug.getExpenseEach(), count, drug.getName()
            );
            expenseRecordList.add(expenseRecordOnDrug);
        }
    }


    /**
     * 生成药物具体使用的记录, 数据库entity
     */
    private static List<SpecificUsingDrugRecord> getSpecificUsingDrugRecords(
            List<InterviewDto.SpecificUsingDrugRecordDto> usingDrugDto, Long visitDoctorId, Long patientId) {
        return usingDrugDto.stream()
                .map(dto -> dto.buildSpecificUsingDrugRecord(visitDoctorId, patientId))
                .collect(Collectors.toList());
    }


    /**
     * 将所有费用记录相加, 获取总费用
     */
    private static int summarizeExpense(List<ExpenseRecord> expenseRecordList) {
        return (int) expenseRecordList.stream()
                .collect(Collectors.summarizingInt(r -> r.getAmount() * r.getCount()))
                .getSum();
    }

    /**
     * 如果没有概述, 就依据确证的病症生成概述
     */
    private String getBriefDescription(InterviewDto interviewDto) {
        String briefDescription = interviewDto.getBriefDescription();
        if (briefDescription != null) {
            return briefDescription;
        }
        // 查询疾病名字, 然后自动生成
        List<String> diseaseNames = diseaseService.queryDiseaseNameByIds(interviewDto.getDiseaseIds());
        return InterviewDto.joinBriefDescription(diseaseNames);
    }

    @Transactional
    @Override
    public void transitionallySaveRecords(
            long visitDoctorId,
            List<ExpenseRecord> expenseRecordList,
            List<SpecificUsingDrugRecord> usingDrugRecords,
            List<SymptomaticPresentation> symptomaticPresentationList,
            List<Integer> diseaseIds) {
        VisitDoctor visitDoctor = visitDoctorService.queryById(visitDoctorId);
        if (visitDoctor.isInterviewed()) {
            // 另一条线程已经完成, 故跳出
            return;
        }
        // 依据id更新数据库就诊信息, visit doctor 实体
        visitDoctorService.updateAfterInterview(visitDoctor);
        // 插入费用记录
        expenseRecordService.saveOnInterview(expenseRecordList);
        // 插入具体药物使用usingDrugRecords(多个)
        specificUsingDrugRecordService.saveSymptomaticPresentationBatch(usingDrugRecords);
        // 依次插入症状信息(多个)
        symptomaticPresentationService.saveSymptomaticPresentationBatch(symptomaticPresentationList);
        // 插入疾病-就诊中间表(多个)
        diseaseDiagnosisIntermediationService.saveOnInterview(visitDoctor.getId(), diseaseIds);
    }

}
