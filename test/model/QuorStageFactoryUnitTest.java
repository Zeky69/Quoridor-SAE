package model;

import boardifier.model.GameStageModel;
import boardifier.model.Model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.PublicKey;

public class QuorStageFactoryUnitTest {

    @Test
    public void testConstructQuorStageFactory()
    {

        Model model = new Model(); //création du model
        GameStageModel gameStageModel = new QuorStageModel("jeu",model);
        QuorStageModel stageModel;
        stageModel = (QuorStageModel) gameStageModel;
        QuorStageFactory quorStageFactory = new QuorStageFactory(gameStageModel);
        //Assertions.assertEquals(quorStageFactory.getStageModel(),gameStageModel);//TODO : absence de getteur
    }

    @Test
    public void initWallsTest()
    {
        Model model = new Model(); //création du model
        GameStageModel gameStageModel = new QuorStageModel("jeu",model);
        QuorStageModel stageModel;
        stageModel = (QuorStageModel) gameStageModel;
        QuorStageFactory quorStageFactory = new QuorStageFactory(gameStageModel);
        Wall[][] walls = quorStageFactory.initWalls();
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                Assertions.assertEquals(false,walls[i][j].getWall(Wall.Direction.UP));
                Assertions.assertEquals(false,walls[i][j].getWall(Wall.Direction.DOWN));
                Assertions.assertEquals(false,walls[i][j].getWall(Wall.Direction.LEFT));
                Assertions.assertEquals(false,walls[i][j].getWall(Wall.Direction.RIGHT));
                boolean [] wallsTest = new boolean[]{false,false,false,false};
                //Assertions.assertEquals(walls[i][j].getWall(),wallsTest); //TODO: bug avec wall.getWall()
            }
        }
    }

}
