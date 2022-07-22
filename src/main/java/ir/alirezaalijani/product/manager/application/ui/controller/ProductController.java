package ir.alirezaalijani.product.manager.application.ui.controller;

import ir.alirezaalijani.product.manager.application.config.ContextHolder;
import ir.alirezaalijani.product.manager.application.model.Product;
import ir.alirezaalijani.product.manager.application.service.ProductService;
import ir.alirezaalijani.product.manager.application.ui.data.ProductDto;
import ir.alirezaalijani.product.manager.application.ui.util.alert.InfoAlert;
import ir.alirezaalijani.product.manager.application.ui.util.alert.QuAlert;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.modelmapper.ModelMapper;

import java.util.Date;

public class ProductController {

    private final ModelMapper modelMapper;
    private final ProductService productService;
    private ProductDto productDto;
    private final InfoAlert alert;
    private final QuAlert quAlert;

    public ProductController() {
        this.modelMapper = ContextHolder.getInstance().getBean("modelMapper",ModelMapper.class);
        this.productService = ContextHolder.getInstance().getBean("productService",ProductService.class);
        this.productDto= getInitializeProduct();
        alert=InfoAlert.getInstance();
        quAlert=QuAlert.getInstance();
    }

    @FXML
    private Label lblCode;

    @FXML
    private Label lblDate;

    @FXML
    private TextField txtBuyCount;

    @FXML
    private TextField txtBuyPrice;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtExistCount;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSellPrice;


    @FXML
    void initialize() {
        Platform.runLater(()->{
            if (this.productDto.getId()==0){
                this.productDto.setId(this.productService.suggestId());
                lblCode.setText("Code: "+this.productDto.getId());
                Date date=new Date();
                this.productDto.setCreateAt(date);
                lblDate.setText("Add Date: "+ this.productDto.getCreateAtPersian());
                txtDescription.setText("");
                txtName.setText("");
                txtBuyPrice.setText("");
                txtSellPrice.setText("");
                txtExistCount.setText("0");
                txtBuyCount.setText("0");
            }else {
                lblCode.setText("Code: "+this.productDto.getId());
                if (this.productDto.getCreateAt()==null){
                    Date date=new Date();
                    this.productDto.setCreateAt(date);
                    lblDate.setText("Add Date: "+ this.productDto.getCreateAtPersian());
                }else {
                    lblDate.setText("Add Date: "+this.productDto.getCreateAtPersian());
                }
                if (this.productDto.getBuyPrice()>0){
                    txtBuyPrice.setText(String.valueOf(this.productDto.getBuyPrice()));
                }
                if (this.productDto.getSellPrice()>0){
                    txtSellPrice.setText(String.valueOf(this.productDto.getSellPrice()));
                }
                if (this.productDto.getName()!=null){
                    txtName.setText(this.productDto.getName());
                }
                if (this.productDto.getDescription()!=null){
                    txtDescription.setText(this.productDto.getDescription());
                }
                txtBuyCount.setText(String.valueOf(this.productDto.getBuyCount()));
                txtExistCount.setText(String.valueOf(this.productDto.getExistCount()));
            }
        });
    }

    @FXML
    void onSave(MouseEvent event) {

        if (txtName.getText().trim().equals("")){
            alert.showW("Filed Required","Product Name is Required!");
            return;
        }else {
            this.productDto.setName(txtName.getText().trim());
        }
        if (!txtBuyCount.getText().trim().equals("")&&!txtExistCount.getText().trim().equals("")){
            Integer buyCountValue=getIntValue(txtBuyCount.getText().trim());
            Integer existCountValue=getIntValue(txtExistCount.getText().trim());
            if (buyCountValue==null || existCountValue==null){
                alert.showW("Filed Required","Pleas Enter Valid Number in Filed (Buy Count) or (Exist Count)");
                return;
            }
            if (buyCountValue<=0 || existCountValue <=0){
                alert.showW("Filed Required","Number in Filed (Buy Count) or (Exist Count) Cannot be 0 or Less!");
                return;
            }
            this.productDto.setBuyCount(buyCountValue);
            this.productDto.setExistCount(existCountValue);
        }
        if (!txtBuyPrice.getText().trim().equals("")&&!txtSellPrice.getText().trim().equals("")){
            Integer buyPriceValue=getIntValue(txtBuyPrice.getText().trim());
            Integer sellPriceValue=getIntValue(txtSellPrice.getText().trim());
            if (buyPriceValue==null || sellPriceValue==null){
                alert.showW("Filed Required","Pleas Enter Valid Number in Filed (Buy Price) or (Sell Price)");
                return;
            }
            if (buyPriceValue<=0 || sellPriceValue <=0){
                alert.showW("Filed Required","Number in Filed (Buy Count) or (Exist Count) Cannot be 0 or Less!");
                return;
            }
            this.productDto.setBuyPrice(buyPriceValue);
            this.productDto.setSellPrice(sellPriceValue);
        }
        if (!txtDescription.getText().equals("")){
            this.productDto.setDescription(txtDescription.getText().trim());
        }
        Product product=convertToEntity(this.productDto);
        boolean result =productService.addNewProduct(product);
        if (result){
           boolean addNew= quAlert.show("Success","Adding new Product was Successful. Do you want add other new product?");
           if (addNew){
               this.productDto= getInitializeProduct();
               initialize();
           }else {
               Stage stage = (Stage)((Node)(event.getSource())).getScene().getWindow();
               stage.fireEvent(new WindowEvent(stage,WindowEvent.WINDOW_CLOSE_REQUEST));
           }
        }else {
            alert.showW("Failed","Adding new Product Failed. Please Try again!");
        }
    }

    private ProductDto getInitializeProduct(){
        return new ProductDto(0,0,0,null,null,null,0,0);
    }

    private Integer getIntValue(String number){
        try {
            return Integer.valueOf(number);
        }catch (Exception e){
           return null;
        }
    }
    public void isUpdate(int id){
        if (id!=0){
            Product product = this.productService.findProduct(id);
            if (product!=null){
                this.productDto = convertToDto(product);
            }
        }
    }

    private ProductDto convertToDto(Product entity){
        return this.modelMapper.map(entity,ProductDto.class);
    }
    private Product convertToEntity(ProductDto dto){
        return this.modelMapper.map(dto,Product.class);
    }
}

