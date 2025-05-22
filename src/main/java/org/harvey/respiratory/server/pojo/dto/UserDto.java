package org.harvey.respiratory.server.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.harvey.respiratory.server.pojo.entity.UserSecurity;

import java.io.Serializable;

/**
 * 用户简要信息
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-06-01 14:13
 */
@Data
@AllArgsConstructor
@ApiModel(description = "简单的用户信息")
public class UserDto implements Serializable {
    @ApiModelProperty("用户主键. 对于更新, 用户只能更新自己. 所以更新的业务上这个字段没有意义")
    private Long id;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("用户身份证")
    private String identityCardId;

    public UserDto() {
    }

    public UserDto(UserSecurity userSecurity) {
        this.id = userSecurity.getId();
        this.name = userSecurity.getName();
        this.identityCardId = userSecurity.getIdentityCardId();
    }
}
