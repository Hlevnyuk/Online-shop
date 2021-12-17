package com.luxoft.service;
import com.luxoft.dao.ProductDao;
import com.luxoft.entity.Product;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
public class ProductService {
    private ProductDao productDao;
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }
    public List<Product> findAll() {
        List<Product> products = productDao.findAll();
        System.out.println("Obtain products " + products.size());
        return products;
    }
    public void addProduct(Product product) {
        LocalDate now = LocalDate.now();
        product.setPublishDate(now);
        productDao.addProduct(product);
        System.out.println("Add product " + product.getName());
    }
    public void removeProduct(int id) {
        productDao.removeProduct(id);
        System.out.println("Remove product with id " + id);
    }
    public void editProduct(Product product) {
        productDao.editProduct(product);
        System.out.println("Product edited");
    }
}