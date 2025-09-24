/**
 * 
 */
package com.asopagos.novedades.dto;

import java.math.BigDecimal;
import java.util.List;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.dto.modelo.GrupoFamiliarModeloDTO;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.entidades.ccf.personas.EntidadPagadora;
import com.asopagos.entidades.ccf.personas.RelacionGrupoFamiliar;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.entidades.transversal.personas.AFP;
import com.asopagos.entidades.transversal.personas.GradoAcademico;
import com.asopagos.entidades.transversal.personas.OcupacionProfesion;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.CausaServiciosSinAfiliacionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.EstadoTarjetaEnum;
import com.asopagos.enumeraciones.core.TipoContratoEnum;
import com.asopagos.enumeraciones.core.TipoSalarioEnum;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;
import com.asopagos.enumeraciones.personas.ClaseIndependienteEnum;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.FactorVulnerabilidadEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.personas.OrientacionSexualEnum;
import com.asopagos.enumeraciones.personas.PertenenciaEtnicaEnum;
import com.asopagos.enumeraciones.personas.SectorUbicacionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.PeriodoPagoPlanillaEnum;

/**
 * DTO que contiene los campos que se pueden modificar a una persona.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class DatosPersonaNovedadDTO implements DatosNovedadDTO {

	/**
	 * Id de la persona.
	 */
	private Long idPersona;
	
	/**
	 * Id del Beneficiario.
	 */
	private Long idBeneficiario;
	
	/**
	 * Id del Grupo Familiar.
	 */
	private Long idGrupoFamiliar;
	
	/**
	 * Id del Rol Afiliado.
	 */
	private Long idRolAfiliado;
	
	/**
	 * Id del Administrador del Subsidio.
	 * */
	private Long idAdministradorSubsidio;

	/**
	 * Numero Identificación Persona.
	 */
	private String numeroIdentificacion;
	
	/**
	 * Tipo Identificación Persona.
	 */
	private TipoIdentificacionEnum tipoIdentificacion;
	
	/**
	 * Tipo de identificación empleador
	 */
	private TipoIdentificacionEnum tipoIdentificacionEmpleador;

	/**
	 * No. de identificación empleador
	 */
	private String numeroIdentificacionEmpleador;

	/**
	 * Nombre o razón social del empleador
	 */
	private String nombreRazonSocialEmpleador;

	/**
	 * tipo de solicitante
	 */
	private TipoAfiliadoEnum tipoSolicitanteTrabajador;

	/**
	 * tipo de identificación nuevo
	 */
	private TipoIdentificacionEnum tipoIdentificacionTrabajador;

	/**
	 * No. Identificación nuevo
	 */
	private String numeroIdentificacionTrabajador;

	/**
	 * Primer apellido
	 */
	private String primerApellidoTrabajador;

	/**
	 * Segundo apellido
	 */
	private String segundoApellidoTrabajador;

	/**
	 * Primer nombre
	 */
	private String primerNombreTrabajador;

	/**
	 * Segundo nombre
	 */
	private String segundoNombreTrabajador;

	/**
	 * Tipo de identificación
	 */
	private TipoIdentificacionEnum tipoIdentificacionBeneficiario;

	/**
	 * No. Identificación
	 */
	private String numeroIdentificacionBeneficiario;
	
	/**
	 * Tipo de identificación
	 */
	private TipoIdentificacionEnum tipoIdentificacionBeneficiarioAnterior;

	/**
	 * No. Identificación
	 */
	private String numeroIdentificacionBeneficiarioAnterior;

	/**
	 * Primer apellido
	 */
	private String primerApellidoBeneficiario;

	/**
	 * Segundo apellido
	 */
	private String segundoApellidoBeneficiario;

	/**
	 * Primer nombre
	 */
	private String primerNombreBeneficiario;

	/**
	 * Segundo nombre
	 */
	private String segundoNombreBeneficiario;

	/**
	 * Clasificación
	 */
	private ClasificacionEnum clasificacion;

	/**
	 * Fecha expedición documento identidad
	 */
	private Long fechaExpedicionDocumentoTrabajador;

	/**
	 * Género
	 */
	private GeneroEnum generoTrabajador;

	/**
	 * Fecha de nacimiento
	 */
	private Long fechaNacimientoTrabajador;

	/**
	 * Fecha de nacimiento
	 */
	private Long fechaNacimientoBeneficiario;

	/**
	 * Nivel educativo
	 */
	private NivelEducativoEnum nivelEducativoTrabajador;

	/**
	 * Ocupación / Profesión
	 */
	private OcupacionProfesion ocupacionProfesionTrabajador;

	/**
	 * Cabeza de hogar
	 */
	private Boolean cabezaHogar;

	/**
	 * Departamento
	 */
	private Departamento departamentoTrabajador;

	/**
	 * Municipio
	 */
	private Municipio municipioTrabajador;

	/**
	 * Dirección residencia
	 */
	private String direccionResidenciaTrabajador;
	
	/**
	 * Dirección residencia
	 */
	private String descripcionIndicacionResidenciaTrabajador;

	/**
	 * Vive en casa propia?
	 */
	private Boolean viveEnCasaPropia;

	/**
	 * Código postal
	 */
	private String codigoPostalTrabajador;

	/**
	 * Indicativo
	 */
	private String indicativoTelFijoTrabajador;

	/**
	 * Teléfono fijo
	 */
	private String telefonoFijoTrabajador;

	/**
	 * Teléfono celular
	 */
	private String telefonoCelularTrabajador;

	/**
	 * Correo electrónico
	 */
	private String correoElectronicoTrabajador;

	/**
	 * Autorización envío de correo electrónico
	 */
	private Boolean autorizacionEnvioCorreoElectronicoTrabajador;

	/**
	 * Autoriza utilización de datos personales?
	 */
	private Boolean autorizaUtilizarDatosPersonales;

	/**
	 * Reside en sector rural?
	 */
	private Boolean resideEnSectorRural;
	
	/**
	 * Reside en sector rural Grupo Familiar?
	 */
	private Boolean resideEnSectorRuralGrupoFamiliar;

	/**
	 * Dispone de tarjeta?
	 */
	private Boolean disponeDeTarjeta;

	/**
	 * Emitir tarjeta?
	 */
	private Boolean emitirTarjeta;

	/**
	 * Emitir inmediatamente?
	 */
	private Boolean emitirInmediatamente;

	/**
	 * Clase de trabajador
	 */
	private ClaseTrabajadorEnum claseTrabajador;

	/**
	 * Fecha inicio de labores con empleador
	 */
	private Long fechaInicioLaboresConEmpleador;

	/**
	 * Tipo de salario
	 */
	private TipoSalarioEnum tipoSalarioTrabajador;

	/**
	 * Valor salario mensual
	 */
	private BigDecimal valorSalarioMensualTrabajador;

	/**
	 * Horas laboradas en el mes
	 */
	private Integer horasLaboralesMesTrabajador;

	/**
	 * Cargo u oficio desempeñado
	 */
	private String cargoOficioDesempeniadoTrabajador;

	/**
	 * Tipo de contrato laboral (indefinido, obra realizada, fijo)
	 */
	private TipoContratoEnum tipoContratoLaboralTrabajador;

	/**
	 * Fecha de terminación del contrato (AAAA, MM, DD)
	 */
	private Long fechaTerminacioContratoTrabajador;

	/**
	 * El trabajador es el mismo representante legal del empleador
	 */
	private String trabajadorMismoReptLegalDelEpleador;

	/**
	 * El trabajador es socio del empleador
	 */
	private String trabajadorEsSocioDelEmpleador;

	/**
	 * El trabajador es cónyuge del socio del empleador
	 */
	private String trabajadorEsConyugeDelSocioDelEmpleador;

	/**
	 * Trabajador inhabilitado para recibir subsidio?
	 */
	private Boolean trabajadorInhabilitadoParaSubsidio;

	/**
	 * Sucursal (Centro de trabajo)
	 */
	private SucursalEmpresa sucursalEmpleadorTrabajador;

	/**
	 * Clase de independiente
	 */
	private ClaseIndependienteEnum claseIndependiente;

	/**
	 * % pago de aportes
	 */
	private BigDecimal porcentajePagoAportesIndependiente;

	/**
	 * Ingresos mensuales
	 */
	private BigDecimal ingresosMensualesIndependiente;

	/**
	 * Entidad pagadora de aportes
	 */
	private EntidadPagadora entidadPagadoraDeAportes;

	/**
	 * Valor mesada pensional
	 */
	private BigDecimal valorMesadaPensional;

	/**
	 * Pagador de pensión
	 */
	private AFP pagadorFondoPensiones;

	/**
	 * Entidad pagadora de aportes de pensionado
	 */
	private EntidadPagadora entidadPagadoraDeAportesPensionado;

	/**
	 * Identificador ante entidad pagadora
	 */
	private String idEntidadPagadora;

	/**
	 * Estado civil
	 */
	private EstadoCivilEnum estadoCivilTrabajador;

	/**
	 * Dirección es la misma del afiliado principal
	 */
	private Boolean mismaDireccionAfiliadoPrincipalGrupoFam;

	/**
	 * Departamento residencia
	 */
	private Object departamentoResidenciaGrupoFam;

	/**
	 * Municipio residencia
	 */
	private Municipio municipioResidenciaGrupoFam;

	/**
	 * Dirección residencia
	 */
	private String direccionResidenciaGrupoFam;
	
	   /**
     * Dirección residencia
     */
    private String descripcionIndicacionGrupoFam;

    /**
	 * Código postal
	 */
	private String codigoPostalGrupoFam;

	/**
	 * Indicativo
	 */
	private String indicativoTelFijoGrupoFam;

	/**
	 * Teléfono fijo
	 */
	private String telefonoFijoGrupoFam;

	/**
	 * Teléfono celular
	 */
	private String telCelularGrupoFam;

	/**
	 * Correo electrónico
	 */
	private String emailGrupoFam;

	/**
	 * Afiliado principal es el mismo administrador del subsidio?
	 */
	private Boolean afiliadoPpalMismoAdminDeSubsidio;

	/**
	 * Fecha expedición documento identidad
	 */
	private Long fechaExpedicionDocConyuge;

	/**
	 * Género
	 */
	private GeneroEnum generoConyuge;

	/**
	 * Fecha de nacimiento
	 */
	private Long fechaNacimientoConyuge;

	/**
	 * Nivel educativo
	 */
	private NivelEducativoEnum nivelEducativoConyuge;

	/**
	 * Ocupación / Profesión
	 */
	private OcupacionProfesion profesionConyuge;

	/**
	 * Cónyuge labora?
	 */
	private Boolean conyugeLabora;

	/**
	 * Valor ingreso mensual
	 */
	private BigDecimal valorIngresoMensualConyuge;

	/**
	 * Fecha expedición documento identidad
	 */
	private Long fechaExpedicionDocHijo;

	/**
	 * Género
	 */
	private GeneroEnum generoHijo;

	/**
	 * Fecha de nacimiento
	 */
	private Long fechaNacimientoHijo;

	/**
	 * Nivel educativo
	 */
	private NivelEducativoEnum nivelEducativoHijo;

	/**
	 * Grado cursado
	 */
	private GradoAcademico gradoCursadoHijo;
	
	/**
     * Grado cursado trabajador
     */
    private GradoAcademico gradoCursado;

	/**
	 * Ocupación / Profesión
	 */
	private OcupacionProfesion profesionHijo;

	/**
	 * Certificado escolar
	 */
	private Boolean certificadoEscolarHijo;

	/**
	 * Fecha vencimiento certificado escolar
	 */
	private Long fechaVencimientoCertEscolar;

	/**
	 * Fecha de reporte del certificado escolar
	 */
	private Long fechaReporteCertEscolarHijo;

	/**
	 * Beneficiario tipo hijo (entre 19 y 22 años) estudiante de programa en
	 * Institución para el trabajo y desarrollo humano
	 */
	private Boolean beneficioProgramaTrabajoDesarrollo;

	/**
	 * Persona en condición de invalidez?
	 */
	private Boolean condicionInvalidezHijo;

	/**
	 * Fecha de reporte de la invalidez
	 */
	private Long fechaRepoteinvalidezHijo;

	/**
	 * Observaciones
	 */
	private String observacionesHijo;

	/**
	 * Fecha expedición documento identidad
	 */
	private Long expediciondocPadre;

	/**
	 * Género
	 */
	private GeneroEnum generoPadre;

	/**
	 * Fecha de nacimiento
	 */
	private Long fechaNacimientoPadre;

	/**
	 * Nivel educativo
	 */
	private NivelEducativoEnum nivelEducativoPadre;

	/**
	 * Ocupación / Profesión
	 */
	private OcupacionProfesion ocupacionProfesionPadre;

	/**
	 * Persona en condición de invalidez?
	 */
	private Boolean condicionInvalidezPadre;

	/**
	 * Fecha de reporte de la invalidez
	 */
	private Long fechaReporteInvalidezPadre;

	/**
	 * Observaciones
	 */
	private String observacionesPadre;

	/**
	 * Estado con respecto al afiliado principal
	 */
	private EstadoAfiliadoEnum estadoAfiliadoPrincipalBeneficiario;

	/**
	 * Parentesco
	 */
	private ClasificacionEnum parentescoBeneficiarios;

	/**
	 * Fecha de inicio de la unión con el afiliado principal
	 */
	private Long fechaInicioUnionConAfiliadoPrincipal;

	/**
	 * Fecha de registro de la activación del beneficiario
	 */
	private Long fechaRegistroActivacionBeneficiario;

	/**
	 * Fecha de terminación de la sociedad conyugal
	 */
	private Long fechaFinsociedadConyugal;

	/**
	 * Motivo de desafiliación
	 */
	private MotivoDesafiliacionAfiliadoEnum motivoDesafiliacionTrabajador;

	/**
	 * Motivo de desafiliación
	 */
	private MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacionBeneficiario;

	/**
	 * Fecha de inactivación de beneficiario
	 */
	private Long fechaInactivacionBeneficiario;

	/**
	 * Estado civil
	 */
	private EstadoCivilEnum estadoCivilConyuge;

	/**
	 * Indica si la novedad esta activa y vigente
	 */
	private Boolean novedadVigente;

	/**
	 * Fecha de inicio de la incapacidad
	 */
	private Long fechaInicioNovedad;

	/**
	 * Fecha de finalización de la incapacidad
	 */
	private Long fechaFinNovedad;

	/**
	 * Vigencia de pagos por medio de entidad pagadora de aportes
	 */
	private Long vigenciaPagosPensionado;

	/**
	 * Vigencia de pagos por medio de entidad pagadora de aportes
	 */
	private Long vigenciaPagosDependiente;

	/**
	 * Tarifa de pago de aportes
	 */
	private BigDecimal tarifaPagoAportesIndependiente;

	/**
	 * Tarifa de pago de aportes
	 */
	private BigDecimal tarifaPagoAportesPensionado;

	/**
	 * Emitir tarjeta inmediatamente
	 */
	private Boolean emitirTarjetaDependiente;

	/**
	 * Solicitud a realizar (tarjeta)
	 */
	private String solicitudTarjeta;

	/**
	 * Motivo (tarjeta)
	 */
	private String motivoReexpedTarjetaDependiente;

	/**
	 * Persona en condición de invalidez?
	 */
	private Boolean condicionInvalidezTrabajador;

	/**
	 * Fecha de reporte de invalidez
	 */
	private Long fechaReporteInvalidezTrabajador;

	/**
	 * Persona fallecida?
	 */
	private Boolean personaFallecidaTrabajador;

	/**
	 * Persona fallecida?
	 */
	private Boolean personaFallecidaBeneficiarios;

	/**
	 * Fecha de reporte de fallecimiento
	 */
	private Long fechaReporteFallecimientoTrabajador;

	/**
	 * Estado de afiliación
	 */
	private EstadoAfiliadoEnum estadoAfiliacionTrabajador;

	/**
	 * Pignoración de subsidio
	 */
	private Boolean pignoracionSubsidioTrabajador;

	/**
	 * Estado de la tarjeta
	 */
	private EstadoTarjetaEnum estadoTarjetaTrabajador;

	/**
	 * Estado de la tarjeta
	 */
	private EstadoTarjetaEnum estadoTarjetaGrupoFam;

	/**
	 * Motivo de anulación
	 */
	private String motivoAnulacionTrabajador;

	/**
	 * Motivo de anulación
	 */
	private String motivoAnulacionBeneficiario;

	/**
	 * Primer Nombre Titular cuenta trabajador
	 */
	private String primerNombreTitularCuentaTrabajador;
	
	/**
	 * Segundo nombre Titular cuenta trabajador
	 */
	private String segundoNombreTitularCuentaTrabajador;
	
	/**
	 * Primer Apellido Titular cuenta trabajador
	 */
	private String primerApellidoTitularCuentaTrabajador;
	
	/**
	 * Segundo Apellido Titular cuenta trabajador
	 */
	private String segundoApellidoTitularCuentaTrabajador;
	
	
	/**
	 * Número de identificación del titular de la cuenta
	 */
	private String numeroDocTitularTrabajador;

	/**
	 * Tipo de identificación del titular de la cuenta
	 */
	private TipoIdentificacionEnum tipoDocTitularTrabajador;

	/**
	 * Banco donde está registrada la cuenta
	 */
	private String bancoDondeRegistraCuentaTrabajador;

	/**
	 * Tipo de cuenta
	 */
	private String tipoCuentaTrabajador;

	/**
	 * Número de la cuenta
	 */
	private String numeroCuentaTrabajador;

	/**
	 * Estado del afiliado principal con respecto al tipo de afiliación
	 */
	private EstadoAfiliadoEnum estadoAfiliadoPrincipalTrabajador;

	/**
	 * Estado general del solicitante con respecto a la caja de compensación
	 */
	private String estadoGeneralSolicitanteCajacompTrabajador;

	/**
	 * Canal
	 */
	private CanalRecepcionEnum canalTrabajador;

	/**
	 * Servicios sin afiliación activos/inactivos
	 */
	private Boolean serviciosSinAfiliacionTrabajador;

	/**
	 * Causa de inactivación de servicios sin afiliación
	 */
	private CausaServiciosSinAfiliacionEnum causaInactivacionServiciosSinAfiliarTrabajador;

	/**
	 * Fecha de registro de la novedad de al novedad de activación de servicios
	 * sin afiliación
	 */
	private Long fechaNovedadActivacionServiciosSinAfiliarTrabajador;

	/**
	 * Categoría
	 */
	private CategoriaPersonaEnum categoriaTrabajador;

	/**
	 * Fecha de finalización de servicios como "Servicios sin afiliación
	 */
	private Long fechaFinaliazaServicioSinAfiliacionTrabajador;

	/**
	 * Grupo familiar inembargable
	 */
	private Boolean grupoFamiliarInembargable;

	/**
	 * Requiere inactivación de cuenta web
	 */
	private Boolean inactivarCuentaWeb;

	/**
	 * Retención del subsidio activa
	 */
	private Boolean rentencionSubsidioActivaGF;

	/**
	 * Tipo de identificación de adminsitrador del subsidio
	 */
	private TipoIdentificacionEnum tipoDocumentoAdminGF;

	/**
	 * Número de identificación adminsitrador de subsidio
	 */
	private String numeroDocumentoAdminGF;

	/**
	 * Primer nombre adminsitrador de subsidio
	 */
	private String primNombreAdminGF;

	/**
	 * Segundo nombre adminsitrador de subsidio
	 */
	private String segNombreAdminGF;

	/**
	 * Primer apellido adminsitrador de subsidio
	 */
	private String primApellidoAdminGF;

	/**
	 * Segundo apellido adminsitrador de subsidio
	 */
	private String segApellidoAdminGF;

	/**
	 * Relación con grupo familiar
	 */
	private RelacionGrupoFamiliar relacionConGrupoFam;

	/**
	 * Activar medio de pago Efectivo
	 */
	private Boolean activarMedioPagoEfectivo;
	
	/**
	 * Atributo que indica si hay o no cesion del subsidio.
	 */
	private Boolean cesionSubsidio;
	
    /**
     * Variable listaChequeoNovedad
     */
    private List<ItemChequeoDTO> listaChequeoNovedad;

    /**
     * Afiliados que se manejan automáticamente.
     * */
    private List<Long> idPersonas;
    
    /**
     * Grupo Familiar Asociado a un Beneficiario.
     */
    private GrupoFamiliarModeloDTO grupoFamiliarBeneficiario;
    
    /**
     * Atributo que indica si se trata de una agregación de un beneficiario.
     */
    private Boolean afiliacion;
    
    /**
     * Atributo que indica el id del empleador, cuando se trata de una novedad de dependientes web.
     */
    private Long idEmpleadorDependiente;
    
    /**
     * Atributo Medio de Pago
     */
    private MedioDePagoModeloDTO medioDePagoModeloDTO;
    
    /**
     * Lista de identificadores de beneficiarios Novedad Automatica
     */
    private List<Long> idBeneficiarios;
    
	/**
	 * Fecha expedición documento identidad
	 */
	private Long fechaExpedicionDocumentoBeneficiario;
    
	/**
     * Contiene el motivo de subsanacion de la expulsion
     */
    private String motivoSubsanacionExpulsion;

    /**
     * Grupo Familiar Asociado a un Beneficiario.
     */
    private GrupoFamiliarModeloDTO grupoFamiliarTrasladoBeneficiario;
    
    /**
     * Representa la fecha de defuncion
     */
    private Long fechaDefuncion;

    /**
     * Representa la oportunidad de aporte
     */
    private PeriodoPagoPlanillaEnum oportunidadAporte;

    /**
     * Genero beneficiario
     */
    private GeneroEnum generoBeneficiario;

    /**
     * Estado civil
     */
    private EstadoCivilEnum estadoCivilBeneficiario;

    /**
     * Fecha de reporte de la invalidez
     */
    private Long fechaReporteInvalidezBeneficiario;

    /**
     * Persona en condición de invalidez?
     */
    private Boolean condicionInvalidezBeneficiario;

    /**
     * Información de padre biologico
     */
    private PersonaModeloDTO padreBiologico;
    
    /**
     * Información de madre biologico
     */
    private PersonaModeloDTO madreBiologica;
    
    /**
     * Fecha de inicio de la condicion de invalidez asociada al beneficiario
     */
    private Long fechaInicioInvalidezHijo;
    
    /**
     * Fecha de inicio de la condicion de invalidez asociada al trabajador
     */
    private Long fechaInicioInvalidezTrabajador;
    
    /**
     * Fecha de inicio de la condicion de invalidez asociada al beneficiario padre
     */
    private Long fechaInicioInvalidezPadre;
    
    /**
	 * Orientacion sexual de la persona
	 */
	private OrientacionSexualEnum orientacionSexual;

	/**
	 * Factor de vulnerabilidad de la persona
	 */
	private FactorVulnerabilidadEnum factorVulnerabilidad;

	/**
	 * Pertenencia Etnica de la persona
	 */
	private PertenenciaEtnicaEnum pertenenciaEtnica;

	/**
	 * Sector de ubicacion de la Ubicación del Grupo Familiar.
	 */
	private SectorUbicacionEnum sectorUbicacion;
	
	/**
	 * Pais de residencia de la persona
	 */
	private Long paisResidencia;
	
	/**
     * Fecha de retiro Trabajador/Beneficiario
     */
    private Long fechaRetiro;

	private Long idResguardo;

	private Long idPuebloIndigena;

	private Long fechaInicioConyugeCuidadorTrabajador;

	private Long fechaInicioConyugeCuidadorHijo;

	private Long fechaFinConyugeCuidadorTrabajador;

	private Long fechaFinConyugeCuidadorHijo;

	private Boolean conyugeCuidadorTrabajador;

	private Boolean conyugeCuidadorHijo;

	private Long fechaInicioConyugeCuidadorPadre;

	private Long fechaFinConyugeCuidadorPadre;

	private Boolean conyugeCuidadorPadre;

	private Long idConyugeCuidador;

	private List<Long> rolesAfiliado;

	private Boolean excluirAfiliado;

	private Long periodoExclusion;

	private Long periodoInicioExclusion;
	
	private Long periodoInicioExclusionPadre;

	private Long periodoFinExclusion;

	private Long periodoFinExclusionPadre;

	private String comentariosInvalidez;

	public Long getPeriodoInicioExclusionPadre() {
		return this.periodoInicioExclusionPadre;
	}

	public void setPeriodoInicioExclusionPadre(Long periodoInicioExclusionPadre) {
		this.periodoInicioExclusionPadre = periodoInicioExclusionPadre;
	}

	public Long getPeriodoFinExclusionPadre() {
		return this.periodoFinExclusionPadre;
	}

	public void setPeriodoFinExclusionPadre(Long periodoFinExclusionPadre) {
		this.periodoFinExclusionPadre = periodoFinExclusionPadre;
	}

	public Boolean isExcluirAfiliado() {
		return this.excluirAfiliado;
	}

	public Boolean getExcluirAfiliado() {
		return this.excluirAfiliado;
	}

	public void setExcluirAfiliado(Boolean excluirAfiliado) {
		this.excluirAfiliado = excluirAfiliado;
	}

	public Long getPeriodoExclusion() {
		return this.periodoExclusion;
	}

	public void setPeriodoExclusion(Long periodoExclusion) {
		this.periodoExclusion = periodoExclusion;
	}

	public Long getPeriodoInicioExclusion() {
		return this.periodoInicioExclusion;
	}

	public void setPeriodoInicioExclusion(Long periodoInicioExclusion) {
		this.periodoInicioExclusion = periodoInicioExclusion;
	}

	public Long getPeriodoFinExclusion() {
		return this.periodoFinExclusion;
	}

	public void setPeriodoFinExclusion(Long periodoFinExclusion) {
		this.periodoFinExclusion = periodoFinExclusion;
	}

	/**
	 * Método que retorna el valor de idPersona.
	 * 
	 * @return valor de idPersona.
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * Método encargado de modificar el valor de idPersona.
	 * 
	 * @param valor
	 *            para modificar idPersona.
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * @return the idBeneficiario
	 */
	public Long getIdBeneficiario() {
		return idBeneficiario;
	}

	/**
	 * @param idBeneficiario the idBeneficiario to set
	 */
	public void setIdBeneficiario(Long idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}

	/**
	 * @return the idGrupoFamiliar
	 */
	public Long getIdGrupoFamiliar() {
		return idGrupoFamiliar;
	}

	/**
	 * @param idGrupoFamiliar the idGrupoFamiliar to set
	 */
	public void setIdGrupoFamiliar(Long idGrupoFamiliar) {
		this.idGrupoFamiliar = idGrupoFamiliar;
	}
	
	/**
	 * @return the idRolAfiliado
	 */
	public Long getIdRolAfiliado() {
		return idRolAfiliado;
	}

	/**
	 * @param idRolAfiliado the idRolAfiliado to set
	 */
	public void setIdRolAfiliado(Long idRolAfiliado) {
		this.idRolAfiliado = idRolAfiliado;
	}

	/**
	 * @return the numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * @param numeroIdentificacion the numeroIdentificacion to set
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * @return the tipoIdentificacion
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * @param tipoIdentificacion the tipoIdentificacion to set
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * Método que retorna el valor de tipoIdentificacionEmpleador
	 * 
	 * @return el valor de tipoIdentificacionEmpleador
	 */
	public TipoIdentificacionEnum getTipoIdentificacionEmpleador() {
		return tipoIdentificacionEmpleador;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacionEmpleador
	 * 
	 * @param valor
	 *            para modificar tipoIdentificacionEmpleador
	 */
	public void setTipoIdentificacionEmpleador(TipoIdentificacionEnum tipoIdentificacionEmpleador) {
		this.tipoIdentificacionEmpleador = tipoIdentificacionEmpleador;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacionEmpleador
	 * 
	 * @return el valor de numeroIdentificacionEmpleador
	 */
	public String getNumeroIdentificacionEmpleador() {
		return numeroIdentificacionEmpleador;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacionEmpleador
	 * 
	 * @param valor
	 *            para modificar numeroIdentificacionEmpleador
	 */
	public void setNumeroIdentificacionEmpleador(String numeroIdentificacionEmpleador) {
		this.numeroIdentificacionEmpleador = numeroIdentificacionEmpleador;
	}

	/**
	 * Método que retorna el valor de nombreRazonSocialEmpleador
	 * 
	 * @return el valor de nombreRazonSocialEmpleador
	 */
	public String getNombreRazonSocialEmpleador() {
		return nombreRazonSocialEmpleador;
	}

	/**
	 * Método encargado de modificar el valor de nombreRazonSocialEmpleador
	 * 
	 * @param valor
	 *            para modificar nombreRazonSocialEmpleador
	 */
	public void setNombreRazonSocialEmpleador(String nombreRazonSocialEmpleador) {
		this.nombreRazonSocialEmpleador = nombreRazonSocialEmpleador;
	}



	/**
	 * Método que retorna el valor de tipoIdentificacionTrabajador
	 * 
	 * @return el valor de tipoIdentificacionTrabajador
	 */
	public TipoIdentificacionEnum getTipoIdentificacionTrabajador() {
		return tipoIdentificacionTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacionTrabajador
	 * 
	 * @param valor
	 *            para modificar tipoIdentificacionTrabajador
	 */
	public void setTipoIdentificacionTrabajador(TipoIdentificacionEnum tipoIdentificacionTrabajador) {
		this.tipoIdentificacionTrabajador = tipoIdentificacionTrabajador;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacionTrabajador
	 * 
	 * @return el valor de numeroIdentificacionTrabajador
	 */
	public String getNumeroIdentificacionTrabajador() {
		return numeroIdentificacionTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacionTrabajador
	 * 
	 * @param valor
	 *            para modificar numeroIdentificacionTrabajador
	 */
	public void setNumeroIdentificacionTrabajador(String numeroIdentificacionTrabajador) {
		this.numeroIdentificacionTrabajador = numeroIdentificacionTrabajador;
	}

	/**
	 * Método que retorna el valor de primerApellidoTrabajador
	 * 
	 * @return el valor de primerApellidoTrabajador
	 */
	public String getPrimerApellidoTrabajador() {
		return primerApellidoTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de primerApellidoTrabajador
	 * 
	 * @param valor
	 *            para modificar primerApellidoTrabajador
	 */
	public void setPrimerApellidoTrabajador(String primerApellidoTrabajador) {
		this.primerApellidoTrabajador = primerApellidoTrabajador;
	}

	/**
	 * Método que retorna el valor de segundoApellidoTrabajador
	 * 
	 * @return el valor de segundoApellidoTrabajador
	 */
	public String getSegundoApellidoTrabajador() {
		return segundoApellidoTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de segundoApellidoTrabajador
	 * 
	 * @param valor
	 *            para modificar segundoApellidoTrabajador
	 */
	public void setSegundoApellidoTrabajador(String segundoApellidoTrabajador) {
		this.segundoApellidoTrabajador = segundoApellidoTrabajador;
	}

	/**
	 * Método que retorna el valor de primerNombreTrabajador
	 * 
	 * @return el valor de primerNombreTrabajador
	 */
	public String getPrimerNombreTrabajador() {
		return primerNombreTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de primerNombreTrabajador
	 * 
	 * @param valor
	 *            para modificar primerNombreTrabajador
	 */
	public void setPrimerNombreTrabajador(String primerNombreTrabajador) {
		this.primerNombreTrabajador = primerNombreTrabajador;
	}

	/**
	 * Método que retorna el valor de segundoNombreTrabajador
	 * 
	 * @return el valor de segundoNombreTrabajador
	 */
	public String getSegundoNombreTrabajador() {
		return segundoNombreTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de segundoNombreTrabajador
	 * 
	 * @param valor
	 *            para modificar segundoNombreTrabajador
	 */
	public void setSegundoNombreTrabajador(String segundoNombreTrabajador) {
		this.segundoNombreTrabajador = segundoNombreTrabajador;
	}


	/**
	 * Método que retorna el valor de numeroIdentificacionBeneficiario
	 * 
	 * @return el valor de numeroIdentificacionBeneficiario
	 */
	public String getNumeroIdentificacionBeneficiario() {
		return numeroIdentificacionBeneficiario;
	}

	/**
	 * Método encargado de modificar el valor de
	 * numeroIdentificacionBeneficiario
	 * 
	 * @param valor
	 *            para modificar numeroIdentificacionBeneficiario
	 */
	public void setNumeroIdentificacionBeneficiario(String numeroIdentificacionBeneficiario) {
		this.numeroIdentificacionBeneficiario = numeroIdentificacionBeneficiario;
	}

	/**
	 * Método que retorna el valor de primerApellidoBeneficiario
	 * 
	 * @return el valor de primerApellidoBeneficiario
	 */
	public String getPrimerApellidoBeneficiario() {
		return primerApellidoBeneficiario;
	}

	/**
	 * Método encargado de modificar el valor de primerApellidoBeneficiario
	 * 
	 * @param valor
	 *            para modificar primerApellidoBeneficiario
	 */
	public void setPrimerApellidoBeneficiario(String primerApellidoBeneficiario) {
		this.primerApellidoBeneficiario = primerApellidoBeneficiario;
	}

	/**
	 * Método que retorna el valor de segundoApellidoBeneficiario
	 * 
	 * @return el valor de segundoApellidoBeneficiario
	 */
	public String getSegundoApellidoBeneficiario() {
		return segundoApellidoBeneficiario;
	}

	/**
	 * Método encargado de modificar el valor de segundoApellidoBeneficiario
	 * 
	 * @param valor
	 *            para modificar segundoApellidoBeneficiario
	 */
	public void setSegundoApellidoBeneficiario(String segundoApellidoBeneficiario) {
		this.segundoApellidoBeneficiario = segundoApellidoBeneficiario;
	}

	/**
	 * Método que retorna el valor de primerNombreBeneficiario
	 * 
	 * @return el valor de primerNombreBeneficiario
	 */
	public String getPrimerNombreBeneficiario() {
		return primerNombreBeneficiario;
	}

	/**
	 * Método encargado de modificar el valor de primerNombreBeneficiario
	 * 
	 * @param valor
	 *            para modificar primerNombreBeneficiario
	 */
	public void setPrimerNombreBeneficiario(String primerNombreBeneficiario) {
		this.primerNombreBeneficiario = primerNombreBeneficiario;
	}

	/**
	 * Método que retorna el valor de segundoNombreBeneficiario
	 * 
	 * @return el valor de segundoNombreBeneficiario
	 */
	public String getSegundoNombreBeneficiario() {
		return segundoNombreBeneficiario;
	}

	/**
	 * Método encargado de modificar el valor de segundoNombreBeneficiario
	 * 
	 * @param valor
	 *            para modificar segundoNombreBeneficiario
	 */
	public void setSegundoNombreBeneficiario(String segundoNombreBeneficiario) {
		this.segundoNombreBeneficiario = segundoNombreBeneficiario;
	}

	/**
	 * Método que retorna el valor de clasificacion
	 * 
	 * @return el valor de clasificacion
	 */
	public ClasificacionEnum getClasificacion() {
		return clasificacion;
	}

	/**
	 * Método encargado de modificar el valor declasificacion
	 * 
	 * @param valor
	 *            para modificar clasificacion
	 */
	public void setClasificacion(ClasificacionEnum clasificacion) {
		this.clasificacion = clasificacion;
	}

	/**
	 * Método que retorna el valor de fechaExpedicionDocumentoTrabajador
	 * 
	 * @return el valor de fechaExpedicionDocumentoTrabajador
	 */
	public Long getFechaExpedicionDocumentoTrabajador() {
		return fechaExpedicionDocumentoTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de
	 * fechaExpedicionDocumentoTrabajador
	 * 
	 * @param valor
	 *            para modificar fechaExpedicionDocumentoTrabajador
	 */
	public void setFechaExpedicionDocumentoTrabajador(Long fechaExpedicionDocumentoTrabajador) {
		this.fechaExpedicionDocumentoTrabajador = fechaExpedicionDocumentoTrabajador;
	}

	/**
	 * Método que retorna el valor de generoTrabajador
	 * 
	 * @return el valor de generoTrabajador
	 */
	public GeneroEnum getGeneroTrabajador() {
		return generoTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de generoTrabajador
	 * 
	 * @param valor
	 *            para modificar generoTrabajador
	 */
	public void setGeneroTrabajador(GeneroEnum generoTrabajador) {
		this.generoTrabajador = generoTrabajador;
	}

	/**
	 * Método que retorna el valor de fechaNacimientoTrabajador
	 * 
	 * @return el valor de fechaNacimientoTrabajador
	 */
	public Long getFechaNacimientoTrabajador() {
		return fechaNacimientoTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de fechaNacimientoTrabajador
	 * 
	 * @param valor
	 *            para modificar fechaNacimientoTrabajador
	 */
	public void setFechaNacimientoTrabajador(Long fechaNacimientoTrabajador) {
		this.fechaNacimientoTrabajador = fechaNacimientoTrabajador;
	}

	/**
	 * Método que retorna el valor de fechaNacimientoBeneficiario
	 * 
	 * @return el valor de fechaNacimientoBeneficiario
	 */
	public Long getFechaNacimientoBeneficiario() {
		return fechaNacimientoBeneficiario;
	}

	/**
	 * Método encargado de modificar el valor de fechaNacimientoBeneficiario
	 * 
	 * @param valor
	 *            para modificar fechaNacimientoBeneficiario
	 */
	public void setFechaNacimientoBeneficiario(Long fechaNacimientoBeneficiario) {
		this.fechaNacimientoBeneficiario = fechaNacimientoBeneficiario;
	}

	/**
	 * Método que retorna el valor de nivelEducativoTrabajador
	 * 
	 * @return el valor de nivelEducativoTrabajador
	 */
	public NivelEducativoEnum getNivelEducativoTrabajador() {
		return nivelEducativoTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de nivelEducativoTrabajador
	 * 
	 * @param valor
	 *            para modificar nivelEducativoTrabajador
	 */
	public void setNivelEducativoTrabajador(NivelEducativoEnum nivelEducativoTrabajador) {
		this.nivelEducativoTrabajador = nivelEducativoTrabajador;
	}

	/**
	 * Método que retorna el valor de ocupacionProfesionTrabajador
	 * 
	 * @return el valor de ocupacionProfesionTrabajador
	 */
	public OcupacionProfesion getOcupacionProfesionTrabajador() {
		return ocupacionProfesionTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de ocupacionProfesionTrabajador
	 * 
	 * @param valor
	 *            para modificar ocupacionProfesionTrabajador
	 */
	public void setOcupacionProfesionTrabajador(OcupacionProfesion ocupacionProfesionTrabajador) {
		this.ocupacionProfesionTrabajador = ocupacionProfesionTrabajador;
	}

	/**
	 * Método que retorna el valor de cabezaHogar
	 * 
	 * @return el valor de cabezaHogar
	 */
	public Boolean getCabezaHogar() {
		return cabezaHogar;
	}

	/**
	 * Método encargado de modificar el valor cabezaHogarde
	 * 
	 * @param valor
	 *            para modificar cabezaHogar
	 */
	public void setCabezaHogar(Boolean cabezaHogar) {
		this.cabezaHogar = cabezaHogar;
	}

	/**
	 * Método que retorna el valor de departamentoTrabajador
	 * 
	 * @return el valor de departamentoTrabajador
	 */
	public Departamento getDepartamentoTrabajador() {
		return departamentoTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de departamentoTrabajador
	 * 
	 * @param valor
	 *            para modificar departamentoTrabajador
	 */
	public void setDepartamentoTrabajador(Departamento departamentoTrabajador) {
		this.departamentoTrabajador = departamentoTrabajador;
	}

	/**
	 * Método que retorna el valor de municipioTrabajador
	 * 
	 * @return el valor de municipioTrabajador
	 */
	public Municipio getMunicipioTrabajador() {
		return municipioTrabajador;
	}

	/**
	 * Método que retorna el valor de tipoSolicitanteTrabajador.
	 * @return valor de tipoSolicitanteTrabajador.
	 */
	public TipoAfiliadoEnum getTipoSolicitanteTrabajador() {
		return tipoSolicitanteTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de tipoSolicitanteTrabajador.
	 * @param valor para modificar tipoSolicitanteTrabajador.
	 */
	public void setTipoSolicitanteTrabajador(TipoAfiliadoEnum tipoSolicitanteTrabajador) {
		this.tipoSolicitanteTrabajador = tipoSolicitanteTrabajador;
	}

	/**
	 * Método que retorna el valor de tipoIdentificacionBeneficiario.
	 * @return valor de tipoIdentificacionBeneficiario.
	 */
	public TipoIdentificacionEnum getTipoIdentificacionBeneficiario() {
		return tipoIdentificacionBeneficiario;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacionBeneficiario.
	 * @param valor para modificar tipoIdentificacionBeneficiario.
	 */
	public void setTipoIdentificacionBeneficiario(TipoIdentificacionEnum tipoIdentificacionBeneficiario) {
		this.tipoIdentificacionBeneficiario = tipoIdentificacionBeneficiario;
	}

	/**
	 * Método encargado de modificar el valor de municipioTrabajador
	 * 
	 * @param valor
	 *            para modificar municipioTrabajador
	 */
	public void setMunicipioTrabajador(Municipio municipioTrabajador) {
		this.municipioTrabajador = municipioTrabajador;
	}

	/**
	 * Método que retorna el valor de direccionResidenciaTrabajador
	 * 
	 * @return el valor de direccionResidenciaTrabajador
	 */
	public String getDireccionResidenciaTrabajador() {
		return direccionResidenciaTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de direccionResidenciaTrabajador
	 * 
	 * @param valor
	 *            para modificar direccionResidenciaTrabajador
	 */
	public void setDireccionResidenciaTrabajador(String direccionResidenciaTrabajador) {
		this.direccionResidenciaTrabajador = direccionResidenciaTrabajador;
	}

	/**
	 * Método que retorna el valor de viveEnCasaPropia
	 * 
	 * @return el valor de viveEnCasaPropia
	 */
	public Boolean getViveEnCasaPropia() {
		return viveEnCasaPropia;
	}

	/**
	 * Método encargado de modificar el valor de viveEnCasaPropia
	 * 
	 * @param valor
	 *            para modificar viveEnCasaPropia
	 */
	public void setViveEnCasaPropia(Boolean viveEnCasaPropia) {
		this.viveEnCasaPropia = viveEnCasaPropia;
	}

	/**
	 * Método que retorna el valor de codigoPostalTrabajador
	 * 
	 * @return el valor de codigoPostalTrabajador
	 */
	public String getCodigoPostalTrabajador() {
		return codigoPostalTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de codigoPostalTrabajador
	 * 
	 * @param valor
	 *            para modificar codigoPostalTrabajador
	 */
	public void setCodigoPostalTrabajador(String codigoPostalTrabajador) {
		this.codigoPostalTrabajador = codigoPostalTrabajador;
	}

	/**
	 * Método que retorna el valor de indicativoTelFijoTrabajador
	 * 
	 * @return el valor de indicativoTelFijoTrabajador
	 */
	public String getIndicativoTelFijoTrabajador() {
		return indicativoTelFijoTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de indicativoTelFijoTrabajador
	 * 
	 * @param valor
	 *            para modificar indicativoTelFijoTrabajador
	 */
	public void setIndicativoTelFijoTrabajador(String indicativoTelFijoTrabajador) {
		this.indicativoTelFijoTrabajador = indicativoTelFijoTrabajador;
	}

	/**
	 * Método que retorna el valor de telefonoFijoTrabajador
	 * 
	 * @return el valor de telefonoFijoTrabajador
	 */
	public String getTelefonoFijoTrabajador() {
		return telefonoFijoTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de telefonoFijoTrabajador
	 * 
	 * @param valor
	 *            para modificar telefonoFijoTrabajador
	 */
	public void setTelefonoFijoTrabajador(String telefonoFijoTrabajador) {
		this.telefonoFijoTrabajador = telefonoFijoTrabajador;
	}

	/**
	 * Método que retorna el valor de telefonoCelularTrabajador
	 * 
	 * @return el valor de telefonoCelularTrabajador
	 */
	public String getTelefonoCelularTrabajador() {
		return telefonoCelularTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de telefonoCelularTrabajador
	 * 
	 * @param valor
	 *            para modificar telefonoCelularTrabajador
	 */
	public void setTelefonoCelularTrabajador(String telefonoCelularTrabajador) {
		this.telefonoCelularTrabajador = telefonoCelularTrabajador;
	}

	/**
	 * Método que retorna el valor de correoElectronicoTrabajador
	 * 
	 * @return el valor de correoElectronicoTrabajador
	 */
	public String getCorreoElectronicoTrabajador() {
		return correoElectronicoTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de correoElectronicoTrabajador
	 * 
	 * @param valor
	 *            para modificar correoElectronicoTrabajador
	 */
	public void setCorreoElectronicoTrabajador(String correoElectronicoTrabajador) {
		this.correoElectronicoTrabajador = correoElectronicoTrabajador;
	}

	/**
	 * Método que retorna el valor de
	 * autorizacionEnvioCorreoElectronicoTrabajador
	 * 
	 * @return el valor de autorizacionEnvioCorreoElectronicoTrabajador
	 */
	public Boolean getAutorizacionEnvioCorreoElectronicoTrabajador() {
		return autorizacionEnvioCorreoElectronicoTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de
	 * autorizacionEnvioCorreoElectronicoTrabajador
	 * 
	 * @param valor
	 *            para modificar autorizacionEnvioCorreoElectronicoTrabajador
	 */
	public void setAutorizacionEnvioCorreoElectronicoTrabajador(Boolean autorizacionEnvioCorreoElectronicoTrabajador) {
		this.autorizacionEnvioCorreoElectronicoTrabajador = autorizacionEnvioCorreoElectronicoTrabajador;
	}

	/**
	 * Método que retorna el valor de autorizaUtilizarDatosPersonales
	 * 
	 * @return el valor de autorizaUtilizarDatosPersonales
	 */
	public Boolean getAutorizaUtilizarDatosPersonales() {
		return autorizaUtilizarDatosPersonales;
	}

	/**
	 * Método encargado de modificar el valor de autorizaUtilizarDatosPersonales
	 * 
	 * @param valor
	 *            para modificar autorizaUtilizarDatosPersonales
	 */
	public void setAutorizaUtilizarDatosPersonales(Boolean autorizaUtilizarDatosPersonales) {
		this.autorizaUtilizarDatosPersonales = autorizaUtilizarDatosPersonales;
	}

	/**
	 * Método que retorna el valor de resideEnSectorRural
	 * 
	 * @return el valor de resideEnSectorRural
	 */
	public Boolean getResideEnSectorRural() {
		return resideEnSectorRural;
	}

	/**
	 * Método encargado de modificar el valor de resideEnSectorRural
	 * 
	 * @param valor
	 *            para modificar resideEnSectorRural
	 */
	public void setResideEnSectorRural(Boolean resideEnSectorRural) {
		this.resideEnSectorRural = resideEnSectorRural;
	}

	/**
	 * Método que retorna el valor de disponeDeTarjeta
	 * 
	 * @return el valor de disponeDeTarjeta
	 */
	public Boolean getDisponeDeTarjeta() {
		return disponeDeTarjeta;
	}

	/**
	 * Método encargado de modificar el valor de disponeDeTarjeta
	 * 
	 * @param valor
	 *            para modificar disponeDeTarjeta
	 */
	public void setDisponeDeTarjeta(Boolean disponeDeTarjeta) {
		this.disponeDeTarjeta = disponeDeTarjeta;
	}

	/**
	 * Método que retorna el valor de emitirTarjeta
	 * 
	 * @return el valor de emitirTarjeta
	 */
	public Boolean getEmitirTarjeta() {
		return emitirTarjeta;
	}

	/**
	 * Método encargado de modificar el valor deemitirTarjeta
	 * 
	 * @param valor
	 *            para modificar emitirTarjeta
	 */
	public void setEmitirTarjeta(Boolean emitirTarjeta) {
		this.emitirTarjeta = emitirTarjeta;
	}

	/**
	 * Método que retorna el valor de emitirInmediatamente
	 * 
	 * @return el valor de emitirInmediatamente
	 */
	public Boolean getEmitirInmediatamente() {
		return emitirInmediatamente;
	}

	/**
	 * Método encargado de modificar el valor de emitirInmediatamente
	 * 
	 * @param valor
	 *            para modificar emitirInmediatamente
	 */
	public void setEmitirInmediatamente(Boolean emitirInmediatamente) {
		this.emitirInmediatamente = emitirInmediatamente;
	}

	/**
	 * Método que retorna el valor de claseTrabajador
	 * 
	 * @return el valor de claseTrabajador
	 */
	public ClaseTrabajadorEnum getClaseTrabajador(){
		return claseTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de claseTrabajador
	 * 
	 * @param valor
	 *            para modificar claseTrabajador
	 */
	public void setClaseTrabajador(ClaseTrabajadorEnum claseTrabajador) {
		this.claseTrabajador = claseTrabajador;
	}

	/**
	 * Método que retorna el valor de fechaInicioLaboresConEmpleador
	 * 
	 * @return el valor de fechaInicioLaboresConEmpleador
	 */
	public Long getFechaInicioLaboresConEmpleador() {
		return fechaInicioLaboresConEmpleador;
	}

	/**
	 * Método encargado de modificar el valor de fechaInicioLaboresConEmpleador
	 * 
	 * @param valor
	 *            para modificar fechaInicioLaboresConEmpleador
	 */
	public void setFechaInicioLaboresConEmpleador(Long fechaInicioLaboresConEmpleador) {
		this.fechaInicioLaboresConEmpleador = fechaInicioLaboresConEmpleador;
	}

	/**
	 * Método que retorna el valor de tipoSalarioTrabajador
	 * 
	 * @return el valor de tipoSalarioTrabajador
	 */
	public TipoSalarioEnum getTipoSalarioTrabajador() {
		return tipoSalarioTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de tipoSalarioTrabajador
	 * 
	 * @param valor
	 *            para modificar tipoSalarioTrabajador
	 */
	public void setTipoSalarioTrabajador(TipoSalarioEnum tipoSalarioTrabajador) {
		this.tipoSalarioTrabajador = tipoSalarioTrabajador;
	}

	/**
	 * Método que retorna el valor de valorSalarioMensualTrabajador
	 * 
	 * @return el valor de valorSalarioMensualTrabajador
	 */
	public BigDecimal getValorSalarioMensualTrabajador() {
		return valorSalarioMensualTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de valorSalarioMensualTrabajador
	 * 
	 * @param valor
	 *            para modificar valorSalarioMensualTrabajador
	 */
	public void setValorSalarioMensualTrabajador(BigDecimal valorSalarioMensualTrabajador) {
		this.valorSalarioMensualTrabajador = valorSalarioMensualTrabajador;
	}

	/**
	 * Método que retorna el valor de horasLaboralesMesTrabajador
	 * 
	 * @return el valor de horasLaboralesMesTrabajador
	 */
	public Integer getHorasLaboralesMesTrabajador() {
		return horasLaboralesMesTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de horasLaboralesMesTrabajador
	 * 
	 * @param valor
	 *            para modificar horasLaboralesMesTrabajador
	 */
	public void setHorasLaboralesMesTrabajador(Integer horasLaboralesMesTrabajador) {
		this.horasLaboralesMesTrabajador = horasLaboralesMesTrabajador;
	}

	/**
	 * Método que retorna el valor de cargoOficioDesempeniadoTrabajador
	 * 
	 * @return el valor de cargoOficioDesempeniadoTrabajador
	 */
	public String getCargoOficioDesempeniadoTrabajador() {
		return cargoOficioDesempeniadoTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de
	 * cargoOficioDesempeniadoTrabajador
	 * 
	 * @param valor
	 *            para modificar cargoOficioDesempeniadoTrabajador
	 */
	public void setCargoOficioDesempeniadoTrabajador(String cargoOficioDesempeniadoTrabajador) {
		this.cargoOficioDesempeniadoTrabajador = cargoOficioDesempeniadoTrabajador;
	}

	/**
	 * Método que retorna el valor de tipoContratoLaboralTrabajador
	 * 
	 * @return el valor de tipoContratoLaboralTrabajador
	 */
	public TipoContratoEnum getTipoContratoLaboralTrabajador() {
		return tipoContratoLaboralTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de tipoContratoLaboralTrabajador
	 * 
	 * @param valor
	 *            para modificar tipoContratoLaboralTrabajador
	 */
	public void setTipoContratoLaboralTrabajador(TipoContratoEnum tipoContratoLaboralTrabajador) {
		this.tipoContratoLaboralTrabajador = tipoContratoLaboralTrabajador;
	}

	/**
	 * Método que retorna el valor de fechaTerminacioContratoTrabajador
	 * 
	 * @return el valor de fechaTerminacioContratoTrabajador
	 */
	public Long getFechaTerminacioContratoTrabajador() {
		return fechaTerminacioContratoTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de
	 * fechaTerminacioContratoTrabajador
	 * 
	 * @param valor
	 *            para modificar fechaTerminacioContratoTrabajador
	 */
	public void setFechaTerminacioContratoTrabajador(Long fechaTerminacioContratoTrabajador) {
		this.fechaTerminacioContratoTrabajador = fechaTerminacioContratoTrabajador;
	}

	/**
	 * Método que retorna el valor de trabajadorMismoReptLegalDelEpleador
	 * 
	 * @return el valor de trabajadorMismoReptLegalDelEpleador
	 */
	public String getTrabajadorMismoReptLegalDelEpleador() {
		return trabajadorMismoReptLegalDelEpleador;
	}

	/**
	 * Método encargado de modificar el valor de
	 * trabajadorMismoReptLegalDelEpleador
	 * 
	 * @param valor
	 *            para modificar trabajadorMismoReptLegalDelEpleador
	 */
	public void setTrabajadorMismoReptLegalDelEpleador(String trabajadorMismoReptLegalDelEpleador) {
		this.trabajadorMismoReptLegalDelEpleador = trabajadorMismoReptLegalDelEpleador;
	}

	/**
	 * Método que retorna el valor de trabajadorEsSocioDelEmpleador
	 * 
	 * @return el valor de trabajadorEsSocioDelEmpleador
	 */
	public String getTrabajadorEsSocioDelEmpleador() {
		return trabajadorEsSocioDelEmpleador;
	}

	/**
	 * Método encargado de modificar el valor de trabajadorEsSocioDelEmpleador
	 * 
	 * @param valor
	 *            para modificar trabajadorEsSocioDelEmpleador
	 */
	public void setTrabajadorEsSocioDelEmpleador(String trabajadorEsSocioDelEmpleador) {
		this.trabajadorEsSocioDelEmpleador = trabajadorEsSocioDelEmpleador;
	}

	/**
	 * Método que retorna el valor de trabajadorEsConyugeDelSocioDelEmpleador
	 * 
	 * @return el valor de trabajadorEsConyugeDelSocioDelEmpleador
	 */
	public String getTrabajadorEsConyugeDelSocioDelEmpleador() {
		return trabajadorEsConyugeDelSocioDelEmpleador;
	}

	/**
	 * Método encargado de modificar el valor de
	 * trabajadorEsConyugeDelSocioDelEmpleador
	 * 
	 * @param valor
	 *            para modificar trabajadorEsConyugeDelSocioDelEmpleador
	 */
	public void setTrabajadorEsConyugeDelSocioDelEmpleador(String trabajadorEsConyugeDelSocioDelEmpleador) {
		this.trabajadorEsConyugeDelSocioDelEmpleador = trabajadorEsConyugeDelSocioDelEmpleador;
	}

	/**
	 * Método que retorna el valor de trabajadorInhabilitadoParaSubsidio
	 * 
	 * @return el valor de trabajadorInhabilitadoParaSubsidio
	 */
	public Boolean getTrabajadorInhabilitadoParaSubsidio() {
		return trabajadorInhabilitadoParaSubsidio;
	}

	/**
	 * Método encargado de modificar el valor de
	 * trabajadorInhabilitadoParaSubsidio
	 * 
	 * @param valor
	 *            para modificar trabajadorInhabilitadoParaSubsidio
	 */
	public void setTrabajadorInhabilitadoParaSubsidio(Boolean trabajadorInhabilitadoParaSubsidio) {
		this.trabajadorInhabilitadoParaSubsidio = trabajadorInhabilitadoParaSubsidio;
	}

	/**
	 * Método que retorna el valor de sucursalEmpleadorTrabajador
	 * 
	 * @return el valor de sucursalEmpleadorTrabajador
	 */
	public SucursalEmpresa getSucursalEmpleadorTrabajador() {
		return sucursalEmpleadorTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de sucursalEmpleadorTrabajador
	 * 
	 * @param valor
	 *            para modificar sucursalEmpleadorTrabajador
	 */
	public void setSucursalEmpleadorTrabajador(SucursalEmpresa sucursalEmpleadorTrabajador) {
		this.sucursalEmpleadorTrabajador = sucursalEmpleadorTrabajador;
	}

	/**
	 * Método que retorna el valor de claseIndependiente
	 * 
	 * @return el valor de claseIndependiente
	 */
	public ClaseIndependienteEnum getClaseIndependiente() {
		return claseIndependiente;
	}

	/**
	 * Método encargado de modificar el valor de claseIndependiente
	 * 
	 * @param valor
	 *            para modificar claseIndependiente
	 */
	public void setClaseIndependiente(ClaseIndependienteEnum claseIndependiente) {
		this.claseIndependiente = claseIndependiente;
	}

	/**
	 * Método que retorna el valor de ingresosMensualesIndependiente
	 * 
	 * @return el valor de ingresosMensualesIndependiente
	 */
	public BigDecimal getIngresosMensualesIndependiente() {
		return ingresosMensualesIndependiente;
	}

	/**
	 * Método encargado de modificar el valor de ingresosMensualesIndependiente
	 * 
	 * @param valor
	 *            para modificar ingresosMensualesIndependiente
	 */
	public void setIngresosMensualesIndependiente(BigDecimal ingresosMensualesIndependiente) {
		this.ingresosMensualesIndependiente = ingresosMensualesIndependiente;
	}

	/**
	 * Método que retorna el valor de entidadPagadoraDeAportes
	 * 
	 * @return el valor de entidadPagadoraDeAportes
	 */
	public EntidadPagadora getEntidadPagadoraDeAportes() {
		return entidadPagadoraDeAportes;
	}

	/**
	 * Método encargado de modificar el valor de entidadPagadoraDeAportes
	 * 
	 * @param valor
	 *            para modificar entidadPagadoraDeAportes
	 */
	public void setEntidadPagadoraDeAportes(EntidadPagadora entidadPagadoraDeAportes) {
		this.entidadPagadoraDeAportes = entidadPagadoraDeAportes;
	}

	/**
	 * Método que retorna el valor de valorMesadaPensional
	 * 
	 * @return el valor de valorMesadaPensional
	 */
	public BigDecimal getValorMesadaPensional() {
		return valorMesadaPensional;
	}

	/**
	 * Método encargado de modificar el valor de valorMesadaPensional
	 * 
	 * @param valor
	 *            para modificar valorMesadaPensional
	 */
	public void setValorMesadaPensional(BigDecimal valorMesadaPensional) {
		this.valorMesadaPensional = valorMesadaPensional;
	}

	/**
	 * Método que retorna el valor de idEntidadPagadora
	 * 
	 * @return el valor de idEntidadPagadora
	 */
	public String getIdEntidadPagadora() {
		return idEntidadPagadora;
	}

	/**
	 * Método encargado de modificar el valor de idEntidadPagadora
	 * 
	 * @param valor
	 *            para modificar idEntidadPagadora
	 */
	public void setIdEntidadPagadora(String idEntidadPagadora) {
		this.idEntidadPagadora = idEntidadPagadora;
	}

	/**
	 * Método que retorna el valor de estadoCivilTrabajador
	 * 
	 * @return el valor de estadoCivilTrabajador
	 */
	public EstadoCivilEnum getEstadoCivilTrabajador() {
		return estadoCivilTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de estadoCivilTrabajador
	 * 
	 * @param valor
	 *            para modificar estadoCivilTrabajador
	 */
	public void setEstadoCivilTrabajador(EstadoCivilEnum estadoCivilTrabajador) {
		this.estadoCivilTrabajador = estadoCivilTrabajador;
	}

	/**
	 * Método que retorna el valor de mismaDireccionAfiliadoPrincipalGrupoFam
	 * 
	 * @return el valor de mismaDireccionAfiliadoPrincipalGrupoFam
	 */
	public Boolean getMismaDireccionAfiliadoPrincipalGrupoFam() {
		return mismaDireccionAfiliadoPrincipalGrupoFam;
	}

	/**
	 * Método encargado de modificar el valor de
	 * mismaDireccionAfiliadoPrincipalGrupoFam
	 * 
	 * @param valor
	 *            para modificar mismaDireccionAfiliadoPrincipalGrupoFam
	 */
	public void setMismaDireccionAfiliadoPrincipalGrupoFam(Boolean mismaDireccionAfiliadoPrincipalGrupoFam) {
		this.mismaDireccionAfiliadoPrincipalGrupoFam = mismaDireccionAfiliadoPrincipalGrupoFam;
	}

	/**
	 * Método que retorna el valor de departamentoResidenciaGrupoFam
	 * 
	 * @return el valor de departamentoResidenciaGrupoFam
	 */
	public Object getDepartamentoResidenciaGrupoFam() {
		return departamentoResidenciaGrupoFam;
	}

	/**
	 * Método encargado de modificar el valor de departamentoResidenciaGrupoFam
	 * 
	 * @param valor
	 *            para modificar departamentoResidenciaGrupoFam
	 */
	public void setDepartamentoResidenciaGrupoFam(Object departamentoResidenciaGrupoFam) {
		this.departamentoResidenciaGrupoFam = departamentoResidenciaGrupoFam;
	}

	/**
	 * Método que retorna el valor de municipioResidenciaGrupoFam
	 * 
	 * @return el valor de municipioResidenciaGrupoFam
	 */
	public Municipio getMunicipioResidenciaGrupoFam() {
		return municipioResidenciaGrupoFam;
	}

	/**
	 * Método encargado de modificar el valor de municipioResidenciaGrupoFam
	 * 
	 * @param valor
	 *            para modificar municipioResidenciaGrupoFam
	 */
	public void setMunicipioResidenciaGrupoFam(Municipio municipioResidenciaGrupoFam) {
		this.municipioResidenciaGrupoFam = municipioResidenciaGrupoFam;
	}

	/**
	 * Método que retorna el valor de direccionResidenciaGrupoFam
	 * 
	 * @return el valor de direccionResidenciaGrupoFam
	 */
	public String getDireccionResidenciaGrupoFam() {
		return direccionResidenciaGrupoFam;
	}

	/**
	 * Método encargado de modificar el valor de direccionResidenciaGrupoFam
	 * 
	 * @param valor
	 *            para modificar direccionResidenciaGrupoFam
	 */
	public void setDireccionResidenciaGrupoFam(String direccionResidenciaGrupoFam) {
		this.direccionResidenciaGrupoFam = direccionResidenciaGrupoFam;
	}

	/**
	 * Método que retorna el valor de codigoPostalGrupoFam
	 * 
	 * @return el valor de codigoPostalGrupoFam
	 */
	public String getCodigoPostalGrupoFam() {
		return codigoPostalGrupoFam;
	}

	/**
	 * Método encargado de modificar el valor de codigoPostalGrupoFam
	 * 
	 * @param valor
	 *            para modificar codigoPostalGrupoFam
	 */
	public void setCodigoPostalGrupoFam(String codigoPostalGrupoFam) {
		this.codigoPostalGrupoFam = codigoPostalGrupoFam;
	}

	/**
	 * Método que retorna el valor de indicativoTelFijoGrupoFam
	 * 
	 * @return el valor de indicativoTelFijoGrupoFam
	 */
	public String getIndicativoTelFijoGrupoFam() {
		return indicativoTelFijoGrupoFam;
	}

	/**
	 * Método encargado de modificar el valor de indicativoTelFijoGrupoFam
	 * 
	 * @param valor
	 *            para modificar indicativoTelFijoGrupoFam
	 */
	public void setIndicativoTelFijoGrupoFam(String indicativoTelFijoGrupoFam) {
		this.indicativoTelFijoGrupoFam = indicativoTelFijoGrupoFam;
	}

	/**
	 * Método que retorna el valor de telefonoFijoGrupoFam
	 * 
	 * @return el valor de telefonoFijoGrupoFam
	 */
	public String getTelefonoFijoGrupoFam() {
		return telefonoFijoGrupoFam;
	}

	/**
	 * Método encargado de modificar el valor de telefonoFijoGrupoFam
	 * 
	 * @param valor
	 *            para modificar telefonoFijoGrupoFam
	 */
	public void setTelefonoFijoGrupoFam(String telefonoFijoGrupoFam) {
		this.telefonoFijoGrupoFam = telefonoFijoGrupoFam;
	}

	/**
	 * Método que retorna el valor de telCelularGrupoFam
	 * 
	 * @return el valor de telCelularGrupoFam
	 */
	public String getTelCelularGrupoFam() {
		return telCelularGrupoFam;
	}

	/**
	 * Método encargado de modificar el valor de telCelularGrupoFam
	 * 
	 * @param valor
	 *            para modificar telCelularGrupoFam
	 */
	public void setTelCelularGrupoFam(String telCelularGrupoFam) {
		this.telCelularGrupoFam = telCelularGrupoFam;
	}

	/**
	 * Método que retorna el valor de emailGrupoFam
	 * 
	 * @return el valor de emailGrupoFam
	 */
	public String getEmailGrupoFam() {
		return emailGrupoFam;
	}

	/**
	 * Método encargado de modificar el valor deemailGrupoFam
	 * 
	 * @param valor
	 *            para modificar emailGrupoFam
	 */
	public void setEmailGrupoFam(String emailGrupoFam) {
		this.emailGrupoFam = emailGrupoFam;
	}

	/**
	 * Método que retorna el valor de afiliadoPpalMismoAdminDeSubsidio
	 * 
	 * @return el valor de afiliadoPpalMismoAdminDeSubsidio
	 */
	public Boolean getAfiliadoPpalMismoAdminDeSubsidio() {
		return afiliadoPpalMismoAdminDeSubsidio;
	}

	/**
	 * Método encargado de modificar el valor de
	 * afiliadoPpalMismoAdminDeSubsidio
	 * 
	 * @param valor
	 *            para modificar afiliadoPpalMismoAdminDeSubsidio
	 */
	public void setAfiliadoPpalMismoAdminDeSubsidio(Boolean afiliadoPpalMismoAdminDeSubsidio) {
		this.afiliadoPpalMismoAdminDeSubsidio = afiliadoPpalMismoAdminDeSubsidio;
	}

	/**
	 * Método que retorna el valor de fechaExpedicionDocConyuge
	 * 
	 * @return el valor de fechaExpedicionDocConyuge
	 */
	public Long getFechaExpedicionDocConyuge() {
		return fechaExpedicionDocConyuge;
	}

	/**
	 * Método encargado de modificar el valor de fechaExpedicionDocConyuge
	 * 
	 * @param valor
	 *            para modificar fechaExpedicionDocConyuge
	 */
	public void setFechaExpedicionDocConyuge(Long fechaExpedicionDocConyuge) {
		this.fechaExpedicionDocConyuge = fechaExpedicionDocConyuge;
	}

	/**
	 * Método que retorna el valor de generoConyuge
	 * 
	 * @return el valor de generoConyuge
	 */
	public GeneroEnum getGeneroConyuge() {
		return generoConyuge;
	}

	/**
	 * Método encargado de modificar el valor degeneroConyuge
	 * 
	 * @param valor
	 *            para modificar generoConyuge
	 */
	public void setGeneroConyuge(GeneroEnum generoConyuge) {
		this.generoConyuge = generoConyuge;
	}

	/**
	 * Método que retorna el valor de fechaNacimientoConyuge
	 * 
	 * @return el valor de fechaNacimientoConyuge
	 */
	public Long getFechaNacimientoConyuge() {
		return fechaNacimientoConyuge;
	}

	/**
	 * Método encargado de modificar el valor de fechaNacimientoConyuge
	 * 
	 * @param valor
	 *            para modificar fechaNacimientoConyuge
	 */
	public void setFechaNacimientoConyuge(Long fechaNacimientoConyuge) {
		this.fechaNacimientoConyuge = fechaNacimientoConyuge;
	}

	/**
	 * Método que retorna el valor de nivelEducativoConyuge
	 * 
	 * @return el valor de nivelEducativoConyuge
	 */
	public NivelEducativoEnum getNivelEducativoConyuge() {
		return nivelEducativoConyuge;
	}

	/**
	 * Método encargado de modificar el valor de nivelEducativoConyuge
	 * 
	 * @param valor
	 *            para modificar nivelEducativoConyuge
	 */
	public void setNivelEducativoConyuge(NivelEducativoEnum nivelEducativoConyuge) {
		this.nivelEducativoConyuge = nivelEducativoConyuge;
	}

	/**
	 * Método que retorna el valor de profesionConyuge
	 * 
	 * @return el valor de profesionConyuge
	 */
	public OcupacionProfesion getProfesionConyuge() {
		return profesionConyuge;
	}

	/**
	 * Método encargado de modificar el valor de profesionConyuge
	 * 
	 * @param valor
	 *            para modificar profesionConyuge
	 */
	public void setProfesionConyuge(OcupacionProfesion profesionConyuge) {
		this.profesionConyuge = profesionConyuge;
	}

	/**
	 * Método que retorna el valor de conyugeLabora
	 * 
	 * @return el valor de conyugeLabora
	 */
	public Boolean getConyugeLabora() {
		return conyugeLabora;
	}

	/**
	 * Método encargado de modificar el valor deconyugeLabora
	 * 
	 * @param valor
	 *            para modificar conyugeLabora
	 */
	public void setConyugeLabora(Boolean conyugeLabora) {
		this.conyugeLabora = conyugeLabora;
	}

	/**
	 * Método que retorna el valor de valorIngresoMensualConyuge
	 * 
	 * @return el valor de valorIngresoMensualConyuge
	 */
	public BigDecimal getValorIngresoMensualConyuge() {
		return valorIngresoMensualConyuge;
	}

	/**
	 * Método encargado de modificar el valor de valorIngresoMensualConyuge
	 * 
	 * @param valor
	 *            para modificar valorIngresoMensualConyuge
	 */
	public void setValorIngresoMensualConyuge(BigDecimal valorIngresoMensualConyuge) {
		this.valorIngresoMensualConyuge = valorIngresoMensualConyuge;
	}

	/**
	 * Método que retorna el valor de fechaExpedicionDocHijo
	 * 
	 * @return el valor de fechaExpedicionDocHijo
	 */
	public Long getFechaExpedicionDocHijo() {
		return fechaExpedicionDocHijo;
	}

	/**
	 * Método encargado de modificar el valor de fechaExpedicionDocHijo
	 * 
	 * @param valor
	 *            para modificar fechaExpedicionDocHijo
	 */
	public void setFechaExpedicionDocHijo(Long fechaExpedicionDocHijo) {
		this.fechaExpedicionDocHijo = fechaExpedicionDocHijo;
	}

	/**
	 * Método que retorna el valor de generoHijo
	 * 
	 * @return el valor de generoHijo
	 */
	public GeneroEnum getGeneroHijo() {
		return generoHijo;
	}

	/**
	 * Método encargado de modificar el valorgeneroHijo de
	 * 
	 * @param valor
	 *            para modificar generoHijo
	 */
	public void setGeneroHijo(GeneroEnum generoHijo) {
		this.generoHijo = generoHijo;
	}

	/**
	 * Método que retorna el valor de fechaNacimientoHijo
	 * 
	 * @return el valor de fechaNacimientoHijo
	 */
	public Long getFechaNacimientoHijo() {
		return fechaNacimientoHijo;
	}

	/**
	 * Método encargado de modificar el valor de fechaNacimientoHijo
	 * 
	 * @param valor
	 *            para modificar fechaNacimientoHijo
	 */
	public void setFechaNacimientoHijo(Long fechaNacimientoHijo) {
		this.fechaNacimientoHijo = fechaNacimientoHijo;
	}

	/**
	 * Método que retorna el valor de nivelEducativoHijo
	 * 
	 * @return el valor de nivelEducativoHijo
	 */
	public NivelEducativoEnum getNivelEducativoHijo() {
		return nivelEducativoHijo;
	}

	/**
	 * Método encargado de modificar el valor de nivelEducativoHijo
	 * 
	 * @param valor
	 *            para modificar nivelEducativoHijo
	 */
	public void setNivelEducativoHijo(NivelEducativoEnum nivelEducativoHijo) {
		this.nivelEducativoHijo = nivelEducativoHijo;
	}

	/**
	 * Método que retorna el valor de gradoCursadoHijo
	 * 
	 * @return el valor de gradoCursadoHijo
	 */
	public GradoAcademico getGradoCursadoHijo() {
		return gradoCursadoHijo;
	}

	/**
	 * Método encargado de modificar el valor de gradoCursadoHijo
	 * 
	 * @param valor
	 *            para modificar gradoCursadoHijo
	 */
	public void setGradoCursadoHijo(GradoAcademico gradoCursadoHijo) {
		this.gradoCursadoHijo = gradoCursadoHijo;
	}

	/**
	 * Método que retorna el valor de profesionHijo
	 * 
	 * @return el valor de profesionHijo
	 */
	public OcupacionProfesion getProfesionHijo() {
		return profesionHijo;
	}

	/**
	 * Método encargado de modificar el valor deprofesionHijo
	 * 
	 * @param valor
	 *            para modificar profesionHijo
	 */
	public void setProfesionHijo(OcupacionProfesion profesionHijo) {
		this.profesionHijo = profesionHijo;
	}

	/**
	 * Método que retorna el valor de certificadoEscolarHijo
	 * 
	 * @return el valor de certificadoEscolarHijo
	 */
	public Boolean getCertificadoEscolarHijo() {
		return certificadoEscolarHijo;
	}

	/**
	 * Método encargado de modificar el valor de certificadoEscolarHijo
	 * 
	 * @param valor
	 *            para modificar certificadoEscolarHijo
	 */
	public void setCertificadoEscolarHijo(Boolean certificadoEscolarHijo) {
		this.certificadoEscolarHijo = certificadoEscolarHijo;
	}

	/**
	 * Método que retorna el valor de fechaVencimientoCertEscolar
	 * 
	 * @return el valor de fechaVencimientoCertEscolar
	 */
	public Long getFechaVencimientoCertEscolar() {
		return fechaVencimientoCertEscolar;
	}

	/**
	 * Método encargado de modificar el valor de fechaVencimientoCertEscolar
	 * 
	 * @param valor
	 *            para modificar fechaVencimientoCertEscolar
	 */
	public void setFechaVencimientoCertEscolar(Long fechaVencimientoCertEscolar) {
		this.fechaVencimientoCertEscolar = fechaVencimientoCertEscolar;
	}

	/**
	 * Método que retorna el valor de fechaReporteCertEscolarHijo
	 * 
	 * @return el valor de fechaReporteCertEscolarHijo
	 */
	public Long getFechaReporteCertEscolarHijo() {
		return fechaReporteCertEscolarHijo;
	}

	/**
	 * Método encargado de modificar el valor de fechaReporteCertEscolarHijo
	 * 
	 * @param valor
	 *            para modificar fechaReporteCertEscolarHijo
	 */
	public void setFechaReporteCertEscolarHijo(Long fechaReporteCertEscolarHijo) {
		this.fechaReporteCertEscolarHijo = fechaReporteCertEscolarHijo;
	}

	/**
	 * Método que retorna el valor de beneficioProgramaTrabajoDesarrollo
	 * 
	 * @return el valor de beneficioProgramaTrabajoDesarrollo
	 */
	public Boolean getBeneficioProgramaTrabajoDesarrollo() {
		return beneficioProgramaTrabajoDesarrollo;
	}

	/**
	 * Método encargado de modificar el valor de
	 * beneficioProgramaTrabajoDesarrollo
	 * 
	 * @param valor
	 *            para modificar beneficioProgramaTrabajoDesarrollo
	 */
	public void setBeneficioProgramaTrabajoDesarrollo(Boolean beneficioProgramaTrabajoDesarrollo) {
		this.beneficioProgramaTrabajoDesarrollo = beneficioProgramaTrabajoDesarrollo;
	}

	/**
	 * Método que retorna el valor de condicionInvalidezHijo
	 * 
	 * @return el valor de condicionInvalidezHijo
	 */
	public Boolean getCondicionInvalidezHijo() {
		return condicionInvalidezHijo;
	}

	/**
	 * Método encargado de modificar el valor de condicionInvalidezHijo
	 * 
	 * @param valor
	 *            para modificar condicionInvalidezHijo
	 */
	public void setCondicionInvalidezHijo(Boolean condicionInvalidezHijo) {
		this.condicionInvalidezHijo = condicionInvalidezHijo;
	}

	/**
	 * Método que retorna el valor de fechaRepoteinvalidezHijo
	 * 
	 * @return el valor de fechaRepoteinvalidezHijo
	 */
	public Long getFechaRepoteinvalidezHijo() {
		return fechaRepoteinvalidezHijo;
	}

	/**
	 * Método encargado de modificar el valor de fechaRepoteinvalidezHijo
	 * 
	 * @param valor
	 *            para modificar fechaRepoteinvalidezHijo
	 */
	public void setFechaRepoteinvalidezHijo(Long fechaRepoteinvalidezHijo) {
		this.fechaRepoteinvalidezHijo = fechaRepoteinvalidezHijo;
	}

	/**
	 * Método que retorna el valor de observacionesHijo
	 * 
	 * @return el valor de observacionesHijo
	 */
	public String getObservacionesHijo() {
		return observacionesHijo;
	}

	/**
	 * Método encargado de modificar el valor de observacionesHijo
	 * 
	 * @param valor
	 *            para modificar observacionesHijo
	 */
	public void setObservacionesHijo(String observacionesHijo) {
		this.observacionesHijo = observacionesHijo;
	}

	/**
	 * Método que retorna el valor de expediciondocPadre
	 * 
	 * @return el valor de expediciondocPadre
	 */
	public Long getExpediciondocPadre() {
		return expediciondocPadre;
	}

	/**
	 * Método encargado de modificar el valor de expediciondocPadre
	 * 
	 * @param valor
	 *            para modificar expediciondocPadre
	 */
	public void setExpediciondocPadre(Long expediciondocPadre) {
		this.expediciondocPadre = expediciondocPadre;
	}

	/**
	 * Método que retorna el valor de generoPadre
	 * 
	 * @return el valor de generoPadre
	 */
	public GeneroEnum getGeneroPadre() {
		return generoPadre;
	}

	/**
	 * Método encargado de modificar el valor generoPadrede
	 * 
	 * @param valor
	 *            para modificar generoPadre
	 */
	public void setGeneroPadre(GeneroEnum generoPadre) {
		this.generoPadre = generoPadre;
	}

	/**
	 * Método que retorna el valor de fechaNacimientoPadre
	 * 
	 * @return el valor de fechaNacimientoPadre
	 */
	public Long getFechaNacimientoPadre() {
		return fechaNacimientoPadre;
	}

	/**
	 * Método encargado de modificar el valor de fechaNacimientoPadre
	 * 
	 * @param valor
	 *            para modificar fechaNacimientoPadre
	 */
	public void setFechaNacimientoPadre(Long fechaNacimientoPadre) {
		this.fechaNacimientoPadre = fechaNacimientoPadre;
	}

	/**
	 * Método que retorna el valor de nivelEducativoPadre
	 * 
	 * @return el valor de nivelEducativoPadre
	 */
	public NivelEducativoEnum getNivelEducativoPadre() {
		return nivelEducativoPadre;
	}

	/**
	 * Método encargado de modificar el valor de nivelEducativoPadre
	 * 
	 * @param valor
	 *            para modificar nivelEducativoPadre
	 */
	public void setNivelEducativoPadre(NivelEducativoEnum nivelEducativoPadre) {
		this.nivelEducativoPadre = nivelEducativoPadre;
	}

	/**
	 * Método que retorna el valor de ocupacionProfesionPadre
	 * 
	 * @return el valor de ocupacionProfesionPadre
	 */
	public OcupacionProfesion getOcupacionProfesionPadre() {
		return ocupacionProfesionPadre;
	}

	/**
	 * Método encargado de modificar el valor de ocupacionProfesionPadre
	 * 
	 * @param valor
	 *            para modificar ocupacionProfesionPadre
	 */
	public void setOcupacionProfesionPadre(OcupacionProfesion ocupacionProfesionPadre) {
		this.ocupacionProfesionPadre = ocupacionProfesionPadre;
	}

	/**
	 * Método que retorna el valor de condicionInvalidezPadre
	 * 
	 * @return el valor de condicionInvalidezPadre
	 */
	public Boolean getCondicionInvalidezPadre() {
		return condicionInvalidezPadre;
	}

	/**
	 * Método encargado de modificar el valor de condicionInvalidezPadre
	 * 
	 * @param valor
	 *            para modificar condicionInvalidezPadre
	 */
	public void setCondicionInvalidezPadre(Boolean condicionInvalidezPadre) {
		this.condicionInvalidezPadre = condicionInvalidezPadre;
	}

	/**
	 * Método que retorna el valor de fechaReporteInvalidezPadre
	 * 
	 * @return el valor de fechaReporteInvalidezPadre
	 */
	public Long getFechaReporteInvalidezPadre() {
		return fechaReporteInvalidezPadre;
	}

	/**
	 * Método encargado de modificar el valor de fechaReporteInvalidezPadre
	 * 
	 * @param valor
	 *            para modificar fechaReporteInvalidezPadre
	 */
	public void setFechaReporteInvalidezPadre(Long fechaReporteInvalidezPadre) {
		this.fechaReporteInvalidezPadre = fechaReporteInvalidezPadre;
	}

	/**
	 * Método que retorna el valor de observacionesPadre
	 * 
	 * @return el valor de observacionesPadre
	 */
	public String getObservacionesPadre() {
		return observacionesPadre;
	}

	/**
	 * Método encargado de modificar el valor de observacionesPadre
	 * 
	 * @param valor
	 *            para modificar observacionesPadre
	 */
	public void setObservacionesPadre(String observacionesPadre) {
		this.observacionesPadre = observacionesPadre;
	}

	/**
	 * Método que retorna el valor de estadoAfiliadoPrincipalBeneficiario
	 * 
	 * @return el valor de estadoAfiliadoPrincipalBeneficiario
	 */
	public EstadoAfiliadoEnum getEstadoAfiliadoPrincipalBeneficiario() {
		return estadoAfiliadoPrincipalBeneficiario;
	}

	/**
	 * Método encargado de modificar el valor de
	 * estadoAfiliadoPrincipalBeneficiario
	 * 
	 * @param valor
	 *            para modificar estadoAfiliadoPrincipalBeneficiario
	 */
	public void setEstadoAfiliadoPrincipalBeneficiario(EstadoAfiliadoEnum estadoAfiliadoPrincipalBeneficiario) {
		this.estadoAfiliadoPrincipalBeneficiario = estadoAfiliadoPrincipalBeneficiario;
	}

	/**
	 * Método que retorna el valor de parentescoBeneficiarios
	 * 
	 * @return el valor de parentescoBeneficiarios
	 */
	public ClasificacionEnum getParentescoBeneficiarios() {
		return parentescoBeneficiarios;
	}

	/**
	 * Método encargado de modificar el valor de parentescoBeneficiarios
	 * 
	 * @param valor
	 *            para modificar parentescoBeneficiarios
	 */
	public void setParentescoBeneficiarios(ClasificacionEnum parentescoBeneficiarios) {
		this.parentescoBeneficiarios = parentescoBeneficiarios;
	}

	/**
	 * Método que retorna el valor de fechaInicioUnionConAfiliadoPrincipal
	 * 
	 * @return el valor de fechaInicioUnionConAfiliadoPrincipal
	 */
	public Long getFechaInicioUnionConAfiliadoPrincipal() {
		return fechaInicioUnionConAfiliadoPrincipal;
	}

	/**
	 * Método encargado de modificar el valor de
	 * fechaInicioUnionConAfiliadoPrincipal
	 * 
	 * @param valor
	 *            para modificar fechaInicioUnionConAfiliadoPrincipal
	 */
	public void setFechaInicioUnionConAfiliadoPrincipal(Long fechaInicioUnionConAfiliadoPrincipal) {
		this.fechaInicioUnionConAfiliadoPrincipal = fechaInicioUnionConAfiliadoPrincipal;
	}

	/**
	 * Método que retorna el valor de fechaRegistroActivacionBeneficiario
	 * 
	 * @return el valor de fechaRegistroActivacionBeneficiario
	 */
	public Long getFechaRegistroActivacionBeneficiario() {
		return fechaRegistroActivacionBeneficiario;
	}

	/**
	 * Método encargado de modificar el valor de
	 * fechaRegistroActivacionBeneficiario
	 * 
	 * @param valor
	 *            para modificar fechaRegistroActivacionBeneficiario
	 */
	public void setFechaRegistroActivacionBeneficiario(Long fechaRegistroActivacionBeneficiario) {
		this.fechaRegistroActivacionBeneficiario = fechaRegistroActivacionBeneficiario;
	}

	/**
	 * Método que retorna el valor de fechaFinsociedadConyugal
	 * 
	 * @return el valor de fechaFinsociedadConyugal
	 */
	public Long getFechaFinsociedadConyugal() {
		return fechaFinsociedadConyugal;
	}

	/**
	 * Método encargado de modificar el valor de fechaFinsociedadConyugal
	 * 
	 * @param valor
	 *            para modificar fechaFinsociedadConyugal
	 */
	public void setFechaFinsociedadConyugal(Long fechaFinsociedadConyugal) {
		this.fechaFinsociedadConyugal = fechaFinsociedadConyugal;
	}

	/**
	 * Método que retorna el valor de motivoDesafiliacionTrabajador
	 * 
	 * @return el valor de motivoDesafiliacionTrabajador
	 */
	public MotivoDesafiliacionAfiliadoEnum getMotivoDesafiliacionTrabajador() {
		return motivoDesafiliacionTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de motivoDesafiliacionTrabajador
	 * 
	 * @param valor
	 *            para modificar motivoDesafiliacionTrabajador
	 */
	public void setMotivoDesafiliacionTrabajador(MotivoDesafiliacionAfiliadoEnum motivoDesafiliacionTrabajador) {
		this.motivoDesafiliacionTrabajador = motivoDesafiliacionTrabajador;
	}

	/**
	 * Método que retorna el valor de motivoDesafiliacionBeneficiario
	 * 
	 * @return el valor de motivoDesafiliacionBeneficiario
	 */
	public MotivoDesafiliacionBeneficiarioEnum getMotivoDesafiliacionBeneficiario() {
		return motivoDesafiliacionBeneficiario;
	}

	/**
	 * Método encargado de modificar el valor de motivoDesafiliacionBeneficiario
	 * 
	 * @param valor
	 *            para modificar motivoDesafiliacionBeneficiario
	 */
	public void setMotivoDesafiliacionBeneficiario(MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacionBeneficiario) {
		this.motivoDesafiliacionBeneficiario = motivoDesafiliacionBeneficiario;
	}

	/**
	 * Método que retorna el valor de fechaInactivacionBeneficiario
	 * 
	 * @return el valor de fechaInactivacionBeneficiario
	 */
	public Long getFechaInactivacionBeneficiario() {
		return fechaInactivacionBeneficiario;
	}

	/**
	 * Método encargado de modificar el valor de fechaInactivacionBeneficiario
	 * 
	 * @param valor
	 *            para modificar fechaInactivacionBeneficiario
	 */
	public void setFechaInactivacionBeneficiario(Long fechaInactivacionBeneficiario) {
		this.fechaInactivacionBeneficiario = fechaInactivacionBeneficiario;
	}

	/**
	 * Método que retorna el valor de estadoCivilConyuge
	 * 
	 * @return el valor de estadoCivilConyuge
	 */
	public EstadoCivilEnum getEstadoCivilConyuge() {
		return estadoCivilConyuge;
	}

	/**
	 * Método encargado de modificar el valor de estadoCivilConyuge
	 * 
	 * @param valor
	 *            para modificar estadoCivilConyuge
	 */
	public void setEstadoCivilConyuge(EstadoCivilEnum estadoCivilConyuge) {
		this.estadoCivilConyuge = estadoCivilConyuge;
	}

	
	/**
	 * @return the fechaInicioNovedad
	 */
	public Long getFechaInicioNovedad() {
		return fechaInicioNovedad;
	}

	/**
	 * @param fechaInicioNovedadPila the fechaInicioNovedad to set
	 */
	public void setFechaInicioNovedad(Long fechaInicioNovedad) {
		this.fechaInicioNovedad = fechaInicioNovedad;
	}

	/**
	 * @return the fechaFinNovedadPila
	 */
	public Long getFechaFinNovedad() {
		return fechaFinNovedad;
	}

	/**
	 * @param fechaFinNovedadPila the fechaFinNovedad to set
	 */
	public void setFechaFinNovedad(Long fechaFinNovedad) {
		this.fechaFinNovedad = fechaFinNovedad;
	}

	/**
	 * Método que retorna el valor de vigenciaPagosPensionado
	 * 
	 * @return el valor de vigenciaPagosPensionado
	 */
	public Long getVigenciaPagosPensionado() {
		return vigenciaPagosPensionado;
	}

	/**
	 * Método encargado de modificar el valor de vigenciaPagosPensionado
	 * 
	 * @param valor
	 *            para modificar vigenciaPagosPensionado
	 */
	public void setVigenciaPagosPensionado(Long vigenciaPagosPensionado) {
		this.vigenciaPagosPensionado = vigenciaPagosPensionado;
	}

	/**
	 * Método que retorna el valor de vigenciaPagosDependiente
	 * 
	 * @return el valor de vigenciaPagosDependiente
	 */
	public Long getVigenciaPagosDependiente() {
		return vigenciaPagosDependiente;
	}

	/**
	 * Método encargado de modificar el valor de vigenciaPagosDependiente
	 * 
	 * @param valor
	 *            para modificar vigenciaPagosDependiente
	 */
	public void setVigenciaPagosDependiente(Long vigenciaPagosDependiente) {
		this.vigenciaPagosDependiente = vigenciaPagosDependiente;
	}

	/**
	 * Método que retorna el valor de emitirTarjetaDependiente
	 * 
	 * @return el valor de emitirTarjetaDependiente
	 */
	public Boolean getEmitirTarjetaDependiente() {
		return emitirTarjetaDependiente;
	}

	/**
	 * Método encargado de modificar el valor de emitirTarjetaDependiente
	 * 
	 * @param valor
	 *            para modificar emitirTarjetaDependiente
	 */
	public void setEmitirTarjetaDependiente(Boolean emitirTarjetaDependiente) {
		this.emitirTarjetaDependiente = emitirTarjetaDependiente;
	}

	/**
	 * Método que retorna el valor de condicionInvalidezTrabajador
	 * 
	 * @return el valor de condicionInvalidezTrabajador
	 */
	public Boolean getCondicionInvalidezTrabajador() {
		return condicionInvalidezTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de condicionInvalidezTrabajador
	 * 
	 * @param valor
	 *            para modificar condicionInvalidezTrabajador
	 */
	public void setCondicionInvalidezTrabajador(Boolean condicionInvalidezTrabajador) {
		this.condicionInvalidezTrabajador = condicionInvalidezTrabajador;
	}

	/**
	 * Método que retorna el valor de fechaReporteInvalidezTrabajador
	 * 
	 * @return el valor de fechaReporteInvalidezTrabajador
	 */
	public Long getFechaReporteInvalidezTrabajador() {
		return fechaReporteInvalidezTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de fechaReporteInvalidezTrabajador
	 * 
	 * @param valor
	 *            para modificar fechaReporteInvalidezTrabajador
	 */
	public void setFechaReporteInvalidezTrabajador(Long fechaReporteInvalidezTrabajador) {
		this.fechaReporteInvalidezTrabajador = fechaReporteInvalidezTrabajador;
	}

	/**
	 * Método que retorna el valor de personaFallecidaTrabajador
	 * 
	 * @return el valor de personaFallecidaTrabajador
	 */
	public Boolean getPersonaFallecidaTrabajador() {
		return personaFallecidaTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de personaFallecidaTrabajador
	 * 
	 * @param valor
	 *            para modificar personaFallecidaTrabajador
	 */
	public void setPersonaFallecidaTrabajador(Boolean personaFallecidaTrabajador) {
		this.personaFallecidaTrabajador = personaFallecidaTrabajador;
	}

	/**
	 * Método que retorna el valor de personaFallecidaBeneficiarios
	 * 
	 * @return el valor de personaFallecidaBeneficiarios
	 */
	public Boolean getPersonaFallecidaBeneficiarios() {
		return personaFallecidaBeneficiarios;
	}

	/**
	 * Método encargado de modificar el valor de personaFallecidaBeneficiarios
	 * 
	 * @param valor
	 *            para modificar personaFallecidaBeneficiarios
	 */
	public void setPersonaFallecidaBeneficiarios(Boolean personaFallecidaBeneficiarios) {
		this.personaFallecidaBeneficiarios = personaFallecidaBeneficiarios;
	}

	/**
	 * Método que retorna el valor de fechaReporteFallecimientoTrabajador
	 * 
	 * @return el valor de fechaReporteFallecimientoTrabajador
	 */
	public Long getFechaReporteFallecimientoTrabajador() {
		return fechaReporteFallecimientoTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de
	 * fechaReporteFallecimientoTrabajador
	 * 
	 * @param valor
	 *            para modificar fechaReporteFallecimientoTrabajador
	 */
	public void setFechaReporteFallecimientoTrabajador(Long fechaReporteFallecimientoTrabajador) {
		this.fechaReporteFallecimientoTrabajador = fechaReporteFallecimientoTrabajador;
	}

	/**
	 * Método que retorna el valor de estadoAfiliacionTrabajador
	 * 
	 * @return el valor de estadoAfiliacionTrabajador
	 */
	public EstadoAfiliadoEnum getEstadoAfiliacionTrabajador() {
		return estadoAfiliacionTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de estadoAfiliacionTrabajador
	 * 
	 * @param valor
	 *            para modificar estadoAfiliacionTrabajador
	 */
	public void setEstadoAfiliacionTrabajador(EstadoAfiliadoEnum estadoAfiliacionTrabajador) {
		this.estadoAfiliacionTrabajador = estadoAfiliacionTrabajador;
	}

	/**
	 * Método que retorna el valor de pignoracionSubsidioTrabajador
	 * 
	 * @return el valor de pignoracionSubsidioTrabajador
	 */
	public Boolean getPignoracionSubsidioTrabajador() {
		return pignoracionSubsidioTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de pignoracionSubsidioTrabajador
	 * 
	 * @param valor
	 *            para modificar pignoracionSubsidioTrabajador
	 */
	public void setPignoracionSubsidioTrabajador(Boolean pignoracionSubsidioTrabajador) {
		this.pignoracionSubsidioTrabajador = pignoracionSubsidioTrabajador;
	}

	/**
	 * Método que retorna el valor de estadoTarjetaTrabajador
	 * 
	 * @return el valor de estadoTarjetaTrabajador
	 */
	public EstadoTarjetaEnum getEstadoTarjetaTrabajador() {
		return estadoTarjetaTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de estadoTarjetaTrabajador
	 * 
	 * @param valor
	 *            para modificar estadoTarjetaTrabajador
	 */
	public void setEstadoTarjetaTrabajador(EstadoTarjetaEnum estadoTarjetaTrabajador) {
		this.estadoTarjetaTrabajador = estadoTarjetaTrabajador;
	}

	/**
	 * Método que retorna el valor de estadoTarjetaGrupoFam
	 * 
	 * @return el valor de estadoTarjetaGrupoFam
	 */
	public EstadoTarjetaEnum getEstadoTarjetaGrupoFam() {
		return estadoTarjetaGrupoFam;
	}

	/**
	 * Método encargado de modificar el valor de estadoTarjetaGrupoFam
	 * 
	 * @param valor
	 *            para modificar estadoTarjetaGrupoFam
	 */
	public void setEstadoTarjetaGrupoFam(EstadoTarjetaEnum estadoTarjetaGrupoFam) {
		this.estadoTarjetaGrupoFam = estadoTarjetaGrupoFam;
	}

	/**
	 * Método que retorna el valor de numeroDocTitularTrabajador
	 * 
	 * @return el valor de numeroDocTitularTrabajador
	 */
	public String getNumeroDocTitularTrabajador() {
		return numeroDocTitularTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de numeroDocTitularTrabajador
	 * 
	 * @param valor
	 *            para modificar numeroDocTitularTrabajador
	 */
	public void setNumeroDocTitularTrabajador(String numeroDocTitularTrabajador) {
		this.numeroDocTitularTrabajador = numeroDocTitularTrabajador;
	}

	/**
	 * Método que retorna el valor de tipoDocTitularTrabajador
	 * 
	 * @return el valor de tipoDocTitularTrabajador
	 */
	public TipoIdentificacionEnum getTipoDocTitularTrabajador() {
		return tipoDocTitularTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de tipoDocTitularTrabajador
	 * 
	 * @param valor
	 *            para modificar tipoDocTitularTrabajador
	 */
	public void setTipoDocTitularTrabajador(TipoIdentificacionEnum tipoDocTitularTrabajador) {
		this.tipoDocTitularTrabajador = tipoDocTitularTrabajador;
	}

	/**
	 * Método que retorna el valor de numeroCuentaTrabajador
	 * 
	 * @return el valor de numeroCuentaTrabajador
	 */
	public String getNumeroCuentaTrabajador() {
		return numeroCuentaTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de numeroCuentaTrabajador
	 * 
	 * @param valor
	 *            para modificar numeroCuentaTrabajador
	 */
	public void setNumeroCuentaTrabajador(String numeroCuentaTrabajador) {
		this.numeroCuentaTrabajador = numeroCuentaTrabajador;
	}

	/**
	 * Método que retorna el valor de estadoAfiliadoPrincipalTrabajador
	 * 
	 * @return el valor de estadoAfiliadoPrincipalTrabajador
	 */
	public EstadoAfiliadoEnum getEstadoAfiliadoPrincipalTrabajador() {
		return estadoAfiliadoPrincipalTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de
	 * estadoAfiliadoPrincipalTrabajador
	 * 
	 * @param valor
	 *            para modificar estadoAfiliadoPrincipalTrabajador
	 */
	public void setEstadoAfiliadoPrincipalTrabajador(EstadoAfiliadoEnum estadoAfiliadoPrincipalTrabajador) {
		this.estadoAfiliadoPrincipalTrabajador = estadoAfiliadoPrincipalTrabajador;
	}

	/**
	 * Método que retorna el valor de canalTrabajador
	 * 
	 * @return el valor de canalTrabajador
	 */
	public CanalRecepcionEnum getCanalTrabajador() {
		return canalTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de canalTrabajador
	 * 
	 * @param valor
	 *            para modificar canalTrabajador
	 */
	public void setCanalTrabajador(CanalRecepcionEnum canalTrabajador) {
		this.canalTrabajador = canalTrabajador;
	}

	/**
	 * Método que retorna el valor de serviciosSinAfiliacionTrabajador
	 * 
	 * @return el valor de serviciosSinAfiliacionTrabajador
	 */
	public Boolean getServiciosSinAfiliacionTrabajador() {
		return serviciosSinAfiliacionTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de
	 * serviciosSinAfiliacionTrabajador
	 * 
	 * @param valor
	 *            para modificar serviciosSinAfiliacionTrabajador
	 */
	public void setServiciosSinAfiliacionTrabajador(Boolean serviciosSinAfiliacionTrabajador) {
		this.serviciosSinAfiliacionTrabajador = serviciosSinAfiliacionTrabajador;
	}

	/**
	 * Método que retorna el valor de
	 * fechaNovedadActivacionServiciosSinAfiliarTrabajador
	 * 
	 * @return el valor de fechaNovedadActivacionServiciosSinAfiliarTrabajador
	 */
	public Long getFechaNovedadActivacionServiciosSinAfiliarTrabajador() {
		return fechaNovedadActivacionServiciosSinAfiliarTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de
	 * fechaNovedadActivacionServiciosSinAfiliarTrabajador
	 * 
	 * @param valor
	 *            para modificar
	 *            fechaNovedadActivacionServiciosSinAfiliarTrabajador
	 */
	public void setFechaNovedadActivacionServiciosSinAfiliarTrabajador(
			Long fechaNovedadActivacionServiciosSinAfiliarTrabajador) {
		this.fechaNovedadActivacionServiciosSinAfiliarTrabajador = fechaNovedadActivacionServiciosSinAfiliarTrabajador;
	}

	/**
	 * Método que retorna el valor de categoriaTrabajador
	 * 
	 * @return el valor de categoriaTrabajador
	 */
	public CategoriaPersonaEnum getCategoriaTrabajador() {
		return categoriaTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de categoriaTrabajador
	 * 
	 * @param valor
	 *            para modificar categoriaTrabajador
	 */
	public void setCategoriaTrabajador(CategoriaPersonaEnum categoriaTrabajador) {
		this.categoriaTrabajador = categoriaTrabajador;
	}

	/**
	 * Método que retorna el valor de
	 * fechaFinaliazaServicioSinAfiliacionTrabajador
	 * 
	 * @return el valor de fechaFinaliazaServicioSinAfiliacionTrabajador
	 */
	public Long getFechaFinaliazaServicioSinAfiliacionTrabajador() {
		return fechaFinaliazaServicioSinAfiliacionTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de
	 * fechaFinaliazaServicioSinAfiliacionTrabajador
	 * 
	 * @param valor
	 *            para modificar fechaFinaliazaServicioSinAfiliacionTrabajador
	 */
	public void setFechaFinaliazaServicioSinAfiliacionTrabajador(Long fechaFinaliazaServicioSinAfiliacionTrabajador) {
		this.fechaFinaliazaServicioSinAfiliacionTrabajador = fechaFinaliazaServicioSinAfiliacionTrabajador;
	}

	/**
	 * Método que retorna el valor de grupoFamiliarInembargable
	 * 
	 * @return el valor de grupoFamiliarInembargable
	 */
	public Boolean getGrupoFamiliarInembargable() {
		return grupoFamiliarInembargable;
	}

	/**
	 * Método encargado de modificar el valor de grupoFamiliarInembargable
	 * 
	 * @param valor
	 *            para modificar grupoFamiliarInembargable
	 */
	public void setGrupoFamiliarInembargable(Boolean grupoFamiliarInembargable) {
		this.grupoFamiliarInembargable = grupoFamiliarInembargable;
	}

	/**
	 * Método que retorna el valor de inactivarCuentaWeb
	 * 
	 * @return el valor de inactivarCuentaWeb
	 */
	public Boolean getInactivarCuentaWeb() {
		return inactivarCuentaWeb;
	}

	/**
	 * Método encargado de modificar el valor de inactivarCuentaWeb
	 * 
	 * @param valor
	 *            para modificar inactivarCuentaWeb
	 */
	public void setInactivarCuentaWeb(Boolean inactivarCuentaWeb) {
		this.inactivarCuentaWeb = inactivarCuentaWeb;
	}

	/**
	 * Método que retorna el valor de rentencionSubsidioActivaGF
	 * 
	 * @return el valor de rentencionSubsidioActivaGF
	 */
	public Boolean getRentencionSubsidioActivaGF() {
		return rentencionSubsidioActivaGF;
	}

	/**
	 * Método encargado de modificar el valor de rentencionSubsidioActivaGF
	 * 
	 * @param valor
	 *            para modificar rentencionSubsidioActivaGF
	 */
	public void setRentencionSubsidioActivaGF(Boolean rentencionSubsidioActivaGF) {
		this.rentencionSubsidioActivaGF = rentencionSubsidioActivaGF;
	}

	/**
	 * Método que retorna el valor de tipoDocumentoAdminGF
	 * 
	 * @return el valor de tipoDocumentoAdminGF
	 */
	public TipoIdentificacionEnum getTipoDocumentoAdminGF() {
		return tipoDocumentoAdminGF;
	}

	/**
	 * Método encargado de modificar el valor de tipoDocumentoAdminGF
	 * 
	 * @param valor
	 *            para modificar tipoDocumentoAdminGF
	 */
	public void setTipoDocumentoAdminGF(TipoIdentificacionEnum tipoDocumentoAdminGF) {
		this.tipoDocumentoAdminGF = tipoDocumentoAdminGF;
	}

	/**
	 * Método que retorna el valor de numeroDocumentoAdminGF
	 * 
	 * @return el valor de numeroDocumentoAdminGF
	 */
	public String getNumeroDocumentoAdminGF() {
		return numeroDocumentoAdminGF;
	}

	/**
	 * Método encargado de modificar el valor de numeroDocumentoAdminGF
	 * 
	 * @param valor
	 *            para modificar numeroDocumentoAdminGF
	 */
	public void setNumeroDocumentoAdminGF(String numeroDocumentoAdminGF) {
		this.numeroDocumentoAdminGF = numeroDocumentoAdminGF;
	}

	/**
	 * Método que retorna el valor de primNombreAdminGF
	 * 
	 * @return el valor de primNombreAdminGF
	 */
	public String getPrimNombreAdminGF() {
		return primNombreAdminGF;
	}

	/**
	 * Método encargado de modificar el valor de primNombreAdminGF
	 * 
	 * @param valor
	 *            para modificar primNombreAdminGF
	 */
	public void setPrimNombreAdminGF(String primNombreAdminGF) {
		this.primNombreAdminGF = primNombreAdminGF;
	}

	/**
	 * Método que retorna el valor de segNombreAdminGF
	 * 
	 * @return el valor de segNombreAdminGF
	 */
	public String getSegNombreAdminGF() {
		return segNombreAdminGF;
	}

	/**
	 * Método encargado de modificar el valor de segNombreAdminGF
	 * 
	 * @param valor
	 *            para modificar segNombreAdminGF
	 */
	public void setSegNombreAdminGF(String segNombreAdminGF) {
		this.segNombreAdminGF = segNombreAdminGF;
	}

	/**
	 * Método que retorna el valor de primApellidoAdminGF
	 * 
	 * @return el valor de primApellidoAdminGF
	 */
	public String getPrimApellidoAdminGF() {
		return primApellidoAdminGF;
	}

	/**
	 * Método encargado de modificar el valor de primApellidoAdminGF
	 * 
	 * @param valor
	 *            para modificar primApellidoAdminGF
	 */
	public void setPrimApellidoAdminGF(String primApellidoAdminGF) {
		this.primApellidoAdminGF = primApellidoAdminGF;
	}

	/**
	 * Método que retorna el valor de segApellidoAdminGF
	 * 
	 * @return el valor de segApellidoAdminGF
	 */
	public String getSegApellidoAdminGF() {
		return segApellidoAdminGF;
	}

	/**
	 * Método encargado de modificar el valor de segApellidoAdminGF
	 * 
	 * @param valor
	 *            para modificar segApellidoAdminGF
	 */
	public void setSegApellidoAdminGF(String segApellidoAdminGF) {
		this.segApellidoAdminGF = segApellidoAdminGF;
	}

	/**
	 * Método que retorna el valor de relacionConGrupoFam
	 * 
	 * @return el valor de relacionConGrupoFam
	 */
	public RelacionGrupoFamiliar getRelacionConGrupoFam() {
		return relacionConGrupoFam;
	}

	/**
	 * Método encargado de modificar el valor de relacionConGrupoFam
	 * 
	 * @param valor
	 *            para modificar relacionConGrupoFam
	 */
	public void setRelacionConGrupoFam(RelacionGrupoFamiliar relacionConGrupoFam) {
		this.relacionConGrupoFam = relacionConGrupoFam;
	}

	/**
	 * Método que retorna el valor de activarMedioPagoEfectivo
	 * 
	 * @return el valor de activarMedioPagoEfectivo
	 */
	public Boolean getActivarMedioPagoEfectivo() {
		return activarMedioPagoEfectivo;
	}

	/**
	 * Método encargado de modificar el valor de activarMedioPagoEfectivo
	 * 
	 * @param valor
	 *            para modificar activarMedioPagoEfectivo
	 */
	public void setActivarMedioPagoEfectivo(Boolean activarMedioPagoEfectivo) {
		this.activarMedioPagoEfectivo = activarMedioPagoEfectivo;
	}

	/**
	 * Método que retorna el valor de porcentajePagoAportesIndependiente
	 * 
	 * @return el valor de porcentajePagoAportesIndependiente
	 */
	public BigDecimal getPorcentajePagoAportesIndependiente() {
		return porcentajePagoAportesIndependiente;
	}

	/**
	 * Método encargado de modificar el valor de
	 * porcentajePagoAportesIndependiente
	 * 
	 * @param el
	 *            valor de porcentajePagoAportesIndependiente
	 */
	public void setPorcentajePagoAportesIndependiente(BigDecimal porcentajePagoAportesIndependiente) {
		this.porcentajePagoAportesIndependiente = porcentajePagoAportesIndependiente;
	}

	/**
	 * Método que retorna el valor de pagadorFondoPensiones
	 * 
	 * @return el valor de pagadorFondoPensiones
	 */
	public AFP getPagadorFondoPensiones() {
		return pagadorFondoPensiones;
	}

	/**
	 * Método encargado de modificar el valor de pagadorFondoPensiones
	 * 
	 * @param el
	 *            valor de pagadorFondoPensiones
	 */
	public void setPagadorFondoPensiones(AFP pagadorFondoPensiones) {
		this.pagadorFondoPensiones = pagadorFondoPensiones;
	}

	/**
	 * Método que retorna el valor de entidadPagadoraDeAportesPensionado
	 * 
	 * @return el valor de entidadPagadoraDeAportesPensionado
	 */
	public EntidadPagadora getEntidadPagadoraDeAportesPensionado() {
		return entidadPagadoraDeAportesPensionado;
	}

	/**
	 * Método encargado de modificar el valor de
	 * entidadPagadoraDeAportesPensionado
	 * 
	 * @param el
	 *            valor de entidadPagadoraDeAportesPensionado
	 */
	public void setEntidadPagadoraDeAportesPensionado(EntidadPagadora entidadPagadoraDeAportesPensionado) {
		this.entidadPagadoraDeAportesPensionado = entidadPagadoraDeAportesPensionado;
	}

	/**
	 * Método que retorna el valor de tarifaPagoAportesIndependiente
	 * 
	 * @return el valor de tarifaPagoAportesIndependiente
	 */
	public BigDecimal getTarifaPagoAportesIndependiente() {
		return tarifaPagoAportesIndependiente;
	}

	/**
	 * Método encargado de modificar el valor de tarifaPagoAportesIndependiente
	 * 
	 * @param el
	 *            valor de tarifaPagoAportesIndependiente
	 */
	public void setTarifaPagoAportesIndependiente(BigDecimal tarifaPagoAportesIndependiente) {
		this.tarifaPagoAportesIndependiente = tarifaPagoAportesIndependiente;
	}

	/**
	 * Método que retorna el valor de tarifaPagoAportesPensionado
	 * 
	 * @return el valor de tarifaPagoAportesPensionado
	 */
	public BigDecimal getTarifaPagoAportesPensionado() {
		return tarifaPagoAportesPensionado;
	}

	/**
	 * Método encargado de modificar el valor de tarifaPagoAportesPensionado
	 * 
	 * @param el
	 *            valor de tarifaPagoAportesPensionado
	 */
	public void setTarifaPagoAportesPensionado(BigDecimal tarifaPagoAportesPensionado) {
		this.tarifaPagoAportesPensionado = tarifaPagoAportesPensionado;
	}

	/**
	 * Método que retorna el valor de solicitudTarjeta
	 * 
	 * @return el valor de solicitudTarjeta
	 */
	public String getSolicitudTarjeta() {
		return solicitudTarjeta;
	}

	/**
	 * Método encargado de modificar el valor de solicitudTarjeta
	 * 
	 * @param el
	 *            valor de solicitudTarjeta
	 */
	public void setSolicitudTarjeta(String solicitudTarjeta) {
		this.solicitudTarjeta = solicitudTarjeta;
	}

	/**
	 * Método que retorna el valor de motivoReexpedTarjetaDependiente
	 * 
	 * @return el valor de motivoReexpedTarjetaDependiente
	 */
	public String getMotivoReexpedTarjetaDependiente() {
		return motivoReexpedTarjetaDependiente;
	}

	/**
	 * Método encargado de modificar el valor de motivoReexpedTarjetaDependiente
	 * 
	 * @param el
	 *            valor de motivoReexpedTarjetaDependiente
	 */
	public void setMotivoReexpedTarjetaDependiente(String motivoReexpedTarjetaDependiente) {
		this.motivoReexpedTarjetaDependiente = motivoReexpedTarjetaDependiente;
	}

	/**
	 * Método que retorna el valor de motivoAnulacionTrabajador
	 * 
	 * @return el valor de motivoAnulacionTrabajador
	 */
	public String getMotivoAnulacionTrabajador() {
		return motivoAnulacionTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de motivoAnulacionTrabajador
	 * 
	 * @param el
	 *            valor de motivoAnulacionTrabajador
	 */
	public void setMotivoAnulacionTrabajador(String motivoAnulacionTrabajador) {
		this.motivoAnulacionTrabajador = motivoAnulacionTrabajador;
	}

	/**
	 * Método que retorna el valor de motivoAnulacionBeneficiario
	 * 
	 * @return el valor de motivoAnulacionBeneficiario
	 */
	public String getMotivoAnulacionBeneficiario() {
		return motivoAnulacionBeneficiario;
	}

	/**
	 * Método encargado de modificar el valor de motivoAnulacionBeneficiario
	 * 
	 * @param el
	 *            valor de motivoAnulacionBeneficiario
	 */
	public void setMotivoAnulacionBeneficiario(String motivoAnulacionBeneficiario) {
		this.motivoAnulacionBeneficiario = motivoAnulacionBeneficiario;
	}

	/**
	 * Método que retorna el valor de bancoDondeRegistraCuentaTrabajador
	 * 
	 * @return el valor de bancoDondeRegistraCuentaTrabajador
	 */
	public String getBancoDondeRegistraCuentaTrabajador() {
		return bancoDondeRegistraCuentaTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de
	 * bancoDondeRegistraCuentaTrabajador
	 * 
	 * @param el
	 *            valor de bancoDondeRegistraCuentaTrabajador
	 */
	public void setBancoDondeRegistraCuentaTrabajador(String bancoDondeRegistraCuentaTrabajador) {
		this.bancoDondeRegistraCuentaTrabajador = bancoDondeRegistraCuentaTrabajador;
	}

	/**
	 * Método que retorna el valor de tipoCuentaTrabajador
	 * 
	 * @return el valor de tipoCuentaTrabajador
	 */
	public String getTipoCuentaTrabajador() {
		return tipoCuentaTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de tipoCuentaTrabajador
	 * 
	 * @param el
	 *            valor de tipoCuentaTrabajador
	 */
	public void setTipoCuentaTrabajador(String tipoCuentaTrabajador) {
		this.tipoCuentaTrabajador = tipoCuentaTrabajador;
	}

	/**
	 * Método que retorna el valor de estadoGeneralSolicitanteCajacompTrabajador
	 * 
	 * @return el valor de estadoGeneralSolicitanteCajacompTrabajador
	 */
	public String getEstadoGeneralSolicitanteCajacompTrabajador() {
		return estadoGeneralSolicitanteCajacompTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de
	 * estadoGeneralSolicitanteCajacompTrabajador
	 * 
	 * @param el
	 *            valor de estadoGeneralSolicitanteCajacompTrabajador
	 */
	public void setEstadoGeneralSolicitanteCajacompTrabajador(String estadoGeneralSolicitanteCajacompTrabajador) {
		this.estadoGeneralSolicitanteCajacompTrabajador = estadoGeneralSolicitanteCajacompTrabajador;
	}

	/**
	 * Método que retorna el valor de
	 * causaInactivacionServiciosSinAfiliarTrabajador
	 * 
	 * @return el valor de causaInactivacionServiciosSinAfiliarTrabajador
	 */
	public CausaServiciosSinAfiliacionEnum getCausaInactivacionServiciosSinAfiliarTrabajador() {
		return causaInactivacionServiciosSinAfiliarTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de
	 * causaInactivacionServiciosSinAfiliarTrabajador
	 * 
	 * @param el
	 *            valor de causaInactivacionServiciosSinAfiliarTrabajador
	 */
	public void setCausaInactivacionServiciosSinAfiliarTrabajador(
			CausaServiciosSinAfiliacionEnum causaInactivacionServiciosSinAfiliarTrabajador) {
		this.causaInactivacionServiciosSinAfiliarTrabajador = causaInactivacionServiciosSinAfiliarTrabajador;
	}

	/**
	 * @return the primerNombreTitularCuentaTrabajador
	 */
	public String getPrimerNombreTitularCuentaTrabajador() {
		return primerNombreTitularCuentaTrabajador;
	}

	/**
	 * @param primerNombreTitularCuentaTrabajador the primerNombreTitularCuentaTrabajador to set
	 */
	public void setPrimerNombreTitularCuentaTrabajador(String primerNombreTitularCuentaTrabajador) {
		this.primerNombreTitularCuentaTrabajador = primerNombreTitularCuentaTrabajador;
	}

	/**
	 * @return the segundoNombreTitularCuentaTrabajador
	 */
	public String getSegundoNombreTitularCuentaTrabajador() {
		return segundoNombreTitularCuentaTrabajador;
	}

	/**
	 * @param segundoNombreTitularCuentaTrabajador the segundoNombreTitularCuentaTrabajador to set
	 */
	public void setSegundoNombreTitularCuentaTrabajador(String segundoNombreTitularCuentaTrabajador) {
		this.segundoNombreTitularCuentaTrabajador = segundoNombreTitularCuentaTrabajador;
	}

	/**
	 * @return the primerApellidoTitularCuentaTrabajador
	 */
	public String getPrimerApellidoTitularCuentaTrabajador() {
		return primerApellidoTitularCuentaTrabajador;
	}

	/**
	 * @param primerApellidoTitularCuentaTrabajador the primerApellidoTitularCuentaTrabajador to set
	 */
	public void setPrimerApellidoTitularCuentaTrabajador(String primerApellidoTitularCuentaTrabajador) {
		this.primerApellidoTitularCuentaTrabajador = primerApellidoTitularCuentaTrabajador;
	}

	/**
	 * @return the segundoApellidoTitularCuentaTrabajador
	 */
	public String getSegundoApellidoTitularCuentaTrabajador() {
		return segundoApellidoTitularCuentaTrabajador;
	}

	/**
	 * @param segundoApellidoTitularCuentaTrabajador the segundoApellidoTitularCuentaTrabajador to set
	 */
	public void setSegundoApellidoTitularCuentaTrabajador(String segundoApellidoTitularCuentaTrabajador) {
		this.segundoApellidoTitularCuentaTrabajador = segundoApellidoTitularCuentaTrabajador;
	}

	/**
	 * @return the resideEnSectorRuralGrupoFamiliar
	 */
	public Boolean getResideEnSectorRuralGrupoFamiliar() {
		return resideEnSectorRuralGrupoFamiliar;
	}

	/**
	 * @param resideEnSectorRuralGrupoFamiliar the resideEnSectorRuralGrupoFamiliar to set
	 */
	public void setResideEnSectorRuralGrupoFamiliar(Boolean resideEnSectorRuralGrupoFamiliar) {
		this.resideEnSectorRuralGrupoFamiliar = resideEnSectorRuralGrupoFamiliar;
	}
	/**
	 * Método que retorna el valor de cesionSubsidio.
	 * @return valor de cesionSubsidio.
	 */
	public Boolean getCesionSubsidio() {
		return cesionSubsidio;
	}

	/**
	 * Método encargado de modificar el valor de cesionSubsidio.
	 * @param valor para modificar cesionSubsidio.
	 */
	public void setCesionSubsidio(Boolean cesionSubsidio) {
		this.cesionSubsidio = cesionSubsidio;
	}

	/**
	 * Método que retorna el valor de listaChequeoNovedad.
	 * @return valor de listaChequeoNovedad.
	 */
	public List<ItemChequeoDTO> getListaChequeoNovedad() {
		return listaChequeoNovedad;
	}

	/**
	 * Método encargado de modificar el valor de listaChequeoNovedad.
	 * @param valor para modificar listaChequeoNovedad.
	 */
	public void setListaChequeoNovedad(List<ItemChequeoDTO> listaChequeoNovedad) {
		this.listaChequeoNovedad = listaChequeoNovedad;
	}

	/**
	 * @return the idPersonas
	 */
	public List<Long> getIdPersonas() {
		return idPersonas;
	}

	/**
	 * @param idPersonas the idPersonas to set
	 */
	public void setIdPersonas(List<Long> idPersonas) {
		this.idPersonas = idPersonas;
	}

	/**
	 * @return the grupoFamiliarBeneficiario
	 */
	public GrupoFamiliarModeloDTO getGrupoFamiliarBeneficiario() {
		return grupoFamiliarBeneficiario;
	}

	/**
	 * @param grupoFamiliarBeneficiario the grupoFamiliarBeneficiario to set
	 */
	public void setGrupoFamiliarBeneficiario(GrupoFamiliarModeloDTO grupoFamiliarBeneficiario) {
		this.grupoFamiliarBeneficiario = grupoFamiliarBeneficiario;
	}

	/**
	 * Método que retorna el valor de afiliacion.
	 * @return valor de afiliacion.
	 */
	public Boolean getAfiliacion() {
		return afiliacion;
	}

	/**
	 * Método encargado de modificar el valor de afiliacion.
	 * @param valor para modificar afiliacion.
	 */
	public void setAfiliacion(Boolean afiliacion) {
		this.afiliacion = afiliacion;
	}

	/**
	 * Método que retorna el valor de idEmpleadorDependiente.
	 * @return valor de idEmpleadorDependiente.
	 */
	public Long getIdEmpleadorDependiente() {
		return idEmpleadorDependiente;
	}

	/**
	 * Método encargado de modificar el valor de idEmpleadorDependiente.
	 * @param valor para modificar idEmpleadorDependiente.
	 */
	public void setIdEmpleadorDependiente(Long idEmpleadorDependiente) {
		this.idEmpleadorDependiente = idEmpleadorDependiente;
	}
	/**
	 * Método que retorna el valor de tipoIdentificacionBeneficiarioAnterior.
	 * @return valor de tipoIdentificacionBeneficiarioAnterior.
	 */
	public TipoIdentificacionEnum getTipoIdentificacionBeneficiarioAnterior() {
		return tipoIdentificacionBeneficiarioAnterior;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacionBeneficiarioAnterior.
	 * @param valor para modificar tipoIdentificacionBeneficiarioAnterior.
	 */
	public void setTipoIdentificacionBeneficiarioAnterior(TipoIdentificacionEnum tipoIdentificacionBeneficiarioAnterior) {
		this.tipoIdentificacionBeneficiarioAnterior = tipoIdentificacionBeneficiarioAnterior;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacionBeneficiarioAnterior.
	 * @return valor de numeroIdentificacionBeneficiarioAnterior.
	 */
	public String getNumeroIdentificacionBeneficiarioAnterior() {
		return numeroIdentificacionBeneficiarioAnterior;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacionBeneficiarioAnterior.
	 * @param valor para modificar numeroIdentificacionBeneficiarioAnterior.
	 */
	public void setNumeroIdentificacionBeneficiarioAnterior(String numeroIdentificacionBeneficiarioAnterior) {
		this.numeroIdentificacionBeneficiarioAnterior = numeroIdentificacionBeneficiarioAnterior;
	}

	/**
	 * @return the idAdministradorSubsidio
	 */
	public Long getIdAdministradorSubsidio() {
		return idAdministradorSubsidio;
	}

	/**
	 * @param idAdministradorSubsidio the idAdministradorSubsidio to set
	 */
	public void setIdAdministradorSubsidio(Long idAdministradorSubsidio) {
		this.idAdministradorSubsidio = idAdministradorSubsidio;
	}

	/**
	 * @return the medioDePagoModeloDTO
	 */
	public MedioDePagoModeloDTO getMedioDePagoModeloDTO() {
		return medioDePagoModeloDTO;
	}

	/**
	 * @param medioDePagoModeloDTO the medioDePagoModeloDTO to set
	 */
	public void setMedioDePagoModeloDTO(MedioDePagoModeloDTO medioDePagoModeloDTO) {
		this.medioDePagoModeloDTO = medioDePagoModeloDTO;
	}

	/**
	 * @return the idBeneficiarios
	 */
	public List<Long> getIdBeneficiarios() {
		return idBeneficiarios;
	}

	/**
	 * @param idBeneficiarios the idBeneficiarios to set
	 */
	public void setIdBeneficiarios(List<Long> idBeneficiarios) {
		this.idBeneficiarios = idBeneficiarios;
	}

	/**
	 * @return the novedadVigente
	 */
	public Boolean getNovedadVigente() {
		return novedadVigente;
	}

	/**
	 * @param novedadVigente the novedadVigente to set
	 */
	public void setNovedadVigente(Boolean novedadVigente) {
		this.novedadVigente = novedadVigente;
	}

	/**
	 * @return the fechaExpedicionDocumentoCambio
	 */
	public Long getFechaExpedicionDocumentoBeneficiario() {
		return fechaExpedicionDocumentoBeneficiario;
	}

	/**
	 * @param fechaExpedicionDocumentoCambio the fechaExpedicionDocumentoCambio to set
	 */
	public void setFechaExpedicionDocumentoBeneficiario(Long fechaExpedicionDocumentoBeneficiario) {
		this.fechaExpedicionDocumentoBeneficiario = fechaExpedicionDocumentoBeneficiario;
	}

	/**
	 * @return the motivoSubsanacionExpulsion
	 */
	public String getMotivoSubsanacionExpulsion() {
		return motivoSubsanacionExpulsion;
	}

	/**
	 * @param motivoSubsanacionExpulsion the motivoSubsanacionExpulsion to set
	 */
	public void setMotivoSubsanacionExpulsion(String motivoSubsanacionExpulsion) {
		this.motivoSubsanacionExpulsion = motivoSubsanacionExpulsion;
	}

	/**
	 * @return the grupoFamiliarTrasladoBeneficiario
	 */
	public GrupoFamiliarModeloDTO getGrupoFamiliarTrasladoBeneficiario() {
		return grupoFamiliarTrasladoBeneficiario;
	}

	/**
	 * @param grupoFamiliarTrasladoBeneficiario the grupoFamiliarTrasladoBeneficiario to set
	 */
	public void setGrupoFamiliarTrasladoBeneficiario(GrupoFamiliarModeloDTO grupoFamiliarTrasladoBeneficiario) {
		this.grupoFamiliarTrasladoBeneficiario = grupoFamiliarTrasladoBeneficiario;
	}
	

    /**
     * @return the descripcionIndicacionResidenciaTrabajador
     */
    public String getDescripcionIndicacionResidenciaTrabajador() {
        return descripcionIndicacionResidenciaTrabajador;
    }

    /**
     * @param descripcionIndicacionResidenciaTrabajador the descripcionIndicacionResidenciaTrabajador to set
     */
    public void setDescripcionIndicacionResidenciaTrabajador(String descripcionIndicacionResidenciaTrabajador) {
        this.descripcionIndicacionResidenciaTrabajador = descripcionIndicacionResidenciaTrabajador;
    }

    /**
     * @return the descripcionIndicacionGrupoFam
     */
    public String getDescripcionIndicacionGrupoFam() {
        return descripcionIndicacionGrupoFam;
    }

    /**
     * @param descripcionIndicacionGrupoFam the descripcionIndicacionGrupoFam to set
     */
    public void setDescripcionIndicacionGrupoFam(String descripcionIndicacionGrupoFam) {
        this.descripcionIndicacionGrupoFam = descripcionIndicacionGrupoFam;
    }

    /**
     * @return the fechaDefuncion
     */
    public Long getFechaDefuncion() {
        return fechaDefuncion;
    }

    /**
     * @param fechaDefuncion the fechaDefuncion to set
     */
    public void setFechaDefuncion(Long fechaDefuncion) {
        this.fechaDefuncion = fechaDefuncion;
    }

    /**
     * @return the oportunidadAporte
     */
    public PeriodoPagoPlanillaEnum getOportunidadAporte() {
        return oportunidadAporte;
    }

    /**
     * @param oportunidadAporte the oportunidadAporte to set
     */
    public void setOportunidadAporte(PeriodoPagoPlanillaEnum oportunidadAporte) {
        this.oportunidadAporte = oportunidadAporte;
    }

    /**
     * @return the generoBeneficiario
     */
    public GeneroEnum getGeneroBeneficiario() {
        return generoBeneficiario;
    }

    /**
     * @param generoBeneficiario the generoBeneficiario to set
     */
    public void setGeneroBeneficiario(GeneroEnum generoBeneficiario) {
        this.generoBeneficiario = generoBeneficiario;
    }

    /**
     * @return the estadoCivilBeneficiario
     */
    public EstadoCivilEnum getEstadoCivilBeneficiario() {
        return estadoCivilBeneficiario;
    }

    /**
     * @param estadoCivilBeneficiario the estadoCivilBeneficiario to set
     */
    public void setEstadoCivilBeneficiario(EstadoCivilEnum estadoCivilBeneficiario) {
        this.estadoCivilBeneficiario = estadoCivilBeneficiario;
    }

    /**
     * @return the gradoCursado
     */
    public GradoAcademico getGradoCursado() {
        return gradoCursado;
    }

    /**
     * @param gradoCursado the gradoCursado to set
     */
    public void setGradoCursado(GradoAcademico gradoCursado) {
        this.gradoCursado = gradoCursado;
    }

    /**
     * @return the fechaReporteInvalidezBeneficiario
     */
    public Long getFechaReporteInvalidezBeneficiario() {
        return fechaReporteInvalidezBeneficiario;
    }

    /**
     * @param fechaReporteInvalidezBeneficiario the fechaReporteInvalidezBeneficiario to set
     */
    public void setFechaReporteInvalidezBeneficiario(Long fechaReporteInvalidezBeneficiario) {
        this.fechaReporteInvalidezBeneficiario = fechaReporteInvalidezBeneficiario;
    }

    /**
     * @return the condicionInvalidezBeneficiario
     */
    public Boolean getCondicionInvalidezBeneficiario() {
        return condicionInvalidezBeneficiario;
    }

    /**
     * @param condicionInvalidezBeneficiario the condicionInvalidezBeneficiario to set
     */
    public void setCondicionInvalidezBeneficiario(Boolean condicionInvalidezBeneficiario) {
        this.condicionInvalidezBeneficiario = condicionInvalidezBeneficiario;
    }

    /**
     * @return the padreBiologico
     */
    public PersonaModeloDTO getPadreBiologico() {
        return padreBiologico;
    }

    /**
     * @param padreBiologico the padreBiologico to set
     */
    public void setPadreBiologico(PersonaModeloDTO padreBiologico) {
        this.padreBiologico = padreBiologico;
    }

    /**
     * @return the madreBiologica
     */
    public PersonaModeloDTO getMadreBiologica() {
        return madreBiologica;
    }

    /**
     * @param madreBiologica the madreBiologica to set
     */
    public void setMadreBiologica(PersonaModeloDTO madreBiologica) {
        this.madreBiologica = madreBiologica;
    }

    /**
     * @return the fechaInicioInvalidezHijo
     */
    public Long getFechaInicioInvalidezHijo() {
        return fechaInicioInvalidezHijo;
    }

    /**
     * @param fechaInicioInvalidezHijo the fechaInicioInvalidezHijo to set
     */
    public void setFechaInicioInvalidezHijo(Long fechaInicioInvalidezHijo) {
        this.fechaInicioInvalidezHijo = fechaInicioInvalidezHijo;
    }

    /**
     * @return the fechaInicioInvalidezTrabajador
     */
    public Long getFechaInicioInvalidezTrabajador() {
        return fechaInicioInvalidezTrabajador;
    }

    /**
     * @param fechaInicioInvalidezTrabajador the fechaInicioInvalidezTrabajador to set
     */
    public void setFechaInicioInvalidezTrabajador(Long fechaInicioInvalidezTrabajador) {
        this.fechaInicioInvalidezTrabajador = fechaInicioInvalidezTrabajador;
    }

    /**
     * @return the fechaInicioInvalidezPadre
     */
    public Long getFechaInicioInvalidezPadre() {
        return fechaInicioInvalidezPadre;
    }

    /**
     * @param fechaInicioInvalidezPadre the fechaInicioInvalidezPadre to set
     */
    public void setFechaInicioInvalidezPadre(Long fechaInicioInvalidezPadre) {
        this.fechaInicioInvalidezPadre = fechaInicioInvalidezPadre;
    }
    
    /**
	 * @return
	 */
	public OrientacionSexualEnum getOrientacionSexual() {
		return orientacionSexual;
	}

	/**Establece el valor de orientacionSexual
	 * @param orientacionSexual
	 */
	public void setOrientacionSexual(OrientacionSexualEnum orientacionSexual) {
		this.orientacionSexual = orientacionSexual;
	}
	
	/**
	 * @return
	 */
	public FactorVulnerabilidadEnum getFactorVulnerabilidad() {
		return factorVulnerabilidad;
	}

	/**Establece el valor de factorVulnerabilidad
	 * @param factorVulnerabilidad
	 */
	public void setFactorVulnerabilidad(FactorVulnerabilidadEnum factorVulnerabilidad) {
		this.factorVulnerabilidad = factorVulnerabilidad;
	}
	
	/**
	 * @return
	 */
	public PertenenciaEtnicaEnum getPertenenciaEtnica() {
		return pertenenciaEtnica;
	}
	
	/**Establece el valor de pertenenciaEtnica
	 * @param pertenenciaEtnica
	 */
	public void setPertenenciaEtnica(PertenenciaEtnicaEnum pertenenciaEtnica) {
		this.pertenenciaEtnica = pertenenciaEtnica;
	}
	
	/**
	 * @return
	 */
	public SectorUbicacionEnum getSectorUbicacion() {
		return sectorUbicacion;
	}

	/**Establece el valor del sectorUbicacion
	 * @param sectorUbicacion
	 */
	public void setSectorUbicacion(SectorUbicacionEnum sectorUbicacion) {
		this.sectorUbicacion = sectorUbicacion;
	}
	
	/**
	 * @return
	 */
	public Long getPaisResidencia() {
		return paisResidencia;
	}

	/**Establece el valor del paisResidencia
	 * @param paisResidencia
	 */
	public void setPaisResidencia(Long paisResidencia) {
		this.paisResidencia = paisResidencia;
	}

	public Long getFechaRetiro() {
		return fechaRetiro;
	}

	public void setFechaRetiro(Long fechaRetiro) {
		this.fechaRetiro = fechaRetiro;
	}
	public Long getIdResguardo() {
		return this.idResguardo;
	}

	public void setIdResguardo(Long idResguardo) {
		this.idResguardo = idResguardo;
	}

	public Long getIdPuebloIndigena() {
		return this.idPuebloIndigena;
	}

	public void setIdPuebloIndigena(Long idPuebloIndigena) {
		this.idPuebloIndigena = idPuebloIndigena;
	}
		public Long getFechaInicioConyugeCuidadorTrabajador() {
		return this.fechaInicioConyugeCuidadorTrabajador;
	}

	public void setFechaInicioConyugeCuidadorTrabajador(Long fechaInicioConyugeCuidadorTrabajador) {
		this.fechaInicioConyugeCuidadorTrabajador = fechaInicioConyugeCuidadorTrabajador;
	}

	public Long getFechaInicioConyugeCuidadorHijo() {
		return this.fechaInicioConyugeCuidadorHijo;
	}

	public void setFechaInicioConyugeCuidadorHijo(Long fechaInicioConyugeCuidadorHijo) {
		this.fechaInicioConyugeCuidadorHijo = fechaInicioConyugeCuidadorHijo;
	}

	public Long getFechaFinConyugeCuidadorTrabajador() {
		return this.fechaFinConyugeCuidadorTrabajador;
	}

	public void setFechaFinConyugeCuidadorTrabajador(Long fechaFinConyugeCuidadorTrabajador) {
		this.fechaFinConyugeCuidadorTrabajador = fechaFinConyugeCuidadorTrabajador;
	}

	public Long getFechaFinConyugeCuidadorHijo() {
		return this.fechaFinConyugeCuidadorHijo;
	}

	public void setFechaFinConyugeCuidadorHijo(Long fechaFinConyugeCuidadorHijo) {
		this.fechaFinConyugeCuidadorHijo = fechaFinConyugeCuidadorHijo;
	}


	public Boolean getConyugeCuidadorTrabajador() {
		return this.conyugeCuidadorTrabajador;
	}

	public void setConyugeCuidadorTrabajador(Boolean conyugeCuidadorTrabajador) {
		this.conyugeCuidadorTrabajador = conyugeCuidadorTrabajador;
	}

	public Boolean getConyugeCuidadorHijo() {
		return this.conyugeCuidadorHijo;
	}

	public void setConyugeCuidadorHijo(Boolean conyugeCuidadorHijo) {
		this.conyugeCuidadorHijo = conyugeCuidadorHijo;
	}

	public Long getFechaInicioConyugeCuidadorPadre() {
		return this.fechaInicioConyugeCuidadorPadre;
	}

	public void setFechaInicioConyugeCuidadorPadre(Long fechaInicioConyugeCuidadorPadre) {
		this.fechaInicioConyugeCuidadorPadre = fechaInicioConyugeCuidadorPadre;
	}

	public Long getFechaFinConyugeCuidadorPadre() {
		return this.fechaFinConyugeCuidadorPadre;
	}

	public void setFechaFinConyugeCuidadorPadre(Long fechaFinConyugeCuidadorPadre) {
		this.fechaFinConyugeCuidadorPadre = fechaFinConyugeCuidadorPadre;
	}

	public Boolean getConyugeCuidadorPadre() {
		return this.conyugeCuidadorPadre;
	}

	public void setConyugeCuidadorPadre(Boolean conyugeCuidadorPadre) {
		this.conyugeCuidadorPadre = conyugeCuidadorPadre;
	}
	
	public Long getIdConyugeCuidador() {
		return this.idConyugeCuidador;
	}

	public void setIdConyugeCuidador(Long idConyugeCuidador) {
		this.idConyugeCuidador = idConyugeCuidador;
	}

	public List<Long> getRolesAfiliado(){
		return this.rolesAfiliado;
	}

	public void setRolesAfiliado(List<Long> roles){
		this.rolesAfiliado = roles;
	}

    /**
     * @return the comentariosInvalidez
     */
    public String getComentariosInvalidez() {
        return comentariosInvalidez;
    }

    /**
     * @param comentariosInvalidez
     *        the comentariosInvalidez to set
     */
    public void setComentariosInvalidez(String comentariosInvalidez) {
        this.comentariosInvalidez = comentariosInvalidez;
    }

	@Override
	public String toString() {
		return "{" +
			" idPersona='" + getIdPersona() + "'" +
			", idBeneficiario='" + getIdBeneficiario() + "'" +
			", idGrupoFamiliar='" + getIdGrupoFamiliar() + "'" +
			", idRolAfiliado='" + getIdRolAfiliado() + "'" +
			", idAdministradorSubsidio='" + getIdAdministradorSubsidio() + "'" +
			", numeroIdentificacion='" + getNumeroIdentificacion() + "'" +
			", tipoIdentificacion='" + getTipoIdentificacion() + "'" +
			", tipoIdentificacionEmpleador='" + getTipoIdentificacionEmpleador() + "'" +
			", numeroIdentificacionEmpleador='" + getNumeroIdentificacionEmpleador() + "'" +
			", nombreRazonSocialEmpleador='" + getNombreRazonSocialEmpleador() + "'" +
			", tipoSolicitanteTrabajador='" + getTipoSolicitanteTrabajador() + "'" +
			", tipoIdentificacionTrabajador='" + getTipoIdentificacionTrabajador() + "'" +
			", numeroIdentificacionTrabajador='" + getNumeroIdentificacionTrabajador() + "'" +
			", primerApellidoTrabajador='" + getPrimerApellidoTrabajador() + "'" +
			", segundoApellidoTrabajador='" + getSegundoApellidoTrabajador() + "'" +
			", primerNombreTrabajador='" + getPrimerNombreTrabajador() + "'" +
			", segundoNombreTrabajador='" + getSegundoNombreTrabajador() + "'" +
			", tipoIdentificacionBeneficiario='" + getTipoIdentificacionBeneficiario() + "'" +
			", numeroIdentificacionBeneficiario='" + getNumeroIdentificacionBeneficiario() + "'" +
			", tipoIdentificacionBeneficiarioAnterior='" + getTipoIdentificacionBeneficiarioAnterior() + "'" +
			", numeroIdentificacionBeneficiarioAnterior='" + getNumeroIdentificacionBeneficiarioAnterior() + "'" +
			", primerApellidoBeneficiario='" + getPrimerApellidoBeneficiario() + "'" +
			", segundoApellidoBeneficiario='" + getSegundoApellidoBeneficiario() + "'" +
			", primerNombreBeneficiario='" + getPrimerNombreBeneficiario() + "'" +
			", segundoNombreBeneficiario='" + getSegundoNombreBeneficiario() + "'" +
			", clasificacion='" + getClasificacion() + "'" +
			", fechaExpedicionDocumentoTrabajador='" + getFechaExpedicionDocumentoTrabajador() + "'" +
			", generoTrabajador='" + getGeneroTrabajador() + "'" +
			", fechaNacimientoTrabajador='" + getFechaNacimientoTrabajador() + "'" +
			", fechaNacimientoBeneficiario='" + getFechaNacimientoBeneficiario() + "'" +
			", nivelEducativoTrabajador='" + getNivelEducativoTrabajador() + "'" +
			", ocupacionProfesionTrabajador='" + getOcupacionProfesionTrabajador() + "'" +
			", departamentoTrabajador='" + getDepartamentoTrabajador() + "'" +
			", municipioTrabajador='" + getMunicipioTrabajador() + "'" +
			", direccionResidenciaTrabajador='" + getDireccionResidenciaTrabajador() + "'" +
			", descripcionIndicacionResidenciaTrabajador='" + getDescripcionIndicacionResidenciaTrabajador() + "'" +
			", codigoPostalTrabajador='" + getCodigoPostalTrabajador() + "'" +
			", indicativoTelFijoTrabajador='" + getIndicativoTelFijoTrabajador() + "'" +
			", telefonoFijoTrabajador='" + getTelefonoFijoTrabajador() + "'" +
			", telefonoCelularTrabajador='" + getTelefonoCelularTrabajador() + "'" +
			", correoElectronicoTrabajador='" + getCorreoElectronicoTrabajador() + "'" +
			", claseTrabajador='" + getClaseTrabajador() + "'" +
			", fechaInicioLaboresConEmpleador='" + getFechaInicioLaboresConEmpleador() + "'" +
			", tipoSalarioTrabajador='" + getTipoSalarioTrabajador() + "'" +
			", valorSalarioMensualTrabajador='" + getValorSalarioMensualTrabajador() + "'" +
			", horasLaboralesMesTrabajador='" + getHorasLaboralesMesTrabajador() + "'" +
			", cargoOficioDesempeniadoTrabajador='" + getCargoOficioDesempeniadoTrabajador() + "'" +
			", tipoContratoLaboralTrabajador='" + getTipoContratoLaboralTrabajador() + "'" +
			", fechaTerminacioContratoTrabajador='" + getFechaTerminacioContratoTrabajador() + "'" +
			", trabajadorMismoReptLegalDelEpleador='" + getTrabajadorMismoReptLegalDelEpleador() + "'" +
			", trabajadorEsSocioDelEmpleador='" + getTrabajadorEsSocioDelEmpleador() + "'" +
			", trabajadorEsConyugeDelSocioDelEmpleador='" + getTrabajadorEsConyugeDelSocioDelEmpleador() + "'" +
			", sucursalEmpleadorTrabajador='" + getSucursalEmpleadorTrabajador() + "'" +
			", claseIndependiente='" + getClaseIndependiente() + "'" +
			", porcentajePagoAportesIndependiente='" + getPorcentajePagoAportesIndependiente() + "'" +
			", ingresosMensualesIndependiente='" + getIngresosMensualesIndependiente() + "'" +
			", entidadPagadoraDeAportes='" + getEntidadPagadoraDeAportes() + "'" +
			", valorMesadaPensional='" + getValorMesadaPensional() + "'" +
			", pagadorFondoPensiones='" + getPagadorFondoPensiones() + "'" +
			", entidadPagadoraDeAportesPensionado='" + getEntidadPagadoraDeAportesPensionado() + "'" +
			", idEntidadPagadora='" + getIdEntidadPagadora() + "'" +
			", estadoCivilTrabajador='" + getEstadoCivilTrabajador() + "'" +
			", departamentoResidenciaGrupoFam='" + getDepartamentoResidenciaGrupoFam() + "'" +
			", municipioResidenciaGrupoFam='" + getMunicipioResidenciaGrupoFam() + "'" +
			", direccionResidenciaGrupoFam='" + getDireccionResidenciaGrupoFam() + "'" +
			", descripcionIndicacionGrupoFam='" + getDescripcionIndicacionGrupoFam() + "'" +
			", codigoPostalGrupoFam='" + getCodigoPostalGrupoFam() + "'" +
			", indicativoTelFijoGrupoFam='" + getIndicativoTelFijoGrupoFam() + "'" +
			", telefonoFijoGrupoFam='" + getTelefonoFijoGrupoFam() + "'" +
			", telCelularGrupoFam='" + getTelCelularGrupoFam() + "'" +
			", emailGrupoFam='" + getEmailGrupoFam() + "'" +
			", fechaExpedicionDocConyuge='" + getFechaExpedicionDocConyuge() + "'" +
			", generoConyuge='" + getGeneroConyuge() + "'" +
			", fechaNacimientoConyuge='" + getFechaNacimientoConyuge() + "'" +
			", nivelEducativoConyuge='" + getNivelEducativoConyuge() + "'" +
			", profesionConyuge='" + getProfesionConyuge() + "'" +
			", valorIngresoMensualConyuge='" + getValorIngresoMensualConyuge() + "'" +
			", fechaExpedicionDocHijo='" + getFechaExpedicionDocHijo() + "'" +
			", generoHijo='" + getGeneroHijo() + "'" +
			", fechaNacimientoHijo='" + getFechaNacimientoHijo() + "'" +
			", nivelEducativoHijo='" + getNivelEducativoHijo() + "'" +
			", gradoCursadoHijo='" + getGradoCursadoHijo() + "'" +
			", gradoCursado='" + getGradoCursado() + "'" +
			", profesionHijo='" + getProfesionHijo() + "'" +
			", fechaVencimientoCertEscolar='" + getFechaVencimientoCertEscolar() + "'" +
			", fechaReporteCertEscolarHijo='" + getFechaReporteCertEscolarHijo() + "'" +
			", fechaRepoteinvalidezHijo='" + getFechaRepoteinvalidezHijo() + "'" +
			", observacionesHijo='" + getObservacionesHijo() + "'" +
			", expediciondocPadre='" + getExpediciondocPadre() + "'" +
			", generoPadre='" + getGeneroPadre() + "'" +
			", fechaNacimientoPadre='" + getFechaNacimientoPadre() + "'" +
			", nivelEducativoPadre='" + getNivelEducativoPadre() + "'" +
			", ocupacionProfesionPadre='" + getOcupacionProfesionPadre() + "'" +
			", fechaReporteInvalidezPadre='" + getFechaReporteInvalidezPadre() + "'" +
			", observacionesPadre='" + getObservacionesPadre() + "'" +
			", estadoAfiliadoPrincipalBeneficiario='" + getEstadoAfiliadoPrincipalBeneficiario() + "'" +
			", parentescoBeneficiarios='" + getParentescoBeneficiarios() + "'" +
			", fechaInicioUnionConAfiliadoPrincipal='" + getFechaInicioUnionConAfiliadoPrincipal() + "'" +
			", fechaRegistroActivacionBeneficiario='" + getFechaRegistroActivacionBeneficiario() + "'" +
			", fechaFinsociedadConyugal='" + getFechaFinsociedadConyugal() + "'" +
			", motivoDesafiliacionTrabajador='" + getMotivoDesafiliacionTrabajador() + "'" +
			", motivoDesafiliacionBeneficiario='" + getMotivoDesafiliacionBeneficiario() + "'" +
			", fechaInactivacionBeneficiario='" + getFechaInactivacionBeneficiario() + "'" +
			", estadoCivilConyuge='" + getEstadoCivilConyuge() + "'" +
			", fechaInicioNovedad='" + getFechaInicioNovedad() + "'" +
			", fechaFinNovedad='" + getFechaFinNovedad() + "'" +
			", vigenciaPagosPensionado='" + getVigenciaPagosPensionado() + "'" +
			", vigenciaPagosDependiente='" + getVigenciaPagosDependiente() + "'" +
			", tarifaPagoAportesIndependiente='" + getTarifaPagoAportesIndependiente() + "'" +
			", tarifaPagoAportesPensionado='" + getTarifaPagoAportesPensionado() + "'" +
			", solicitudTarjeta='" + getSolicitudTarjeta() + "'" +
			", motivoReexpedTarjetaDependiente='" + getMotivoReexpedTarjetaDependiente() + "'" +
			", fechaReporteInvalidezTrabajador='" + getFechaReporteInvalidezTrabajador() + "'" +
			", fechaReporteFallecimientoTrabajador='" + getFechaReporteFallecimientoTrabajador() + "'" +
			", estadoAfiliacionTrabajador='" + getEstadoAfiliacionTrabajador() + "'" +
			", estadoTarjetaTrabajador='" + getEstadoTarjetaTrabajador() + "'" +
			", estadoTarjetaGrupoFam='" + getEstadoTarjetaGrupoFam() + "'" +
			", motivoAnulacionTrabajador='" + getMotivoAnulacionTrabajador() + "'" +
			", motivoAnulacionBeneficiario='" + getMotivoAnulacionBeneficiario() + "'" +
			", primerNombreTitularCuentaTrabajador='" + getPrimerNombreTitularCuentaTrabajador() + "'" +
			", segundoNombreTitularCuentaTrabajador='" + getSegundoNombreTitularCuentaTrabajador() + "'" +
			", primerApellidoTitularCuentaTrabajador='" + getPrimerApellidoTitularCuentaTrabajador() + "'" +
			", segundoApellidoTitularCuentaTrabajador='" + getSegundoApellidoTitularCuentaTrabajador() + "'" +
			", numeroDocTitularTrabajador='" + getNumeroDocTitularTrabajador() + "'" +
			", tipoDocTitularTrabajador='" + getTipoDocTitularTrabajador() + "'" +
			", bancoDondeRegistraCuentaTrabajador='" + getBancoDondeRegistraCuentaTrabajador() + "'" +
			", tipoCuentaTrabajador='" + getTipoCuentaTrabajador() + "'" +
			", numeroCuentaTrabajador='" + getNumeroCuentaTrabajador() + "'" +
			", estadoAfiliadoPrincipalTrabajador='" + getEstadoAfiliadoPrincipalTrabajador() + "'" +
			", estadoGeneralSolicitanteCajacompTrabajador='" + getEstadoGeneralSolicitanteCajacompTrabajador() + "'" +
			", canalTrabajador='" + getCanalTrabajador() + "'" +
			", serviciosSinAfiliacionTrabajador='" + getServiciosSinAfiliacionTrabajador() + "'" +
			", causaInactivacionServiciosSinAfiliarTrabajador='" + getCausaInactivacionServiciosSinAfiliarTrabajador() + "'" +
			", fechaNovedadActivacionServiciosSinAfiliarTrabajador='" + getFechaNovedadActivacionServiciosSinAfiliarTrabajador() + "'" +
			", categoriaTrabajador='" + getCategoriaTrabajador() + "'" +
			", fechaFinaliazaServicioSinAfiliacionTrabajador='" + getFechaFinaliazaServicioSinAfiliacionTrabajador() + "'" +
			", tipoDocumentoAdminGF='" + getTipoDocumentoAdminGF() + "'" +
			", numeroDocumentoAdminGF='" + getNumeroDocumentoAdminGF() + "'" +
			", primNombreAdminGF='" + getPrimNombreAdminGF() + "'" +
			", segNombreAdminGF='" + getSegNombreAdminGF() + "'" +
			", primApellidoAdminGF='" + getPrimApellidoAdminGF() + "'" +
			", segApellidoAdminGF='" + getSegApellidoAdminGF() + "'" +
			", relacionConGrupoFam='" + getRelacionConGrupoFam() + "'" +
			", listaChequeoNovedad='" + getListaChequeoNovedad() + "'" +
			", idPersonas='" + getIdPersonas() + "'" +
			", grupoFamiliarBeneficiario='" + getGrupoFamiliarBeneficiario() + "'" +
			", idEmpleadorDependiente='" + getIdEmpleadorDependiente() + "'" +
			", medioDePagoModeloDTO='" + getMedioDePagoModeloDTO() + "'" +
			", idBeneficiarios='" + getIdBeneficiarios() + "'" +
			", fechaExpedicionDocumentoBeneficiario='" + getFechaExpedicionDocumentoBeneficiario() + "'" +
			", motivoSubsanacionExpulsion='" + getMotivoSubsanacionExpulsion() + "'" +
			", grupoFamiliarTrasladoBeneficiario='" + getGrupoFamiliarTrasladoBeneficiario() + "'" +
			", fechaDefuncion='" + getFechaDefuncion() + "'" +
			", oportunidadAporte='" + getOportunidadAporte() + "'" +
			", generoBeneficiario='" + getGeneroBeneficiario() + "'" +
			", estadoCivilBeneficiario='" + getEstadoCivilBeneficiario() + "'" +
			", fechaReporteInvalidezBeneficiario='" + getFechaReporteInvalidezBeneficiario() + "'" +
			", padreBiologico='" + getPadreBiologico() + "'" +
			", madreBiologica='" + getMadreBiologica() + "'" +
			", fechaInicioInvalidezHijo='" + getFechaInicioInvalidezHijo() + "'" +
			", fechaInicioInvalidezTrabajador='" + getFechaInicioInvalidezTrabajador() + "'" +
			", fechaInicioInvalidezPadre='" + getFechaInicioInvalidezPadre() + "'" +
			", orientacionSexual='" + getOrientacionSexual() + "'" +
			", factorVulnerabilidad='" + getFactorVulnerabilidad() + "'" +
			", pertenenciaEtnica='" + getPertenenciaEtnica() + "'" +
			", sectorUbicacion='" + getSectorUbicacion() + "'" +
			", paisResidencia='" + getPaisResidencia() + "'" +
			", fechaRetiro='" + getFechaRetiro() + "'" +
			", comentariosInvalidez='" + getComentariosInvalidez() + "'" +
			"}";
	}

    
}
