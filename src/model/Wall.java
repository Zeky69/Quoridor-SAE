package model;

import boardifier.model.GameElement;
import boardifier.model.GameStageModel;

import java.util.Arrays;
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

     public static int[] directionToInt(Direction direction){
        if(direction == Direction.UP){
            return new int[]{0,-1};
        } else if(direction == Direction.DOWN){
            return new int[]{0,1};
        } else if(direction == Direction.LEFT){
            return new int[]{-1,0};
        } else if(direction == Direction.RIGHT){
            return new int[]{1,0};
        }
        return new int[]{0,0};
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
        return Arrays.equals(this.wall, new boolean[4]);
    }

    public void setWalls(boolean[] wall) {
        this.wall = wall;
    }

    public static Direction intToDirection(int i){
        if(i == 0){
            return Direction.UP;
        } else if(i == 1){
            return Direction.DOWN;
        } else if(i == 2){
            return Direction.LEFT;
        } else if(i == 3){
            return Direction.RIGHT;
        }
        return null;
    }


    public static boolean isBorder(int[] coord, Wall.Direction direction){
        if (direction == Wall.Direction.UP && coord[1]==0){
            return true;
        }else if (direction == Wall.Direction.DOWN && coord[1]==8){
            return true;
        }else if (direction == Wall.Direction.LEFT && coord[0]==0){
            return true;
        }else if (direction == Wall.Direction.RIGHT && coord[0]==8){
            return true;
        }
        return false;
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

    public String toString(){
        return Arrays.toString(wall);

    }


}
