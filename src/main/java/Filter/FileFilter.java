package Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 对用户身份验证
 * @author Ambi
 * @version 1.0
 * @date 2020/3/3 23:52
 */
@WebFilter(filterName="UrlFilter",urlPatterns="/*")
public class FileFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("jsjsj");
    }

    @Override
    public void destroy() {

    }
}
