package view;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.Pawn;

public class PawnLook extends ElementLook {

    public PawnLook(GameElement element){
        super(element, 1, 1);
        Pawn pawn = (Pawn)element;
        if(pawn.getPlayer() == 1){
            shape[0][0] = ConsoleColor.BLUE_BOLD +"B"+ ConsoleColor.RESET;
        }
        else{
            shape[0][0] = ConsoleColor.RED_BOLD +"R"+ ConsoleColor.RESET;
        }

    }

    @Override
    public void onLookChange() {
        // do nothing since a pawn never change of aspect
    }


}
