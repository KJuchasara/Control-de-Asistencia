package Ventanas;

import static Ventanas.RegistroIngresoSalida.coneccioBD;
import static Ventanas.RegistroIngresoSalida.lblNomApe;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
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
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import javax.swing.JTable;

public class NominaEstudiantes extends javax.swing.JFrame {

    ResultSet rs;
    private static Connection con = null;
    PreparedStatement ps;
    // Declaramos los datos de conexion a la bd
    String driver = "com.mysql.cj.jdbc.Driver";
    private static final String user = "root";
    private static final String pass = "123456";
    private static final String url = "jdbc:mysql://localhost:3306/control";
    DefaultTableModel tabla;// Nombre de Tabla
    String titulo_columnas[] = {"UID", "Nombre", "Apellido", "CU", "CI", "Estado"}; //Titulos para la tabla
    String filas[] = new String[6]; // NUmeero de filas en la tabla

    public NominaEstudiantes() {
        initComponents();
        this.setLocationRelativeTo(null);
        btnPDF.setEnabled(false);
        idAsignatura.setVisible(false);
        txtID.setEnabled(false);
        txtNombr.setEnabled(false);
        txtApellido.setEnabled(false);
        txtCU.setEnabled(false);
        txtCI.setEnabled(false);
        //     txtMateria.setEnabled(false);
        btnInactivo.setEnabled(false);
        btnActivo.setEnabled(false);
        jTableEstudiantes.getColumnModel().getColumn(0).setPreferredWidth(10);
        jTableEstudiantes.getColumnModel().getColumn(0).setResizable(false);
        jTableEstudiantes.getColumnModel().getColumn(1).setPreferredWidth(180);
        llamado();
        verificardocente();
        jTableEstudiantes.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt) {
                JTable table = (JTable) Mouse_evt.getSource();
                Point point = Mouse_evt.getPoint();
                int row = table.rowAtPoint(point);
                if (Mouse_evt.getClickCount() == 1) {
                    txtID.setText(jTableEstudiantes.getValueAt(jTableEstudiantes.getSelectedRow(), 0).toString());
                    txtNombr.setText(jTableEstudiantes.getValueAt(jTableEstudiantes.getSelectedRow(), 1).toString());
                    txtApellido.setText(jTableEstudiantes.getValueAt(jTableEstudiantes.getSelectedRow(), 2).toString());
                    txtCU.setText(jTableEstudiantes.getValueAt(jTableEstudiantes.getSelectedRow(), 3).toString());
                    txtCI.setText(jTableEstudiantes.getValueAt(jTableEstudiantes.getSelectedRow(), 4).toString());
                }
            }
        });

    }

    public void limpiarcajas() {
        this.txtNombr.setText(null);
        this.txtApellido.setText(null);
        this.txtID.setText(null);
        this.txtCU.setText(null);
        this.txtCI.setText(null);
    }

    public void limpiartablas() {
        int fila = jTableEstudiantes.getRowCount();
        for (int i = fila - 1; i >= 0; i--) {
            tabla.removeRow(i);
        }
        cboMateriaBusqueda.setSelectedIndex(0);
        cboMateriaBusqueda1.setSelectedIndex(0);
    }

    public static Connection coneccioBD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            //JOptionPane.showMessageDialog(null, "Conexion exitosa");
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }

    public void llamado() {
        Autenticacion a = new Autenticacion();
        lblNomApe.setText(a.Nombre + " " + a.Apellido);
    }

    public void cargarDatosTabla1() throws SQLException {
        String materia = cboMateriaBusqueda1.getSelectedItem().toString();
        tabla = new DefaultTableModel(null, titulo_columnas);
        try {
            con = coneccioBD();
            //Busca valores a medida que se escribe
            String consulta_tabla = "SELECT estudiantes.idtarjeta,estudiantes.nombre,estudiantes.apellido,estudiantes.cu,estudiantes.ci,asignatura.estado\n"
                    + "FROM estudiantes,asignatura "
                    + "WHERE asignatura.estudiantes_id=estudiantes.id and asignatura.nombre_materia='" + materia + "' AND asignatura.estado='activo'";
            ps = con.prepareStatement(consulta_tabla);
            rs = ps.executeQuery();
            while (rs.next()) {//Carga los valores de la BD en la tabla
                filas[0] = rs.getString(1);
                filas[1] = rs.getString(2);
                filas[2] = rs.getString(3);
                filas[3] = rs.getString(4);
                filas[4] = rs.getString(5);
                filas[5] = rs.getString(6);
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

    public void cargarDatosTabla() throws SQLException {

        String materia = cboMateriaBusqueda.getSelectedItem().toString();
        tabla = new DefaultTableModel(null, titulo_columnas);
        try {
            con = coneccioBD();
            //Busca valores a medida que se escribe
            String consulta_tabla = "SELECT estudiantes.idtarjeta,estudiantes.nombre,estudiantes.apellido,estudiantes.cu,estudiantes.ci,asignatura.estado,asignatura.id\n"
                    + "FROM estudiantes,asignatura "
                    + "WHERE asignatura.estudiantes_id=estudiantes.id and asignatura.nombre_materia='" + materia + "' AND asignatura.estado='activo'";
            ps = con.prepareStatement(consulta_tabla);
            rs = ps.executeQuery();
            while (rs.next()) {//Carga los valores de la BD en la tabla
                filas[0] = rs.getString(1);
                filas[1] = rs.getString(2);
                filas[2] = rs.getString(3);
                filas[3] = rs.getString(4);
                filas[4] = rs.getString(5);
                filas[5] = rs.getString(6);
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

    public void cargarDatosTabla2() throws SQLException {
        String materia = cboMateriaBusqueda1.getSelectedItem().toString();
        tabla = new DefaultTableModel(null, titulo_columnas);
        try {
            con = coneccioBD();
            //Busca valores a medida que se escribe
            String consulta_tabla = "SELECT estudiantes.idtarjeta,estudiantes.nombre,estudiantes.apellido,estudiantes.cu,estudiantes.ci,asignatura.estado\n"
                    + "FROM estudiantes,asignatura "
                    + "WHERE asignatura.estudiantes_id=estudiantes.id and asignatura.nombre_materia='" + materia + "' AND asignatura.estado='Inactivo'";
            ps = con.prepareStatement(consulta_tabla);
            rs = ps.executeQuery();
            while (rs.next()) {//Carga los valores de la BD en la tabla
                filas[0] = rs.getString(1);
                filas[1] = rs.getString(2);
                filas[2] = rs.getString(3);
                filas[3] = rs.getString(4);
                filas[4] = rs.getString(5);
                filas[5] = rs.getString(6);
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

    public void cargarDatosTabla3() throws SQLException {
        String materia = cboMateriaBusqueda.getSelectedItem().toString();
        tabla = new DefaultTableModel(null, titulo_columnas);
        try {
            con = coneccioBD();
            //Busca valores a medida que se escribe
            String consulta_tabla = "SELECT estudiantes.idtarjeta,estudiantes.nombre,estudiantes.apellido,estudiantes.cu,estudiantes.ci,asignatura.estado,asignatura.id\n"
                    + "FROM estudiantes,asignatura "
                    + "WHERE asignatura.estudiantes_id=estudiantes.id and asignatura.nombre_materia='" + materia + "' AND asignatura.estado='Inactivo'";
            ps = con.prepareStatement(consulta_tabla);
            rs = ps.executeQuery();
            while (rs.next()) {//Carga los valores de la BD en la tabla
                filas[0] = rs.getString(1);
                filas[1] = rs.getString(2);
                filas[2] = rs.getString(3);
                filas[3] = rs.getString(4);
                filas[4] = rs.getString(5);
                filas[5] = rs.getString(6);
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

    public void cargarDatosSemestre() throws SQLException {
        String titulo_columna[] = {"UID", "Nombre", "Apellido", "CU", "CI", "Materia", "Estado"}; //Titulos para la tabla
        String fila[] = new String[7]; // NUmeero de filas en la tabla
        String semestre = cboSemestre.getSelectedItem().toString();
        tabla = new DefaultTableModel(null, titulo_columna);
        try {
            con = coneccioBD();
            //Busca valores a medida que se escribe
            String consulta_tabla = "SELECT estudiantes.idtarjeta,estudiantes.nombre,estudiantes.apellido,estudiantes.cu,estudiantes.ci,asignatura.nombre_materia,asignatura.estado\n"
                    + "FROM estudiantes,asignatura "
                    + "WHERE asignatura.estudiantes_id=estudiantes.id and asignatura.semestre='" + semestre + "' AND asignatura.nombre_materia='ELC-315' or asignatura.estudiantes_id=estudiantes.id and asignatura.semestre='" + semestre + "' AND asignatura.nombre_materia='ELC-416'";
            ps = con.prepareStatement(consulta_tabla);
            rs = ps.executeQuery();
            while (rs.next()) {//Carga los valores de la BD en la tabla
                fila[0] = rs.getString(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getString(4);
                fila[4] = rs.getString(5);
                fila[5] = rs.getString(6);
                fila[6] = rs.getString(7);
                tabla.addRow(fila);
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

    public void cargarDatosSemestrE() throws SQLException {
        String titulo_columna[] = {"UID", "Nombre", "Apellido", "CU", "CI", "Materia", "Estado"}; //Titulos para la tabla
        String fila[] = new String[7]; // NUmeero de filas en la tabla
        String semestre = cboSemestre.getSelectedItem().toString();
        tabla = new DefaultTableModel(null, titulo_columna);
        try {
            con = coneccioBD();
            //Busca valores a medida que se escribe
            String consulta_tabla = "SELECT estudiantes.idtarjeta,estudiantes.nombre,estudiantes.apellido,estudiantes.cu,estudiantes.ci,asignatura.nombre_materia,asignatura.estado\n"
                    + "FROM estudiantes,asignatura "
                    + "WHERE asignatura.estudiantes_id=estudiantes.id and asignatura.semestre='" + semestre + "' AND asignatura.nombre_materia='ELC-415' or asignatura.estudiantes_id=estudiantes.id and asignatura.semestre='" + semestre + "' AND asignatura.nombre_materia='ELC-513'";
            ps = con.prepareStatement(consulta_tabla);
            rs = ps.executeQuery();
            while (rs.next()) {//Carga los valores de la BD en la tabla
                fila[0] = rs.getString(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getString(4);
                fila[4] = rs.getString(5);
                fila[5] = rs.getString(6);
                fila[6] = rs.getString(7);
                tabla.addRow(fila);
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

    public void usuarioInactivo() throws SQLException {
        String materia = cboMateriaBusqueda1.getSelectedItem().toString();
        try {
            con = coneccioBD();
            ps = con.prepareStatement("SELECT asignatura.id "
                    + "FROM estudiantes,asignatura "
                    + "WHERE asignatura.estudiantes_id=estudiantes.id AND estudiantes.idtarjeta='" + txtID.getText() + "' AND estudiantes.nombre='" + txtNombr.getText() + "' AND estudiantes.apellido='" + txtApellido.getText() + "' AND estudiantes.cu='" + txtCU.getText() + "' AND estudiantes.ci='" + txtCI.getText() + "' AND asignatura.nombre_materia='" + materia + "' AND asignatura.estado='Activo'");
            rs = ps.executeQuery();//Guarda
            if (rs.next()) {
                idAsignatura.setText(rs.getString("asignatura.id"));
            } else {
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            con = coneccioBD();
            ps = (PreparedStatement) con.prepareStatement("UPDATE asignatura a, estudiantes e "
                    + "SET a.estado='Inactivo' "
                    + "WHERE a.estudiantes_id=e.id and a.id='" + idAsignatura.getText() + "'");
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
    }

    public void usuarioInactivo1() throws SQLException {
        String materia = cboMateriaBusqueda.getSelectedItem().toString();
        try {
            con = coneccioBD();
            ps = con.prepareStatement("SELECT asignatura.id "
                    + "FROM estudiantes,asignatura "
                    + "WHERE asignatura.estudiantes_id=estudiantes.id AND estudiantes.idtarjeta='" + txtID.getText() + "' AND estudiantes.nombre='" + txtNombr.getText() + "' AND estudiantes.apellido='" + txtApellido.getText() + "' AND estudiantes.cu='" + txtCU.getText() + "' AND estudiantes.ci='" + txtCI.getText() + "' AND asignatura.nombre_materia='" + materia + "' AND asignatura.estado='Activo'");
            rs = ps.executeQuery();//Guarda
            if (rs.next()) {
                idAsignatura.setText(rs.getString("asignatura.id"));
            } else {
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            con = coneccioBD();
            ps = (PreparedStatement) con.prepareStatement("UPDATE asignatura a, estudiantes e "
                    + "SET a.estado='Inactivo' "
                    + "WHERE a.estudiantes_id=e.id and a.id='" + idAsignatura.getText() + "'");
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
    }

    public void usuarioInactivo2() throws SQLException {
        String materia = cboMateriaBusqueda1.getSelectedItem().toString();
        tabla = new DefaultTableModel(null, titulo_columnas);
        try {
            con = coneccioBD();
            ps = con.prepareStatement("SELECT asignatura.id "
                    + "FROM estudiantes,asignatura "
                    + "WHERE asignatura.estudiantes_id=estudiantes.id AND estudiantes.idtarjeta='" + txtID.getText() + "' AND estudiantes.nombre='" + txtNombr.getText() + "' AND estudiantes.apellido='" + txtApellido.getText() + "' AND estudiantes.cu='" + txtCU.getText() + "' AND estudiantes.ci='" + txtCI.getText() + "' AND asignatura.nombre_materia='" + materia + "' AND asignatura.estado='Inactivo'");
            rs = ps.executeQuery();
            if (rs.next()) {
                idAsignatura.setText(rs.getString("asignatura.id"));
            } else {
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            con = coneccioBD();
            ps = (PreparedStatement) con.prepareStatement("UPDATE asignatura a, estudiantes e "
                    + "SET a.estado='Activo' "
                    + "WHERE a.estudiantes_id=e.id and a.id='" + idAsignatura.getText() + "'");
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
    }

    public void usuarioInactivo3() throws SQLException {
        String materia = cboMateriaBusqueda.getSelectedItem().toString();
        tabla = new DefaultTableModel(null, titulo_columnas);
        try {
            con = coneccioBD();
            ps = con.prepareStatement("SELECT asignatura.id "
                    + "FROM estudiantes,asignatura "
                    + "WHERE asignatura.estudiantes_id=estudiantes.id AND estudiantes.idtarjeta='" + txtID.getText() + "' AND estudiantes.nombre='" + txtNombr.getText() + "' AND estudiantes.apellido='" + txtApellido.getText() + "' AND estudiantes.cu='" + txtCU.getText() + "' AND estudiantes.ci='" + txtCI.getText() + "' AND asignatura.nombre_materia='" + materia + "' AND asignatura.estado='Inactivo'");
            rs = ps.executeQuery();//Guarda
            if (rs.next()) {
                idAsignatura.setText(rs.getString("asignatura.id"));
            } else {
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            con = coneccioBD();
            ps = (PreparedStatement) con.prepareStatement("UPDATE asignatura a, estudiantes e "
                    + "SET a.estado='Activo' "
                    + "WHERE a.estudiantes_id=e.id and a.id='" + idAsignatura.getText() + "'");
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
    }

    public void pdf1() throws SQLException {
        String materia = cboMateriaBusqueda1.getSelectedItem().toString();
        Document documento = new Document();
        try {
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Reportes PDF/Lista de Estudiantes/" + "Materia " + cboMateriaBusqueda1.getSelectedItem().toString() + ".pdf"));
            Paragraph universidad = new Paragraph();
            universidad.setAlignment(Paragraph.ALIGN_CENTER);
            universidad.add("UNIVERSIDAD SAN FRANCISCO XAVIER DE CHUQUISACA");
            universidad.setFont(FontFactory.getFont("Tahoma", 16, Font.NORMAL, BaseColor.BLUE));
            Paragraph lista = new Paragraph();
            lista.setAlignment(Paragraph.ALIGN_LEFT);
            lista.add("Lista de Estudiantes");
            lista.setFont(FontFactory.getFont("Tahoma", 13, Font.NORMAL, BaseColor.DARK_GRAY));
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
            documento.add(lista);
            documento.add(facultad);
            documento.add(carrera);
            documento.add(asignatura);
            documento.add(docente);
            PdfPTable tablaCliente = new PdfPTable(6);
            tablaCliente.addCell("ID");
            tablaCliente.addCell("Apellido");
            tablaCliente.addCell("Nombre");
            tablaCliente.addCell("CU");
            tablaCliente.addCell("CI");
            tablaCliente.addCell("Estado");
            try {
                con = coneccioBD();
                String consulta_tabla = "SELECT estudiantes.idtarjeta,estudiantes.apellido,estudiantes.nombre,estudiantes.cu,estudiantes.ci,asignatura.estado\n"
                        + "FROM estudiantes,asignatura "
                        + "WHERE asignatura.estudiantes_id=estudiantes.id and asignatura.nombre_materia='" + materia + "' AND asignatura.estado='activo'"
                        + "ORDER BY estudiantes.apellido";
                ps = con.prepareStatement(consulta_tabla);
                rs = ps.executeQuery();
                while (rs.next()) {//Carga los valores de la BD en la tabla
                    tablaCliente.addCell(rs.getString(1));
                    tablaCliente.addCell(rs.getString(2));
                    tablaCliente.addCell(rs.getString(3));
                    tablaCliente.addCell(rs.getString(4));
                    tablaCliente.addCell(rs.getString(5));
                    tablaCliente.addCell(rs.getString(6));
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
        String materia = cboMateriaBusqueda.getSelectedItem().toString();
        Document documento = new Document();
        try {
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Reportes PDF/Lista de Estudiantes/" + "Materia " + cboMateriaBusqueda.getSelectedItem().toString() + ".pdf"));
            Paragraph universidad = new Paragraph();
            universidad.setAlignment(Paragraph.ALIGN_CENTER);
            universidad.add("UNIVERSIDAD SAN FRANCISCO XAVIER DE CHUQUISACA");
            universidad.setFont(FontFactory.getFont("Tahoma", 16, Font.NORMAL, BaseColor.BLUE));
            Paragraph lista = new Paragraph();
            lista.setAlignment(Paragraph.ALIGN_LEFT);
            lista.add("Lista de Estudiantes");
            lista.setFont(FontFactory.getFont("Tahoma", 13, Font.NORMAL, BaseColor.DARK_GRAY));
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
            documento.add(lista);
            documento.add(facultad);
            documento.add(carrera);
            documento.add(asignatura);
            documento.add(docente);
            PdfPTable tablaCliente = new PdfPTable(6);
            tablaCliente.addCell("ID");
            tablaCliente.addCell("Apellido");
            tablaCliente.addCell("Nombre");
            tablaCliente.addCell("CU");
            tablaCliente.addCell("CI");
            tablaCliente.addCell("Materia");
            try {
                con = coneccioBD();
                String consulta_tabla = "SELECT estudiantes.idtarjeta,estudiantes.apellido,estudiantes.nombre,estudiantes.cu,estudiantes.ci,asignatura.estado\n"
                        + "FROM estudiantes,asignatura "
                        + "WHERE asignatura.estudiantes_id=estudiantes.id and asignatura.nombre_materia='" + materia + "' AND asignatura.estado='activo'"
                        + "ORDER BY estudiantes.apellido";
                ps = con.prepareStatement(consulta_tabla);
                rs = ps.executeQuery();
                while (rs.next()) {//Carga los valores de la BD en la tabla
                    tablaCliente.addCell(rs.getString(1));
                    tablaCliente.addCell(rs.getString(2));
                    tablaCliente.addCell(rs.getString(3));
                    tablaCliente.addCell(rs.getString(4));
                    tablaCliente.addCell(rs.getString(5));
                    tablaCliente.addCell(rs.getString(6));
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
        String titulo_columnas[] = {"UID", "Apellido", "Nombre", "CU", "CI", "Materia"}; //Titulos para la tabla
        String fila[] = new String[6]; // NUmeero de filas en la tabla
        tabla = new DefaultTableModel(null, titulo_columnas);
        try {
            con = coneccioBD();
            //Busca valores a medida que se escribe
            String consulta_tabla = " SELECT estudiantes.idtarjeta,estudiantes.apellido,estudiantes.nombre,estudiantes.cu,estudiantes.ci,asignatura.nombre_materia"
                    + " FROM estudiantes,asignatura"
                    + " WHERE asignatura.estudiantes_id=estudiantes.id and estudiantes.nombre LIKE  " + '"' + nombre + "%" + '"'
                    + " ORDER BY estudiantes.apellido";
            ps = con.prepareStatement(consulta_tabla);
            rs = ps.executeQuery();
            while (rs.next()) {//Carga los valores de la BD en la tabla
                filas[0] = rs.getString(1);
                filas[1] = rs.getString(2);
                filas[2] = rs.getString(3);
                filas[3] = rs.getString(4);
                filas[4] = rs.getString(5);
                filas[5] = rs.getString(6);
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

        estados = new javax.swing.ButtonGroup();
        cboMateriaBusqueda = new javax.swing.JComboBox<>();
        cboMateriaBusqueda1 = new javax.swing.JComboBox<>();
        jLabelNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jButtonVolver = new javax.swing.JButton();
        jButtonBuscar = new javax.swing.JButton();
        jLabelID = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabelNombre2 = new javax.swing.JLabel();
        txtNombr = new javax.swing.JTextField();
        jLabelApellido = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabelCU = new javax.swing.JLabel();
        txtCU = new javax.swing.JTextField();
        jLabelCI = new javax.swing.JLabel();
        txtCI = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEstudiantes = new javax.swing.JTable();
        cboSemestre = new javax.swing.JComboBox<>();
        btnActivo = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblNomApe = new javax.swing.JLabel();
        RbtnActivos = new javax.swing.JRadioButton();
        idAsignatura = new javax.swing.JTextField();
        RbtnSemestre = new javax.swing.JRadioButton();
        RbtnInactivos = new javax.swing.JRadioButton();
        jLabelNombre1 = new javax.swing.JLabel();
        btnInactivo = new javax.swing.JButton();
        btnPDF = new javax.swing.JButton();
        jLabelFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cboMateriaBusqueda.setBackground(new java.awt.Color(0, 153, 153));
        cboMateriaBusqueda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cboMateriaBusqueda.setForeground(new java.awt.Color(255, 255, 255));
        cboMateriaBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Asignatura", "ELC-415", "ELC-513" }));
        getContentPane().add(cboMateriaBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, 180, 30));

        cboMateriaBusqueda1.setBackground(new java.awt.Color(0, 153, 153));
        cboMateriaBusqueda1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cboMateriaBusqueda1.setForeground(new java.awt.Color(255, 255, 255));
        cboMateriaBusqueda1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Asignatura", "ELC-315", "ELC-416" }));
        getContentPane().add(cboMateriaBusqueda1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, 180, 30));

        jLabelNombre.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelNombre.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombre.setText("Nombre o Apellido:");
        getContentPane().add(jLabelNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, 30));

        txtNombre.setBackground(new java.awt.Color(0, 153, 153));
        txtNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(255, 255, 255));
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreKeyReleased(evt);
            }
        });
        getContentPane().add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 170, 30));

        jButtonVolver.setBackground(new java.awt.Color(102, 102, 255));
        jButtonVolver.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonVolver.setForeground(new java.awt.Color(255, 255, 255));
        jButtonVolver.setText("Volver");
        jButtonVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVolverActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 0, -1, -1));

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
        getContentPane().add(jButtonBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 90, -1, 30));

        jLabelID.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelID.setForeground(new java.awt.Color(255, 255, 255));
        jLabelID.setText("UID:");
        getContentPane().add(jLabelID, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 150, -1, 30));

        txtID.setBackground(new java.awt.Color(0, 153, 153));
        txtID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtID.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 150, 170, 30));

        jLabelNombre2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelNombre2.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombre2.setText("NOMBRE:");
        getContentPane().add(jLabelNombre2, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 210, -1, 30));

        txtNombr.setBackground(new java.awt.Color(0, 153, 153));
        txtNombr.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNombr.setForeground(new java.awt.Color(255, 255, 255));
        txtNombr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombrKeyTyped(evt);
            }
        });
        getContentPane().add(txtNombr, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 210, 170, 30));

        jLabelApellido.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelApellido.setForeground(new java.awt.Color(255, 255, 255));
        jLabelApellido.setText("APELLIDO:");
        getContentPane().add(jLabelApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 270, -1, 30));

        txtApellido.setBackground(new java.awt.Color(0, 153, 153));
        txtApellido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtApellido.setForeground(new java.awt.Color(255, 255, 255));
        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoKeyTyped(evt);
            }
        });
        getContentPane().add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 270, 170, 30));

        jLabelCU.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelCU.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCU.setText("CU:");
        getContentPane().add(jLabelCU, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 330, -1, 30));

        txtCU.setBackground(new java.awt.Color(0, 153, 153));
        txtCU.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCU.setForeground(new java.awt.Color(255, 255, 255));
        txtCU.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCUKeyTyped(evt);
            }
        });
        getContentPane().add(txtCU, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 330, 170, 30));

        jLabelCI.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelCI.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCI.setText("CI:");
        getContentPane().add(jLabelCI, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 390, -1, 30));

        txtCI.setBackground(new java.awt.Color(0, 153, 153));
        txtCI.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCI.setForeground(new java.awt.Color(255, 255, 255));
        txtCI.setDoubleBuffered(true);
        txtCI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCIKeyTyped(evt);
            }
        });
        getContentPane().add(txtCI, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 390, 170, 30));

        jTableEstudiantes.setBackground(new java.awt.Color(0, 153, 153));
        jTableEstudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "UID", "Nombre", "Apellido", "CU", "CI", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, false, false, false
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
            jTableEstudiantes.getColumnModel().getColumn(0).setPreferredWidth(20);
            jTableEstudiantes.getColumnModel().getColumn(1).setResizable(false);
            jTableEstudiantes.getColumnModel().getColumn(1).setPreferredWidth(40);
            jTableEstudiantes.getColumnModel().getColumn(2).setResizable(false);
            jTableEstudiantes.getColumnModel().getColumn(2).setPreferredWidth(40);
            jTableEstudiantes.getColumnModel().getColumn(3).setResizable(false);
            jTableEstudiantes.getColumnModel().getColumn(4).setResizable(false);
            jTableEstudiantes.getColumnModel().getColumn(5).setResizable(false);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 700, 340));

        cboSemestre.setBackground(new java.awt.Color(0, 153, 153));
        cboSemestre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cboSemestre.setForeground(new java.awt.Color(255, 255, 255));
        cboSemestre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Semestre", "1", "2", "3", "4", "5" }));
        getContentPane().add(cboSemestre, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, 170, 30));

        btnActivo.setBackground(new java.awt.Color(51, 51, 255));
        btnActivo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnActivo.setForeground(new java.awt.Color(255, 255, 255));
        btnActivo.setText("Activo");
        btnActivo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActivoActionPerformed(evt);
            }
        });
        getContentPane().add(btnActivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 460, 90, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("NOMINA DE ESTUDIANTES");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 500, 50));

        lblNomApe.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNomApe.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblNomApe, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 180, 30));

        estados.add(RbtnActivos);
        RbtnActivos.setText("Activos");
        RbtnActivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RbtnActivosActionPerformed(evt);
            }
        });
        getContentPane().add(RbtnActivos, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 60, -1, -1));

        idAsignatura.setEditable(false);
        idAsignatura.setEnabled(false);
        getContentPane().add(idAsignatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 40, 30));

        estados.add(RbtnSemestre);
        RbtnSemestre.setText("Semestres");
        RbtnSemestre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RbtnSemestreActionPerformed(evt);
            }
        });
        getContentPane().add(RbtnSemestre, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, -1, -1));

        estados.add(RbtnInactivos);
        RbtnInactivos.setText("Inactivos");
        RbtnInactivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RbtnInactivosActionPerformed(evt);
            }
        });
        getContentPane().add(RbtnInactivos, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, -1, -1));

        jLabelNombre1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelNombre1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombre1.setText("DOCENTE:");
        getContentPane().add(jLabelNombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 30));

        btnInactivo.setBackground(new java.awt.Color(51, 51, 255));
        btnInactivo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnInactivo.setForeground(new java.awt.Color(255, 255, 255));
        btnInactivo.setText("Inactivo");
        btnInactivo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnInactivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInactivoActionPerformed(evt);
            }
        });
        getContentPane().add(btnInactivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 460, -1, 30));

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
        getContentPane().add(btnPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 460, -1, 40));

        jLabelFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Fondo4.jpg"))); // NOI18N
        getContentPane().add(jLabelFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 1010, 510));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVolverActionPerformed
//////      AL PRECIONAR EL BOTON ENVIA A LA PESTAÑA Autenticacion  ///////  
        Acceso ventana = new Acceso();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonVolverActionPerformed

    public void verificardocente() {
        String Usuario1 = "Mario Cruz Cueto";
        String Usuario2 = "Franco Fidel Rivero";
        if (lblNomApe.getText().equals(Usuario1)) {
            cboMateriaBusqueda.setVisible(false);
            cboMateriaBusqueda1.setEnabled(true);
        } else {
            if (lblNomApe.getText().equals(Usuario2)) {
                cboMateriaBusqueda.setEnabled(true);
                cboMateriaBusqueda1.setVisible(false);
            }
        }
    }


    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        if (RbtnActivos.isSelected()) {
            txtID.setEnabled(false);
            txtNombr.setEnabled(false);
            txtApellido.setEnabled(false);
            txtCU.setEnabled(false);
            txtCI.setEnabled(false);
            String Usuario1 = "Mario Cruz Cueto";
            String Usuario2 = "Franco Fidel Rivero";
            if (lblNomApe.getText().equals(Usuario1)) {
                try {
                    cargarDatosTabla1();
                } catch (SQLException ex) {
                    Logger.getLogger(NominaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                if (lblNomApe.getText().equals(Usuario2)) {
                    try {
                        cargarDatosTabla();
                    } catch (SQLException ex) {
                        Logger.getLogger(NominaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            btnInactivo.setEnabled(true);
            cboSemestre.setSelectedIndex(0);
        } else {
            if (RbtnInactivos.isSelected()) {
                txtID.setEnabled(false);
                txtNombr.setEnabled(false);
                txtApellido.setEnabled(false);
                txtCU.setEnabled(false);
                txtCI.setEnabled(false);
                String Usuario1 = "Mario Cruz Cueto";
                String Usuario2 = "Franco Fidel Rivero";
                if (lblNomApe.getText().equals(Usuario1)) {
                    try {
                        cargarDatosTabla2();
                    } catch (SQLException ex) {
                        Logger.getLogger(NominaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    if (lblNomApe.getText().equals(Usuario2)) {
                        try {
                            cargarDatosTabla3();
                        } catch (SQLException ex) {
                            Logger.getLogger(NominaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                btnActivo.setEnabled(true);
                cboSemestre.setSelectedIndex(0);
            } else {
                if (RbtnSemestre.isSelected()) {
                    txtID.setEnabled(false);
                    txtNombr.setEnabled(false);
                    txtApellido.setEnabled(false);
                    txtCU.setEnabled(false);
                    txtCI.setEnabled(false);
                    String Usuario1 = "Mario Cruz Cueto";
                    String Usuario2 = "Franco Fidel Rivero";
                    if (lblNomApe.getText().equals(Usuario1)) {
                        try {
                            cargarDatosSemestre();
                        } catch (SQLException ex) {
                            Logger.getLogger(NominaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        if (lblNomApe.getText().equals(Usuario2)) {
                            try {
                                cargarDatosSemestrE();
                            } catch (SQLException ex) {
                                Logger.getLogger(NominaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    btnActivo.setEnabled(false);
                    btnInactivo.setEnabled(false);
                }
                cboSemestre.setSelectedIndex(0);
            }
        }
        btnPDF.setEnabled(true);
        limpiarcajas();


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

    private void txtNombrKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombrKeyTyped
        //Validar solo para letras
        char c = evt.getKeyChar();
        if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c < ' ' || c > ' ')) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombrKeyTyped

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
        //Validar solo para letras
        char c = evt.getKeyChar();
        if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c < ' ' || c > ' ')) {
            evt.consume();
        }
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void txtCUKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCUKeyTyped
        //Validar solo para letras
        char c = evt.getKeyChar();
        if ((c < '0' || c > '9') && (c < '-' || c > '-')) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCUKeyTyped

    private void txtCIKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCIKeyTyped
        //Validar solo para letras
        char c = evt.getKeyChar();
        if ((c < '0' || c > '9')) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCIKeyTyped

    private void btnInactivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInactivoActionPerformed
        String Usuario1 = "Mario Cruz Cueto";
        String Usuario2 = "Franco Fidel Rivero";
        if (lblNomApe.getText().equals(Usuario1)) {
            try {
                usuarioInactivo();
            } catch (SQLException ex) {
                Logger.getLogger(NominaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (lblNomApe.getText().equals(Usuario2)) {
                try {
                    usuarioInactivo1();
                } catch (SQLException ex) {
                    Logger.getLogger(NominaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        limpiarcajas();
    }//GEN-LAST:event_btnInactivoActionPerformed

    private void RbtnInactivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RbtnInactivosActionPerformed
        txtNombre.setEnabled(true);
        btnPDF.setEnabled(false);
        btnInactivo.setEnabled(false);
        txtID.setEnabled(false);
        txtNombr.setEnabled(false);
        txtApellido.setEnabled(false);
        txtCU.setEnabled(false);
        txtCI.setEnabled(false);
        cboSemestre.setEnabled(false);
        String Usuario1 = "Mario Cruz Cueto";
        String Usuario2 = "Franco Fidel Rivero";
        if (lblNomApe.getText().equals(Usuario1)) {
            cboMateriaBusqueda1.setEnabled(true);
        } else {
            if (lblNomApe.getText().equals(Usuario2)) {
                cboMateriaBusqueda.setEnabled(true);
            }
        }
        cboSemestre.setSelectedIndex(0);
        cboMateriaBusqueda.setSelectedIndex(0);
        cboMateriaBusqueda1.setSelectedIndex(0);
    }//GEN-LAST:event_RbtnInactivosActionPerformed

    private void btnActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActivoActionPerformed
        String Usuario1 = "Mario Cruz Cueto";
        String Usuario2 = "Franco Fidel Rivero";
        if (lblNomApe.getText().equals(Usuario1)) {
            try {
                usuarioInactivo2();
            } catch (SQLException ex) {
                Logger.getLogger(NominaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (lblNomApe.getText().equals(Usuario2)) {
                try {
                    usuarioInactivo3();
                } catch (SQLException ex) {
                    Logger.getLogger(NominaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        cboMateriaBusqueda.setSelectedIndex(0);
        cboMateriaBusqueda1.setSelectedIndex(0);
        limpiarcajas();
    }//GEN-LAST:event_btnActivoActionPerformed

    private void RbtnActivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RbtnActivosActionPerformed
        btnPDF.setEnabled(false);
        btnActivo.setEnabled(false);
        txtID.setEnabled(false);
        txtNombr.setEnabled(false);
        txtApellido.setEnabled(false);
        txtCU.setEnabled(false);
        txtCI.setEnabled(false);
        cboSemestre.setEnabled(false);
        String Usuario1 = "Mario Cruz Cueto";
        String Usuario2 = "Franco Fidel Rivero";
        if (lblNomApe.getText().equals(Usuario1)) {
            cboMateriaBusqueda1.setEnabled(true);
        } else {
            if (lblNomApe.getText().equals(Usuario2)) {
                cboMateriaBusqueda.setEnabled(true);
            }
        }
        cboSemestre.setSelectedIndex(0);
        cboMateriaBusqueda.setSelectedIndex(0);
        cboMateriaBusqueda1.setSelectedIndex(0);

    }//GEN-LAST:event_RbtnActivosActionPerformed

    private void RbtnSemestreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RbtnSemestreActionPerformed
        txtNombre.setEnabled(true);
        btnActivo.setEnabled(false);
        btnInactivo.setEnabled(false);
        txtID.setEnabled(false);
        txtNombr.setEnabled(false);
        txtApellido.setEnabled(false);
        txtCU.setEnabled(false);
        txtCI.setEnabled(false);
        cboMateriaBusqueda.setEnabled(false);
        cboMateriaBusqueda1.setEnabled(false);
        txtNombre.setEnabled(true);
        cboSemestre.setEnabled(true);
        cboSemestre.setSelectedIndex(0);
        cboMateriaBusqueda.setSelectedIndex(0);
        cboMateriaBusqueda1.setSelectedIndex(0);
        btnPDF.setEnabled(false);
        limpiartablas();
    }//GEN-LAST:event_RbtnSemestreActionPerformed

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
            java.util.logging.Logger.getLogger(NominaEstudiantes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NominaEstudiantes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NominaEstudiantes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NominaEstudiantes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new NominaEstudiantes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton RbtnActivos;
    private javax.swing.JRadioButton RbtnInactivos;
    private javax.swing.JRadioButton RbtnSemestre;
    private javax.swing.JButton btnActivo;
    private javax.swing.JButton btnInactivo;
    private javax.swing.JButton btnPDF;
    private javax.swing.JComboBox<String> cboMateriaBusqueda;
    private javax.swing.JComboBox<String> cboMateriaBusqueda1;
    private javax.swing.JComboBox<String> cboSemestre;
    private javax.swing.ButtonGroup estados;
    private javax.swing.JTextField idAsignatura;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelApellido;
    private javax.swing.JLabel jLabelCI;
    private javax.swing.JLabel jLabelCU;
    private javax.swing.JLabel jLabelFondo;
    private javax.swing.JLabel jLabelID;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JLabel jLabelNombre1;
    private javax.swing.JLabel jLabelNombre2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableEstudiantes;
    public static javax.swing.JLabel lblNomApe;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCI;
    private javax.swing.JTextField txtCU;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNombr;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
