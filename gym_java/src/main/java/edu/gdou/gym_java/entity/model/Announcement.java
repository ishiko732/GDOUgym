package edu.gdou.gym_java.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 公告表
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-06-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("Announcement")
@ApiModel(value = "Announcement对象", description = "公告表")
public class Announcement implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("公告id")
    @TableId(value = "aid", type = IdType.AUTO)
    private Integer aid;

    @ApiModelProperty("操作者")
    @TableField("uid")
    private Integer uid;

    @ApiModelProperty("公告类型")
    @TableField("type")
    private String type;

    @ApiModelProperty("公告内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("发布时间")
    @TableField("createDate")
    private String createDate;

    @ApiModelProperty("修改时间")
    @TableField("updateDate")
    private String updateDate;


}
