package view;

import boardifier.model.GameStageModel;
import boardifier.view.GameStageView;
import model.QuorStageModel;
import model.Wall;


public class QuorStageView extends GameStageView{
    GameStageModel gameStageModel1;
    public QuorStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
        width = 650;
        height = 650;
    }

    /**
     * Create the shape of the stage
     */



    /**
     * Create the looks of the stage
     */
    @Override
    public void createLooks() {

        addLook(new QuorGridLook(650, ((QuorStageModel) gameStageModel).getBoard()));
//        addLook(new WallPotLook(4, 2 , ((QuorStageModel) gameStageModel).getWallPot1()));
//        addLook(new WallPotLook(4, 2 , ((QuorStageModel) gameStageModel).getWallPot2()));


        for(int i=0;i<2;i++) {
            addLook(new PawnLook(((QuorStageModel) gameStageModel).getPawns()[i],24));

        }
        Wall[][] wallsShow = ((QuorStageModel) gameStageModel).getWallsShow();
        for(int i=0;i<10;i++) {
            addLook(new WallLook(0,wallsShow[0][i]));
            addLook(new WallLook(1,wallsShow[1][i]));
        }
    }
}

