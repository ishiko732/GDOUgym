package edu.gdou.gym_java.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author ylhy
 * @since 2022-05-31
 */
@Getter
@Setter
@TableName("time_arrange")
@ApiModel(value = "TimeArrange对象", description = "")
public class TimeArrange implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("时间安排主键")
    @TableId(value="time_id",type = IdType.AUTO)
    private Integer timeId;

    @ApiModelProperty("开始时间")
    @TableField("start_time")
    private java.sql.Time startTime;

    @ApiModelProperty("结束时间")
    @TableField("end_time")
    private java.sql.Time endTime;

    @TableField("inode")
    private Integer inode;

    @ApiModelProperty("状态")
    @TableField("status")
    private String status;

    @TableField("fdid")
    private Integer fdid;




}
