package view;

import boardifier.view.RootPane;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
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
    public Button buttonModeHvH,buttonModeHvC,buttonModeCvC;
    int mode;

    public QuorRootPane(int mode) { super(); }

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
        buttonModeHvH = new Button("Human vs Human");
        buttonModeHvC = new Button("Human vs Computer");
        buttonModeCvC = new Button("Computer vs Computer");
        buttonModeHvH.setStyle("-fx-background-color: #d2b916; -fx-text-fill: white; -fx-font-size: 14px;");
        buttonModeHvC.setStyle("-fx-background-color: #d2b916; -fx-text-fill: white; -fx-font-size: 14px;");
        buttonModeCvC.setStyle("-fx-background-color: #d2b916; -fx-text-fill: white; -fx-font-size: 14px;");
        buttonModeHvH.setOnMouseEntered(e -> {buttonModeHvH.setCursor(Cursor.HAND);
            buttonModeHvH.setStyle("-fx-background-color: #A48F06FF;-fx-text-fill: white; -fx-font-size: 14px;");});
        buttonModeHvC.setOnMouseEntered(e -> {buttonModeHvC.setCursor(Cursor.HAND);
            buttonModeHvC.setStyle("-fx-background-color: #A48F06FF;-fx-text-fill: white; -fx-font-size: 14px;");});
        buttonModeCvC.setOnMouseEntered(e -> {buttonModeCvC.setCursor(Cursor.HAND);
            buttonModeCvC.setStyle("-fx-background-color: #A48F06FF;-fx-text-fill: white; -fx-font-size: 14px;");});

        buttonModeHvH.setOnMouseExited(e -> buttonModeHvH.setStyle("-fx-background-color: #d2b916; -fx-text-fill: white; -fx-font-size: 14px;"));
        buttonModeHvC.setOnMouseExited(e -> buttonModeHvC.setStyle("-fx-background-color: #d2b916; -fx-text-fill: white; -fx-font-size: 14px;"));
        buttonModeCvC.setOnMouseExited(e -> buttonModeCvC.setStyle("-fx-background-color: #d2b916; -fx-text-fill: white; -fx-font-size: 14px;"));

        grid.add(buttonModeHvH, 0, 1);
        grid.add(buttonModeHvC, 1, 1);
        grid.add(buttonModeCvC, 2, 1);

        flowPane.getChildren().add(grid);
        flowPane.setAlignment(javafx.geometry.Pos.CENTER);
        flowPane.setMinSize(500, 400);
        // put shapes in the group
        group.getChildren().clear();
        group.getChildren().addAll(background,flowPane);
    }
}
