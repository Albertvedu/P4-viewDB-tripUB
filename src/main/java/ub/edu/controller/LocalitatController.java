package ub.edu.controller;

import ub.edu.model.ModelFacade;
import ub.edu.model.Ruta;
import ub.edu.model.TripUB;

import java.util.HashMap;
import java.util.List;

public class LocalitatController {


    private static LocalitatController uniqueInstance;
    private ModelFacade modelFacade;
    private TripUB tripUB;

    public static LocalitatController getInstance() throws Exception {
        if (uniqueInstance == null) {
            uniqueInstance = new LocalitatController();
            uniqueInstance.tripUB = TripUB.getInstance();
            uniqueInstance.modelFacade = ModelFacade.getInstance();
        }
        return uniqueInstance;
    }
    public HashMap<Object,Object> getLocalitatByID(Integer id) throws Exception {
        return modelFacade.getLocalitatByID(id);
    }
    public List<HashMap<Object,Object>> getAllLocalitats() throws Exception {
        return modelFacade.getAllLocalitats();
    }
    public HashMap<Object, Object> getLocalitat_y_EtapaByID(Integer id) throws Exception {
        return modelFacade.getLocalitat_y_EtapaByID(id);
    }
    public HashMap<Object,Object> getLocalitat_y_PuntDePasById(Integer id) throws Exception {
        return modelFacade.getLocalitat_y_PuntDePasById(id);
    }
}
