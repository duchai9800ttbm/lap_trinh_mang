package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import server.NetworkConnection;

import java.awt.*;
import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private NetworkConnection networkConnection = createClient();

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        this.primaryStage.setResizable(false);
        this.primaryStage.setTitle("Testing Online");

//        this.initRootLayout();

//        this.initLoginLayot();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/chat.fxml"));
        AnchorPane chatLayout = (AnchorPane) loader.load();

        Scene scene = new Scene(chatLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        networkConnection.startConnection();
    }

    public void initRootLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/root.fxml"));
        rootLayout = (BorderPane) loader.load();

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void initLoginLayot() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/login.fxml"));
        GridPane loginView = (GridPane) loader.load();

        Scene scene = new Scene(loginView);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Client createClient() {
        return new Client("127.0.0.1", 5050, data -> {
            Platform.runLater(() -> {
                System.out.println(data);
            });
        });
    }
}
