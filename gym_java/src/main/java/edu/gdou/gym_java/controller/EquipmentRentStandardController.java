package edu.gdou.gym_java.controller;

import edu.gdou.gym_java.entity.bean.ResponseBean;
import edu.gdou.gym_java.service.EquipmentRentStandardService;
import edu.gdou.gym_java.service.EquipmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * <p>
 * 器材租用标准表 前端控制器
 * </p>
 *
 * @author lzh
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/equipmentRentStandard")
public class EquipmentRentStandardController {
    private final EquipmentRentStandardService equipmentRentStandardService;
    public EquipmentRentStandardController(EquipmentRentStandardService equipmentRentStandardService){
        this.equipmentRentStandardService = equipmentRentStandardService;
    }

    @GetMapping("/queryEquipmentRentStandard")
    public ResponseBean responseBean(){
        return new ResponseBean(200,"查询成功",equipmentRentStandardService.queryEquipmentRentStandard());
    }

    @GetMapping("/queryEquipmentRentStandardByEid")
    public ResponseBean responseBean(@RequestParam("eid")String eid){
        if (StringUtils.isNumeric(eid)){
            return new ResponseBean(200,"查询成功",equipmentRentStandardService.queryEquipmentRentStandardByEid(Integer.parseInt(eid)));
        }else{
            return new ResponseBean(200,"输入的eid为非数字",null);
        }
    }
}
