package VendingMachine.Window.CheckoutManagement;

import VendingMachine.Processor.UserProcessor;
import VendingMachine.Window.MainWindow;
import VendingMachine.Window.TimeRemain;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CheckoutWindow {
    private final double amount;
    private Stage stage;
    private AnchorPane pane;
    private Button cashBtn;
    private Button cardBtn;
    private Button cancel;
    private TimeRemain time;


    public CheckoutWindow() {
        this.amount = UserProcessor.getInstance().getCurrentUser().getTotalPrice();
        if (amount == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please purchase an item.");
            alert.show();
            return;
        }

        stage = new Stage();
        pane = new AnchorPane();
        Scene scene = new Scene(pane, 250, 200);
        stage.setScene(scene);
        stage.setTitle("Checkout");
        stage.show();

        initButtons();
        intiText();
        initButtonActions();
        time = new TimeRemain(stage, pane, 30, 125);
    }

    private void initButtonActions() {
        // create actions here(New Window)
        cashBtn.setOnAction((event -> {
            new CashPaymentWindow();
            stage.close();
            time.stopTime();
        }));

        cardBtn.setOnAction(event -> {
            new CardPaymentWindow();
            stage.close();
            time.stopTime();
        });

        cancel.setOnAction(event -> cancel());
    }

    private void cancel() {
        UserProcessor.getInstance().getCurrentUser().cancelShopping("User cancelled.");
        time.stopTime();
        stage.close();

        UserProcessor.getInstance().logoutUser();
        MainWindow.getInstance().update();
    }

    private void intiText() {
        Text total = new Text();
        total.setX(50);
        total.setY(50);
        total.setText("Total amount: " + this.amount);
        pane.getChildren().add(total);
    }

    private void initButtons() {
        cardBtn = new Button();
        cashBtn = new Button();
        cancel = new Button();
        Button[] btns = {cardBtn, cancel, cashBtn};
        String[] names = {"Card", "Cancel", "Cash"};
        for (int i = 0; i < btns.length; i++) {
            btns[i].setLayoutY(150);
            btns[i].setLayoutX(15 + i * 80);
            btns[i].setPrefWidth(60);
            btns[i].setText(names[i]);
            pane.getChildren().add(btns[i]);
        }
    }
}
