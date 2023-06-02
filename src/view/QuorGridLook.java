package view;

import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.model.GridElement;
import boardifier.view.GridLook;
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
        super((cellWidth)*9,(cellHeight)*9,cellWidth, cellHeight,1, "0x000000", element);
        System.out.println(cellWidth +" , "+ cellHeight);
        cells = new Rectangle[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0 ; j < 9; j++) {
                cells[i][j] = new Rectangle(cellWidth, cellHeight);
                cells[i][j].setX(i * (cellWidth));
                cells[i][j].setY(j * (cellHeight));
                cells[i][j].setFill(javafx.scene.paint.Color.valueOf("0xFFFFFF"));
                cells[i][j].setStyle("-fx-stroke: black; -fx-stroke-width: 10;");

                addShape(cells[i][j]);
            }
        }
    }

    @Override
    public void onChange() {}
}


