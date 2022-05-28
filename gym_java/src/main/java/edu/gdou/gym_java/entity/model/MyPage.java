package edu.gdou.gym_java.entity.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MyPage<T> extends Page<T> {
    private static final long serialVersionUID = 1L;

    private Integer selectInt;
    private String selectStr;

    public MyPage(long current, long size) {
        super(current, size);
    }
}