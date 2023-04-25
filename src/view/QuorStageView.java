package view;

import boardifier.model.GameStageModel;
import boardifier.view.GameStageView;
import boardifier.view.GridLook;
import model.QuorStageModel;

public class QuorStageView extends GameStageView{

    public QuorStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
    }

    @Override
    public void createLooks() {
        gameStageModel = (QuorStageModel)gameStageModel;
        addLook(new GridLook(4, 2, ((QuorStageModel) gameStageModel).getBoard(), -1, true));






    }
}
