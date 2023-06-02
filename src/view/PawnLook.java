package view;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.geometry.Bounds;
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
    Color lightColor;
    public PawnLook(GameElement element , int radius){
        super(element);
        Pawn pawn = (Pawn)element;
        circle = new Circle();
        circle.setRadius(radius);
        if(pawn.getPlayer() == 1){
            color = Color.LIGHTSKYBLUE;
            lightColor = Color.DEEPSKYBLUE;

        }
        else {
            color =Color.PINK;
            lightColor = Color.LIGHTCORAL;
        }
        circle.setFill(color);

        circle.setCenterX(radius);
        circle.setCenterY(radius);
        addShape(circle);


    }
    @Override
    public void onSelectionChange() {
        Pawn pawn = (Pawn)getElement();
        if (pawn.isSelected()) {
            circle.setStrokeWidth(8);
            circle.setStrokeMiterLimit(10);
            circle.setStrokeType(StrokeType.CENTERED);
            circle.setStroke(lightColor);
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
