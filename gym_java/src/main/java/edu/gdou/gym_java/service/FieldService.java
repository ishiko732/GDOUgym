package edu.gdou.gym_java.service;

import edu.gdou.gym_java.entity.model.Field;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.gdou.gym_java.entity.model.FieldDate;
import edu.gdou.gym_java.entity.model.FieldType;
import edu.gdou.gym_java.entity.model.TimeArrange;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ylhy
 * @since 2022-05-31
 */
public interface FieldService extends IService<Field> {

    List<Field> queryFieldByType(Integer valueOf);

    void fill(FieldType fieldType);

    Field queryFieldById(Integer valueOf);

    List<FieldDate> search(Integer fid, java.sql.Date date);

    List<FieldType> queryType();

    FieldType queryTypeById(Integer valueOf);

}
