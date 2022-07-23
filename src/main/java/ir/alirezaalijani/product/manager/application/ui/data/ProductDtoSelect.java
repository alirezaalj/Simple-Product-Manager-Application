package ir.alirezaalijani.product.manager.application.ui.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoSelect {
    private ProductDto productDto;
    private int quantity;
}
