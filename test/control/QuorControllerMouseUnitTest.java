/*
package control;
import boardifier.control.Controller;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.scene.input.MouseEvent;

import model.Pawn;
import model.QuorBoard;
import model.QuorStageModel;
import model.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuorControllerMouseTest {
    private Model model;
    private View view;
    private Controller control;
    private QuorControllerMouse quorControllerMouse;

    @BeforeEach
    public void setup() {
        model = mock(Model.class);
        view = mock(View.class);
        control = mock(Controller.class);
        quorControllerMouse = new QuorControllerMouse(model, view, control);
    }

    @Test
    public void testDetectWall() {
        MouseEvent event = mock(MouseEvent.class);
        when(event.getSceneX()).thenReturn(35.0);
        when(event.getSceneY()).thenReturn(35.0);

        Wall.Direction result = quorControllerMouse.detectWall(event);

        verify(event, times(2)).getSceneX();
        verify(event, times(2)).getSceneY();

        assertSame(Wall.Direction.LEFT, result);
    }

    @Test
    public void testHandle_SelectPawnState() {
        MouseEvent event = mock(MouseEvent.class);
        when(event.getSceneX()).thenReturn(100.0);
        when(event.getSceneY()).thenReturn(100.0);

        QuorStageModel stageModel = mock(QuorStageModel.class);
        when(model.getGameStage()).thenReturn(stageModel);
        when(stageModel.getState()).thenReturn(QuorStageModel.STATE_SELECTPAWN);

        Pawn pawn = mock(Pawn.class);
        when(pawn.getPlayer()).thenReturn(1);
        when(pawn.getWallCount()).thenReturn(0);

        QuorBoard board = mock(QuorBoard.class);
        when(stageModel.getBoard()).thenReturn(board);

        when(control.elementsAt(any())).thenReturn(Arrays.asList(pawn));
        when(board.canReachCell(anyInt(), anyInt())).thenReturn(true);

        quorControllerMouse.handle(event);

        verify(model).getGameStage();
        verify(stageModel).getState();
        verify(pawn).getPlayer();
        verify(pawn, never()).toggleSelected();
        verify(board, never()).setLookChanged();
        verify(control, never()).getElementLook(any());
        verify(stageModel, never()).unselectAll();
        verify(control, never()).nextPlayer();
        verify(board).canReachCell(anyInt(), anyInt());
        // Ajoutez les assertions supplémentaires pour vérifier le comportement attendu
    }

    // Ajoutez d'autres méthodes de test pour couvrir les différents cas de la méthode handle()

}
*/
