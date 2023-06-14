package view;

import boardifier.model.GridElement;
import boardifier.view.GridLook;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class WallPotLook extends GridLook {

    /**
     * Constructor
     * @param cellWidth
     * @param cellHeight
     * @param gridElement
     */

    private Rectangle[] cells;

    public WallPotLook(int size, GridElement element) {

        super(size*10, size, size, size, 0, "0X000000", element);
        cells = new Rectangle[10];
        // create the rectangles.
        for(int i=0;i<10;i++) {
            cells[i] = new Rectangle(0, 0, Color.WHITE);
            addShape(cells[i]);
        }
    }

    @Override
    public void onChange() {
        // number of walls

    }


    }