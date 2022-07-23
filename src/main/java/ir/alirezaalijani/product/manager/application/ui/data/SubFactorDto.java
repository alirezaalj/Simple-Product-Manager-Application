package ir.alirezaalijani.product.manager.application.ui.data;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubFactorDto {
    private int id;
    private int price;
    private int count;
    private int totalPrice;
    private int profit;
    private ProductDto product;
    private FactorDto factor;

    @Override
    public String toString() {
        return "SubFactorDto{" +
                "id=" + id +
                ", price=" + price +
                ", count=" + count +
                ", totalPrice=" + totalPrice +
                ", profit=" + profit +
                ", product=" + product +
                '}';
    }
}
