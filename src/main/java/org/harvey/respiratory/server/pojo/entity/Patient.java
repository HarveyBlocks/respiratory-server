package org.harvey.respiratory.server.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.harvey.respiratory.server.pojo.enums.DoctorJob;
import org.harvey.respiratory.server.pojo.enums.Sex;

import java.sql.Date;

/**
 * 患者表(既往用药史查询特定药品使用方式中间表)
 * id
 * 电话(unique, char(11))
 * 姓名(varchar(64))
 * 性别(enum-male/female)
 * 出生日期(date)
 * 家庭住址(varchar(255))
 * 身高(m) (float)
 * 体重(kg) (float)
 * 医保表id
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-13 00:48
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_patient")
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    /**
     * 患者
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private long id;

    /**
     * 手机号码(unique, char(11))
     */
    private String phone;

    /**
     * 名称(varchar(63))
     */
    private String name;

    /**
     * 职称(enum-普通/主管/药物)
     */
    private Sex sex;
    /**
     * 医疗服务机构表id
     */
    private Date birthDate;
    /**
     * 家庭住址(varchar(255))
     */
    private Date address;
    /**
     * 身高(m) (float)
     */
    private Date height;

    /**
     * 体重(kg) (float)
     */
    private Date weight;
    /**
     * 医保表id
     */
    private int healthcareId;
}
