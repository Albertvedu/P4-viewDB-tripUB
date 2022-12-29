package ub.edu.controller;

import javafx.stage.Stage;
import ub.edu.model.*;
import ub.edu.resources.ResourcesFacade;
import ub.edu.view.Vista;

import java.util.*;


public class Controller {
    private volatile static Controller uniqueInstance;
    private ResourcesFacade inicialitzador;
    private ModelFacade modelFacade;
    private TripUB tripUB;
    private SessionMemory sessionMemory;


    public static Controller getInstance() throws Exception {
        if (uniqueInstance == null) {
            synchronized (Controller.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Controller();
                    uniqueInstance.tripUB = TripUB.getInstance();
                    uniqueInstance.sessionMemory = SessionMemory.getInstance();
                    uniqueInstance.modelFacade = ModelFacade.getInstance();
                    uniqueInstance.inicialitzador =  ResourcesFacade.getInstance();
                    uniqueInstance.inicialitzador.populateTripUB();
                }
            }

        }
        return uniqueInstance;
    }

    public SessionMemory getSessionMemory() {
        return sessionMemory;
    }

    public List<HashMap<Object, Object>> getAllComarques() throws Exception {
        return modelFacade.getAllComarques();
    }
    public List<HashMap<Object,Object>> getAllTramsEtapaTrackByRutaId(Integer id_ruta) throws Exception {
        return modelFacade.getAllTramsEtapaTrackByRutaId(id_ruta);
    }
    public HashMap<Object,Object> getTramTrackById(Integer id) throws Exception {
        return modelFacade.getTramTrackById(id);
    }
    public HashMap<Object, Object> getTramEtapaById(Integer id) throws Exception {
        return modelFacade.getTramEtapaById(id);
    }
    public List<HashMap<Object,Object>> getAllAllotjaments() throws Exception {
        return modelFacade.getAllAllotjaments();
    }
}