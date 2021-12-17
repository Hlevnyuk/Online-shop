package com.luxoft.web.servlets;
import com.luxoft.entity.Client;
import com.luxoft.service.UserService;
import com.luxoft.web.utils.PageGenerator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
public class RegistrationServlet extends HttpServlet{
    private UserService userService;
    public RegistrationServlet(UserService userService) {
        this.userService = userService;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("registration.html");
        resp.getWriter().write(page);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Client client = Client.builder().
                name(req.getParameter("name"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();
        String email = client.getEmail();
        try {
            if (!userService.isMailAlreadyExist(email)) {
                userService.addUser(client);
                resp.sendRedirect("/");
            } else {
                String errorMessage = "Mail is already registered! Please login or register with new mail.";
                PageGenerator pageGenerator = PageGenerator.instance();
                Map<String, Object> parameters = Map.of("errorMessage", errorMessage);
                String page = pageGenerator.getPage("registration.html", parameters);
                resp.getWriter().write(page);
            }
        } catch (Exception e){
            String errorMessage = "Something wrong!";
            PageGenerator pageGenerator = PageGenerator.instance();
            Map<String, Object> parameters = Map.of("errorMessage", errorMessage);
            String page = pageGenerator.getPage("registration.html", parameters);
            resp.getWriter().write(page);
        }
    }
}