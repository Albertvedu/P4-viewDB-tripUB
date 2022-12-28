package ub.edu.model.punPas_Strategy;

import ub.edu.model.*;
import ub.edu.resources.RessourceFacadePuntdePas;
import ub.edu.view.ObserverView;
import ub.edu.view.ObserverViewPuntPas;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class PuntPasStrategy implements SubjectModelPuntPas {
    private volatile static PuntPasStrategy uniqueInstance;
    private PerLikes perLikes;
    private PerEstrelles perEstrelles;
    private PerDesLike perDesLike;
    private Map<String, Ruta> rutaMap;
    private RessourceFacadePuntdePas ressourceFacadePuntdePas;
    private ArrayList observers;

//    public PuntPasStrategy() throws Exception {
//        this.perLikes = new PerLikes();
//        this.perEstrelles = new PerEstrelles();
//        this.perDesLike = new PerDesLike();
//        this.ressourceFacadePuntdePas = RessourceFacadePuntdePas.getInstance();
//        this.observers = new ArrayList<>();
//      // perDesLike.performaValorar();
//    }
    public static PuntPasStrategy getInstance() throws Exception {
        if (uniqueInstance == null) {
            synchronized (PuntPasStrategy.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new PuntPasStrategy();
                    uniqueInstance.perLikes = new PerLikes();
                    uniqueInstance.perEstrelles = new PerEstrelles();
                    uniqueInstance.perDesLike = new PerDesLike();
                    uniqueInstance.ressourceFacadePuntdePas = RessourceFacadePuntdePas.getInstance();
                    uniqueInstance.observers = new ArrayList<>();
                }
            }
        }
        return uniqueInstance;
    }

//    public boolean  perLikes(String punt, int valoracio){
//        perLikes.setValorarPuntPas(new ValorarLikes());
//        return perLikes.performaValorar( punt, valoracio);
//    }
    public void getTopEstrellesRutes(String text) throws Exception {
        List<Vot> listVots = ressourceFacadePuntdePas.getAllVots();
        List<Ruta> listRutes = ressourceFacadePuntdePas.getAllRutes();
        Map<String , Integer> topLike = new HashMap<>();

        for (Vot vot: listVots){
            for(Ruta ruta: listRutes)
                if (vot.getIdRuta() == ruta.getId())
                    if (vot.getTipusVot().equals("Estrelles"))
                        topLike.put(ruta.getNom(), Integer.parseInt(vot.getValorVot()));
        }
        Map<String, Integer> sortTopLike = topLike.entrySet()
                .stream()
                .sorted((Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        top10_PaneChanged(sortTopLike, text);
    }
    public void getTop_5Likes(String criteri) throws Exception {
        int top = 10;
        List<Opinio> listaOpinions =  ressourceFacadePuntdePas.getAllOpinions();
        List<PuntDePas> puntPasList = ressourceFacadePuntdePas.getAllPuntdePas();

        List<Vot> listVots = ressourceFacadePuntdePas.getAllVots();
        String  puntPasName;
        int [] contarLikes = new int[listaOpinions.size()];
        int [] contarUnlike = new int[listaOpinions.size()];
        Map<String , Integer> topLike = new HashMap<>();

        // Recopila Like de listaOpinions
        for (Opinio op: listaOpinions){
            if (op.getValorOpinio().equals(criteri)) {
                contarLikes[op.getIdPuntDePas()] += 1;
            }
//            if (op.getValorOpinio().equals("Unlike")) {
//                contarUnlike[op.getIdPuntDePas()] += 1;
//            }
        }
        // Aconsegueix Nom de Punt de Pas per ID i ho guarda a Map<Nom Punt Pas i Likes>
        for (int posId = 0; posId < puntPasList.size(); posId++) {
            if(contarLikes[posId] != 0) {
                puntPasName = ressourceFacadePuntdePas.getPuntdePasById(posId).getHighlight();
                topLike.put(puntPasName, contarLikes[posId]);
            }
        }
        // Ordena de major a menor el Likes
        Map<String, Integer> sortTopLike = topLike.entrySet()
                .stream()
                .sorted((Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        // Deixa les primeras 10 valoracions, ho demés ho esborra
        for (int i = top; i < sortTopLike.size(); i++)
            sortTopLike.remove(i);

        top10_PaneChanged(sortTopLike, criteri);
    }

    public boolean perEstrelles(int idPuntPas , String valoracio, String correu_persona, Integer id_ruta) throws Exception {
        perEstrelles.setValorarPuntPas(new ValorarEstrelles());

        return perEstrelles.performaValorar( idPuntPas, valoracio, correu_persona, id_ruta);
    }

    @Override
    public void registerObserver(ObserverView o) {
        this.observers.add(o);
        System.out.println("Observador PuntPasStrategy registrat: " + o.toString());

    }

    @Override
    public void removeObserver(ObserverView o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers(Map<String, Integer> sortTopLike, String text ) {
        for (int i = 0; i < observers.size(); i++) {
            ObserverViewPuntPas observer = (ObserverViewPuntPas) observers.get(i);
            observer.update(sortTopLike, text);
        }
    }
    public void top10_PaneChanged(Map<String, Integer> sortTopLike, String text) {

        notifyObservers(sortTopLike, text);
    }
//    public boolean perDesLikes(String punt, int valoracio){
//        perDesLike.setValorarPuntPas(new ValorarDeslike());
//        return perDesLike.performaValorar( rutaMap, punt, valoracio);
//    }
//    public List<Punt_dePas> llistarAll(String rutaNom){
//        perLikes.setLlistarPuntPas(new LlistarLikes());
//        return perLikes.performaLlistarAll(rutaMap, rutaNom);
//    }
//    public List<Punt_dePas> llistarTopEstrelles(String rutaNom){
//        perEstrelles.setLlistarPuntPas(new LlistarEstrelles());
//        return perEstrelles.performaLlistar(rutaMap, rutaNom);
//    }
//    public List<Punt_dePas> llistarTopLikes(String rutaNom){
//        perLikes.setLlistarPuntPas(new LlistarLikes());
//        return perLikes.performaLlistar( rutaMap, rutaNom);
//    }
//    public List<Punt_dePas> llistarTopDesLikes(String rutaNom){
//        perLikes.setLlistarPuntPas(new LlistarDesLikes());
//        return perLikes.performaLlistar( rutaMap, rutaNom);
//    }
}
