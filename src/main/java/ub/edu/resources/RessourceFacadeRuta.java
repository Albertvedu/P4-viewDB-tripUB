package ub.edu.resources;

import ub.edu.model.Ruta;
import ub.edu.model.TripUB;
import ub.edu.model.Vot;
import ub.edu.resources.dao.Quartet;
import ub.edu.resources.service.AbstractFactoryData;
import ub.edu.resources.service.DataService;
import ub.edu.resources.service.FactoryDB;

import java.util.List;

public class RessourceFacadeRuta {
    private volatile static RessourceFacadeRuta uniqueInstance;
    private AbstractFactoryData factory;      // Origen de les dades
    private DataService dataService;         // Connexio amb les dades
    private TripUB tripUB;                  // Model a tractar

    public static RessourceFacadeRuta getInstance(){
        if (uniqueInstance == null) {
            synchronized (RessourceFacadeRuta.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new RessourceFacadeRuta();
                    uniqueInstance.tripUB = TripUB.getInstance();
                    uniqueInstance.factory = new FactoryDB();
                    uniqueInstance.dataService = new DataService(uniqueInstance.factory);
                }
            }
        }
        return uniqueInstance;
    }
    public List<Vot> getAllVots() throws Exception {
        return dataService.getAllVots();
    }
    public Ruta getRutaByID(int id) throws Exception {
        return dataService.getRutaById( id);
    }
    public List<Ruta> getAllRutes() throws Exception {
        return dataService.getAllRutas();
    }
    public List<Quartet<Integer, String, Integer, Boolean>> getAllTipusVots() throws Exception {
        return dataService.getAllTipusVots();
    }
}
