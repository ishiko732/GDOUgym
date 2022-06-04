package edu.gdou.gym_java.service.serviceImpl;

import edu.gdou.gym_java.entity.model.*;
import edu.gdou.gym_java.mapper.FieldMapper;
import edu.gdou.gym_java.service.FieldService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ylhy
 * @since 2022-05-31
 */
@Service
public class FieldServiceImpl extends ServiceImpl<FieldMapper, Field> implements FieldService {

    private static final java.sql.Time[] time = new java.sql.Time[]{
            new Time(8,0,0),
            new Time(9,0,0),
            new Time(10,0,0),
            new Time(11,0,0),
            new Time(12,0,0),
            new Time(13,0,0),
            new Time(14,0,0),
            new Time(15,0,0),
            new Time(16,0,0),
            new Time(17,0,0),
            new Time(18,0,0),
            new Time(19,0,0),
            new Time(20,0,0),
            new Time(21,0,0),
            new Time(22,0,0)
    };



    @Override
    public List<Field> queryFieldByType(Integer tid ) {
        return getBaseMapper().queryFieldByType(tid);
    }

    @Override
    public void fill(FieldType fieldType) {



        List<Field> fieldList  = getBaseMapper().fillFieldInType(fieldType.getTid());
        fieldType.setFieldList(fieldList);


    }

    @Override
    public Field queryFieldById(Integer fid) {
        Field field = getBaseMapper().queryFieldById(fid);
        if (field!=null){
            field.setFieldType(getBaseMapper().queryTypeById(field.getTid()));
        }
        return field;
    }

    @Override
    public List<FieldDate> search(Integer fid,java.sql.Date date) {
        Field field = getBaseMapper().queryFieldById(fid);
        List<FieldDate> fieldDateList = new ArrayList<>();

        for (int i = 0;i < field.getNum(); i++) {
            //初始化
            FieldDate fieldDate = new FieldDate();
            fieldDate.setInode(i);
            fieldDate.setDate(date);
            fieldDate.setField(field);
          List<FieldDate>  result = getBaseMapper().queryDateByField(fid,date,i);
                    if (result.size()>0) {
                    int id = result.get(0).getId();
                    fieldDate.setId(id);
                    fieldDate.setTimeArrangeList(list(id));
                } else {
                        getBaseMapper().addDate(fieldDate);
                        fieldDate.setTimeArrangeList(list(fieldDate.getId()));
                }

            fieldDateList.add(fieldDate);
        }
        return fieldDateList;
    }

    List<TimeArrange> list(Integer fdid){
        List<TimeArrange> beans = new ArrayList<>();
        List<TimeArrange> results =  getBaseMapper().queryTimeByFdId(fdid);

            int j = -1;
            if (results.size()>0){
                j = results.get(0).getInode();
            }
            for (int i = 0; i < 14; i++) {
                TimeArrange timeArrange = new TimeArrange();
                timeArrange.setStartTime(time[i]);
                timeArrange.setEndTime(time[i + 1]);
                timeArrange.setStatus("空闲");
                timeArrange.setInode(i);
                timeArrange.setFdid(fdid);
                if (i == j) {
                    int id = results.get(i).getTimeId();
                    timeArrange.setTimeId(id);
                    timeArrange.setStatus(results.get(i).getStatus());
                    if (results.size()>0){
                        j ++;
                    }
                } else {
                    getBaseMapper().addTime(timeArrange);
                }
                beans.add(timeArrange);
            }
        return beans;
    }

    @Override
    public List<FieldType> queryType() {
        return getBaseMapper().queryType();
    }

    @Override
    public FieldType queryTypeById(Integer tid) {
        return getBaseMapper().queryTypeById(tid);
    }

    @Override
    public boolean addField(Field field) {
        return getBaseMapper().addField(field);
    }

    @Override
    public boolean updateField(Field field) {
        return getBaseMapper().updateField(field);
    }

    @Override
    public boolean updateStatus(Integer time_id, String status) {
        return getBaseMapper().updateStatus(time_id,status);
    }

    @Override
    public Boolean addCheck(FieldCheck fieldCheck) {
        return getBaseMapper().addCheck(fieldCheck);
    }

    @Override
    public TimeArrange queryTimeById(Integer timeId) {
        return getBaseMapper().queryTimeById(timeId);
    }

    @Override
    public Boolean addOrderItem(OrderItem orderItem) {
        return getBaseMapper().addOrderItem(orderItem);
    }

    @Override
    public FieldCheck queryCheckById(Integer id) {
        return getBaseMapper().queryCheckById(id);
    }

    @Override
    public Boolean updateCheck(FieldCheck fieldCheck) {
        return getBaseMapper().updateCheck(fieldCheck);
    }

    @Override
    public List<OrderItem> queryOrderItemByFcid(Integer id) {
        return getBaseMapper().queryOrderItemByFcid(id);
    }



    @Override
    public List<FieldCheck> queryCheck() {
        return getBaseMapper().queryCheck();
    }

    @Override
    public List<FieldCheck> queryCheckByUid(Integer uid) {
        return getBaseMapper().queryCheckByUid(uid);
    }

    @Override
    public Boolean updateCheckById(FieldCheck fieldCheck) {
        return getBaseMapper().updateCheckById(fieldCheck);
    }



}
