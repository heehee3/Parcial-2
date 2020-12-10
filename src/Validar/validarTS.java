/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validar;

import Entidad.TipoSensor;

public class validarTS {

    public validarTS() {
    }
    
    public void verificarTS (TipoSensor ts){
    }
    public boolean verificarValor (double x){
        return (x>30 && x<40);
    }
    public String verificarValorProc (double x){
        if(x<34){
            return "Alerta 1";
        }else if(x>34 && x<37.5){
            return "Alerta 2";
        }else{
            return "Alerta 3";
        }
    }
}
