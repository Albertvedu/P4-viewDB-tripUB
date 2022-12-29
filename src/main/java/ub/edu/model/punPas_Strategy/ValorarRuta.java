package ub.edu.model.punPas_Strategy;

import ub.edu.controller.SessionMemory;
import ub.edu.model.Opinio;
import ub.edu.model.Textes;
import ub.edu.model.Vot;
import ub.edu.resources.RessourceFacadeRuta;

import java.time.LocalDate;
import java.util.List;

public class ValorarRuta implements iValorar{
    private Textes textes;
    private SessionMemory sessionMemory;
    private RessourceFacadeRuta ressourceFacadeRuta;

    public ValorarRuta() throws Exception {
        this.textes = Textes.getInstance();
        this.sessionMemory = SessionMemory.getInstance();
        this.ressourceFacadeRuta = RessourceFacadeRuta.getInstance();
    }

    @Override
    public void valorar() throws Exception {
        List<Vot> llistaVots =  sessionMemory.getLlistaVots();
        Vot vot = new Vot();
        vot.setId(llistaVots.size()+7);
        vot.setIdTipusVot(sessionMemory.getIdTipus());
        vot.setIdRuta(sessionMemory.getIdRuta());
        vot.setData(LocalDate.now());
        vot.setCorreuPersona(sessionMemory.getCorreuPersona());
        vot.setIdGrup(sessionMemory.getIdGrup());
        vot.setTipusVot(sessionMemory.getTipusValoracio());
        vot.setValorVot(sessionMemory.getValoracio());

        llistaVots.add(vot);

        sessionMemory.setLlistaVots(llistaVots);
        textes.mostrarPopUp("Valoració Registrada");

    }

}
