/**
 * 
 */
package com.asopagos.dto.aportes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.entidades.ccf.aportes.AporteDetallado;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.enumeraciones.SubTipoCotizanteEnum;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoGestionAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoValidacionRegistroAporteEnum;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.PersonasUtils;

/**
 * Clase DTO con los datos de un cotizante.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 * @author <a href="mailto:criparra@heinsohn.com.co"> Cristian David Parra
 *         Zulugaga. </a>
 */
@XmlRootElement
public class CotizanteDTO implements Serializable {

	/**
	 * serialVersionUID generado por la JVM.
	 */
	private static final long serialVersionUID = -6842204412488335244L;

	/**
	 * Tipo de identificación del cotizante.
	 */
	private TipoIdentificacionEnum tipoIdentificacion;

	/**
	 * Número de identificación del cotizante.
	 */
	private String numeroIdentificacion;

	/**
	 * Digito de verificación en caso de que sea NIT.
	 */
	private Short dv;
	/**
	 * Primer nombre del aportante(caso Persona).
	 */
	private String primerNombre;
	/**
	 * Segundo nombre del aportante(caso Persona).
	 */
	private String segundoNombre;
	/**
	 * Primer apellido del aportante (caso Persona).
	 */
	private String primerApellido;
	/**
	 * Segundo apellido del aportante (caso Persona).
	 */
	private String segundoApellido;

	/**
	 * Nombre completo compuesto de nombres y apellidos.
	 */
	private String nombreCompletoCotizante;

	/**
	 * Razón social del aportante (caso Empleador)
	 */
	private String razonSocialAportante;

	/**
	 * Estado del cotizante como afiliado.
	 */
	private EstadoAfiliadoEnum estado;
	/**
	 * Fecha de ingreso del aportante.
	 */
	private Long fechaIngreso;
	/**
	 * Fecha de retiro del aportante.
	 */
	private Long fechaRetiro;
	/**
	 * Tipo de cotizante.
	 */
	private TipoCotizanteEnum tipoCotizante;
	/**
	 * Subtipo de cotizante.
	 */
	private SubTipoCotizanteEnum subtipoCotizante;
	/**
	 * Codigo del departamente de ubicación.
	 */
	private String departamentoLaboral;
	/**
	 * Código del muncipio de ubicación.
	 */
	private String municipioLaboral;
	/**
	 * Días cotizados.
	 */
	private Short diasCotizados;
	/**
	 * Atributo que indica el salario básico.
	 */
	private BigDecimal salarioBasico;
	/**
	 * Atriubto que contiene el ingreso base.
	 */
	private BigDecimal valorIBC;
	/**
	 * Tarifa
	 */
	private BigDecimal tarifa;
	/**
	 * Aporte obligatorio (sumatoria de Aporte obligatorio)
	 */
	private BigDecimal aporteObligatorio;
	/**
	 * Correcciones
	 */
	private String correcciones;
	/**
	 * Presenta Salario integral
	 */
	private Boolean salarioIntegral;
	/**
	 * Número de horas laboradas
	 */
	private Short horasLaboradas;
	/**
	 * Valor de mora individual
	 */
	private BigDecimal valorMora;
	/**
	 * Estado del cotizante en el periodo de pago.
	 */
	private EstadoAfiliadoEnum estadoPeriodoPago;

	/**
	 * Listado de las novedades de un cotizante.
	 */
	private List<NovedadCotizanteDTO> novedades;

	// Por verificar con pantallas su pertinencia.
	// private String estadoConRespectoAportante;

	/**
	 * Tipo de afiliado.
	 */
	private TipoAfiliadoEnum tipoAfiliado;

	/**
	 * Campo que indica si tiene o no modificaciones.
	 */
	private Boolean tieneModificaciones;

	/**
	 * Campo que indica si aportó o no por sí mismo.
	 */
	private Boolean aportoPorSiMismo;

	/**
	 * Tipo de identificación del aportante.
	 */
	private TipoIdentificacionEnum tipoIdentificacionAportante;

	/**
	 * Número de identificación del aportante.
	 */
	private String numeroIdentificacionAportante;

	/**
	 * Nombre del aportante.
	 */
	private String nombreAportante;

	/**
	 * Campo que dice si está o no gestionado.
	 */
	private Boolean gestionado;

	/**
	 * Resultado o estado de la gestión.
	 */
	private EstadoGestionAporteEnum resultado;

	/**
	 * Monto a devolver.
	 */
	private BigDecimal monto;

	/**
	 * Intereses a devolver.
	 */
	private BigDecimal interes;

	/**
	 * Evaluación vigente.
	 */
	private EvaluacionDTO evaluacion;
	/**
	 * Evaluación simulada.
	 */
	private EvaluacionDTO evaluacionSimulada;
	/**
	 * Extranjero no obligado a cotizar pension
	 */
	private Boolean extranjeroNoObligadoCotizar;
	/**
	 * Colombiano en el exterior
	 */
	private Boolean colombianoExterior;

	/**
	 * Aportes del cotizante
	 */
	private AportesDTO aportes;
	/**
	 * Historico del cotizante
	 */
	private HistoricoDTO historico;
	/**
	 * Comentario de las novedades.
	 */
	private String comentarioNovedad;
	/**
	 * Cumple critierio de la novedad.
	 */
	private Boolean cumpleNovedad;
	/**
	 * Id del cotizante, (corresponde al aporte detallado, o registro detallado)
	 */
	private Long idCotizante;
	/**
	 * Origen, en el caso de aportes manuales, si llega por cargue, consulta, o
	 * agregar.
	 */
	private Short origen;

	/**
	 * Identificador del registro detallado para simulación
	 */
	private Long idRegistroDetalladoNuevo;

	/**
	 * Estado de la simulación.
	 */
	private EstadoValidacionRegistroAporteEnum evaluacionSimulacion;

	/**
	 * Correccion del aportante.
	 */
	private CorreccionAportanteDTO correccion;
	/**
	 * 
	 */
	private String idEcm;

	/**
	 * Referencia a identifiador de registro detallado vigente
	 */
	private Long idRegistro;
	
	/**
	 * Descripción del estado del aporte a nivel de cotizante
	 */
	private EstadoAporteEnum estadoAporteCotizante;

	/**
	 * Método constructor.
	 */
	public CotizanteDTO() {
		this.setResultado(EstadoGestionAporteEnum.PENDIENTE);
		this.setGestionado(Boolean.FALSE);
	}

	/**
	 * Método constructor con la persona y rol afiliado.
	 * @param persona persona cotizante.
	 * @param rolAfiliado rol afiliado asociado.
	 */
    public CotizanteDTO(Persona persona, RolAfiliado rolAfiliado) {
        this.setTipoIdentificacion(persona.getTipoIdentificacion());
        this.setNumeroIdentificacion(persona.getNumeroIdentificacion());
        this.setPrimerNombre(persona.getPrimerNombre());
        this.setSegundoNombre(persona.getSegundoNombre());
        this.setPrimerApellido(persona.getPrimerApellido());
        this.setSegundoApellido(persona.getSegundoApellido());
        if (rolAfiliado.getFechaRetiro() != null) {
            this.setFechaRetiro(rolAfiliado.getFechaRetiro().getTime());
        }
        if (rolAfiliado.getFechaAfiliacion() != null) {
            this.setFechaIngreso(rolAfiliado.getFechaAfiliacion().getTime());
        }
        this.setOrigen(new Short("0"));

    }
	
	/**
	 * Método constructor para consultar cotizantes.
	 */
	public CotizanteDTO(Persona cotizante, Persona aportante, AporteDetallado aporteDetallado, Long cantidadMovimientos) {
        this.setTipoIdentificacion(cotizante.getTipoIdentificacion());
        this.setNumeroIdentificacion(cotizante.getNumeroIdentificacion());
        this.setNombreCompletoCotizante(PersonasUtils.obtenerNombrePersona(cotizante));
        this.setTipoAfiliado(aporteDetallado.getTipoCotizante());
        
        if (aportante.getIdPersona().equals(cotizante.getIdPersona())) {
            this.setAportoPorSiMismo(Boolean.TRUE);
        } else {
            this.setAportoPorSiMismo(Boolean.FALSE);
        }    
        this.setTipoIdentificacionAportante(cotizante.getTipoIdentificacion());
        this.setNumeroIdentificacionAportante(cotizante.getNumeroIdentificacion());
        this.setNombreAportante(PersonasUtils.obtenerNombrePersona(cotizante));
        
        this.setIdCotizante(aporteDetallado.getId());
        this.setTieneModificaciones(cantidadMovimientos>0?true:false);
        this.setAporteObligatorio(aporteDetallado.getAporteObligatorio());
        this.setValorMora(aporteDetallado.getValorMora());
        this.setPrimerNombre(cotizante.getPrimerNombre());
        this.setSegundoNombre(cotizante.getSegundoNombre());
        this.setPrimerApellido(cotizante.getPrimerApellido());
        this.setSegundoApellido(cotizante.getSegundoApellido());
        this.setIdRegistro(aporteDetallado.getIdRegistroDetallado());
        this.setEstadoAporteCotizante(aporteDetallado.getEstadoAporteCotizante());
	}
	
	/**
	 * Método constructor para consultar cotizantes.
	 */
	public CotizanteDTO(Persona cotizante, Empresa empresa, AporteDetallado aporteDetallado, Long cantidadMovimientos) {
	    this.setTipoIdentificacion(cotizante.getTipoIdentificacion());
        this.setNumeroIdentificacion(cotizante.getNumeroIdentificacion());
        this.setNombreCompletoCotizante(PersonasUtils.obtenerNombrePersona(cotizante));
        this.setTipoAfiliado(aporteDetallado.getTipoCotizante());
        this.setAportoPorSiMismo(Boolean.FALSE);
        this.setTipoIdentificacionAportante(empresa.getPersona().getTipoIdentificacion());
        this.setNumeroIdentificacionAportante(empresa.getPersona().getNumeroIdentificacion());
        this.setNombreAportante(empresa.getPersona().getRazonSocial());
        this.setIdCotizante(aporteDetallado.getId());
        this.setTieneModificaciones(cantidadMovimientos>0?true:false);
        this.setAporteObligatorio(aporteDetallado.getAporteObligatorio());
        this.setValorMora(aporteDetallado.getValorMora());
        this.setPrimerNombre(cotizante.getPrimerNombre());
        this.setSegundoNombre(cotizante.getSegundoNombre());
        this.setPrimerApellido(cotizante.getPrimerApellido());
        this.setSegundoApellido(cotizante.getSegundoApellido());
        this.setIdRegistro(aporteDetallado.getIdRegistroDetallado());
        this.setEstadoAporteCotizante(aporteDetallado.getEstadoAporteCotizante());
    }
		
	/**
	 * Método constructor con los valores para un cotizante.
	 * 
	 * @param idCotizante
	 *            id del cotizante.
	 * @param primerNombre
	 *            primer nombre del cotizante.
	 * @param segundoNombre
	 *            segundo nombre del cotizante.
	 * @param primerApellido
	 *            primer apellido del cotizante.
	 * @param segundoApellido
	 *            segundo apellido del cotizante.
	 * @param tipoAfiliado
	 *            tipo afiliado.
	 */
	public CotizanteDTO(Long idCotizante, String primerNombre, String segundoNombre, String primerApellido,
			String segundoApellido, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
			TipoAfiliadoEnum tipoAfiliado) {
		this.setIdCotizante(idCotizante);
		this.setTipoAfiliado(tipoAfiliado);
		this.setPrimerApellido(primerApellido);
		this.setSegundoApellido(segundoApellido);
		this.setPrimerNombre(primerNombre);
		this.setSegundoApellido(segundoApellido);
		this.setTipoIdentificacion(tipoIdentificacion);
		this.setNumeroIdentificacion(numeroIdentificacion);
		StringBuilder nombre = new StringBuilder();
		nombre.append(primerNombre + " ");
		nombre.append(segundoNombre != null ? segundoNombre + " " : "");
		nombre.append(primerApellido + " ");
		nombre.append(segundoApellido != null ? segundoApellido : "");
		this.setNombreCompletoCotizante(nombre.toString());
	}

	/**
	 * Método encargado de convertir de Entidad a DTO.
	 * 
	 * @param solicitud
	 *            entidad a convertir.
	 */
	public RegistroDetalladoModeloDTO convertToDTO() {
		RegistroDetalladoModeloDTO registroDetalladoDTO = new RegistroDetalladoModeloDTO();
		registroDetalladoDTO.setAporteObligatorio(this.getAporteObligatorio());
		registroDetalladoDTO.setOutValorMoraCotizante(this.getValorMora() != null ? this.getValorMora() : BigDecimal.ZERO);
		registroDetalladoDTO.setCodDepartamento(this.getDepartamentoLaboral());
		if (this.getMunicipioLaboral() != null && this.getMunicipioLaboral().length() == 3) {
			registroDetalladoDTO.setCodMunicipio(this.getDepartamentoLaboral() + this.getMunicipioLaboral());
		} else {
			registroDetalladoDTO.setCodMunicipio(this.getMunicipioLaboral());
		}
		registroDetalladoDTO.setCorrecciones(this.getCorrecciones());
		registroDetalladoDTO.setDiasCotizados(this.getDiasCotizados());
		registroDetalladoDTO.setFechaRetiro(this.getFechaRetiro());
		registroDetalladoDTO.setHorasLaboradas(this.getHorasLaboradas());
		registroDetalladoDTO.setNumeroIdentificacionCotizante(this.getNumeroIdentificacion());
		registroDetalladoDTO.setPrimerApellido(this.getPrimerApellido());
		registroDetalladoDTO.setSegundoApellido(this.getSegundoApellido());
		registroDetalladoDTO.setPrimerNombre(this.getPrimerNombre());
		registroDetalladoDTO.setSegundoNombre(this.getSegundoNombre());
		registroDetalladoDTO.setSalarioBasico(this.getSalarioBasico());
		registroDetalladoDTO.setTarifa(this.getTarifa());
		if (this.getTipoCotizante() != null) {
			registroDetalladoDTO.setTipoCotizante(this.getTipoCotizante().getCodigo().shortValue());
		}
		registroDetalladoDTO.setTipoIdentificacionCotizante(this.getTipoIdentificacion());
		registroDetalladoDTO.setValorIBC(this.getValorIBC() != null ? this.getValorIBC() : this.getSalarioBasico());
		registroDetalladoDTO.setEstadoEvaluacion(EstadoAporteEnum.VIGENTE);
		registroDetalladoDTO.setUsuarioAprobadorAporte("SISTEMA");
		if(this.getSalarioIntegral()!=null){
		    registroDetalladoDTO.setSalarioIntegral(this.getSalarioIntegral()? "1":"0");
		}
		registroDetalladoDTO.setOutTipoAfiliado(this.getTipoAfiliado());
		// Se genern las novedades por registro
		if (novedades != null) {
			registroDetalladoDTO = generarNovedadRegistro(novedades, registroDetalladoDTO,Boolean.TRUE);
		}
		return registroDetalladoDTO;

	}
	
	/**
     * Método encargado de convertir de Entidad a DTO.
     * 
     * @param solicitud
     *            entidad a convertir.
     */
    public RegistroDetalladoModeloDTO convertToDTODevolucion() {
        RegistroDetalladoModeloDTO registroDetalladoDTO = new RegistroDetalladoModeloDTO();
        registroDetalladoDTO.setAporteObligatorio(this.aportes.getAporteObligatorioNuevo());
        registroDetalladoDTO.setDiasCotizados(this.aportes.getDiasCotizadoNuevo().shortValue());
        registroDetalladoDTO.setHorasLaboradas(this.aportes.getNumeroHorasLaboralNuevo()!=null?this.aportes.getNumeroHorasLaboralNuevo().shortValue():null);
        registroDetalladoDTO.setSalarioBasico(this.aportes.getSalarioBasicoNuevo());
        registroDetalladoDTO.setTarifa(this.aportes.getTarifaNuevo());
        registroDetalladoDTO.setValorIBC(this.aportes.getIbcNuevo());
        registroDetalladoDTO.setOutValorMoraCotizante(this.aportes.getMoraAporteNuevo());
        if(this.aportes.getSalarioIntegralNuevo()!=null){
            registroDetalladoDTO.setSalarioIntegral(this.aportes.getSalarioIntegralNuevo()?"1":"0");
        }
        registroDetalladoDTO.setEstadoEvaluacion(EstadoAporteEnum.VIGENTE);
        registroDetalladoDTO.setUsuarioAprobadorAporte("SISTEMA");
        // Se genern las novedades por registro
        if (novedades != null) {
            registroDetalladoDTO = generarNovedadRegistro(novedades, registroDetalladoDTO,Boolean.FALSE);
        }
        return registroDetalladoDTO;

    }
    
    /**
     * Método que se encarga de añadir las novedades al registro de corrección.
     * @param registroDetalladoDTO
     * @return
     */
    public RegistroDetalladoModeloDTO convertToDTOCorreccion(RegistroDetalladoModeloDTO registroDetalladoDTO){
        if (novedades != null) {
            registroDetalladoDTO = generarNovedadRegistro(novedades, registroDetalladoDTO,Boolean.FALSE);
        }
        return registroDetalladoDTO;
    }
	/**
	 * Método encargado de generar la novedad por registro detallado
	 * 
	 * @param novedades,
	 *            listado de novedades a generar
	 * @param registroDetalladoDTO,
	 *            registro detallado al cual se le setean las novedades
	 * @return retorna el registro detallado dto
	 */
	private RegistroDetalladoModeloDTO generarNovedadRegistro(List<NovedadCotizanteDTO> novedades,
			RegistroDetalladoModeloDTO registroDetalladoDTO,Boolean aporteManual) {
		String marcaNovedad = "X";
		registroDetalladoDTO = limpiarNovedades(registroDetalladoDTO);
		// Se verifican las novedades
		for (NovedadCotizanteDTO novedadCotizanteDTO : novedades) {
			if (novedadCotizanteDTO.getCondicionNueva() != null && novedadCotizanteDTO.getCondicionNueva()) {
			    Long fechaInicio;
			    Long fechaFin;
			    if(aporteManual){
			        fechaInicio = novedadCotizanteDTO.getFechaInicio();
			        fechaFin = novedadCotizanteDTO.getFechaFin();
			    }else{
			        fechaInicio = novedadCotizanteDTO.getFechaInicioNueva();
                    fechaFin = novedadCotizanteDTO.getFechaFinNueva();
			    }
				if (novedadCotizanteDTO.getTipoNovedad()
						.equals(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO)) {
					registroDetalladoDTO.setNovIngreso(marcaNovedad);
					registroDetalladoDTO.setFechaIngreso(fechaInicio);
				}
				// Verificación del tipo de novedad de retiro
				else if (validarNovedadRetiro(novedadCotizanteDTO.getTipoNovedad())) {
					registroDetalladoDTO.setNovRetiro(marcaNovedad);
					registroDetalladoDTO.setFechaRetiro(fechaInicio);
				} else /*
						 * Verificación para la novedad de variacion mesada
						 * pensional
						 */
				if (validarNovedadPermanteMesada(novedadCotizanteDTO.getTipoNovedad())) {
					registroDetalladoDTO.setNovVSP(marcaNovedad);
					registroDetalladoDTO.setFechaInicioVSP(fechaInicio);
				} else
				// Verificación de la novedad de variación transitoria
				if (novedadCotizanteDTO.getTipoNovedad()
						.equals(TipoTransaccionEnum.VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_PRESENCIAL)
						|| novedadCotizanteDTO.getTipoNovedad().equals(
								TipoTransaccionEnum.VARIACION_TRANSITORIA_SALARIO_VST_INDEPENDIENTE_PRESENCIAL)) {
					registroDetalladoDTO.setNovVST(marcaNovedad);
					registroDetalladoDTO.setFechaInicioVST(fechaInicio);
					registroDetalladoDTO.setFechaFinVST(fechaFin);
				} else
				/*
				 * Verificación de la novedad de suspension temporal del
				 * contrato por licencia
				 */
				if (novedadCotizanteDTO.getTipoNovedad()
						.equals(TipoTransaccionEnum.SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PRESENCIAL)
						|| novedadCotizanteDTO.getTipoNovedad().equals(
								TipoTransaccionEnum.SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_PRESENCIAL)) {
					registroDetalladoDTO.setNovSLN(marcaNovedad);
					registroDetalladoDTO.setFechaInicioSLN(fechaInicio);
					registroDetalladoDTO.setFechaFinSLN(fechaFin);

                }else 
                /*
                 * Verificación de la novedad de incapacidad temporal por
                 * enfermedad
                 */
                if (novedadCotizanteDTO.getTipoNovedad()
                        .equals(TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_PRESENCIAL)) {
                    Long dias = obtenerFechas(fechaInicio, fechaFin);
                    registroDetalladoDTO.setDiasIRL(dias.toString());
                    registroDetalladoDTO.setFechaInicioIRL(fechaInicio);
                    registroDetalladoDTO.setFechaFinIRL(fechaFin);
                }
                else
				/*
				 * Verificación de la novedad de incapacidad temporal por
				 * enfermedad
				 */
				if (novedadCotizanteDTO.getTipoNovedad()
						.equals(TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL)) {
					registroDetalladoDTO.setNovIGE(marcaNovedad);
					registroDetalladoDTO.setFechaInicioIGE(fechaInicio);
					registroDetalladoDTO.setFechaFinIGE(fechaFin);
				} else
				/* Licencia de maternidad y paternidad persona presencial */
				if (novedadCotizanteDTO.getTipoNovedad()
						.equals(TipoTransaccionEnum.LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PRESENCIAL)) {
					registroDetalladoDTO.setNovLMA(marcaNovedad);
					registroDetalladoDTO.setFechaInicioLMA(fechaInicio);
					registroDetalladoDTO.setFechaFinLMA(fechaFin);
				} else
				/* Licencia remunerada de vacaciones */
				if (novedadCotizanteDTO.getTipoNovedad()
						.equals(TipoTransaccionEnum.VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_PRESENCIAL)
						|| novedadCotizanteDTO.getTipoNovedad().equals(
								TipoTransaccionEnum.VACACIONES_LICENCIA_REMUNERADA_VAC_INDEPENDIENTE_PRESENCIAL)) {
					registroDetalladoDTO.setNovVACLR(marcaNovedad);
					registroDetalladoDTO.setFechaInicioVACLR(fechaInicio);
					registroDetalladoDTO.setFechaFinVACLR(fechaFin);
				} else
	                /* Suspension Pensionado */
	                if (novedadCotizanteDTO.getTipoNovedad().equals(TipoTransaccionEnum.SUSPENSION_PENSIONADO_SUS)) {
	                    registroDetalladoDTO.setNovSUS(marcaNovedad);
	                    registroDetalladoDTO.setFechaInicioSuspension(fechaInicio);
	                    registroDetalladoDTO.setFechaFinSuspension(fechaFin);
	                }
			}
		}
		return registroDetalladoDTO;
	}
	/**
	 * Método que se encarga de limpiar los valores que hayan puesto en las novedades.
	 * @param registroDetalladoDTO registro detallado a limpiar las novedades
	 * @return registro detallado modificado.
	 */
	private RegistroDetalladoModeloDTO limpiarNovedades(RegistroDetalladoModeloDTO registroDetalladoDTO){
	    registroDetalladoDTO.setNovSUS(null);
        registroDetalladoDTO.setFechaInicioSuspension(null);
        registroDetalladoDTO.setFechaFinSuspension(null);
        registroDetalladoDTO.setNovVACLR(null);
        registroDetalladoDTO.setFechaInicioVACLR(null);
        registroDetalladoDTO.setFechaFinVACLR(null);
        registroDetalladoDTO.setNovLMA(null);
        registroDetalladoDTO.setFechaInicioLMA(null);
        registroDetalladoDTO.setFechaFinLMA(null);
        registroDetalladoDTO.setNovIGE(null);
        registroDetalladoDTO.setFechaInicioIGE(null);
        registroDetalladoDTO.setFechaFinIGE(null);
        registroDetalladoDTO.setDiasIRL(null);
        registroDetalladoDTO.setFechaInicioIRL(null);
        registroDetalladoDTO.setFechaFinIRL(null);
        registroDetalladoDTO.setNovSLN(null);
        registroDetalladoDTO.setFechaInicioSLN(null);
        registroDetalladoDTO.setFechaFinSLN(null);
        registroDetalladoDTO.setNovVST(null);
        registroDetalladoDTO.setNovVSP(null);
        registroDetalladoDTO.setFechaInicioVSP(null);
        registroDetalladoDTO.setNovRetiro(null);
        registroDetalladoDTO.setFechaRetiro(null);
        registroDetalladoDTO.setNovIngreso(null);
        registroDetalladoDTO.setFechaIngreso(null);
        return registroDetalladoDTO;
	}
	/**
	 * Método encargado de validar la novedad de retiro
	 * 
	 * @param tipoNovedad,
	 *            tipo de novedad a verificar
	 * @return retorna true si es una novedad de retiro
	 */
	private boolean validarNovedadRetiro(TipoTransaccionEnum tipoNovedad) {
		boolean novedadRetiro = false;
		if (tipoNovedad.equals(TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE)
				|| tipoNovedad.equals(TipoTransaccionEnum.RETIRO_TRABAJADOR_INDEPENDIENTE)
				|| tipoNovedad.equals(TipoTransaccionEnum.RETIRO_PENSIONADO_25ANIOS)
				|| tipoNovedad.equals(TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_0_6)
				|| tipoNovedad.equals(TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_2)
				|| tipoNovedad.equals(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0)
				|| tipoNovedad.equals(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0_6)
				|| tipoNovedad.equals(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_2)
				|| tipoNovedad.equals(TipoTransaccionEnum.RETIRO_PENSIONADO_PENSION_FAMILIAR)) {
			novedadRetiro = true;
		}
		return novedadRetiro;
	}

	/**
	 * Método encargado de generar la validacion de la novedad de permanente de
	 * mesada
	 * 
	 * @param tipoNovedad,
	 *            tipo de novedad a verificar
	 * @return retorna true si es una novedad de variacion permante de mesada
	 */
	private boolean validarNovedadPermanteMesada(TipoTransaccionEnum tipoNovedad) {
		boolean novedadMesada = false;
		if (tipoNovedad
				.equals(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_25ANIOS_PRESENCIAL)
				|| tipoNovedad
						.equals(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL)
				|| tipoNovedad
						.equals(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL)
				|| tipoNovedad
						.equals(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_PRESENCIAL)
				|| tipoNovedad
						.equals(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL)
				|| tipoNovedad
						.equals(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL)
				|| tipoNovedad.equals(
						TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_PENSION_FAMILIAR_PRESENCIAL)
				|| tipoNovedad.equals(
				        TipoTransaccionEnum.VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_PRESENCIAL)
                || tipoNovedad.equals(
                        TipoTransaccionEnum.VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_PRESENCIAL)) {
			novedadMesada = true;
		}
		return novedadMesada;
	}
	
	/**
	 * Método que retorna el valor de tipoIdentificacion.
	 * 
	 * @return valor de tipoIdentificacion.
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacion.
	 * 
	 * @param valor
	 *            para modificar tipoIdentificacion.
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacion.
	 * 
	 * @return valor de numeroIdentificacion.
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacion.
	 * 
	 * @param valor
	 *            para modificar numeroIdentificacion.
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * Método que retorna el valor de dv.
	 * 
	 * @return valor de dv.
	 */
	public Short getDv() {
		return dv;
	}

	/**
	 * Método encargado de modificar el valor de dv.
	 * 
	 * @param valor
	 *            para modificar dv.
	 */
	public void setDv(Short dv) {
		this.dv = dv;
	}

	/**
	 * Método que retorna el valor de primerNombre.
	 * 
	 * @return valor de primerNombre.
	 */
	public String getPrimerNombre() {
		return primerNombre;
	}

	/**
	 * Método encargado de modificar el valor de primerNombre.
	 * 
	 * @param valor
	 *            para modificar primerNombre.
	 */
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	/**
	 * Método que retorna el valor de segundoNombre.
	 * 
	 * @return valor de segundoNombre.
	 */
	public String getSegundoNombre() {
		return segundoNombre;
	}

	/**
	 * Método encargado de modificar el valor de segundoNombre.
	 * 
	 * @param valor
	 *            para modificar segundoNombre.
	 */
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	/**
	 * Método que retorna el valor de primerApellido.
	 * 
	 * @return valor de primerApellido.
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * Método encargado de modificar el valor de primerApellido.
	 * 
	 * @param valor
	 *            para modificar primerApellido.
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	/**
	 * Método que retorna el valor de segundoApellido.
	 * 
	 * @return valor de segundoApellido.
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}

	/**
	 * Método encargado de modificar el valor de segundoApellido.
	 * 
	 * @param valor
	 *            para modificar segundoApellido.
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	/**
	 * Método que retorna el valor de nombreCompletoCotizante.
	 * 
	 * @return valor de nombreCompletoCotizante.
	 */
	public String getNombreCompletoCotizante() {
		return nombreCompletoCotizante;
	}

	/**
	 * Método encargado de modificar el valor de nombreCompletoCotizante.
	 * 
	 * @param valor
	 *            para modificar nombreCompletoCotizante.
	 */
	public void setNombreCompletoCotizante(String nombreCompletoCotizante) {
		this.nombreCompletoCotizante = nombreCompletoCotizante;
	}

	/**
	 * Método que retorna el valor de razonSocialAportante.
	 * 
	 * @return valor de razonSocialAportante.
	 */
	public String getRazonSocialAportante() {
		return razonSocialAportante;
	}

	/**
	 * Método encargado de modificar el valor de razonSocialAportante.
	 * 
	 * @param valor
	 *            para modificar razonSocialAportante.
	 */
	public void setRazonSocialAportante(String razonSocialAportante) {
		this.razonSocialAportante = razonSocialAportante;
	}

	/**
	 * Método que retorna el valor de estado.
	 * 
	 * @return valor de estado.
	 */
	public EstadoAfiliadoEnum getEstado() {
		return estado;
	}

	/**
	 * Método encargado de modificar el valor de estado.
	 * 
	 * @param valor
	 *            para modificar estado.
	 */
	public void setEstado(EstadoAfiliadoEnum estado) {
		this.estado = estado;
	}

	/**
	 * Método que retorna el valor de fechaIngreso.
	 * 
	 * @return valor de fechaIngreso.
	 */
	public Long getFechaIngreso() {
		return fechaIngreso;
	}

	/**
	 * Método encargado de modificar el valor de fechaIngreso.
	 * 
	 * @param valor
	 *            para modificar fechaIngreso.
	 */
	public void setFechaIngreso(Long fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	/**
	 * Método que retorna el valor de fechaRetiro.
	 * 
	 * @return valor de fechaRetiro.
	 */
	public Long getFechaRetiro() {
		return fechaRetiro;
	}

	/**
	 * Método encargado de modificar el valor de fechaRetiro.
	 * 
	 * @param valor
	 *            para modificar fechaRetiro.
	 */
	public void setFechaRetiro(Long fechaRetiro) {
		this.fechaRetiro = fechaRetiro;
	}

	/**
	 * Método que retorna el valor de tipoCotizante.
	 * 
	 * @return valor de tipoCotizante.
	 */
	public TipoCotizanteEnum getTipoCotizante() {
		return tipoCotizante;
	}

	/**
	 * Método encargado de modificar el valor de tipoCotizante.
	 * 
	 * @param valor
	 *            para modificar tipoCotizante.
	 */
	public void setTipoCotizante(TipoCotizanteEnum tipoCotizante) {
		this.tipoCotizante = tipoCotizante;
	}

	/**
	 * Método que retorna el valor de subtipoCotizante.
	 * 
	 * @return valor de subtipoCotizante.
	 */
	public SubTipoCotizanteEnum getSubtipoCotizante() {
		return subtipoCotizante;
	}

	/**
	 * Método encargado de modificar el valor de subtipoCotizante.
	 * 
	 * @param valor
	 *            para modificar subtipoCotizante.
	 */
	public void setSubtipoCotizante(SubTipoCotizanteEnum subtipoCotizante) {
		this.subtipoCotizante = subtipoCotizante;
	}

	/**
	 * Método que retorna el valor de departamentoLaboral.
	 * 
	 * @return valor de departamentoLaboral.
	 */
	public String getDepartamentoLaboral() {
		return departamentoLaboral;
	}

	/**
	 * Método encargado de modificar el valor de departamentoLaboral.
	 * 
	 * @param valor
	 *            para modificar departamentoLaboral.
	 */
	public void setDepartamentoLaboral(String departamentoLaboral) {
		this.departamentoLaboral = departamentoLaboral;
	}

	/**
	 * Método que retorna el valor de municipioLaboral.
	 * 
	 * @return valor de municipioLaboral.
	 */
	public String getMunicipioLaboral() {
		return municipioLaboral;
	}

	/**
	 * Método encargado de modificar el valor de municipioLaboral.
	 * 
	 * @param valor
	 *            para modificar municipioLaboral.
	 */
	public void setMunicipioLaboral(String municipioLaboral) {
		this.municipioLaboral = municipioLaboral;
	}

	/**
	 * Método que retorna el valor de diasCotizados.
	 * 
	 * @return valor de diasCotizados.
	 */
	public Short getDiasCotizados() {
		return diasCotizados;
	}

	/**
	 * Método encargado de modificar el valor de diasCotizados.
	 * 
	 * @param valor
	 *            para modificar diasCotizados.
	 */
	public void setDiasCotizados(Short diasCotizados) {
		this.diasCotizados = diasCotizados;
	}

	/**
	 * Método que retorna el valor de salarioBasico.
	 * 
	 * @return valor de salarioBasico.
	 */
	public BigDecimal getSalarioBasico() {
		return salarioBasico;
	}

	/**
	 * Método encargado de modificar el valor de salarioBasico.
	 * 
	 * @param valor
	 *            para modificar salarioBasico.
	 */
	public void setSalarioBasico(BigDecimal salarioBasico) {
		this.salarioBasico = salarioBasico;
	}

	/**
	 * Método que retorna el valor de valorIBC.
	 * 
	 * @return valor de valorIBC.
	 */
	public BigDecimal getValorIBC() {
		return valorIBC;
	}

	/**
	 * Método encargado de modificar el valor de valorIBC.
	 * 
	 * @param valor
	 *            para modificar valorIBC.
	 */
	public void setValorIBC(BigDecimal valorIBC) {
		this.valorIBC = valorIBC;
	}

	/**
	 * Método que retorna el valor de tarifa.
	 * 
	 * @return valor de tarifa.
	 */
	public BigDecimal getTarifa() {
		return tarifa;
	}

	/**
	 * Método encargado de modificar el valor de tarifa.
	 * 
	 * @param valor
	 *            para modificar tarifa.
	 */
	public void setTarifa(BigDecimal tarifa) {
		this.tarifa = tarifa;
	}

	/**
	 * Método que retorna el valor de aporteObligatorio.
	 * 
	 * @return valor de aporteObligatorio.
	 */
	public BigDecimal getAporteObligatorio() {
		return aporteObligatorio;
	}

	/**
	 * Método encargado de modificar el valor de aporteObligatorio.
	 * 
	 * @param valor
	 *            para modificar aporteObligatorio.
	 */
	public void setAporteObligatorio(BigDecimal aporteObligatorio) {
		this.aporteObligatorio = aporteObligatorio;
	}

	/**
	 * Método que retorna el valor de correcciones.
	 * 
	 * @return valor de correcciones.
	 */
	public String getCorrecciones() {
		return correcciones;
	}

	/**
	 * Método encargado de modificar el valor de correcciones.
	 * 
	 * @param valor
	 *            para modificar correcciones.
	 */
	public void setCorrecciones(String correcciones) {
		this.correcciones = correcciones;
	}

	/**
	 * Método que retorna el valor de salarioIntegral.
	 * 
	 * @return valor de salarioIntegral.
	 */
	public Boolean getSalarioIntegral() {
		return salarioIntegral;
	}

	/**
	 * Método encargado de modificar el valor de salarioIntegral.
	 * 
	 * @param valor
	 *            para modificar salarioIntegral.
	 */
	public void setSalarioIntegral(Boolean salarioIntegral) {
		this.salarioIntegral = salarioIntegral;
	}

	/**
	 * Método que retorna el valor de horasLaboradas.
	 * 
	 * @return valor de horasLaboradas.
	 */
	public Short getHorasLaboradas() {
		return horasLaboradas;
	}

	/**
	 * Método encargado de modificar el valor de horasLaboradas.
	 * 
	 * @param valor
	 *            para modificar horasLaboradas.
	 */
	public void setHorasLaboradas(Short horasLaboradas) {
		this.horasLaboradas = horasLaboradas;
	}

	/**
	 * Método que retorna el valor de valorMora.
	 * 
	 * @return valor de valorMora.
	 */
	public BigDecimal getValorMora() {
		return valorMora;
	}

	/**
	 * Método encargado de modificar el valor de valorMora.
	 * 
	 * @param valor
	 *            para modificar valorMora.
	 */
	public void setValorMora(BigDecimal valorMora) {
		this.valorMora = valorMora;
	}

	/**
	 * Método que retorna el valor de estadoPeriodoPago.
	 * 
	 * @return valor de estadoPeriodoPago.
	 */
	public EstadoAfiliadoEnum getEstadoPeriodoPago() {
		return estadoPeriodoPago;
	}

	/**
	 * Método encargado de modificar el valor de estadoPeriodoPago.
	 * 
	 * @param valor
	 *            para modificar estadoPeriodoPago.
	 */
	public void setEstadoPeriodoPago(EstadoAfiliadoEnum estadoPeriodoPago) {
		this.estadoPeriodoPago = estadoPeriodoPago;
	}

	/**
	 * Método que retorna el valor de novedades.
	 * 
	 * @return valor de novedades.
	 */
	public List<NovedadCotizanteDTO> getNovedades() {
		return novedades;
	}

	/**
	 * Método encargado de modificar el valor de novedades.
	 * 
	 * @param valor
	 *            para modificar novedades.
	 */
	public void setNovedades(List<NovedadCotizanteDTO> novedades) {
		this.novedades = novedades;
	}

	/**
	 * Método que retorna el valor de tipoAfiliado.
	 * 
	 * @return valor de tipoAfiliado.
	 */
	public TipoAfiliadoEnum getTipoAfiliado() {
		return tipoAfiliado;
	}

	/**
	 * Método encargado de modificar el valor de tipoAfiliado.
	 * 
	 * @param valor
	 *            para modificar tipoAfiliado.
	 */
	public void setTipoAfiliado(TipoAfiliadoEnum tipoAfiliado) {
		this.tipoAfiliado = tipoAfiliado;
	}

	/**
	 * Método que retorna el valor de tieneModificaciones.
	 * 
	 * @return valor de tieneModificaciones.
	 */
	public Boolean getTieneModificaciones() {
		return tieneModificaciones;
	}

	/**
	 * Método encargado de modificar el valor de tieneModificaciones.
	 * 
	 * @param valor
	 *            para modificar tieneModificaciones.
	 */
	public void setTieneModificaciones(Boolean tieneModificaciones) {
		this.tieneModificaciones = tieneModificaciones;
	}

	/**
	 * Método que retorna el valor de aportoPorSiMismo.
	 * 
	 * @return valor de aportoPorSiMismo.
	 */
	public Boolean getAportoPorSiMismo() {
		return aportoPorSiMismo;
	}

	/**
	 * Método encargado de modificar el valor de aportoPorSiMismo.
	 * 
	 * @param valor
	 *            para modificar aportoPorSiMismo.
	 */
	public void setAportoPorSiMismo(Boolean aportoPorSiMismo) {
		this.aportoPorSiMismo = aportoPorSiMismo;
	}

	/**
	 * Método que retorna el valor de tipoIdentificacionAportante.
	 * 
	 * @return valor de tipoIdentificacionAportante.
	 */
	public TipoIdentificacionEnum getTipoIdentificacionAportante() {
		return tipoIdentificacionAportante;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentificacionAportante.
	 * 
	 * @param valor
	 *            para modificar tipoIdentificacionAportante.
	 */
	public void setTipoIdentificacionAportante(TipoIdentificacionEnum tipoIdentificacionAportante) {
		this.tipoIdentificacionAportante = tipoIdentificacionAportante;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacionAportante.
	 * 
	 * @return valor de numeroIdentificacionAportante.
	 */
	public String getNumeroIdentificacionAportante() {
		return numeroIdentificacionAportante;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacionAportante.
	 * 
	 * @param valor
	 *            para modificar numeroIdentificacionAportante.
	 */
	public void setNumeroIdentificacionAportante(String numeroIdentificacionAportante) {
		this.numeroIdentificacionAportante = numeroIdentificacionAportante;
	}

	/**
	 * Método que retorna el valor de nombreAportante.
	 * 
	 * @return valor de nombreAportante.
	 */
	public String getNombreAportante() {
		return nombreAportante;
	}

	/**
	 * Método encargado de modificar el valor de nombreAportante.
	 * 
	 * @param valor
	 *            para modificar nombreAportante.
	 */
	public void setNombreAportante(String nombreAportante) {
		this.nombreAportante = nombreAportante;
	}

	/**
	 * Método que retorna el valor de gestionado.
	 * 
	 * @return valor de gestionado.
	 */
	public Boolean getGestionado() {
		return gestionado;
	}

	/**
	 * Método encargado de modificar el valor de gestionado.
	 * 
	 * @param valor
	 *            para modificar gestionado.
	 */
	public void setGestionado(Boolean gestionado) {
		this.gestionado = gestionado;
	}

	/**
	 * Método que retorna el valor de resultado.
	 * 
	 * @return valor de resultado.
	 */
	public EstadoGestionAporteEnum getResultado() {
		return resultado;
	}

	/**
	 * Método encargado de modificar el valor de resultado.
	 * 
	 * @param valor
	 *            para modificar resultado.
	 */
	public void setResultado(EstadoGestionAporteEnum resultado) {
		this.resultado = resultado;
	}

	/**
	 * Método que retorna el valor de monto.
	 * 
	 * @return valor de monto.
	 */
	public BigDecimal getMonto() {
		return monto;
	}

	/**
	 * Método encargado de modificar el valor de monto.
	 * 
	 * @param valor
	 *            para modificar monto.
	 */
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	/**
	 * Método que retorna el valor de interes.
	 * 
	 * @return valor de interes.
	 */
	public BigDecimal getInteres() {
		return interes;
	}

	/**
	 * Método encargado de modificar el valor de interes.
	 * 
	 * @param valor
	 *            para modificar interes.
	 */
	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	/**
	 * Método que retorna el valor de evaluacion.
	 * 
	 * @return valor de evaluacion.
	 */
	public EvaluacionDTO getEvaluacion() {
		return evaluacion;
	}

	/**
	 * Método encargado de modificar el valor de evaluacion.
	 * 
	 * @param valor
	 *            para modificar evaluacion.
	 */
	public void setEvaluacion(EvaluacionDTO evaluacion) {
		this.evaluacion = evaluacion;
	}

	/**
	 * Método que retorna el valor de evaluacionSimulada.
	 * 
	 * @return valor de evaluacionSimulada.
	 */
	public EvaluacionDTO getEvaluacionSimulada() {
		return evaluacionSimulada;
	}

	/**
	 * Método encargado de modificar el valor de evaluacionSimulada.
	 * 
	 * @param valor
	 *            para modificar evaluacionSimulada.
	 */
	public void setEvaluacionSimulada(EvaluacionDTO evaluacionSimulada) {
		this.evaluacionSimulada = evaluacionSimulada;
	}

	/**
	 * Método que retorna el valor de extranjeroNoObligadoCotizar.
	 * 
	 * @return valor de extranjeroNoObligadoCotizar.
	 */
	public Boolean getExtranjeroNoObligadoCotizar() {
		return extranjeroNoObligadoCotizar;
	}

	/**
	 * Método encargado de modificar el valor de extranjeroNoObligadoCotizar.
	 * 
	 * @param valor
	 *            para modificar extranjeroNoObligadoCotizar.
	 */
	public void setExtranjeroNoObligadoCotizar(Boolean extranjeroNoObligadoCotizar) {
		this.extranjeroNoObligadoCotizar = extranjeroNoObligadoCotizar;
	}

	/**
	 * Método que retorna el valor de colombianoExterior.
	 * 
	 * @return valor de colombianoExterior.
	 */
	public Boolean getColombianoExterior() {
		return colombianoExterior;
	}

	/**
	 * Método encargado de modificar el valor de colombianoExterior.
	 * 
	 * @param valor
	 *            para modificar colombianoExterior.
	 */
	public void setColombianoExterior(Boolean colombianoExterior) {
		this.colombianoExterior = colombianoExterior;
	}

	/**
	 * Método que retorna el valor de idCotizante.
	 * 
	 * @return valor de idCotizante.
	 */
	public Long getIdCotizante() {
		return idCotizante;
	}

	/**
	 * Método encargado de modificar el valor de idCotizante.
	 * 
	 * @param valor
	 *            para modificar idCotizante.
	 */
	public void setIdCotizante(Long idCotizante) {
		this.idCotizante = idCotizante;
	}

	/**
	 * Método que retorna el valor de aportes.
	 * 
	 * @return valor de aportes.
	 */
	public AportesDTO getAportes() {
		return aportes;
	}

	/**
	 * Método encargado de modificar el valor de aportes.
	 * 
	 * @param valor
	 *            para modificar aportes.
	 */
	public void setAportes(AportesDTO aportes) {
		this.aportes = aportes;
	}

	/**
	 * Método que retorna el valor de historico.
	 * 
	 * @return valor de historico.
	 */
	public HistoricoDTO getHistorico() {
		return historico;
	}

	/**
	 * Método encargado de modificar el valor de historico.
	 * 
	 * @param valor
	 *            para modificar historico.
	 */
	public void setHistorico(HistoricoDTO historico) {
		this.historico = historico;
	}

	/**
	 * Método que retorna el valor de comentarioNovedad.
	 * 
	 * @return valor de comentarioNovedad.
	 */
	public String getComentarioNovedad() {
		return comentarioNovedad;
	}

	/**
	 * Método encargado de modificar el valor de comentarioNovedad.
	 * 
	 * @param valor
	 *            para modificar comentarioNovedad.
	 */
	public void setComentarioNovedad(String comentarioNovedad) {
		this.comentarioNovedad = comentarioNovedad;
	}

	/**
	 * Método que retorna el valor de cumpleNovedad.
	 * 
	 * @return valor de cumpleNovedad.
	 */
	public Boolean getCumpleNovedad() {
		return cumpleNovedad;
	}

	/**
	 * Método encargado de modificar el valor de cumpleNovedad.
	 * 
	 * @param valor
	 *            para modificar cumpleNovedad.
	 */
	public void setCumpleNovedad(Boolean cumpleNovedad) {
		this.cumpleNovedad = cumpleNovedad;
	}

	/**
	 * Método que retorna el valor de origen.
	 * 
	 * @return valor de origen.
	 */
	public Short getOrigen() {
		return origen;
	}

	/**
	 * Método encargado de modificar el valor de origen.
	 * 
	 * @param valor
	 *            para modificar origen.
	 */
	public void setOrigen(Short origen) {
		this.origen = origen;
	}

	/**
	 * Método que retorna el valor de evaluacionSimulacion.
	 * 
	 * @return valor de evaluacionSimulacion.
	 */
	public EstadoValidacionRegistroAporteEnum getEvaluacionSimulacion() {
		return evaluacionSimulacion;
	}

	/**
	 * Método encargado de modificar el valor de evaluacionSimulacion.
	 * 
	 * @param valor
	 *            para modificar evaluacionSimulacion.
	 */
	public void setEvaluacionSimulacion(EstadoValidacionRegistroAporteEnum evaluacionSimulacion) {
		this.evaluacionSimulacion = evaluacionSimulacion;
	}

	/**
	 * Método que retorna el valor de correccion.
	 * 
	 * @return valor de correccion.
	 */
	public CorreccionAportanteDTO getCorreccion() {
		return correccion;
	}

	/**
	 * Método encargado de modificar el valor de correccion.
	 * 
	 * @param valor
	 *            para modificar correccion.
	 */
	public void setCorreccion(CorreccionAportanteDTO correccion) {
		this.correccion = correccion;
	}

	/**
	 * Obtiene el valor de idRegistroDetalladoNuevo
	 * 
	 * @return El valor de idRegistroDetalladoNuevo
	 */
	public Long getIdRegistroDetalladoNuevo() {
		return idRegistroDetalladoNuevo;
	}

	/**
	 * Método que retorna el valor de idEcm.
	 * 
	 * @return valor de idEcm.
	 */
	public String getIdEcm() {
		return idEcm;
	}

	/**
	 * Establece el valor de idRegistroDetalladoNuevo
	 * 
	 * @param idRegistroDetalladoNuevo
	 *            El valor de idRegistroDetalladoNuevo por asignar
	 */
	public void setIdRegistroDetalladoNuevo(Long idRegistroDetalladoNuevo) {
		this.idRegistroDetalladoNuevo = idRegistroDetalladoNuevo;
	}

	/**
	 * Método encargado de modificar el valor de idEcm.
	 * 
	 * @param valor
	 *            para modificar idEcm.
	 */
	public void setIdEcm(String idEcm) {
		this.idEcm = idEcm;
	}

	/**
	 * @return the idRegistro
	 */
	public Long getIdRegistro() {
		return idRegistro;
	}

	/**
	 * @param idRegistro
	 *            the idRegistro to set
	 */
	public void setIdRegistro(Long idRegistro) {
		this.idRegistro = idRegistro;
	}

    /**
     * @return the estadoAporteCotizante
     */
    public EstadoAporteEnum getEstadoAporteCotizante() {
        return estadoAporteCotizante;
    }

    /**
     * @param estadoAporteCotizante the estadoAporteCotizante to set
     */
    public void setEstadoAporteCotizante(EstadoAporteEnum estadoAporteCotizante) {
        this.estadoAporteCotizante = estadoAporteCotizante;
    }

    private Long obtenerFechas(Long fechaInicio, Long fechaFin) {
        Date fechaInicioDate = new Date(fechaInicio);
        Date fechaFinDate = new Date(fechaFin);
        Calendar fechaInicioCalendar = Calendar.getInstance();
        Calendar fechaFinCalendar = Calendar.getInstance();
        fechaInicioCalendar.setTime(fechaInicioDate);
        fechaFinCalendar.setTime(fechaFinDate);
        fechaFinCalendar.add(Calendar.DATE, 1);
        Long dias = CalendarUtils.obtenerDiferenciaEntreFechas(fechaInicioCalendar, fechaFinCalendar, 5);
        return dias;
    }

	@Override
	public String toString() {
		return "{" +
			" tipoIdentificacion='" + getTipoIdentificacion() + "'" +
			", numeroIdentificacion='" + getNumeroIdentificacion() + "'" +
			", dv='" + getDv() + "'" +
			", primerNombre='" + getPrimerNombre() + "'" +
			", segundoNombre='" + getSegundoNombre() + "'" +
			", primerApellido='" + getPrimerApellido() + "'" +
			", segundoApellido='" + getSegundoApellido() + "'" +
			", nombreCompletoCotizante='" + getNombreCompletoCotizante() + "'" +
			", razonSocialAportante='" + getRazonSocialAportante() + "'" +
			", estado='" + getEstado() + "'" +
			", fechaIngreso='" + getFechaIngreso() + "'" +
			", fechaRetiro='" + getFechaRetiro() + "'" +
			", tipoCotizante='" + getTipoCotizante() + "'" +
			", subtipoCotizante='" + getSubtipoCotizante() + "'" +
			", departamentoLaboral='" + getDepartamentoLaboral() + "'" +
			", municipioLaboral='" + getMunicipioLaboral() + "'" +
			", diasCotizados='" + getDiasCotizados() + "'" +
			", salarioBasico='" + getSalarioBasico() + "'" +
			", valorIBC='" + getValorIBC() + "'" +
			", tarifa='" + getTarifa() + "'" +
			", aporteObligatorio='" + getAporteObligatorio() + "'" +
			", correcciones='" + getCorrecciones() + "'" +
			", salarioIntegral='" + getSalarioIntegral() + "'" +
			", horasLaboradas='" + getHorasLaboradas() + "'" +
			", valorMora='" + getValorMora() + "'" +
			", estadoPeriodoPago='" + getEstadoPeriodoPago() + "'" +
			", novedades='" + getNovedades() + "'" +
			", tipoAfiliado='" + getTipoAfiliado() + "'" +
			", tieneModificaciones='" + getTieneModificaciones() + "'" +
			", aportoPorSiMismo='" + getAportoPorSiMismo() + "'" +
			", tipoIdentificacionAportante='" + getTipoIdentificacionAportante() + "'" +
			", numeroIdentificacionAportante='" + getNumeroIdentificacionAportante() + "'" +
			", nombreAportante='" + getNombreAportante() + "'" +
			", gestionado='" + getGestionado() + "'" +
			", resultado='" + getResultado() + "'" +
			", monto='" + getMonto() + "'" +
			", interes='" + getInteres() + "'" +
			", evaluacion='" + getEvaluacion() + "'" +
			", evaluacionSimulada='" + getEvaluacionSimulada() + "'" +
			", extranjeroNoObligadoCotizar='" + getExtranjeroNoObligadoCotizar() + "'" +
			", colombianoExterior='" + getColombianoExterior() + "'" +
			", aportes='" + getAportes() + "'" +
			", historico='" + getHistorico() + "'" +
			", comentarioNovedad='" + getComentarioNovedad() + "'" +
			", cumpleNovedad='" + getCumpleNovedad() + "'" +
			", idCotizante='" + getIdCotizante() + "'" +
			", origen='" + getOrigen() + "'" +
			", idRegistroDetalladoNuevo='" + getIdRegistroDetalladoNuevo() + "'" +
			", evaluacionSimulacion='" + getEvaluacionSimulacion() + "'" +
			", correccion='" + getCorreccion() + "'" +
			", idEcm='" + getIdEcm() + "'" +
			", idRegistro='" + getIdRegistro() + "'" +
			", estadoAporteCotizante='" + getEstadoAporteCotizante() + "'" +
			"}";
	}


}
