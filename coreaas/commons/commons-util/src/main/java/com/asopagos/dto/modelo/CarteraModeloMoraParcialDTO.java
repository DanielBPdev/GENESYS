package com.asopagos.dto.modelo;

import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.DeudaParcialEnum;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoDeudaEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.text.DateFormat;  
import java.text.SimpleDateFormat;

/**
 * DTO que contiene el modelo del estado de cartera de los aportantes.
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
public class CarteraModeloMoraParcialDTO implements Serializable {
    /**
     * Serial version
     */
    private static final long serialVersionUID = 8859026526280068108L;

    /**
     * Identificador de cartera (En vista 360 se usa este campo guardando el numero de operación)
     */
    private Long idCartera;

    /**
     * Identificador de la exclusion de cartera
     */
    private Long idExclusionCartera;

    /**
     * Usuario que registra la exclusion
     */
    private String usuarioRegistro;

    /**
     * Valor de deuda presunta
     */
    private BigDecimal deudaPresunta;

    /**
     * Identificador de la entidad reportada en cartera
     */
    private Long idPersona;

    /**
     * Periodo de deuda de la entidad
     */
    private Long periodoDeuda;


    /**
     * Tipo de acción de cobro
     */
    private TipoAccionCobroEnum tipoAccionCobro;

    /**
     * Tipo de deuda
     */
    private TipoDeudaEnum tipoDeuda;


    /**
     * Tipo de solicitante
     */
    private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;

    /**
     * Persona modelo DTO.
     */
    private PersonaModeloDTO personaDTO;

    /**
     * deuda parcial.
     */
    private DeudaParcialEnum deudaParcial;
    /**
     * Fecha de inico
     */
    private Long fechaInicio;
    /**
     * Fecha de finalizacion
     */
    private Long fechaFinalizacion;

    /**
     * Estado de cartera para un aportante
     */
    private EstadoCarteraEnum estadoCartera;


    /**
     * Método constructor
     */
    public CarteraModeloMoraParcialDTO() {

    }

    public Long getIdCartera() {
        return idCartera;
    }

    public void setIdCartera(Long idCartera) {
        this.idCartera = idCartera;
    }

    public Long getIdExclusionCartera() {
        return idExclusionCartera;
    }

    public void setIdExclusionCartera(Long idExclusionCartera) {
        this.idExclusionCartera = idExclusionCartera;
    }

    public BigDecimal getDeudaPresunta() {
        return deudaPresunta;
    }

    public void setDeudaPresunta(BigDecimal deudaPresunta) {
        this.deudaPresunta = deudaPresunta;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Long getPeriodoDeuda() {
        return periodoDeuda;
    }

    public void setPeriodoDeuda(Long periodoDeuda) {
        this.periodoDeuda = periodoDeuda;
    }

    public TipoAccionCobroEnum getTipoAccionCobro() {
        return tipoAccionCobro;
    }

    public void setTipoAccionCobro(TipoAccionCobroEnum tipoAccionCobro) {
        this.tipoAccionCobro = tipoAccionCobro;
    }

    public TipoDeudaEnum getTipoDeuda() {
        return tipoDeuda;
    }

    public void setTipoDeuda(TipoDeudaEnum tipoDeuda) {
        this.tipoDeuda = tipoDeuda;
    }

    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    public PersonaModeloDTO getPersonaDTO() {
        return personaDTO;
    }

    public void setPersonaDTO(PersonaModeloDTO personaDTO) {
        this.personaDTO = personaDTO;
    }

    public DeudaParcialEnum getDeudaParcial() {
        return deudaParcial;
    }

    public void setDeudaParcial(DeudaParcialEnum deudaParcial) {
        this.deudaParcial = deudaParcial;
    }

    public Long getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Long getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Long fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    /**
     * Establece el valor de estadoCartera
     *
     * @param estadoCartera El valor de estadoCartera por asignar
     */
    public void setEstadoCartera(EstadoCarteraEnum estadoCartera) {
        this.estadoCartera = estadoCartera;
    }

    /**
     * Obtiene el valor de estadoCartera
     *
     * @return El valor de estadoCartera
     */
    public EstadoCarteraEnum getEstadoCartera() {
        return estadoCartera;
    }

        public void limpiarNulos() {
        if (this.personaDTO == null) this.personaDTO = new PersonaModeloDTO();
        if (this.idCartera == null) this.idCartera = 0L;
        if (this.deudaPresunta == null) this.deudaPresunta = BigDecimal.ZERO;
        if (this.idPersona == null) this.idPersona = 0L;
        if (this.periodoDeuda == null) this.periodoDeuda = 0L;
    }

    public String[] toListString() {
        this.limpiarNulos();

        String periodoDeudaFormateado = this.periodoDeuda != null ? new SimpleDateFormat("yyyy-MM").format(new Date(periodoDeuda)) : "0";
        String deudaPresuntaFormateada = this.deudaPresunta != null ? this.deudaPresunta.setScale(0, BigDecimal.ROUND_HALF_UP).toString() : BigDecimal.ZERO.toString();

        String fechaInicioFormateada = this.fechaInicio != null ? new SimpleDateFormat("dd/MM/yyyy").format(new Date(fechaInicio)) : "0";
        String fechaFinalizacionFormateada = this.fechaFinalizacion != null ? new SimpleDateFormat("dd/MM/yyyy").format(new Date(fechaFinalizacion)) : "0";

        String[] data = new String[] {
            this.personaDTO.getTipoIdentificacion() != null ? this.personaDTO.getTipoIdentificacion().name() : "",
            this.personaDTO.getNumeroIdentificacion() != null ? this.personaDTO.getNumeroIdentificacion() : "",
            this.personaDTO.getRazonSocial() != null ? this.personaDTO.getRazonSocial() : "",
            this.estadoCartera != null ? this.estadoCartera.name() : "",
            periodoDeudaFormateado,
            "Deuda Parcial",
            deudaPresuntaFormateada,
            this.tipoAccionCobro != null ? this.tipoAccionCobro.name() : "",
            this.usuarioRegistro != null ? this.usuarioRegistro : "",
            fechaInicioFormateada,
            fechaFinalizacionFormateada
        };
        return data;
    }

}
