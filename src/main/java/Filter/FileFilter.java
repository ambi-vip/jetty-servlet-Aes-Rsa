package Filter;

import org.eclipse.jetty.server.Request;
import until.TokenRsa;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 对用户身份验证
 * @author Ambi
 * @version 1.0
 * @date 2020/3/3 23:52
 */
@WebFilter(filterName="UrlFilter",urlPatterns="/*")
public class FileFilter implements Filter {

    //使用MultipartConfig需要加上这个配置，不加报MultipartConfig不存在
    private static final MultipartConfigElement MULTI_PART_CONFIG = new MultipartConfigElement("E:\\javacode\\SpringBoot\\test\\fileServe");
    private static final long serialVersionUID = 1L;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        /*  允许跨域访问 */
        response.reset();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.addHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "*");
        response.setContentType("text/html; charset=UTF-8");
        //说明输入的请求信息采用UTF-8编码方式
        request.setCharacterEncoding("utf-8");
        boolean authentication = TokenRsa.Authentication(request, response);
        if (!authentication){
            return;
        }else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
