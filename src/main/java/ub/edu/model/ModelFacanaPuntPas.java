package ub.edu.model;


import ub.edu.model.punPas_Strategy.PuntPasStrategy;

public class ModelFacanaPuntPas {
    private volatile static ModelFacanaPuntPas uniqueInstance;
    private PuntPasStrategy puntPasStrategy;
    private TripUB tripUB;


    public static ModelFacanaPuntPas getInstance() throws Exception {
        if (uniqueInstance == null) {
            synchronized (ModelFacanaPuntPas.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new ModelFacanaPuntPas();
                    uniqueInstance.tripUB = TripUB.getInstance();
                    uniqueInstance.puntPasStrategy = PuntPasStrategy.getInstance();
                }
            }
        }
        return uniqueInstance;
    }
//    public String incluir_PuntPas(String rutaNom, String tram, String user, String punt, double dist) throws Exception {
//
//        return puntPasEngine.incluirPuntdePas(rutaNom, tram, user, punt, dist);
//    }
    public void getTop10Likes(String llistarPer, String criteri) throws Exception {
       // puntPasStrategy.getTop_5Likes(criteri);
        puntPasStrategy.llistarLikes(llistarPer, criteri);
    }
    public void getTopEstrellesRuta( String llistarPer, String criteri) throws Exception {
        puntPasStrategy.getTopEstrellesRutes(llistarPer, criteri);
    }
    public void getTopEstrellesPuntPas( String llistarPer, String criteri) throws Exception {
        puntPasStrategy.getTopEstrellesPuntPas(llistarPer, criteri);
    }
    public boolean valorarPuntdePas() throws Exception {
        puntPasStrategy.perEstrelles();
        return true;
    }
    public boolean ferLike() throws Exception {
        return puntPasStrategy.perLikes();
    }
    public boolean ferDesLike() throws Exception {
        return puntPasStrategy.perDesLikes();
    }


    public PuntDePas findPuntDePas(Integer idPuntDePas) {
        PuntDePas pdp = null;
        for (Ruta r: tripUB.getRutes()) {
            pdp = r.findPuntDePas(idPuntDePas);
            if (pdp != null) {
                return pdp;
            }
        }
        return pdp;
    }
    public PuntDePas getPuntDePasByIdUB(Integer id) {
        PuntDePas tt = null;
        for (Ruta r: tripUB.getRutes()) {
            tt = r.getPuntDePasById(id);
            if (tt!=null) return tt;
        }
        return tt;
    }
}
