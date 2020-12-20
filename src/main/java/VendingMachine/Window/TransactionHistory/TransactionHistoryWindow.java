package VendingMachine.Window.TransactionHistory;

import VendingMachine.Data.Transaction;
import VendingMachine.Processor.UserProcessor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

public class TransactionHistoryWindow {

    private final AnchorPane pane;
    private final UserProcessor userProcessor;
    private TableView<TransactionHistoryTableEntry> table;
    private Button viewButton;

    public TransactionHistoryWindow() {
        Stage stage = new Stage();
        pane = new AnchorPane();
        Scene scene = new Scene(pane, 600, 480);
        stage.setScene(scene);
        stage.setTitle("Transaction History");
        stage.show();
        userProcessor = UserProcessor.getInstance();
        initTable();
        initBtn();
        initBtnAction();
    }

    private void initBtn() {
        viewButton = new Button();
        viewButton.setLayoutY(440);
        viewButton.setLayoutX(250);
        viewButton.setPrefWidth(100);
        viewButton.setPrefHeight(30);
        viewButton.setText("View details");
        pane.getChildren().add(viewButton);
    }

    private void initBtnAction() {
        viewButton.setOnAction(event -> {
            if (this.table.getSelectionModel().isEmpty()) {
                Alert alert =
                        new Alert(Alert.AlertType.WARNING, "Please select a transaction");
                alert.show();
            }else{
                new TransactionProductWindow(this.table.getSelectionModel().getSelectedItem());
            }

        });
    }

    private void initTable() {
        table = new TableView<>();
        table.setLayoutX(40);
        table.setLayoutY(15);
        table.setPrefWidth(520);
        table.setPrefHeight(400);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        pane.getChildren().add(table);

        String[] colNames = {"Time", "Money Paid", "Changes", "Payment Method"};
        String[] properties = {"time", "moneyPaid", "changes", "paymentMethod"};
        for (int i = 0; i < colNames.length; i++) {
            String colName = colNames[i];
            TableColumn<TransactionHistoryTableEntry, String> column = new TableColumn<>(colName);
            column.setSortable(false);
            column.setPrefWidth(100);
            column.setStyle("-fx-alignment: CENTER;");
            column.setCellValueFactory(new PropertyValueFactory<>(properties[i]));
            table.getColumns().add(column);
        }
        table.getColumns().get(0).setPrefWidth(120);
        setTableData();
    }

    private void setTableData() {
        table.getItems().clear();
        for (Transaction transaction : Transaction.getTransactionList()) {
            if (transaction.getStatus() != Transaction.Status.PAID) {
                continue;
            }
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            table.getItems().add(new TransactionHistoryTableEntry(transaction.getDate().format(fmt),
                    Double.toString(transaction.getPaidAmount()),
                    Double.toString(transaction.getChange()),
                    transaction.getPayment().toString(), transaction.getShoppingList()));
        }
    }
}
