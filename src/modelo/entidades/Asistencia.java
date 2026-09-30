// modelo/entidades/Asistencia.java
package modelo.entidades;

import java.sql.Time;
import java.util.Date;

public class Asistencia {
    private int idAsistencia;
    private int idPersonal;
    private Date fecha;
    private Time horaEntrada;
    private Time horaSalida;
    private int minutosExtras;
    private int minutosTardanza;
    private String estado; // PRESENTE, AUSENTE, TARDANZA
    private String observaciones;
    
    // Constructor vacío
    public Asistencia() {}
    
    // Constructor completo
    public Asistencia(int idAsistencia, int idPersonal, Date fecha, Time horaEntrada, 
                     Time horaSalida, int minutosExtras, int minutosTardanza, 
                     String estado, String observaciones) {
        this.idAsistencia = idAsistencia;
        this.idPersonal = idPersonal;
        this.fecha = fecha;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.minutosExtras = minutosExtras;
        this.minutosTardanza = minutosTardanza;
        this.estado = estado;
        this.observaciones = observaciones;
    }
    
    // Getters y Setters
    public int getIdAsistencia() {
        return idAsistencia;
    }
    
    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }
    
    public int getIdPersonal() {
        return idPersonal;
    }
    
    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }
    
    public Date getFecha() {
        return fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public Time getHoraEntrada() {
        return horaEntrada;
    }
    
    public void setHoraEntrada(Time horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
    
    public Time getHoraSalida() {
        return horaSalida;
    }
    
    public void setHoraSalida(Time horaSalida) {
        this.horaSalida = horaSalida;
    }
    
    public int getMinutosExtras() {
        return minutosExtras;
    }
    
    public void setMinutosExtras(int minutosExtras) {
        this.minutosExtras = minutosExtras;
    }
    
    public int getMinutosTardanza() {
        return minutosTardanza;
    }
    
    public void setMinutosTardanza(int minutosTardanza) {
        this.minutosTardanza = minutosTardanza;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}