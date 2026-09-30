// controlador/ControladorAsistencia.java
package controlador;

import modelo.dao.AsistenciaDAO;
import modelo.dao.PersonalDAO;
import modelo.entidades.Asistencia;
import modelo.entidades.Personal;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ControladorAsistencia {
    private AsistenciaDAO asistenciaDAO;
    private PersonalDAO personalDAO;
    
    public ControladorAsistencia() {
        this.asistenciaDAO = new AsistenciaDAO();
        this.personalDAO = new PersonalDAO();
    }
    
    // Registrar entrada
    public boolean registrarEntrada(String dni) {
        try {
            // Buscar personal por DNI
            Personal personal = personalDAO.buscarPorDni(dni);
            
            if (personal == null) {
                JOptionPane.showMessageDialog(null, 
                    "Personal no encontrado con DNI: " + dni, 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            Date fechaActual = new Date();
            
            // Verificar si ya registró entrada hoy
            if (asistenciaDAO.existeRegistroHoy(personal.getIdPersonal(), fechaActual)) {
                JOptionPane.showMessageDialog(null, 
                    "El personal ya registró su entrada el día de hoy", 
                    "Registro duplicado", 
                    JOptionPane.WARNING_MESSAGE);
                return false;
            }
            
            // Crear registro de asistencia
            Asistencia asistencia = new Asistencia();
            asistencia.setIdPersonal(personal.getIdPersonal());
            asistencia.setFecha(fechaActual);
            asistencia.setHoraEntrada(new Time(System.currentTimeMillis()));
            asistencia.setEstado("PRESENTE");
            asistencia.setObservaciones("Entrada registrada");
            
            boolean resultado = asistenciaDAO.registrarEntrada(asistencia);
            
            if (resultado) {
                JOptionPane.showMessageDialog(null, 
                    "Entrada registrada exitosamente\n" +
                    "Personal: " + personal.getNombreCompleto() + "\n" +
                    "Hora: " + new Time(System.currentTimeMillis()), 
                    "Registro exitoso", 
                    JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Error al registrar la entrada", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error: " + e.getMessage(), 
                "Error del sistema", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Registrar salida
    public boolean registrarSalida(String dni) {
        try {
            // Buscar personal por DNI
            Personal personal = personalDAO.buscarPorDni(dni);
            
            if (personal == null) {
                JOptionPane.showMessageDialog(null, 
                    "Personal no encontrado con DNI: " + dni, 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            Date fechaActual = new Date();
            
            // Obtener asistencia del día
            Asistencia asistencia = asistenciaDAO.obtenerAsistenciaDelDia(
                personal.getIdPersonal(), fechaActual);
            
            if (asistencia == null) {
                JOptionPane.showMessageDialog(null, 
                    "No se encontró registro de entrada para hoy", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            if (asistencia.getHoraSalida() != null) {
                JOptionPane.showMessageDialog(null, 
                    "Ya se registró la salida para hoy", 
                    "Registro duplicado", 
                    JOptionPane.WARNING_MESSAGE);
                return false;
            }
            
            Time horaSalida = new Time(System.currentTimeMillis());
            
            // Calcular minutos extras (simplificado)
            long horaEntradaMs = asistencia.getHoraEntrada().getTime();
            long horaSalidaMs = horaSalida.getTime();
            long horasTrabajadasMs = horaSalidaMs - horaEntradaMs;
            int minutosExtras = (int) ((horasTrabajadasMs / (1000 * 60)) - 540); // 540 min = 9 horas
            minutosExtras = Math.max(0, minutosExtras);
            
            boolean resultado = asistenciaDAO.registrarSalida(
                asistencia.getIdAsistencia(), horaSalida, minutosExtras);
            
            if (resultado) {
                JOptionPane.showMessageDialog(null, 
                    "Salida registrada exitosamente\n" +
                    "Personal: " + personal.getNombreCompleto() + "\n" +
                    "Hora: " + horaSalida + "\n" +
                    "Minutos extras: " + minutosExtras, 
                    "Registro exitoso", 
                    JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Error al registrar la salida", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error: " + e.getMessage(), 
                "Error del sistema", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Cargar asistencias en tabla
    public void cargarAsistencias(JTable tabla, Date fechaInicio, Date fechaFin) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        
        List<Asistencia> asistencias = asistenciaDAO.listarPorRangoFechas(fechaInicio, fechaFin);
        
        for (Asistencia asistencia : asistencias) {
            Personal personal = personalDAO.buscarPorDni(
                String.valueOf(asistencia.getIdPersonal()));
            
            Object[] fila = {
                asistencia.getIdAsistencia(),
                personal != null ? personal.getDni() : "N/A",
                personal != null ? personal.getNombreCompleto() : "N/A",
                asistencia.getFecha(),
                asistencia.getHoraEntrada(),
                asistencia.getHoraSalida() != null ? asistencia.getHoraSalida() : "Sin registro",
                asistencia.getMinutosTardanza(),
                asistencia.getMinutosExtras(),
                asistencia.getEstado()
            };
            modelo.addRow(fila);
        }
    }
    
    // Buscar personal por DNI
    public Personal buscarPersonal(String dni) {
        return personalDAO.buscarPorDni(dni);
    }
}
