package ub.edu;

import javafx.application.Application;
import javafx.stage.Stage;
import ub.edu.controller.Controller;
import ub.edu.controller.ControllerCrearVista;
import ub.edu.view.Vista;

import java.io.IOException;


public class AppMain extends Application{
    private ControllerCrearVista controllerVista;

    @Override
    public void start(Stage stage) throws Exception {
        // Creacio de la vista, el model i el controlador
        //TODO: Fer els canvis que es creguin necessaris per a fer la creació
        // de controlador, model i vista: Qui crea a qui?

        controllerVista = new ControllerCrearVista(stage);

    }

    public static void main(String[] args) {
        launch();
    }


}