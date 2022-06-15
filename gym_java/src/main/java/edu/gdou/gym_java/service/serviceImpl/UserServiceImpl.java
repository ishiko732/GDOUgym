package edu.gdou.gym_java.service.serviceImpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gdou.gym_java.entity.model.User;
import edu.gdou.gym_java.mapper.UserMapper;
import edu.gdou.gym_java.service.UserService;
import edu.gdou.gym_java.shiro.jwt.JWTUtil;
import edu.gdou.gym_java.utils.MD5;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-05-25
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final MD5 md5;

    public UserServiceImpl(MD5 md5) {
        this.md5 = md5;
    }

    @Override
    public User currentUser() {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String username = JWTUtil.getUsername(token);
        if (username!=null){
            User user = getUser(username);
            user.setPassword(null);
            return user;
        }else{
            return null;
        }
    }

    /**
     * 注册信息
     * @param user 用户信息
     * @param id 学工号
     * @return true 插入成功，false 插入失败，null 学工号未得到验证
     */
    @Override
    public Boolean register(User user,String id) {
        val md5_password = md5.md5(user.getPassword());
        user.setPassword(md5_password);
        //验证学工号
        val map = getBaseMapper().selectInfoById(id);
        if (map!=null){
            val insert = getBaseMapper().insert(user);
            if (insert==1){
                return getBaseMapper().updateUserInfo(map.get("id").toString(),user.getId(),user.getName());
            }else{
                return false;
            }
        }else{
            return null;
        }
    }

    @Override
    public Map<String,Integer> exportInfo(List<Map<String, String>> mapList) {
//        mapList.stream().mapToInt(map -> getBaseMapper().exportInfo(map)).sum();
        int insert_cnt =0 ;
        int update_cnt =0 ;
        val row = getBaseMapper().getUserInfoRow();
        for (Map<String, String> map : mapList) {
//            val remove_key = map.keySet().removeAll(row);// 取差集，x属于用户数据，但x不属于数据库的数据
            map.keySet().removeIf(key->!row.contains(key));
            try{
                insert_cnt+=getBaseMapper().exportInfo(map);
            }catch (Exception e){
                val id = map.get("id");
                map.remove("id");
                val info = getBaseMapper().updateInfo(id, map);
                if(info!=0){
                    update_cnt+=info;
                    log.warn("更新"+id+"到用户表成功："+map);
                }else {
                    log.error("导入到用户表失败："+map);
                }
            }
        }
        Map<String,Integer> ret =new HashMap<>();
        ret.put("insert",insert_cnt);
        ret.put("update",update_cnt);
        return ret;
    }
    @Override
    public Map<String,Integer> exportInfoByFile(MultipartFile excel){
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(excel.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            List<Map<String,String>> mapList=new ArrayList<>();
            if (sheet.getPhysicalNumberOfRows()==0){
                return null;
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
            //System.out.println("导入成功："+exportInfo(mapList)+"条");
            return this.exportInfo(mapList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUser(@NonNull String username) {
        final User user = getBaseMapper().getUserByName(username);
        if (Objects.isNull(user)) {
            return null;
        }
        return user;
    }

    @Override
    public List<User> queryManagerByUsername(String username) {
        return getBaseMapper().queryManagerByName(username);
    }

    @Override
    public Boolean addManager(User user) {
        val md5_password = md5.md5(user.getPassword());
        user.setPassword(md5_password);
        val insert = getBaseMapper().insert(user);
        return insert == 1;
    }

    @Override
    public Boolean deleteManager(Integer ID) {
        val delete = getBaseMapper().deleteById(ID);
        return delete == 1;
    }

    @Override
    public User queryUserByID(Integer ID) {
        return getBaseMapper().selectById(ID);
    }

    @Override
    public IPage<User> selectUserPage(Page<User> page) {
        return getBaseMapper().selectPageUsers(page);
    }

    @Override
    public Boolean changePassword(@NonNull String username, @Nullable String prePassword, String newPassword, Boolean isForced) {
        User user = getUser(username);
        String pre_md5 ;
        String new_md5 ;
        if (user!=null && isForced) {
            pre_md5=user.getPassword();
        }else if (prePassword!=null){
            pre_md5=md5.md5(prePassword);
        }else{
            return false;
        }
        new_md5 = md5.md5(newPassword);

        if (user!=null && user.getPassword().equals(pre_md5)){
            user.setPassword(new_md5);
            return getBaseMapper().updateById(user)!=0;
        }else{
            return false;
        }
    }

    @Override
    public Map<String, Object> selectInfoByUid(@NonNull Integer id) {
        return getBaseMapper().selectInfoByUid(id);
    }

    @Override
    public Map<String, Object> selectInfoById(@NonNull String id) {
        return getBaseMapper().selectInfoById(id);
    }

    @Override
    public Set<Map<String, Object>> getUserListBySingle(Integer uid,String name,String truename) {
        return getBaseMapper().getUserListBySingle(uid,name,truename);
    }
}
