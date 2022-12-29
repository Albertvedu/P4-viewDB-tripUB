package ub.edu.controller;

import ub.edu.model.ModelFacade;
import ub.edu.model.TripUB;
import ub.edu.resources.ResourcesFacade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GrupsController {

    private static GrupsController uniqueInstance;
    private ResourcesFacade inicialitzador;
    private ModelFacade modelFacade;
    private TripUB tripUB;


    public static GrupsController getInstance() throws Exception {
        if(uniqueInstance == null){
            synchronized (GrupsController.class){
                if (uniqueInstance == null) {
                    uniqueInstance = new GrupsController();
                    uniqueInstance.tripUB = TripUB.getInstance();
                    uniqueInstance.modelFacade = ModelFacade.getInstance();
                    uniqueInstance.inicialitzador = ResourcesFacade.getInstance();
                }
            }
        }
        return uniqueInstance;
    }
    public List<HashMap<Object,Object>> getAllGrupsPerNom() {
        return modelFacade.getAllGrupsPerNom();
    }
    public Integer addNewGrup(String grupNom) throws Exception {
        return inicialitzador.addNewGrup(grupNom);
    }
    public boolean addRelacionGrupPersona(String correuPersona, Integer idGrup) throws Exception {
        return  inicialitzador.addRelacionGrupPersona(correuPersona, idGrup);

    }

    public ArrayList<HashMap<Object,Object>> getAllGrupsPerPersona(String correu) throws Exception {
        return modelFacade.getAllGrupsPerPersona(correu);
    }
    public Integer getIdGrupByName(String newValue) {
        return tripUB.getIdGrupByName(newValue);
    }


}
