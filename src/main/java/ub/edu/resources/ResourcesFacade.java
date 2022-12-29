package ub.edu.resources;

import ub.edu.controller.SessionMemory;
import ub.edu.model.StatusType;
import ub.edu.model.Seguretat;
import ub.edu.model.*;
import ub.edu.resources.dao.Parell;
import ub.edu.resources.service.AbstractFactoryData;
import ub.edu.resources.service.DataService;

import java.time.LocalDate;
import java.util.List;

import ub.edu.resources.service.FactoryDB;

public class ResourcesFacade {

    private volatile static ResourcesFacade uniqueInstance;
    private AbstractFactoryData factory;      // Origen de les dades
    private DataService dataService;         // Connexio amb les dades
    private TripUB tripUB;                  // Model a tractar
    private ModelFacade modelFacade;
    private ModelFacanaPuntPas modelFacanaPuntPas;
    private SessionMemory sessionMemory;


    public static ResourcesFacade getInstance() throws Exception {
        if (uniqueInstance == null) {
            synchronized (ResourcesFacade.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new ResourcesFacade();
                    uniqueInstance.tripUB = TripUB.getInstance();
                    uniqueInstance.modelFacade = ModelFacade.getInstance();
                    uniqueInstance.factory = new FactoryDB();
                    uniqueInstance.dataService = new DataService(uniqueInstance.factory);
                    uniqueInstance.modelFacanaPuntPas = ModelFacanaPuntPas.getInstance();
                    uniqueInstance.sessionMemory = SessionMemory.getInstance();
                }
            }
        }
        return uniqueInstance;
    }

    public void populateTripUB() {
        try {
            initXarxaPersones(); //success
            initGrups(); //success
            relacioPersonesGrups(); //success

            initRutes(); //success
            initComarques(); //success
            relacionarRutesComarques(); //success
            initLocalitats();  //success // Pressuposa que les comarques ja han estat creades

            initTramsRuta();  //success // Pressuposa que les rutes ja estan creades.
            initAllotjaments(); //success // Pressuposa que les etapes ja han estat creades

            initPerfilsPersones(); //success
            getRelacioRutesComarques();   // iniciar atribut Relacio rutes comarques
            initListOpinions();   // iniciar Opinions... Chapuza, per que no toquem BBDD, com diu l'exercici
        } catch (Exception e) {
            System.out.println("Exception: --> " + e.getMessage());
        }
    }

    private void relacioPersonesGrups() throws Exception {
        // Han estat creats els grups i les persones previament
        // Ara cal lligar les persones i els grups
        List<Parell<String, Integer>> relacionsPG = dataService.getAllGrupFormatPersones();

        for (Parell<String, Integer> parell: relacionsPG) {
            Persona persona;
            persona = modelFacade.findPersonaByCorreu(parell.getElement1());
            Grup grup;
            grup = tripUB.findGrup(parell.getElement2());
            grup.addPersona(persona);

            PerfilPersona pp = persona.getPerfil();
            pp.addGrup(grup);
        }

    }
    public void initListOpinions() throws Exception {
        Opinio op1 = new Opinio(11,"Estrelles", "4", "dtomacal@yahoo.cat");
        Opinio op2 = new Opinio(11,"Estrelles", "3", "dtomacal@yahoo.cat");
        Opinio op3 = new Opinio(12,"Estrelles", "2", "dtomacal@yahoo.cat");
        Opinio op4 = new Opinio(11,"Estrelles", "1", "dtomacal@yahoo.cat");
        Opinio op5 = new Opinio(10,"Estrelles", "2", "dtomacal@yahoo.cat");
        Opinio op6 = new Opinio(14,"Estrelles", "3", "dtomacal@yahoo.cat");


        List<Opinio> ll = dataService.getAllOpinions();
        ll.add(op1);
        ll.add(op2);
        ll.add(op3);
        ll.add(op4);
        ll.add(op5);
        ll.add(op6);
        sessionMemory.setListOpinions(ll); // Chapuza per no fer servir BBDD, com diu l'exercici
    }
    private void initPerfilsPersones() throws Exception {
        // Es considera que totes les classes amb les que estan relacionades
        // vots, opinio i reserves ja han estat creades
        initVots();
        initOpinio();
        initReserves();
    }

    private void initOpinio() throws Exception  {
        List<Opinio> llista = dataService.getAllOpinions();
        sessionMemory.setListOpinions(llista);  // Iniciar llista Opinions.. per no usar BBDD
        for (Opinio o: llista) {
            o.setPuntDePas(modelFacanaPuntPas.findPuntDePas(o.getIdPuntDePas()));
            Persona persona = modelFacade.findPersonaByCorreu(o.getCorreuPersona());
            o.setPersona(persona);

            PerfilPersona pp = persona.getPerfil();
            pp.addOpinio(o);
        }
    }

    private void initVots() throws Exception  {
        List<Vot> llista = getAllVots();


        // Afegir dades a la llista
        Vot vot1 = new Vot(llista.size() + 7, LocalDate.now(), 1, "CapitaCC@gmail.com", "Like", "Likes", 3, 6);
        Vot vot2 = new Vot(llista.size() + 8, LocalDate.now(), 1, "CapitaCC@gmail.com", "Like", "Unlike", 3, 10);
        Vot vot3 = new Vot(llista.size() + 9, LocalDate.now(), 1, "CapitaCC@gmail.com", "Estrelles", "2", 3, 6);
        Vot vot4 = new Vot(llista.size() + 10, LocalDate.now(), 1, "CapitaCC@gmail.com", "Like", "Likes", 3, 6);
        Vot vot5 = new Vot(llista.size() + 11, LocalDate.now(), 1, "CapitaCC@gmail.com", "Like", "Likes", 3, 6);
        Vot vot6 = new Vot(llista.size() + 12, LocalDate.now(), 1, "CapitaCC@gmail.com", "Like", "Likes", 3, 6);

        llista.add(vot1);
        llista.add(vot2);
        llista.add(vot3);
        llista.add(vot4);
        llista.add(vot5);
        llista.add(vot6);
        sessionMemory.setLlistaVots(llista);   // INICIA llista vots a sessionMemory, per no Usar BBDD
        for (Vot v: llista) {
            v.setRuta(tripUB.findRuta((v.getIdRuta())));
            Persona persona = modelFacade.findPersonaByCorreu(v.getCorreuPersona());
            v.setPersona(persona);
            v.setGrup(tripUB.findGrup(v.getIdGrup()));

            PerfilPersona pp = persona.getPerfil();
            pp.addVot(v);
        }
    }

    private void initReserves() throws Exception  {
        List<Reserva> llista = dataService.getAllReservas();
        for (Reserva r: llista) {
            r.setAllotjament(modelFacade.findAllotjament(r.getIdAllotjament()));
            Persona persona = modelFacade.findPersonaByCorreu(r.getCorreuPersona());
            r.setPersona(persona);

            PerfilPersona pp = persona.getPerfil();
            pp.addReserva(r);
        }
    }
    private boolean initLocalitats() throws Exception  {
        List<Localitat> llista = dataService.getAllLocalitats();
        for (Localitat l : llista) {
            Comarca c = tripUB.findComarca(l.getIdComarca());
            l.setComarca(c);
            c.addLocalitat(l);
            List<Ruta> origens = tripUB.findRutaByOrigen(l.getId());
            for (Ruta r: origens) {
                r.setLocalitatOrigen(l);
            }
            List<Ruta> destins = tripUB.findRutaByDesti(l.getId());
            for (Ruta r: destins) {
                r.setLocalitatDesti(l);
            }
        }
        return tripUB.setLocalitats(llista);
    }

    private void initAllotjaments() throws Exception {
        List<Allotjament> lallotjaments= dataService.getAllAllotjaments();
        for (Allotjament a : lallotjaments) {
            //Etapa e = dataService.getEtapaById(a.getIdEtapa());
            Etapa e = modelFacade.findEtapa(a.getIdEtapa());
            if (e!=null) {
                e.addAllotjament(a);
                a.setEtapa(e);
            }
        }
    }

    private void initTramsRuta() throws Exception {
        // Es donen d'alta els trams d'una ruta i depres, segons siguin
        // de tipus etapa o de tipus track es donen d'alta
        // les etapes o els punts de control
        // Al final es reparteixen els transports pels trams ja creats
        // no es consulta al dataservice pels trams sino a tripUB per
        // a no fer dos vegades new del mateix tram

        initTramsEtapaRuta();
        initTramsTrackRuta();
        // Lligar transports amb els trams etapa. Els transports també es creen a demanda
        // segons el que necessiti el tram.
        List<Parell<Integer, Integer>> llistaTransportsTrams= dataService.getAllTransportsPosibleTrams();

        for (Parell p: llistaTransportsTrams) {
            // element 1 Transport
            // element 2 tram
            Transport t = dataService.getTransportById((Integer)(p.getElement1()));
            modelFacade.addTransportTram((Integer)(p.getElement2()), t);
        }
    }

    private void initTramsEtapaRuta() throws Exception {
        // Es donen d'alta els trams d'una ruta i depres, segons siguin
        // de tipus etapa es donen d'alta
        // les etapes
        // Al final es reparteixen els transports pels trams ja creats
        // no es consulta al dataservice pels trams sino a tripUB per
        // a no fer dos vegades new del mateix tram

        List<TramEtapa> trams = dataService.getAllTramEtapas();
        for (TramEtapa te : trams) {
            // Reparticio dels trams a les rutes
            modelFacade.addTram(te);

            // Lligar etapa-TramEtapa. Les etapes es llegeixen una a una de la BD
            // segons ho necessiti el tram etapa

            Etapa etapa = dataService.getEtapaById(te.getIdEtapaDesti());
            te.setEtapaDesti(etapa);
            etapa = dataService.getEtapaById(te.getIdEtapaOrigen());
            te.setEtapaOrigen(etapa);
        }
    }
    private void initTramsTrackRuta() throws Exception {
        // Es donen d'alta els trams d'una ruta i depres, segons siguin
        // d de tipus track es donen d'alta
        // els punts de control

        List<TramTrack> trams = dataService.getAllTramTracks();
        for (TramTrack tt: trams) {
            // Reparticio dels trams a les rutes
            modelFacade.addTram(tt);

            PuntDePas pdp = dataService.getPuntDePasById(tt.getIdPuntDePasDesti());
            tt.setPuntDePasDesti(pdp);
            pdp = dataService.getPuntDePasById(tt.getIdPuntDePasOrigen());
            tt.setPuntDePasOrigen(pdp);
        }
    }


    public boolean initGrups() throws Exception {
        return (tripUB.setGrups(dataService.getAllGrups()));
    }

    public boolean initXarxaPersones() throws Exception {
        return modelFacade.setXarxaPersones(dataService.getAllPersonas());
    }

    public boolean initRutes() throws Exception {
        List<Ruta> listRutas = dataService.getAllRutas();
        return tripUB.setRutes(listRutas);
    }

    public boolean initComarques() throws Exception {
        List<Comarca> listComarques = dataService.getAllComarcas();
        return tripUB.setComarques(listComarques);
    }

    public void relacionarRutesComarques() throws Exception {
        //DB:
        List<Parell<Integer, Integer>> relacionsRC = dataService.getAllRelacioComarcasRutas();
        tripUB.setComarquesToRutes(relacionsRC);
    }
    public boolean getRelacioRutesComarques() throws Exception{
        List<Parell<Integer, Integer>> relacionsRC = dataService.getAllRelacioComarcasRutas();
        return tripUB.setLocalitatsRutes(relacionsRC);
    }


    public StatusType addNewPersona(String correu, String nom, String cognoms, String dni, String password, Integer id_tripUB) throws Exception {
        if  (modelFacade.findPersonaByCorreu(correu) != null)
            return StatusType.PERSONA_DUPLICADA;
        else if (Seguretat.isMail(correu) && Seguretat.isPasswordSegur(password)){
            Persona persona = new Persona(correu, nom, cognoms, dni, password, id_tripUB);
            if(this.dataService.addPersona(persona)){
                //actualizar lista
                this.initXarxaPersones();
            }
            return StatusType.REGISTRE_VALID;
        }
        else return StatusType.FORMAT_INCORRECTE_CORREU_PWD;
    }


    public Integer addNewGrup(String grupNom) throws Exception {
        Grup grup = new Grup(grupNom);
        dataService.addGrup(grup);
        tripUB.addGrup(grup);
        return grup.getId();
    }
    public boolean addRelacionGrupPersona(String correuPersona, Integer idGrup) throws Exception {
        Parell<String,Integer> gfp = new Parell<>(correuPersona,idGrup);
        modelFacade.addPersonaToGrup(idGrup, correuPersona);
        return dataService.addGrupFormatPersones(gfp);
    }
    public List<Vot> getAllVots() throws Exception {
        return dataService.getAllVots();
    }
    public boolean addNewVot(Vot vot) throws Exception {
        return dataService.addVot(vot);
    }
}
