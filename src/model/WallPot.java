package model;

import boardifier.model.GameStageModel;
import boardifier.model.GridElement;

public class WallPot extends GridElement {


    int nbWalls = 10;

    /**
     * Constructor
      * @param x the x position
     * @param y the y position
     * @param gameStageModel the game stage model
     */
    public WallPot(int x, int y, GameStageModel gameStageModel) {
        super("wallpot", x, y, 1, 10, gameStageModel);

    }

    /**
     *  loockcganged set to true
     */
    public void lookChangedTrue() {
        lookChanged = true;
    }


    /**
     * Get the number of walls
     * @return the number of walls
     */
    public int getNbWalls() {
        return nbWalls;
    }


    /**
     * Remove a wall
     */
    public void removeWall() {
        nbWalls--;
    }

}
