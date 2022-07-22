package ir.alirezaalijani.product.manager.application.service;

import ir.alirezaalijani.product.manager.application.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    List<Product> findByNameContains(String name);
    Product findProduct(int id);
    boolean addNewProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(int id);
    int suggestId();
}
