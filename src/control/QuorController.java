package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.model.GameElement;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;
import boardifier.model.action.MoveAction;
import boardifier.view.View;
import model.QuorStageModel;

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
            String moove;
            ok = false;
            while (!ok) {
                try {

                    switch (choice){
                        case "P" :
                            System.out.print("Enter the case you want to go >");
                            moove = consoleIn.readLine();
                            ok = analyseSecondStepP(moove); break;
                        case "W" :
                            System.out.print("Enter the cases you want to put a wall >");
                            moove = consoleIn.readLine();
                            ok = analyseSecondStepW(moove); break;
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
        QuorStageModel gameStage = (QuorStageModel) model.getGameStage();
        GameElement pawn = gameStage.getPawns()[model.getIdPlayer()];
        int col = (int)(line.charAt(0)-'A');
        int row = Integer.parseInt(line.substring(1,2))-1;

        gameStage.getBoard().setValidCells();
        //if (!gameStage.getBoard().canReachCell(row,col)) return false;

        ActionList actions = new ActionList(true);
        GameAction move = new MoveAction(model, pawn, "QuorBoard", row, col);
        // add the action to the action list.
        actions.addSingleAction(move);
        ActionPlayer play = new ActionPlayer(model, this, actions);
        play.start();
        return true;
    }

    public boolean analyseSecondStepW(String line){
        boolean result = true;

        // TODO conditions pour pouvoir placer un mur

        return result;
    }

}
