package Ventanas;

import static Ventanas.NominaEstudiantes.coneccioBD;
import static Ventanas.NominaEstudiantes.lblNomApe;
import static Ventanas.NominaRegistro.coneccioBD;
import static Ventanas.NominaRegistro.lblNomApe;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.cj.protocol.Message;
import comunicacionserial.ArduinoExcepcion;
import comunicacionserial.ComunicacionSerial_Arduino;//460
import java.awt.print.PrinterException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.Document;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class RegistroIngresoSalida extends javax.swing.JFrame {

    ComunicacionSerial_Arduino conexion = new ComunicacionSerial_Arduino();
    ResultSet rs;
    private static Connection con;
    private static Statement stmt;
    DefaultTableModel tabla;// Nombre de Tabla
    String titulo_columnas[] = {"UID", "Nombre", "Apellido", "CU", "CI", "Materia", "Hora Ingreso", "Hora Salida", "Estado"}; //Titulos para la tabla
    String[] columna = new String[9]; // NUmeero de columnas en la tabla
    Calendar fecha_actual = new GregorianCalendar();
    String fechahora = "";
    PreparedStatement ps;
    String fechaHora = "";
    // Declaramos los datos de conexion a la bd
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String user = "root";
    private static final String pass = "123456";
    private static final String url = "jdbc:mysql://localhost:3306/control";

    public void limpiartabla() {
        int filas = jTableIngreso.getRowCount();
        for (int i = filas - 1; i >= 0; i--) {
            tabla.removeRow(i);
        }
    }

    public void btnRegistro() {
        buscar();//553
    }

    public void btnIngreso() {
        buscar();//553
        ingreso();
    }

    public void btnSalida() {
        buscar();//553
        salida();//418
    }

    public void cargaDatosTabla() throws SQLException {
        String materia = cboMateriaBusqueda.getSelectedItem().toString();// selecciona materia
        String calendar = "";
        String consulta;
        if (Calendario.getDate() != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = Calendario.getDate(); // Asigna hora fecha de la pc
            calendar = dateFormat.format(date); //convierte al formato asignado
        }
        tabla = new DefaultTableModel(null, titulo_columnas);
        try {
            con = coneccioBD();
            String consulta_tabla = " SELECT estudiantes.idtarjeta,estudiantes.nombre,estudiantes.apellido,estudiantes.cu,estudiantes.ci,asignatura.nombre_materia,ingreso_salida.horaingreso,ingreso_salida.horasalida,ingreso_salida.estado"
                    + " FROM estudiantes,asignatura,ingreso_salida "
                    + " WHERE ingreso_salida.estudiantes_id=estudiantes.id and ingreso_salida.asignatura_id=asignatura.id and asignatura.nombre_materia='" + materia + "' AND ingreso_salida.horaingreso like " + '"' + calendar + "%" + '"';
            ps = con.prepareStatement(consulta_tabla);
            rs = ps.executeQuery();
            while (rs.next()) {//Carga los valores de la BD en la tabla
                columna[0] = rs.getString(1);
                columna[1] = rs.getString(2);
                columna[2] = rs.getString(3);
                columna[3] = rs.getString(4);
                columna[4] = rs.getString(5);
                columna[5] = rs.getString(6);
                columna[6] = rs.getString(7);
                columna[7] = rs.getString(8);
                columna[8] = rs.getString(9);
                tabla.addRow(columna);
            }
            jTableIngreso.setModel(tabla);//llenas datos de la tabla
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
        tamañotabla();
    }

    public void cargaDatosTabla1() throws SQLException {
        String materia = cboMateriaBusqueda1.getSelectedItem().toString();
        String calendar = "";
        String consulta;
        if (Calendario.getDate() != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = Calendario.getDate(); // Asigna hora fecha de la pc
            calendar = dateFormat.format(date); //convierte al formato asignado
        }
        tabla = new DefaultTableModel(null, titulo_columnas);
        try {
            con = coneccioBD();
            String consulta_tabla = " SELECT estudiantes.idtarjeta,estudiantes.nombre,estudiantes.apellido,estudiantes.cu,estudiantes.ci,asignatura.nombre_materia,ingreso_salida.horaingreso,ingreso_salida.horasalida,ingreso_salida.estado"
                    + " FROM estudiantes,asignatura,ingreso_salida "
                    + " WHERE ingreso_salida.estudiantes_id=estudiantes.id and ingreso_salida.asignatura_id=asignatura.id and asignatura.nombre_materia='" + materia + "' AND ingreso_salida.horaingreso like " + '"' + calendar + "%" + '"';
            ps = con.prepareStatement(consulta_tabla);
            rs = ps.executeQuery();
            while (rs.next()) {//Carga los valores de la BD en la tabla
                columna[0] = rs.getString(1);
                columna[1] = rs.getString(2);
                columna[2] = rs.getString(3);
                columna[3] = rs.getString(4);
                columna[4] = rs.getString(5);
                columna[5] = rs.getString(6);
                columna[6] = rs.getString(7);
                columna[7] = rs.getString(8);
                columna[8] = rs.getString(9);
                tabla.addRow(columna);
            }
            jTableIngreso.setModel(tabla);//llenas datos de la tabla
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
        tamañotabla();
    }

    public void cargarDatosTablaIngreso() throws SQLException {
        columna[0] = txtID.getText();
        columna[1] = txtNombre.getText();
        columna[2] = txtApellido.getText();
        columna[3] = txtCU.getText();
        columna[4] = txtCI.getText();
        columna[5] = txtMateria.getText();
        columna[6] = fechaHora;
        columna[7] = "";
        columna[8] = "En Aula";
        tabla.addRow(columna);//Mostrar datos en tabla
    }

    public void cargarDatosTablaSalida() throws SQLException {
        columna[0] = txtID.getText();
        columna[1] = txtNombre.getText();
        columna[2] = txtApellido.getText();
        columna[3] = txtCU.getText();
        columna[4] = txtCI.getText();
        columna[5] = txtMateria.getText();
        columna[6] = "";
        columna[7] = fechaHora;
        columna[8] = "Fuera de Aula";
        tabla.addRow(columna);
    }

    public void pdf1() throws SQLException {
        String materia = cboMateriaBusqueda.getSelectedItem().toString();// Seleccione la materia
        com.itextpdf.text.Document documento = new com.itextpdf.text.Document();//crea variable tipo documento
        try {
            String ruta = System.getProperty("user.home");//establece rutw local de la pc
            try {
                PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Reportes PDF/Reportes del dia/" + txtTitulo.getText() + ".pdf"));//ruta donde se va a guardar los archivos creados
            } catch (FileNotFoundException ex) {
                Logger.getLogger(RegistroIngresoSalida.class.getName()).log(Level.SEVERE, null, ex);
            }
            Paragraph universidad = new Paragraph();//variable de formato para el pdf
            universidad.setAlignment(Paragraph.ALIGN_CENTER);//posicion que se asigna en el pdf
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
            docente.add("DOCENTE: " + lblNomApe.getText());
            docente.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));
            Paragraph practica = new Paragraph();
            practica.setAlignment(Paragraph.ALIGN_LEFT);
            practica.add("TITULO DE PRÁCTICA: " + txtTitulo.getText() + "\n\n");
            practica.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.GREEN));
            documento.open();
            documento.add(universidad);//agrega al documento la variable que se guarda en universidad
            documento.add(facultad);
            documento.add(carrera);
            documento.add(asignatura);
            documento.add(docente);
            documento.add(practica);
            PdfPTable tablaCliente = new PdfPTable(8);
            tablaCliente.addCell("ID");
            tablaCliente.addCell("Nombre");
            tablaCliente.addCell("Apellido");
            tablaCliente.addCell("CU");
            tablaCliente.addCell("CI");
            tablaCliente.addCell("Materia");
            tablaCliente.addCell("Hora Ingreso");
            tablaCliente.addCell("Hora Salida");
            String calendar = "";
            String consulta;
            if (Calendario.getDate() != null) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = Calendario.getDate();
                calendar = dateFormat.format(date);
                tabla = new DefaultTableModel(null, titulo_columnas);
                try {
                    con = coneccioBD();
                    String consulta_tabla = " SELECT estudiantes.idtarjeta,estudiantes.nombre,estudiantes.apellido,estudiantes.cu,estudiantes.ci,asignatura.nombre_materia,ingreso_salida.horaingreso,ingreso_salida.horasalida,ingreso_salida.estado"
                            + " FROM estudiantes,asignatura,ingreso_salida "
                            + " WHERE ingreso_salida.estudiantes_id=estudiantes.id and ingreso_salida.asignatura_id=asignatura.id and asignatura.nombre_materia='" + materia + "' AND ingreso_salida.horaingreso like " + '"' + calendar + "%" + '"';
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
            } else {
                btnPDF.setEnabled(false);
            }

        } catch (DocumentException e) {
            System.err.println("Error en PDF o ruta de imagen" + e);
            JOptionPane.showMessageDialog(null, "Error al generar PDF, contacte al administrador");
        }
    }

    public void pdf2() throws SQLException {
        String materia = cboMateriaBusqueda1.getSelectedItem().toString();
        com.itextpdf.text.Document documento = new com.itextpdf.text.Document();
        try {
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Reportes PDF/Reportes del dia/" + txtTitulo.getText() + ".pdf"));
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
            docente.add("DOCENTE: " + lblNomApe.getText());
            docente.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));
            Paragraph practica = new Paragraph();
            practica.setAlignment(Paragraph.ALIGN_LEFT);
            practica.add("TITULO DE PRÁCTICA: " + txtTitulo.getText() + "\n\n");
            practica.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.GREEN));
            documento.open();
            documento.add(universidad);
            documento.add(facultad);
            documento.add(carrera);
            documento.add(asignatura);
            documento.add(docente);
            documento.add(practica);
            PdfPTable tablaCliente = new PdfPTable(8);
            tablaCliente.addCell("ID");
            tablaCliente.addCell("Nombre");
            tablaCliente.addCell("Apellido");
            tablaCliente.addCell("CU");
            tablaCliente.addCell("CI");
            tablaCliente.addCell("Materia");
            tablaCliente.addCell("Hora Ingreso");
            tablaCliente.addCell("Hora Salida");
            String calendar = "";
            String consulta;
            if (Calendario.getDate() != null) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = Calendario.getDate();
                calendar = dateFormat.format(date);
                tabla = new DefaultTableModel(null, titulo_columnas);
                try {
                    con = coneccioBD();
                    String consulta_tabla = " SELECT estudiantes.idtarjeta,estudiantes.nombre,estudiantes.apellido,estudiantes.cu,estudiantes.ci,asignatura.nombre_materia,ingreso_salida.horaingreso,ingreso_salida.horasalida,ingreso_salida.estado"
                            + " FROM estudiantes,asignatura,ingreso_salida "
                            + " WHERE ingreso_salida.estudiantes_id=estudiantes.id and ingreso_salida.asignatura_id=asignatura.id and asignatura.nombre_materia='" + materia + "' AND ingreso_salida.horaingreso like " + '"' + calendar + "%" + '"';
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
            } else {
                btnPDF.setEnabled(false);
            }

        } catch (DocumentException | IOException e) {
            System.err.println("Error en PDF o ruta de imagen" + e);
            JOptionPane.showMessageDialog(null, "Error al generar PDF, contacte al administrador");
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

    public String fechahora() {
        java.util.Date fecha = new java.util.Date();
        SimpleDateFormat fechaact = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return fechaact.format(fecha);
    }

    public void buscaid() {
        try {
            con = coneccioBD();//CONECTA A BD
//////////////////////////SELECCIONA TODOS LOS DATOS DE BD PARATIR DE IDTARJETA///////////////////////////////////
            ps = con.prepareStatement("SELECT estudiantes.id,estudiantes.idtarjeta,estudiantes.nombre,estudiantes.apellido,estudiantes.cu,estudiantes.ci,asignatura.nombre_materia\n"
                    + " FROM estudiantes,asignatura "
                    + " WHERE asignatura.estudiantes_id=estudiantes.id and estudiantes.idtarjeta='" + txtID.getText() + "'");
            rs = ps.executeQuery();//Guarda
            if (rs.next()) {
                txtId.setText(rs.getString("estudiantes.id")); // Busco el id y pongo en el testfield
                txtNombre.setText(rs.getString("estudiantes.nombre"));
                txtApellido.setText(rs.getString("estudiantes.apellido"));
                txtCU.setText(rs.getString("estudiantes.cu"));
                txtCI.setText(rs.getString("estudiantes.ci"));
                cboMater.setSelectedItem(rs.getString("asignatura.nombre_materia"));
                cboMateria1.setSelectedItem(rs.getString("asignatura.nombre_materia"));
                txtMateria.setText(rs.getString("asignatura.nombre_materia"));
            } else {
                JOptionPane.showMessageDialog(null, "Estudiante no registrado");
                limpiarcajas1();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            con = coneccioBD();
            ps = con.prepareStatement("SELECT asignatura.id "
                    + "FROM estudiantes,asignatura "
                    + "WHERE asignatura.estudiantes_id=estudiantes.id AND estudiantes.idtarjeta='" + txtID.getText() + "' AND estudiantes.nombre='" + txtNombre.getText() + "' AND estudiantes.apellido='" + txtApellido.getText() + "' AND estudiantes.cu='" + txtCU.getText() + "' AND estudiantes.ci='" + txtCI.getText() + "' AND asignatura.nombre_materia='" + txtMateria.getText() + "' AND asignatura.estado='Activo'");
            rs = ps.executeQuery();//Guarda
            if (rs.next()) {
                idAsignatura.setText(rs.getString("asignatura.id"));
                // JOptionPane.showMessageDialog(null, "Usuario Actualizado");
            } else {
                JOptionPane.showMessageDialog(null, "Error al Actualizar");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void ingreso() {
        if (txtNombre.getText().isEmpty()) {
            limpiarcajas();
        } else {
            try {
                con = coneccioBD();//CONECTA A BD
//////////////////////////SELECCIONA TODOS LOS DATOS DE BD PARATIR DE IDTARJETA///////////////////////////////////
                ps = con.prepareStatement("SELECT estudiantes.id,estudiantes.idtarjeta,estudiantes.nombre,estudiantes.apellido,estudiantes.cu,estudiantes.ci,asignatura.nombre_materia\n"
                        + " FROM estudiantes,asignatura "
                        + " WHERE asignatura.estudiantes_id=estudiantes.id and estudiantes.idtarjeta='" + txtID.getText() + "'");
                rs = ps.executeQuery();//Guarda
                if (rs.next()) {
                    txtId.setText(rs.getString("estudiantes.id")); // Busco el id y pongo en el testfield
                    txtNombre.setText(rs.getString("estudiantes.nombre"));
                    txtApellido.setText(rs.getString("estudiantes.apellido"));
                    txtCU.setText(rs.getString("estudiantes.cu"));
                    txtCI.setText(rs.getString("estudiantes.ci"));
                    cboMater.setSelectedItem(rs.getString("asignatura.nombre_materia"));
                    cboMateria1.setSelectedItem(rs.getString("asignatura.nombre_materia"));
                    txtMateria.setText(rs.getString("asignatura.nombre_materia"));
                } else {
                    JOptionPane.showMessageDialog(null, "Estudiante no registrado");
                    limpiarcajas1();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                con = coneccioBD();
                ps = con.prepareStatement("SELECT asignatura.id "
                        + "FROM estudiantes,asignatura "
                        + "WHERE asignatura.estudiantes_id=estudiantes.id AND estudiantes.idtarjeta='" + txtID.getText() + "' AND estudiantes.nombre='" + txtNombre.getText() + "' AND estudiantes.apellido='" + txtApellido.getText() + "' AND estudiantes.cu='" + txtCU.getText() + "' AND estudiantes.ci='" + txtCI.getText() + "' AND asignatura.nombre_materia='" + txtMateria.getText() + "' AND asignatura.estado='Activo'");
                rs = ps.executeQuery();//Guarda
                if (rs.next()) {
                    idAsignatura.setText(rs.getString("asignatura.id"));
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Actualizar");
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                con = coneccioBD();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                java.util.Date date = cal.getTime();//libreria para fecha y hora PC
                fechaHora = dateFormat.format(date);
                System.out.print(dateFormat.format(date));
                Statement stat = con.createStatement();//para ingreso de datos 
                ps = (PreparedStatement) con.prepareStatement("INSERT INTO ingreso_salida (horaingreso,horasalida,estudiantes_id,asignatura_id,estado)VALUES(?,?,?,?,?) ");
                ps.setString(1, fechaHora);// en este orden insertamos a la BD
                ps.setString(2, "");
                ps.setString(3, txtId.getText());
                ps.setString(4, idAsignatura.getText());
                ps.setString(5, "En Aula");
                int res = ps.executeUpdate();
                if (res > 0) {
                    //      JOptionPane.showMessageDialog(null, "Estudiante Registrado");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Registrar");
                }
                cargarDatosTablaIngreso();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void salida() {
        if (txtNombre.getText().isEmpty()) {
            limpiarcajas();
        } else {
            try {
                con = coneccioBD();//CONECTA A BD
//////////////////////////SELECCIONA TODOS LOS DATOS DE BD PARATIR DE IDTARJETA///////////////////////////////////
                ps = con.prepareStatement("SELECT estudiantes.id,estudiantes.idtarjeta,estudiantes.nombre,estudiantes.apellido,estudiantes.cu,estudiantes.ci,asignatura.nombre_materia\n"
                        + " FROM estudiantes,asignatura "
                        + " WHERE asignatura.estudiantes_id=estudiantes.id and estudiantes.idtarjeta='" + txtID.getText() + "'");
                rs = ps.executeQuery();//Guarda
                if (rs.next()) {
                    txtId.setText(rs.getString("estudiantes.id")); // Busco el id y pongo en el testfield
                    txtNombre.setText(rs.getString("estudiantes.nombre"));
                    txtApellido.setText(rs.getString("estudiantes.apellido"));
                    txtCU.setText(rs.getString("estudiantes.cu"));
                    txtCI.setText(rs.getString("estudiantes.ci"));
                    cboMater.setSelectedItem(rs.getString("asignatura.nombre_materia"));
                    cboMateria1.setSelectedItem(rs.getString("asignatura.nombre_materia"));
                    txtMateria.setText(rs.getString("asignatura.nombre_materia"));
                } else {
                    JOptionPane.showMessageDialog(null, "Estudiante no registrado");
                    limpiarcajas1();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                con = coneccioBD();
                ps = con.prepareStatement("SELECT asignatura.id "
                        + "FROM estudiantes,asignatura "
                        + "WHERE asignatura.estudiantes_id=estudiantes.id AND estudiantes.idtarjeta='" + txtID.getText() + "' AND estudiantes.nombre='" + txtNombre.getText() + "' AND estudiantes.apellido='" + txtApellido.getText() + "' AND estudiantes.cu='" + txtCU.getText() + "' AND estudiantes.ci='" + txtCI.getText() + "' AND asignatura.nombre_materia='" + txtMateria.getText() + "' AND asignatura.estado='Activo'");
                rs = ps.executeQuery();//Guarda
                if (rs.next()) {
                    idAsignatura.setText(rs.getString("asignatura.id"));
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Actualizar");
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                con = coneccioBD();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                java.util.Date date = cal.getTime();
                fechaHora = dateFormat.format(date);
                Statement stat = con.createStatement();
                stat.executeUpdate("UPDATE ingreso_salida "
                        + "SET horasalida='" + fechaHora + "',estado='Fuera de Aula' "
                        + "WHERE estudiantes_id='" + txtId.getText() + "' AND asignatura_id='" + idAsignatura.getText() + "' AND estado='En Aula'");
                cargarDatosTablaSalida();
            } catch (SQLException ex) {
                Logger.getLogger(RegistroIngresoSalida.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void limpiarcajas1() {
        this.txtNombre.setText(null);
        this.txtApellido.setText(null);
        this.txtCU.setText(null);
        this.txtCI.setText(null);
        this.txtMateria.setText(null);
        this.txtId.setText(null);
        this.txtSemestre.setText(null);
        this.txtTitulo.setText(null);

    }

    public void limpiarcajas() {
        this.txtNombre.setText(null);
        this.txtApellido.setText(null);
        this.txtID.setText(null);
        this.txtCU.setText(null);
        this.txtCI.setText(null);
        this.txtMateria.setText(null);
        this.txtId.setText(null);
        this.txtSemestre.setText(null);
        cboMater.setSelectedIndex(0);
        cboMateria1.setSelectedIndex(0);
        cboMateriaBusqueda.setSelectedIndex(0);
        cboMateriaBusqueda1.setSelectedIndex(0);
        this.txtTitulo.setText(null);
    }

    SerialPortEventListener listen = new SerialPortEventListener() {

        @Override

        public void serialEvent(SerialPortEvent spe) {
            try {
                if (conexion.isMessageAvailable()) {
                    if (btnRegistro.isSelected()) {
                        limpiarcajas();
                        txtID.setText(conexion.printMessage());//pasa el dato de la comunicacion arduino y puerto serie
                        buscar();//560
                    }
                    if (RbtnIngreso.isSelected()) {
                        limpiarcajas();
                        txtID.setText(conexion.printMessage());
                        btnIngreso();
                        enviar();
                    } else {
                        if (RbtnSalida.isSelected()) {
                            limpiarcajas();
                            txtID.setText(conexion.printMessage());
                            btnSalida();//80
                            enviar1();
                        }
                    }

                }

            } catch (SerialPortException ex) {
                Logger.getLogger(RegistroIngresoSalida.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ArduinoExcepcion ex) {
                Logger.getLogger(RegistroIngresoSalida.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }
    ;

    };

    void tamañotabla() {
        jTableIngreso.getColumnModel().getColumn(0).setPreferredWidth(10);
        jTableIngreso.getColumnModel().getColumn(0).setResizable(false);
        jTableIngreso.getColumnModel().getColumn(1).setPreferredWidth(10);
        jTableIngreso.getColumnModel().getColumn(2).setPreferredWidth(20);
        jTableIngreso.getColumnModel().getColumn(3).setPreferredWidth(15);
        jTableIngreso.getColumnModel().getColumn(4).setPreferredWidth(15);
        jTableIngreso.getColumnModel().getColumn(5).setPreferredWidth(15);
        jTableIngreso.getColumnModel().getColumn(6).setPreferredWidth(10);
        jTableIngreso.getColumnModel().getColumn(7).setPreferredWidth(10);
        jTableIngreso.getColumnModel().getColumn(8).setPreferredWidth(20);
        jTableIngreso.setRowHeight(20);
    }

    public RegistroIngresoSalida() {//habilitar y dessabilitar objetos
        initComponents();
        llamado();
        // RbtnRegistro.setSelected(true);
        tabla = new DefaultTableModel(null, titulo_columnas);
        this.jTableIngreso.setModel(tabla);
        txtId.setVisible(true);
        cboMateria1.setEnabled(false);
        cboMater.setEnabled(false);
        cboMateriaBusqueda.setEnabled(false);
        cboMateriaBusqueda.setEnabled(false);
        txtTitulo.setEnabled(false);
        btnBuscar.setVisible(false);
        idAsignatura.setVisible(false);
        this.setLocationRelativeTo(null);
        btnRegistrar.setEnabled(false);// BOTON REGISTRAR SE ENCUENTRA APAGADO
        txtID.setEnabled(false);
        txtNombre.setEnabled(false);
        txtApellido.setEnabled(false);
        txtCU.setEnabled(false);
        txtCI.setEnabled(false);
        txtMateria.setEnabled(false);
        txtSemestre.setEnabled(false);
        btnPDF.setEnabled(false);
        verificardocente();
        try {
            conexion.arduinoRXTX("COM3", 9600, listen);
        } catch (ArduinoExcepcion ex) {
            Logger.getLogger(RegistroIngresoSalida.class.getName()).log(Level.SEVERE, null, ex);
        }
        conexion.setTimeOut(2);
    }

    public void actualizarusuario() {
        String materia = cboMateria1.getSelectedItem().toString();
        try {
            con = coneccioBD();

            ps = (PreparedStatement) con.prepareStatement("UPDATE asignatura, estudiantes "
                    + "SET estudiantes.idtarjeta='" + txtID.getText() + "',estudiantes.nombre='" + txtNombre.getText() + "', estudiantes.apellido='" + txtApellido.getText() + "',estudiantes.cu='" + txtCU.getText() + "', estudiantes.ci='" + txtCI.getText() + "',asignatura.nombre_materia='" + materia + "',asignatura.semestre='" + txtSemestre.getText() + "' "
                    + "WHERE asignatura.estudiantes_id=estudiantes.id and asignatura.id='" + idAsignatura.getText() + "'");
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
        this.idAsignatura.setText(null);
    }

    public void actualizarusuario1() {
        String materia = cboMater.getSelectedItem().toString();

        try {
            con = coneccioBD();
            ps = con.prepareStatement("SELECT asignatura.id "
                    + "FROM estudiantes,asignatura "
                    + "WHERE asignatura.estudiantes_id=estudiantes.id AND estudiantes.idtarjeta='" + txtID.getText() + "' AND asignatura.nombre_materia='" + materia + "' AND asignatura.estado='Activo'");
            rs = ps.executeQuery();
            if (rs.next()) {
                idAsignatura.setText(rs.getString("asignatura.id"));
                // JOptionPane.showMessageDialog(null, "Usuario Actualizado");
            } else {
                JOptionPane.showMessageDialog(null, "Error al Actualizar ID");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        String a = idAsignatura.getText();
        try {
            con = coneccioBD();
            ps = (PreparedStatement) con.prepareStatement("UPDATE estudiantes, asignatura "
                    + "SET estudiantes.idtarjeta='" + txtID.getText() + "',estudiantes.nombre='" + txtNombre.getText() + "', estudiantes.apellido='" + txtApellido.getText() + "',estudiantes.cu='" + txtCU.getText() + "', estudiantes.ci='" + txtCI.getText() + "',asignatura.nombre_materia='" + materia + "',asignatura.semestre='" + txtSemestre.getText() + "' "
                    + "WHERE asignatura.estudiantes_id=estudiantes.id and asignatura.id='" + a + "'");
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
        this.idAsignatura.setText(null);
    }

    public void eliminarusuario() {
        String materia = cboMateria1.getSelectedItem().toString();
        try {
            con = coneccioBD();
            ps = con.prepareStatement("SELECT asignatura.id "
                    + "FROM estudiantes,asignatura "
                    + "WHERE asignatura.estudiantes_id=estudiantes.id AND estudiantes.idtarjeta='" + txtID.getText() + "' AND estudiantes.nombre='" + txtNombre.getText() + "' AND estudiantes.apellido='" + txtApellido.getText() + "' AND estudiantes.cu='" + txtCU.getText() + "' AND estudiantes.ci='" + txtCI.getText() + "' AND asignatura.nombre_materia='" + materia + "'");
            rs = ps.executeQuery();
            if (rs.next()) {
                idAsignatura.setText(rs.getString("asignatura.id"));
                // JOptionPane.showMessageDialog(null, "Usuario Actualizado");
            } else {
                JOptionPane.showMessageDialog(null, "Error al Actualizar");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            con = coneccioBD();
            ps = (PreparedStatement) con.prepareStatement("DELETE FROM ingreso_salida WHERE asignatura_id='" + idAsignatura.getText() + "'");
            int res = ps.executeUpdate();
            if (res > 0) {
            } else {
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            con = coneccioBD();
            ps = (PreparedStatement) con.prepareStatement("DELETE FROM asignatura WHERE id='" + idAsignatura.getText() + "'");
            int res = ps.executeUpdate();
            if (res > 0) {
            } else {
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            con = coneccioBD();
            ps = (PreparedStatement) con.prepareStatement("DELETE FROM estudiantes WHERE id='" + txtId.getText() + "'");
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
    }

    public void eliminarusuario1() {
        String materia = cboMater.getSelectedItem().toString();
        try {
            con = coneccioBD();
            ps = con.prepareStatement("SELECT asignatura.id "
                    + "FROM estudiantes,asignatura "
                    + "WHERE asignatura.estudiantes_id=estudiantes.id AND estudiantes.idtarjeta='" + txtID.getText() + "' AND estudiantes.nombre='" + txtNombre.getText() + "' AND estudiantes.apellido='" + txtApellido.getText() + "' AND estudiantes.cu='" + txtCU.getText() + "' AND estudiantes.ci='" + txtCI.getText() + "'AND asignatura.nombre_materia='" + materia + "'");
            rs = ps.executeQuery();
            if (rs.next()) {
                idAsignatura.setText(rs.getString("asignatura.id"));
            } else {
                JOptionPane.showMessageDialog(null, "Error al Actualizar");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            con = coneccioBD();
            ps = (PreparedStatement) con.prepareStatement("DELETE FROM ingreso_salida WHERE asignatura_id='" + idAsignatura.getText() + "'");
            int res = ps.executeUpdate();
            if (res > 0) {
            } else {
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            con = coneccioBD();
            ps = (PreparedStatement) con.prepareStatement("DELETE FROM asignatura WHERE id='" + idAsignatura.getText() + "'");
            int res = ps.executeUpdate();
            if (res > 0) {
            } else {
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            con = coneccioBD();
            ps = (PreparedStatement) con.prepareStatement("DELETE FROM estudiantes WHERE id='" + txtId.getText() + "'");
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
    }

    public void buscar() {
        try {
            con = coneccioBD();//CONECTA A BD
//////////////////////////SELECCIONA TODOS LOS DATOS DE BD PARATIR DE IDTARJETA///////////////////////////////////
            ps = con.prepareStatement("SELECT estudiantes.id,estudiantes.idtarjeta,estudiantes.nombre,estudiantes.apellido,estudiantes.cu,estudiantes.ci,asignatura.nombre_materia\n"
                    + " FROM estudiantes,asignatura "
                    + " WHERE asignatura.estudiantes_id=estudiantes.id and estudiantes.idtarjeta='" + txtID.getText() + "'");
            rs = ps.executeQuery();//Guarda
            if (rs.next()) {
                txtId.setText(rs.getString("estudiantes.id")); // Busco el id y pongo en el testfield
                txtNombre.setText(rs.getString("estudiantes.nombre"));
                txtApellido.setText(rs.getString("estudiantes.apellido"));
                txtCU.setText(rs.getString("estudiantes.cu"));
                txtCI.setText(rs.getString("estudiantes.ci"));
                cboMater.setSelectedItem(rs.getString("asignatura.nombre_materia"));
                cboMateria1.setSelectedItem(rs.getString("asignatura.nombre_materia"));
                txtMateria.setText(rs.getString("asignatura.nombre_materia"));
            } else {
                JOptionPane.showMessageDialog(null, "Estudiante no registrado");
                limpiarcajas1();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            String materia = cboMateria1.getSelectedItem().toString();
            con = coneccioBD();
            ps = con.prepareStatement("SELECT asignatura.id "
                    + "FROM estudiantes,asignatura "
                    + "WHERE asignatura.estudiantes_id=estudiantes.id AND estudiantes.idtarjeta='" + txtID.getText() + "' AND estudiantes.nombre='" + txtNombre.getText() + "' AND estudiantes.apellido='" + txtApellido.getText() + "' AND estudiantes.cu='" + txtCU.getText() + "' AND estudiantes.ci='" + txtCI.getText() + "' AND asignatura.nombre_materia='" + materia + "' AND asignatura.estado='Activo'");
            rs = ps.executeQuery();
            if (rs.next()) {
                idAsignatura.setText(rs.getString("asignatura.id"));
            } else {
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void enviar() {
        String envio = "";
        try {
            con = coneccioBD();//CONECTA A BD
//////////////////////////SELECCIONA TODOS LOS DATOS DE BD APARTIR DE IDTARJETA///////////////////////////////////
            ps = con.prepareStatement("SELECT * FROM estudiantes WHERE idtarjeta=?");
            ps.setString(1, txtID.getText());
            rs = ps.executeQuery();
            if (rs.next()) {//Validar si va a un 
                txtNombre.setText(rs.getString("nombre"));
                txtApellido.setText(rs.getString("apellido"));
                envio = txtNombre.getText() + " " + txtApellido.getText();
                conexion.sendData(envio);//envio el dato hacia el arduino 
             } else {
                JOptionPane.showMessageDialog(null, "Estudiante no registrado");
                limpiarcajas1();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void enviar1() {
        String envio = "";
        try {
            con = coneccioBD();//CONECTA A BD
//////////////////////////SELECCIONA TODOS LOS DATOS DE BD APARTIR DE IDTARJETA///////////////////////////////////
            ps = con.prepareStatement("SELECT * FROM estudiantes WHERE idtarjeta=?");
            ps.setString(1, txtID.getText());
            rs = ps.executeQuery();
            if (rs.next()) {//Validar si va a un 
                txtNombre.setText(rs.getString("nombre"));
                txtApellido.setText(rs.getString("apellido"));
                envio = "";
                conexion.sendData(envio);
            } else {
                JOptionPane.showMessageDialog(null, "Estudiante no registrado");
                limpiarcajas1();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void busquedaFiltrada() throws SQLException {
        String materia = cboMateriaBusqueda.getSelectedItem().toString();
        tabla = new DefaultTableModel(null, titulo_columnas);
        try {
            con = coneccioBD();
            //Busca valores a medida que se escribe
            String consulta_tabla = "SELECT * FROM ingresosalida WHERE materia LIKE " + '"' + materia + "%" + '"';
            ps = con.prepareStatement(consulta_tabla);
            rs = ps.executeQuery();
            while (rs.next()) {//Carga los valores de la BD en la tabla
                columna[0] = rs.getString(2);
                columna[1] = rs.getString(3);
                columna[2] = rs.getString(4);
                columna[3] = rs.getString(5);
                columna[4] = rs.getString(6);
                columna[5] = rs.getString(7);
                columna[6] = rs.getString(8);
                columna[7] = rs.getString(9);
                columna[8] = rs.getString(10);
                tabla.addRow(columna);
            }
            jTableIngreso.setModel(tabla);
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

    ///////     FUNION CREADA PARA VALIDAR ESPACIOS    ////////
    public void validar() { //Funcion que permite el llenado de los espacios de registro
        /////////    SI TODOS LOS CAMPOS ESTA VACIO   ///////////7          
        if (txtID.getText().isEmpty() || txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() || txtCU.getText().isEmpty()
                || txtCI.getText().isEmpty()) {
            btnRegistrar.setEnabled(false);// BOTON REGISTRAR SE ENCUENTRA APAGADO
        } else {
            btnRegistrar.setEnabled(true);// BOTON REGISTRAR SE ENCUENTRA ENCENDIDO
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupo_botones = new javax.swing.ButtonGroup();
        cboMater = new javax.swing.JComboBox<>();
        cboMateria1 = new javax.swing.JComboBox<>();
        jLabelID = new javax.swing.JLabel();
        jLabelNombre = new javax.swing.JLabel();
        jLabelApellido = new javax.swing.JLabel();
        jLabelCU = new javax.swing.JLabel();
        jLabelCI = new javax.swing.JLabel();
        jLabelMateria = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtCU = new javax.swing.JTextField();
        txtCI = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        jButtonVolver = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnRegistro = new javax.swing.JRadioButton();
        RbtnIngreso = new javax.swing.JRadioButton();
        RbtnSalida = new javax.swing.JRadioButton();
        txtTitulo = new javax.swing.JTextField();
        cboMateriaBusqueda = new javax.swing.JComboBox<>();
        cboMateriaBusqueda1 = new javax.swing.JComboBox<>();
        txtMateria = new javax.swing.JTextField();
        jLabelNombre3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableIngreso = new javax.swing.JTable();
        LabelID = new javax.swing.JLabel();
        Calendario = new com.toedter.calendar.JDateChooser();
        btnBuscar1 = new javax.swing.JButton();
        jLabelNombre1 = new javax.swing.JLabel();
        lblNomApe = new javax.swing.JLabel();
        lblEnviar = new javax.swing.JLabel();
        btnPDF = new javax.swing.JButton();
        idAsignatura = new javax.swing.JTextField();
        jLabelMateria3 = new javax.swing.JLabel();
        txtSemestre = new javax.swing.JTextField();
        jLabelFondo = new javax.swing.JLabel();
        btningre = new javax.swing.JButton();
        btnactu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cboMater.setBackground(new java.awt.Color(0, 153, 153));
        cboMater.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cboMater.setForeground(new java.awt.Color(255, 255, 255));
        cboMater.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Asignatura", "ELC-415", "ELC-513" }));
        cboMater.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaterActionPerformed(evt);
            }
        });
        getContentPane().add(cboMater, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 360, 180, 30));

        cboMateria1.setBackground(new java.awt.Color(0, 153, 153));
        cboMateria1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cboMateria1.setForeground(new java.awt.Color(255, 255, 255));
        cboMateria1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Asignatura", "ELC-315", "ELC-416" }));
        cboMateria1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMateria1ActionPerformed(evt);
            }
        });
        getContentPane().add(cboMateria1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 360, 180, 30));

        jLabelID.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelID.setForeground(new java.awt.Color(255, 255, 255));
        jLabelID.setText("ID TARJETA:");
        getContentPane().add(jLabelID, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, 30));

        jLabelNombre.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelNombre.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombre.setText("NOMBRE:");
        getContentPane().add(jLabelNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, 30));

        jLabelApellido.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelApellido.setForeground(new java.awt.Color(255, 255, 255));
        jLabelApellido.setText("APELLIDO:");
        getContentPane().add(jLabelApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, -1, 30));

        jLabelCU.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelCU.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCU.setText("CU:");
        getContentPane().add(jLabelCU, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, -1, 30));

        jLabelCI.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelCI.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCI.setText("CI:");
        getContentPane().add(jLabelCI, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, -1, 30));

        jLabelMateria.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelMateria.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMateria.setText("MATERIA:");
        getContentPane().add(jLabelMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 360, -1, 30));

        txtID.setBackground(new java.awt.Color(0, 153, 153));
        txtID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtID.setForeground(new java.awt.Color(255, 255, 255));
        txtID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIDKeyReleased(evt);
            }
        });
        getContentPane().add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 170, 30));

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
        getContentPane().add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 170, 30));

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
        getContentPane().add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 170, 30));

        txtCU.setBackground(new java.awt.Color(0, 153, 153));
        txtCU.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCU.setForeground(new java.awt.Color(255, 255, 255));
        txtCU.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCUKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCUKeyTyped(evt);
            }
        });
        getContentPane().add(txtCU, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 170, 30));

        txtCI.setBackground(new java.awt.Color(0, 153, 153));
        txtCI.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCI.setForeground(new java.awt.Color(255, 255, 255));
        txtCI.setDoubleBuffered(true);
        txtCI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCIKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCIKeyTyped(evt);
            }
        });
        getContentPane().add(txtCI, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 170, 30));

        txtId.setEditable(false);
        txtId.setEnabled(false);
        getContentPane().add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 40, 30));

        jButtonVolver.setBackground(new java.awt.Color(102, 102, 255));
        jButtonVolver.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonVolver.setForeground(new java.awt.Color(255, 255, 255));
        jButtonVolver.setText("Volver");
        jButtonVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVolverActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 0, -1, -1));

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
        getContentPane().add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, -1, 30));

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
        getContentPane().add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, -1, 30));

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
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 450, -1, 30));

        btnBuscar.setBackground(new java.awt.Color(51, 51, 255));
        btnBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, 30));

        grupo_botones.add(btnRegistro);
        btnRegistro.setText("Registro");
        btnRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistroActionPerformed(evt);
            }
        });
        getContentPane().add(btnRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, -1, -1));

        grupo_botones.add(RbtnIngreso);
        RbtnIngreso.setText("Ingreso");
        RbtnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RbtnIngresoActionPerformed(evt);
            }
        });
        getContentPane().add(RbtnIngreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, -1, -1));

        grupo_botones.add(RbtnSalida);
        RbtnSalida.setText("Salida");
        RbtnSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RbtnSalidaActionPerformed(evt);
            }
        });
        getContentPane().add(RbtnSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, -1, -1));

        txtTitulo.setBackground(new java.awt.Color(0, 153, 153));
        txtTitulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTitulo.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(txtTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 450, 670, 30));

        cboMateriaBusqueda.setBackground(new java.awt.Color(0, 153, 153));
        cboMateriaBusqueda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cboMateriaBusqueda.setForeground(new java.awt.Color(255, 255, 255));
        cboMateriaBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Asignatura", "ELC-315", "ELC-416" }));
        getContentPane().add(cboMateriaBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, 180, 30));

        cboMateriaBusqueda1.setBackground(new java.awt.Color(0, 153, 153));
        cboMateriaBusqueda1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cboMateriaBusqueda1.setForeground(new java.awt.Color(255, 255, 255));
        cboMateriaBusqueda1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Asignatura", "ELC-415", "ELC-513" }));
        getContentPane().add(cboMateriaBusqueda1, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, 180, 30));

        txtMateria.setBackground(new java.awt.Color(0, 153, 153));
        txtMateria.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtMateria.setForeground(new java.awt.Color(255, 255, 255));
        txtMateria.setDoubleBuffered(true);
        getContentPane().add(txtMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 360, 170, 30));

        jLabelNombre3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelNombre3.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombre3.setText("Título de Práctica:");
        getContentPane().add(jLabelNombre3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 450, -1, 30));

        jTableIngreso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "UID", "Nombre", "Apellido", "CU", "CI", "Materia", "Hora Entrada", "Hora Salida", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableIngreso);
        if (jTableIngreso.getColumnModel().getColumnCount() > 0) {
            jTableIngreso.getColumnModel().getColumn(0).setResizable(false);
            jTableIngreso.getColumnModel().getColumn(1).setResizable(false);
            jTableIngreso.getColumnModel().getColumn(2).setResizable(false);
            jTableIngreso.getColumnModel().getColumn(3).setResizable(false);
            jTableIngreso.getColumnModel().getColumn(4).setResizable(false);
            jTableIngreso.getColumnModel().getColumn(5).setResizable(false);
            jTableIngreso.getColumnModel().getColumn(6).setResizable(false);
            jTableIngreso.getColumnModel().getColumn(7).setResizable(false);
            jTableIngreso.getColumnModel().getColumn(8).setResizable(false);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 50, 1010, 380));

        LabelID.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        LabelID.setForeground(new java.awt.Color(255, 255, 255));
        LabelID.setText("ID:");
        getContentPane().add(LabelID, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, -1, 30));
        getContentPane().add(Calendario, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 20, 190, -1));

        btnBuscar1.setBackground(new java.awt.Color(51, 51, 255));
        btnBuscar1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnBuscar1.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar1.setText("Buscar");
        btnBuscar1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnBuscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscar1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuscar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 10, -1, 30));

        jLabelNombre1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelNombre1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombre1.setText("DOCENTE:");
        getContentPane().add(jLabelNombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 30));

        lblNomApe.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNomApe.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblNomApe, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 180, 30));
        getContentPane().add(lblEnviar, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 400, 170, 30));

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
        getContentPane().add(btnPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 450, -1, 30));

        idAsignatura.setEditable(false);
        idAsignatura.setEnabled(false);
        getContentPane().add(idAsignatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 50, 40));

        jLabelMateria3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelMateria3.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMateria3.setText("SEMESTRE:");
        getContentPane().add(jLabelMateria3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, -1, 30));

        txtSemestre.setBackground(new java.awt.Color(0, 153, 153));
        txtSemestre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtSemestre.setForeground(new java.awt.Color(255, 255, 255));
        txtSemestre.setDoubleBuffered(true);
        getContentPane().add(txtSemestre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 170, 30));

        jLabelFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Fondo4.jpg"))); // NOI18N
        getContentPane().add(jLabelFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1366, 490));

        btningre.setBackground(new java.awt.Color(51, 51, 255));
        btningre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btningre.setForeground(new java.awt.Color(255, 255, 255));
        btningre.setText("ingre");
        btningre.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btningre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btningreActionPerformed(evt);
            }
        });
        getContentPane().add(btningre, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, 30));

        btnactu.setBackground(new java.awt.Color(51, 51, 255));
        btnactu.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnactu.setForeground(new java.awt.Color(255, 255, 255));
        btnactu.setText("actu");
        btnactu.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnactu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactuActionPerformed(evt);
            }
        });
        getContentPane().add(btnactu, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        if (txtId.getText().isEmpty()) {
            try {
                con = coneccioBD();
                ps = (PreparedStatement) con.prepareStatement("INSERT INTO estudiantes (idtarjeta, nombre, apellido, cu, ci) VALUES(?,?,?,?,?) ");
                ps.setString(1, txtID.getText());// en este orden insertamos a la BD
                ps.setString(2, txtNombre.getText());
                ps.setString(3, txtApellido.getText());
                ps.setString(4, txtCU.getText());
                ps.setString(5, txtCI.getText());
                int res = ps.executeUpdate();
                if (res > 0) {
                    JOptionPane.showMessageDialog(null, "Estudiante Registrado");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Registrar");
                }

            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                con = coneccioBD();//CONECTA A BD
//////////////////////////SELECCIONA TODOS LOS DATOS DE BD PARATIR DE IDTARJETA///////////////////////////////////
                ps = con.prepareStatement("SELECT * FROM estudiantes WHERE idtarjeta=?");
                ps.setString(1, txtID.getText());//Prepara la sentencia
                rs = ps.executeQuery();//Guarda
                if (rs.next()) {
                    txtId.setText(rs.getString("id")); // Busco el id y pongo en el testfield
                } else {
                    limpiarcajas1();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                con = coneccioBD();
                ps = (PreparedStatement) con.prepareStatement("INSERT INTO asignatura (nombre_materia,semestre,estudiantes_id,estado)VALUES(?,?,?,?) ");
                ps.setString(1, txtMateria.getText());// en este orden insertamos a la BD
                ps.setString(2, txtSemestre.getText());
                ps.setString(3, txtId.getText());
                ps.setString(4, "Activo");
                int res = ps.executeUpdate();
                if (res > 0) {
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Registrar");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            try {
                con = coneccioBD();//CONECTA A BD
//////////////////////////SELECCIONA TODOS LOS DATOS DE BD PARATIR DE IDTARJETA///////////////////////////////////
                ps = con.prepareStatement("SELECT * FROM estudiantes WHERE idtarjeta=?");
                ps.setString(1, txtID.getText());//Prepara la sentencia
                rs = ps.executeQuery();//Guarda
                if (rs.next()) {
                    txtId.setText(rs.getString("id")); // Busco el id y pongo en el testfield
                } else {
                    JOptionPane.showMessageDialog(null, "Estudiante no registrado");
                    limpiarcajas1();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                con = coneccioBD();
                ps = (PreparedStatement) con.prepareStatement("INSERT INTO asignatura (nombre_materia,semestre,estudiantes_id,estado)VALUES(?,?,?,?) ");
                ps.setString(1, txtMateria.getText());// en este orden insertamos a la BD
                ps.setString(2, txtSemestre.getText());
                ps.setString(3, txtId.getText());
                ps.setString(4, "Activo");
                int res = ps.executeUpdate();
                if (res > 0) {
                    JOptionPane.showMessageDialog(null, "Estudiante Registrado");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Registrar");
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        limpiarcajas();
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void jButtonVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVolverActionPerformed
//////      AL PRECIONAR EL BOTON ENVIA A LA PESTAÑA Autenticacion  ///////        
        Acceso ventana = new Acceso();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonVolverActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        String Usuario1 = "Mario Cruz Cueto";
        String Usuario2 = "Franco Fidel Rivero";
        if (lblNomApe.getText().equals(Usuario1)) {
            actualizarusuario();
        } else {
            if (lblNomApe.getText().equals(Usuario2)) {
                actualizarusuario1();
            }
        }
        limpiarcajas();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        String Usuario1 = "Mario Cruz Cueto";
        String Usuario2 = "Franco Fidel Rivero";
        if (lblNomApe.getText().equals(Usuario1)) {
            eliminarusuario();
        } else {
            if (lblNomApe.getText().equals(Usuario2)) {
                eliminarusuario1();
            }
        }
        limpiarcajas();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyReleased
        validar();//LLAMAR LA FUNCION CREADA
    }//GEN-LAST:event_txtNombreKeyReleased

    private void txtApellidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyReleased
        validar();//LLAMAR LA FUNCION CREADA
    }//GEN-LAST:event_txtApellidoKeyReleased

    private void txtCUKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCUKeyReleased
        validar();//LLAMAR LA FUNCION CREADA
    }//GEN-LAST:event_txtCUKeyReleased

    private void txtCIKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCIKeyReleased
        validar();//LLAMAR LA FUNCION CREADA
    }//GEN-LAST:event_txtCIKeyReleased

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

    private void txtIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDKeyReleased
        validar();//LLAMAR LA FUNCION CREADA
    }//GEN-LAST:event_txtIDKeyReleased


    private void btnBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscar1ActionPerformed
        String Usuario1 = "Mario Cruz Cueto";
        String Usuario2 = "Franco Fidel Rivero";
        if (lblNomApe.getText().equals(Usuario1)) {
            try {
                cargaDatosTabla();
                int num = cboMateriaBusqueda.getSelectedIndex();
                if (num != 0) {
                    txtTitulo.setEnabled(true);
                    btnPDF.setEnabled(true);
                } else {
                    txtTitulo.setEnabled(false);
                }
            } catch (SQLException ex) {
                Logger.getLogger(NominaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (lblNomApe.getText().equals(Usuario2)) {
                try {
                    cargaDatosTabla1();
                    int num = cboMateriaBusqueda1.getSelectedIndex();
                    if (num != 0) {
                        txtTitulo.setEnabled(true);
                        btnPDF.setEnabled(true);
                    } else {
                        txtTitulo.setEnabled(false);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(NominaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btnBuscar1ActionPerformed

    private void RbtnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RbtnIngresoActionPerformed
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnBuscar.setEnabled(false);
        txtID.setEnabled(false);
        txtNombre.setEnabled(false);
        txtApellido.setEnabled(false);
        txtCU.setEnabled(false);
        txtCI.setEnabled(false);
        cboMater.setEnabled(false);
        txtMateria.setEnabled(false);
        limpiarcajas();
        limpiartabla();
    }//GEN-LAST:event_RbtnIngresoActionPerformed

    private void RbtnSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RbtnSalidaActionPerformed
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnBuscar.setEnabled(false);
        txtID.setEnabled(false);
        txtNombre.setEnabled(false);
        txtApellido.setEnabled(false);
        txtCU.setEnabled(false);
        txtCI.setEnabled(false);
        cboMater.setEnabled(false);
        txtMateria.setEnabled(false);
        limpiarcajas();
        limpiartabla();
    }//GEN-LAST:event_RbtnSalidaActionPerformed
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
            cboMateriaBusqueda.setEnabled(true);
        } else {
            if (lblNomApe.getText().equals(Usuario2)) {
                cboMateriaBusqueda.setVisible(false);
                cboMateriaBusqueda1.setVisible(true);
                cboMateriaBusqueda1.setEnabled(true);
            }
        }
    }


    private void btnRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistroActionPerformed
        String Usuario1 = "Mario Cruz Cueto";
        String Usuario2 = "Franco Fidel Rivero";
        if (lblNomApe.getText().equals(Usuario1)) {
            cboMater.setVisible(false);
            cboMateria1.setVisible(true);
            cboMateria1.setEnabled(true);
            btnActualizar.setEnabled(true);
            btnEliminar.setEnabled(true);
            btnBuscar.setVisible(false);
            txtID.setEnabled(false);
            txtNombre.setEnabled(true);
            txtApellido.setEnabled(true);
            txtCU.setEnabled(true);
            txtCI.setEnabled(true);
            txtMateria.setEnabled(false);
            txtSemestre.setEnabled(false);
        } else {
            if (lblNomApe.getText().equals(Usuario2)) {
                cboMater.setVisible(true);
                cboMateria1.setVisible(false);
                cboMater.setEnabled(true);
                btnActualizar.setEnabled(true);
                btnEliminar.setEnabled(true);
                btnBuscar.setEnabled(true);
                txtID.setEnabled(false);
                txtNombre.setEnabled(true);
                txtApellido.setEnabled(true);
                txtCU.setEnabled(true);
                txtCI.setEnabled(true);
                txtMateria.setEnabled(true);
            }
        }
        limpiarcajas();
        limpiartabla();
    }//GEN-LAST:event_btnRegistroActionPerformed

    private void btningreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btningreActionPerformed
        ingreso();
        try {
            cargarDatosTablaIngreso();
        } catch (SQLException ex) {
            Logger.getLogger(RegistroIngresoSalida.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btningreActionPerformed

    private void btnactuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactuActionPerformed
        salida();
        try {
            cargarDatosTablaSalida();
        } catch (SQLException ex) {
            Logger.getLogger(RegistroIngresoSalida.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnactuActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscar();      
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void cboMaterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaterActionPerformed
        String mat = cboMater.getSelectedItem().toString();
        int num = cboMater.getSelectedIndex();
        if (num != 0) {
            txtMateria.setText(mat);
        }
        if (num == 1) {
            txtSemestre.setText("4");
        }
        if (num == 2) {
            txtSemestre.setText("5");
        }

    }//GEN-LAST:event_cboMaterActionPerformed

    private void cboMateria1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMateria1ActionPerformed
        String mat = cboMateria1.getSelectedItem().toString();
        int num = cboMateria1.getSelectedIndex();
        if (num != 0) {
            txtMateria.setText(mat);
        }
        if (num == 1) {
            txtSemestre.setText("3");
        }
        if (num == 2) {
            txtSemestre.setText("4");
        }
    }//GEN-LAST:event_cboMateria1ActionPerformed

    private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFActionPerformed
        if (txtTitulo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese Título de la Práctica");
        } else {
            String Usuario1 = "Mario Cruz Cueto";
            String Usuario2 = "Franco Fidel Rivero";
            if (lblNomApe.getText().equals(Usuario1)) {// Nombre de Docente en la ventana
                try {
                    pdf1();//207
                    limpiarcajas1();

                } catch (SQLException ex) {
                    Logger.getLogger(NominaEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                if (lblNomApe.getText().equals(Usuario2)) {
                    try {
                        pdf2();//292

                    } catch (SQLException ex) {
                        Logger.getLogger(RegistroIngresoSalida.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }
        limpiarcajas1();
        cboMateriaBusqueda.setSelectedIndex(0);
        cboMateriaBusqueda1.setSelectedIndex(0);


    }//GEN-LAST:event_btnPDFActionPerformed

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
            java.util.logging.Logger.getLogger(RegistroIngresoSalida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroIngresoSalida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroIngresoSalida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroIngresoSalida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroIngresoSalida().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Calendario;
    private javax.swing.JLabel LabelID;
    private javax.swing.JRadioButton RbtnIngreso;
    private javax.swing.JRadioButton RbtnSalida;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscar1;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnPDF;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JRadioButton btnRegistro;
    private javax.swing.JButton btnactu;
    private javax.swing.JButton btningre;
    private javax.swing.JComboBox<String> cboMater;
    private javax.swing.JComboBox<String> cboMateria1;
    private javax.swing.JComboBox<String> cboMateriaBusqueda;
    private javax.swing.JComboBox<String> cboMateriaBusqueda1;
    private javax.swing.ButtonGroup grupo_botones;
    private javax.swing.JTextField idAsignatura;
    private javax.swing.JButton jButtonVolver;
    private javax.swing.JLabel jLabelApellido;
    private javax.swing.JLabel jLabelCI;
    private javax.swing.JLabel jLabelCU;
    private javax.swing.JLabel jLabelFondo;
    private javax.swing.JLabel jLabelID;
    private javax.swing.JLabel jLabelMateria;
    private javax.swing.JLabel jLabelMateria3;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JLabel jLabelNombre1;
    private javax.swing.JLabel jLabelNombre3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableIngreso;
    private javax.swing.JLabel lblEnviar;
    public static javax.swing.JLabel lblNomApe;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCI;
    private javax.swing.JTextField txtCU;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtMateria;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtSemestre;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
