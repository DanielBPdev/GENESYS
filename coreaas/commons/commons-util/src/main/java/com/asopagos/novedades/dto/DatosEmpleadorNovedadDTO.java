/**
 * 
 */
package com.asopagos.novedades.dto;

import java.math.BigDecimal;
import java.util.List;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.entidades.transversal.core.CodigoCIIU;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.entidades.transversal.personas.ARL;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionEnum;
import com.asopagos.enumeraciones.core.MotivoInactivacionRetencionSubsidioEnum;
import com.asopagos.enumeraciones.core.MotivoRetencionSubsidioEnum;
import com.asopagos.enumeraciones.core.TipoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import java.io.Serializable;

/**
 * DTO que contiene los campos que se pueden modificar a un empleador.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
/**
 * Clase que contiene la lógica para validar 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class DatosEmpleadorNovedadDTO implements DatosNovedadDTO, Serializable {

    /**
     * Id del empleador
     */
    private Long idEmpleador;
    /**
     * Tipo de identificación del empleador
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    /**
     * Número de identificación del empleador
     */
    private String numeroIdentificacion;
    
    /**
     * Variable listaChequeoNovedad
     */
    private List<ItemChequeoDTO> listaChequeoNovedad;

    /**
     * Variable digitoVerificacion
     */
    private Short digitoVerificacion;
    /**
     * Variable razonSocial
     */
    private String razonSocial;
    
    /**
     * Variable primerNombre persona empleador
     */
    private String primerNombre;
    /**
     * Variable segundoNombre persona empleador
     */
    private String segundoNombre;
    /**
     * Variable primerApellido persona empleador
     */
    private String primerApellido;
    /**
     * Variable segundoApellido persona empleador
     */
    private String segundoApellido;
    
    /**
     * Variable tipoSolicitante
     */
    private TipoTipoSolicitanteEnum tipoSolicitante;
    /**
     * Variable nombreComercial
     */
    private String nombreComercial;
    /**
     * Variable fechaConstitucion
     */
    private Long fechaConstitucion;
    /**
     * Variable naturalezaJuridica
     */
    private NaturalezaJuridicaEnum naturalezaJuridica;
    /**
     * Variable codigoCIIU
     */
    private CodigoCIIU codigoCIIU;
    /**
     * Variable arl
     */
    private ARL arl;
    /**
     * Variable numeroTotalTrabajadores
     */
    private Integer numeroTotalTrabajadores;
    /**
     * Variable valorTotalUltimaNomina
     */
    private BigDecimal valorTotalUltimaNomina;
    /**
     * Variable periodoUltimaNomina
     */
    private Long periodoUltimaNomina;
    /**
     * Variable idUltimaCajaCompensacion
     */
    private Integer idUltimaCajaCompensacion;
    /**
     * Variable paginaWeb
     */
    private String paginaWeb;
    /**
     * Variable que contiene el id de la ubicación principal.
     */
    private Long idUbicacion;
    /**
     * Variable departamento
     */
    private Object departamento;
    /**
     * Variable municipio
     */
    private Municipio municipio;
    /**
     * Variable direccionFisica
     */
    private String direccionFisica;
    
    /**
     * Variable direccionFisica
     */
    private String descripcionIndicacion;
    /**
     * Variable codigoPostal
     */
    private String codigoPostal;
    /**
     * Variable indicativoTelFijo
     */
    private String indicativoTelFijo;
    /**
     * Variable telefonoFijo
     */
    private String telefonoFijo;
    /**
     * Variable telefonoCelular
     */
    private String telefonoCelular;
    /**
     * Variable autorizacionEnvioEmail
     */
    private Boolean autorizacionEnvioEmail;
    /**
     * Variable email
     */
    private String email;
    /**
     * Variable con el id de la ubicacion principal
     */
    private Long idUbicacionPrincipal;
    /**
     * Variable correspondenciaIgualOficinaPrincipal
     */
    private Boolean correspondenciaIgualOficinaPrincipal;
    /**
     * Variable municipioOficinaPrincipal
     */
    private Municipio municipioOficinaPrincipal;
    /**
     * Variable direccionFisicaOficinaPrincipal
     */
    private String direccionFisicaOficinaPrincipal;
    /**
     * Variable direccionFisicaOficinaPrincipal
     */
    private String descripcionIndicacionOficinaPrincipal;
    /**
     * Variable codigoPostalOficinaPrincipal
     */
    private String codigoPostalOficinaPrincipal;
    /**
     * Variable indicativoTelFijoOficinaPrincipal
     */
    private String indicativoTelFijoOficinaPrincipal;
    /**
     * Variable telefonoFijoOficinaPrincipal
     */
    private String telefonoFijoOficinaPrincipal;
	/**
     * Variable telefonoCelularOficinaPrincipal
     */
    private String telefonoCelularOficinaPrincipal;
    /**
     * Variable con el id de la ubicación judicial.
     */
    private Long idUbicacionJudicial;
    /**
     * Variable judicialIgualOficinaPrincipal
     */
    private Integer judicialIgualOficinaPrincipal;
    /**
     * Variable municipioJudicial
     */
    private Municipio municipioJudicial;
    /**
     * Variable direccionFisicaJudicial
     */
    private String direccionFisicaJudicial;
    
    /**
     * Variable direccionFisicaOficinaPrincipal
     */
    private String descripcionIndicacionJudicial;
    /**
     * Variable codigoPostalJudicial
     */
    private String codigoPostalJudicial;
    /**
     * Variable indicativoTelFijoJudicial
     */
    private Integer indicativoTelFijoJudicial;
    /**
     * Variable telefonoFijoJudicial
     */
    private String telefonoFijoJudicial;
    /**
     * Variable telefonoCelularJudicial
     */
    private String telefonoCelularJudicial;
    /**
     * Variable tipoIdentificacionRepLegal
     */
    private TipoIdentificacionEnum tipoIdentificacionRepLegal;
    /**
     * Variable numeroIdentificacionRepLegal
     */
    private String numeroIdentificacionRepLegal;
    /**
     * Variable primerNombreRepLegal
     */
    private String primerNombreRepLegal;
    /**
     * Variable segundoNombreRepLegal
     */
    private String segundoNombreRepLegal;
    /**
     * Variable primerApellidoRepLegal
     */
    private String primerApellidoRepLegal;
    /**
     * Variable segundoApellidoRepLegal
     */
    private String segundoApellidoRepLegal;
    /**
     * Variable emailRepLegal
     */
    private String emailRepLegal;
    /**
     * Variable indicativoTelFijoRepLegal
     */
    private String indicativoTelFijoRepLegal;
    /**
     * Variable telefonoFijoRepLegal
     */
    private String telefonoFijoRepLegal;
    /**
     * Variable telefonoCelularRepLegal
     */
    private String telefonoCelularRepLegal;
    /**
     * Variable tipoIdentificacionRepLegalSupl
     */
    private TipoIdentificacionEnum tipoIdentificacionRepLegalSupl;
    /**
     * Variable numeroIdentificacionRepLegalSupl
     */
    private String numeroIdentificacionRepLegalSupl;
    /**
     * Variable primerNombreRepLegalSupl
     */
    private String primerNombreRepLegalSupl;
    /**
     * Variable segundoNombreRepLegalSupl
     */
    private String segundoNombreRepLegalSupl;
    /**
     * Variable primerApellidoRepLegalSupl
     */
    private String primerApellidoRepLegalSupl;
    /**
     * Variable segundoApellidoRepLegalSupl
     */
    private String segundoApellidoRepLegalSupl;
    /**
     * Variable emailRepLegalSupl
     */
    private String emailRepLegalSupl;
    /**
     * Variable indicativoTelFijoRepLegalSupl
     */
    private String indicativoTelFijoRepLegalSupl;
    /**
     * Variable telefonoFijoRepLegalSupl
     */
    private String telefonoFijoRepLegalSupl;
    /**
     * Variable telefonoCelularRepLegalSupl
     */
    private String telefonoCelularRepLegalSupl;
    
    /**
     * Variable sucursalIgualOficinaPrincipal
     */
    private Boolean sucursalIgualOficinaPrincipal;
    /**
     * Variable codigoSucursal
     */
    private String codigoSucursal;
    /**
     * Variable nombreSucursal
     */
    private String nombreSucursal;
    /**
     * Variable municipioSucursal
     */
    private Municipio municipioSucursal;
    /**
     * Variable direccionFisicaSucursal
     */
    private String direccionFisicaSucursal;

    /**
     * Variable direccionFisicaOficinaPrincipal
     */
    private String descripcionIndicacionSucursal;
    /**
     * Variable que continee el id de la ubicacion de la sucursal.
     */
    private Long idUbicacionSucursal;
    /**
     * Variable codigoPostalSucursal
     */
    private String codigoPostalSucursal;
    /**
     * Variable indicativoTelFijoSucursal
     */
    private String indicativoTelFijoSucursal;
    /**
     * Variable telefonoFijoSucursal
     */
    private String telefonoFijoSucursal;
    /**
     * Variable telefonoCelularSucursal
     */
    private String telefonoCelularSucursal;

    /**
     * Variable que contiene el id de la sucursal a modificar.
     */
    private Long idSucursalEmpresa;
    /**
     * Variable codigoCIIUSucursal
     */
    private CodigoCIIU codigoCIIUSucursal;
    /**
     * Variable idRolAfiliacion
     */
    private Long idRolAfiliacion; 
    /**
     * Variable rolAfiliacionIgualRepresentanteLegal
     */
    private Boolean rolAfiliacionIgualRepresentanteLegal;
    /**
     * Variable tipoIdentificacionRolAfiliacion
     */
    private TipoIdentificacionEnum tipoIdentificacionRolAfiliacion;
    /**
     * Variable numeroIdentificacionRolAfiliacion
     */
    private String numeroIdentificacionRolAfiliacion;
    /**
     * Variable primerNombreRolAfiliacion
     */
    private String primerNombreRolAfiliacion;
    /**
     * Variable segundoNombreRolAfiliacion
     */
    private String segundoNombreRolAfiliacion;
    /**
     * Variable primerApellidoRolAfiliacion
     */
    private String primerApellidoRolAfiliacion;
    /**
     * Variable segundoApellidoRolAfiliacion
     */
    private String segundoApellidoRolAfiliacion;
    /**
     * Variable emailRolAfiliacion
     */
    private String emailRolAfiliacion;
    /**
     * Variable indicativoTelFijoRolAfiliacion
     */
    private String indicativoTelFijoRolAfiliacion;
    /**
     * Variable telefonoFijoRolAfiliacion
     */
    private String telefonoFijoRolAfiliacion;
    /**
     * Variable telefonoCelularRolAfiliacion
     */
    private String telefonoCelularRolAfiliacion;
    /**
     * Variable sucursalesRolAfiliacion
     */
    private List<Long> sucursalesRolAfiliacion;
    /**
     * Variable idRolAportes
     */
    private Long idRolAportes; 
    /**
     * Variable rolAportesIgualRepresentanteLegal
     */
    private Boolean rolAportesIgualRepresentanteLegal;
    /**
     * Variable tipoIdentificacionRolAportes
     */
    private TipoIdentificacionEnum tipoIdentificacionRolAportes;
    /**
     * Variable numeroIdentificacionRolAportes
     */
    private String numeroIdentificacionRolAportes;
    /**
     * Variable primerNombreRolAportes
     */
    private String primerNombreRolAportes;
    /**
     * Variable segundoNombreRolAportes
     */
    private String segundoNombreRolAportes;
    /**
     * Variable primerApellidoRolAportes
     */
    private String primerApellidoRolAportes;
    /**
     * Variable segundoApellidoRolAportes
     */
    private String segundoApellidoRolAportes;
    /**
     * Variable emailRolAportes
     */
    private String emailRolAportes;
    /**
     * Variable indicativoTelFijoRolAportes
     */
    private String indicativoTelFijoRolAportes;
    /**
     * Variable telefonoFijoRolAportes
     */
    private String telefonoFijoRolAportes;
    /**
     * Variable telefonoCelularRolAportes
     */
    private String telefonoCelularRolAportes;
    /**
     * Variable sucursalesRolAportes
     */
    private List<Long> sucursalesRolAportes;
    /**
     * Variable idRolSubsidio
     */
    private Long idRolSubsidio; 
    /**
     * Variable rolSubsidioIgualRepresentanteLegal
     */
    private Boolean rolSubsidioIgualRepresentanteLegal;
    /**
     * Variable tipoIdentificacionRolSubsidio
     */
    private TipoIdentificacionEnum tipoIdentificacionRolSubsidio;
    /**
     * Variable numeroIdentificacionRolSubsidio
     */
    private String numeroIdentificacionRolSubsidio;
    /**
     * Variable primerNombreRolSubsidio
     */
    private String primerNombreRolSubsidio;
    /**
     * Variable segundoNombreRolSubsidio
     */
    private String segundoNombreRolSubsidio;
    /**
     * Variable primerApellidoRolSubsidio
     */
    private String primerApellidoRolSubsidio;
    /**
     * Variable segundoApellidoRolSubsidio
     */
    private String segundoApellidoRolSubsidio;
    /**
     * Variable emailRolSubsidio
     */
    private String emailRolSubsidio;
    /**
     * Variable indicativoTelFijoRolSubsidio
     */
    private String indicativoTelFijoRolSubsidio;
    /**
     * Variable telefonoFijoRolSubsidio
     */
    private String telefonoFijoRolSubsidio;
    /**
     * Variable telefonoCelularRolSubsidio
     */
    private String telefonoCelularRolSubsidio;
    /**
     * Variable sucursalesRolSubsidio
     */
    private List<Long> sucursalesRolSubsidio;
    /**
     * Variable responsable1CajaContacto
     */
    private String responsable1CajaContacto;
    /**
     * Variable responsable2CajaContacto
     */
    private String responsable2CajaContacto;
    /**
     * Variable codigoNombreCoincidePILA
     */
    private Boolean codigoNombreCoincidePILA;
    /**
     * Variable inactivarSucursal
     */
    private Boolean inactivarSucursal;
    /**
     * Variable empleadorCubiertoLey1429
     */
    private Boolean empleadorCubiertoLey1429;
    /**
     * Variable anoInicioBeneficioLey1429
     */
    private Long anoInicioBeneficioLey1429;
    /**
     * Variable numeroConsecutivoAnosBeneficioLey1429
     */
    private Byte numeroConsecutivoAnosBeneficioLey1429;
    /**
     * Variable anoFinBeneficioLey1429
     */
    private Long anoFinBeneficioLey1429;
    /**
     * Variable motivoInactivacionBeneficioLey1429
     */
    /**
     * Variable motivoInactivacionBeneficioLey1429
     */
    private String motivoInactivacionBeneficioLey1429;
    /**
     * Variable empleadorCubiertoLey590
     */
    private Boolean empleadorCubiertoLey590;
    /**
     * Variable periodoInicioBeneficioLey590
     */
    private Long periodoInicioBeneficioLey590;
    /**
     * Variable numeroConsecutivoAnosBeneficioLey590
     */
    private Byte numeroConsecutivoAnosBeneficioLey590;
    /**
     * Variable periodoFinBeneficioLey590
     */
    private Long periodoFinBeneficioLey590;
    /**
     * Variable motivoInactivacionBeneficioLey590
     */
    private String motivoInactivacionBeneficioLey590;
    /**
     * Variable sucursalOrigenTraslado
     */
    private Long sucursalOrigenTraslado;
    /**
     * Variable sucursalDestinoTraslado
     */
    private Long sucursalDestinoTraslado;
    /**
     * Variable trabajadoresTraslado
     */
    private List<Long> trabajadoresTraslado;
    /**
     * Variable fechaFinLaboresSucursalOrigenTraslado
     */
    private Long fechaFinLaboresSucursalOrigenTraslado;
    /**
     * Variable tipoIdentificacionOrigenSustPatronal
     */
    private TipoIdentificacionEnum tipoIdentificacionOrigenSustPatronal;
    /**
     * Variable numeroIdentificacionOrigenSustPatronal
     */
    private String numeroIdentificacionOrigenSustPatronal;
    /**
     * Variable razonSocialOrigenSustPatronal
     */
    private String razonSocialOrigenSustPatronal;
    /**
     * Variable sucursalesOrigenSustPatronal
     */
    private List<Long> sucursalesOrigenSustPatronal;
    /**
     * Variable fechaFinLaboresOrigenSustPatronal
     */
    private Long fechaFinLaboresOrigenSustPatronal;
    /**
     * Variable tipoIdentificacionDestinoSustPatronal
     */
    private TipoIdentificacionEnum tipoIdentificacionDestinoSustPatronal;
    /**
     * Variable numeroIdentificacionDestinoSustPatronal
     */
    private String numeroIdentificacionDestinoSustPatronal;
    /**
     * Variable razonSocialDestinoSustPatronal
     */
    private String razonSocialDestinoSustPatronal;
    
    /**
     * Variable Sucursal persona
     */
    private List<SucursalPersonaDTO> trabajadoresSustPatronal;
    
    /**
     * Variable motivoDesafiliacion
     */
    private MotivoDesafiliacionEnum motivoDesafiliacion;
    /**
     * Variable motivoAnulacionAfiliacion
     */
    private String motivoAnulacionAfiliacion;
    /**
     * Variable requiereInactivacionCuentaWeb
     */
    private Boolean requiereInactivacionCuentaWeb;
    
    /**
     * Medio de Pago asociado a la Sucursal.
     */
    private TipoMedioDePagoEnum medioDePagoSubsidioMonetarioSucursal;
    
    /**
     * Medio de Pago asociado al Empleador.
     */
    private TipoMedioDePagoEnum medioDePagoSubsidioMonetario;
    
    /**
     * Variable sociosEmpleador que contendra una lista de sociosEmpleador.
     */
    private List<SocioEmpleadorDTO> sociosEmpleador;
    
    /**
     * Contiene los ids para ejecución de procesos masivos en Empleadores o personas.
     */
    private List<Long> idEmpleadoresPersona;
    
    /**
     * Contiene el identificador del empleador destino de la sustitucion patronal
     */
    private Long idEmpleadorDestinoSustPatronal;

    /**
     * Contiene el estado de afiliacion del empleador
     */
    private String estadoAfiliacion;
    
    /**
     * Contiene el motivo de subsanacion de la expulsion
     */
    private String motivoSubsanacionExpulsion;

    /**
     * Contiene el identificador de la empresa destino de la sustitucion patronal
     */
    private Long idEmpresaDestinoSustPatronal;
    
    /**
     * Indicador S/N si el empleador tiene activa la retencion de subsidios para los trabajadores
     */
    private Boolean retencionSubsidioActivaEmpleador;
    
    /**
     * Motivo de la retencion de subsidio activa
     */
    private MotivoRetencionSubsidioEnum motivoRetencionSubsidioEmpleador;

    /**
     * Motivo de la inactivacion de la retencion de subsidio
     */
    private MotivoInactivacionRetencionSubsidioEnum motivoInactivaRetencionSubsidioEmpleador;

    /**
     * Indicador S/N si el empleador tiene activa la retencion de subsidios para los trabajadores
     */
    private Boolean retencionSubsidioActivaSucursal;
    
    /**
     * Motivo de la retencion de subsidio activa
     */
    private MotivoRetencionSubsidioEnum motivoRetencionSubsidioSucursal;

    /**
     * Motivo de la inactiviacion de retencion de subsidio
     */
    private MotivoInactivacionRetencionSubsidioEnum motivoInactivaRetencionSubsidioSucursal;

    /**
     * Tipo de identificación del empleador nuevo
     */
    private TipoIdentificacionEnum tipoIdentificacionNuevo;
    /**
     * Número de identificación del empleador nuevo
     */
    private String numeroIdentificacionNuevo;

    /**
     * Variable digitoVerificacion nuevo
     */
    private Short digitoVerificacionNuevo;
    
    /**
     * Indica si el empleador pertenece a uno de los departamentos validos para 
     * acceder al benficio de ley 1429
     */
    private Boolean perteneceDepartamento;
    
    /**
     * GLPI 62660 Sustitucion Patronal
     */
    private String serialEmpresa;

	/**
	 * Método que retorna el valor de idEmpleador.
	 * @return valor de idEmpleador.
	 */

	/**
     * GLPI 62260 sustitucion patronal
     * Es usado para setear Fecha inicio labores con empleador (roaFechaAfiliacion)
     * Es usado para setear Ultima fecha de ingreso: (roaFechaIngreso)
     */
    private Long fechaInicioAfiliacion;

	/**
     * GLPI 64825 trasladoCajasCompensacion
     * Es usado para el cambio de marca para el traslado de empresas entre ccf
     */
    private Boolean trasladoCajasCompensacion;

	public Long getIdEmpleador() {
		return idEmpleador;
	}

	/**
	 * Método encargado de modificar el valor de idEmpleador.
	 * @param valor para modificar idEmpleador.
	 */
	public void setIdEmpleador(Long idEmpleador) {
		this.idEmpleador = idEmpleador;
	}

	/**
	 * Método que retorna el valor de tipoIdentificacion.
	 * @return valor de tipoIdentificacion.
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacion.
	 * @param valor para modificar tipoIdentificacion.
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacion.
	 * @return valor de numeroIdentificacion.
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacion.
	 * @param valor para modificar numeroIdentificacion.
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
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
	 * Método que retorna el valor de digitoVerificacion.
	 * @return valor de digitoVerificacion.
	 */
	public Short getDigitoVerificacion() {
		return digitoVerificacion;
	}

	/**
	 * Método encargado de modificar el valor de digitoVerificacion.
	 * @param valor para modificar digitoVerificacion.
	 */
	public void setDigitoVerificacion(Short digitoVerificacion) {
		this.digitoVerificacion = digitoVerificacion;
	}

	/**
	 * Método que retorna el valor de razonSocial.
	 * @return valor de razonSocial.
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * Método encargado de modificar el valor de razonSocial.
	 * @param valor para modificar razonSocial.
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * Método que retorna el valor de tipoSolicitante.
	 * @return valor de tipoSolicitante.
	 */
	public TipoTipoSolicitanteEnum getTipoSolicitante() {
		return tipoSolicitante;
	}

	/**
	 * Método encargado de modificar el valor de tipoSolicitante.
	 * @param valor para modificar tipoSolicitante.
	 */
	public void setTipoSolicitante(TipoTipoSolicitanteEnum tipoSolicitante) {
		this.tipoSolicitante = tipoSolicitante;
	}

	/**
	 * Método que retorna el valor de nombreComercial.
	 * @return valor de nombreComercial.
	 */
	public String getNombreComercial() {
		return nombreComercial;
	}

	/**
	 * Método encargado de modificar el valor de nombreComercial.
	 * @param valor para modificar nombreComercial.
	 */
	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	/**
	 * Método que retorna el valor de fechaConstitucion.
	 * @return valor de fechaConstitucion.
	 */
	public Long getFechaConstitucion() {
		return fechaConstitucion;
	}

	/**
	 * Método encargado de modificar el valor de fechaConstitucion.
	 * @param valor para modificar fechaConstitucion.
	 */
	public void setFechaConstitucion(Long fechaConstitucion) {
		this.fechaConstitucion = fechaConstitucion;
	}

	/**
	 * Método que retorna el valor de naturalezaJuridica.
	 * @return valor de naturalezaJuridica.
	 */
	public NaturalezaJuridicaEnum getNaturalezaJuridica() {
		return naturalezaJuridica;
	}

	/**
	 * Método encargado de modificar el valor de naturalezaJuridica.
	 * @param valor para modificar naturalezaJuridica.
	 */
	public void setNaturalezaJuridica(NaturalezaJuridicaEnum naturalezaJuridica) {
		this.naturalezaJuridica = naturalezaJuridica;
	}

	/**
	 * Método que retorna el valor de codigoCIIU.
	 * @return valor de codigoCIIU.
	 */
	public CodigoCIIU getCodigoCIIU() {
		return codigoCIIU;
	}

	/**
	 * Método encargado de modificar el valor de codigoCIIU.
	 * @param valor para modificar codigoCIIU.
	 */
	public void setCodigoCIIU(CodigoCIIU codigoCIIU) {
		this.codigoCIIU = codigoCIIU;
	}

	/**
	 * Método que retorna el valor de arl.
	 * @return valor de arl.
	 */
	public ARL getArl() {
		return arl;
	}

	/**
	 * Método encargado de modificar el valor de arl.
	 * @param valor para modificar arl.
	 */
	public void setArl(ARL arl) {
		this.arl = arl;
	}

	/**
	 * Método que retorna el valor de numeroTotalTrabajadores.
	 * @return valor de numeroTotalTrabajadores.
	 */
	public Integer getNumeroTotalTrabajadores() {
		return numeroTotalTrabajadores;
	}

	/**
	 * Método encargado de modificar el valor de numeroTotalTrabajadores.
	 * @param valor para modificar numeroTotalTrabajadores.
	 */
	public void setNumeroTotalTrabajadores(Integer numeroTotalTrabajadores) {
		this.numeroTotalTrabajadores = numeroTotalTrabajadores;
	}

	/**
	 * Método que retorna el valor de valorTotalUltimaNomina.
	 * @return valor de valorTotalUltimaNomina.
	 */
	public BigDecimal getValorTotalUltimaNomina() {
		return valorTotalUltimaNomina;
	}

	/**
	 * Método encargado de modificar el valor de valorTotalUltimaNomina.
	 * @param valor para modificar valorTotalUltimaNomina.
	 */
	public void setValorTotalUltimaNomina(BigDecimal valorTotalUltimaNomina) {
		this.valorTotalUltimaNomina = valorTotalUltimaNomina;
	}

	/**
	 * Método que retorna el valor de periodoUltimaNomina.
	 * @return valor de periodoUltimaNomina.
	 */
	public Long getPeriodoUltimaNomina() {
		return periodoUltimaNomina;
	}

	/**
	 * Método encargado de modificar el valor de periodoUltimaNomina.
	 * @param valor para modificar periodoUltimaNomina.
	 */
	public void setPeriodoUltimaNomina(Long periodoUltimaNomina) {
		this.periodoUltimaNomina = periodoUltimaNomina;
	}

	/**
	 * Método que retorna el valor de idUltimaCajaCompensacion.
	 * @return valor de idUltimaCajaCompensacion.
	 */
	public Integer getIdUltimaCajaCompensacion() {
		return idUltimaCajaCompensacion;
	}

	/**
	 * Método encargado de modificar el valor de idUltimaCajaCompensacion.
	 * @param valor para modificar idUltimaCajaCompensacion.
	 */
	public void setIdUltimaCajaCompensacion(Integer idUltimaCajaCompensacion) {
		this.idUltimaCajaCompensacion = idUltimaCajaCompensacion;
	}

	/**
	 * Método que retorna el valor de paginaWeb.
	 * @return valor de paginaWeb.
	 */
	public String getPaginaWeb() {
		return paginaWeb;
	}

	/**
	 * Método encargado de modificar el valor de paginaWeb.
	 * @param valor para modificar paginaWeb.
	 */
	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}

	/**
	 * Método que retorna el valor de idUbicacion.
	 * @return valor de idUbicacion.
	 */
	public Long getIdUbicacion() {
		return idUbicacion;
	}

	/**
	 * Método encargado de modificar el valor de idUbicacion.
	 * @param valor para modificar idUbicacion.
	 */
	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	/**
	 * Método que retorna el valor de departamento.
	 * @return valor de departamento.
	 */
	public Object getDepartamento() {
		return departamento;
	}

	/**
	 * Método encargado de modificar el valor de departamento.
	 * @param valor para modificar departamento.
	 */
	public void setDepartamento(Object departamento) {
		this.departamento = departamento;
	}

	/**
	 * Método que retorna el valor de municipio.
	 * @return valor de municipio.
	 */
	public Municipio getMunicipio() {
		return municipio;
	}

	/**
	 * Método encargado de modificar el valor de municipio.
	 * @param valor para modificar municipio.
	 */
	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	/**
	 * Método que retorna el valor de direccionFisica.
	 * @return valor de direccionFisica.
	 */
	public String getDireccionFisica() {
		return direccionFisica;
	}

	/**
	 * Método encargado de modificar el valor de direccionFisica.
	 * @param valor para modificar direccionFisica.
	 */
	public void setDireccionFisica(String direccionFisica) {
		this.direccionFisica = direccionFisica;
	}

	/**
	 * Método que retorna el valor de codigoPostal.
	 * @return valor de codigoPostal.
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * Método encargado de modificar el valor de codigoPostal.
	 * @param valor para modificar codigoPostal.
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/**
	 * Método que retorna el valor de indicativoTelFijo.
	 * @return valor de indicativoTelFijo.
	 */
	public String getIndicativoTelFijo() {
		return indicativoTelFijo;
	}

	/**
	 * Método encargado de modificar el valor de indicativoTelFijo.
	 * @param valor para modificar indicativoTelFijo.
	 */
	public void setIndicativoTelFijo(String indicativoTelFijo) {
		this.indicativoTelFijo = indicativoTelFijo;
	}

	/**
	 * Método que retorna el valor de telefonoFijo.
	 * @return valor de telefonoFijo.
	 */
	public String getTelefonoFijo() {
		return telefonoFijo;
	}

	/**
	 * Método encargado de modificar el valor de telefonoFijo.
	 * @param valor para modificar telefonoFijo.
	 */
	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	/**
	 * Método que retorna el valor de telefonoCelular.
	 * @return valor de telefonoCelular.
	 */
	public String getTelefonoCelular() {
		return telefonoCelular;
	}

	/**
	 * Método encargado de modificar el valor de telefonoCelular.
	 * @param valor para modificar telefonoCelular.
	 */
	public void setTelefonoCelular(String telefonoCelular) {
		this.telefonoCelular = telefonoCelular;
	}

	/**
	 * Método que retorna el valor de autorizacionEnvioEmail.
	 * @return valor de autorizacionEnvioEmail.
	 */
	public Boolean getAutorizacionEnvioEmail() {
		return autorizacionEnvioEmail;
	}

	/**
	 * Método encargado de modificar el valor de autorizacionEnvioEmail.
	 * @param valor para modificar autorizacionEnvioEmail.
	 */
	public void setAutorizacionEnvioEmail(Boolean autorizacionEnvioEmail) {
		this.autorizacionEnvioEmail = autorizacionEnvioEmail;
	}

	/**
	 * Método que retorna el valor de email.
	 * @return valor de email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Método encargado de modificar el valor de email.
	 * @param valor para modificar email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Método que retorna el valor de idUbicacionPrincipal.
	 * @return valor de idUbicacionPrincipal.
	 */
	public Long getIdUbicacionPrincipal() {
		return idUbicacionPrincipal;
	}

	/**
	 * Método encargado de modificar el valor de idUbicacionPrincipal.
	 * @param valor para modificar idUbicacionPrincipal.
	 */
	public void setIdUbicacionPrincipal(Long idUbicacionPrincipal) {
		this.idUbicacionPrincipal = idUbicacionPrincipal;
	}

	/**
	 * Método que retorna el valor de correspondenciaIgualOficinaPrincipal.
	 * @return valor de correspondenciaIgualOficinaPrincipal.
	 */
	public Boolean getCorrespondenciaIgualOficinaPrincipal() {
		return correspondenciaIgualOficinaPrincipal;
	}

	/**
	 * Método encargado de modificar el valor de correspondenciaIgualOficinaPrincipal.
	 * @param valor para modificar correspondenciaIgualOficinaPrincipal.
	 */
	public void setCorrespondenciaIgualOficinaPrincipal(Boolean correspondenciaIgualOficinaPrincipal) {
		this.correspondenciaIgualOficinaPrincipal = correspondenciaIgualOficinaPrincipal;
	}

	/**
	 * Método que retorna el valor de municipioOficinaPrincipal.
	 * @return valor de municipioOficinaPrincipal.
	 */
	public Municipio getMunicipioOficinaPrincipal() {
		return municipioOficinaPrincipal;
	}

	/**
	 * Método encargado de modificar el valor de municipioOficinaPrincipal.
	 * @param valor para modificar municipioOficinaPrincipal.
	 */
	public void setMunicipioOficinaPrincipal(Municipio municipioOficinaPrincipal) {
		this.municipioOficinaPrincipal = municipioOficinaPrincipal;
	}

	/**
	 * Método que retorna el valor de direccionFisicaOficinaPrincipal.
	 * @return valor de direccionFisicaOficinaPrincipal.
	 */
	public String getDireccionFisicaOficinaPrincipal() {
		return direccionFisicaOficinaPrincipal;
	}

	/**
	 * Método encargado de modificar el valor de direccionFisicaOficinaPrincipal.
	 * @param valor para modificar direccionFisicaOficinaPrincipal.
	 */
	public void setDireccionFisicaOficinaPrincipal(String direccionFisicaOficinaPrincipal) {
		this.direccionFisicaOficinaPrincipal = direccionFisicaOficinaPrincipal;
	}

	/**
	 * Método que retorna el valor de codigoPostalOficinaPrincipal.
	 * @return valor de codigoPostalOficinaPrincipal.
	 */
	public String getCodigoPostalOficinaPrincipal() {
		return codigoPostalOficinaPrincipal;
	}

	/**
	 * Método encargado de modificar el valor de codigoPostalOficinaPrincipal.
	 * @param valor para modificar codigoPostalOficinaPrincipal.
	 */
	public void setCodigoPostalOficinaPrincipal(String codigoPostalOficinaPrincipal) {
		this.codigoPostalOficinaPrincipal = codigoPostalOficinaPrincipal;
	}

	/**
	 * Método que retorna el valor de indicativoTelFijoOficinaPrincipal.
	 * @return valor de indicativoTelFijoOficinaPrincipal.
	 */
	public String getIndicativoTelFijoOficinaPrincipal() {
		return indicativoTelFijoOficinaPrincipal;
	}

	/**
	 * Método encargado de modificar el valor de indicativoTelFijoOficinaPrincipal.
	 * @param valor para modificar indicativoTelFijoOficinaPrincipal.
	 */
	public void setIndicativoTelFijoOficinaPrincipal(String indicativoTelFijoOficinaPrincipal) {
		this.indicativoTelFijoOficinaPrincipal = indicativoTelFijoOficinaPrincipal;
	}

	/**
	 * Método que retorna el valor de telefonoFijoOficinaPrincipal.
	 * @return valor de telefonoFijoOficinaPrincipal.
	 */
	public String getTelefonoFijoOficinaPrincipal() {
		return telefonoFijoOficinaPrincipal;
	}

	/**
	 * Método encargado de modificar el valor de telefonoFijoOficinaPrincipal.
	 * @param valor para modificar telefonoFijoOficinaPrincipal.
	 */
	public void setTelefonoFijoOficinaPrincipal(String telefonoFijoOficinaPrincipal) {
		this.telefonoFijoOficinaPrincipal = telefonoFijoOficinaPrincipal;
	}

	/**
	 * Método que retorna el valor de telefonoCelularOficinaPrincipal.
	 * @return valor de telefonoCelularOficinaPrincipal.
	 */
	public String getTelefonoCelularOficinaPrincipal() {
		return telefonoCelularOficinaPrincipal;
	}

	/**
	 * Método encargado de modificar el valor de telefonoCelularOficinaPrincipal.
	 * @param valor para modificar telefonoCelularOficinaPrincipal.
	 */
	public void setTelefonoCelularOficinaPrincipal(String telefonoCelularOficinaPrincipal) {
		this.telefonoCelularOficinaPrincipal = telefonoCelularOficinaPrincipal;
	}

	/**
	 * Método que retorna el valor de idUbicacionJudicial.
	 * @return valor de idUbicacionJudicial.
	 */
	public Long getIdUbicacionJudicial() {
		return idUbicacionJudicial;
	}

	/**
	 * Método encargado de modificar el valor de idUbicacionJudicial.
	 * @param valor para modificar idUbicacionJudicial.
	 */
	public void setIdUbicacionJudicial(Long idUbicacionJudicial) {
		this.idUbicacionJudicial = idUbicacionJudicial;
	}

	/**
	 * Método que retorna el valor de judicialIgualOficinaPrincipal.
	 * @return valor de judicialIgualOficinaPrincipal.
	 */
	public Integer getJudicialIgualOficinaPrincipal() {
		return judicialIgualOficinaPrincipal;
	}

	/**
	 * Método encargado de modificar el valor de judicialIgualOficinaPrincipal.
	 * @param valor para modificar judicialIgualOficinaPrincipal.
	 */
	public void setJudicialIgualOficinaPrincipal(Integer judicialIgualOficinaPrincipal) {
		this.judicialIgualOficinaPrincipal = judicialIgualOficinaPrincipal;
	}

	/**
	 * Método que retorna el valor de municipioJudicial.
	 * @return valor de municipioJudicial.
	 */
	public Municipio getMunicipioJudicial() {
		return municipioJudicial;
	}

	/**
	 * Método encargado de modificar el valor de municipioJudicial.
	 * @param valor para modificar municipioJudicial.
	 */
	public void setMunicipioJudicial(Municipio municipioJudicial) {
		this.municipioJudicial = municipioJudicial;
	}

	/**
	 * Método que retorna el valor de direccionFisicaJudicial.
	 * @return valor de direccionFisicaJudicial.
	 */
	public String getDireccionFisicaJudicial() {
		return direccionFisicaJudicial;
	}

	/**
	 * Método encargado de modificar el valor de direccionFisicaJudicial.
	 * @param valor para modificar direccionFisicaJudicial.
	 */
	public void setDireccionFisicaJudicial(String direccionFisicaJudicial) {
		this.direccionFisicaJudicial = direccionFisicaJudicial;
	}

	/**
	 * Método que retorna el valor de codigoPostalJudicial.
	 * @return valor de codigoPostalJudicial.
	 */
	public String getCodigoPostalJudicial() {
		return codigoPostalJudicial;
	}

	/**
	 * Método encargado de modificar el valor de codigoPostalJudicial.
	 * @param valor para modificar codigoPostalJudicial.
	 */
	public void setCodigoPostalJudicial(String codigoPostalJudicial) {
		this.codigoPostalJudicial = codigoPostalJudicial;
	}

	/**
	 * Método que retorna el valor de indicativoTelFijoJudicial.
	 * @return valor de indicativoTelFijoJudicial.
	 */
	public Integer getIndicativoTelFijoJudicial() {
		return indicativoTelFijoJudicial;
	}

	/**
	 * Método encargado de modificar el valor de indicativoTelFijoJudicial.
	 * @param valor para modificar indicativoTelFijoJudicial.
	 */
	public void setIndicativoTelFijoJudicial(Integer indicativoTelFijoJudicial) {
		this.indicativoTelFijoJudicial = indicativoTelFijoJudicial;
	}

	/**
	 * Método que retorna el valor de telefonoFijoJudicial.
	 * @return valor de telefonoFijoJudicial.
	 */
	public String getTelefonoFijoJudicial() {
		return telefonoFijoJudicial;
	}

	/**
	 * Método encargado de modificar el valor de telefonoFijoJudicial.
	 * @param valor para modificar telefonoFijoJudicial.
	 */
	public void setTelefonoFijoJudicial(String telefonoFijoJudicial) {
		this.telefonoFijoJudicial = telefonoFijoJudicial;
	}

	/**
	 * Método que retorna el valor de telefonoCelularJudicial.
	 * @return valor de telefonoCelularJudicial.
	 */
	public String getTelefonoCelularJudicial() {
		return telefonoCelularJudicial;
	}

	/**
	 * Método encargado de modificar el valor de telefonoCelularJudicial.
	 * @param valor para modificar telefonoCelularJudicial.
	 */
	public void setTelefonoCelularJudicial(String telefonoCelularJudicial) {
		this.telefonoCelularJudicial = telefonoCelularJudicial;
	}

	/**
	 * Método que retorna el valor de tipoIdentificacionRepLegal.
	 * @return valor de tipoIdentificacionRepLegal.
	 */
	public TipoIdentificacionEnum getTipoIdentificacionRepLegal() {
		return tipoIdentificacionRepLegal;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacionRepLegal.
	 * @param valor para modificar tipoIdentificacionRepLegal.
	 */
	public void setTipoIdentificacionRepLegal(TipoIdentificacionEnum tipoIdentificacionRepLegal) {
		this.tipoIdentificacionRepLegal = tipoIdentificacionRepLegal;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacionRepLegal.
	 * @return valor de numeroIdentificacionRepLegal.
	 */
	public String getNumeroIdentificacionRepLegal() {
		return numeroIdentificacionRepLegal;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacionRepLegal.
	 * @param valor para modificar numeroIdentificacionRepLegal.
	 */
	public void setNumeroIdentificacionRepLegal(String numeroIdentificacionRepLegal) {
		this.numeroIdentificacionRepLegal = numeroIdentificacionRepLegal;
	}

	/**
	 * Método que retorna el valor de primerNombreRepLegal.
	 * @return valor de primerNombreRepLegal.
	 */
	public String getPrimerNombreRepLegal() {
		return primerNombreRepLegal;
	}

	/**
	 * Método encargado de modificar el valor de primerNombreRepLegal.
	 * @param valor para modificar primerNombreRepLegal.
	 */
	public void setPrimerNombreRepLegal(String primerNombreRepLegal) {
		this.primerNombreRepLegal = primerNombreRepLegal;
	}

	/**
	 * Método que retorna el valor de segundoNombreRepLegal.
	 * @return valor de segundoNombreRepLegal.
	 */
	public String getSegundoNombreRepLegal() {
		return segundoNombreRepLegal;
	}

	/**
	 * Método encargado de modificar el valor de segundoNombreRepLegal.
	 * @param valor para modificar segundoNombreRepLegal.
	 */
	public void setSegundoNombreRepLegal(String segundoNombreRepLegal) {
		this.segundoNombreRepLegal = segundoNombreRepLegal;
	}

	/**
	 * Método que retorna el valor de primerApellidoRepLegal.
	 * @return valor de primerApellidoRepLegal.
	 */
	public String getPrimerApellidoRepLegal() {
		return primerApellidoRepLegal;
	}

	/**
	 * Método encargado de modificar el valor de primerApellidoRepLegal.
	 * @param valor para modificar primerApellidoRepLegal.
	 */
	public void setPrimerApellidoRepLegal(String primerApellidoRepLegal) {
		this.primerApellidoRepLegal = primerApellidoRepLegal;
	}

	/**
	 * Método que retorna el valor de segundoApellidoRepLegal.
	 * @return valor de segundoApellidoRepLegal.
	 */
	public String getSegundoApellidoRepLegal() {
		return segundoApellidoRepLegal;
	}

	/**
	 * Método encargado de modificar el valor de segundoApellidoRepLegal.
	 * @param valor para modificar segundoApellidoRepLegal.
	 */
	public void setSegundoApellidoRepLegal(String segundoApellidoRepLegal) {
		this.segundoApellidoRepLegal = segundoApellidoRepLegal;
	}

	/**
	 * Método que retorna el valor de emailRepLegal.
	 * @return valor de emailRepLegal.
	 */
	public String getEmailRepLegal() {
		return emailRepLegal;
	}

	/**
	 * Método encargado de modificar el valor de emailRepLegal.
	 * @param valor para modificar emailRepLegal.
	 */
	public void setEmailRepLegal(String emailRepLegal) {
		this.emailRepLegal = emailRepLegal;
	}

	/**
	 * Método que retorna el valor de indicativoTelFijoRepLegal.
	 * @return valor de indicativoTelFijoRepLegal.
	 */
	public String getIndicativoTelFijoRepLegal() {
		return indicativoTelFijoRepLegal;
	}

	/**
	 * Método encargado de modificar el valor de indicativoTelFijoRepLegal.
	 * @param valor para modificar indicativoTelFijoRepLegal.
	 */
	public void setIndicativoTelFijoRepLegal(String indicativoTelFijoRepLegal) {
		this.indicativoTelFijoRepLegal = indicativoTelFijoRepLegal;
	}

	/**
	 * Método que retorna el valor de telefonoFijoRepLegal.
	 * @return valor de telefonoFijoRepLegal.
	 */
	public String getTelefonoFijoRepLegal() {
		return telefonoFijoRepLegal;
	}

	/**
	 * Método encargado de modificar el valor de telefonoFijoRepLegal.
	 * @param valor para modificar telefonoFijoRepLegal.
	 */
	public void setTelefonoFijoRepLegal(String telefonoFijoRepLegal) {
		this.telefonoFijoRepLegal = telefonoFijoRepLegal;
	}

	/**
	 * Método que retorna el valor de telefonoCelularRepLegal.
	 * @return valor de telefonoCelularRepLegal.
	 */
	public String getTelefonoCelularRepLegal() {
		return telefonoCelularRepLegal;
	}

	/**
	 * Método encargado de modificar el valor de telefonoCelularRepLegal.
	 * @param valor para modificar telefonoCelularRepLegal.
	 */
	public void setTelefonoCelularRepLegal(String telefonoCelularRepLegal) {
		this.telefonoCelularRepLegal = telefonoCelularRepLegal;
	}

	/**
	 * Método que retorna el valor de tipoIdentificacionRepLegalSupl.
	 * @return valor de tipoIdentificacionRepLegalSupl.
	 */
	public TipoIdentificacionEnum getTipoIdentificacionRepLegalSupl() {
		return tipoIdentificacionRepLegalSupl;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacionRepLegalSupl.
	 * @param valor para modificar tipoIdentificacionRepLegalSupl.
	 */
	public void setTipoIdentificacionRepLegalSupl(TipoIdentificacionEnum tipoIdentificacionRepLegalSupl) {
		this.tipoIdentificacionRepLegalSupl = tipoIdentificacionRepLegalSupl;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacionRepLegalSupl.
	 * @return valor de numeroIdentificacionRepLegalSupl.
	 */
	public String getNumeroIdentificacionRepLegalSupl() {
		return numeroIdentificacionRepLegalSupl;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacionRepLegalSupl.
	 * @param valor para modificar numeroIdentificacionRepLegalSupl.
	 */
	public void setNumeroIdentificacionRepLegalSupl(String numeroIdentificacionRepLegalSupl) {
		this.numeroIdentificacionRepLegalSupl = numeroIdentificacionRepLegalSupl;
	}

	/**
	 * Método que retorna el valor de primerNombreRepLegalSupl.
	 * @return valor de primerNombreRepLegalSupl.
	 */
	public String getPrimerNombreRepLegalSupl() {
		return primerNombreRepLegalSupl;
	}

	/**
	 * Método encargado de modificar el valor de primerNombreRepLegalSupl.
	 * @param valor para modificar primerNombreRepLegalSupl.
	 */
	public void setPrimerNombreRepLegalSupl(String primerNombreRepLegalSupl) {
		this.primerNombreRepLegalSupl = primerNombreRepLegalSupl;
	}

	/**
	 * Método que retorna el valor de segundoNombreRepLegalSupl.
	 * @return valor de segundoNombreRepLegalSupl.
	 */
	public String getSegundoNombreRepLegalSupl() {
		return segundoNombreRepLegalSupl;
	}

	/**
	 * Método encargado de modificar el valor de segundoNombreRepLegalSupl.
	 * @param valor para modificar segundoNombreRepLegalSupl.
	 */
	public void setSegundoNombreRepLegalSupl(String segundoNombreRepLegalSupl) {
		this.segundoNombreRepLegalSupl = segundoNombreRepLegalSupl;
	}

	/**
	 * Método que retorna el valor de primerApellidoRepLegalSupl.
	 * @return valor de primerApellidoRepLegalSupl.
	 */
	public String getPrimerApellidoRepLegalSupl() {
		return primerApellidoRepLegalSupl;
	}

	/**
	 * Método encargado de modificar el valor de primerApellidoRepLegalSupl.
	 * @param valor para modificar primerApellidoRepLegalSupl.
	 */
	public void setPrimerApellidoRepLegalSupl(String primerApellidoRepLegalSupl) {
		this.primerApellidoRepLegalSupl = primerApellidoRepLegalSupl;
	}

	/**
	 * Método que retorna el valor de segundoApellidoRepLegalSupl.
	 * @return valor de segundoApellidoRepLegalSupl.
	 */
	public String getSegundoApellidoRepLegalSupl() {
		return segundoApellidoRepLegalSupl;
	}

	/**
	 * Método encargado de modificar el valor de segundoApellidoRepLegalSupl.
	 * @param valor para modificar segundoApellidoRepLegalSupl.
	 */
	public void setSegundoApellidoRepLegalSupl(String segundoApellidoRepLegalSupl) {
		this.segundoApellidoRepLegalSupl = segundoApellidoRepLegalSupl;
	}

	/**
	 * Método que retorna el valor de emailRepLegalSupl.
	 * @return valor de emailRepLegalSupl.
	 */
	public String getEmailRepLegalSupl() {
		return emailRepLegalSupl;
	}

	/**
	 * Método encargado de modificar el valor de emailRepLegalSupl.
	 * @param valor para modificar emailRepLegalSupl.
	 */
	public void setEmailRepLegalSupl(String emailRepLegalSupl) {
		this.emailRepLegalSupl = emailRepLegalSupl;
	}

	/**
	 * Método que retorna el valor de indicativoTelFijoRepLegalSupl.
	 * @return valor de indicativoTelFijoRepLegalSupl.
	 */
	public String getIndicativoTelFijoRepLegalSupl() {
		return indicativoTelFijoRepLegalSupl;
	}

	/**
	 * Método encargado de modificar el valor de indicativoTelFijoRepLegalSupl.
	 * @param valor para modificar indicativoTelFijoRepLegalSupl.
	 */
	public void setIndicativoTelFijoRepLegalSupl(String indicativoTelFijoRepLegalSupl) {
		this.indicativoTelFijoRepLegalSupl = indicativoTelFijoRepLegalSupl;
	}

	/**
	 * Método que retorna el valor de telefonoFijoRepLegalSupl.
	 * @return valor de telefonoFijoRepLegalSupl.
	 */
	public String getTelefonoFijoRepLegalSupl() {
		return telefonoFijoRepLegalSupl;
	}

	/**
	 * Método encargado de modificar el valor de telefonoFijoRepLegalSupl.
	 * @param valor para modificar telefonoFijoRepLegalSupl.
	 */
	public void setTelefonoFijoRepLegalSupl(String telefonoFijoRepLegalSupl) {
		this.telefonoFijoRepLegalSupl = telefonoFijoRepLegalSupl;
	}

	/**
	 * Método que retorna el valor de telefonoCelularRepLegalSupl.
	 * @return valor de telefonoCelularRepLegalSupl.
	 */
	public String getTelefonoCelularRepLegalSupl() {
		return telefonoCelularRepLegalSupl;
	}

	/**
	 * Método encargado de modificar el valor de telefonoCelularRepLegalSupl.
	 * @param valor para modificar telefonoCelularRepLegalSupl.
	 */
	public void setTelefonoCelularRepLegalSupl(String telefonoCelularRepLegalSupl) {
		this.telefonoCelularRepLegalSupl = telefonoCelularRepLegalSupl;
	}

	/**
	 * Método que retorna el valor de medioDePagoSubsidioMonetario.
	 * @return valor de medioDePagoSubsidioMonetario.
	 */
	public TipoMedioDePagoEnum getMedioDePagoSubsidioMonetario() {
		return medioDePagoSubsidioMonetario;
	}

	/**
	 * Método encargado de modificar el valor de medioDePagoSubsidioMonetario.
	 * @param valor para modificar medioDePagoSubsidioMonetario.
	 */
	public void setMedioDePagoSubsidioMonetario(TipoMedioDePagoEnum medioDePagoSubsidioMonetario) {
		this.medioDePagoSubsidioMonetario = medioDePagoSubsidioMonetario;
	}

	/**
	 * Método que retorna el valor de sucursalIgualOficinaPrincipal.
	 * @return valor de sucursalIgualOficinaPrincipal.
	 */
	public Boolean getSucursalIgualOficinaPrincipal() {
		return sucursalIgualOficinaPrincipal;
	}

	/**
	 * Método encargado de modificar el valor de sucursalIgualOficinaPrincipal.
	 * @param valor para modificar sucursalIgualOficinaPrincipal.
	 */
	public void setSucursalIgualOficinaPrincipal(Boolean sucursalIgualOficinaPrincipal) {
		this.sucursalIgualOficinaPrincipal = sucursalIgualOficinaPrincipal;
	}

	/**
	 * Método que retorna el valor de codigoSucursal.
	 * @return valor de codigoSucursal.
	 */
	public String getCodigoSucursal() {
		return codigoSucursal;
	}

	/**
	 * Método encargado de modificar el valor de codigoSucursal.
	 * @param valor para modificar codigoSucursal.
	 */
	public void setCodigoSucursal(String codigoSucursal) {
		this.codigoSucursal = codigoSucursal;
	}

	/**
	 * Método que retorna el valor de nombreSucursal.
	 * @return valor de nombreSucursal.
	 */
	public String getNombreSucursal() {
		return nombreSucursal;
	}

	/**
	 * Método encargado de modificar el valor de nombreSucursal.
	 * @param valor para modificar nombreSucursal.
	 */
	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}

	/**
	 * Método que retorna el valor de municipioSucursal.
	 * @return valor de municipioSucursal.
	 */
	public Municipio getMunicipioSucursal() {
		return municipioSucursal;
	}

	/**
	 * Método encargado de modificar el valor de municipioSucursal.
	 * @param valor para modificar municipioSucursal.
	 */
	public void setMunicipioSucursal(Municipio municipioSucursal) {
		this.municipioSucursal = municipioSucursal;
	}

	/**
	 * Método que retorna el valor de direccionFisicaSucursal.
	 * @return valor de direccionFisicaSucursal.
	 */
	public String getDireccionFisicaSucursal() {
		return direccionFisicaSucursal;
	}

	/**
	 * Método encargado de modificar el valor de direccionFisicaSucursal.
	 * @param valor para modificar direccionFisicaSucursal.
	 */
	public void setDireccionFisicaSucursal(String direccionFisicaSucursal) {
		this.direccionFisicaSucursal = direccionFisicaSucursal;
	}

	/**
	 * Método que retorna el valor de idUbicacionSucursal.
	 * @return valor de idUbicacionSucursal.
	 */
	public Long getIdUbicacionSucursal() {
		return idUbicacionSucursal;
	}

	/**
	 * Método encargado de modificar el valor de idUbicacionSucursal.
	 * @param valor para modificar idUbicacionSucursal.
	 */
	public void setIdUbicacionSucursal(Long idUbicacionSucursal) {
		this.idUbicacionSucursal = idUbicacionSucursal;
	}

	/**
	 * Método que retorna el valor de codigoPostalSucursal.
	 * @return valor de codigoPostalSucursal.
	 */
	public String getCodigoPostalSucursal() {
		return codigoPostalSucursal;
	}

	/**
	 * Método encargado de modificar el valor de codigoPostalSucursal.
	 * @param valor para modificar codigoPostalSucursal.
	 */
	public void setCodigoPostalSucursal(String codigoPostalSucursal) {
		this.codigoPostalSucursal = codigoPostalSucursal;
	}

	/**
	 * Método que retorna el valor de indicativoTelFijoSucursal.
	 * @return valor de indicativoTelFijoSucursal.
	 */
	public String getIndicativoTelFijoSucursal() {
		return indicativoTelFijoSucursal;
	}

	/**
	 * Método encargado de modificar el valor de indicativoTelFijoSucursal.
	 * @param valor para modificar indicativoTelFijoSucursal.
	 */
	public void setIndicativoTelFijoSucursal(String indicativoTelFijoSucursal) {
		this.indicativoTelFijoSucursal = indicativoTelFijoSucursal;
	}

	/**
	 * Método que retorna el valor de telefonoFijoSucursal.
	 * @return valor de telefonoFijoSucursal.
	 */
	public String getTelefonoFijoSucursal() {
		return telefonoFijoSucursal;
	}

	/**
	 * Método encargado de modificar el valor de telefonoFijoSucursal.
	 * @param valor para modificar telefonoFijoSucursal.
	 */
	public void setTelefonoFijoSucursal(String telefonoFijoSucursal) {
		this.telefonoFijoSucursal = telefonoFijoSucursal;
	}

	/**
	 * Método que retorna el valor de telefonoCelularSucursal.
	 * @return valor de telefonoCelularSucursal.
	 */
	public String getTelefonoCelularSucursal() {
		return telefonoCelularSucursal;
	}

	/**
	 * Método encargado de modificar el valor de telefonoCelularSucursal.
	 * @param valor para modificar telefonoCelularSucursal.
	 */
	public void setTelefonoCelularSucursal(String telefonoCelularSucursal) {
		this.telefonoCelularSucursal = telefonoCelularSucursal;
	}

	/**
	 * Método que retorna el valor de medioDePagoSubsidioMonetarioSucursal.
	 * @return valor de medioDePagoSubsidioMonetarioSucursal.
	 */
	public TipoMedioDePagoEnum getMedioDePagoSubsidioMonetarioSucursal() {
		return medioDePagoSubsidioMonetarioSucursal;
	}

	/**
	 * Método encargado de modificar el valor de medioDePagoSubsidioMonetarioSucursal.
	 * @param valor para modificar medioDePagoSubsidioMonetarioSucursal.
	 */
	public void setMedioDePagoSubsidioMonetarioSucursal(TipoMedioDePagoEnum medioDePagoSubsidioMonetarioSucursal) {
		this.medioDePagoSubsidioMonetarioSucursal = medioDePagoSubsidioMonetarioSucursal;
	}

	/**
	 * Método que retorna el valor de idSucursalEmpresa.
	 * @return valor de idSucursalEmpresa.
	 */
	public Long getIdSucursalEmpresa() {
		return idSucursalEmpresa;
	}

	/**
	 * Método encargado de modificar el valor de idSucursalEmpresa.
	 * @param valor para modificar idSucursalEmpresa.
	 */
	public void setIdSucursalEmpresa(Long idSucursalEmpresa) {
		this.idSucursalEmpresa = idSucursalEmpresa;
	}

	/**
	 * Método que retorna el valor de codigoCIIUSucursal.
	 * @return valor de codigoCIIUSucursal.
	 */
	public CodigoCIIU getCodigoCIIUSucursal() {
		return codigoCIIUSucursal;
	}

	/**
	 * Método encargado de modificar el valor de codigoCIIUSucursal.
	 * @param valor para modificar codigoCIIUSucursal.
	 */
	public void setCodigoCIIUSucursal(CodigoCIIU codigoCIIUSucursal) {
		this.codigoCIIUSucursal = codigoCIIUSucursal;
	}

	/**
	 * @return the idRolAfiliacion
	 */
	public Long getIdRolAfiliacion() {
		return idRolAfiliacion;
	}

	/**
	 * @param idRolAfiliacion the idRolAfiliacion to set
	 */
	public void setIdRolAfiliacion(Long idRolAfiliacion) {
		this.idRolAfiliacion = idRolAfiliacion;
	}
	
	/**
	 * Método que retorna el valor de rolAfiliacionIgualRepresentanteLegal.
	 * @return valor de rolAfiliacionIgualRepresentanteLegal.
	 */
	public Boolean getRolAfiliacionIgualRepresentanteLegal() {
		return rolAfiliacionIgualRepresentanteLegal;
	}

	/**
	 * Método encargado de modificar el valor de rolAfiliacionIgualRepresentanteLegal.
	 * @param valor para modificar rolAfiliacionIgualRepresentanteLegal.
	 */
	public void setRolAfiliacionIgualRepresentanteLegal(Boolean rolAfiliacionIgualRepresentanteLegal) {
		this.rolAfiliacionIgualRepresentanteLegal = rolAfiliacionIgualRepresentanteLegal;
	}

	/**
	 * Método que retorna el valor de tipoIdentificacionRolAfiliacion.
	 * @return valor de tipoIdentificacionRolAfiliacion.
	 */
	public TipoIdentificacionEnum getTipoIdentificacionRolAfiliacion() {
		return tipoIdentificacionRolAfiliacion;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacionRolAfiliacion.
	 * @param valor para modificar tipoIdentificacionRolAfiliacion.
	 */
	public void setTipoIdentificacionRolAfiliacion(TipoIdentificacionEnum tipoIdentificacionRolAfiliacion) {
		this.tipoIdentificacionRolAfiliacion = tipoIdentificacionRolAfiliacion;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacionRolAfiliacion.
	 * @return valor de numeroIdentificacionRolAfiliacion.
	 */
	public String getNumeroIdentificacionRolAfiliacion() {
		return numeroIdentificacionRolAfiliacion;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacionRolAfiliacion.
	 * @param valor para modificar numeroIdentificacionRolAfiliacion.
	 */
	public void setNumeroIdentificacionRolAfiliacion(String numeroIdentificacionRolAfiliacion) {
		this.numeroIdentificacionRolAfiliacion = numeroIdentificacionRolAfiliacion;
	}

	/**
	 * Método que retorna el valor de primerNombreRolAfiliacion.
	 * @return valor de primerNombreRolAfiliacion.
	 */
	public String getPrimerNombreRolAfiliacion() {
		return primerNombreRolAfiliacion;
	}

	/**
	 * Método encargado de modificar el valor de primerNombreRolAfiliacion.
	 * @param valor para modificar primerNombreRolAfiliacion.
	 */
	public void setPrimerNombreRolAfiliacion(String primerNombreRolAfiliacion) {
		this.primerNombreRolAfiliacion = primerNombreRolAfiliacion;
	}

	/**
	 * Método que retorna el valor de segundoNombreRolAfiliacion.
	 * @return valor de segundoNombreRolAfiliacion.
	 */
	public String getSegundoNombreRolAfiliacion() {
		return segundoNombreRolAfiliacion;
	}

	/**
	 * Método encargado de modificar el valor de segundoNombreRolAfiliacion.
	 * @param valor para modificar segundoNombreRolAfiliacion.
	 */
	public void setSegundoNombreRolAfiliacion(String segundoNombreRolAfiliacion) {
		this.segundoNombreRolAfiliacion = segundoNombreRolAfiliacion;
	}

	/**
	 * Método que retorna el valor de primerApellidoRolAfiliacion.
	 * @return valor de primerApellidoRolAfiliacion.
	 */
	public String getPrimerApellidoRolAfiliacion() {
		return primerApellidoRolAfiliacion;
	}

	/**
	 * Método encargado de modificar el valor de primerApellidoRolAfiliacion.
	 * @param valor para modificar primerApellidoRolAfiliacion.
	 */
	public void setPrimerApellidoRolAfiliacion(String primerApellidoRolAfiliacion) {
		this.primerApellidoRolAfiliacion = primerApellidoRolAfiliacion;
	}

	/**
	 * Método que retorna el valor de segundoApellidoRolAfiliacion.
	 * @return valor de segundoApellidoRolAfiliacion.
	 */
	public String getSegundoApellidoRolAfiliacion() {
		return segundoApellidoRolAfiliacion;
	}

	/**
	 * Método encargado de modificar el valor de segundoApellidoRolAfiliacion.
	 * @param valor para modificar segundoApellidoRolAfiliacion.
	 */
	public void setSegundoApellidoRolAfiliacion(String segundoApellidoRolAfiliacion) {
		this.segundoApellidoRolAfiliacion = segundoApellidoRolAfiliacion;
	}

	/**
	 * Método que retorna el valor de emailRolAfiliacion.
	 * @return valor de emailRolAfiliacion.
	 */
	public String getEmailRolAfiliacion() {
		return emailRolAfiliacion;
	}

	/**
	 * Método encargado de modificar el valor de emailRolAfiliacion.
	 * @param valor para modificar emailRolAfiliacion.
	 */
	public void setEmailRolAfiliacion(String emailRolAfiliacion) {
		this.emailRolAfiliacion = emailRolAfiliacion;
	}

	/**
	 * Método que retorna el valor de indicativoTelFijoRolAfiliacion.
	 * @return valor de indicativoTelFijoRolAfiliacion.
	 */
	public String getIndicativoTelFijoRolAfiliacion() {
		return indicativoTelFijoRolAfiliacion;
	}

	/**
	 * Método encargado de modificar el valor de indicativoTelFijoRolAfiliacion.
	 * @param valor para modificar indicativoTelFijoRolAfiliacion.
	 */
	public void setIndicativoTelFijoRolAfiliacion(String indicativoTelFijoRolAfiliacion) {
		this.indicativoTelFijoRolAfiliacion = indicativoTelFijoRolAfiliacion;
	}

	/**
	 * Método que retorna el valor de telefonoFijoRolAfiliacion.
	 * @return valor de telefonoFijoRolAfiliacion.
	 */
	public String getTelefonoFijoRolAfiliacion() {
		return telefonoFijoRolAfiliacion;
	}

	/**
	 * Método encargado de modificar el valor de telefonoFijoRolAfiliacion.
	 * @param valor para modificar telefonoFijoRolAfiliacion.
	 */
	public void setTelefonoFijoRolAfiliacion(String telefonoFijoRolAfiliacion) {
		this.telefonoFijoRolAfiliacion = telefonoFijoRolAfiliacion;
	}

	/**
	 * Método que retorna el valor de telefonoCelularRolAfiliacion.
	 * @return valor de telefonoCelularRolAfiliacion.
	 */
	public String getTelefonoCelularRolAfiliacion() {
		return telefonoCelularRolAfiliacion;
	}

	/**
	 * Método encargado de modificar el valor de telefonoCelularRolAfiliacion.
	 * @param valor para modificar telefonoCelularRolAfiliacion.
	 */
	public void setTelefonoCelularRolAfiliacion(String telefonoCelularRolAfiliacion) {
		this.telefonoCelularRolAfiliacion = telefonoCelularRolAfiliacion;
	}

	/**
	 * Método que retorna el valor de sucursalesRolAfiliacion.
	 * @return valor de sucursalesRolAfiliacion.
	 */
	public List<Long> getSucursalesRolAfiliacion() {
		return sucursalesRolAfiliacion;
	}

	/**
	 * Método encargado de modificar el valor de sucursalesRolAfiliacion.
	 * @param valor para modificar sucursalesRolAfiliacion.
	 */
	public void setSucursalesRolAfiliacion(List<Long> sucursalesRolAfiliacion) {
		this.sucursalesRolAfiliacion = sucursalesRolAfiliacion;
	}

	/**
	 * @return the idRolAportes
	 */
	public Long getIdRolAportes() {
		return idRolAportes;
	}

	/**
	 * @param idRolAportes the idRolAportes to set
	 */
	public void setIdRolAportes(Long idRolAportes) {
		this.idRolAportes = idRolAportes;
	}
	
	/**
	 * Método que retorna el valor de rolAportesIgualRepresentanteLegal.
	 * @return valor de rolAportesIgualRepresentanteLegal.
	 */
	public Boolean getRolAportesIgualRepresentanteLegal() {
		return rolAportesIgualRepresentanteLegal;
	}

	/**
	 * Método encargado de modificar el valor de rolAportesIgualRepresentanteLegal.
	 * @param valor para modificar rolAportesIgualRepresentanteLegal.
	 */
	public void setRolAportesIgualRepresentanteLegal(Boolean rolAportesIgualRepresentanteLegal) {
		this.rolAportesIgualRepresentanteLegal = rolAportesIgualRepresentanteLegal;
	}

	/**
	 * Método que retorna el valor de tipoIdentificacionRolAportes.
	 * @return valor de tipoIdentificacionRolAportes.
	 */
	public TipoIdentificacionEnum getTipoIdentificacionRolAportes() {
		return tipoIdentificacionRolAportes;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacionRolAportes.
	 * @param valor para modificar tipoIdentificacionRolAportes.
	 */
	public void setTipoIdentificacionRolAportes(TipoIdentificacionEnum tipoIdentificacionRolAportes) {
		this.tipoIdentificacionRolAportes = tipoIdentificacionRolAportes;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacionRolAportes.
	 * @return valor de numeroIdentificacionRolAportes.
	 */
	public String getNumeroIdentificacionRolAportes() {
		return numeroIdentificacionRolAportes;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacionRolAportes.
	 * @param valor para modificar numeroIdentificacionRolAportes.
	 */
	public void setNumeroIdentificacionRolAportes(String numeroIdentificacionRolAportes) {
		this.numeroIdentificacionRolAportes = numeroIdentificacionRolAportes;
	}

	/**
	 * Método que retorna el valor de primerNombreRolAportes.
	 * @return valor de primerNombreRolAportes.
	 */
	public String getPrimerNombreRolAportes() {
		return primerNombreRolAportes;
	}

	/**
	 * Método encargado de modificar el valor de primerNombreRolAportes.
	 * @param valor para modificar primerNombreRolAportes.
	 */
	public void setPrimerNombreRolAportes(String primerNombreRolAportes) {
		this.primerNombreRolAportes = primerNombreRolAportes;
	}

	/**
	 * Método que retorna el valor de segundoNombreRolAportes.
	 * @return valor de segundoNombreRolAportes.
	 */
	public String getSegundoNombreRolAportes() {
		return segundoNombreRolAportes;
	}

	/**
	 * Método encargado de modificar el valor de segundoNombreRolAportes.
	 * @param valor para modificar segundoNombreRolAportes.
	 */
	public void setSegundoNombreRolAportes(String segundoNombreRolAportes) {
		this.segundoNombreRolAportes = segundoNombreRolAportes;
	}

	/**
	 * Método que retorna el valor de primerApellidoRolAportes.
	 * @return valor de primerApellidoRolAportes.
	 */
	public String getPrimerApellidoRolAportes() {
		return primerApellidoRolAportes;
	}

	/**
	 * Método encargado de modificar el valor de primerApellidoRolAportes.
	 * @param valor para modificar primerApellidoRolAportes.
	 */
	public void setPrimerApellidoRolAportes(String primerApellidoRolAportes) {
		this.primerApellidoRolAportes = primerApellidoRolAportes;
	}

	/**
	 * Método que retorna el valor de segundoApellidoRolAportes.
	 * @return valor de segundoApellidoRolAportes.
	 */
	public String getSegundoApellidoRolAportes() {
		return segundoApellidoRolAportes;
	}

	/**
	 * Método encargado de modificar el valor de segundoApellidoRolAportes.
	 * @param valor para modificar segundoApellidoRolAportes.
	 */
	public void setSegundoApellidoRolAportes(String segundoApellidoRolAportes) {
		this.segundoApellidoRolAportes = segundoApellidoRolAportes;
	}

	/**
	 * Método que retorna el valor de emailRolAportes.
	 * @return valor de emailRolAportes.
	 */
	public String getEmailRolAportes() {
		return emailRolAportes;
	}

	/**
	 * Método encargado de modificar el valor de emailRolAportes.
	 * @param valor para modificar emailRolAportes.
	 */
	public void setEmailRolAportes(String emailRolAportes) {
		this.emailRolAportes = emailRolAportes;
	}

	/**
	 * Método que retorna el valor de indicativoTelFijoRolAportes.
	 * @return valor de indicativoTelFijoRolAportes.
	 */
	public String getIndicativoTelFijoRolAportes() {
		return indicativoTelFijoRolAportes;
	}

	/**
	 * Método encargado de modificar el valor de indicativoTelFijoRolAportes.
	 * @param valor para modificar indicativoTelFijoRolAportes.
	 */
	public void setIndicativoTelFijoRolAportes(String indicativoTelFijoRolAportes) {
		this.indicativoTelFijoRolAportes = indicativoTelFijoRolAportes;
	}

	/**
	 * Método que retorna el valor de telefonoFijoRolAportes.
	 * @return valor de telefonoFijoRolAportes.
	 */
	public String getTelefonoFijoRolAportes() {
		return telefonoFijoRolAportes;
	}

	/**
	 * Método encargado de modificar el valor de telefonoFijoRolAportes.
	 * @param valor para modificar telefonoFijoRolAportes.
	 */
	public void setTelefonoFijoRolAportes(String telefonoFijoRolAportes) {
		this.telefonoFijoRolAportes = telefonoFijoRolAportes;
	}

	/**
	 * Método que retorna el valor de telefonoCelularRolAportes.
	 * @return valor de telefonoCelularRolAportes.
	 */
	public String getTelefonoCelularRolAportes() {
		return telefonoCelularRolAportes;
	}

	/**
	 * Método encargado de modificar el valor de telefonoCelularRolAportes.
	 * @param valor para modificar telefonoCelularRolAportes.
	 */
	public void setTelefonoCelularRolAportes(String telefonoCelularRolAportes) {
		this.telefonoCelularRolAportes = telefonoCelularRolAportes;
	}

	/**
	 * Método que retorna el valor de sucursalesRolAportes.
	 * @return valor de sucursalesRolAportes.
	 */
	public List<Long> getSucursalesRolAportes() {
		return sucursalesRolAportes;
	}

	/**
	 * Método encargado de modificar el valor de sucursalesRolAportes.
	 * @param valor para modificar sucursalesRolAportes.
	 */
	public void setSucursalesRolAportes(List<Long> sucursalesRolAportes) {
		this.sucursalesRolAportes = sucursalesRolAportes;
	}

	/**
	 * @return the idRolSubsidio
	 */
	public Long getIdRolSubsidio() {
		return idRolSubsidio;
	}

	/**
	 * @param idRolSubsidio the idRolSubsidio to set
	 */
	public void setIdRolSubsidio(Long idRolSubsidio) {
		this.idRolSubsidio = idRolSubsidio;
	}
	
	/**
	 * Método que retorna el valor de rolSubsidioIgualRepresentanteLegal.
	 * @return valor de rolSubsidioIgualRepresentanteLegal.
	 */
	public Boolean getRolSubsidioIgualRepresentanteLegal() {
		return rolSubsidioIgualRepresentanteLegal;
	}

	/**
	 * Método encargado de modificar el valor de rolSubsidioIgualRepresentanteLegal.
	 * @param valor para modificar rolSubsidioIgualRepresentanteLegal.
	 */
	public void setRolSubsidioIgualRepresentanteLegal(Boolean rolSubsidioIgualRepresentanteLegal) {
		this.rolSubsidioIgualRepresentanteLegal = rolSubsidioIgualRepresentanteLegal;
	}

	/**
	 * Método que retorna el valor de tipoIdentificacionRolSubsidio.
	 * @return valor de tipoIdentificacionRolSubsidio.
	 */
	public TipoIdentificacionEnum getTipoIdentificacionRolSubsidio() {
		return tipoIdentificacionRolSubsidio;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacionRolSubsidio.
	 * @param valor para modificar tipoIdentificacionRolSubsidio.
	 */
	public void setTipoIdentificacionRolSubsidio(TipoIdentificacionEnum tipoIdentificacionRolSubsidio) {
		this.tipoIdentificacionRolSubsidio = tipoIdentificacionRolSubsidio;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacionRolSubsidio.
	 * @return valor de numeroIdentificacionRolSubsidio.
	 */
	public String getNumeroIdentificacionRolSubsidio() {
		return numeroIdentificacionRolSubsidio;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacionRolSubsidio.
	 * @param valor para modificar numeroIdentificacionRolSubsidio.
	 */
	public void setNumeroIdentificacionRolSubsidio(String numeroIdentificacionRolSubsidio) {
		this.numeroIdentificacionRolSubsidio = numeroIdentificacionRolSubsidio;
	}

	/**
	 * Método que retorna el valor de primerNombreRolSubsidio.
	 * @return valor de primerNombreRolSubsidio.
	 */
	public String getPrimerNombreRolSubsidio() {
		return primerNombreRolSubsidio;
	}

	/**
	 * Método encargado de modificar el valor de primerNombreRolSubsidio.
	 * @param valor para modificar primerNombreRolSubsidio.
	 */
	public void setPrimerNombreRolSubsidio(String primerNombreRolSubsidio) {
		this.primerNombreRolSubsidio = primerNombreRolSubsidio;
	}

	/**
	 * Método que retorna el valor de segundoNombreRolSubsidio.
	 * @return valor de segundoNombreRolSubsidio.
	 */
	public String getSegundoNombreRolSubsidio() {
		return segundoNombreRolSubsidio;
	}

	/**
	 * Método encargado de modificar el valor de segundoNombreRolSubsidio.
	 * @param valor para modificar segundoNombreRolSubsidio.
	 */
	public void setSegundoNombreRolSubsidio(String segundoNombreRolSubsidio) {
		this.segundoNombreRolSubsidio = segundoNombreRolSubsidio;
	}

	/**
	 * Método que retorna el valor de primerApellidoRolSubsidio.
	 * @return valor de primerApellidoRolSubsidio.
	 */
	public String getPrimerApellidoRolSubsidio() {
		return primerApellidoRolSubsidio;
	}

	/**
	 * Método encargado de modificar el valor de primerApellidoRolSubsidio.
	 * @param valor para modificar primerApellidoRolSubsidio.
	 */
	public void setPrimerApellidoRolSubsidio(String primerApellidoRolSubsidio) {
		this.primerApellidoRolSubsidio = primerApellidoRolSubsidio;
	}

	/**
	 * Método que retorna el valor de segundoApellidoRolSubsidio.
	 * @return valor de segundoApellidoRolSubsidio.
	 */
	public String getSegundoApellidoRolSubsidio() {
		return segundoApellidoRolSubsidio;
	}

	/**
	 * Método encargado de modificar el valor de segundoApellidoRolSubsidio.
	 * @param valor para modificar segundoApellidoRolSubsidio.
	 */
	public void setSegundoApellidoRolSubsidio(String segundoApellidoRolSubsidio) {
		this.segundoApellidoRolSubsidio = segundoApellidoRolSubsidio;
	}

	/**
	 * Método que retorna el valor de emailRolSubsidio.
	 * @return valor de emailRolSubsidio.
	 */
	public String getEmailRolSubsidio() {
		return emailRolSubsidio;
	}

	/**
	 * Método encargado de modificar el valor de emailRolSubsidio.
	 * @param valor para modificar emailRolSubsidio.
	 */
	public void setEmailRolSubsidio(String emailRolSubsidio) {
		this.emailRolSubsidio = emailRolSubsidio;
	}

	/**
	 * Método que retorna el valor de indicativoTelFijoRolSubsidio.
	 * @return valor de indicativoTelFijoRolSubsidio.
	 */
	public String getIndicativoTelFijoRolSubsidio() {
		return indicativoTelFijoRolSubsidio;
	}

	/**
	 * Método encargado de modificar el valor de indicativoTelFijoRolSubsidio.
	 * @param valor para modificar indicativoTelFijoRolSubsidio.
	 */
	public void setIndicativoTelFijoRolSubsidio(String indicativoTelFijoRolSubsidio) {
		this.indicativoTelFijoRolSubsidio = indicativoTelFijoRolSubsidio;
	}

	/**
	 * Método que retorna el valor de telefonoFijoRolSubsidio.
	 * @return valor de telefonoFijoRolSubsidio.
	 */
	public String getTelefonoFijoRolSubsidio() {
		return telefonoFijoRolSubsidio;
	}

	/**
	 * Método encargado de modificar el valor de telefonoFijoRolSubsidio.
	 * @param valor para modificar telefonoFijoRolSubsidio.
	 */
	public void setTelefonoFijoRolSubsidio(String telefonoFijoRolSubsidio) {
		this.telefonoFijoRolSubsidio = telefonoFijoRolSubsidio;
	}

	/**
	 * Método que retorna el valor de telefonoCelularRolSubsidio.
	 * @return valor de telefonoCelularRolSubsidio.
	 */
	public String getTelefonoCelularRolSubsidio() {
		return telefonoCelularRolSubsidio;
	}

	/**
	 * Método encargado de modificar el valor de telefonoCelularRolSubsidio.
	 * @param valor para modificar telefonoCelularRolSubsidio.
	 */
	public void setTelefonoCelularRolSubsidio(String telefonoCelularRolSubsidio) {
		this.telefonoCelularRolSubsidio = telefonoCelularRolSubsidio;
	}

	/**
	 * Método que retorna el valor de sucursalesRolSubsidio.
	 * @return valor de sucursalesRolSubsidio.
	 */
	public List<Long> getSucursalesRolSubsidio() {
		return sucursalesRolSubsidio;
	}

	/**
	 * Método encargado de modificar el valor de sucursalesRolSubsidio.
	 * @param valor para modificar sucursalesRolSubsidio.
	 */
	public void setSucursalesRolSubsidio(List<Long> sucursalesRolSubsidio) {
		this.sucursalesRolSubsidio = sucursalesRolSubsidio;
	}

	/**
	 * Método que retorna el valor de responsable1CajaContacto.
	 * @return valor de responsable1CajaContacto.
	 */
	public String getResponsable1CajaContacto() {
		return responsable1CajaContacto;
	}

	/**
	 * Método encargado de modificar el valor de responsable1CajaContacto.
	 * @param valor para modificar responsable1CajaContacto.
	 */
	public void setResponsable1CajaContacto(String responsable1CajaContacto) {
		this.responsable1CajaContacto = responsable1CajaContacto;
	}

	/**
	 * Método que retorna el valor de responsable2CajaContacto.
	 * @return valor de responsable2CajaContacto.
	 */
	public String getResponsable2CajaContacto() {
		return responsable2CajaContacto;
	}

	/**
	 * Método encargado de modificar el valor de responsable2CajaContacto.
	 * @param valor para modificar responsable2CajaContacto.
	 */
	public void setResponsable2CajaContacto(String responsable2CajaContacto) {
		this.responsable2CajaContacto = responsable2CajaContacto;
	}

	/**
	 * Método que retorna el valor de codigoNombreCoincidePILA.
	 * @return valor de codigoNombreCoincidePILA.
	 */
	public Boolean getCodigoNombreCoincidePILA() {
		return codigoNombreCoincidePILA;
	}

	/**
	 * Método encargado de modificar el valor de codigoNombreCoincidePILA.
	 * @param valor para modificar codigoNombreCoincidePILA.
	 */
	public void setCodigoNombreCoincidePILA(Boolean codigoNombreCoincidePILA) {
		this.codigoNombreCoincidePILA = codigoNombreCoincidePILA;
	}

	/**
	 * Método que retorna el valor de inactivarSucursal.
	 * @return valor de inactivarSucursal.
	 */
	public Boolean getInactivarSucursal() {
		return inactivarSucursal;
	}

	/**
	 * Método encargado de modificar el valor de inactivarSucursal.
	 * @param valor para modificar inactivarSucursal.
	 */
	public void setInactivarSucursal(Boolean inactivarSucursal) {
		this.inactivarSucursal = inactivarSucursal;
	}

	/**
	 * Método que retorna el valor de empleadorCubiertoLey1429.
	 * @return valor de empleadorCubiertoLey1429.
	 */
	public Boolean getEmpleadorCubiertoLey1429() {
		return empleadorCubiertoLey1429;
	}

	/**
	 * Método encargado de modificar el valor de empleadorCubiertoLey1429.
	 * @param valor para modificar empleadorCubiertoLey1429.
	 */
	public void setEmpleadorCubiertoLey1429(Boolean empleadorCubiertoLey1429) {
		this.empleadorCubiertoLey1429 = empleadorCubiertoLey1429;
	}

	/**
	 * Método que retorna el valor de anoInicioBeneficioLey1429.
	 * @return valor de anoInicioBeneficioLey1429.
	 */
	public Long getAnoInicioBeneficioLey1429() {
		return anoInicioBeneficioLey1429;
	}

	/**
	 * Método encargado de modificar el valor de anoInicioBeneficioLey1429.
	 * @param valor para modificar anoInicioBeneficioLey1429.
	 */
	public void setAnoInicioBeneficioLey1429(Long anoInicioBeneficioLey1429) {
		this.anoInicioBeneficioLey1429 = anoInicioBeneficioLey1429;
	}

	/**
	 * Método que retorna el valor de numeroConsecutivoAnosBeneficioLey1429.
	 * @return valor de numeroConsecutivoAnosBeneficioLey1429.
	 */
	public Byte getNumeroConsecutivoAnosBeneficioLey1429() {
		return numeroConsecutivoAnosBeneficioLey1429;
	}

	/**
	 * Método encargado de modificar el valor de numeroConsecutivoAnosBeneficioLey1429.
	 * @param valor para modificar numeroConsecutivoAnosBeneficioLey1429.
	 */
	public void setNumeroConsecutivoAnosBeneficioLey1429(Byte numeroConsecutivoAnosBeneficioLey1429) {
		this.numeroConsecutivoAnosBeneficioLey1429 = numeroConsecutivoAnosBeneficioLey1429;
	}

	/**
	 * Método que retorna el valor de anoFinBeneficioLey1429.
	 * @return valor de anoFinBeneficioLey1429.
	 */
	public Long getAnoFinBeneficioLey1429() {
		return anoFinBeneficioLey1429;
	}

	/**
	 * Método encargado de modificar el valor de anoFinBeneficioLey1429.
	 * @param valor para modificar anoFinBeneficioLey1429.
	 */
	public void setAnoFinBeneficioLey1429(Long anoFinBeneficioLey1429) {
		this.anoFinBeneficioLey1429 = anoFinBeneficioLey1429;
	}

	/**
	 * Método que retorna el valor de motivoInactivacionBeneficioLey1429.
	 * @return valor de motivoInactivacionBeneficioLey1429.
	 */
	public String getMotivoInactivacionBeneficioLey1429() {
		return motivoInactivacionBeneficioLey1429;
	}

	/**
	 * Método encargado de modificar el valor de motivoInactivacionBeneficioLey1429.
	 * @param valor para modificar motivoInactivacionBeneficioLey1429.
	 */
	public void setMotivoInactivacionBeneficioLey1429(String motivoInactivacionBeneficioLey1429) {
		this.motivoInactivacionBeneficioLey1429 = motivoInactivacionBeneficioLey1429;
	}

	/**
	 * Método que retorna el valor de empleadorCubiertoLey590.
	 * @return valor de empleadorCubiertoLey590.
	 */
	public Boolean getEmpleadorCubiertoLey590() {
		return empleadorCubiertoLey590;
	}

	/**
	 * Método encargado de modificar el valor de empleadorCubiertoLey590.
	 * @param valor para modificar empleadorCubiertoLey590.
	 */
	public void setEmpleadorCubiertoLey590(Boolean empleadorCubiertoLey590) {
		this.empleadorCubiertoLey590 = empleadorCubiertoLey590;
	}

	/**
	 * Método que retorna el valor de periodoInicioBeneficioLey590.
	 * @return valor de periodoInicioBeneficioLey590.
	 */
	public Long getPeriodoInicioBeneficioLey590() {
		return periodoInicioBeneficioLey590;
	}

	/**
	 * Método encargado de modificar el valor de periodoInicioBeneficioLey590.
	 * @param valor para modificar periodoInicioBeneficioLey590.
	 */
	public void setPeriodoInicioBeneficioLey590(Long periodoInicioBeneficioLey590) {
		this.periodoInicioBeneficioLey590 = periodoInicioBeneficioLey590;
	}

	/**
	 * Método que retorna el valor de numeroConsecutivoAnosBeneficioLey590.
	 * @return valor de numeroConsecutivoAnosBeneficioLey590.
	 */
	public Byte getNumeroConsecutivoAnosBeneficioLey590() {
		return numeroConsecutivoAnosBeneficioLey590;
	}

	/**
	 * Método encargado de modificar el valor de numeroConsecutivoAnosBeneficioLey590.
	 * @param valor para modificar numeroConsecutivoAnosBeneficioLey590.
	 */
	public void setNumeroConsecutivoAnosBeneficioLey590(Byte numeroConsecutivoAnosBeneficioLey590) {
		this.numeroConsecutivoAnosBeneficioLey590 = numeroConsecutivoAnosBeneficioLey590;
	}

	/**
	 * Método que retorna el valor de periodoFinBeneficioLey590.
	 * @return valor de periodoFinBeneficioLey590.
	 */
	public Long getPeriodoFinBeneficioLey590() {
		return periodoFinBeneficioLey590;
	}

	/**
	 * Método encargado de modificar el valor de periodoFinBeneficioLey590.
	 * @param valor para modificar periodoFinBeneficioLey590.
	 */
	public void setPeriodoFinBeneficioLey590(Long periodoFinBeneficioLey590) {
		this.periodoFinBeneficioLey590 = periodoFinBeneficioLey590;
	}

	/**
	 * Método que retorna el valor de motivoInactivacionBeneficioLey590.
	 * @return valor de motivoInactivacionBeneficioLey590.
	 */
	public String getMotivoInactivacionBeneficioLey590() {
		return motivoInactivacionBeneficioLey590;
	}

	/**
	 * Método encargado de modificar el valor de motivoInactivacionBeneficioLey590.
	 * @param valor para modificar motivoInactivacionBeneficioLey590.
	 */
	public void setMotivoInactivacionBeneficioLey590(String motivoInactivacionBeneficioLey590) {
		this.motivoInactivacionBeneficioLey590 = motivoInactivacionBeneficioLey590;
	}

	/**
	 * Método que retorna el valor de sucursalOrigenTraslado.
	 * @return valor de sucursalOrigenTraslado.
	 */
	public Long getSucursalOrigenTraslado() {
		return sucursalOrigenTraslado;
	}

	/**
	 * Método encargado de modificar el valor de sucursalOrigenTraslado.
	 * @param valor para modificar sucursalOrigenTraslado.
	 */
	public void setSucursalOrigenTraslado(Long sucursalOrigenTraslado) {
		this.sucursalOrigenTraslado = sucursalOrigenTraslado;
	}

	/**
	 * Método que retorna el valor de sucursalDestinoTraslado.
	 * @return valor de sucursalDestinoTraslado.
	 */
	public Long getSucursalDestinoTraslado() {
		return sucursalDestinoTraslado;
	}

	/**
	 * Método encargado de modificar el valor de sucursalDestinoTraslado.
	 * @param valor para modificar sucursalDestinoTraslado.
	 */
	public void setSucursalDestinoTraslado(Long sucursalDestinoTraslado) {
		this.sucursalDestinoTraslado = sucursalDestinoTraslado;
	}

	/**
	 * Método que retorna el valor de trabajadoresTraslado.
	 * @return valor de trabajadoresTraslado.
	 */
	public List<Long> getTrabajadoresTraslado() {
		return trabajadoresTraslado;
	}

	/**
	 * Método encargado de modificar el valor de trabajadoresTraslado.
	 * @param valor para modificar trabajadoresTraslado.
	 */
	public void setTrabajadoresTraslado(List<Long> trabajadoresTraslado) {
		this.trabajadoresTraslado = trabajadoresTraslado;
	}

	/**
	 * Método que retorna el valor de fechaFinLaboresSucursalOrigenTraslado.
	 * @return valor de fechaFinLaboresSucursalOrigenTraslado.
	 */
	public Long getFechaFinLaboresSucursalOrigenTraslado() {
		return fechaFinLaboresSucursalOrigenTraslado;
	}

	/**
	 * Método encargado de modificar el valor de fechaFinLaboresSucursalOrigenTraslado.
	 * @param valor para modificar fechaFinLaboresSucursalOrigenTraslado.
	 */
	public void setFechaFinLaboresSucursalOrigenTraslado(Long fechaFinLaboresSucursalOrigenTraslado) {
		this.fechaFinLaboresSucursalOrigenTraslado = fechaFinLaboresSucursalOrigenTraslado;
	}

	/**
	 * Método que retorna el valor de tipoIdentificacionOrigenSustPatronal.
	 * @return valor de tipoIdentificacionOrigenSustPatronal.
	 */
	public TipoIdentificacionEnum getTipoIdentificacionOrigenSustPatronal() {
		return tipoIdentificacionOrigenSustPatronal;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacionOrigenSustPatronal.
	 * @param valor para modificar tipoIdentificacionOrigenSustPatronal.
	 */
	public void setTipoIdentificacionOrigenSustPatronal(TipoIdentificacionEnum tipoIdentificacionOrigenSustPatronal) {
		this.tipoIdentificacionOrigenSustPatronal = tipoIdentificacionOrigenSustPatronal;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacionOrigenSustPatronal.
	 * @return valor de numeroIdentificacionOrigenSustPatronal.
	 */
	public String getNumeroIdentificacionOrigenSustPatronal() {
		return numeroIdentificacionOrigenSustPatronal;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacionOrigenSustPatronal.
	 * @param valor para modificar numeroIdentificacionOrigenSustPatronal.
	 */
	public void setNumeroIdentificacionOrigenSustPatronal(String numeroIdentificacionOrigenSustPatronal) {
		this.numeroIdentificacionOrigenSustPatronal = numeroIdentificacionOrigenSustPatronal;
	}

	/**
	 * Método que retorna el valor de razonSocialOrigenSustPatronal.
	 * @return valor de razonSocialOrigenSustPatronal.
	 */
	public String getRazonSocialOrigenSustPatronal() {
		return razonSocialOrigenSustPatronal;
	}

	/**
	 * Método encargado de modificar el valor de razonSocialOrigenSustPatronal.
	 * @param valor para modificar razonSocialOrigenSustPatronal.
	 */
	public void setRazonSocialOrigenSustPatronal(String razonSocialOrigenSustPatronal) {
		this.razonSocialOrigenSustPatronal = razonSocialOrigenSustPatronal;
	}

	/**
	 * Método que retorna el valor de sucursalesOrigenSustPatronal.
	 * @return valor de sucursalesOrigenSustPatronal.
	 */
	public List<Long> getSucursalesOrigenSustPatronal() {
		return sucursalesOrigenSustPatronal;
	}

	/**
	 * Método encargado de modificar el valor de sucursalesOrigenSustPatronal.
	 * @param valor para modificar sucursalesOrigenSustPatronal.
	 */
	public void setSucursalesOrigenSustPatronal(List<Long> sucursalesOrigenSustPatronal) {
		this.sucursalesOrigenSustPatronal = sucursalesOrigenSustPatronal;
	}

	/**
	 * Método que retorna el valor de fechaFinLaboresOrigenSustPatronal.
	 * @return valor de fechaFinLaboresOrigenSustPatronal.
	 */
	public Long getFechaFinLaboresOrigenSustPatronal() {
		return fechaFinLaboresOrigenSustPatronal;
	}

	/**
	 * Método encargado de modificar el valor de fechaFinLaboresOrigenSustPatronal.
	 * @param valor para modificar fechaFinLaboresOrigenSustPatronal.
	 */
	public void setFechaFinLaboresOrigenSustPatronal(Long fechaFinLaboresOrigenSustPatronal) {
		this.fechaFinLaboresOrigenSustPatronal = fechaFinLaboresOrigenSustPatronal;
	}

	/**
	 * Método que retorna el valor de tipoIdentificacionDestinoSustPatronal.
	 * @return valor de tipoIdentificacionDestinoSustPatronal.
	 */
	public TipoIdentificacionEnum getTipoIdentificacionDestinoSustPatronal() {
		return tipoIdentificacionDestinoSustPatronal;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacionDestinoSustPatronal.
	 * @param valor para modificar tipoIdentificacionDestinoSustPatronal.
	 */
	public void setTipoIdentificacionDestinoSustPatronal(TipoIdentificacionEnum tipoIdentificacionDestinoSustPatronal) {
		this.tipoIdentificacionDestinoSustPatronal = tipoIdentificacionDestinoSustPatronal;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacionDestinoSustPatronal.
	 * @return valor de numeroIdentificacionDestinoSustPatronal.
	 */
	public String getNumeroIdentificacionDestinoSustPatronal() {
		return numeroIdentificacionDestinoSustPatronal;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacionDestinoSustPatronal.
	 * @param valor para modificar numeroIdentificacionDestinoSustPatronal.
	 */
	public void setNumeroIdentificacionDestinoSustPatronal(String numeroIdentificacionDestinoSustPatronal) {
		this.numeroIdentificacionDestinoSustPatronal = numeroIdentificacionDestinoSustPatronal;
	}

	/**
	 * Método que retorna el valor de razonSocialDestinoSustPatronal.
	 * @return valor de razonSocialDestinoSustPatronal.
	 */
	public String getRazonSocialDestinoSustPatronal() {
		return razonSocialDestinoSustPatronal;
	}

	/**
	 * Método encargado de modificar el valor de razonSocialDestinoSustPatronal.
	 * @param valor para modificar razonSocialDestinoSustPatronal.
	 */
	public void setRazonSocialDestinoSustPatronal(String razonSocialDestinoSustPatronal) {
		this.razonSocialDestinoSustPatronal = razonSocialDestinoSustPatronal;
	}

	/**
	 * Método que retorna el valor de trabajadoresSustPatronal.
	 * @return valor de trabajadoresSustPatronal.
	 */
	public List<SucursalPersonaDTO> getTrabajadoresSustPatronal() {
		return trabajadoresSustPatronal;
	}

	/**
	 * Método encargado de modificar el valor de trabajadoresSustPatronal.
	 * @param valor para modificar trabajadoresSustPatronal.
	 */
	public void setTrabajadoresSustPatronal(List<SucursalPersonaDTO> trabajadoresSustPatronal) {
		this.trabajadoresSustPatronal = trabajadoresSustPatronal;
	}

	/**
	 * Método que retorna el valor de motivoDesafiliacion.
	 * @return valor de motivoDesafiliacion.
	 */
	public MotivoDesafiliacionEnum getMotivoDesafiliacion() {
		return motivoDesafiliacion;
	}

	/**
	 * Método encargado de modificar el valor de motivoDesafiliacion.
	 * @param valor para modificar motivoDesafiliacion.
	 */
	public void setMotivoDesafiliacion(MotivoDesafiliacionEnum motivoDesafiliacion) {
		this.motivoDesafiliacion = motivoDesafiliacion;
	}

	/**
	 * Método que retorna el valor de motivoAnulacionAfiliacion.
	 * @return valor de motivoAnulacionAfiliacion.
	 */
	public String getMotivoAnulacionAfiliacion() {
		return motivoAnulacionAfiliacion;
	}

	/**
	 * Método encargado de modificar el valor de motivoAnulacionAfiliacion.
	 * @param valor para modificar motivoAnulacionAfiliacion.
	 */
	public void setMotivoAnulacionAfiliacion(String motivoAnulacionAfiliacion) {
		this.motivoAnulacionAfiliacion = motivoAnulacionAfiliacion;
	}

	/**
	 * Método que retorna el valor de requiereInactivacionCuentaWeb.
	 * @return valor de requiereInactivacionCuentaWeb.
	 */
	public Boolean getRequiereInactivacionCuentaWeb() {
		return requiereInactivacionCuentaWeb;
	}

	/**
	 * Método encargado de modificar el valor de requiereInactivacionCuentaWeb.
	 * @param valor para modificar requiereInactivacionCuentaWeb.
	 */
	public void setRequiereInactivacionCuentaWeb(Boolean requiereInactivacionCuentaWeb) {
		this.requiereInactivacionCuentaWeb = requiereInactivacionCuentaWeb;
	}

	/**
	 * Método que retorna el valor de sociosEmpleador.
	 * @return valor de sociosEmpleador.
	 */
	public List<SocioEmpleadorDTO> getSociosEmpleador() {
		return sociosEmpleador;
	}

	/**
	 * Método encargado de modificar el valor de sociosEmpleador.
	 * @param valor para modificar sociosEmpleador.
	 */
	public void setSociosEmpleador(List<SocioEmpleadorDTO> sociosEmpleador) {
		this.sociosEmpleador = sociosEmpleador;
	}

	/**
	 * @return the idEmpleadoresPersona
	 */
	public List<Long> getIdEmpleadoresPersona() {
		return idEmpleadoresPersona;
	}

	/**
	 * @param idEmpleadoresPersona the idEmpleadoresPersona to set
	 */
	public void setIdEmpleadoresPersona(List<Long> idEmpleadoresPersona) {
		this.idEmpleadoresPersona = idEmpleadoresPersona;
	}
    
    /**
     * Método que retorna el valor de idEmpleadorDestinoSustPatronal.
     * @return valor de idEmpleadorDestinoSustPatronal.
     */
    public Long getIdEmpleadorDestinoSustPatronal() {
        return idEmpleadorDestinoSustPatronal;
    }
    
    /**
     * Método encargado de modificar el valor de idEmpleadorDestinoSustPatronal.
     * @param valor para modificar idEmpleadorDestinoSustPatronal.
     */
    public void setIdEmpleadorDestinoSustPatronal(Long idEmpleadorDestinoSustPatronal) {
        this.idEmpleadorDestinoSustPatronal = idEmpleadorDestinoSustPatronal;
    }

	/**
	 * Retorna el valor de estadoAfiliacion
	 * @return estadoAfiliacion
	 */
	public String getEstadoAfiliacion() {
		return estadoAfiliacion;
	}

	/**
	 * Modifica el valor de estadoAfiliacion
	 * @param estadoAfiliacion Valor para modificar
	 */
	public void setEstadoAfiliacion(String estadoAfiliacion) {
		this.estadoAfiliacion = estadoAfiliacion;
	}

	/**
	 * @return the idEmpresaDestinoSustPatronal
	 */
	public Long getIdEmpresaDestinoSustPatronal() {
		return idEmpresaDestinoSustPatronal;
	}

	/**
	 * @param idEmpresaDestinoSustPatronal the idEmpresaDestinoSustPatronal to set
	 */
	public void setIdEmpresaDestinoSustPatronal(Long idEmpresaDestinoSustPatronal) {
		this.idEmpresaDestinoSustPatronal = idEmpresaDestinoSustPatronal;
	}

	/**
	 * Retorna el valor de motivoSubsanacionExpulsion
	 * @return motivoSubsanacionExpulsion
	 */
	public String getMotivoSubsanacionExpulsion() {
		return motivoSubsanacionExpulsion;
	}

	/**
	 * Modifica el valor de motivoSubsanacionExpulsion
	 * @param motivoSubsanacionExpulsion Valor para modificar
	 */
	public void setMotivoSubsanacionExpulsion(String motivoSubsanacionExpulsion) {
		this.motivoSubsanacionExpulsion = motivoSubsanacionExpulsion;
	}
    /**
     * @return the descripcionIndicacion
     */
    public String getDescripcionIndicacion() {
        return descripcionIndicacion;
    }

    /**
     * @param descripcionIndicacion the descripcionIndicacion to set
     */
    public void setDescripcionIndicacion(String descripcionIndicacion) {
        this.descripcionIndicacion = descripcionIndicacion;
    }

    /**
     * @return the descripcionIndicacionOficinaPrincipal
     */
    public String getDescripcionIndicacionOficinaPrincipal() {
        return descripcionIndicacionOficinaPrincipal;
    }

    /**
     * @param descripcionIndicacionOficinaPrincipal the descripcionIndicacionOficinaPrincipal to set
     */
    public void setDescripcionIndicacionOficinaPrincipal(String descripcionIndicacionOficinaPrincipal) {
        this.descripcionIndicacionOficinaPrincipal = descripcionIndicacionOficinaPrincipal;
    }

    /**
     * @return the descripcionIndicacionJudicial
     */
    public String getDescripcionIndicacionJudicial() {
        return descripcionIndicacionJudicial;
    }

    /**
     * @param descripcionIndicacionJudicial the descripcionIndicacionJudicial to set
     */
    public void setDescripcionIndicacionJudicial(String descripcionIndicacionJudicial) {
        this.descripcionIndicacionJudicial = descripcionIndicacionJudicial;
    }

    /**
     * @return the descripcionIndicacionSucursal
     */
    public String getDescripcionIndicacionSucursal() {
        return descripcionIndicacionSucursal;
    }

    /**
     * @param descripcionIndicacionSucursal the descripcionIndicacionSucursal to set
     */
    public void setDescripcionIndicacionSucursal(String descripcionIndicacionSucursal) {
        this.descripcionIndicacionSucursal = descripcionIndicacionSucursal;
    }

    /**
     * @return the retencionSubsidioActivaEmpleador
     */
    public Boolean getRetencionSubsidioActivaEmpleador() {
        return retencionSubsidioActivaEmpleador;
    }

    /**
     * @param retencionSubsidioActivaEmpleador
     *        the retencionSubsidioActivaEmpleador to set
     */
    public void setRetencionSubsidioActivaEmpleador(Boolean retencionSubsidioActivaEmpleador) {
        this.retencionSubsidioActivaEmpleador = retencionSubsidioActivaEmpleador;
    }

    /**
     * @return the motivoRetencionSubsidioEmpleador
     */
    public MotivoRetencionSubsidioEnum getMotivoRetencionSubsidioEmpleador() {
        return motivoRetencionSubsidioEmpleador;
    }

    /**
     * @param motivoRetencionSubsidioEmpleador
     *        the motivoRetencionSubsidioEmpleador to set
     */
    public void setMotivoRetencionSubsidioEmpleador(MotivoRetencionSubsidioEnum motivoRetencionSubsidioEmpleador) {
        this.motivoRetencionSubsidioEmpleador = motivoRetencionSubsidioEmpleador;
    }

    /**
     * @return the retencionSubsidioActivaSucursal
     */
    public Boolean getRetencionSubsidioActivaSucursal() {
        return retencionSubsidioActivaSucursal;
    }

    /**
     * @param retencionSubsidioActivaSucursal
     *        the retencionSubsidioActivaSucursal to set
     */
    public void setRetencionSubsidioActivaSucursal(Boolean retencionSubsidioActivaSucursal) {
        this.retencionSubsidioActivaSucursal = retencionSubsidioActivaSucursal;
    }

    /**
     * @return the motivoRetencionSubsidioSucursal
     */
    public MotivoRetencionSubsidioEnum getMotivoRetencionSubsidioSucursal() {
        return motivoRetencionSubsidioSucursal;
    }

    /**
     * @param motivoRetencionSubsidioSucursal
     *        the motivoRetencionSubsidioSucursal to set
     */
    public void setMotivoRetencionSubsidioSucursal(MotivoRetencionSubsidioEnum motivoRetencionSubsidioSucursal) {
        this.motivoRetencionSubsidioSucursal = motivoRetencionSubsidioSucursal;
    }

    /**
     * @return the motivoInactivaRetencionSubsidioEmpleador
     */
    public MotivoInactivacionRetencionSubsidioEnum getMotivoInactivaRetencionSubsidioEmpleador() {
        return motivoInactivaRetencionSubsidioEmpleador;
    }

    /**
     * @param motivoInactivaRetencionSubsidioEmpleador the motivoInactivaRetencionSubsidioEmpleador to set
     */
    public void setMotivoInactivaRetencionSubsidioEmpleador(MotivoInactivacionRetencionSubsidioEnum motivoInactivaRetencionSubsidioEmpleador) {
        this.motivoInactivaRetencionSubsidioEmpleador = motivoInactivaRetencionSubsidioEmpleador;
    }

    /**
     * @return the motivoInactivaRetencionSubsidioSucursal
     */
    public MotivoInactivacionRetencionSubsidioEnum getMotivoInactivaRetencionSubsidioSucursal() {
        return motivoInactivaRetencionSubsidioSucursal;
    }

    /**
     * @param motivoInactivaRetencionSubsidioSucursal the motivoInactivaRetencionSubsidioSucursal to set
     */
    public void setMotivoInactivaRetencionSubsidioSucursal(MotivoInactivacionRetencionSubsidioEnum motivoInactivaRetencionSubsidioSucursal) {
        this.motivoInactivaRetencionSubsidioSucursal = motivoInactivaRetencionSubsidioSucursal;
    }

    /**
     * @return the tipoIdentificacionNuevo
     */
    public TipoIdentificacionEnum getTipoIdentificacionNuevo() {
        return tipoIdentificacionNuevo;
    }

    /**
     * @param tipoIdentificacionNuevo the tipoIdentificacionNuevo to set
     */
    public void setTipoIdentificacionNuevo(TipoIdentificacionEnum tipoIdentificacionNuevo) {
        this.tipoIdentificacionNuevo = tipoIdentificacionNuevo;
    }

    /**
     * @return the numeroIdentificacionNuevo
     */
    public String getNumeroIdentificacionNuevo() {
        return numeroIdentificacionNuevo;
    }

    /**
     * @param numeroIdentificacionNuevo the numeroIdentificacionNuevo to set
     */
    public void setNumeroIdentificacionNuevo(String numeroIdentificacionNuevo) {
        this.numeroIdentificacionNuevo = numeroIdentificacionNuevo;
    }

    /**
     * @return the digitoVerificacionNuevo
     */
    public Short getDigitoVerificacionNuevo() {
        return digitoVerificacionNuevo;
    }

    /**
     * @param digitoVerificacionNuevo the digitoVerificacionNuevo to set
     */
    public void setDigitoVerificacionNuevo(Short digitoVerificacionNuevo) {
        this.digitoVerificacionNuevo = digitoVerificacionNuevo;
    }

    /**
     * @return the primerNombre
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * @param primerNombre the primerNombre to set
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * @return the segundoNombre
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * @param segundoNombre the segundoNombre to set
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * @return the primerApellido
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * @param primerApellido the primerApellido to set
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * @return the segundoApellido
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * @param segundoApellido the segundoApellido to set
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

	/**
	 * @return the perteneceDepartamento
	 */
	public Boolean getPerteneceDepartamento() {
		return perteneceDepartamento;
	}

	/**
	 * @param perteneceDepartamento the perteneceDepartamento to set
	 */
	public void setPerteneceDepartamento(Boolean perteneceDepartamento) {
		this.perteneceDepartamento = perteneceDepartamento;
	}

    public String getSerialEmpresa() {
        return serialEmpresa;
    }

    public void setSerialEmpresa(String serialEmpresa) {
        this.serialEmpresa = serialEmpresa;
    }

	public Long getFechaInicioAfiliacion() {
        return fechaInicioAfiliacion;
    }
    
    public void setFechaInicioAfiliacion(Long fechaInicioAfiliacion) {
        this.fechaInicioAfiliacion = fechaInicioAfiliacion;
    }

	public Boolean getTrasladoCajasCompensacion() {
        return trasladoCajasCompensacion;
    }
    
    public void setTrasladoCajasCompensacion(Boolean trasladoCajasCompensacion) {
        this.trasladoCajasCompensacion = trasladoCajasCompensacion;
    }
        
    
}
