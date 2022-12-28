package ub.edu.resources;

import ub.edu.model.*;
import ub.edu.resources.service.AbstractFactoryData;
import ub.edu.resources.service.DataService;
import ub.edu.resources.service.FactoryDB;

import java.util.List;

public class RessourceFacadePuntdePas {
    private volatile static RessourceFacadePuntdePas uniqueInstance;
    private AbstractFactoryData factory;      // Origen de les dades
    private DataService dataService;         // Connexio amb les dades
    private TripUB tripUB;                  // Model a tractar

    public static RessourceFacadePuntdePas getInstance(){
        if (uniqueInstance == null) {
            synchronized (RessourceFacadePuntdePas.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new RessourceFacadePuntdePas();
                    uniqueInstance.tripUB = TripUB.getInstance();

                    uniqueInstance.factory = new FactoryDB();
                    uniqueInstance.dataService = new DataService(uniqueInstance.factory);

                }
            }
        }
        return uniqueInstance;
    }

    public List<Opinio> getAllOpinions() throws Exception {
        return dataService.getAllOpinions();
    }
    public PuntDePas getPuntdePasById(int id) throws Exception {
        return dataService.getPuntDePasById(id);
    }
    public List<PuntDePas> getAllPuntdePas() throws Exception {
        return dataService.getAllPuntDePas();
    }
    public List<Vot> getAllVots() throws Exception {
        return dataService.getAllVots();
    }
    public List<Ruta> getAllRutes() throws Exception {
        return dataService.getAllRutas();
    }
}
