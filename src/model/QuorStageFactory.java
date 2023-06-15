package model;

import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.model.StageElementsFactory;
import boardifier.model.TextElement;

public class QuorStageFactory extends StageElementsFactory {
    private final QuorStageModel stageModel;

    /**
     * Constructor
     *
     * @param model the game stage model
     */
    public QuorStageFactory(GameStageModel model) {
        super(model);
        this.stageModel = (QuorStageModel) model;

    }

    /**
     * @return the stageModel
     */
    public QuorStageModel getStageModel() {
        return this.stageModel;
    }

    /**
     * Create a grid of walls
     * The variable wallsAround is an array of boolean that tells if there are walls to the (north, south, west, est) of the wall
     *
     * @return a grid of walls
     */
    public Wall[][] initWalls() {
        Wall[][] walls = new Wall[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boolean[] wallsAround = {false, false, false, false};
                walls[i][j] = new Wall(wallsAround, stageModel);
            }
        }
        return walls;
    }

    /**
     * Set up the stage with all the elements needed for the game
     */
    @Override
    public void setup() {

        stageModel.setWalls(initWalls());
        stageModel.setBoard(new QuorBoard(30, 30, stageModel));
        Pawn[] pawns = new Pawn[2];
        Pawn pawn1 = new Pawn(4, 0, 1, 8, stageModel);
        Pawn pawn2 = new Pawn(4, 8, 2, 0, stageModel);
        WallPot wallPot1 = new WallPot(720, 50, stageModel);
        WallPot wallPot2 = new WallPot(720, 150, stageModel);

        Wall[] walls1 = new Wall[10];
        Wall[] walls2 = new Wall[10];

        for (int i = 0; i < 10; i++) {
            walls1[i] = new Wall(stageModel);
            walls2[i] = new Wall(stageModel);
        }

        Wall[][] wallsShow = new Wall[][]{walls1, walls2};
        stageModel.setWallsShow(wallsShow);


        stageModel.setWallPot1(wallPot1);
        stageModel.setWallPot2(wallPot2);
        pawns[0] = pawn1;
        pawns[1] = pawn2;
        stageModel.setPawns(pawns);
        stageModel.getBoard().putElement(pawn1, pawn1.getPawnY(), pawn1.getPawnX());
        stageModel.getBoard().putElement(pawn2, pawn2.getPawnY(), pawn2.getPawnX());
        stageModel.addGrid(wallPot1);
        stageModel.addGrid(wallPot2);
        for (int i = 0; i < 10; i++) {
            wallPot1.putElement(walls1[i], 0, i);
            wallPot2.putElement(walls2[i], 0, i);
            stageModel.addElement(walls1[i]);
            stageModel.addElement(walls2[i]);

        }


        TextElement text = new TextElement(stageModel.getCurrentPlayerName(), stageModel);
        text.setLocation(750, 320);
        text.setLocationType(GameElement.LOCATION_TOPLEFT);
        stageModel.setPlayerName(text);

        TextElement textTurn = new TextElement("\nit's your turn.", stageModel);
        textTurn.setLocation(750, 320);
        textTurn.setLocationType(GameElement.LOCATION_TOPLEFT);
        stageModel.setTextTurn(textTurn);


    }
}
