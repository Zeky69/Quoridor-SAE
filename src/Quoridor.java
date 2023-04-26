import boardifier.control.StageFactory;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import control.QuorController;

public class Quoridor{

    public static void main(String[] args){

        Model model = new Model();

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



        if (mode == 0) {
            model.addHumanPlayer("player1");
            model.addHumanPlayer("player2");
        }
        else if (mode == 1) {
            model.addHumanPlayer("player");
            model.addComputerPlayer("computer");
        }
        else {
            model.addComputerPlayer("computer1");
            model.addComputerPlayer("computer2");
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
