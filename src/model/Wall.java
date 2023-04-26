package model;

import boardifier.model.GameElement;
import boardifier.model.GameStageModel;

import java.util.Objects;

public class Wall extends GameElement {

    public enum Direction {UP, DOWN, LEFT, RIGHT}

    private boolean[] wall;

    public Wall(boolean[] wall, GameStageModel gameStageModel) {
        super(gameStageModel);
        this.wall = wall;
    }

    public boolean[] getWall() {
        return this.wall;
    }

    public boolean getWall(Direction direction) {
        if(Direction.UP == direction){
            return this.wall[0];
        } else if(Direction.DOWN == direction){
            return this.wall[1];
        } else if(Direction.LEFT == direction){
            return this.wall[2];
        } else if(Direction.RIGHT == direction){
            return this.wall[3];
        }
        return false;
    }

    public boolean isEmpty() {
        return Objects.equals(this.wall, new boolean[4]);
    }

    public void setWalls(boolean[] wall) {
        this.wall = wall;
    }

    public void setWall(Direction direction, boolean bool) {
        if(Direction.UP == direction){
            this.wall[0] = bool;
        } else if(Direction.DOWN == direction){
            this.wall[1] = bool;
        } else if(Direction.LEFT == direction){
            this.wall[2] = bool;
        } else if(Direction.RIGHT == direction){
            this.wall[3] = bool;
        }
    }


}
