package view;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import model.Pawn;

public class PawnLook extends ElementLook {

    /**
     * Constructor
     * @param element
     */

    Circle circle;

    Color color;
    Color selectedColor;
    public PawnLook(GameElement element , int radius){
        super(element);
        Pawn pawn = (Pawn)element;
        circle = new Circle();
        circle.setRadius(radius);
        if(pawn.getPlayer() == 1){
            color = Color.LIGHTSKYBLUE;
            selectedColor = Color.valueOf("0x2e86c1");

        }
        else {
            color =Color.PINK;
            selectedColor = Color.LIGHTCORAL;
        }
        circle.setFill(color);

        circle.setCenterX(radius+2);
        circle.setCenterY(radius+2);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GRAY);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        dropShadow.setRadius(4);
        dropShadow.setSpread(0.5);
        circle.setEffect(dropShadow);
        addShape(circle);


    }
    @Override
    public void onSelectionChange() {
        Pawn pawn = (Pawn)getElement();
        if (pawn.isSelected()) {
            circle.setStrokeWidth(8);
            circle.setStrokeMiterLimit(10);
            circle.setStrokeType(StrokeType.CENTERED);
            circle.setStroke(selectedColor);
        }
        else {
            circle.setStrokeWidth(0);
        }
    }




    /**
     * Change the look of the pawn (do nothing since a pawn never change of aspect)
     */
    @Override
    public void onChange() {
    }


}
