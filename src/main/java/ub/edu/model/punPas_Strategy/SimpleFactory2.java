package ub.edu.model.punPas_Strategy;

public enum SimpleFactory2 {
    INSTANCE;

    public  iLlistar obtenirClasseILlistar(String type) throws Exception {
        iLlistar nomClasse = null;

        try {
            String name = iLlistar.class.getPackage().getName();
            nomClasse = (iLlistar) Class.forName(name+"."+ type).newInstance();

            return nomClasse;
        } catch (Exception e) {
            throw new Exception("The Class type is unknown!");
        }
    }
}
