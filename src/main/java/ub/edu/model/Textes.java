package ub.edu.model;

import javafx.scene.control.Alert;

public class Textes {
    private volatile static Textes uniqueInstance;

    public static Textes getInstance() throws Exception {
        if (uniqueInstance == null) {
            uniqueInstance = new Textes();
        }
        return uniqueInstance;
    }
    public void mostrarPopUp(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Info");
        alert.setContentText(text);
        alert.showAndWait();
    }
}
