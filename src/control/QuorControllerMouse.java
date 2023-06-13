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
import graph.Graph;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.Pawn;
import model.QuorBoard;
import model.QuorStageModel;
import model.Wall;

import java.util.Arrays;
import java.util.List;

import static model.Wall.isBorder;

/**
 * A basic mouse controller that just grabs the mouse clicks and prints out some informations.
 * It gets the elements of the scene that are at the clicked position and prints them.
 */
public class QuorControllerMouse extends ControllerMouse implements EventHandler<MouseEvent> {
    int tailleGrid = 630;
    int tailleCase = (tailleGrid/9);
    int tailleMur = 5;
    public QuorControllerMouse(Model model, View view, Controller control) {
        super(model, view, control);
        view.getRootPane().setOnMouseMoved(event -> { //adding the mouse moved event not present in the original ControllerMouse class
            if (detectWall(event)!=null) {
                //previsualisation du mur
                Coord2D clic = new Coord2D(event.getSceneX(),event.getSceneY());


            } else {
                System.out.println("Not wall");
            }
        });
    }

    /**
     * Detect if the click is on a wall position
     * @param event
     * @return
     */
    public Wall.Direction detectWall(MouseEvent event) {
        Coord2D clic = new Coord2D(event.getSceneX(),event.getSceneY());
//        GridLook lookBoard = (GridLook) control.getElementLook(board);
//        int[] dest = lookBoard.getCellFromSceneLocation(clic);


        boolean[] direction = new boolean[4]; //0 = bas, 1 = haut, 2 = droite, 3 = gauche (normalement)
        direction[0] = (clic.getX() ) % tailleCase > (tailleCase-tailleMur);
        direction[1] = (clic.getX() ) %tailleCase<tailleMur;
        direction[2] = (clic.getY()-30)%tailleCase>(tailleCase-tailleMur);
        direction[3] = (clic.getY()-30)%tailleCase<tailleMur;
        if ((direction[0] || direction[1]) != (direction[2] || direction[3])){
            System.out.println(Arrays.toString(direction));
            if(direction[0])
                return Wall.Direction.RIGHT;
            if(direction[1])
                return Wall.Direction.LEFT;
            if(direction[2])
                return Wall.Direction.DOWN;
            if(direction[3])
                return Wall.Direction.UP;

        }
        return null;
    }

    public void handle(MouseEvent event) {

        // if mouse event capture is disabled in the model, just return
        if (!model.isCaptureMouseEvent()) return;
        QuorStageModel stageModel = (QuorStageModel) model.getGameStage();

        Coord2D clic = new Coord2D(event.getSceneX(),event.getSceneY());
        System.out.println("Mouse clicked at " + event.getSceneX() + " " + event.getSceneY());
        List<GameElement> list = control.elementsAt(clic);




        QuorBoard board = stageModel.getBoard();

        if (stageModel.getState() == QuorStageModel.STATE_SELECTPAWN) {
            for (GameElement element : list) {
                if (element.getType() == ElementTypes.getType("pawn")) {

                    Pawn pawn = (Pawn)element;
                    // check if color of the pawn corresponds to the current player id
                    if (pawn.getPlayer()-1 == model.getIdPlayer()) {
                        ((QuorController)control).analyseSecondStepP();
                        element.toggleSelected();
                        stageModel.setState(QuorStageModel.STATE_SELECTDEST);
                        return; // do not allow another element to be selected
                    }
                }
            }
            if (detectWall(event)!=null) {
                int[] dest = new int[]{(int) (clic.getY()-30)/tailleCase,(int)clic.getX()/tailleCase};
                QuorController control = (QuorController) this.control;
                Wall.Direction direction = detectWall(event);
                if (direction != null && dest != null){
                    System.out.println(Arrays.toString(dest)+" "+direction);
                    if( control.analyseSecondStepW(dest[1],dest[0],direction)){
                        control.nextPlayer();
                    }
                }
                return;
            }
        }else if (stageModel.getState() == QuorStageModel.STATE_SELECTDEST) {
            // first check if the click is on the current selected pawn. In this case, unselect it
            for (GameElement element : list) {
                if (element.isSelected()) {
                    element.toggleSelected();
                     board.setLookChanged(true);
                    stageModel.setState(QuorStageModel.STATE_SELECTPAWN);
                    return;
                }
            }




            // thirdly, get the clicked cell in the 3x3 board
            GridLook lookBoard = (GridLook) control.getElementLook(board);
            int[] dest = lookBoard.getCellFromSceneLocation(clic);


            System.out.println(Arrays.toString(dest));


            if (dest!= null && stageModel.getBoard().canReachCell(dest[0],dest[1])){
                GameElement pawn = stageModel.getPawns()[model.getIdPlayer()];
                System.out.println("can reach");
                ActionList actions = new ActionList(true);
                //GameAction move = new MoveAction(model, pawn, "QuorBoard", dest[0], dest[1]);
                stageModel.unselectAll();
                Coord2D center = lookBoard.getRootPaneLocationForCellCenter(dest[0], dest[1]);

                ((Pawn)pawn).setPawnY(dest[0]);
                ((Pawn)pawn).setPawnX(dest[1]);
                GameAction move = new MoveAction(model, pawn, "QuorBoard", dest[0], dest[1] , AnimationTypes.MOVE_LINEARPROP, center.getX(), center.getY(),10 );

                actions.addSingleAction(move);
                stageModel.setState(QuorStageModel.STATE_SELECTPAWN);
                ActionPlayer play = new ActionPlayer(model, control, actions);
                play.start();
                stageModel.getBoard().setInvalidCells();







            }
            else{
                System.out.println("can't reach");
            }

        }




    }



}
