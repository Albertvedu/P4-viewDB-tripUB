package ub.edu.model.punPas_Strategy;

import ub.edu.controller.SessionMemory;
import ub.edu.model.Opinio;
import ub.edu.model.PuntDePas;
import ub.edu.resources.RessourceFacadePuntdePas;

import java.util.*;
import java.util.stream.Collectors;

public class LlistarEstrellesPuntdePas implements iLlistar{
    private RessourceFacadePuntdePas ressourceFacadePuntdePas;
    private SessionMemory sessionMemory;

    public LlistarEstrellesPuntdePas() {
        this.ressourceFacadePuntdePas = RessourceFacadePuntdePas.getInstance();
        this.sessionMemory = SessionMemory.getInstance();
    }

    @Override
    public Map<String, Integer> llistar(String criteri) throws Exception {
        final int TOP = 10;
        List<Opinio> listOpinions = sessionMemory.getListOpinions();
        List<PuntDePas> puntPasList = ressourceFacadePuntdePas.getAllPuntdePas();
        Map<String, Integer> contarEstrelles = new HashMap<>();
        int [] contadorVots = new int[puntPasList.size()+1];
        String nomPuntPas;

        /// CONTAR VOTS LIKE o UNLIKE
        for (Opinio opinio : listOpinions) {
            if (opinio.getTipusOpinio().equals(criteri)) {
                contadorVots[opinio.getIdPuntDePas()] += 1;
                nomPuntPas = ressourceFacadePuntdePas.getPuntdePasById(opinio.getIdPuntDePas()).getNom();
                if (contarEstrelles.containsKey(nomPuntPas)) {
                    double valorAnterior = Double.parseDouble(String.valueOf(contarEstrelles.get(nomPuntPas)));
                    contarEstrelles.put(nomPuntPas, (int) ((Double.parseDouble(opinio.getValorOpinio()) + valorAnterior) / contadorVots[opinio.getIdPuntDePas()]));
                } else
                    contarEstrelles.put(nomPuntPas, (int) Double.parseDouble(opinio.getValorOpinio()));
            }
        }

        // Ordenar el Map de manera inversa
        Map<String, Integer> sortTopLike = contarEstrelles.entrySet()
                .stream()
                .sorted((Comparator<? super Map.Entry<String, Integer>>) Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        // Deixa les primeras 10 valoracions, ho demés ho esborra
        int contar = 0;
        for (Map.Entry<String, Integer> map: sortTopLike.entrySet()){
            contar++;
            if (contar > TOP)
                sortTopLike.remove(map.getKey());
        }
        return sortTopLike;
    }

}
