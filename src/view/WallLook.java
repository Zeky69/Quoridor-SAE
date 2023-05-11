
package view;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.Pawn;

public class WallLook extends ElementLook {

    public WallLook(int player, GameElement element) {
        super(element, 1, 1);
        if(player == 0){
            shape[0][0] = ConsoleColor.BLUE_BOLD +"┃"+ ConsoleColor.RESET;
        }
        else{
            shape[0][0] = ConsoleColor.RED_BOLD +"┃"+ ConsoleColor.RESET;
        }

    }

    @Override
    public void onLookChange() {
        // do nothing since a pawn never change of aspect
    }


}
