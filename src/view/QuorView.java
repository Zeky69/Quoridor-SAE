package view;

import boardifier.model.Model;
import boardifier.view.RootPane;
import boardifier.view.View;
import javafx.scene.control.*;
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
        this.rootPane = (QuorRootPane) rootPane;
    }

    @Override
    protected void createMenuBar() {
        menuBar = new MenuBar();
        Menu menu1 = new Menu("Game");
        menuItemStart = new MenuItem("New game");
        menuIntro = new MenuItem("Intro");
        menuQuit = new MenuItem("Quit");
        menu1.getItems().addAll(menuItemStart, menuIntro, menuQuit);
        menuBar.getMenus().add(menu1);
    }

    public MenuItem getMenuItemStart() {
        return menuItemStart;
    }

    public Button getButtonPlay() {
        return rootPane.buttonPlay;
    }

    public String getPlayer1Name() {
        if (rootPane.pseudo1 == null) {
            return "Player 1";
        }
        return rootPane.pseudo1.getText();
    }

    public String getPlayer2Name() {
        if (rootPane.pseudo2 == null) {
            return "Player 2";
        }
        return rootPane.pseudo2.getText();
    }

    public ComboBox<String> getComboBox() {
        return rootPane.comboBox;
    }

    public MenuItem getMenuIntro() {
        return menuIntro;
    }

    public MenuItem getMenuQuit() {
        return menuQuit;
    }
}


