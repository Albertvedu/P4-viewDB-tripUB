package ub.edu.view;

import javafx.stage.Stage;
import ub.edu.controller.*;

public abstract class Escena {
    protected Stage stage;
    protected LoginController loginController;
    protected Controller controller;
    protected RutesController rutesController;
    protected GrupsController grupsController;
    protected RegisterController registerController;
    protected LocalitatController localitatController;
    protected PuntdePasController puntdePasController;

    public void setController() throws Exception {
        this.loginController = LoginController.getInstance();
        this.controller = Controller.getInstance();
        this.rutesController = RutesController.getInstance();
        this.grupsController = GrupsController.getInstance();
        this.registerController = RegisterController.getInstance();
        this.localitatController = LocalitatController.getInstance();
        this.puntdePasController = PuntdePasController.getInstance();
    }

    public void setStage(Stage newStage) {
        this.stage = newStage;
    }
}
