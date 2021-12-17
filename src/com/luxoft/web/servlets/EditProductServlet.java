package com.luxoft.web.servlets;
import com.luxoft.entity.Product;
import com.luxoft.service.ProductService;
import com.luxoft.service.UserService;
import com.luxoft.web.util.WebUtil;
import com.luxoft.web.utils.PageGenerator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
public class EditProductServlet extends HttpServlet{
    private ProductService productService;
    private UserService userService;
    private List<String> userTokens;
    private int idOfEditProduct;
    public EditProductServlet(ProductService productService, UserService userService, List<String> userTokens) {
        this.productService = productService;
        this.userService = userService;
        this.userTokens = userTokens;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean registered = userService.Registered(req, userTokens);
        if (registered) {
            idOfEditProduct = Integer.parseInt(req.getParameter("id"));
            PageGenerator pageGenerator = PageGenerator.instance();
            String page = pageGenerator.getPage("edit_product.html");
            resp.getWriter().write(page);
        } else {
            resp.sendRedirect("/login");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean registered = userService.Registered(req, userTokens);
        if (registered) {
            try {
                Product product = WebUtil.getProduct(req);
                product.setId(idOfEditProduct);
                productService.editProduct(product);
                resp.sendRedirect("/products");
            } catch (Exception e) {
                String errorMessage = "Your product has not been edited";
                PageGenerator pageGenerator = PageGenerator.instance();
                Map<String, Object> parameters = Map.of("errorMessage", errorMessage);
                String page = pageGenerator.getPage("edit_product.html", parameters);
                resp.getWriter().write(page);
            }
        } else {
            resp.sendRedirect("/login");
        }
    }
}
