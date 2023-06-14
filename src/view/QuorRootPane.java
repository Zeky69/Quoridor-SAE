package view;

import boardifier.view.RootPane;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.awt.*;

public class QuorRootPane extends RootPane {
    public Button buttonModeHvH,buttonModeHvC,buttonModeCvC;
    int mode;

    public QuorRootPane(int mode) { super(); }

    @Override
    public void createDefaultGroup() {
        Rectangle frame = new Rectangle(600, 100, Color.LIGHTGREY);
        GridPane grid = new GridPane();
        Text text = new Text("Playing to Quoridor");
        text.setFont(new Font(15));
        text.setFill(Color.BLACK);

        grid.add(text, 0, 0, 3, 1);
        grid.setHalignment(text, javafx.geometry.HPos.CENTER);

        buttonModeHvH = new Button("Human vs Human");
        buttonModeHvC = new Button("Human vs Computer");
        buttonModeCvC = new Button("Computer vs Computer");
        grid.add(buttonModeHvH, 0, 1);
        grid.add(buttonModeHvC, 1, 1);
        grid.add(buttonModeCvC, 2, 1);
        // put shapes in the group
        group.getChildren().clear();
        group.getChildren().addAll(frame,grid);
    }
}
