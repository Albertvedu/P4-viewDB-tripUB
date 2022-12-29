package ub.edu.model;

import java.util.*;

public class ModelFacade {
    private static ModelFacade uniqueInstance;
    private TripUB tripUB;
    private ModelFacanaPuntPas modelFacanaPuntPas;

    public static ModelFacade getInstance() throws Exception {
        if (uniqueInstance == null) {
            synchronized (ModelFacade.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new ModelFacade();
                    uniqueInstance.tripUB = TripUB.getInstance();
                    uniqueInstance.modelFacanaPuntPas = ModelFacanaPuntPas.getInstance();
                }
            }
        }
        return uniqueInstance;
    }
    public StatusType validateRegistrePersona(String username, String password) {
        if (Seguretat.isMail((username)) && Seguretat.isPasswordSegur(password)) {
            Persona persona = findPersonaByCorreu(username);
            if (persona != null) {
                return StatusType.PERSONA_DUPLICADA; // Persona duplicada
            } else return StatusType.REGISTRE_VALID; // Registre valid
        } else return StatusType.FORMAT_INCORRECTE_CORREU_PWD; // Format incorrecte
    }

    public int loguejarPersona(String username, String password) {
        Persona persona = findPersonaByCorreu(username);
        if (persona == null) {
            return 1; // "Correu inexistent";
        }
        if (persona.getPwd().equals(password)) {
            return 0; // "Login correcte";
        } else {
            return 2; //"Contrassenya incorrecta";
        }
    }

    public String recuperarContrasenya(String username) {
        Persona persona = findPersonaByCorreu(username);
        if (persona == null) {
            return StatusType.CORREU_INEXISTENT.toString();
        }
        return persona.getPwd();
    }

    public StatusType  loguejarSociStatus(String correu, String password) {
        Persona persona = findPersonaByCorreu(correu);
        if (persona == null) {
            return StatusType.CORREU_INEXISTENT;
        }
        if (persona.getPwd().equals(password)) {
            return StatusType.LOGIN_CORRECTE;
        } else {
            return StatusType.CONTRASENYA_INCORRECTA;
        }
    }

    public List<HashMap<Object, Object>> getAllComarques() throws Exception {
        List<Comarca> l = tripUB.getAllComarques();
        List<HashMap<Object, Object>> comarquesDisponibles = new ArrayList<>();
        for (Comarca c : l) {
            HashMap<Object, Object> hashMap = new HashMap<>();
            hashMap.put("id", c.getId());
            hashMap.put("nom", c.getNom());
            comarquesDisponibles.add(hashMap);
        }
        return comarquesDisponibles;
    }


    public List<HashMap<Object, Object>> getAllTramsEtapaTrackByRutaId(Integer id_ruta) throws Exception {
        //Obtener todos los trams -> si se cogen directamente los TramEtapa y TtramTrack, dentro ya estarán los Trams respectivamente

        List<HashMap<Object, Object>> listaFinal = new ArrayList<>();

        // al ser una herencia, y tal como está montada la DB
        // dentro de la hija, la madre contiene tambien los datos respectivos a la madre/super
        // el id de la madre, es igual al id de la hija
        // TramEtapa y TramTrack son hijas de Tram
        // por tanto, tendrán el mismo id

        for (TramEtapa tramEtapa : getAllTramEtapasUB()) {
            HashMap<Object, Object> hashMap = new HashMap<>();
            if (Objects.equals(tramEtapa.getId_ruta(), id_ruta)) {
                String s = "Tram Etapa " + tramEtapa.getId().toString();
                hashMap.put("id", tramEtapa.getId());
                hashMap.put("value", 1); //el value es un flag que indica si es TramEtapa o TramTrack
                hashMap.put("text", s);
                listaFinal.add(hashMap);
            }
        }
        for (TramTrack tramTrack : getAllTramTrackUB()) {
            HashMap<Object, Object> hashMap = new HashMap<>();
            if (Objects.equals(tramTrack.getId_ruta(), id_ruta)) {
                String s = "Tram Track " + tramTrack.getId().toString();
                hashMap.put("id", tramTrack.getId());
                hashMap.put("value", 2); //el value es un flag que indica si es TramEtapa o TramTrack
                hashMap.put("text", s);
                listaFinal.add(hashMap);
            }
        }
        return listaFinal;
    }

    // TODO
    private List<TramEtapa> getAllTramEtapa() throws Exception {
        return getAllTramEtapasUB();
    }

    private List<TramTrack> getAllTramTrack() throws Exception {
        return getAllTramTrackUB();
    }

    public HashMap<Object, Object> getTramTrackById(Integer id) throws Exception {
        TramTrack tramTrack = getTramTrackByIdUB(id);
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("id", tramTrack.getId());
        hashMap.put("id_pdp_origen", tramTrack.getIdPuntDePasOrigen());
        hashMap.put("id_pdp_desti", tramTrack.getIdPuntDePasDesti());
        return hashMap;
    }
    public List<HashMap<Object,Object>> getAllGrupsPerNom() {
        List<HashMap<Object,Object>> grupsDisponibles = new ArrayList<>();
        //debido a que en el initGrups se inicializó y se guardo los grupos en this.grups
        //en lugar de volver a llamar a DB con el dataService.getAllGrups();
        //lo cogemos de la variable. De esta forma (si el proyecto fuera grande) ahorramos tiempo
        for (Grup g : tripUB.getAllGrups()) {
            HashMap<Object,Object> hashMap= new HashMap<>();
            hashMap.put("id",g.getId());
            hashMap.put("nom",g.getNom());
            grupsDisponibles.add(hashMap);
        }
        return grupsDisponibles;
    }

    public ArrayList<HashMap<Object,Object>> getAllGrupsPerPersona(String correu) {

        ArrayList<String> arrayNomGrupsByPersonaCorreu = tripUB.getXarxaPersones().getAllGrupsPerPersona(correu);
        ArrayList<HashMap<Object,Object>> listHashMap = new ArrayList<>();
        for (Grup g: tripUB.getAllGrups()) {
            if(arrayNomGrupsByPersonaCorreu.contains(g.getNom())){
                HashMap<Object,Object> hashMap = new HashMap<>();
                hashMap.put("id",g.getId());
                hashMap.put("nom",g.getNom());
                listHashMap.add(hashMap);
            }
        }
        return listHashMap;
    }

    public HashMap<Object, Object> getTramEtapaById(Integer id) throws Exception {
        TramEtapa tramEtapa = getTramEtapaByIdUB(id);
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("id", tramEtapa.getId());
        hashMap.put("id_etapa_origen", tramEtapa.getIdEtapaOrigen());
        hashMap.put("id_etapa_desti", tramEtapa.getIdEtapaDesti());
        return hashMap;
    }

    public List<HashMap<Object, Object>> getAllAllotjaments() throws Exception {
        List<Allotjament> allotjamentList = getAllAllotjamentsUB();
        List<HashMap<Object, Object>> listaFinal = new ArrayList<>();
        for (Allotjament allotjament : allotjamentList) {
            HashMap<Object, Object> hashMap = new HashMap<>();
            hashMap.put("id", allotjament.getId());
            hashMap.put("nom", allotjament.getNom());
            listaFinal.add(hashMap);
        }
        return listaFinal;
    }


    public List<TramEtapa> getAllTramEtapasUB() {
        List<TramEtapa> listTrams = new ArrayList<>();
        for (Ruta r: tripUB.getRutes()) {
            List<TramEtapa> le = r.getAllTramEtapas();
            for (TramEtapa te: le) {
                listTrams.add(te);
            }
        }
        return listTrams;
    }
    public List<TramTrack> getAllTramTrackUB() {
        List<TramTrack> listTrams = new ArrayList<>();
        for (Ruta r: tripUB.getRutes()) {
            List<TramTrack> le = r.getAllTramTracks();
            for (TramTrack te: le) {
                listTrams.add(te);
            }
        }
        return listTrams;
    }

    public TramTrack getTramTrackByIdUB(Integer id) {
        TramTrack tt = null;
        for (Ruta r: tripUB.getRutes()) {
            tt = r.findTramTrackById(id);
            if (tt!=null) return tt;
        }
        return tt;
    }


    public TramEtapa getTramEtapaByIdUB(Integer id) {
        TramEtapa tt = null;
        for (Ruta r: tripUB.getRutes()) {
            tt = r.findTramEtapaById(id);
            if (tt!=null) return tt;
        }
        return tt;
    }

    public Etapa getEtapaByIdUB(Integer id) {
        Etapa tt = null;
        for (Ruta r: tripUB.getRutes()) {
            tt = r.getEtapaById(id);
            if (tt!=null) return tt;
        }
        return tt;
    }

    public List<Allotjament> getAllAllotjamentsUB() {
        List<Allotjament> listAllotjaments = new ArrayList<>();
        for (Ruta r: tripUB.getRutes()) {
            List<Allotjament> le = r.getAllAllotjaments();
            for (Allotjament te: le) {
                listAllotjaments.add(te);
            }
        }
        return listAllotjaments;
    }

    public Allotjament findAllotjament(Integer idAllotjament) {
        Allotjament a = null;
        for (Ruta r: tripUB.getRutes()) {
            a = r.findAllotjament(idAllotjament);
            if (a != null) {
                return a;
            }
        }
        return a;
    }

    public void addPersonaToGrup(Integer grup, String correuPersona) {
        Grup g = tripUB.findGrup(grup);
        Persona p = findPersonaByCorreu(correuPersona);
        PerfilPersona pp = p.getPerfil();
        pp.addGrup(g);
        g.addPersona(p);
    }

    public Persona findPersonaByCorreu(String correu) {
        XarxaPersones xp = tripUB.getXarxaPersones();
        return(xp.find(correu));
    }


    public boolean setXarxaPersones(List<Persona> l) {
        XarxaPersones xarxa;
        if (l != null) {
            xarxa = new XarxaPersones(l);
            tripUB.setXarxaPersones(xarxa);
            return true;
        } else return false;
    }

    public Etapa findEtapa(Integer idEtapa) {
        Etapa e = null;
        for (Ruta r : tripUB.getRutes()) {
            e = r.findEtapa(idEtapa);
            if (e!= null) break;
        }
        return e;
    }

    public void addTram(Tram te) {
        Ruta r = tripUB.findRuta(te.getId_ruta());
        if (r!=null)
            r.addTram(te);
    }

    public void addTransportTram(Integer id_tram, Transport t) {

        for (Ruta r: tripUB.getRutes()) {
            if (r.findTram(id_tram)) {
                r.addTransportTram(id_tram, t);
                break;
            }
        }
    }
}
