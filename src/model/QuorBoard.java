package model;

import boardifier.model.GameStageModel;
import boardifier.model.GridElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QuorBoard extends GridElement {

    /**
     * Crée un tableau de booléens indiquant si une case est inatteignable ou non
     */
    public void setInvalidCells() {
        resetReachableCells(false);
        List<Point> valid = computeValidCells();
        if (valid != null) {
            for(Point p : valid) {
                reachableCells[p.y][p.x] = false;
            }
        }
    }

    /**
     * change the value of a cell to true (reachable)
     * @param x
     * @param y
     */
    public void setvalidCell(int x, int y) {
        reachableCells[y][x] = true;

    }

    /**
     * Create a list of all the valid cells
     * @return
     */
    private List<Point> computeValidCells() {
        List<Point> lst = new ArrayList<>();
        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                if (isEmptyAt(i,j)) {
                    lst.add(new Point(j,i));
                }
            }
        }
        return lst;
    }

    /**
     * Constructor
     * @param x
     * @param y
     * @param model
     */
    public QuorBoard(int x , int y, GameStageModel model) {
            super("QuorBoard",x,y, 9 ,9, model);
            resetReachableCells(false);
        }
}
