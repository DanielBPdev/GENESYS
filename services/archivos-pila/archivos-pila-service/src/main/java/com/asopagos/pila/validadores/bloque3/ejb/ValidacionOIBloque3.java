package com.asopagos.pila.validadores.bloque3.ejb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.SubTipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.util.FuncionesValidador;
import com.asopagos.pila.validadores.bloque3.interfaces.IValidacionOIBloque3;

/**
 * <b>Descripción:</b> Clase que contiene la validación de bloque 3 para los archivos del operador de Información<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
@Stateless
public class ValidacionOIBloque3 implements IValidacionOIBloque3, Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidacionOIBloque3.class);

	/**
	 * (non-Javadoc) 
	 * @see com.asopagos.pila.validadores.bloque3.interfaces.IValidacionOIBloque3#validarBloque3(java.util.Map,
	 *      com.asopagos.pila.dto.RespuestaValidacionDTO)
	 */
	@SuppressWarnings("unchecked")
    @Override
	public RespuestaValidacionDTO validarBloque3(Map<String, Object> contexto, RespuestaValidacionDTO result)
			throws ErrorFuncionalValidacionException {
        logger.debug("Inicia validarBloque3(Map<String, Object>)");

        RespuestaValidacionDTO resultTemp = result;

        String mensajeDiferencias = null;
        String tipoError = TipoErrorValidacionEnum.TIPO_2.toString();

        //boolean error = false;
        HashMap<String, String[]> variables = (HashMap<String, String[]>) contexto.get(ConstantesContexto.VARIABLE_BLOQUE2);

        logger.info("Se obtienen las variables del contexto: " + variables);
        try {
            logger.info("Se obtienen el entry set del contexto: " + variables.entrySet());
        } catch (Exception e) {
            logger.error("Error al obtener el entry set del contexto: " + e.getMessage()+ e);
            // TODO: handle exception
        }

        // se recorren las variables almacenadas en el contexto
        for (Map.Entry<String, String[]> variable : variables.entrySet()) {
            String variableEnArchivoA = null;
            String variableEnArchivoI = null;
            String idCampoA = null;
            String idCampoI = null;
            TipoArchivoPilaEnum tipoA = null;
            TipoArchivoPilaEnum tipoI = null;

            try {
                variableEnArchivoA = variable.getValue()[0];

                // se hace una converisión de letra a número y de nuevo a letra para evitar diferencias tipo "1" != "01"
                variableEnArchivoA = "" + Integer.parseInt(variableEnArchivoA);

            } catch (NullPointerException e) {
            } catch (NumberFormatException e) {
            }

            idCampoA = variable.getValue()[2];
            tipoA = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(variable.getValue()[4]);

            try {
                variableEnArchivoI = variable.getValue()[1];

                // se hace una converisión de letra a número y de nuevo a letra para evitar diferencias tipo "1" != "01"
                variableEnArchivoI = "" + Long.parseLong(variableEnArchivoI);

            } catch (NullPointerException e) {
            } catch (NumberFormatException e) {
            }

            idCampoI = variable.getValue()[3];
            tipoI = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(variable.getValue()[5]);

            // se toma los valores nulos como campos sin valor
            if (variableEnArchivoA == null) {
                variableEnArchivoA = "sin valor";
            }

            if (variableEnArchivoI == null) {
                variableEnArchivoI = "sin valor";
            }

            // se toma los valores numéricos cero como campos sin valor
            try {
                if (Long.parseLong(variableEnArchivoA) == 0) {
                    variableEnArchivoA = "sin valor";
                }
            } catch (NumberFormatException nfe) {
            }

            try {
                if (Long.parseLong(variableEnArchivoI) == 0) {
                    variableEnArchivoI = "sin valor";
                }
            } catch (NumberFormatException nfe) {
            }

            // comparo los valores en cada archivo
            if ((variableEnArchivoA == null && variableEnArchivoI != null) || (variableEnArchivoA != null && variableEnArchivoI == null)
                    || ((variableEnArchivoA != null && variableEnArchivoI != null && !variableEnArchivoA.equals(variableEnArchivoI)))) {

                // se agrega error por archivo A
                mensajeDiferencias = MensajesValidacionEnum.ERROR_DIFERENCIA_CAMPOS.getReadableMessage(idCampoA, variableEnArchivoA,
                        tipoError, SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.getDescripcion());

                resultTemp = FuncionesValidador.agregarError(resultTemp, tipoA, BloqueValidacionEnum.BLOQUE_3_OI, mensajeDiferencias, null, null);

                // se agrega error por archivo I
                mensajeDiferencias = MensajesValidacionEnum.ERROR_DIFERENCIA_CAMPOS.getReadableMessage(idCampoI, variableEnArchivoI,
                        tipoError, SubTipoArchivoPilaEnum.DETALLE_APORTE.getDescripcion());

                resultTemp = FuncionesValidador.agregarError(resultTemp, tipoI, BloqueValidacionEnum.BLOQUE_3_OI, mensajeDiferencias, null, null);
            }
        }

        //        if (error) {
        //            logger.debug("Finaliza validarBloque3(Map<String, Object>) - " + mensajeDiferencias.toString());
        //            throw new ErrorFuncionalValidacionException(mensajeDiferencias.toString(), new Throwable());
        //        }

        logger.debug("Finaliza validarBloque3(Map<String, Object>)");
        return resultTemp;
    }

}
