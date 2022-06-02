package edu.gdou.gym_java.service;

import edu.gdou.gym_java.GymJavaApplication;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = GymJavaApplication.class)
@Slf4j
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void testDel() {
        val delete = userService.getBaseMapper().deleteById(3);
        System.out.println(delete);
    }

    @Test
    public void testMap() {
        val map = userService.selectInfoByUid(1);
        System.out.println(map.keySet());
        /*
          ==>  Preparing: select * from UserInfo where uid=?
          ==> Parameters: 1(Integer)
          <==    Columns: uid, name, id, phone
          <==        Row: 1, superAdmin2, 001, null
          <==      Total: 1
          [uid, name, id]
         */
    }

    @Test
    public void excelReader() {
        try {
            val fileInputStream = new FileInputStream("/Users/liuyuanfeng/Desktop/gym/软件系2019级信息导入的副本.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            List<Map<String,String>> mapList=new ArrayList<>();
            if (sheet.getPhysicalNumberOfRows()==0){
                return ;
            }
            XSSFRow header = sheet.getRow(0);
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheet.getRow(i);
                Map<String,String> map=new HashMap<>();
                for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                    val headerCell = header.getCell(j);
                    val cell = row.getCell(j);
                    if (headerCell!=null && cell!=null ){
                        map.put(headerCell.toString(), cell.toString());
                    }
                }
                if (map.containsKey("id")&&!"".equals(map.get("id"))){
                    mapList.add(map);
                }
            }
//            mapList.forEach(System.out::println);
            System.out.println("导入成功："+userService.exportInfo(mapList)+"条");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void mapSet(){
        Set<String> list_A= new HashSet<>(Arrays.asList("id", "phone", "truename")); // 数据库给的
        Set<String> list_B= new HashSet<>(Arrays.asList("id","sex","true_name")); // 用户给的
        list_B.removeAll(list_A);
        System.out.println("差集"+list_B);

    }

}