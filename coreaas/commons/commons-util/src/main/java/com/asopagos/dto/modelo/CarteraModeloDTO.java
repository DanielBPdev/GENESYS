package com.asopagos.dto.modelo;

import com.asopagos.entidades.ccf.cartera.Cartera;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Calendar;
import java.util.ArrayList;

/**
 * DTO que contiene el modelo del estado de cartera de los aportantes.
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
public class CarteraModeloDTO implements Serializable {
    /**
     * Serial version
     */
    private static final long serialVersionUID = 8859026526280068108L;

    /**
     * Identificador de cartera (En vista 360 se usa este campo guardando el numero de operación)
     */
    private Long idCartera;

    /**
     * Identificador de cartera se agrega para el control de cambios mantis 268882 para guardar el identificador de cartera
     * en idCartera se esta asignando el número de operación como se hace en vista 360
     */
    private Long identificadorCartera;

    /**
     * Valor de deuda presunta
     */
    private BigDecimal deudaPresunta;

    /**
     * Valor de deuda real
     */
    private BigDecimal deudaReal;

    /**
     * Valor de deuda real
     */
    private BigDecimal deudaTotal;

    /**
     * Estado de cartera para un aportante
     */
    private EstadoCarteraEnum estadoCartera;

    /**
     * Estado de la operación de cartera
     */
    private EstadoOperacionCarteraEnum estadoOperacion;

    /**
     * Representa la fecha en que realizo la cartera
     */
    private Long fechaCreacion;

    /**
     * Identificador de la entidad reportada en cartera
     */
    private Long idPersona;

    /**
     * Método de acción de cobro
     */
    private MetodoAccionCobroEnum metodo;

    /**
     * Periodo de deuda de la entidad
     */
    private Long periodoDeuda;

    /**
     * Riesgo incobrabilidad
     */
    private RiesgoIncobrabilidadEnum riesgoIncobrabilidad;

    /**
     * Tipo de acción de cobro
     */
    private TipoAccionCobroEnum tipoAccionCobro;

    /**
     * Tipo de deuda
     */
    private TipoDeudaEnum tipoDeuda;

    /**
     * Tipo de línea de cobro
     */
    private TipoLineaCobroEnum tipoLineaCobro;

    /**
     * Tipo de solicitante
     */
    private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;

    /**
     * Persona modelo DTO.
     */
    private PersonaModeloDTO personaDTO;
    /**
     * Usuario que realizo el traspaso a deuda antigua
     */
    private String usuarioTraspaso;
    /**
     * Representa la fecha en que se realizo el traspaso de la deuda
     */
    private Long fechaAsignacionAccion;

    /**
     * Estado de la solicitud de gestión de cobro activa
     */
    private EstadoSolicitudGestionCobroEnum estadoSolicitudActiva;

    /**
     * Usuario que realizo el traspaso a deuda antigua
     */
    private String clasificacion;

    /**
     * Lista de actividades bitacora
     */
    private List<String> actividades;
    /**
     * deuda parcial.
     */
    private DeudaParcialEnum deudaParcial;

    /**
     * prescribir cartera.
     */
    private String prescribir;

    /**
     * Método constructor
     */
    public CarteraModeloDTO() {

    }

    /**
     * Método constructor con la cartera y la persona.
     *
     * @param cartera datos de cartera.
     * @param persona datos de identificación del aportante asociado a la cartera.
     */
    public CarteraModeloDTO(Cartera cartera, Persona persona) {
        convertToDTO(cartera);
        personaDTO = new PersonaModeloDTO();
        personaDTO.setTipoIdentificacion(persona.getTipoIdentificacion());
        personaDTO.setNumeroIdentificacion(persona.getNumeroIdentificacion());
        personaDTO.setRazonSocial(persona.getRazonSocial());
    }

    /**
     * Método Constructor
     *
     * @param idCartera       Identificador unico de la entidad Cartera
     * @param deudaPresunta   Valor de la deuda presunta
     * @param idPersona       Identificador unico d ela entidad Persona
     * @param periodoDeuda    Fecha del period de la deuda
     * @param tipoSolicitante Tipo de solicitante
     */
    public CarteraModeloDTO(Cartera cartera) {
        this.idCartera = cartera.getIdCartera();
        this.deudaPresunta = cartera.getDeudaPresunta();
        this.idPersona = cartera.getIdPersona();
        this.periodoDeuda = cartera.getPeriodoDeuda() != null ? cartera.getPeriodoDeuda().getTime() : null;
        this.tipoSolicitante = cartera.getTipoSolicitante();
        this.estadoCartera = cartera.getEstadoCartera();
        this.estadoOperacion = cartera.getEstadoOperacion();
        this.fechaCreacion = cartera.getFechaCreacion().getTime();
        this.metodo = cartera.getMetodo();
        this.riesgoIncobrabilidad = cartera.getRiesgoIncobrabilidad();
        this.tipoAccionCobro = cartera.getTipoAccionCobro();
        this.tipoDeuda = cartera.getTipoDeuda();
        this.tipoLineaCobro = cartera.getTipoLineaCobro();
        this.usuarioTraspaso = cartera.getUsuarioTraspaso();
        this.fechaAsignacionAccion = cartera.getFechaAsignacionAccion() != null ? cartera.getFechaAsignacionAccion().getTime() : null;
    }

    public void convertToDTO(Cartera cartera) {
        this.deudaPresunta = cartera.getDeudaPresunta();
        this.deudaReal = cartera.getDeudaPresunta();
        this.estadoCartera = cartera.getEstadoCartera();
        this.estadoOperacion = cartera.getEstadoOperacion();
        this.fechaCreacion = cartera.getFechaCreacion().getTime();
        this.idCartera = cartera.getIdCartera();
        this.idPersona = cartera.getIdPersona();
        this.metodo = cartera.getMetodo();
        this.periodoDeuda = cartera.getPeriodoDeuda().getTime();
        this.riesgoIncobrabilidad = cartera.getRiesgoIncobrabilidad();
        this.tipoAccionCobro = cartera.getTipoAccionCobro();
        this.tipoDeuda = cartera.getTipoDeuda();
        this.tipoLineaCobro = cartera.getTipoLineaCobro();
        this.tipoSolicitante = cartera.getTipoSolicitante();
        this.usuarioTraspaso = cartera.getUsuarioTraspaso();
        this.fechaAsignacionAccion = cartera.getFechaAsignacionAccion().getTime();
    }

    /**
     * Metodo que convierte de DTO a entidad
     *
     * @param cartera
     */
    public Cartera convertToEntity() {

        Cartera cartera = new Cartera();
        cartera.setDeudaPresunta(this.getDeudaPresunta());
        cartera.setEstadoCartera(this.estadoCartera);
        cartera.setEstadoOperacion(this.estadoOperacion);
        cartera.setFechaCreacion(new Date(fechaCreacion));
        cartera.setIdCartera(this.idCartera);
        cartera.setIdPersona(this.idPersona);
        cartera.setMetodo(this.metodo);
        cartera.setPeriodoDeuda(new Date(periodoDeuda));
        cartera.setRiesgoIncobrabilidad(this.riesgoIncobrabilidad);
        cartera.setTipoAccionCobro(this.tipoAccionCobro);
        cartera.setTipoDeuda(this.tipoDeuda);
        cartera.setTipoLineaCobro(this.tipoLineaCobro);
        cartera.setTipoSolicitante(this.tipoSolicitante);
        cartera.setUsuarioTraspaso(this.usuarioTraspaso);
        cartera.setFechaAsignacionAccion(this.fechaAsignacionAccion != null ? new Date(this.fechaAsignacionAccion) : null);
        cartera.setDeudaParcial(this.deudaParcial != null ? deudaParcial : null);
        return cartera;
    }


    public void limpiarNulos() {
        if (this.personaDTO == null) this.personaDTO = new PersonaModeloDTO();
        if (this.idCartera == null) this.idCartera = 0L;
        if (this.identificadorCartera == null) this.identificadorCartera = 0L;
        if (this.deudaPresunta == null) this.deudaPresunta = BigDecimal.ZERO;
        if (this.deudaReal == null) this.deudaReal = BigDecimal.ZERO;
        if (this.deudaTotal == null) this.deudaTotal = BigDecimal.ZERO;
        if (this.idPersona == null) this.idPersona = 0L;
        if (this.periodoDeuda == null) this.periodoDeuda = 0L;
        if (this.usuarioTraspaso == null) this.usuarioTraspaso = "";
        if (this.fechaAsignacionAccion == null) this.fechaAsignacionAccion = 0L;
        if (this.clasificacion == null) this.clasificacion = "";
        if (this.actividades == null) this.actividades = new ArrayList<>();
        if (this.prescribir == null) this.prescribir = "";
    }

    public String[] toListString() {
        this.limpiarNulos();

        String periodoDeudaFormateado = this.periodoDeuda != null ? new SimpleDateFormat("yyyy-MM").format(new Date(periodoDeuda)) : "0";
        String deudaPresuntaFormateada = this.deudaPresunta != null ? this.deudaPresunta.setScale(0, BigDecimal.ROUND_HALF_UP).toString() : BigDecimal.ZERO.toString();

        String[] data = new String[] {
            this.personaDTO.getTipoIdentificacion() != null ? this.personaDTO.getTipoIdentificacion().name() : "",
            this.personaDTO.getNumeroIdentificacion() != null ? this.personaDTO.getNumeroIdentificacion() : "",
            this.personaDTO.getRazonSocial() != null ? this.personaDTO.getRazonSocial() : "",
            this.estadoCartera != null ? this.estadoCartera.name() : "",
            periodoDeudaFormateado,
            "Deuda Parcial",
            deudaPresuntaFormateada,
            this.tipoAccionCobro != null ? this.tipoAccionCobro.name() : ""
        };
        return data;
    }

    /**
     * Obtiene el valor de idCartera
     *
     * @return El valor de idCartera
     */
    public Long getIdCartera() {
        return idCartera;
    }

    /**
     * Establece el valor de idCartera
     *
     * @param idCartera El valor de idCartera por asignar
     */
    public void setIdCartera(Long idCartera) {
        this.idCartera = idCartera;
    }

    /**
     * Obtiene el valor de deudaPresunta
     *
     * @return El valor de deudaPresunta
     */
    public BigDecimal getDeudaPresunta() {
        return deudaPresunta;
    }

    /**
     * Establece el valor de deudaPresunta
     *
     * @param deudaPresunta El valor de deudaPresunta por asignar
     */
    public void setDeudaPresunta(BigDecimal deudaPresunta) {
        this.deudaPresunta = deudaPresunta;
    }

    /**
     * Obtiene el valor de deudaReal
     *
     * @return El valor de deudaReal
     */
    public BigDecimal getDeudaReal() {
        return deudaReal;
    }

    /**
     * Establece el valor de deudaReal
     *
     * @param deudaReal El valor de deudaReal por asignar
     */
    public void setDeudaReal(BigDecimal deudaReal) {
        this.deudaReal = deudaReal;
    }

    /**
     * Obtiene el valor de estadoCartera
     *
     * @return El valor de estadoCartera
     */
    public EstadoCarteraEnum getEstadoCartera() {
        return estadoCartera;
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
     * Obtiene el valor de estadoOperacion
     *
     * @return El valor de estadoOperacion
     */
    public EstadoOperacionCarteraEnum getEstadoOperacion() {
        return estadoOperacion;
    }

    /**
     * Establece el valor de estadoOperacion
     *
     * @param estadoOperacion El valor de estadoOperacion por asignar
     */
    public void setEstadoOperacion(EstadoOperacionCarteraEnum estadoOperacion) {
        this.estadoOperacion = estadoOperacion;
    }

    /**
     * Obtiene el valor de fechaCreacion
     *
     * @return El valor de fechaCreacion
     */
    public Long getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Establece el valor de fechaCreacion
     *
     * @param fechaCreacion El valor de fechaCreacion por asignar
     */
    public void setFechaCreacion(Long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Obtiene el valor de idPersona
     *
     * @return El valor de idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * Establece el valor de idPersona
     *
     * @param idPersona El valor de idPersona por asignar
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * Obtiene el valor de metodo
     *
     * @return El valor de metodo
     */
    public MetodoAccionCobroEnum getMetodo() {
        return metodo;
    }

    /**
     * Establece el valor de metodo
     *
     * @param metodo El valor de metodo por asignar
     */
    public void setMetodo(MetodoAccionCobroEnum metodo) {
        this.metodo = metodo;
    }

    /**
     * Obtiene el valor de periodoDeuda
     *
     * @return El valor de periodoDeuda
     */
    public Long getPeriodoDeuda() {
        return periodoDeuda;
    }

    /**
     * Establece el valor de periodoDeuda
     *
     * @param periodoDeuda El valor de periodoDeuda por asignar
     */
    public void setPeriodoDeuda(Long periodoDeuda) {
        this.periodoDeuda = periodoDeuda;
    }

    /**
     * Obtiene el valor de riesgoIncobrabilidad
     *
     * @return El valor de riesgoIncobrabilidad
     */
    public RiesgoIncobrabilidadEnum getRiesgoIncobrabilidad() {
        return riesgoIncobrabilidad;
    }

    /**
     * Establece el valor de riesgoIncobrabilidad
     *
     * @param riesgoIncobrabilidad El valor de riesgoIncobrabilidad por asignar
     */
    public void setRiesgoIncobrabilidad(RiesgoIncobrabilidadEnum riesgoIncobrabilidad) {
        this.riesgoIncobrabilidad = riesgoIncobrabilidad;
    }

    /**
     * Obtiene el valor de tipoAccionCobro
     *
     * @return El valor de tipoAccionCobro
     */
    public TipoAccionCobroEnum getTipoAccionCobro() {
        return tipoAccionCobro;
    }

    /**
     * Establece el valor de tipoAccionCobro
     *
     * @param tipoAccionCobro El valor de tipoAccionCobro por asignar
     */
    public void setTipoAccionCobro(TipoAccionCobroEnum tipoAccionCobro) {
        this.tipoAccionCobro = tipoAccionCobro;
    }

    /**
     * Obtiene el valor de tipoDeuda
     *
     * @return El valor de tipoDeuda
     */
    public TipoDeudaEnum getTipoDeuda() {
        return tipoDeuda;
    }

    /**
     * Establece el valor de tipoDeuda
     *
     * @param tipoDeuda El valor de tipoDeuda por asignar
     */
    public void setTipoDeuda(TipoDeudaEnum tipoDeuda) {
        this.tipoDeuda = tipoDeuda;
    }

    /**
     * Obtiene el valor de tipoLineaCobro
     *
     * @return El valor de tipoLineaCobro
     */
    public TipoLineaCobroEnum getTipoLineaCobro() {
        return tipoLineaCobro;
    }

    /**
     * Establece el valor de tipoLineaCobro
     *
     * @param tipoLineaCobro El valor de tipoLineaCobro por asignar
     */
    public void setTipoLineaCobro(TipoLineaCobroEnum tipoLineaCobro) {
        this.tipoLineaCobro = tipoLineaCobro;
    }

    /**
     * Obtiene el valor de tipoSolicitante
     *
     * @return El valor de tipoSolicitante
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * Establece el valor de tipoSolicitante
     *
     * @param tipoSolicitante El valor de tipoSolicitante por asignar
     */
    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * Obtiene el valor de personaDTO
     *
     * @return El valor de personaDTO
     */
    public PersonaModeloDTO getPersonaDTO() {
        return personaDTO;
    }

    /**
     * Establece el valor de personaDTO
     *
     * @param personaDTO El valor de personaDTO por asignar
     */
    public void setPersonaDTO(PersonaModeloDTO personaDTO) {
        this.personaDTO = personaDTO;
    }

    /**
     * Obtiene el valor de usuarioTraspaso
     *
     * @return El valor de usuarioTraspaso
     */
    public String getUsuarioTraspaso() {
        return usuarioTraspaso;
    }

    /**
     * Establece el valor de usuarioTraspaso
     *
     * @param usuarioTraspaso El valor de usuarioTraspaso por asignar
     */
    public void setUsuarioTraspaso(String usuarioTraspaso) {
        this.usuarioTraspaso = usuarioTraspaso;
    }

    /**
     * Obtiene el valor de fechaAsignacionAccion
     *
     * @return El valor de fechaAsignacionAccion
     */
    public Long getFechaAsignacionAccion() {
        return fechaAsignacionAccion;
    }

    /**
     * Establece el valor de fechaAsignacionAccion
     *
     * @param fechaAsignacionAccion El valor de fechaAsignacionAccion por asignar
     */
    public void setFechaAsignacionAccion(Long fechaAsignacionAccion) {
        this.fechaAsignacionAccion = fechaAsignacionAccion;
    }

    /**
     * Obtiene el valor de estadoSolicitudActiva
     *
     * @return El valor de estadoSolicitudActiva
     */
    public EstadoSolicitudGestionCobroEnum getEstadoSolicitudActiva() {
        return estadoSolicitudActiva;
    }

    /**
     * Establece el valor de estadoSolicitudActiva
     *
     * @param estadoSolicitudActiva El valor de estadoSolicitudActiva por asignar
     */
    public void setEstadoSolicitudActiva(EstadoSolicitudGestionCobroEnum estadoSolicitudActiva) {
        this.estadoSolicitudActiva = estadoSolicitudActiva;
    }

    /**
     * @return the deudaTotal
     */
    public BigDecimal getDeudaTotal() {
        return deudaTotal;
    }

    /**
     * @param deudaTotal the deudaTotal to set
     */
    public void setDeudaTotal(BigDecimal deudaTotal) {
        this.deudaTotal = deudaTotal;
    }

    /**
     * @return the identificadorCartera
     */
    public Long getIdentificadorCartera() {
        return identificadorCartera;
    }

    /**
     * @param identificadorCartera the identificadorCartera to set
     */
    public void setIdentificadorCartera(Long identificadorCartera) {
        this.identificadorCartera = identificadorCartera;
    }


    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public List<String> getActividades() {
        return actividades;
    }

    public void setActividades(List<String> actividades) {
        this.actividades = actividades;
    }

    public DeudaParcialEnum getDeudaParcial() {
        return deudaParcial;
    }

    public void setDeudaParcial(DeudaParcialEnum deudaParcial) {
        this.deudaParcial = deudaParcial;
    }

    public String getPrescribir() {
        return prescribir;
    }

    public void setPrescribir(String prescribir) {
        this.prescribir = prescribir;
    }

    @Override
    public String toString() {
        return "{" +
            " idCartera='" + getIdCartera() + "'" +
            ", identificadorCartera='" + getIdentificadorCartera() + "'" +
            ", deudaPresunta='" + getDeudaPresunta() + "'" +
            ", deudaReal='" + getDeudaReal() + "'" +
            ", deudaTotal='" + getDeudaTotal() + "'" +
            ", estadoCartera='" + getEstadoCartera() + "'" +
            ", estadoOperacion='" + getEstadoOperacion() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", idPersona='" + getIdPersona() + "'" +
            ", metodo='" + getMetodo() + "'" +
            ", periodoDeuda='" + getPeriodoDeuda() + "'" +
            ", riesgoIncobrabilidad='" + getRiesgoIncobrabilidad() + "'" +
            ", tipoAccionCobro='" + getTipoAccionCobro() + "'" +
            ", tipoDeuda='" + getTipoDeuda() + "'" +
            ", tipoLineaCobro='" + getTipoLineaCobro() + "'" +
            ", tipoSolicitante='" + getTipoSolicitante() + "'" +
            ", personaDTO='" + getPersonaDTO() + "'" +
            ", usuarioTraspaso='" + getUsuarioTraspaso() + "'" +
            ", fechaAsignacionAccion='" + getFechaAsignacionAccion() + "'" +
            ", estadoSolicitudActiva='" + getEstadoSolicitudActiva() + "'" +
            ", clasificacion='" + getClasificacion() + "'" +
            ", actividades='" + getActividades() + "'" +
            ", deudaParcial='" + getDeudaParcial() + "'" +
            ", prescribir='" + getPrescribir() + "'" +
            "}";
    }

}
