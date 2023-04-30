package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;

public class Pawn extends GameElement {

    private int x;
    private int y;
    private int player;
    private int nbWalls;

    public Pawn(int x, int y, int player , GameStageModel gameStageModel) {
        super(gameStageModel);
        this.x = x;
        this.y = y;
        this.player = player;
        this.nbWalls = 10;

        ElementTypes.register("pawn",50);
        type = ElementTypes.getType("pawn");
    }

    public int getPawnX() {
        return this.x;
    }

    public int getPawnY() {
        return this.y;
    }

    public int getPlayer() {
        return this.player;
    }

    public int getNbWalls() {return this.nbWalls;}


}
