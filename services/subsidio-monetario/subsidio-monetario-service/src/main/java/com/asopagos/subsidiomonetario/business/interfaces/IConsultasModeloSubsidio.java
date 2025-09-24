package com.asopagos.subsidiomonetario.business.interfaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;

import com.asopagos.dto.subsidiomonetario.liquidacion.CondicionEntidadDescuentoLiquidacionDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.CondicionPersonaLiquidacionDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DetalleLiquidacionBeneficiarioFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.DispersionResultadoMedioPagoFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.PersonaFallecidaTrabajadorDTO;
import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoLiquidacionFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.pagos.SubsidioAfiliadoDTO;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ConjuntoValidacionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.GrupoAplicacionValidacionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum;
import com.asopagos.subsidiomonetario.dto.BloqueoBeneficiarioCuotaMonetariaDTO;
import com.asopagos.subsidiomonetario.dto.ConsultaBeneficiarioBloqueadosDTO;
import com.asopagos.subsidiomonetario.dto.ConsultaDescuentosSubsidioTrabajadorGrupoDTO;
import com.asopagos.subsidiomonetario.dto.ConsultaLiquidacionSubsidioMonetarioDTO;
import com.asopagos.subsidiomonetario.dto.DetalleCantidadEmpresaTrabajadorDTO;
import com.asopagos.subsidiomonetario.dto.DetalleResultadoPorAdministradorDTO;
import com.asopagos.subsidiomonetario.dto.RegistroLiquidacionSubsidioDTO;
import com.asopagos.subsidiomonetario.dto.RegistroSinDerechoSubsidioDTO;
import com.asopagos.subsidiomonetario.dto.RespuestaVerificarPersonasSinCondicionesDTO;
import com.asopagos.subsidiomonetario.dto.ResultadoLiquidacionMasivaDTO;
import com.asopagos.subsidiomonetario.dto.TemporalAsignacionDerechoDTO;
import com.asopagos.entidades.subsidiomonetario.liquidacion.BloqueoAfiliadoBeneficiarioCM;
/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de información en
 * el modelo de datos Subsidio <br/>
 * <b>Módulo:</b> Asopagos - HU-311-435 <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> rarboleda</a>
 */

@Local
public interface IConsultasModeloSubsidio {

    /**
     * Metodo que inicia la liquidacion masiva
     * @param numeroRadicado
     * @param periodo
     */
    public void iniciarLiquidacionMasiva(String numeroRadicado, Long periodo);
    
    /**
     * Metodo para llamar al SP de Orquestacion Stagin
     */
    public void ejecutarOrquestadorStagin(Long fechaActual);

    /**
     * Metodo para llamar al SP de Orquestacion Stagin Fallecimiento
     */
     public void ejecutarOrquestadorStaginFallecimiento(Long fechaActual, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion);

    /**
     * Metodo para ejecutar el Orquestador de Staging entre 2 fechas
     * @param fechaInicio
     * @param fechaFin
     */
    public void ejecutarOrquestadorStaginIntervaloFechas(Long fechaInicio, Long fechaFin);

    /**
     * Metodo para eliminar una liquidacion a traves del llamado SP.
     * @param numeroRadicado
     */     
    public Boolean eliminarLiquidacionSP(String numeroRadicado, Boolean isAsync);
    
    /**
     * Metodo para eliminar una liquidacion a traves del llamado SP.
     * @param numeroRadicado
     */    
    public void eliminarLiquidacionSP(String numeroRadicado,int fallecido);
    
    /**
     * Metodo para comprobar si se está ejecutando un proceso de liquidacion, staging o eliminacion de liquidacion
     * @param numeroRadicado
     */  
    public boolean consultarEjecucionProcesoSubsidio(String numeroRadicado);
    
    /**
     * Método que permite obtener los descuentos agrupados por beneficiario
     * @param numeroRadicacion 
     * 			valor del número de radicación
     * @param tipoIdentificacion 
     * 			el tipo de identificación del empleador
     * @param numeroIdentificacion
     * 			el número de identificación del empleador
     * @return descuentos agrupados por beneficiario
     */
    public Map<String, String> obtenerDescuentosBeneficiarios(String numeroRadicacion,
    		TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion);
    
    //TODO eliminar este método
    public List<TemporalAsignacionDerechoDTO> temporalDerechoBeneficiarios(String numeroRadicacion);
    
    /**
     * Método que permite consultar los resultados de una liquidación masiva
     * @param codigoProceso
     *        codigo del proceso a consultar
     * @param periodo
     *        periodo de la liquidación
     * @author rlopez
     * @return información de la liquidación masiva
     */
    public ResultadoLiquidacionMasivaDTO consultarResultadoLiquidacionMasiva(String numeroSolicitud, Date periodo);

    /**
     * Método que permite consultar el detalle de los trabajadores con mas subsidios liquidados desde el umbral establecido
     * @param codigoProceso
     *        número de radicado del proceso de liquidación
     * @author rlopez
     * @return información detallada de los trabajadores con una cantidad de subsidios liquidados por encima del umbral
     */
    public DetalleCantidadEmpresaTrabajadorDTO subsidiosLiquidadosPorTrabajadores(String numeroSolicitud, Date periodoLiquidado);

    /**
     * Método que permite consultar el detalle de los trabajadores con mas monto de subsidios liquidados desde el umbral establecido
     * @param codigoProceso
     *        número de radicado del proceso de liquidación
     * @author rlopez
     * @return información detallada de los trabajadores con un monto de subsidios liquidados por encima del umbral
     */
    public DetalleCantidadEmpresaTrabajadorDTO montoSubsidiosLiquidadosPorTrabajadores(String numeroSolicitud, Date periodoLiquidado);

    /**
     * Método que permite consultar el detalle de los trabajadores con mas subsidios con invalidez
     * @param codigoProceso
     *        número de radicado del proceso de liquidación
     * @author rlopez
     * @return información detallada de los trabajadores con una cantidad de subsidios de invalidez por encima del umbral
     */
    public DetalleCantidadEmpresaTrabajadorDTO subsidiosInvalidezPorTrabajadores(String numeroSolicitud, Date periodoLiquidado);

    /**
     * Método que permite consutlar el detalle de las empresas con mas periodos retroactivos en el mes desde el umbral establecido
     * @param codigoProceso
     *        número de radicado del proceso de liquidación
     * @author rlopez
     * @return información detallada de las empresas con mas periodos retroactivos por encima del umbral
     */
    public DetalleCantidadEmpresaTrabajadorDTO periodosRetroactivosMes(String numeroSolicitud, Date periodoLiquidado);

    /**
     * Método que permite consultar el detalle de las empresas acogidas al decreto 1429 que se encuentran en el año 1 y 2 del beneficio con
     * subsidio mayor a 0
     * @param codigoProceso
     *        número de radicado del proceso de liquidación
     * @author rlopez
     * @return información detallada de las empresas con las condiciones establecidas
     */
    public DetalleCantidadEmpresaTrabajadorDTO empresasBeneficio1429(String numeroSolicitud, Date periodoLiquidado);

    /**
     * Método que permite consultar el detalle de personas con monto descontado
     * @param codigoProceso
     *        número de radicado del proceso de liquidación
     * @author rlopez
     * @return información detallada de las personas con montos descontados
     */
    public DetalleCantidadEmpresaTrabajadorDTO personasConDescuentos(String numeroSolicitud, Date periodoLiquidado);
    
    /**
     * Método que se encarga de enviar la información de una liquidación a pagos
     * @param nombreUsuario
     *        usuario que ejecuta la aprobación en segundo nivel
     * @param numeroRadicado
     *        valor del número de radicado
     * @author rlopez
     */
    public void enviarResultadoLiquidacionAPagos(String nombreUsuario, String numeroRadicado);

    /**
     * Metodo que permite ejecutar una liquidacion especifica
     * HU's 143-144-146-148
     * @param tipoLiquidacionEspecifica Tipo de liquidacion especifica
     * @param numeroRadicado Número de radicado asociado
     * @author rarboleda     * 
     */    
    public void ejecutarSPLiquidacionEspecifica(TipoLiquidacionEspecificaEnum tipoLiquidacionEspecifica, String numeroRadicado);

    /**
     * Método que permite actualizar el estado de derecho para los subsidios asignados en un proceso de liquidación
     * @param numeroRadicacion
     *        valor del número de radicación
     * @author rlopez
     */
    public void actualizarEstadoDerechoLiquidacion(String numeroRadicacion);
    
    /**
     * Método que permite consultar los resultados de una liquidación especifica
     * @param codigoProceso
     *        codigo del proceso a consultar
     * @param periodo
     *        periodo de la liquidación
     * @author rlopez
     * @return información de la liquidación
     */
    public ResultadoLiquidacionMasivaDTO consultarResultadoLiquidacionEspecifica(String numeroSolicitud, Date periodo);

    /**
     * Metodo que permite ejecutar una liquidacion especifica
     * HU's 226 
     * @param numeroRadicado Número de radicado asociado
     * @author Robinson Andrés Arboleda 
     */    
    public void ejecutarSPLiquidacionReconocimiento(String numeroRadicado);
    
    /**
     * Metodo que devuelve lista de radicados a liquidar gestionando la cola de liquidaciones paralelas de subsidio
      * @author Diego Suesca 
     */  
    public List<String> ejecutarSPGestionarColaEjecucionLiquidacion();

    /**
     * Método que permite consultar el resultado de una liquidación específica por fallecimiento
     * 
     * @param numeroRadicacion
     *        valor del número de radicado
     * @return DTO con la información de la liquidación por fallecimiento
     * 
     * @author rlopez
     */
    public ResultadoLiquidacionFallecimientoDTO consultarResultadoLiquidacionFallecimiento(String numeroRadicacion);
    
    
    /**
     * Método que permite consultar el resultado de una liquidación específica por fallecimiento
     * 
     * @param numeroRadicacion
     *        valor del número de radicado
     * @return DTO con la información de la liquidación por fallecimiento
     * 
     * @author rlopez
     */
    public ResultadoLiquidacionFallecimientoDTO consultarResultadoLiquidacionFallecimientoConfirmados(String numeroRadicacion);
	
    /**
     * Método que permite activar los valores en las condiciones para una liquidación rechazada en primer o segundo nivel
     * 
     * @param numeroRadicacion
     *        valor del número de radicado
     * @author rlopez
     */
    public void activarEnColaProcesoLiquidacion(String numeroRadicacion);
    
    /**
     * Método que se encarga de realizar las acciones correspondientes a la confirmación de un beneficiario dentro del proceso de
     * liquidación por fallecimiento
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param idCondicionBeneficiario
     *        identificador de condición de beneficiario
     * @author rlopez
     */
    public void confirmarBeneficiarioLiquidacionFallecimiento(String numeroRadicacion, Long idCondicionBeneficiario);

    /**
     * Método que se encarga de realizar las acciones correspondientes a la confirmación de un afiliado dentro del proceso de liquidación
     * por fallecimiento
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param idCondicionAfiliado
     *        identificador de condición de afiliado
     * @author rlopez
     */
    public void confirmarAfiliadoLiquidacionFallecimiento(String numeroRadicacion, Long idCondicionAfiliado);
    
    /**
     * Método que permite obtener el detalle del beneficiario en una liquidación de fallecimeinto
     * @param numeroRadicacion
     *        valor del número de radicación
     * @param idCondicionBeneficiario
     *        identificador de la condición del beneficiario
     * @return DTO con la información del beneficiario
     * @author rlopez
     */
    public DetalleLiquidacionBeneficiarioFallecimientoDTO consultarDetalleBeneficiarioLiquidacionFallecimiento(String numeroRadicacion,
            Long idCondicionBeneficiario);

    /**
     * Metodo para conusltar si los beneficiarios asociados a una persona han tenido derecho asignado en la ultima liquidacion
     * @param persona
     * @return
     */
    public PersonaFallecidaTrabajadorDTO consultarEstadoDerechoBeneficiarios(PersonaFallecidaTrabajadorDTO persona);
    
    /**
     * Método que permite obtener la información de una persona dentro de un proceso de liquidación a partir de su identificador
     * @param numeroRadicacion
     *        valor del número de radicado
     * @param identificadoresCondiciones
     *        lista de identificadores de condiciones
     * @return Información de las condiciones de las personas
     * @author rlopez
     */
    public Map<String, CondicionPersonaLiquidacionDTO> consultarCondicionesPersonas(String numeroRadicacion,
            List<Long> identificadoresCondiciones);

    /**
     * Método que permite obtener la información de las condiciones de las entidades de descuento en un proceso de liquidación a partir de
     * su identificador
     * @param numeroRadicacion
     *        valor del número de radicado
     * @param identificadoresCondiciones
     *        lista de identificadores de condiciones
     * @return Información de las condiciones de las personas
     * @author rlopez
     */
    public Map<Long, CondicionEntidadDescuentoLiquidacionDTO> consultarCondicionesEntidadesDescuento(String numeroRadicacion,
            List<Long> identificadoresCondiciones);

    /**
     * Metodo para llamar al Stored Procedure de subsidio por fallecimiento
     * HU's 317-503, 317-506
     * @param numeroRadicado
     *        Número de radicado asociado
     * @param periodo
     *        Periodo asociado  
     * @param beneficiarioFallecido
     *        false para trabajador, true para beneficiario
     * @author rarboleda
     */
    public void ejecutarSPLiquidacionFallecimiento(String numeroRadicado, Long periodo, Boolean beneficiarioFallecido);

    /**
     * Método que permite obtener la información de desembolso para una liquidación de fallecimiento
     * @param numeroRadicacion
     *        valor del número de radicación
     * @return DTO con la información de desembolso
     * @author rlopez
     */
    public ResultadoLiquidacionFallecimientoDTO consultarDesembolsoSubsidioLiquidacionFallecimiento(String numeroRadicacion,
            Date periodoFallecimiento);

    /**
     * Método que permite consultar la fecha de fallecimiento del afiliado principal en una liquidación de fallecimiento
     * @param numeroRadicacion
     *        valor del número de radicación
     * @return fecha de fallecimiento
     * @author rlopez
     */
    public Date consultarPeriodoFallecimientoAfiliado(String numeroRadicacion);
    
    /**
     * Método que permite consultar la fecha de fallecimiento del beneficiariol en una liquidación de fallecimiento
     * @param numeroRadicacion
     *        valor del número de radicación
     * @return fecha de fallecimiento
     * @author rlopez
     */
    public Date consultarPeriodoFallecimientoBeneficiario(String numeroRadicacion);

    /**
     * Condiciones de beneficiario para HU-317-506 que vienen de staging
     * @param persona
     * @return persona
     * @author rarboleda
     */
    public PersonaFallecidaTrabajadorDTO consultarCondicionesBeneficiarioFallecido(PersonaFallecidaTrabajadorDTO persona);

    /**
     * Metodo para consultar el caso 5 de la HU-317-503 Validar que exista al menos un beneficiario distinto a “Cónyuge”, 
     * que al día anterior al fallecimiento del afiliado tenga “Estado con respecto al afiliado principal” igual a “Activo”
     * @param persona
     * @return persona
     * @author rarboleda
     */
    public PersonaFallecidaTrabajadorDTO consultarBeneficiarioActivoAlFallecerAfiliado(PersonaFallecidaTrabajadorDTO persona);

    /**
     * Método que se encarga de enviar la información de una liquidación de fallecimiento a pagos
     * @param nombreUsuario
     *        Usuario que realiza las acciones sobre la liquidación de fallecimiento
     * @param numeroRadicado
     *        Valor del número de radicado
     * @param modoDesembolso
     *        Modo en el que realiza el desembolso de subsidio
     * @author rlopez
     */
    public void enviarResultadoLiquidacionAPagosFallecimiento(String nombreUsuario, String numeroRadicado,
            ModoDesembolsoEnum modoDesembolso);
    
    /**
     * Método que permite obtener la información de la validación fallida para una persona (trabajador o benefiario) en el proceso de
     * fallecimiento
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @param condicionPersona
     *        Identificador de condición persona
     * @return Validación fallida
     * @author rlopez
     */
    public ConjuntoValidacionSubsidioEnum consultarValidacionFallidaPersonaFallecimiento(String numeroRadicacion, Long condicionPersona);

    /**
     * Método que permite obtener el identificador de la persona en el modelo core a partir de su identificador de condición en un proceso
     * de liquidación
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @param condicionPersona
     *        Identificador de condición persona
     * @return Identificador de la persona en core
     * @author rlopez
     */
    public Long consultarIdentificadorPersonaCore(String numeroRadicacion, Long condicionPersona);
    
    /**
     * Método que permite realizar la ejecución del SP de fallecimiento tras la gestión sobre una persona en los resultados de la
     * liquidación
     * @param numeroRadicado
     *        Valor del número de radicación
     * @param periodo
     *        Periodo relacionado a la liquidación
     * @param beneficiarioFallecido
     *        Indicador de fallecimiento de beneficiario
     * @param tipoIdentificacion
     *        Tipo de identificación de la persona gestionada
     * @param numeroIdentificacion
     *        Número de identificación de la persona gestionada
     * @author rlopez
     */
    public void ejecutarSPLiquidacionFallecimientoGestionPersona(String numeroRadicado, Long periodo, Boolean beneficiarioFallecido,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion);
    
    /**
	 * Método que permite obtener la información relacionada con los destinatarios
	 * del comunicado de una liquidación masiva
	 * 
	 * @param numeroRadicacion
	 *            Valor del número de radicación
	 * @param etiquetaPlantillaComunicado
	 * 				Valor de la etiqueta dekl comunicado al cual consultar la información relacionada
	 * @return lista DatosComunicadoDTO con la información de parametrización
	 * @author jocampo
	 */
    public List<Object[]> consultarInformacionComunicadosLiquidacionMasiva(String numeroRadicacion, EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicado);
    
    /**
     * Método que permite obtener el porcentaje de avance sobre un proceso de liquidación
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @return Número que representa el porcentaje de avance sobre el proceso de liquidación
     * @author rlopez
     */
    public Integer consultarPorcentajeAvanceProcesoLiquidacion(String numeroRadicacion);
    
    /**
     * Método que permite obtener el porcentaje de avance sobre un proceso de liquidación
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @return Número que representa el porcentaje de avance sobre el proceso de liquidación
     * @author rlopez
     */
    public Object[] consultarAvanceProcesoLiquidacion(String numeroRadicacion);
    
    /**
     * Método que permite registrar la cancelación para un proceso de liquidación
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @author rlopez
     */
    public void cancelarProcesoLiquidacion(String numeroRadicacion);
    
    /**
	 * Método que permite obtener la la persona objeto de la liquidación de fallecimiento
	 * 
	 * @param numeroRadicacion
	 *            Valor del número de radicación
	 * @return lista de nombres
	 * @author jocampo
	 */
    public List<Object[]> consultarNombreFallecido(String numeroRadicacion);
    
    /**
     * Método que permite registrar el inicio del avance para un proceso de liquidación
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @author jocampo
     */
    public void iniciarAvanceProcesoLiquidacion(String numeroRadicacion);
    
    /**
     * Método que permite consultar la cancelación de un proceso de liquidación
     * @param numeroRadicacion
     *        Valor del número de radicación
     * @return true si fue cancelada, de lo contrario false 
     * @author jocampo
     */
    public Boolean consultarCancelacionProcesoLiquidacion(String numeroRadicacion);

	/** Método que se encarga de buscar liquidaciones por empleador
	 *
	 * @param tipoIdentificacion
	 *            Valor del tipo de identificación del empleador
	 * @param numeroIdentificacion
	 *            Valor del número de identificación del empleador
	 * @param periodo
	 *            periodo liquidado
	 * @param fechaInicio
	 *            Rango inicial de la fecha de la liquidación
	 * @param fechaFin
	 *            Rango inicial de la fecha de la liquidación
	 * @param numeroRadicacion
	 *            numero de radicación de la liquidación
	 * @return listado de liquidaciones que cumplen con los criterios de búsqueda
	 * @author jocampo
	 */
	public List<ConsultaLiquidacionSubsidioMonetarioDTO> consultarLiquidacionesPorEmpleador(
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, Date periodo, Date fechaInicio,
			Date fechaFin, String numeroRadicacion, UriInfo uri, HttpServletResponse response);
	
	/**
	 * Método que se encarga de buscar liquidaciones por trabajador
	 *
	 * @param tipoIdentificacion
	 *            Valor del tipo de identificación del trabajador
	 * @param numeroIdentificacion
	 *            Valor del número de identificación del trabajador
	 * @param periodo
	 *            periodo liquidado
	 * @param fechaInicio
	 *            Rango inicial de la fecha de la liquidación
	 * @param fechaFin
	 *            Rango inicial de la fecha de la liquidación
	 * @param numeroRadicacion
	 *            numero de radicación de la liquidación
	 * @param tipoLiquidacion
	 * 			  tipo de liquidación
	 * @return listado de liquidaciones que cumplen con los criterios de búsqueda
	 * @author jocampo
	 */
	public List<ConsultaLiquidacionSubsidioMonetarioDTO> consultarLiquidacionesPorTrabajador(
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, List<Date> periodo, Date fechaInicio,
			Date fechaFin, String numeroRadicacion, TipoProcesoLiquidacionEnum tipoLiquidacion, UriInfo uri,
			HttpServletResponse response);
	
	/**
	 * @param tipoIdentificacion
	 *            Valor del tipo de identificación del trabajador
	 * @param numeroIdentificacion
	 *            Valor del número de identificación del trabajador
	 * @param numeroRadicacion
	 *            numero de radicación de la liquidación
	 * @param segmentoTrabajador
	 *            indica si consulta el bloque de validaciones del trabajador o el
	 *            bloque del empleador
	 * @return listado de las validaciones de la liquidación que cumplen con los
	 *         criterios de búsqueda
	 * @param tipoIdentificacionEmpl
	 *            Valor del tipo de identificación del trabajador
	 * @param numeroIdentificacionEmpl
	 *            Valor del número de identificación del trabajador
	 * @return
	 * @author jocampo
	 */
	public List<ConsultaLiquidacionSubsidioMonetarioDTO> consultarValidacionesLiquidacionesPorTrabajador(
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String numeroRadicacion,
			Boolean segmentoTrabajador, TipoIdentificacionEnum tipoIdentificacionEmpl, String numeroIdentificacionEmpl);
	
	/** Método que permite obtener el periodo de fallecimiento del beneficiario si el afiliado no ha cumplido
     * las validaciones
     * @param numeroRadicacion
     * @return Periodo de fallecimiento del beneficiario
     */
    public Date consultarPeriodoFallecimientoBenAfiliadoNoCumpleValidaciones(String numeroRadicacion);
	
	/**
	 * @param tipoIdentificacion
	 *            Valor del tipo de identificación del trabajador
	 * @param numeroIdentificacion
	 *            Valor del número de identificación del trabajador
	 * @param numeroRadicacion
	 *            numero de radicación de la liquidación
	 * @param periodo
	 *            periodo por el cual se consultará
	 * @param sitiosPago
	 *            nombres de los sitios de pago (llave valor) 
	 * @return listado de información de grupos familiares
	 * @author jocampo
	 */
	public List<DetalleResultadoPorAdministradorDTO> consultarGrupoFamiliarLiquidacionesPorTrabajador(
			TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacionEmp, String numeroIdentificacionEmp,
                        String numeroRadicacion,
			Date periodo, Map<Long, String> sitiosPago);


     
    /**
	 * Permite consultar la información de la vista 360 para liquidaciones de fallecimiento
	 * 
	 * @param numeroRadicado
	 * @return
	 * @author jocampo
	 */
	public Object[] consultarInfoLiquidacionFallecimientoVista360(String numeroRadicado);
	
	/**
	 * Metodo que consulta la ultima actualizción del modelo stagin que funciona
	 * como fecha corte de aportes
	 * 
	 * @return fecha corte de aportes
	 * @author jocampo
	 */
    public Date consultarUltimaFechaCorteAportes();

    /**Metodo encargado de saber si el beneficiario esta en la categoria PADRES
     * @param idCondicionBeneficiario
     *        <code>Long</code>
     *        Identificador de la condición del beneficiario
     * @return TRUE es esta en la categoria PADRES, FALSE de lo contrario.
     */
    public Boolean consultarBeneficiarioPadre(Long idCondicionBeneficiario, String numeroRadicado);
    
    /**
     * Metodo encargado de traer la información general del comunicado 137 (138)
     * Notificación rechazo liquidación específica por fallecimiento - Admin Subsidio
     * 
     * @param numeroRadicacion
     *          valor del numero de radicado
     * @return listado de la información relevante para la generación del comunicado
     * @author mosorio
     */
    public List<Object[]> consultarInformacionComunicado137(String numeroRadicacion, Long causa);

    /**
     * Metodo encargado de traer la información general del comunicado 138 (139)
     * Notificación rechazo liquidación específica por fallecimiento - trabajador
     * 
     * @param numeroRadicacion
     *          valor del numero de radicado
     * @return listado de la información relevante para la generación del comunicado
     * @author mosorio
     */
    public List<Object[]> consultarInformacionComunicado138(String numeroRadicacion, Long causa);

    /**
     * Metodo encargado de obtener las liquidaciones de un empleador por numero y tipo de identificación
     * @param tipoIdentificacion
     *        <code>TipoIdentificacionEnum</code>
     *        Tipo de identificación del empleador
     * @param numeroIdentificacion
     *        <code>String</code>
     *        Número identificación del empleador
     * @return Registros de liquidación del subsidio de un empleador
     */
    public List<RegistroLiquidacionSubsidioDTO> exportarLiquidacionesPorEmpleadorNoDispersadas(TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion, Date periodo, Date fechaInicio, Date fechaFin, String numeroRadicacion);

    /**
     * Método encargado de llamar el SP que permite consolidar los subsidios por fallecimiento
     * @param numeroRadicado
     *        <code>String</code>
     *        Número de radicación
     * @param modoDesembolso
     *        Modo en el que realiza el desembolso de subsidio
     */
    public void consolidarSubsidiosFallecimiento(String numeroRadicado,ModoDesembolsoEnum modoDesembolso);

    /**
     * Se obtiene la primera validación que tenia la persona antes de gestionar las demás.
     * @param numeroRadicacion
     * @param idCondicionPersona
     * @return validación que tenia al comienzo antes de gestionar la persona
     */
    public GrupoAplicacionValidacionSubsidioEnum obtenerPrimeraValidacionSubsidio(String numeroRadicacion, Long idCondicionPersona,
            TipoLiquidacionEspecificaEnum tipoLiquidacion, Boolean cumple, Boolean esTrabajadorFallecido);
    
    /**
     * Método encargado de consultar numero de beneficiarios con otro derecho asignado para el mismo perioso. CC liquidacion paralela.
     * @param numeroRadicado
     * @return numero de beneficiarios con derecho asignado para el mismo periodo
     */
    public Integer consultarBeneficiarioConDerechoAsignadoEnMismoPeriodo(String numeroRadicado);
    
    public void actualizarResultadoValidacionLiquidacionDerechoNoAsignado(String numeroRadicacion, String mensaje);
    
    /**
     * Método encargado de validar si el preprocesado de subsidio (staging) está en proceso
     * @param numeroRadicado
     * @return numero de beneficiarios con derecho asignado para el mismo periodo
     */
    public Boolean validarEnProcesoStaging();

    /**
     * Método encargado de verificar si hay personas afiliadas sin condiciones en el staging
     * @param perido
     * @param idPersonas
     * @param idEmpleadores
     * @return
     */
    public RespuestaVerificarPersonasSinCondicionesDTO consultarPersonasAfiliadosSinCondiciones(List<Long> periodos, List<Integer> idPersonas);
    
    /**
     * Método encargado de verificar si hay personas de un empleador sin condiciones en el staging
     * @param periodo
     * @param idEmpleadores
     * @return
     */
    public RespuestaVerificarPersonasSinCondicionesDTO consultarPersonasEmpleadoresSinCondiciones(List<Long> periodos, List<Integer> idEmpleadores);

	public Boolean validarMarcaAprobacionSegNivel(String numeroRadicado);

	public void eliminarMarcaAprobacionSegNivel();
	
	public List<Object[]> consultarBeneficiarioBloqueadosSubsidio();
	
	public int desbloquearBeneficiariosCMSubsidio(List<Long> idBeneficiarioBloqueados);
	
        public int consultarAfiliadoBeneficiarioCM(Long idBeneficiarioBloqueado);

	/**
     * Método que consulta los beneficiarios bloqueados    
     * @return lista de beneficiarios bloqueados
     * @author dsuesca
     */
    public List<BloqueoBeneficiarioCuotaMonetariaDTO> consultarBeneficiariosBloqueadosSubsidioFiltros(ConsultaBeneficiarioBloqueadosDTO consulta);
    
    public Boolean consultarExistenciaBeneficiariosBloqueadosSubsidio();
    
    /**
     * Metodo encargado de consultar las cuotas
     * @param numeroRadicacion
     * @return
     */
    public DispersionResultadoMedioPagoFallecimientoDTO consultarDispersionMontoLiquidadoFallecimientoProyeccionCuotas(String numeroRadicacion, Long identificadorCondicion);
	
    /**
     * Consulta si para la solicitud de liquidación existe error de validación por Aporte mínimo.
     * @param numeroRadicacion
     * @return TRUE: Existe/ FALSE: No existe.
     */
    public Boolean consultarValidacionAporteMinimoFallecimiento(String numeroRadicacion);
    
    /**
     * Consulta la condicion Persona del trabajador por Número de radicación.
     * @param numeroRadicacion
     * @return 
     */
    public Long consultarCondicionPersonaRadicacion(String numeroRadicacion);
    
    /**
     *  Confirma liquidación por fallecimiento.
     * @param numeroRadicacion
     * @return 
     */
    public void confirmarLiquidacionFallecimientoAporteMinimo(String numeroRadicacion);
    
    /**
     *  Consultar Resultado Liquidacion Sin procesar datos.
     * @param numeroRadicacion
     * @return 
     */
    public ResultadoLiquidacionFallecimientoDTO consultarResultadoLiquidacionGestionAporteMinimo(String numeroRadicacion);
   
    /**
     * Gestiona si el proceso de eliminación se está ejecutando.
     * @param numeroRadicado
     */     
    public void gestionProcesoEliminacion(String numeroRadicado);
    
    /**
     * Verifica si existe un proceso de subsidio por eliminación
     * @param numeroRadicado
     * @return
     */
    public boolean consultarEjecucionProcesoEliminacion(String numeroRadicado);
    
    /**
     * Consulta los descuentos asociados a una liquidación y trabajador
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param numeroRadicacion
     * @return
     */
    public List<ConsultaDescuentosSubsidioTrabajadorGrupoDTO> consultarDescuentosSubsidioTrabajador(
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String numeroRadicacion);
        /** Interface para el guardado del registro liquidacion masiva sin derecho */
    public void guardarListaComoCSV(String resultado, String numeroRadicacion) throws IOException;
            /** Interface para metodo consulta de liquidacion masiva sin derecho */
    public List<RegistroSinDerechoSubsidioDTO> generarDataLiquidaciomSinDerecho(String numeroRadicacion, Integer offset, Integer rows,TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion);

    public Long consultarNumeroRegistrosSinDerecho(String numeroRadicacion);
    public void limpiarGeneracionArchivoSinDerecho(String numeroRadicacion);
    public void iniciarProcesoGeneracionArchivoSinDerecho(String numeroRadicacion);
    public void insertarDatosSubsidioBloqueoAfiliadoBeneficiarioCM(BloqueoAfiliadoBeneficiarioCM bloqueoAfiliadoBeneficiarioCM);

    public void insercionCondicionesDbo(String numeroRadicado);

}
