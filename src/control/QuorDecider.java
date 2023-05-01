package control;

import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import graph.Graph;
import model.Pawn;
import model.QuorStageModel;
import model.Wall;

public class QuorDecider extends Decider {

    public QuorDecider(Model model, Controller control) {
        super(model, control);
    }


    public int evaluteState(Pawn pawn1 , Pawn pawn2 , Wall[][] walls){

        if(pawn1.getWinY() == pawn1.getPawnY()){
            return 1000;
        }
        Graph graph = new Graph(walls);
        int distancePawn1 =  graph.shortestPath(pawn1.getPawnXY() , pawn1.getWinY());
        int distancePawn2 = graph.shortestPath(pawn2.getPawnXY() , pawn2.getWinY());
        int wallPawn1 = pawn1.getWallCount();
        int wallPawn2 = pawn2.getWallCount();
        int wallDifference = wallPawn1 - wallPawn2;
        int distanceDifference = distancePawn1 - distancePawn2;
        return 1000 - (distanceDifference * 8 + wallDifference*10);
    }

    @Override
    public ActionList decide(){
        return null;
    }
}
