package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.PagoPeriodoConvenioModeloDTO;
import com.asopagos.entidades.ccf.cartera.ConvenioPago;
import com.asopagos.entidades.ccf.cartera.ExclusionCartera;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.aportes.EstadoAportanteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.cartera.EstadoConvenioPagoEnum;
import com.asopagos.enumeraciones.cartera.EstadoExclusionCarteraEnum;
import com.asopagos.enumeraciones.cartera.ResultadoAprobacionSolicitanteCarteraEnum;
import com.asopagos.enumeraciones.cartera.TipoExclusionCarteraEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO que contiene los datos de la gestión de exclusion para cartera
 * @author Julian Andres Sanchez Bedoya <jusanchez@heinsohn.com.co>
 * @updated 29-sept.-2017 2:35:50 p.m.
 */
@XmlRootElement
public class ExclusionCarteraDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 4952038546317307261L;
    /**
     * Identificador de la exclusion de cartera
     */
    private Long idExclusionCartera;
    /**
     * Estado de la exclusion de cartera
     */
    private EstadoExclusionCarteraEnum estadoExclusionCartera;
    /**
     * Fecha de inico
     */
    private Long fechaInicio;
    /**
     * Fecha de finalizacion
     */
    private Long fechaFinalizacion;
    /**
     * Fecha de registro
     */
    private Long fechaRegistro;
    /**
     * Comentario de las Observacion
     */
    private String observacion;
    /**
     * Tipo de exclusion de cartera
     */
    private TipoExclusionCarteraEnum tipoExclusionCartera;
    /**
     * Estado antes de exclusion
     */
    private EstadoAportanteEnum estadoAntesExclusion;
    /**
     * Número de operacion de mora
     */
    private Long numeroOperacionMora;
    /**
     * Usuario que registra la exclusion
     */
    private String usuarioRegistro;
    /**
     * Tipo de solicitante
     */
    private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
    /**
     * Observacion de cambio de resultado
     */
    private String observacionCambioResultado;
    /**
     * Identificador de la persona
     */
    private Long idPersona;
    /**
     * Tipo de identificación
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    /**
     * Número de identificación
     */
    private String numeroIdentificacion;
    /**
     * Nombre de la razón social
     */
    private String razonSocial;
    /**
     * Descripción del primer nombre
     */
    private String primerNombre;
    /**
     * Descripción del segundo nombre
     */
    private String segundoNombre;

    /**
     * Descripción del primer apellido
     */
    private String primerApellido;

    /**
     * Descripción del segundo apellido
     */
    private String segundoApellido;
    /**
     * Tipo de exclusiones
     */
    private List<TipoExclusionCarteraEnum> tiposExclusion;
    /**
     * Estado del empleador respecto a la caja de compensacion
     */
    private EstadoEmpleadorEnum estadoEmpleadorCaja;
    /**
     * Estado del afiliado respecto a la caja de compensacion
     */
    private EstadoAfiliadoEnum estadoAfiliadoRespectoCaja;
    /**
     * Estado de cartera
     */
    private EstadoCarteraEnum estadoCartera;
    /**
     * Resultado de Aprobación del solicitante
     */
    private ResultadoAprobacionSolicitanteCarteraEnum resultadoAprobacionSolicitante;
    /**
     * Listado de periodos de exclusion de mora
     */
    private List<PeriodoExclusionMoraDTO> periodosExclusionMora;
    /**
     * Pagos Periodo Convenio
     */
    private List<PagoPeriodoConvenioModeloDTO> periodoConvenioPago;
    /**
     * Fecha de Movimiento de la exclusion de cartera
     */
    private Long fechaMovimiento;
    /**
     * Periodo de deuda de la entidad
     */
    private Long periodoDeuda;

    /**
     * 
     */
    public ExclusionCarteraDTO() {
    }

    /**
     * 
     * @param persona
     * @param tipoExclusionCartera
     * @param tipoSolicitante
     */
    public ExclusionCarteraDTO(Persona persona, TipoExclusionCarteraEnum tipoExclusionCartera,
            TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        super();
        this.tipoIdentificacion = persona.getTipoIdentificacion();
        this.numeroIdentificacion = persona.getNumeroIdentificacion();
        this.razonSocial = persona.getRazonSocial();
        this.tipoSolicitante = tipoSolicitante;
        this.tipoExclusionCartera = tipoExclusionCartera;
    }

    public ExclusionCarteraDTO(Persona persona, ExclusionCartera exclusionCartera, TipoExclusionCarteraEnum tipoExclusionPago,
            TipoExclusionCarteraEnum tipoImposicionRecurso, TipoExclusionCarteraEnum tipoAclaracionMora, TipoExclusionCarteraEnum tipoRiesgoIncobrabilidad) {
        super();
        completarDatosExclusionCartera(persona, tipoExclusionPago, tipoImposicionRecurso,tipoAclaracionMora,
                tipoRiesgoIncobrabilidad,null);
        //Datos asociados a exclusion de Cartera
        this.idExclusionCartera = exclusionCartera.getIdExclusionCartera();
        this.estadoExclusionCartera = exclusionCartera.getEstadoExclusionCartera();
        if (exclusionCartera.getFechaInicio() != null) {
            this.fechaInicio = exclusionCartera.getFechaInicio().getTime();
        }
        if (exclusionCartera.getFechaFinalizacion() != null) {
            this.fechaFinalizacion = exclusionCartera.getFechaFinalizacion().getTime();
        }
        if (exclusionCartera.getFechaRegistro() != null) {
            this.fechaRegistro = exclusionCartera.getFechaRegistro().getTime();
        }
        this.observacion = exclusionCartera.getObservacion();
        this.idPersona = persona.getIdPersona();
        this.tipoExclusionCartera = exclusionCartera.getTipoExclusionCartera();
        this.estadoAntesExclusion = exclusionCartera.getEstadoAntesExclusion();
        this.numeroOperacionMora = exclusionCartera.getNumeroOperacionMora();
        this.usuarioRegistro = exclusionCartera.getUsuarioRegistro();
        this.tipoSolicitante = exclusionCartera.getTipoSolicitante();
        this.observacionCambioResultado = exclusionCartera.getObservacionCambioResultado();
        this.resultadoAprobacionSolicitante = exclusionCartera.getResultado();
        if (exclusionCartera.getFechaMovimiento() != null) {
            this.fechaMovimiento = exclusionCartera.getFechaMovimiento().getTime();
        }
    }

    /**
     * Método encargado de construir una exclusion de cartera para un empleador
     * @param empleador,
     *        Empleador a construir la exclusion de cartera
     * @param tipoExclusionPago,tipo
     *        de exclusion EXCLUSION_NEGOCIO
     * @param tipoImposicionRecurso,tipo
     *        de exclusion IMPOSICION_RECURSO
     * @param tipoConvenioPago,tipo
     *        de exclusion CONVENIO_PAGO
     * @param tipoAclaracionMora,tipo
     *        de exclusion ACLARACION_MORA
     * @param tipoRiesgoIncobrabilidad,tipo
     *        de exclusion RIESGO_INCOBRABILIDAD
     */
    public ExclusionCarteraDTO(Empleador empleador, TipoExclusionCarteraEnum tipoExclusionPago,
            TipoExclusionCarteraEnum tipoImposicionRecurso, TipoExclusionCarteraEnum tipoAclaracionMora,
            TipoExclusionCarteraEnum tipoRiesgoIncobrabilidad,ConvenioPago convenioPago) {
        if (empleador != null) {
            Empresa empresa = empleador.getEmpresa();
            if (empresa != null) {
                Persona persona = empresa.getPersona();
                if (persona != null) {
                    completarDatosExclusionCartera(persona, tipoExclusionPago, tipoImposicionRecurso, tipoAclaracionMora,
                            tipoRiesgoIncobrabilidad,convenioPago);
                    //Datos asociados al empleador 
                    this.estadoEmpleadorCaja = empleador.getEstadoEmpleador();
                    this.tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.EMPLEADOR;
                }
            }
        }
    }

    /**
     * Método encargado de construir una exclusion de cartera para un afiliado
     * @param rolAfiliado
     * @param tipoExclusionPago
     * @param tipoImposicionRecurso
     * @param tipoConvenioPago
     * @param tipoAclaracionMora
     * @param tipoRiesgoIncobrabilidad
     */
    public ExclusionCarteraDTO(RolAfiliado rolAfiliado, TipoExclusionCarteraEnum tipoExclusionPago,
            TipoExclusionCarteraEnum tipoImposicionRecurso,TipoExclusionCarteraEnum tipoAclaracionMora, TipoExclusionCarteraEnum tipoRiesgoIncobrabilidad,ConvenioPago convenioPago) {
        if (rolAfiliado != null) {
            Afiliado afiliado = rolAfiliado.getAfiliado();
            if (afiliado != null) {
                Persona persona = afiliado.getPersona();
                if (persona != null) {
                    completarDatosExclusionCartera(persona, tipoExclusionPago, tipoImposicionRecurso, tipoAclaracionMora,
                            tipoRiesgoIncobrabilidad,convenioPago);
                    //Datos asociados al empleador 
                    this.estadoAfiliadoRespectoCaja = rolAfiliado.getEstadoAfiliado();
                    if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(rolAfiliado.getTipoAfiliado())) {
                        this.tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE;
                    }
                    if (TipoAfiliadoEnum.PENSIONADO.equals(rolAfiliado.getTipoAfiliado())) {
                        this.tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.PENSIONADO;
                    }
                }
            }
        }
    }

    /**
     * Método construtor de exclusionCarteraDTO
     * @param exclusionCartera
     * @param rolAfiliado
     * @param empleador
     */
    public ExclusionCarteraDTO(ExclusionCartera exclusionCartera) {
        //Datos asociados a exclusion de Cartera
        this.idExclusionCartera = exclusionCartera.getIdExclusionCartera();
        this.estadoExclusionCartera = exclusionCartera.getEstadoExclusionCartera();
        if (exclusionCartera.getFechaInicio() != null) {
            this.fechaInicio = exclusionCartera.getFechaInicio().getTime();
        }
        if (exclusionCartera.getFechaFinalizacion() != null) {
            this.fechaFinalizacion = exclusionCartera.getFechaFinalizacion().getTime();
        }
        if (exclusionCartera.getFechaRegistro() != null) {
            this.fechaRegistro = exclusionCartera.getFechaRegistro().getTime();
        }
        this.observacion = exclusionCartera.getObservacion();
        this.tipoExclusionCartera = exclusionCartera.getTipoExclusionCartera();
        this.estadoAntesExclusion = exclusionCartera.getEstadoAntesExclusion();
        this.numeroOperacionMora = exclusionCartera.getNumeroOperacionMora();
        this.usuarioRegistro = exclusionCartera.getUsuarioRegistro();
        this.tipoSolicitante = exclusionCartera.getTipoSolicitante();
        this.observacionCambioResultado = exclusionCartera.getObservacionCambioResultado();
        this.resultadoAprobacionSolicitante = exclusionCartera.getResultado();
        if (exclusionCartera.getFechaMovimiento() != null) {
            this.fechaMovimiento = exclusionCartera.getFechaMovimiento().getTime();
        }
    }
    
    /** Constructor
     * @param exclusionCartera Datos de la exclusión
     * @param persona Información de la persona
     */
    public ExclusionCarteraDTO(ExclusionCartera exclusionCartera, Persona persona) {
    	this.idPersona = persona.getIdPersona();
    	this.tipoIdentificacion = persona.getTipoIdentificacion();
        this.numeroIdentificacion = persona.getNumeroIdentificacion();
        this.primerNombre = persona.getPrimerNombre();
        this.segundoNombre = persona.getSegundoNombre();
        this.primerApellido = persona.getPrimerApellido();
        this.segundoApellido = persona.getSegundoApellido();
        this.razonSocial = persona.getRazonSocial();
        this.idExclusionCartera = exclusionCartera.getIdExclusionCartera();
        this.estadoExclusionCartera = exclusionCartera.getEstadoExclusionCartera();
        if (exclusionCartera.getFechaInicio() != null) {
            this.fechaInicio = exclusionCartera.getFechaInicio().getTime();
        }
        if (exclusionCartera.getFechaFinalizacion() != null) {
            this.fechaFinalizacion = exclusionCartera.getFechaFinalizacion().getTime();
        }
        if (exclusionCartera.getFechaRegistro() != null) {
            this.fechaRegistro = exclusionCartera.getFechaRegistro().getTime();
        }
        this.observacion = exclusionCartera.getObservacion();
        this.tipoExclusionCartera = exclusionCartera.getTipoExclusionCartera();
        this.estadoAntesExclusion = exclusionCartera.getEstadoAntesExclusion();
        this.numeroOperacionMora = exclusionCartera.getNumeroOperacionMora();
        this.usuarioRegistro = exclusionCartera.getUsuarioRegistro();
        this.tipoSolicitante = exclusionCartera.getTipoSolicitante();
        this.observacionCambioResultado = exclusionCartera.getObservacionCambioResultado();
        this.resultadoAprobacionSolicitante = exclusionCartera.getResultado();
        if (exclusionCartera.getFechaMovimiento() != null) {
            this.fechaMovimiento = exclusionCartera.getFechaMovimiento().getTime();
        }
    }

    /**
     * Método encargado de completar los datos de exclusion
     * @param persona
     * @param tipoExclusionPago
     * @param tipoImposicionRecurso
     * @param tipoConvenioPago
     * @param tipoAclaracionMora
     * @param tipoRiesgoIncobrabilidad
     */
    private void completarDatosExclusionCartera(Persona persona, TipoExclusionCarteraEnum tipoExclusionPago,
            TipoExclusionCarteraEnum tipoImposicionRecurso, TipoExclusionCarteraEnum tipoAclaracionMora,
            TipoExclusionCarteraEnum tipoRiesgoIncobrabilidad,ConvenioPago convenioPago) {
        //Datos asocaidos a las persona  
        this.idPersona = persona.getIdPersona();
        this.tipoIdentificacion = persona.getTipoIdentificacion();
        this.numeroIdentificacion = persona.getNumeroIdentificacion();
        this.primerNombre = persona.getPrimerNombre();
        this.segundoNombre = persona.getSegundoNombre();
        this.primerApellido = persona.getPrimerApellido();
        this.segundoApellido = persona.getSegundoApellido();
        this.razonSocial = persona.getRazonSocial();
        //Datos asociados a la exclusion de cartera
        this.tiposExclusion = new ArrayList<>();
        if (tipoExclusionPago != null) {
            this.tiposExclusion.add(tipoExclusionPago);
        }
        if (tipoImposicionRecurso != null) {
            this.tiposExclusion.add(tipoImposicionRecurso);
        }
        if (tipoAclaracionMora != null) {
            this.tiposExclusion.add(tipoAclaracionMora);
        }
        if (tipoRiesgoIncobrabilidad != null) {
            this.tiposExclusion.add(tipoRiesgoIncobrabilidad);
        }
        if(convenioPago!=null){
            this.tiposExclusion.add(TipoExclusionCarteraEnum.CONVENIO_PAGO);
        }
        //Estado de cartera enum 
        //TODO Modificar el estado de cartera 
        this.estadoCartera = EstadoCarteraEnum.MOROSO;
    }
    
    /**
     * Método encargado de asociar los datos del dto a la entidad
     * @return ExclusionCartera
     */
    public ExclusionCartera convertToExclusionCarteraEntity() {
        ExclusionCartera exclusionCartera = new ExclusionCartera();
        exclusionCartera.setEstadoExclusionCartera(this.getEstadoExclusionCartera());
        if (this.getFechaInicio() != null) {
            exclusionCartera.setFechaInicio(new Date(this.getFechaInicio()));
        }
        if (this.getFechaFinalizacion() != null) {
            exclusionCartera.setFechaFinalizacion(new Date(this.getFechaFinalizacion()));
        }
        if (this.getFechaRegistro() != null) {
            exclusionCartera.setFechaRegistro(new Date(this.getFechaRegistro()));
        }
        exclusionCartera.setObservacion(this.getObservacion());
        exclusionCartera.setIdPersona(this.getIdPersona());
        exclusionCartera.setTipoExclusionCartera(this.getTipoExclusionCartera());
        exclusionCartera.setEstadoAntesExclusion(this.getEstadoAntesExclusion());
        exclusionCartera.setNumeroOperacionMora(this.getNumeroOperacionMora());
        exclusionCartera.setUsuarioRegistro(this.getUsuarioRegistro());
        exclusionCartera.setTipoSolicitante(this.getTipoSolicitante());
        exclusionCartera.setObservacionCambioResultado(this.getObservacionCambioResultado());
        exclusionCartera.setResultado(this.getResultadoAprobacionSolicitante());
        if (this.getFechaMovimiento() != null) {
            exclusionCartera.setFechaMovimiento(new Date(this.getFechaMovimiento()));
        }
        if (this.getPeriodoDeuda() != null) {
            exclusionCartera.setPeriodoDeuda(new Date(this.getPeriodoDeuda()));
        }
        return exclusionCartera;
    }

    /**
     * Método encargado de convertir una entidad a un dto
     * @param exclusionCartera,
     *        exclusionCartera entidad
     * @return retorna ExclusionCarteraDTO
     */
    public ExclusionCarteraDTO convertEntityToDTO(ExclusionCartera exclusionCartera) {
        ExclusionCarteraDTO exclusionCarteraDTO = new ExclusionCarteraDTO();
        exclusionCarteraDTO.setIdExclusionCartera(exclusionCartera.getIdExclusionCartera());
        exclusionCarteraDTO.setEstadoExclusionCartera(exclusionCartera.getEstadoExclusionCartera());
        if (exclusionCartera.getFechaInicio() != null) {
            exclusionCarteraDTO.setFechaInicio(exclusionCartera.getFechaInicio().getTime());
        }
        if (exclusionCartera.getFechaFinalizacion() != null) {
            exclusionCarteraDTO.setFechaFinalizacion(exclusionCartera.getFechaFinalizacion().getTime());
        }
        if (exclusionCartera.getFechaRegistro() != null) {
            exclusionCarteraDTO.setFechaRegistro(exclusionCartera.getFechaRegistro().getTime());
        }

        exclusionCarteraDTO.setObservacion(exclusionCartera.getObservacion());
        exclusionCarteraDTO.setIdPersona(exclusionCartera.getIdPersona());
        exclusionCarteraDTO.setTipoExclusionCartera(exclusionCartera.getTipoExclusionCartera());
        exclusionCarteraDTO.setEstadoAntesExclusion(exclusionCartera.getEstadoAntesExclusion());
        exclusionCarteraDTO.setNumeroOperacionMora(exclusionCartera.getNumeroOperacionMora());
        exclusionCarteraDTO.setUsuarioRegistro(exclusionCartera.getUsuarioRegistro());
        exclusionCarteraDTO.setTipoSolicitante(exclusionCartera.getTipoSolicitante());
        exclusionCarteraDTO.setObservacionCambioResultado(exclusionCartera.getObservacionCambioResultado());
        exclusionCarteraDTO.setResultadoAprobacionSolicitante(exclusionCartera.getResultado());
        if (this.getFechaMovimiento() != null) {
            exclusionCarteraDTO.setFechaMovimiento(exclusionCartera.getFechaMovimiento().getTime());
        }
        if (this.getPeriodoDeuda() != null) {
            exclusionCarteraDTO.setPeriodoDeuda(exclusionCartera.getPeriodoDeuda().getTime());
        }
        return exclusionCarteraDTO;
    }

    /**
     * Método encargado de asociar los datos del dto a la entidad
     * @param exclusionCartera
     * @param exclusionCarteraDTO
     * @return retorna una exclusion de cartera
     */
    public ExclusionCartera convertToExclusionEntityToDto(ExclusionCartera exclusionCartera, ExclusionCarteraDTO exclusionCarteraDTO) {
        if (exclusionCarteraDTO.getIdExclusionCartera() != null) {
            exclusionCartera.setIdExclusionCartera(exclusionCarteraDTO.getIdExclusionCartera());
        }
        if (exclusionCarteraDTO.getEstadoExclusionCartera() != null) {
            exclusionCartera.setEstadoExclusionCartera(exclusionCarteraDTO.getEstadoExclusionCartera());
        }
        if (exclusionCarteraDTO.getFechaInicio() != null) {
            exclusionCartera.setFechaInicio(new Date(exclusionCarteraDTO.getFechaInicio()));
        }
        if (exclusionCarteraDTO.getFechaFinalizacion() != null) {
            exclusionCartera.setFechaFinalizacion(new Date(exclusionCarteraDTO.getFechaFinalizacion()));
        }
        if (exclusionCarteraDTO.getFechaRegistro() != null) {
            exclusionCartera.setFechaRegistro(new Date(exclusionCarteraDTO.getFechaRegistro()));
        }
        if (exclusionCarteraDTO.getObservacion() != null) {
            exclusionCartera.setObservacion(exclusionCarteraDTO.getObservacion());
        }
        if (exclusionCarteraDTO.getIdPersona() != null) {
            exclusionCartera.setIdPersona(exclusionCarteraDTO.getIdPersona());
        }
        if (exclusionCarteraDTO.getTipoExclusionCartera() != null) {
            exclusionCartera.setTipoExclusionCartera(exclusionCarteraDTO.getTipoExclusionCartera());
        }
        if (exclusionCarteraDTO.getEstadoAntesExclusion() != null) {
            exclusionCartera.setEstadoAntesExclusion(exclusionCarteraDTO.getEstadoAntesExclusion());
        }
        if (exclusionCarteraDTO.getNumeroOperacionMora() != null) {
            exclusionCartera.setNumeroOperacionMora(exclusionCarteraDTO.getNumeroOperacionMora());
        }
        if (exclusionCarteraDTO.getUsuarioRegistro() != null) {
            exclusionCartera.setUsuarioRegistro(exclusionCarteraDTO.getUsuarioRegistro());
        }
        if (exclusionCarteraDTO.getTipoSolicitante() != null) {
            exclusionCartera.setTipoSolicitante(exclusionCarteraDTO.getTipoSolicitante());
        }
        if (exclusionCarteraDTO.getObservacionCambioResultado() != null) {
            exclusionCartera.setObservacionCambioResultado(exclusionCarteraDTO.getObservacionCambioResultado());
        }
        if (exclusionCarteraDTO.getResultadoAprobacionSolicitante() != null) {
            exclusionCartera.setResultado(exclusionCarteraDTO.getResultadoAprobacionSolicitante());
        }
        if (exclusionCarteraDTO.getPeriodoDeuda() != null) {
            exclusionCartera.setPeriodoDeuda(new Date(exclusionCarteraDTO.getPeriodoDeuda()));
        }
        return exclusionCartera;
    }

    public ExclusionCarteraDTO(RolAfiliado rolAfiliado, List<TipoExclusionCarteraEnum> tiposExclusionCartera) {
        if (rolAfiliado != null) {
            Afiliado afiliado = rolAfiliado.getAfiliado();
            if (afiliado != null) {
                Persona persona = afiliado.getPersona();
                if (persona != null) {
                    this.tiposExclusion = tiposExclusionCartera;
                    //Datos asociados al empleador 
                    this.estadoAfiliadoRespectoCaja = rolAfiliado.getEstadoAfiliado();
                    if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(rolAfiliado.getTipoAfiliado())) {
                        this.tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE;
                    }
                    if (TipoAfiliadoEnum.PENSIONADO.equals(rolAfiliado.getTipoAfiliado())) {
                        this.tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.PENSIONADO;
                    }
                }
            }
        }
    }
    
    /**
     * 
     * @param persona
     * @param convenioPago
     */
    public ExclusionCarteraDTO(Persona persona, ConvenioPago convenioPago) {
        //Datos asocaidos a las persona  
        this.idPersona = persona.getIdPersona();
        this.tipoIdentificacion = persona.getTipoIdentificacion();
        this.numeroIdentificacion = persona.getNumeroIdentificacion();
        this.primerNombre = persona.getPrimerNombre();
        this.segundoNombre = persona.getSegundoNombre();
        this.primerApellido = persona.getPrimerApellido();
        this.segundoApellido = persona.getSegundoApellido();
        this.razonSocial = persona.getRazonSocial();
        if (convenioPago != null) {
            this.tipoExclusionCartera = TipoExclusionCarteraEnum.CONVENIO_PAGO;
            this.fechaInicio =convenioPago.getFechaRegistro() !=null ? convenioPago.getFechaRegistro().getTime():null;
            this.fechaRegistro =convenioPago.getFechaRegistro() !=null ? convenioPago.getFechaRegistro().getTime():null;
            this.fechaFinalizacion = convenioPago.getFechaAnulacion() !=null ? convenioPago.getFechaAnulacion().getTime() :null;
            this.tipoSolicitante = convenioPago.getTipoSolicitante();
            this.estadoAntesExclusion=EstadoAportanteEnum.ACTIVO;
            if( convenioPago.getEstadoConvenioPago()!=null){
                if(EstadoConvenioPagoEnum.ACTIVO.equals(convenioPago.getEstadoConvenioPago())){
                    this.estadoExclusionCartera =EstadoExclusionCarteraEnum.ACTIVA;
                }else{
                    this.estadoExclusionCartera =EstadoExclusionCarteraEnum.NO_ACTIVA;
                }
               
            }
            
        }
         //Estado de cartera enum 
        //TODO Modificar el estado de cartera 
        this.estadoCartera = EstadoCarteraEnum.MOROSO;
    }

    
    
    /**
     * Método que retorna el valor de idExclusionCartera.
     * @return valor de idExclusionCartera.
     */
    public Long getIdExclusionCartera() {
        return idExclusionCartera;
    }

    /**
     * Método que retorna el valor de estadoExclusionCartera.
     * @return valor de estadoExclusionCartera.
     */
    public EstadoExclusionCarteraEnum getEstadoExclusionCartera() {
        return estadoExclusionCartera;
    }

    /**
     * Método que retorna el valor de fechaInicio.
     * @return valor de fechaInicio.
     */
    public Long getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Método que retorna el valor de fechaFinalizacion.
     * @return valor de fechaFinalizacion.
     */
    public Long getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    /**
     * Método que retorna el valor de fechaRegistro.
     * @return valor de fechaRegistro.
     */
    public Long getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Método que retorna el valor de observacion.
     * @return valor de observacion.
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * Método que retorna el valor de idPersona.
     * @return valor de idPersona.
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * Método que retorna el valor de tipoExclusionCartera.
     * @return valor de tipoExclusionCartera.
     */
    public TipoExclusionCarteraEnum getTipoExclusionCartera() {
        return tipoExclusionCartera;
    }

    /**
     * Método que retorna el valor de estadoAntesExclusion.
     * @return valor de estadoAntesExclusion.
     */
    public EstadoAportanteEnum getEstadoAntesExclusion() {
        return estadoAntesExclusion;
    }

    /**
     * Método que retorna el valor de numeroOperacionMora.
     * @return valor de numeroOperacionMora.
     */
    public Long getNumeroOperacionMora() {
        return numeroOperacionMora;
    }

    /**
     * Método que retorna el valor de usuarioRegistro.
     * @return valor de usuarioRegistro.
     */
    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    /**
     * Método que retorna el valor de tipoSolicitante.
     * @return valor de tipoSolicitante.
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * Método que retorna el valor de observacionCambioResultado.
     * @return valor de observacionCambioResultado.
     */
    public String getObservacionCambioResultado() {
        return observacionCambioResultado;
    }

    /**
     * Método encargado de modificar el valor de idExclusionCartera.
     * @param valor
     *        para modificar idExclusionCartera.
     */
    public void setIdExclusionCartera(Long idExclusionCartera) {
        this.idExclusionCartera = idExclusionCartera;
    }

    /**
     * Método encargado de modificar el valor de estadoExclusionCartera.
     * @param valor
     *        para modificar estadoExclusionCartera.
     */
    public void setEstadoExclusionCartera(EstadoExclusionCarteraEnum estadoExclusionCartera) {
        this.estadoExclusionCartera = estadoExclusionCartera;
    }

    /**
     * Método encargado de modificar el valor de fechaInicio.
     * @param valor
     *        para modificar fechaInicio.
     */
    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Método encargado de modificar el valor de fechaFinalizacion.
     * @param valor
     *        para modificar fechaFinalizacion.
     */
    public void setFechaFinalizacion(Long fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    /**
     * Método encargado de modificar el valor de fechaRegistro.
     * @param valor
     *        para modificar fechaRegistro.
     */
    public void setFechaRegistro(Long fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Método encargado de modificar el valor de observacion.
     * @param valor
     *        para modificar observacion.
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    /**
     * Método encargado de modificar el valor de idPersona.
     * @param valor
     *        para modificar idPersona.
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * Método encargado de modificar el valor de tipoExclusionCartera.
     * @param valor
     *        para modificar tipoExclusionCartera.
     */
    public void setTipoExclusionCartera(TipoExclusionCarteraEnum tipoExclusionCartera) {
        this.tipoExclusionCartera = tipoExclusionCartera;
    }

    /**
     * Método encargado de modificar el valor de estadoAntesExclusion.
     * @param valor
     *        para modificar estadoAntesExclusion.
     */
    public void setEstadoAntesExclusion(EstadoAportanteEnum estadoAntesExclusion) {
        this.estadoAntesExclusion = estadoAntesExclusion;
    }

    /**
     * Método encargado de modificar el valor de numeroOperacionMora.
     * @param valor
     *        para modificar numeroOperacionMora.
     */
    public void setNumeroOperacionMora(Long numeroOperacionMora) {
        this.numeroOperacionMora = numeroOperacionMora;
    }

    /**
     * Método encargado de modificar el valor de usuarioRegistro.
     * @param valor
     *        para modificar usuarioRegistro.
     */
    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    /**
     * Método encargado de modificar el valor de tipoSolicitante.
     * @param valor
     *        para modificar tipoSolicitante.
     */
    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * Método encargado de modificar el valor de observacionCambioResultado.
     * @param valor
     *        para modificar observacionCambioResultado.
     */
    public void setObservacionCambioResultado(String observacionCambioResultado) {
        this.observacionCambioResultado = observacionCambioResultado;
    }

    /**
     * Método que retorna el valor de tipoIdentificacion.
     * @return valor de tipoIdentificacion.
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * Método que retorna el valor de numeroIdentificacion.
     * @return valor de numeroIdentificacion.
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * Método que retorna el valor de razonSocial.
     * @return valor de razonSocial.
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * Método que retorna el valor de primerNombre.
     * @return valor de primerNombre.
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * Método que retorna el valor de segundoNombre.
     * @return valor de segundoNombre.
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * Método que retorna el valor de primerApellido.
     * @return valor de primerApellido.
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * Método que retorna el valor de segundoApellido.
     * @return valor de segundoApellido.
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacion.
     * @param valor
     *        para modificar tipoIdentificacion.
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacion.
     * @param valor
     *        para modificar numeroIdentificacion.
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de razonSocial.
     * @param valor
     *        para modificar razonSocial.
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * Método encargado de modificar el valor de primerNombre.
     * @param valor
     *        para modificar primerNombre.
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * Método encargado de modificar el valor de segundoNombre.
     * @param valor
     *        para modificar segundoNombre.
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * Método encargado de modificar el valor de primerApellido.
     * @param valor
     *        para modificar primerApellido.
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * Método encargado de modificar el valor de segundoApellido.
     * @param valor
     *        para modificar segundoApellido.
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * Método que retorna el valor de tiposExclusion.
     * @return valor de tiposExclusion.
     */
    public List<TipoExclusionCarteraEnum> getTiposExclusion() {
        return tiposExclusion;
    }

    /**
     * Método encargado de modificar el valor de tiposExclusion.
     * @param valor
     *        para modificar tiposExclusion.
     */
    public void setTiposExclusion(List<TipoExclusionCarteraEnum> tiposExclusion) {
        this.tiposExclusion = tiposExclusion;
    }

    /**
     * Método que retorna el valor de estadoEmpleadorCaja.
     * @return valor de estadoEmpleadorCaja.
     */
    public EstadoEmpleadorEnum getEstadoEmpleadorCaja() {
        return estadoEmpleadorCaja;
    }

    /**
     * Método que retorna el valor de estadoAfiliadoRespectoCaja.
     * @return valor de estadoAfiliadoRespectoCaja.
     */
    public EstadoAfiliadoEnum getEstadoAfiliadoRespectoCaja() {
        return estadoAfiliadoRespectoCaja;
    }

    /**
     * Método encargado de modificar el valor de estadoEmpleadorCaja.
     * @param valor
     *        para modificar estadoEmpleadorCaja.
     */
    public void setEstadoEmpleadorCaja(EstadoEmpleadorEnum estadoEmpleadorCaja) {
        this.estadoEmpleadorCaja = estadoEmpleadorCaja;
    }

    /**
     * Método encargado de modificar el valor de estadoAfiliadoRespectoCaja.
     * @param valor
     *        para modificar estadoAfiliadoRespectoCaja.
     */
    public void setEstadoAfiliadoRespectoCaja(EstadoAfiliadoEnum estadoAfiliadoRespectoCaja) {
        this.estadoAfiliadoRespectoCaja = estadoAfiliadoRespectoCaja;
    }

    /**
     * Método que retorna el valor de estadoCartera.
     * @return valor de estadoCartera.
     */
    public EstadoCarteraEnum getEstadoCartera() {
        return estadoCartera;
    }

    /**
     * Método encargado de modificar el valor de estadoCartera.
     * @param valor
     *        para modificar estadoCartera.
     */
    public void setEstadoCartera(EstadoCarteraEnum estadoCartera) {
        this.estadoCartera = estadoCartera;
    }

    /**
     * Método que retorna el valor de resultadoAprobacionSolicitante.
     * @return valor de resultadoAprobacionSolicitante.
     */
    public ResultadoAprobacionSolicitanteCarteraEnum getResultadoAprobacionSolicitante() {
        return resultadoAprobacionSolicitante;
    }

    /**
     * Método encargado de modificar el valor de resultadoAprobacionSolicitante.
     * @param valor
     *        para modificar resultadoAprobacionSolicitante.
     */
    public void setResultadoAprobacionSolicitante(ResultadoAprobacionSolicitanteCarteraEnum resultadoAprobacionSolicitante) {
        this.resultadoAprobacionSolicitante = resultadoAprobacionSolicitante;
    }

    /**
     * Método que retorna el valor de periodosExclusionMora.
     * @return valor de periodosExclusionMora.
     */
    public List<PeriodoExclusionMoraDTO> getPeriodosExclusionMora() {
        return periodosExclusionMora;
    }

    /**
     * Método encargado de modificar el valor de periodosExclusionMora.
     * @param valor
     *        para modificar periodosExclusionMora.
     */
    public void setPeriodosExclusionMora(List<PeriodoExclusionMoraDTO> periodosExclusionMora) {
        this.periodosExclusionMora = periodosExclusionMora;
    }

    /**
     * Método que retorna el valor de periodoConvenioPago.
     * @return valor de periodoConvenioPago.
     */
    public List<PagoPeriodoConvenioModeloDTO> getPeriodoConvenioPago() {
        return periodoConvenioPago;
    }

    /**
     * Método encargado de modificar el valor de periodoConvenioPago.
     * @param valor
     *        para modificar periodoConvenioPago.
     */
    public void setPeriodoConvenioPago(List<PagoPeriodoConvenioModeloDTO> periodoConvenioPago) {
        this.periodoConvenioPago = periodoConvenioPago;
    }

    /**
     * Método que retorna el valor de fechaMovimiento.
     * @return valor de fechaMovimiento.
     */
    public Long getFechaMovimiento() {
        return fechaMovimiento;
    }

    /**
     * Método encargado de modificar el valor de fechaMovimiento.
     * @param valor
     *        para modificar fechaMovimiento.
     */
    public void setFechaMovimiento(Long fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public Long getPeriodoDeuda() {
        return periodoDeuda;
    }

    public void setPeriodoDeuda(Long periodoDeuda) {
        this.periodoDeuda = periodoDeuda;
    }
}
