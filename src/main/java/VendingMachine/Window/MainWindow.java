package VendingMachine.Window;

import VendingMachine.Data.Product;
import VendingMachine.Data.Transaction;
import VendingMachine.Data.User;
import VendingMachine.Processor.ProductProcessor;
import VendingMachine.Processor.UserProcessor;
import VendingMachine.Window.CancelHistory.CancelHistoryWindow;
import VendingMachine.Window.CashManagement.CashManagementWindow;
import VendingMachine.Window.CheckoutManagement.CheckoutWindow;
import VendingMachine.Window.ProductManagement.ProductManagementWindow;
import VendingMachine.Window.ProductManagement.ProductTableEntry;
import VendingMachine.Window.SoldHistory.SoldHistoryWindow;
import VendingMachine.Window.TransactionHistory.TransactionHistoryWindow;
import VendingMachine.Window.UserManagement.UserManagementWindow;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class MainWindow {
    private static MainWindow mainWindow;
    private final Scene scene;
    private final AnchorPane pane;
    private final UserProcessor userProcessor;
    private Button accountBtn;
    private Button userManagementBtn;
    private Button cashManagementBtn;
    private Button productManageBtn;
    private Button reportBtn;
    private Button soldHistoryBtn;
    private Button transactionHistoryBtn;
    private Button cancelHistroyBtn;
    private Text currentUserInfo;
    private Text selectedItemText;
    private ComboBox<Integer> selectedQuantityCombo;
    private TableView<ProductTableEntry> shoppingHistoryTable;
    private TableView<ProductTableEntry> productTable;

    private MainWindow() {
        pane = new AnchorPane();
        scene = new Scene(pane, 1150, 460);
        userProcessor = UserProcessor.getInstance();
        initProductTable();
        initButtons();
        initBtnActions();
        initLabels();
        initText();
        updateCurrencyUserInfo();
        initShoppingNodes();
        initShoppingHistory();
        setProductTableAction();
    }

    public static MainWindow getInstance() {
        if (mainWindow == null) {
            mainWindow = new MainWindow();
        }
        return mainWindow;
    }

    public void updateCurrencyUserInfo() {
        currentUserInfo.setText("Username: "
                + userProcessor.getCurrentUser().getUsername()
                + "\t\tRole: "
                + userProcessor.getCurrentUser().getType()
        );
        if (userProcessor.getCurrentUser().getType() == User.UserType.ANONYMOUS) {
            accountBtn.setText("Account");
        }
    }

    public void changeAccountButtonText(String text) {
        this.accountBtn.setText(text);
    }

    public void setShoppingHistoryData() {
        this.shoppingHistoryTable.getItems().clear();
        List<Transaction> shoppingHistory = UserProcessor.getInstance().getCurrentUser().getShoppingHistory();
        int count = 0;
        for (int i = shoppingHistory.size() - 1; i >= 0; i--) {
            Transaction transaction = shoppingHistory.get(i);
            if (transaction.getStatus() != Transaction.Status.PAID) {
                continue;
            }
            Map<Product, Integer> boughtProducts = transaction.getShoppingList();
            List<Entry<Product, Integer>> entrySet =
                    boughtProducts.entrySet()
                            .stream().sorted(Comparator.comparing(o -> o.getKey().getCode()))
                            .collect(Collectors.toList());
            for (Map.Entry<Product, Integer> entry : entrySet) {
                Product product = entry.getKey();
                Integer qty = entry.getValue();
                this.shoppingHistoryTable.getItems().add(new ProductTableEntry(product.getCode(),
                        product.getName(), product.getCategory().toString(),
                        Double.toString(product.getPrice()), Integer.toString(qty),
                        product.getId()));
                count++;
                if (count >= 5) {
                    return;
                }
            }
        }
    }

    public void update() {
        updateProductTable();
        updateCurrencyUserInfo();
        setShoppingHistoryData();
    }

    public void updateProductTable() {
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
            Map<Product, Integer> currentShoppingCart = UserProcessor.getInstance()
                    .getCurrentUser().getShoppingList();
            String inCart = "0";
            if (currentShoppingCart.containsKey(product)) {
                inCart = Integer.toString(currentShoppingCart.get(product));
            }
            productTable.getItems().add(new ProductTableEntry(code, name, category, price, quantity,
                    product.getId(), inCart));
        });
    }

    private void initLabels() {
        Label productTableLabel = new Label("Product Table");
        productTableLabel.setLayoutX(250);
        productTableLabel.setLayoutY(30);

        Label shoppingHistoryLabel = new Label("Latest bought items");
        shoppingHistoryLabel.setLayoutX(810);
        shoppingHistoryLabel.setLayoutY(30);

        pane.getChildren().addAll(productTableLabel, shoppingHistoryLabel);
    }

    private void initButtons() {
        accountBtn = new Button();
        reportBtn = new Button();
        Button[] buttons = {accountBtn, reportBtn};
        String[] texts = {"Account", "Generate Report"};
        for (int i = 0; i < buttons.length; i++) {
            Button button = buttons[i];
            button.setText(texts[i]);
            button.setLayoutX(600);
            button.setLayoutY(240 + 50 * i);
            button.setPrefWidth(110);
            button.setPrefHeight(30);
            pane.getChildren().add(button);
        }

        soldHistoryBtn = new Button();
        transactionHistoryBtn = new Button();
        cancelHistroyBtn = new Button();
        buttons = new Button[]{soldHistoryBtn, transactionHistoryBtn, cancelHistroyBtn};
        texts = new String[]{"Sold History", "Transaction History", "Cancelled History"};
        for (int i = 0; i < buttons.length; i++) {
            Button button = buttons[i];
            button.setText(texts[i]);
            button.setLayoutX(770);
            button.setLayoutY(240 + 50 * i);
            button.setPrefWidth(150);
            button.setPrefHeight(30);
            pane.getChildren().add(button);
        }

        userManagementBtn = new Button();
        cashManagementBtn = new Button();
        productManageBtn = new Button();
        buttons = new Button[]{userManagementBtn, cashManagementBtn, productManageBtn};
        texts = new String[]{"Manage User", "Manage Cash", "Manage Product"};
        for (int i = 0; i < buttons.length; i++) {
            Button button = buttons[i];
            button.setText(texts[i]);
            button.setLayoutX(990);
            button.setLayoutY(240 + 50 * i);
            button.setPrefWidth(110);
            button.setPrefHeight(30);
            pane.getChildren().add(button);
        }
    }

    private void initBtnActions() {
        accountBtn.setOnAction((event -> {
            if (userProcessor.getCurrentUser().getType() == User.UserType.ANONYMOUS) {
                // If the currency user is the Anonymous
                new LoginWindow();
            } else {
                userProcessor.logoutUser();
                accountBtn.setText("Account");
                update();
            }
        }));
        reportBtn.setOnAction(event -> {
            try {
                new ReportWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        soldHistoryBtn.setOnAction(event -> {
            if (userProcessor.getCurrentUser().getPermission(User.Permission.MANAGE_ITEM)) {
                new SoldHistoryWindow();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You don't have the permission " +
                        "to do this action.");
                alert.show();
            }
        });
        transactionHistoryBtn.setOnAction(event -> {
            if (userProcessor.getCurrentUser().getPermission(User.Permission.MANAGE_CASH)) {
                new TransactionHistoryWindow();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You don't have the permission " +
                        "to do this action.");
                alert.show();
            }
        });
        cancelHistroyBtn.setOnAction(event -> {
            if (userProcessor.getCurrentUser().getPermission(User.Permission.MANAGE_USER)) {
                new CancelHistoryWindow();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You don't have the permission " +
                        "to do this action.");
                alert.show();
            }
        });

        userManagementBtn.setOnAction(event -> {
            if (userProcessor.getCurrentUser().getPermission(User.Permission.MANAGE_USER)) {
                new UserManagementWindow();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You don't have the permission " +
                        "to do this action.");
                alert.show();
            }
        });
        cashManagementBtn.setOnAction(event -> {
            if (userProcessor.getCurrentUser().getPermission(User.Permission.MANAGE_CASH)) {
                new CashManagementWindow();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You don't have the permission " +
                        "to do this action.");
                alert.show();
            }
        });
        productManageBtn.setOnAction(event -> {
            if (userProcessor.getCurrentUser().getPermission(User.Permission.MANAGE_ITEM)) {
                new ProductManagementWindow();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You don't have the permission " +
                        "to do this action.");
                alert.show();
            }
        });
    }

    private void initText() {
        currentUserInfo = new Text();
        currentUserInfo.setLayoutX(730);
        currentUserInfo.setLayoutY(220);
        this.pane.getChildren().add(currentUserInfo);
    }

    private void setProductTableAction() {
        this.productTable.setOnMouseClicked(event -> {
            selectedQuantityCombo.getItems().clear();
            if (!productTable.getSelectionModel().isEmpty()) {
                ProductTableEntry selected = productTable.getSelectionModel().getSelectedItem();
                selectedItemText.setText(selected.getName());
                for (int i = 0; i <= Integer.parseInt(selected.getQuantity()); i++) {
                    selectedQuantityCombo.getItems().add(i);
                }

                if (selectedQuantityCombo.getItems().size() > 0) {
                    selectedQuantityCombo.getSelectionModel().select(1);
                } else {
                    selectedQuantityCombo.getSelectionModel().select(0);
                }
            } else {
                selectedItemText.setText("Selected Item");
                selectedQuantityCombo.setPromptText("Quantity");
            }
        });
    }

    private void initShoppingHistory() {
        shoppingHistoryTable = new TableView<>();
        shoppingHistoryTable.setLayoutX(600);
        shoppingHistoryTable.setLayoutY(50);
        shoppingHistoryTable.setPrefWidth(500);
        shoppingHistoryTable.setPrefHeight(150);
        shoppingHistoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        pane.getChildren().add(shoppingHistoryTable);

        //create table
        String[] colNames = {"Category", "Code", "Name", "Price($)", "Quantity"};
        String[] properties = {"category", "code", "name", "price", "quantity"};
        for (int i = 0; i < colNames.length; i++) {
            String colName = colNames[i];
            TableColumn<ProductTableEntry, String> column = new TableColumn<>(colName);
            column.setSortable(false);
            column.setPrefWidth(118);
            column.setStyle("-fx-alignment: CENTER;");
            column.setCellValueFactory(new PropertyValueFactory<>(properties[i]));
            shoppingHistoryTable.getColumns().add(column);
        }
        this.setShoppingHistoryData();
    }

    private void initShoppingNodes() {
        selectedItemText = new Text();
        selectedItemText.setLayoutX(50);
        selectedItemText.setLayoutY(430);
        selectedItemText.setFont(Font.font(16));
        selectedItemText.setText("Selected Item");
        pane.getChildren().add(selectedItemText);

        selectedQuantityCombo = new ComboBox<>();
        selectedQuantityCombo.setLayoutX(170);
        selectedQuantityCombo.setLayoutY(410);
        selectedQuantityCombo.setPrefWidth(120);
        selectedQuantityCombo.setPromptText("Quantity");
        pane.getChildren().add(selectedQuantityCombo);

        Button addToCartBtn = new Button();
        addToCartBtn.setLayoutX(300);
        addToCartBtn.setLayoutY(410);
        addToCartBtn.setPrefWidth(120);
        addToCartBtn.setText("Set Qty in Cart");
        addToCartBtn.setOnAction(event -> addToCart());
        pane.getChildren().add(addToCartBtn);

        Button checkout = new Button();
        checkout.setLayoutX(430);
        checkout.setLayoutY(410);
        checkout.setPrefWidth(120);
        checkout.setText("Checkout");
        checkout.setOnAction(event -> new CheckoutWindow());
        pane.getChildren().add(checkout);
    }

    private void addToCart() {
        ProductTableEntry selectedItem = this.productTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an item.");
            alert.show();
            return;
        }

        if (selectedQuantityCombo.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select quantity.");
            alert.show();
            return;
        }


        userProcessor.getCurrentUser().setItemInCart(selectedItem.getId(),
                selectedQuantityCombo.getSelectionModel().getSelectedItem());


        updateProductTable();
        selectedQuantityCombo.getItems().clear();
        selectedItemText.setText("Selected Item");
    }

    private void initProductTable() {
        productTable = new TableView<>();
        productTable.setLayoutX(50);
        productTable.setLayoutY(50);
        productTable.setPrefWidth(500);
        productTable.setPrefHeight(350);
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // create table
        String[] colNames = {"Category", "Name", "Code", "Price ($)", "Stock", "In the Cart"};
        String[] properties = {"category", "name", "code", "price", "quantity", "inCart"};
        for (int i = 0; i < colNames.length; i++) {
            String colName = colNames[i];
            TableColumn<ProductTableEntry, String> column = new TableColumn<>(colName);
            column.setSortable(false);
            column.setPrefWidth(118);
            column.setStyle("-fx-alignment: CENTER;");
            column.setCellValueFactory(new PropertyValueFactory<>(properties[i]));
            productTable.getColumns().add(column);
        }
        updateProductTable();
        pane.getChildren().add(productTable);
    }

    public Scene getScene() {
        return scene;
    }
}
