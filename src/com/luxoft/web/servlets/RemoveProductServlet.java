package com.luxoft.web.servlets;
import com.luxoft.service.ProductService;
import com.luxoft.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
public class RemoveProductServlet extends HttpServlet {
    private ProductService productService;
    private UserService userService;
    private List<String> userTokens;
    public RemoveProductServlet(ProductService productService, UserService userService, List<String> userTokens) {
        this.productService = productService;
        this.userService = userService;
        this.userTokens = userTokens;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean isAuth = userService.Registered(req, userTokens);
        if (isAuth) {
            int id = Integer.parseInt(req.getParameter("id"));
            productService.removeProduct(id);
            resp.sendRedirect("/");
        } else {
            resp.sendRedirect("/login");
        }
    }
}