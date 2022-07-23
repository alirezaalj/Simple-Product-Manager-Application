package ir.alirezaalijani.product.manager.application.service;

import ir.alirezaalijani.product.manager.application.mapper.ObjectsMapperTest;
import ir.alirezaalijani.product.manager.application.model.Factor;
import ir.alirezaalijani.product.manager.application.model.Product;
import ir.alirezaalijani.product.manager.application.repositories.ProductRepository;
import ir.alirezaalijani.product.manager.application.ui.data.FactorDto;
import ir.alirezaalijani.product.manager.application.ui.data.ProductDto;
import ir.alirezaalijani.product.manager.application.ui.data.SubFactorDto;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FactorServiceImplTest {

    @Autowired
    private FactorService factorService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Test
    void addNewFactorTest() {

        ProductDto productDto =modelMapper.map(new Product(1,10000,120000,"product1",
                "non",null,10,12),ProductDto.class);
        Product product = productRepository.save(modelMapper.map(productDto,Product.class));
        assertThat(productDto).isNotNull();
        assertThat(product).isNotNull();

        FactorDto factorDto= ObjectsMapperTest.createFactorDto(productDto);

        List<SubFactorDto> subFactorDtos=factorDto.getSubFactors();

        Factor factor = modelMapper.map(factorDto, Factor.class);

        assertThat(factor.getId()).isEqualTo(1);
        assertThat(factor.getSubFactors()).hasSize(2);
        assertThat(factor.getSubFactors().get(0).getId()).isEqualTo(subFactorDtos.get(0).getId());
        assertThat(factor.getSubFactors().get(1).getId()).isEqualTo(subFactorDtos.get(1).getId());
        assertThat(factor.getSubFactors().get(0).getProduct().getId()).isEqualTo(subFactorDtos.get(0).getProduct().getId());
        assertThat(factor.getSubFactors().get(1).getProduct().getId()).isEqualTo(subFactorDtos.get(1).getProduct().getId());

        factor.getSubFactors()
                        .forEach(subFactor -> subFactor.setProduct(product));

        assertThat(factorService.addNewFactor(factor)).isTrue();


    }
}