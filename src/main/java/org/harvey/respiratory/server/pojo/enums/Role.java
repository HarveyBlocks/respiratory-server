package org.harvey.respiratory.server.pojo.enums;


import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;

/**
 * 角色(用于授权)
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2024-02-01 16:09
 */
@Getter
public enum Role implements IEnum<String> {

    UNKNOWN("未知"),
    PATIENT("患者"),
    NORMAL_DOCTOR("普通医生"),
    CHARGE_DOCTOR("主管医生"),
    MEDICATION_DOCTOR("药物医生"),
    DEVELOPER("开发者"),
    DATABASE_ADMINISTRATOR("数据库管理员");

    private final String comment;// description



    Role(String comment) {

        this.comment = comment;
    }

    public static Role create(Integer roleValue) {
        return Role.values()[roleValue];
    }


    @Override
    public String getValue() {
        return this.name();
    }
}
