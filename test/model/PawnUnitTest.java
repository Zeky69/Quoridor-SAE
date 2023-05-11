package model;


import boardifier.model.GameStageModel;
import boardifier.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PawnUnitTest {

    private Pawn pawn;
    private GameStageModel gameStageModel;

    @BeforeEach
    public void setUp() {
        Model model = new Model(); //création du model
        GameStageModel gameStageModel = new QuorStageModel("jeu",model);
        QuorStageModel stageModel;
        gameStageModel = (QuorStageModel) gameStageModel;

        pawn = new Pawn(0, 0, 1, 8, gameStageModel);
    }

    @Test
    public void testPawnCoordinates() {
        assertEquals(0, pawn.getPawnX());
        assertEquals(0, pawn.getPawnY());

        int[] newCoords = {1, 2};
        pawn.setPawnXY(newCoords);
        assertArrayEquals(newCoords, pawn.getPawnXY());
    }



    @Test
    public void testPawnWallCount() {
        int initialWallCount = pawn.getWallCount();
        assertEquals(10, initialWallCount);

        pawn.decrementWallCount();
        assertEquals(initialWallCount - 1, pawn.getWallCount());

        int newWallCount = 5;
        pawn.setWallCount(newWallCount);
        assertEquals(newWallCount, pawn.getWallCount());
    }

    @Test
    public void testPawnWinY() {
        assertEquals(8, pawn.getWinY());

        int newWinY = 9;
        pawn.setWinY(newWinY);
        assertEquals(newWinY, pawn.getWinY());
    }

    @Test
    public void testPawnCopy() {
        Pawn copy = pawn.copy();
        assertNotSame(pawn, copy);
        assertEquals(pawn.getPawnX(), copy.getPawnX());
        assertEquals(pawn.getPawnY(), copy.getPawnY());
        assertEquals(pawn.getPlayer(), copy.getPlayer());
        assertEquals(pawn.getWallCount(), copy.getWallCount());
        assertEquals(pawn.getWinY(), copy.getWinY());
    }

}