package org.harvey.respiratory.server.pojo.doc;


import lombok.Data;
import org.harvey.respiratory.server.pojo.entity.Video;
import org.harvey.respiratory.server.util.TimeUtil;

/**
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2024-02-06 15:06
 */
@Data
public class VideoDoc {
    private Long id;
    private Long userId;
    private String icon;
    private String nickName;
    private String title;
    private String videoPath;
    private Long click;
    private Integer comments;
    private Long createTime;

    public VideoDoc() {
    }

    public VideoDoc(Video video) {
        this.id = video.getId();
        this.userId = video.getUserId();
        this.icon = video.getIcon();
        this.nickName = video.getNickName();
        this.title = video.getTitle();
        this.videoPath = video.getVideoPath();
        this.click = video.getClick();
        this.comments = video.getComments();
        this.createTime = TimeUtil.toMillion(video.getCreateTime());
    }
}
