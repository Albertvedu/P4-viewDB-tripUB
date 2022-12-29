package ub.edu.model.punPas_Strategy;


import java.util.Map;

public abstract class ValorarPuntPas {
    protected iValorar ivalorar;
    protected iLlistar illistar;

    public ValorarPuntPas() {
    }

    public void setValorarPuntPas(iValorar v){
        this.ivalorar = v;
    }
    public void setLlistarPuntPas(iLlistar l){
        this.illistar = l;
    }

    protected boolean performaValorar() throws Exception {
        ivalorar.valorar();
        return true;
    }
    protected Map<String, Integer> performaLlistar(String criteri) throws Exception {
        return illistar.llistar( criteri);
    }

}
