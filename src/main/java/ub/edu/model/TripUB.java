package ub.edu.model;

import ub.edu.resources.dao.Parell;
import ub.edu.view.ObserverView;

import java.sql.SQLOutput;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TripUB implements SubjectModel{

    private volatile static TripUB uniqueInstance;
    private Integer id;
    private XarxaPersones xarxaPersones;   // tripub
    private Map<Integer, Ruta> rutaMap; // tripub
    private Map<Integer, Comarca> comarcaMap; // tripub
    private Map<Integer, Grup> grups;
    private Map<Integer, Localitat> localitatMap;
    private List<Parell<Integer, Integer>>  relacioComarcaRuta;
    private ArrayList observers;


    public static TripUB getInstance(){
        if (uniqueInstance == null) {
            synchronized (TripUB.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new TripUB();
                    uniqueInstance.grups = new HashMap<>();
                    uniqueInstance.comarcaMap = new HashMap<>();
                    uniqueInstance.rutaMap = new HashMap<>();
                    uniqueInstance.localitatMap = new HashMap<>();
                    uniqueInstance.relacioComarcaRuta = new ArrayList<>();
                    uniqueInstance.observers = new ArrayList<>();
                }
            }
        }
        return uniqueInstance;
    }

    public boolean setGrups(List<Grup> lg) {
        this.grups =  (lg.stream()
                .collect(Collectors.toMap(Grup::getId, Function.identity())));
        return (grups!= null);
    }

    public void setXarxaPersones(XarxaPersones x) {
        this.xarxaPersones = x;
    }

    public boolean setRutes(List<Ruta> listRutas) {
        this.rutaMap =  (listRutas.stream()
                .collect(Collectors.toMap(Ruta::getId, Function.identity())));
        return (this.rutaMap != null);
    }


    public boolean  setComarques(List<Comarca> listComarques) {
        comarcaMap =  (listComarques.stream()
                .collect(Collectors.toMap(Comarca::getId, Function.identity())));
        return (comarcaMap != null) ;
    }
    public Integer getId() {
        return id;
    }

    public void setComarquesToRutes(List<Parell<Integer, Integer>> relacionsRC) {
        for (Parell p : relacionsRC) {
            Ruta r = rutaMap.get(p.getElement2());
            Comarca c = comarcaMap.get(p.getElement1());
            r.addComarca(c);
        }
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Comarca findComarca(Integer idComarca) {
        Comarca c = null;
        c = comarcaMap.get(idComarca);
        return c;
    }

    public Grup findGrup(Integer idGrup) {
        return grups.get(idGrup);
    }

    public Ruta findRuta(Integer idRuta) {
        return rutaMap.get(idRuta);
    }

    public List<Comarca> getAllComarques() {
        //no se puede hacer cast directo entre map->hasmap a una collection->list. Se debe instanciar una nueva arraylist
        return new ArrayList<Comarca>(this.comarcaMap.values());
    }

    public void addGrup(Grup grup) {
        grups.put(grup.getId(), grup);
    }


    public List<Ruta> getRutes() {
        //no se puede hacer cast directo entre map->hasmap a una collection->list. Se debe instanciar una nueva arraylist
        return new ArrayList<Ruta>(this.rutaMap.values());
    }
    public boolean setLocalitats(List<Localitat> listLocalitats) {
        this.localitatMap =  (listLocalitats.stream()
                .collect(Collectors.toMap(Localitat::getId, Function.identity())));
        return (this.localitatMap != null);
    }
    public boolean setLocalitatsRutes(List<Parell<Integer, Integer>> relacionsRC){
        relacioComarcaRuta = relacionsRC;
        return this.relacioComarcaRuta != null;
    }
    public Map<Integer, Localitat> getLocalitats(){
        return this.localitatMap;
    }

    public void getRutesByLocalitat(String name){
        int idComarca= 0;
        int idRuta = 0;
        List<Ruta> listRutes = new ArrayList<>();

        for (Localitat l: localitatMap.values()) {
            if(l.getNom().equals(name))
                idComarca = l.getIdComarca();
        }

        for (Parell<Integer, Integer> p: relacioComarcaRuta)
            if (p.getElement1() == idComarca)
                idRuta = p.getElement2();

        for (Ruta r: getRutes())
            if (r.getId() == idRuta)
                listRutes.add(r);


        List<HashMap<Object, Object>> rutesDisponibles = new ArrayList<>();
        for (Ruta r : listRutes) {
            HashMap<Object, Object> atributRuta = new HashMap<>();
            Integer id = r.getId();
            String nom = r.getNom();
            atributRuta.put("id", id);
            atributRuta.put("nom", nom);

            rutesDisponibles.add(atributRuta);
        }
        rutaPaneChanged( rutesDisponibles);
    }


    public XarxaPersones getXarxaPersones() {
        return xarxaPersones;
    }

    public List<Grup> getAllGrups() {
        //no se puede hacer cast directo entre map->hasmap a una collection->list. Se debe instanciar una nueva arraylist
        return new ArrayList<Grup>(this.grups.values());
    }

    public Integer getIdGrupByName(String newValue) {
        List<Grup> list = this.getAllGrups();
        for (Grup g: list) {
            if(g.getNom().equals(newValue))
                return g.getId();
        }
        return null;
    }

    public List<Ruta> findRutaByOrigen(Integer id) {
        List<Ruta> rutes = new ArrayList<>();
        for (Ruta r: rutaMap.values()) {
            if (r.getIdLlocOrigen()==id) rutes.add(r);
        }
        return rutes;
    }

    public List<Ruta> findRutaByDesti(Integer id) {
        List<Ruta> rutes = new ArrayList<>();
        for (Ruta r: rutaMap.values()) {
            if (r.getIdLlocDesti()==id) rutes.add(r);
        }
        return rutes;
    }

    @Override
    public void registerObserver(ObserverView o) {
        this.observers.add(o);
        System.out.println("Observador TripUB registrat: " + o.toString());

    }

    @Override
    public void removeObserver(ObserverView o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers(List<HashMap<Object, Object>> listRutes ) {
        for (int i = 0; i < observers.size(); i++) {
            ObserverView observer = (ObserverView) observers.get(i);
            observer.update(listRutes);
        }
    }
    public void rutaPaneChanged(List<HashMap<Object, Object>> listRutes) {

        notifyObservers(listRutes);
    }

}

