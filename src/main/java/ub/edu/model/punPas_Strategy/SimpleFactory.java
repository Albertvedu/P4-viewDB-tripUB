package ub.edu.model.punPas_Strategy;



public enum SimpleFactory {
    INSTANCE;

    public ValorarPuntPas obtenirClasseAbstract(String type) throws Exception {
        ValorarPuntPas nomClasse = null;

        try {
            String name = ValorarPuntPas.class.getPackage().getName();
            nomClasse = (ValorarPuntPas) Class.forName(name+".extendClasses"+"."+type).newInstance();

            return nomClasse;
        } catch (Exception e) {
            throw new Exception("The Class type is unknown!");
        }
    }
}
