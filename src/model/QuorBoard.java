package model;

import boardifier.model.GameStageModel;
import boardifier.model.GridElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QuorBoard extends GridElement {

    public void setValidCells() {
        resetReachableCells(false);
        List<Point> valid = computeValidCells();
        if (valid != null) {
            for(Point p : valid) {
                reachableCells[p.y][p.x] = true;
            }
        }
    }
    public void setInvalidCells() {
        resetReachableCells(false);
        List<Point> valid = computeValidCells();
        if (valid != null) {
            for(Point p : valid) {
                reachableCells[p.y][p.x] = false;
            }
        }
    }

    public void setInvalalidCell(int x, int y) {
        reachableCells[y][x] = false;

    }

    public void setvalalidCell(int x, int y) {
        reachableCells[y][x] = true;

    }

    private List<Point> computeValidCells() { //TO DO finir de voir les destinations possibles
        List<Point> lst = new ArrayList<>();
        Pawn p = null;
        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                if (isEmptyAt(i,j)) {
                    lst.add(new Point(j,i));
                }
            }
        }
        return lst;
    }

    public QuorBoard(int x , int y, GameStageModel model) {
            super("QuorBoard",x,y, 9 ,9, model);
            resetReachableCells(false);

        }



}
