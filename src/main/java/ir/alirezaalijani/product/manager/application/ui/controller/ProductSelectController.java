package ir.alirezaalijani.product.manager.application.ui.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import ir.alirezaalijani.product.manager.application.config.ContextHolder;
import ir.alirezaalijani.product.manager.application.model.Product;
import ir.alirezaalijani.product.manager.application.service.ProductService;
import ir.alirezaalijani.product.manager.application.ui.data.ProductDto;
import ir.alirezaalijani.product.manager.application.ui.data.ProductDtoSelect;
import ir.alirezaalijani.product.manager.application.ui.service.ViewManager;
import ir.alirezaalijani.product.manager.application.ui.util.alert.InfoAlert;
import ir.alirezaalijani.product.manager.application.ui.util.alert.QuAlert;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.modelmapper.ModelMapper;

public class ProductSelectController {

    private ObservableList<ProductDto> productDtos;
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final QuAlert quAlert;
    private final InfoAlert alert;
    private ProductDto selectedProduct;
    private ProductDtoSelect productDtoSelect;

    public ProductSelectController() {
        this.productService = ContextHolder.getInstance().getBean("productService", ProductService.class);
        this.modelMapper = ContextHolder.getInstance().getBean("modelMapper", ModelMapper.class);
        this.quAlert = QuAlert.getInstance();
        this.alert = InfoAlert.getInstance();
    }

    @FXML
    private Label lblFormTitle;

    @FXML
    private TableView<ProductDto> productTable;

    @FXML
    private TextField txtFindByCode;

    @FXML
    private TextField txtFindByName;

    @FXML
    private TextField txtQuantity;

    @FXML
    void initialize() {
        this.productDtos = productTable.getItems();
        Platform.runLater(this::loadProducts);
        this.txtQuantity.setText("1");
        productTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, o, n) -> {
                    if (n != null) {
                        this.selectedProduct=n;
                        if (selectedProduct.getExistCount() > 0) {
                            productDtos.clear();
                            productDtos.add(selectedProduct);
                            txtFindByName.setText(selectedProduct.getName());
                            txtFindByCode.setText(String.valueOf(selectedProduct.getId()));
                        } else {
                            alert.showW("Not Exist", "Selected Product has 0 quantity!");
                        }
                    } else {
                        System.out.println("product null");
                    }
                });
    }


    @FXML
    void onDecreaseQuantity(MouseEvent event) {
        if (selectedProduct != null) {
            Integer quantity = null;
            try {
                quantity = Integer.parseInt(txtQuantity.getText());
                if (quantity > 1) {
                    quantity--;
                    txtQuantity.setText(String.valueOf(quantity));
                }
            } catch (Exception ignored) {

            }
        }
    }

    @FXML
    void onIncreaseQuantity(MouseEvent event) {
        if (selectedProduct != null) {
            Integer quantity = null;
            try {
                quantity = Integer.parseInt(txtQuantity.getText());
                if (quantity < selectedProduct.getExistCount()) {
                    quantity++;
                    txtQuantity.setText(String.valueOf(quantity));
                }
            } catch (Exception ignored) {

            }
        }
    }

    @FXML
    void onFindByCode(MouseEvent event) {
        try {
            this.txtQuantity.setText("1");
            this.txtFindByName.setText("");
            int code = Integer.parseInt(txtFindByCode.getText().trim());
            this.productDtos.clear();
            Product product = productService.findProduct(code);
            this.productDtos.add(modelMapper.map(product, ProductDto.class));
        } catch (Exception ignored) {

        }
    }

    @FXML
    void onFindByName(MouseEvent event) {
        String name = txtFindByName.getText().trim();
        this.txtFindByCode.setText("");
        if (name.equals("")) {
            loadProducts();
        } else {
            System.out.println(name);
            loadProducts(name);
        }
    }

    private void loadProducts() {
        this.txtQuantity.setText("1");
        this.productDtos.clear();
        List<ProductDto> productDtoList = productService.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, ProductDto.class))
                .collect(Collectors.toList());
        this.productDtos.addAll(productDtoList);
    }

    private void loadProducts(String name) {
        this.txtQuantity.setText("1");
        this.productDtos.clear();
        List<ProductDto> productDtoList = productService.findByNameContains(name)
                .stream()
                .map(entity -> modelMapper.map(entity, ProductDto.class))
                .collect(Collectors.toList());
        this.productDtos.addAll(productDtoList);
    }

    @FXML
    public void onEnterFindByName(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            this.txtFindByCode.setText("");
            String name = txtFindByName.getText().trim();
            if (name.equals("")) {
                loadProducts();
            } else {
                System.out.println(name);
                loadProducts(name);
            }
        }
    }

    @FXML
    public void onEnterFindById(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            this.txtFindByName.setText("");
            this.txtQuantity.setText("1");
            try {
                int code = Integer.parseInt(txtFindByCode.getText().trim());
                this.productDtos.clear();
                Product product = productService.findProduct(code);
                this.productDtos.add(modelMapper.map(product, ProductDto.class));
            } catch (Exception ignored) {

            }
        }
    }

    @FXML
    void onSave(MouseEvent event) {
        if (this.selectedProduct != null && this.selectedProduct.getExistCount()>=Integer.parseInt(this.txtQuantity.getText())) {
            Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        } else {
            alert.show("Select Product", "Please Select Product From table!");
        }
    }

    @FXML
    void onCancel(MouseEvent event) {
        this.selectedProduct = null;
        Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public ProductDtoSelect getProductDtoSelect() {
        if (this.selectedProduct != null) {
            if (this.productDtoSelect == null) {
                this.productDtoSelect = ProductDtoSelect.builder()
                        .productDto(this.selectedProduct)
                        .quantity(Integer.parseInt(this.txtQuantity.getText()))
                        .build();
            }
        }
        return this.productDtoSelect;
    }
}
