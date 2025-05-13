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
 * 费用产生表
 * 费用表id
 * 就诊号/就诊表id
 * 类别(varchar(63))
 * 金额(float)
 * 描述(varchar(255))
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-13 00:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_expenses")
@NoArgsConstructor
@AllArgsConstructor
public class Expenses {
    /**
     * 费用表id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private long id;
    /**
     * 就诊号/就诊表id
     */
    private long visitDoctorId;
    /**
     * 类别(varchar(63))
     */
    private String category;

    /**
     * 金额(float)
     */
    private float amount;

    /**
     * 描述(varchar(255))
     */
    private float description;
}
