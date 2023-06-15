package view;

import boardifier.model.GridElement;
import boardifier.view.GridLook;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.WallPot;

public class WallPotLook extends GridLook {

    private Rectangle[] cells;
    Text text;

    /**
     * Constructor
     * @param size size of the wallpot
     * @param element the element
     */
    public WallPotLook(int size, GridElement element) {

        super(size * 10, size, size, size, 0, "0X000000", element);
        cells = new Rectangle[10];
        // create the rectangles.
        for (int i = 0; i < 10; i++) {
            cells[i] = new Rectangle(0, 0, Color.WHITE);
            addShape(cells[i]);
        }
    }


    /**
     * Change the look of the wallpot
     */

    @Override
    public void onChange() {
        // number of walls
        int nbWalls = ((WallPot) element).getNbWalls();
        // display nnumber of walls
        if (text == null) {
            text = new Text(nbWalls + "x");
            text.setX(-38);
            text.setY(+20);
            text.setFont(javafx.scene.text.Font.font("Verdana", 20));
            addShape(text);
        } else {
            text.setText(nbWalls + "x");
        }


    }


}