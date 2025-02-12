package ub.edu.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import ub.edu.model.SubjectModel;
import ub.edu.model.SubjectModelPuntPas;
import ub.edu.model.Textes;
import ub.edu.model.TripUB;
import ub.edu.model.punPas_Strategy.PuntPasStrategy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class EscenaMain extends Escena implements ObserverView, ObserverViewPuntPas {
    private static final double ESPAI_ENTRE_BOTONS = 30;

    public Button ruta_btn;
    public Button button_izq_resetFiltres_main;
    public Button button_der_resetFiltres_main;
    public AnchorPane rutes_pane;
    public TableView tableTop5Valorades;
    public TableColumn nomColumn;
    public TableColumn valueColumn;
    public Text textPrincipal;
    public Text textValoracions;
    public ComboBox comboBox_main_grup;
    public ComboBox button_der_ruta_puntpas_main;
    public ImageView image_main;
    public Button button1_abajo_main;
    public Button button2_abajo_main;
    public Button button3_abajo_main;
    public Popup popup;
    private Label label;


    @FXML
    public ComboBox comboBox_main_comarca;
    public ComboBox comboBox_main_localitat;

    private String correuPersona;
    private Integer id_grup;
    private SubjectModel tripUB;
    private SubjectModelPuntPas puntdePasStartegy;
    private Textes textes;


    public EscenaMain() throws Exception {
        this.textes = Textes.getInstance();

    }

    public void registrarseObservador(SubjectModel tripUB, SubjectModelPuntPas subjectModelPuntPas) {
        this.tripUB = tripUB ;
        this.tripUB.registerObserver(this);
        this.puntdePasStartegy = subjectModelPuntPas;
        this.puntdePasStartegy.registerObserver(this);

    }

    public void start() throws Exception {
        this.correuPersona=this.controller.getSessionMemory().getCorreuPersona();
        asignarTextoPrincipal_Correo_y_Grupo(correuPersona);
        asignarItemsVeureValoracionsPer();
        asignarimagen();
        popularComboBoxComarques(); //success
        popularComboBoxLocalitat(); //success
        popularRutesPerNom();
        registrarseObservador(TripUB.getInstance(), PuntPasStrategy.getInstance());                // Cridar per REGISTRARSE COM OBSERVADOR

        popularTopXYZValorades("Like");  // Inicia Valoracions per Punt de pas i Likes
    }

    public void asignarItemsVeureValoracionsPer(){
        button_der_ruta_puntpas_main.getItems().add("Rutes");
        button_der_ruta_puntpas_main.getItems().add("Punt de Pas");
        button_der_ruta_puntpas_main.setValue("Punt de Pas");
        this.observable_comboBox_ruta_puntpas_main();
    }
    public void asignarTextoPrincipal_Correo_y_Grupo(String correuPersona) throws Exception {
        //Paso1. Teniendo el correo de la persona, buscar el id del grupo.
        ArrayList<HashMap<Object,Object>> resuListGrupHashMap= grupsController.getAllGrupsPerPersona(correuPersona);
        //Paso2. Recoger el grupo por su id.
        Integer firstidCB=null;
        Integer lastidCB=null;
        String firstnameCB=null;
        Boolean flagGrup=false;

        if(resuListGrupHashMap.size()>0){
            firstidCB = (Integer) resuListGrupHashMap.get(0).get("id");
            firstnameCB = (String) resuListGrupHashMap.get(0).get("nom");

            for (HashMap<Object,Object> grup:resuListGrupHashMap){
                comboBox_main_grup.getItems().add(grup.get("nom"));
                lastidCB= (Integer) grup.get("id");
                flagGrup=true;
            }
        }else{
            firstnameCB="Empty Groups";
            comboBox_main_grup.setDisable(true);
        }
        if(flagGrup){
            this.controller.getSessionMemory().setIdGrup(lastidCB);
        }
        comboBox_main_grup.setPromptText(firstnameCB);
        textPrincipal.setText("TripUB: "+correuPersona);
        this.observable_comboBox_main_grup();
    }

    public void asignarimagen() throws FileNotFoundException {
        FileInputStream input = new FileInputStream("./src/main/view-resources/ub/edu/static-resources/logo8.png");
        Image image = new Image(input);
        image_main.setImage(image);
    }


     @Override
    public void update(List<HashMap<Object, Object>> listRutes) {
        popularRutesPerLocalitat(listRutes);
    }

    @Override
    public void update(Map<String, Integer> sortTopLike, String text) {
            //a cada iteracion seleccionamos la tabla y sus items, y le añadimos una
            //nueva instancia de la clase interna DataTop pasandole los parámetros en el constructor

        // Borrar Items anteriors...
        int elementsAborrar = tableTop5Valorades.getItems().size();
        for (int i = elementsAborrar-1; i >=0 ; i--)
            tableTop5Valorades.getItems().remove(i);

        // Text del titol del que mostra la finestra Like-Unlike-Estrelles o Per grups
        if (text.equals("Estrelles") && controller.getSessionMemory().getOpcioVeure().equals("Rutes"))    //  Mostra text menu
            textValoracions.setText("Top-5 " + text + " del Grup");
        else textValoracions.setText("Top-5 " + text);

        // Mostrar items nous
        if (sortTopLike == null)
            tableTop5Valorades.getItems().add(new DataTop("Aquesta busqueda no té resultats", 0));
        Iterator<Map.Entry<String, Integer>> iterator = sortTopLike.entrySet().iterator();

        while (iterator.hasNext()) {        // Recorre Map Relacio Comarca i Localitats
            Map.Entry entry = (Map.Entry) iterator.next();
            tableTop5Valorades.getItems().add(
                    new DataTop((String) entry.getKey(), (Integer) entry.getValue()));
        }
    }

    /*
    * Aquesta inner Class la associarem als items de la taula en el mètode popularTopActivitatsFetes
    * Seguir aquesta estructura per popular taules
    * */
    public static class DataTop {
        //Cal deixar aquests atributs com finals per poder popular la taula quan el mètode  la cridi
        private final SimpleStringProperty nom;
        private final SimpleStringProperty value;
        DataTop(String nom_param, Integer value_param){
            this.nom = new SimpleStringProperty(nom_param);
            this.value = new SimpleStringProperty(value_param.toString());
        }
        //métodos getters
        public String getNom() {
            return nom.get();
        }
        public String getValue() {
            return value.get();
        }
    }

    private void popularTopXYZValorades(String criteri) throws Exception {
        //La clase interna DataTop está justo arriba de esta función.
        nomColumn.setCellValueFactory(new PropertyValueFactory<DataTop, String>("nom"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<DataTop, String>("value"));
        // TODO: Dependiendo del grupo debereis mostrar unos datos u otros

        puntdePasController.getTop5Likes( controller.getSessionMemory().getOpcioVeure(), criteri);

    }
    private void popularTopXYZEstrelles() throws Exception {
        //La clase interna DataTop está justo arriba de esta función.
        nomColumn.setCellValueFactory(new PropertyValueFactory<DataTop, String>("nom"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<DataTop, String>("value"));
        // TODO: Dependiendo del grupo debereis mostrar unos datos u otros
        //por el momento ponemos unos datos dummy para que veais como se añaden

        if(controller.getSessionMemory().getOpcioVeure().equals("Rutes"))
            puntdePasController.getTopEstrellesRuta(controller.getSessionMemory().getOpcioVeure(),"Estrelles");
        else
            puntdePasController.getTopEstrellesPuntPas(controller.getSessionMemory().getOpcioVeure(),"Estrelles");

    }

    private void popularRutesPerNom(){
        List<HashMap<Object,Object>> listaRutas = rutesController.getAllRutesPerNom();
        if(listaRutas == null || listaRutas.size()==0){
            return;
        }
        List<Node> excursionsPaneChildren = rutes_pane.getChildren();

        double width = ruta_btn.getWidth();
        double height = ruta_btn.getHeight();
        double layoutX = ruta_btn.getLayoutX();
        double layoutY = ruta_btn.getLayoutY();
        //Instanciem un botó per cada ruta

        String text;
        Button new_btn;
        Integer id;
        String nom;
        for (HashMap<Object,Object> ruta:listaRutas) {
            id = (Integer) ruta.get("id");
            nom = (String) ruta.get("nom");

            text = id.toString()+" | "+nom;
            new_btn = createRutaButton(id, text, width, height, layoutX, layoutY);
            excursionsPaneChildren.add(new_btn);
            layoutY += ESPAI_ENTRE_BOTONS;
        }
        //Actualitzem la mida del pane que conté els botons perque es pugui fer scroll cap abaix si hi ha més botons dels que caben al pane
        rutes_pane.setPrefHeight(layoutY);
        //Esborrem excursio_btn, que l'utilitzavem únicament com a referència per la mida dels botons
        excursionsPaneChildren.remove(ruta_btn);
    }
    private void popularRutesPerLocalitat(List<HashMap<Object, Object>> listaRutas){
        if(listaRutas == null || listaRutas.size()==0){
            return;
        }
        List<Node> excursionsPaneChildren = rutes_pane.getChildren();

        double width = ruta_btn.getWidth();
        double height = ruta_btn.getHeight();
        double layoutX = ruta_btn.getLayoutX();
        double layoutY = ruta_btn.getLayoutY();
        //Instanciem un botó per cada ruta

        String text;
        Button new_btn;
        Integer id;
        String nom;
        for (HashMap<Object,Object> ruta:listaRutas) {
            id = (Integer) ruta.get("id");
            nom = (String) ruta.get("nom");

            text = id.toString()+" | "+nom;
            new_btn = createRutaButton(id, text, width, height, layoutX, layoutY);
            excursionsPaneChildren.add(new_btn);
            layoutY += ESPAI_ENTRE_BOTONS;
        }
        //Actualitzem la mida del pane que conté els botons perque es pugui fer scroll cap abaix si hi ha més botons dels que caben al pane
        rutes_pane.setPrefHeight(layoutY);
        //Esborrem excursio_btn, que l'utilitzavem únicament com a referència per la mida dels botons

        for (int i = 0; i < (excursionsPaneChildren.size() - listaRutas.size()); i++) {
            excursionsPaneChildren.remove(i);
            i--;
        }
        excursionsPaneChildren.remove(ruta_btn);
    }

    /*
    * Crea un botó de dimensions width x height, colocat a la posició (layoutX, layoutY)
    * */
    private Button createRutaButton(Integer id_ruta, String text, double width, double height, double layoutX, double layoutY){
        Button new_btn = new Button();
        new_btn.setPrefWidth(width);
        new_btn.setPrefHeight(height);
        new_btn.setText(text);
        new_btn.setLayoutX(layoutX);
        new_btn.setLayoutY(layoutY);
        new_btn.setAlignment(Pos.BASELINE_LEFT);
        //asignamos el evento del click que ejecutará mostrar la ventana con detalles de la ruta
        new_btn.setOnMouseClicked(event ->
        {
            if (event.getButton() == MouseButton.PRIMARY)
            {
                try {
                    mostrarFinestraRuta(id_ruta);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return new_btn;
    }

    private void mostrarFinestraRuta(Integer id) throws Exception {
        //Nova finestra
        Escena escena = EscenaFactory.INSTANCE.creaEscena("rutaDetalls-view", "Detalls ruta "+String.valueOf(id));
        EscenaRutaDetalls escenaRutaDetalls = ((EscenaRutaDetalls)escena);
        escenaRutaDetalls.setController();
        this.controller.getSessionMemory().setIdRuta(id);
        escenaRutaDetalls.start();
    }

    public void observable_comboBox_main_grup(){
        comboBox_main_grup.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue classObject, String oldValue, String newValue) {

                if (button_der_ruta_puntpas_main.getValue().equals("Rutes"))
                    try {
                        Integer idGrup = grupsController.getIdGrupByName(newValue);
                        controller.getSessionMemory().setIdGrup(idGrup);
                        controller.getSessionMemory().setOpcioVeure("Rutes");
                        popularTopXYZEstrelles();
                        System.out.println("Filtrar contenido por grup: id: "+idGrup+" name: "+newValue);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                else textes.mostrarPopUp("Opció només disponible per a Valoracions per Rutes");
            }
        });
    }

    public void observable_comboBox_ruta_puntpas_main(){
        controller.getSessionMemory().setOpcioVeure("Punt de Pas"); // Iniciar el ComboBox en Rutes
        button_der_ruta_puntpas_main.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue classObject, String oldValue, String newValue) {
                try {
                    controller.getSessionMemory().setOpcioVeure(newValue);   // Setter del valor pel qual vol llistar valoracions
                    popularTopXYZValorades("Like");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Filtrar contenido per " +newValue);

            }
        });
    }

    public void popularComboBoxComarques() throws Exception {
        List<HashMap<Object,Object>> comarques = controller.getAllComarques();
        System.out.println("comarques: "+comarques);
        String s = comboBox_main_comarca.getPromptText();
        comboBox_main_comarca.getItems().add(0,s);

        Integer id=null;
        String nom=null;
        for (HashMap<Object,Object> comarca: comarques) {
            if(comarca.get("id")!=null){id=(Integer) comarca.get("id");}
            if(comarca.get("nom")!=null){nom=(String) comarca.get("nom");}
            //el index del comboBox debe empezar por 0,
            //pero mas arriba en el 0 hemos añadido el default
            //el que hará de reset del filtro
            //IMPORTANTE: la lista debe estar ordenada por sus ids (ver como se ordenda para popularComboBoxLocalitat)
            //comboBox_main_comarca.getItems().add(id,nom);
            //si pasamos del id, no nos petará en caso de que no esté ordenada
            comboBox_main_comarca.getItems().add(nom);
        }

        //añadir el listener del combobox
        comboBox_main_comarca.valueProperty().addListener(new ChangeListener<String>() {
            //OPCIÓN-1 -> asignar listener para que se ejecute cuando detecte el cambio
            @Override public void changed(ObservableValue classObject, String oldValue, String newValue) {
                System.out.println("TODO: Filtrar rutas por Comarca: "+newValue);
                //TODO: extensión de popular la lista de Rutas
            }
        });
    }

    public void popularComboBoxLocalitat() throws Exception {
        List<HashMap<Object,Object>> localitats = localitatController.getAllLocalitats();
        System.out.println("localitats: "+localitats);
        String s = comboBox_main_localitat.getPromptText();
          comboBox_main_localitat.getItems().add(0,s);

        Integer id=null;
        String nom=null;
        for (HashMap<Object,Object> loc: localitats) {
            if(loc.get("id")!=null){
                id=(Integer) loc.get("id");
            }
            if(loc.get("nom")!=null){nom=(String) loc.get("nom");}
            //el index del comboBox debe empezar por 0
            //pero mas arriba en el 0 hemos añadido el default
            //el que hará de reset del filtro
            // IMPORTANTE: la lista debe estar ordenada por sus ids
            //comboBox_main_localitat.getItems().add(id,nom);
            //si pasamos del id, no nos petará en caso de que no esté ordenada
            comboBox_main_localitat.getItems().add(nom);
        }

        //añadir el listener del combobox
        comboBox_main_localitat.valueProperty().addListener(new ChangeListener<String>() {
            //OPCIÓN-1 -> asignar listener para que se ejecute cuando detecte el cambio
            @Override public void changed(ObservableValue classObject, String oldValue, String newValue) {
                System.out.println("TODO: Filtrar rutas por Localitat: "+newValue);
                //TODO: extensión de popular la lista de Rutas

                rutesController.getAllRutesPerLocalitat(newValue);

            }
        });
    }
    public void onButtonIzqResetFiltresClick(){
        this.popularRutesPerNom();

        //reset filtro ComboBox1 Comarcas
        //Object stringNameOfComboBoxComarcas = comboBox_main_comarca.getValue();
        //System.out.println("stringNameOfComboBoxComarcas:"+stringNameOfComboBoxComarcas);
        //actualizar el combobox por ese stringValue
        Object str_default_ComboBox_comarcas = comboBox_main_comarca.getPromptText();
        //System.out.println("str_default_ComboBox_comarcas:"+str_default_ComboBox_comarcas);
        comboBox_main_comarca.setValue(str_default_ComboBox_comarcas);

        //reset filtro ComboBox2 Localitat
        //Object stringNameOfComboBoxLocalitat = comboBox_main_localitat.getValue();
        //System.out.println("stringNameOfComboBoxLocalitat:"+stringNameOfComboBoxLocalitat);
        //actualizar el combobox por ese stringValue
        Object str_default_ComboBox_localitat = comboBox_main_localitat.getPromptText();
        //System.out.println("str_default_ComboBox_localitat:"+str_default_ComboBox_localitat);
        comboBox_main_localitat.setValue(str_default_ComboBox_localitat);
    }

    public void onButtonDerResetFiltresClick(){
        //OPCIÓN-2 -> asignar una funcion asociada al click del botón
        System.out.println("TODO: hacer reset filtros derecha en EscenaMain");
        //TODO
        int elementsAborrar = tableTop5Valorades.getItems().size();
        for (int i = elementsAborrar-1; i >=0 ; i--)
            tableTop5Valorades.getItems().remove(i);
    }

    public void onButton1Click() throws Exception {
        popularTopXYZValorades("Like");
    }
    public void onButton2Click() throws Exception {
        popularTopXYZValorades("Unlike");
    }
    public void onButton3Click() throws Exception {
        popularTopXYZEstrelles();
    }
}
