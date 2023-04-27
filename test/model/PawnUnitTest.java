package model;



import boardifier.model.GameStageModel;
import boardifier.model.Model;
import boardifier.model.StageElementsFactory;
import boardifier.view.View;
import model.Pawn;
import model.QuorBoard;
import model.QuorStageFactory;
import model.QuorStageModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import boardifier.control.Controller;

public class PawnUnitTest {

    @Test
    public void testPawnConstruct()
    {
        Model model = new Model(); //création du model
        GameStageModel gameStageModel = new QuorStageModel("jeu",model);
        QuorStageModel stageModel;
        stageModel = (QuorStageModel) gameStageModel;


        int x =1;
        int y=2;
        int player = 3;
        Pawn pawn = new Pawn(x,y,player,stageModel);


        Assertions.assertEquals(1,pawn.getPawnX());
        Assertions.assertEquals(2,pawn.getPawnY());
        Assertions.assertEquals(3,pawn.getPlayer());


    }
}
