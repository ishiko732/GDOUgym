package edu.gdou.gym_java.shiro;

import edu.gdou.gym_java.entity.model.User;
import edu.gdou.gym_java.service.UserService;
import edu.gdou.gym_java.shiro.jwt.JWTToken;
import edu.gdou.gym_java.shiro.redis.Constant;
import edu.gdou.gym_java.shiro.redis.JedisUtil;
import edu.gdou.gym_java.shiro.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@Service
@Slf4j
public class MyRealm extends AuthorizingRealm {


    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = JWTUtil.getUsername(principals.toString());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if(username==null){
            return simpleAuthorizationInfo;
        }
        User user = userService.getUser(username);
        if(user!=null){
            simpleAuthorizationInfo.addRole(user.getRole().getRole());
            Set<String> permission = user.getRole().getPermissions();
            simpleAuthorizationInfo.setStringPermissions(permission);
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        String username = JWTUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token 验证失败(用户信息不存在) "+token);
        }
        if (!JWTUtil.isExp(token)) {
            // 解密获得username，用于和数据库进行对比
            User userBean = userService.getUser(username);
            if (userBean == null || userBean.getId() == null) {
                throw new AuthenticationException("用户不存在");
            }

            if (!JWTUtil.verify(token, username, userBean.getPassword())) {
                SecurityUtils.getSubject().logout();
                throw new AuthenticationException("用户或密码错误");
            } else {
                // 获取RefreshToken的时间戳
                String currentTimeMillisRedis = JedisUtil.getJson(Constant.PREFIX_SHIRO_REFRESH_TOKEN + username);
                // 获取AccessToken时间戳，与RefreshToken的时间戳对比
                if (currentTimeMillisRedis.equals(JWTUtil.getCreateTime(token))) {
                    refreshToken(token,userBean);
                }else{
                    setHeaderToken(token);
                }
                return new SimpleAuthenticationInfo(token, token, "my_realm");
            }
        }
        log.info("[doGetAuthenticationInfo] token是否过期："+JWTUtil.isExp(token));
        throw new AuthenticationException("Token已过期(Token expired or incorrect.)");// 已过期
    }

    /**
     * 此处为AccessToken刷新，进行判断RefreshToken是否过期，未过期就返回新的AccessToken且继续正常访问
     * @param token 当前账号的token
     */
    private String refreshToken(String token,User userBean) {
        String username =userBean.getName();
        // 判断Redis中RefreshToken是否存在
        if (JedisUtil.exists(Constant.PREFIX_SHIRO_REFRESH_TOKEN + username)) {
            // Redis中RefreshToken还存在，获取RefreshToken的时间戳
            String currentTimeMillisRedis =JedisUtil.getJson(Constant.PREFIX_SHIRO_REFRESH_TOKEN + username);
            // 获取当前AccessToken中的时间戳，与RefreshToken的时间戳对比，如果当前时间戳一致，进行AccessToken刷新
            if (currentTimeMillisRedis.equals(JWTUtil.getCreateTime(token))) {
                String new_token = JWTUtil.sign(username, userBean.getPassword());// 刷新AccessToken
                setHeaderToken(new_token);
            }
        }
        return token;
    }

    private void setHeaderToken(String token){
        HttpServletResponse response = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
        // 最后将刷新的AccessToken存放在Response的Header中的Authorization字段返回
        assert response != null;
        response.setHeader("Authorization", token);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        log.info("[MyRealm-setHeaderToken]刷新token");
    }
}
