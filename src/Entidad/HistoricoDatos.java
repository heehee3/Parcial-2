/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.util.*;
import java.io.*;
import javax.persistence.*;
import static javax.persistence.TemporalType.DATE;
/**
 *
 * @author Camilo
 */
@Entity
@Table(name="Historico")
public class HistoricoDatos implements Serializable{
    public int idsensor;
    public double valor;
    public String fechaHora;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public HistoricoDatos(int idsensor, double valor, String fechaHora, int id) {
        this.idsensor = idsensor;
        this.valor = valor;
        this.fechaHora = fechaHora;
        this.id = id;
    }
    
    public HistoricoDatos() {
    }

    public int getIdsensor() {
        return idsensor;
    }

    public void setIdsensor(int idsensor) {
        this.idsensor = idsensor;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
