package VendingMachine.Window.SoldHistory;


import VendingMachine.Data.Product;
import VendingMachine.Processor.ProductProcessor;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SoldHistoryWindow {
    private final AnchorPane pane;
    private TableView<SoldTableEntry> table;

    public SoldHistoryWindow() {
        Stage stage = new Stage();
        pane = new AnchorPane();
        Scene scene = new Scene(pane, 600, 480);
        stage.setScene(scene);
        stage.setTitle("Sold History");
        stage.show();
        initTable();
    }

    public void updateTableData() {
        // set data to table
        table.getItems().clear();
        Map<Integer, Product> productMap = ProductProcessor.getInstance().getProductMap();
        List<Product> products =
                productMap.values()
                        .stream().sorted(Comparator.comparing(Product::getCategory))
                        .collect(Collectors.toList());

        products.forEach(product -> {
            String code = product.getCode();
            String name = product.getName();
            String price = Double.toString(product.getPrice());
            String sold = Integer.toString(product.getSold());
            String category = product.getCategory().toString();
            table.getItems().add(new SoldTableEntry(code, name, category, price, sold, product.getId()));
        });
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
        String[] colNames = {"Category", "Name", "Code", "Price ($)", "Sold"};
        String[] properties = {"category", "name", "code", "price", "sold"};
        for (int i = 0; i < colNames.length; i++) {
            String colName = colNames[i];
            TableColumn<SoldTableEntry, String> column = new TableColumn<>(colName);
            column.setSortable(false);
            column.setPrefWidth(118);
            column.setStyle("-fx-alignment: CENTER;");
            column.setCellValueFactory(new PropertyValueFactory<>(properties[i]));
            table.getColumns().add(column);
        }
        updateTableData();
    }
}

