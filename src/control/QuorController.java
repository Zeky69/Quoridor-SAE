package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class QuorController extends Controller {

    BufferedReader consoleIn;
    boolean firstPlayer;

    public QuorController(Model model , View view) {
        super(model, view);
        firstPlayer = true;
    }

    public void stageLoop() {
        consoleIn = new BufferedReader(new InputStreamReader(System.in));
        update();
        while (!model.isEndStage()) {
            nextPlayer();
            update();
        }
        endGame();
    }

    public void nextPlayer() {
        if (!firstPlayer) {
            model.setNextPlayer();
        }
        else {
            firstPlayer = false;
        }
        Player p = model.getCurrentPlayer();
        if (p.getType() == Player.COMPUTER) {
            System.out.println("COMPUTER PLAYS");
            QuorDecider decider = new QuorDecider(model,this);
            ActionPlayer play = new ActionPlayer(model, this, decider, null);
            play.start();
        }
        else {
            String choice="";
            boolean ok = false;
            while (!ok) {
                System.out.println(p.getName()+ " : ");
                System.out.print("Enter P to move a pawn, or W to place a wall >");
                try {
                    choice = consoleIn.readLine();
                    if (choice.length() == 1) {
                        ok = analyseFirstStep(choice);
                    }
                    if (!ok) {
                        System.out.println("incorrect instruction. retry !");
                    }
                }
                catch(IOException e) {}
            }

            ok = false;
            while (!ok) {
                try {
                    String moove = consoleIn.readLine();
                    switch (choice){
                        case "P" :
                            ok = analyseSecondStepP(moove);
                        case "W" :
                            ok = analyseSecondStepW(moove);
                    }

                    if (!ok) {
                        System.out.println("incorrect instruction. retry !");
                    }
                }
                catch(IOException e) {}


            }
        }
    }

    public boolean analyseFirstStep(String line) {
        if (line.equals("P")) {
            return true;
        }
        else if (line.equals("W")) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean analyseSecondStepP(String line){
        boolean result = true;

        // TODO conditions pour pouvoir bouger un pion

        return result;
    }

    public boolean analyseSecondStepW(String line){
        boolean result = true;

        // TODO conditions pour pouvoir placer un mur

        return result;
    }

}
