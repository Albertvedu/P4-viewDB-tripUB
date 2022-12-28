package ub.edu.model.punPas_Strategy;



public abstract class ValorarPuntPas {
    protected iValorar ivalorar;
   // protected iLlistar illistar;

    public ValorarPuntPas() {
    }

    public void setValorarPuntPas(iValorar v){
        this.ivalorar = v;
    }
//    public void setLlistarPuntPas(iLlistar l){
//        this.illistar = l;
//    }
    protected boolean performaValorar(int idPuntPas, String valoracio, String correu_persona, Integer id_ruta) throws Exception {
        return ivalorar.valorar( idPuntPas, valoracio, correu_persona, id_ruta);
    }
//    protected List<PuntDePas> performaLlistar(Map<String, Ruta> rutaMap, String rutaNom){
//
//        return illistar.llistar(rutaMap, rutaNom);
//    }
//    protected List<Punt_dePas> performaLlistarAll(Map<String, Ruta> rutaMap, String rutaNom){
//        return illistar.llistarAll(rutaMap, rutaNom);
//
//    }
}
