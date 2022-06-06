package edu.gdou.gym_java.controller;

import edu.gdou.gym_java.entity.bean.ResponseBean;
import edu.gdou.gym_java.entity.model.Equipment;
import edu.gdou.gym_java.entity.model.FixEquipment;
import edu.gdou.gym_java.entity.model.RecycleEquipment;
import edu.gdou.gym_java.service.EquipmentRentStandardService;
import edu.gdou.gym_java.service.EquipmentService;
import edu.gdou.gym_java.service.FixEquipmentService;
import edu.gdou.gym_java.service.RecycleEquipmentService;
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
    public EquipmentController(EquipmentService equipmentService,EquipmentRentStandardService equipmentRentStandardService,FixEquipmentService fixEquipmentService,RecycleEquipmentService recycleEquipmentService){
        this.fixEquipmentService = fixEquipmentService;
        this.equipmentService = equipmentService;
        this.equipmentRentStandardService = equipmentRentStandardService;
        this.recycleEquipmentService = recycleEquipmentService;
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
            return new ResponseBean(200,"查询失败，输入的信息有误",null);
        }
    }

    @PostMapping("/addEquipment")
    public ResponseBean addEquipment(String name,String types,String number){
        if(StringUtils.isNumeric(number)){
            return new ResponseBean(200,equipmentService.addEquipment(new Equipment(null,name,types,Integer.parseInt(number)))?"添加成功":"添加失败",null);
        }else{
            return new ResponseBean(200,"添加失败，输入的数量有误",null);
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
                    return new ResponseBean(200,flag?"器材维修申报成功":"器材申报失败",null);
                }else{
                    return new ResponseBean(200,"器材维修申报失败，器材申报数量大于器材可使用数量",null);
                }
            }
        }else{
            return new ResponseBean(200,"输入的eid或number为非数字",null);
        }
    }

    @PostMapping("/confirmFixEquipment")
    public ResponseBean confirmFixEquipment(@RequestParam("eid")String eid,@RequestParam("number")String number){
        if (StringUtils.isNumeric(eid)&&StringUtils.isNumeric(number)){
            Equipment equipment = equipmentService.queryEquipmentByEid(Integer.parseInt(eid));
            if (equipment!=null){
                FixEquipment fixEquipment = new FixEquipment(Integer.parseInt(eid), equipment.getName(), equipment.getTypes(), Integer.parseInt(number));
                Boolean flag = fixEquipmentService.confirmFixEquipment(fixEquipment);
                return new ResponseBean(200,flag?"器材维修成功确认":"器材维修成功失败",null);
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
                return new ResponseBean(200,"该器材的可用数量为：",count);
            }else{
                return new ResponseBean(200,"该器材不存在",null);
            }
        }else{
            return new ResponseBean(200,"输入的eid为空或为非数字",null);
        }
    }

    @PostMapping("/reduceEquipmentCount")
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

}
