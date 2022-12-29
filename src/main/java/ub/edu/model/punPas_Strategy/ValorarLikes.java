package ub.edu.model.punPas_Strategy;



import ub.edu.controller.SessionMemory;
import ub.edu.model.Opinio;
import ub.edu.model.Textes;

import java.util.List;

public class ValorarLikes implements iValorar {
    private SessionMemory sessionMemory;
    private Textes textes;

    public ValorarLikes() throws Exception {
        this.sessionMemory = SessionMemory.getInstance();
        this.textes =  Textes.getInstance();
    }



    @Override
    public void valorar() throws Exception {

        System.out.println("tic a valorar 222");
        List<Opinio> listaOpinions =  sessionMemory.getListOpinions();
        System.out.println(sessionMemory);
        Opinio op = new Opinio();
        op.setIdPuntDePas(sessionMemory.getIdPuntDePas());
        op.setIdTipusOpinio(5);
        op.setCorreuPersona(sessionMemory.getCorreuPersona());
        op.setTipusOpinio("Like");
        op.setValorOpinio("Like");
        listaOpinions.add(op);    // Afegeixo Like

        sessionMemory.setListOpinions(listaOpinions);
        textes.mostrarPopUp("Valoració anotada");
    }

}
