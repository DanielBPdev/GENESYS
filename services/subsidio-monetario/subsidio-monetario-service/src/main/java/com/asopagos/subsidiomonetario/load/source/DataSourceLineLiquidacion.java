package com.asopagos.subsidiomonetario.load.source;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;

import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.locator.ResourceLocator;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.subsidiomonetario.business.interfaces.EntityManagerSubsidioPersistenceLocal;
import com.asopagos.subsidiomonetario.constants.NamedQueriesConstants;
import com.asopagos.subsidiomonetario.dto.RegistroLiquidacionSubsidioDTO;
import com.asopagos.util.CalendarUtils;
import co.com.heinsohn.lion.filegenerator.dto.QueryFilterInDTO;
import co.com.heinsohn.lion.filegenerator.ejb.ILineDataSource;
import co.com.heinsohn.lion.filegenerator.exception.FileGeneratorException;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;


/**
 * <b>Descripcion:</b> Clase que se encarga de obtener la información fuente para la generación del archivo de liquidación<br/>
 * <b>Módulo:</b> Asopagos - HU 442<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class DataSourceLineLiquidacion implements ILineDataSource {

    /**
     * Referencia al logger
     */
    final ILogger logger = LogManager.getLogger(DataSourceLineLiquidacion.class);

    /**
     * Atributo para dar reemplazo a valores nulos
     */
    final String cadenaVacia = "";

    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.filegenerator.ejb.ILineDataSource#getData(co.com.heinsohn.lion.filegenerator.dto.QueryFilterInDTO, int,
     *      int, javax.persistence.EntityManager)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<Object> getData(QueryFilterInDTO queryFilter, int firstResult, int maxResults, EntityManager em)
            throws FileGeneratorException {
        String firmaMetodo = "DataSourceLineLiquidacion.getData(QueryFilterInDTO,int,int,EntityManager)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        EntityManager entityManager = obtenerEntitySubsidio();
        Map<String, String> descuentosBeneficiarios = ((ArchivoLiquidacionFilterDTO) queryFilter).getDescuentosBeneficiarios();
        if (descuentosBeneficiarios != null && !descuentosBeneficiarios.isEmpty()) {
            logger.debug("Descuentos Beneficiarios: " + descuentosBeneficiarios);
        } else {
            logger.debug("No se encontraron descuentos para los beneficiarios.");
        }

        List<RegistroLiquidacionSubsidioDTO> registrosLiquidacion = new ArrayList<RegistroLiquidacionSubsidioDTO>();

        try {
        	List<Object[]> resultadosBeneficiarios;
			if (((ArchivoLiquidacionFilterDTO) queryFilter).getTipoIdentificacion() != null
					&& ((ArchivoLiquidacionFilterDTO) queryFilter).getNumeroIdentificacion() != null) {
				/*
				resultadosBeneficiarios = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_RESULTADO_LIQUIDACION_EMPLEADOR)
	                    .setParameter("numeroRadicacion", ((ArchivoLiquidacionFilterDTO) queryFilter).getNumeroRadicacion())
	                    .setParameter("tipoIdentificacion", ((ArchivoLiquidacionFilterDTO) queryFilter).getTipoIdentificacion().name())
	                    .setParameter("numeroIdentificacion", ((ArchivoLiquidacionFilterDTO) queryFilter).getNumeroIdentificacion()).getResultList();
	                    */
				resultadosBeneficiarios = entityManager.createStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_RESULTADO_LIQUIDACION)
						.registerStoredProcedureParameter("numeroRadicacion", String.class, ParameterMode.IN)
	                    .setParameter("numeroRadicacion", ((ArchivoLiquidacionFilterDTO) queryFilter).getNumeroRadicacion())
						.registerStoredProcedureParameter("tipoIdentificacion", String.class, ParameterMode.IN)
						.setParameter("tipoIdentificacion", ((ArchivoLiquidacionFilterDTO) queryFilter).getTipoIdentificacion().name())
						.registerStoredProcedureParameter("numeroIdentificacion", String.class, ParameterMode.IN)
						.setParameter("numeroIdentificacion", ((ArchivoLiquidacionFilterDTO) queryFilter).getNumeroIdentificacion()).getResultList();
			} else {/*
				resultadosBeneficiarios = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_RESULTADO_LIQUIDACION)
	                    .setParameter("numeroRadicacion", ((ArchivoLiquidacioenFilterDTO) queryFilter).getNumeroRadicacion()).getResultList();
	                    */
				
				resultadosBeneficiarios = entityManager.createStoredProcedureQuery(NamedQueriesConstants.CONSULTAR_RESULTADO_LIQUIDACION)
						.registerStoredProcedureParameter("numeroRadicacion", String.class, ParameterMode.IN)
	                    .setParameter("numeroRadicacion", ((ArchivoLiquidacionFilterDTO) queryFilter).getNumeroRadicacion()).getResultList();
			}
            
            //Map<String, Set<String>> beneficiariosPorTrabajador = new HashMap<>();
            

            for (Object[] registro : resultadosBeneficiarios) {
                
                //se valida que no sea el mismo trabajador con el mismo beneficiario
                /*if(beneficiariosPorTrabajador.isEmpty()){
                    beneficiariosPorTrabajador.put(registro[9].toString(), new HashSet<>());
                    beneficiariosPorTrabajador.get(registro[9].toString()).add(registro[12].toString());
                }else if( beneficiariosPorTrabajador.containsKey(registro[9].toString())){
                    if(beneficiariosPorTrabajador.get(registro[9].toString()).contains(registro[12].toString())){
                        continue;
                    }else{
                    beneficiariosPorTrabajador.get(registro[9].toString()).add(registro[12].toString());
                    }
                }else{
                    beneficiariosPorTrabajador.put(registro[9].toString(), new HashSet<>());
                    beneficiariosPorTrabajador.get(registro[9].toString()).add(registro[12].toString());
                
                
                if(registro[21] == null || registro[21].toString().equals(cadenaVacia)){
                    continue;
                }
                }*/
                RegistroLiquidacionSubsidioDTO registroLiquidacionDTO = new RegistroLiquidacionSubsidioDTO();
                registro = reemplazarNulos(registro);

                String rvl = registro[0].toString();
                if (descuentosBeneficiarios != null && !descuentosBeneficiarios.isEmpty()) {
                    if (descuentosBeneficiarios.containsKey(rvl)) {
                        String cadenaCodigos = descuentosBeneficiarios.get(rvl);
                        registroLiquidacionDTO.setCodigoConvenio(cadenaCodigos);
                    }
                }
                else {
                    registroLiquidacionDTO.setCodigoConvenio(registro[24].toString());
                }

                registroLiquidacionDTO.setFechaLiquidacion(CalendarUtils.darFormatoYYYYMMDDGuionDate(registro[1].toString()));
                registroLiquidacionDTO.setNumeroIdentificacionEmpleador(registro[2].toString());
                registroLiquidacionDTO.setTipoIdentificacionEmpleador(
                        validarExistenciaValorEnumeracion(TipoIdentificacionEnum.class, registro[3].toString())
                                ? TipoIdentificacionEnum.valueOf(registro[3].toString()).getDescripcion() : "");
                registroLiquidacionDTO.setNombreEmpleador(registro[4].toString().replace(',', ' '));
                registroLiquidacionDTO.setCiiu(registro[5].toString());
                registroLiquidacionDTO.setCondicionAgraria(registro[6].toString().equals(Boolean.TRUE.toString()) ? "A" : "N");
               	registroLiquidacionDTO.setCodigoSucursal(registro[7].toString());
                if (!registro[8].toString().equals(cadenaVacia)) {
                    registroLiquidacionDTO.setAnioBeneficio1429(registro[8].toString());
                }
                registroLiquidacionDTO.setNumeroIdentificacionTrabajador(registro[9].toString());
                registroLiquidacionDTO.setTipoIdentificacionTrabajador(
                        validarExistenciaValorEnumeracion(TipoIdentificacionEnum.class, registro[10].toString())
                                ? TipoIdentificacionEnum.valueOf(registro[10].toString()).getDescripcion() : "");
                registroLiquidacionDTO.setNombreTrabajador(registro[11].toString());
                registroLiquidacionDTO.setNumeroIdentificacionBeneficiario(registro[12].toString());
                registroLiquidacionDTO.setTipoIdentificacionBeneficiario(
                        validarExistenciaValorEnumeracion(TipoIdentificacionEnum.class, registro[13].toString())
                                ? TipoIdentificacionEnum.valueOf(registro[13].toString()).getDescripcion() : "");
                registroLiquidacionDTO.setNombreBeneficiario(registro[14].toString());
                registroLiquidacionDTO
                        .setTipoSolicitante(validarExistenciaValorEnumeracion(TipoBeneficiarioEnum.class, registro[15].toString())
                                ? TipoBeneficiarioEnum.valueOf(registro[15].toString()).getDescripcion() : "");
                registroLiquidacionDTO.setClasificacion(validarExistenciaValorEnumeracion(ClasificacionEnum.class, registro[16].toString())
                        ? ClasificacionEnum.valueOf(registro[16].toString()).getDescripcion() : "");
                registroLiquidacionDTO.setValorCuota(BigDecimal.valueOf(Double.parseDouble(registro[17].toString().equals(cadenaVacia) ? "0": registro[17].toString())));
                registroLiquidacionDTO.setDescuento(BigDecimal.valueOf(Double.parseDouble(registro[18].toString().equals(cadenaVacia) ? "0": registro[18].toString())));
                registroLiquidacionDTO.setValorPagar(BigDecimal.valueOf(Double.parseDouble(registro[19].toString().equals(cadenaVacia) ? "0": registro[19].toString())));
                registroLiquidacionDTO.setInvalidez(registro[20].toString().equals(cadenaVacia) ? "NI" : "I");
                registroLiquidacionDTO.setNumeroIdentificacionAdministrador(registro[21].toString());
                registroLiquidacionDTO.setTipoIdentificacionAdministrador(
                        validarExistenciaValorEnumeracion(TipoIdentificacionEnum.class, registro[22].toString())
                                ? TipoIdentificacionEnum.valueOf(registro[22].toString()).getDescripcion() : "");
                registroLiquidacionDTO.setNombreAdministrador(registro[23].toString());
                registroLiquidacionDTO.setPeriodoLiquidado(registro[25].toString());
                registroLiquidacionDTO.setTipoPeriodo(registro[26].toString());
                registroLiquidacionDTO.setMedioDePago(validarExistenciaValorEnumeracion(TipoMedioDePagoEnum.class, registro[27].toString())
                ? TipoMedioDePagoEnum.valueOf(registro[27].toString()).getDescripcion() : "");

                registrosLiquidacion.add(registroLiquidacionDTO);
            }

        } catch (Exception e) {
        	logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo, e);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return (List) registrosLiquidacion;
    }

    /**
     * Método encargado de obtener una instancia del EntityManager direccionado al modelo de datos de Subsidio
     * @return <b>EntityManager</b>
     *         Instancia del EntityManager direccionado al modelo de datos de Subsidio
     */
    private EntityManager obtenerEntitySubsidio() {
        EntityManagerSubsidioPersistenceLocal emSubsidio = ResourceLocator.lookupEJBReference(EntityManagerSubsidioPersistenceLocal.class);
        return emSubsidio.getEntityManager();
    }

    /**
     * Método que permite reemplazar los valores nulos dentro del arreglo de tipo Object
     * @param registro
     *        valor del arreglo
     * @return arreglo con los valores reemplazados
     * @author rlopez
     */
    private Object[] reemplazarNulos(Object[] registro) {
        for (int i = 0; i < registro.length; i++) {
            registro[i] = (registro[i] == null) ? cadenaVacia : registro[i];
        }
        return registro;
    }
    
    /**
     * Método que permite validar la existencia de una cadena como valor de una enumeración
     * @param classEnum
     *        Clase de la enumeración
     * @param valor
     *        Valor a evaluar
     * @return Indicador de existencia del valor
     * @author rlopez
     */
    private <T extends Enum<?>> Boolean validarExistenciaValorEnumeracion(Class<T> classEnum, String valor) {
        List<T> values = Arrays.asList(classEnum.getEnumConstants());
        for (T t : values) {
            if (t.toString().equals(valor)) {
                return true;
            }
        }
        return false;
    }

}
