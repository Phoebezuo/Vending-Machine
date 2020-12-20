package VendingMachine.Window.CancelHistory;

import VendingMachine.Data.Transaction;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.time.format.DateTimeFormatter;

public class CancelHistoryWindow {

    private AnchorPane pane;
    private TableView<CancelTableEntry> table;

    public CancelHistoryWindow() {
        Stage stage = new Stage();
        pane = new AnchorPane();
        Scene scene = new Scene(pane, 600, 480);
        stage.setScene(scene);
        stage.setTitle("Cancelled History");
        stage.show();
        initTable();
    }

    public void updateTableData() {
        table.getItems().clear();
        for (Transaction transaction : Transaction.getTransactionList()) {
            if (transaction.getStatus() == Transaction.Status.CANCELLED) {
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd HH:mm");
                table.getItems().add(new CancelTableEntry(transaction.getDate().format(fmt),
                        transaction.getPayee().getUsername(), transaction.getReason()));
            }
        }
    }

    private void initTable() {
        table = new TableView<>();
        table.setLayoutX(50);
        table.setLayoutY(40);
        table.setPrefWidth(500);
        table.setPrefHeight(350);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        pane.getChildren().add(table);

        // create table
        String[] colNames = {"Date", "User", "Reason"};
        String[] properties = {"date", "user", "reason"};
        for (int i = 0; i < colNames.length; i++) {
            String colName = colNames[i];
            TableColumn<CancelTableEntry, String> column = new TableColumn<>(colName);
            column.setSortable(false);
            column.setPrefWidth(118);
            column.setStyle("-fx-alignment: CENTER;");
            column.setCellValueFactory(new PropertyValueFactory<>(properties[i]));
            table.getColumns().add(column);
        }
        updateTableData();
    }
}
