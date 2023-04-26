package control;

import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Model;
import boardifier.model.action.ActionList;

public class QuorDecider extends Decider {

    public QuorDecider(Model model, Controller control) {
        super(model, control);
    }

    @Override
    public ActionList decide(){
        return null;
    }
}
