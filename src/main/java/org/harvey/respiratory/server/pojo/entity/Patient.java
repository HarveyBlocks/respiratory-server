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
@ApiModel(description = "患者信息")
public class Patient {
    @ApiModelProperty(value = "新增时需要为null, 更新时不能为null, 就是依据id更新")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "手机号码(unique, char(11)), 不可为null", required = true)
    private String phone;


    @ApiModelProperty(value = "名称(varchar(63)), 不可为null", required = true)
    private String name;

    @ApiModelProperty(value = "职称(enum-普通/主管/药物), 不可为null", required = true)
    private Sex sex;

    @ApiModelProperty(value = "医疗服务机构表id, 不可为null", required = true)
    private Date birthDate;

    @ApiModelProperty(value = "家庭住址(varchar(255)), 不可为null", required = true)
    private Date address;

    @ApiModelProperty(value = "身高(m) (float), 不可为null", required = true)
    private Date height;

    @ApiModelProperty(value = "体重(kg) (float), 不可为null", required = true)
    private Date weight;

    @ApiModelProperty(value = "医保表id, 可以为null")
    private Long healthcareId;

    @ApiModelProperty(value = "医保类型, 可以为null")
    private String healthcareType;

    @ApiModelProperty(value = "医保余额")
    private Integer balance;
}
