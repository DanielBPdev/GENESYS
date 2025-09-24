package com.asopagos.afiliaciones.empleadores.composite.dto;

import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.EscalamientoSolicitudDTO;
import com.asopagos.dto.afiliaciones.EmpleadorIntentoAfiliacionDTO;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import com.asopagos.enumeraciones.afiliaciones.CausaIntentoFallidoAfiliacionEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoRadicacionSolicitudEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * <b>Descripción:</b> DTO para el servicio de terminar tarea de la HU
 * <b>Historia de Usuario:</b> HU-066
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@XmlRootElement
public class RadicarSolicitudAfiliacionDTO implements Serializable {

    @NotNull
    private Long idTarea;
    
    @NotNull
    private ResultadoRadicacionSolicitudEnum resultadoRadicacion;
    
    @NotNull
    @Valid
    private SolicitudAfiliacionEmpleador solicitudAfiliacion;
        
    @Valid
    private EscalamientoSolicitudDTO solicitudEscalamiento;
        
    private CausaIntentoFallidoAfiliacionEnum causaIntentoFallido;
    
    @NotNull
    private TipoTransaccionEnum tipoTransaccion;    
    
    private Long idInstanciaProceso;
    
    /**
     * Atributo que representa la información básica del empleador al registrar un intento de afiliación
     */
    private EmpleadorIntentoAfiliacionDTO empleador;
    
    
    /**
     * Atributo que representa el resultado de la validación de la solicitud de afiliación de un empleador
     */
    private ResultadoValidacionEnum resultadoValidacion;

    /**
     * @return the idTarea
     */
    public Long getIdTarea() {
        return idTarea;
    }

    /**
     * @param idTarea the idTarea to set
     */
    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }
    
    /**
     * @return the solicitudAfiliacion
     */
    public SolicitudAfiliacionEmpleador getSolicitudAfiliacion() {
        return solicitudAfiliacion;
    }

    /**
     * @param solicitudAfiliacion the solicitudAfiliacion to set
     */
    public void setSolicitudAfiliacion(SolicitudAfiliacionEmpleador solicitudAfiliacion) {
        this.solicitudAfiliacion = solicitudAfiliacion;
    }

    /**
     * @return the solicitudEscalamiento
     */
    public EscalamientoSolicitudDTO getSolicitudEscalamiento() {
        return solicitudEscalamiento;
    }

    /**
     * @param solicitudEscalamiento the solicitudEscalamiento to set
     */
    public void setSolicitudEscalamiento(EscalamientoSolicitudDTO solicitudEscalamiento) {
        this.solicitudEscalamiento = solicitudEscalamiento;
    }

    /**
     * @return the resultadoRadicacion
     */
    public ResultadoRadicacionSolicitudEnum getResultadoRadicacion() {
        return resultadoRadicacion;
    }

    /**
     * @param resultadoRadicacion the resultadoRadicacion to set
     */
    public void setResultadoRadicacion(ResultadoRadicacionSolicitudEnum resultadoRadicacion) {
        this.resultadoRadicacion = resultadoRadicacion;
    }

    /**
     * @return the causaIntentoFallido
     */
    public CausaIntentoFallidoAfiliacionEnum getCausaIntentoFallido() {
        return causaIntentoFallido;
    }

    /**
     * @param causaIntentoFallido the causaIntentoFallido to set
     */
    public void setCausaIntentoFallido(CausaIntentoFallidoAfiliacionEnum causaIntentoFallido) {
        this.causaIntentoFallido = causaIntentoFallido;
    }

    /**
     * @return the tipoTransaccion
     */
    public TipoTransaccionEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * @param tipoTransaccion the tipoTransaccion to set
     */
    public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

	/**
	 * @return the idInstanciaProceso
	 */
	public Long getIdInstanciaProceso() {
		return idInstanciaProceso;
	}

	/**
	 * @param idInstanciaProceso the idInstanciaProceso to set
	 */
	public void setIdInstanciaProceso(Long idInstanciaProceso) {
		this.idInstanciaProceso = idInstanciaProceso;
	}

    /**
     * @return the resultadoValidacion
     */
    public ResultadoValidacionEnum getResultadoValidacion() {
        return resultadoValidacion;
    }

    /**
     * @param resultadoValidacion the resultadoValidacion to set
     */
    public void setResultadoValidacion(ResultadoValidacionEnum resultadoValidacion) {
        this.resultadoValidacion = resultadoValidacion;
    }

    /**
     * @return the empleador
     */
    public EmpleadorIntentoAfiliacionDTO getEmpleador() {
        return empleador;
    }

    /**
     * @param empleador the empleador to set
     */
    public void setEmpleador(EmpleadorIntentoAfiliacionDTO empleador) {
        this.empleador = empleador;
    }
    
    
}
