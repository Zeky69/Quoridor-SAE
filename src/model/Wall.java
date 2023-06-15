package model;

import boardifier.model.GameElement;
import boardifier.model.GameStageModel;

import java.util.Arrays;

public class Wall extends GameElement {
    public enum Direction {UP, DOWN, LEFT, RIGHT}

    private boolean[] wall;

    int[] colorWall;
    private final GameStageModel gameStageModel;

    /**
     * Constructor
     *
     * @param gameStageModel the game stage model
     */
    public Wall(GameStageModel gameStageModel) {
        super(gameStageModel);
        this.gameStageModel = gameStageModel;
    }

    /**
     * Copy constructor
     *
     * @param wall the wall
     * @param gameStageModel the game stage model
     */
    public Wall(boolean[] wall, GameStageModel gameStageModel) {
        super(gameStageModel);
        this.gameStageModel = gameStageModel;
        this.wall = wall;
        this.colorWall = new int[4];
    }

    /**
     * Get boolean array of wall representing the presence of a wall in each direction
     *
     * @return boolean array of wall
     */
    public boolean[] getWall() {
        return this.wall;
    }

    public static int[] directionToInt(Direction direction) {
        if (direction == Direction.UP) {
            return new int[]{0, -1};
        } else if (direction == Direction.DOWN) {
            return new int[]{0, 1};
        } else if (direction == Direction.LEFT) {
            return new int[]{-1, 0};
        } else if (direction == Direction.RIGHT) {
            return new int[]{1, 0};
        }
        return new int[]{0, 0};
    }

    public static int[] get2ndWall(Direction direction, int x1, int y1) {
        int x2 = x1;
        int y2 = y1;
        if (direction == Direction.UP || direction == Direction.DOWN) {
            x2++;

        } else {
            y2++;
        }
        return new int[]{x2, y2};
    }

    /**
     * Get the presence of a wall in a direction
     *
     * @param direction of the wall
     * @return true if there is a wall in the direction
     */
    public boolean getWall(Direction direction) {
        if (Direction.UP == direction) {
            return this.wall[0];
        } else if (Direction.DOWN == direction) {
            return this.wall[1];
        } else if (Direction.LEFT == direction) {
            return this.wall[2];
        } else if (Direction.RIGHT == direction) {
            return this.wall[3];
        }
        return false;
    }

    /**
     * Check if the wall's neighbors are false (no wall)
     *
     * @return
     */
    public boolean isEmpty() {
        return Arrays.equals(this.wall, new boolean[4]);
    }

    /**
     * Convert an int to a direction
     *
     * @param i
     * @return
     */
    public static Direction intToDirection(int i) {
        if (i == 0) {
            return Direction.UP;
        } else if (i == 1) {
            return Direction.DOWN;
        } else if (i == 2) {
            return Direction.LEFT;
        } else if (i == 3) {
            return Direction.RIGHT;
        }
        return null;
    }

    /**
     * Check if the wall is on the border of the board
     *
     * @param coord    of the wall
     * @param direction of the wall
     * @return true if the wall is on the border
     */
    public static boolean isBorder(int[] coord, Wall.Direction direction) {
        return direction == Direction.UP && coord[1] == 0
                || direction == Direction.DOWN && coord[1] == 8
                || direction == Direction.LEFT && coord[0] == 0
                || direction == Direction.RIGHT && coord[0] == 8;
    }


    public int[] getColorWall() {
        return colorWall;
    }

    /**
     * Set the presence of a wall in a direction
     *
     * @param direction of the wall
     * @param bool     true if there is a wall
     */
    public void setWall(Direction direction, boolean bool, int color) {
        if (Direction.UP == direction) {
            this.wall[0] = bool;
            this.colorWall[0] = color + 1;
        } else if (Direction.DOWN == direction) {
            this.wall[1] = bool;
            this.colorWall[1] = color + 1;
        } else if (Direction.LEFT == direction) {
            this.wall[2] = bool;
            this.colorWall[2] = color + 1;
        } else if (Direction.RIGHT == direction) {
            this.wall[3] = bool;
            this.colorWall[3] = color + 1;
        }
    }

    /**
     * @return the gameStageModel
     */
    public GameStageModel getGameStageModel() {
        return this.gameStageModel;
    }

    /**
     * Copy the wall
     *
     * @return the copy of the wall
     */
    public Wall copy() {
        boolean[] wallBool = this.getWall().clone();
        return new Wall(wallBool, this.getGameStageModel());
    }

    public String toString() {
        return Arrays.toString(wall);
    }
}
