package model;

import boardifier.model.GameStageModel;
import boardifier.model.StageElementsFactory;

public class QuorStageFactory extends StageElementsFactory {

    private QuorStageModel stageModel;
    private  Pawn[] pawns = new Pawn[2];

    public QuorStageFactory(GameStageModel model) {
        super(model);
        this.stageModel = (QuorStageModel) model;

    }


    @Override
        public void setup() {
        stageModel.setBoard(new QuorBoard(0, 0, stageModel));
        Pawn pawn1 = new Pawn(4, 0, 1, stageModel);
        Pawn pawn2 = new Pawn(4, 8, 2, stageModel);
        this.pawns[0] = pawn1;
        this.pawns[1] = pawn2;
        stageModel.setPawns(this.pawns);
        stageModel.getBoard().putElement(pawn1, pawn1.getPawnY(), pawn1.getPawnX());
        stageModel.getBoard().putElement(pawn2, pawn2.getPawnY(), pawn2.getPawnX());




        }
}
