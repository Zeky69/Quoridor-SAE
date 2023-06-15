package view;

import boardifier.model.GameElement;
import boardifier.view.GridLook;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.QuorBoard;
import model.QuorStageModel;
import model.Wall;

import java.util.List;


public class QuorGridLook extends GridLook {
    Wall[][] walls;
    Rectangle recPreview;
    private Rectangle[][] cells;

    /**
     * Constructor
     *
     * @param cellWidth the width of a cell
     * @param cellHeight the height of a cell
     */
    public QuorGridLook(int cellWidth, int cellHeight, GameElement element) {
        // 10 = taille des murs
        // 80 = espace sur le coté pour afficher les murs restants
        super((cellWidth) * 9, (cellHeight) * 9, cellWidth, cellHeight, 10, "0x000000", element);

        cells = new Rectangle[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new Rectangle(cellWidth, cellHeight);
                cells[i][j].setX(j * cellWidth + borderWidth);
                cells[i][j].setY(i * cellHeight + borderWidth);
                cells[i][j].setFill(javafx.scene.paint.Color.valueOf("0xFFFFFF"));
//                cells[i][j].setStyle("-fx-stroke: black; -fx-stroke-width: 10;");
                addShape(cells[i][j]);
            }
        }


    }

    /**
     *  Constructor
     * @param size the size of the board
     * @param element the element
     */
    public QuorGridLook(int size, GameElement element) {
        // NB: To have more liberty in the design, GridLook does not compute the cell size from the dimension of the element parameter.
        // If we create the 3x3 board by adding a border of 10 pixels, with cells occupying all the available surface,
        // then, cells have a size of (size-20)/3
        super(size, size, (size) / 9, (size) / 9, 1, "0X000000", element);
        cells = new Rectangle[9][9];
        // create the rectangles.
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
//                javafx.scene.paint.Color c;
//                if ((i+j)%2 == 0) {
//                    c = javafx.scene.paint.Color.BEIGE;
//                }
//                else {
//                    c = Color.DARKGRAY;
//                }

                cells[i][j] = new Rectangle(cellWidth, cellHeight);
                cells[i][j].setFill(javafx.scene.paint.Color.valueOf("0xFFFFFF"));
                cells[i][j].setX(j * cellWidth + borderWidth);
                cells[i][j].setY(i * cellHeight + borderWidth);
                cells[i][j].setStyle("-fx-stroke: #2c2830; -fx-stroke-width: 10;");

                addShape(cells[i][j]);
            }
        }
    }

    /**
     *  show the wall
     * @param x the x coordinate
     * @param y the y coordinate
     * @param player the player
     * @param horizontal true if the wall is horizontal
     */
    public void showPreview(int x, int y, int player, boolean horizontal) {
        if ((x > 8 || y > 8) || ((x == 8 || y == 0) && !horizontal) || ((y == 8 || x == 0) && horizontal)) {
            unshowPreview();
            return;
        }
        if (recPreview == null) {
            recPreview = new Rectangle(cellWidth, cellHeight);
            recPreview.setFill(Color.valueOf("0xEFCF00"));
            recPreview.setOpacity(0.5);
            addShape(recPreview);
        }
        recPreview.setOpacity(0.5);

        if (horizontal) {
            if (player == 0) {
                recPreview.setFill(Color.LIGHTSKYBLUE);

            } else if (player == 1) {
                recPreview.setFill(Color.PINK);


            } else {
                recPreview.setFill(Color.RED);

            }

            recPreview.setWidth(10);
            recPreview.setHeight(cellHeight * 2);
            recPreview.setX(x * cellWidth - 4);
            recPreview.setY(y * cellHeight);
        } else {
            if (player == 0) {
                recPreview.setFill(Color.LIGHTSKYBLUE);

            } else if (player == 1) {
                recPreview.setFill(Color.PINK);


            } else {
                recPreview.setFill(Color.RED);

            }
            recPreview.setWidth(cellWidth * 2);
            recPreview.setHeight(10);
            recPreview.setX(x * cellWidth + 2);
            recPreview.setY(y * cellHeight - 5);
        }
    }

    /**
     * unshow the preview of the wall
     */
    public void unshowPreview() {
        if (recPreview != null) {
            recPreview.setOpacity(0);
        }
    }


    @Override
    public void onChange() {
        // in a pawn is selected, reachableCells changes. Thus, the look of the board must also changes.
        QuorBoard board = (QuorBoard) element;
        if (walls == null) {
            walls = ((QuorStageModel) (element.getGameStage())).getWalls();
        }
        boolean[][] reach = board.getReachableCells();
        List<GameElement> selectedElement = board.getGameStage().getSelected();
        boolean selected = !selectedElement.isEmpty();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boolean[] wall = walls[i][j].getWall();


                if (reach[i][j] && selected) {
                    Circle c = new Circle(10, Color.valueOf("0xe8daef"));
                    c.setCenterX(cells[i][j].getX() + cellWidth / 2);
                    c.setCenterY(cells[i][j].getY() + cellHeight / 2);
                    addShape(c);


                } else {

                    Rectangle[] rectangles = new Rectangle[4];


                    if (wall[0]) {
                        rectangles[0] = new Rectangle(cellWidth, 10);
                        rectangles[0].setX(cells[i][j].getX());
                        rectangles[0].setY(cells[i][j].getY() - 5);
                        rectangles[0].setFill(walls[i][j].getColorWall()[0] == 2 ? Color.PINK : Color.LIGHTSKYBLUE);
                        rectangles[0].setArcWidth(20);
                        rectangles[0].setArcHeight(20);
                        addShape(rectangles[0]);
                    }
                    if (wall[1]) {
                        rectangles[1] = new Rectangle(cellHeight, 10);
                        rectangles[1].setX(cells[i][j].getX());
                        rectangles[1].setY(cells[i][j].getY() + cellHeight - 5);
                        rectangles[1].setFill(walls[i][j].getColorWall()[1] == 2 ? Color.PINK : Color.LIGHTSKYBLUE);
                        rectangles[1].setArcWidth(20);
                        rectangles[1].setArcHeight(20);
                        addShape(rectangles[1]);


                    }
                    if (wall[2]) {
                        rectangles[2] = new Rectangle(10, cellWidth);
                        rectangles[2].setX(cells[i][j].getX() - 5);
                        rectangles[2].setY(cells[i][j].getY());
                        rectangles[2].setFill(walls[i][j].getColorWall()[2] == 2 ? Color.PINK : Color.LIGHTSKYBLUE);
                        rectangles[2].setArcWidth(20);
                        rectangles[2].setArcHeight(20);
                        addShape(rectangles[2]);


                    }
                    if (wall[3]) {
                        rectangles[3] = new Rectangle(10, cellHeight);
                        rectangles[3].setX(cells[i][j].getX() + cellWidth - 5);
                        rectangles[3].setY(cells[i][j].getY());
                        rectangles[3].setFill(walls[i][j].getColorWall()[3] == 2 ? Color.PINK : Color.LIGHTSKYBLUE);
                        rectangles[3].setArcWidth(20);
                        rectangles[3].setArcHeight(20);

                        addShape(rectangles[3]);


                    }
                    Circle c = new Circle(10, Color.valueOf("white"));
                    c.setCenterX(cells[i][j].getX() + cellWidth / 2);
                    c.setCenterY(cells[i][j].getY() + cellHeight / 2);
                    addShape(c);


                }


            }
        }
    }
}


