package edu.gdou.gym_java.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 赛事审核表
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-06-04
 */
@Getter
@Setter
@TableName("Competition_check")
@ApiModel(value = "CompetitionCheck对象", description = "赛事审核表")
public class CompetitionCheck implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("赛事id")
    @TableField("cid")
    private Integer cid;

    @ApiModelProperty("审核人")
    @TableField("uid")
    private Integer uid;

    @ApiModelProperty("审核理由")
    @TableField("reason")
    private String reason;

    @ApiModelProperty("审核状态")
    @TableField("status")
    private String status;

    @ApiModelProperty("创建审核时间")
    @TableField("create_time")
    private String createTime;

    @ApiModelProperty("审核时间")
    @TableField("check_time")
    private String checkTime;

    @TableField(exist = false)
    private Competition competition;


}
