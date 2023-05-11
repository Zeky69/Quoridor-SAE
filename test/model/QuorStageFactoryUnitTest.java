package model;

import boardifier.model.GameStageModel;
import boardifier.model.Model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.PublicKey;

public class QuorStageFactoryUnitTest {

    @Test
    public void testConstructQuorStageFactory() {

        Model model = new Model(); //création du model
        QuorStageModel quorStageModel = new QuorStageModel("jeu", model);

    }

    @Test
    public void initWallsTest() {
        Model model = new Model(); //création du model
        GameStageModel gameStageModel = new QuorStageModel("jeu", model);

        QuorStageFactory quorStageFactory = new QuorStageFactory(gameStageModel);
        Wall[][] walls = quorStageFactory.initWalls();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Assertions.assertFalse(walls[i][j].getWall(Wall.Direction.UP));
                Assertions.assertFalse(walls[i][j].getWall(Wall.Direction.DOWN));
                Assertions.assertFalse(walls[i][j].getWall(Wall.Direction.LEFT));
                Assertions.assertFalse(walls[i][j].getWall(Wall.Direction.RIGHT));
                boolean[] wallsTest = walls[i][j].getWall();
                boolean[] wallFalse = new boolean[]{false, false, false, false};
                Assertions.assertArrayEquals(wallsTest, wallFalse);
            }
        }
    }

}
