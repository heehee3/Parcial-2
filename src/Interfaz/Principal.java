/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Entidad.*;
import DAO.*;
import Validar.*;
import static java.lang.Math.random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Camilo
 */
public class Principal extends javax.swing.JFrame {

   HistoricoDatos h=new HistoricoDatos();
   TipoSensor ts= new TipoSensor();
   Sensor s= new Sensor();
   Random num= new Random();
   Date date=new Date();
   validarTS temp=new validarTS();
   HistoricoDatosDAO daoHD= new HistoricoDatosDAO();
   SensorDAO daoS= new SensorDAO();
   TipoSensorDAO daoTS= new TipoSensorDAO();
   ArrayList<HistoricoDatos> listaH= new ArrayList<>();
    public Principal() {
        initComponents();
        inicializacion();
    }
    public void inicializacion(){
        
        ts.setTipo("LLAMA");
        ts.setNombre("Sensor de Llama");
        ts.setMinimo(34);
        ts.setMaximo(37.5);
        
        s.setTipo(ts.getTipo());
        s.setUbicacion("Central");
        s.setIdsensor(1);
        
        System.out.println("Sensor");
        System.out.println(s.getIdsensor() + " " + s.getUbicacion() + " " + s.getTipo());
        System.out.println("\n Tipo de Sensor");
        System.out.println(ts.getTipo()+ " " + ts.getNombre() + " (" + ts.getMinimo() + " - " + ts.getMaximo()+")");
        
    }
    
    public ArrayList<HistoricoDatos> lista (){       
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String url="jdbc:derby://localhost:1527/sensorAPP";
            Connection con= DriverManager.getConnection(url);
            String query="SELECT * FROM HISTORICODATOS ORDER BY id DESC";
            Statement st=con.createStatement();
            ResultSet rs= st.executeQuery(query);
            HistoricoDatos hd;
            while (rs.next()){
                hd= new HistoricoDatos(rs.getInt("IDSENSOR"),rs.getDouble("VALOR"),rs.getDate("FECHAHORA"), rs.getInt("ID"));
                listaH.add(hd); 
            }                
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return listaH;
        
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(58, 58, 58))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mostrarDatobtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarDatobtnActionPerformed
        lista();
        DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
        Object[] row= new Object[4];
        
        for(int i=0;i<5;i++){
            row[0]=listaH.get(i).getId();
            row[1]=listaH.get(i).getIdsensor();
            row[2]=listaH.get(i).getValor();
            row[3]=listaH.get(i).getFechaHora();
            model.addRow(row);           
        }
    }//GEN-LAST:event_mostrarDatobtnActionPerformed

    private void enviarDatobtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarDatobtnActionPerformed
        h.setIdsensor(s.getIdsensor());
        h.setValor(Math.random()*30 + 20);
        h.setFechaHora(date);
        if(temp.verificarValor(h.getValor())==true){
            System.out.println(h.getId()+ " " +h.getIdsensor()+ " " +h.getValor()+ " " +h.getFechaHora());
            daoHD.crear(h);
        }                    
    }//GEN-LAST:event_enviarDatobtnActionPerformed

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
    private javax.swing.JButton enviarDatobtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton mostrarDatobtn;
    // End of variables declaration//GEN-END:variables
}
