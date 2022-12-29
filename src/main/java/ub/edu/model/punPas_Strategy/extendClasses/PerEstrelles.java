package ub.edu.model.punPas_Strategy.extendClasses;

import ub.edu.model.punPas_Strategy.ValorarEstrelles;
import ub.edu.model.punPas_Strategy.ValorarPuntPas;

public class PerEstrelles extends ValorarPuntPas {
    public PerEstrelles() throws Exception {
        ivalorar = new ValorarEstrelles();
    }

}
