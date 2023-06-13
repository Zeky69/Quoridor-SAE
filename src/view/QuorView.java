package view;

import boardifier.model.Model;
import boardifier.view.RootPane;
import boardifier.view.View;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class QuorView extends View {
    private MenuItem menuItemStart;
    private MenuItem menuIntro;
    private MenuItem menuQuit;
    public int mode;
    private QuorRootPane rootPane;

    public QuorView(Model model, Stage stage, RootPane rootPane, int mode) {
        super(model, stage, rootPane);
        this.mode = mode;
        this.rootPane = (QuorRootPane)rootPane;
    }

    @Override
    protected void createMenuBar() {
        menuBar = new MenuBar();
        Menu menu1 = new Menu("Game");
        menuItemStart = new MenuItem("New game");
        menuIntro = new MenuItem("Intro");
        menuQuit = new MenuItem("Quit");
        menu1.getItems().addAll(menuItemStart,menuIntro,menuQuit);
        menuBar.getMenus().add(menu1);
    }

    public MenuItem getMenuItemStart() {return menuItemStart;}

    public Button getButtonModeHvH() {return rootPane.buttonModeHvH;}

    public Button getButtonModeHvC() {return rootPane.buttonModeHvC;}

    public Button getButtonModeCvC() {return rootPane.buttonModeCvC;}

    public MenuItem getMenuIntro() {
        return menuIntro;
    }

    public MenuItem getMenuQuit() {return menuQuit;}
}


