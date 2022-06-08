package edu.gdou.gym_java.exception;

import edu.gdou.gym_java.entity.bean.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@Slf4j
public class GlobalException {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseBean exception(Exception e) {
        if (e instanceof AuthorizationException){
            return new ResponseBean(401,"没有权限访问",null);
        }else if(e instanceof UnknownAccountException){
            log.warn(e.getMessage(), e);
            log.info("catch UnknownAccountException");
            return new ResponseBean(403,"操作被禁止",e.getMessage());
        }
        log.error("系统全局发生错误:[出错原因:{},出错信息:{}]",e.getCause(),e.getMessage());
        return new ResponseBean(500,"出错",e);
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomUnauthorizedException.class)
    @ResponseBody
    public ResponseBean UnauthorizedException(CustomUnauthorizedException e) {
        log.error("系统全局发生错误:[出错原因:{},出错信息:{}]",e.getCause(),e.getMessage());
        return new ResponseBean(403,"操作被禁止",e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AuthenticationException.class, UnknownAccountException.class,
            UnauthenticatedException.class, IncorrectCredentialsException.class})
    @ResponseBody
    public ResponseBean unauthorized(Exception e) {
        if (e instanceof AuthorizationException){
            return new ResponseBean(401,"没有权限访问",null);
        }else {
            return new ResponseBean(403,"操作被禁止",e.getMessage());
        }
    }
}

