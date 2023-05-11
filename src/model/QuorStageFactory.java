package model;

import boardifier.model.GameStageModel;
import boardifier.model.StageElementsFactory;

public class QuorStageFactory extends StageElementsFactory {

    private QuorStageModel stageModel;



    public QuorStageFactory(GameStageModel model) {
        super(model);
        this.stageModel = (QuorStageModel) model;

    }

    public QuorStageModel getStageModel() {
        return this.stageModel;
    }

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
        stageModel.setBoard(new QuorBoard(0, 1, stageModel));
        Pawn[] pawns = new Pawn[2];
        Pawn pawn1 = new Pawn(4, 0, 1, 8, stageModel);
        Pawn pawn2 = new Pawn(4, 8, 2, 0, stageModel);
        WallPot wallPot1 = new WallPot(50, 0, stageModel);
        WallPot wallPot2 = new WallPot(43, 0, stageModel);

        Wall[] walls1 = new Wall[10];
        Wall[] walls2 = new Wall[10];

        for (int i = 0; i < 10; i++) {
            walls1[i] = new Wall(stageModel);
            walls2[i] = new Wall(stageModel);
        }

        Wall[][] wallsShow = new Wall[][]{walls1,walls2};
        stageModel.setWallsShow(wallsShow);


        stageModel.setWallPot1(wallPot1);
        stageModel.setWallPot2(wallPot2);
        pawns[0] = pawn1;
        pawns[1] = pawn2;
        stageModel.setPawns(pawns);
        stageModel.setWalls(initWalls());
        stageModel.getBoard().putElement(pawn1, pawn1.getPawnY(), pawn1.getPawnX());
        stageModel.getBoard().putElement(pawn2, pawn2.getPawnY(), pawn2.getPawnX());
        for (int i = 0; i < 10; i++) {
            wallPot1.putElement(walls1[i], i,0);
            wallPot2.putElement(walls2[i], i, 0);
            stageModel.addElement(walls1[i]);
            stageModel.addElement(walls2[i]);

        }
        stageModel.addGrid(wallPot1);
        stageModel.addGrid(wallPot2);







    }
}
