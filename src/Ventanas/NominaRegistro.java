package Ventanas;

import static Ventanas.NominaEstudiantes.coneccioBD;
import static Ventanas.NominaEstudiantes.lblNomApe;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.HeadlessException;
import java.io.FileOutputStream;
import java.io.IOException;

public class NominaRegistro extends javax.swing.JFrame {

    ResultSet rs;
    private static Connection con = null;
    PreparedStatement ps;
    // Declaramos los datos de conexion a la bd
    String driver = "com.mysql.cj.jdbc.Driver";
    private static final String user = "root";
    private static final String pass = "123456";
    private static final String url = "jdbc:mysql://localhost:3306/control";
    DefaultTableModel tabla;// Nombre de Tabla
    String titulo_columnas[] = {"ID", "Nombre", "Apellido", "CU", "CI", "Materia", "Hora Ingreso", "Hora Salida"}; //Titulos para la tabla
    String filas[] = new String[9]; // NUmeero de filas en la tabla

    public NominaRegistro() {
        initComponents();
        this.setLocationRelativeTo(null);
        btnPDF.setEnabled(false);
        llamado();
        verificardocente();
    }

    public void llamado() {
        Autenticacion a = new Autenticacion();
        lblNomApe.setText(a.Nombre + " " + a.Apellido);
    }

    public void verificardocente() {
        String Usuario1 = "Mario Cruz Cueto";
        String Usuario2 = "Franco Fidel Rivero";
        if (lblNomApe.getText().equals(Usuario1)) {
            cboMateriaBusqueda.setVisible(true);
            cboMateriaBusqueda1.setVisible(false);
        } else {
            if (lblNomApe.getText().equals(Usuario2)) {
                cboMateriaBusqueda.setVisible(false);
                cboMateriaBusqueda1.setVisible(true);
            }
        }
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

    public void cargarDatosTabla() throws SQLException {
        String materia = cboMateriaBusqueda.getSelectedItem().toString();
        tabla = new DefaultTableModel(null, titulo_columnas);
        try {
            con = coneccioBD();
            //Busca valores a medida que se escribe
            String consulta_tabla = " SELECT estudiantes.idtarjeta,estudiantes.nombre,estudiantes.apellido,estudiantes.cu,estudiantes.ci,asignatura.nombre_materia,ingreso_salida.horaingreso,ingreso_salida.horasalida"
                    + " FROM estudiantes,asignatura,ingreso_salida "
                    + " WHERE ingreso_salida.estudiantes_id=estudiantes.id and ingreso_salida.asignatura_id=asignatura.id and asignatura.nombre_materia='" + materia + "'";
            ps = con.prepareStatement(consulta_tabla);
            rs = ps.executeQuery();
            while (rs.next()) {//Carga los valores de la BD en la tabla
                filas[0] = rs.getString(1);
                filas[1] = rs.getString(2);
                filas[2] = rs.getString(3);
                filas[3] = rs.getString(4);
                filas[4] = rs.getString(5);
                filas[5] = rs.getString(6);
                filas[6] = rs.getString(7);
                filas[7] = rs.getString(8);
                tabla.addRow(filas);
            }
            jTableEstudiantes.setModel(tabla);
            con.close();
        } catch (SQLException e) {
            System.out.println("Error" + e);
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error" + e);
            }
        }
    }

    public void cargarDatosTabla1() throws SQLException {
        String materia = cboMateriaBusqueda1.getSelectedItem().toString();
        tabla = new DefaultTableModel(null, titulo_columnas);
        try {
            con = coneccioBD();
            //Busca valores a medida que se escribe
            String consulta_tabla = " SELECT estudiantes.idtarjeta,estudiantes.nombre,estudiantes.apellido,estudiantes.cu,estudiantes.ci,asignatura.nombre_materia,ingreso_salida.horaingreso,ingreso_salida.horasalida"
                    + " FROM estudiantes,asignatura,ingreso_salida "
                    + " WHERE ingreso_salida.estudiantes_id=estudiantes.id and ingreso_salida.asignatura_id=asignatura.id and asignatura.nombre_materia='" + materia + "'";
            ps = con.prepareStatement(consulta_tabla);
            rs = ps.executeQuery();
            while (rs.next()) {//Carga los valores de la BD en la tabla
                filas[0] = rs.getString(1);
                filas[1] = rs.getString(2);
                filas[2] = rs.getString(3);
                filas[3] = rs.getString(4);
                filas[4] = rs.getString(5);
                filas[5] = rs.getString(6);
                filas[6] = rs.getString(7);
                filas[7] = rs.getString(8);
                tabla.addRow(filas);
            }
            jTableEstudiantes.setModel(tabla);
            con.close();
        } catch (SQLException e) {
            System.out.println("Error" + e);
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error" + e);
            }
        }
    }

    public void pdf1() throws SQLException {
        String materia = cboMateriaBusqueda.getSelectedItem().toString();
        Document documento = new Document();
        try {
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Reportes PDF/Registros Ingreso Salida/" + "Materia " + cboMateriaBusqueda.getSelectedItem().toString() + ".pdf"));
            Paragraph universidad = new Paragraph();
            universidad.setAlignment(Paragraph.ALIGN_CENTER);
            universidad.add("UNIVERSIDAD SAN FRANCISCO XAVIER DE CHUQUISACA");
            universidad.setFont(FontFactory.getFont("Tahoma", 16, Font.NORMAL, BaseColor.BLUE));
            Paragraph facultad = new Paragraph();
            facultad.setAlignment(Paragraph.ALIGN_LEFT);
            facultad.add("FACULTAD: TÉCNICA");
            facultad.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));
            Paragraph carrera = new Paragraph();
            carrera.setAlignment(Paragraph.ALIGN_LEFT);
            carrera.add("CARRERA: ELECTRÓNICA");
            carrera.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));
            Paragraph asignatura = new Paragraph();
            asignatura.setAlignment(Paragraph.ALIGN_LEFT);
            asignatura.add("MATERIA: " + cboMateriaBusqueda.getSelectedItem());
            asignatura.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));
            Paragraph docente = new Paragraph();
            docente.setAlignment(Paragraph.ALIGN_LEFT);
            docente.add("DOCENTE: " + lblNomApe.getText() + "\n\n");
            docente.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));
            documento.open();
            documento.add(universidad);
            documento.add(facultad);
            documento.add(carrera);
            documento.add(asignatura);
            documento.add(docente);
            PdfPTable tablaCliente = new PdfPTable(8);
            tablaCliente.addCell("ID");
            tablaCliente.addCell("Nombre");
            tablaCliente.addCell("Apellido");
            tablaCliente.addCell("CU");
            tablaCliente.addCell("CI");
            tablaCliente.addCell("Materia");
            tablaCliente.addCell("Hora Ingreso");
            tablaCliente.addCell("Hora Salida");
            try {
                con = coneccioBD();
                String consulta_tabla = " SELECT estudiantes.idtarjeta,estudiantes.nombre,estudiantes.apellido,estudiantes.cu,estudiantes.ci,asignatura.nombre_materia,ingreso_salida.horaingreso,ingreso_salida.horasalida"
                        + " FROM estudiantes,asignatura,ingreso_salida "
                        + " WHERE ingreso_salida.estudiantes_id=estudiantes.id and ingreso_salida.asignatura_id=asignatura.id and asignatura.nombre_materia='" + materia + "'";
                ps = con.prepareStatement(consulta_tabla);
                rs = ps.executeQuery();
                while (rs.next()) {//Carga los valores de la BD en la tabla
                    tablaCliente.addCell(rs.getString(1));
                    tablaCliente.addCell(rs.getString(2));
                    tablaCliente.addCell(rs.getString(3));
                    tablaCliente.addCell(rs.getString(4));
                    tablaCliente.addCell(rs.getString(5));
                    tablaCliente.addCell(rs.getString(6));
                    tablaCliente.addCell(rs.getString(7));
                    tablaCliente.addCell(rs.getString(8));
                }
                documento.add(tablaCliente);
            } catch (SQLException e) {
                System.err.print("Error al obtener datos del clientes. " + e);
            }
            documento.close();
            JOptionPane.showMessageDialog(null, "Reporte creado correctamente.");
        } catch (DocumentException | IOException e) {
            System.err.println("Error en PDF o ruta de imagen" + e);
            JOptionPane.showMessageDialog(null, "Error al generar PDF, contacte al administrador");
        }
    }

    public void pdf2() throws SQLException {
        String materia = cboMateriaBusqueda1.getSelectedItem().toString();
        Document documento = new Document();
        try {
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Reportes PDF/Registros Ingreso Salida/" + "Materia " + cboMateriaBusqueda1.getSelectedItem().toString() + ".pdf"));
            Paragraph universidad = new Paragraph();
            universidad.setAlignment(Paragraph.ALIGN_CENTER);
            universidad.add("UNIVERSIDAD SAN FRANCISCO XAVIER DE CHUQUISACA");
            universidad.setFont(FontFactory.getFont("Tahoma", 16, Font.NORMAL, BaseColor.BLUE));
            Paragraph facultad = new Paragraph();
            facultad.setAlignment(Paragraph.ALIGN_LEFT);
            facultad.add("FACULTAD: TÉCNICA");
            facultad.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));
            Paragraph carrera = new Paragraph();
            carrera.setAlignment(Paragraph.ALIGN_LEFT);
            carrera.add("CARRERA: ELECTRÓNICA");
            carrera.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));
            Paragraph asignatura = new Paragraph();
            asignatura.setAlignment(Paragraph.ALIGN_LEFT);
            asignatura.add("MATERIA: " + cboMateriaBusqueda1.getSelectedItem());
            asignatura.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));
            Paragraph docente = new Paragraph();
            docente.setAlignment(Paragraph.ALIGN_LEFT);
            docente.add("DOCENTE: " + lblNomApe.getText() + "\n\n");
            docente.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));
            documento.open();
            documento.add(universidad);
            documento.add(facultad);
            documento.add(carrera);
            documento.add(asignatura);
            documento.add(docente);
            PdfPTable tablaCliente = new PdfPTable(8);
            tablaCliente.addCell("ID");
            tablaCliente.addCell("Nombre");
            tablaCliente.addCell("Apellido");
            tablaCliente.addCell("CU");
            tablaCliente.addCell("CI");
            tablaCliente.addCell("Materia");
            tablaCliente.addCell("Hora Ingreso");
            tablaCliente.addCell("Hora Salida");
            try {
                con = coneccioBD();
                String consulta_tabla = " SELECT estudiantes.idtarjeta,estudiantes.nombre,estudiantes.apellido,estudiantes.cu,estudiantes.ci,asignatura.nombre_materia,ingreso_salida.horaingreso,ingreso_salida.horasalida"
                        + " FROM estudiantes,asignatura,ingreso_salida "
                        + " WHERE ingreso_salida.estudiantes_id=estudiantes.id and ingreso_salida.asignatura_id=asignatura.id and asignatura.nombre_materia='" + materia + "'";
                ps = con.prepareStatement(consulta_tabla);
                rs = ps.executeQuery();
                while (rs.next()) {//Carga los valores de la BD en la tabla
                    tablaCliente.addCell(rs.getString(1));
                    tablaCliente.addCell(rs.getString(2));
                    tablaCliente.addCell(rs.getString(3));
                    tablaCliente.addCell(rs.getString(4));
                    tablaCliente.addCell(rs.getString(5));
                    tablaCliente.addCell(rs.getString(6));
                    tablaCliente.addCell(rs.getString(7));
                    tablaCliente.addCell(rs.getString(8));
                }
                documento.add(tablaCliente);
            } catch (SQLException e) {
                System.err.print("Error al obtener datos del clientes. " + e);
            }
            documento.close();
            JOptionPane.showMessageDialog(null, "Reporte creado correctamente.");
        } catch (DocumentException | IOException e) {
            System.err.println("Error en PDF o ruta de imagen" + e);
            JOptionPane.showMessageDialog(null, "Error al generar PDF, contacte al administrador");
        }
    }

    public void busquedaFiltrada(String nombre) throws SQLException {
        tabla = new DefaultTableModel(null, titulo_columnas);
        try {
            con = coneccioBD();
            //Busca valores a medida que se escribe
            String consulta_tabla = " SELECT estudiantes.idtarjeta,estudiantes.nombre,estudiantes.apellido,estudiantes.cu,estudiantes.ci,asignatura.nombre_materia,ingreso_salida.horaingreso,ingreso_salida.horasalida"
                    + " FROM estudiantes,asignatura,ingreso_salida "
                    + " WHERE ingreso_salida.estudiantes_id=estudiantes.id and ingreso_salida.asignatura_id=asignatura.id and estudiantes.nombre LIKE " + '"' + nombre + "%" + '"';
            ps = con.prepareStatement(consulta_tabla);
            rs = ps.executeQuery();
            while (rs.next()) {//Carga los valores de la BD en la tabla
                filas[0] = rs.getString(1);
                filas[1] = rs.getString(2);
                filas[2] = rs.getString(3);
                filas[3] = rs.getString(4);
                filas[4] = rs.getString(5);
                filas[5] = rs.getString(6);
                filas[6] = rs.getString(7);
                filas[7] = rs.getString(8);
                tabla.addRow(filas);
            }
            jTableEstudiantes.setModel(tabla);
            con.close();
        } catch (SQLException e) {
            System.out.println("Error" + e);
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error" + e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jButtonVolver = new javax.swing.JButton();
        jButtonBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEstudiantes = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabelNombre1 = new javax.swing.JLabel();
        lblNomApe = new javax.swing.JLabel();
        cboMateriaBusqueda = new javax.swing.JComboBox<>();
        cboMateriaBusqueda1 = new javax.swing.JComboBox<>();
        btnPDF = new javax.swing.JButton();
        jLabelNombre2 = new javax.swing.JLabel();
        jLabelFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelNombre.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelNombre.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombre.setText("Nombre o Apellido:");
        getContentPane().add(jLabelNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, -1, 30));

        txtNombre.setBackground(new java.awt.Color(0, 153, 153));
        txtNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(255, 255, 255));
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreKeyReleased(evt);
            }
        });
        getContentPane().add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 170, 30));

        jButtonVolver.setBackground(new java.awt.Color(102, 102, 255));
        jButtonVolver.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonVolver.setForeground(new java.awt.Color(255, 255, 255));
        jButtonVolver.setText("Volver");
        jButtonVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVolverActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 0, -1, -1));

        jButtonBuscar.setBackground(new java.awt.Color(51, 51, 255));
        jButtonBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonBuscar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonBuscar.setText("Cargar Datos");
        jButtonBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 50, -1, 30));

        jTableEstudiantes.setBackground(new java.awt.Color(0, 153, 153));
        jTableEstudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Apellido", "CU", "CI", "Materia", "Hora Entrada", "Hora Salida"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableEstudiantes);
        if (jTableEstudiantes.getColumnModel().getColumnCount() > 0) {
            jTableEstudiantes.getColumnModel().getColumn(0).setResizable(false);
            jTableEstudiantes.getColumnModel().getColumn(1).setResizable(false);
            jTableEstudiantes.getColumnModel().getColumn(2).setResizable(false);
            jTableEstudiantes.getColumnModel().getColumn(3).setResizable(false);
            jTableEstudiantes.getColumnModel().getColumn(4).setResizable(false);
            jTableEstudiantes.getColumnModel().getColumn(5).setResizable(false);
            jTableEstudiantes.getColumnModel().getColumn(6).setResizable(false);
            jTableEstudiantes.getColumnModel().getColumn(7).setResizable(false);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 1060, 290));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("NOMINA DE REGISTRO INGRESO Y SALIDA");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 670, 50));

        jLabelNombre1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelNombre1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombre1.setText("DOCENTE:");
        getContentPane().add(jLabelNombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 30));

        lblNomApe.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNomApe.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblNomApe, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 180, 30));

        cboMateriaBusqueda.setBackground(new java.awt.Color(0, 153, 153));
        cboMateriaBusqueda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cboMateriaBusqueda.setForeground(new java.awt.Color(255, 255, 255));
        cboMateriaBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Asignatura", "ELC-315", "ELC-416" }));
        getContentPane().add(cboMateriaBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 50, 180, 30));

        cboMateriaBusqueda1.setBackground(new java.awt.Color(0, 153, 153));
        cboMateriaBusqueda1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cboMateriaBusqueda1.setForeground(new java.awt.Color(255, 255, 255));
        cboMateriaBusqueda1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Asignatura", "ELC-415", "ELC-513" }));
        getContentPane().add(cboMateriaBusqueda1, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 50, 180, 30));

        btnPDF.setBackground(new java.awt.Color(51, 51, 255));
        btnPDF.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPDF.setForeground(new java.awt.Color(255, 255, 255));
        btnPDF.setText("Exportar a PDF");
        btnPDF.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPDFActionPerformed(evt);
            }
        });
        getContentPane().add(btnPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 380, -1, 30));

        jLabelNombre2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelNombre2.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombre2.setText("Titulo de Práctica:");
        getContentPane().add(jLabelNombre2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, -1, 30));

        jLabelFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Fondo4.jpg"))); // NOI18N
        getContentPane().add(jLabelFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVolverActionPerformed
//////      AL PRECIONAR EL BOTON ENVIA A LA PESTAÑA Acceso  ///////     
        Acceso ventana = new Acceso();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonVolverActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        String Usuario1 = "Mario Cruz Cueto";
        String Usuario2 = "Franco Fidel Rivero";
        if (lblNomApe.getText().equals(Usuario1)) {
            try {
                cargarDatosTabla();
            } catch (SQLException ex) {
                Logger.getLogger(NominaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (lblNomApe.getText().equals(Usuario2)) {
                try {
                    cargarDatosTabla1();
                } catch (SQLException ex) {
                    Logger.getLogger(NominaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        btnPDF.setEnabled(true);
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void txtNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyReleased
        try {
            busquedaFiltrada(txtNombre.getText());
        } catch (SQLException ex) {
            Logger.getLogger(NominaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtNombreKeyReleased

    private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFActionPerformed
        String Usuario1 = "Mario Cruz Cueto";
        String Usuario2 = "Franco Fidel Rivero";
        if (lblNomApe.getText().equals(Usuario1)) {
            try {
                pdf1();
            } catch (SQLException ex) {
                Logger.getLogger(NominaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (lblNomApe.getText().equals(Usuario2)) {
                try {
                    pdf2();
                } catch (SQLException ex) {
                    Logger.getLogger(NominaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }//GEN-LAST:event_btnPDFActionPerformed

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
            java.util.logging.Logger.getLogger(NominaRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NominaRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NominaRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NominaRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new NominaRegistro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPDF;
    private javax.swing.JComboBox<String> cboMateriaBusqueda;
    private javax.swing.JComboBox<String> cboMateriaBusqueda1;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelFondo;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JLabel jLabelNombre1;
    private javax.swing.JLabel jLabelNombre2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableEstudiantes;
    public static javax.swing.JLabel lblNomApe;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
