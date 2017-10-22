/**
 * Created by danielpclin on 2017/2/6.
 */
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
    public AlertBox() {
    }

    public static void display(String title, String message) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(300.0D);
        stage.setMinHeight(200.0D);
        Label label = new Label();
        label.setText(message);
        Button okButton = new Button("確定");
        okButton.setOnAction((e) -> {
            stage.close();
            Platform.exit();
        });
        VBox vBox = new VBox(20.0D);
        vBox.getChildren().addAll(label, okButton);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
