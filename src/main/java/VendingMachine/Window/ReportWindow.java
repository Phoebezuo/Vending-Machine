package VendingMachine.Window;

import VendingMachine.Data.Product;
import VendingMachine.Data.Transaction;
import VendingMachine.Data.User;
import VendingMachine.Processor.CashProcessor;
import VendingMachine.Processor.ProductProcessor;
import VendingMachine.Processor.UserProcessor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReportWindow {

    private final AnchorPane pane;
    private Button userReportBtn;
    private Button cancelReportBtn;
    private Button transactionReportBtn;
    private Button currentItemReportBtn;
    private Button soldItemReportBtn;
    private Button changeReportBtn;

    public ReportWindow() throws IOException {
        Stage stage = new Stage();
        pane = new AnchorPane();
        Scene scene = new Scene(pane, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Report");
        stage.show();

        Files.createDirectories(Paths.get("Report/"));
        initButtons();
        initBtnActions();
    }

    private void initButtons() {
        currentItemReportBtn = new Button("Generate Current Items Report");
        soldItemReportBtn = new Button("Generate Sold Items Report");
        changeReportBtn = new Button("Generate Change Report");
        transactionReportBtn = new Button("Generate Transaction Report");
        userReportBtn = new Button("Generate User Report");
        cancelReportBtn = new Button("Generate Cancel Transaction Report");

        Button[] buttons = {currentItemReportBtn, soldItemReportBtn, changeReportBtn, transactionReportBtn,
                userReportBtn, cancelReportBtn};

        for (int i = 0; i < buttons.length; i++) {
            Button button = buttons[i];
            button.setLayoutX(50);
            button.setLayoutY(60 + 50 * i);
            button.setPrefWidth(300);
            button.setPrefHeight(30);
            pane.getChildren().add(button);
        }
    }

    private void initBtnActions() {
        currentItemReportBtn.setOnAction(event -> {
            if (UserProcessor.getInstance().getCurrentUser().getPermission(User.Permission.MANAGE_ITEM)) {
                generateCurrentItemReport();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You don't have the permission " +
                        "to do this action.");
                alert.show();
            }
        });

        soldItemReportBtn.setOnAction(event -> {
            if (UserProcessor.getInstance().getCurrentUser().getPermission(User.Permission.MANAGE_ITEM)) {
                generateSoldItemReport();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You don't have the permission " +
                        "to do this action.");
                alert.show();
            }
        });

        changeReportBtn.setOnAction(event -> {
            if (UserProcessor.getInstance().getCurrentUser().getPermission(User.Permission.MANAGE_CASH)) {
                generateChangeReport();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You don't have the permission " +
                        "to do this action.");
                alert.show();
            }
        });

        transactionReportBtn.setOnAction(event -> {
            if (UserProcessor.getInstance().getCurrentUser().getPermission(User.Permission.MANAGE_CASH)) {
                generateTransactionReport();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You don't have the permission " +
                        "to do this action.");
                alert.show();
            }
        });

        userReportBtn.setOnAction(event -> {
            if (UserProcessor.getInstance().getCurrentUser().getPermission(User.Permission.MANAGE_USER)) {
                generateUserReport();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You don't have the permission " +
                        "to do this action.");
                alert.show();
            }
        });

        cancelReportBtn.setOnAction(event -> {
            if (UserProcessor.getInstance().getCurrentUser().getPermission(User.Permission.MANAGE_USER)) {
                generateCancelReport();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You don't have the permission " +
                        "to do this action.");
                alert.show();
            }
        });
    }

    private void generateCurrentItemReport() {
        try {
            FileWriter csvWriter = new FileWriter("Report/CurrentItemReport.csv");

            List<String> title = Arrays.asList("Code", "Name", "Category", "Price", "Quantity");
            csvWriter.append(String.join(",", title));
            csvWriter.append("\n");

            Map<Integer, Product> productMap = ProductProcessor.getInstance().getProductMap();
            productMap.forEach((k, v) -> {
                String code = v.getCode();
                String name = v.getName();
                String price = Double.toString(v.getPrice());
                String quantity = Integer.toString(v.getStock());
                String category = v.getCategory().toString();
                List<String> texts = Arrays.asList(code, name, category, price, quantity);
                try {
                    csvWriter.append(String.join(",", texts));
                    csvWriter.append("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateSoldItemReport() {
        try {
            FileWriter csvWriter = new FileWriter("Report/SoldItemReport.csv");

            List<String> title = Arrays.asList("Code", "Name", "Sold");
            csvWriter.append(String.join(",", title));
            csvWriter.append("\n");

            Map<Integer, Product> productMap = ProductProcessor.getInstance().getProductMap();
            productMap.forEach((k, v) -> {
                String code = v.getCode();
                String name = v.getName();
                String sold = Integer.toString(v.getSold());
                List<String> texts = Arrays.asList(code, name, sold);
                try {
                    csvWriter.append(String.join(",", texts));
                    csvWriter.append("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateChangeReport() {
        try {
            FileWriter csvWriter = new FileWriter("Report/ChangeReport.csv");

            List<String> title = Arrays.asList("Value", "Number");
            csvWriter.append(String.join(",", title));
            csvWriter.append("\n");

            Map<Double, Integer> cashMap = CashProcessor.getInstance().getCashMap();
            List<Double> list = new ArrayList<>(cashMap.keySet());
            Collections.sort(list);
            for (Double value : list) {
                List<String> texts = Arrays.asList(value.toString(), cashMap.get(value).toString());
                csvWriter.append(String.join(",", texts));
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateTransactionReport() {
        try {
            FileWriter csvWriter = new FileWriter("Report/TransactionReport.csv");

            List<String> title = Arrays.asList("Date", "Paid Amount", "Return Change", "Paid Method", "Product", "Quantity");
            csvWriter.append(String.join(",", title));
            csvWriter.append("\n");

            List<Transaction> transactionList = Transaction.getTransactionList();
            for (Transaction t: transactionList) {
                if (t.getStatus() != Transaction.Status.PAID) {
                    continue;
                }
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd HH:mm");
                String date = t.getDate().format(fmt);
                String paidAmount = Double.toString(t.getPaidAmount());
                String returnChange = Double.toString(t.getPaidAmount() - t.getTotalPrice());
                String paidMethod = t.getPayment().toString();
                Map<Product, Integer> shoppingList = t.getShoppingList();
                shoppingList.forEach((k, v) -> {
                    String name = k.getName();
                    String quantity = v.toString();
                    List<String> texts = Arrays.asList(date, paidAmount, returnChange, paidMethod, name, quantity);
                    try {
                        csvWriter.append(String.join(",", texts));
                        csvWriter.append("\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateUserReport() {
        try {
            FileWriter csvWriter = new FileWriter("Report/UserReport.csv");

            List<String> title = Arrays.asList("ID", "Username", "Password", "Type");
            csvWriter.append(String.join(",", title));
            csvWriter.append("\n");

            for (User user : UserProcessor.getInstance().getUsers()) {
                List<String> texts = Arrays.asList(Integer.toString(user.getId()),
                        user.getUsername(), user.getPassword(), user.getType().toString());
                csvWriter.append(String.join(",", texts));
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateCancelReport() {
        try {
            FileWriter csvWriter = new FileWriter("Report/CancelledReport.csv");

            List<String> title = Arrays.asList("Date", "User", "Reason");
            csvWriter.append(String.join(",", title));
            csvWriter.append("\n");

            List<Transaction> transactionList = Transaction.getTransactionList();
            for (Transaction t: transactionList) {
                if (t.getStatus() == Transaction.Status.CANCELLED) {
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd HH:mm");
                    String date = t.getDate().format(fmt);
                    String user = t.getPayee().getUsername();
                    String reason = t.getReason();
                    List<String> texts = Arrays.asList(date, user, reason);
                    try {
                        csvWriter.append(String.join(",", texts));
                        csvWriter.append("\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
