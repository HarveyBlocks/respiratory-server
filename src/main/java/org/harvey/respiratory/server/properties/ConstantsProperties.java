package org.harvey.respiratory.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2024-02-04 17:28
 */
@Data
@ConfigurationProperties(prefix = "respiratory.constants")
public class ConstantsProperties {
    private String authorizationHeader;
    private String videoUploadDir;
    private String imageUploadDir;
    private String restrictRequestTimes;
    private String clearClickHistoryWaitSeconds;
    private String maxPageSize;
    private String defaultPageSize;
    private String redisHost;
    private String esHost;
    private String esPort;
    private List<String> sensitiveWords;
}
