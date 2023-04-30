package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;
import boardifier.model.action.MoveAction;
import boardifier.view.GridLook;
import boardifier.view.View;
import model.Pawn;
import model.QuorStageModel;
import model.Wall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class QuorController extends Controller {

    BufferedReader consoleIn;
    boolean firstPlayer;

    enum orientation {
        HORIZONTAL,
        VERTICAL
    }

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
                QuorStageModel gameStage = (QuorStageModel) model.getGameStage();
                System.out.println(p.getName()+ " : " + gameStage.getNbWalls()[model.getIdPlayer()] + " walls left");
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
            QuorStageModel gameStage = (QuorStageModel) model.getGameStage();
            if (gameStage.getNbWalls()[model.getIdPlayer()] > 0) {
                return true;
            }
            else {
                return false;
            }
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

    public Wall.Direction charToDirection(char direction){
        switch (direction){
            case 'H' :
                return Wall.Direction.UP;
            case 'B' :
                return Wall.Direction.DOWN;
            case 'D' :
                return Wall.Direction.RIGHT;
            case 'G' :
                return Wall.Direction.LEFT;
            default :
                return null;
        }
    }

    public int[] charToCoord(char x, char y){
        int[] coord = new int[2];
        coord[0] = x - 'A';
        coord[1] = y - '0'-1;
        return coord;
    }

    public orientation coordsToOrientation(int[] coord1, int[] coord2){
        if (coord1[0] == coord2[0]){
            if (coord1[1] == coord2[1] + 1 || coord1[1] == coord2[1] - 1){
                 return orientation.VERTICAL;
            }
        }
        else if (coord1[1] == coord2[1]){
            if (coord1[0] == coord2[0] + 1 || coord1[0] == coord2[0] - 1){
                return orientation.HORIZONTAL;
            }
        }
        return null;
    }


    public Wall[][] setWallcoord(int[] coord , Wall.Direction direction , Wall[][] walls){
        walls[coord[1]][coord[0]].setWall(direction ,true);
        if (direction == Wall.Direction.UP && coord[1]!=0){
            walls[coord[1]-1][coord[0]].setWall(Wall.Direction.DOWN ,true);
        }else if (direction == Wall.Direction.DOWN && coord[1]!=8){
            walls[coord[1]+1][coord[0]].setWall(Wall.Direction.UP ,true);
        }else if (direction == Wall.Direction.LEFT && coord[0]!=0){
            walls[coord[1]][coord[0]-1].setWall(Wall.Direction.RIGHT ,true);
        }else if (direction == Wall.Direction.RIGHT && coord[0]!=8){
            walls[coord[1]][coord[0]+1].setWall(Wall.Direction.LEFT ,true);
        }

        return walls;
    }

    public boolean isBorder(int[] coord, Wall.Direction direction){
        if (direction == Wall.Direction.UP && coord[1]==0){
            return true;
        }else if (direction == Wall.Direction.DOWN && coord[1]==8){
            return true;
        }else if (direction == Wall.Direction.LEFT && coord[0]==0){
            return true;
        }else if (direction == Wall.Direction.RIGHT && coord[0]==8){
            return true;
        }
        return false;
    }

    public boolean isCross(int[] coord , int[] coord2 , Wall.Direction dir){
//        QuorStageModel gameStage = (QuorStageModel) model.getGameStage();
//        Wall[][] walls = gameStage.getWalls();
//        orientation orien = coordsToOrientation(coord , coord2);
//        int[] sens = new int[2] ;
//        sens[0] = coord2[0] - coord[0];
//        sens[1] = coord2[1] - coord[1];
//        if (orien == orientation.HORIZONTAL){
//            if (dir == Wall.Direction.UP && coord[1]!=0) {
//                return walls[coord[1]+sens[1]][coord[0]].getWall() ;
//            }
//        }
        return false;

    }



    public void wallPlay(int[] coord,int[] coord2,Wall.Direction direction){
        QuorStageModel gameStage = (QuorStageModel) model.getGameStage();
        Wall[][] walls = gameStage.getWalls();
        walls = setWallcoord(coord , direction , walls);
        walls = setWallcoord(coord2 , direction , walls);
        gameStage.setWalls(walls);
        model.setGameStage(gameStage);
    }


    public boolean analyseSecondStepW(String line){
        QuorStageModel gameStage = (QuorStageModel) model.getGameStage();
        if (line.length() != 5) {return false;}
        line = line.toUpperCase();
        int[] coord = charToCoord(line.charAt(0), line.charAt(1));
        char direction = line.charAt(2);
        int[] coord2 = charToCoord(line.charAt(3), line.charAt(4));
        orientation orien = coordsToOrientation(coord, coord2);
        Wall.Direction dir = charToDirection(direction);
        Wall[][] walls = gameStage.getWalls();

        if (coord[0] < 0 || coord[0] >= 9 || coord[1] < 0 || coord[1] >= 9 || coord2[0] < 0 || coord2[0] >= 9 || coord2[1] < 0 || coord2[1] >= 9 ||  dir==null  || orien == null) {
            System.out.println("les coordonnées ne correspondent pas à un mur ou à une direction");
            return false;}
        if (orien  == orientation.HORIZONTAL && (direction != 'H' && direction != 'B')) {
            System.out.println("les coordonnées ne correspondent pas à un mur horizontal");
            return false;}
        if (orien  == orientation.VERTICAL && (direction != 'G' && direction != 'D')) {
            System.out.println("les coordonnées ne correspondent pas à un mur vertical");
            return false;
        }

        if (isBorder(coord,dir) || isBorder(coord2,dir)){
            System.out.println("le mur est sur le bord");
            return false;
        }

        else if ( walls[coord[0]][coord[1]].getWall(dir)|| walls[coord2[0]][coord2[1]].getWall(dir)) {
            System.out.println("le mur n'est pas libre");
            return false;
        }
        //TODO verifier si le chemin du pion n'est pas coupé


        wallPlay(coord,coord2,dir);

        System.out.println(Arrays.toString(coord) + " " + Arrays.toString(coord2) + " " );
        gameStage.getBoard().update();

        System.out.println("le mur a été posé");
        gameStage.getNbWalls()[model.getIdPlayer()]--;

        gameStage.getGrid("QuorBoard").resetReachableCells(true);















        return true;
    }

}
