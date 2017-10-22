/**
 * Created by danielpclin on 2017/2/6.
 */
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {
    static boolean answer;

    public ConfirmBox() {
    }

    public static boolean display(String title, String message) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(300.0D);
        stage.setMinHeight(200.0D);
        Label label = new Label();
        label.setText(message);
        Button confirmButton = new Button("確定");
        Button cancelButton = new Button("取消");
        confirmButton.setOnAction((e) -> {
            answer = true;
            stage.close();
        });
        cancelButton.setOnAction((e) -> {
            answer = false;
            stage.close();
        });
        VBox vBox = new VBox(20.0D);
        HBox hbox = new HBox(10.0D);
        vBox.getChildren().addAll(label, hbox);
        hbox.getChildren().addAll(confirmButton, cancelButton);
        vBox.setAlignment(Pos.CENTER);
        hbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.showAndWait();
        return answer;
    }
}
