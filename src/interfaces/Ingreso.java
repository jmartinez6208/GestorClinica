/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import interfaces.Conexion;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class Ingreso extends javax.swing.JFrame {

    public Ingreso() {
        initComponents();
        this.setIconImage(getIconImage());
        this.setLocationRelativeTo(null);
        this.setTitle("Ingreso de usuario");
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("imagenes/01.png"));
        return retValue;
    }
    
    public void LimpiarTextos() {
        jtxtUsuario.setText(null);
        jpswContraseña.setText(null);
    }

    public void LimpiarTextoContraseña() {
        jpswContraseña.setText(null);
    }

    public void ingresarUsuario() {
        int op;
        String usuario = jtxtUsuario.getText();
        String contraseña = jpswContraseña.getText();
        if (jtxtUsuario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el usuario (cédula)");
        } else if (jpswContraseña.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese la contraseña");
        } else {
            ValidarUsuario(usuario, contraseña);
        }

    }

    public void ValidarUsuario(String dato, String dato2) {
        try {
            //
            String[] campos = new String[2];
            // obtener la tabla de la base de datos
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            //
            String sql = "";
            sql = "select CLA_USU from usuarios where CED_USU = '" + dato + "'";
            //
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql); // RECORRER POR COLUMNAS
            while (rs.next()) {
                campos[0] = rs.getString("CLA_USU");
            }
            if (campos[0] == null) {
                JOptionPane.showMessageDialog(null, "Usuario no registrado");
                LimpiarTextos();
            } else if (campos[0].equals(dato2)) {
                abrirInterfaz();
            } else {
                LimpiarTextoContraseña();
                JOptionPane.showMessageDialog(null, "Contraseña Incorrecta");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void abrirInterfaz() {
        Citas ventana = new Citas();
        this.dispose();
        ventana.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtxtUsuario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jbtnIngresar = new javax.swing.JButton();
        jpswContraseña = new javax.swing.JPasswordField();
        jchbxMostrarCont = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 0, 0));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Clinica Odontológica");

        jLabel2.setText("Usuario:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Ingrese el usuario y contraseña.");

        jtxtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtUsuarioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtUsuarioKeyTyped(evt);
            }
        });

        jLabel4.setText("Contraseña:");

        jbtnIngresar.setText("Ingresar");
        jbtnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnIngresarActionPerformed(evt);
            }
        });

        jpswContraseña.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jpswContraseñaKeyTyped(evt);
            }
        });

        jchbxMostrarCont.setText("Mostrar Contraseña");
        jchbxMostrarCont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchbxMostrarContActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtxtUsuario)
                    .addComponent(jpswContraseña, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                .addGap(88, 88, 88))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jchbxMostrarCont))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(jbtnIngresar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jLabel1)))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtxtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jpswContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jchbxMostrarCont)
                .addGap(18, 18, 18)
                .addComponent(jbtnIngresar)
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnIngresarActionPerformed
        // TODO add your handling code here:
        ingresarUsuario();
    }//GEN-LAST:event_jbtnIngresarActionPerformed

    private void jtxtUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtUsuarioKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_jtxtUsuarioKeyReleased

    private void jtxtUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtUsuarioKeyTyped
        // TODO add your handling code here:
        char key = evt.getKeyChar();
        if (jtxtUsuario.getText().length() == 10) {
            evt.consume();
        }
        if (key < '0' || key > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_jtxtUsuarioKeyTyped

    private void jpswContraseñaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jpswContraseñaKeyTyped
        // TODO add your handling code here:
        char key = evt.getKeyChar();
        if (jpswContraseña.getText().length() == 4) {
            evt.consume();
        }
        if (key < 'A' || key > 'Z') {
            if (key < 'a' || key > 'z') {
                if (key < '0' || key > '9') {
                    evt.consume();
                }
            }
        }
    }//GEN-LAST:event_jpswContraseñaKeyTyped

    private void jchbxMostrarContActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchbxMostrarContActionPerformed
        // TODO add your handling code here:
        boolean a = true;
        if (jchbxMostrarCont.isSelected()) {
            jpswContraseña.setEchoChar((char) 0); //password = JPasswordField 
        } else {
            jpswContraseña.setEchoChar('•');
        }
    }//GEN-LAST:event_jchbxMostrarContActionPerformed

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
            java.util.logging.Logger.getLogger(Ingreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ingreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ingreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ingreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ingreso().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton jbtnIngresar;
    private javax.swing.JCheckBox jchbxMostrarCont;
    private javax.swing.JPasswordField jpswContraseña;
    private javax.swing.JTextField jtxtUsuario;
    // End of variables declaration//GEN-END:variables
}
