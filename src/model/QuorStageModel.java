package model;

import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.model.Model;
import boardifier.model.StageElementsFactory;

public class QuorStageModel extends GameStageModel {

    QuorBoard board;
    Pawn[] pawns ;


    public QuorStageModel(String name, Model model) {
        super(name , model);
    }

    public QuorBoard getBoard() {
        return this.board;
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

    public GameElement[] getPawns() {
        return this.pawns;
    }

    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new QuorStageFactory(this);
    }

}

