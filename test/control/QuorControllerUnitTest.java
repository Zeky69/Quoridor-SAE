/*
package control;

import boardifier.control.StageFactory;
import boardifier.model.*;
import boardifier.view.RootPane;
import boardifier.view.View;
import javafx.stage.Stage;
import model.Pawn;
import model.QuorStageModel;
import model.Wall;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import boardifier.model.Model;

import boardifier.view.ConsoleColor;


public class QuorControllerUnitTest {

    @BeforeAll
    public static void setup() {
        Model model = new Model();
        RootPane rootPane = new RootPane();
        Stage stage = new Stage();

        View quorView = new View(model, stage, rootPane);
    }

    @Test
    public void nextPlayerUnitTest() {

        String computerName = "computer";
        String playerName = "player";
        model.addHumanPlayer(playerName + ConsoleColor.BLUE_BOLD + " 1" + ConsoleColor.RESET);
        model.addHumanPlayer(playerName + ConsoleColor.RED_BOLD + " 2" + ConsoleColor.RESET);
        QuorController quorController = new QuorController(model, quorView);
        quorController.nextPlayer();
        Assertions.assertFalse(quorController.firstPlayer);
    }

    @Test
    public void updateUnitTest() throws GameException {
        StageFactory.registerModelAndView("Quoridor", "model.QuorStageModel", "view.QuorStageView");
        Model model = new Model();
        View quorView = new View(model);
        String computerName = "computer";
        String playerName = "player";
        model.addHumanPlayer(playerName + ConsoleColor.BLUE_BOLD + " 1" + ConsoleColor.RESET);
        model.addHumanPlayer(playerName + ConsoleColor.RED_BOLD + " 2" + ConsoleColor.RESET);

        QuorController control = new QuorController(model, quorView);
        control.setFirstStageName("Quoridor");
        control.startGame();
        control.stageLoop();
        control.nextPlayer();

        Assertions.assertFalse(control.firstPlayer);
    }

}
*/
