package model;

import boardifier.model.GameStageModel;
import boardifier.model.Model;
import boardifier.model.StageElementsFactory;

public class QuorStageModel extends GameStageModel {

    QuorBoard board;
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

    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new QuorStageFactory(this);
    }

}
