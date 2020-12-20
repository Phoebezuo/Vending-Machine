package VendingMachine.Window.CheckoutManagement;

import VendingMachine.Processor.UserProcessor;
import VendingMachine.Window.MainWindow;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;

public class ChangeWindow {
    private final Stage stage;
    private final AnchorPane pane;
    private TableView<CashTableEntry> table;
    private Button okBtn;

    public ChangeWindow() {
        stage = new Stage();
        pane = new AnchorPane();
        Scene scene = new Scene(pane, 480, 600);
        stage.setScene(scene);
        stage.setTitle("Cash Changes");
        stage.show();

        initLabel();
        initTable();
        initButton();
        initButtonAction();
    }

    private void initLabel() {
        Label youReceiveLabel = new Label("Your changes");
        youReceiveLabel.setLayoutX(130);
        youReceiveLabel.setLayoutY(50);
        youReceiveLabel.setFont(Font.font(20));
        pane.getChildren().add(youReceiveLabel);
    }

    private void initButton() {
        okBtn = new Button();
        okBtn.setLayoutX(180);
        okBtn.setLayoutY(60 + 470);
        okBtn.setPrefWidth(120);
        okBtn.setPrefHeight(30);
        okBtn.setText("Okay");
        pane.getChildren().add(okBtn);
    }

    private void initButtonAction() {
        okBtn.setOnAction((event -> {
            UserProcessor.getInstance().logoutUser();
            MainWindow.getInstance().update();
            stage.close();
        }));
    }

    private void initTable() {
        table = new TableView<>();
        table.setLayoutX(40);
        table.setLayoutY(100);
        table.setPrefWidth(400);
        table.setPrefHeight(400);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        pane.getChildren().add(table);

        // create table
        String[] colNames = {"Value", "Number"};
        String[] properties = {"value", "number"};
        for (int i = 0; i < colNames.length; i++) {
            String colName = colNames[i];
            TableColumn<CashTableEntry, String> column = new TableColumn<>(colName);
            column.setSortable(false);
            column.setPrefWidth(118);
            column.setStyle("-fx-alignment: CENTER;");
            column.setCellValueFactory(new PropertyValueFactory<>(properties[i]));
            table.getColumns().add(column);
        }
        setTableData();
    }

    private void setTableData() {
        // set data to table
        Map<Double, Integer> changesMap = UserProcessor.getInstance().getCurrentUser().getReturnChangeMap();
        Collection<Double> keySet = changesMap.keySet();
        List<Double> list = new ArrayList<>(keySet);
        Collections.sort(list);
        for (Double value : list) {
            table.getItems().add(new CashTableEntry(value.toString(), changesMap.get(value).toString()));
        }
    }
}
