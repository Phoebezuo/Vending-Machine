package VendingMachine.Window.CheckoutManagement;

import VendingMachine.Data.CreditCard;
import VendingMachine.Data.Transaction;
import VendingMachine.Data.User;
import VendingMachine.Processor.CardProcessor;
import VendingMachine.Processor.UserProcessor;
import VendingMachine.Window.MainWindow;
import VendingMachine.Window.TimeRemain;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.TextFieldSkin;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CardPaymentWindow {
    private final Stage stage;
    private final AnchorPane pane;
    private final User currentUser;
    private TextField nameField;
    private TextField numberField;
    private CheckBox checkBox;
    private TimeRemain time;

    public CardPaymentWindow() {
        pane = new AnchorPane();
        Scene scene = new Scene(pane, 370, 100);
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Card Payment");
        stage.show();
        currentUser = UserProcessor.getInstance().getCurrentUser();
        initCheckBox();
        initTextField();
        initBtn();
        if (currentUser.getType() != User.UserType.ANONYMOUS && currentUser.getCard() != null) {
            nameField.setText(currentUser.getCard().getName());
            numberField.setText(currentUser.getCard().getNumber());
            checkBox.setSelected(true);
        }
        time = new TimeRemain(stage, pane, 10, 11);
    }

    private void initTextField() {
        nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setLayoutX(20);
        nameField.setLayoutY(20);
        nameField.setPrefWidth(150);

        numberField = new TextField();
        numberField.setPromptText("Number");
        numberField.setLayoutX(200);
        numberField.setLayoutY(20);
        numberField.setPrefWidth(150);
        numberField.setSkin(new TextFieldSkin(numberField) {
            // Hide the password and show as *
            @Override
            protected String maskText(String txt) {
                return "*".repeat(txt.length());
            }
        });

        pane.getChildren().add(nameField);
        pane.getChildren().add(numberField);
    }

    private void initBtn() {
        Button checkBtn = new Button();
        checkBtn.setText("Check");
        checkBtn.setLayoutX(150);
        checkBtn.setLayoutY(60);
        pane.getChildren().add(checkBtn);
        checkBtn.setOnAction(event -> checkAction());

        Button cancelBtn = new Button();
        cancelBtn.setText("Cancel");
        cancelBtn.setLayoutX(230);
        cancelBtn.setLayoutY(60);
        pane.getChildren().add(cancelBtn);
        cancelBtn.setOnAction(event -> cancelAction());
    }

    private void cancelAction() {
        UserProcessor.getInstance().getCurrentUser().cancelShopping("user cancelled.");
        UserProcessor.getInstance().logoutUser();
        MainWindow.getInstance().update();
        time.stopTime();
        stage.close();
    }

    private void initCheckBox() {
        checkBox = new CheckBox();
        checkBox.setText("Save card");
        checkBox.setLayoutX(20);
        checkBox.setLayoutY(60);
        pane.getChildren().add(checkBox);
        checkBox.setOnAction(event -> {
            if (currentUser.getType() == User.UserType.ANONYMOUS) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Anonymous user can't save " +
                        "card");
                alert.show();
                checkBox.setSelected(false);
            }
        });
    }

    private void checkAction() {
        time.stopTime();
        String name = nameField.getText();
        String number = numberField.getText();
        CardProcessor cardProcessor = CardProcessor.getInstance();
        CreditCard card = cardProcessor.validateCard(name, number);
        if (card != null) {
                if (currentUser.pay(currentUser.getTotalPrice()) == 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successful!");
                    alert.show();
                    if (checkBox.isSelected()) {
                        currentUser.setCard(card);
                    }else{
                        currentUser.setCard(null);
                    }
                    time.stopTime();
                    UserProcessor.getInstance().logoutUser();
                    MainWindow.getInstance().update();
                    stage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Fail to pay.");
                    alert.show();
                }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Card does not exist.");
            alert.show();
        }
    }
}
