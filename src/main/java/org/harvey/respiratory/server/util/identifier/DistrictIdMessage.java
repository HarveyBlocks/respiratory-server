package org.harvey.respiratory.server.util.identifier;

import lombok.Getter;

/**
 * 身份证中的区
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-16 13:01
 */
@Getter
public class DistrictIdMessage {
    String code;
    String address;

    public DistrictIdMessage(String code, String address) {
        this.code = code;
        this.address = address;
    }
}
