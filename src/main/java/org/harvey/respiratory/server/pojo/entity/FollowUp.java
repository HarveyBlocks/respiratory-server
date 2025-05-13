package org.harvey.respiratory.server.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 随访表
 * id
 * 过敏症改善情况(varchar(255))
 * 身体发育情况(varchar(255))
 * 药物不良反应(varchar(255))
 * 不良反应处理方法(varchar(255))
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-13 00:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_follow_up")
@NoArgsConstructor
@AllArgsConstructor
public class FollowUp {
    /**
     * 随访表id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private long id;


    /**
     * 就诊号, 冗余字段, 但是为了提高效率
     */
    private long visitDoctorId;
    /**
     * 过敏症改善情况(varchar(255))
     */
    private String allergiesImprove;
    /**
     * 身体发育情况(varchar(255))
     */
    private String physicalDevelopment;

    /**
     * 药物不良反应(varchar(255))
     */
    private String adverseDrugReaction;

    /**
     * 不良反应处理方法(varchar(255))
     */
    private String adverseReactionTreatment;
}
