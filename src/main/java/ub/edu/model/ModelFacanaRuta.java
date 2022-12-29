package ub.edu.model;

import ub.edu.model.punPas_Strategy.PuntPasStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModelFacanaRuta {
    private volatile static ModelFacanaRuta uniqueInstance;
    private PuntPasStrategy puntPasStrategy;
    private TripUB tripUB;

    public static ModelFacanaRuta getInstance() throws Exception {
        if (uniqueInstance == null) {
            synchronized (ModelFacanaRuta.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new ModelFacanaRuta();
                    uniqueInstance.puntPasStrategy = PuntPasStrategy.getInstance();
                    uniqueInstance.tripUB = TripUB.getInstance();
                }
            }
        }
        return uniqueInstance;
    }
    public void valorarRuta() throws Exception {
        puntPasStrategy.valorarRuta();
    }
    public void valorarRutaEstrelles() throws Exception {
        puntPasStrategy.valorarRutaEstrelles();
    }
    public HashMap<Object, Object> getRutaById(Integer id_ruta) throws Exception {
        Ruta r = tripUB.findRuta(id_ruta);
        HashMap<Object, Object> hashMap = new HashMap<>();
        if (r != null) {
            hashMap.put("id", r.getId());
            hashMap.put("nom", r.getNom());
            hashMap.put("dataCreacio", r.getDataCreacio());
            hashMap.put("durada", r.getDurada());
            hashMap.put("descripcio", r.getDescripcio());
            hashMap.put("cost", r.getCost());
            hashMap.put("dificultat", r.getDificultat());
            hashMap.put("tipusRuta", r.getTipusRuta());
            hashMap.put("id_lloc_origen", r.getIdLlocOrigen());
            hashMap.put("id_lloc_desti", r.getIdLlocDesti());
        }
        return hashMap;
    }
    public  void getAllRutesPerLocalitat(String name){
        this.tripUB.getRutesByLocalitat(name);
    }
    public List<HashMap<Object,Object>> getAllRutesPerNom() {
        List<HashMap<Object, Object>> rutesDisponibles = new ArrayList<>();
        for (Ruta r : tripUB.getRutes()) {
            HashMap<Object, Object> atributRuta = new HashMap<>();
            Integer id = r.getId();
            String nom = r.getNom();
            atributRuta.put("id", id);
            atributRuta.put("nom", nom);

            rutesDisponibles.add(atributRuta);
        }
        return rutesDisponibles;
    }
}
