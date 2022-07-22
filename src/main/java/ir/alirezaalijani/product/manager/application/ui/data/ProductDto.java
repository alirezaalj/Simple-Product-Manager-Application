package ir.alirezaalijani.product.manager.application.ui.data;

import ir.alirezaalijani.jalali.calender.PersianCalender;
import ir.alirezaalijani.product.manager.application.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private int id;
    private int buyPrice;
    private int sellPrice;
    private String name;
    private String description;
    private Date createAt;
    private int existCount;
    private int buyCount;

    public String getCreateAtPersian(){
        if (createAt==null) return null;
       return PersianCalender.getInstance(createAt).getFullDateString();
    }
}
