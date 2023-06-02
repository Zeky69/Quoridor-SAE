package view;

import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.model.GridElement;
import boardifier.view.GridLook;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.QuorBoard;
import model.QuorStageModel;
import model.Wall;

import java.awt.*;

public class QuorGridLook extends GridLook {
    Wall[][] walls ;
    private Rectangle[][] cells;

    /**
     * Constructor
     * @param cellWidth
     * @param cellHeight
     */
    public QuorGridLook(int cellWidth, int cellHeight, GameElement element) {
        // 10 = taille des murs
        // 80 = espace sur le coté pour afficher les murs restants
        super((cellWidth)*9,(cellHeight)*9,cellWidth, cellHeight,10, "0x000000", element);
        System.out.println(cellWidth +" , "+ cellHeight);
        cells = new Rectangle[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0 ; j < 9; j++) {
                cells[i][j] = new Rectangle(cellWidth, cellHeight);
                cells[i][j].setX(j * cellWidth + borderWidth);
                cells[i][j].setY(i * cellHeight+ borderWidth);
                cells[i][j].setFill(javafx.scene.paint.Color.valueOf("0xFFFFFF"));
//                cells[i][j].setStyle("-fx-stroke: black; -fx-stroke-width: 10;");
                addShape(cells[i][j]);
            }
        }
    }



    public QuorGridLook(int size, GameElement element) {
        // NB: To have more liberty in the design, GridLook does not compute the cell size from the dimension of the element parameter.
        // If we create the 3x3 board by adding a border of 10 pixels, with cells occupying all the available surface,
        // then, cells have a size of (size-20)/3
        super(size, size, (size-20)/9, (size-20)/9, 10, "0X000000", element);
        cells = new Rectangle[9][9];
        // create the rectangles.
        for (int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
//                javafx.scene.paint.Color c;
//                if ((i+j)%2 == 0) {
//                    c = javafx.scene.paint.Color.BEIGE;
//                }
//                else {
//                    c = Color.DARKGRAY;
//                }

                cells[i][j] = new Rectangle(cellWidth, cellHeight);
                cells[i][j].setFill(javafx.scene.paint.Color.valueOf("0xFFFFFF"));
                cells[i][j].setX(j*cellWidth+borderWidth);
                cells[i][j].setY(i*cellHeight+borderWidth);
                cells[i][j].setStyle("-fx-stroke: black; -fx-stroke-width: 10;");

                addShape(cells[i][j]);
            }
        }
    }

    @Override
    public void onChange() {}
}


