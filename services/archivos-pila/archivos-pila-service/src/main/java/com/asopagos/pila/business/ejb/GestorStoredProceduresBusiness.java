package com.asopagos.pila.business.ejb;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.ConstantesParametrosSp;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.staging.ControlEjecucionPlanillas;
import com.asopagos.entidades.pila.staging.PreliminarArchivoPila;
import com.asopagos.enumeraciones.pila.FasePila2Enum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.GestorStoredProceduresLocal;
import com.asopagos.pila.constants.NamedQueriesConstants;

import co.com.heinsohn.lion.common.util.FileUtilities;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.util.ConstantsProperties;

/**
 * <b>Descripcion:</b> Clase que implementa los métodos para el trabajo con procedimientos almacenados en BD
 * para el arranque de la segunda etapa de la gestión de aportes<br/>
 * <b>Módulo:</b> ArchivosPILAService - HU-211-395, HU-211-396, HU-211-480, HU-211-397, HU-211-398<br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
@Stateless
public class GestorStoredProceduresBusiness implements GestorStoredProceduresLocal, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(GestorStoredProceduresBusiness.class);

    private static final String USP_INICIAR_JOB_ETL = "USP_ExecutePILA2";

    /**
     * Referencia a la unidad de persistencia de PILA
     */
    @PersistenceContext(unitName = "archivosPila_PU")
    private EntityManager entityManager;

    /**
     * Referencia a la unidad de persistencia de staging de PILA
     */
    @PersistenceContext(unitName = "pilaStaging_PU")
    private EntityManager entityManagerStaging;

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.GestorStoredProceduresLocal#ejecutarProcesamientoPila2(java.lang.Long,
     *      com.asopagos.enumeraciones.pila.FasePila2Enum, java.lang.Boolean, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void ejecutarProcesamientoPila2(Long idIndicePlanilla, FasePila2Enum fase, Boolean esSimulado, String usuario, Long timeStamp) {
        logger.info("Inicia ejecutarProcesamientoPila2(Long, FasePila2Enum, Boolean) {" + idIndicePlanilla + ", " + fase.toString() + ", "
                + esSimulado + ", " + timeStamp + " } ");

        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery(USP_INICIAR_JOB_ETL);
        storedProcedure.registerStoredProcedureParameter(ConstantesParametrosSp.ID_INDICE_PLANILLA, Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter(ConstantesParametrosSp.FASE_INICIO, String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter(ConstantesParametrosSp.ES_SIMULADO, Boolean.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter(ConstantesParametrosSp.ID_PAQUETE, Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter(ConstantesParametrosSp.REANUDAR_TRANSACCION_410, Boolean.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter(ConstantesParametrosSp.ID_TRANSACCION_410, Long.class, ParameterMode.OUT);
        
        storedProcedure.setParameter(ConstantesParametrosSp.ID_INDICE_PLANILLA, idIndicePlanilla);
        storedProcedure.setParameter(ConstantesParametrosSp.FASE_INICIO, fase.toString());
        storedProcedure.setParameter(ConstantesParametrosSp.ES_SIMULADO, esSimulado);
        storedProcedure.setParameter(ConstantesParametrosSp.ID_PAQUETE, timeStamp);
        storedProcedure.setParameter(ConstantesParametrosSp.REANUDAR_TRANSACCION_410, Boolean.FALSE);
       //storedProcedure.setParameter(ConstantesParametrosSp.ID_TRANSACCION_410, 0L);

        storedProcedure.registerStoredProcedureParameter(ConstantesParametrosSp.USUARIO_PROCESO, String.class, ParameterMode.IN);
        if (usuario != null) {
            storedProcedure.setParameter(ConstantesParametrosSp.USUARIO_PROCESO, usuario);
        }
        else {
            storedProcedure.setParameter(ConstantesParametrosSp.USUARIO_PROCESO,
                    ConstantesParametrosSp.USUARIO_PROCESAMIENTO_POR_DEFECTO);
        }
        storedProcedure.execute();

        logger.info("Finaliza ejecutarProcesamientoPila2(Long, FasePila2Enum, Boolean, String)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores#obtenerValoresSecuencia(java.lang.Integer,
     *      java.lang.Integer)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public synchronized List<Long> obtenerValoresSecuencia(Integer cantidad, String nombreSecuencia) {
        String firmaMetodo = "obtenerValorSecuencia(EntityManager, Integer)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Long> result = new ArrayList<>();

        if (cantidad != 0) {
            StoredProcedureQuery query = entityManager
                    .createNamedStoredProcedureQuery(NamedQueriesConstants.EJECUTAR_USP_GET_CONJUNTO_SECUENCIAS_PERSISTENCIA_PILA_M1);

            query.setParameter("iCantidadValores", cantidad);
            query.setParameter("sNombreSecuencia", nombreSecuencia);
            query.execute();
            
            Long primerValor = (Long) query.getOutputParameterValue("iPrimerValor");
            Long ultimoValor = primerValor + cantidad - 1;

            for (long i = primerValor; i<=ultimoValor; i++) {
                result.add(i);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

	/** (non-Javadoc)
	 * @see com.asopagos.pila.business.interfaces.GestorStoredProceduresLocal#almacenarPaquetePlanillas(java.util.List, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void almacenarPaquetePlanillas(List<Long> idsParaPila2, Long timeStamp) {
        String firmaMetodo = "almacenarPaquetePlanillas(List<Long>, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        ControlEjecucionPlanillas control = null;
        List<Long> ids = obtenerValoresSecuencia(idsParaPila2.size(), "Sec_ControlEjecucionPlanillas");
        for (Long idPlanilla : idsParaPila2) {
			control = new ControlEjecucionPlanillas();
			control.setId(ids.get(0));
			ids.remove(0);
			control.setEjecutado(Boolean.FALSE);
			control.setIdPaquete(timeStamp);
			control.setIdPlanilla(idPlanilla);
			
			entityManagerStaging.persist(control);
		}

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void almacenarPreliminarArchivoPila(byte[] file, IndicePlanilla indicePlanilla, String locale) throws FileProcessingException {
		
		String insert = "INSERT INTO staging.PreliminarArchivoPila (papIndicePlanilla, papTipoArchivo, papTextoRegistro) VALUES ";

		try {

			boolean isUTF = false;
			String charsetName = FileUtilities.getFileEncoding(file);
			if (charsetName == null) {
				charsetName = ConstantsProperties.DEFAULT_ISO;
			} else if (charsetName.toUpperCase().matches(ConstantsProperties.UTF_PATTERN)) {
				isUTF = true;
			}

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new ByteArrayInputStream(file), charsetName));

			Long lineNumber = 0L;
			String line = readLine(reader, lineNumber, true);

			if (lineNumber == 1L && isUTF == true && line.length() > 0) {
				// verificar si para utf-32 se usan 1 o 2 caracteres
				if (Character.isIdentifierIgnorable(line.toCharArray()[0])) {
					line = line.substring(1, line.length());
				}
			}
			
			String tipo;
			switch (indicePlanilla.getTipoArchivo()) {
			        case ARCHIVO_OI_A:
			        case ARCHIVO_OI_AR:
			        case ARCHIVO_OI_AP:
			        case ARCHIVO_OI_APR:
				        tipo = "A";
				        break;
			        case ARCHIVO_OI_I:
			        case ARCHIVO_OI_IR:
			        case ARCHIVO_OI_IP:
			        case ARCHIVO_OI_IPR:
				        tipo = "I";
				        break;
			        default:
				        throw new FileProcessingException("Error cargando el archivo: " + indicePlanilla.getNombreArchivo() + 
						                                  ", tipo de archivo: " + indicePlanilla.getTipoArchivo());
			}

			/*
			* Proceso de validacion en el Insert Transaccional staging.PreliminarArchivoPila
			*
			*/
			int count = 0;
			PreliminarArchivoPila p;
			
			StringBuffer sb = new StringBuffer(insert);
			while (line != null) {
				count ++;
				
				if(count > 1) {
					sb.append(",");
				}
				
				sb.append("(");
				sb.append(indicePlanilla.getId());
				sb.append(",'");
				sb.append(tipo);
				sb.append("','");
				sb.append(line.replaceAll("'", " "));
				sb.append("')");
				
				if(count == 500) {
					entityManagerStaging.createNativeQuery(sb.toString()).executeUpdate();
					count = 0;
					sb = new StringBuffer(insert);
				}

				line = readLine(reader, lineNumber, true);
			}
			
			if(count > 0) {
				entityManagerStaging.createNativeQuery(sb.toString()).executeUpdate();
				logger.info("INSERT:DB "+sb.toString());
			}

		} catch (Exception e) {
			logger.error("Error cargando el archivo: " + file, e);
			throw new FileProcessingException("Error cargando el archivo: " + indicePlanilla.getNombreArchivo() + "." + e.getMessage(), e);
		}
	}

	private String readLine(BufferedReader reader, Long lineNumber, Boolean ignoreEmptyLines) throws IOException {
		String line = reader.readLine();
		lineNumber++;
		if (line != null && line.length() == 0 && Boolean.TRUE.equals(ignoreEmptyLines)) {
			return readLine(reader, lineNumber, ignoreEmptyLines);
		}
		return line;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void executePILA1Persistencia(Long idPlanilla) {
		entityManagerStaging.createNamedStoredProcedureQuery(NamedQueriesConstants.EXECUTE_PILA1_PERSISTENCIA)
		.setParameter("IndicePlanilla", idPlanilla)
				.execute();
	}
}