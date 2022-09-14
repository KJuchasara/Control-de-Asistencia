package Ventanas;

import static Ventanas.RegistroIngresoSalida.coneccioBD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import javax.swing.JOptionPane;// librerias

public class Autenticacion extends javax.swing.JFrame {

    ResultSet rs;
    private static Connection con;
    private static Statement stmt;
    PreparedStatement ps;
    String driver = "com.mysql.cj.jdbc.Driver";
    private static final String user = "root";
    private static final String pass = "123456";
    private static final String url = "jdbc:mysql://localhost:3306/control";
    public static String Nombre;
    public static String Apellido;// declaración de variables

    public Autenticacion() { // metodo
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public static Connection coneccioBD() { // metodo
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }

    public void buscar() {
        if (txtUsuario.getText().isEmpty() && txtContraseña.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese Usuario y Contraseña");
        } else {
            try {
                con = coneccioBD();//CONECTA A BD
//////////////////////////SELECCIONA TODOS LOS DATOS DE BD PARATIR DE CONTRASEÑA///////////////////////////////////
                String sql = "SELECT * FROM docentes WHERE contraseña='" + txtContraseña.getText() + "' AND correo='" + txtUsuario.getText() + "'";
                ps = con.prepareStatement(sql);//EJECUTA CONSULTA bd
                rs = ps.executeQuery();// VISUALIZA LOS DATOS
                if (rs.next()) {// Obtienes los datos de columnas BD
                    Nombre = rs.getString("nombre");
                    Apellido = rs.getString("apellido");
                    JOptionPane.showMessageDialog(this, "Acceso Correcto " + Nombre + " " + Apellido);
                    Acceso ventana = new Acceso();
                    ventana.setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario o Contraseña incorrectos");
                    this.txtUsuario.setText(null);
                    this.txtContraseña.setText(null);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonSalir = new javax.swing.JButton();
        jButtonIS = new javax.swing.JButton();
        jButtonRegistro = new javax.swing.JButton();
        jLabelUsuario = new javax.swing.JLabel();
        jLabelContraseña = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtContraseña = new javax.swing.JPasswordField();
        jLabelIcono = new javax.swing.JLabel();
        lblus = new javax.swing.JLabel();
        lblco = new javax.swing.JLabel();
        jLabelFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonSalir.setBackground(new java.awt.Color(102, 102, 255));
        jButtonSalir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonSalir.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSalir.setText("Salir");
        jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(282, 0, -1, -1));

        jButtonIS.setBackground(new java.awt.Color(51, 51, 255));
        jButtonIS.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonIS.setForeground(new java.awt.Color(255, 255, 255));
        jButtonIS.setText("Iniciar sesiòn");
        jButtonIS.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonIS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonISActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonIS, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 420, -1, 30));

        jButtonRegistro.setBackground(new java.awt.Color(51, 51, 255));
        jButtonRegistro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonRegistro.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRegistro.setText("Registro");
        jButtonRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistroActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 460, 90, 30));

        jLabelUsuario.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelUsuario.setForeground(new java.awt.Color(255, 255, 255));
        jLabelUsuario.setText("USUARIO:");
        getContentPane().add(jLabelUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, 30));

        jLabelContraseña.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelContraseña.setForeground(new java.awt.Color(255, 255, 255));
        jLabelContraseña.setText("CONTRASEÑA:");
        getContentPane().add(jLabelContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 382, -1, 30));

        txtUsuario.setBackground(new java.awt.Color(0, 153, 153));
        txtUsuario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, 170, 30));

        txtContraseña.setBackground(new java.awt.Color(0, 153, 153));
        txtContraseña.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtContraseña.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(txtContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 380, 170, 30));

        jLabelIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user.png"))); // NOI18N
        getContentPane().add(jLabelIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, -1));
        getContentPane().add(lblus, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, 70, 40));
        getContentPane().add(lblco, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, 100, 40));

        jLabelFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Fondo4.jpg"))); // NOI18N
        getContentPane().add(jLabelFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -120, 340, 630));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jButtonISActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonISActionPerformed
//////      AL PRECIONAR EL BOTON ENVIA A LA PESTAÑA Acceso ///////      
        buscar();
    }//GEN-LAST:event_jButtonISActionPerformed
    private void jButtonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButtonSalirActionPerformed
    private void jButtonRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistroActionPerformed
        Administrador ventana = new Administrador();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonRegistroActionPerformed

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
            java.util.logging.Logger.getLogger(Autenticacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Autenticacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Autenticacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Autenticacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Autenticacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonIS;
    private javax.swing.JButton jButtonRegistro;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JLabel jLabelContraseña;
    private javax.swing.JLabel jLabelFondo;
    private javax.swing.JLabel jLabelIcono;
    private javax.swing.JLabel jLabelUsuario;
    private javax.swing.JLabel lblco;
    private javax.swing.JLabel lblus;
    private javax.swing.JPasswordField txtContraseña;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
