
package Interfaz;

import Entidad.*;
import DAO.*;
import Validar.*;
import static java.lang.Math.random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import javafx.scene.control.Alert;
import javafx.util.converter.LocalDateTimeStringConverter;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Camilo
 */
public class Principal extends javax.swing.JFrame {
    
   HistoricoDatos h=new HistoricoDatos();
   TipoSensor ts1= new TipoSensor();
   TipoSensor ts2= new TipoSensor();
   Sensor s= new Sensor();
   Random num= new Random();
   Date date=new Date();
   LocalDateTime dateSI;
   validarTS temp=new validarTS();
   validarTS parcial=new validarTS();
   String opcionNO=new String();
   String opcionSI=new String();
   HistoricoDatosDAO daoHD= new HistoricoDatosDAO();
   SensorDAO daoS= new SensorDAO();
   TipoSensorDAO daoTS= new TipoSensorDAO();
   ArrayList<HistoricoDatos> listaH= new ArrayList<>();
    ArrayList<Object> listaHSI= new ArrayList<>();
   SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   double prom;
   
    public Principal() {
        initComponents();
        inicializacion();
    }
    public void inicializacion(){
        SIbtn.setEnabled(false);
        NObtn.setEnabled(false);
        ts1.setTipo("LLAMA");
        ts1.setNombre("Sensor de Llama");
        ts1.setMinimo(34);
        ts1.setMaximo(37.5);
        ts1.setNumHoras(2);                                 
        
        s.setTipo(ts1.getTipo());
        s.setUbicacion("Central");
        s.setIdsensor(1);
        /*
        System.out.println("Sensor");
        System.out.println(s.getIdsensor() + " " + s.getUbicacion() + " " + s.getTipo());
        System.out.println("\n Tipo de Sensor");
        System.out.println(ts1.getTipo()+ " " + ts1.getNombre() + " (" + ts1.getMinimo() + " - " + ts1.getMaximo() + " - " + ts1.getPromedio()+ " - " + ts1.getNumHoras()+")");        
        daoTS.crear(ts1);*/
    }
    
    public ArrayList<HistoricoDatos> lista (){     
        listaH.clear();
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String url="jdbc:derby://localhost:1527/sensorAPP";
            Connection con= DriverManager.getConnection(url);
            String query="SELECT * FROM HISTORICOD ORDER BY id DESC";
            Statement st=con.createStatement();
            ResultSet rs= st.executeQuery(query);
            HistoricoDatos hd;
            while (rs.next()){
                hd= new HistoricoDatos(rs.getInt("IDSENSOR"),rs.getDouble("VALOR"),rs.getString("FECHAHORA"), rs.getInt("ID"));
                listaH.add(hd); 
            }                
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return listaH;
        
    }  
  
    public double listaSI (){     
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String url="jdbc:derby://localhost:1527/sensorAPP";
            Connection con= DriverManager.getConnection(url);
            String query="SELECT AVG(VALOR) AS \"PROMEDIO\" FROM APP.HISTORICOD";
            Statement st=con.createStatement();
            ResultSet rs= st.executeQuery(query);
            while (rs.next()){                
                String query1="SELECT AVG(VALOR) AS PROMEDIO FROM APP.HISTORICOD WHERE FECHAHORA BETWEEN '2020-12-09 18:32:13.480' AND CURRENT_TIMESTAMP";
                    Statement st1=con.createStatement();
                    ResultSet rs1= st1.executeQuery(query1);
                    while(rs1.next()){
                        prom= rs1.getDouble("PROMEDIO");
                    }               
            }                
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return prom;
        
    }
    
    
    public void llenarEnviar(){
        DefaultTableModel model= (DefaultTableModel) jTable1.getModel();   
        Object[] row= new Object[4];  
        
        int rowCount = model.getRowCount();
            for(int i = 0; i < rowCount; i++){
                model.removeRow(0);
            } 
        
        for(int i=0;i<5;i++){
            row[0]=listaH.get(i).getId();
            row[1]=listaH.get(i).getIdsensor();
            row[2]=listaH.get(i).getValor();
            row[3]=listaH.get(i).getFechaHora();
            model.addRow(row);   
            
        }
    }
    
    public void llenarProcsNO(){
        
        DefaultTableModel model= (DefaultTableModel) jTable1.getModel();   
        Object[] row= new Object[4];  
        
        int rowCount = model.getRowCount();
            for(int i = 0; i < rowCount; i++){
                model.removeRow(0);
            } 
        
        for(int i=0;i<1;i++){
            row[0]=listaH.get(i).getId();
            row[1]=listaH.get(i).getIdsensor();
            row[2]=listaH.get(i).getValor();
            row[3]=listaH.get(i).getFechaHora();
            model.addRow(row); 
            opcionNO=parcial.verificarValorProc(listaH.get(i).getValor());
            System.out.println(parcial.verificarValorProc(listaH.get(i).getValor()));
            if(opcionNO=="Alerta 1"){
                JOptionPane.showConfirmDialog(null,"Inferior al mínimo permitido: \n Id: "+listaH.get(i).getId() +"\n Id Sensor: "+ listaH.get(i).getIdsensor() +"\n Valor: "+ listaH.get(i).getValor() +"\n Fecha y Hora: "+ listaH.get(i).getFechaHora());
            }else if(opcionNO=="Alerta 2"){
                JOptionPane.showConfirmDialog(null,"Entre el mínimo y el máximo: \n Id: "+listaH.get(i).getId() +"\n Id Sensor: "+ listaH.get(i).getIdsensor() +"\n Valor:"+ listaH.get(i).getValor() +"\n Fecha y Hora: "+ listaH.get(i).getFechaHora());
            }else if(opcionNO=="Alerta 3"){
                JOptionPane.showConfirmDialog(null,"Superior al máximo permitido: \n Id: "+listaH.get(i).getId() +"\n Id Sensor: "+ listaH.get(i).getIdsensor() +"\n Valor: "+ listaH.get(i).getValor() +"\n Fecha y Hora: "+ listaH.get(i).getFechaHora());
            }                        
        }
    }
    public void llenarProcsSI(){
        
        for(int i=0;i<1;i++){
            
            opcionSI=parcial.verificarValorProc(prom);
            System.out.println(prom);
            if(opcionSI=="Alerta 1"){
                JOptionPane.showConfirmDialog(null,"Inferior al mínimo permitido: \n Valor promedio: "+ prom);
            }else if(opcionSI=="Alerta 2"){
                JOptionPane.showConfirmDialog(null,"Entre el mínimo y el máximo: \n Valor promedio: "+ prom);
            }else if(opcionSI=="Alerta 3"){
                JOptionPane.showConfirmDialog(null,"Superior al máximo permitido: \n Valor promedio: "+ prom);
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        enviarDatobtn = new javax.swing.JButton();
        mostrarDatobtn = new javax.swing.JButton();
        parcialbtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        panel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        SIbtn = new javax.swing.JButton();
        NObtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        enviarDatobtn.setText("Enviar Dato");
        enviarDatobtn.setFocusable(false);
        enviarDatobtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        enviarDatobtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        enviarDatobtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarDatobtnActionPerformed(evt);
            }
        });
        jToolBar1.add(enviarDatobtn);

        mostrarDatobtn.setText("Mostrar Datos");
        mostrarDatobtn.setFocusable(false);
        mostrarDatobtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mostrarDatobtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mostrarDatobtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarDatobtnActionPerformed(evt);
            }
        });
        jToolBar1.add(mostrarDatobtn);

        parcialbtn.setText("Procesamiento de Datos");
        parcialbtn.setFocusable(false);
        parcialbtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        parcialbtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        parcialbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parcialbtnActionPerformed(evt);
            }
        });
        jToolBar1.add(parcialbtn);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "IDsensor", "Valor", "Fecha y Hora"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Promedio:");

        SIbtn.setText("SI");
        SIbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SIbtnActionPerformed(evt);
            }
        });

        NObtn.setText("NO");
        NObtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NObtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(SIbtn)
                .addGap(38, 38, 38)
                .addComponent(NObtn)
                .addContainerGap(114, Short.MAX_VALUE))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(SIbtn)
                    .addComponent(NObtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(58, 58, 58))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mostrarDatobtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarDatobtnActionPerformed
        jTable1.setVisible(true);
        lista();
        llenarEnviar();
        
    }//GEN-LAST:event_mostrarDatobtnActionPerformed

    private void enviarDatobtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarDatobtnActionPerformed
        h.setIdsensor(s.getIdsensor());
        h.setValor(Math.random()*10 + 30);
        h.setFechaHora(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(date));
        if(temp.verificarValor(h.getValor())==true){
            System.out.println(h.getId()+ " " +h.getIdsensor()+ " " +h.getValor()+ " " +h.getFechaHora());
            daoHD.crear(h);                    
        }  
        jTable1.setVisible(false);
        SIbtn.setEnabled(false);
        NObtn.setEnabled(false);
    }//GEN-LAST:event_enviarDatobtnActionPerformed

    private void parcialbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parcialbtnActionPerformed
        SIbtn.setEnabled(true);
        NObtn.setEnabled(true);
    }//GEN-LAST:event_parcialbtnActionPerformed

    private void SIbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SIbtnActionPerformed
        listaSI();
        llenarProcsSI();
    }//GEN-LAST:event_SIbtnActionPerformed

    private void NObtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NObtnActionPerformed
        lista();
        llenarProcsNO();
    }//GEN-LAST:event_NObtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton NObtn;
    private javax.swing.JButton SIbtn;
    private javax.swing.JButton enviarDatobtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton mostrarDatobtn;
    private javax.swing.JPanel panel2;
    private javax.swing.JButton parcialbtn;
    // End of variables declaration//GEN-END:variables
}
