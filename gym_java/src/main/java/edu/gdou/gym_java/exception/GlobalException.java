package edu.gdou.gym_java.exception;

import edu.gdou.gym_java.entity.bean.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalException {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseBean exception(Exception e) {
        log.error("系统全局发生错误:[出错原因:{},出错信息:{}]",e.getCause(),e.getMessage());
        if (e instanceof AuthorizationException){
            return new ResponseBean(401,"没有权限访问",null);
        }
        return new ResponseBean(500,"出错",e);
    }
}

