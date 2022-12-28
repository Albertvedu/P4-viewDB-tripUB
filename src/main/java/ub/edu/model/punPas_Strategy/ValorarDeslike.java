//package ub.edu.model.punPas_Strategy;
//
//import ub.edu.model.model.Punt_dePas;
//import ub.edu.model.model.Ruta;
//import ub.edu.model.model.Tram;
//
//import java.util.Map;
//
//public class ValorarDeslike implements iValorar {
//    @Override
//    public boolean valorar(Map<String, Ruta> rutaMap, String punt, int valoracio) {
//        for (Map.Entry<String, Ruta> elementMap : rutaMap.entrySet()) { // recorre RUTES
//            Map.Entry<String, Ruta> entry =  elementMap;
//
//            for (Tram t: entry.getValue().getTramList())               //  recorre TRAMS
//                for (Punt_dePas p: t.getPunt_dePasList())              //  recorre PUNTS DE PAS
//                    if (p.getNom().equals(punt)){
//                        p.setDesLikes(p.getDesLikes() + valoracio);
//                        return true;
//                    }
//        }
//        return false;
//    }
//}
