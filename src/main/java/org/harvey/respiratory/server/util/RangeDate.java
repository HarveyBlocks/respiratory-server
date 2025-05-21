package org.harvey.respiratory.server.util;

import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-20 23:11
 */
@Data
public class RangeDate {
    /**
     * null for 不设限制
     */
    private final Date start;
    /**
     * null for 不设限制
     */
    private final Date end;
}
