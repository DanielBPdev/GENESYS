package com.asopagos.pila.validadores.bloque2;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Metodo se encarga de validar el departamento municipio<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorDepartamentoMunicipio extends LineValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorDepartamentoMunicipio.class);
    private static final String DEPARTAMENTO_MUNICIPIO = "Departamento y municipio";

    /**
     * Metodo se encarga de validar el municipio departamento
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        String firmaMetodo = "validate(LineArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String mensaje = null;

        // se carga el listado de los departamentos y municipios
        Set<String[]> departamentosMunicipios = (Set<String[]>) args.getContext().get(ConstantesContexto.DEPARTAMENTOS_MUNICIPIOS);

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();
        Object valorCampo = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_DEPARTAMENTO));

        String departamento = null;
        if (valorCampo instanceof String) {
            departamento = (String) valorCampo;
        }
        else if (valorCampo != null) {
            departamento = valorCampo.toString();
        }
        
        valorCampo = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_MUNICIPIO));
        String municipio = null;
        if (valorCampo instanceof String) {
            municipio = (String) valorCampo;
        }
        else if (valorCampo != null) {
            municipio = valorCampo.toString();
        }
        
        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        boolean error = true;

        if (departamentosMunicipios != null && departamento != null && municipio != null && !departamento.isEmpty()
                && !municipio.isEmpty()) {
            for (Iterator<String[]> iterator = departamentosMunicipios.iterator(); iterator.hasNext();) {

                String[] departamentoMunicipio = iterator.next();

                try {
                    int departamentoBase = Integer.parseInt(departamentoMunicipio[0]);
                    int municipioBase = Integer.parseInt(departamentoMunicipio[1]);

                    int departamentoLectura = Integer.parseInt(departamento);
                    int municipioLectura = Integer.parseInt(municipio);

                    if (departamentoBase == departamentoLectura && municipioBase == municipioLectura) {
                        error = false;
                        break;
                    } else if(departamentoLectura == 0 && municipioLectura == 0){
                        error = false;
                        break;
                    }
                    
                } catch (NumberFormatException nfe) {
                    mensaje = MensajesValidacionEnum.ERROR_DEPARTAMENTO_MUNICIPIO.getReadableMessage(idCampo, municipio, departamento,
                            tipoError);
                    
                    logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
                    throw new FileProcessingException(mensaje);

                }
            }

            if (error) {
                mensaje = MensajesValidacionEnum.ERROR_DEPARTAMENTO_MUNICIPIO.getReadableMessage(idCampo, municipio, departamento,
                        tipoError);

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
                throw new FileProcessingException(mensaje);

            }
        }
        else {

            if(null == departamentosMunicipios){

                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO_MULTIPLE, tipoError, nombreCampo, DEPARTAMENTO_MUNICIPIO);

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
            
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
}
