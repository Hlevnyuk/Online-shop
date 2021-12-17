package com.luxoft.web.servlets;
import com.luxoft.entity.Product;
import com.luxoft.service.ProductService;
import com.luxoft.web.utils.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
public class ShowAllProductsServlet extends HttpServlet{
    private ProductService productService;
    private List<String> userTokens;
    public ShowAllProductsServlet(ProductService productService, List<String> userTokens) {
        this.productService = productService;
        this.userTokens = userTokens;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Product> products = productService.findAll();
        PageGenerator pageGenerator = PageGenerator.instance();
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("products", products);
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    if (userTokens.contains(cookie.getValue())) {
                        parameters.put("isAuth", true);
                        break;
                    }
                }
            }
        }
        String page = pageGenerator.getPage("products_list.html", parameters);
        resp.getWriter().write(page);
    }
}