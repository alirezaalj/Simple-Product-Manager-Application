package ir.alirezaalijani.product.manager.application.mapper;

import ir.alirezaalijani.product.manager.application.model.Factor;
import ir.alirezaalijani.product.manager.application.model.Product;
import ir.alirezaalijani.product.manager.application.ui.data.FactorDto;
import ir.alirezaalijani.product.manager.application.ui.data.ProductDto;
import ir.alirezaalijani.product.manager.application.ui.data.SubFactorDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectsMapperTest {

    private final ModelMapper modelMapper;

    public ObjectsMapperTest() {
        this.modelMapper = new ModelMapper();
    }

    @Test
    void factorDtoToEntityTest(){
        ProductDto productDto =modelMapper.map(new Product(1,10000,120000,"product1",
                "non",null,10,12),ProductDto.class);
        assertThat(productDto).isNotNull();
        FactorDto factorDto=createFactorDto(productDto);
        List<SubFactorDto> subFactorDtos=factorDto.getSubFactors();

        Factor factor = modelMapper.map(factorDto, Factor.class);
        assertThat(factor.getId()).isEqualTo(1);
        assertThat(factor.getSubFactors()).hasSize(2);
        assertThat(factor.getSubFactors().get(0).getId()).isEqualTo(subFactorDtos.get(0).getId());
        assertThat(factor.getSubFactors().get(1).getId()).isEqualTo(subFactorDtos.get(1).getId());

        assertThat(factor.getSubFactors().get(0).getProduct().getId()).isEqualTo(subFactorDtos.get(0).getProduct().getId());
        assertThat(factor.getSubFactors().get(1).getProduct().getId()).isEqualTo(subFactorDtos.get(1).getProduct().getId());
    }

    public static FactorDto createFactorDto(ProductDto productDto){



        FactorDto factorDto=FactorDto.builder()
                .id(1)
                .createAt(new Date())
                .finalPrice(1500000)
                .totalPrice(1800000)
                .totalProfit(14000)
                .build();
        List<SubFactorDto> subFactorDtos= Arrays.asList(
                SubFactorDto.builder()
                        .factor(factorDto)
                        .id(1).profit(15000).product(productDto)
                        .totalPrice(16000).price(14000)
                        .count(10)
                        .build(),
                SubFactorDto.builder()
                        .factor(factorDto)
                        .id(2).profit(15000).product(productDto)
                        .totalPrice(16000).price(14000)
                        .count(10)
                        .build()
        );

        factorDto.setSubFactors(subFactorDtos);
        return factorDto;
    }
}
