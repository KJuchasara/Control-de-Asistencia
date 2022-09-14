package Ventanas;

public class Acceso extends javax.swing.JFrame {

    public Acceso() {
        initComponents();
        this.setLocationRelativeTo(null);
        Autenticacion a = new Autenticacion();
        lblNomApe.setText(a.Nombre + " " + a.Apellido);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelNombre = new javax.swing.JLabel();
        jButtonVolver = new javax.swing.JButton();
        jButtonNE = new javax.swing.JButton();
        jButtonRE = new javax.swing.JButton();
        jButtonRI = new javax.swing.JButton();
        lblNomApe = new javax.swing.JLabel();
        jLabelFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelNombre.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombre.setText("DOCENTE:");
        getContentPane().add(jLabelNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 30));

        jButtonVolver.setBackground(new java.awt.Color(102, 102, 255));
        jButtonVolver.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonVolver.setForeground(new java.awt.Color(255, 255, 255));
        jButtonVolver.setText("Volver");
        jButtonVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVolverActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(625, 0, -1, -1));

        jButtonNE.setBackground(new java.awt.Color(51, 51, 255));
        jButtonNE.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonNE.setForeground(new java.awt.Color(255, 255, 255));
        jButtonNE.setText("Nomina de Estudiantes");
        jButtonNE.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonNE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNEActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonNE, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, -1, 30));

        jButtonRE.setBackground(new java.awt.Color(51, 51, 255));
        jButtonRE.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonRE.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRE.setText("Registro de Estudiantes - Entrada - Salida");
        jButtonRE.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonRE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonREActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonRE, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 30));

        jButtonRI.setBackground(new java.awt.Color(51, 51, 255));
        jButtonRI.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonRI.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRI.setText("Nomina de Ingreso y Salida de Estudiantes");
        jButtonRI.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonRI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRIActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonRI, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, -1, 30));

        lblNomApe.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNomApe.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblNomApe, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 180, 30));

        jLabelFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Fondo4.jpg"))); // NOI18N
        getContentPane().add(jLabelFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 200));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVolverActionPerformed
//////      AL PRECIONAR EL BOTON ENVIA A LA PESTAÑA Autenticacion  ///////       
        Autenticacion ventana = new Autenticacion();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonVolverActionPerformed

    private void jButtonNEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNEActionPerformed
//////      AL PRECIONAR EL BOTON ENVIA A LA PESTAÑA NominaEstudiantes   ///////       
        NominaEstudiantes ventana = new NominaEstudiantes();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonNEActionPerformed

    private void jButtonREActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonREActionPerformed
//////      AL PRECIONAR EL BOTON ENVIA A LA PESTAÑA RegistroIngresoSalida     ///////       
        RegistroIngresoSalida ventana = new RegistroIngresoSalida();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonREActionPerformed

    private void jButtonRIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRIActionPerformed
        //////      AL PRECIONAR EL BOTON ENVIA A LA PESTAÑA NominaRegistro     ///////       
        NominaRegistro ventana = new NominaRegistro();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonRIActionPerformed

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
            java.util.logging.Logger.getLogger(Acceso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Acceso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Acceso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Acceso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Acceso().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonNE;
    private javax.swing.JButton jButtonRE;
    private javax.swing.JButton jButtonRI;
    private javax.swing.JButton jButtonVolver;
    private javax.swing.JLabel jLabelFondo;
    private javax.swing.JLabel jLabelNombre;
    public static javax.swing.JLabel lblNomApe;
    // End of variables declaration//GEN-END:variables
}
