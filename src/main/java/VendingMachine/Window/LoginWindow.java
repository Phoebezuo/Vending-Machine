package VendingMachine.Window;

import VendingMachine.Processor.UserProcessor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.skin.TextFieldSkin;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginWindow {
    private final Stage stage;
    private final AnchorPane pane;
    private TextField inputUsername;
    private PasswordField inputPassword;

    public LoginWindow() {

        stage = new Stage();
        pane = new AnchorPane();
        Scene scene = new Scene(pane, 600, 170);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();

        initLabels();
        initInputFields();
        initButtons();
    }

    public void signInAction() {
        String usernameInp = inputUsername.getText();
        String passwordInp = inputPassword.getText();

        if (UserProcessor.getInstance().verifyUser(usernameInp, passwordInp)) {
//            Alert alert = new Alert(AlertType.INFORMATION, "Sign in successfully.");
//            alert.show();
            MainWindow.getInstance().changeAccountButtonText("Logout");
            MainWindow.getInstance().update();
            stage.close();
        } else {
            Alert alert = new Alert(AlertType.WARNING, "Fail to sign in. Wrong username or " +
                    "password.");
            alert.show();
        }
    }

    public void signUpAction() {
        String usernameInp = inputUsername.getText();
        String passwordInp = inputPassword.getText();
        UserProcessor userProcessor = UserProcessor.getInstance();

        if (userProcessor.addUser(usernameInp, passwordInp)) {
//            Alert alert = new Alert(AlertType.INFORMATION, "Sign up successfully.");
//            alert.show();
            userProcessor.verifyUser(usernameInp, passwordInp);
            MainWindow.getInstance().changeAccountButtonText("Logout");
            MainWindow.getInstance().update();
            stage.close();
        } else {
            Alert alert = new Alert(AlertType.WARNING, "User exists");
            alert.show();
        }
    }

    private void initLabels() {
        Label username = new Label("Username");
        username.setLayoutX(50);
        username.setLayoutY(15);
        pane.getChildren().add(username);
        Label password = new Label("Password");
        password.setLayoutX(350);
        password.setLayoutY(15);
        pane.getChildren().add(password);
    }

    private void initInputFields() {
        inputUsername = new TextField();
        inputUsername.setLayoutX(50);
        inputUsername.setLayoutY(50);
        inputUsername.setPrefWidth(200);
        inputUsername.setPromptText("Username");
        pane.getChildren().add(inputUsername);


        inputPassword = new PasswordField();
        inputPassword.setSkin(new TextFieldSkin(inputPassword) {
            // Hide the password and show as *
            @Override
            protected String maskText(String txt) {
                return "*".repeat(txt.length());
            }
        });
        inputPassword.setLayoutX(350);
        inputPassword.setLayoutY(50);
        inputPassword.setPrefWidth(200);
        inputPassword.setPromptText("password");
        pane.getChildren().add(inputPassword);
    }

    private void initButtons() {
        Button signInButton = new Button();
        signInButton.setLayoutX(75);
        signInButton.setLayoutY(105);
        signInButton.setPrefWidth(100);
        signInButton.setText("Sign In");
        signInButton.requestFocus();
        pane.getChildren().add(signInButton);
        signInButton.setOnAction(event -> signInAction());

        Button signUpButton = new Button();
        signUpButton.setLayoutX(400);
        signUpButton.setLayoutY(105);
        signUpButton.setPrefWidth(100);
        signUpButton.setText("Sign Up");
        signUpButton.requestFocus();
        pane.getChildren().add(signUpButton);
        signUpButton.setOnAction(event -> signUpAction());
    }

}
