package ub.edu.controller;

import javafx.stage.Stage;

import ub.edu.view.Vista;

public class ControllerCrearVista {
    private Vista vista;

    public ControllerCrearVista() {
    }

    public  ControllerCrearVista (Stage stage) throws Exception {

        vista = new Vista(stage); // eliminar this
    }
}
