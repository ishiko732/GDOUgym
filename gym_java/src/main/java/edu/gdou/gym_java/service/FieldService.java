package edu.gdou.gym_java.service;

import edu.gdou.gym_java.entity.model.*;
import com.baomidou.mybatisplus.extension.service.IService;

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

    boolean addField(Field field);

    boolean updateField(Field field);

    boolean updateStatus(Integer valueOf, String status);

    Boolean addCheck(FieldCheck fieldCheck);

    TimeArrange queryTimeById(Integer valueOf);

    Boolean addOrderItem(OrderItem orderItem);

    FieldCheck queryCheckById(Integer id);

    Boolean updateCheck(FieldCheck fieldCheck);

    List<OrderItem> queryOrderItemByFcid(Integer id);

    List<FieldCheck> queryCheck( );

    List<FieldCheck> queryCheckByUid(Integer uid);

    Boolean updateCheckById(FieldCheck fieldCheck);



}
