package model;

import boardifier.model.GameStageModel;
import boardifier.model.Model;
import boardifier.model.StageElementsFactory;
import boardifier.model.TextElement;

public class QuorStageModel extends GameStageModel {

    public static final int STATE_SELECTPAWN = 1;
    public static final int STATE_SELECTDEST = 2;
    QuorBoard board;
    Pawn[] pawns;
    int[] nbWalls = {10, 10};
    Wall[][] walls = new Wall[9][9];
    Wall[][] wallsShow;
    WallPot wallPot1;
    WallPot wallPot2;
    private TextElement playerName;
    private TextElement textTurn;


    /**
     * Constructor
     *
     * @param name the name of the stage
     * @param model the model of the game
     */
    public QuorStageModel(String name, Model model) {
        super(name, model);
        state = STATE_SELECTPAWN;
        setupCallbacks();


    }

    /**
     * Get the board
     *
     * @return the board
     */
    public QuorBoard getBoard() {
        return this.board;
    }

    /**
     * Get the first wall pot
     *
     * @return the first wall pot
     */
    public WallPot getWallPot1() {
        return this.wallPot1;
    }

    public WallPot[] getWallPots() {
        return new WallPot[]{this.wallPot1, this.wallPot2};
    }

    /**
     * Get the walls to show
     *
     * @return the walls to show
     */
    public Wall[][] getWallsShow() {
        return this.wallsShow;
    }

    /**
     * Set the walls to show
     *
     * @param wallsShow the walls to show
     */
    public void setWallsShow(Wall[][] wallsShow) {
        this.wallsShow = wallsShow;
    }

    /**
     * Get the second wall pot
     *
     * @return the second wall pot
     */
    public WallPot getWallPot2() {
        return this.wallPot2;
    }


    /**
     * Get the list of pawns
     * @return the list of pawns
     */
    public TextElement getPlayerName() {
        return playerName;
    }

    /**
     * Set the player name
     * @param playerName the player name
     */
    public void setPlayerName(TextElement playerName) {
        this.playerName = playerName;
        addElement(playerName);
    }

    /**
     * Get the text turn
     * @return the text turn
     */
    public TextElement getTextTurn() {
        return textTurn;
    }

    /**
     * Set the text turn
     * @param textTurn the text turn
     */
    public void setTextTurn(TextElement textTurn) {
        this.textTurn = textTurn;
        addElement(textTurn);
    }


    /**
     * Set the board
     *
     * @param board the board
     */
    public void setBoard(QuorBoard board) {
        this.board = board;
        addGrid(board);
    }

    /**
     * Set the list of pawns
     *
     * @param pawns the list of pawns
     */
    public void setPawns(Pawn[] pawns) {
        this.pawns = pawns;
        for (Pawn pawn : pawns) {
            addElement(pawn);
        }
    }

    /**
     * Set the list of walls
     *
     * @param walls the list of walls
     */
    public void setWalls(Wall[][] walls) {
        this.walls = walls;
    }


    private void setupCallbacks() {
        onMoveInGrid((element, gridDest, rowDest, colDst) -> {
            hasWon();
        });
    }

    /**
     * Set the second wall pot
     *
     * @param wallPot2 the second wall pot
     */
    public void setWallPot2(WallPot wallPot2) {
        this.wallPot2 = wallPot2;
    }

    /**
     * Set the first wall pot
     *
     * @param wallPot1 the first wall pot
     */
    public void setWallPot1(WallPot wallPot1) {
        this.wallPot1 = wallPot1;
    }

    /**
     * Get the list of walls
     *
     * @return the list walls
     */
    public Wall[][] getWalls() {
        return walls;
    }

    /**
     * Get the list of pawns
     *
     * @return the list pawn of pawns
     */
    public Pawn[] getPawns() {
        return this.pawns;
    }

    /**
     * Get the number of walls
     *
     * @return the list int of walls
     */
    public int[] getNbWalls() {
        return this.nbWalls;
    }

    /**
     * check if a player has reach his win position
     */
    public void hasWon() {
        for (int i = 0; i < pawns.length; i++) {
            if (pawns[i].getWinY() == pawns[i].getPawnY()) {
                model.setIdWinner(i);
                model.stopGame();
            }
        }
    }

    /**
     * Get the default element factory
     *
     * @return the default element factory
     */
    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new QuorStageFactory(this);
    }
}

