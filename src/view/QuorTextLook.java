package view;

import boardifier.model.Model;
import boardifier.model.TextElement;
import boardifier.view.TextLook;
import javafx.scene.paint.Color;

public class QuorTextLook extends TextLook {
    public QuorTextLook(int size, String color, TextElement textElement) {
        super(size, color, textElement);
        text.setFont(javafx.scene.text.Font.font ("Helvetica", size));

    }
    @Override
    public void updateText() {
        TextElement te = (TextElement) getElement();
       Model model = te.getGameStage().getModel()  ;
       int id = model != null ?model.getIdPlayer() : 0;
        if(id == 0)
            text.setFill(Color.LIGHTSKYBLUE);
        else
            text.setFill(Color.PINK);

        text.setText(te.getText());
    }

}
