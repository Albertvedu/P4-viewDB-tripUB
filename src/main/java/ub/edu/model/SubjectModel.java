package ub.edu.model;

import ub.edu.view.ObserverView;

import java.util.HashMap;
import java.util.List;

public interface SubjectModel {
    public void registerObserver(ObserverView o);
    public void removeObserver(ObserverView o);
    public void notifyObservers(List<HashMap<Object, Object>> listRutes  );

}
