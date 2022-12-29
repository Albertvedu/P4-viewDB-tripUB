package ub.edu.model.punPas_Strategy;

import ub.edu.controller.SessionMemory;
import ub.edu.model.Opinio;
import ub.edu.model.Textes;
import ub.edu.model.TripUB;
import ub.edu.resources.RessourceFacadePuntdePas;

import java.util.List;



public class ValorarEstrelles implements iValorar {

    private TripUB tripUB;
    private RessourceFacadePuntdePas ressourceFacadePuntdePas;
    private SessionMemory sessionMemory;
    private Textes textes;

    public ValorarEstrelles() throws Exception {
        this.tripUB = TripUB.getInstance();
        this.ressourceFacadePuntdePas = RessourceFacadePuntdePas.getInstance();
        this.sessionMemory = SessionMemory.getInstance();
        this.textes = Textes.getInstance();
    }

    @Override
    public void valorar() throws Exception {
        // Aqui manager de valorar ...

        List<Opinio> listaOpinions =  sessionMemory.getListOpinions();
        Opinio op = new Opinio();

        op.setIdPuntDePas(sessionMemory.getIdPuntDePas());
        op.setCorreuPersona(sessionMemory.getCorreuPersona());
        op.setTipusOpinio("Estrelles");
        op.setValorOpinio(sessionMemory.getValoracio());
        listaOpinions.add(op);

        sessionMemory.setListOpinions( listaOpinions );
        textes.mostrarPopUp("Valoració Registrada");
    }




}
