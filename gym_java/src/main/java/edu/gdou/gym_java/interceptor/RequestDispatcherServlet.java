package edu.gdou.gym_java.interceptor;

import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestDispatcherServlet extends DispatcherServlet {

    /**
     * 包装成我们自定义的request
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.doDispatch(new RequestWrapper(request), response);
    }
}