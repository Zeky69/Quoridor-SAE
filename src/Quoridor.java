import boardifier.control.StageFactory;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import control.QuorController;

public class Quoridor {
    public static void main(String[] args) {

        StageFactory.registerModelAndView("Quoridor", "model.QuorStageModel", "view.QuorStageView");
        Model model = new Model();

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
