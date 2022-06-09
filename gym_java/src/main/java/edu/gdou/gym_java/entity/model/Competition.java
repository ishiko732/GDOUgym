package edu.gdou.gym_java.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("申请人Id")
    @TableField("uid")
    private Integer uid;

    @ApiModelProperty("赛事名称")
    @NotNull(message = "赛事名称不能未空")
    @TableField("name")
    private String name;

    @ApiModelProperty("赛事时间")
    @NotNull(message = "赛事时间不能未空")
    @TableField("competition_time")
    private String competitionTime;

    @ApiModelProperty("赛事时长，按分钟计算")
    @NotNull(message = "赛事时长不能未空")
    @Min(value = 0)
    @TableField("event_length")
    private Integer eventLength;

    @ApiModelProperty("赛事简介")
    @TableField("introduction")
    private String introduction;

    @ApiModelProperty("赛事费用")
    @Min(value = 0)
    @TableField("money")
    private Double money;

    @ApiModelProperty("赛事时间")
    @TableField("create_time")
    private String createTime;

    @ApiModelProperty("赛事场地")
    @TableField(exist = false)
    private Set<CompetitionField> competitionFields;

    @ApiModelProperty("赛事审核状态")
    @TableField(exist = false)
    private String isCheck;

    @ApiModelProperty("赛事取消状态")
    @TableField(exist = false)
    private Boolean isCancel;
}
