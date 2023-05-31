package control;

import boardifier.control.Controller;
import boardifier.control.ControllerAction;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.QuorView;


public class QuorControllerAction extends ControllerAction implements EventHandler<ActionEvent> {

        private QuorView quorView;
        public QuorControllerAction(Model model, View view, Controller control) {
            super(model, view, control);
            quorView = (QuorView)view;

            // set handlers dedicated to menu items
            setMenuHandlers();


        }

        private void setMenuHandlers(){
            quorView.getMenuStart().setOnAction(e -> {
                try {
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

        @Override
        public void handle(ActionEvent event) {
            if (!model.isCaptureActionEvent()) return;
        }

}
