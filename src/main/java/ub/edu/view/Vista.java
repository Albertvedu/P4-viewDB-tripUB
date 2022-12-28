package ub.edu.view;

import javafx.stage.Stage;
import ub.edu.controller.Controller;
import ub.edu.controller.ControllerCrearVista;
import ub.edu.controller.LoginController;

public class Vista {


    public Vista(Stage stage ) throws Exception {

        //Posem titol a la finestra

        Escena login = EscenaFactory.INSTANCE.creaEscena("login-view", "TripUB Login View");

        //Li enviem la finestra (stage) i el controlador a la nova escena
        login.setController();

    }
}
