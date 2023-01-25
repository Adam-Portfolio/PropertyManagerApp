package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApartmentApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(
                "ApartmentView.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("AFravel Property Manager");
        primaryStage.show();

    }
}
