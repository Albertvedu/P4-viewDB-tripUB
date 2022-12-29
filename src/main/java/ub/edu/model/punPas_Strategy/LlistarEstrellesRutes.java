package ub.edu.model.punPas_Strategy;



import ub.edu.controller.SessionMemory;
import ub.edu.model.Ruta;
import ub.edu.model.Vot;

import ub.edu.resources.RessourceFacadeRuta;

import java.util.*;
import java.util.stream.Collectors;

public class LlistarEstrellesRutes implements iLlistar {
    private SessionMemory sessionMemory;
    private RessourceFacadeRuta ressourceFacadeRuta;

    public LlistarEstrellesRutes() throws Exception {
        this.sessionMemory = SessionMemory.getInstance();
        this.ressourceFacadeRuta = RessourceFacadeRuta.getInstance();
    }

    @Override
    public Map<String, Integer> llistar( String criteri) throws Exception {
        final int TOP = 10;
        List<Vot> listVots = sessionMemory.getLlistaVots();
        int idGrup = sessionMemory.getIdGrup();
        List<Ruta> listRutes = ressourceFacadeRuta.getAllRutes();
        int [] contadorVots = new int[listRutes.size()+1];
        Map<String , Integer> topLike = new HashMap<>();
        Map<Integer, Integer> contarEstrelles = new HashMap<>();

        /// CONTAR VALORACION ESTRELLES
        for (Vot vot : listVots) {
            if (vot.getIdGrup() == idGrup || idGrup == 0) { // idGrup == zero, vol dir que és Punt de pas i no té grup
                if (vot.getTipusVot().equals(criteri)) {
                    if ( !vot.getValorVot().equals("0")) {
                        contadorVots[vot.getIdRuta()] += 1;
                        if (contarEstrelles.containsKey(vot.getIdRuta())) {
                            int valorAnterior = contarEstrelles.get(vot.getIdRuta());
                            contarEstrelles.put(vot.getIdRuta(), (int) ((Double.parseDouble(vot.getValorVot()) + valorAnterior) / contadorVots[vot.getIdRuta()]));
                        } else
                            contarEstrelles.put(vot.getIdRuta(), Integer.parseInt(vot.getValorVot()));
                    }
                }
            }
        }

        //  Ompli Map amb el Nom de la Ruta
        Iterator<Integer> it = contarEstrelles.keySet().iterator();

        while(it.hasNext()){
            Integer clave = it.next();
            int valor = contarEstrelles.get(clave);
            if (valor != 0)
                topLike.put(ressourceFacadeRuta.getRutaByID(clave).getNom(), valor);
        }
        // Ordenar el Map de manera inversa
        Map<String, Integer> sortTopLike = topLike.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        // Deixa les primeras 5 valoracions, ho demés ho esborra
        int contar = 0;
        for (Map.Entry<String, Integer> map: sortTopLike.entrySet()){
            contar++;
            if (contar > TOP)
                sortTopLike.remove(map.getKey());
        }
        return sortTopLike;
    }

}
