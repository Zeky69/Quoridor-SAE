package view;

import boardifier.view.RootPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;

public class QuorRootPane extends RootPane {
    public Button buttonPlay;
    public ComboBox<String> comboBox;
    TextField pseudo1, pseudo2;

    public QuorRootPane() { super(); }

    @Override
    public void createDefaultGroup() {
        Stop[] stops = new Stop[] { new Stop(0, Color.LIGHTSKYBLUE), new Stop(1, Color.PINK)};
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);

        Rectangle background = new Rectangle(0, 0, 500, 500);
        background.setFill(lg1);
        GridPane grid = new GridPane();
        FlowPane flowPane = new FlowPane();
        Text text = new Text("Playing to Quoridor");
        text.setFont(new Font(30));
        text.setStyle("-fx-font-family: 'Montserrat'; -fx-font-size: 30px; -fx-font-weight: bold;");
        text.setFill(Color.BLACK);
        grid.add(text, 0, 0, 3, 1);
        GridPane.setHalignment(text, javafx.geometry.HPos.CENTER);
        grid.setHgap(10);
        grid.setVgap(50);
        Label label1 = new Label("Nickname of player 1 :");
        Label label2 = new Label("Nickname of player 2 :");
        pseudo1 = new TextField();
        pseudo2 = new TextField();

        comboBox = new ComboBox<String>();
        ObservableList<String> list = FXCollections.observableArrayList("Human vs Human", "Human vs IA1", "Human vs IA2", "IA1 vs IA2");
        comboBox.setItems(list);
        comboBox.getSelectionModel().select(0);

        buttonPlay = new Button("Play");
        buttonPlay.setStyle("-fx-background-color: #d2b916; -fx-text-fill: white; -fx-font-size: 14px;");
        buttonPlay.setOnMouseEntered(e -> {buttonPlay.setCursor(Cursor.HAND);
            buttonPlay.setStyle("-fx-background-color: #A48F06FF;-fx-text-fill: white; -fx-font-size: 14px;");});

        buttonPlay.setOnMouseExited(e -> buttonPlay.setStyle("-fx-background-color: #d2b916; -fx-text-fill: white; -fx-font-size: 14px;"));

        grid.add(comboBox, 0, 1);
        grid.add(buttonPlay, 1, 1);

        flowPane.getChildren().add(grid);
        flowPane.setAlignment(javafx.geometry.Pos.CENTER);
        flowPane.setMinSize(500, 400);
        // put shapes in the group
        group.getChildren().clear();
        group.getChildren().addAll(background,flowPane);
    }


}
