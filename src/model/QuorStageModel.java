package model;

import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.model.Model;
import boardifier.model.StageElementsFactory;

public class QuorStageModel extends GameStageModel {
    QuorBoard board;
    Pawn[] pawns ;
    int[] nbWalls = {10,10};
    Wall[][] walls = new Wall[9][9];
    Wall[][] wallsShow;
    WallPot wallPot1;
    WallPot wallPot2 ;

    /**
     * Constructor
     * @param name
     * @param model
     */
    public QuorStageModel(String name, Model model) {
        super(name , model);
    }

    /**
     * Get the board
     * @return
     */
    public QuorBoard getBoard() {
        return this.board;
    }

    /**
     * Get the first wall pot
     * @return
     */
    public WallPot getWallPot1() {
        return this.wallPot1;
    }

    /**
     * Get the walls to show
     * @return
     */
    public Wall[][] getWallsShow() {
        return this.wallsShow;
    }

    /**
     * Set the walls to show
     * @param wallsShow
     */
    public void setWallsShow(Wall[][] wallsShow) {
        this.wallsShow = wallsShow;
    }

    /**
     * Get the second wall pot
     * @return
     */
    public WallPot getWallPot2() {
        return this.wallPot2;
    }

    /**
     * Set the board
     * @param board
     */
    public void setBoard(QuorBoard board) {
        this.board = board;
        addGrid(board);
    }

    /**
     * Set the list of pawns
     * @param pawns
     */
    public void setPawns(Pawn[] pawns) {
        this.pawns = pawns;
        for(int i=0;i<pawns.length;i++) {
            addElement(pawns[i]);
        }
    }

    /**
     * Set the list of walls
     * @param walls
     */
    public void setWalls(Wall[][] walls) {
        this.walls = walls;
    }

    /**
     * Set the second wall pot
     * @param wallPot2
     */
    public void setWallPot2(WallPot wallPot2) {
        this.wallPot2 = wallPot2;
    }

    /**
     * Set the first wall pot
     * @param wallPot1
     */
    public  void setWallPot1(WallPot wallPot1) {
        this.wallPot1 = wallPot1;
    }

    /**
     * Get the list of walls
     * @return
     */
    public Wall[][] getWalls() {
        return walls;
    }

    /**
     * Get the list of pawns
     * @return
     */
    public Pawn[] getPawns() {
        return this.pawns;
    }

    /**
     * Get the number of walls
     * @return
     */
    public int[] getNbWalls() {
        return this.nbWalls;
    }

    /**
     * check if a player has reach his win position
     */
    public void hasWon() {
        for(int i=0;i<pawns.length;i++){
            if(pawns[i].getWinY() == pawns[i].getPawnY()){
                model.setIdWinner(i);
                model.stopStage();
            }
        }
    }

    /**
     * Get the default element factory
     * @return
     */
    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new QuorStageFactory(this);
    }
}

