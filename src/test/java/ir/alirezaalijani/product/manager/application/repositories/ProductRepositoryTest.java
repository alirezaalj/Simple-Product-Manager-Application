package ir.alirezaalijani.product.manager.application.repositories;

import ir.alirezaalijani.product.manager.application.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    void getHighestIdTest() {
        Integer suggestId=productRepository.getHighestId();
        assertThat(suggestId).isNull();
        Product product =productRepository.save(createProduct());
        product=productRepository.findById(product.getId()).orElse(null);
        assertThat(product).isNotNull();
        suggestId=productRepository.getHighestId();
        assertThat(suggestId).isEqualTo(1);
    }

    private Product createProduct(){
        return new Product(1,10000,120000,"product1","non",null,10,12);
    }
}