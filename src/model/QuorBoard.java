package model;

import boardifier.model.GameStageModel;
import boardifier.model.GridElement;

public class QuorBoard extends GridElement {

        public QuorBoard(int x , int y, GameStageModel model) {
            super("QuorBoard",x,y, 9 ,9, model);
            resetReachableCells(false);
        }



}
