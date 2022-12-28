package ub.edu.controller;

import ub.edu.model.ModelFacade;
import ub.edu.model.Seguretat;
import ub.edu.model.StatusType;
import ub.edu.model.TripUB;
import ub.edu.resources.ResourcesFacade;

public class RegisterController {

    private static RegisterController uniqueInstance;
    private ResourcesFacade inicialitzador;

    private ModelFacade modelFacade;
    private TripUB tripUB;


    public static RegisterController getInstance(){
        if(uniqueInstance == null){
            synchronized (RegisterController.class){
                if (uniqueInstance == null) {
                    uniqueInstance = new RegisterController();
                    uniqueInstance.tripUB = TripUB.getInstance();
                    uniqueInstance.modelFacade = ModelFacade.getInstance();
                    uniqueInstance.inicialitzador = ResourcesFacade.getInstance();
                }
            }
        }
        return uniqueInstance;
    }
    public String validateRegistrePersona(String username, String password) {
        switch (modelFacade.validateRegistrePersona(username, password)) {
            case REGISTRE_VALID:
                return "Registre vàlid";
            case PERSONA_DUPLICADA:
                return "Persona duplicada";
            case FORMAT_INCORRECTE_CORREU_PWD:
                return "Format incorrecte";
            default:
        }
        return"Cas no contemplat";
    }
    public String loguejarPersona(String username, String password){

        switch (modelFacade.loguejarPersona(username, password)) {
            case 0:
                return StatusType.REGISTRE_VALID.toString();
            case 1:
                return StatusType.CORREU_INEXISTENT.toString();
            case 2:
                return StatusType.CONTRASENYA_INCORRECTA.toString();
            default:
        }
        return"Cas no contemplat";
    }
    public StatusType addNewPersona(String correu, String nom, String cognoms, String dni, String password, Integer id_tripUB) throws Exception {
        return inicialitzador.addNewPersona(correu, nom, cognoms, dni, password, id_tripUB);
    }
    public String validatePassword(String b) {
        switch (Seguretat.validatePassword(b)) {
            case CONTRASENYA_NO_SEGURA:
                return "Contrasenya no prou segura";
            case CONTRASENYA_SEGURA:
                return "Contrasenya segura";
        }
        return "Cas no contemplat";
    }
    public String validateUsername(String a) {
        switch (Seguretat.validateUsername(a)) {
            case CORREU_INCORRECTE:
                return "Correu en format incorrecte";
            case CORREU_CORRECTE:
                return "Correu en format correcte";
        }
        return "Cas no contemplat";

    }
}
