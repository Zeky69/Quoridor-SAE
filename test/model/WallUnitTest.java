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

        Assertions.assertEquals(wallTest,wall1.getWall());
        Assertions.assertTrue(wall1.getWall(Wall.Direction.UP));
        Assertions.assertFalse(wall1.getWall(Wall.Direction.DOWN));
        Assertions.assertTrue(wall1.getWall(Wall.Direction.LEFT));
        Assertions.assertFalse(wall1.getWall(Wall.Direction.RIGHT));
        Assertions.assertFalse(wall1.isEmpty());

        wall1.setWall(Wall.Direction.UP,false);

        Assertions.assertFalse(wall1.getWall(Wall.Direction.UP));

        wall1.setWalls(wallTest);

        Assertions.assertEquals(wallTest,wall1.getWall());

    }
}
