package com.pekings.pos;

import javafx.scene.layout.Region;
import javafx.util.Builder;

public class Controller {
    private Builder<Region> viewBuild;
    private Interactor interactor;

    public Controller() {
        POS_Model model = new POS_Model();
        //viewBuild = new ViewBuilder(model);
        //interactor = new Interactor(model);
    }

    public Region getView() {
        return viewBuild.build();
    }
}
