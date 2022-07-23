package ir.alirezaalijani.product.manager.application.ui.controller;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ir.alirezaalijani.product.manager.application.config.ContextHolder;
import ir.alirezaalijani.product.manager.application.service.FactorService;
import ir.alirezaalijani.product.manager.application.service.ProductService;
import ir.alirezaalijani.product.manager.application.ui.data.FactorDto;
import ir.alirezaalijani.product.manager.application.ui.data.ProductDto;
import ir.alirezaalijani.product.manager.application.ui.data.SubFactorDto;
import ir.alirezaalijani.product.manager.application.ui.service.ViewManager;
import ir.alirezaalijani.product.manager.application.ui.service.fxmlcontent.FxmlContent;
import ir.alirezaalijani.product.manager.application.ui.service.fxmlcontent.ViewLoadType;
import ir.alirezaalijani.product.manager.application.ui.util.ViewPath;
import ir.alirezaalijani.product.manager.application.ui.util.alert.QuAlert;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.modelmapper.ModelMapper;

public class FactorsController {

    private final ViewManager viewManager;
    private final BorderPane rootPane;
    private final ModelMapper modelMapper;
    private final QuAlert quAlert;
    private final FactorService factorService;
    private ObservableList<FactorDto> factorDtos;
    private ObservableList<SubFactorDto> subFactorDtos;

    public FactorsController() {
        this.rootPane = ContextHolder.getInstance().getBean("RootPane", BorderPane.class);
        this.viewManager = ContextHolder.getInstance().getBean("ViewManager", ViewManager.class);
        this.factorService = ContextHolder.getInstance().getBean("factorService", FactorService.class);
        this.modelMapper = ContextHolder.getInstance().getBean("modelMapper", ModelMapper.class);
        this.quAlert = QuAlert.getInstance();
    }


    @FXML
    private CheckBox cbToday;

    @FXML
    private TableView<FactorDto> factorTable;

    @FXML
    private ProgressIndicator mainProgress;

    @FXML
    private TableView<SubFactorDto> subFactorTable;
    private final TableColumn<SubFactorDto, String> column5 = new TableColumn<>("Product Code");
    private final TableColumn<SubFactorDto, String> column6 = new TableColumn<>("Product Name");

    @FXML
    private TextField txtFindByCode;

    @FXML
    void initialize() {
        this.mainProgress.setVisible(false);
        this.factorDtos = factorTable.getItems();
        this.subFactorDtos = subFactorTable.getItems();

        column5.setPrefWidth(86.0);
        column5.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getProduct().getId())));
        this.subFactorTable.getColumns().set(5, column5);

        column6.setPrefWidth(102.0);
        column6.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getProduct().getName())));
        this.subFactorTable.getColumns().set(6, column6);

        Platform.runLater(() -> loadFactors(false));
        factorTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, o, n) -> {
                    if (n != null) {
                        subFactorDtos.clear();
                        subFactorDtos.addAll(n.getSubFactors());
                    }
                });

        cbToday.setOnAction(actionEvent -> {
            loadFactors(cbToday.isSelected());
        });
    }

    private void loadFactors(boolean today) {
        factorDtos.clear();
        subFactorDtos.clear();
        List<FactorDto> factorDtoList;
        if (today) {
            factorDtoList = factorService.findAllToday()
                    .stream()
                    .map(factor -> modelMapper.map(factor, FactorDto.class))
                    .collect(Collectors.toList());
        } else {
            factorDtoList = factorService.findAll()
                    .stream()
                    .map(factor -> modelMapper.map(factor, FactorDto.class))
                    .collect(Collectors.toList());
        }
        factorDtos.addAll(factorDtoList);
    }

    private void loadFactorByCode(int code) {
        factorDtos.clear();
        subFactorDtos.clear();
        List<FactorDto> factorDtoList = Stream.of(factorService.findByCode(code))
                .map(factor -> modelMapper.map(factor, FactorDto.class))
                .collect(Collectors.toList());
        factorDtos.addAll(factorDtoList);
    }

    @FXML
    void onBack(MouseEvent event) {
        FxmlContent content = viewManager.popView();
        rootPane.setCenter(content.getContentPane());
    }

    @FXML
    void onAddNewFactor(MouseEvent event) {
        loadFactorView(0);
    }

    @FXML
    void onEditFactor(MouseEvent event) {

    }

    private void loadFactorView(int id) {
        URL view = ViewPath.ViewFactor;
        FxmlContent content = viewManager.getView(view, ViewLoadType.STAGE_PURE);
        Stage stage = content.getStage();
        FactorController controller = content.getLoader().getController();
        if (controller != null) controller.isUpdate(id);
        if (!stage.isShowing()) {
            stage.show();
            stage.setTitle("Add New Product");
            stage.getIcons().add(new Image(ViewPath.imgApplicationIcon));
            stage.setMaximized(false);
            stage.setOnCloseRequest(event -> {
                System.out.println("FactorController close event");
                ((Stage) this.rootPane.getScene().getWindow()).show();
                viewManager.removeView(view);
                loadFactors(true);
                cbToday.setSelected(true);
            });
            if (stage.isShowing()) {
                this.rootPane.getScene().getWindow().hide();
            }
        }
    }

    @FXML
    void onDeleteFactor(MouseEvent event) {

    }

    @FXML
    void onEnterFindById(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                if (txtFindByCode.getText().trim().equals("")){
                    loadFactors(false);
                }else {
                    int id=Integer.parseInt(txtFindByCode.getText());
                    loadFactorByCode(id);
                }
            }catch (Exception ignored){

            }
        }
    }

    @FXML
    void onFindByCode(MouseEvent event) {
        try {
            if (txtFindByCode.getText().trim().equals("")){
                loadFactors(false);
            }else {
                int id=Integer.parseInt(txtFindByCode.getText());
                loadFactorByCode(id);
            }

        }catch (Exception ignored){

        }
    }


}
