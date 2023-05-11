package model;

import boardifier.model.GameStageModel;
import boardifier.model.GridElement;

public class WallPot extends GridElement {
    public WallPot(int x, int y, GameStageModel gameStageModel) {
        super("wallpot", x, y, 10, 1, gameStageModel);
    }

}
