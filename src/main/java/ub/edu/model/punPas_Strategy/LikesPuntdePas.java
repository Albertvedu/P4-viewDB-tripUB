package ub.edu.model.punPas_Strategy;

import ub.edu.controller.SessionMemory;
import ub.edu.model.Opinio;
import ub.edu.model.PuntDePas;
import ub.edu.resources.RessourceFacadePuntdePas;


import java.util.*;
import java.util.stream.Collectors;

public class LikesPuntdePas implements iLlistar {

    private RessourceFacadePuntdePas ressourceFacadePuntdePas;
    private SessionMemory sessionMemory;
    private ArrayList observers;

    public LikesPuntdePas() {
        this.ressourceFacadePuntdePas = RessourceFacadePuntdePas.getInstance();
        this.sessionMemory = SessionMemory.getInstance();
        this.observers = new ArrayList<>();
    }


    @Override
    public Map<String, Integer> llistar(String criteri) throws Exception {
        final int TOP = 5;
        List<Opinio> listaOpinions =  sessionMemory.getListOpinions();
        List<PuntDePas> puntPasList = ressourceFacadePuntdePas.getAllPuntdePas();
        int idGrup = sessionMemory.getIdGrup();


        String  puntPasName;
        int [] contarLikes = new int[listaOpinions.size()];
        Map<String , Integer> topLike = new HashMap<>();

        // Recopila Like de listaOpinions
        for (Opinio op: listaOpinions){
            if (op.getValorOpinio().equals(criteri)) {  // Criteri = Like o Unlike
                contarLikes[op.getIdPuntDePas()] += 1;
            }
        }
        // Aconsegueix Nom de Punt de Pas per ID i ho guarda a Map<Nom Punt Pas i Likes>
        for (int posId = 0; posId < puntPasList.size(); posId++) {
            if(contarLikes[posId] != 0) {
                puntPasName = ressourceFacadePuntdePas.getPuntdePasById(posId).getHighlight();
                topLike.put(puntPasName,  contarLikes[posId]);
            }
        }
        // Ordena de major a menor el Likes
        Map<String, Integer> sortTopLike = topLike.entrySet()
                .stream()
                .sorted((Comparator<? super Map.Entry<String, Integer>>) Map.Entry.<String, Integer>comparingByValue().reversed())
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
