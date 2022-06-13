package edu.gdou.gym_java.controller;


import com.google.gson.Gson;
import edu.gdou.gym_java.entity.VO.FieldCheckVo;
import edu.gdou.gym_java.entity.VO.QueryTimeVo;
import edu.gdou.gym_java.entity.bean.ResponseBean;
import edu.gdou.gym_java.entity.enums.CheckStatus;
import edu.gdou.gym_java.entity.model.*;
import edu.gdou.gym_java.service.FieldService;
import edu.gdou.gym_java.service.UserService;
import edu.gdou.gym_java.utils.TimeUtils;
import lombok.val;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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
    private final Gson gson;
    public FieldController(FieldService fieldService,UserService userService,Gson gson) {
        this.fieldService = fieldService;
        this.userService = userService;
        this.gson = gson;
    }
    //id查询场地类型
    @RequiresAuthentication
    @GetMapping("/queryFieldByType")
    public ResponseBean queryFieldByType(@RequestParam("tid")String tid){
        List<Field> fieldList =fieldService.queryFieldByType(Integer.valueOf(tid));
        return new ResponseBean(200,fieldList.size()>0?"查询成功":"查询结果为空",fieldList);
    }
    //查询所有场地类型
    @RequiresAuthentication
    @GetMapping("/queryType")
    public ResponseBean queryType(){
        List<FieldType> fieldTypeList =fieldService.queryType();
        return new ResponseBean(200,fieldTypeList.size()>0?"查询成功":"查询结果为空",fieldTypeList);
    }

    //添加场地类型
    @RequiresRoles(logical = Logical.OR, value = {"超级管理员", "场地管理员"})
    @PostMapping("/addType")
    public ResponseBean addType(@RequestParam("typeName") String typeName){
        FieldType fieldType = new FieldType();
        fieldType.setTypeName(typeName);
        Integer isExist=(fieldService.queryTypeByName(typeName));
        if (isExist==1){
            return new ResponseBean(200,"该类型已存在",null);
        }else if (isExist>1){
            return new ResponseBean(200,"数据错误",null);
        }
        Boolean addType = fieldService.addType(fieldType);
        return new ResponseBean(200,addType?"添加成功":"添加失败",fieldType);
    }

    //删除场地类型
    @RequiresRoles(logical = Logical.OR, value = {"超级管理员", "场地管理员"})
    @PostMapping("/deleteType")
    public ResponseBean deleteType(@RequestParam("tid") String tid){
        FieldType fieldType = fieldService.queryTypeById(Integer.valueOf(tid));
        if (fieldType==null){
            return new ResponseBean(200,"该场地类型不存在",null);
        }
        Boolean deleteType = fieldService.deleteType(fieldType);
        return new ResponseBean(200,deleteType?"删除成功":"删除失败",null);
    }

    //添加场地
    @RequiresRoles(logical = Logical.OR, value = {"超级管理员", "场地管理员"})
    @PostMapping("/addField")
    public ResponseBean addField(@RequestParam(value = "money",defaultValue = "0") String money,
                                   @RequestParam(value = "description",defaultValue = "") String description,
                                   @RequestParam(value = "num",defaultValue = "0") String num,
                                   @RequestParam(value = "tid",required = true) String tid){
        Field field = new Field();
        field.setTid(Integer.valueOf(tid));
        field.setDescription(description);
        Integer isExist=(fieldService.queryFieldByDes(field));
        if (isExist==1){
            return new ResponseBean(200,"该类型下所描述的场地已存在",null);
        }else if (isExist>1){
            return new ResponseBean(200,"数据错误",null);
        }
        field.setNum(Integer.valueOf(num));
        field.setMoney(Double.parseDouble(money));
        if (fieldService.addField(field)) {
            return new ResponseBean(200, "场地添加成功！", field);
        } else {
            return new ResponseBean(200, "场地添加失败", null);
        }
    }

    //删除场地
    @RequiresRoles(logical = Logical.OR, value = {"超级管理员", "场地管理员"})
    @PostMapping("/deleteField")
    public ResponseBean deleteField(@RequestParam("fid") String fid){
        Field field = fieldService.queryFieldById(Integer.valueOf(fid));
        if (field==null){
            return new ResponseBean(200,"该场地不存在",null);
        }
        Boolean deleteField = fieldService.deleteField(field);
        return new ResponseBean(200,deleteField?"删除成功":"删除失败",null);
    }

    //编辑场地
    @RequiresRoles(logical = Logical.OR, value = {"超级管理员", "场地管理员"})
    @PostMapping("/updateField")
    public ResponseBean updateField(@RequestParam(value = "money",defaultValue = "0") String money,
                                   @RequestParam(value = "description",defaultValue = "") String description,
                                   @RequestParam(value = "num",defaultValue = "0") String num,
                                   @RequestParam(value = "fid",required = true) String fid){

        Field field  = fieldService.queryFieldById(Integer.valueOf(fid));
        if (field!=null){
            field.setDescription(description);
            field.setNum(Integer.valueOf(num));
            field.setMoney(Double.parseDouble(money));
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
    @RequiresRoles(logical = Logical.OR, value = {"超级管理员", "场地管理员"})
    @PostMapping("/updateStatus")
    public ResponseBean updateStatus(@RequestParam("timeId") String timeId ,
                                    @RequestParam("status") String status){

        if (!status.equals("预约中")&&!status.equals("上课用地")&&!status.equals("校队预留") && !status.equals("维护") && !status.equals("空闲")){
            return new ResponseBean(200, "状态错误", null);
        }
        TimeArrange timeArrange = fieldService.queryTimeById(Integer.valueOf(timeId));
        if(timeArrange != null) {
            if (fieldService.updateStatus(Integer.valueOf(timeId), status)) {
                return new ResponseBean(200, "场地时间段状态更新成功！", status);
            } else {
                return new ResponseBean(200, "场地时间段状态更新失败", null);
            }
        }
        return new ResponseBean(200, "该时间段不存在", null);
    }

    //编辑场地时间段状态（批量）
    @RequiresRoles(logical = Logical.OR, value = {"超级管理员", "场地管理员"})
    @PostMapping("/updateStatusById")
    public ResponseBean updateStatusById(@RequestBody Map<String,Object> map){
        val objectList =gson.fromJson(gson.toJson(map.get("timeArrangeList")),List.class);
        val timeArranges = new ArrayList<TimeArrange>();
        val timeArranges_db = new ArrayList<TimeArrange>();
        for (Object obj : objectList) {
            timeArranges.add(gson.fromJson(gson.toJson(obj), TimeArrange.class));
        }
        for (int i=0;i<timeArranges.size();i++){
            if (!timeArranges.get(i).getStatus().equals("占用")&&
                    !timeArranges.get(i).getStatus().equals("预约中")&&
                    !timeArranges.get(i).getStatus().equals("上课用地")&&
                    !timeArranges.get(i).getStatus().equals("校队预留") &&
                    !timeArranges.get(i).getStatus().equals("维护")&&
                    !timeArranges.get(i).getStatus().equals("空闲")){
                return new ResponseBean(200, "状态错误", timeArranges.get(i).getStatus());
            }

            TimeArrange timeArrange_db = fieldService.queryTimeById(timeArranges.get(i).getTimeId());
            if (!timeArranges.get(i).getStatus().equals("预约中")&&timeArrange_db.getStatus().equals("预约中")){
                return new ResponseBean(200, "该场地预约中，请先在审核处理！", timeArrange_db.getStatus());
            }
            if (!timeArranges.get(i).getStatus().equals("占用")&&timeArrange_db.getStatus().equals("占用")){
                return new ResponseBean(200, "该场地已被预约！", timeArrange_db.getStatus());
            }
            if (timeArranges.get(i).getStatus().equals("占用")&&!timeArrange_db.getStatus().equals("占用")){
                return new ResponseBean(200, "错误操作，不能使用占用字段更改非占用的场地状态！", timeArranges.get(i).getStatus());
            }
            if (!timeArranges.get(i).getStatus().equals(timeArrange_db.getStatus())) {
                timeArranges_db.add(timeArranges.get(i));
            }
        }
            if (fieldService.updateStatusById(timeArranges_db)) {
                return new ResponseBean(200, "场地时间段状态更新成功！", null);
            } else {
                return new ResponseBean(200, "场地时间段状态更新失败", null);
            }
    }




    // 场地收费标准查询
    @RequiresAuthentication
    @GetMapping("/queryMoneyByTimeId")
    public ResponseBean queryMoneyByTimeId(@RequestParam("timeId") String timeId_par){
        Integer timeId = Integer.valueOf(timeId_par);
        Double money = fieldService.queryMoneyByTimeId(timeId);
            return new ResponseBean(200, "场地收费标准查询成功", money);
    }


    //点击日期查询场地时间段信息安排
    @RequiresAuthentication
    @PostMapping("/listTimeByDate")
    public ResponseBean listTimeByDate(@RequestParam(value="tid",defaultValue = "0") String tid_par ,
                                     @RequestParam(value = "fid",defaultValue = "0") String fid_par,
                                       @RequestParam(value = "date",defaultValue = "") String date_par){
        Map<String,Object> map = new HashMap<>();
        List<FieldType> fieldTypeList = fieldService.queryType();
        Integer tid = Integer.parseInt(tid_par);
        //获取场地类型id号
        if (tid == 0) {
            tid = fieldTypeList.get(0).getTid();
        }
        FieldType fieldType = fieldService.queryTypeById(tid);
        List<Field> fieldList = fieldService.queryFieldByType(tid);
        Integer fid = Integer.parseInt(fid_par);

        //获取场地id号
        if (fid == 0) {
            fid = fieldList.get(0).getFid();
        }

        Date date;
        //获取能获取的有效日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(TimeUtils.nowToTimeStamp());
        List<Date> dateValid = new ArrayList<>();

        for(int i=0;i<7;i++){
            calendar.add(Calendar.DATE, 1);
            dateValid.add(new Date(calendar.getTimeInMillis()));
        }

        if (!date_par.isEmpty()) {
            date = Date.valueOf(date_par);
        }else {
            date = dateValid.get(0);
        }

        //获取所有场的安排表
        List<FieldDate> fieldDateList = fieldService.search(fid,date);
        map.put("name",fieldType.getTypeName().substring(0,1));
//        map.put("fields",fieldList);
//        map.put("dates",dateValid);
//        map.put("types",fieldTypeList);
        map.put("fieldDateList",fieldDateList);
            return new ResponseBean(200,fieldDateList.size()>0?"查询成功":"查询结果为空",map);
    }

    //用户提交预约审核，审核项设置为审核中，日期安排项设置为预约中
    @RequiresAuthentication
    @PostMapping("/orderField")
    public ResponseBean orderField(@RequestParam("timeId") String time_id ,
                                       @RequestParam(value = "name") String name,
                                       @RequestParam("money") String money_par){
        FieldCheck fieldCheck = new FieldCheck();
       User user =  userService.currentUser();
       val map = userService.selectInfoByUid(user.getId());
        //新增审核
        fieldCheck.setTime(new java.sql.Timestamp(System.currentTimeMillis()));
        fieldCheck.setName(name);
        fieldCheck.setCard( map.get("id").toString());
        fieldCheck.setMoney(Double.parseDouble(money_par));
        fieldCheck.setStatus("审核中");
        fieldCheck.setUser(user);
       Boolean addCheck = false;
        Boolean addOrderItem =false;
        //验证是否存在失约
        Boolean checkFlag = fieldService.checkFlag(fieldCheck);
       if (checkFlag){
           return new ResponseBean(200,"存在失约行为，2天内禁止预约",null);
       }

       //预约次数限制，每个账号每天只能预约成功3次
       Boolean checkNum = fieldService.checkNum(fieldCheck);
        if (checkNum){
            return new ResponseBean(200,"每个账号每天只能成功预约三次，请注意订单审核状态",null);
        }
        TimeArrange timeArrange = fieldService.queryTimeById(Integer.valueOf(time_id));
       if (timeArrange.getStatus().equals("空闲")){
           addCheck= fieldService.addCheck(fieldCheck);
           fieldService.updateStatus(Integer.valueOf(time_id),"预约中"); //编辑状态
           OrderItem orderItem = new OrderItem();
           orderItem.setTimeId(Integer.valueOf(time_id));
           orderItem.setFcid(fieldCheck.getId());
           addOrderItem  = fieldService.addOrderItem(orderItem);
       }else{
           return new ResponseBean(200,"该时间段场地处于非空闲状态",null);
       }
        return new ResponseBean(200,addCheck&&addOrderItem?"提交审核成功":"提交审核失败",name);
    }


    //后台审核通过，审核项设置为待支付，日期安排项设置为占用
    //后台审核不通过，审核项设置为已退回，日期安排项设置为空闲
    @RequiresRoles(logical = Logical.OR, value = {"超级管理员", "场地管理员"})
    @PostMapping("/checkOrder")
    public ResponseBean orderField(@RequestParam("id") String id ,
                                   @RequestParam("status") String status){
        FieldCheck fieldCheck = fieldService.queryCheckById(Integer.valueOf(id));
        String arrangeStatus="";
        //更改审核状态
        if (fieldCheck!=null){
        if (status.equals("审核通过")){
            status = "待支付";
            fieldCheck.setStatus(status);
             arrangeStatus="占用";
        }else if(status.equals("审核退回")) {
            status = "已退回";
            fieldCheck.setStatus(status);
             arrangeStatus="空闲";
        }else {
            return new ResponseBean(200,"审核状态输入错误",status);
        }
        }
        //更新预约审核状态
        Boolean updateCheck = fieldService.updateCheckStatusById(fieldCheck);
        //根据审核id获取到订单，再更新订单状态
        List<OrderItem> orderItemList = fieldService.queryOrderItemByFcid(Integer.valueOf(id));

        //根据订单里的安排表id更新时间段状态
        for (int i=0;i<orderItemList.size();i++){
          fieldService.updateStatus(orderItemList.get(i).getTimeId(),arrangeStatus); //编辑状态
        }


        return new ResponseBean(200,updateCheck?"审核成功":"审核失败",fieldCheck);
    }


    //管理员查询审核列表
    @RequiresRoles(logical = Logical.OR, value = {"超级管理员", "场地管理员"})
    @GetMapping("/queryCheck")
    public ResponseBean queryCheck(){
        List<FieldCheckVo> fieldCheckVos = fieldService.queryCheck();
        return new ResponseBean(200,fieldCheckVos.size()>0?"查询成功":"查询结果为空",fieldCheckVos);
    }

    //用户查询自己的审核列表
    @RequiresAuthentication
     @PostMapping("/queryCheckByUid")
     public ResponseBean queryCheckByUid(){
         User user =  userService.currentUser();
         List<FieldCheckVo> fieldCheckVos = fieldService.queryCheckByUid(Integer.valueOf(user.getId()));
         return new ResponseBean(200,fieldCheckVos.size()>0?"查询成功":"查询结果为空",fieldCheckVos);
       }

    //场地收入查询
    @RequiresPermissions("收支处理")
    @PostMapping("/querySumMoney")
    public ResponseBean querySumMoney(@RequestParam("beginDate")String beginDate,
                                      @RequestParam("endDate")String endDate){
        Double sumMoney = fieldService.querySumMoney(beginDate,endDate);
        return new ResponseBean(200,"场地收入查询成功",sumMoney);
    }

    //用户根据id和uid修改预约(修改一卡通？)
//    @PostMapping("/updateCheckById")
//    public ResponseBean updateCheckById(@RequestParam(value = "uid",required = true)String uid,
//                                         @RequestParam(value = "id",required = true)String id,
//                                         @RequestParam(value = "card",required = true)String card){
//        FieldCheck fieldCheck = fieldService.queryCheckById(Integer.valueOf(id));
//        User user = userService.queryUserByID(Integer.valueOf(uid));
//        if (fieldCheck.getStatus().equals("审核中") &&fieldCheck!=null && user!=null){
//            fieldCheck.setCard(card);
//                fieldCheck.setUser(user);
//                fieldService.updateCheckCardById(fieldCheck);
//                fieldCheck.setUser(null);
//                    return new ResponseBean(200, "修改预约成功！", fieldCheck);
//
//        }
//        return new ResponseBean(200, "不存在该用户订单或不能取消该状态下的预约", null);
//    }


    //用户根据id和uid取消预约审核
    @RequiresAuthentication
    @PostMapping("/cancelCheckById")
    public ResponseBean cancelCheckById(@RequestParam(value = "id",required = true)String id) {
        FieldCheck fieldCheck = fieldService.queryCheckById(Integer.valueOf(id));
        User user =  userService.currentUser();
        if (user != null&&fieldCheck!=null) {
            fieldCheck.setUser(user);
            if (fieldCheck.getStatus().equals("审核中")) {
                fieldCheck.setStatus("已取消");
                fieldService.updateCheck(fieldCheck);
                //2022-06-08 9:25 fix bug取消订单，根据订单里的安排表id更新时间段状态为空闲
                List<OrderItem> orderItemList = fieldService.queryOrderItemByFcid(Integer.valueOf(id));
                for (int i=0;i<orderItemList.size();i++){
                    fieldService.updateStatus(orderItemList.get(i).getTimeId(),"空闲"); //编辑状态
                }
                fieldCheck.setUser(null);
                return new ResponseBean(200, "取消预约成功", fieldCheck);
            }else if (fieldCheck.getStatus().equals("待支付")) {
                Boolean checkCancelTime = fieldService.checkCancelTime(fieldCheck);
                if (checkCancelTime){
                    fieldCheck.setStatus("已取消");
                    fieldService.updateCheck(fieldCheck);
                    //根据订单里的安排表id更新时间段状态
                    List<OrderItem> orderItemList = fieldService.queryOrderItemByFcid(Integer.valueOf(id));
                    for (int i=0;i<orderItemList.size();i++){
                        fieldService.updateStatus(orderItemList.get(i).getTimeId(),"空闲"); //编辑状态
                    }
                    fieldCheck.setUser(null);
                    return new ResponseBean(200, "取消预约成功", fieldCheck);
                }else {
                    return new ResponseBean(200, "已经接近预约时间，不能取消", null);
                }
        }

    }
            return new ResponseBean(200,"不存在该用户订单或不能取消该状态下的预约",null);
    }

    //赛事预约场地审核，审核项设置为审核中，日期安排项设置为预约中
    //name页面用场地类型+场地描述+序号生成


    @RequiresAuthentication
    @PostMapping("/orderFieldByCom")
    public ResponseBean orderFieldByCom(@RequestBody Map<String,Object> map){
        val queryTimeVos = new ArrayList<QueryTimeVo>();
            val objectList =gson.fromJson(gson.toJson(map.get("queryTimeVos")),List.class);
            for (Object obj : objectList) {
                queryTimeVos.add(gson.fromJson(gson.toJson(obj), QueryTimeVo.class));
            }
        List<String> nameList = new ArrayList<>();
        String name="";
        List<Integer> ids = new ArrayList<>();
        for (int i=0;i<queryTimeVos.size();i++){
          nameList.add(queryTimeVos.get(i).getName());
            ids.add(queryTimeVos.get(i).getTimeId());
        }
        //用set顺序会变倒序
    for(int j=0;j<nameList.size()-1;j++){
        for (int k=j+1;k<nameList.size();k++){
            if(nameList.get(j).equals(nameList.get(k))){
                nameList.remove(k);
                k--;
            }
        }
    }
    for (String str:nameList){
        name = name + str +" ";
    }
        val user = userService.currentUser();
        val objectMap = userService.selectInfoByUid(user.getId());
        //新增审核
        val card=String.valueOf(objectMap.get("id"));
        val status =CheckStatus.CHECKING.getStatus();
        val com_name =  name;
        val money = 0.0;
        val fieldCheck = new FieldCheck(null,null,money, status, com_name,card,null,user);
        for (Integer id : ids) {
            TimeArrange timeArrange = fieldService.queryTimeById(id);
            if (!timeArrange.getStatus().equals("空闲")) {
                return new ResponseBean(200, "存在占用场地", null);
            }
        }
        Boolean addCheck = fieldService.addCheck(fieldCheck);
        Boolean addOrderItem =false;
        // 若添加审核失败，应当不做绑定时间操作
        if (addCheck){
            for (int i = 0; i < ids.size(); i++) {
                fieldService.updateStatus(ids.get(i),"预约中"); //编辑状态
                OrderItem orderItem = new OrderItem();
                orderItem.setTimeId(ids.get(i));
                orderItem.setFcid(fieldCheck.getId());
                addOrderItem  = fieldService.addOrderItem(orderItem);
            }
        }
        fieldCheck.setUser(null);
        return new ResponseBean(200,addCheck&&addOrderItem?"提交审核成功":"提交审核失败", fieldCheck);
    }

    @RequiresAuthentication
    @GetMapping("/loadingDate")
    public ResponseBean loadingDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(TimeUtils.nowToTimeStamp());
        List<Date> dateValid = new ArrayList<>();
        //可选日期
//        calendar.add(Calendar.DATE, 0);
//        dateValid.add(new Date(calendar.getTimeInMillis()));
        for(int i=0;i<7;i++){
            calendar.add(Calendar.DATE, 1);
            dateValid.add(new Date(calendar.getTimeInMillis()));
        }

        return new ResponseBean(200,dateValid.size()==7?"查询成功":"查询结果为空",dateValid);
    }

//场号列表
    @RequiresAuthentication
    @GetMapping("/queryNumByFid")
    public ResponseBean queryNumByFid(@RequestParam("fid")String fid){
        List<Integer> nums = new ArrayList<>();
                Field field = fieldService.queryFieldById(Integer.valueOf(fid));
        for (int i=0;i<field.getNum();i++){
            nums.add(i+1);
        }
        return new ResponseBean(200,nums.size()>0?"查询成功":"查询结果为空",nums);
    }

    //空闲时间段列表
    @RequiresAuthentication
    @PostMapping("/queryTime")
    public ResponseBean queryTime(@RequestParam("date")String date_par,
                                  @RequestParam("tid")String tid_par,
                                   @RequestParam("fid")String fid_par,
                                   @RequestParam("index")String index_par
                                            ){
        Date date = Date.valueOf(date_par);
        Integer tid = Integer.valueOf(tid_par);
        Integer fid = Integer.valueOf(fid_par);
        Integer index = Integer.valueOf(index_par);
        List<TimeArrange> timeArrangeList = fieldService.queryTime(date,tid,fid,index);

        return new ResponseBean(200,timeArrangeList.size()>0?"查询成功":"没有空闲时间段",timeArrangeList);
    }



    @RequiresAuthentication
    @PostMapping("/pay")
    public ResponseBean payForField(@RequestParam(value = "id",required = true)String id){

        FieldCheck fieldCheck = fieldService.queryCheckById(Integer.valueOf(id));
        if (fieldCheck==null){
            return new ResponseBean(200,"该订单号不存在",id);
        }
        User user = userService.currentUser();
        fieldCheck.setUser(user);
        fieldCheck.setStatus("已完成");
        Boolean payForField = fieldService.updateCheck(fieldCheck);

        return new ResponseBean(200,payForField?"支付成功":"支付失败",null);
    }

    //!!!暂时别用
    //加载日期安排，无参数默认第一个类型第一个场地；带参数查询特定类型下的某个场地安排（tid场地类型id,fid场地id）
    @GetMapping("/queryDate")
    public ResponseBean queryDate(@RequestParam(value = "tid",required = false)String tid,@RequestParam(value="fid",required = false)String fid){
        Map<String,Object> map = new HashMap<>();
        List<FieldType> fieldTypeList = fieldService.queryType();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(TimeUtils.nowToTimeStamp());
        List<Date> dateValid = new ArrayList<>();
        //可选日期
//        calendar.add(Calendar.DATE, 0);
//        dateValid.add(new Date(calendar.getTimeInMillis()));
        for(int i=0;i<7;i++){
            calendar.add(Calendar.DATE, 1);
            dateValid.add(new Date(calendar.getTimeInMillis()));
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
