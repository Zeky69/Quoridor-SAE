package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.model.GameElement;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;
import boardifier.model.action.MoveAction;
import boardifier.view.GridLook;
import boardifier.view.View;
import graph.Graph;
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
                    ok = switch (choice) {
                        case "P" -> analyseSecondStepP(moove);
                        case "W" -> analyseSecondStepW(moove);
                        default -> ok;
                    };

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
            // TODO verifier si le joueur peut encore poser un mur
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
        QuorStageModel gameStage = (QuorStageModel) model.getGameStage();
        Wall[][] walls = gameStage.getWalls();
        orientation orien = coordsToOrientation(coord , coord2);
        if (orien == orientation.HORIZONTAL){
            int wall1 = Math.min(coord[0], coord2[0]);
            if (dir == Wall.Direction.UP){
                    if (walls[coord[1]][wall1].getWall(Wall.Direction.RIGHT) && walls[coord[1]-1][wall1].getWall(Wall.Direction.RIGHT)){
                        return true;
                    }

            }else if (dir == Wall.Direction.DOWN){
                    if (walls[coord[1]][wall1].getWall(Wall.Direction.RIGHT) && walls[coord[1]+1][wall1].getWall(Wall.Direction.RIGHT)){
                        return true;
                    }
            }
        }else if (orien == orientation.VERTICAL){
            int wall1 = Math.min(coord[1], coord2[1]);
            if (dir == Wall.Direction.LEFT){
                    if (walls[wall1][coord[0]].getWall(Wall.Direction.DOWN) && walls[wall1][coord[0]-1].getWall(Wall.Direction.DOWN)){
                        return true;
                    }
            }else if (dir == Wall.Direction.RIGHT){
                    if (walls[wall1][coord[0]].getWall(Wall.Direction.DOWN) && walls[wall1][coord[0]+1].getWall(Wall.Direction.DOWN)){
                        return true;
                    }
            }
        }

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

        else if ( walls[coord[1]][coord[0]].getWall(dir)|| walls[coord2[1]][coord2[0]].getWall(dir)) {
            System.out.println("le mur n'est pas libre");
            return false;

        }

        if (isCross(coord,coord2,dir)){
            System.out.println("le mur croise un autre mur");
            return false;
        }

        Graph graph = new Graph(walls);
        graph.removeArete(coord,coord2,dir);
        Pawn[] pawns = gameStage.getPawns();
        if (!graph.isPathPossibleY(pawns[0].getPawnXY(),pawns[0].getWinY()) || !graph.isPathPossibleY(pawns[1].getPawnXY(),pawns[1].getWinY())){
            System.out.println("le mur bloque le chemin d'un joueur");
            return false;
        }


        wallPlay(coord,coord2,dir);
        gameStage.getGrid("QuorBoard").resetReachableCells(true);
        return true;
    }

}
