package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.model.GameElement;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.view.View;
import graph.Graph;
import javafx.scene.control.TextInputDialog;
import model.Pawn;
import model.QuorStageModel;
import model.Wall;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static model.Wall.isBorder;

public class QuorController extends Controller {
    int mode;
    String player1Name;
    String player2Name;
    BufferedReader consoleIn;
    boolean firstPlayer;

    enum orientation {
        HORIZONTAL,
        VERTICAL
    }

    /**
     * Constructor
     *
     * @param model The model
     * @param view The view
     */
    public QuorController(Model model, View view, int mode) {
        super(model, view);
        setControlAction(new QuorControllerAction(model, view, this));
        setControlMouse(new QuorControllerMouse(model, view, this));
        firstPlayer = true;
        this.mode = mode;
    }

    public void showAlertName(int mode) {
        TextInputDialog player1Dialog = new TextInputDialog();
        player1Dialog.setTitle("Player 1");
        player1Dialog.setHeaderText("Enter Player 1 Name:");
        Optional<String> player1Result = player1Dialog.showAndWait();

        // Check if player 1 name was entered
        if (player1Result.isPresent()) {
            player1Name = player1Result.get();
            if (player1Name.equals("")) {
                player1Name = "Player 1";
            }
        } else {
            player1Name = "Player 1";
        }
        if (mode == 0) {
            // Create a TextInputDialog for player 2 name
            TextInputDialog player2Dialog = new TextInputDialog();
            player2Dialog.setTitle("Player 2");
            player2Dialog.setHeaderText("Enter Player 2 Name:");
            Optional<String> player2Result = player2Dialog.showAndWait();

            // Check if player 2 name was entered
            if (player2Result.isPresent()) {
                player2Name = player2Result.get();
                if (player2Name.equals("")) {
                    player2Name = "Player 2";
                }
            } else {
                player2Name = "Player 2";
            }
        }
    }

    public void initPlayers(int mode) {

        if (mode == 0) {
            showAlertName(mode);
            model.addHumanPlayer(player1Name);
            model.addHumanPlayer(player2Name);
        } else if (mode == 1) {
            showAlertName(mode);
            model.addHumanPlayer(player1Name);
            model.addComputerPlayer("IA 2");
        } else if (mode == 2) {
            showAlertName(mode);
            model.addComputerPlayer("IA 1");
            model.addHumanPlayer(player1Name);
        } else {
            model.addComputerPlayer("IA 1");
            model.addComputerPlayer("IA 2");
        }
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
        model.setNextPlayer();
        // get the new player
        Player p = model.getCurrentPlayer();
        // change the text of the TextElement
        QuorStageModel stageModel = (QuorStageModel) model.getGameStage();
        stageModel.getPlayerName().setText(p.getName());
        System.out.println("NEXT PLAYER: " + p.getName() + " (" + p.getType() + ")");
        if (p.getType() == Player.COMPUTER) {
            System.out.println("COMPUTER PLAYS");
            QuorDecider decider = new QuorDecider(model, this, model.getIdPlayer());
            ActionPlayer play = new ActionPlayer(model, this, decider, null);
            play.start();
        }

    }



    /**
     * Find the coordinates of every places a wall could be placed
     *
     * @param walls List of walls
     * @param pawns List of pawns
     * @return List of coordinates
     */
    public List<int[]> possibleWall(Wall[][] walls, Pawn[] pawns) {
        List<int[]> possibleWall = new ArrayList<>();
        Graph graph = new Graph(walls);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 8; j++) {
                if (i != 0 && !walls[i][j].getWall(Wall.Direction.UP) && !walls[i][j + 1].getWall(Wall.Direction.UP) && !isCross(new int[]{j, i}, new int[]{j + 1, i}, Wall.Direction.UP, walls)) {
                    graph.removeArete(new int[]{j, i}, new int[]{j + 1, i}, Wall.Direction.UP);
                    if (graph.isPathPossibleY(pawns[0].getPawnXY(), pawns[0].getWinY()) && graph.isPathPossibleY(pawns[1].getPawnXY(), pawns[1].getWinY())) {
                        possibleWall.add(new int[]{j, i, j + 1, i, 0});
                    }
                    graph.addArete(new int[]{j, i}, new int[]{j + 1, i}, Wall.Direction.UP);
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 9; j++) {
                if (j != 0 && !walls[i][j].getWall(Wall.Direction.LEFT) && !walls[i + 1][j].getWall(Wall.Direction.LEFT) && !isCross(new int[]{j, i}, new int[]{j, i + 1}, Wall.Direction.LEFT, walls)) {
                    graph.removeArete(new int[]{j, i}, new int[]{j, i + 1}, Wall.Direction.LEFT);
                    if (graph.isPathPossibleY(pawns[0].getPawnXY(), pawns[0].getWinY()) && graph.isPathPossibleY(pawns[1].getPawnXY(), pawns[1].getWinY())) {
                        possibleWall.add(new int[]{j, i, j, i + 1, 2});
                    }
                    graph.addArete(new int[]{j, i}, new int[]{j, i + 1}, Wall.Direction.LEFT);
                }
            }
        }


        return possibleWall;

    }


    /**
     * Find the coordinates of every places a pawn could go
     *
     * @param x x coordinate of the pawn
     * @param y y coordinate of the pawn
     * @param walls List of the walls
     * @param pawns List of the pawns
     * @return List of coordinates
     */
    public List<int[]> possibleDest(int x, int y, Wall[][] walls, Pawn[] pawns) {
        List<int[]> dest = new ArrayList<>();

        // verifie si le pion peut aller a gauche ou sinon si il peut sauter par dessus un pion
        if (x != 0 && !walls[y][x].getWall(Wall.Direction.LEFT) && !walls[y][x - 1].getWall(Wall.Direction.RIGHT) && !(pawns[0].getPawnX() == x - 1 && pawns[0].getPawnY() == y || pawns[1].getPawnX() == x - 1 && pawns[1].getPawnY() == y)) {
            dest.add(new int[]{x - 1, y});
        } else if ((x != 0 && !walls[y][x].getWall(Wall.Direction.LEFT) && !walls[y][x - 1].getWall(Wall.Direction.RIGHT)) && (pawns[0].getPawnX() == x - 1 && pawns[0].getPawnY() == y || pawns[1].getPawnX() == x - 1 && pawns[1].getPawnY() == y)) {
            if (x - 1 > 0 && !walls[y][x - 1].getWall(Wall.Direction.LEFT)) {
                dest.add(new int[]{x - 2, y});
            } else {

                if (y != 0 && !walls[y][x - 1].getWall(Wall.Direction.UP)) {
                    dest.add(new int[]{x - 1, y - 1});
                }
                if (y != 8 && !walls[y][x - 1].getWall(Wall.Direction.DOWN)) {
                    dest.add(new int[]{x - 1, y + 1});
                }
            }
        }

        // Verifie si le pion peut aller a droite ou sinon il doit traverser le pion adverse
        if (x != 8 && !walls[y][x].getWall(Wall.Direction.RIGHT) && !walls[y][x + 1].getWall(Wall.Direction.LEFT) && !(pawns[0].getPawnX() == x + 1 && pawns[0].getPawnY() == y || pawns[1].getPawnX() == x + 1 && pawns[1].getPawnY() == y)) {
            dest.add(new int[]{x + 1, y});
        } else if ((x != 8 && !walls[y][x].getWall(Wall.Direction.RIGHT) && !walls[y][x + 1].getWall(Wall.Direction.LEFT)) && ((pawns[0].getPawnX() == x + 1 && pawns[0].getPawnY() == y) || (pawns[1].getPawnX() == x + 1 && pawns[1].getPawnY() == y))) {
            if (x + 1 < 8 && !walls[y][x + 1].getWall(Wall.Direction.RIGHT)) {
                dest.add(new int[]{x + 2, y});
            } else {
                if (y > 0 && x < 8 && !walls[y][x + 1].getWall(Wall.Direction.UP)) {
                    dest.add(new int[]{x + 1, y - 1});
                }
                if (y < 8 && x < 8 && !walls[y][x + 1].getWall(Wall.Direction.DOWN)) {
                    dest.add(new int[]{x + 1, y + 1});
                }
            }
        }

        if (y != 0 && !walls[y][x].getWall(Wall.Direction.UP) && !walls[y - 1][x].getWall(Wall.Direction.DOWN) && !((pawns[0].getPawnX() == x && pawns[0].getPawnY() == y - 1 || pawns[1].getPawnX() == x && pawns[1].getPawnY() == y - 1))) {
            dest.add(new int[]{x, y - 1});
        } else if ((y != 0 && !walls[y][x].getWall(Wall.Direction.UP) && !walls[y - 1][x].getWall(Wall.Direction.DOWN)) && ((pawns[0].getPawnX() == x && pawns[0].getPawnY() == y - 1) || (pawns[1].getPawnX() == x && pawns[1].getPawnY() == y - 1))) {
            if (y - 1 > 0 && !walls[y - 1][x].getWall(Wall.Direction.UP)) {
                dest.add(new int[]{x, y - 2});
            } else {
                if (x != 0 && !walls[y - 1][x].getWall(Wall.Direction.LEFT)) {
                    dest.add(new int[]{x - 1, y - 1});
                }
                if (x != 8 && !walls[y - 1][x].getWall(Wall.Direction.RIGHT)) {
                    dest.add(new int[]{x + 1, y - 1});
                }
            }

        }

        if (y != 8 && !walls[y][x].getWall(Wall.Direction.DOWN) && !walls[y + 1][x].getWall(Wall.Direction.UP) && !((pawns[0].getPawnX() == x && pawns[0].getPawnY() == y + 1) || pawns[1].getPawnX() == x && pawns[1].getPawnY() == y + 1)) {
            dest.add(new int[]{x, y + 1});
        } else if ((y != 8 && !walls[y][x].getWall(Wall.Direction.DOWN) && !walls[y + 1][x].getWall(Wall.Direction.UP)) && ((pawns[0].getPawnX() == x && pawns[0].getPawnY() == y + 1) || (pawns[1].getPawnX() == x && pawns[1].getPawnY() == y + 1))) {
            if (y + 1 < 8 && !walls[y + 1][x].getWall(Wall.Direction.DOWN)) {
                dest.add(new int[]{x, y + 2});
            } else {
                if (x != 0 && !walls[y + 1][x].getWall(Wall.Direction.LEFT)) {
                    dest.add(new int[]{x - 1, y + 1});
                }
                if (x != 8 && !walls[y + 1][x].getWall(Wall.Direction.RIGHT)) {
                    dest.add(new int[]{x + 1, y + 1});
                }
            }
        }
        return dest;
    }

    /**
     * Analyse the second input of the player for the move of a pawn
     */
    public void analyseSecondStepP() {
        QuorStageModel gameStage = (QuorStageModel) model.getGameStage();
        GameElement pawn = gameStage.getPawns()[model.getIdPlayer()];
        gameStage.getBoard().setInvalidCells();

        List<int[]> possibleDest = possibleDest(((Pawn) pawn).getPawnX(), ((Pawn) pawn).getPawnY(), gameStage.getWalls(), gameStage.getPawns());

        for (int[] dest : possibleDest) {
            gameStage.getBoard().setvalidCell(dest[0], dest[1]);
        }
    }


    /**
     * Convert the given coord to an orientation (for the walls)
     *
     * @param pos1 the coord of the wall
     * @param pos2 the coord of the wall
     * @return the orientation of the wall
     */
    public orientation coordsToOrientation(int[] pos1, int[] pos2) {
        if (pos1[0] == pos2[0]) {
            if (pos1[1] == pos2[1] + 1 || pos1[1] == pos2[1] - 1) {
                return orientation.VERTICAL;
            }
        } else if (pos1[1] == pos2[1] && (pos1[0] == pos2[0] + 1 || pos1[0] == pos2[0] - 1)) {
            return orientation.HORIZONTAL;
        }
        return null;
    }


    /**
     * set the coord of the wall
     *
     * @param coord    coord of the wall
     * @param direction direction of the wall
     * @param walls the walls of the game
     */
    public void setWallCoord(int[] coord, Wall.Direction direction, Wall[][] walls, int idPlayer) {
        walls[coord[1]][coord[0]].setWall(direction, true, idPlayer);
        if (direction == Wall.Direction.UP && coord[1] != 0) {
            walls[coord[1] - 1][coord[0]].setWall(Wall.Direction.DOWN, true, idPlayer);
        } else if (direction == Wall.Direction.DOWN && coord[1] != 8) {
            walls[coord[1] + 1][coord[0]].setWall(Wall.Direction.UP, true, idPlayer);
        } else if (direction == Wall.Direction.LEFT && coord[0] != 0) {
            walls[coord[1]][coord[0] - 1].setWall(Wall.Direction.RIGHT, true, idPlayer);
        } else if (direction == Wall.Direction.RIGHT && coord[0] != 8) {
            walls[coord[1]][coord[0] + 1].setWall(Wall.Direction.LEFT, true, idPlayer);
        }

    }

    /**
     * Check if the wall cross another wall
     *
     * @param coord x y of the first wall
     * @param coord2 x y of the second wall
     * @param dir   direction of walls
     * @param walls the walls of the game
     * @return true if the wall cross another wall
     */
    public boolean isCross(int[] coord, int[] coord2, Wall.Direction dir, Wall[][] walls) {
        orientation orien = coordsToOrientation(coord, coord2);
        if (orien == orientation.HORIZONTAL) {
            int wall1 = Math.min(coord[0], coord2[0]);
            if (isBorder(coord, dir) || isBorder(coord2, dir)) {
                return true;
            }
            if (dir == Wall.Direction.UP) {
                return walls[coord[1]][wall1].getWall(Wall.Direction.RIGHT) && walls[coord[1] - 1][wall1].getWall(Wall.Direction.RIGHT);

            } else if (dir == Wall.Direction.DOWN) {
                return walls[coord[1]][wall1].getWall(Wall.Direction.RIGHT) && walls[coord[1] + 1][wall1].getWall(Wall.Direction.RIGHT);
            }
        } else if (orien == orientation.VERTICAL) {
            int wall1 = Math.min(coord[1], coord2[1]);
            if (isBorder(coord, dir) || isBorder(coord2, dir)) {
                return true;
            }
            if (dir == Wall.Direction.LEFT) {
                return walls[wall1][coord[0]].getWall(Wall.Direction.DOWN) && walls[wall1][coord[0] - 1].getWall(Wall.Direction.DOWN);
            } else if (dir == Wall.Direction.RIGHT) {
                return walls[wall1][coord[0]].getWall(Wall.Direction.DOWN) && walls[wall1][coord[0] + 1].getWall(Wall.Direction.DOWN);
            }
        }

        return false;

    }


    /**
     *  check if the wall is possible to place
     * @param x x coord
     * @param y y coord
     * @param horizontal orientation of the wall
     * @return true if the wall is possible to place
     */
    public boolean analyseSecondStepW(int x, int y, boolean horizontal) {
        Wall.Direction dir;
        if (horizontal) {
            dir = Wall.Direction.LEFT;
        } else {
            dir = Wall.Direction.UP;
        }

        if (x == 8 && (dir != Wall.Direction.LEFT) || (y == 8 && dir != Wall.Direction.UP) || x == 0 && dir == Wall.Direction.LEFT || y == 0 && dir == Wall.Direction.UP) {
            return false;
        }

        QuorStageModel gameStage = (QuorStageModel) model.getGameStage();
        Wall[][] walls = gameStage.getWalls();
        int[] coord2ndWall = Wall.get2ndWall(dir, x, y);
        int x2 = coord2ndWall[0];
        int y2 = coord2ndWall[1];

        if (x2 > 8 || x2 < 0 || y2 > 8 || y2 < 0) {
            return false;
        }

        if (walls[y][x].getWall(dir) || walls[y2][x2].getWall(dir)) {
            return false;

        }

        if (isCross(new int[]{x, y}, coord2ndWall, dir, walls)) {
            return false;
        }

        Graph graph = new Graph(walls);
        graph.removeArete(new int[]{x, y}, coord2ndWall, dir);
        Pawn[] pawns = gameStage.getPawns();
        if (!graph.isPathPossibleY(pawns[0].getPawnXY(), pawns[0].getWinY()) || !graph.isPathPossibleY(pawns[1].getPawnXY(), pawns[1].getWinY())) {
            return false;
        }

        return true;
    }

    /**
     * Analyse the second input of the player for the wall placement
     *
     * @param x   the x coord of the wall
     * @param y   the y coord of the wall
     * @param dir the direction of the wall
     * @return true if the wall can be placed
     */
    public boolean analyseSecondStepW(int x, int y, Wall.Direction dir) {
        if (x == 8 && (dir != Wall.Direction.LEFT) || (y == 8 && dir != Wall.Direction.UP) || x == 0 && dir == Wall.Direction.LEFT || y == 0 && dir == Wall.Direction.UP) {
            System.out.println("border");
            return false;
        }

        QuorStageModel gameStage = (QuorStageModel) model.getGameStage();
        Wall[][] walls = gameStage.getWalls();
        int[] coord2ndWall = Wall.get2ndWall(dir, x, y);
        int x2 = coord2ndWall[0];
        int y2 = coord2ndWall[1];

        if (x2 > 8 || x2 < 0 || y2 > 8 || y2 < 0) {
            System.out.println("Hors grille");
            return false;
        }

        if (walls[y][x].getWall(dir) || walls[y2][x2].getWall(dir)) {
            System.out.println("le mur n'est pas libre");
            return false;

        }

        if (isCross(new int[]{x, y}, coord2ndWall, dir, walls)) {
            System.out.println("le mur croise un autre mur");
            return false;
        }

        Graph graph = new Graph(walls);
        graph.removeArete(new int[]{x, y}, coord2ndWall, dir);
        Pawn[] pawns = gameStage.getPawns();
        if (!graph.isPathPossibleY(pawns[0].getPawnXY(), pawns[0].getWinY()) || !graph.isPathPossibleY(pawns[1].getPawnXY(), pawns[1].getWinY())) {
            System.out.println("le mur bloque le chemin d'un joueur");
            return false;
        }


        setWallCoord(new int[]{x, y}, dir, walls, model.getIdPlayer());
        setWallCoord(new int[]{x2, y2}, dir, walls, model.getIdPlayer());
        pawns[model.getIdPlayer()].decrementWallCount();

        Wall[][] wallsShow = gameStage.getWallsShow();
        gameStage.removeElement(wallsShow[model.getIdPlayer()][pawns[model.getIdPlayer()].getWallCount()]);


        gameStage.getWallPots()[model.getIdPlayer()].removeWall();
        gameStage.getWallPots()[model.getIdPlayer()].lookChangedTrue();


        gameStage.getGrid("QuorBoard").resetReachableCells(true);
        return true;
    }

}
