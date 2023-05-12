package model;

import boardifier.model.GameStageModel;
import boardifier.model.Model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class QuorStageModelUnitTest {

    @Test
    public void testConstructQuorStageModel()
    {

        QuorStageModel quorStageModel = new QuorStageModel("QuorStageModel", new Model());

        Assertions.assertEquals("QuorStageModel", quorStageModel.getName());
        Assertions.assertNull( quorStageModel.getBoard());
    }
}
