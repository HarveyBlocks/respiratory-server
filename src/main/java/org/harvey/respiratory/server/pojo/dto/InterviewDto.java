package org.harvey.respiratory.server.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.harvey.respiratory.server.pojo.entity.SymptomaticPresentation;
import org.harvey.respiratory.server.pojo.enums.Severity;

import java.sql.Date;
import java.util.List;

/**
 * TODO
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-14 13:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "医生问诊信息")
public class InterviewDto {

    @ApiModelProperty(value = "就诊号/就诊表id", required = true)
    private long visitDoctorId;

    @ApiModelProperty(value = "症状表, 可以为empty, 不可为null", required = true)
    private List<SymptomaticPresentation> symptomaticPresentationList;

    @ApiModelProperty(value = "药物具体使用表, 可以为empty, 不可为null", required = true)
    private List<SpecificUsingDrugsIntermediationDto> specificUsingDrugsIntermediations;
    @ApiModelProperty(value = "疾病诊断, 可以为empty, 不可为null", required = true)
    private List<Long> diseaseIds;
    @ApiModelProperty("简要介绍(varchar(255)), 如果医生不提供, 就是病症名的list")
    private String briefDescription;
    @ApiModelProperty("其他辅助治疗")
    private String otherAdjuvantTherapy;


    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(description = "药物具体使用")
    public static class SpecificUsingDrugsIntermediationDto {

        @ApiModelProperty("药品表id, 指向药品表中的药品")
        private long drugId;

        @ApiModelProperty("药品数量, 和费用计算相关")
        private Integer count;

        @ApiModelProperty("使用剂量(varchar(63)), 由于单位不确定, 所以是字符串")
        private String dosageUsed;

        @ApiModelProperty("使用天数(int)")
        private Integer dayTimeUsed;

        @ApiModelProperty("使用频率(varchar(63)), 同样不知道单位")
        private String frequencyUsed;

        @ApiModelProperty("治疗开始时间(date)")
        private Date treatStart;

        @ApiModelProperty("治疗结束时间(date)")
        private Date treatEnd;


        @ApiModelProperty("其他用药指导(TEXT)")
        private String otherMedicationGuidance;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(description = "症状")
    public static class SymptomaticPresentationDto {

        @ApiModelProperty("名称(varchar(63))")
        private String name;

        @ApiModelProperty("严重程度(enum-轻度/中度/重度)")
        private Severity severity;

        @ApiModelProperty("频率(varchar(63)), 由于不知道单位")
        private String frequency;

        @ApiModelProperty("开始时间(date)")
        private Date startTime;

        @ApiModelProperty("诱因(varchar(63))")
        private String incentive;

        @ApiModelProperty("环境因素(varchar(63))")
        private String environmentalFactor;

        @ApiModelProperty("体征描述(varchar(63))")
        private String signDescription;

        @ApiModelProperty("描述(TEXT)")
        private String description;
    }
}
