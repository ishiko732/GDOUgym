package edu.gdou.gym_java.controller;


import edu.gdou.gym_java.entity.bean.ResponseBean;
import edu.gdou.gym_java.entity.model.*;
import edu.gdou.gym_java.service.FieldService;
import edu.gdou.gym_java.service.UserService;
import org.springframework.web.bind.annotation.*;

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
    private final UserService userService;
    public FieldController(FieldService fieldService,UserService userService) {
        this.fieldService = fieldService;
        this.userService = userService;
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

    //添加场地
    @PostMapping("/addField")
    public ResponseBean addField(@RequestParam(value = "money",defaultValue = "0") String money,
                                   @RequestParam(value = "description",defaultValue = "") String description,
                                   @RequestParam(value = "num",defaultValue = "0") String num,
                                   @RequestParam(value = "tid",required = true) String tid){
        Field field = new Field();
        field.setTid(Integer.valueOf(tid));
        field.setDescription(description);
        field.setNum(Integer.valueOf(num));
        field.setMoney(Integer.valueOf(money));
        if (fieldService.addField(field)) {
            return new ResponseBean(200, "场地添加成功！", field);
        } else {
            return new ResponseBean(200, "场地添加失败", null);
        }
    }

    //编辑场地
    @PostMapping("/updateField")
    public ResponseBean updateField(@RequestParam(value = "money",defaultValue = "0") String money,
                                   @RequestParam(value = "description",defaultValue = "") String description,
                                   @RequestParam(value = "num",defaultValue = "0") String num,
                                   @RequestParam(value = "fid",required = true) String fid){

        Field field  = fieldService.queryFieldById(Integer.valueOf(fid));
        if (field!=null){
            field.setDescription(description);
            field.setNum(Integer.valueOf(num));
            field.setMoney(Integer.valueOf(money));
            if (fieldService.updateField(field)) {
                return new ResponseBean(200, "场地更新成功！", field);
            } else {
                return new ResponseBean(200, "场地更新失败", null);
            }
        }else {
            return new ResponseBean(200, "该场地不存在", null);
        }
    }


    //编辑场地时间段状态
    @PostMapping("/updateStatus")
    public ResponseBean updateStatus(@RequestParam("timeId") String timeId ,
                                    @RequestParam("status") String status){
            if (fieldService.updateStatus(Integer.valueOf(timeId),status)) {
                return new ResponseBean(200, "场地时间段状态更新成功！", status);
            } else {
                return new ResponseBean(200, "场地时间段状态更新失败", null);
            }

    }

    //点击日期查询场地时间段信息安排
    @PostMapping("/listTimeByDate")
    public ResponseBean listTimeByDate(@RequestParam("tid") String tid ,
                                     @RequestParam(value = "fid",defaultValue = "0") String fid_par,
                                       @RequestParam("date") String date_par){
        Map<String,Object> map = new HashMap<>();
        FieldType fieldType = fieldService.queryTypeById(Integer.parseInt(tid));
        List<Field> fieldList = fieldService.queryFieldByType(Integer.parseInt(tid));
        Integer fid = Integer.parseInt(fid_par);

        //获取场地id号
        if (fid == 0) {
            fid = fieldList.get(0).getFid();
        }

        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        if (date_par != null) {
            date = java.sql.Date.valueOf(date_par);
        }

        //获取能获取的有效日期
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
        //获取所有场的安排表
        List<FieldDate> fieldDateList = fieldService.search(fid,date);
        map.put("name",fieldType.getTypeName().substring(0,1));
        map.put("fieldDateList",fieldDateList);
            return new ResponseBean(200,fieldDateList.size()>0?"查询成功":"查询结果为空",map);
    }

    //用户提交预约审核，生成订单，审核项设置为审核中，日期安排项设置为预约中，订单项设置为未支付
    @PostMapping("/orderField")
    public ResponseBean orderField(@RequestParam("uid") String uid ,
                                   @RequestParam("idCard") String idCard ,
                                   @RequestParam("timeId") String time_id ,
                                       @RequestParam(value = "name") String name,
                                       @RequestParam("money") String money_par){
        FieldCheck fieldCheck = new FieldCheck();

        User user = userService.queryUserByID(Integer.valueOf(uid));//用户
        //新增审核
        fieldCheck.setTime(new java.sql.Timestamp(System.currentTimeMillis()));
        fieldCheck.setName(name);
        fieldCheck.setIdCard(idCard);
        fieldCheck.setMoney(Integer.valueOf(money_par));
        fieldCheck.setStatus("审核中");
        fieldCheck.setUser(user);

       Boolean addCheck = fieldService.addCheck(fieldCheck);
                fieldService.updateStatus(Integer.valueOf(time_id),"预约中"); //编辑状态
                //新增订单项
                OrderItem orderItem = new OrderItem();
                orderItem.setTimeId(Integer.valueOf(time_id));
                orderItem.setFcid(fieldCheck.getId());
        orderItem.setOrderStatus("审核中");
        Boolean addOrderItem  = fieldService.addOrderItem(orderItem);
        return new ResponseBean(200,addCheck&&addOrderItem?"提交审核成功":"提交审核失败",name);
    }


    //后台审核通过，审核项设置为审核通过，日期安排项设置为占用，订单项设置为未支付
    //后台审核不通过，审核项设置为审核退回，日期安排项设置为空闲，订单项设置为已取消
    @PostMapping("/checkOrder")
    public ResponseBean orderField(@RequestParam("id") String id ,
                                   @RequestParam("status") String status){
        FieldCheck fieldCheck = fieldService.queryCheckById(Integer.valueOf(id));
        String orderStatus="";
        String arrangeStatus="";
        //更改审核状态
        if (status.equals("审核通过")){
            fieldCheck.setStatus(status);
             orderStatus="未支付";
             arrangeStatus="占用";
        }else if(status.equals("审核退回")) {
            fieldCheck.setStatus(status);
             orderStatus="已取消";
             arrangeStatus="空闲";
        }else {
            return new ResponseBean(200,"审核状态输入错误",status);
        }

        //更新预约审核状态
        Boolean updateCheck = fieldService.updateCheck(fieldCheck);
        //根据审核id获取到订单，再更新订单状态
        OrderItem orderItem = fieldService.queryOrderItemByFcid(Integer.valueOf(id));
        orderItem.setOrderStatus(orderStatus);
        Boolean updateOrder = fieldService.updateOrder(orderItem);
        //根据订单里的安排表id更新时间段状态
        Boolean updateArrange =fieldService.updateStatus(orderItem.getTimeId(),arrangeStatus); //编辑状态


        return new ResponseBean(200,updateCheck&&updateOrder&&updateArrange?"审核成功":"审核失败",fieldCheck);
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

        if (tid == null) {  //未选择场地
            fieldType = fieldTypeList.get(0);
        } else {
            fieldType = fieldService.queryTypeById(Integer.valueOf(tid));
        }
        fieldService.fill(fieldType);  //填充类型的场地

        //获得场地
        if (fid == null) {   //未选择场地
            if (fieldType.getFieldList().size()>0){
                field = fieldType.getFieldList().get(0);
            }
        } else {    //选过场地，移除默认场馆
            field = fieldService.queryFieldById(Integer.valueOf(fid));
            fieldService.fill(field.getFieldType());
        }

        List<List<FieldDate>> lists = new ArrayList<>();
        //每个日期获得一个安排表列表
        if (field!=null){
            for (int i = 0; i < dateValid.size(); i++)
                lists.add(fieldService.search(field.getFid(), dateValid.get(i)));
        }
        map.put("name",fieldType.getTypeName().substring(0,1));
        map.put("date",dateValid);
        map.put("timeArrange",lists);
        return new ResponseBean(200,map.size()>0?"查询成功":"查询结果为空",map);
    }
}
