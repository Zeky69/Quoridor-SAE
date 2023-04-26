package control;

import boardifier.control.Controller;
import boardifier.model.Model;
import boardifier.view.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class QuorController extends Controller {

    BufferedReader consoleIn;

    public QuorController(Model model , View view) {
            super(model, view);
        }

        public void stageLoop() {
            consoleIn = new BufferedReader(new InputStreamReader(System.in));
            update();
        }




}
