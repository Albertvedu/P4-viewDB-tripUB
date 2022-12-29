package ub.edu.model.punPas_Strategy.extendClasses;

import ub.edu.model.punPas_Strategy.ValorarDeslike;
import ub.edu.model.punPas_Strategy.ValorarPuntPas;

public class PerDesLike extends ValorarPuntPas {
    public PerDesLike() throws Exception {
        ivalorar = new ValorarDeslike();
    }
}
