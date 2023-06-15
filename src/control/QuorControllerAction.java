package control;

import boardifier.control.Controller;
import boardifier.control.ControllerAction;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import view.QuorView;


public class QuorControllerAction extends ControllerAction implements EventHandler<ActionEvent> {

    private final QuorView quorView;
    private final QuorController quorControl;

    /**
     * Constructor
     * @param model model
     * @param view view
     * @param control control
     */
    public QuorControllerAction(Model model, View view, Controller control) {
        super(model, view, control);
        quorView = (QuorView) view;
        quorControl = (QuorController) control;

        // set handlers dedicated to menu items
        setMenuHandlers();
        setButtonHandlers();


    }

    /**
     * set button handlers
     */
    private void setMenuHandlers() {
        quorView.getMenuItemStart().setOnAction(e -> {
            try {
                if (model.getPlayers().size() == 0) {
                    quorControl.initPlayers(quorView.mode);
                }
                control.startGame();
            } catch (GameException err) {
                System.err.println(err.getMessage());
                System.exit(1);
            }
        });
        quorView.getMenuItemRule().setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            // Set the title and content text of the alert
            alert.setTitle("Rules");
            alert.setHeaderText(null); // Remove the header text if not needed
            alert.setContentText("Objective of the Game\n" +
                    "The objective of \"Quoridor\" is to be the first player to reach the line opposite to their starting line. Players must navigate through a maze of barriers while planning their moves to find the shortest path to victory.\n" +
                    "\n" +
                    "Game Mechanics\n" +
                    "Players take turns and have two possible actions: moving their pawn one space or placing a barrier to slow down the opponent. The opponent is required to leave at least one open passage. Pawns must navigate around the barriers, creating a maze that needs to be traversed quickly, as the first player to reach the opposite line will be declared the winner.");
            alert.showAndWait();
        });
        // set event handler on the MenuIntro item
        quorView.getMenuIntro().setOnAction(e -> {
            control.stopGame();
            quorView.resetView();
            this.setButtonHandlers();
            model.getPlayers().clear();
        });
        // set event handler on the MenuQuit item
        quorView.getMenuQuit().setOnAction(e -> {
            System.exit(0);
        });

    }

    /**
     * set button handlers
     */
    private void setButtonHandlers() {
        quorView.getButtonPlay().setOnAction(e -> {
            try {
                quorView.mode = quorView.getComboBox().getSelectionModel().getSelectedIndex();
                quorControl.initPlayers(quorView.mode);
                control.startGame();
            } catch (GameException err) {
                System.err.println(err.getMessage());
                System.exit(1);
            }
        });
    }


    /**
     * handle event
     * @param event event
     */
    @Override
    public void handle(ActionEvent event) {
        if (!model.isCaptureActionEvent()) return;
    }

}
