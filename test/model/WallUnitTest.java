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


        boolean[] wall = new boolean[4];
        wall[0] = true;
        wall[1] = false;
        wall[2] = true;
        wall[3] = false;
        Wall wall1 = new Wall(wall,stageModel);

        Assertions.assertEquals(wall,wall1.getWall());
        Assertions.assertEquals(true,wall1.getWall(Wall.Direction.UP));
        Assertions.assertEquals(false,wall1.getWall(Wall.Direction.DOWN));
        Assertions.assertEquals(true,wall1.getWall(Wall.Direction.LEFT));
        Assertions.assertEquals(false,wall1.getWall(Wall.Direction.RIGHT));
        Assertions.assertEquals(false,wall1.isEmpty());
        wall1.setWall(Wall.Direction.UP,false);
        Assertions.assertEquals(false,wall1.getWall(Wall.Direction.UP));
        wall1.setWalls(wall);
        Assertions.assertEquals(wall,wall1.getWall());




    }
}
