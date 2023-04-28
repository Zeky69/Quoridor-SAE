package view;

import boardifier.model.GameStageModel;
import boardifier.model.GridElement;
import boardifier.view.ConsoleColor;
import boardifier.view.GridLook;
import model.QuorBoard;
import model.QuorStageModel;
import model.Wall;

public class QuorGridLook extends GridLook {


    public QuorGridLook(int cellWidth, int cellHeight, GameStageModel gameStageModel, int depth, boolean showCoords) {
        super(cellWidth, cellHeight, ((QuorStageModel)gameStageModel).getBoard(), depth, showCoords);


    }


    protected void createShape() {
        GridElement gridElement = (GridElement) element;
        int nbRows = gridElement.getNbRows();
        int nbCols = gridElement.getNbCols();

        Wall[][] walls = model.QuorStageModel.getWalls();
        // start by drawing the border of each cell, which will be change after
        for(int i=0;i<nbRows;i++) {
            for(int j=0;j<nbCols;j++) {
                //top-left corner
                shape[i*cellHeight][j*cellWidth] = "\u2554";
                // top-right corner
                shape[i*cellHeight][(j+1)*cellWidth] = "\u2557";
                //bottom-left corner
                shape[(i+1)*cellHeight][j*cellWidth] = "\u255A";
                // bottom-right corner
                shape[(i+1)*cellHeight][(j+1)*cellWidth] = "\u255D";

                for(int k=1;k<cellWidth;k++) {
                    if(!walls[i][j].getWall(Wall.Direction.UP) && ( i == 0 || !walls[i-1][j].getWall(Wall.Direction.DOWN) )) {
                        shape[i*cellHeight][j*cellWidth+k] = "\u2550" ;
                        shape[(i+1)*cellHeight][j*cellWidth+k] = "\u2550";
                    }
                    else {
                        shape[i*cellHeight][j*cellWidth+k] = ConsoleColor.YELLOW_BOLD+"\u2501"+ConsoleColor.RESET;
                        if( walls[i][j].getWall(Wall.Direction.DOWN) ){
                            shape[(i+1)*cellHeight][j*cellWidth+k] = ConsoleColor.YELLOW_BOLD+"\u2501"+ConsoleColor.RESET;
                        }else{
                            shape[(i+1)*cellHeight][j*cellWidth+k] = "\u2550";
                        }
                    }
                }
                // draw left & righ vertical lines
                for(int k=1;k<cellHeight;k++) {
                    if(!walls[i][j].getWall(Wall.Direction.LEFT) && (j == 0 || !walls[i][j - 1].getWall(Wall.Direction.RIGHT))) {
                        shape[i*cellHeight+k][j*cellWidth] = "\u2551";
                        shape[i*cellHeight+k][(j+1)*cellWidth] = "\u2551";
                    }
                    else{
                        shape[i*cellHeight+k][j*cellWidth] = ConsoleColor.YELLOW_BOLD+"\u2503"+ConsoleColor.RESET;
                        if(walls[i][j].getWall(Wall.Direction.RIGHT)) {
                            shape[i * cellHeight + k][(j + 1) * cellWidth] = ConsoleColor.YELLOW_BOLD + "\u2503" + ConsoleColor.RESET;
                        }else{
                            shape[i * cellHeight + k][(j + 1) * cellWidth] = "\u2551";
                        }
                    }
                }
            }
        }
        // change intersections on first & last hori. border
        for (int j = 1; j < nbCols; j++) {
            shape[0][j*cellWidth] = "\u2566";
            shape[nbRows*cellHeight][j*cellWidth] = "\u2569";
        }
        // change intersections on first & last vert. border
        for (int i = 1; i < nbRows; i++) {
            shape[i*cellHeight][0] = "\u2560";
            shape[i*cellHeight][nbCols*cellWidth] = "\u2563";
        }
        // change intersections within
        for (int i = 1; i < nbRows; i++) {
            for (int j = 1; j < nbCols; j++) {
                shape[i*cellHeight][j*cellWidth] = "\u256C";
            }
        }
        // draw the coords, if needed
        if (showCoords) {
            for (int i = 0; i < nbRows; i++) {
                shape[(int) ((i + 0.5) * cellHeight)][nbCols * cellWidth + 1] = String.valueOf(i+1);
            }
            for (int j = 0; j < nbCols; j++) {
                char c = (char) (j + 'A');
                shape[nbRows * cellHeight + 1][(int) ((j + 0.5) * cellWidth)] = String.valueOf(c);
            }
        }

    }
}


