package model;

import boardifier.model.GameStageModel;
import boardifier.model.GridElement;
import boardifier.model.Model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuorBoardUnitTest {

    @Test
    public void testQuorBoardConstructor() {


        QuorBoard board = new QuorBoard(0, 0, new QuorStageModel("QuorStageModel", new Model()));
        Assertions.assertEquals("QuorBoard", board.getName());
        Assertions.assertEquals(0, board.getX());
        Assertions.assertEquals(0, board.getY());


        Assertions.assertEquals(9, board.getNbCols());
        Assertions.assertEquals(9, board.getNbRows());

    }

}
