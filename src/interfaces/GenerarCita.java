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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class GenerarCita extends javax.swing.JInternalFrame {

    Connection cn = Conexion.getInstancia();
    DefaultComboBoxModel modelo1 = new DefaultComboBoxModel();
    DefaultComboBoxModel modelo2;

    /**
     * Creates new form GenerarCita
     */
    public GenerarCita() {
        initComponents();
        desactivarTextos();
        cargarEspecialidades();
    }

    public void desactivarTextos() {
        jtxtNombre.setEnabled(false);
    }
    
    public void limpiarCampos(){
        jtxtCedula.setText("");
        jtxtNombre.setText("");
        jcbxEspecialidad.setSelectedIndex(-1);
        jcbxDoctores.setSelectedIndex(-1);
        jffHora.setText("");
    }

    public void cargarEspecialidades() {
        try {

            String registro = new String();
            //
            String sql = "";
            sql = "select NOM_ESP from especialidades";
            //
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql); // RECORRER POR COLUMNAS
            while (rs.next()) {
                registro = rs.getString("NOM_ESP");
                modelo1.addElement(registro);
            }
            jcbxEspecialidad.setModel(modelo1);
            jcbxEspecialidad.setSelectedIndex(-1);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public String obtenerIdEspecialidades(String nombre) {
        try {
            String registro = new String();
            //
            String sql = "";
            sql = "select ID_ESP from especialidades where NOM_ESP = '" + nombre + "'";
            //
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql); // RECORRER POR COLUMNAS
            if (rs.next()) {
                return rs.getString("ID_ESP");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return null;
    }

    public void cargarDoctores(String id) {
        modelo2 = new DefaultComboBoxModel();
        try {
            String nombre = new String();
            String apellido = new String();
            String registro = new String();
            //
            String sql = "";
            sql = "select NOM_DOC, APE_DOC from doctores where ID_ESP_PER='" + id + "'";
            //
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql); // RECORRER POR COLUMNAS
            while (rs.next()) {
                nombre = rs.getString("NOM_DOC");
                apellido = rs.getString("APE_DOC");
                registro = nombre + " " + apellido;
                modelo2.addElement(registro);
            }
            jcbxDoctores.setModel(modelo2);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public String obtenerIdDoctores(String nombre) {
        String[] datos = nombre.split(" ");
        try {

            String registro = new String();
            //
            String sql = "";
            sql = "select ID_DOC from doctores where NOM_DOC = '" + datos[0] + "' and APE_DOC ='" + datos[1] + "'";
            //
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql); // RECORRER POR COLUMNAS
            if (rs.next()) {
                return rs.getString("ID_DOC");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return null;
    }

    public boolean comprobarPaciente(String ced) {
        try {
            String[] registro = new String[2];
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

    public void cambiarHorarios(String especialidad) {
        if (especialidad.equals("Cariologia")) {
            jlblHorarios.setText("Lunes a Jueves de 10:00 a 15:00");
        } else if (especialidad.equals("Odontopediatria")) {
            jlblHorarios.setText("Lunes a Viernes de 10:00 a 12:00");
        } else if (especialidad.equals("Implantologia")) {
            jlblHorarios.setText("Lunes a Sabado de 09:00 a 12:00");
        }
    }
    
    public boolean comprobarHorarios(String especialidad, String fecha) {
        String[] datos = fecha.split(" ");
        String[] hora = jffHora.getText().split(":");
        Integer hora2 = Integer.valueOf(hora[0]);
        Integer minutos = Integer.valueOf(hora[1]);
        if (especialidad.equals("Cariologia")) {
            if (datos[0].equals("Mon")||datos[0].equals("Tue")||datos[0].equals("Wed")||datos[0].equals("Thu")) {
                if (hora2==15) {
                    if (minutos==0) {
                        return true;
                    } else {
                        return false;
                    }
                }
                if(hora2>=10 && hora2<15){
                    return true;
                }
            } else {
                return false;
            }
        } else if (especialidad.equals("Odontopediatria")){
            if (datos[0].equals("Mon")||datos[0].equals("Tue")||datos[0].equals("Wed")||datos[0].equals("Thu")||datos[0].equals("Fri")) {
                if (hora2==12) {
                    if (minutos==0) {
                        return true;
                    } else {
                        return false;
                    }
                }
                if(hora2>=10 && hora2<12){
                    return true;
                }
            } else {
                return false;
            }
        } else if (especialidad.equals("Implantologia")){
            if (datos[0].equals("Mon")||datos[0].equals("Tue")||datos[0].equals("Wed")||datos[0].equals("Thu")||datos[0].equals("Fri")||datos[0].equals("Sat")) {
                if (hora2==12) {
                    if (minutos==0) {
                        return true;
                    } else {
                        return false;
                    }
                }
                if(hora2>=9 && hora2<12){
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }
    
    public Integer obtenerNumCita(){
        try {
            //
            String sql = "";
            sql = "select MAX(ID_CIT) from citas";
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
    
    public void generarCita(){
        try {
                Integer secuencia = obtenerNumCita() + 1;
                String cedulaPac = jtxtCedula.getText();
                String idDoc = obtenerIdDoctores(jcbxDoctores.getSelectedItem().toString());     
                String[] datos = jCalendar1.getDate().toString().split(" ");
                String fecHor = datos[5]+"-"+datos[1]+"-"+datos[2]+" "+jffHora.getText();
                String estado = "PENDIENTE";
                String costo = "35.00";
                //
                //
                String sql = "";
                sql = "insert into citas values(?,?,?,?,?,?)";
                //
                PreparedStatement psd = cn.prepareStatement(sql);
                psd.setInt(1, secuencia);
                psd.setString(2, cedulaPac);
                psd.setString(3, idDoc);
                psd.setString(4, fecHor);
                psd.setString(5, estado);
                psd.setString(6, costo);
                int n = psd.executeUpdate(); // devuelve el numero de filas afectadas
                System.out.println(n);
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Cita generada exitosamente");
                    limpiarCampos();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error. Contactate con el administador.");
                
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

        jtxtEspecialidad = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtxtCedula = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jcbxDoctores = new javax.swing.JComboBox();
        jtxtNombre = new javax.swing.JTextField();
        jcbxEspecialidad = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        jLabel5 = new javax.swing.JLabel();
        jlblHorarios = new javax.swing.JLabel();
        jffHora = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jbtnGenerarCita = new javax.swing.JButton();
        jbtnSalir = new javax.swing.JButton();

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Cédula:");

        jtxtCedula.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtCedulaFocusLost(evt);
            }
        });
        jtxtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtCedulaKeyTyped(evt);
            }
        });

        jLabel2.setText("Paciente:");

        jcbxDoctores.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbxDoctoresItemStateChanged(evt);
            }
        });

        jcbxEspecialidad.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbxEspecialidadItemStateChanged(evt);
            }
        });

        jLabel3.setText("Escoger Especialidad:");

        jLabel4.setText("Escoger Doctor:");

        jCalendar1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jCalendar1FocusLost(evt);
            }
        });

        jLabel5.setText("Horarios Disponibles:");

        jffHora.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));
        jffHora.setToolTipText("");
        jffHora.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jffHoraFocusGained(evt);
            }
        });

        jLabel7.setText("Hora:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(jCalendar1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jffHora, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jcbxDoctores, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtxtCedula, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtNombre, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbxEspecialidad, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblHorarios, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(122, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jcbxEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jcbxDoctores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jlblHorarios))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jffHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jCalendar1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jbtnGenerarCita.setText("Generar Cita");
        jbtnGenerarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGenerarCitaActionPerformed(evt);
            }
        });

        jbtnSalir.setText("Salir");
        jbtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(537, 537, 537))
            .addGroup(layout.createSequentialGroup()
                .addGap(291, 291, 291)
                .addComponent(jbtnGenerarCita)
                .addGap(260, 260, 260)
                .addComponent(jbtnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnGenerarCita)
                    .addComponent(jbtnSalir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jLabel6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jcbxEspecialidadItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbxEspecialidadItemStateChanged
        // TODO add your handling code here:
        String id = obtenerIdEspecialidades(String.valueOf(jcbxEspecialidad.getSelectedItem()));
        cargarDoctores(String.valueOf(id));
        cambiarHorarios(String.valueOf(jcbxEspecialidad.getSelectedItem()));
    }//GEN-LAST:event_jcbxEspecialidadItemStateChanged

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

    private void jtxtCedulaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtCedulaFocusLost
        // TODO add your handling code here:
        comprobarPaciente(jtxtCedula.getText());
    }//GEN-LAST:event_jtxtCedulaFocusLost

    private void jbtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose(); //desactiva la ventana
    }//GEN-LAST:event_jbtnSalirActionPerformed

    private void jcbxDoctoresItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbxDoctoresItemStateChanged
        // TODO add your handling code here:
        String id = obtenerIdDoctores(String.valueOf(jcbxDoctores.getSelectedItem()));
    }//GEN-LAST:event_jcbxDoctoresItemStateChanged

    private void jffHoraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jffHoraFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jffHoraFocusGained

    private void jCalendar1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jCalendar1FocusLost
        // TODO add your handling code here:
        jCalendar1.getDayChooser();
        System.out.println(jCalendar1.getDayChooser().toString());
    }//GEN-LAST:event_jCalendar1FocusLost

    private void jbtnGenerarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGenerarCitaActionPerformed
        // TODO add your handling code here:
        if (comprobarHorarios(jcbxEspecialidad.getSelectedItem().toString(), jCalendar1.getDate().toString())) {
            generarCita();
        } else {
            JOptionPane.showMessageDialog(null, "El horario escogido no es correcto.");
        }
    }//GEN-LAST:event_jbtnGenerarCitaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbtnGenerarCita;
    private javax.swing.JButton jbtnSalir;
    private javax.swing.JComboBox jcbxDoctores;
    private javax.swing.JComboBox jcbxEspecialidad;
    private javax.swing.JFormattedTextField jffHora;
    private javax.swing.JLabel jlblHorarios;
    private javax.swing.JTextField jtxtCedula;
    private javax.swing.JTextField jtxtEspecialidad;
    private javax.swing.JTextField jtxtNombre;
    // End of variables declaration//GEN-END:variables
}
