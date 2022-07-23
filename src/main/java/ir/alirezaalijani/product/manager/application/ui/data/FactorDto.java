package ir.alirezaalijani.product.manager.application.ui.data;

import ir.alirezaalijani.product.manager.application.model.SubFactor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactorDto {

    private int id;
    private int totalPrice;
    private int finalPrice;
    private int totalProfit;
    private Date createAt;
    private List<SubFactorDto> subFactors;

    public void addSubFactor(SubFactorDto subFactor){
        if (subFactors==null) subFactors=new ArrayList<>();
        subFactors.add(subFactor);
    }
}
