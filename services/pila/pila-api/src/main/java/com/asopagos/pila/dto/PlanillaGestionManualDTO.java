package com.asopagos.pila.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.FasePila2Enum;
import com.asopagos.enumeraciones.pila.MotivoProcesoPilaManualEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;

/**
 * <b>Descripcion:</b> DTO que contiene los datos solicitados por la consulta de planillas pendientes por gestión manual<br/>
 * <b>Módulo:</b> Asopagos - HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero Echeverry</a>
 */

public class PlanillaGestionManualDTO implements Serializable {
    private static final long serialVersionUID = 6392068508923710659L;

    /** Nombre del archivo */
    private String nombreArchivo;

    /** Númeroo planilla */
    private String numeroPlanilla;

    /** Tipo de archivo */
    private TipoArchivoPilaEnum tipoArchivo;

    /** Tipo de planilla */
    private String tipoPlanilla;

    /** Período */
    private String periodoAporte;

    /** Fecha y hora de cargue */
    private Long fechaCarga;

    /** Tipo identificación del aportante */
    private TipoIdentificacionEnum tipoIdAportante;

    /** Número identificación del aportante */
    private String numeroIdAportante;

    /** Nombre del aportante */
    private String nombreAportante;

    /** Estado del proceso */
    private EstadoProcesoArchivoEnum estadoProceso;
    
    /** Identificador de Índice de Planilla */
    private Long idIndicePlanilla;
    
    /** ID de documento ECM para el archivo */
    private String idDocumentoECM; 
    
    /** Versión del documento en ECM del archivo */
    private String versionDocumentoECM;
    
    /** Fase de PILA 2 en la que se encuentra el archivo */
    private FasePila2Enum faseActual;
    
    /** Motivo por el cual se lanza un procesamiento manual de planilla PILA */
    private MotivoProcesoPilaManualEnum motivoProcesoManual;
    
    /** Indicador de planilla original procesada */
    private Long idPlanillaOriginal;
    
    /** Indicador de presencia de aportes anulados por la planilla original 
     * 0 -> No tiene anulaciones / aporte original
     * 1 -> Todos los aportes de la planilla original se encuentran anulados
     * 2 -> La planilla original presenta anulación parcial
     * */
    private Short presenciaAnulaciones = 0;

    /** Constructor por defecto para JSON converter */
    public PlanillaGestionManualDTO(){}

    /**
     * Constructor del DTO para uso en consultas JPA
     * @param nombreArchivo
     * @param numeroPlanilla
     * @param tipoArchivo
     * @param tipoPlanilla
     * @param periodoAporte
     * @param fechaCarga
     * @param tipoIdAporte
     * @param numeroIdAportante
     * @param nombreAportante
     * @param estadoProceso
     * @param idPlanillaOriginal 
     */
    public PlanillaGestionManualDTO(String nombreArchivo, Long numeroPlanilla, String tipoArchivo, String tipoPlanilla,
            String periodoAporte, Date fechaCarga, String tipoIdAporte, String numeroIdAportante, String nombreAportante,
            String estadoProceso, Long idIndicePlanilla, String idDocumentoECM, String versionDocumentoECM,
            String fase, String motivoProcesoManual, Long idPlanillaOriginal) {

        this.nombreArchivo = nombreArchivo;
        this.numeroPlanilla = numeroPlanilla.toString();
        this.tipoArchivo = TipoArchivoPilaEnum.valueOf(tipoArchivo);
        this.tipoPlanilla = tipoPlanilla;
        this.periodoAporte = periodoAporte;
        this.fechaCarga = fechaCarga.getTime();
        this.tipoIdAportante = TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(tipoIdAporte);
        this.numeroIdAportante = numeroIdAportante;
        this.nombreAportante = nombreAportante;
        this.estadoProceso = EstadoProcesoArchivoEnum.valueOf(estadoProceso);
        this.idIndicePlanilla = idIndicePlanilla;
        this.idDocumentoECM = idDocumentoECM;
        this.versionDocumentoECM = versionDocumentoECM;
        this.faseActual = FasePila2Enum.valueOf(fase);
        this.motivoProcesoManual = MotivoProcesoPilaManualEnum.valueOf(motivoProcesoManual);
        this.idPlanillaOriginal = idPlanillaOriginal;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo
     *        the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * @return the numeroPlanilla
     */
    public String getNumeroPlanilla() {
        return numeroPlanilla;
    }

    /**
     * @param numeroPlanilla
     *        the numeroPlanilla to set
     */
    public void setNumeroPlanilla(String numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }

    /**
     * @return the tipoArchivo
     */
    public TipoArchivoPilaEnum getTipoArchivo() {
        return tipoArchivo;
    }

    /**
     * @param tipoArchivo
     *        the tipoArchivo to set
     */
    public void setTipoArchivo(TipoArchivoPilaEnum tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    /**
     * @return the tipoPlanilla
     */
    public String getTipoPlanilla() {
        return tipoPlanilla;
    }

    /**
     * @param tipoPlanilla
     *        the tipoPlanilla to set
     */
    public void setTipoPlanilla(String tipoPlanilla) {
        this.tipoPlanilla = tipoPlanilla;
    }

    /**
     * @return the periodoAporte
     */
    public String getPeriodoAporte() {
        return periodoAporte;
    }

    /**
     * @param periodoAporte
     *        the periodoAporte to set
     */
    public void setPeriodoAporte(String periodoAporte) {
        this.periodoAporte = periodoAporte;
    }

    /**
     * @return the fechaCarga
     */
    public Long getFechaCarga() {
        return fechaCarga;
    }

    /**
     * @param fechaCarga
     *        the fechaCarga to set
     */
    public void setFechaCarga(Long fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    /**
     * @return the tipoIdAporte
     */
    public TipoIdentificacionEnum getTipoIdAporte() {
        return tipoIdAportante;
    }

    /**
     * @param tipoIdAportante
     *        the tipoIdAportante to set
     */
    public void setTipoIdAporte(TipoIdentificacionEnum tipoIdAportante) {
        this.tipoIdAportante = tipoIdAportante;
    }

    /**
     * @return the numeroIdAportante
     */
    public String getNumeroIdAportante() {
        return numeroIdAportante;
    }

    /**
     * @param numeroIdAportante
     *        the numeroIdAportante to set
     */
    public void setNumeroIdAportante(String numeroIdAportante) {
        this.numeroIdAportante = numeroIdAportante;
    }

    /**
     * @return the nombreAportante
     */
    public String getNombreAportante() {
        return nombreAportante;
    }

    /**
     * @param nombreAportante
     *        the nombreAportante to set
     */
    public void setNombreAportante(String nombreAportante) {
        this.nombreAportante = nombreAportante;
    }

    /**
     * @return the estadoProceso
     */
    public EstadoProcesoArchivoEnum getEstadoProceso() {
        return estadoProceso;
    }

    /**
     * @param estadoProceso
     *        the estadoProceso to set
     */
    public void setEstadoProceso(EstadoProcesoArchivoEnum estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    /**
     * @return the idIndicePlanilla
     */
    public Long getIdIndicePlanilla() {
        return idIndicePlanilla;
    }

    /**
     * @param idIndicePlanilla the idIndicePlanilla to set
     */
    public void setIdIndicePlanilla(Long idIndicePlanilla) {
        this.idIndicePlanilla = idIndicePlanilla;
    }

    /**
     * @return the tipoIdAportante
     */
    public TipoIdentificacionEnum getTipoIdAportante() {
        return tipoIdAportante;
    }

    /**
     * @param tipoIdAportante the tipoIdAportante to set
     */
    public void setTipoIdAportante(TipoIdentificacionEnum tipoIdAportante) {
        this.tipoIdAportante = tipoIdAportante;
    }

    /**
     * @return the idDocumentoECM
     */
    public String getIdDocumentoECM() {
        return idDocumentoECM;
    }

    /**
     * @param idDocumentoECM the idDocumentoECM to set
     */
    public void setIdDocumentoECM(String idDocumentoECM) {
        this.idDocumentoECM = idDocumentoECM;
    }

    /**
     * @return the versionDocumentoECM
     */
    public String getVersionDocumentoECM() {
        return versionDocumentoECM;
    }

    /**
     * @param versionDocumentoECM the versionDocumentoECM to set
     */
    public void setVersionDocumentoECM(String versionDocumentoECM) {
        this.versionDocumentoECM = versionDocumentoECM;
    }

    /**
     * @return the faseActual
     */
    public FasePila2Enum getFaseActual() {
        return faseActual;
    }

    /**
     * @param faseActual the faseActual to set
     */
    public void setFaseActual(FasePila2Enum faseActual) {
        this.faseActual = faseActual;
    }

    /**
     * @return the motivoProcesoManual
     */
    public MotivoProcesoPilaManualEnum getMotivoProcesoManual() {
        return motivoProcesoManual;
    }

    /**
     * @param motivoProcesoManual the motivoProcesoManual to set
     */
    public void setMotivoProcesoManual(MotivoProcesoPilaManualEnum motivoProcesoManual) {
        this.motivoProcesoManual = motivoProcesoManual;
    }

    /**
     * @return the idPlanillaOriginal
     */
    public Long getIdPlanillaOriginal() {
        return idPlanillaOriginal;
    }

    /**
     * @param idPlanillaOriginal the idPlanillaOriginal to set
     */
    public void setIdPlanillaOriginal(Long idPlanillaOriginal) {
        this.idPlanillaOriginal = idPlanillaOriginal;
    }

    /**
     * @return the presenciaAnulaciones
     */
    public Short getPresenciaAnulaciones() {
        return presenciaAnulaciones;
    }

    /**
     * @param presenciaAnulaciones the presenciaAnulaciones to set
     */
    public void setPresenciaAnulaciones(Short presenciaAnulaciones) {
        this.presenciaAnulaciones = presenciaAnulaciones;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlanillaGestionManualDTO [nombreArchivo=");
		builder.append(nombreArchivo);
		builder.append(", numeroPlanilla=");
		builder.append(numeroPlanilla);
		builder.append(", tipoArchivo=");
		builder.append(tipoArchivo);
		builder.append(", tipoPlanilla=");
		builder.append(tipoPlanilla);
		builder.append(", periodoAporte=");
		builder.append(periodoAporte);
		builder.append(", fechaCarga=");
		builder.append(fechaCarga);
		builder.append(", tipoIdAportante=");
		builder.append(tipoIdAportante);
		builder.append(", numeroIdAportante=");
		builder.append(numeroIdAportante);
		builder.append(", nombreAportante=");
		builder.append(nombreAportante);
		builder.append(", estadoProceso=");
		builder.append(estadoProceso);
		builder.append(", idIndicePlanilla=");
		builder.append(idIndicePlanilla);
		builder.append(", idDocumentoECM=");
		builder.append(idDocumentoECM);
		builder.append(", versionDocumentoECM=");
		builder.append(versionDocumentoECM);
		builder.append(", faseActual=");
		builder.append(faseActual);
		builder.append(", motivoProcesoManual=");
		builder.append(motivoProcesoManual);
		builder.append(", idPlanillaOriginal=");
		builder.append(idPlanillaOriginal);
		builder.append(", presenciaAnulaciones=");
		builder.append(presenciaAnulaciones);
		builder.append("]");
		return builder.toString();
	}
}
