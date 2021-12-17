package com.luxoft.service;
import com.luxoft.dao.ProductDao;
import com.luxoft.entity.Client;
import com.luxoft.entity.Product;
import java.time.LocalDate;
import java.util.List;

public class ProductService {
    private ProductDao productDao;
    private SecurityService securityService;
    public ProductService(ProductDao productDao, SecurityService securityService) {
        this.productDao = productDao;
        this.securityService = securityService;
    }
    public List<Product> findAll() {
        List<Product> products = productDao.findAll();
        System.out.println("Obtain products " + products.size());
        return products;
    }
    public List<Product> getProductsByAuthor(String author){
        List<Product> products = productDao.findProductsByAuthor(author);
        System.out.println("Found " + products.size() + " products from author: " + author);
        return products;
    }
    public void addProduct(Product product, String token) {
        LocalDate now = LocalDate.now();
        Client client = securityService.getUserByToken(token);
        product.setAuthorName(client.getName());
        product.setPublishDate(now);
        productDao.addProduct(product);
        System.out.println("Add product " + product.getName() + " by author: " + client.getName());
    }
    public void removeProduct(int id, String authorName) {
        productDao.removeProduct(id, authorName);
        System.out.println("Remove product with id " + id + "by author: " + authorName);
    }
    public void editProduct(Product product, String authorName) {
        productDao.editProduct(product, authorName);
        System.out.println("Product edited");
    }
}