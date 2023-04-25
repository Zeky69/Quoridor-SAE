package model;

import boardifier.model.GameStageModel;
import boardifier.model.StageElementsFactory;

public class QuorStageFactory extends StageElementsFactory {

    private QuorStageModel stageModel;

    public QuorStageFactory(GameStageModel model) {
        super(model);
        this.stageModel = (QuorStageModel) model;

    }

    @Override
        public void setup() {
        stageModel.setBoard(new QuorBoard(0, 0, stageModel));




        }
}
