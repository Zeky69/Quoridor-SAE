package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.control.ControllerMouse;
import boardifier.model.Coord2D;
import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;
import boardifier.model.action.MoveAction;
import boardifier.model.animation.AnimationTypes;
import boardifier.view.GridLook;
import boardifier.view.View;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.Pawn;
import model.QuorBoard;
import model.QuorStageModel;

import java.util.Arrays;
import java.util.List;

/**
 * A basic mouse controller that just grabs the mouse clicks and prints out some informations.
 * It gets the elements of the scene that are at the clicked position and prints them.
 */
public class QuorControllerMouse extends ControllerMouse implements EventHandler<MouseEvent> {

    public QuorControllerMouse(Model model, View view, Controller control) {
        super(model, view, control);
    }

    public void handle(MouseEvent event) {
        // if mouse event capture is disabled in the model, just return
        if (!model.isCaptureMouseEvent()) return;
        Coord2D clic = new Coord2D(event.getSceneX(),event.getSceneY());
//        List<GameElement> list = control.elementsAt(clic);
        System.out.println("click in "+event.getSceneX()+","+event.getSceneY());
        int[] coordCase = new int[]{(int)event.getSceneX()/80, (int)event.getSceneY()/80};
        System.out.println("case:" + coordCase[0] + "," + coordCase[1]);

        QuorStageModel stageModel = (QuorStageModel) model.getGameStage();
        GameElement pawn = stageModel.getPawns()[model.getIdPlayer()];

        ((QuorController)control).analyseSecondStepP();
        if (stageModel.getBoard().canReachCell(coordCase[1],coordCase[0])){
            System.out.println("can reach");
            ActionList actions = new ActionList(true);
            GameAction move = new MoveAction(model, pawn, "QuorBoard", coordCase[1], coordCase[0]);
            ((Pawn)pawn).setPawnY(coordCase[0]);
            ((Pawn)pawn).setPawnX(coordCase[1]);
            actions.addSingleAction(move);
            ActionPlayer play = new ActionPlayer(model, control, actions);
            play.start();

        }
        else{
            System.out.println("can't reach");
        }













    }
}
