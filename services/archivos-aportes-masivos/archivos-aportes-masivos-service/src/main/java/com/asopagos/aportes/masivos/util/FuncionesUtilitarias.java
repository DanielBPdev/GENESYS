package com.asopagos.aportes.masivos.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.entidades.ccf.core.OperadorInformacion;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.aportes.MarcaPeriodoEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.PeriodoPagoPlanillaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripcion:</b> Clase en la que se implementan funciones utilitarias de apoyo en el proceso compuesto
 * de aportes <br/>
 * <b>Módulo:</b> Asopagos - HU-21 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
public class FuncionesUtilitarias {
    /** Instancia del logger */
    private static final ILogger logger = LogManager.getLogger(FuncionesUtilitarias.class);

    /** Constructor privado para ocultar contructor por defecto */
    private FuncionesUtilitarias() {

    }

    /**
     * Método encargado de ubicar un juego de credenciales en un arreglo de PersonaModeloDTO
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param personas
     * @return
     */
    public static Long ubicarPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            List<PersonaModeloDTO> personas) {
        String firmaMetodo = "FuncionesUtilitarias.ubicarPersona(TipoIdentificacionEnum, String, List<PersonaModeloDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (tipoIdentificacion == null || numeroIdentificacion == null || personas == null || personas.isEmpty()) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        }

        for (PersonaModeloDTO persona : personas) {
            if (tipoIdentificacion.equals(persona.getTipoIdentificacion())
                    && numeroIdentificacion.equals(persona.getNumeroIdentificacion())) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return persona.getIdPersona();
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return null;
    }

    /**
     * Método encargado de ubicar un juego de credenciales en un arreglo de EmpresaModeloDTO
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param empresas
     * @return
     */
    public static Long ubicarEmpresa(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            List<EmpresaModeloDTO> empresas) {
        String firmaMetodo = "FuncionesUtilitarias.ubicarEmpresa(TipoIdentificacionEnum, String, List<EmpresaModeloDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (tipoIdentificacion == null || numeroIdentificacion == null || empresas == null || empresas.isEmpty()) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        }

        for (EmpresaModeloDTO empresa : empresas) {
            if (tipoIdentificacion.equals(empresa.getTipoIdentificacion())
                    && numeroIdentificacion.equals(empresa.getNumeroIdentificacion())) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return empresa.getIdEmpresa();
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return null;
    }

    /**
     * Método que ubica el id de un OI a partir de su código en pila
     * @param codOIPila
     * @param operadoresInformacion
     * @return
     */
    public static Long ubicarIdOI(Integer codOIPila, List<OperadorInformacion> operadoresInformacion) {
        String firmaMetodo = "FuncionesUtilitarias.ubicarIdOI(Integer, List<OperadorInformacion>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (codOIPila == null || operadoresInformacion == null || operadoresInformacion.isEmpty()) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return null;
        }

        for (OperadorInformacion operadorInformacion : operadoresInformacion) {
            Integer codigo = new Integer(operadorInformacion.getCodigo());
            if (codigo.compareTo(codOIPila) == 0) {
                return operadorInformacion.getId();
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return null;
    }

    /**
     * Método encargado de ubicar un aporte por su registro general o por su combinación de id de empresa
     * aportante y registro general
     * @param llave
     * @param aportesGenerales
     * @return
     */
    public static Boolean ubicarRegistroGeneral(Object llave, List<AporteGeneralModeloDTO> aportesGenerales) {
        String firmaMetodo = "FuncionesUtilitarias.ubicarRegistroGeneral(Object, List<AporteGeneralModeloDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        for (AporteGeneralModeloDTO aporteGeneral : aportesGenerales) {
            String llaveAporte = "" + aporteGeneral.getIdPersona() + aporteGeneral.getIdRegistroGeneral();

            if ((llave instanceof Long && aporteGeneral.getIdRegistroGeneral().compareTo((Long) llave) == 0)
                    || (llave instanceof String && llaveAporte.equalsIgnoreCase((String) llave))) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return Boolean.TRUE;
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return Boolean.FALSE;
    }

    /**
     * Método encargado de ubicar un aporte por su registro detallado
     * @param idRegistroDetallado
     * @param aportesDetallados
     * @return
     */
    public static Boolean ubicarRegistroDetallado(Long idRegistroDetallado, List<AporteDetalladoModeloDTO> aportesDetallados) {
        String firmaMetodo = "FuncionesUtilitarias.ubicarRegistroDetallado(Long, List<AporteDetalladoModeloDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        for (AporteDetalladoModeloDTO aporteDetallado : aportesDetallados) {
            if (aporteDetallado.getIdRegistroDetallado().compareTo(idRegistroDetallado) == 0) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return Boolean.TRUE;
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return Boolean.FALSE;
    }

    /**
     * Método encargado de ubicar un código por su código DANE
     * @param codigoMunicio
     * @param municipios
     * @return
     */
    public static Short ubicarMunicipio(String codigoMunicio, List<Municipio> municipios) {
        String firmaMetodo = "FuncionesUtilitarias.ubicarRegistroDetallado(Long, List<AporteDetalladoModeloDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        for (Municipio municipio : municipios) {
            if (municipio.getCodigo().equals(codigoMunicio)) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return municipio.getIdMunicipio();
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return null;
    }

    /**
     * Método encargado de obtener el estado de un empleador asociado a un ID de empresa
     * @param idEmpresa
     * @param empleadoresAportantes
     * @return
     */
    public static EmpleadorModeloDTO obtenerEmpleador(Long idEmpresa, List<EmpleadorModeloDTO> empleadoresAportantes) {
        String firmaMetodo = "FuncionesUtilitarias.obtenerEstadoEmpleador(Long, List<EmpleadorModeloDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        for (EmpleadorModeloDTO empleador : empleadoresAportantes) {
            if (empleador.getIdEmpresa().compareTo(idEmpresa) == 0) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                return empleador;
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return null;
    }

    /**
     * Método encargado de obtener el estado de un RolAfiliado asociado a un ID de persona
     * @param tipoAfiliacion
     * @param idPersona
     * @param rolesAfiliados
     * @return
     */
    public static RolAfiliadoModeloDTO obtenerRolAfiliado(TipoAfiliadoEnum tipoAfiliacion, Long idPersona,
            List<RolAfiliadoModeloDTO> rolesAfiliados) {
        String firmaMetodo = "FuncionesUtilitarias.obtenerRolAfiliado(TipoSolicitanteMovimientoAporteEnum, List<RolAfiliadoModeloDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (tipoAfiliacion != null) {
            for (RolAfiliadoModeloDTO rolAfiliado : rolesAfiliados) {
                if (rolAfiliado.getTipoAfiliado().equals(tipoAfiliacion)
                        && rolAfiliado.getAfiliado().getIdPersona().compareTo(idPersona) == 0) {

                    logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                    return rolAfiliado;
                }
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return null;
    }

    /**
     * Método encargado de calcular el valor de marca de período del aporte
     * @param periodo
     * @param oportunidadPago
     * @return
     */
    public static MarcaPeriodoEnum calcularMarcaPeriodo(String periodo, PeriodoPagoPlanillaEnum oportunidadPago) {
        String firmaMetodo = "FuncionesUtilitarias.calcularMarcaPeriodo(String, PeriodoPagoPlanillaEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        MarcaPeriodoEnum result = null;

        LocalDate periodoFecha = LocalDate.parse(periodo + "-01");
        LocalDate periodoActual = LocalDate.now().withDayOfMonth(1);

        switch (oportunidadPago) {
            case ANTICIPADO:
                periodoActual = periodoActual.plusMonths(1L);
                break;
            case MES_VENCIDO:
                periodoActual = periodoActual.minusMonths(1L);
                break;
            default:
                break;
        }

        Integer comparacion = periodoActual.compareTo(periodoFecha);
        if (comparacion > 0) {
            result = MarcaPeriodoEnum.PERIODO_RETROACTIVO;
        }
        else if (comparacion == 0) {
            result = MarcaPeriodoEnum.PERIODO_REGULAR;
        }
        else {
            result = MarcaPeriodoEnum.PERIODO_FUTURO;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * Método encargado de ubicar la oportunidad de aporte de un rol afiliado por ID de persona y tipo de afiliación
     * @param idPersona
     * @param tipoAfiliacion
     * @param rolesAfiliados
     * @return
     */
    public static PeriodoPagoPlanillaEnum ubicarOportunidadRolAfiliado(Long idPersona, TipoSolicitanteMovimientoAporteEnum tipoAfiliacion,
            List<RolAfiliadoModeloDTO> rolesAfiliados) {
        String firmaMetodo = "FuncionesUtilitarias.ubicarOportunidadRolAfiliado(Long, TipoSolicitanteMovimientoAporteEnum, "
                + "List<RolAfiliadoModeloDTO>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        PeriodoPagoPlanillaEnum result = null;

        TipoAfiliadoEnum tipoAfiliadoEnum = null;
        
        switch(tipoAfiliacion){
            case EMPLEADOR:
                tipoAfiliadoEnum = TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE;
                break;
            case INDEPENDIENTE:
                tipoAfiliadoEnum = TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE;
                break;
            case PENSIONADO:
                tipoAfiliadoEnum = TipoAfiliadoEnum.PENSIONADO;
                break;
            default:
                break;
        }

        if (tipoAfiliadoEnum != null) {
            for (RolAfiliadoModeloDTO rolAfiliado : rolesAfiliados) {
                if (rolAfiliado.getTipoAfiliado().equals(tipoAfiliadoEnum)
                        && rolAfiliado.getAfiliado().getIdPersona().compareTo(idPersona) == 0) {
                    result = rolAfiliado.getOportunidadPago();
                    break;
                }
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }


    /**
     * Método para obtener una fecha en millisegundos a partir de un String
     * @param fechaCadena
     * @return <b>Long</b>
     */
    public static Long obtenerFechaMillis(String fechaCadena) {
        String firmaMetodo = "FuncionesUtilitarias.obtenerFechaMillis(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try {
            int anio = Integer.parseInt(fechaCadena.split("-")[0]);
            int mes = Integer.parseInt(fechaCadena.split("-")[1]);
            int dia = Integer.parseInt(fechaCadena.split("-")[2]);

            LocalDate fecha = LocalDate.of(anio, mes, dia);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return fecha.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }

	/**
	 * Metodo que toma una fecha en milisegundos y la convierte a String
	 * 
	 * @param fecha
	 *            Fecha a convertir
	 * @param completa
	 *            Indica que se desea fecha completa
	 * @return <b>String</b> Fecha convertida
	 */
	public static String formatoFechaMilis(Long fecha, Boolean completa) {
		logger.debug("Inicia formatoFechaMilis(Long)");

		String result = null;
		DateTimeFormatter formatter = null;
		
		if(!completa){
			LocalDate fechaLD = Instant.ofEpochMilli(fecha).atZone(ZoneId.systemDefault()).toLocalDate();

			formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			result = formatter.format(fechaLD);
		}else{
			LocalDateTime fechaLD = Instant.ofEpochMilli(fecha).atZone(ZoneId.systemDefault()).toLocalDateTime();

			formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");

			result = formatter.format(fechaLD);
		}

		logger.debug("Finaliza formatoFechaMilis(Long)");
		return result;
	}
}
