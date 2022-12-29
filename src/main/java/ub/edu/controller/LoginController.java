package ub.edu.controller;

import ub.edu.model.ModelFacade;
import ub.edu.model.StatusType;
import ub.edu.model.TripUB;
import ub.edu.resources.ResourcesFacade;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginController {

    private static LoginController uniqueInstance;
    private TripUB tripUB;
    private ModelFacade modelFacade;

    public static LoginController getInstance() throws Exception {
        if (uniqueInstance == null) {
            uniqueInstance = new LoginController();
            uniqueInstance.tripUB = TripUB.getInstance();
            uniqueInstance.modelFacade = ModelFacade.getInstance();
        }
        return uniqueInstance;
    }
    //public SessionMemory getSessionMemory() {
//        return sessionMemory;
//    }
    public StatusType loguejarSociStatus(String correu, String password){
        return (modelFacade.loguejarSociStatus(correu, password));
    }
    public String recuperarContrasenya(String username){
        return modelFacade.recuperarContrasenya(username);
    }


}
