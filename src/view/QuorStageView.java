package view;

import boardifier.model.GameStageModel;
import boardifier.view.GameStageView;
import model.QuorStageModel;
import model.Wall;


public class QuorStageView extends GameStageView{

    GameStageModel gameStageModel1;

    public QuorStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
    }

    @Override
    protected void createShape() {
            return;
    }


    @Override
    public void createLooks() {

        addLook(new QuorGridLook(4, 2, gameStageModel, -1, true));
        addLook(new WallPotLook(4, 2 , ((QuorStageModel) gameStageModel).getWallPot1()));
        addLook(new WallPotLook(4, 2 , ((QuorStageModel) gameStageModel).getWallPot2()));


        for(int i=0;i<2;i++) {
            addLook(new PawnLook(((QuorStageModel) gameStageModel).getPawns()[i]));

        }
        Wall[][] wallsShow = ((QuorStageModel) gameStageModel).getWallsShow();
        for(int i=0;i<10;i++) {
            addLook(new WallLook(0,wallsShow[0][i]));
            addLook(new WallLook(1,wallsShow[1][i]));

        }






    }
}

