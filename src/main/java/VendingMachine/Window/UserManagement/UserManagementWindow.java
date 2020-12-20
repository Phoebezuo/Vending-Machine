package VendingMachine.Window.UserManagement;

import VendingMachine.Data.User;
import VendingMachine.Processor.UserProcessor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UserManagementWindow {
    private final AnchorPane pane;
    private final UserProcessor userProcessor;
    private TableView<UserTableEntry> table;
    private ComboBox<String> typeCombo;
    private Button addButton;
    private Button changeButton;
    private Button removeButton;
    private TextField usernameField;
    private TextField passwordField;
    private int selectedId;


    public UserManagementWindow() {
        Stage stage = new Stage();
        pane = new AnchorPane();
        Scene scene = new Scene(pane, 600, 480);
        stage.setScene(scene);
        stage.setTitle("User Management");
        stage.show();
        userProcessor = UserProcessor.getInstance();
        initTable();
        initButton();
        initButtonActions();
        initCombobox();
        initTextFields();
        selectedId = -1;
    }

    private void initTable() {
        table = new TableView<>();
        table.setLayoutX(100);
        table.setLayoutY(15);
        table.setPrefWidth(400);
        table.setPrefHeight(300);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        pane.getChildren().add(table);

        // create table
        String[] colNames = {"ID", "User Name", "Password", "Type"};
        String[] properties = {"id", "username", "password", "type"};
        for (int i = 0; i < colNames.length; i++) {
            String colName = colNames[i];
            TableColumn<UserTableEntry, String> column = new TableColumn<>(colName);
            column.setSortable(false);
            column.setPrefWidth(118);
            column.setStyle("-fx-alignment: CENTER;");
            column.setCellValueFactory(new PropertyValueFactory<>(properties[i]));
            table.getColumns().add(column);
        }
        table.setOnMouseClicked(event -> {
            if (!table.getSelectionModel().isEmpty()) {
                UserTableEntry selected = table.getSelectionModel().getSelectedItem();
                selectedId = Integer.parseInt(selected.getId());
                usernameField.setText(selected.getUsername());
                passwordField.setText(selected.getPassword());
                typeCombo.getSelectionModel().select(selected.getType());
            }
        });

        setTableData();
    }

    private void initButton() {
        addButton = new Button();
        changeButton = new Button();
        removeButton = new Button();

        Button[] buttons = {addButton, changeButton, removeButton};
        String[] texts = {"Add", "Change", "Remove"};

        for (int i = 0; i < buttons.length; i++) {
            Button button = buttons[i];
            button.setLayoutX(90 + 150 * i);
            button.setLayoutY(400);
            button.setPrefWidth(120);
            button.setPrefHeight(30);
            button.setText(texts[i]);
            pane.getChildren().add(button);
        }
    }

    private void initButtonActions() {
        addButton.setOnAction((event -> addAction()));
        removeButton.setOnAction((event -> removeAction()));
        changeButton.setOnAction((event -> changeAction()));
    }

    private void initCombobox() {
        typeCombo = new ComboBox<>();
        typeCombo.setLayoutX(390);
        typeCombo.setLayoutY(350);
        for (User.UserType s : User.UserType.values()) {
            if (s != User.UserType.ANONYMOUS) {
                typeCombo.getItems().add(s.toString());
            }
        }
        pane.getChildren().add(typeCombo);
    }

    private void initTextFields() {
        usernameField = new TextField();
        usernameField.setLayoutX(90);
        usernameField.setLayoutY(350);
        usernameField.setPrefWidth(120);
        usernameField.setPromptText("Username");

        passwordField = new TextField();
        passwordField.setLayoutX(240);
        passwordField.setLayoutY(350);
        passwordField.setPrefWidth(120);
        passwordField.setPromptText("Password");

        pane.getChildren().add(usernameField);
        pane.getChildren().add(passwordField);
    }

    private void setTableData() {
        // set data to table
        table.getItems().clear();
        for (User user : userProcessor.getUsers()) {
            table.getItems().add(new UserTableEntry(Integer.toString(user.getId()),
                    user.getUsername(),
                    user.getPassword(),
                    user.getType().toString()));
        }
    }

    private void addAction() {
        String type = typeCombo.getSelectionModel().getSelectedItem();
        if (!validateInput()) {
            return;
        }
        if (userProcessor.addUser(usernameField.getText(), passwordField.getText(), type)) {
            setTableData();
            selectedId = -1;
        } else {
            alert(Alert.AlertType.INFORMATION, "User exists.");
        }
    }

    private void removeAction() {
        if (table.getSelectionModel().getSelectedItem() == null) {
            alert(Alert.AlertType.WARNING, "You don't select any user.");
            return;
        }
        int id = Integer.parseInt(table.getSelectionModel().getSelectedItem().getId());
        if (userProcessor.removeUser(id)) {
            setTableData();
        }
    }

    private void changeAction() {
        if (selectedId < 0) {
            alert(Alert.AlertType.WARNING, "You don't select any user.");
            return;
        } else if (selectedId == 0) {
            alert(Alert.AlertType.WARNING, "You can't remove the anonymous user" +
                    ".");
            return;
        } else if (!validateInput()) {
            return;
        }
        userProcessor.setUsername(selectedId, usernameField.getText());
        userProcessor.setPassword(selectedId, passwordField.getText());
        userProcessor.setUserType(selectedId, typeCombo.getSelectionModel().getSelectedItem());

        usernameField.setText("");
        passwordField.setText("");
        typeCombo.getSelectionModel().clearSelection();
        setTableData();
        selectedId = -1;
    }

    private boolean validateInput() {
        if (usernameField.getText().trim().isEmpty()) {
            alert(Alert.AlertType.WARNING, "Username needed");
            return false;
        } else if (passwordField.getText().trim().isEmpty()) {
            alert(Alert.AlertType.WARNING, "Password needed");
            return false;
        } else if (typeCombo.getSelectionModel().isEmpty()) {
            alert(Alert.AlertType.WARNING, "User type needed.");
            return false;
        }
        return true;
    }

    private void alert(Alert.AlertType warning, String s) {
        Alert alert = new Alert(warning, s);
        alert.show();
    }

}
