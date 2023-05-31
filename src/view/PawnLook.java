package view;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
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
    public PawnLook(GameElement element , int radius){
        super(element);
        Pawn pawn = (Pawn)element;
        circle = new Circle();
        circle.setRadius(radius);
        if(pawn.getPlayer() == 1){
            circle.setFill(Color.BLUE);
        }
        else {
            circle.setFill(Color.RED);
        }

        circle.setCenterX(radius);
        circle.setCenterY(radius);
        onSelectionChange();
        addShape(circle);

    }
    @Override
    public void onSelectionChange() {
        Pawn pawn = (Pawn)getElement();
        circle.setTranslateX(pawn.getX()+pawn.getPawnX()*10-10);
        circle.setTranslateY(pawn.getY()+ pawn.getPawnY()*10-10);


//        if (pawn.isSelected()) {
//            circle.setStrokeWidth(3);
//            circle.setStrokeMiterLimit(10);
//            circle.setStrokeType(StrokeType.CENTERED);
//            circle.setStroke(Color.valueOf("0x333333"));
//        }
//        else {
//            circle.setStrokeWidth(0);
//        }

    }




    /**
     * Change the look of the pawn (do nothing since a pawn never change of aspect)
     */
    @Override
    public void onChange() {
    }


}
