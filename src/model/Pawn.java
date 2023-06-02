package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.model.animation.AnimationStep;
import boardifier.view.GridGeometry;

public class Pawn extends GameElement {

    private int x;
    private int y;
    private int player;

    private int winY;

    private int wallCount = 10;

    /**
     * Constructor for the first initialization of the pawn
     * @param x
     * @param y
     * @param player
     * @param winY
     * @param gameStageModel
     */
    public Pawn(int x, int y, int player ,int winY, GameStageModel gameStageModel) {
        super(gameStageModel);
        this.x = x;
        this.y = y;
        this.player = player;
        this.winY = winY;

        ElementTypes.register("pawn",50);
        type = ElementTypes.getType("pawn");
    }

    /**
     * Constructor for the copy of the pawn
     * @param x
     * @param y
     * @param wallCount
     * @param player
     * @param winY
     * @param gameStageModel
     */
    public Pawn(int x, int y,int wallCount, int player ,int winY, GameStageModel gameStageModel) {
        super(gameStageModel);
        this.x = x;
        this.y = y;
        this.player = player;
        this.winY = winY;
        this.wallCount = wallCount;

        ElementTypes.register("pawn",50);
        type = ElementTypes.getType("pawn");
    }

    /**
     * Set the x position of the pawn
     * @param x
     */
    public void setPawnX(int x) {
        this.x = x;
    }

    /**
     * Set the y position of the pawn
     * @param y
     */
    public void setPawnY(int y) {
        this.y = y;
    }

    /**
     * Set the number of wall of the pawn
     * @param wallCount
     */
    public void setWallCount(int wallCount){
        this.wallCount = wallCount;
    }

    /**
     * Get the number of wall of the pawn
     * @return
     */
    public int getWallCount(){
        return this.wallCount;
    }

    /**
     * Decrement the number of wall of the pawn
     */
    public void decrementWallCount(){
        this.wallCount--;
    }
    public void incrementWallCount(){
        this.wallCount++;
    }

    /**
     * Get the x position of the pawn
     * @return
     */
    public int getPawnX() {
        return this.x;
    }

    /**
     * Get the y position of the winning position of the pawn
     * @return
     */
    public int getWinY(){
        return this.winY;
    }

    /**
     * Set the y position for a pawn to win
     * @param winY
     */
    public void setWinY(int winY){
        this.winY = winY;
    }

    /**
     * Get the y position of the pawn
     * @return
     */
    public int getPawnY() {
        return this.y;
    }

    /**
     * Get the x and y position of the pawn
     * @return
     */
    public int[] getPawnXY(){return new int[]{x,y};}

    /**
     * Set the x and y position of the pawn
     * @param coord
     */
    public void setPawnXY(int[] coord){
        this.x = coord[0];
        this.y = coord[1];

    }

    /**
     * Get the player linked to the pawn
     * @return
     */
    public int getPlayer() {
        return this.player;
    }

    /**
     * Copy the current pawn
     * @return
     */
    public Pawn copy(){
        return new Pawn(this.x , this.y ,this.wallCount, this.player , this.winY , this.gameStageModel);
    }

    public void update(double width, double height, GridGeometry gridGeometry) {
        // if must be animated, move the pawn
        if (animation != null) {
            AnimationStep step = animation.next();
            if (step != null) {
                setLocation(step.getInt(0), step.getInt(1));
            }
            else {
                animation = null;
            }
        }
    }

}
