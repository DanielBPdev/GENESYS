package com.asopagos.correspondencia.dto;

import java.util.Date;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

public class RemisionDocumento {
    
    private String nombreSede;
    private String destinatario;
    private TipoTransaccionEnum tipoTransaccion;
    private String numeroRadicacion;
    private Date fechaRadicacion;
    private String descripcionDocumento;
    private String usuarioRadicacion;
    private String idInstanciaProceso;
    
    
    public RemisionDocumento(String nombreSede, String destinatario, TipoTransaccionEnum tipoTransaccion, String numeroRadicacion, Date fechaRadicacion,
            String descripcionDocumento, String usuarioRadicacion, String idInstanciaProceso) {
        super();
        this.nombreSede = nombreSede;
        this.destinatario = destinatario;
        this.tipoTransaccion = tipoTransaccion;
        this.numeroRadicacion = numeroRadicacion;
        this.fechaRadicacion = fechaRadicacion;
        this.descripcionDocumento = descripcionDocumento;
        this.usuarioRadicacion = usuarioRadicacion;
        this.idInstanciaProceso = idInstanciaProceso;
    }
    
    public String getNombreSede() {
        return nombreSede;
    }
    public String getDestinatario() {
        return destinatario;
    }
    public TipoTransaccionEnum getTipoTransaccion() {
        return tipoTransaccion;
    }
    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }
    public Date getFechaRadicacion() {
        return fechaRadicacion;
    }
    public String getDescripcionDocumento() {
        return descripcionDocumento;
    }
    public String getUsuarioRadicacion() {
        return usuarioRadicacion;
    }
    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }
    public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }
    public void setFechaRadicacion(Date fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }
    public void setDescripcionDocumento(String descripcionDocumento) {
        this.descripcionDocumento = descripcionDocumento;
    }
    public void setUsuarioRadicacion(String usuarioRadicacion) {
        this.usuarioRadicacion = usuarioRadicacion;
    }

    public String getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    public void setIdInstanciaProceso(String idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }

    @Override
    public String toString() {
        return "RemisionDocumento [nombreSede=" + nombreSede + ", destinatario=" + destinatario + ", tipoTransaccion=" + tipoTransaccion
                + ", numeroRadicacion=" + numeroRadicacion + ", fechaRadicacion=" + fechaRadicacion + ", descripcionDocumento="
                + descripcionDocumento + ", usuarioRadicacion=" + usuarioRadicacion + "]";
    }
}
