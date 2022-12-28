//package ub.edu.model.punPas_Strategy;
//
//import ub.edu.model.model.Punt_dePas;
//import ub.edu.model.model.Ruta;
//import ub.edu.model.model.Tram;
//
//import java.util.*;
//
//public class LlistarDesLikes implements iLlistar {
//    @Override
//    public List<Punt_dePas> llistarAll(Map<String, Ruta> rutaMap, String rutaNom) {
//        return null;
//    }
//
//    @Override
//    public List<Punt_dePas> llistar(Map<String, Ruta> rutaMap, String rutaNom) {
//
//        return retornar10elements( ordenarList(obtenirLlistaPuntsPas(rutaMap, rutaNom)));
//    }
//
//    ///////////////////  MÉTODES PRIVATS
//    private List<Punt_dePas> obtenirLlistaPuntsPas(Map<String, Ruta> rutaMap, String rutaNom){
//        List<Punt_dePas> puntsPas = new ArrayList<>();
//        boolean existeix = false;
//        Iterator<Map.Entry<String, Ruta>> iterator = rutaMap.entrySet().iterator();
//        while (iterator.hasNext()) {        // Recorre Map rutes
//            Map.Entry<String, Ruta> entry = (Map.Entry) iterator.next();
//
//            if (entry.getKey().equals(rutaNom)) {
//                existeix = true;
//                for (Tram t : entry.getValue().getTramList())  // recorre rutes, les originals no les del soci
//                    puntsPas.addAll(t.getPunt_dePasList());
//            }
//        }
//        if ( !existeix )
//            puntsPas.add(new Punt_dePas("La ruta no existeix"));
//        return puntsPas;
//    }
//    private List<Punt_dePas> ordenarList(List<Punt_dePas> punt_dePas){
//        Comparator<Punt_dePas> comparar = Comparator.comparing(Punt_dePas::getDesLikes);
//        Collections.sort(punt_dePas, comparar.reversed());
//        return punt_dePas;
//    }
//    private List<Punt_dePas> retornar10elements(List<Punt_dePas> puntsPasTop5){
//        List<Punt_dePas> p = new ArrayList<>();
//
//        for (int i = 0; i < puntsPasTop5.size(); i++) {
//            if ( i < 10 )
//                p.add(puntsPasTop5.get(i));
//        }
//        return p;
//    }
//}
