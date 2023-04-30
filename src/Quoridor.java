import boardifier.control.StageFactory;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.ConsoleColor;
import boardifier.view.View;
import control.QuorController;

public class Quoridor {
    public static void main(String[] args) {

        int mode = 0;
        if (args.length == 1) {
            try {
                mode = Integer.parseInt(args[0]);
                if ((mode <0) || (mode>2)) mode = 0;
            }
            catch(NumberFormatException e) {
                mode = 0;
            }
        }
        Model model = new Model();
        if (mode == 0) {
            model.addHumanPlayer("player " + ConsoleColor.BLUE_BOLD +"1" + ConsoleColor.RESET);
            model.addHumanPlayer("player "+ ConsoleColor.RED_BOLD +"2" + ConsoleColor.RESET);
        }
        else if (mode == 1) {
            model.addHumanPlayer("player "+ ConsoleColor.BLUE_BOLD +"1" + ConsoleColor.RESET);
            model.addComputerPlayer("computer "+ ConsoleColor.RED_BOLD +"2" + ConsoleColor.RESET);
        }
        else {
            model.addComputerPlayer("computer "+ ConsoleColor.BLUE_BOLD +"1" + ConsoleColor.RESET);
            model.addComputerPlayer("computer "+ ConsoleColor.RED_BOLD +"2" + ConsoleColor.RESET );
        }

        StageFactory.registerModelAndView("Quoridor", "model.QuorStageModel", "view.QuorStageView");

        View quorView = new View(model);
        QuorController control = new QuorController(model,quorView);
        control.setFirstStageName("Quoridor");
        try{
            control.startGame();
            control.stageLoop();
        } catch (GameException e) {
            throw new RuntimeException(e);
        }


    }
}
