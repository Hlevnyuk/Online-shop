package com.luxoft.web.servlets;
import com.luxoft.entity.Client;
import com.luxoft.service.SecurityService;
import com.luxoft.web.utils.PageGenerator;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class LoginServlet extends HttpServlet{
    private SecurityService securityService;
    public LoginServlet(SecurityService securityService) {
        this.securityService = securityService;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("login.html");
        resp.getWriter().write(page);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token;
        PageGenerator pageGenerator = PageGenerator.instance();
        Client client = Client.builder()
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();
        try {
            token = securityService.login(client);
            Cookie cookie = new Cookie("client-token", token);
            resp.addCookie(cookie);
            resp.sendRedirect("/products");
        } catch (Exception e) {
            String errorMessage = "Wrong email or password!";
            String page = pageGenerator.getPageWithMessage("login.html", errorMessage);
            resp.getWriter().write(page);
        }
    }
}