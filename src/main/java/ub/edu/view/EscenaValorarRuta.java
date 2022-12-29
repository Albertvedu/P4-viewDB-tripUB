package ub.edu.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ub.edu.controller.RutesController;

public class EscenaValorarRuta extends Escena {

    public RadioButton radioButton_Group1_Like;
    public RadioButton radioButton_Group1_Dislike;
    public RadioButton radioButton_Group2_Text1;
    public RadioButton radioButton_Group2_Text2;
    public RadioButton radioButton_Group2_Text3;
    public RadioButton radioButton_Group2_Text4;
    public RadioButton radioButton_Group3_Text1;
    public RadioButton radioButton_Group3_Text2;
    public Button button_valorar;
    public Button button_cancel;
    public RadioButton radioButton_Group3_Text3;
    public RadioButton radioButton_Group3_Text4;
    public RadioButton radioButton_Group3_Text5;

    public RadioButton radioButton_G1_Text1;
    public RadioButton radioButton_G2_Text2;
    public RadioButton radioButton_G3_Text3;

    private String correu_persona;
    private Integer id_ruta;
    private RutesController rutesController;

    public EscenaValorarRuta() throws Exception {
        this.rutesController = RutesController.getInstance();
    }

    public void start(){
        System.out.println("Entro a Valorar Ruta");
        this.correu_persona=this.controller.getSessionMemory().getCorreuPersona();
        this.id_ruta=this.controller.getSessionMemory().getIdRuta();
        initialize_RB();
    }

    @FXML
    private void initialize_RB() {
        ToggleGroup group1 = new ToggleGroup();
        radioButton_Group1_Like.setToggleGroup(group1);
        radioButton_Group1_Dislike.setToggleGroup(group1);

        ToggleGroup group2= new ToggleGroup();
        radioButton_Group2_Text1.setToggleGroup(group2);
        radioButton_Group2_Text2.setToggleGroup(group2);
        radioButton_Group2_Text3.setToggleGroup(group2);
        radioButton_Group2_Text4.setToggleGroup(group2);


        ToggleGroup group3= new ToggleGroup();
        radioButton_Group3_Text1.setToggleGroup(group3);
        radioButton_Group3_Text2.setToggleGroup(group3);
        radioButton_Group3_Text3.setToggleGroup(group3);
        radioButton_Group3_Text4.setToggleGroup(group3);
        radioButton_Group3_Text5.setToggleGroup(group3);

        ToggleGroup group= new ToggleGroup();
        radioButton_G1_Text1.setToggleGroup(group);
        radioButton_G2_Text2.setToggleGroup(group);
        radioButton_G3_Text3.setToggleGroup(group);

        initDisabled();
        initObservers();

    }

    public void initDisabled(){
        radioButton_Group1_Like.setDisable(true);
        radioButton_Group1_Dislike.setDisable(true);
        radioButton_Group2_Text1.setDisable(true);
        radioButton_Group2_Text2.setDisable(true);
        radioButton_Group2_Text3.setDisable(true);
        radioButton_Group2_Text4.setDisable(true);
        radioButton_Group3_Text1.setDisable(true);
        radioButton_Group3_Text2.setDisable(true);
        radioButton_Group3_Text3.setDisable(true);
        radioButton_Group3_Text4.setDisable(true);
        radioButton_Group3_Text5.setDisable(true);

        radioButton_G1_Text1.setDisable(false);
        radioButton_G2_Text2.setDisable(false);
        radioButton_G3_Text3.setDisable(false);

    }

    public void initObservers(){
        radioButton_G1_Text1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                radioButton_Group1_Like.setDisable(false);
                radioButton_Group1_Dislike.setDisable(false);

                radioButton_Group2_Text1.setDisable(true);
                radioButton_Group2_Text2.setDisable(true);
                radioButton_Group2_Text3.setDisable(true);
                radioButton_Group2_Text4.setDisable(true);

                radioButton_Group3_Text1.setDisable(true);
                radioButton_Group3_Text2.setDisable(true);
                radioButton_Group3_Text3.setDisable(true);
                radioButton_Group3_Text4.setDisable(true);
                radioButton_Group3_Text5.setDisable(true);
            }
        });

        radioButton_G2_Text2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                radioButton_Group1_Like.setDisable(true);
                radioButton_Group1_Dislike.setDisable(true);

                radioButton_Group2_Text1.setDisable(false);
                radioButton_Group2_Text2.setDisable(false);
                radioButton_Group2_Text3.setDisable(false);
                radioButton_Group2_Text4.setDisable(false);

                radioButton_Group3_Text1.setDisable(true);
                radioButton_Group3_Text2.setDisable(true);
                radioButton_Group3_Text3.setDisable(true);
                radioButton_Group3_Text4.setDisable(true);
                radioButton_Group3_Text5.setDisable(true);
            }
        });

        radioButton_G3_Text3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                radioButton_Group1_Like.setDisable(true);
                radioButton_Group1_Dislike.setDisable(true);

                radioButton_Group2_Text1.setDisable(true);
                radioButton_Group2_Text2.setDisable(true);
                radioButton_Group2_Text3.setDisable(true);
                radioButton_Group2_Text4.setDisable(true);

                radioButton_Group3_Text1.setDisable(false);
                radioButton_Group3_Text2.setDisable(false);
                radioButton_Group3_Text3.setDisable(false);
                radioButton_Group3_Text4.setDisable(false);
                radioButton_Group3_Text5.setDisable(false);
            }
        });
    }

    public void onButtonValorarClick() throws Exception {
        //enviar la valoracion
        System.out.println("Entro a enviar una valoració");
        //TODO:
        // La idea es: guardar la valoracion en el modelo y actualizar la vista en caso necesario
        // Para ello:
        // 1-Recoger los datos seleccionados de la vista (cómo se recogen? ver codigo ejemplo de más abajo)
        // 2-Conectar con el controller pasandole los parametros necesarios para que
        // el controller el modelo (y opcionalmente se ejecute el metodo add de los respectivos DAO_TipusVot_DB + DAO_Vot_DB)
        // 3- Refrescar la vista si es necesario

        // Aqui tienes un código de ejemplo para ver cómo recoger el valor de un radio button

        if (radioButton_G1_Text1.isSelected()) {
            controller.getSessionMemory().setTipusValoracio("Like");
            if (radioButton_Group1_Like.isSelected()) {
                controller.getSessionMemory().setValoracio("Like");
                controller.getSessionMemory().setIdTipus(6);
            }
            else {
                controller.getSessionMemory().setValoracio("Unlike");
                controller.getSessionMemory().setIdTipus(10);
            }
            rutesController.valorarRuta();
        }else{
            //recoger valoración Opinión
            if (radioButton_G2_Text2.isSelected()){
                controller.getSessionMemory().setTipusValoracio("Opinio");
                if (radioButton_Group2_Text1.isSelected()) controller.getSessionMemory().setValoracio("No la faria mai");
                else if (radioButton_Group2_Text2.isSelected()) controller.getSessionMemory().setValoracio("La faré si no hi ha més remei");
                else if (radioButton_Group2_Text3.isSelected()) controller.getSessionMemory().setValoracio("Potser la faria");
                else if (radioButton_Group2_Text4.isSelected()) controller.getSessionMemory().setValoracio("M'encantaria fer-la");
            }else{
                if (radioButton_G3_Text3.isSelected()){
                    controller.getSessionMemory().setTipusValoracio("Estrelles");
                    if (radioButton_Group3_Text1.isSelected()){
                        controller.getSessionMemory().setValoracio("1");
                        controller.getSessionMemory().setIdTipus(1);
                    }
                    else if (radioButton_Group3_Text2.isSelected()){
                        controller.getSessionMemory().setValoracio("2");
                        controller.getSessionMemory().setIdTipus(2);
                    }
                    else if (radioButton_Group3_Text3.isSelected()){
                        controller.getSessionMemory().setValoracio("3");
                        controller.getSessionMemory().setIdTipus(3);
                    }
                    else if (radioButton_Group3_Text4.isSelected()){
                        controller.getSessionMemory().setValoracio("4");
                        controller.getSessionMemory().setIdTipus(4);
                    }
                    else if (radioButton_Group3_Text5.isSelected()){
                        controller.getSessionMemory().setValoracio("5");
                        controller.getSessionMemory().setIdTipus(5);
                    }
                }
                rutesController.valorarRutaEstrelles();
            }
        }

        //TODO: hacer efectiva la valoracion -> controller....
        /** Your Code Here **/

        stage.close();
    }

    public void onButtonCancelarClick(){
        //enviar la valoracion
        System.out.println("Entro en cancelar una valoracion");
        stage.close();
    }


}