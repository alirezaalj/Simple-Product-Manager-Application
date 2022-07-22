package ir.alirezaalijani.product.manager.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    private int id;
    @Column(nullable = false)
    private int buyPrice;
    @Column(nullable = false)
    private int sellPrice;
    @Column(length = 200,nullable = false)
    private String name;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Column(nullable = false)
    private int existCount;
    @Column(nullable = false)
    private int buyCount;
}
