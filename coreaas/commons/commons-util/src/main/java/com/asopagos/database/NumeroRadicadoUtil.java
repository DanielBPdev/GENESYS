package com.asopagos.database;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.dto.NumeroRadicadoCorrespondenciaDTO;
import com.asopagos.enumeraciones.core.TipoEtiquetaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.security.dto.UserDTO;

public class NumeroRadicadoUtil {

	/**
	 * obtiene el siguiente valor de la secuencia de numeros de radicado
	 */
	private static final String PROCEDURE_USP_GET_NUMERO_RADICADO = "dbo.USP_GET_NumeroRadicado";

	/**
	 * Referencia al logger
	 */
	private static final ILogger logger = LogManager.getLogger(NumeroRadicadoUtil.class);

	/**
	 * Servicio que genera y retonra la información de un numero de radicado para asignar en los diversos sitios que se usa 
	 * 
	 * @param entityManager
	 * @param tipoEtiqueta
	 * @param cantidad
	 * @param userDTO
	 * @return
	 */
	public NumeroRadicadoCorrespondenciaDTO obtenerNumeroRadicadoCorrespondencia(EntityManager entityManager,
			TipoEtiquetaEnum tipoEtiqueta, Integer cantidad, UserDTO userDTO) {
		
		String firmaServicio = "obtenerNumeroRadicadoCorrespondencia(EntityManager, TipoEtiquetaEnum, Integer, UserDTO)";
		logger.info("Inicia " + firmaServicio);
  System.out.println(" inicia ejecucion procedimiento almacenado PROCEDURE_USP_GET_NUMERO_RADICADO iCantidad: "+cantidad+"");
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery(PROCEDURE_USP_GET_NUMERO_RADICADO);
		query.registerStoredProcedureParameter("iCantidad", Integer.class, ParameterMode.IN);
		query.setParameter("iCantidad", cantidad);
		
		query.registerStoredProcedureParameter("iPrimerValor", Integer.class, ParameterMode.OUT);
		query.registerStoredProcedureParameter("sAnio", String.class, ParameterMode.OUT);
		
		query.execute();
  System.out.println(" fin ejecucion procedimiento almacenado PROCEDURE_USP_GET_NUMERO_RADICADO");
		Integer primerValor = (Integer) query.getOutputParameterValue("iPrimerValor");
		String anio = (String) query.getOutputParameterValue("sAnio");

		String constante = (String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_ID);
		String codigoCaja = String.format("%02d", new Integer(constante));
 System.out.println(" PROCEDURE_USP_GET_NUMERO_RADICADO primerValor: "+primerValor+" constante: "+constante+" codigoCaja: "+codigoCaja+" anio:"+anio);
		String codigoSede = null;
		if (!TipoEtiquetaEnum.NUMERO_RADICADO.equals(tipoEtiqueta)) {
			codigoSede = String.format("%02d", new Integer(userDTO.getSedeCajaCompensacion()));
		}
 System.out.println(" PROCEDURE_USP_GET_NUMERO_RADICADO codigoSede: "+ codigoSede +"tipoEtiqueta: "+tipoEtiqueta);
		NumeroRadicadoCorrespondenciaDTO num = new NumeroRadicadoCorrespondenciaDTO(codigoCaja, codigoSede, anio,
				primerValor, cantidad, tipoEtiqueta);
		
		logger.info("Finaliza " + firmaServicio);
		return num;
	}
}
