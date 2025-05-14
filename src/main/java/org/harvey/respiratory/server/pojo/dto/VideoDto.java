package org.harvey.respiratory.server.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.harvey.respiratory.server.pojo.entity.Video;

import java.io.Serializable;

/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2024-02-01 14:09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "视频简要信息")
public class VideoDto implements Serializable {

    @ApiModelProperty("作者昵称")
    private String nickName;

    @ApiModelProperty("视频标题")
    private String title;

    @ApiModelProperty("点击量")
    private Long click;

    @ApiModelProperty("评论量")
    private Integer comments;

    public VideoDto(Video video) {
        this.nickName = video.getNickName();
        this.title = video.getTitle();
        this.click = video.getClick();
        this.comments = video.getComments();
    }
}
