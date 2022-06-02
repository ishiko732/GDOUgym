package edu.gdou.gym_java.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
@TableName("field_check")
@ApiModel(value = "FieldCheck对象", description = "")
public class FieldCheck implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("审核表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("提交时间")
    @TableField("time")
    private LocalDateTime time;

    @ApiModelProperty("费用")
    @TableField("money")
    private Integer money;

    @ApiModelProperty("审核状态")
    @TableField("status")
    private String status;

    @ApiModelProperty("审核标题")
    @TableField("name")
    private String name;

    @TableField(exist = false)
    private Field field;

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private List<OrderItem> orderItemList;

}