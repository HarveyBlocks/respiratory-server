package org.harvey.respiratory.server.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 药品表
 * id
 * 名称(varchar(63))
 * 规格(varchar(63))
 * 给药途径(varchar(63))
 * 用药部位(varchar(63))
 * 用药注意事项(varchar(255))
 * 用药指导(varchar(255))
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-13 00:16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_drug")
@NoArgsConstructor
@AllArgsConstructor
public class Drug {
    /**
     * 药品id, 不是雪花, 因为不是高并发, 也不怕被猜到id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    /**
     * 药物名称(varchar(63))
     */
    private String name;
    /**
     * 药物规格(varchar(63))
     */
    private String specification;
    /**
     * 给药途径(varchar(63))
     */
    private String administrationRoute;
    /**
     * 用药部位(varchar(63))
     */
    private String medicationSite;
    /**
     * 用药注意事项(varchar(255))
     */
    private String medicationPrecaution;
    /**
     * 用药指导(varchar(255))
     */
    private String guidance;
}
