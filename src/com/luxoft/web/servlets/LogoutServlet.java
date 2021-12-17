package com.luxoft.web.servlets;
import com.luxoft.service.SecurityService;
import com.luxoft.web.utils.WebUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class LogoutServlet extends HttpServlet{
    private SecurityService securityService;
    public LogoutServlet(SecurityService securityService) {
        this.securityService = securityService;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = WebUtils.getUserToken(req);
        Cookie cookie = new Cookie("user-token", token);
        if (securityService.removeToken(token)) {
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
            resp.sendRedirect("/products");
        }
    }
}
