package org.harvey.respiratory.server.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * 金额(int/分)
 * 描述(varchar(255))
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-13 00:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_expenses_record")
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("费用产生记录")
public class ExpenseRecord {
    /**
     * 费用表id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "费用表id")
    private long id;
    /**
     * 就诊号/就诊表id
     */
    @ApiModelProperty(value = "就诊号/就诊表id")
    private long visitDoctorId;
    /**
     * 类别(varchar(63))
     */
    @ApiModelProperty(value = "类别(varchar(63))")
    private String category;

    /**
     * 金额/分(int)
     */
    @ApiModelProperty(value = "金额/分(int)")
    private int amount;

    /**
     * 描述(varchar(255))
     */
    @ApiModelProperty(value = "描述(varchar(255))")
    private String description;
}
