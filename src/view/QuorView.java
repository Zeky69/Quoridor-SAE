package view;

import boardifier.model.Model;
import boardifier.view.RootPane;
import boardifier.view.View;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class QuorView extends View {
    private MenuItem menuItemStart;
    private MenuItem menuIntro;
    private MenuItem menuQuit;
    private MenuItem menuItemRule;
    public int mode;
    private QuorRootPane rootPane;

    /**
     * Constructor
     * @param model model
     * @param stage stage
     * @param rootPane root pane
     * @param mode mode
     */
    public QuorView(Model model, Stage stage, RootPane rootPane, int mode) {
        super(model, stage, rootPane);
        this.mode = mode;
        this.rootPane = (QuorRootPane) rootPane;
    }

    @Override
    protected void createMenuBar() {
        menuBar = new MenuBar();
        Menu menu1 = new Menu("Game");
        menuItemStart = new MenuItem("New game");
        menuItemRule = new MenuItem("Rules");
        menuIntro = new MenuItem("Intro");
        menuQuit = new MenuItem("Quit");
        menu1.getItems().addAll(menuItemStart,menuItemRule,menuIntro,menuQuit);
        menuBar.getMenus().add(menu1);
    }

    /**
     *  get button play
     * @return button play
     */
    public Button getButtonPlay() {return rootPane.buttonPlay;}

    /**
     * get combo box
     * @return combo box
     */
    public ComboBox<String> getComboBox() {return rootPane.comboBox;}

    /**
     * get menu item start
     * @return menu item start
     */
    public MenuItem getMenuItemStart() {return menuItemStart;}

    /**
     * get menu item rule
     * @return menu item rule
     */
    public MenuItem getMenuItemRule() {return menuItemRule;}

    /**
     * get menu item intro
     * @return menu item intro
     */
    public MenuItem getMenuIntro() {return menuIntro;}

    /**
     * get menu item quit
     * @return menu item quit
     */
    public MenuItem getMenuQuit() {return menuQuit;}
}


