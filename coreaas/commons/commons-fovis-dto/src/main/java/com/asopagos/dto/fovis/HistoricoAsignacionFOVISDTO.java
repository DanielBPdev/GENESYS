package com.asopagos.dto.fovis;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;
import com.asopagos.enumeraciones.fovis.PrioridadAsignacionEnum;
import com.asopagos.enumeraciones.fovis.ResultadoAsignacionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción</b> DTO que representa los datos que debe llevar la consulta
 * de las cartas de asignacion generadas <b>HU-051</b>
 * 
 * @author <a href="mailto:ecastano@heinsohn.com.co">Edward Castano</a>
 */
@XmlRootElement
public class HistoricoAsignacionFOVISDTO implements Serializable {

    /**
     * Serial autogenerado.
     */
    private static final long serialVersionUID = 2498089760493805105L;

    /**
     * Identificador único, llave primaria
     */
    private Long idPostulacion;

    /**
     * Nombre del ciclo de asignación.
     */
    private String nombreCiclo;

    /**
     * Número de radicación de la solicitud
     */
    private String numeroRadicacion;

    /**
     * Número de identificación del jefe de hogar.
     */
    private String numeroIdentificacionJefeHogar;

    /**
     * Número de identificación del jefe de hogar.
     */
    private TipoIdentificacionEnum tipoIdentificacion;

    /**
     * Nombre del jefe de hogar.
     */
    private String nombreJefeHogar;

    /**
     * Modalidad de la postulacion
     */
    private ModalidadEnum modalidad;

    /**
     * Identificador de la carta de asignacion generada para la postulacion
     */
    private String idCartaAsignacion;

    /**
     * Puntaje solicitado asociado a la Postulación.
     */
    private BigDecimal puntaje;

    /**
     * Usuario que aprueba
     */
    private String usuario;

    /**
     * Valor SFV solicitado asociado a la Postulación.
     */
    private BigDecimal valorSFVSolicitado;

    /**
     * Fecha de creacion acta de asignacion fovis
     */
    private Long fechaActaAsignacionFovis;

    /**
     * Estado del resultado de asignacion para el hogar
     */
    private ResultadoAsignacionEnum resultadoAsignacion;

    /**
     * Fecha de aceptacion de resultados para el ciclo de asignación
     * seleccionado
     */
    private Long fechaAceptacion;

    /**
     * Prioridad obtenida durante el análisis de asignación del hogar al
     * subsidio
     */
    private PrioridadAsignacionEnum prioridadAsignacion;

    /**
     * Informacion postulación al momento de la asignación
     */
    private String infoAsignacion;
    
    /**
     * Recurso prioridad de la asignacion
     */
    private String recursoPrioridad;

    public HistoricoAsignacionFOVISDTO() {
        super();
    }

    /**
     * Método constructor para devolver los datos consultados relacionados a la
     * lista de cartas de asignacion
     */
    public HistoricoAsignacionFOVISDTO(String idPostulacion, String nombreCiclo, String numeroRadicacion,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacionJefeHogar, String nombreJefeHogar,
            ModalidadEnum modalidad, String puntaje, String valorSolicitado, String idCartaAsignacion, String usuario,
            ResultadoAsignacionEnum resultado, Date fechaActa, Date fechaAceptacion, PrioridadAsignacionEnum prioridadAsignacion, String infoAsignacion, String recursoPrioridad) {
        this.setIdPostulacion(Long.valueOf(idPostulacion));
        this.setNombreCiclo(nombreCiclo);
        this.setNumeroRadicacion(numeroRadicacion);
        this.setNumeroIdentificacionJefeHogar(numeroIdentificacionJefeHogar);
        this.setTipoIdentificacion(tipoIdentificacion);
        this.setNombreJefeHogar(nombreJefeHogar);
        this.setModalidad(modalidad);
        this.setIdCartaAsignacion(idCartaAsignacion);
        this.setUsuario(usuario);
        this.setPuntaje(puntaje != null ? new BigDecimal(puntaje) : null);
        this.setValorSFVSolicitado(valorSolicitado != null ? new BigDecimal(valorSolicitado) : null);
        this.setResultadoAsignacion(resultado);
        this.setFechaActaAsignacionFovis(fechaActa != null ? fechaActa.getTime() : null);
        this.setFechaAceptacion(fechaAceptacion != null ? fechaAceptacion.getTime() : null);
        this.setPrioridadAsignacion(prioridadAsignacion);
        this.setInfoAsignacion(infoAsignacion);
        this.setRecursoPrioridad(recursoPrioridad);
    }

    /**
     * Método que retorna el valor de numeroIdentificacionJefeHogar.
     * 
     * @return valor de numeroIdentificacionJefeHogar.
     */
    public String getNumeroIdentificacionJefeHogar() {
        return numeroIdentificacionJefeHogar;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacionJefeHogar.
     * 
     * @param valor
     *        para modificar numeroIdentificacionJefeHogar.
     */
    public void setNumeroIdentificacionJefeHogar(String numeroIdentificacionJefeHogar) {
        this.numeroIdentificacionJefeHogar = numeroIdentificacionJefeHogar;
    }

    /**
     * @return the numeroRadicacion
     */
    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }

    /**
     * @param numeroRadicacion
     *        the numeroRadicacion to set
     */
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    /**
     * @return the nombreJefeHogar
     */
    public String getNombreJefeHogar() {
        return nombreJefeHogar;
    }

    /**
     * @param nombreJefeHogar
     *        the nombreJefeHogar to set
     */
    public void setNombreJefeHogar(String nombreJefeHogar) {
        this.nombreJefeHogar = nombreJefeHogar;
    }

    /**
     * @return the modalidad
     */
    public ModalidadEnum getModalidad() {
        return modalidad;
    }

    /**
     * @param modalidad
     *        the modalidad to set
     */
    public void setModalidad(ModalidadEnum modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * @return the idCartaAsignacion
     */
    public String getIdCartaAsignacion() {
        return idCartaAsignacion;
    }

    /**
     * @param idCartaAsignacion
     *        the idCartaAsignacion to set
     */
    public void setIdCartaAsignacion(String idCartaAsignacion) {
        this.idCartaAsignacion = idCartaAsignacion;
    }

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion
     *        the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the puntaje
     */
    public BigDecimal getPuntaje() {
        return puntaje;
    }

    /**
     * @param puntaje
     *        the puntaje to set
     */
    public void setPuntaje(BigDecimal puntaje) {
        this.puntaje = puntaje;
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
     * @return the valorSFVSolicitado
     */
    public BigDecimal getValorSFVSolicitado() {
        return valorSFVSolicitado;
    }

    /**
     * @param valorSFVSolicitado
     *        the valorSFVSolicitado to set
     */
    public void setValorSFVSolicitado(BigDecimal valorSFVSolicitado) {
        this.valorSFVSolicitado = valorSFVSolicitado;
    }

    /**
     * @return the resultadoAsignacion
     */
    public ResultadoAsignacionEnum getResultadoAsignacion() {
        return resultadoAsignacion;
    }

    /**
     * @param resultadoAsignacion
     *        the resultadoAsignacion to set
     */
    public void setResultadoAsignacion(ResultadoAsignacionEnum resultadoAsignacion) {
        this.resultadoAsignacion = resultadoAsignacion;
    }

    /**
     * @return the fechaActaAsignacionFovis
     */
    public Long getFechaActaAsignacionFovis() {
        return fechaActaAsignacionFovis;
    }

    /**
     * @param fechaActaAsignacionFovis
     *        the fechaActaAsignacionFovis to set
     */
    public void setFechaActaAsignacionFovis(Long fechaActaAsignacionFovis) {
        this.fechaActaAsignacionFovis = fechaActaAsignacionFovis;
    }

    /**
     * @return the fechaAceptacion
     */
    public Long getFechaAceptacion() {
        return fechaAceptacion;
    }

    /**
     * @param fechaAceptacion
     *        the fechaAceptacion to set
     */
    public void setFechaAceptacion(Long fechaAceptacion) {
        this.fechaAceptacion = fechaAceptacion;
    }

    /**
     * @return the nombreCiclo
     */
    public String getNombreCiclo() {
        return nombreCiclo;
    }

    /**
     * @param nombreCiclo
     *        the nombreCiclo to set
     */
    public void setNombreCiclo(String nombreCiclo) {
        this.nombreCiclo = nombreCiclo;
    }

    /**
     * @return the idPostulacion
     */
    public Long getIdPostulacion() {
        return idPostulacion;
    }

    /**
     * @param idPostulacion
     *        the idPostulacion to set
     */
    public void setIdPostulacion(Long idPostulacion) {
        this.idPostulacion = idPostulacion;
    }

    /**
     * @return the prioridadAsignacion
     */
    public PrioridadAsignacionEnum getPrioridadAsignacion() {
        return prioridadAsignacion;
    }

    /**
     * @param prioridadAsignacion
     *        the prioridadAsignacion to set
     */
    public void setPrioridadAsignacion(PrioridadAsignacionEnum prioridadAsignacion) {
        this.prioridadAsignacion = prioridadAsignacion;
    }

    /**
     * @return the infoAsignacion
     */
    public String getInfoAsignacion() {
        return infoAsignacion;
    }

    /**
     * @param infoAsignacion
     *        the infoAsignacion to set
     */
    public void setInfoAsignacion(String infoAsignacion) {
        this.infoAsignacion = infoAsignacion;
    }
    
    /**
     * @return the recursoPrioridad
     */
    public String getRecursoPrioridad() {
        return recursoPrioridad;
    }

    /**
     * @param recursoPrioridad
     *        the recursoPrioridad to set
     */
    public void setRecursoPrioridad(String recursoPrioridad) {
        this.recursoPrioridad = recursoPrioridad;
    }

}
