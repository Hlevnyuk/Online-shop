package com.luxoft.web.servlets;
import com.luxoft.entity.Product;
import com.luxoft.service.ProductService;
import com.luxoft.service.SecurityService;
import com.luxoft.web.utils.PageGenerator;
import com.luxoft.web.utils.WebUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
public class ShowAllProductsServlet extends HttpServlet{
    private ProductService productService;
    private SecurityService securityService;
    public ShowAllProductsServlet(ProductService productService, SecurityService securityService) {
        this.productService = productService;
        this.securityService = securityService;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        List<Product> products = productService.findAll();
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("products", products);
        String token = WebUtils.getUserToken(req);
        boolean isAuth = securityService.isAuth(token);
        if (isAuth) {
            parameters.put("isAuth", true);
        }
        String page = pageGenerator.getPage("product_list.html", parameters);
        resp.getWriter().write(page);
    }
}