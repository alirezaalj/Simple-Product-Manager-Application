package ir.alirezaalijani.product.manager.application.service;

import ir.alirezaalijani.product.manager.application.model.Product;
import ir.alirezaalijani.product.manager.application.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("productService")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll(Sort.by("id"));
    }

    @Override
    public List<Product> findByNameContains(String name) {
        return productRepository.findAllByNameContaining(name);
    }

    @Override
    public Product findProduct(int id) {
        return productRepository.findById(id)
                .orElse(null);
    }

    @Override
    public boolean addNewProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        log.info("Add new product with name:{}",product.getName());
        return savedProduct.getId() > 0;
    }

    @Override
    public boolean updateProduct(Product product) {
        if (product.getId()<=0) return false;
        Product savedProduct = productRepository.save(product);
        log.info("Update product with Id:{}",product.getId());
        return savedProduct.getId()==product.getId();
    }

    @Override
    public boolean deleteProduct(int id) {
        productRepository.deleteById(id);
        log.info("Delete product with id:{}",id);
        return true;
    }

    @Override
    public int suggestId() {
        Integer highestId=productRepository.getHighestId();
        if (highestId==null){
            return 1;
        }
        return highestId+1;
    }
}
