package edu.gdou.gym_java.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@ToString
@TableName("field_date")
@ApiModel(value = "FieldDate对象", description = "")
public class FieldDate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("inode")
    private Integer inode;

    @TableField("date")
    private java.sql.Date date;

    @TableField(exist = false)
    private Field field;

    @TableField(exist = false)
    private List<TimeArrange> timeArrangeList;   //场地状态,包含所有安排表项

}
