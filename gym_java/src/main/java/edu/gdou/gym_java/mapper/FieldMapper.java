package edu.gdou.gym_java.mapper;

import edu.gdou.gym_java.entity.model.Field;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.gdou.gym_java.entity.model.FieldDate;
import edu.gdou.gym_java.entity.model.FieldType;
import edu.gdou.gym_java.entity.model.TimeArrange;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ylhy
 * @since 2022-05-31
 */
@Mapper
public interface FieldMapper extends BaseMapper<Field> {

    List<Field> queryFieldByType(Integer tid);

    Field queryFieldById(Integer fid);

    List<FieldDate> queryDateByField(Integer fid, java.sql.Date date, int inode);


    void addDate(FieldDate fieldDate);

    List<TimeArrange> queryTimeByFdId(Integer fdid);

    void addTime(TimeArrange timeArrange);


    List<FieldType> queryType();

    FieldType queryTypeById(Integer tid);
}
