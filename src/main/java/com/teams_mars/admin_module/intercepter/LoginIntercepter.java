package com.teams_mars.admin_module.intercepter;

/*
public class LoginIntercepter implements HandlerInterceptor {
//防止用户没登录，通过输入网页地址就可以访问
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
//未登陆，返回登陆页面
            request.setAttribute("msg","请登录");
            request.getRequestDispatcher("/login").forward(request,response);
            return false;
        }else{
//已登陆，放行请求
            return true;
        }

    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

}
*/
