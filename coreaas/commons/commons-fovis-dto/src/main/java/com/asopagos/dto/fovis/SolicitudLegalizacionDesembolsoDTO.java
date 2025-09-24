package com.asopagos.dto.fovis;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.asopagos.dto.EscalamientoSolicitudDTO;
import com.asopagos.dto.ListaChequeoDTO;
import com.asopagos.dto.modelo.AhorroPrevioModeloDTO;
import com.asopagos.dto.modelo.LegalizacionDesembolosoProveedorModeloDTO;
import com.asopagos.dto.modelo.LegalizacionDesembolsoModeloDTO;
import com.asopagos.dto.modelo.ProyectoSolucionViviendaModeloDTO;
import com.asopagos.dto.modelo.RecursoComplementarioModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.afiliaciones.MetodoAsignacionBackEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoSolicitudEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.CausaIntentoFallidoLegalizacionDesembolsoEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudLegalizacionDesembolsoEnum;
import java.util.List;

public class SolicitudLegalizacionDesembolsoDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 14542121545454666L;

    /**
     * Identificador único, llave primaria
     */
    private Long idSolicitudLegalizacionDesembolso;

    /**
     * Identificador solicitud global
     */
    private Long idSolicitud;

    /**
     * Identificador solicitud global
     */
    private String numeroRadicacion;

    /**
     * Identificador de la instancia de proceso BPM.
     */
    private Long idInstanciaProceso;

    /**
     * Los datos de la legalización
     */
    private LegalizacionDesembolsoModeloDTO legalizacionDesembolso;

    /**
     * indicador para diligenciar o no la existencia y habitabilidad
     */
    private Boolean incluyeCertificadoExistenciaHabitabilidad;

    /**
     * Objeto que trae todos los datos de la postulación
     */
    private SolicitudPostulacionFOVISDTO datosPostulacionFovis;

    /**
     * Lista de chequeo de requisitos documentales generales de la Legalización y desembolso
     */
    private ListaChequeoDTO listaChequeo;

    /**
     * Estado de la solicitud postulación.
     */
    private EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitud;

    /**
     * Información del oferente para la legalización y desembolso
     */
    private OferenteDTO oferenteLegalizacion;
    
    /**
     * Información del proveedor
     */
    private List<LegalizacionDesembolosoProveedorModeloDTO> legalizacionProveedor;
    
    
    /**
     * Proyecto de solución de vivienda de la legalizacion y desembolso
     */
    private ProyectoSolucionViviendaModeloDTO proyectoSolucionViviendaLegalizacion;

    /**
     * Escalamiento a miembros del hogar
     */
    private EscalamientoSolicitudDTO escalamientoMiembrosHogar;

    /**
     * Escalamiento jurídico
     */
    private EscalamientoSolicitudDTO escalamientoJuridico;

    /**
     * Escalamiento al técnico en construcción
     */
    private EscalamientoSolicitudDTO escalamientoTecnicoConstruccion;

    /**
     * Canal de recepcion de la solicitud
     */
    private CanalRecepcionEnum canalRecepcion;

    /**
     * Tipo transaccion en proceso.
     */
    private TipoTransaccionEnum tipoTransaccionEnum;

    /**
     * Método de envío de los documentos
     */
    private FormatoEntregaDocumentoEnum metodoEnvio;

    /**
     * Tarea activa.
     */
    private Long idTarea;

    /**
     * Indica la causa por la cual un intento de postulación no pudo ser
     * radicado
     */
    private CausaIntentoFallidoLegalizacionDesembolsoEnum causaIntentoFallido;

    /**
     * Observaciones de la solicitud de legalización y desembolso.
     */
    private String observaciones;

    /**
     * Propiedad para indicar si la solicitud de legalizacion ha sido escalada a algún analista
     */
    private Boolean escalada;

    /**
     * Propiedad para indicar si la solicitud de legalización ha sido exitosa en su radicación.
     */
    private Boolean legalizacionExitosa;

    /**
     * Propiedad para indicar si la solicitud de legalizacion ha sido un intento fallido
     */
    private Boolean intentoLegalizacion;

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
     * Datos Existencia y Habitabilidad.
     */
    private RegistroExistenciaHabitabilidadDTO existenciaHabitabilidad;

    /**
     * Valor Proyecto de Vivienda.
     */
    private BigDecimal valorProyectoVivienda;

    /**
     * Resultado del desembolso (Exitoso o No exitoso)
     * 
     */
    private Boolean desembolsoExitoso;

    /**
     * Variable que contiene el metodo de asignación.
     */
    private MetodoAsignacionBackEnum metodoAsignacion;
    
    /**
     * Variable que contiene la observación.
     */
    private String observacion;
    
    /**
     * Variable usuario
     */
    private String usuario;
    
    /**
	 * Identificador comunicado solicitud
	 */
	private String idComunicadoSolicitud;
	
	/**
	 * Fecha de radicación de la solicitud
	 */
	private Long fechaRadicacion;
	
	/**
     * Tipo de solicitud
     */
    private TipoSolicitudEnum tipoSolicitud;

    /**
     * Indica la cantidad de reintento de transaccion desembolso maximo se permiten 10
     */
    private Short cantidadReintentos;
    
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
	 * Constructor por defecto
	 */
	public SolicitudLegalizacionDesembolsoDTO() {
	}

	/**
	 * Constructor de la clase
	 * 
	 * @param idSolicitudPostulacion
	 * @param idSolicitud
	 * @param numeroRadicacion
	 * @param postulacion
	 */
	public SolicitudLegalizacionDesembolsoDTO(String idSolicitud, String numeroRadicacion, Date fechaRadicacion,
			String idSolicitudNovedad, EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitud,
			String idComunicadoSolicitud) {
		this.idSolicitudLegalizacionDesembolso = idSolicitudNovedad != null ? Long.parseLong(idSolicitudNovedad) : null;
		this.idSolicitud = idSolicitud != null ? Long.parseLong(idSolicitud) : null;
		this.numeroRadicacion = numeroRadicacion;
		this.estadoSolicitud = estadoSolicitud;
		this.fechaRadicacion = fechaRadicacion != null ? fechaRadicacion.getTime() : null;
		this.idComunicadoSolicitud = idComunicadoSolicitud;
		this.tipoSolicitud = TipoSolicitudEnum.LEGALIZACION_FOVIS;
	}

    /**
     * Obtiene el valor de idSolicitudLegalizacionDesembolso
     * 
     * @return El valor de idSolicitudLegalizacionDesembolso
     */
    public Long getIdSolicitudLegalizacionDesembolso() {
        return idSolicitudLegalizacionDesembolso;
    }

    /**
     * Establece el valor de idSolicitudLegalizacionDesembolso
     * 
     * @param idSolicitudLegalizacionDesembolso
     *        El valor de idSolicitudLegalizacionDesembolso por asignar
     */
    public void setIdSolicitudLegalizacionDesembolso(Long idSolicitudLegalizacionDesembolso) {
        this.idSolicitudLegalizacionDesembolso = idSolicitudLegalizacionDesembolso;
    }

    /**
     * Obtiene el valor de idSolicitud
     * 
     * @return El valor de idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * Establece el valor de idSolicitud
     * 
     * @param idSolicitud
     *        El valor de idSolicitud por asignar
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * @return the legalizacionDesembolso
     */
    public LegalizacionDesembolsoModeloDTO getLegalizacionDesembolso() {
        return legalizacionDesembolso;
    }

    /**
     * @param legalizacionDesembolso
     *        the legalizacionDesembolso to set
     */
    public void setLegalizacionDesembolso(LegalizacionDesembolsoModeloDTO legalizacionDesembolso) {
        this.legalizacionDesembolso = legalizacionDesembolso;
    }

    /**
     * @return the incluyeCertificadoExistenciaHabitabilidad
     */
    public Boolean getIncluyeCertificadoExistenciaHabitabilidad() {
        return incluyeCertificadoExistenciaHabitabilidad;
    }

    /**
     * @param incluyeCertificadoExistenciaHabitabilidad
     *        the incluyeCertificadoExistenciaHabitabilidad to set
     */
    public void setIncluyeCertificadoExistenciaHabitabilidad(Boolean incluyeCertificadoExistenciaHabitabilidad) {
        this.incluyeCertificadoExistenciaHabitabilidad = incluyeCertificadoExistenciaHabitabilidad;
    }

    /**
     * @return the datosPostulacionFovis
     */
    public SolicitudPostulacionFOVISDTO getDatosPostulacionFovis() {
        return datosPostulacionFovis;
    }

    /**
     * @param datosPostulacionFovis
     *        the datosPostulacionFovis to set
     */
    public void setDatosPostulacionFovis(SolicitudPostulacionFOVISDTO datosPostulacionFovis) {
        this.datosPostulacionFovis = datosPostulacionFovis;
    }

    /**
     * Obtiene el valor de listaChequeo
     * 
     * @return El valor de listaChequeo
     */
    public ListaChequeoDTO getListaChequeo() {
        return listaChequeo;
    }

    /**
     * Establece el valor de listaChequeo
     * 
     * @param listaChequeo
     *        El valor de listaChequeo por asignar
     */
    public void setListaChequeo(ListaChequeoDTO listaChequeo) {
        this.listaChequeo = listaChequeo;
    }

    /**
     * Obtiene el valor de estadoSolicitud
     * 
     * @return El valor de estadoSolicitud
     */
    public EstadoSolicitudLegalizacionDesembolsoEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * Establece el valor de estadoSolicitud
     * 
     * @param estadoSolicitud
     *        El valor de estadoSolicitud por asignar
     */
    public void setEstadoSolicitud(EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * @return the oferenteLegalizacion
     */
    public OferenteDTO getOferenteLegalizacion() {
        return oferenteLegalizacion;
    }

    /**
     * @param oferenteLegalizacion
     *        the oferenteLegalizacion to set
     */
    public void setOferenteLegalizacion(OferenteDTO oferenteLegalizacion) {
        this.oferenteLegalizacion = oferenteLegalizacion;
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

    
    /**
     * @return the proyectoSolucionViviendaLegalizacion
     */
    public ProyectoSolucionViviendaModeloDTO getProyectoSolucionViviendaLegalizacion() {
        return proyectoSolucionViviendaLegalizacion;
    }

    /**
     * @param proyectoSolucionViviendaLegalizacion
     *        the proyectoSolucionViviendaLegalizacion to set
     */
    public void setProyectoSolucionViviendaLegalizacion(ProyectoSolucionViviendaModeloDTO proyectoSolucionViviendaLegalizacion) {
        this.proyectoSolucionViviendaLegalizacion = proyectoSolucionViviendaLegalizacion;
    }

    /**
     * Obtiene el valor de escalamientoMiembrosHogar
     * 
     * @return El valor de escalamientoMiembrosHogar
     */
    public EscalamientoSolicitudDTO getEscalamientoMiembrosHogar() {
        return escalamientoMiembrosHogar;
    }

    /**
     * Establece el valor de escalamientoMiembrosHogar
     * 
     * @param escalamientoMiembrosHogar
     *        El valor de escalamientoMiembrosHogar por asignar
     */
    public void setEscalamientoMiembrosHogar(EscalamientoSolicitudDTO escalamientoMiembrosHogar) {
        this.escalamientoMiembrosHogar = escalamientoMiembrosHogar;
    }

    /**
     * Obtiene el valor de escalamientoJuridico
     * 
     * @return El valor de escalamientoJuridico
     */
    public EscalamientoSolicitudDTO getEscalamientoJuridico() {
        return escalamientoJuridico;
    }

    /**
     * Establece el valor de escalamientoJuridico
     * 
     * @param escalamientoJuridico
     *        El valor de escalamientoJuridico por asignar
     */
    public void setEscalamientoJuridico(EscalamientoSolicitudDTO escalamientoJuridico) {
        this.escalamientoJuridico = escalamientoJuridico;
    }

    /**
     * Obtiene el valor de escalamientoTecnicoConstruccion
     * 
     * @return El valor de escalamientoTecnicoConstruccion
     */
    public EscalamientoSolicitudDTO getEscalamientoTecnicoConstruccion() {
        return escalamientoTecnicoConstruccion;
    }

    /**
     * Establece el valor de escalamientoTecnicoConstruccion
     * 
     * @param escalamientoTecnicoConstruccion
     *        El valor de escalamientoTecnicoConstruccion por asignar
     */
    public void setEscalamientoTecnicoConstruccion(EscalamientoSolicitudDTO escalamientoTecnicoConstruccion) {
        this.escalamientoTecnicoConstruccion = escalamientoTecnicoConstruccion;
    }

    /**
     * Obtiene el valor de canalRecepcion
     * 
     * @return El valor de canalRecepcion
     */
    public CanalRecepcionEnum getCanalRecepcion() {
        return canalRecepcion;
    }

    /**
     * Establece el valor de canalRecepcion
     * 
     * @param canalRecepcion
     *        El valor de canalRecepcion por asignar
     */
    public void setCanalRecepcion(CanalRecepcionEnum canalRecepcion) {
        this.canalRecepcion = canalRecepcion;
    }

    /**
     * @return the tipoTransaccionEnum
     */
    public TipoTransaccionEnum getTipoTransaccionEnum() {
        return tipoTransaccionEnum;
    }

    /**
     * @param tipoTransaccionEnum
     *        the tipoTransaccionEnum to set
     */
    public void setTipoTransaccionEnum(TipoTransaccionEnum tipoTransaccionEnum) {
        this.tipoTransaccionEnum = tipoTransaccionEnum;
    }

    /**
     * @return the metodoEnvio
     */
    public FormatoEntregaDocumentoEnum getMetodoEnvio() {
        return metodoEnvio;
    }

    /**
     * @param metodoEnvio
     *        the metodoEnvio to set
     */
    public void setMetodoEnvio(FormatoEntregaDocumentoEnum metodoEnvio) {
        this.metodoEnvio = metodoEnvio;
    }

    /**
     * @return the idInstanciaProceso
     */
    public Long getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    /**
     * @param idInstanciaProceso
     *        the idInstanciaProceso to set
     */
    public void setIdInstanciaProceso(Long idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }

    /**
     * @return the idTarea
     */
    public Long getIdTarea() {
        return idTarea;
    }

    /**
     * @param idTarea
     *        the idTarea to set
     */
    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
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
     * Obtiene el valor de causaIntentoFallido
     * 
     * @return El valor de causaIntentoFallido
     */
    public CausaIntentoFallidoLegalizacionDesembolsoEnum getCausaIntentoFallido() {
        return causaIntentoFallido;
    }

    /**
     * Establece el valor de causaIntentoFallido
     * 
     * @param causaIntentoFallido
     *        El valor de causaIntentoFallido por asignar
     */
    public void setCausaIntentoFallido(CausaIntentoFallidoLegalizacionDesembolsoEnum causaIntentoFallido) {
        this.causaIntentoFallido = causaIntentoFallido;
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

    /**
     * @return the escalada
     */
    public Boolean getEscalada() {
        return escalada;
    }

    /**
     * @param escalada
     *        the escalada to set
     */
    public void setEscalada(Boolean escalada) {
        this.escalada = escalada;
    }

    /**
     * @return the legalizacionExitosa
     */
    public Boolean getLegalizacionExitosa() {
        return legalizacionExitosa;
    }

    /**
     * @param legalizacionExitosa
     *        the legalizacionExitosa to set
     */
    public void setLegalizacionExitosa(Boolean legalizacionExitosa) {
        this.legalizacionExitosa = legalizacionExitosa;
    }

    /**
     * @return the intentoLegalizacion
     */
    public Boolean getIntentoLegalizacion() {
        return intentoLegalizacion;
    }

    /**
     * @param intentoLegalizacion
     *        the intentoLegalizacion to set
     */
    public void setIntentoLegalizacion(Boolean intentoLegalizacion) {
        this.intentoLegalizacion = intentoLegalizacion;
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
     * @return the existenciaHabitabilidad
     */
    public RegistroExistenciaHabitabilidadDTO getExistenciaHabitabilidad() {
        return existenciaHabitabilidad;
    }

    /**
     * @param existenciaHabitabilidad
     *        the existenciaHabitabilidad to set
     */
    public void setExistenciaHabitabilidad(RegistroExistenciaHabitabilidadDTO existenciaHabitabilidad) {
        this.existenciaHabitabilidad = existenciaHabitabilidad;
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
     * @return the desembolsoExitoso
     */
    public Boolean getDesembolsoExitoso() {
        return desembolsoExitoso;
    }

    /**
     * @param desembolsoExitoso
     *        the desembolsoExitoso to set
     */
    public void setDesembolsoExitoso(Boolean desembolsoExitoso) {
        this.desembolsoExitoso = desembolsoExitoso;
    }

    /**
     * @return the metodoAsignacion
     */
    public MetodoAsignacionBackEnum getMetodoAsignacion() {
        return metodoAsignacion;
    }

    /**
     * @param metodoAsignacion
     *        the metodoAsignacion to set
     */
    public void setMetodoAsignacion(MetodoAsignacionBackEnum metodoAsignacion) {
        this.metodoAsignacion = metodoAsignacion;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

	/**
	 * @return the idComunicadoSolicitud
	 */
	public String getIdComunicadoSolicitud() {
		return idComunicadoSolicitud;
	}

	/**
	 * @param idComunicadoSolicitud the idComunicadoSolicitud to set
	 */
	public void setIdComunicadoSolicitud(String idComunicadoSolicitud) {
		this.idComunicadoSolicitud = idComunicadoSolicitud;
	}

	/**
	 * @return the fechaRadicacion
	 */
	public Long getFechaRadicacion() {
		return fechaRadicacion;
	}

	/**
	 * @param fechaRadicacion the fechaRadicacion to set
	 */
	public void setFechaRadicacion(Long fechaRadicacion) {
		this.fechaRadicacion = fechaRadicacion;
	}

	/**
	 * @return the tipoSolicitud
	 */
	public TipoSolicitudEnum getTipoSolicitud() {
		return tipoSolicitud;
	}

	/**
	 * @param tipoSolicitud the tipoSolicitud to set
	 */
	public void setTipoSolicitud(TipoSolicitudEnum tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

    /**
     * @return the cantidadReintentos
     */
    public Short getCantidadReintentos() {
        return cantidadReintentos;
    }

    /**
     * @param cantidadReintentos the cantidadReintentos to set
     */
    public void setCantidadReintentos(Short cantidadReintentos) {
        this.cantidadReintentos = cantidadReintentos;
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

    @Override
    public String toString() {
        return "SolicitudLegalizacionDesembolsoDTO{" +
                "idSolicitudLegalizacionDesembolso=" + idSolicitudLegalizacionDesembolso +
                ", idSolicitud=" + idSolicitud +
                ", numeroRadicacion='" + numeroRadicacion + '\'' +
                ", idInstanciaProceso=" + idInstanciaProceso +
                ", legalizacionDesembolso=" + legalizacionDesembolso +
                ", incluyeCertificadoExistenciaHabitabilidad=" + incluyeCertificadoExistenciaHabitabilidad +
                ", datosPostulacionFovis=" + datosPostulacionFovis +
                ", listaChequeo=" + listaChequeo +
                ", estadoSolicitud=" + estadoSolicitud +
                ", oferenteLegalizacion=" + oferenteLegalizacion +
                ", legalizacionProveedor=" + legalizacionProveedor +
                ", proyectoSolucionViviendaLegalizacion=" + proyectoSolucionViviendaLegalizacion +
                ", escalamientoMiembrosHogar=" + escalamientoMiembrosHogar +
                ", escalamientoJuridico=" + escalamientoJuridico +
                ", escalamientoTecnicoConstruccion=" + escalamientoTecnicoConstruccion +
                ", canalRecepcion=" + canalRecepcion +
                ", tipoTransaccionEnum=" + tipoTransaccionEnum +
                ", metodoEnvio=" + metodoEnvio +
                ", idTarea=" + idTarea +
                ", causaIntentoFallido=" + causaIntentoFallido +
                ", observaciones='" + observaciones + '\'' +
                ", escalada=" + escalada +
                ", legalizacionExitosa=" + legalizacionExitosa +
                ", intentoLegalizacion=" + intentoLegalizacion +
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
                ", existenciaHabitabilidad=" + existenciaHabitabilidad +
                ", valorProyectoVivienda=" + valorProyectoVivienda +
                ", desembolsoExitoso=" + desembolsoExitoso +
                ", metodoAsignacion=" + metodoAsignacion +
                ", observacion='" + observacion + '\'' +
                ", usuario='" + usuario + '\'' +
                ", idComunicadoSolicitud='" + idComunicadoSolicitud + '\'' +
                ", fechaRadicacion=" + fechaRadicacion +
                ", tipoSolicitud=" + tipoSolicitud +
                ", cantidadReintentos=" + cantidadReintentos +
                ", matriculaInmobiliariaInmueble='" + matriculaInmobiliariaInmueble + '\'' +
                ", fechaRegistroEscritura=" + fechaRegistroEscritura +
                ", loteUrbanizado=" + loteUrbanizado +
                ", poseedorOcupanteVivienda='" + poseedorOcupanteVivienda + '\'' +
                ", numeroEscritura='" + numeroEscritura + '\'' +
                ", fechaEscritura=" + fechaEscritura +
                ", ubicacionViviendaMismaProyecto=" + ubicacionViviendaMismaProyecto +
                ", ubicacionVivienda=" + ubicacionVivienda +
                '}';
    }
}