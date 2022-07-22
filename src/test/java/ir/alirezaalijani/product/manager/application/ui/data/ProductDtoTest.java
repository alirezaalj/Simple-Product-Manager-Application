package ir.alirezaalijani.product.manager.application.ui.data;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ProductDtoTest {

    @Test
    void getCreateAtTest() {
        ProductDto productDto=new ProductDto(1,1000,1200,"Product1","", new Date(),10,10);
        System.out.println(productDto.getCreateAt());
    }
}