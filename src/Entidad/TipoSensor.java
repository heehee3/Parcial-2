/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;
import java.io.*;
import javax.persistence.*;
/**
 *
 * @author Camilo
 */
 @Entity
 @Table(name="TipoSensor")
public class TipoSensor implements Serializable {
    @Id
    public String tipo;
    public String nombre;
    public double minimo;
    public double maximo;

    public TipoSensor() {
        
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getMinimo() {
        return minimo;
    }

    public void setMinimo(double minimo) {
        this.minimo = minimo;
    }

    public double getMaximo() {
        return maximo;
    }

    public void setMaximo(double maximo) {
        this.maximo = maximo;
    }
    
    
}
