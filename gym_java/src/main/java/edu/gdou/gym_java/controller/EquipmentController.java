package edu.gdou.gym_java.controller;

import edu.gdou.gym_java.entity.bean.ResponseBean;
import edu.gdou.gym_java.entity.model.*;
import edu.gdou.gym_java.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * <p>
 * 器材表 前端控制器
 * </p>
 *
 * @author lzh
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/equipment")
public class EquipmentController {
    private final EquipmentService equipmentService;
    private final FixEquipmentService fixEquipmentService;
    private final RecycleEquipmentService recycleEquipmentService;
    private final UserService userService;
    private final RentEquipmentService rentEquipmentService;
    private final FixEquipmentBillService fixEquipmentBillService;
    public EquipmentController(EquipmentService equipmentService, UserService userService,
                               FixEquipmentService fixEquipmentService, RecycleEquipmentService recycleEquipmentService,
                               RentEquipmentService rentEquipmentService, FixEquipmentBillService fixEquipmentBillService){
        this.fixEquipmentService = fixEquipmentService;
        this.equipmentService = equipmentService;
        this.recycleEquipmentService = recycleEquipmentService;
        this.userService = userService;
        this.rentEquipmentService = rentEquipmentService;
        this.fixEquipmentBillService = fixEquipmentBillService;
    }

    @GetMapping("/queryEquipment")
    @RequiresPermissions(logical = Logical.AND, value = {"查询器材"})
    public ResponseBean queryEquipment(@RequestParam(value = "name",required = false)String name,
                                       @RequestParam(value = "types",required = false)String types,
                                       @RequestParam(value = "number",required = false)String number){
        List<Equipment> equipments = equipmentService.queryEquipment(name, types, StringUtils.isNumeric(number) ? Integer.parseInt(number) : null);
        ArrayList available = new ArrayList();
        for (Equipment equipment : equipments) {
            Integer count = equipmentService.availableEquipmentCount(equipment.getId());
            available.add(count);
        }
        return new ResponseBean(200,"查询成功", new List[]{equipments, available});
    }

    @PostMapping("/addEquipment")
    @RequiresPermissions(logical = Logical.AND, value = {"新增器材"})
    public ResponseBean addEquipment(@RequestParam("name") String name,@RequestParam("types") String types,
                                     @RequestParam("number") String number,@RequestParam("price") String price,
                                     @RequestParam("rentPrice")String rentPrice,@RequestParam("maxRentTime")String maxRentTime){
        if(StringUtils.isNumeric(number)|| NumberUtils.isNumber(price)|| NumberUtils.isNumber(rentPrice)||StringUtils.isNumeric(maxRentTime)){
            return new ResponseBean(200,equipmentService.addEquipment(new Equipment(null,name,types,Integer.parseInt(number),Double.parseDouble(price),Double.parseDouble(rentPrice),Integer.parseInt(maxRentTime)))?"添加成功":"添加失败",null);
        }else{
            return new ResponseBean(200,"添加失败，输入的部分参数为非数字",null);
        }
    }

    @PostMapping("/updateEquipment")
    @RequiresPermissions(logical = Logical.AND, value = {"新增器材"})
    public ResponseBean updateEquipmentRentStandard(@RequestParam(value = "rentPrice",required = false)String rentPrice,
                                                    @RequestParam(value = "maxRentTime",required = false)String maxRentTime,
                                                    @RequestParam(value = "eid",required = false)String eid){
        if (StringUtils.isNumeric(eid)) {
            Equipment equipment = equipmentService.queryEquipmentByEid(Integer.parseInt(eid));
            if (equipment!=null){
                equipment.setRentPrice(NumberUtils.isNumber(rentPrice)?Double.parseDouble(rentPrice):equipment.getRentPrice());
                equipment.setMaxRentTime(StringUtils.isNumeric(maxRentTime)?Integer.parseInt(maxRentTime):equipment.getMaxRentTime());
                Boolean flag = equipmentService.updateEquipment(equipment);
                return new ResponseBean(200,flag?"器材更新成功":"器材更新失败",null);
            }else{
                return new ResponseBean(200,"该器材不存在",null);
            }
        }else{
            return new ResponseBean(200,"添加失败，输入的eid为非数字",null);
        }

    }


    @PostMapping("/applyFixEquipment")
    @RequiresPermissions(logical = Logical.AND, value = {"申请维护器材"})
    public ResponseBean applyFixEquipment(@RequestParam("eid")String eid,@RequestParam("number")String number){
        if (StringUtils.isNumeric(eid)&&StringUtils.isNumeric(number)){
            Equipment equipment = equipmentService.queryEquipmentByEid(Integer.parseInt(eid));
            if (equipment==null){
                return new ResponseBean(200,"需要维修的器材不存在",null);
            }else{
                if (Integer.parseInt(number)<=equipmentService.availableEquipmentCount(Integer.parseInt(eid))){
                    FixEquipment fixEquipment = new FixEquipment(Integer.parseInt(eid), equipment.getName(), equipment.getTypes(), Integer.parseInt(number));
                    Boolean flag = fixEquipmentService.applyFixEquipment(fixEquipment);
                    if (flag){
                        equipment.setNumber(equipment.getNumber()-Integer.parseInt(number));
                        Boolean update = equipmentService.updateEquipment(equipment);
                        return new ResponseBean(200,update?"器材维修申报成功":"器材维修申报失败",null);
                    }else{
                        return new ResponseBean(200,"器材维修申报失败",null);
                    }
                }else{
                    return new ResponseBean(200,"器材维修申报失败，器材申报数量大于器材可使用数量",null);
                }
            }
        }else{
            return new ResponseBean(200,"输入的eid或number为非数字",null);
        }
    }

    @GetMapping("/queryFixEquipment")
    @RequiresPermissions(logical = Logical.AND, value = {"查询维修器材"})
    public ResponseBean queryFixEquipment(@RequestParam(value = "fid",required = false) String fid,@RequestParam(value = "name",required = false)String name,
                                          @RequestParam(value = "number",required = false)String number,@RequestParam(value = "type",required = false)String type){
        List<FixEquipment> fixEquipments = fixEquipmentService.queryFixEquipment(StringUtils.isNumeric(fid) ? Integer.parseInt(fid) : null, name, StringUtils.isNumeric(number) ? Integer.parseInt(number) : null, type);
        return new ResponseBean(200,"查询成功",fixEquipments);
    }

    @PostMapping("/confirmFixEquipment")
    @RequiresPermissions(logical = Logical.AND, value = {"维护器材"})
    public ResponseBean confirmFixEquipment(@RequestParam("eid")String eid){
        if (StringUtils.isNumeric(eid)){
            Equipment equipment = equipmentService.queryEquipmentByEid(Integer.parseInt(eid));
            FixEquipment fixEquipment = fixEquipmentService.queryFixEquipmentByFid(Integer.parseInt(eid));
            if (equipment==null){
                return new ResponseBean(200,"器材不存在",null);
            }else if(fixEquipment==null){
                return new ResponseBean(200,"需要维修的器材不存在",null);
            }else{
                if (fixEquipmentService.deleteFixEquipmentByFid(fixEquipment.getFid())) {
                    equipment.setNumber(equipment.getNumber()+fixEquipment.getNumber());
                    Boolean flag = equipmentService.updateEquipment(equipment);
                    FixEquipmentBill fixEquipmentBill = new FixEquipmentBill(null, equipment.getId(), fixEquipment.getNumber(), equipment.getPrice()/2, new Date());
                    Boolean insert = fixEquipmentBillService.addFixEquipmentBill(fixEquipmentBill);
                    return new ResponseBean(200,flag&&insert?"器材维修确认成功":"器材维修确认失败",null);
                }else{
                    return new ResponseBean(200,"器材维修确认失败",null);
                }
            }
        }else{
            return new ResponseBean(200,"输入的eid或number为非数字",null);
        }
    }

    @GetMapping("/availableEquipmentCount")
    @RequiresPermissions(logical = Logical.AND, value = {"查询器材"})
    public ResponseBean availableEquipmentCount(@RequestParam("eid")String eid){
        if (StringUtils.isNumeric(eid)) {
            Equipment equipment = equipmentService.queryEquipmentByEid(Integer.parseInt(eid));
            if (equipment != null) {
                Integer count = equipmentService.availableEquipmentCount(equipment.getId());
                return new ResponseBean(200,"该器材的可用数量为："+count,count);
            }else{
                return new ResponseBean(200,"该器材不存在",null);
            }
        }else{
            return new ResponseBean(200,"输入的eid为空或为非数字",null);
        }
    }

    @DeleteMapping("/reduceEquipmentCount")
    @RequiresPermissions(logical = Logical.AND, value = {"器材废弃"})
    public ResponseBean reduceEquipmentCount(@RequestParam("eid")String eid,@RequestParam("number")String number){
        if (StringUtils.isNumeric(eid)&&StringUtils.isNumeric(number)){
            Equipment equipment = equipmentService.queryEquipmentByEid(Integer.parseInt(eid));
            if (equipment==null){
                return new ResponseBean(200,"器材不存在",null);
            }else{
                if (Integer.parseInt(number)<=equipmentService.availableEquipmentCount(Integer.parseInt(eid))){
                    equipment.setNumber(equipment.getNumber()-Integer.parseInt(number));
                    Boolean flag = equipmentService.updateEquipment(equipment);
                    return new ResponseBean(200,flag?"器材数量减少成功":"器材数量减少失败",null);
                }else{
                    return new ResponseBean(200,"器材数量减少失败，器材减少数量大于器材可使用数量",null);
                }
            }
        }else{
            return new ResponseBean(200,"输入的eid或number为非数字",null);
        }
    }

    @PostMapping("/applyRecycleEquipment")
    @RequiresPermissions(logical = Logical.AND, value = {"申请回收器材"})
    public ResponseBean applyRecycleEquipment(@RequestParam("eid")String eid,@RequestParam("number")String number){
        if (StringUtils.isNumeric(eid)&&StringUtils.isNumeric(number)){
            Equipment equipment = equipmentService.queryEquipmentByEid(Integer.parseInt(eid));
            if (equipment==null){
                return new ResponseBean(200,"器材不存在",null);
            }else{
                RecycleEquipment recycleEquipment = new RecycleEquipment(equipment.getId(),equipment.getName(),equipment.getTypes(),Integer.parseInt(number));
                Boolean flag = recycleEquipmentService.applyRecycleEquipment(recycleEquipment);
                return new ResponseBean(200,flag?"器材回收申请成功":"器材回收申请失败",null);
            }
        }else{
            return new ResponseBean(200,"输入的eid或number为非数字",null);
        }
    }

    @GetMapping("/queryRecycleEquipment")
    @RequiresPermissions(logical = Logical.AND, value = {"查询回收器材"})
    public ResponseBean queryRecycleEquipment(@RequestParam(value = "reid",required = false)String reid,@RequestParam(value = "name",required = false)String name,
                                              @RequestParam(value = "number",required = false)String number,@RequestParam(value = "type",required = false)String type){
        List<RecycleEquipment> recycleEquipments = recycleEquipmentService.queryRecycleEquipment(StringUtils.isNumeric(reid) ? Integer.parseInt(reid) : null, name,StringUtils.isNumeric(number)?Integer.parseInt(number) : null, type);
        return new ResponseBean(200,"查询成功",recycleEquipments);
    }

    @PostMapping("/confirmRecycleEquipment")
    @RequiresPermissions(logical = Logical.AND, value = {"回收器材审核"})
    public ResponseBean confirmRecycleEquipment(@RequestParam("eid")String eid){
        if (StringUtils.isNumeric(eid)){
            Equipment equipment = equipmentService.queryEquipmentByEid(Integer.parseInt(eid));
            RecycleEquipment recycle = recycleEquipmentService.queryRecycleEquipment(Integer.parseInt(eid));
            if (equipment==null){
                return new ResponseBean(200,"器材不存在",null);
            }else if(recycle==null){
                return new ResponseBean(200,"器材回收记录不存在",null);
            }else{
                Boolean flag = recycleEquipmentService.confirmRecycleEquipment(recycle);
                return new ResponseBean(200,flag?"器材回收确认成功":"器材回收确认失败",null);
            }
        }else{
            return new ResponseBean(200,"输入的eid或number为非数字",null);
        }
    }

    @PostMapping("/addRentEquipment")
    @RequiresPermissions(logical = Logical.AND, value = {"租用器材"})
    public ResponseBean addRentEquipment(@RequestParam("eid")String eid, @RequestParam("rentTime")String rentTime,@RequestParam("number")String number){
        if(StringUtils.isNumeric(eid)||StringUtils.isNumeric(rentTime)||StringUtils.isNumeric(number)){
            User user = userService.currentUser();
            Equipment equipment = equipmentService.queryEquipmentByEid(Integer.parseInt(eid));
            if (user!=null && equipment!=null){
                if (Integer.parseInt(number)<=equipmentService.availableEquipmentCount(equipment.getId())&&Integer.parseInt(rentTime)<=equipment.getMaxRentTime()){
                    RentEquipment rentEquipment = new RentEquipment(null, equipment.getId(), equipment.getName(), user.getId(), user.getName(), Integer.parseInt(rentTime), Integer.parseInt(number),new Date(),0);
                    Boolean flag = rentEquipmentService.addRentEquipment(rentEquipment);
                    return new ResponseBean(200,flag?"器材租用成功":"器材租用失败",null);
                }else if(Integer.parseInt(rentTime)>equipment.getMaxRentTime()){
                    return new ResponseBean(200,"器材租用失败，器材租用时间大于器材可租用时间",null);
                }else{
                    return new ResponseBean(200,"器材租用失败，器材租用数量大于器材可使用数量",null);
                }
            }else if(equipment==null){
                return new ResponseBean(200,"查询不到器材，输入的eid有误",null);
            }
            else if(user==null){
                return new ResponseBean(200,"查询不到用户，输入的uid有误",null);
            }else{
                return new ResponseBean(200,"查询不到对应的器材租用标准，请联系器材管理员",null);
            }
        }else{
            return new ResponseBean(200,"输入的参数为非数字",null);
        }
    }

    @PostMapping("/queryRentEquipmentByUid")
    public ResponseBean responseBean(){
        User user = userService.currentUser();
        List<RentEquipment> rentEquipments = rentEquipmentService.queryRentEquipmentByUid(user.getId());
        return new ResponseBean(200,"查询成功",rentEquipments);
    }

    @PostMapping("/redeemEquipment")
    public ResponseBean redeemEquipment(@RequestParam("rid")String rid){
        if (StringUtils.isNumeric(rid)) {
            Boolean flag = rentEquipmentService.redeemEquipment(Integer.parseInt(rid));
            return new ResponseBean(200,flag?"器材归还成功":"器材归还失败",null);
        }else{
            return new ResponseBean(200,"输入的rid为非数字",null);
        }
    }

    @GetMapping("/queryRentEquipmentByEid")
    @RequiresPermissions(logical = Logical.AND, value = {"查询器材"})
    public ResponseBean queryRentEquipmentByEid(@RequestParam("rid")String rid){
        if(StringUtils.isNumeric(rid)){
            RentEquipment rentEquipment = rentEquipmentService.queryRentEquipmentByEid(Integer.parseInt(rid));
            return  new ResponseBean(200,rentEquipment!=null?"器材租用记录查询成功":"该器材租用记录不存在",rentEquipment);
        }else{
            return new ResponseBean(200,"输入的rid为非数字",null);
        }
    }

    @GetMapping("/queryRentEquipment")
    @RequiresPermissions(logical = Logical.AND, value = {"查询器材"})
    public ResponseBean queryRentEquipment(@RequestParam(value = "rid",required = false) String rid, @RequestParam(value = "eid",required = false)String eid,
                                           @RequestParam(value = "eName",required = false)String eName, @RequestParam(value = "uid",required = false)String uid,
                                           @RequestParam(value = "username",required = false)String username, @RequestParam(value = "rentTime",required = false)String rentTime,
                                           @RequestParam(value = "number",required = false)String number,@RequestParam(value = "status",required = false)String status){
        List<RentEquipment> rentEquipments = rentEquipmentService.queryRentEquipment(StringUtils.isNumeric(rid) ? Integer.parseInt(rid) : null, StringUtils.isNumeric(eid) ? Integer.parseInt(eid) : null, eName,
                StringUtils.isNumeric(uid) ? Integer.parseInt(uid) : null, username, StringUtils.isNumeric(rentTime) ? Integer.parseInt(rentTime) : null,
                StringUtils.isNumeric(number) ? Integer.parseInt(number) : null,StringUtils.isNumeric(status) ? Integer.parseInt(status) : null);
        return new ResponseBean(200,"查询成功",rentEquipments);
    }

    @GetMapping("/generateEquipmentIncome")
    @RequiresPermissions(logical = Logical.AND, value = {"收支处理"})
    public ResponseBean generateEquipmentIncome(String year,String month){
        Double income = rentEquipmentService.generateEquipmentIncome(year, month);
        return new ResponseBean(200,"查询成功,当月的收入为："+income,income);
    }

    @GetMapping("/generateEquipmentOutcome")
    @RequiresPermissions(logical = Logical.AND, value = {"收支处理"})
    public ResponseBean generateEquipmentOutcome(String year,String month){
        Double outcome = fixEquipmentBillService.generateEquipmentOutcome(year, month);
        return new ResponseBean(200,"查询成功,当月的支出为："+outcome,outcome);
    }


}
