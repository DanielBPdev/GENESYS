package com.asopagos.subsidiomonetario.load.source;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum;
import com.asopagos.locator.ResourceLocator;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.subsidiomonetario.business.interfaces.EntityManagerSubsidioPersistenceLocal;
import com.asopagos.subsidiomonetario.constants.NamedQueriesConstants;
import com.asopagos.subsidiomonetario.dto.RegistroSinDerechoSubsidioDTO;
import com.asopagos.util.CalendarUtils;
import co.com.heinsohn.lion.filegenerator.dto.QueryFilterInDTO;
import co.com.heinsohn.lion.filegenerator.ejb.ILineDataSource;
import co.com.heinsohn.lion.filegenerator.exception.FileGeneratorException;

/**
 * <b>Descripcion:</b> Clase que se encarga de obtener la información fuente para la generación del archivo de personas sin derecho en una liquidación<br/>
 * <b>Módulo:</b> Asopagos - HU 317-266<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class DataSourceLineSinDerecho implements ILineDataSource {
    
    /**
     * Referencia al logger
     */
    final ILogger logger = LogManager.getLogger(DataSourceLineSinDerecho.class);
    
    /**
     * Atributo para dar reemplazo a valores nulos
     */
    final String cadenaVacia = "";
    
    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.filegenerator.ejb.ILineDataSource#getData(co.com.heinsohn.lion.filegenerator.dto.QueryFilterInDTO, int,
     *      int, javax.persistence.EntityManager)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<Object> getData(QueryFilterInDTO queryFilter, int firstResult, int maxResults, EntityManager em)
            throws FileGeneratorException {
        String firmaMetodo = "DataSourceLineSinDerecho.getData(QueryFilterInDTO,int,int,EntityManager)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        logger.info("Inicio el metodo getData en la clase DataSouceLineSinDerecho");
        EntityManager entityManager = obtenerEntitySubsidio();
        List<RegistroSinDerechoSubsidioDTO> personasSinDerechoDTO = new ArrayList<RegistroSinDerechoSubsidioDTO>();

        try {
        	
        	if (((ArchivoLiquidacionFilterDTO) queryFilter).getTipoIdentificacion() != null
					&& ((ArchivoLiquidacionFilterDTO) queryFilter).getNumeroIdentificacion() != null) {
                        logger.info("Ingreso a la validacion del primer if de getData");
        		personasSinDerechoDTO.addAll(generarData(queryFilter, firstResult, maxResults, entityManager));
        	} else {
        		
        	    /*int cantidadTotal;
            	maxResults = 1000;
            	
        		cantidadTotal = (int) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_NUMERO_PERSONAS_SIN_DERECHO)
                        .setParameter("numeroRadicacion", ((ArchivoLiquidacionFilterDTO) queryFilter).getNumeroRadicacion())
                        .getSingleResult();
        		
        		int paginas = (int) (cantidadTotal/maxResults);
            	if((paginas * maxResults) < cantidadTotal) {
            		paginas++;
            	}
            	
            	for(int i = 0; i < paginas; i++) {
            		personasSinDerechoDTO.addAll(generarData(queryFilter, i * maxResults, maxResults, entityManager));
            	}*/
        	    logger.info("Ingreso a la validacion del else de getData");
        	    personasSinDerechoDTO.addAll(generarData(queryFilter, firstResult, maxResults, entityManager));
        	    
        	}

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        logger.info("El resultado de personas sin derechoDTO metodo getData: " + personasSinDerechoDTO);
        return (List) personasSinDerechoDTO;
    }
    
    /**
     * Método encargado de consultar y generar los datos de manera paginada 
     * @param queryFilter
     * @param firstResult
     * @param maxResults
     * @param entityManager
     * @return
     */
    private List<RegistroSinDerechoSubsidioDTO> generarData(QueryFilterInDTO queryFilter, int firstResult, int maxResults, EntityManager entityManager) {
    	String firmaMetodo = "DataSourceLineSinDerecho.generarData(QueryFilterInDTO,int,int,EntityManager)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        logger.info("Inicio el metodo generarData en la clase DataSouceLineSinDerecho");
        List<RegistroSinDerechoSubsidioDTO> personasSinDerechoDTO = new ArrayList<RegistroSinDerechoSubsidioDTO>();
    	List<Object[]> resultadosSinDerecho;
    	try {
    	    if (((ArchivoLiquidacionFilterDTO) queryFilter).getTipoIdentificacion() != null
                    && ((ArchivoLiquidacionFilterDTO) queryFilter).getNumeroIdentificacion() != null) {
                        logger.info("Ingreso a la primera validacion del if de generarData");
                resultadosSinDerecho = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONAS_SIN_DERECHO_EMPLEADOR)
                    .setParameter("numeroRadicacion", ((ArchivoLiquidacionFilterDTO) queryFilter).getNumeroRadicacion())
                    .setParameter("tipoIdentificacion", ((ArchivoLiquidacionFilterDTO) queryFilter).getTipoIdentificacion().name())
                    .setParameter("numeroIdentificacion", ((ArchivoLiquidacionFilterDTO) queryFilter).getNumeroIdentificacion()).getResultList();
            } else {
                /*
                resultadosSinDerecho = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONAS_SIN_DERECHO)  
                        .setParameter("numeroRadicacion", ((ArchivoLiquidacionFilterDTO) queryFilter).getNumeroRadicacion())                    
                        .setFirstResult(firstResult)
                        .setMaxResults(maxResults)
                        .getResultList();
                */
                logger.info("Ingreso al else de generarData");
                resultadosSinDerecho = entityManager
                .createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_USP_GET_RESULTADO_LIQUIDACION_SIN_DERECHO)            
                .setParameter("sNumeroRadicado", ((ArchivoLiquidacionFilterDTO) queryFilter).getNumeroRadicacion())
                .setParameter("tipoIdentificacion", "null")
                .setParameter("numeroIdentificacion", "null")
                .setParameter("offset", 0)
                .setParameter("rows", 1000000)
                .getResultList();
            }
    	} catch (Exception e) {
    	    logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
    	}
    	
        logger.info("Ingresa a realizar la iteracion del objeto");
        for (Object[] registro : resultadosSinDerecho) {
            registro = reemplazarNulos(registro);
            RegistroSinDerechoSubsidioDTO sinDerechoDTO = new RegistroSinDerechoSubsidioDTO();
            //logger.debug("numEmp: " + registro[5].toString()+ " - numTrabaj: " + registro[11]!=null?registro[11].toString():"null");

            if (!registro[1].toString().equals(cadenaVacia)) {
                sinDerechoDTO.setFechaLiquidacion(CalendarUtils.darFormatoYYYYMMDDGuionDate(registro[1].toString()));
            }
            
            sinDerechoDTO.setTipoLiquidacion(validarExistenciaValorEnumeracion(TipoProcesoLiquidacionEnum.class, registro[1].toString())
                    ? TipoProcesoLiquidacionEnum.valueOf(registro[2].toString()).equals(TipoProcesoLiquidacionEnum.MASIVA)
                            ? TipoProcesoLiquidacionEnum.MASIVA.getDescripcion() : TipoLiquidacionEnum.ESPECIFICA.getDescripcion()
                    : "");
            //TipoProcesoLiquidacionEnum.MASIVA.name().equals(registro[2].toString()) ? TipoLiquidacionEnum.MASIVA.getDescripcion() : TipoLiquidacionEnum.ESPECIFICA.getDescripcion() : "");
            sinDerechoDTO
                    .setSubtipoLiquidacion(validarExistenciaValorEnumeracion(TipoProcesoLiquidacionEnum.class, registro[1].toString())
                            ? TipoProcesoLiquidacionEnum.valueOf(registro[2].toString()).equals(TipoProcesoLiquidacionEnum.MASIVA)
                                    ? TipoProcesoLiquidacionEnum.MASIVA.getDescripcion()
                                    : TipoProcesoLiquidacionEnum.valueOf(registro[2].toString()).getDescripcion()
                            : "");
            //TipoProcesoLiquidacionEnum.MASIVA.name().equals(registro[2].toString())? "" :TipoProcesoLiquidacionEnum.valueOf(registro[1].toString()).getDescripcion(): "");
            sinDerechoDTO.setTipoIdentificacionEmpleador(
                    validarExistenciaValorEnumeracion(TipoIdentificacionEnum.class, registro[4].toString())
                            ? TipoIdentificacionEnum.valueOf(registro[4].toString()).getDescripcion() : "");
            sinDerechoDTO.setNumeroIdentificacionEmpleador(registro[5].toString());
            sinDerechoDTO.setNombreEmpleador(registro[6].toString());

            sinDerechoDTO.setCiiu(registro[7].toString());

            //try {
                // Intentamos convertir el valor a un entero
               // int ciiu = Integer.parseInt(registro[7].toString());

                // Si la conversión es exitosa, lo guardamos en sinDerechoDTO
                //sinDerechoDTO.setCiiu(Integer.toString(ciiu));
            //} catch (NumberFormatException e) {

                //sinDerechoDTO.setCiiu(registro[8].toString());
            //}
            sinDerechoDTO.setCondicionAgraria(registro[8].toString().equals(Boolean.TRUE.toString()) ? "A" : "N");
            sinDerechoDTO.setCodigoSucursal(registro[9].toString());
            if (!registro[10].toString().equals(cadenaVacia)) {
                sinDerechoDTO.setAnioBeneficio1429(registro[10].toString());
            }
            sinDerechoDTO.setNumeroIdentificacionTrabajador(registro[11].toString());
            sinDerechoDTO.setTipoIdentificacionTrabajador(
                    validarExistenciaValorEnumeracion(TipoIdentificacionEnum.class, registro[12].toString())
                            ? TipoIdentificacionEnum.valueOf(registro[12].toString()).getDescripcion() : "");
            sinDerechoDTO.setNombreTrabajador(registro[13].toString());
            sinDerechoDTO.setNumeroIdentificacionBeneficiario(registro[14].toString());
            sinDerechoDTO.setTipoIdentificacionBeneficiario(
                    validarExistenciaValorEnumeracion(TipoIdentificacionEnum.class, registro[15].toString())
                            ? TipoIdentificacionEnum.valueOf(registro[15].toString()).getDescripcion() : "");
            sinDerechoDTO.setNombreBeneficiario(registro[16].toString());
            sinDerechoDTO.setTipoSolicitante(validarExistenciaValorEnumeracion(TipoBeneficiarioEnum.class, registro[17].toString())
                    ? TipoBeneficiarioEnum.valueOf(registro[17].toString()).getDescripcion() : "");
            sinDerechoDTO.setClasificacion(validarExistenciaValorEnumeracion(ClasificacionEnum.class, registro[18].toString())
                    ? ClasificacionEnum.valueOf(registro[18].toString()).getDescripcion() : "");
            sinDerechoDTO.setRazonesSinDerecho(registro[19].toString());
            sinDerechoDTO.setPeriodoLiquidado(registro[20].toString());
            sinDerechoDTO.setTipoPeriodo(registro[21].toString());

            personasSinDerechoDTO.add(sinDerechoDTO);
        }

         StringBuilder sb = new StringBuilder();
        for (RegistroSinDerechoSubsidioDTO persona : personasSinDerechoDTO) {
            sb.append(persona.getFechaLiquidacion())
                    .append("|")
                    .append(persona.getTipoLiquidacion())
                    .append("|")
                    .append(persona.getSubtipoLiquidacion())
                    .append("|")
                    .append(persona.getTipoIdentificacionEmpleador())
                    .append("|")
                    .append(persona.getNumeroIdentificacionEmpleador())
                    .append("|")
                    .append(persona.getNombreEmpleador())
                    .append("|")
                    .append(persona.getCiiu())
                    .append("|")
                    .append(persona.getCondicionAgraria())
                    .append("|")
                    .append(persona.getCodigoSucursal())
                    .append("|")
                    .append(persona.getAnioBeneficio1429())
                    .append("|")
                    .append(persona.getTipoIdentificacionTrabajador())
                    .append("|")
                    .append(persona.getNumeroIdentificacionTrabajador())
                    .append("|")
                    .append(persona.getNombreTrabajador())
                    .append("|")
                    .append(persona.getTipoIdentificacionBeneficiario())
                    .append("|")
                    .append(persona.getNumeroIdentificacionBeneficiario())
                    .append("|")
                    .append(persona.getNombreBeneficiario())
                    .append("|")
                    .append(persona.getTipoSolicitante())
                    .append("|")
                    .append(persona.getClasificacion())
                    .append("|")
                    .append(persona.getRazonesSinDerecho())
                    .append("|")
                    .append(persona.getPeriodoLiquidado())
                    .append("|")
                    .append(persona.getTipoPeriodo())
                    .append("\n"); // Agrega un salto de línea después de cada registro
        }

//       Imprime la cadena resultante
        System.out.println(sb.toString());

        List<RegistroSinDerechoSubsidioDTO> listaPersonas = new ArrayList<>();
        String[] lineas = sb.toString().split("\n");
        for (String linea : lineas) {
            String[] campos = linea.split("\\|");
            RegistroSinDerechoSubsidioDTO persona = new RegistroSinDerechoSubsidioDTO();
            persona.setFechaLiquidacion(Date.valueOf(campos[0]));
            persona.setTipoLiquidacion(campos[1]);
            persona.setSubtipoLiquidacion(campos[2]);
            persona.setTipoIdentificacionEmpleador(campos[3]);
            persona.setNumeroIdentificacionEmpleador(campos[4]);
            persona.setNombreEmpleador(campos[5]);
            persona.setCiiu(campos[6]);
            persona.setCondicionAgraria(campos[7]);
            persona.setCodigoSucursal(campos[8]);
            persona.setAnioBeneficio1429(campos[9]);
            persona.setTipoIdentificacionTrabajador(campos[10]);
            persona.setNumeroIdentificacionTrabajador(campos[11]);
            persona.setNombreTrabajador(campos[12]);
            persona.setTipoIdentificacionBeneficiario(campos[13]);
            persona.setNumeroIdentificacionBeneficiario(campos[14]);
            persona.setNombreBeneficiario(campos[15]);
            persona.setTipoSolicitante(campos[16]);
            persona.setClasificacion(campos[17]);
            persona.setRazonesSinDerecho(campos[18]);
            persona.setPeriodoLiquidado(campos[19]);
            persona.setTipoPeriodo(campos[20]);
            listaPersonas.add(persona);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
         logger.info("Respuesta de parte de la DB de liquidacion de personas sin derecho DTO: " + personasSinDerechoDTO);
        return listaPersonas;
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
