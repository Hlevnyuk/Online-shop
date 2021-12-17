package com.luxoft.web.servlets;
import com.luxoft.entity.Product;
import com.luxoft.service.ProductService;
import com.luxoft.web.utils.PageGenerator;
import com.luxoft.web.utils.WebUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class AddProductServlet extends HttpServlet{
    private ProductService productService;
    public AddProductServlet(ProductService productService) {
        this.productService = productService;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("add_product.html");
        resp.getWriter().write(page);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String token = WebUtils.getUserToken(req);
        try {
            Product product = WebUtils.getProduct(req);
            productService.addProduct(product, token);
            resp.sendRedirect("/products");
        } catch (Exception e) {
            String errorMessage = "Your product has not been added! Please, enter correct data in the fields";
            String page = pageGenerator.getPageWithMessage("add_product.html", errorMessage);
            resp.getWriter().write(page);
        }
    }
}