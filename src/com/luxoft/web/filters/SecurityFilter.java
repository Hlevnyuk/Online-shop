package com.luxoft.web.filters;
import com.luxoft.service.SecurityService;
import com.luxoft.web.utils.WebUtils;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
public class SecurityFilter implements Filter{
    private SecurityService securityService;
    private List<String> allowedPaths = List.of("/products", "/registration", "/login");
    public SecurityFilter(SecurityService securityService) {
        this.securityService = securityService;
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = WebUtils.getUserToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();
        for (String path : allowedPaths) {
            if (requestURI.startsWith(path)) {
                chain.doFilter(request, response);
                return;
            }
        }
        if (securityService.isAuth(token)) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect("/login");
        }
    }
    @Override
    public void destroy() {

    }
}
