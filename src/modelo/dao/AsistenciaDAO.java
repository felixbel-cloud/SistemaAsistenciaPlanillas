// modelo/dao/AsistenciaDAO.java
package modelo.dao;

import modelo.conexion.ConexionDB;
import modelo.entidades.Asistencia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class AsistenciaDAO {
    private Connection conexion;
    
    public AsistenciaDAO() {
        this.conexion = ConexionDB.getConexion();
    }
    
    // Registrar entrada
    public boolean registrarEntrada(Asistencia asistencia) {
        String sql = "INSERT INTO asistencia (id_personal, fecha, hora_entrada, estado, observaciones) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, asistencia.getIdPersonal());
            ps.setDate(2, new java.sql.Date(asistencia.getFecha().getTime()));
            ps.setTime(3, asistencia.getHoraEntrada());
            ps.setString(4, asistencia.getEstado());
            ps.setString(5, asistencia.getObservaciones());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar entrada: " + e.getMessage());
            return false;
        }
    }
    
    // Registrar salida
    public boolean registrarSalida(int idAsistencia, Time horaSalida, int minutosExtras) {
        String sql = "UPDATE asistencia SET hora_salida = ?, minutos_extras = ? WHERE id_asistencia = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setTime(1, horaSalida);
            ps.setInt(2, minutosExtras);
            ps.setInt(3, idAsistencia);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar salida: " + e.getMessage());
            return false;
        }
    }
    
    // Verificar si ya existe registro del día
    public boolean existeRegistroHoy(int idPersonal, Date fecha) {
        String sql = "SELECT COUNT(*) FROM asistencia WHERE id_personal = ? AND fecha = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idPersonal);
            ps.setDate(2, new java.sql.Date(fecha.getTime()));
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar registro: " + e.getMessage());
        }
        
        return false;
    }
    
    // Obtener asistencia del día
    public Asistencia obtenerAsistenciaDelDia(int idPersonal, Date fecha) {
        Asistencia asistencia = null;
        String sql = "SELECT * FROM asistencia WHERE id_personal = ? AND fecha = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idPersonal);
            ps.setDate(2, new java.sql.Date(fecha.getTime()));
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    asistencia = new Asistencia();
                    asistencia.setIdAsistencia(rs.getInt("id_asistencia"));
                    asistencia.setIdPersonal(rs.getInt("id_personal"));
                    asistencia.setFecha(rs.getDate("fecha"));
                    asistencia.setHoraEntrada(rs.getTime("hora_entrada"));
                    asistencia.setHoraSalida(rs.getTime("hora_salida"));
                    asistencia.setMinutosExtras(rs.getInt("minutos_extras"));
                    asistencia.setMinutosTardanza(rs.getInt("minutos_tardanza"));
                    asistencia.setEstado(rs.getString("estado"));
                    asistencia.setObservaciones(rs.getString("observaciones"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener asistencia: " + e.getMessage());
        }
        
        return asistencia;
    }
    
    // Listar asistencias por rango de fechas
    public List<Asistencia> listarPorRangoFechas(Date fechaInicio, Date fechaFin) {
        List<Asistencia> listaAsistencias = new ArrayList<>();
        String sql = "SELECT * FROM asistencia WHERE fecha BETWEEN ? AND ? ORDER BY fecha DESC";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(fechaInicio.getTime()));
            ps.setDate(2, new java.sql.Date(fechaFin.getTime()));
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Asistencia asistencia = new Asistencia();
                    asistencia.setIdAsistencia(rs.getInt("id_asistencia"));
                    asistencia.setIdPersonal(rs.getInt("id_personal"));
                    asistencia.setFecha(rs.getDate("fecha"));
                    asistencia.setHoraEntrada(rs.getTime("hora_entrada"));
                    asistencia.setHoraSalida(rs.getTime("hora_salida"));
                    asistencia.setMinutosExtras(rs.getInt("minutos_extras"));
                    asistencia.setMinutosTardanza(rs.getInt("minutos_tardanza"));
                    asistencia.setEstado(rs.getString("estado"));
                    asistencia.setObservaciones(rs.getString("observaciones"));
                    listaAsistencias.add(asistencia);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar asistencias: " + e.getMessage());
        }
        
        return listaAsistencias;
    }
    
    // Listar asistencias por personal y mes
    public List<Asistencia> listarPorPersonalYMes(int idPersonal, String periodo) {
        List<Asistencia> listaAsistencias = new ArrayList<>();
        String sql = "SELECT * FROM asistencia WHERE id_personal = ? AND DATE_FORMAT(fecha, '%Y-%m') = ? ORDER BY fecha";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idPersonal);
            ps.setString(2, periodo);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Asistencia asistencia = new Asistencia();
                    asistencia.setIdAsistencia(rs.getInt("id_asistencia"));
                    asistencia.setIdPersonal(rs.getInt("id_personal"));
                    asistencia.setFecha(rs.getDate("fecha"));
                    asistencia.setHoraEntrada(rs.getTime("hora_entrada"));
                    asistencia.setHoraSalida(rs.getTime("hora_salida"));
                    asistencia.setMinutosExtras(rs.getInt("minutos_extras"));
                    asistencia.setMinutosTardanza(rs.getInt("minutos_tardanza"));
                    asistencia.setEstado(rs.getString("estado"));
                    asistencia.setObservaciones(rs.getString("observaciones"));
                    listaAsistencias.add(asistencia);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar asistencias por mes: " + e.getMessage());
        }
        
        return listaAsistencias;
    }
    
    // Actualizar minutos de tardanza
    public boolean actualizarTardanza(int idAsistencia, int minutosTardanza) {
        String sql = "UPDATE asistencia SET minutos_tardanza = ? WHERE id_asistencia = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, minutosTardanza);
            ps.setInt(2, idAsistencia);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar tardanza: " + e.getMessage());
            return false;
        }
    }
}
