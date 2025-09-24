package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.asopagos.dto.ListaChequeoDTO;
import com.asopagos.dto.fovis.ResultadoAsignacionDTO;
import com.asopagos.entidades.ccf.fovis.PostulacionFOVIS;
import com.asopagos.entidades.fovis.parametricas.CicloAsignacion;
import com.asopagos.enumeraciones.fovis.CondicionHogarEnum;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;
import com.asopagos.enumeraciones.fovis.MotivoDesistimientoPostulacionEnum;
import com.asopagos.enumeraciones.fovis.MotivoEnajenacionPostulacionEnum;
import com.asopagos.enumeraciones.fovis.MotivoHabilitacionPostulacionEnum;
import com.asopagos.enumeraciones.fovis.MotivoRechazoPostulacionEnum;
import com.asopagos.enumeraciones.fovis.MotivoRestitucionSubsidioEnum;
import com.asopagos.enumeraciones.fovis.PrioridadAsignacionEnum;
import com.asopagos.enumeraciones.fovis.ResultadoAsignacionEnum;
import com.asopagos.enumeraciones.fovis.TiempoSancionPostulacionEnum;
import java.util.List;

/**
 * DTO que representa los datos de la entidad PostulacionFOVIS
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class PostulacionFOVISModeloDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = -3038937381547818905L;

    /**
     * Identificador único, llave primaria
     */
    private Long idPostulacion;

    /**
     * Asociación al ciclo de asignación de la Postulación
     */
    private Long idCicloAsignacion;

    /**
     * Informacion del ciclo de asignacion de la postulacion
     */
    private CicloAsignacionModeloDTO cicloAsignacion;
    
    /**
     * Asociación a la modalidad
     */
    private ModalidadEnum idModalidad;

    /**
     * Asociación al Id del Jefe del Hogar
     */
    private Long idJefeHogar;

    /**
     * Información del Jefe de Hogar
     */
    private JefeHogarModeloDTO jefeHogar;

    /**
     * Lista de chequeo de requisitos documentales para el jefe del hogar
     */
    private ListaChequeoDTO listaChequeoJefeHogar;

    /**
     * Estado del hogar de la Postulación
     */
    private EstadoHogarEnum estadoHogar;

    /**
     * Condición del hogar de la Postulación
     */
    private CondicionHogarEnum condicionHogar;

    /**
     * Hogar Perdió subsidio por Imposibilidad de Pago
     */
    private Boolean hogarPerdioSubsidioNoPago;

    /**
     * Cantidad de folios Postulación.
     */
    private Short cantidadFolios;

    /**
     * Cantidad de folios Postulación.
     */
    private BigDecimal valorSFVSolicitado;

    /**
     * Proyecto de solución de vivienda de la postulación
     */
    private ProyectoSolucionViviendaModeloDTO proyectoSolucionVivienda;

    /**
     * Tabla de ahorroPrevio de ahorro Programado
     */
    private AhorroPrevioModeloDTO ahorroProgramado;

    /**
     * Tabla de ahorroPrevio de ahorro Programado Contractual
     */
    private AhorroPrevioModeloDTO ahorroProgramadoContractual;

    /**
     * Tabla de ahorroPrevio de aportes periódicos
     */
    private AhorroPrevioModeloDTO aportesPeriodicos;

    /**
     * Tabla de ahorroPrevio de cesantías inmovilizadas
     */
    private AhorroPrevioModeloDTO cesantiasInmovilizadas;

    /**
     * Tabla de ahorroPrevio de cuota inicial
     */
    private AhorroPrevioModeloDTO cuotaInicial;

    /**
     * Tabla de ahorroPrevio de cuotasPagadas
     */
    private AhorroPrevioModeloDTO cuotasPagadas;

    /**
     * Tabla de ahorroPrevio de valor Lote o Terreno Propio
     */
    private AhorroPrevioModeloDTO valorLoteTerreno;

    /**
     * Tabla de ahorroPrevio de valor Lote OPV
     */
    private AhorroPrevioModeloDTO valorLoteOPV;

    /**
     * Tabla de ahorroPrevio de Valor Lote Subsidio Municipal
     */
    private AhorroPrevioModeloDTO valorLoteSubsidioMunicipal;

    /**
     * Tabla de recursosComplementarios de ahorro otras modalidades
     */
    private RecursoComplementarioModeloDTO ahorroOtrasModalidades;

    /**
     * Tabla de recursosComplementarios de aportesEnteTerritorial
     */
    private RecursoComplementarioModeloDTO aportesEnteTerritorial;

    /**
     * Tabla de recursosComplementarios de aportesSolidarios
     */
    private RecursoComplementarioModeloDTO aportesSolidarios;

    /**
     * Tabla de recursosComplementarios de cesantiasNoInmovilizadas
     */
    private RecursoComplementarioModeloDTO cesantiasNoInmovilizadas;

    /**
     * Tabla de recursosComplementarios de creditoAprobado
     */
    private RecursoComplementarioModeloDTO creditoAprobado;

    /**
     * Tabla de recursosComplementarios de donacionOtrasEntidades
     */
    private RecursoComplementarioModeloDTO donacionOtrasEntidades;

    /**
     * Tabla de recursosComplementarios de evaluacionCrediticia
     */
    private RecursoComplementarioModeloDTO evaluacionCrediticia;

    /**
     * Tabla de recursosComplementarios de otrosRecursos
     */
    private RecursoComplementarioModeloDTO otrosRecursos;

    /**
     * Tabla de recursosComplementarios de valorAvanceObra
     */
    private RecursoComplementarioModeloDTO valorAvanceObra;

    /**
     * Resultado de asignación del hogar
     */
    private ResultadoAsignacionEnum resultadoAsignacion;

    /**
     * Identificador de la solicitud de asignacion
     */
    private Long idSolicitudAsignacion;

    /**
     * Valor asignado de SFV
     */
    private BigDecimal valorAsignadoSFV;

    /**
     * Puntaje obtenido por la postulacion
     */
    private BigDecimal puntaje;

    /**
     * Fecha y hora en que se realizó la calificación del hogar
     */
    private Long fechaCalificacion;

    /**
     * Prioridad obtenida durante el análisis de asignación del hogar al
     * subsidio
     */
    private PrioridadAsignacionEnum prioridadAsignacion;

    /**
     * Identificador de la carta generada por la asignacion
     */
    private String idDocumento;

    /**
     * Valor calculado de SFV
     */
    private BigDecimal valorCalculadoSFV;

    /**
     * Valor Proyecto de Vivienda.
     */
    private BigDecimal valorProyectoVivienda;

    /**
     * Fecha de publicación
     */
    private Long fechaPublicacion;

    /**
     * Motivo de Desistimiento de la Postulación.
     */
    private MotivoDesistimientoPostulacionEnum motivoDesistimientoPostulacion;

    /**
     * Indica si el tipo de Ahorro Programado sera Mobilizado.
     */
    private Boolean ahorroProgramadoMobilizado;

    /**
     * Indica si el tipo de Ahorro Programado Contractual
     * con Evaluación crediticia favorable previa (FNA) sera Mobilizado.
     */
    private Boolean ahorroEvaluacionCrediticiaMobilizado;

    /**
     * Indica si el tipo de Ahorro Cesantías sera Mobilizado.
     */
    private Boolean cesantiasMovilizado;

    /**
     * Novedad 15: Habilitación de Postulación rechazada
     * Motivo de Habilitación de la Postulación.
     */
    private MotivoHabilitacionPostulacionEnum motivoHabilitacion;

    /**
     * Indica si la Postulación es restituída con Sanción.
     */
    private Boolean restituidoConSancion;

    /**
     * Tiempo de la sanción por Restitución de la Postulación.
     */
    private TiempoSancionPostulacionEnum tiempoSancion;

    /**
     * Motivo de Restitución del Subsidio.
     */
    private MotivoRestitucionSubsidioEnum motivoRestitucion;

    /**
     * Motivo de Enajenación del Hogar
     */
    private MotivoEnajenacionPostulacionEnum motivoEnajenacion;

    /**
     * Valor SFV calculado asociado a la Postulación.
     */
    private BigDecimal valorAjusteIPCSFV;

    /**
     * Motivo de Rechazo de la Postulación. 
     */
    private MotivoRechazoPostulacionEnum motivoRechazo;

    /**
     * Información al momento de la asignación en JSON
     */
    private String informacionAsignacion;

    /**
     * Asociación a la calificación de la postulación
     */
    private Long idCalificacionPostulacion;

    /**
     * Avalúo Catastral del Proyecto o Solución de vivienda
     */
    private BigDecimal avaluoCatastralVivienda;

    /**
     * Información de la parametrización de tope por modalidad al momento de radicar
     */
    private String informacionParametrizacion;

    /**
     * Valor del SMLMV al momento de la asignación
     */
    private BigDecimal valorSalarioAsignacion;

    /**
     * Valor de Subsidio Familiar de Vivienda (SFV) ajustado por decreto 133 2018
     */
    private BigDecimal valorSFVAjustado;
    
    /**
     * Matrícula inmobiliaria inmueble
     */
    private String matriculaInmobiliariaInmueble;

    /**
     * Fecha Registro Escritura del Proyecto o Solución de vivienda
     */
    private Long fechaRegistroEscritura;

    /**
     * Identifica si esta Urbanizado el Proyecto o Solución de vivienda
     */
    private Boolean loteUrbanizado;

    /**
     * Poseedor/Ocupante de vivienda del Proyecto o Solución de vivienda
     */
    private String poseedorOcupanteVivienda;

    /**
     * Número de Escritura del Proyecto o Solución de vivienda
     */
    private String numeroEscritura;

    /**
     * Fecha Registro Escritura del Proyecto o Solución de vivienda
     */
    private Long fechaEscritura;
    
    /**
     * Identifica si la Ubicación de la vivienda es igual a la del proyecto de
     * vivienda.
     */
    private Boolean ubicacionViviendaMismaProyecto;

    /**
     * Asociación de la Ubicación de la Vivienda con el Proyecto de Vivienda
     */
    private UbicacionModeloDTO ubicacionVivienda;

    /**
     * Json con la información mas actualizada de la postulación
     */
    private String informacionPostulacion;
    
    /**
     * Oferente asociado a la postulación
     */
    private OferenteModeloDTO oferente;
    
    
    /**
     * Información del proveedor
     */
    private List<LegalizacionDesembolosoProveedorModeloDTO> legalizacionProveedor;
    
    /**
     * Recurso prioridad de la asignacion
     */
    private String recursoPrioridad;

    /**
     * Fecha validacion cruce interno
     */
    private Date fechaValidacionCruce;

    
    /**
     * Constructor vacio
     */
    public PostulacionFOVISModeloDTO() {
        super();
    }

    /**
     * Constructor que recibe los datos de la entidad.
     * @param postulacionFOVIS
     */
    public PostulacionFOVISModeloDTO(PostulacionFOVIS postulacionFOVIS) {
        convertToDTO(postulacionFOVIS);
    }

    /**
     * Constructor de los datos de entidad y ciclo asingacion
     * @param postulacionFOVIS
     *        Info entidad
     * @param cicloAsignacion
     *        Info ciclo asignacion
     */
    public PostulacionFOVISModeloDTO(PostulacionFOVIS postulacionFOVIS, CicloAsignacion cicloAsignacion) {
        this.convertToDTO(postulacionFOVIS);
        this.setCicloAsignacion(new CicloAsignacionModeloDTO(cicloAsignacion));
    }

    /**
     * Constructor que recibe los datos de la entidad y la fecha de publicacion
     * @param postulacionFOVIS
     *        postulacion fovis
     * @param fechaPublicacion
     *        fecha de publicacion del acta de asignacion
     */
    public PostulacionFOVISModeloDTO(PostulacionFOVIS postulacionFOVIS, Date fechaPublicacion) {
        convertToDTO(postulacionFOVIS);
        this.setFechaPublicacion(fechaPublicacion != null ? fechaPublicacion.getTime() : null);
    }

    /**
     * Asocia los datos del DTO a la Entidad
     * 
     * @return PostulacionFOVIS
     */
    public PostulacionFOVIS convertToEntity() {
        PostulacionFOVIS postulacionFOVIS = new PostulacionFOVIS();
        postulacionFOVIS.setAvaluoCatastralVivienda(this.getAvaluoCatastralVivienda());
        postulacionFOVIS.setCantidadFolios(this.getCantidadFolios());
        postulacionFOVIS.setCondicionHogar(this.getCondicionHogar());
        postulacionFOVIS.setEstadoHogar(this.getEstadoHogar());
        if (this.getFechaCalificacion() != null) {
            postulacionFOVIS.setFechaCalificacion(new Date(this.getFechaCalificacion()));
        }
        if (this.getFechaEscritura() != null) {
            postulacionFOVIS.setFechaEscritura(new Date(this.getFechaEscritura()));
        }
        if (this.getFechaRegistroEscritura() != null) {
            postulacionFOVIS.setFechaRegistroEscritura(new Date(this.getFechaRegistroEscritura()));
        }
        postulacionFOVIS.setHogarPerdioSubsidioNoPago(this.getHogarPerdioSubsidioNoPago());
        postulacionFOVIS.setIdCalificacionPostulacion(this.getIdCalificacionPostulacion());
        postulacionFOVIS.setIdCicloAsignacion(this.getIdCicloAsignacion());
        postulacionFOVIS.setIdDocumento(this.getIdDocumento());
        postulacionFOVIS.setIdPostulacion(this.getIdPostulacion());
        if (this.getProyectoSolucionVivienda() != null && this.getProyectoSolucionVivienda().getIdProyectoVivienda() != null) {
            postulacionFOVIS.setIdProyectoVivienda(this.getProyectoSolucionVivienda().getIdProyectoVivienda());
        }
        postulacionFOVIS.setIdSolicitudAsignacion(this.getIdSolicitudAsignacion());
        if (this.getUbicacionVivienda() != null && this.getUbicacionVivienda().getIdUbicacion() != null) {
            postulacionFOVIS.setIdUbicacionVivienda(this.getUbicacionVivienda().getIdUbicacion());
        }
        postulacionFOVIS.setInformacionAsignacion(this.getInformacionAsignacion());
        postulacionFOVIS.setInformacionParametrizacion(this.getInformacionParametrizacion());
        postulacionFOVIS.setInformacionPostulacion(this.getInformacionPostulacion());
        postulacionFOVIS.setJefeHogar(this.getIdJefeHogar());
        postulacionFOVIS.setLoteUrbanizado(this.getLoteUrbanizado());
        postulacionFOVIS.setMatriculaInmobiliariaInmueble(this.getMatriculaInmobiliariaInmueble());
        postulacionFOVIS.setModalidad(this.getIdModalidad());
        postulacionFOVIS.setMotivoDesistimiento(this.getMotivoDesistimientoPostulacion());
        postulacionFOVIS.setMotivoEnajenacion(this.getMotivoEnajenacion());
        postulacionFOVIS.setMotivoHabilitacion(this.getMotivoHabilitacion());
        postulacionFOVIS.setMotivoRechazo(this.getMotivoRechazo());
        postulacionFOVIS.setMotivoRestitucion(this.getMotivoRestitucion());
        postulacionFOVIS.setNumeroEscritura(this.getNumeroEscritura());
        postulacionFOVIS.setPoseedorOcupanteVivienda(this.getPoseedorOcupanteVivienda());
        postulacionFOVIS.setPrioridadAsignacion(this.getPrioridadAsignacion());
        postulacionFOVIS.setPuntaje(this.getPuntaje());
        postulacionFOVIS.setRestituidoConSancion(this.getRestituidoConSancion());
        postulacionFOVIS.setResultadoAsignacion(this.getResultadoAsignacion());
        postulacionFOVIS.setTiempoSancion(this.getTiempoSancion());
        postulacionFOVIS.setUbicacionIgualProyecto(this.getUbicacionViviendaMismaProyecto());
        postulacionFOVIS.setValorAjusteIPCSFV(this.getValorAjusteIPCSFV());
        postulacionFOVIS.setValorAsignadoSFV(this.getValorAsignadoSFV());
        postulacionFOVIS.setValorCalculadoSFV(this.getValorCalculadoSFV());
        postulacionFOVIS.setValorProyectoVivienda(this.getValorProyectoVivienda());
        postulacionFOVIS.setValorSalarioAsignacion(this.getValorSalarioAsignacion());
        postulacionFOVIS.setValorSFVAjustado(this.getValorSFVAjustado());
        postulacionFOVIS.setValorSFVSolicitado(this.getValorSFVSolicitado());
        //Se agrega Oferente a la Postulación
        if (this.getOferente() != null && this.getOferente().getIdOferente() != null) {
            postulacionFOVIS.setIdOferente(this.getOferente().getIdOferente());
        }

        return postulacionFOVIS;
    }

    /**
     * Asocia los datos de la Entidad al DTO
     * 
     * @param PostulacionFOVIS
     */
    public void convertToDTO(PostulacionFOVIS postulacionFOVIS) {
        this.setAvaluoCatastralVivienda(postulacionFOVIS.getAvaluoCatastralVivienda());
        this.setCantidadFolios(postulacionFOVIS.getCantidadFolios());
        this.setCondicionHogar(postulacionFOVIS.getCondicionHogar());
        this.setEstadoHogar(postulacionFOVIS.getEstadoHogar());
        if (postulacionFOVIS.getFechaCalificacion() != null) {
            this.setFechaCalificacion(postulacionFOVIS.getFechaCalificacion().getTime());
        }
        if (postulacionFOVIS.getFechaEscritura() != null) {
            this.setFechaEscritura(postulacionFOVIS.getFechaEscritura().getTime());
        }
        if (postulacionFOVIS.getFechaRegistroEscritura() != null) {
            this.setFechaRegistroEscritura(postulacionFOVIS.getFechaRegistroEscritura().getTime());
        }
        this.setHogarPerdioSubsidioNoPago(postulacionFOVIS.getHogarPerdioSubsidioNoPago());
        this.setIdCalificacionPostulacion(postulacionFOVIS.getIdCalificacionPostulacion());
        this.setIdCicloAsignacion(postulacionFOVIS.getIdCicloAsignacion());
        this.setIdDocumento(postulacionFOVIS.getIdDocumento());
        this.setIdJefeHogar(postulacionFOVIS.getJefeHogar());
        this.setIdModalidad(postulacionFOVIS.getModalidad());
        this.setIdPostulacion(postulacionFOVIS.getIdPostulacion());
        this.setIdSolicitudAsignacion(postulacionFOVIS.getIdSolicitudAsignacion());
        this.setInformacionAsignacion(postulacionFOVIS.getInformacionAsignacion());
        this.setInformacionParametrizacion(postulacionFOVIS.getInformacionParametrizacion());
        this.setInformacionPostulacion(postulacionFOVIS.getInformacionPostulacion());
        this.setLoteUrbanizado(postulacionFOVIS.getLoteUrbanizado());
        this.setMatriculaInmobiliariaInmueble(postulacionFOVIS.getMatriculaInmobiliariaInmueble());
        this.setMotivoDesistimientoPostulacion(postulacionFOVIS.getMotivoDesistimiento());
        this.setMotivoEnajenacion(postulacionFOVIS.getMotivoEnajenacion());
        this.setMotivoHabilitacion(postulacionFOVIS.getMotivoHabilitacion());
        this.setMotivoRechazo(postulacionFOVIS.getMotivoRechazo());
        this.setMotivoRestitucion(postulacionFOVIS.getMotivoRestitucion());
        this.setNumeroEscritura(postulacionFOVIS.getNumeroEscritura());
        this.setPoseedorOcupanteVivienda(postulacionFOVIS.getPoseedorOcupanteVivienda());
        this.setPrioridadAsignacion(postulacionFOVIS.getPrioridadAsignacion());
        if (postulacionFOVIS.getIdProyectoVivienda() != null) {
            ProyectoSolucionViviendaModeloDTO proyecto = new ProyectoSolucionViviendaModeloDTO();
            proyecto.setIdProyectoVivienda(postulacionFOVIS.getIdProyectoVivienda());
            this.setProyectoSolucionVivienda(proyecto);
        }
        this.setPuntaje(postulacionFOVIS.getPuntaje());
        this.setRestituidoConSancion(postulacionFOVIS.getRestituidoConSancion());
        this.setResultadoAsignacion(postulacionFOVIS.getResultadoAsignacion());
        this.setTiempoSancion(postulacionFOVIS.getTiempoSancion());
        this.setUbicacionViviendaMismaProyecto(postulacionFOVIS.getUbicacionIgualProyecto());
        if(postulacionFOVIS.getIdUbicacionVivienda() != null){
            UbicacionModeloDTO ubicacion = new UbicacionModeloDTO();
            ubicacion.setIdUbicacion(postulacionFOVIS.getIdUbicacionVivienda());
            this.setUbicacionVivienda(ubicacion);
        }
        //Se agrega Oferente a la postulación
        if(postulacionFOVIS.getIdOferente() != null){
            OferenteModeloDTO oferente = new OferenteModeloDTO();
            oferente.setIdOferente(postulacionFOVIS.getIdOferente());
            this.setOferente(oferente);
        }
        this.setValorAjusteIPCSFV(postulacionFOVIS.getValorAjusteIPCSFV());
        this.setValorAsignadoSFV(postulacionFOVIS.getValorAsignadoSFV());
        this.setValorCalculadoSFV(postulacionFOVIS.getValorCalculadoSFV());
        this.setValorProyectoVivienda(postulacionFOVIS.getValorProyectoVivienda());
        this.setValorSalarioAsignacion(postulacionFOVIS.getValorSalarioAsignacion());
        this.setValorSFVAjustado(postulacionFOVIS.getValorSFVAjustado());
        this.setValorSFVSolicitado(postulacionFOVIS.getValorSFVSolicitado());
    }

    /**
     * Asocia los datos del DTO ResultadoAsignacionDTO al DTO de postulacion FOVIS
     * 
     * @param resultadoAsignacionDTO
     *        dto con el resultado de asignacion
     */
    public void convertToDTO(ResultadoAsignacionDTO resultadoAsignacionDTO) {
        this.setCantidadFolios(resultadoAsignacionDTO.getCantidadFolios());
        this.setCondicionHogar(resultadoAsignacionDTO.getCondicionHogar());
        this.setEstadoHogar(resultadoAsignacionDTO.getEstadoHogar());
        this.setHogarPerdioSubsidioNoPago(resultadoAsignacionDTO.getHogarPerdioSubsidioNoPago());
        this.setIdCicloAsignacion(resultadoAsignacionDTO.getIdCicloAsignacion());
        this.setIdModalidad(resultadoAsignacionDTO.getIdModalidad());
        this.setIdPostulacion(resultadoAsignacionDTO.getIdPostulacion());
        this.setIdJefeHogar(resultadoAsignacionDTO.getIdJefeHogar());
        if (resultadoAsignacionDTO.getProyectoSolucionVivienda() != null
                && resultadoAsignacionDTO.getProyectoSolucionVivienda().getIdProyectoVivienda() != null) {
            this.setProyectoSolucionVivienda(resultadoAsignacionDTO.getProyectoSolucionVivienda());
        }
        this.setValorSFVSolicitado(resultadoAsignacionDTO.getValorSFVSolicitado());
        this.setResultadoAsignacion(resultadoAsignacionDTO.getResultadoAsignacion());
        this.setIdSolicitudAsignacion(resultadoAsignacionDTO.getIdSolicitudAsignacion());
        this.setValorAsignadoSFV(resultadoAsignacionDTO.getValorAsignadoSFV());
        this.setPuntaje(resultadoAsignacionDTO.getPuntaje());
        this.setIdDocumento(resultadoAsignacionDTO.getIdDocumento());
        this.setValorCalculadoSFV(resultadoAsignacionDTO.getValorCalculadoSFV());

        if (resultadoAsignacionDTO.getFechaCalificacion() != null) {
            this.setFechaCalificacion(resultadoAsignacionDTO.getFechaCalificacion());
        }

        this.setPrioridadAsignacion(resultadoAsignacionDTO.getPrioridadAsignacion());
        this.setValorProyectoVivienda(resultadoAsignacionDTO.getValorProyectoVivienda());
        this.setRecursoPrioridad(resultadoAsignacionDTO.getRecursoPrioridad());

    }

    /**
     * Copia los datos del DTO a la Entidad.
     * 
     * @param persona
     *        postulacionFOVIS consultada.
     */
    public PostulacionFOVIS copyDTOToEntity(PostulacionFOVIS postulacionFOVIS) {
        if (this.getAvaluoCatastralVivienda() != null) {
            postulacionFOVIS.setAvaluoCatastralVivienda(this.getAvaluoCatastralVivienda());
        }
        if (this.getCantidadFolios() != null) {
            postulacionFOVIS.setCantidadFolios(this.getCantidadFolios());
        }
        if (this.getCondicionHogar() != null) {
            postulacionFOVIS.setCondicionHogar(this.getCondicionHogar());
        }
        if (this.getEstadoHogar() != null) {
            postulacionFOVIS.setEstadoHogar(this.getEstadoHogar());
        }
        if (this.getFechaCalificacion() != null) {
            postulacionFOVIS.setFechaCalificacion(new Date(this.getFechaCalificacion()));
        }
        if (this.getFechaEscritura() != null) {
            postulacionFOVIS.setFechaEscritura(new Date(this.getFechaEscritura()));
        }
        if (this.getFechaRegistroEscritura() != null) {
            postulacionFOVIS.setFechaRegistroEscritura(new Date(this.getFechaRegistroEscritura()));
        }
        if (this.getHogarPerdioSubsidioNoPago() != null) {
            postulacionFOVIS.setHogarPerdioSubsidioNoPago(this.getHogarPerdioSubsidioNoPago());
        }
        if (this.getIdCalificacionPostulacion() != null) {
            postulacionFOVIS.setIdCalificacionPostulacion(this.getIdCalificacionPostulacion());
        }
        if (this.getIdCicloAsignacion() != null) {
            postulacionFOVIS.setIdCicloAsignacion(this.getIdCicloAsignacion());
        }
        if (this.getIdDocumento() != null) {
            postulacionFOVIS.setIdDocumento(this.getIdDocumento());
        }
        if (this.getIdJefeHogar() != null) {
            postulacionFOVIS.setJefeHogar(this.getIdJefeHogar());
        }
        if (this.getIdModalidad() != null) {
            postulacionFOVIS.setModalidad(this.getIdModalidad());
        }
        if (this.getIdPostulacion() != null) {
            postulacionFOVIS.setIdPostulacion(this.getIdPostulacion());
        }
        if (this.getIdSolicitudAsignacion() != null) {
            postulacionFOVIS.setIdSolicitudAsignacion(this.getIdSolicitudAsignacion());
        }
        if (this.getInformacionAsignacion() != null) {
            postulacionFOVIS.setInformacionAsignacion(this.getInformacionAsignacion());
        }
        if (this.getInformacionParametrizacion() != null) {
            postulacionFOVIS.setInformacionParametrizacion(this.getInformacionParametrizacion());
        }
        if (this.getInformacionPostulacion() != null) {
            postulacionFOVIS.setInformacionPostulacion(this.getInformacionPostulacion());
        }
        if (this.getLoteUrbanizado() != null) {
            postulacionFOVIS.setLoteUrbanizado(this.getLoteUrbanizado());
        }
        if (this.getMatriculaInmobiliariaInmueble() != null) {
            postulacionFOVIS.setMatriculaInmobiliariaInmueble(this.getMatriculaInmobiliariaInmueble());
        }
        if (this.getMotivoDesistimientoPostulacion() != null) {
            postulacionFOVIS.setMotivoDesistimiento(this.getMotivoDesistimientoPostulacion());
        }
        if (this.getMotivoEnajenacion() != null) {
            postulacionFOVIS.setMotivoEnajenacion(this.getMotivoEnajenacion());
        }
        if (this.getMotivoHabilitacion() != null) {
            postulacionFOVIS.setMotivoHabilitacion(this.getMotivoHabilitacion());
        }
        if (this.getMotivoRechazo() != null) {
            postulacionFOVIS.setMotivoRechazo(this.getMotivoRechazo());
        }
        if (this.getMotivoRestitucion() != null) {
            postulacionFOVIS.setMotivoRestitucion(this.getMotivoRestitucion());
        }
        if (this.getNumeroEscritura() != null) {
            postulacionFOVIS.setNumeroEscritura(this.getNumeroEscritura());
        }
        if (this.getPoseedorOcupanteVivienda() != null) {
            postulacionFOVIS.setPoseedorOcupanteVivienda(this.getPoseedorOcupanteVivienda());
        }
        if (this.getPrioridadAsignacion() != null) {
            postulacionFOVIS.setPrioridadAsignacion(this.getPrioridadAsignacion());
        }
        if (this.getProyectoSolucionVivienda() != null && this.getProyectoSolucionVivienda().getIdProyectoVivienda() != null) {
            postulacionFOVIS.setIdProyectoVivienda(this.getProyectoSolucionVivienda().getIdProyectoVivienda());
        }
        if (this.getPuntaje() != null) {
            postulacionFOVIS.setPuntaje(this.getPuntaje());
        }
        if (this.getRestituidoConSancion() != null) {
            postulacionFOVIS.setRestituidoConSancion(this.getRestituidoConSancion());
        }
        if (this.getResultadoAsignacion() != null) {
            postulacionFOVIS.setResultadoAsignacion(this.getResultadoAsignacion());
        }
        if (this.getTiempoSancion() != null) {
            postulacionFOVIS.setTiempoSancion(this.getTiempoSancion());
        }
        if (this.getUbicacionViviendaMismaProyecto() != null) {
            postulacionFOVIS.setUbicacionIgualProyecto(this.getUbicacionViviendaMismaProyecto());
        }
        if (this.getUbicacionVivienda() != null && this.getUbicacionVivienda().getIdUbicacion() != null) {
            postulacionFOVIS.setIdUbicacionVivienda(this.getUbicacionVivienda().getIdUbicacion());
        }
        if (this.getValorAjusteIPCSFV() != null) {
            postulacionFOVIS.setValorAjusteIPCSFV(this.getValorAjusteIPCSFV());
        }
        if (this.getValorAsignadoSFV() != null) {
            postulacionFOVIS.setValorAsignadoSFV(this.getValorAsignadoSFV());
        }
        if (this.getValorCalculadoSFV() != null) {
            postulacionFOVIS.setValorCalculadoSFV(this.getValorCalculadoSFV());
        }
        if (this.getValorProyectoVivienda() != null) {
            postulacionFOVIS.setValorProyectoVivienda(this.getValorProyectoVivienda());
        }
        if (this.getValorSalarioAsignacion() != null) {
            postulacionFOVIS.setValorSalarioAsignacion(this.getValorSalarioAsignacion());
        }
        if (this.getValorSFVAjustado() != null) {
            postulacionFOVIS.setValorSFVAjustado(this.getValorSFVAjustado());
        }
        if (this.getValorSFVSolicitado() != null) {
            postulacionFOVIS.setValorSFVSolicitado(this.getValorSFVSolicitado());
        }
        //Se agrega Oferente a la postulación
        if (this.getOferente() != null && this.getOferente().getIdOferente() != null) {
            postulacionFOVIS.setIdOferente(this.getOferente().getIdOferente());
        }
        return postulacionFOVIS;
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
     * @return the idCicloAsignacion
     */
    public Long getIdCicloAsignacion() {
        return idCicloAsignacion;
    }

    /**
     * @param idCicloAsignacion
     *        the idCicloAsignacion to set
     */
    public void setIdCicloAsignacion(Long idCicloAsignacion) {
        this.idCicloAsignacion = idCicloAsignacion;
    }

    /**
     * @return the idModalidad
     */
    public ModalidadEnum getIdModalidad() {
        return idModalidad;
    }

    /**
     * @param idModalidad
     *        the idModalidad to set
     */
    public void setIdModalidad(ModalidadEnum idModalidad) {
        this.idModalidad = idModalidad;
    }

    /**
     * @return the jefeHogar
     */
    public JefeHogarModeloDTO getJefeHogar() {
        return jefeHogar;
    }

    /**
     * @param jefeHogar
     *        the jefeHogar to set
     */
    public void setJefeHogar(JefeHogarModeloDTO jefeHogar) {
        this.jefeHogar = jefeHogar;
    }

    /**
     * @return the estadoHogar
     */
    public EstadoHogarEnum getEstadoHogar() {
        return estadoHogar;
    }

    /**
     * @param estadoHogar
     *        the estadoHogar to set
     */
    public void setEstadoHogar(EstadoHogarEnum estadoHogar) {
        this.estadoHogar = estadoHogar;
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
     * @return the hogarPerdioSubsidioNoPago
     */
    public Boolean getHogarPerdioSubsidioNoPago() {
        return hogarPerdioSubsidioNoPago;
    }

    /**
     * @param hogarPerdioSubsidioNoPago
     *        the hogarPerdioSubsidioNoPago to set
     */
    public void setHogarPerdioSubsidioNoPago(Boolean hogarPerdioSubsidioNoPago) {
        this.hogarPerdioSubsidioNoPago = hogarPerdioSubsidioNoPago;
    }

    /**
     * @return the cantidadFolios
     */
    public Short getCantidadFolios() {
        return cantidadFolios;
    }

    /**
     * @param cantidadFolios
     *        the cantidadFolios to set
     */
    public void setCantidadFolios(Short cantidadFolios) {
        this.cantidadFolios = cantidadFolios;
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
     * Obtiene el valor de listaChequeoJefeHogar
     * 
     * @return El valor de listaChequeoJefeHogar
     */
    public ListaChequeoDTO getListaChequeoJefeHogar() {
        return listaChequeoJefeHogar;
    }

    /**
     * Establece el valor de listaChequeoJefeHogar
     * 
     * @param listaChequeoJefeHogar
     *        El valor de listaChequeoJefeHogar por asignar
     */
    public void setListaChequeoJefeHogar(ListaChequeoDTO listaChequeoJefeHogar) {
        this.listaChequeoJefeHogar = listaChequeoJefeHogar;
    }

    /**
     * Obtiene el valor de proyectoSolucionVivienda
     * 
     * @return El valor de proyectoSolucionVivienda
     */
    public ProyectoSolucionViviendaModeloDTO getProyectoSolucionVivienda() {
        return proyectoSolucionVivienda;
    }

    /**
     * Establece el valor de proyectoSolucionVivienda
     * 
     * @param proyectoSolucionVivienda
     *        El valor de proyectoSolucionVivienda por asignar
     */
    public void setProyectoSolucionVivienda(ProyectoSolucionViviendaModeloDTO proyectoSolucionVivienda) {
        this.proyectoSolucionVivienda = proyectoSolucionVivienda;
    }

    /**
     * @return the ahorroProgramado
     */
    public AhorroPrevioModeloDTO getAhorroProgramado() {
        return ahorroProgramado;
    }

    /**
     * @param ahorroProgramado
     *        the ahorroProgramado to set
     */
    public void setAhorroProgramado(AhorroPrevioModeloDTO ahorroProgramado) {
        this.ahorroProgramado = ahorroProgramado;
    }

    /**
     * @return the ahorroProgramadoContractual
     */
    public AhorroPrevioModeloDTO getAhorroProgramadoContractual() {
        return ahorroProgramadoContractual;
    }

    /**
     * @param ahorroProgramadoContractual
     *        the ahorroProgramadoContractual to set
     */
    public void setAhorroProgramadoContractual(AhorroPrevioModeloDTO ahorroProgramadoContractual) {
        this.ahorroProgramadoContractual = ahorroProgramadoContractual;
    }

    /**
     * @return the aportesPeriodicos
     */
    public AhorroPrevioModeloDTO getAportesPeriodicos() {
        return aportesPeriodicos;
    }

    /**
     * @param aportesPeriodicos
     *        the aportesPeriodicos to set
     */
    public void setAportesPeriodicos(AhorroPrevioModeloDTO aportesPeriodicos) {
        this.aportesPeriodicos = aportesPeriodicos;
    }

    /**
     * @return the cesantiasInmovilizadas
     */
    public AhorroPrevioModeloDTO getCesantiasInmovilizadas() {
        return cesantiasInmovilizadas;
    }

    /**
     * @param cesantiasInmovilizadas
     *        the cesantiasInmovilizadas to set
     */
    public void setCesantiasInmovilizadas(AhorroPrevioModeloDTO cesantiasInmovilizadas) {
        this.cesantiasInmovilizadas = cesantiasInmovilizadas;
    }

    /**
     * @return the cuotaInicial
     */
    public AhorroPrevioModeloDTO getCuotaInicial() {
        return cuotaInicial;
    }

    /**
     * @param cuotaInicial
     *        the cuotaInicial to set
     */
    public void setCuotaInicial(AhorroPrevioModeloDTO cuotaInicial) {
        this.cuotaInicial = cuotaInicial;
    }

    /**
     * @return the cuotasPagadas
     */
    public AhorroPrevioModeloDTO getCuotasPagadas() {
        return cuotasPagadas;
    }

    /**
     * @param cuotasPagadas
     *        the cuotasPagadas to set
     */
    public void setCuotasPagadas(AhorroPrevioModeloDTO cuotasPagadas) {
        this.cuotasPagadas = cuotasPagadas;
    }

    /**
     * @return the valorLoteTerreno
     */
    public AhorroPrevioModeloDTO getValorLoteTerreno() {
        return valorLoteTerreno;
    }

    /**
     * @param valorLoteTerreno
     *        the valorLoteTerreno to set
     */
    public void setValorLoteTerreno(AhorroPrevioModeloDTO valorLoteTerreno) {
        this.valorLoteTerreno = valorLoteTerreno;
    }

    /**
     * @return the valorLoteOPV
     */
    public AhorroPrevioModeloDTO getValorLoteOPV() {
        return valorLoteOPV;
    }

    /**
     * @param valorLoteOPV
     *        the valorLoteOPV to set
     */
    public void setValorLoteOPV(AhorroPrevioModeloDTO valorLoteOPV) {
        this.valorLoteOPV = valorLoteOPV;
    }

    /**
     * @return the valorLoteSubsidioMunicipal
     */
    public AhorroPrevioModeloDTO getValorLoteSubsidioMunicipal() {
        return valorLoteSubsidioMunicipal;
    }

    /**
     * @param valorLoteSubsidioMunicipal
     *        the valorLoteSubsidioMunicipal to set
     */
    public void setValorLoteSubsidioMunicipal(AhorroPrevioModeloDTO valorLoteSubsidioMunicipal) {
        this.valorLoteSubsidioMunicipal = valorLoteSubsidioMunicipal;
    }

    /**
     * @return the ahorroOtrasModalidades
     */
    public RecursoComplementarioModeloDTO getAhorroOtrasModalidades() {
        return ahorroOtrasModalidades;
    }

    /**
     * @param ahorroOtrasModalidades
     *        the ahorroOtrasModalidades to set
     */
    public void setAhorroOtrasModalidades(RecursoComplementarioModeloDTO ahorroOtrasModalidades) {
        this.ahorroOtrasModalidades = ahorroOtrasModalidades;
    }

    /**
     * @return the aportesEnteTerritorial
     */
    public RecursoComplementarioModeloDTO getAportesEnteTerritorial() {
        return aportesEnteTerritorial;
    }

    /**
     * @param aportesEnteTerritorial
     *        the aportesEnteTerritorial to set
     */
    public void setAportesEnteTerritorial(RecursoComplementarioModeloDTO aportesEnteTerritorial) {
        this.aportesEnteTerritorial = aportesEnteTerritorial;
    }

    /**
     * @return the aportesSolidarios
     */
    public RecursoComplementarioModeloDTO getAportesSolidarios() {
        return aportesSolidarios;
    }

    /**
     * @param aportesSolidarios
     *        the aportesSolidarios to set
     */
    public void setAportesSolidarios(RecursoComplementarioModeloDTO aportesSolidarios) {
        this.aportesSolidarios = aportesSolidarios;
    }

    /**
     * @return the cesantiasNoInmovilizadas
     */
    public RecursoComplementarioModeloDTO getCesantiasNoInmovilizadas() {
        return cesantiasNoInmovilizadas;
    }

    /**
     * @param cesantiasNoInmovilizadas
     *        the cesantiasNoInmovilizadas to set
     */
    public void setCesantiasNoInmovilizadas(RecursoComplementarioModeloDTO cesantiasNoInmovilizadas) {
        this.cesantiasNoInmovilizadas = cesantiasNoInmovilizadas;
    }

    /**
     * @return the creditoAprobado
     */
    public RecursoComplementarioModeloDTO getCreditoAprobado() {
        return creditoAprobado;
    }

    /**
     * @param creditoAprobado
     *        the creditoAprobado to set
     */
    public void setCreditoAprobado(RecursoComplementarioModeloDTO creditoAprobado) {
        this.creditoAprobado = creditoAprobado;
    }

    /**
     * @return the donacionOtrasEntidades
     */
    public RecursoComplementarioModeloDTO getDonacionOtrasEntidades() {
        return donacionOtrasEntidades;
    }

    /**
     * @param donacionOtrasEntidades
     *        the donacionOtrasEntidades to set
     */
    public void setDonacionOtrasEntidades(RecursoComplementarioModeloDTO donacionOtrasEntidades) {
        this.donacionOtrasEntidades = donacionOtrasEntidades;
    }

    /**
     * @return the evaluacionCrediticia
     */
    public RecursoComplementarioModeloDTO getEvaluacionCrediticia() {
        return evaluacionCrediticia;
    }

    /**
     * @param evaluacionCrediticia
     *        the evaluacionCrediticia to set
     */
    public void setEvaluacionCrediticia(RecursoComplementarioModeloDTO evaluacionCrediticia) {
        this.evaluacionCrediticia = evaluacionCrediticia;
    }

    /**
     * @return the otrosRecursos
     */
    public RecursoComplementarioModeloDTO getOtrosRecursos() {
        return otrosRecursos;
    }

    /**
     * @param otrosRecursos
     *        the otrosRecursos to set
     */
    public void setOtrosRecursos(RecursoComplementarioModeloDTO otrosRecursos) {
        this.otrosRecursos = otrosRecursos;
    }

    /**
     * @return the valorAvanceObra
     */
    public RecursoComplementarioModeloDTO getValorAvanceObra() {
        return valorAvanceObra;
    }

    /**
     * @param valorAvanceObra
     *        the valorAvanceObra to set
     */
    public void setValorAvanceObra(RecursoComplementarioModeloDTO valorAvanceObra) {
        this.valorAvanceObra = valorAvanceObra;
    }

    /**
     * @return the idJefeHogar
     */
    public Long getIdJefeHogar() {
        return idJefeHogar;
    }

    /**
     * @param idJefeHogar
     *        the idJefeHogar to set
     */
    public void setIdJefeHogar(Long idJefeHogar) {
        this.idJefeHogar = idJefeHogar;
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
     * @return the idSolicitudAsignacion
     */
    public Long getIdSolicitudAsignacion() {
        return idSolicitudAsignacion;
    }

    /**
     * @param idSolicitudAsignacion
     *        the idSolicitudAsignacion to set
     */
    public void setIdSolicitudAsignacion(Long idSolicitudAsignacion) {
        this.idSolicitudAsignacion = idSolicitudAsignacion;
    }

    /**
     * @return the valorAsignadoSFV
     */
    public BigDecimal getValorAsignadoSFV() {
        return valorAsignadoSFV;
    }

    /**
     * @param valorAsignadoSFV
     *        the valorAsignadoSFV to set
     */
    public void setValorAsignadoSFV(BigDecimal valorAsignadoSFV) {
        this.valorAsignadoSFV = valorAsignadoSFV;
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
     * @return the idDocumento
     */
    public String getIdDocumento() {
        return idDocumento;
    }

    /**
     * @param idDocumento
     *        the idDocumento to set
     */
    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    /**
     * Obtiene el valor de fechaCalificacion
     * 
     * @return El valor de fechaCalificacion
     */
    public Long getFechaCalificacion() {
        return fechaCalificacion;
    }

    /**
     * Establece el valor de fechaCalificacion
     * 
     * @param fechaCalificacion
     *        El valor de fechaCalificacion por asignar
     */
    public void setFechaCalificacion(Long fechaCalificacion) {
        this.fechaCalificacion = fechaCalificacion;
    }

    /**
     * Obtiene el valor de prioridadAsignacion
     * 
     * @return El valor de prioridadAsignacion
     */
    public PrioridadAsignacionEnum getPrioridadAsignacion() {
        return prioridadAsignacion;
    }

    /**
     * Establece el valor de prioridadAsignacion
     * 
     * @param prioridadAsignacion
     *        El valor de prioridadAsignacion por asignar
     */
    public void setPrioridadAsignacion(PrioridadAsignacionEnum prioridadAsignacion) {
        this.prioridadAsignacion = prioridadAsignacion;
    }

    /**
     * @return the valorCalculadoSFV
     */
    public BigDecimal getValorCalculadoSFV() {
        return valorCalculadoSFV;
    }

    /**
     * @param valorCalculadoSFV
     *        the valorCalculadoSFV to set
     */
    public void setValorCalculadoSFV(BigDecimal valorCalculadoSFV) {
        this.valorCalculadoSFV = valorCalculadoSFV;
    }

    /**
     * @return the valorProyectoVivienda
     */
    public BigDecimal getValorProyectoVivienda() {
        return valorProyectoVivienda;
    }

    /**
     * @param valorProyectoVivienda
     *        the valorProyectoVivienda to set
     */
    public void setValorProyectoVivienda(BigDecimal valorProyectoVivienda) {
        this.valorProyectoVivienda = valorProyectoVivienda;
    }

    /**
     * @return the fechaPublicacion
     */
    public Long getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * @param fechaPublicacion
     *        the fechaPublicacion to set
     */
    public void setFechaPublicacion(Long fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * @return the motivoDesistimientoPostulacion
     */
    public MotivoDesistimientoPostulacionEnum getMotivoDesistimientoPostulacion() {
        return motivoDesistimientoPostulacion;
    }

    /**
     * @param motivoDesistimientoPostulacion
     *        the motivoDesistimientoPostulacion to set
     */
    public void setMotivoDesistimientoPostulacion(MotivoDesistimientoPostulacionEnum motivoDesistimientoPostulacion) {
        this.motivoDesistimientoPostulacion = motivoDesistimientoPostulacion;
    }

    /**
     * @return the ahorroProgramadoMobilizado
     */
    public Boolean getAhorroProgramadoMobilizado() {
        return ahorroProgramadoMobilizado;
    }

    /**
     * @param ahorroProgramadoMobilizado
     *        the ahorroProgramadoMobilizado to set
     */
    public void setAhorroProgramadoMobilizado(Boolean ahorroProgramadoMobilizado) {
        this.ahorroProgramadoMobilizado = ahorroProgramadoMobilizado;
    }

    /**
     * @return the ahorroEvaluacionCrediticiaMobilizado
     */
    public Boolean getAhorroEvaluacionCrediticiaMobilizado() {
        return ahorroEvaluacionCrediticiaMobilizado;
    }

    /**
     * @param ahorroEvaluacionCrediticiaMobilizado
     *        the ahorroEvaluacionCrediticiaMobilizado to set
     */
    public void setAhorroEvaluacionCrediticiaMobilizado(Boolean ahorroEvaluacionCrediticiaMobilizado) {
        this.ahorroEvaluacionCrediticiaMobilizado = ahorroEvaluacionCrediticiaMobilizado;
    }

    /**
     * @return the cesantiasMovilizado
     */
    public Boolean getCesantiasMovilizado() {
        return cesantiasMovilizado;
    }

    /**
     * @param cesantiasMovilizado
     *        the cesantiasMovilizado to set
     */
    public void setCesantiasMovilizado(Boolean cesantiasMovilizado) {
        this.cesantiasMovilizado = cesantiasMovilizado;
    }

    /**
     * @return the motivoHabilitacion
     */
    public MotivoHabilitacionPostulacionEnum getMotivoHabilitacion() {
        return motivoHabilitacion;
    }

    /**
     * @param motivoHabilitacion
     *        the motivoHabilitacion to set
     */
    public void setMotivoHabilitacion(MotivoHabilitacionPostulacionEnum motivoHabilitacion) {
        this.motivoHabilitacion = motivoHabilitacion;
    }

    /**
     * @return the restituidoConSancion
     */
    public Boolean getRestituidoConSancion() {
        return restituidoConSancion;
    }

    /**
     * @param restituidoConSancion
     *        the restituidoConSancion to set
     */
    public void setRestituidoConSancion(Boolean restituidoConSancion) {
        this.restituidoConSancion = restituidoConSancion;
    }

    /**
     * @return the tiempoSancion
     */
    public TiempoSancionPostulacionEnum getTiempoSancion() {
        return tiempoSancion;
    }

    /**
     * @param tiempoSancion
     *        the tiempoSancion to set
     */
    public void setTiempoSancion(TiempoSancionPostulacionEnum tiempoSancion) {
        this.tiempoSancion = tiempoSancion;
    }

    /**
     * @return the motivoRestitucion
     */
    public MotivoRestitucionSubsidioEnum getMotivoRestitucion() {
        return motivoRestitucion;
    }

    /**
     * @param motivoRestitucion
     *        the motivoRestitucion to set
     */
    public void setMotivoRestitucion(MotivoRestitucionSubsidioEnum motivoRestitucion) {
        this.motivoRestitucion = motivoRestitucion;
    }

    /**
     * @return the motivoEnajenacion
     */
    public MotivoEnajenacionPostulacionEnum getMotivoEnajenacion() {
        return motivoEnajenacion;
    }

    /**
     * @param motivoEnajenacion
     *        the motivoEnajenacion to set
     */
    public void setMotivoEnajenacion(MotivoEnajenacionPostulacionEnum motivoEnajenacion) {
        this.motivoEnajenacion = motivoEnajenacion;
    }

    /**
     * @return the valorAjusteIPCSFV
     */
    public BigDecimal getValorAjusteIPCSFV() {
        return valorAjusteIPCSFV;
    }

    /**
     * @param valorAjusteIPCSFV
     *        the valorAjusteIPCSFV to set
     */
    public void setValorAjusteIPCSFV(BigDecimal valorAjusteIPCSFV) {
        this.valorAjusteIPCSFV = valorAjusteIPCSFV;
    }

    /**
     * @return the cicloAsignacion
     */
    public CicloAsignacionModeloDTO getCicloAsignacion() {
        return cicloAsignacion;
    }

    /**
     * @param cicloAsignacion the cicloAsignacion to set
     */
    public void setCicloAsignacion(CicloAsignacionModeloDTO cicloAsignacion) {
        this.cicloAsignacion = cicloAsignacion;
    }

    /**
     * @return the motivoRechazo
     */
    public MotivoRechazoPostulacionEnum getMotivoRechazo() {
        return motivoRechazo;
    }

    /**
     * @param motivoRechazo the motivoRechazo to set
     */
    public void setMotivoRechazo(MotivoRechazoPostulacionEnum motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    /**
     * @return the informacionAsignacion
     */
    public String getInformacionAsignacion() {
        return informacionAsignacion;
    }

    /**
     * @param informacionAsignacion the informacionAsignacion to set
     */
    public void setInformacionAsignacion(String informacionAsignacion) {
        this.informacionAsignacion = informacionAsignacion;
    }

    /**
     * @return the idCalificacionPostulacion
     */
    public Long getIdCalificacionPostulacion() {
        return idCalificacionPostulacion;
    }

    /**
     * @param idCalificacionPostulacion the idCalificacionPostulacion to set
     */
    public void setIdCalificacionPostulacion(Long idCalificacionPostulacion) {
        this.idCalificacionPostulacion = idCalificacionPostulacion;
    }

    /**
     * @return the avaluoCatastralVivienda
     */
    public BigDecimal getAvaluoCatastralVivienda() {
        return avaluoCatastralVivienda;
    }

    /**
     * @param avaluoCatastralVivienda
     *        the avaluoCatastralVivienda to set
     */
    public void setAvaluoCatastralVivienda(BigDecimal avaluoCatastralVivienda) {
        this.avaluoCatastralVivienda = avaluoCatastralVivienda;
    }

    /**
     * @return the informacionParametrizacion
     */
    public String getInformacionParametrizacion() {
        return informacionParametrizacion;
    }

    /**
     * @param informacionParametrizacion the informacionParametrizacion to set
     */
    public void setInformacionParametrizacion(String informacionParametrizacion) {
        this.informacionParametrizacion = informacionParametrizacion;
    }

    /**
     * @return the valorSalarioAsignacion
     */
    public BigDecimal getValorSalarioAsignacion() {
        return valorSalarioAsignacion;
    }

    /**
     * @param valorSalarioAsignacion the valorSalarioAsignacion to set
     */
    public void setValorSalarioAsignacion(BigDecimal valorSalarioAsignacion) {
        this.valorSalarioAsignacion = valorSalarioAsignacion;
    }

    /**
     * @return the valorSFVAjustado
     */
    public BigDecimal getValorSFVAjustado() {
        return valorSFVAjustado;
    }

    /**
     * @param valorSFVAjustado the valorSFVAjustado to set
     */
    public void setValorSFVAjustado(BigDecimal valorSFVAjustado) {
        this.valorSFVAjustado = valorSFVAjustado;
    }

    /**
     * @return the matriculaInmobiliariaInmueble
     */
    public String getMatriculaInmobiliariaInmueble() {
        return matriculaInmobiliariaInmueble;
    }

    /**
     * @param matriculaInmobiliariaInmueble the matriculaInmobiliariaInmueble to set
     */
    public void setMatriculaInmobiliariaInmueble(String matriculaInmobiliariaInmueble) {
        this.matriculaInmobiliariaInmueble = matriculaInmobiliariaInmueble;
    }

    /**
     * @return the fechaRegistroEscritura
     */
    public Long getFechaRegistroEscritura() {
        return fechaRegistroEscritura;
    }

    /**
     * @param fechaRegistroEscritura the fechaRegistroEscritura to set
     */
    public void setFechaRegistroEscritura(Long fechaRegistroEscritura) {
        this.fechaRegistroEscritura = fechaRegistroEscritura;
    }

    /**
     * @return the loteUrbanizado
     */
    public Boolean getLoteUrbanizado() {
        return loteUrbanizado;
    }

    /**
     * @param loteUrbanizado the loteUrbanizado to set
     */
    public void setLoteUrbanizado(Boolean loteUrbanizado) {
        this.loteUrbanizado = loteUrbanizado;
    }

    /**
     * @return the poseedorOcupanteVivienda
     */
    public String getPoseedorOcupanteVivienda() {
        return poseedorOcupanteVivienda;
    }

    /**
     * @param poseedorOcupanteVivienda the poseedorOcupanteVivienda to set
     */
    public void setPoseedorOcupanteVivienda(String poseedorOcupanteVivienda) {
        this.poseedorOcupanteVivienda = poseedorOcupanteVivienda;
    }

    /**
     * @return the numeroEscritura
     */
    public String getNumeroEscritura() {
        return numeroEscritura;
    }

    /**
     * @param numeroEscritura the numeroEscritura to set
     */
    public void setNumeroEscritura(String numeroEscritura) {
        this.numeroEscritura = numeroEscritura;
    }

    /**
     * @return the fechaEscritura
     */
    public Long getFechaEscritura() {
        return fechaEscritura;
    }

    /**
     * @param fechaEscritura the fechaEscritura to set
     */
    public void setFechaEscritura(Long fechaEscritura) {
        this.fechaEscritura = fechaEscritura;
    }

    /**
     * @return the ubicacionViviendaMismaProyecto
     */
    public Boolean getUbicacionViviendaMismaProyecto() {
        return ubicacionViviendaMismaProyecto;
    }

    /**
     * @param ubicacionViviendaMismaProyecto the ubicacionViviendaMismaProyecto to set
     */
    public void setUbicacionViviendaMismaProyecto(Boolean ubicacionViviendaMismaProyecto) {
        this.ubicacionViviendaMismaProyecto = ubicacionViviendaMismaProyecto;
    }

    /**
     * @return the ubicacionVivienda
     */
    public UbicacionModeloDTO getUbicacionVivienda() {
        return ubicacionVivienda;
    }

    /**
     * @param ubicacionVivienda the ubicacionVivienda to set
     */
    public void setUbicacionVivienda(UbicacionModeloDTO ubicacionVivienda) {
        this.ubicacionVivienda = ubicacionVivienda;
    }

    /**
     * @return the informacionPostulacion
     */
    public String getInformacionPostulacion() {
        return informacionPostulacion;
    }

    /**
     * @param informacionPostulacion the informacionPostulacion to set
     */
    public void setInformacionPostulacion(String informacionPostulacion) {
        this.informacionPostulacion = informacionPostulacion;
    }

	public OferenteModeloDTO getOferente() {
		return oferente;
	}

	public void setOferente(OferenteModeloDTO oferente) {
		this.oferente = oferente;
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

        
    /**
     * Obtiene el valor de proveedor
     * 
     * @return El valor de proveedor
     */
    public List<LegalizacionDesembolosoProveedorModeloDTO> getLegalizacionProveedor() {
        return legalizacionProveedor;
    }

    /**
     * Establece el valor de proveedor
     * 
     * @param proveedor
     *        El valor de proveedor por asignar
     */
    public void setLegalizacionProveedor(List<LegalizacionDesembolosoProveedorModeloDTO> legalizacionProveedor) {
        this.legalizacionProveedor = legalizacionProveedor;
    }

    public Date getFechaValidacionCruce() {
        return fechaValidacionCruce;
    }

    public void setFechaValidacionCruce(Date fechaValidacionCruce) {
        this.fechaValidacionCruce = fechaValidacionCruce;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "PostulacionFOVISModeloDTO{" +
                "idPostulacion=" + idPostulacion +
                ", idCicloAsignacion=" + idCicloAsignacion +
                ", cicloAsignacion=" + cicloAsignacion +
                ", idModalidad=" + idModalidad +
                ", idJefeHogar=" + idJefeHogar +
                ", jefeHogar=" + jefeHogar +
                ", listaChequeoJefeHogar=" + listaChequeoJefeHogar +
                ", estadoHogar=" + estadoHogar +
                ", condicionHogar=" + condicionHogar +
                ", hogarPerdioSubsidioNoPago=" + hogarPerdioSubsidioNoPago +
                ", cantidadFolios=" + cantidadFolios +
                ", valorSFVSolicitado=" + valorSFVSolicitado +
                ", proyectoSolucionVivienda=" + proyectoSolucionVivienda +
                ", ahorroProgramado=" + ahorroProgramado +
                ", ahorroProgramadoContractual=" + ahorroProgramadoContractual +
                ", aportesPeriodicos=" + aportesPeriodicos +
                ", cesantiasInmovilizadas=" + cesantiasInmovilizadas +
                ", cuotaInicial=" + cuotaInicial +
                ", cuotasPagadas=" + cuotasPagadas +
                ", valorLoteTerreno=" + valorLoteTerreno +
                ", valorLoteOPV=" + valorLoteOPV +
                ", valorLoteSubsidioMunicipal=" + valorLoteSubsidioMunicipal +
                ", ahorroOtrasModalidades=" + ahorroOtrasModalidades +
                ", aportesEnteTerritorial=" + aportesEnteTerritorial +
                ", aportesSolidarios=" + aportesSolidarios +
                ", cesantiasNoInmovilizadas=" + cesantiasNoInmovilizadas +
                ", creditoAprobado=" + creditoAprobado +
                ", donacionOtrasEntidades=" + donacionOtrasEntidades +
                ", evaluacionCrediticia=" + evaluacionCrediticia +
                ", otrosRecursos=" + otrosRecursos +
                ", valorAvanceObra=" + valorAvanceObra +
                ", resultadoAsignacion=" + resultadoAsignacion +
                ", idSolicitudAsignacion=" + idSolicitudAsignacion +
                ", valorAsignadoSFV=" + valorAsignadoSFV +
                ", puntaje=" + puntaje +
                ", fechaCalificacion=" + fechaCalificacion +
                ", prioridadAsignacion=" + prioridadAsignacion +
                ", idDocumento='" + idDocumento + '\'' +
                ", valorCalculadoSFV=" + valorCalculadoSFV +
                ", valorProyectoVivienda=" + valorProyectoVivienda +
                ", fechaPublicacion=" + fechaPublicacion +
                ", motivoDesistimientoPostulacion=" + motivoDesistimientoPostulacion +
                ", ahorroProgramadoMobilizado=" + ahorroProgramadoMobilizado +
                ", ahorroEvaluacionCrediticiaMobilizado=" + ahorroEvaluacionCrediticiaMobilizado +
                ", cesantiasMovilizado=" + cesantiasMovilizado +
                ", motivoHabilitacion=" + motivoHabilitacion +
                ", restituidoConSancion=" + restituidoConSancion +
                ", tiempoSancion=" + tiempoSancion +
                ", motivoRestitucion=" + motivoRestitucion +
                ", motivoEnajenacion=" + motivoEnajenacion +
                ", valorAjusteIPCSFV=" + valorAjusteIPCSFV +
                ", motivoRechazo=" + motivoRechazo +
                ", informacionAsignacion='" + informacionAsignacion + '\'' +
                ", idCalificacionPostulacion=" + idCalificacionPostulacion +
                ", avaluoCatastralVivienda=" + avaluoCatastralVivienda +
                ", informacionParametrizacion='" + informacionParametrizacion + '\'' +
                ", valorSalarioAsignacion=" + valorSalarioAsignacion +
                ", valorSFVAjustado=" + valorSFVAjustado +
                ", matriculaInmobiliariaInmueble='" + matriculaInmobiliariaInmueble + '\'' +
                ", fechaRegistroEscritura=" + fechaRegistroEscritura +
                ", loteUrbanizado=" + loteUrbanizado +
                ", poseedorOcupanteVivienda='" + poseedorOcupanteVivienda + '\'' +
                ", numeroEscritura='" + numeroEscritura + '\'' +
                ", fechaEscritura=" + fechaEscritura +
                ", ubicacionViviendaMismaProyecto=" + ubicacionViviendaMismaProyecto +
                ", ubicacionVivienda=" + ubicacionVivienda +
                ", informacionPostulacion='" + informacionPostulacion + '\'' +
                ", oferente=" + oferente +
                ", legalizacionProveedor=" + legalizacionProveedor +
                ", recursoPrioridad=" + recursoPrioridad +
                '}';
    }
}
