package VendingMachine.Window.ProductManagement;

import VendingMachine.Data.Product;
import VendingMachine.Processor.ProductProcessor;
import VendingMachine.Window.MainWindow;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductManagementWindow {
    private final ProductProcessor productProcessor;
    private final AnchorPane pane;
    private Button addButton;
    private Button changeButton;
    private Button removeButton;
    private TextField codeField;
    private TextField nameField;
    private ComboBox<String> categoryCombo;
    private TextField priceField;
    private TextField stockField;
    private int selectedId;
    private String originCategory;
    private TableView<ProductTableEntry> productTable;

    public ProductManagementWindow() {
        Stage stage = new Stage();
        pane = new AnchorPane();
        Scene scene = new Scene(pane, 600, 500);
        stage.setScene(scene);
        stage.setTitle("Product Management");
        stage.show();
        productProcessor = ProductProcessor.getInstance();
        initTable();
        setTableAction();
        initButton();
        initButtonActions();
        initCombobox();
        initLabels();
        initTextFields();
        selectedId = -1;
    }

    public void updateTableData() {
        // set data to table
        productTable.getItems().clear();
        Map<Integer, Product> productMap = ProductProcessor.getInstance().getProductMap();
        List<Product> products =
                productMap.values()
                        .stream().sorted(Comparator.comparing(Product::getCategory))
                        .collect(Collectors.toList());

        products.forEach(product -> {
            String code = product.getCode();
            String name = product.getName();
            String price = Double.toString(product.getPrice());
            String quantity = Integer.toString(product.getStock());
            String category = product.getCategory().toString();
            productTable.getItems().add(new ProductTableEntry(code, name, category, price, quantity,
                    product.getId()));
        });
    }

    private void setTableAction() {
        this.productTable.setOnMouseClicked(event -> {
            if (!this.productTable.getSelectionModel().isEmpty()) {
                ProductTableEntry selected = this.productTable.getSelectionModel().getSelectedItem();
                selectedId = selected.getId();
                codeField.setText(selected.getCode());
                nameField.setText(selected.getName());
                originCategory = selected.getCategory();
                categoryCombo.getSelectionModel().select(originCategory);
                priceField.setText(selected.getPrice());
                stockField.setText(selected.getQuantity());
            }
        });
    }

    private void initButton() {
        addButton = new Button();
        changeButton = new Button();
        removeButton = new Button();

        Button[] buttons = {addButton, changeButton, removeButton};
        String[] texts = {"Add", "Change", "Remove"};

        for (int i = 0; i < buttons.length; i++) {
            Button button = buttons[i];
            button.setLayoutX(60 + 160 * i);
            button.setLayoutY(450);
            button.setPrefWidth(130);
            button.setPrefHeight(30);
            button.setText(texts[i]);
            pane.getChildren().add(button);
        }
    }

    private void initButtonActions() {
        addButton.setOnAction((event -> addAction()));
        removeButton.setOnAction((event -> removeAction()));
        changeButton.setOnAction((event -> changeAction()));
    }

    private void initCombobox() {
        categoryCombo = new ComboBox<>();
        categoryCombo.setLayoutX(400);
        categoryCombo.setLayoutY(410);
        categoryCombo.setPrefWidth(110);

        for (Product.Category c : Product.Category.values()) {
            categoryCombo.getItems().add(c.toString());
        }

        pane.getChildren().add(categoryCombo);
    }

    private void initTextFields() {
        codeField = new TextField();
        codeField.setLayoutX(60);
        codeField.setLayoutY(410);
        codeField.setPrefWidth(60);

        nameField = new TextField();
        nameField.setLayoutX(130);
        nameField.setLayoutY(410);
        nameField.setPrefWidth(120);

        priceField = new TextField();
        priceField.setLayoutX(260);
        priceField.setLayoutY(410);
        priceField.setPrefWidth(60);

        stockField = new TextField();
        stockField.setLayoutX(330);
        stockField.setLayoutY(410);
        stockField.setPrefWidth(60);

        pane.getChildren().addAll(codeField, nameField, priceField, stockField);
    }

    private void initLabels() {
        Label codeLabel = new Label("Code");
        codeLabel.setLayoutX(60);
        codeLabel.setLayoutY(390);

        Label nameLabel = new Label("Name");
        nameLabel.setLayoutX(130);
        nameLabel.setLayoutY(390);

        Label priceLabel = new Label("Price");
        priceLabel.setLayoutX(260);
        priceLabel.setLayoutY(390);

        Label quantityLabel = new Label("Quantity");
        quantityLabel.setLayoutX(330);
        quantityLabel.setLayoutY(390);

        Label categoryLabel = new Label("Category");
        categoryLabel.setLayoutX(400);
        categoryLabel.setLayoutY(390);

        pane.getChildren().addAll(codeLabel, nameLabel, priceLabel, quantityLabel, categoryLabel);
    }

    private void addAction() {
        if (!validateInput()) {
            return;
        } else if (Integer.parseInt(stockField.getText()) > 15) {
            alert(Alert.AlertType.WARNING, "Quantity exceed 15.");
            return;
        }
        String category = categoryCombo.getSelectionModel().getSelectedItem();
        String code = codeField.getText();


        if (productProcessor.addProduct(code, category, nameField.getText(),
                Double.parseDouble(priceField.getText()), Integer.parseInt(stockField.getText()))) {
            updateTableData();
            MainWindow.getInstance().updateProductTable();
            selectedId = -1;
        } else {
            alert(Alert.AlertType.WARNING, "Conflicting name/code");
        }

    }

    private void removeAction() {
        ProductTableEntry selectedItem = productTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            alert(Alert.AlertType.WARNING, "You don't select any product.");
            return;
        }
        if (productProcessor.removeProduct(selectedId)) {

            updateTableData();
            MainWindow.getInstance().updateProductTable();
        }
    }

    private void changeAction() {
        if (selectedId < 0) {
            alert(Alert.AlertType.WARNING, "You don't select any product.");
            return;
        } else if (!validateInput()) {
            return;
        } else if (Integer.parseInt(stockField.getText()) > 15) {
            alert(Alert.AlertType.WARNING, "Quantity exceeds 15.");
            return;
        }

        String newCategory = categoryCombo.getSelectionModel().getSelectedItem();
        String newCode = codeField.getText();
        double newPrice = Double.parseDouble(priceField.getText());
        int newStock = Integer.parseInt(stockField.getText());
        String newName = nameField.getText();
        String oldCode = productProcessor.getProduct(selectedId).getCode();

        if (!productProcessor.setProductCode(selectedId, newCode)) {
            alert(Alert.AlertType.WARNING, "Conflicting code.");
            return;
        }

        if (!productProcessor.setProductName(selectedId, newName)) {
            productProcessor.setProductCode(selectedId, oldCode);
            alert(Alert.AlertType.WARNING, "Conflicting name.");
            return;
        }

        productProcessor.setProductCategory(selectedId, newCategory);
        productProcessor.setProductPrice(selectedId, newPrice);
        productProcessor.setProductStock(selectedId, newStock);

        codeField.setText("");
        nameField.setText("");
        priceField.setText("");
        stockField.setText("");
        categoryCombo.getSelectionModel().clearSelection();
        updateTableData();
        MainWindow.getInstance().updateProductTable();
        selectedId = -1;
    }

    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty()) {
            alert(Alert.AlertType.WARNING, "Product name needed");
            return false;
        } else if (priceField.getText().trim().isEmpty()) {
            alert(Alert.AlertType.WARNING, "Product price needed");
            return false;
        } else if (stockField.getText().trim().isEmpty()) {
            alert(Alert.AlertType.WARNING, "Product quantity needed");
            return false;
        } else if (categoryCombo.getSelectionModel().isEmpty()) {
            alert(Alert.AlertType.WARNING, "Product category needed.");
            return false;
        }
        return true;
    }

    private void alert(Alert.AlertType warning, String s) {
        Alert alert = new Alert(warning, s);
        alert.show();
    }

    private void initTable() {
        productTable = new TableView<>();
        productTable.setLayoutX(50);
        productTable.setLayoutY(30);
        productTable.setPrefWidth(500);
        productTable.setPrefHeight(350);
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // create table
        String[] colNames = {"Category", "Name", "Code", "Price ($)", "Quantity"};
        String[] properties = {"category", "name", "code", "price", "quantity"};
        for (int i = 0; i < colNames.length; i++) {
            String colName = colNames[i];
            TableColumn<ProductTableEntry, String> column = new TableColumn<>(colName);
            column.setSortable(false);
            column.setPrefWidth(118);
            column.setStyle("-fx-alignment: CENTER;");
            column.setCellValueFactory(new PropertyValueFactory<>(properties[i]));
            productTable.getColumns().add(column);
        }
        updateTableData();
        pane.getChildren().add(productTable);
    }
}
