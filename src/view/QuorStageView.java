package view;

import boardifier.model.GameStageModel;
import boardifier.view.GameStageView;
import model.QuorStageModel;


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


        for(int i=0;i<2;i++) {
            addLook(new PawnLook(((QuorStageModel) gameStageModel).getPawns()[i]));
        }






    }
}

