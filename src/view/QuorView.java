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
    private Menu menuStart;
    private MenuItem menuIntro;
    private MenuItem menuQuit;
    private MenuItem subMenuHvH;
    private MenuItem subMenuHvC;
    private MenuItem subMenuCvC;

    public QuorView(Model model, Stage stage, RootPane rootPane) {
        super(model, stage, rootPane);
    }

    @Override
    protected void createMenuBar() {
        menuBar = new MenuBar();
        Menu menu1 = new Menu("Game");
        menuStart = new Menu("New game");
        menuIntro = new MenuItem("Intro");
        menuQuit = new MenuItem("Quit");
        menu1.getItems().addAll(menuStart,menuIntro,menuQuit);

        subMenuHvH = new MenuItem("Human vs Human");
        subMenuHvC = new MenuItem("Human vs Computer");
        subMenuCvC = new MenuItem("Computer vs Computer");
        menuStart.getItems().addAll(subMenuHvH, subMenuHvC, subMenuCvC);


        menuBar.getMenus().add(menu1);
    }



    public Menu getMenuStart() {
        return menuStart;
    }

    public MenuItem getSubMenuHvH() {return subMenuHvH; }

    public MenuItem getSubMenuHvC() {return subMenuHvC; }

    public MenuItem getSubMenuCvC() {return subMenuCvC; }

    public MenuItem getMenuIntro() {
        return menuIntro;
    }

    public MenuItem getMenuQuit() {
        return menuQuit;
    }
}


