/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class AtenderCita extends javax.swing.JInternalFrame {
    DefaultTableModel modelo;
    /**
     * Creates new form AtenderCita
     */
    public AtenderCita() {
        initComponents();
        cargarTablaCitas("");
        desactivarTextos();
        jtblResumen.setEnabled(false);
    }
    
    public void desactivarTextos() {
        jtxtNombre.setEnabled(false);
    }
    
    public void cargarTablaCitas(String cedula) {
        try {
            String[] titulos = {"Cita", "Doctor", "Fecha"};
            modelo = new DefaultTableModel(null, titulos);
            jtblResumen.setModel(modelo);
            jtblResumen.getTableHeader().setReorderingAllowed(false);
            jtblResumen.getTableHeader().setResizingAllowed(false);
            //
            String[] registros = new String[3];
            // obtener la tabla de la base de datos
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            //
            String sql = "";
            sql = "select ID_CIT, ID_DOC_ATE, FEC_CIT from citas where EST_CIT= 'PENDIENTE'AND ID_PAC_ATE like '%" + cedula + "%'order by ID_CIT";
            //
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql); // RECORRER POR COLUMNAS
            while (rs.next()) {
                registros[0] = rs.getString("ID_CIT");
                registros[1] = rs.getString("ID_DOC_ATE");
                registros[2] = rs.getString("FEC_CIT");
                modelo.addRow(registros);
            }
            if (modelo.getRowCount()==0) {
                JOptionPane.showMessageDialog(null, "Paciente no tiene citas pendientes");
            }
            jtblResumen.setModel(modelo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public boolean comprobarPaciente(String ced) {
        try {
            String[] registro = new String[2];
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            //
            String sql = "";
            sql = "select NOM_PAC, APE_PAC from pacientes where ID_PAC = '" + ced + "'";
            //
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql); // RECORRER POR COLUMNAS
            if (rs.next()) {
                jtxtNombre.setText(rs.getString("NOM_PAC") + " " + rs.getString("APE_PAC"));
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "El paciente con cédula " + ced + " no existe");
            return false;
        }
        return false;
    }
    
    public void confirmarAtenderCita(String idCita){
        JOptionPane.showConfirmDialog(null, "Desea atender la cita seleccionada?");
        if (JOptionPane.YES_OPTION==0) {
            atenderCita(idCita);
        }
    }
    
    public void atenderCita(String idCita){
        try {
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            String sql = "";
            sql = "UPDATE citas SET EST_CIT ='ATENDIDA' WHERE ID_CIT ='" + idCita + "'";
            PreparedStatement psd = cn.prepareStatement(sql);
            cargarTablaCitas("");
            int n = psd.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Se atendió correctamente");
                cargarTablaCitas("");
                generarResumenCita();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void generarResumenCita(){
        generarTratamiento();
        generarDiagnostico();
    }
    
    public void generarTratamiento(){
        try {
                String tratamiento = jtxtTratamiento.getText();
                String cedulaPac = jtxtCedula.getText();
                Integer numTra= obtenerNumTratamiento()+1;
                //
                Conexion cc = new Conexion();
                Connection cn = cc.conectar();
                //
                String sql = "";
                sql = "insert into tratamientos values(?,?,?)";
                //
                PreparedStatement psd = cn.prepareStatement(sql);
                psd.setInt(1, numTra);
                psd.setString(2, tratamiento);
                psd.setString(3, cedulaPac);
                int n = psd.executeUpdate(); // devuelve el numero de filas afectadas
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error. Contactate con el administador.");
            }
    }
    
    public void generarDiagnostico(){
        try {
                String diagnostico = jtxtDiagnostico.getText();
                String cedulaPac = jtxtCedula.getText();
                Integer numDia= obtenerNumDiagnostico()+1;
                //
                Conexion cc = new Conexion();
                Connection cn = cc.conectar();
                //
                String sql = "";
                sql = "insert into diagnosticos values(?,?,?)";
                //
                PreparedStatement psd = cn.prepareStatement(sql);
                psd.setInt(1, numDia);
                psd.setString(2, diagnostico);
                psd.setString(3, cedulaPac);
                int n = psd.executeUpdate(); // devuelve el numero de filas afectadas
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error. Contactate con el administador.");
            }
    }
    
     public Integer obtenerNumTratamiento(){
        try {
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            //
            String sql = "";
            sql = "select MAX(ID_TRA) from tratamientos";
            //
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql); // RECORRER POR COLUMNAS
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return 0;
    }
     
    public Integer obtenerNumDiagnostico(){
        try {
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            //
            String sql = "";
            sql = "select MAX(ID_DIAG) from diagnosticos";
            //
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql); // RECORRER POR COLUMNAS
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return 0;
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
        jLabel1 = new javax.swing.JLabel();
        jtxtCedula = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblResumen = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jtxtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jbtnSalir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtxtTratamiento = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtxtDiagnostico = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        jLabel1.setText("Cédula:");

        jtxtCedula.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtCedulaFocusLost(evt);
            }
        });
        jtxtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtCedulaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtCedulaKeyTyped(evt);
            }
        });

        jtblResumen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jtblResumen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblResumenMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtblResumen);

        jLabel2.setText("Seleccione una de las citas pendientes:");

        jLabel3.setText("Paciente:");

        jButton1.setText("Atender Cita");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jbtnSalir.setText("Salir");
        jbtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSalirActionPerformed(evt);
            }
        });

        jtxtTratamiento.setColumns(20);
        jtxtTratamiento.setRows(5);
        jScrollPane2.setViewportView(jtxtTratamiento);

        jtxtDiagnostico.setColumns(20);
        jtxtDiagnostico.setRows(5);
        jScrollPane3.setViewportView(jtxtDiagnostico);

        jLabel4.setText("Tratamiento:");

        jLabel5.setText("Diagnóstico:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addGap(62, 62, 62)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(117, 117, 117))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(34, 34, 34)
                                .addComponent(jbtnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(107, 107, 107))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(43, 43, 43))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(48, 48, 48)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jbtnSalir))
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtCedulaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtCedulaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCedulaFocusLost

    private void jtxtCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtCedulaKeyTyped
        // TODO add your handling code here:
        char key = evt.getKeyChar();
        if (jtxtCedula.getText().length() == 10) {
            evt.consume();
        }
        if (key < '0' || key > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_jtxtCedulaKeyTyped

    private void jtxtCedulaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtCedulaKeyReleased
        // TODO add your handling code here:
        if (jtxtCedula.getText().length()==10) {
            comprobarPaciente(jtxtCedula.getText());
            cargarTablaCitas(jtxtCedula.getText());
            jtblResumen.setEnabled(true);
        }
    }//GEN-LAST:event_jtxtCedulaKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        confirmarAtenderCita(modelo.getValueAt(jtblResumen.getSelectedRow(), 0).toString());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtblResumenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblResumenMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jtblResumenMouseClicked

    private void jbtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jbtnSalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbtnSalir;
    private javax.swing.JTable jtblResumen;
    private javax.swing.JTextField jtxtCedula;
    private javax.swing.JTextArea jtxtDiagnostico;
    private javax.swing.JTextField jtxtNombre;
    private javax.swing.JTextArea jtxtTratamiento;
    // End of variables declaration//GEN-END:variables
}
