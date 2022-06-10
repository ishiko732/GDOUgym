package edu.gdou.gym_java.exception;

import edu.gdou.gym_java.entity.bean.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RestControllerAdvice
@Slf4j
public class GlobalException {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseBean exception(Exception e) {
        if (e instanceof AuthorizationException){
            return new ResponseBean(401,"没有权限访问",null);
        }else if(e instanceof UnknownAccountException){
            return new ResponseBean(403,"操作被禁止",e.getMessage());
        }else if(e instanceof DataIntegrityViolationException){
            return new ResponseBean(403,"数据库操作失败,已回滚",e.getMessage());
        }
        log.error("系统全局发生错误:[出错原因:{},出错信息:{}]",e.getCause(),e.getMessage());
        return new ResponseBean(500,"出错",e);
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomUnauthorizedException.class)
    @ResponseBody
    public ResponseBean UnauthorizedException(CustomUnauthorizedException e) {
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

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseBean requestMissingServletRequest(Exception ex) {
        return new ResponseBean(400, "参数异常" , ex.getMessage());
    }
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseBean dataIntegrityViolationException(Exception e) {
        return new ResponseBean(0,"数据库操作异常",e.getMessage());
    }
}

