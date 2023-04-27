package view;

import boardifier.model.GameStageModel;
import boardifier.model.GridElement;
import boardifier.view.ConsoleColor;
import boardifier.view.GameStageView;
import boardifier.view.GridLook;
import model.QuorStageModel;
import model.Wall;

public class QuorStageView extends GameStageView{

    GameStageModel gameStageModel1;

    public QuorStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
    }



    @Override
    protected void createShape(){



    }

    @Override
    public void createLooks() {

        addLook(new QuorGridLook(4, 2, gameStageModel, -1, true));


        for(int i=0;i<2;i++) {
            addLook(new PawnLook(((QuorStageModel) gameStageModel).getPawns()[i]));
        }






    }
}

