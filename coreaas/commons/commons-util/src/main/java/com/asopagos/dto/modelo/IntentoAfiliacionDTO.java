package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import com.asopagos.entidades.ccf.afiliaciones.IntentoAfiliacion;
import com.asopagos.entidades.ccf.afiliaciones.IntentoAfiliacionRequisito;
import com.asopagos.enumeraciones.afiliaciones.CausaIntentoFallidoAfiliacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

public class IntentoAfiliacionDTO implements Serializable{

    /**
     * Referencia a la solicitud asociada al intento de afiliación
     */
    private Long idSolicitud;
    
    /**
     * Descripción de la causa por la que un intento de afiliación no resulta exitoso
     */
    private CausaIntentoFallidoAfiliacionEnum causaIntentoFallido;
    
    /**
     * Descripción del tipo de transacción del proceso
     */
    private TipoTransaccionEnum tipoTransaccion;
    
    /**
     * Descripción de la sede de caja de compensación
     */
    private String sedeCajaCompensacion;
    
    /**
     * Fecha de inicio del proceso
     */
    private Date fechaInicioProceso;
    
    /**
     * Descripción del requisito de intento de afiliación
     */
    private List<IntentoAfiliacionRequisito> requsitos;
    
    /**
     * Fecha creación
     */
    private Date fechaCreacion;
    
    /**
     * Usuario de creación 
     */
    private String usuarioCreacion;

    public IntentoAfiliacion convertToEntity() throws Exception{
        IntentoAfiliacion intentoAfiliacion = new IntentoAfiliacion();
        BeanUtils.copyProperties(intentoAfiliacion, this);
        return intentoAfiliacion;
    }
    
    public IntentoAfiliacionDTO convertToDTO(IntentoAfiliacion intentoAfiliacion) throws Exception{
        IntentoAfiliacionDTO intentoAfiliacionDTO = new IntentoAfiliacionDTO();
        BeanUtils.copyProperties(intentoAfiliacionDTO, intentoAfiliacion);
        return intentoAfiliacionDTO;
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public CausaIntentoFallidoAfiliacionEnum getCausaIntentoFallido() {
        return causaIntentoFallido;
    }

    public void setCausaIntentoFallido(CausaIntentoFallidoAfiliacionEnum causaIntentoFallido) {
        this.causaIntentoFallido = causaIntentoFallido;
    }

    public TipoTransaccionEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public String getSedeCajaCompensacion() {
        return sedeCajaCompensacion;
    }

    public void setSedeCajaCompensacion(String sedeCajaCompensacion) {
        this.sedeCajaCompensacion = sedeCajaCompensacion;
    }

    public Date getFechaInicioProceso() {
        return fechaInicioProceso;
    }

    public void setFechaInicioProceso(Date fechaInicioProceso) {
        this.fechaInicioProceso = fechaInicioProceso;
    }

    public List<IntentoAfiliacionRequisito> getRequsitos() {
        return requsitos;
    }

    public void setRequsitos(List<IntentoAfiliacionRequisito> requsitos) {
        this.requsitos = requsitos;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }
}
