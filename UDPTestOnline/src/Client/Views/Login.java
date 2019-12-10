package Client.Views;

import Client.MainClient;
import Client.Utils.MessageService;
import Server.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;

public class Login {
    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private void onLogin() throws IOException, ClassNotFoundException {
        User user = MainClient.login(new User(txtUsername.getText(), txtPassword.getText()));

        if (user == null) {
            MessageService.showWarning("Username and Password does not match!");
        } else {
            MainClient.setUserCurrent(user);
            MainClient.showExam();
        }
    }

    @FXML
    private void onSignup() throws IOException {
        MainClient.showSignup();
    }

    @FXML
    private void onRegister() {

    }
}
