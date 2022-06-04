package edu.gdou.gym_java.entity.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Init_Competition {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("赛事id")
    @TableField("cid")
    private Integer cid;

    public Init_Competition(Integer cid) {
        this.cid = cid;
    }
}
