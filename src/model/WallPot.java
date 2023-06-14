package model;

import boardifier.model.GameStageModel;
import boardifier.model.GridElement;

public class WallPot extends GridElement {

    /**
     * Constructor
     * @param x
     * @param y
     * @param gameStageModel
     */
    public WallPot(int x, int y, GameStageModel gameStageModel) {
        super("wallpot", x, y, 1, 10, gameStageModel);

    }

}
