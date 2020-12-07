
package Control;
import DAO.*;
import Entidad.*;
import java.util.*;
import org.junit.Test;

public class Inicializacion {
    HistoricoDatos hd=new HistoricoDatos();
   TipoSensor ts= new TipoSensor();
   Sensor s= new Sensor();
    @Test
    public void initData(){
        
        ts.setTipo("LLAMA");
        ts.setNombre("Sensor de Llama");
        ts.setMinimo(-25);
        ts.setMaximo(85);
        
        s.setTipo(ts.getTipo());
        s.setUbicacion("Central");
        s.setIdsensor(1);
        
        System.out.println("Sensor");
        System.out.println(s.getIdsensor() + " " + s.getUbicacion() + " " + s.getTipo());
        System.out.println("\n Tipo de Sensor");
        System.out.println(ts.getTipo()+ " " + ts.getNombre() + " " + ts.getMinimo() + " " + ts.getMaximo());
    }
}
