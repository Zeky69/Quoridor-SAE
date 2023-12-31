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
import boardifier.view.ConsoleColor;
import boardifier.view.View;
import graph.Graph;
import model.Pawn;
import model.QuorStageModel;
import model.Wall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static model.Wall.isBorder;

public class QuorController extends Controller {

    BufferedReader consoleIn;
    boolean firstPlayer;

    enum orientation {
        HORIZONTAL,
        VERTICAL
    }

    /**
     * Constructor
     * @param model
     * @param view
     */
    public QuorController(Model model , View view) {
        super(model, view);
        firstPlayer = true;
    }

    /**
     * Loop of the game
     * Manage the turns of the players
     */
    public void stageLoop() {
        QuorStageModel gameStage = (QuorStageModel) model.getGameStage();
        consoleIn = new BufferedReader(new InputStreamReader(System.in));
        update();
        while (!model.isEndStage()) {
            nextPlayer();
            gameStage.getBoard().resetReachableCells(false);
            gameStage.hasWon();
            update();
        }
        endGame();
    }

    /**
     * Change the current player to the next one
     * If the current player is a computer, it plays
     * If the current player is a human, it asks him to play
     */
    @Override
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
            QuorDecider decider = new QuorDecider(model,this, model.getIdPlayer());
            ActionPlayer play = new ActionPlayer(model, this, decider, null);
            play.start();
        }

        else {
            String choice="";
            boolean ok = false;
            while (!ok) {
                QuorStageModel gameStage = (QuorStageModel) model.getGameStage();
                System.out.println(p.getName()+ " : "+ ConsoleColor.GREEN_BOLD + gameStage.getNbWalls()[model.getIdPlayer()] +ConsoleColor.RESET + " walls left");
                System.out.print("Enter P to move a pawn, or W to place a wall >");
                try {
                    choice = consoleIn.readLine().toUpperCase();
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

                    if (choice.equals("P")){
                        System.out.print("Enter the case you want to go >");
                        moove = consoleIn.readLine().toUpperCase();
                        ok = analyseSecondStepP(moove);
                    }
                    else if(choice.equals("W")) {
                            System.out.println("Enter the coordinates of the wall as follow : case1OrientationCase2");
                            System.out.println("Orientation : D for right, G for left, H for up, B for down");
                            System.out.println("Example : E1DE2");
                            System.out.print("Enter the cases you want to put a wall >");
                            moove = consoleIn.readLine();
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

    /**
     * Analyse the first input of the player
     * @param line
     * @return
     */
    public boolean analyseFirstStep(String line) {
        if (line.equals("P")) {
            return true;
        }
        else if (line.equals("W")) {
            QuorStageModel gameStage = (QuorStageModel) model.getGameStage();
            return gameStage.getNbWalls()[model.getIdPlayer()] > 0;
        }
        else {
            return false;
        }
    }

    /**
     * Find the coordinates of every places a wall could be placed
     * @param walls
     * @param pawns
     * @return List of coordinates
     */
    public List<int[]> possibleWall(Wall[][] walls , Pawn[] pawns){
        List<int[]> possibleWall = new ArrayList<>();
        Graph graph = new Graph(walls);
        for (int i = 0 ; i < 9 ; i++){
            for (int j = 0 ; j < 8 ; j++){
                if ( i!= 0 && !walls[i][j].getWall(Wall.Direction.UP)&& !walls[i][j+1].getWall(Wall.Direction.UP) && !isCross(new int[]{j,i}, new int[]{j+1,i} , Wall.Direction.UP ,walls) ){
                    graph.removeArete(new int[]{j,i}, new int[]{j+1,i} , Wall.Direction.UP);
                    if(graph.isPathPossibleY(pawns[0].getPawnXY(), pawns[0].getWinY()) && graph.isPathPossibleY(pawns[1].getPawnXY(), pawns[1].getWinY())){
                        possibleWall.add(new int[]{j,i,j+1,i,0});
                    }
                    graph.addArete(new int[]{j,i}, new int[]{j+1,i} , Wall.Direction.UP);
                }
            }
        }

        for (int i = 0 ; i < 8 ; i++){
            for (int j = 0 ; j < 9 ; j++){
                if ( j!= 0 && !walls[i][j].getWall(Wall.Direction.LEFT)&& !walls[i+1][j].getWall(Wall.Direction.LEFT)&& !isCross(new int[]{j,i}, new int[]{j,i+1} , Wall.Direction.LEFT ,walls) ){
                    graph.removeArete(new int[]{j,i}, new int[]{j,i+1} , Wall.Direction.LEFT);
                    if(graph.isPathPossibleY(pawns[0].getPawnXY(), pawns[0].getWinY()) && graph.isPathPossibleY(pawns[1].getPawnXY(), pawns[1].getWinY())){
                        possibleWall.add(new int[]{j,i,j,i+1,2});
                    }
                    graph.addArete(new int[]{j,i}, new int[]{j,i+1} , Wall.Direction.LEFT);
                }
            }
        }


        return possibleWall;

    }


    /**
     * Find the coordinates of every places a pawn could go
     * @param x
     * @param y
     * @param walls
     * @param pawns
     * @return
     */
    public List<int[]> possibleDest(int x , int y , Wall[][] walls , Pawn[] pawns){
        List<int[]> dest = new ArrayList<>();

        // verifie si le pion peut aller a gauche ou sinon si il peut sauter par dessus un pion
        if (x!=0 && !walls[y][x].getWall(Wall.Direction.LEFT) && !walls[y][x-1].getWall(Wall.Direction.RIGHT) && !(pawns[0].getPawnX() == x-1 && pawns[0].getPawnY() == y || pawns[1].getPawnX() == x-1 && pawns[1].getPawnY() == y)){
            dest.add(new int[]{x-1,y});
        }else if ((x!=0 && !walls[y][x].getWall(Wall.Direction.LEFT) && !walls[y][x-1].getWall(Wall.Direction.RIGHT)) && (pawns[0].getPawnX() == x-1 && pawns[0].getPawnY() == y || pawns[1].getPawnX() == x-1 && pawns[1].getPawnY() == y)){
            if (x - 1 > 0 && !walls[y][x - 1].getWall(Wall.Direction.LEFT)) {
                dest.add(new int[]{x - 2, y});
            }else {

            if (y != 0 && x != 0 && !walls[y][x - 1].getWall(Wall.Direction.UP)) {
                dest.add(new int[]{x - 1, y - 1});
            }
            if (y != 8 && x != 0 && !walls[y][x - 1].getWall(Wall.Direction.DOWN)) {
                dest.add(new int[]{x - 1, y + 1});
            }
            }
        }

        // Verifie si le pion peut aller a droite ou sinon il doit traverser le pion adverse
        if (x!=8 && !walls[y][x].getWall(Wall.Direction.RIGHT) && !walls[y][x+1].getWall(Wall.Direction.LEFT) && !(pawns[0].getPawnX() == x+1 && pawns[0].getPawnY() == y  || pawns[1].getPawnX() == x+1 && pawns[1].getPawnY() == y)){
            dest.add(new int[]{x+1,y});
        }else if ((x!=8 && !walls[y][x].getWall(Wall.Direction.RIGHT) && !walls[y][x+1].getWall(Wall.Direction.LEFT)) && (pawns[0].getPawnX() == x+1 && pawns[0].getPawnY() == y)  || (pawns[1].getPawnX() == x+1 && pawns[1].getPawnY() == y)){
            if (x+1 < 8  && !walls[y][x + 1].getWall(Wall.Direction.RIGHT)) {
                dest.add(new int[]{x + 2, y});
            }else {
            if (y > 0 && x < 8 && !walls[y][x - 1].getWall(Wall.Direction.UP)) {
                dest.add(new int[]{x + 1, y-1});
            }
            if (y < 8 && x < 8 && !walls[y][x - 1].getWall(Wall.Direction.DOWN)) {
                dest.add(new int[]{x + 1, y + 1});
            }
            }
        }

        if (y!=0 && !walls[y][x].getWall(Wall.Direction.UP) && !walls[y-1][x].getWall(Wall.Direction.DOWN) && !((pawns[0].getPawnX() == x && pawns[0].getPawnY() == y-1 || pawns[1].getPawnX() == x && pawns[1].getPawnY() == y-1))){
            dest.add(new int[]{x,y-1});
        }else if( (y!=0 && !walls[y][x].getWall(Wall.Direction.UP) && !walls[y-1][x].getWall(Wall.Direction.DOWN)) && ((pawns[0].getPawnX() == x && pawns[0].getPawnY() == y-1 ) || (pawns[1].getPawnX() == x && pawns[1].getPawnY() == y-1))){
            if(y-1 > 0 && !walls[y-1][x].getWall(Wall.Direction.UP)){
                dest.add(new int[]{x,y-2});
            }else{
            if (x != 0 && y != 0 && !walls[y-1][x].getWall(Wall.Direction.LEFT)) {
                dest.add(new int[]{x - 1, y - 1});
            }
            if (x != 8 && y != 0 && !walls[y-1][x].getWall(Wall.Direction.RIGHT)) {
                dest.add(new int[]{x + 1, y - 1});
            }
            }

        }

        if (y!=8 && !walls[y][x].getWall(Wall.Direction.DOWN) && !walls[y+1][x].getWall(Wall.Direction.UP) && !((pawns[0].getPawnX() == x && pawns[0].getPawnY() == y+1) || pawns[1].getPawnX() == x && pawns[1].getPawnY() == y+1)){
            dest.add(new int[]{x,y+1});
        }else if((y!=8 && !walls[y][x].getWall(Wall.Direction.DOWN) && !walls[y+1][x].getWall(Wall.Direction.UP)) && ((pawns[0].getPawnX() == x && pawns[0].getPawnY() == y+1) || (pawns[1].getPawnX() == x && pawns[1].getPawnY() == y+1))){
            if(y+1 < 8 && !walls[y+1][x].getWall(Wall.Direction.DOWN)){
                dest.add(new int[]{x,y+2});
            }
            else{
            if (x != 0 && y != 8 && !walls[y+1][x].getWall(Wall.Direction.LEFT)) {
                dest.add(new int[]{x - 1, y + 1});
            }
            if (x != 8 && y != 8 && !walls[y+1][x].getWall(Wall.Direction.RIGHT)) {
                dest.add(new int[]{x + 1, y + 1});
            }
            }
        }
     return dest;
    }

    /**
     * Analyse the second input of the player for the move of a pawn
     * @param line
     * @return
     */
    public boolean analyseSecondStepP(String line){
        QuorStageModel gameStage = (QuorStageModel) model.getGameStage();
        GameElement pawn = gameStage.getPawns()[model.getIdPlayer()];
        if(line.length() !=2)return false;
        int col = (line.charAt(0)-'A');
        int row = (line.charAt(1)-'1');
        if(row < 0 || row > 8 || col < 0 || col > 8) return false;

        gameStage.getBoard().setInvalidCells();
        List<int[]> possibleDest = possibleDest(((Pawn)pawn).getPawnX() , ((Pawn)pawn).getPawnY() , gameStage.getWalls(), gameStage.getPawns());

        for(int[] dest : possibleDest){

            gameStage.getBoard().setvalidCell(dest[0],dest[1]);
        }
        if (!gameStage.getBoard().canReachCell(row,col)) return false;

        ActionList actions = new ActionList(true);
        GameAction move = new MoveAction(model, pawn, "QuorBoard", row, col);
        ((Pawn)pawn).setPawnY(row);
        ((Pawn)pawn).setPawnX(col);
        // add the action to the action list.
        actions.addSingleAction(move);
        ActionPlayer play = new ActionPlayer(model, this, actions);
        play.start();
        return true;
    }

    /**
     * Convert the given char to a direction
     * @param direction
     * @return
     */
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

    /**
     * Convert the given char to a coord
     * @param x
     * @param y
     * @return
     */
    public int[] charToCoord(char x, char y){
        int[] coord = new int[2];
        coord[0] = x - 'A';
        coord[1] = y - '0'-1;
        return coord;
    }

    /**
     * Convert the given coord to an orientation (for the walls)
     * @param coord1
     * @param coord2
     * @return
     */
    public orientation coordsToOrientation(int[] coord1, int[] coord2){
        if (coord1[0] == coord2[0]){
            if (coord1[1] == coord2[1] + 1 || coord1[1] == coord2[1] - 1){
                 return orientation.VERTICAL;
            }
        }
        else if (coord1[1] == coord2[1] && (coord1[0] == coord2[0] + 1 || coord1[0] == coord2[0] - 1)){
            return orientation.HORIZONTAL;
        }
        return null;
    }


    /**
     * set the coord of the wall
     * @param coord
     * @param direction
     * @param walls
     * @return
     */
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

    /**
     * Check if the wall cross another wall
     * @param coord
     * @param coord2
     * @param dir
     * @param walls
     * @return
     */
    public boolean isCross(int[] coord , int[] coord2 , Wall.Direction dir , Wall[][] walls ){
        orientation orien = coordsToOrientation(coord , coord2);
        if (orien == orientation.HORIZONTAL){
            int wall1 = Math.min(coord[0], coord2[0]);
            if (isBorder( coord , dir) || isBorder(coord2 , dir)){
                return true;
            }
            if (dir == Wall.Direction.UP){
                return walls[coord[1]][wall1].getWall(Wall.Direction.RIGHT) && walls[coord[1] - 1][wall1].getWall(Wall.Direction.RIGHT);

            }else if (dir == Wall.Direction.DOWN){
                return walls[coord[1]][wall1].getWall(Wall.Direction.RIGHT) && walls[coord[1] + 1][wall1].getWall(Wall.Direction.RIGHT);
            }
        }else if (orien == orientation.VERTICAL){
            int wall1 = Math.min(coord[1], coord2[1]);
            if (isBorder( coord , dir) || isBorder(coord2 , dir)){
                return true;
            }
            if (dir == Wall.Direction.LEFT){
                return walls[wall1][coord[0]].getWall(Wall.Direction.DOWN) && walls[wall1][coord[0] - 1].getWall(Wall.Direction.DOWN);
            }else if (dir == Wall.Direction.RIGHT){
                return walls[wall1][coord[0]].getWall(Wall.Direction.DOWN) && walls[wall1][coord[0] + 1].getWall(Wall.Direction.DOWN);
            }
        }

        return false;

    }

    /**
     * Analyse the second input of the player for the wall placement
     * @param line
     * @return
     */
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

        if (isCross(coord,coord2,dir,walls)){
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


        setWallcoord(coord,dir, walls);
        setWallcoord(coord2,dir, walls);
        gameStage.getBoard().update();

        gameStage.getNbWalls()[model.getIdPlayer()]--;
        pawns[model.getIdPlayer()].decrementWallCount();

        Wall[][] wallsShow = gameStage.getWallsShow();
        gameStage.removeElement(wallsShow[model.getIdPlayer()][9-pawns[model.getIdPlayer()].getWallCount()]);



        gameStage.getGrid("QuorBoard").resetReachableCells(true);
        return true;
    }

}
