package org.harvey.respiratory.server.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 疾病表
 *
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-12 23:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_disease")
@NoArgsConstructor
@AllArgsConstructor
public class Disease {
    /**
     * primary key
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
}
