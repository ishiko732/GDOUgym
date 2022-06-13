package edu.gdou.gym_java.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 赛事器材表
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-06-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("Competition_equipment")
@ApiModel(value = "CompetitionEquipment对象", description = "赛事器材表")
public class CompetitionEquipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("赛事场地id")
    @TableField("cfid")
    private Integer cfid;

    @ApiModelProperty("器材id")
    @TableField("eid")
    private Integer eid;

    @ApiModelProperty("器材数量")
    @TableField("number")
    private Integer number;
    // 不要审核器材，只要够数量就给


}
