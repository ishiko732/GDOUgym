package edu.gdou.gym_java.controller;


import edu.gdou.gym_java.entity.bean.ResponseBean;
import edu.gdou.gym_java.entity.model.Field;
import edu.gdou.gym_java.entity.model.FieldDate;
import edu.gdou.gym_java.entity.model.FieldType;
import edu.gdou.gym_java.service.FieldService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ylhy
 * @since 2022-05-31
 */
@RestController
@RequestMapping("/field")
public class FieldController {
    private final FieldService fieldService;
    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }
    //id查询场地类型
    @GetMapping("/queryFieldByType")
    public ResponseBean queryFieldByType(@RequestParam("tid")String tid){
        List<Field> fieldList =fieldService.queryFieldByType(Integer.valueOf(tid));
        return new ResponseBean(200,fieldList.size()>0?"查询成功":"查询结果为空",fieldList);
    }
    //查询所有场地类型
    @GetMapping("/queryType")
    public ResponseBean queryType(){
        List<FieldType> fieldTypeList =fieldService.queryType();
        return new ResponseBean(200,fieldTypeList.size()>0?"查询成功":"查询结果为空",fieldTypeList);
    }

    //加载日期安排，无参数默认第一个类型第一个场地；带参数查询特定类型下的某个场地安排（tid场地类型id,fid场地id）
    @GetMapping("/queryDate")
    public ResponseBean queryDate(@RequestParam(value = "tid",required = false)String tid,@RequestParam(value="fid",required = false)String fid){
        Map<String,Object> map = new HashMap<>();
        List<FieldType> fieldTypeList = fieldService.queryType();
        Date current = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        List<java.sql.Date> dateValid = new ArrayList<>();
        //可选日期
        calendar.add(Calendar.DATE, 0);
        dateValid.add(new java.sql.Date(calendar.getTimeInMillis()));
        for(int i=0;i<6;i++){
            calendar.add(Calendar.DATE, 1);
            dateValid.add(new java.sql.Date(calendar.getTimeInMillis()));
        }

        FieldType fieldType=null;     //场地类型：需要填充场地
        Field field=null;       //场地：需要填充安排表
//        String date = null;   //日期：切换场馆或场地设的日期

        if (tid == null) {  //未选择场地
            fieldType = fieldTypeList.get(0);
        } else {
            fieldType = fieldService.queryTypeById(Integer.valueOf(tid));
        }
        fieldService.fill(fieldType);  //填充类型的场地

        //获得场地
        if (fid == null) {   //未选择场地
            field = fieldType.getFieldList().get(0);
        } else {    //选过场地，移除默认场馆
            field = fieldService.queryFieldById(Integer.valueOf(fid));
            fieldService.fill(field.getFieldType());
            map.put("fieldType",field.getFieldType());
        }

        List<List<FieldDate>> lists = new ArrayList<>();
        //每个日期获得一个安排表列表
        for (int i = 0; i < dateValid.size(); i++)
            lists.add(fieldService.search(field.getFid(), dateValid.get(i)));

        map.put("date",dateValid);
        map.put("timeArrange",lists);
        return new ResponseBean(200,map.size()>0?"查询成功":"查询结果为空",map);
    }
}
