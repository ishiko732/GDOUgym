package edu.gdou.gym_java.entity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 赛事表
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-06-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("Competition")
@ApiModel(value = "Competition对象", description = "赛事表")
public class Competition implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("赛事id")
    @TableId("id")
    private Integer id;

    @ApiModelProperty("申请人Id")
    @TableField("uid")
    private Integer uid;

    @ApiModelProperty("赛事名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("赛事时间")
    @TableField("competition_time")
    private Timestamp competitionTime;

    @ApiModelProperty("赛事时长，按分钟计算")
    @TableField("event_length")
    private Integer eventLength;

    @ApiModelProperty("赛事简介")
    @TableField("introduction")
    private String introduction;

    @ApiModelProperty("赛事费用")
    @TableField("money")
    private Double money;

    @ApiModelProperty("赛事时间")
    @TableField("create_time")
    private Timestamp createTime;

    @ApiModelProperty("赛事场地")
    @TableField(exist = false)
    private Set<CompetitionField> competitionFields;

    @ApiModelProperty("赛事审核状态")
    @TableField(exist = false)
    private Boolean isCheck;

    @ApiModelProperty("赛事取消状态")
    @TableField(exist = false)
    private Boolean isCancel;
}
