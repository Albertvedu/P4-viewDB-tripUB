package ub.edu.controller;

import ub.edu.model.ModelFacade;
import ub.edu.model.ModelFacanaRuta;
import ub.edu.model.TripUB;

import java.util.HashMap;
import java.util.List;

public class RutesController {

    private static RutesController uniqueInstance;
    private ModelFacanaRuta modelFacanaRuta;


    public static RutesController getInstance() throws Exception {
        if(uniqueInstance == null){
            synchronized (RutesController.class){
                if (uniqueInstance == null) {
                    uniqueInstance = new RutesController();
                    uniqueInstance.modelFacanaRuta = ModelFacanaRuta.getInstance();
                }
            }
        }
        return uniqueInstance;
    }
    public void valorarRuta() throws Exception {
        modelFacanaRuta.valorarRuta();
    }
    public void valorarRutaEstrelles() throws Exception {
        modelFacanaRuta.valorarRutaEstrelles();
    }
    public List<HashMap<Object,Object>> getAllRutesPerNom() {
        return modelFacanaRuta.getAllRutesPerNom();
    }
    public void getAllRutesPerLocalitat(String name) {
        modelFacanaRuta.getAllRutesPerLocalitat(name);
    }
    public HashMap<Object, Object> getRutaById(Integer id_ruta) throws Exception {
        return modelFacanaRuta.getRutaById(id_ruta);
    }
}
