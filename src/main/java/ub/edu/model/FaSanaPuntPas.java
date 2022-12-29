package ub.edu.model;


import ub.edu.model.punPas_Strategy.PuntPasStrategy;

public class FaSanaPuntPas {

    private PuntPasStrategy puntPasStrategy;

    public FaSanaPuntPas(TripUB tripUB) throws Exception {
        puntPasStrategy = PuntPasStrategy.getInstance();
    }
//    public String incluir_PuntPas(String rutaNom, String tram, String user, String punt, double dist) throws Exception {
//
//        return puntPasEngine.incluirPuntdePas(rutaNom, tram, user, punt, dist);
//    }
    public void getTop10Likes(String criteri) throws Exception {
        puntPasStrategy.getTop_5Likes(criteri);
    }
    public void getTopEstrellesRuta( String text) throws Exception {
        puntPasStrategy.getTopEstrellesRutes(text);
    }
    public boolean valorarPuntdePas(int idPuntPas, String  valoracio, String correu_persona, Integer id_ruta) throws Exception {
        return puntPasStrategy.perEstrelles(idPuntPas, valoracio, correu_persona, id_ruta);

    }
//    public boolean ferLike(String punt ){
//        return puntPasStrategy.perLikes(punt);
//    }
//    public boolean ferDesLike(String punt){
//        return puntPasStrategy.perDesLikes(punt);
//    }
//    public List<PuntDePas> llistarAll(String rutaNom){
//        return puntPasStrategy.llistarAll();
//    }
//    public List<PuntDePas> llistarTopEstrelles(String rutaNom){
//        return puntPasStrategy.llistarTopEstrelles();
//    }
//    public List<PuntDePas> llistarTopLikes(String rutaNom){
//        return puntPasStrategy.llistarTopLikes();
//    }
//    public List<PuntDePas> llistarTopDesLikes(String rutaNom){
//        return puntPasStrategy.llistarTopDesLikes();
//    }
}
