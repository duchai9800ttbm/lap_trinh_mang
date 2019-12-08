package server.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Dashboard {
    @FXML
    private Button btnStart;

    @FXML
    private void onStart() {
        System.out.println("start");
    }
}
