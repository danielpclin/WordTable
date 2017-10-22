/**
 * Created by danielpclin on 2016/11/13.
 */
import javax.swing.*;
import java.awt.*;

import java.util.Iterator;
import java.util.function.Consumer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;

public class WriteTable extends Application {
    TableView<Element> table;
    TextField nameInput;
    TextField contentInput;
    TextField numInput;
    TextField repeatInput;
    Stage primaryStage;

    public WriteTable() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("標籤紙");
        TableColumn<Element, String> nameColumn = new TableColumn("品項");
        nameColumn.setMinWidth(100.0D);
        nameColumn.setPrefWidth(100.0D);
        nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit((t) -> {
            ((Element)t.getTableView().getItems().get(t.getTablePosition().getRow())).setName((String)t.getNewValue());
        });
        TableColumn<Element, String> contentColumn = new TableColumn("標籤內容");
        contentColumn.setMinWidth(300.0D);
        contentColumn.setPrefWidth(300.0D);
        contentColumn.setCellValueFactory(new PropertyValueFactory("content"));
        contentColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        contentColumn.setOnEditCommit((t) -> {
            ((Element)t.getTableView().getItems().get(t.getTablePosition().getRow())).setContent((String)t.getNewValue());
        });
        TableColumn<Element, String> numColumn = new TableColumn("多少單位");
        numColumn.setMinWidth(100.0D);
        numColumn.setPrefWidth(100.0D);
        numColumn.setCellValueFactory(new PropertyValueFactory("num"));
        numColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        numColumn.setOnEditCommit((t) -> {
            ((Element)t.getTableView().getItems().get(t.getTablePosition().getRow())).setNum((String)t.getNewValue());
        });
        TableColumn<Element, Integer> repeatColumn = new TableColumn("重複次數");
        repeatColumn.setMinWidth(100);
        repeatColumn.setPrefWidth(100);
        repeatColumn.setCellValueFactory(new PropertyValueFactory("repeat"));
        repeatColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        repeatColumn.setOnEditCommit((t) -> {
            ((Element)t.getTableView().getItems().get(t.getTablePosition().getRow())).setRepeat((t.getNewValue()).intValue());
        });
        nameInput = new TextField();
        nameInput.setPromptText("品項");
        nameInput.setAlignment(Pos.CENTER);
        nameInput.setMinWidth(75);
        nameInput.setPrefWidth(75);
        contentInput = new TextField();
        contentInput.setPromptText("標籤內容");
        contentInput.setAlignment(Pos.CENTER);
        contentInput.setMinWidth(100);
        contentInput.setPrefWidth(200);
        numInput = new TextField();
        numInput.setPromptText("多少單位");
        numInput.setAlignment(Pos.CENTER);
        numInput.setMinWidth(75.0D);
        numInput.setPrefWidth(75.0D);
        repeatInput = new TextField();
        repeatInput.setPromptText("重複次數");
        repeatInput.setAlignment(Pos.CENTER);
        repeatInput.setMinWidth(75.0D);
        repeatInput.setPrefWidth(75.0D);
        Button addButton = new Button("新增");
        addButton.setOnAction((e) -> {
            this.addButtonClicked();
        });
        Button deleteButton = new Button("刪除");
        deleteButton.setOnAction((e) -> {
            this.deleteButtonClicked();
        });
        Button executeButton = new Button("創造檔案");
        executeButton.setOnAction((e) -> {
            this.executeButtonClicked();
        });
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10.0D, 10.0D, 10.0D, 10.0D));
        hBox.setSpacing(10.0D);
        hBox.getChildren().addAll(nameInput, contentInput, numInput, this.repeatInput, addButton, deleteButton, executeButton);
        table = new TableView();
        table.setEditable(true);
        table.setItems(this.getElement());
        table.getColumns().addAll(nameColumn, contentColumn, numColumn, repeatColumn);
        table.setPlaceholder(new Label("在表格底下新增內容"));
        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, hBox);
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void addButtonClicked() {
        Element element = new Element();
        element.setName(this.nameInput.getText());
        element.setContent(this.contentInput.getText());
        element.setNum(this.numInput.getText());
        element.setRepeat(Integer.parseInt(this.repeatInput.getText()));
        table.getItems().add(element);
        //nameInput.clear();
        contentInput.clear();
        numInput.clear();
        repeatInput.clear();
    }

    public void deleteButtonClicked() {
        ObservableList<Element> allElements = table.getItems();
        ObservableList<Element> elementSelected = table.getSelectionModel().getSelectedItems();
        allElements.removeAll(elementSelected);
    }

    public void executeButtonClicked() {
        if(ConfirmBox.display("創造檔案確認", "確定創造檔案?")) {
            Label label = new Label("進度:");
            ProgressBar progressBar = new ProgressBar();
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(label, progressBar);
            vBox.setPrefWidth(600.0D);
            vBox.setPrefHeight(400.0D);
            vBox.setSpacing(10.0D);
            progressBar.setPrefWidth(400.0D);
            Scene progress = new Scene(vBox);
            primaryStage.setScene(progress);
            progressBar.setProgress(0.0D);
            int repeatTotal = 0;
            String[] printStr = new String[500];
            int index = 0;
            Iterator var8 = this.table.getItems().iterator();

            while(var8.hasNext()) {
                Element element = (Element)var8.next();
                repeatTotal += element.getRepeat();

                for(int i = 0; i < element.getRepeat(); ++i) {
                    printStr[index] = element.getName() + "-" + element.getContent() + " " + element.getNum();
                    ++index;
                }
            }

            progressBar.setProgress(0.2D);
            Word doc = new Word();
            progressBar.setProgress(0.4D);
            doc.createXWPFDoc();
            progressBar.setProgress(0.5D);
            doc.createsectPr();
            progressBar.setProgress(0.6D);
            doc.setPageSize(STPageOrientation.PORTRAIT, 210, 297);
            doc.setMargin(12.5D, 15.5D, 12.5D, 10.5D);
            progressBar.setProgress(0.7D);
            doc.PopulateTable(repeatTotal, printStr);
            progressBar.setProgress(1.0D);
            AlertBox.display("成功", "檔案製作完成");
        }

    }

    public ObservableList<Element> getElement() {
        ObservableList<Element> elements = FXCollections.observableArrayList();
        return elements;
    }
}
