/*
package control;

import boardifier.control.Controller;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.ActionEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.mockito.Mockito.mock;

public class QuorControllerActionUnitTest {

    private Model model;
    private View view;
    private Controller control;
    private QuorControllerAction controllerAction;

    @BeforeEach
    public void setUp() {
        model = mock(Model.class);
        view = mock(View.class);
        control = mock(Controller.class);
        controllerAction = new QuorControllerAction(model, view, control);
    }

    @Test
    public void testHandle_WhenCaptureActionEventIsFalse() {
        ActionEvent event = mock(ActionEvent.class);

        // Test if the method does nothing when captureActionEvent is false in the model
        Mockito.when(model.isCaptureActionEvent()).thenReturn(false);
        controllerAction.handle(event);

        // Assert that no methods are called on the mocked objects
        Mockito.verifyZeroInteractions(view);
        Mockito.verifyZeroInteractions(control);
    }

    @Test
    public void testHandle_WhenCaptureActionEventIsTrue() {
        ActionEvent event = mock(ActionEvent.class);

        Mockito.when(model.isCaptureActionEvent()).thenReturn(true);
        controllerAction.handle(event);

        Mockito.verifyZeroInteractions(view);
        Mockito.verifyZeroInteractions(control);
    }

    @Test
    public void testHandle_MenuItemStartAction() throws GameException {
        ActionEvent event = mock(ActionEvent.class);

        MenuItem menuItemStart = mock(MenuItem.class);
        Mockito.when(view.getMenuItemStart()).thenReturn(menuItemStart);
        Mockito.when(menuItemStart.getId()).thenReturn("menuItemStart");
        Mockito.when(model.getPlayers().size()).thenReturn(0);

        controllerAction.handle(event);

        Mockito.verify(control).startGame();
    }

    @Test
    public void testHandle_MenuIntroAction() {
        ActionEvent event = mock(ActionEvent.class);

        MenuItem menuIntro = mock(MenuItem.class);
        Mockito.when(view.getMenuIntro()).thenReturn(menuIntro);
        Mockito.when(menuIntro.getId()).thenReturn("menuIntro");

        controllerAction.handle(event);

        Mockito.verify(control).stopGame();
        Mockito.verify(view).resetView();
    }

    @Test
    public void testHandle_MenuQuitAction() {
        ActionEvent event = mock(ActionEvent.class);

        MenuItem menuQuit = mock(MenuItem.class);
        Mockito.when(view.getMenuQuit()).thenReturn(menuQuit);
        Mockito.when(menuQuit.getId()).thenReturn("menuQuit");

        controllerAction.handle(event);

    }

    @Test
    public void testHandle_ButtonModeHvHAction() throws GameException {
        ActionEvent event = mock(ActionEvent.class);

        Button buttonModeHvH = mock(Button.class);
        Mockito.when(view.getButtonModeHvH()).thenReturn(buttonModeHvH);
        Mockito.when(buttonModeHvH.getId()).thenReturn("buttonModeHvH");

        controllerAction.handle(event);

        Mockito.verify(control).initPlayers(0);
        Mockito.verify(control).startGame();
    }

    @Test
    public void testHandle_ButtonModeHvCAction() throws GameException {
        ActionEvent event = mock(ActionEvent.class);

        Button buttonModeHvC = mock(Button.class);
        Mockito.when(view.getButtonModeHvC()).thenReturn(buttonModeHvC);
        Mockito.when(buttonModeHvC.getId()).thenReturn("buttonModeHvC");

        controllerAction.handle(event);

        Mockito.verify(control).initPlayers(1);
        Mockito.verify(control).startGame();
    }

    @Test
    public void testHandle_ButtonModeCvCAction() throws GameException {
        ActionEvent event = mock(ActionEvent.class);

        Button buttonModeCvC = mock(Button.class);
        Mockito.when(view.getButtonModeCvC()).thenReturn(buttonModeCvC);
        Mockito.when(buttonModeCvC.getId()).thenReturn("buttonModeCvC");

        controllerAction.handle(event);

        Mockito.verify(control).initPlayers(2);
        Mockito.verify(control).startGame();
    }


}
*/
