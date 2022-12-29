package ub.edu.controller;

import ub.edu.model.ModelFacanaPuntPas;
import ub.edu.model.TripUB;
import ub.edu.resources.RessourceFacadePuntdePas;

public class PuntdePasController {

    private ModelFacanaPuntPas modelFacanaPuntPas;
    private static PuntdePasController uniqueInstance;
    private TripUB tripUB;


    public static PuntdePasController getInstance() throws Exception {
        if (uniqueInstance == null) {
            uniqueInstance = new PuntdePasController();
            uniqueInstance.tripUB = TripUB.getInstance();
            uniqueInstance.modelFacanaPuntPas = ModelFacanaPuntPas.getInstance();
        }
        return uniqueInstance;
    }

    public void getTop5Likes(String llistarPer, String criteri) throws Exception {
        modelFacanaPuntPas.getTop10Likes(llistarPer, criteri);
    }
    public void getTopEstrellesRuta( String llistarPer, String criteri) throws Exception {
        modelFacanaPuntPas.getTopEstrellesRuta(llistarPer, criteri);
    }
    public void getTopEstrellesPuntPas( String llistarPer, String criteri) throws Exception {
        modelFacanaPuntPas.getTopEstrellesPuntPas(llistarPer, criteri);
    }
    public void valorarPuntdePas() throws Exception {
        modelFacanaPuntPas.valorarPuntdePas( );
    }

    public void ferLike() throws Exception {
        modelFacanaPuntPas.ferLike(  );
    }
    public void ferDesLike () throws Exception {
        modelFacanaPuntPas.ferDesLike( );
    }

}
