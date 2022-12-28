package ub.edu.controller;

import ub.edu.model.FaSanaPuntPas;
import ub.edu.model.TripUB;

public class PuntdePasController {

    private  FaSanaPuntPas faSanaPuntPas;
    private static PuntdePasController uniqueInstance;
    private TripUB tripUB;


    public static PuntdePasController getInstance() throws Exception {
        if (uniqueInstance == null) {
            uniqueInstance = new PuntdePasController();
            uniqueInstance.tripUB = TripUB.getInstance();
            uniqueInstance.faSanaPuntPas = new FaSanaPuntPas(uniqueInstance.tripUB);
        }
        return uniqueInstance;

    }
//    public String incluir_PuntPas(String rutaNom, String tram, String user, String punt, double dist) throws Exception {
//
//        return switch (faSanaPuntPas.incluir_PuntPas(rutaNom, tram, user, punt, dist)) {
//            case "No pots" -> "No pots afegir aquest punt de pass, ja està registrat";
//            case "Has afegit" -> "Has afegit un nou punt de control. Gràcies";
//            case "Encara no" -> "Encara no has iniciat la ruta";
//            case "no tram" -> "Tram introduït no existeix";
//            case "No ruta" -> "Nom ruta inexistent";
//            default -> "Alguna cosa a fallat";
//        };
//    }
    public void getTop10Likes(String criteri) throws Exception {
        faSanaPuntPas.getTop10Likes(criteri);
    }
    public void getTopEstrellesRuta( String text) throws Exception {
        faSanaPuntPas.getTopEstrellesRuta(text);
    }
    public void valorarPuntdePas(int idPuntPas, String valoracio, String correu_persona, Integer id_ruta) throws Exception {
        faSanaPuntPas.valorarPuntdePas( idPuntPas, valoracio, correu_persona, id_ruta);
    }

//    public void ferLike(String punt){
//        faSanaPuntPas.ferLike( punt );
//    }
//    public void ferDesLike(String punt){
//        faSanaPuntPas.ferDesLike( punt );
//    }
//    public List<PuntDePas> llistarAll(String rutaNom){
//        return faSanaPuntPas.llistarAll( rutaNom);
//    }
//    public List<PuntDePas> llistarTopEstrelles(String rutaNom){
//        return faSanaPuntPas.llistarTopEstrelles(rutaNom);
//    }
//    public List<PuntDePas> llistarTopLikes(String rutaNom){
//        return faSanaPuntPas.llistarTopLikes(rutaNom);
//    }
//    public List<PuntDePas> llistarTopDeslikes(String rutaNom){
//        return faSanaPuntPas.llistarTopDesLikes(rutaNom);
//    }
}
