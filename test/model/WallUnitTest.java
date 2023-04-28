package model;

import boardifier.model.GameStageModel;
import boardifier.model.Model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WallUnitTest {
    @Test
    public void testConstructWall()
    {
        Model model = new Model(); //création du model
        GameStageModel gameStageModel = new QuorStageModel("jeu",model);
        QuorStageModel stageModel;
        stageModel = (QuorStageModel) gameStageModel;


        boolean[] wallTest = new boolean[4];
        wallTest[0] = true;
        wallTest[1] = false;
        wallTest[2] = true;
        wallTest[3] = false;
        Wall wall1 = new Wall(wallTest,stageModel);

        Assertions.assertArrayEquals(wallTest,wall1.getWall());
        Assertions.assertTrue(wall1.getWall(Wall.Direction.UP));
        Assertions.assertFalse(wall1.getWall(Wall.Direction.DOWN));
        Assertions.assertTrue(wall1.getWall(Wall.Direction.LEFT));
        Assertions.assertFalse(wall1.getWall(Wall.Direction.RIGHT));
        Assertions.assertFalse(wall1.isEmpty());

    }

    @Test
    public void testSetWall() {
        Model model = new Model(); //création du model
        GameStageModel gameStageModel = new QuorStageModel("jeu", model);
        QuorStageModel stageModel;
        stageModel = (QuorStageModel) gameStageModel;

        boolean[] wallTest = new boolean[]{true, false, true, false};

        Wall wall1 = new Wall(wallTest, stageModel);

        wall1.setWall(Wall.Direction.UP, false);

        Assertions.assertFalse(wall1.getWall(Wall.Direction.UP));


    }

    @Test
    public void testToString(){
        //TODO TITOU, Faire test de ToString mais voir si c'est possible et quel sortie ça peut etre.
    }

}
