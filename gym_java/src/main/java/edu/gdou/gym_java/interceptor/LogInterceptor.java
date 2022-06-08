package edu.gdou.gym_java.interceptor;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component("LogInterceptor")
@Slf4j
public class LogInterceptor extends HandlerInterceptorAdapter {
    private static final String START_LOG_TEMPLATE = "[request log]请求执行路径[{}:{}] [content_type:{}][params:{}]";
    private final Gson gson;

    public LogInterceptor(Gson gson) {
        this.gson = gson;
    }

    /**
     * 在请求到达Controller控制器之前 通过拦截器执行一段代码
     * 如果方法返回true,继续执行后续操作
     * 如果返回false，执行中断请求处理，请求不会发送到Controller
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 打印开始日志
        try {
            String method = request.getMethod();
            String url = request.getRequestURI();
            val requestHeader = request.getHeader("content-type");
            String content_type = null;
            String params =null;
            if(requestHeader!=null){
                content_type = requestHeader.contains("multipart/form-data")?"multipart/form-data":requestHeader;
                if("multipart/form-data".equals(content_type)){
                    params = gson.toJson(request.getParameterMap());
                }
//                else{ TODO inputStream 无法多次重复读取
//                    //获取请求body
//                    byte[] bodyBytes = StreamUtils.copyToByteArray(request.getInputStream());
//                    params =new String(bodyBytes, request.getCharacterEncoding());
//                }
            }

            log.info(START_LOG_TEMPLATE, method,url, content_type, params);
        } catch (Exception e) {
            log.error("failed to print request info, url:{} ex:{} params:{}", request.getRequestURI(), e.getMessage(), gson.toJson(request.getParameterMap()), e);
        }
        return true;
    }

    /**
     * 控制器之后，跳转前
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        log.info("[request log]请求执行路径完毕:{}",request.getRequestURI());
    }

    /**
     * 跳转之后执行
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        log.info("最后执行");
    }

}
