
package view;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class WallLook extends ElementLook {

    Rectangle rectangle;

    /**
     * Constructor
     *
     * @param player player id
     * @param element the element
     */
    public WallLook(int player, GameElement element) {
        super(element);
        rectangle = new Rectangle();
        if (player == 0) {
            rectangle.setFill(Color.LIGHTSKYBLUE);
        } else {
            rectangle.setFill(javafx.scene.paint.Color.valueOf("pink"));
        }
        rectangle.setWidth(15);
        rectangle.setHeight(60);
        rectangle.setX(0);
        rectangle.setY(0);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GRAY);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        dropShadow.setRadius(4);
        dropShadow.setSpread(0.5);
        rectangle.setEffect(dropShadow);
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);
        addShape(rectangle);


    }

    /**
     * Change the look of the wall (do nothing since a wall never change of aspect)
     */
    @Override
    public void onChange() {
        // do nothing since a wall never change of aspect
    }


}
