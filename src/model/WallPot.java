package model;

import boardifier.model.GameStageModel;
import boardifier.model.GridElement;

public class WallPot extends GridElement {

    /**
     * Constructor
     *
     * @param x
     * @param y
     * @param gameStageModel
     */
    int nbWalls = 10;

    public WallPot(int x, int y, GameStageModel gameStageModel) {
        super("wallpot", x, y, 1, 10, gameStageModel);

    }

    public void lookChangedTrue() {
        lookChanged = true;
    }


    /**
     * Get the number of walls
     *
     * @return
     */

    public int getNbWalls() {
        return nbWalls;
    }


    public void removeWall() {
        nbWalls--;
    }

}
