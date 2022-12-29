package ub.edu.controller;

import ub.edu.model.ModelFacade;
import ub.edu.model.Opinio;
import ub.edu.model.TripUB;
import ub.edu.model.Vot;
import ub.edu.resources.ResourcesFacade;

import java.util.ArrayList;
import java.util.List;

public class SessionMemory {
    private volatile static SessionMemory uniqueInstance;
    String correuPersona;
    Integer idGrup;
    Integer idRuta;
    Integer idTram; //TramEtapa o TramTrack
    Integer idLocalitzacio;
    Integer idPuntDePas;
    Integer idAllotjament;
    String opcioVeure;
    String tipusValoracio;
    String valoracio;
    List<Opinio> listOpinions;  // Al no poder escriure en BBDD, ho guardo aqui. Per no anar amunt i avall per paràmetres
    List<Vot> llistaVots;     // Al no poder escriure en BBDD, ho guardo aqui.
    int idTipus;


    public static SessionMemory getInstance(){
        if(uniqueInstance == null){
            synchronized (SessionMemory.class){
                if (uniqueInstance == null) {
                    uniqueInstance = new SessionMemory();
                    uniqueInstance.listOpinions = new ArrayList<>();
                    uniqueInstance.llistaVots = new ArrayList<>();
                }
            }
        }
        return uniqueInstance;
    }

    public List<Opinio> getListOpinions() {
        return listOpinions;
    }

    public void setListOpinions(List<Opinio> listOpinions) {
        this.listOpinions = listOpinions;
    }

    public String getValoracio() {
        return valoracio;
    }

    public void setValoracio(String valoracio) {
        this.valoracio = valoracio;
    }

    public String getTipusValoracio() {
        return tipusValoracio;
    }

    public void setTipusValoracio(String tipusValoracio) {
        this.tipusValoracio = tipusValoracio;
    }

    public List<Vot> getLlistaVots() {
        return llistaVots;
    }

    public void setLlistaVots(List<Vot> llistaVots) {
        this.llistaVots = llistaVots;
    }

    public int getIdTipus() {
        return idTipus;
    }

    public void setIdTipus(int idTipus) {
        this.idTipus = idTipus;
    }

    public String getOpcioVeure() {
        return opcioVeure;
    }

    public void setOpcioVeure(String opcioVeure) {
        this.opcioVeure = opcioVeure;
    }

    public String getCorreuPersona() {
        return correuPersona;
    }

    public void setCorreuPersona(String correuPersona) {
        this.correuPersona = correuPersona;
    }

    public Integer getIdGrup() {
        return idGrup;
    }

    public void setIdGrup(Integer idGrup) {
        this.idGrup = idGrup;
    }

    public Integer getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(Integer idRuta) {
        this.idRuta = idRuta;
    }

    public Integer getIdTram() {
        return idTram;
    }

    public void setIdTram(Integer idTram) {
        this.idTram = idTram;
    }

    public Integer getIdLocalitzacio() {
        return idLocalitzacio;
    }

    public void setIdLocalitzacio(Integer idLocalitzacio) {
        this.idLocalitzacio = idLocalitzacio;
    }

    public Integer getIdPuntDePas() {
        return idPuntDePas;
    }

    public void setIdPuntDePas(Integer idPuntDePas) {
        this.idPuntDePas = idPuntDePas;
    }

    public Integer getIdAllotjament() {
        return idAllotjament;
    }

    public void setIdAllotjament(Integer idAllotjament) {
        this.idAllotjament = idAllotjament;
    }
}
