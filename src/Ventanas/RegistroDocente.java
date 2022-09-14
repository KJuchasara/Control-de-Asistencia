package Ventanas;

import java.sql.Connection;
import java.awt.HeadlessException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class RegistroDocente extends javax.swing.JFrame {

    //   PreparedStatement ps;
    ResultSet rs;
    private static Connection con = null;
    private static Statement stmt;
    PreparedStatement ps;
    // Declaramos los datos de conexion a la bd
    String driver = "com.mysql.cj.jdbc.Driver";
    private static final String user = "root";
    private static final String pass = "123456";
    private static final String url = "jdbc:mysql://localhost:3306/control";

    public void limpiarcajas() {
        this.txtNombre.setText(null);
        this.txtApellido.setText(null);
        this.txtCorreo.setText(null);
        this.txtContraseña.setText(null);
    }

    public static Connection coneccioBD() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
         } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }

    public RegistroDocente() {
        initComponents();
        txtId.setVisible(false);
        this.setResizable(false);
        this.setTitle("Registro Docentes");
        this.setLocationRelativeTo(null);
        btnRegistrar.setEnabled(false);// BOTON REGISTRAR SE ENCUENTRA APAGADO desde el inicio
        validar();//LLAMAR LA FUNCION CREADA
    }
///////     FUNION CREADA PARA VALIDAR ESPACIOS    ////////

    public void validar() { //Funcion que permite el llenado de los espacios de registro
        /////////    SI EL CAMPO DE NOMBRE ESTA VACIO   ///////////7       
        if (txtNombre.getText().isEmpty()) {
            lblNombre.setText("*Campo Requerido");
        } else {
            lblNombre.setText("");
        }
        /////////    SI EL CAMPO DE APELLIDO ESTA VACIO   ///////////7            
        if (txtApellido.getText().isEmpty()) {
            lblApellido.setText("*Campo Requerido");
        } else {
            lblApellido.setText("");
        }
        /////////    SI EL CAMPO DE CORRE0 ESTA VACIO   ///////////7           
        if (txtCorreo.getText().isEmpty()) {
            lblCorreo.setText("*Campo Requerido");
        } else if (!txtCorreo.getText().contains("@") || !txtCorreo.getText().contains(".")) {// PARA QUE SEA OBLIGATORIO TENER @ Y PUNTO
            lblCorreo.setText("*Correo Invalido");
        } else {
            lblCorreo.setText("");
        }
        /////////    SI EL CAMPO DE CONTRASEÑA ESTA VACIO   ///////////7    
        if (txtContraseña.getText().isEmpty()) {
            lblContraseña.setText("*Campo Requerido");
        } else {
            lblContraseña.setText("");
        }
        /////////    SI TODOS LOS CAMPOS ESTA VACIO   ///////////7            
        if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() || txtCorreo.getText().isEmpty()
                || txtContraseña.getText().isEmpty() || lblCorreo.getText().equals("*Correo Invalido")) {
            btnRegistrar.setEnabled(false);// BOTON REGISTRAR SE ENCUENTRA APAGADO
        } else {
            btnRegistrar.setEnabled(true);// BOTON REGISTRAR SE ENCUENTRA ENCENDIDO
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelNombre = new javax.swing.JLabel();
        jLabelApellido = new javax.swing.JLabel();
        jLabelCorreo = new javax.swing.JLabel();
        jLabelContraseña = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        txtContraseña = new javax.swing.JPasswordField();
        jButtonVolver = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnTablaDocentes = new javax.swing.JButton();
        jButtonBuscar = new javax.swing.JButton();
        lblContraseña = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblApellido = new javax.swing.JLabel();
        lblCorreo = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabelFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelNombre.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelNombre.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombre.setText("NOMBRE:");
        getContentPane().add(jLabelNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(51, 50, -1, 30));

        jLabelApellido.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelApellido.setForeground(new java.awt.Color(255, 255, 255));
        jLabelApellido.setText("APELLIDO:");
        getContentPane().add(jLabelApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 90, -1, 30));

        jLabelCorreo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelCorreo.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCorreo.setText("CORREO:");
        getContentPane().add(jLabelCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 130, -1, 30));

        jLabelContraseña.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelContraseña.setForeground(new java.awt.Color(255, 255, 255));
        jLabelContraseña.setText("CONTRASEÑA:");
        getContentPane().add(jLabelContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 170, -1, 30));

        txtNombre.setBackground(new java.awt.Color(0, 153, 153));
        txtNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(255, 255, 255));
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        getContentPane().add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 270, 30));

        txtApellido.setBackground(new java.awt.Color(0, 153, 153));
        txtApellido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtApellido.setForeground(new java.awt.Color(255, 255, 255));
        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtApellidoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoKeyTyped(evt);
            }
        });
        getContentPane().add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 270, 30));

        txtCorreo.setBackground(new java.awt.Color(0, 153, 153));
        txtCorreo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCorreo.setForeground(new java.awt.Color(255, 255, 255));
        txtCorreo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCorreoKeyReleased(evt);
            }
        });
        getContentPane().add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 270, 30));

        txtContraseña.setBackground(new java.awt.Color(0, 153, 153));
        txtContraseña.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtContraseña.setForeground(new java.awt.Color(255, 255, 255));
        txtContraseña.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtContraseñaKeyReleased(evt);
            }
        });
        getContentPane().add(txtContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 270, 30));

        jButtonVolver.setBackground(new java.awt.Color(102, 102, 255));
        jButtonVolver.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonVolver.setForeground(new java.awt.Color(255, 255, 255));
        jButtonVolver.setText("Volver");
        jButtonVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVolverActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 0, -1, -1));

        btnRegistrar.setBackground(new java.awt.Color(51, 51, 255));
        btnRegistrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setText("Registrar");
        btnRegistrar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        getContentPane().add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, 30));

        btnActualizar.setBackground(new java.awt.Color(51, 51, 255));
        btnActualizar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("Actualizar");
        btnActualizar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        getContentPane().add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, -1, 30));

        btnEliminar.setBackground(new java.awt.Color(51, 51, 255));
        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 230, -1, 30));

        btnTablaDocentes.setBackground(new java.awt.Color(51, 51, 255));
        btnTablaDocentes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnTablaDocentes.setForeground(new java.awt.Color(255, 255, 255));
        btnTablaDocentes.setText("Tabla Docentes");
        btnTablaDocentes.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTablaDocentes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTablaDocentesActionPerformed(evt);
            }
        });
        getContentPane().add(btnTablaDocentes, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 230, -1, 30));

        jButtonBuscar.setBackground(new java.awt.Color(51, 51, 255));
        jButtonBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonBuscar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 30));

        lblContraseña.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        lblContraseña.setForeground(new java.awt.Color(204, 0, 0));
        getContentPane().add(lblContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 170, 150, 30));

        lblNombre.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(204, 0, 0));
        getContentPane().add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 47, 150, 30));

        lblApellido.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        lblApellido.setForeground(new java.awt.Color(204, 0, 0));
        getContentPane().add(lblApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 90, 150, 30));

        lblCorreo.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        lblCorreo.setForeground(new java.awt.Color(204, 0, 0));
        getContentPane().add(lblCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, 150, 30));

        txtId.setEditable(false);
        txtId.setEnabled(false);
        getContentPane().add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 0, 30, 20));

        jLabelFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Fondo4.jpg"))); // NOI18N
        getContentPane().add(jLabelFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 647, 270));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        try {
            con = coneccioBD();
            ps = (PreparedStatement) con.prepareStatement("INSERT INTO docentes (nombre, apellido, correo, contraseña) VALUES(?,?,?,?) ");
            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtApellido.getText());
            ps.setString(3, txtCorreo.getText());
            ps.setString(4, txtContraseña.getText());
            int res = ps.executeUpdate();
            if (res > 0) {
                JOptionPane.showMessageDialog(null, "Usuario Registrado");
            } else {
                JOptionPane.showMessageDialog(null, "Error al Registrar");
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println(e);
        }
        limpiarcajas();
    }//GEN-LAST:event_btnRegistrarActionPerformed


    private void jButtonVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVolverActionPerformed
        //////      AL PRECIONAR EL BOTON ENVIA A LA PESTAÑA AUTENTICACION     ///////      
        Autenticacion ventana = new Autenticacion();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonVolverActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        try {
            con = coneccioBD();
            ps = (PreparedStatement) con.prepareStatement("UPDATE docentes SET nombre=?, apellido=?, correo=?, contraseña=? WHERE id=?");
            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtApellido.getText());
            ps.setString(3, txtCorreo.getText());
            ps.setString(4, txtContraseña.getText());
            ps.setString(5, txtId.getText());
            int res = ps.executeUpdate();
            if (res > 0) {
                JOptionPane.showMessageDialog(null, "Usuario Actualizado");
            } else {
                JOptionPane.showMessageDialog(null, "Error al Actualizar");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        limpiarcajas();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        try {
            con = coneccioBD();
            ps = (PreparedStatement) con.prepareStatement("DELETE FROM docentes WHERE id=?");
            ps.setInt(1, Integer.parseInt(txtId.getText()));
            int res = ps.executeUpdate();
            if (res > 0) {
                JOptionPane.showMessageDialog(null, "Usuario Eliminado");
            } else {
                JOptionPane.showMessageDialog(null, "Error al Eliminar");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        limpiarcajas();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnTablaDocentesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTablaDocentesActionPerformed
        //////      AL PRECIONAR EL BOTON ENVIA A LA PESTAÑA NominaDocentes     /////// 
        NominaDocentes ventana = new NominaDocentes();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnTablaDocentesActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed

        try {
            con = coneccioBD();
            ps = con.prepareStatement("SELECT * FROM docentes WHERE correo=?");
            ps.setString(1, txtCorreo.getText());
            rs = ps.executeQuery();
            if (rs.next()) {//Validar si va a un 
                txtId.setText(rs.getString("id"));
                txtNombre.setText(rs.getString("nombre"));
                txtApellido.setText(rs.getString("apellido"));
                //  txtCorreo.setText(rs.getString("correo"));
                txtContraseña.setText(rs.getString("contraseña"));
            } else {
                JOptionPane.showMessageDialog(null, "Usuario no registrado");
                limpiarcajas();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void txtNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyReleased
        validar();//LLAMAR LA FUNCION CREADA
    }//GEN-LAST:event_txtNombreKeyReleased

    private void txtApellidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyReleased
        validar();//LLAMAR LA FUNCION CREADA
    }//GEN-LAST:event_txtApellidoKeyReleased

    private void txtCorreoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoKeyReleased
        validar();//LLAMAR LA FUNCION CREADA
    }//GEN-LAST:event_txtCorreoKeyReleased

    private void txtContraseñaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContraseñaKeyReleased
        validar();//LLAMAR LA FUNCION CREADA
    }//GEN-LAST:event_txtContraseñaKeyReleased

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        //Validar solo para letras 
        char c = evt.getKeyChar();
        if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c < ' ' || c > ' ')) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
        //Validar solo para letras 
        char c = evt.getKeyChar();
        if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c < ' ' || c > ' ')) {
            evt.consume();
        }
    }//GEN-LAST:event_txtApellidoKeyTyped

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
            java.util.logging.Logger.getLogger(RegistroDocente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroDocente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroDocente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroDocente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroDocente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnTablaDocentes;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonVolver;
    private javax.swing.JLabel jLabelApellido;
    private javax.swing.JLabel jLabelContraseña;
    private javax.swing.JLabel jLabelCorreo;
    private javax.swing.JLabel jLabelFondo;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblContraseña;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JPasswordField txtContraseña;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
