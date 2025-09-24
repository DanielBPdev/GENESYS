package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoEjecucionProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum;

/**
 * <b>Descripcion:</b> DTO para inicializacion de pantalla <br/>
 * <b>Módulo:</b> Asopagos - HU-311-434 <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> rarboleda</a>
 */

public class IniciarSolicitudLiquidacionMasivaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Indica si hay una liquidacion en proceso
     */
    private Boolean liquidacionEnProceso;

    /**
     * Indica en que porcentaje se encuentra la liquidacion en progreso
     */
    private Integer porcentajeActual;

    /**
     * Determina el estado actual del proceso de liquidacion
     */
    private EstadoProcesoLiquidacionEnum estadoProcesoLiquidacion;

    /**
     * Indica si es inmediata o programada
     */
    private TipoEjecucionProcesoLiquidacionEnum tipoEjecucionProcesoLiquidacion;

    /**
     * Ultima fecha en la que se ejecuto el proceso de liquidacion
     */
    private Date fechaUltimoCorteAportes;

    /**
     * Indica si la finalizacion del proceso fue correcta
     */
    private Boolean finalizacionExitosa;

    /**
     * Indica si el proceso fue cancelado por algun operador de la caja
     */
    private Boolean procesoCanceladoPorOperador;

    /**
     * Indica si la liquidacion fue rechazada por el supervisor
     */
    private Boolean rechazadoPorSupervisor;

    /**
     * fecha y hora de corte de aportes (Fecha del día anterior a la fecha en la que se va a ejecutar esta liquidación masiva)
     */
    private Date fechaHoraCorteAportes;

    /**
     * Id de la solicitud de liquidacion
     */
    private Long idSolicitudLiquidacionActual;

    /**
     * Id de la solicitud global
     */
    private Long idSolicitudGlobalActual;

    /**
     * Id instancia proceso, id del proceso en BPM
     */
    private String idInstanciaProceso;

    /**
     * Número de radicado del proceso
     */
    private String numeroRadicado;

    /**
     * Tipo del proceso de liquidacion
     */
    private TipoProcesoLiquidacionEnum tipoProcesoLiquidacion;

    /**
     * Tipo de proceso de liquidacion especifica
     */
    private TipoLiquidacionEspecificaEnum tipoLiquidacionEspecifica;

    /**
     * Variable boolean que determina si el usuario actual es el mismo que hizo el radicado de la solicitud
     */
    private Boolean esUsuarioQueRadico;
    
    /**
     * Variable que contiene el usuario que hizo la radicacion de la solicitud     * 
     */
    private String usuarioRadicador;
    
    /**
     * Variable que contiene el usuario actual
     */
    private String usuarioActual;
    
    /**
     * Variable que contiene el periodo de liquidación asociado 
     */
    private Date periodoLiquidacionMasiva;
    
    /**
     * variable que contiene la fecha de la hora programada de ejecución 
     */
    private Date fechaHoraEjecucionProgramada;
    
    /**
     * Indica las Observaciones del proceso de liquidación de subsidio
     */
    private String observacionesProceso;
    
    /**
     * Indica el id de la tarea
     */
    private Long idTarea;

    /**
     * @return the liquidacionEnProceso
     */
    public Boolean getLiquidacionEnProceso() {
        return liquidacionEnProceso;
    }

    /**
     * @param liquidacionEnProceso
     *        the liquidacionEnProceso to set
     */
    public void setLiquidacionEnProceso(Boolean liquidacionEnProceso) {
        this.liquidacionEnProceso = liquidacionEnProceso;
    }

    /**
     * @return the porcentajeActual
     */
    public Integer getPorcentajeActual() {
        return porcentajeActual;
    }

    /**
     * @param porcentajeActual
     *        the porcentajeActual to set
     */
    public void setPorcentajeActual(Integer porcentajeActual) {
        this.porcentajeActual = porcentajeActual;
    }

    /**
     * @return the estadoProcesoLiquidacion
     */
    public EstadoProcesoLiquidacionEnum getEstadoProcesoLiquidacion() {
        return estadoProcesoLiquidacion;
    }

    /**
     * @param estadoProcesoLiquidacion
     *        the estadoProcesoLiquidacion to set
     */
    public void setEstadoProcesoLiquidacion(EstadoProcesoLiquidacionEnum estadoProcesoLiquidacion) {
        this.estadoProcesoLiquidacion = estadoProcesoLiquidacion;
    }

    /**
     * @return the tipoEjecucionProcesoLiquidacion
     */
    public TipoEjecucionProcesoLiquidacionEnum getTipoEjecucionProcesoLiquidacion() {
        return tipoEjecucionProcesoLiquidacion;
    }

    /**
     * @param tipoEjecucionProcesoLiquidacion
     *        the tipoEjecucionProcesoLiquidacion to set
     */
    public void setTipoEjecucionProcesoLiquidacion(TipoEjecucionProcesoLiquidacionEnum tipoEjecucionProcesoLiquidacion) {
        this.tipoEjecucionProcesoLiquidacion = tipoEjecucionProcesoLiquidacion;
    }

    /**
     * @return the fechaUltimoCorteAportes
     */
    public Date getFechaUltimoCorteAportes() {
        return fechaUltimoCorteAportes;
    }

    /**
     * @param fechaUltimoCorteAportes
     *        the fechaUltimoCorteAportes to set
     */
    public void setFechaUltimoCorteAportes(Date fechaUltimoCorteAportes) {
        this.fechaUltimoCorteAportes = fechaUltimoCorteAportes;
    }

    /**
     * @return the finalizacionExitosa
     */
    public Boolean getFinalizacionExitosa() {
        return finalizacionExitosa;
    }

    /**
     * @param finalizacionExitosa
     *        the finalizacionExitosa to set
     */
    public void setFinalizacionExitosa(Boolean finalizacionExitosa) {
        this.finalizacionExitosa = finalizacionExitosa;
    }

    /**
     * @return the procesoCanceladoPorOperador
     */
    public Boolean getProcesoCanceladoPorOperador() {
        return procesoCanceladoPorOperador;
    }

    /**
     * @param procesoCanceladoPorOperador
     *        the procesoCanceladoPorOperador to set
     */
    public void setProcesoCanceladoPorOperador(Boolean procesoCanceladoPorOperador) {
        this.procesoCanceladoPorOperador = procesoCanceladoPorOperador;
    }

    /**
     * @return the rechazadoPorSupervisor
     */
    public Boolean getRechazadoPorSupervisor() {
        return rechazadoPorSupervisor;
    }

    /**
     * @param rechazadoPorSupervisor
     *        the rechazadoPorSupervisor to set
     */
    public void setRechazadoPorSupervisor(Boolean rechazadoPorSupervisor) {
        this.rechazadoPorSupervisor = rechazadoPorSupervisor;
    }

    /**
     * @return the fechaHoraCorteAportes
     */
    public Date getFechaHoraCorteAportes() {
        return fechaHoraCorteAportes;
    }

    /**
     * @param fechaHoraCorteAportes
     *        the fechaHoraCorteAportes to set
     */
    public void setFechaHoraCorteAportes(Date fechaHoraCorteAportes) {
        this.fechaHoraCorteAportes = fechaHoraCorteAportes;
    }

    /**
     * @return the idSolicitudLiquidacionActual
     */
    public Long getIdSolicitudLiquidacionActual() {
        return idSolicitudLiquidacionActual;
    }

    /**
     * @param idSolicitudLiquidacionActual
     *        the idSolicitudLiquidacionActual to set
     */
    public void setIdSolicitudLiquidacionActual(Long idSolicitudLiquidacionActual) {
        this.idSolicitudLiquidacionActual = idSolicitudLiquidacionActual;
    }

    /**
     * @return the idSolicitudGlobalActual
     */
    public Long getIdSolicitudGlobalActual() {
        return idSolicitudGlobalActual;
    }

    /**
     * @param idSolicitudGlobalActual
     *        the idSolicitudGlobalActual to set
     */
    public void setIdSolicitudGlobalActual(Long idSolicitudGlobalActual) {
        this.idSolicitudGlobalActual = idSolicitudGlobalActual;
    }

    /**
     * @return the idInstanciaProceso
     */
    public String getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    /**
     * @param idInstanciaProceso
     *        the idInstanciaProceso to set
     */
    public void setIdInstanciaProceso(String idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }

    /**
     * @return the numeroRadicado
     */
    public String getNumeroRadicado() {
        return numeroRadicado;
    }

    /**
     * @param numeroRadicado the numeroRadicado to set
     */
    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }

    /**
     * @return the tipoProcesoLiquidacion
     */
    public TipoProcesoLiquidacionEnum getTipoProcesoLiquidacion() {
        return tipoProcesoLiquidacion;
    }

    /**
     * @param tipoProcesoLiquidacion the tipoProcesoLiquidacion to set
     */
    public void setTipoProcesoLiquidacion(TipoProcesoLiquidacionEnum tipoProcesoLiquidacion) {
        this.tipoProcesoLiquidacion = tipoProcesoLiquidacion;
    }

    /**
     * @return the tipoLiquidacionEspecifica
     */
    public TipoLiquidacionEspecificaEnum getTipoLiquidacionEspecifica() {
        return tipoLiquidacionEspecifica;
    }

    /**
     * @param tipoLiquidacionEspecifica the tipoLiquidacionEspecifica to set
     */
    public void setTipoLiquidacionEspecifica(TipoLiquidacionEspecificaEnum tipoLiquidacionEspecifica) {
        this.tipoLiquidacionEspecifica = tipoLiquidacionEspecifica;
    }

    /**
     * @return the esUsuarioQueRadico
     */
    public Boolean getEsUsuarioQueRadico() {
        return esUsuarioQueRadico;
    }

    /**
     * @param esUsuarioQueRadico the esUsuarioQueRadico to set
     */
    public void setEsUsuarioQueRadico(Boolean esUsuarioQueRadico) {
        this.esUsuarioQueRadico = esUsuarioQueRadico;
    }

    /**
     * @return the usuarioRadicador
     */
    public String getUsuarioRadicador() {
        return usuarioRadicador;
    }

    /**
     * @param usuarioRadicador the usuarioRadicador to set
     */
    public void setUsuarioRadicador(String usuarioRadicador) {
        this.usuarioRadicador = usuarioRadicador;
    }

    /**
     * @return the usuarioActual
     */
    public String getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * @param usuarioActual the usuarioActual to set
     */
    public void setUsuarioActual(String usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    /**
     * @return the periodoLiquidacionMasiva
     */
    public Date getPeriodoLiquidacionMasiva() {
        return periodoLiquidacionMasiva;
    }

    /**
     * @param periodoLiquidacionMasiva the periodoLiquidacionMasiva to set
     */
    public void setPeriodoLiquidacionMasiva(Date periodoLiquidacionMasiva) {
        this.periodoLiquidacionMasiva = periodoLiquidacionMasiva;
    }

	/**
	 * @return the fechaHoraEjecucionProgramada
	 */
	public Date getFechaHoraEjecucionProgramada() {
		return fechaHoraEjecucionProgramada;
	}

	/**
	 * @param fechaHoraEjecucionProgramada the fechaHoraEjecucionProgramada to set
	 */
	public void setFechaHoraEjecucionProgramada(Date fechaHoraEjecucionProgramada) {
		this.fechaHoraEjecucionProgramada = fechaHoraEjecucionProgramada;
	}

	/**
	 * @return the observacionesProceso
	 */
	public String getObservacionesProceso() {
		return observacionesProceso;
	}

	/**
	 * @param observacionesProceso the observacionesProceso to set
	 */
	public void setObservacionesProceso(String observacionesProceso) {
		this.observacionesProceso = observacionesProceso;
	}

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

}
