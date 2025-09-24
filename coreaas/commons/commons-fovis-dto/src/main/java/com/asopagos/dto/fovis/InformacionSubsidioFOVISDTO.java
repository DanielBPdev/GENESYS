package com.asopagos.dto.fovis;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import com.asopagos.entidades.ccf.fovis.PostulacionFOVIS;
import com.asopagos.entidades.ccf.fovis.SolicitudPostulacion;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.fovis.parametricas.CicloAsignacion;
import com.asopagos.enumeraciones.fovis.CondicionHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.PersonasUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * <b>Descripcion:</b> Clase que contiene la informacion del subsidio FOVIS<br/>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co">Jose Arley Correa</a>
 */
@JsonInclude(Include.NON_EMPTY)
public class InformacionSubsidioFOVISDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Tipo de identificación del Jefe Hogar
     */
    private TipoIdentificacionEnum tipoId;

    /**
     * Número de identificación del Jefe Hogar
     */
    private String identificacion;

    /**
     * Nombre completo del Jefe Hogar
     */
    private String nombreCompleto;

    /**
     * Tipo del aportante (Dependiente, Independiente, Pensionado)
     */
    private TipoAfiliadoEnum tipoAportante;

    /**
     * Condición del Jefe Hogar
     */
    private String condicionJefeHogar;

    /**
     * Ciclo de asignación de la postulación
     */
    private String cicloAsignacion;

    /**
     * Número de radicado de la solicitud
     */
    private String numeroRadicado;

    /**
     * Fecha en la que se cambio el estado de la asignación
     */
    private String fechaEstadoPostulacion;

    /**
     * Estado de la postulación FOVIS
     */
    private EstadoHogarEnum estadoPostulacion;

    /**
     * Condición del hogar
     */
    private CondicionHogarEnum condicionHogar;

    /**
     * Modalidad de la postulación FOVIS
     */
    private ModalidadEnum modalidad;

    /**
     * Código de la visita del inspector FOVIS
     */
    private String codigoVisita;

    /**
     * Puntaje del proceso de asignación
     */
    private BigDecimal puntaje;

    /**
     * Fecha en la que se realizó la calificación del hogar
     */
    private String fechaPuntaje;

    /**
     * Valor solicitado asociado a la Postulación
     */
    private BigDecimal valorSolicitado;

    /**
     * Valor asignado al proyecto o solucion Vivienda
     */
    private BigDecimal valorAsignado;

    /**
     * Valor del Proyecto o solución de vivienda seleccionada para el subsidio
     */
    private BigDecimal valorSolucionVivienda;

    /**
     * Valor del monto desembolsado (EstadoHogar=SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO, SUBSIDIO_DESEMBOLSADO)
     */
    private BigDecimal montoDesembolsado;

    /**
     * Información del oferente
     */
    private InformacionOferenteSubsidioFOVISDTO oferente;

    /**
     * Lista de ahorros previos postulación
     */
    private List<InformacionAporteSubsidioFOVISDTO> ahorrosPrevios;

    /**
     * Lista de recursos complementarios postulación
     */
    private List<InformacionAporteSubsidioFOVISDTO> recursosComplementarios;

    /**
     * Lista de integrantes del Hogar
     */
    private List<InformacionIntegranteHogarSubsidioFOVISDTO> integrantesHogar;

    /**
     * Constructor por defecto
     */
    public InformacionSubsidioFOVISDTO() {
        super();
    }

    public InformacionSubsidioFOVISDTO(PostulacionFOVIS postulacionFOVIS, SolicitudPostulacion solicitudPostulacion, Persona jefeHogar,
            CicloAsignacion cicloAsignacion, BigDecimal codigoVisita, BigDecimal montoDesembolsado) {
        super();
        this.setTipoId(jefeHogar.getTipoIdentificacion());
        this.setIdentificacion(jefeHogar.getNumeroIdentificacion());
        this.setNombreCompleto(PersonasUtils.obtenerNombreORazonSocial(jefeHogar));
        this.setCicloAsignacion(cicloAsignacion.getNombre());
        this.setNumeroRadicado(solicitudPostulacion.getSolicitudGlobal().getNumeroRadicacion());
        if (solicitudPostulacion.getSolicitudGlobal().getFechaRadicacion() != null) {
            this.setFechaEstadoPostulacion(CalendarUtils.darFormatoYYYYMMDD(solicitudPostulacion.getSolicitudGlobal().getFechaRadicacion()));
        }
        this.setEstadoPostulacion(postulacionFOVIS.getEstadoHogar());
        this.setCondicionHogar(postulacionFOVIS.getCondicionHogar());
        this.setModalidad(postulacionFOVIS.getModalidad());
        if (codigoVisita != null) {
            this.setCodigoVisita(codigoVisita.toString());
        }
        this.setPuntaje(postulacionFOVIS.getPuntaje());
        if (postulacionFOVIS.getFechaCalificacion() != null) {
            this.setFechaPuntaje(CalendarUtils.darFormatoYYYYMMDD(postulacionFOVIS.getFechaCalificacion()));
        }
        this.setValorSolicitado(postulacionFOVIS.getValorSFVSolicitado());
        this.setValorAsignado(postulacionFOVIS.getValorAsignadoSFV());
        this.setValorSolucionVivienda(postulacionFOVIS.getValorProyectoVivienda());
        this.setMontoDesembolsado(montoDesembolsado);
    }
    
    /**
     * @return the tipoId
     */
    public TipoIdentificacionEnum getTipoId() {
        return tipoId;
    }

    /**
     * @param tipoId
     *        the tipoId to set
     */
    public void setTipoId(TipoIdentificacionEnum tipoId) {
        this.tipoId = tipoId;
    }

    /**
     * @return the identificacion
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * @param identificacion
     *        the identificacion to set
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    /**
     * @return the nombreCompleto
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * @param nombreCompleto
     *        the nombreCompleto to set
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * @return the tipoAportante
     */
    public TipoAfiliadoEnum getTipoAportante() {
        return tipoAportante;
    }

    /**
     * @param tipoAportante
     *        the tipoAportante to set
     */
    public void setTipoAportante(TipoAfiliadoEnum tipoAportante) {
        this.tipoAportante = tipoAportante;
    }

    /**
     * @return the condicionJefeHogar
     */
    public String getCondicionJefeHogar() {
        return condicionJefeHogar;
    }

    /**
     * @param condicionJefeHogar
     *        the condicionJefeHogar to set
     */
    public void setCondicionJefeHogar(String condicionJefeHogar) {
        this.condicionJefeHogar = condicionJefeHogar;
    }

    /**
     * @return the cicloAsignacion
     */
    public String getCicloAsignacion() {
        return cicloAsignacion;
    }

    /**
     * @param cicloAsignacion
     *        the cicloAsignacion to set
     */
    public void setCicloAsignacion(String cicloAsignacion) {
        this.cicloAsignacion = cicloAsignacion;
    }

    /**
     * @return the numeroRadicado
     */
    public String getNumeroRadicado() {
        return numeroRadicado;
    }

    /**
     * @param numeroRadicado
     *        the numeroRadicado to set
     */
    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }

    /**
     * @return the fechaEstadoPostulacion
     */
    public String getFechaEstadoPostulacion() {
        return fechaEstadoPostulacion;
    }

    /**
     * @param fechaEstadoPostulacion
     *        the fechaEstadoPostulacion to set
     */
    public void setFechaEstadoPostulacion(String fechaEstadoPostulacion) {
        this.fechaEstadoPostulacion = fechaEstadoPostulacion;
    }

    /**
     * @return the estadoPostulacion
     */
    public EstadoHogarEnum getEstadoPostulacion() {
        return estadoPostulacion;
    }

    /**
     * @param estadoPostulacion
     *        the estadoPostulacion to set
     */
    public void setEstadoPostulacion(EstadoHogarEnum estadoPostulacion) {
        this.estadoPostulacion = estadoPostulacion;
    }

    /**
     * @return the condicionHogar
     */
    public CondicionHogarEnum getCondicionHogar() {
        return condicionHogar;
    }

    /**
     * @param condicionHogar
     *        the condicionHogar to set
     */
    public void setCondicionHogar(CondicionHogarEnum condicionHogar) {
        this.condicionHogar = condicionHogar;
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
     * @return the codigoVisita
     */
    public String getCodigoVisita() {
        return codigoVisita;
    }

    /**
     * @param codigoVisita
     *        the codigoVisita to set
     */
    public void setCodigoVisita(String codigoVisita) {
        this.codigoVisita = codigoVisita;
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
     * @return the fechaPuntaje
     */
    public String getFechaPuntaje() {
        return fechaPuntaje;
    }

    /**
     * @param fechaPuntaje
     *        the fechaPuntaje to set
     */
    public void setFechaPuntaje(String fechaPuntaje) {
        this.fechaPuntaje = fechaPuntaje;
    }

    /**
     * @return the valorSolicitado
     */
    public BigDecimal getValorSolicitado() {
        return valorSolicitado;
    }

    /**
     * @param valorSolicitado
     *        the valorSolicitado to set
     */
    public void setValorSolicitado(BigDecimal valorSolicitado) {
        this.valorSolicitado = valorSolicitado;
    }

    /**
     * @return the valorAsignado
     */
    public BigDecimal getValorAsignado() {
        return valorAsignado;
    }

    /**
     * @param valorAsignado
     *        the valorAsignado to set
     */
    public void setValorAsignado(BigDecimal valorAsignado) {
        this.valorAsignado = valorAsignado;
    }

    /**
     * @return the valorSolucionVivienda
     */
    public BigDecimal getValorSolucionVivienda() {
        return valorSolucionVivienda;
    }

    /**
     * @param valorSolucionVivienda
     *        the valorSolucionVivienda to set
     */
    public void setValorSolucionVivienda(BigDecimal valorSolucionVivienda) {
        this.valorSolucionVivienda = valorSolucionVivienda;
    }

    /**
     * @return the montoDesembolsado
     */
    public BigDecimal getMontoDesembolsado() {
        return montoDesembolsado;
    }

    /**
     * @param montoDesembolsado
     *        the montoDesembolsado to set
     */
    public void setMontoDesembolsado(BigDecimal montoDesembolsado) {
        this.montoDesembolsado = montoDesembolsado;
    }

    /**
     * @return the oferente
     */
    public InformacionOferenteSubsidioFOVISDTO getOferente() {
        return oferente;
    }

    /**
     * @param oferente
     *        the oferente to set
     */
    public void setOferente(InformacionOferenteSubsidioFOVISDTO oferente) {
        this.oferente = oferente;
    }

    /**
     * @return the ahorrosPrevios
     */
    public List<InformacionAporteSubsidioFOVISDTO> getAhorrosPrevios() {
        return ahorrosPrevios;
    }

    /**
     * @param ahorrosPrevios
     *        the ahorrosPrevios to set
     */
    public void setAhorrosPrevios(List<InformacionAporteSubsidioFOVISDTO> ahorrosPrevios) {
        this.ahorrosPrevios = ahorrosPrevios;
    }

    /**
     * @return the recursosComplementarios
     */
    public List<InformacionAporteSubsidioFOVISDTO> getRecursosComplementarios() {
        return recursosComplementarios;
    }

    /**
     * @param recursosComplementarios
     *        the recursosComplementarios to set
     */
    public void setRecursosComplementarios(List<InformacionAporteSubsidioFOVISDTO> recursosComplementarios) {
        this.recursosComplementarios = recursosComplementarios;
    }

    /**
     * @return the integrantesHogar
     */
    public List<InformacionIntegranteHogarSubsidioFOVISDTO> getIntegrantesHogar() {
        return integrantesHogar;
    }

    /**
     * @param integrantesHogar
     *        the integrantesHogar to set
     */
    public void setIntegrantesHogar(List<InformacionIntegranteHogarSubsidioFOVISDTO> integrantesHogar) {
        this.integrantesHogar = integrantesHogar;
    }

}
