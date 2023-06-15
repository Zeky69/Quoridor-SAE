package control;

import boardifier.control.Controller;
import boardifier.control.ControllerAction;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.QuorView;

import javax.swing.plaf.synth.SynthDesktopIconUI;


public class QuorControllerAction extends ControllerAction implements EventHandler<ActionEvent> {

        private QuorView quorView;
        private QuorController quorControl;
        public QuorControllerAction(Model model, View view, Controller control) {
            super(model, view, control);
            quorView = (QuorView)view;
            quorControl = (QuorController)control;

            // set handlers dedicated to menu items
            setMenuHandlers();
            setButtonHandlers();


        }

        private void setMenuHandlers(){
            quorView.getMenuItemStart().setOnAction(e -> {
                try {
                    if (model.getPlayers().size() == 0){
                        quorControl.initPlayers(quorView.mode);
                    }
                    control.startGame();
                }
                catch(GameException err) {
                    System.err.println(err.getMessage());
                    System.exit(1);
                }
            });
            // set event handler on the MenuIntro item
            quorView.getMenuIntro().setOnAction(e -> {
                control.stopGame();
                quorView.resetView();
            });
            // set event handler on the MenuQuit item
            quorView.getMenuQuit().setOnAction(e -> {
                System.exit(0);
            });

        }

    private void setButtonHandlers(){
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



        @Override
        public void handle(ActionEvent event) {
            if (!model.isCaptureActionEvent()) return;
        }

}
