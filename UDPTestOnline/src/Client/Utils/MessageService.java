package Client.Utils;

import javafx.scene.control.Alert;

public class MessageService {
    public static void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText(message);

        alert.showAndWait();
    }

    public static void showMessage(String title, String point) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(point);

        alert.showAndWait();
    }

    public static void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Infomation dialog");
        alert.setHeaderText(message);

        alert.showAndWait();
    }
}
