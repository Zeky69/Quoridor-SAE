import boardifier.control.StageFactory;
import boardifier.model.Model;
import control.QuorController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.QuorRootPane;
import view.QuorView;

public class Quoridor extends Application {
    private static int mode;

    public static void main(String[] args) {

        Quoridor.mode = 0;
        if (args.length == 1) {
            try {
                Quoridor.mode = Integer.parseInt(args[0]);
                if ((Quoridor.mode < 0) || (Quoridor.mode > 2)) Quoridor.mode = 0;
            } catch (NumberFormatException e) {
                Quoridor.mode = 0;
            }
        }
        launch(args);


    }

    @Override
    public void start(Stage stage) throws Exception {

        Model model = new Model();
        StageFactory.registerModelAndView("Quoridor", "model.QuorStageModel", "view.QuorStageView");

        QuorRootPane rootPane = new QuorRootPane();

        QuorView view = new QuorView(model, stage, rootPane, Quoridor.mode);

        QuorController control = new QuorController(model, view, Quoridor.mode);
        control.setFirstStageName("Quoridor");
        stage.setTitle("Quoridor");
        stage.show();
    }
}
