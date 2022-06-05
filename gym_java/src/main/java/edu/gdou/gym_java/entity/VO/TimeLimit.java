package edu.gdou.gym_java.entity.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class TimeLimit {
    Timestamp start;
    Timestamp end;
}
