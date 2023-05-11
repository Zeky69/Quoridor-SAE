package control;

import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;
import boardifier.model.action.MoveAction;
import graph.Graph;
import model.Pawn;
import model.QuorBoard;
import model.QuorStageModel;
import model.Wall;

import java.util.Arrays;
import java.util.List;

import static model.Wall.intToDirection;

public class QuorDecider2 extends Decider {

    public QuorDecider2(Model model, Controller control) {
        super(model, control);
    }


    public int[] placeWall(){
        Pawn[] pawns = ((QuorStageModel)(model.getGameStage())).getPawns();
        Pawn self = pawns[model.getIdPlayer()];
        Pawn adversaire = pawns[1-model.getIdPlayer()];
        Wall[][] walls = ((QuorStageModel)(model.getGameStage())).getWalls();
        List<int[]> possibleWalls = ((QuorController)control).possibleWall(walls , pawns);

        if (possibleWalls.size() == 0){
            return makeMoove();
        } else if (possibleWalls.size() == 1){
            return possibleWalls.get(0);
        } else {
            //place un mur devant l'adversaire
            int posAY = adversaire.getPawnY();
            int posAX = adversaire.getPawnX();
            int direction;

            if ( adversaire.getWinY() == 0){
                direction = 0;
            } else {
                direction = 1;
            }
            Graph graph = new Graph(walls);
            int shortestPath = graph.shortestPath(new int[]{posAX , posAY} , adversaire.getWinY());

            if (goodWall(direction, new int[]{1,0}, walls, adversaire, self, graph, shortestPath)){
                return new int[]{posAX , posAY, posAX+1 , posAY , direction};
            }

            if (goodWall(direction, new int[]{-1,0}, walls, adversaire, self, graph, shortestPath)){
                return new int[]{posAX , posAY, posAX-1 , posAY , direction};
            }

            direction = 2;
            if (goodWall(direction, new int[]{0,-1}, walls, adversaire, self, graph, shortestPath)){
                return new int[]{posAX , posAY, posAX , posAY-1 , direction};
            }


            direction = 3;
            if (goodWall(direction, new int[]{0,-1}, walls, adversaire, self, graph, shortestPath)){
                return new int[]{posAX , posAY, posAX , posAY-1 , direction};
            }

            return makeMoove();
        }
    }

    public boolean goodWall(int direction, int[] decalage, Wall[][] walls, Pawn adversaire, Pawn self, Graph graph, int shortestPath){
        int posAY = adversaire.getPawnY();
        int posAX = adversaire.getPawnX();
        int posSY = self.getPawnY();
        int posSX = self.getPawnX();
        boolean wallPresent = posAX+decalage[0]<=8 && posAX+decalage[0]>=0 && posAY+decalage[1]>=0 && !(walls[posAY][posAX].getWall(intToDirection(direction)) || walls[posAY+decalage[1]][posAX+decalage[0]].getWall(intToDirection(direction)));
        if (wallPresent && !((QuorController)control).isCross(new int[]{posAX,posAY}, new int[]{posAX+decalage[0],posAY+decalage[1]}, intToDirection(direction), walls)){
            graph.removeArete(new int[]{posAX , posAY} , new int[]{posAX+decalage[0] , posAY+decalage[1]}, intToDirection(direction));
            int newShortestPath = graph.shortestPath(new int[]{posAX , posAY} , adversaire.getWinY());
            return (newShortestPath > shortestPath) && graph.isPathPossibleY(new int[]{posAX, posAY}, adversaire.getWinY()) && graph.isPathPossibleY(new int[]{posSX, posSY}, self.getWinY());
        }
        return false;
    }

    public int[] makeMoove(){
        QuorStageModel stage = (QuorStageModel) model.getGameStage();
        Pawn[] pawns = stage.getPawns();
        Wall[][] walls = stage.getWalls();
        Pawn pawnCurrent = pawns[model.getIdPlayer()];
        List<int[]> possibleMoves = ((QuorController)control).possibleDest(pawnCurrent.getPawnX() , pawnCurrent.getPawnY() ,walls, pawns);
        Graph graph = new Graph(walls);
        int bestMove = Integer.MAX_VALUE;
        int[] bestMovePossible = new int[2];
        int score;
        for (int[] move : possibleMoves){
            score = graph.shortestPath(new int[]{move[0],move[1]}, pawnCurrent.getWinY());
            if (score < bestMove){
                bestMove = score;
                bestMovePossible = move;
            }
        }
        return bestMovePossible;
    }


    public int[] choiceAI(){
        QuorStageModel stage = (QuorStageModel) model.getGameStage();
        Wall[][] walls = stage.getWalls();
        Pawn[] pawns = stage.getPawns();
        Graph graph = new Graph(walls);
        int pathPlayer = graph.shortestPath(new int[]{pawns[model.getIdPlayer()].getPawnX(), pawns[model.getIdPlayer()].getPawnY()}, pawns[model.getIdPlayer()].getWinY());
        int pathIA = graph.shortestPath(new int[]{pawns[1-model.getIdPlayer()].getPawnX(), pawns[1-model.getIdPlayer()].getPawnY()}, pawns[1-model.getIdPlayer()].getWinY());
        if (pathPlayer > pathIA) {
            if (pawns[model.getIdPlayer()].getWallCount() > 0) {
                return placeWall();
            }
        }
        return makeMoove();
    }

    @Override
    public ActionList decide(){
        int[] moveIA  = choiceAI();
        QuorStageModel stage = (QuorStageModel) model.getGameStage();
        Pawn pawn = stage.getPawns()[model.getIdPlayer()];
        ActionList actions = new ActionList(true);
        if(moveIA.length == 2){
            pawn.setPawnXY(moveIA);
            GameAction move = new MoveAction(model, pawn, "QuorBoard", moveIA[1] , moveIA[0]);
            actions.addSingleAction(move);
        }else{
            Wall[][] walls = stage.getWalls();
            ((QuorController)control).setWallcoord(new int[]{moveIA[0],moveIA[1]} , intToDirection(moveIA[4]),walls);
            ((QuorController)control).setWallcoord(new int[]{moveIA[2],moveIA[3]} , intToDirection(moveIA[4]),walls);
            pawn.setWallCount(pawn.getWallCount()-1);
            System.out.println(stage.getNbWalls()[model.getIdPlayer()]);

        }
        return actions;



    }
}
