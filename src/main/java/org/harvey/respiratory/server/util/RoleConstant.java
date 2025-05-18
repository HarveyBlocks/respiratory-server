package org.harvey.respiratory.server.util;

import org.harvey.respiratory.server.pojo.enums.Role;

/**
 * TODO
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-18 19:54
 */
public class RoleConstant {
    /**
     * 医疗机构相关的写权限
     */
    public static final Role[] MEDICAL_PROVIDER_UPDATE = {Role.CHARGE_DOCTOR, Role.DEVELOPER,
            Role.DATABASE_ADMINISTRATOR};
    public static final Role[] MEDICAL_PROVIDER_READ = {Role.NORMAL_DOCTOR, Role.MEDICATION_DOCTOR,
            Role.CHARGE_DOCTOR};
}
