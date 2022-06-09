package edu.gdou.gym_java.entity.VO;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("referee_announcements")
public class RefereeAnnouncement {

    @TableField("cid")
    private Integer cid;

    @TableId("cfId")
    private Integer cfId;

    @TableField("fcId")
    private Integer fcId;

    @TableField("uid")
    private Integer uid;

    @TableField("judgment")
    private String judgment;

    @TableField("competition_name")
    private String competitionName;

    @TableField("field_name")
    private String fieldName;

    @TableField("introduction")
    private String introduction;

    @TableField("starttime")
    private String startTime;

    @TableField("endtime")
    private String endTime;
}
