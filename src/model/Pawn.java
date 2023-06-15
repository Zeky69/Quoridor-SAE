package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.model.animation.AnimationStep;
import boardifier.view.GridGeometry;
import javafx.scene.paint.Color;

public class Pawn extends GameElement {

    private int x;
    private int y;
    private final int player;

    private int winY;

    private int wallCount = 10;

    /**
     * Constructor for the first initialization of the pawn
     *
     * @param x the x position
     * @param y the y position
     * @param player the player
     * @param winY the win y position
     * @param gameStageModel the game stage model
     */
    public Pawn(int x, int y, int player, int winY, GameStageModel gameStageModel) {
        super(gameStageModel);
        this.x = x;
        this.y = y;
        this.player = player;
        this.winY = winY;

        ElementTypes.register("pawn", 50);
        type = ElementTypes.getType("pawn");
    }

    /**
     * Constructor for the copy of the pawn
     *
     * @param x the x position
     * @param y the y position
     * @param wallCount the number of wall
     * @param player the player
     * @param winY the win y position
     * @param gameStageModel the game stage model
     */
    public Pawn(int x, int y, int wallCount, int player, int winY, GameStageModel gameStageModel) {
        super(gameStageModel);
        this.x = x;
        this.y = y;
        this.player = player;
        this.winY = winY;
        this.wallCount = wallCount;

        ElementTypes.register("pawn", 50);
        type = ElementTypes.getType("pawn");
    }

    /**
     * Set the x position of the pawn
     *
     * @param x the x position
     */
    public void setPawnX(int x) {
        this.x = x;
    }

    /**
     * Set the y position of the pawn
     *
     * @param y the y position
     */
    public void setPawnY(int y) {
        this.y = y;
    }

    /**
     * Set the number of wall of the pawn
     *
     * @param wallCount the number of wall
     */
    public void setWallCount(int wallCount) {
        this.wallCount = wallCount;
    }

    /**
     * Get the number of wall of the pawn
     *
     * @return the number of wall
     */
    public int getWallCount() {
        return this.wallCount;
    }

    /**
     * Decrement the number of wall of the pawn
     */
    public void decrementWallCount() {
        this.wallCount--;
    }

    public void incrementWallCount() {
        this.wallCount++;
    }

    /**
     * Get the x position of the pawn
     *
     * @return the x position
     */
    public int getPawnX() {
        return this.x;
    }

    /**
     * Get the y position of the winning position of the pawn
     *
     * @return the win y position
     */
    public int getWinY() {
        return this.winY;
    }

    /**
     * Set the y position for a pawn to win
     *
     * @param winY
     */
    public void setWinY(int winY) {
        this.winY = winY;
    }

    /**
     * Get the y position of the pawn
     *
     * @return the y position
     */
    public int getPawnY() {
        return this.y;
    }

    /**
     * Get the x and y position of the pawn
     *
     * @return the x and y position
     */
    public int[] getPawnXY() {
        return new int[]{x, y};
    }

    /**
     * Set the x and y position of the pawn
     *
     * @param coord the x and y position
     */
    public void setPawnXY(int[] coord) {
        this.x = coord[0];
        this.y = coord[1];

    }

    /**
     * Get the player linked to the pawn
     *
     * @return the player
     */
    public int getPlayer() {
        return this.player;
    }

    /**
     * Copy the current pawn
     *
     * @return the copy of the pawn
     */
    public Pawn copy() {
        return new Pawn(this.x, this.y, this.wallCount, this.player, this.winY, this.gameStageModel);
    }

    /**
     *  Get the color of the pawn
     * @return Color of the pawn
     */
    public Color getColor() {
        if (this.player == 0) {
            return Color.LIGHTSKYBLUE;
        } else {
            return Color.PINK;
        }
    }

    /**
     *  update the pawn
     * @param width witdh
     * @param height  height
     * @param gridGeometry grid geometry
     */
    public void update(double width, double height, GridGeometry gridGeometry) {
        // if must be animated, move the pawn
        if (animation != null) {
            AnimationStep step = animation.next();
            if (step != null) {
                setLocation(step.getInt(0), step.getInt(1));
            } else {
                animation = null;
            }
        }
    }

}
