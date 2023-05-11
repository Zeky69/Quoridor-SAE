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


    public QuorStageModel(String name, Model model) {
        super(name , model);
    }

    public QuorBoard getBoard() {
        return this.board;
    }

    public WallPot getWallPot1() {
        return this.wallPot1;
    }

    public Wall[][] getWallsShow() {
        return this.wallsShow;
    }



    public void setWallsShow(Wall[][] wallsShow) {
        this.wallsShow = wallsShow;
    }


    public WallPot getWallPot2() {
        return this.wallPot2;
    }

    public void setBoard(QuorBoard board) {
        this.board = board;
        addGrid(board);
    }

    public void setPawns(Pawn[] pawns) {
        this.pawns = pawns;
        for(int i=0;i<pawns.length;i++) {
            addElement(pawns[i]);
        }
    }

    public void setWalls(Wall[][] walls) {
        this.walls = walls;
    }
    public void setWallPot2(WallPot wallPot2) {
        this.wallPot2 = wallPot2;
    }
    public  void setWallPot1(WallPot wallPot1) {
        this.wallPot1 = wallPot1;
    }
    public Wall[][] getWalls() {
        return walls;
    }

    public Pawn[] getPawns() {
        return this.pawns;
    }

    public int[] getNbWalls() {
        return this.nbWalls;
    }

    public void hasWon() {
        for(int i=0;i<pawns.length;i++){
            if(pawns[i].getWinY() == pawns[i].getPawnY()){
                model.setIdWinner(i);
                model.stopStage();
            }
        }

    }

    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new QuorStageFactory(this);
    }



}

