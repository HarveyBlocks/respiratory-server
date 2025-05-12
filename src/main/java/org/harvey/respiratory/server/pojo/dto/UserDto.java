package org.harvey.respiratory.server.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.harvey.respiratory.server.pojo.entity.UserSecurity;
import org.harvey.respiratory.server.pojo.enums.Role;

import java.io.Serializable;

/**
 * 用户简要信息
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2024-02-01 14:13
 */
@Data
@AllArgsConstructor
@ApiModel(description = "简单的用户信息")
public class UserDto implements Serializable {
    @ApiModelProperty(value = "用户权限",example = "UNKNOWN for 未知, DEVELOPER for 开发者")
    private Role role;
    @ApiModelProperty("用户主键")
    private Long id;
    @ApiModelProperty("昵称")
    private String name;

    public UserDto() {
    }
    public UserDto(UserSecurity userSecurity) {
        this.role = userSecurity.getRole();
        this.id = userSecurity.getId();
        this.name = userSecurity.getName();
    }
}
