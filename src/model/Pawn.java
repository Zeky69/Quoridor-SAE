package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;

public class Pawn extends GameElement {

    private int x;
    private int y;
    private int player;

    private int winY;

    public Pawn(int x, int y, int player ,int winY, GameStageModel gameStageModel) {
        super(gameStageModel);
        this.x = x;
        this.y = y;
        this.player = player;
        this.winY = winY;

        ElementTypes.register("pawn",50);
        type = ElementTypes.getType("pawn");
    }

    public int getPawnX() {
        return this.x;
    }

    public int getWinY(){
        return this.winY;
    }

    public void setWinY(int winY){
        this.winY = winY;
    }

    public int getPawnY() {
        return this.y;
    }

    public int[] getPawnXY(){return new int[]{x,y};}

    public int getPlayer() {
        return this.player;
    }

}
