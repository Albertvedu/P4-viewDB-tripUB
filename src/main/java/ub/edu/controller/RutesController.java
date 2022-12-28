package ub.edu.controller;

import ub.edu.model.ModelFacade;
import ub.edu.model.TripUB;

import java.util.HashMap;
import java.util.List;

public class RutesController {

    private static RutesController uniqueInstance;
    private ModelFacade modelFacade;
    private TripUB tripUB;


    public static RutesController getInstance(){
        if(uniqueInstance == null){
            synchronized (RutesController.class){
                if (uniqueInstance == null) {
                    uniqueInstance = new RutesController();
                    uniqueInstance.tripUB = TripUB.getInstance();
                    uniqueInstance.modelFacade = ModelFacade.getInstance();
                }
            }
        }
        return uniqueInstance;
    }
    public List<HashMap<Object,Object>> getAllRutesPerNom() {
        return modelFacade.getAllRutesPerNom();
    }
    public void getAllRutesPerLocalitat(String name) {
        modelFacade.getAllRutesPerLocalitat(name);
    }
    public HashMap<Object, Object> getRutaById(Integer id_ruta) throws Exception {
        return modelFacade.getRutaById(id_ruta);
    }
}
