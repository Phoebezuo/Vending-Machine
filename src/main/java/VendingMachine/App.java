package VendingMachine;

import VendingMachine.Data.Transaction;
import VendingMachine.Processor.CardProcessor;
import VendingMachine.Processor.CashProcessor;
import VendingMachine.Processor.ProductProcessor;
import VendingMachine.Processor.UserProcessor;
import VendingMachine.Window.MainWindow;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            CardProcessor.load();
            CashProcessor.load();
            ProductProcessor.load();
            UserProcessor.load();
            Transaction.load();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Can't load processors.");
            alert.show();
        }

        MainWindow mainWindow = MainWindow.getInstance();
        primaryStage.setScene(mainWindow.getScene());
        primaryStage.setTitle("Vending Machine");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        DatabaseHandler.saveCashData(CashProcessor.getInstance().getCashMap());
        DatabaseHandler.saveProductData(ProductProcessor.getInstance().getProductMap());
        DatabaseHandler.saveUserData(UserProcessor.getInstance().getUsers());
        DatabaseHandler.saveTransactionData(Transaction.getTransactionList());
    }
}
