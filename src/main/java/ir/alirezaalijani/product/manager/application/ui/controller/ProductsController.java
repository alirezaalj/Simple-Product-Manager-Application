package ir.alirezaalijani.product.manager.application.ui.controller;

import ir.alirezaalijani.product.manager.application.config.ContextHolder;
import ir.alirezaalijani.product.manager.application.model.Product;
import ir.alirezaalijani.product.manager.application.service.ProductService;
import ir.alirezaalijani.product.manager.application.ui.data.ProductDto;
import ir.alirezaalijani.product.manager.application.ui.service.ViewManager;
import ir.alirezaalijani.product.manager.application.ui.service.fxmlcontent.FxmlContent;
import ir.alirezaalijani.product.manager.application.ui.service.fxmlcontent.ViewLoadType;
import ir.alirezaalijani.product.manager.application.ui.util.ViewPath;
import ir.alirezaalijani.product.manager.application.ui.util.alert.QuAlert;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ProductsController {

    private final ViewManager viewManager;
    private final BorderPane rootPane;
    private ObservableList<ProductDto> productDtos;
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final QuAlert quAlert;

    public ProductsController() {
        this.rootPane = ContextHolder.getInstance().getBean("RootPane",BorderPane.class);
        this.viewManager= ContextHolder.getInstance().getBean("ViewManager",ViewManager.class);
        this.productService = ContextHolder.getInstance().getBean("productService",ProductService.class);
        this.modelMapper=ContextHolder.getInstance().getBean("modelMapper",ModelMapper.class);
        this.quAlert=QuAlert.getInstance();
    }

    @FXML
    private TableView<ProductDto> dataTable;

    @FXML
    private ProgressIndicator mainProgress;

    @FXML
    private TextField txtFindByCode;

    @FXML
    private TextField txtFindByName;

    @FXML
    void initialize() {
        mainProgress.setVisible(false);
        this.productDtos=dataTable.getItems();
        Platform.runLater(this::loadProducts);
    }

    @FXML
    public void onBack(MouseEvent mouseEvent) {
        FxmlContent content = viewManager.popView();
        rootPane.setCenter(content.getContentPane());
    }

    @FXML
    public void onAddNewProduct(MouseEvent mouseEvent) {
       loadProductView(0);
    }

    @FXML
    public void onEditProduct(MouseEvent mouseEvent) {
        ProductDto selectedProduct =dataTable.getSelectionModel().getSelectedItem();
        if (selectedProduct!=null) {
            loadProductView(selectedProduct.getId());
        }
    }

    private void loadProductView(int id){
        FxmlContent content= viewManager.getView(ViewPath.ViewProduct,ViewLoadType.STAGE_PURE);
        Stage stage =content.getStage();
        ProductController controller =content.getLoader().getController();
        if (controller!=null) controller.isUpdate(id);
        if (!stage.isShowing()){
            stage.show();
            stage.setTitle("Add New Product");
            stage.getIcons().add(new Image(ViewPath.imgApplicationIcon));
            stage.setMaximized(false);
            stage.setOnCloseRequest(event ->{
                System.out.println("ProductController close event");
                viewManager.removeView(ViewPath.ViewProduct);
                loadProducts();
            });
        }
    }

    @FXML
    public void onDeleteProduct(MouseEvent mouseEvent) {
        ProductDto selectedProduct =dataTable.getSelectionModel().getSelectedItem();
        if (selectedProduct!=null){
            if (quAlert.show("Delete Item",String.format("Do you want to delete product(code:%s ,name:%s) ?",selectedProduct.getId(),selectedProduct.getName()))){
                boolean result = productService.deleteProduct(selectedProduct.getId());
                loadProducts();
            }
        }
    }

    @FXML
    public void onFindByName(MouseEvent mouseEvent) {
        String name=txtFindByName.getText().trim();
        if (name.equals("")){
            loadProducts();
        }else {
            System.out.println(name);
            loadProducts(name);
        }
    }

    @FXML
    public void onFindByCode(MouseEvent mouseEvent) {
        try {
            int code=Integer.parseInt(txtFindByCode.getText().trim());
            this.productDtos.clear();
            Product product = productService.findProduct(code);
            this.productDtos.add(modelMapper.map(product,ProductDto.class));
        }catch (Exception ignored){

        }
    }

    private void loadProducts(){
        this.productDtos.clear();
        List<ProductDto> productDtoList =productService.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity,ProductDto.class))
                .collect(Collectors.toList());
        this.productDtos.addAll(productDtoList);
    }

    private void loadProducts(String name){
        this.productDtos.clear();
        List<ProductDto> productDtoList=productService.findByNameContains(name)
                .stream()
                .map(entity -> modelMapper.map(entity,ProductDto.class))
                .collect(Collectors.toList());
        this.productDtos.addAll(productDtoList);
    }

    public void onEnterFindByName(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            String name=txtFindByName.getText().trim();
            if (name.equals("")){
                loadProducts();
            }else {
                System.out.println(name);
                loadProducts(name);
            }
        }
    }

    public void onEnterFindById(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            try {
                int code=Integer.parseInt(txtFindByCode.getText().trim());
                this.productDtos.clear();
                Product product = productService.findProduct(code);
                this.productDtos.add(modelMapper.map(product,ProductDto.class));
            }catch (Exception ignored){

            }
        }
    }
}
