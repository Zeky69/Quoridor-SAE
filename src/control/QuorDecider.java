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
import java.util.Random;

import static model.Wall.intToDirection;

public class QuorDecider extends Decider {
    int idPlayer;

    /**
     * Constructor
     * @param model
     * @param control
     * @param idPlayer
     */
    public QuorDecider(Model model, Controller control, int idPlayer) {
        super(model, control);
        this.idPlayer = idPlayer;
    }

    /**
     * Make the AI decide for the player (each player has its own AI)
     * @return
     */
    @Override
    public ActionList decide(){
        if (this.idPlayer == 0){
            return decidePlayer1();
        }else{
            return decidePlayer2();
        }

    }

    /**
     * Copy the walls
     * @param walls wall list
     * @return
     */
    public Wall[][] copyWalls(Wall[][] walls){
        Wall[][] newWalls = new Wall[9][9];
        for(int i = 0 ; i < 9 ; i++){
            for(int j = 0 ; j < 9 ; j++){
                newWalls[i][j] = walls[i][j].copy();
            }
        }
        return newWalls;
    }

    /**
     * Copy a pawn
     * @param pawn
     * @return
     */
    public Pawn copyPawn(Pawn pawn){
        return pawn.copy();
    }

    /**
     * Copy the pawns
     * @param pawns pawn list
     * @return
     */
    public Pawn[] copyPawns(Pawn[] pawns){
        Pawn[] newPawns = new Pawn[2];
        newPawns[0] = copyPawn(pawns[0]);
        newPawns[1] = copyPawn(pawns[1]);
        return newPawns;
    }

    /**
     *  //TO DO
     * @param pawn1
     * @param pawn2
     * @param walls
     * @return
     */
    public int evaluateState(Pawn pawn1 , Pawn pawn2 , Wall[][] walls){
        if(pawn1.getWinY() == pawn1.getPawnY()){
            return 100000;
        }
        Graph graph = new Graph(walls);
        int distancePawn1 =  graph.shortestPath(pawn1.getPawnXY() , pawn1.getWinY());
        int distancePawn2 = graph.shortestPath(pawn2.getPawnXY() , pawn2.getWinY());
        int diffDistance = distancePawn2*3 - distancePawn1 ;
        int wallPawn1 = pawn1.getWallCount();
        int wallPawn2 = pawn2.getWallCount();
        int diffWall = wallPawn1 - wallPawn2;
        int pawnMouvPossible = ((QuorController)control).possibleDest(pawn2.getPawnX() , pawn2.getPawnY() ,walls , copyPawns(((QuorStageModel)(model.getGameStage())).getPawns())).size();
        return  diffDistance + diffWall - pawnMouvPossible;
    }

    /**
     * Caclulate the best move for the player
     * @return the best move
     */
    public int[] scoreAI(){
        Pawn[] pawns = copyPawns(((QuorStageModel)(model.getGameStage())).getPawns());
        Wall[][] wallsCopy = copyWalls(((QuorStageModel)(model.getGameStage())).getWalls());
        Pawn pawnCurrent = pawns[model.getIdPlayer()];
        Pawn pawnOther = pawns[1-model.getIdPlayer()];
        List<int[]> possibleMoves = ((QuorController)control).possibleDest(pawnCurrent.getPawnX() , pawnCurrent.getPawnY() ,wallsCopy, pawns);

        int[] bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        int score ;
        for(int[] move : possibleMoves){
            pawnCurrent.setPawnXY(move);
            score = evaluateState(pawnCurrent,pawnOther,wallsCopy);
            if(score > bestScore){
                bestScore = score;
                bestMove = move;
            }
        }
        if(pawnCurrent.getWallCount()>0){
            List<int[]> possibleWalls = ((QuorController)control).possibleWall(wallsCopy , pawns);
            for(int[] moveWall : possibleWalls){
                wallsCopy[moveWall[1]][moveWall[0]].setWall(Wall.intToDirection(moveWall[4]),true);
                wallsCopy[moveWall[3]][moveWall[2]].setWall(Wall.intToDirection(moveWall[4]),true);
                pawnCurrent.decrementWallCount();
                score = evaluateState(pawnCurrent,pawnOther,wallsCopy);
                pawnCurrent.incrementWallCount();
                wallsCopy[moveWall[1]][moveWall[0]].setWall(Wall.intToDirection(moveWall[4]),false);
                wallsCopy[moveWall[3]][moveWall[2]].setWall(Wall.intToDirection(moveWall[4]),false);
                if(score>bestScore){
                    bestScore = score;
                    bestMove =  moveWall;
                }
            }
        }
        return bestMove;
    }

    /**
     * Find the best wall to place
     * @return coordinates of the wall
     */
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

    /**
     * Test if a wall can be placed in the given positions and direction
     * @param direction
     * @param decalage
     * @param walls
     * @param adversaire
     * @param self
     * @param graph
     * @param shortestPath
     * @return
     */
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

    /**
     * Move the pawn to the nearest win position (using Dijsktra algorithm)
     * @return
     */
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

    /**
     * Choose to place a wall or move the pawn
     * @return coordinates of the wall or the pawn
     */
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

    /**
     * Main method of the second AI :
     * make a move or place a wall depending on the given coordinates
     * @return
     */
    private ActionList decidePlayer2() {
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
            Wall[][] wallsShow = stage.getWallsShow();

            ((QuorController)control).setWallcoord(new int[]{moveIA[0],moveIA[1]} , intToDirection(moveIA[4]),walls);
            ((QuorController)control).setWallcoord(new int[]{moveIA[2],moveIA[3]} , intToDirection(moveIA[4]),walls);
            pawn.setWallCount(pawn.getWallCount()-1);
            stage.removeElement(wallsShow[model.getIdPlayer()][9-pawn.getWallCount()]);


        }
        return actions;
    }

    /**
     * Main method of the first AI :
     * make a move or place a wall depending on the given coordinates
     * @return
     */
    private ActionList decidePlayer1() {
        int[] moveIA  = scoreAI();
        QuorStageModel stage = (QuorStageModel) model.getGameStage();
        Pawn pawn = stage.getPawns()[model.getIdPlayer()];
        ActionList actions = new ActionList(true);
        if(moveIA.length == 2){
            pawn.setPawnXY(moveIA);
            GameAction move = new MoveAction(model, pawn, "QuorBoard", moveIA[1] , moveIA[0]);
            actions.addSingleAction(move);
        }else{
            Wall[][] walls = stage.getWalls();
            Wall[][] wallsShow = stage.getWallsShow();
            ((QuorController)control).setWallcoord(new int[]{moveIA[0],moveIA[1]} , Wall.intToDirection(moveIA[4]),walls);
            ((QuorController)control).setWallcoord(new int[]{moveIA[2],moveIA[3]} , Wall.intToDirection(moveIA[4]),walls);
            pawn.setWallCount(pawn.getWallCount()-1);
            stage.removeElement(wallsShow[model.getIdPlayer()][9-pawn.getWallCount()]);


        }

        return actions;
    }
}
