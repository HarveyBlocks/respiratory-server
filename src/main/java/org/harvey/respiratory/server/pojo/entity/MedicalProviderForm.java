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
 * 医疗服务机构表
 * id
 * 名称(varchar(63))
 * 地址(varchar(255))
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-13 00:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_medical_provider_form")
@NoArgsConstructor
@AllArgsConstructor
public class MedicalProviderForm {
    /**
     * 医疗服务机构表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private long id;

    /**
     * 名称(varchar(63))
     */
    private String phone;

    /**
     * 地址(varchar(255))
     */
    private String address;

}
