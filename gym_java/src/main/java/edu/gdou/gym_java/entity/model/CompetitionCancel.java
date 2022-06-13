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
import lombok.*;

/**
 * <p>
 * 赛事取消
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-06-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("Competition_cancel")
@ApiModel(value = "CompetitionCancel对象", description = "赛事取消")
public class CompetitionCancel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("赛事id")
    @TableField("cid")
    private Integer cid;

    @ApiModelProperty("取消的用户")
    @TableField("uid")
    private Integer uid;

    @ApiModelProperty("取消的理由")
    @TableField("reasons")
    private String reasons;

    @ApiModelProperty("取消的时间")
    @TableField("cancellation_time")
    private String cancellationTime;


}
