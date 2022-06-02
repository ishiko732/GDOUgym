package edu.gdou.gym_java.controller;

import edu.gdou.gym_java.entity.bean.ResponseBean;
import edu.gdou.gym_java.entity.model.Equipment;
import edu.gdou.gym_java.service.EquipmentService;
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
    public EquipmentController(EquipmentService equipmentService){
        this.equipmentService = equipmentService;
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
}
