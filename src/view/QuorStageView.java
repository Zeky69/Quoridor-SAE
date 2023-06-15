package view;

import boardifier.model.GameStageModel;
import boardifier.view.GameStageView;
import boardifier.view.TextLook;
import model.QuorStageModel;
import model.Wall;


public class QuorStageView extends GameStageView {
    GameStageModel gameStageModel1;


    /**
     * Constructor
     * @param name name of the stage
     * @param gameStageModel model of the stage
     */
    public QuorStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
        width = 1050;
        height = 700;
    }

    /**
     * Create the looks of the stage
     */
    @Override
    public void createLooks() {
        addLook(new QuorGridLook(630, ((QuorStageModel) gameStageModel).getBoard()));
        addLook(new WallPotLook(25, ((QuorStageModel) gameStageModel).getWallPot1()));
        addLook(new WallPotLook(25, ((QuorStageModel) gameStageModel).getWallPot2()));

        for (int i = 0; i < 2; i++) {
            addLook(new PawnLook(((QuorStageModel) gameStageModel).getPawns()[i], 21));
        }

        Wall[][] wallsShow = ((QuorStageModel) gameStageModel).getWallsShow();
        for (int i = 0; i < 10; i++) {
            addLook(new WallLook(0, wallsShow[0][i]));
            addLook(new WallLook(1, wallsShow[1][i]));
        }
        addLook(new QuorTextLook(40, "0x87cefa", ((QuorStageModel) gameStageModel).getPlayerName()));

        addLook(new TextLook(30, "0x000000", ((QuorStageModel) gameStageModel).getTextTurn()));


    }
}

