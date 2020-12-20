package VendingMachine.Window.CashManagement;

import VendingMachine.Processor.CashProcessor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.*;

public class CashManagementWindow {
    private final AnchorPane pane;
    private final CashProcessor cashProcessor;
    private TableView<CashTableEntry> table;
    private Button changeButton;
    private double selectedCashType;
    private TextField amountField;
    private ComboBox<String> typeCombo;
    private String originCash;

    public CashManagementWindow() {
        Stage stage = new Stage();
        pane = new AnchorPane();
        Scene scene = new Scene(pane, 600, 480);
        stage.setScene(scene);
        stage.setTitle("Cash Management");
        stage.show();
        cashProcessor = CashProcessor.getInstance();
        initTable();
        initButton();
        initTextFields();
        initButtonActions();
        initCombobox();
        selectedCashType = -1;
    }

    private void initTable() {
        table = new TableView<>();
        table.setLayoutX(100);
        table.setLayoutY(15);
        table.setPrefWidth(400);
        table.setPrefHeight(300);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        pane.getChildren().add(table);

        // create table
        String[] colNames = {"Value", "Number"};
        String[] properties = {"cashType", "amount"};
        for (int i = 0; i < colNames.length; i++) {
            String colName = colNames[i];
            TableColumn<CashTableEntry, String> column = new TableColumn<>(colName);
            column.setSortable(false);
            column.setPrefWidth(118);
            column.setStyle("-fx-alignment: CENTER;");
            column.setCellValueFactory(new PropertyValueFactory<>(properties[i]));
            table.getColumns().add(column);
        }

        table.setOnMouseClicked(event -> {
            if (!table.getSelectionModel().isEmpty()) {
                CashTableEntry selected = table.getSelectionModel().getSelectedItem();
                selectedCashType = Double.parseDouble(selected.getCashType());
                originCash = selected.getCashType();
                typeCombo.getSelectionModel().select(originCash);
                amountField.setText(selected.getAmount());
            }
        });

        setTableData();
    }


    private void initButton() {
        changeButton = new Button();
        Button[] buttons = {changeButton};
        String[] texts = {"Change number"};
        for (int i = 0; i < buttons.length; i++) {
            Button button = buttons[i];
            button.setLayoutX(200);
            button.setLayoutY(400);
            button.setPrefWidth(200);
            button.setPrefHeight(30);
            button.setText(texts[i]);
            pane.getChildren().add(button);
        }
    }

    private void initTextFields() {
        amountField = new TextField();
        amountField.setLayoutX(300);
        amountField.setLayoutY(350);
        amountField.setPrefWidth(120);
        amountField.setPromptText("Number");
        pane.getChildren().add(amountField);
    }

    private void initCombobox() {
        typeCombo = new ComboBox<>();
        typeCombo.setLayoutX(150);
        typeCombo.setLayoutY(350);
        typeCombo.setPrefWidth(120);
        typeCombo.setPromptText("Cash Type");

        pane.getChildren().add(typeCombo);
    }

    private void setTableData() {
        // set data to table
        table.getItems().clear();
        Map<Double, Integer> cashMap = cashProcessor.getCashMap();
        Collection<Double> keySet = cashMap.keySet();
        List<Double> list = new ArrayList<>(keySet);
        Collections.sort(list);
        for (Double value : list) {
            table.getItems().add(new CashTableEntry(value.toString(), cashMap.get(value).toString()));
        }
    }

    private void initButtonActions() {
        changeButton.setOnAction((event -> changeAction()));
    }

    private void changeAction() {
        if (selectedCashType < 0) {
            warn("You don't select any cash.");
            return;
        } else if (!validateInput()) {
            return;
        }
        try {
            cashProcessor.setCashNumber(selectedCashType, Integer.parseInt(amountField.getText()));
//            alert(Alert.AlertType.INFORMATION, "Change successfully.");
        } catch (Exception e) {
            warn("Fail to change");
        }

        amountField.setText("");
        setTableData();
        selectedCashType = -1;
    }

    private boolean validateInput() {
        if (amountField.getText().trim().isEmpty()) {
            warn("Number needed");
            return false;
        }
        return true;
    }

    private void warn(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING, msg);
        alert.show();
    }
}