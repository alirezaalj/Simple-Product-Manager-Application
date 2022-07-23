package ir.alirezaalijani.product.manager.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sub_factor")
public class SubFactor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private int count;
    @Column(nullable = false)
    private int totalPrice;
    private int profit;
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name="product_id", nullable=false)
    private Product product;
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name="factor_id", nullable=false)
    private Factor factor;

    @Override
    public String toString() {
        return "SubFactor{" +
                "id=" + id +
                ", price=" + price +
                ", count=" + count +
                ", totalPrice=" + totalPrice +
                ", profit=" + profit +
                ", product=" + product +
                '}';
    }
}
