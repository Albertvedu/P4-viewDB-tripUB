package ub.edu.model;


import java.util.*;

public class ModelFacanaLocalitat {
    private volatile static ModelFacanaLocalitat uniqueInstance;
    private ModelFacanaPuntPas modelFacanaPuntPas;
    private ModelFacade modelFacade;
    private TripUB tripUB;


    public static ModelFacanaLocalitat getInstance() throws Exception {
        if (uniqueInstance == null) {
            synchronized (ModelFacanaLocalitat.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new ModelFacanaLocalitat();
                    uniqueInstance.tripUB = TripUB.getInstance();
                    uniqueInstance.modelFacanaPuntPas = ModelFacanaPuntPas.getInstance();
                    uniqueInstance.modelFacade = ModelFacade.getInstance();
                }
            }
        }
        return uniqueInstance;
    }
    public List<HashMap<Object, Object>> getAllLocalitats() throws Exception {
        List<Localitat> locs = getAllLocalitatsUB();
        List<HashMap<Object, Object>> localitats = new ArrayList<>();
        for (Localitat l : locs) {
            HashMap<Object, Object> hashMap = new HashMap<>();
            hashMap.put("id", l.getId());
            hashMap.put("nom", l.getNom());
            hashMap.put("altitud", l.getAltitud());
            hashMap.put("latitud", l.getLatitud());
            hashMap.put("longitud", l.getLongitud());
            hashMap.put("id_comarca", l.getIdComarca());
            localitats.add(hashMap);
        }
        return localitats;
    }

    public List<Localitat> getAllLocalitatsUB() {
        List<Localitat> llistaTotal = new ArrayList<>();
        for (Comarca c: tripUB.getAllComarques()) {
            List<Localitat>  l;
            l = c.getAllLocalitats();
            for (Localitat local: l) {
                llistaTotal.add(local);
            }
        }
        //la lista resultante no esta ordenada, mejor ordenarla ahora,
        //porque despeus en la vista si coges por id y esta desordenada, petará
        Collections.sort(llistaTotal, new Comparator<Localitat>() {
            @Override
            public int compare(Localitat l1, Localitat l2) {
                // Aqui esta el truco, ahora comparamos p2 con p1 y no al reves como antes
                return l1.getId().compareTo(l2.getId());
            }
        });
        return llistaTotal;
    }
    public HashMap<Object, Object> getLocalitat_y_EtapaByID(Integer id) throws Exception {
        Etapa etapa = modelFacade.getEtapaByIdUB(id);
        Localitat localitat = getLocalitatByIdUB(etapa.getId());
        // Por seguridad y comprension lo hacemos en 2 pasos, pero realmente, nuestra DB permite hacer Localitat localitat = dataService.getLocalitatById(id).get();
        //al ser herencia recordar que id_localitat == id_etapa
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("id", etapa.getId());
        hashMap.put("nom", localitat.getNom());
        return hashMap;
    }
    public HashMap<Object, Object> getLocalitatByID(Integer id) throws Exception {
        Localitat loc = getLocalitatByIdUB(id);
        HashMap hashMap = new HashMap();
        if (loc != null) {
            hashMap.put("id", loc.getId());
            hashMap.put("nom", loc.getNom());
            hashMap.put("altitud", loc.getAltitud());
            hashMap.put("latitud", loc.getLatitud());
            hashMap.put("longitud", loc.getLongitud());
            hashMap.put("id_comarca", loc.getIdComarca());
        }
        return hashMap;
    }

    public Localitat getLocalitatByIdUB(Integer id) {
        Localitat tt = null;
        for (Comarca c: tripUB.getAllComarques()) {
            tt = c.getLocalitat(id);
            if (tt!=null) return tt;
        }
        return tt;
    }

    public HashMap<Object, Object> getLocalitat_y_PuntDePasById(Integer id) throws Exception {
        PuntDePas puntDePas = modelFacanaPuntPas.getPuntDePasByIdUB(id);
        Localitat localitat = getLocalitatByIdUB(puntDePas.getId());
        //al ser herencia recordar que id_localitat == id_puntDePas
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("id", puntDePas.getId());
        hashMap.put("nom", localitat.getNom());
        hashMap.put("highlight", puntDePas.getHighlight());
        return hashMap;
    }
}
