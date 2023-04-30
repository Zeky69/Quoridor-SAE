package model;

import boardifier.model.GameStageModel;
import boardifier.model.StageElementsFactory;

public class QuorStageFactory extends StageElementsFactory {

    private QuorStageModel stageModel;



    public QuorStageFactory(GameStageModel model) {
        super(model);
        this.stageModel = (QuorStageModel) model;

    }
    //FIXME : faire un getteur pour le QuorStageModel stageModel ?
    // Voir QuorStageFactoryUnitTest.java

    Wall[][] initWalls() {
        Wall[][] walls = new Wall[9][9];
        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                boolean[] wallsAround = {false, false, false, false};

                walls[i][j] = new Wall(wallsAround , stageModel);
            }
        }
        return walls;
    }


    @Override
        public void setup() {
        stageModel.setBoard(new QuorBoard(0, 0, stageModel));
        Pawn[] pawns = new Pawn[2];
        Pawn pawn1 = new Pawn(4, 0, 1, 8,stageModel);
        Pawn pawn2 = new Pawn(4, 8, 2,0, stageModel);
        pawns[0] = pawn1;
        pawns[1] = pawn2;
        stageModel.setPawns(pawns);
        stageModel.setWalls(initWalls());
        stageModel.getBoard().putElement(pawn1, pawn1.getPawnY(), pawn1.getPawnX());
        stageModel.getBoard().putElement(pawn2, pawn2.getPawnY(), pawn2.getPawnX());

        }
}
