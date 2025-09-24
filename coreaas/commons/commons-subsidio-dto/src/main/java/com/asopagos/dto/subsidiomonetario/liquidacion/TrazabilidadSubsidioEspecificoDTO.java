package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.EstadoDerechoSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.RazonRechazoEnum;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información de la trazabilidad asociada a un subsidio específico <br/>
 * <b>Módulo:</b> Asopagos - HU 317-141<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co">Roy López Cardona.</a>
 */
public class TrazabilidadSubsidioEspecificoDTO implements Serializable {

    private static final long serialVersionUID = 7391373884748515555L;

    /**
     * Fecha y hora en la que cambia en estado de la liquidación
     */
    private Date fecha;

    /**
     * Estado de la liquidación al finalizar la actividad realizada
     */
    private EstadoProcesoLiquidacionEnum estadoSolicitud;

    /**
     * Resultado de la solicitud global asociada
     */
    private ResultadoProcesoEnum estadoProceso;

    /**
     * Nombre del usuario que realizo la actividad sobre la liquidación
     */
    private String usuario;

    /**
     * Descripción de la actividad realizada sobre la liquidación específica
     */
    private String actividadRealizada;

    /**
     * HU 317-525 Estado del derecho al finalizar la actividad realizada
     */
    private EstadoDerechoSubsidioEnum estadoDerecho;

    /**
     * HU 317-525 Valor asignado por el supervisor de subsidios cuando se realiza la probación o rechazo de la liquidación
     */
    private String observaciones;

    /**
     * Constructor nulo
     */
    public TrazabilidadSubsidioEspecificoDTO() {

    }

    /**
     * Método que permite realizar la conversión de los registros obtenidos en base de datos
     * 
     * @param registroTrazabilidad
     *        arreglo con los valores de la trazabilidad
     * 
     * @return DTO con la información de la trazabilidad
     */
    public TrazabilidadSubsidioEspecificoDTO convertirTrazabilidad(Object[] registroTrazabilidad) {
        TrazabilidadSubsidioEspecificoDTO trazabilidadDTO = new TrazabilidadSubsidioEspecificoDTO();

        trazabilidadDTO.setEstadoSolicitud(
                (registroTrazabilidad[0] == null) ? null : EstadoProcesoLiquidacionEnum.valueOf(registroTrazabilidad[0].toString()));
        trazabilidadDTO.setFecha((registroTrazabilidad[2] == null) ? null : new Date(Long.parseLong(registroTrazabilidad[2].toString())));
        trazabilidadDTO.setUsuario((registroTrazabilidad[3] == null) ? null : registroTrazabilidad[3].toString());

        StringBuilder sbObservaciones = new StringBuilder();
        if (registroTrazabilidad[4] != null) {        	
            sbObservaciones.append(registroTrazabilidad[4].toString());
            if (registroTrazabilidad[5] != null) {
            	sbObservaciones.append(" ");
                sbObservaciones.append(RazonRechazoEnum.valueOf(registroTrazabilidad[5].toString()));
            }
        }
        trazabilidadDTO.setObservaciones(sbObservaciones.toString().replace('_', ' '));
        
        return trazabilidadDTO;
    }

    /**
     * Método que permite realizar la conversión de los registros obtenidos en base de datos
     * @param registroTrazabilidad
     *        Arreglo con los valores de la trazabilidad
     * @return DTO con la información de trazabilidad
     */
    public TrazabilidadSubsidioEspecificoDTO convertirTrazabilidadFallecimiento(Object[] registroTrazabilidad) {
        TrazabilidadSubsidioEspecificoDTO trazabilidadDTO = new TrazabilidadSubsidioEspecificoDTO();

        trazabilidadDTO.setEstadoSolicitud(
                (registroTrazabilidad[0] == null) ? null : EstadoProcesoLiquidacionEnum.valueOf(registroTrazabilidad[0].toString()));
        trazabilidadDTO.setFecha((registroTrazabilidad[2] == null) ? null : new Date(Long.parseLong(registroTrazabilidad[2].toString())));
        trazabilidadDTO.setUsuario((registroTrazabilidad[3] == null) ? null : registroTrazabilidad[3].toString());

        StringBuilder sbObservaciones = new StringBuilder();
        if (registroTrazabilidad[4] != null) {
            sbObservaciones.append(registroTrazabilidad[4].toString());
            if (registroTrazabilidad[5] != null) {
                sbObservaciones.append(RazonRechazoEnum.valueOf(registroTrazabilidad[5].toString()));
            }
        }
        trazabilidadDTO.setObservaciones(sbObservaciones.toString());

        return trazabilidadDTO;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha
     *        the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the estadoSolicitud
     */
    public EstadoProcesoLiquidacionEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * @param estadoSolicitud
     *        the estadoSolicitud to set
     */
    public void setEstadoSolicitud(EstadoProcesoLiquidacionEnum estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * @return the estadoProceso
     */
    public ResultadoProcesoEnum getEstadoProceso() {
        return estadoProceso;
    }

    /**
     * @param estadoProceso
     *        the estadoProceso to set
     */
    public void setEstadoProceso(ResultadoProcesoEnum estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario
     *        the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the actividadRealizada
     */
    public String getActividadRealizada() {
        return actividadRealizada;
    }

    /**
     * @param actividadRealizada
     *        the actividadRealizada to set
     */
    public void setActividadRealizada(String actividadRealizada) {
        this.actividadRealizada = actividadRealizada;
    }

    /**
     * @return the estadoDerecho
     */
    public EstadoDerechoSubsidioEnum getEstadoDerecho() {
        return estadoDerecho;
    }

    /**
     * @param estadoDerecho
     *        the estadoDerecho to set
     */
    public void setEstadoDerecho(EstadoDerechoSubsidioEnum estadoDerecho) {
        this.estadoDerecho = estadoDerecho;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones
     *        the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}
