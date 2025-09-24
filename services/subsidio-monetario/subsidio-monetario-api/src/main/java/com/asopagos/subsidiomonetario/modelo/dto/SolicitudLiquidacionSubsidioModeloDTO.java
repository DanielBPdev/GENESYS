package com.asopagos.subsidiomonetario.modelo.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.subsidiomonetario.liquidacion.SolicitudLiquidacionSubsidio;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoEjecucionProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.RazonRechazoEnum;

/**
 * <b>Descripcion:</b> Clase DTO que representa una solicitud de liquidación de subsidio <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda Valencia</a>
 */

public class SolicitudLiquidacionSubsidioModeloDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Código identificador de llave primaria llamado No. de operación de liquidación
     */
    private Long idProcesoLiquidacionSubsidio;

    /**
     * Referencia a la solicitud asociada al proceso de liquidación asociando el número de radicado de la solicitud
     */
    private Solicitud solicitudGlobal;

    /**
     * Fecha y hora final del corte de aportes del proceso de liquidación de subsidio
     */
    private Long fechaCorteAportes;

    /**
     * Fecha y hora inicial de ejecución del proceso de liquidación de subsidio
     */
    private Long fechaInicial;

    /**
     * Fecha y hora final de ejecución del proceso de liquidación de subsidio
     */
    private Long fechaFinal;

    /**
     * Descripción del tipo del proceso de liquidación de subsidio
     */
    private TipoProcesoLiquidacionEnum tipoLiquidacion;

    /**
     * Descripción del tipo del proceso de liquidación de subsidio especifica
     */
    private TipoLiquidacionEspecificaEnum tipoLiquidacionEspecifica;

    /**
     * Descripción del estado de liquidación de subsidio
     */
    private EstadoProcesoLiquidacionEnum estadoLiquidacion;

    /**
     * Indica el tipo de ejecucion del proceso de liquidación.
     */
    private TipoEjecucionProcesoLiquidacionEnum tipoEjecucionProceso;

    /**
     * Fecha y hora de ejecucion programada
     */
    private Long fechaHoraEjecucionProgramada;

    /**
     * Indica el usuario evaluador en primer nivel del resultado del proceso de liquidación de subsidio
     */
    private String usuarioEvaluacionPrimerNivel;

    /**
     * Indica las Observaciones del usuario en primer nivel del resultado del proceso de liquidación de subsidio
     */
    private String observacionesPrimerNivel;

    /**
     * Indica el usuario evaluador en segundo nivel del resultado del proceso de liquidación de subsidio
     */
    private String usuarioEvaluacionSegundoNivel;

    /**
     * Indica las Observaciones del usuario en segundo nivel del resultado del proceso de liquidación de subsidio
     */
    private String observacionesSegundoNivel;

    /**
     * Indica la razon del rechazo del resultado del proceso de liquidación de subsidio
     */
    private RazonRechazoEnum razonRechazoLiquidacion;

    /**
     * Indica las Observaciones del proceso de liquidación de subsidio
     */
    private String observacionesProceso;

    /**
     * Periodo regular de la solicitud de liquidación de subsidio
     */
    private Date periodoRegular;

    /**
     * Codigo del reclamo asociado a una liquidacion especifica
     */
    private String codigoReclamo;

    /**
     * Comentario sobre el reclamo asociado a una liquidacion especifica
     */
    private String comentarioReclamo;

    /**
     * Fecha en la que se realiza la dispersión de la liquidación
     */
    private Date fechaDispersion;

    /**
     * Indicador de consideración de aportes para el desembolso del subsidio por fallecimiento
     */
    private Boolean consideracionAporteDesembolso;

    /**
     * Modo en que se realiza el desembolso de subsidio monetario por fallecimiento
     */
    private ModoDesembolsoEnum modoDesembolso;
    
    /**
	 * Descripción de la instancia del proceso
	 */
    private String idInstanciaProceso;
    
    /**
	 * Marca que indica que hay una liquidacion o otro rechazo en proceso
	 */
    private Boolean liquidORechazOStagingEnProceso;
    
    /** Fecha de fallecido asociado a la liquidación */
    //private Date fechaFallecido;
    
    /**
     * Metodo encargado de convertir una entidad a DTO
     * @param Entidad
     *        a convertir
     */
    public void convertToDTO(SolicitudLiquidacionSubsidio solicitudLiquidacionSubsidio) {
        this.setIdProcesoLiquidacionSubsidio(solicitudLiquidacionSubsidio.getIdProcesoLiquidacionSubsidio());
        this.setSolicitudGlobal(solicitudLiquidacionSubsidio.getSolicitudGlobal());
        if (solicitudLiquidacionSubsidio.getFechaCorteAportes() != null) {
            this.setFechaCorteAportes(solicitudLiquidacionSubsidio.getFechaCorteAportes().getTime());
        }
        if (solicitudLiquidacionSubsidio.getFechaInicial() != null) {
            this.setFechaInicial(solicitudLiquidacionSubsidio.getFechaInicial().getTime());
        }
        if (solicitudLiquidacionSubsidio.getFechaFinal() != null) {
            this.setFechaFinal(solicitudLiquidacionSubsidio.getFechaFinal().getTime());
        }
        this.setTipoLiquidacion(solicitudLiquidacionSubsidio.getTipoLiquidacion());
        this.setTipoLiquidacionEspecifica(solicitudLiquidacionSubsidio.getTipoLiquidacionEspecifica());
        this.setEstadoLiquidacion(solicitudLiquidacionSubsidio.getEstadoLiquidacion());
        this.setTipoEjecucionProceso(solicitudLiquidacionSubsidio.getTipoEjecucionProceso());
        if (solicitudLiquidacionSubsidio.getFechaHoraEjecucionProgramada() != null) {
            this.setFechaHoraEjecucionProgramada(solicitudLiquidacionSubsidio.getFechaHoraEjecucionProgramada().getTime());
        }
        this.setUsuarioEvaluacionPrimerNivel(solicitudLiquidacionSubsidio.getUsuarioEvaluacionPrimerNivel());
        this.setObservacionesPrimerNivel(solicitudLiquidacionSubsidio.getObservacionesPrimerNivel());
        this.setUsuarioEvaluacionSegundoNivel(solicitudLiquidacionSubsidio.getUsuarioEvaluacionSegundoNivel());
        this.setObservacionesSegundoNivel(solicitudLiquidacionSubsidio.getObservacionesSegundoNivel());
        this.setRazonRechazoLiquidacion(solicitudLiquidacionSubsidio.getRazonRechazoLiquidacion());
        this.setObservacionesProceso(solicitudLiquidacionSubsidio.getObservacionesProceso());
        this.setCodigoReclamo(solicitudLiquidacionSubsidio.getCodigoReclamo());
        this.setComentarioReclamo(solicitudLiquidacionSubsidio.getComentarioReclamo());
        this.setFechaDispersion(solicitudLiquidacionSubsidio.getFechaDispersion());
        this.setConsideracionAporteDesembolso(solicitudLiquidacionSubsidio.getConsideracionAporteDesembolso());
        this.setModoDesembolso(solicitudLiquidacionSubsidio.getModoDesembolso());
    }

    /**
     * Metodo encargado de convertir de DTO a entidad
     * @return Entidad convertida
     */
    public SolicitudLiquidacionSubsidio convertToEntity() {
        SolicitudLiquidacionSubsidio solicitudLiquidacionSubsidio = new SolicitudLiquidacionSubsidio();
        solicitudLiquidacionSubsidio.setIdProcesoLiquidacionSubsidio(this.getIdProcesoLiquidacionSubsidio());
        solicitudLiquidacionSubsidio.setSolicitudGlobal(this.getSolicitudGlobal());
        if (this.getFechaCorteAportes() != null) {
            solicitudLiquidacionSubsidio.setFechaCorteAportes(new Date(this.getFechaCorteAportes()));
        }
        if (this.getFechaInicial() != null) {
            solicitudLiquidacionSubsidio.setFechaInicial(new Date(this.getFechaInicial()));
        }
        if (this.getFechaFinal() != null) {
            solicitudLiquidacionSubsidio.setFechaFinal(new Date(this.getFechaFinal()));
        }
        solicitudLiquidacionSubsidio.setTipoLiquidacion(this.getTipoLiquidacion());
        solicitudLiquidacionSubsidio.setTipoLiquidacionEspecifica(this.getTipoLiquidacionEspecifica());
        solicitudLiquidacionSubsidio.setEstadoLiquidacion(this.getEstadoLiquidacion());
        solicitudLiquidacionSubsidio.setTipoEjecucionProceso(this.getTipoEjecucionProceso());
        if (this.getFechaHoraEjecucionProgramada() != null) {
            solicitudLiquidacionSubsidio.setFechaHoraEjecucionProgramada(new Date(this.getFechaHoraEjecucionProgramada()));
        }
        solicitudLiquidacionSubsidio.setUsuarioEvaluacionPrimerNivel(this.getUsuarioEvaluacionPrimerNivel());
        solicitudLiquidacionSubsidio.setObservacionesPrimerNivel(this.getObservacionesPrimerNivel());
        solicitudLiquidacionSubsidio.setUsuarioEvaluacionSegundoNivel(this.getUsuarioEvaluacionSegundoNivel());
        solicitudLiquidacionSubsidio.setObservacionesSegundoNivel(this.getObservacionesSegundoNivel());
        solicitudLiquidacionSubsidio.setRazonRechazoLiquidacion(this.getRazonRechazoLiquidacion());
        solicitudLiquidacionSubsidio.setObservacionesProceso(this.getObservacionesProceso());
        solicitudLiquidacionSubsidio.setCodigoReclamo(this.getCodigoReclamo());
        solicitudLiquidacionSubsidio.setComentarioReclamo(this.getComentarioReclamo());
        solicitudLiquidacionSubsidio.setFechaDispersion(this.getFechaDispersion());
        solicitudLiquidacionSubsidio.setConsideracionAporteDesembolso(this.getConsideracionAporteDesembolso());
        solicitudLiquidacionSubsidio.setModoDesembolso(this.getModoDesembolso());

        return solicitudLiquidacionSubsidio;
    }

    /**
     * @return the idProcesoLiquidacionSubsidio
     */
    public Long getIdProcesoLiquidacionSubsidio() {
        return idProcesoLiquidacionSubsidio;
    }

    /**
     * @param idProcesoLiquidacionSubsidio
     *        the idProcesoLiquidacionSubsidio to set
     */
    public void setIdProcesoLiquidacionSubsidio(Long idProcesoLiquidacionSubsidio) {
        this.idProcesoLiquidacionSubsidio = idProcesoLiquidacionSubsidio;
    }

    /**
     * @return the solicitudGlobal
     */
    public Solicitud getSolicitudGlobal() {
        return solicitudGlobal;
    }

    /**
     * @param solicitudGlobal
     *        the solicitudGlobal to set
     */
    public void setSolicitudGlobal(Solicitud solicitudGlobal) {
        this.solicitudGlobal = solicitudGlobal;
    }

    /**
     * @return the fechaCorteAportes
     */
    public Long getFechaCorteAportes() {
        return fechaCorteAportes;
    }

    /**
     * @param fechaCorteAportes
     *        the fechaCorteAportes to set
     */
    public void setFechaCorteAportes(Long fechaCorteAportes) {
        this.fechaCorteAportes = fechaCorteAportes;
    }

    /**
     * @return the fechaInicial
     */
    public Long getFechaInicial() {
        return fechaInicial;
    }

    /**
     * @param fechaInicial
     *        the fechaInicial to set
     */
    public void setFechaInicial(Long fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    /**
     * @return the fechaFinal
     */
    public Long getFechaFinal() {
        return fechaFinal;
    }

    /**
     * @param fechaFinal
     *        the fechaFinal to set
     */
    public void setFechaFinal(Long fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    /**
     * @return the tipoLiquidacion
     */
    public TipoProcesoLiquidacionEnum getTipoLiquidacion() {
        return tipoLiquidacion;
    }

    /**
     * @param tipoLiquidacion
     *        the tipoLiquidacion to set
     */
    public void setTipoLiquidacion(TipoProcesoLiquidacionEnum tipoLiquidacion) {
        this.tipoLiquidacion = tipoLiquidacion;
    }

    /**
     * @return the tipoLiquidacionEspecifica
     */
    public TipoLiquidacionEspecificaEnum getTipoLiquidacionEspecifica() {
        return tipoLiquidacionEspecifica;
    }

    /**
     * @param tipoLiquidacionEspecifica
     *        the tipoLiquidacionEspecifica to set
     */
    public void setTipoLiquidacionEspecifica(TipoLiquidacionEspecificaEnum tipoLiquidacionEspecifica) {
        this.tipoLiquidacionEspecifica = tipoLiquidacionEspecifica;
    }

    /**
     * @return the estadoLiquidacion
     */
    public EstadoProcesoLiquidacionEnum getEstadoLiquidacion() {
        return estadoLiquidacion;
    }

    /**
     * @param estadoLiquidacion
     *        the estadoLiquidacion to set
     */
    public void setEstadoLiquidacion(EstadoProcesoLiquidacionEnum estadoLiquidacion) {
        this.estadoLiquidacion = estadoLiquidacion;
    }

    /**
     * @return the tipoEjecucionProceso
     */
    public TipoEjecucionProcesoLiquidacionEnum getTipoEjecucionProceso() {
        return tipoEjecucionProceso;
    }

    /**
     * @param tipoEjecucionProceso
     *        the tipoEjecucionProceso to set
     */
    public void setTipoEjecucionProceso(TipoEjecucionProcesoLiquidacionEnum tipoEjecucionProceso) {
        this.tipoEjecucionProceso = tipoEjecucionProceso;
    }

    /**
     * @return the fechaHoraEjecucionProgramada
     */
    public Long getFechaHoraEjecucionProgramada() {
        return fechaHoraEjecucionProgramada;
    }

    /**
     * @param fechaHoraEjecucionProgramada
     *        the fechaHoraEjecucionProgramada to set
     */
    public void setFechaHoraEjecucionProgramada(Long fechaHoraEjecucionProgramada) {
        this.fechaHoraEjecucionProgramada = fechaHoraEjecucionProgramada;
    }

    /**
     * @return the usuarioEvaluacionPrimerNivel
     */
    public String getUsuarioEvaluacionPrimerNivel() {
        return usuarioEvaluacionPrimerNivel;
    }

    /**
     * @param usuarioEvaluacionPrimerNivel
     *        the usuarioEvaluacionPrimerNivel to set
     */
    public void setUsuarioEvaluacionPrimerNivel(String usuarioEvaluacionPrimerNivel) {
        this.usuarioEvaluacionPrimerNivel = usuarioEvaluacionPrimerNivel;
    }

    /**
     * @return the observacionesPrimerNivel
     */
    public String getObservacionesPrimerNivel() {
        return observacionesPrimerNivel;
    }

    /**
     * @param observacionesPrimerNivel
     *        the observacionesPrimerNivel to set
     */
    public void setObservacionesPrimerNivel(String observacionesPrimerNivel) {
        this.observacionesPrimerNivel = observacionesPrimerNivel;
    }

    /**
     * @return the usuarioEvaluacionSegundoNivel
     */
    public String getUsuarioEvaluacionSegundoNivel() {
        return usuarioEvaluacionSegundoNivel;
    }

    /**
     * @param usuarioEvaluacionSegundoNivel
     *        the usuarioEvaluacionSegundoNivel to set
     */
    public void setUsuarioEvaluacionSegundoNivel(String usuarioEvaluacionSegundoNivel) {
        this.usuarioEvaluacionSegundoNivel = usuarioEvaluacionSegundoNivel;
    }

    /**
     * @return the observacionesSegundoNivel
     */
    public String getObservacionesSegundoNivel() {
        return observacionesSegundoNivel;
    }

    /**
     * @param observacionesSegundoNivel
     *        the observacionesSegundoNivel to set
     */
    public void setObservacionesSegundoNivel(String observacionesSegundoNivel) {
        this.observacionesSegundoNivel = observacionesSegundoNivel;
    }

    /**
     * @return the razonRechazoLiquidacion
     */
    public RazonRechazoEnum getRazonRechazoLiquidacion() {
        return razonRechazoLiquidacion;
    }

    /**
     * @param razonRechazoLiquidacion
     *        the razonRechazoLiquidacion to set
     */
    public void setRazonRechazoLiquidacion(RazonRechazoEnum razonRechazoLiquidacion) {
        this.razonRechazoLiquidacion = razonRechazoLiquidacion;
    }

    /**
     * @return the observacionesProceso
     */
    public String getObservacionesProceso() {
        return observacionesProceso;
    }

    /**
     * @param observacionesProceso
     *        the observacionesProceso to set
     */
    public void setObservacionesProceso(String observacionesProceso) {
        this.observacionesProceso = observacionesProceso;
    }

    /**
     * @return the periodoRegular
     */
    public Date getPeriodoRegular() {
        return periodoRegular;
    }

    /**
     * @param periodoRegular
     *        the periodoRegular to set
     */
    public void setPeriodoRegular(Date periodoRegular) {
        this.periodoRegular = periodoRegular;
    }

    /**
     * @return the codigoReclamo
     */
    public String getCodigoReclamo() {
        return codigoReclamo;
    }

    /**
     * @param codigoReclamo
     *        the codigoReclamo to set
     */
    public void setCodigoReclamo(String codigoReclamo) {
        this.codigoReclamo = codigoReclamo;
    }

    /**
     * @return the comentarioReclamo
     */
    public String getComentarioReclamo() {
        return comentarioReclamo;
    }

    /**
     * @param comentarioReclamo
     *        the comentarioReclamo to set
     */
    public void setComentarioReclamo(String comentarioReclamo) {
        this.comentarioReclamo = comentarioReclamo;
    }

    /**
     * @return the fechaDispersion
     */
    public Date getFechaDispersion() {
        return fechaDispersion;
    }

    /**
     * @param fechaDispersion
     *        the fechaDispersion to set
     */
    public void setFechaDispersion(Date fechaDispersion) {
        this.fechaDispersion = fechaDispersion;
    }

    /**
     * @return the consideracionAporteDesembolso
     */
    public Boolean getConsideracionAporteDesembolso() {
        return consideracionAporteDesembolso;
    }

    /**
     * @param consideracionAporteDesembolso
     *        the consideracionAporteDesembolso to set
     */
    public void setConsideracionAporteDesembolso(Boolean consideracionAporteDesembolso) {
        this.consideracionAporteDesembolso = consideracionAporteDesembolso;
    }

    /**
     * @return the modoDesembolso
     */
    public ModoDesembolsoEnum getModoDesembolso() {
        return modoDesembolso;
    }

    /**
     * @param modoDesembolso
     *        the modoDesembolso to set
     */
    public void setModoDesembolso(ModoDesembolsoEnum modoDesembolso) {
        this.modoDesembolso = modoDesembolso;
    }

	/**
	 * @return the idInstanciaProceso
	 */
	public String getIdInstanciaProceso() {
		return idInstanciaProceso;
	}

	/**
	 * @param idInstanciaProceso the idInstanciaProceso to set
	 */
	public void setIdInstanciaProceso(String idInstanciaProceso) {
		this.idInstanciaProceso = idInstanciaProceso;
	}

	/**
	 * @return the liquidacionORechazoEnProceso
	 */
	public Boolean getLiquidacionORechazOStagingEnProceso() {
		return liquidORechazOStagingEnProceso;
	}

	/**
	 * @param liquidacionORechazoEnProceso
	 */
	public void setLiquidacionORechazOStagingEnProceso(Boolean liquidacionORechazoEnProceso) {
		this.liquidORechazOStagingEnProceso = liquidacionORechazoEnProceso;
	}
	
	
	
}