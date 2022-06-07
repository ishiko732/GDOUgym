package edu.gdou.gym_java.controller;

import edu.gdou.gym_java.entity.bean.ResponseBean;
import edu.gdou.gym_java.entity.model.*;
import edu.gdou.gym_java.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private final EquipmentRentStandardService equipmentRentStandardService;
    private final FixEquipmentService fixEquipmentService;
    private final RecycleEquipmentService recycleEquipmentService;
    private final UserService userService;
    private final RentEquipmentService rentEquipmentService;
    public EquipmentController(EquipmentService equipmentService,EquipmentRentStandardService equipmentRentStandardService,
                               UserService userService,FixEquipmentService fixEquipmentService,
                               RecycleEquipmentService recycleEquipmentService,RentEquipmentService rentEquipmentService){
        this.fixEquipmentService = fixEquipmentService;
        this.equipmentService = equipmentService;
        this.equipmentRentStandardService = equipmentRentStandardService;
        this.recycleEquipmentService = recycleEquipmentService;
        this.userService = userService;
        this.rentEquipmentService = rentEquipmentService;
    }

    @GetMapping("/queryEquipment")
    public ResponseBean queryEquipment(@RequestParam(value = "name",required = false)String name,
                                       @RequestParam(value = "types",required = false)String types,
                                       @RequestParam(value = "number",required = false)String number){
        List<Equipment> equipment;
        if (StringUtils.isNumeric(number)){
            equipment = equipmentService.queryEquipment(name,types,Integer.parseInt(number));
            return new ResponseBean(200,"查询成功",equipment);
        }else if(number==null){
            equipment = equipmentService.queryEquipment(name, types, null);
            return new ResponseBean(200,"查询成功",equipment);
        }else {
            return new ResponseBean(200,"查询失败，输入的number为非数字",null);
        }
    }

    @PostMapping("/addEquipment")
    public ResponseBean addEquipment(String name,String types,String number){
        if(StringUtils.isNumeric(number)){
            return new ResponseBean(200,equipmentService.addEquipment(new Equipment(null,name,types,Integer.parseInt(number)))?"添加成功":"添加失败",null);
        }else{
            return new ResponseBean(200,"添加失败，输入的数量为非数字",null);
        }
    }

    @GetMapping("/queryEquipmentRentStandard")
    public ResponseBean queryEquipmentRentStandard(){
        return new ResponseBean(200,"查询成功",equipmentRentStandardService.queryEquipmentRentStandard());
    }

    @GetMapping("/queryEquipmentRentStandardByEid")
    public ResponseBean queryEquipmentRentStandardByEid(@RequestParam("eid")String eid){
        if (StringUtils.isNumeric(eid)){
            return new ResponseBean(200,"查询成功",equipmentRentStandardService.queryEquipmentRentStandardByEid(Integer.parseInt(eid)));
        }else{
            return new ResponseBean(200,"输入的eid为非数字",null);
        }
    }

    @PostMapping("/applyFixEquipment")
    public ResponseBean applyFixEquipment(@RequestParam("eid")String eid,@RequestParam("number")String number){
        if (StringUtils.isNumeric(eid)&&StringUtils.isNumeric(number)){
            Equipment equipment = equipmentService.queryEquipmentByEid(Integer.parseInt(eid));
            if (equipment==null){
                return new ResponseBean(200,"需要维修的器材不存在",null);
            }else{
                if (Integer.parseInt(number)<=equipmentService.availableEquipmentCount(Integer.parseInt(eid))){
                    FixEquipment fixEquipment = new FixEquipment(Integer.parseInt(eid), equipment.getName(), equipment.getTypes(), Integer.parseInt(number));
                    Boolean flag = fixEquipmentService.applyFixEquipment(fixEquipment);
                    return new ResponseBean(200,flag?"器材维修申报成功":"器材维修申报失败",null);
                }else{
                    return new ResponseBean(200,"器材维修申报失败，器材申报数量大于器材可使用数量",null);
                }
            }
        }else{
            return new ResponseBean(200,"输入的eid或number为非数字",null);
        }
    }

    @GetMapping("/queryFixEquipment")
    public ResponseBean queryFixEquipment(@RequestParam(value = "fid",required = false) String fid,@RequestParam(value = "name",required = false)String name,
                                          @RequestParam(value = "number",required = false)String number,@RequestParam(value = "type",required = false)String type){
        List<FixEquipment> fixEquipments = fixEquipmentService.queryFixEquipment(fid != null ? Integer.parseInt(fid) : null, name, number != null ? Integer.parseInt(number) : null, type);
        return new ResponseBean(200,"查询成功",fixEquipments);
    }

    @PostMapping("/confirmFixEquipment")
    public ResponseBean confirmFixEquipment(@RequestParam("eid")String eid,@RequestParam("number")String number){
        if (StringUtils.isNumeric(eid)&&StringUtils.isNumeric(number)){
            Equipment equipment = equipmentService.queryEquipmentByEid(Integer.parseInt(eid));
            if (equipment!=null){
                FixEquipment fixEquipment = new FixEquipment(Integer.parseInt(eid), equipment.getName(), equipment.getTypes(), Integer.parseInt(number));
                Boolean flag = fixEquipmentService.confirmFixEquipment(fixEquipment);
                return new ResponseBean(200,flag?"器材维修确认成功":"器材维修确认失败",null);
            }else{
                return new ResponseBean(200,"需要维修的器材不存在",null);
            }
        }else{
            return new ResponseBean(200,"输入的eid或number为非数字",null);
        }
    }

    @PostMapping("/availableEquipmentCount")
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
    public ResponseBean reduceEquipmentCount(@RequestParam("eid")String eid,@RequestParam("number")String number){
        if (StringUtils.isNumeric(eid)&&StringUtils.isNumeric(number)){
            Equipment equipment = equipmentService.queryEquipmentByEid(Integer.parseInt(eid));
            if (equipment==null){
                return new ResponseBean(200,"器材不存在",null);
            }else{
                if (Integer.parseInt(number)<=equipmentService.availableEquipmentCount(Integer.parseInt(eid))){
                    equipment.setNumber(equipment.getNumber()-Integer.parseInt(number));
                    Boolean flag = equipmentService.updateEquipmentCount(equipment);
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
    public ResponseBean queryRecycleEquipment(@RequestParam(value = "reid",required = false)String reid,@RequestParam(value = "name",required = false)String name,
                                              @RequestParam(value = "number",required = false)String number,@RequestParam(value = "type",required = false)String type){
        List<RecycleEquipment> recycleEquipments = recycleEquipmentService.queryRecycleEquipment(reid != null ? Integer.parseInt(reid) : null, name, number != null ? Integer.parseInt(number) : null, type);
        return new ResponseBean(200,"查询成功",recycleEquipments);
    }

    @PostMapping("/confirmRecycleEquipment")
    public ResponseBean confirmRecycleEquipment(@RequestParam("eid")String eid,@RequestParam("number")String number){
        if (StringUtils.isNumeric(eid)&&StringUtils.isNumeric(number)){
            Equipment equipment = equipmentService.queryEquipmentByEid(Integer.parseInt(eid));
            RecycleEquipment recycle = recycleEquipmentService.queryRecycleEquipment(Integer.parseInt(eid));
            if (equipment==null){
                return new ResponseBean(200,"器材不存在",null);
            }else if(recycle==null){
                return new ResponseBean(200,"器材回收记录不存在",null);
            }else{
                RecycleEquipment recycleEquipment = new RecycleEquipment(equipment.getId(),equipment.getName(),equipment.getTypes(),Integer.parseInt(number));
                Boolean flag = recycleEquipmentService.confirmRecycleEquipment(recycleEquipment);
                return new ResponseBean(200,flag?"器材回收确认成功":"器材回收确认失败",null);
            }
        }else{
            return new ResponseBean(200,"输入的eid或number为非数字",null);
        }
    }

    @PostMapping("/addRentEquipment")
    public ResponseBean addRentEquipment(@RequestParam("uid")String uid,@RequestParam("eid")String eid,
                                         @RequestParam("rentTime")String rentTIme,@RequestParam("number")String number){
        if(StringUtils.isNumeric(uid)||StringUtils.isNumeric(eid)||StringUtils.isNumeric(rentTIme)||StringUtils.isNumeric(number)){
            User user = userService.queryUserByID(Integer.parseInt(uid));
            Equipment equipment = equipmentService.queryEquipmentByEid(Integer.parseInt(eid));
            if (user!=null && equipment!=null){
                if (Integer.parseInt(number)<=equipmentService.availableEquipmentCount(equipment.getId())){
                    RentEquipment rentEquipment = new RentEquipment(null, equipment.getId(), equipment.getName(), user.getId(), user.getName(), Integer.parseInt(rentTIme), Integer.parseInt(number));
                    Boolean flag = rentEquipmentService.addRentEquipment(rentEquipment);
                    return new ResponseBean(200,flag?"器材租用成功":"器材租用失败",null);
                }else{
                    return new ResponseBean(200,"器材租用失败，器材租用数量大于器材可使用数量",null);
                }
            }else if(user==null){
                return new ResponseBean(200,"查询不到用户，输入的uid有误",null);
            }else{
                return new ResponseBean(200,"查询不到器材，输入的eid有误",null);
            }
        }else{
            return new ResponseBean(200,"输入的参数为非数字",null);
        }
    }

    @GetMapping("/queryRentEquipmentByEid")
    public ResponseBean queryRentEquipmentByEid(@RequestParam("rid")String rid){
        if(StringUtils.isNumeric(rid)){
            RentEquipment rentEquipment = rentEquipmentService.queryRentEquipmentByEid(Integer.parseInt(rid));
            return  new ResponseBean(200,rentEquipment!=null?"器材租用记录查询成功":"该器材租用记录不存在",rentEquipment);
        }else{
            return new ResponseBean(200,"输入的rid为非数字",null);
        }
    }

    @GetMapping("/queryRentEquipment")
    public ResponseBean queryRentEquipment(@RequestParam(value = "rid",required = false) String rid, @RequestParam(value = "eid",required = false)String eid,
                                           @RequestParam(value = "eName",required = false)String eName, @RequestParam(value = "uid",required = false)String uid,
                                           @RequestParam(value = "username",required = false)String username, @RequestParam(value = "rentTime",required = false)String rentTime,
                                           @RequestParam(value = "number",required = false)String number){
        List<RentEquipment> rentEquipments = rentEquipmentService.queryRentEquipment(rid != null ? Integer.parseInt(rid) : null, eid != null ? Integer.parseInt(eid) : null, eName, uid != null ? Integer.parseInt(uid) : null,
                                                                                    username, rentTime != null ? Integer.parseInt(rentTime) : null, number != null ? Integer.parseInt(number) : null);
        return new ResponseBean(200,"查询成功",rentEquipments);
    }

}
