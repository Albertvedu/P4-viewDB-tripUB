package ub.edu.model.punPas_Strategy;

import ub.edu.model.PerfilPersona;
import ub.edu.model.Persona;
import ub.edu.model.TripUB;
import ub.edu.model.Vot;
import ub.edu.resources.ResourcesFacade;
import ub.edu.resources.service.DataService;


import java.time.LocalDate;
import java.util.List;



public class ValorarEstrelles implements iValorar {

    private TripUB tripUB;
    private ResourcesFacade resourcesFacade;
    private List<Vot> listVots;

    public ValorarEstrelles() throws Exception {
        this.tripUB = TripUB.getInstance();
        this.resourcesFacade = ResourcesFacade.getInstance();
        this.listVots = resourcesFacade.getAllVots();
    }

    @Override
    public boolean valorar(int idPuntPas, String valoracio, String correu_persona, Integer id_ruta) throws Exception {
        // Aqui manager de valorar ...

//        for (Map.Entry<String, Ruta> elementMap : rutaMap.entrySet()) { // recorre RUTES
//            Map.Entry<String, Ruta> entry =  elementMap;
//
//            for (Tram t: entry.getValue().getTramList())               //  recorre TRAMS
//                for (Punt_dePas p: t.getPunt_dePasList())              //  recorre PUNTS DE PAS
//                    if (p.getNom().equals(punt)){
//                        p.setEstrellas(calculEstrelles(p, valoracio));
//                        p.setnValoracions( p.getnValoracions() + 1);  // aumenta opinions rebudes
//                        return true;
//                    }
//        }
        System.out.println(".......................... "+ id_ruta+ correu_persona+ "merda" + valoracio );
        Vot vot = new Vot(LocalDate.now(), id_ruta, correu_persona, "Estrelles", valoracio, 1,13);


        System.out.println("VOT REGISTRATTTTT... MIra la BBDD.....");
//        for (Vot v: listVots) {
//            v.setRuta(tripUB.findRuta((v.getIdRuta())));
//            Persona persona = modelFacade.findPersonaByCorreu(v.getCorreuPersona());
//            v.setPersona(persona);
//            v.setGrup(tripUB.findGrup(v.getIdGrup()));
//
//            PerfilPersona pp = persona.getPerfil();
//            pp.addVot(v);
//        }



        return true;


    }

//    private double calculEstrelles(Punt_dePas p, int valoracio){
//        double estr = p.getEstrellas();    // aconsegueix valoració actual del punt de pas
//        int opinions = p.getnValoracions();   // aconsegueix la quantitat de gent que ha fet valoracions
//
//        if (estr == 0)
//            return valoracio;
//        else
//            return (valoracio / opinions +1) + estr;
//    }
}
