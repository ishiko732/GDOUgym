package edu.gdou.gym_java.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

/**
 * <p>
 * 赛事场地表
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-06-04
 */
@Getter
@Setter
@TableName("Competition_field")
@ApiModel(value = "CompetitionField对象", description = "赛事场地表")
public class CompetitionField implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("赛事场地Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("赛事id")
    @TableField("cid")
    private Integer cid;

    @ApiModelProperty("场地审核id")// 通过场地审核id，找到场地和对应时间
    @TableField("fcId")
    private Integer fcId;

    @ApiModelProperty("裁判id")
    @TableField("uid")
    private Integer uid;

    @ApiModelProperty("裁判简介")
    @TableField("introduction")
    private String introduction;

    @ApiModelProperty("赛事器材")
    @TableField(exist = false)
    private Set<CompetitionEquipment> competitionEquipments;

    @ApiModelProperty("场地")
    @TableField(exist = false)
    private Field field;

    // 通过场地审核fcId -> 到order_item(fcId)找到tid-> time_arrange(tid) 找到start,endTime
    @ApiModelProperty("场地开始使用时间")
    @TableField(exist = false)
    private String startTime;

    @ApiModelProperty("场地结束使用时间")
    @TableField(exist = false)
    private String endTime;

    @ApiModelProperty("赛事场地更新时间")
    @TableField("time")
    private String time;

    @TableField(exist = false)
    private String name;

    @ApiModelProperty("场地审核状态")
//    @TableField("fieldStatus")
    @TableField(exist = false)
    private String fieldStatus;


}
