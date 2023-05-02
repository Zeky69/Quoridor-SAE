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

public class QuorDecider extends Decider {

    public QuorDecider(Model model, Controller control) {
        super(model, control);
    }

    public Wall[][] copyWalls(Wall[][] walls){
        Wall[][] newWalls = new Wall[9][9];
        for(int i = 0 ; i < 9 ; i++){
            for(int j = 0 ; j < 9 ; j++){
                newWalls[i][j] = walls[i][j].copy();
            }
        }
        return newWalls;
    }

    public Pawn copyPawn(Pawn pawn){
        return pawn.copy();
    }

    public Pawn[] copyPawns(Pawn[] pawns){
        Pawn[] newPawns = new Pawn[2];
        newPawns[0] = copyPawn(pawns[0]);
        newPawns[1] = copyPawn(pawns[1]);
        return newPawns;
    }




    public int evaluteState(Pawn pawn1 , Pawn pawn2 , Wall[][] walls){

        if(pawn1.getWinY() == pawn1.getPawnY()){
            return 100000;
        }
        Graph graph = new Graph(walls);
        int distancePawn1 =  graph.shortestPath(pawn1.getPawnXY() , pawn1.getWinY());
        int distancePawn2 = graph.shortestPath(pawn2.getPawnXY() , pawn2.getWinY());
        int wallPawn1 = pawn1.getWallCount();
        int wallPawn2 = pawn2.getWallCount();
        double distanceDifference = distancePawn2*0.5 - distancePawn1;
        return ((int)distanceDifference*18 +wallPawn1*20 );
    }

    public int[] scoreAI(){

        Pawn[] pawns = copyPawns(((QuorStageModel)(model.getGameStage())).getPawns());
        Wall[][] walls = copyWalls(((QuorStageModel)(model.getGameStage())).getWalls());
        Wall[][] wallsCopy = copyWalls(walls);
        Pawn pawnCurrent = pawns[model.getIdPlayer()];
        Pawn pawnOther = pawns[(model.getIdPlayer()+1)%2];
        List<int[]> possibleMoves = ((QuorController)control).possibleDest(pawnCurrent.getPawnX() , pawnCurrent.getPawnY() ,walls, pawns);

        int[] bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        int score ;
        for(int[] move : possibleMoves){
            pawnCurrent.setPawnXY(move);
            score = evaluteState(pawnCurrent ,pawnOther , walls);
            if(score > bestScore){
                bestScore = score;
                bestMove = move;
            }
        }
        if(pawnCurrent.getWallCount()>0){
            List<int[]> possibleWalls = ((QuorController)control).possibleWall(walls , pawns);
            for(int[] moveWall : possibleWalls){
                wallsCopy[moveWall[1]][moveWall[0]].setWall(Wall.intToDirection(moveWall[4]),true);
                wallsCopy[moveWall[3]][moveWall[2]].setWall(Wall.intToDirection(moveWall[4]),true);
                score = evaluteState(pawnCurrent,pawnOther,wallsCopy);
                wallsCopy[moveWall[1]][moveWall[0]].setWall(Wall.intToDirection(moveWall[4]),false);
                wallsCopy[moveWall[3]][moveWall[2]].setWall(Wall.intToDirection(moveWall[4]),false);
                if(score>bestScore){
                    bestScore = score;
                    bestMove =  moveWall;
                }
            }
        }

        System.out.println(Arrays.toString(bestMove) + " " + bestScore);




        return bestMove;
    }




    @Override
    public ActionList decide(){
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
            ((QuorController)control).setWallcoord(new int[]{moveIA[0],moveIA[1]} , Wall.intToDirection(moveIA[4]),walls);
            ((QuorController)control).setWallcoord(new int[]{moveIA[2],moveIA[3]} , Wall.intToDirection(moveIA[4]),walls);
            pawn.setWallCount(pawn.getWallCount()-1);

        }

        return actions;



    }
}
