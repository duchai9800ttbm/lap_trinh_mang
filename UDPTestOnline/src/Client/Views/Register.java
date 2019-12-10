package Client.Views;

import Client.MainClient;
import Client.Utils.MessageService;
import Server.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Register {
    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtRePassword;

    @FXML
    private void onSignin() throws IOException {
        MainClient.showLogin();
    }

    @FXML
    private void onRegister() throws IOException, ClassNotFoundException {
        if (txtUsername.getText().trim().length() > 0 && txtPassword.getText().trim().length() > 0
                && txtRePassword.getText().trim().length() > 0) {
            if (txtPassword.getText().equals(txtRePassword.getText())) {
                if (MainClient.register(new User(txtUsername.getText(), txtPassword.getText()))) {
                    MessageService.showInfo("User is registed!");
                    MainClient.showLogin();
                } else {
                    MessageService.showWarning("Username is existed!");
                }
            } else {
                MessageService.showWarning("Re-enter password again!");
            }
        } else {
            MessageService.showWarning("All field is required!");
        }
    }
}
