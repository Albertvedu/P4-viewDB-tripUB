package ub.edu.model.punPas_Strategy;

import ub.edu.model.Ruta;
import ub.edu.model.SubjectModelPuntPas;
import ub.edu.model.punPas_Strategy.extendClasses.PerDesLike;
import ub.edu.model.punPas_Strategy.extendClasses.PerEstrelles;
import ub.edu.model.punPas_Strategy.extendClasses.PerEstrellesRuta;
import ub.edu.model.punPas_Strategy.extendClasses.PerLikes;
import ub.edu.view.ObserverView;
import ub.edu.view.ObserverViewPuntPas;

import java.util.ArrayList;
import java.util.Map;

public class PuntPasStrategy implements SubjectModelPuntPas {
    private volatile static PuntPasStrategy uniqueInstance;
    private PerLikes perLikes;
    private PerEstrelles perEstrelles;
    private PerDesLike perDesLike;
    private PerEstrellesRuta perEstrellesRuta;
    private Map<String, Ruta> rutaMap;
    private ArrayList observers;
    private ValorarPuntPas veurePer;
    private SimpleFactory  simpleFactory;
    private SimpleFactory2 simpleFactory2;

    public static PuntPasStrategy getInstance() throws Exception {
        if (uniqueInstance == null) {
            synchronized (PuntPasStrategy.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new PuntPasStrategy();
                    uniqueInstance.perLikes = new PerLikes();
                    uniqueInstance.perEstrelles = new PerEstrelles();
                    uniqueInstance.perDesLike = new PerDesLike();
                    uniqueInstance.perEstrellesRuta = new PerEstrellesRuta();
                    uniqueInstance.observers = new ArrayList<>();
                    uniqueInstance.simpleFactory = SimpleFactory.INSTANCE;
                    uniqueInstance.simpleFactory2 = SimpleFactory2.INSTANCE;
                }
            }
        }
        return uniqueInstance;
    }


    public void getTopEstrellesRutes(String llistaPer, String criteri) throws Exception {
        //Pre: Amb l'String llistaPer, obté la classe <extends> i la interfeace iLListar, tant per Rutes o Punt de Pas

        llistaPer =  llistaPer.replace(" ","");  // Treu espais del nom que ve del ComboBox
        veurePer = simpleFactory.obtenirClasseAbstract("ListEstrelles" +llistaPer);
        String classe = "LlistarEstrelles" + llistaPer;
        veurePer.setLlistarPuntPas(simpleFactory2.obtenirClasseILlistar(classe));
        Map<String, Integer> sortTopLike = veurePer.performaLlistar(criteri);
        modelChanged(sortTopLike, criteri);
    }
    public void getTopEstrellesPuntPas(String llistaPer, String criteri) throws Exception {
        //Pre: Amb l'String llistaPer, obté la classe <extends> i la interfeace iLListar, tant per Rutes o Punt de Pas

        llistaPer =  llistaPer.replace(" ","");  // Treu espais del nom que ve del ComboBox
        veurePer = simpleFactory.obtenirClasseAbstract("ListEstrelles" +llistaPer);
        String classe = "LlistarEstrelles" + llistaPer;
        veurePer.setLlistarPuntPas(simpleFactory2.obtenirClasseILlistar(classe));
        Map<String, Integer> sortTopLike = veurePer.performaLlistar(criteri);
        modelChanged(sortTopLike, criteri);
    }

    public void llistarLikes(String llistaPer, String criteri) throws Exception {
        // Métode tant per llistar Rutes o Punt de Pas
        //Pre: Amb l'String llistaPer, obté la classe <extends> i la interfeace iLListar, tant per Rutes o Punt de Pas

        llistaPer =  llistaPer.replace(" ","");  // Treu espais del nom que ve del ComboBox
        veurePer = simpleFactory.obtenirClasseAbstract("ListLikes" +llistaPer);
        String classe = "Likes" + llistaPer;
        veurePer.setLlistarPuntPas(simpleFactory2.obtenirClasseILlistar(classe));
        Map<String, Integer> sortTopLike = veurePer.performaLlistar(criteri);
        modelChanged(sortTopLike, criteri);
    }
    public boolean  perLikes() throws Exception {
        perLikes.setValorarPuntPas(new ValorarLikes());
        perLikes.performaValorar();
        llistarLikes("Punt de Pas", "Like");
        return true;
    }
    public boolean perDesLikes() throws Exception {
          perDesLike.setValorarPuntPas(new ValorarDeslike());
          perDesLike.performaValorar();
        llistarLikes("Punt de Pas", "Unlike");
        return true;
    }
    public void perEstrelles() throws Exception {
        perEstrelles.setValorarPuntPas(new ValorarEstrelles());
        perEstrelles.performaValorar();
        getTopEstrellesRutes("Punt de Pas", "Estrelles");
    }
    public void valorarRuta() throws Exception {
        perEstrellesRuta.setValorarPuntPas((new ValorarRuta()));
        perEstrellesRuta.performaValorar();
        llistarLikes("Rutes", "Like");
    }
    public void valorarRutaEstrelles() throws Exception {
        perEstrellesRuta.setValorarPuntPas((new ValorarRuta()));
        perEstrellesRuta.performaValorar();
        getTopEstrellesRutes("Rutes", "Estrelles");
    }

    @Override
    public void registerObserver(ObserverView o) {
        this.observers.add(o);
        System.out.println("Observador PuntPasStrategy registrat: " + o.toString());

    }

    @Override
    public void removeObserver(ObserverView o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers(Map<String, Integer> sortTopLike, String text ) {
        for (int i = 0; i < observers.size(); i++) {
            ObserverViewPuntPas observer = (ObserverViewPuntPas) observers.get(i);
            observer.update(sortTopLike, text);
        }
    }
    public void modelChanged(Map<String, Integer> sortTopLike, String text) {

        notifyObservers(sortTopLike, text);
    }


}
