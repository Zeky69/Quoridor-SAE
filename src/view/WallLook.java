
package view;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.Pawn;

public class WallLook extends ElementLook {

    /**
     * Constructor
     * @param player
     * @param element
     */
    public WallLook(int player, GameElement element) {
        super(element, 1, 1);
        if(player == 0){
            shape[0][0] = ConsoleColor.BLUE_BOLD +"┃"+ ConsoleColor.RESET;
        }
        else{
            shape[0][0] = ConsoleColor.RED_BOLD +"┃"+ ConsoleColor.RESET;
        }

    }

    /**
     * Change the look of the wall (do nothing since a wall never change of aspect)
     */
    @Override
    public void onLookChange() {
        // do nothing since a wall never change of aspect
    }


}
