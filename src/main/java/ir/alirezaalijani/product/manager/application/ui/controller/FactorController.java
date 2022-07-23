package ir.alirezaalijani.product.manager.application.ui.controller;

import com.jfoenix.controls.JFXRadioButton;
import ir.alirezaalijani.product.manager.application.config.ContextHolder;
import ir.alirezaalijani.product.manager.application.model.Factor;
import ir.alirezaalijani.product.manager.application.service.FactorService;
import ir.alirezaalijani.product.manager.application.ui.data.FactorDto;
import ir.alirezaalijani.product.manager.application.ui.data.ProductDtoSelect;
import ir.alirezaalijani.product.manager.application.ui.data.SubFactorDto;
import ir.alirezaalijani.product.manager.application.ui.service.ViewManager;
import ir.alirezaalijani.product.manager.application.ui.service.fxmlcontent.FxmlContent;
import ir.alirezaalijani.product.manager.application.ui.service.fxmlcontent.ViewLoadType;
import ir.alirezaalijani.product.manager.application.ui.util.ViewPath;
import ir.alirezaalijani.product.manager.application.ui.util.alert.InfoAlert;
import ir.alirezaalijani.product.manager.application.ui.util.alert.QuAlert;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FactorController {

    private final ViewManager viewManager;
    private final ModelMapper modelMapper;
    private final QuAlert quAlert;
    private final InfoAlert alert;
    private final FactorService factorService;
    private ObservableList<SubFactorDto> subFactorDtos;
    private ToggleGroup toggleGroup;
    private FactorDto factorDto;

    public FactorController() {
        this.viewManager = ContextHolder.getInstance().getBean("ViewManager", ViewManager.class);
        this.factorService = ContextHolder.getInstance().getBean("factorService", FactorService.class);
        this.modelMapper = ContextHolder.getInstance().getBean("modelMapper", ModelMapper.class);
        this.quAlert = QuAlert.getInstance();
        this.alert = InfoAlert.getInstance();
        toggleGroup = new ToggleGroup();
        this.factorDto = new FactorDto();
    }

    @FXML
    private Label lblFormTitle;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private Label lblTotalProfit;

    @FXML
    private JFXRadioButton rdb0Prs;

    @FXML
    private JFXRadioButton rdb10Prs;

    @FXML
    private JFXRadioButton rdb15Prs;

    @FXML
    private JFXRadioButton rdb5Prs;

    @FXML
    private JFXRadioButton rdb7Prs;

    @FXML
    private TableView<SubFactorDto> subFactorTable;
    private final TableColumn<SubFactorDto, String> column4 = new TableColumn<>("Product Code");
    private final TableColumn<SubFactorDto, String> column5 = new TableColumn<>("Product Name");

    @FXML
    private TextField txtCustomPrs;

    @FXML
    private TextField txtFinalPrice;

    @FXML
    void initialize() {
        rdb0Prs.setToggleGroup(this.toggleGroup);
        rdb5Prs.setToggleGroup(this.toggleGroup);
        rdb7Prs.setToggleGroup(this.toggleGroup);
        rdb10Prs.setToggleGroup(this.toggleGroup);
        rdb15Prs.setToggleGroup(this.toggleGroup);
        rdb0Prs.setSelected(true);
        this.subFactorDtos = this.subFactorTable.getItems();

        column4.setPrefWidth(112.0);
        column4.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getProduct().getId())));
        this.subFactorTable.getColumns().set(4, column4);

        column5.setPrefWidth(132.0);
        column5.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getProduct().getName())));
        this.subFactorTable.getColumns().set(5, column5);

        rdb0Prs.setOnAction(event -> calculateOffer());
        rdb5Prs.setOnAction(event -> calculateOffer());
        rdb7Prs.setOnAction(event -> calculateOffer());
        rdb10Prs.setOnAction(event -> calculateOffer());
        rdb15Prs.setOnAction(event -> calculateOffer());
    }

    private void calculateOffer() {
        calculateTotals();
        if (this.factorDto.getTotalPrice() > 0) {
            if (rdb0Prs.isSelected()) {
                this.factorDto.setFinalPrice(this.factorDto.getTotalPrice());
            }
            if (rdb5Prs.isSelected()) {
                this.factorDto.setFinalPrice(this.factorDto.getTotalPrice() - calculatePercentage(this.factorDto.getTotalPrice(), 5));
            }
            if (rdb7Prs.isSelected()) {
                this.factorDto.setFinalPrice(this.factorDto.getTotalPrice() - calculatePercentage(this.factorDto.getTotalPrice(), 7));
            }
            if (rdb10Prs.isSelected()) {
                this.factorDto.setFinalPrice(this.factorDto.getTotalPrice() - calculatePercentage(this.factorDto.getTotalPrice(), 10));
            }
            if (rdb15Prs.isSelected()) {
                this.factorDto.setFinalPrice(this.factorDto.getTotalPrice() - calculatePercentage(this.factorDto.getTotalPrice(), 15));
            }
            int totalOffer = this.factorDto.getTotalPrice() - this.factorDto.getFinalPrice();
            if (this.factorDto.getTotalProfit() - totalOffer <= 0) {
                this.lblTotalProfit.setStyle("-fx-text-fill: #b51c1c");
            } else {
                this.lblTotalProfit.setStyle("-fx-text-fill: #000000");
            }
            this.factorDto.setTotalProfit(this.factorDto.getTotalProfit() - totalOffer);
            this.lblTotalProfit.setText(String.valueOf(this.factorDto.getTotalProfit()));
            this.txtFinalPrice.setText(String.valueOf(this.factorDto.getFinalPrice()));
        }

    }

    private int calculatePercentage(int price, int present) {
        return price * present / 100;
    }

    @FXML
    void onAddToBasket(MouseEvent mouseEvent) {
        URL view = ViewPath.ViewProductSelect;
        FxmlContent content = viewManager.getView(view, ViewLoadType.STAGE_PURE);
        Stage stage = content.getStage();
        ProductSelectController controller = content.getLoader().getController();
        if (!stage.isShowing()) {
            stage.show();
            stage.setTitle("Find Product");
            stage.getIcons().add(new Image(ViewPath.imgApplicationIcon));
            stage.setMaximized(false);
            stage.setOnCloseRequest(event -> {
                System.out.println("ProductSelectController close event");
                if (controller != null) {
                    if (controller.getProductDtoSelect() != null) {
                        addSubFactorToList(controller.getProductDtoSelect());
                    }
                }
                ((Stage) this.subFactorTable.getScene().getWindow()).show();
                viewManager.removeView(view);
            });
            if (stage.isShowing()) {
                this.subFactorTable.getScene().getWindow().hide();
            }
        }
    }

    private void addSubFactorToList(ProductDtoSelect productDtoSelect) {
        boolean existInList = false;
        for (SubFactorDto subFactorDto : subFactorDtos) {
            if (subFactorDto.getProduct().getId() == productDtoSelect.getProductDto().getId()) {
                existInList = true;
                break;
            }
        }
        if (!existInList) {
            subFactorDtos.add(SubFactorDto.builder()
                    .factor(this.factorDto)
                    .id(0)
                    .count(productDtoSelect.getQuantity())
                    .price(productDtoSelect.getProductDto().getSellPrice())
                    .totalPrice(productDtoSelect.getQuantity() * productDtoSelect.getProductDto().getSellPrice())
                    .profit(productDtoSelect.getQuantity() * productDtoSelect.getProductDto().getSellPrice() - productDtoSelect.getQuantity() * productDtoSelect.getProductDto().getBuyPrice())
                    .product(productDtoSelect.getProductDto())
                    .build());
        } else {
            boolean refreshList=false;
            for (SubFactorDto subFactor : subFactorDtos) {
                if (subFactor.getProduct().getId() == productDtoSelect.getProductDto().getId() && subFactor.getCount() != productDtoSelect.getQuantity()) {
                    subFactor.setCount(productDtoSelect.getQuantity());
                    subFactor.setTotalPrice(productDtoSelect.getQuantity() * productDtoSelect.getProductDto().getSellPrice());
                    subFactor.setProfit(productDtoSelect.getQuantity() * productDtoSelect.getProductDto().getSellPrice() - productDtoSelect.getQuantity() * productDtoSelect.getProductDto().getBuyPrice());
                    refreshList=true;
                }
            }
            if (refreshList){
                List<SubFactorDto> tmpList = new ArrayList<>(subFactorDtos);
                subFactorDtos.clear();
                subFactorDtos.addAll(tmpList);
            }
        }

        calculateOffer();
    }

    private void calculateTotals() {
        int totalPrice = 0;
        int totalProfit = 0;
        for (SubFactorDto dto : subFactorDtos) {
            totalPrice += dto.getTotalPrice();
            totalProfit += dto.getProfit();
        }
        this.factorDto.setTotalPrice(totalPrice);
        this.factorDto.setFinalPrice(totalPrice);
        this.factorDto.setTotalProfit(totalProfit);
        this.lblTotalPrice.setText(String.valueOf(totalPrice));
        this.txtFinalPrice.setText(String.valueOf(totalPrice));
        this.lblTotalProfit.setText(String.valueOf(totalProfit));
    }

    @FXML
    void onRemoveFromBasket(MouseEvent event) {
        int selectedSubFactorIndex=subFactorTable.getSelectionModel().getSelectedIndex();
        if (selectedSubFactorIndex>=0&&selectedSubFactorIndex<subFactorDtos.size()){
            subFactorDtos.remove(selectedSubFactorIndex);
            calculateOffer();
        }
    }

    @FXML
    void onSave(MouseEvent event) {
        if (this.factorDto!=null&&this.factorDto.getTotalPrice()>0&&this.factorDto.getFinalPrice()>0){
            FactorDto factor=this.factorDto;
            factor.setSubFactors(this.subFactorDtos);
            Factor factorEntity = modelMapper.map(factor, Factor.class);
            boolean result = factorService.addNewFactor(factorEntity);
            if (result){
                alert.show("Success","Add new Factor was Successful.");
                subFactorDtos.clear();
                calculateOffer();
            }else {
                alert.showW("Failed","Add new Factor was Failed try again.");
            }
        }
    }

    public void isUpdate(int id) {

    }

    @FXML
    void onFinalPriceKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                if (!this.txtFinalPrice.getText().equals("")&&this.factorDto.getTotalPrice()>0) {
                    int finalPrice = Integer.parseInt(this.txtFinalPrice.getText());
                    calculateTotals();
                    this.txtFinalPrice.setText(String.valueOf(finalPrice));
                    if (finalPrice > 0) {
                        this.factorDto.setFinalPrice(finalPrice);
                        int offer = this.factorDto.getTotalPrice() - finalPrice;
                        if (this.factorDto.getTotalProfit() - offer <= 0) {
                            this.lblTotalProfit.setStyle("-fx-text-fill: #b51c1c");
                        } else {
                            this.lblTotalProfit.setStyle("-fx-text-fill: #000000");
                        }
                        this.factorDto.setTotalProfit(this.factorDto.getTotalProfit() - offer);
                        this.lblTotalProfit.setText(String.valueOf(this.factorDto.getTotalProfit()));
                        this.txtCustomPrs.setText(String.valueOf(100-(this.factorDto.getFinalPrice() * 100 / this.factorDto.getTotalPrice())));
                    }
                }
            } catch (Exception ignored) {

            }
        }
    }

    @FXML
    void onCancel(MouseEvent event) {
        Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
