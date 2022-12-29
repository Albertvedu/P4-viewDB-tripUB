package ub.edu.model.punPas_Strategy;

import ub.edu.controller.SessionMemory;
import ub.edu.model.Vot;
import ub.edu.resources.ResourcesFacade;
import ub.edu.resources.RessourceFacadePuntdePas;
import ub.edu.resources.RessourceFacadeRuta;
import ub.edu.resources.dao.Quartet;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LikesRutes implements iLlistar {
    private RessourceFacadeRuta ressourceFacadeRuta;
    private SessionMemory sessionMemory;

    public LikesRutes() throws Exception {
        this.ressourceFacadeRuta = RessourceFacadeRuta.getInstance();
        this.sessionMemory = SessionMemory.getInstance();
    }

    @Override
    public Map<String, Integer> llistar(String criteri) throws Exception {
        String tipusVot;
        final int TOP = 5;
        String key;

        Map<String , Integer> topLike = new HashMap<>();
        List<Vot> listVots = sessionMemory.getLlistaVots();
        List<Quartet<Integer, String, Integer, Boolean>> listaAllTipus = ressourceFacadeRuta.getAllTipusVots();
        int [] contarLikes = new int[listVots.size()];
        int [] contarUnlike = new int[listVots.size()];
        int idGrup = sessionMemory.getIdGrup();

        /// CONTAR VOTS LIKE o UNLIKE
        for (Vot vot : listVots) {
            if (vot.getIdGrup() == idGrup || idGrup == 0) { // idGrup == zero, vol dir que és Punt de pas i no té grup

                tipusVot = cercaDicotomica(vot, listaAllTipus, 0, listaAllTipus.size() - 1);
                if (!tipusVot.equals("null")) {
                    if (tipusVot.equals("true")) {   // Like o Unlike
                        //al IDRUTA sumarli +1
                        contarLikes[vot.getIdRuta()] += 1;
                    } else
                        contarUnlike[vot.getIdRuta()] += 1;
                }

            }
        }

        // Omplir el Map que s'ha de retornar
        if ( criteri.equals("Like"))
            for (int i = 1; i < contarLikes.length; i++) {
                if (contarLikes[i] != 0) {
                    key = ressourceFacadeRuta.getRutaByID(i).getNom();
                    topLike.put(key, contarLikes[i]);
                }
            }
        else{
            for (int i = 1; i < contarUnlike.length; i++) {
                if (contarUnlike[i] != 0) {
                    key = ressourceFacadeRuta.getRutaByID(i).getNom();
                    topLike.put(key, contarUnlike[i]);

                }
            }
        }
        // Ordena de major a menor el Likes o Unlikes
        Map<String, Integer> sortTopLike = topLike.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        // Deixa les primeras 5 valoracions, ho demés ho esborra
        for (int i = TOP; i < sortTopLike.size(); i++)
            sortTopLike.remove(i);

        return sortTopLike;
    }

    private String  cercaDicotomica(Vot vot, List<Quartet<Integer, String, Integer, Boolean>> listaAllTipus, int esq, int dreta){

        boolean trobat = false;
        int mitat = (esq + dreta)/2;

        while (dreta >= esq && !trobat) {
            if (listaAllTipus.get(mitat).getElement1() < vot.getIdTipusVot())
                return cercaDicotomica(vot,  listaAllTipus, mitat+1, dreta );
            if (listaAllTipus.get(mitat).getElement1() > vot.getIdTipusVot())
                return cercaDicotomica(vot,  listaAllTipus, esq, mitat-1);

            if (listaAllTipus.get(mitat).getElement4() != null) {
                System.out.println("vot Id  vot: " + vot.getId() + " id tpus: " + vot.getIdTipusVot() + " " + listaAllTipus.get(mitat).getElement4());
                return listaAllTipus.get(mitat).getElement4().toString();
            }
            trobat = true;
        }
        return "null";
    }
}
