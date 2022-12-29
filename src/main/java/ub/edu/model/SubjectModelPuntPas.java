package ub.edu.model;

import ub.edu.view.ObserverView;

import java.util.Map;

public interface SubjectModelPuntPas {
    public void registerObserver(ObserverView o);
    public void removeObserver(ObserverView o);
    public void notifyObservers(Map<String, Integer> sortTopLike, String text );

}
