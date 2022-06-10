package edu.gdou.gym_java.service.serviceImpl;

import edu.gdou.gym_java.entity.VO.FieldCheckVo;
import edu.gdou.gym_java.entity.model.*;
import edu.gdou.gym_java.mapper.FieldMapper;
import edu.gdou.gym_java.service.FieldService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gdou.gym_java.utils.TimeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.text.Format;
import java.text.ParseException;
import java.time.temporal.ChronoUnit;
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
@Transactional(rollbackFor = Exception.class)
public class FieldServiceImpl extends ServiceImpl<FieldMapper, Field> implements FieldService {

    private static final java.sql.Time[] time = new java.sql.Time[]{
            new Time(8, 0, 0),
            new Time(9, 0, 0),
            new Time(10, 0, 0),
            new Time(11, 0, 0),
            new Time(12, 0, 0),
            new Time(13, 0, 0),
            new Time(14, 0, 0),
            new Time(15, 0, 0),
            new Time(16, 0, 0),
            new Time(17, 0, 0),
            new Time(18, 0, 0),
            new Time(19, 0, 0),
            new Time(20, 0, 0),
            new Time(21, 0, 0),
//            new Time(22, 0, 0)
    };


    @Override
    public List<Field> queryFieldByType(Integer tid) {
        return getBaseMapper().queryFieldByType(tid);
    }

    @Override
    public void fill(FieldType fieldType) {


        List<Field> fieldList = getBaseMapper().fillFieldInType(fieldType.getTid());
        fieldType.setFieldList(fieldList);


    }

    @Override
    public Field queryFieldById(Integer fid) {
        Field field = getBaseMapper().queryFieldById(fid);
        if (field != null) {
            field.setFieldType(getBaseMapper().queryTypeById(field.getTid()));
        }
        return field;
    }

    @Override
    public List<FieldDate> search(Integer fid, java.sql.Date date) {
        Field field = getBaseMapper().queryFieldById(fid);
        List<FieldDate> fieldDateList = new ArrayList<>();

        for (int i = 0; i < field.getNum(); i++) {
            //初始化
            FieldDate fieldDate = new FieldDate();
            fieldDate.setInode(i);
            fieldDate.setDate(date);
            fieldDate.setField(field);
            List<FieldDate> result = getBaseMapper().queryDateByField(fid, date, i);
            if (result.size() > 0) {
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

    List<TimeArrange> list(Integer fdid) {
        List<TimeArrange> beans = new ArrayList<>();
        List<TimeArrange> results = getBaseMapper().queryTimeByFdId(fdid);

        int j = -1;
        if (results.size() > 0) {
            j = results.get(0).getInode();
        }
        for (int i = 0; i < 13; i++) {
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
                if (results.size() > 0) {
                    j++;
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
        return getBaseMapper().updateStatus(time_id, status);
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
    public List<FieldCheckVo> queryCheck() {
        List<FieldCheck> fieldCheckList = getBaseMapper().queryCheck();
        List<FieldCheckVo> fieldCheckVos = new ArrayList<>();
        for (int i = 0; i < fieldCheckList.size(); i++) {
            FieldCheckVo fieldCheckVo = new FieldCheckVo();
            if (fieldCheckList.get(i).getStatus().equals("审核中")){
                fieldCheckVo.setFlag(true);
            }else {
                fieldCheckVo.setFlag(false);
            }
            fieldCheckVo.setCard(fieldCheckList.get(i).getCard());
            fieldCheckVo.setId(fieldCheckList.get(i).getId());
            fieldCheckVo.setMoney(fieldCheckList.get(i).getMoney());
            fieldCheckVo.setStatus(fieldCheckList.get(i).getStatus());
            fieldCheckVo.setTime(TimeUtils.TimeStampToString(fieldCheckList.get(i).getTime()));
            fieldCheckVo.setName(fieldCheckList.get(i).getName());
            fieldCheckVo.setUserName(fieldCheckList.get(i).getUser().getName());
            List<OrderItem> orderItemList = getBaseMapper().
                    queryOrderItemByFcid(fieldCheckList.get(i).getId());
            List<TimeArrange> timeArrangeList = new ArrayList<>();

            for (int j = 0; j < orderItemList.size(); j++) {
                TimeArrange timeArrange = getBaseMapper().queryTimeById(orderItemList.get(j).getTimeId());
                timeArrangeList.add(timeArrange);
            }
            fieldCheckVo.setTimeArrangeList(timeArrangeList);
            List<String> dateList = new ArrayList<>();
            for (int n=0;n<timeArrangeList.size();n++){
                FieldDate fieldDate = getBaseMapper().queryDateById(timeArrangeList.get(n).getFdid());
                dateList.add(fieldDate.getDate().toString());
            }
            for(int j=0;j<dateList.size()-1;j++){
                for (int k=j+1;k<dateList.size();k++){
                    if(dateList.get(j).equals(dateList.get(k))){
                        dateList.remove(k);
                        k--;
                    }
                }
            }
            String date="";
            for (String str:dateList){
                date=date+str+" ";
            }
            fieldCheckVo.setDate(date);
            fieldCheckVos.add(fieldCheckVo);
        }

        return fieldCheckVos;
    }

    @Override
    public List<FieldCheckVo> queryCheckByUid(Integer uid) {

        List<FieldCheck> fieldCheckList = getBaseMapper().queryCheckByUid(uid);
        List<FieldCheckVo> fieldCheckVos = new ArrayList<>();
        User user = getBaseMapper().queryUserById(uid);
        for (int i = 0; i < fieldCheckList.size(); i++) {
            FieldCheckVo fieldCheckVo = new FieldCheckVo();
            if (fieldCheckList.get(i).getStatus().equals("待支付")){
                fieldCheckVo.setFlag(true);
            }else {
                fieldCheckVo.setFlag(false);
            }
            fieldCheckVo.setCard(fieldCheckList.get(i).getCard());
            fieldCheckVo.setId(fieldCheckList.get(i).getId());
            fieldCheckVo.setMoney(fieldCheckList.get(i).getMoney());
            fieldCheckVo.setStatus(fieldCheckList.get(i).getStatus());
            fieldCheckVo.setTime(TimeUtils.TimeStampToString(fieldCheckList.get(i).getTime()));
            fieldCheckVo.setName(fieldCheckList.get(i).getName());
            fieldCheckVo.setUserName(user.getName());
            List<OrderItem> orderItemList = getBaseMapper().
                    queryOrderItemByFcid(fieldCheckList.get(i).getId());
            List<TimeArrange> timeArrangeList = new ArrayList<>();

            for (int j = 0; j < orderItemList.size(); j++) {
                TimeArrange timeArrange = getBaseMapper().queryTimeById(orderItemList.get(j).getTimeId());
                timeArrangeList.add(timeArrange);
            }
            fieldCheckVo.setTimeArrangeList(timeArrangeList);
            List<String> dateList = new ArrayList<>();
            for (int n=0;n<timeArrangeList.size();n++){
                FieldDate fieldDate = getBaseMapper().queryDateById(timeArrangeList.get(n).getFdid());
                dateList.add(fieldDate.getDate().toString());
            }
                for(int j=0;j<dateList.size()-1;j++){
        for (int k=j+1;k<dateList.size();k++){
            if(dateList.get(j).equals(dateList.get(k))){
                dateList.remove(k);
                k--;
            }
        }
    }
                    String date="";
           for (String str:dateList){
               date=date+str+" ";
           }
            fieldCheckVo.setDate(date);
            fieldCheckVos.add(fieldCheckVo);
        }
        return fieldCheckVos;
    }

    @Override
    public Boolean updateCheckCardById(FieldCheck fieldCheck) {
        return getBaseMapper().updateCheckCardById(fieldCheck);
    }

    @Override
    public Boolean updateCheckStatusById(FieldCheck fieldCheck) {
        return getBaseMapper().updateCheckStatusById(fieldCheck);
    }

    @Override
    public Boolean checkFlag(FieldCheck fieldCheck) {
        List<FieldCheck> fieldCheckList = getBaseMapper().queryCheckByUid(fieldCheck.getUser().getId());
        Date dateNow = new Date(System.currentTimeMillis());
        for (int i = 0; i < fieldCheckList.size(); i++) {
            if (fieldCheckList.get(i).getStatus().equals("待支付")) {
                List<OrderItem> orderItemList = getBaseMapper().queryOrderItemByFcid(fieldCheckList.get(i).getId());
                for (int j = 0; j < orderItemList.size(); j++) {
                    TimeArrange timeArrange = getBaseMapper().queryTimeById(orderItemList.get(j).getTimeId());
                    FieldDate fieldDate = getBaseMapper().queryDateById(timeArrange.getFdid());
                    try {
                        int diff = TimeUtils.getDayDiffer(fieldDate.getDate(), dateNow);
                        if (diff < 3 && diff != 0) {
                            return true;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Boolean checkNum(FieldCheck fieldCheck) {
        List<FieldCheck> fieldCheckList = getBaseMapper().queryCheckByUid(fieldCheck.getUser().getId());
        Date dateNow = new Date(System.currentTimeMillis());
        int flag=0;
        for (int i = 0; i < fieldCheckList.size(); i++) {
            if (fieldCheckList.get(i).getStatus().equals("待支付") || fieldCheckList.get(i).getStatus().equals("审核中")) {
                try {
                    int diff = TimeUtils.getDayDiffer(TimeUtils.
                            TimeStampToDate(fieldCheckList.get(i).getTime()), dateNow);
               if (diff==0){
                   flag++;
               }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        if (flag>=3){
            return true;
        }
        return false;

    }

    @Override
    public Boolean addType(FieldType fieldType) {
        return getBaseMapper().addType(fieldType);
    }

    @Override
    public Integer queryTypeByName(String typeName) {
        return getBaseMapper().queryTypeByName(typeName);
    }

    @Override
    public Double queryMoneyByTimeId(Integer timeId) {
        Double money = 0.00;
        TimeArrange timeArrange = getBaseMapper().queryTimeById(timeId);
        FieldDate fieldDate = getBaseMapper().queryDateById(timeArrange.getFdid());
        Field field = getBaseMapper().queryFieldById(fieldDate.getField().getFid());
        Time endTime = timeArrange.getEndTime();
        Date date = fieldDate.getDate();
        String week = TimeUtils.getWeekOfDate(date);
        if (week.equals("星期六")||week.equals("星期日")){
                if (endTime.getTime()!=time[5].getTime()||endTime.getTime()!=time[6].getTime()){
                    money = field.getMoney();
                }
        }else {
            if (endTime.getTime()<=time[8].getTime()||endTime.getTime()>time[10].getTime()){
                money = field.getMoney();
            }
        }
            return money;
    }

    @Override
    public Boolean checkCancelTime(FieldCheck fieldCheck) {
        List<OrderItem> orderItemList = getBaseMapper().queryOrderItemByFcid(fieldCheck.getId());
        Date dateNow = new Date(System.currentTimeMillis());
        for (int j = 0; j < orderItemList.size(); j++) {
            TimeArrange timeArrange = getBaseMapper().queryTimeById(orderItemList.get(j).getTimeId());
            FieldDate fieldDate = getBaseMapper().queryDateById(timeArrange.getFdid());
            try {
                int diff = TimeUtils.getDayDiffer(fieldDate.getDate(), dateNow);
                if (diff>= 0) {
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Double querySumMoney(String beginDate, String endDate) {
        Double sum=0.00;
        List<FieldCheck> fieldCheckList = getBaseMapper().queryCheckByTime(beginDate,endDate);
        if (fieldCheckList.size()>0){
            for (int i=0;i<fieldCheckList.size();i++){
                sum = sum+  fieldCheckList.get(i).getMoney();
            }
        }
        return sum;
    }

    @Override
    public Boolean deleteType(FieldType fieldType) {
        fieldType.setIsDel(1);
        return getBaseMapper().deleteType(fieldType);
    }

    @Override
    public Boolean deleteField(Field field) {
        field.setIsDel(1);
        return getBaseMapper().deleteField(field);
    }

    @Override
    public Integer queryFieldByDes(Field field) {
        return getBaseMapper().queryFieldByDes(field);
    }

    @Override
    public boolean updateStatusById(ArrayList<TimeArrange> timeArranges) {
        for (TimeArrange timeArrange : timeArranges){
            if (!getBaseMapper().updateStatus(timeArrange.getTimeId(),timeArrange.getStatus())){
                return false;
            }
        }
        return true;
    }

    @Override
    public List<TimeArrange> queryTime(Date date, Integer tid, Integer fid, Integer index) {
        return getBaseMapper().queryTime(date,tid,fid,index);
    }
}