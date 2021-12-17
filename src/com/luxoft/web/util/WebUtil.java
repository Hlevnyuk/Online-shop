package com.luxoft.web.util;
import com.luxoft.entity.Product;
import javax.servlet.http.HttpServletRequest;
public class WebUtil {
    public static Product getProduct(HttpServletRequest req) {
        return  Product.builder()
                .name(req.getParameter("name"))
                .price(Double.parseDouble(req.getParameter("price")))
                .description(req.getParameter("description"))
                .build();

    }
}
