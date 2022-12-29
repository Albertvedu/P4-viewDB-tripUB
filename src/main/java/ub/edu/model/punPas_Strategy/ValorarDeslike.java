package ub.edu.model.punPas_Strategy;


import ub.edu.controller.SessionMemory;
import ub.edu.model.Opinio;
import ub.edu.model.Textes;

import java.util.List;

public class ValorarDeslike implements iValorar {
    private SessionMemory sessionMemory;
    private Textes textes;

    public ValorarDeslike() throws Exception {
        this.sessionMemory = SessionMemory.getInstance();
        this.textes =  Textes.getInstance();
    }

    @Override
    public void valorar() {
        List<Opinio> listaOpinions =  sessionMemory.getListOpinions();

        Opinio op = new Opinio();
        op.setIdPuntDePas(sessionMemory.getIdPuntDePas());
        op.setIdTipusOpinio(6);
        op.setCorreuPersona(sessionMemory.getCorreuPersona());
        op.setTipusOpinio("Like");
        op.setValorOpinio("Unlike");
        listaOpinions.add(op);    // Afegeixo Like

        sessionMemory.setListOpinions(listaOpinions);
        textes.mostrarPopUp("Valoració anotada");
    }


}
