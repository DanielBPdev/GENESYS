package com.asopagos.pila.validadores.bloque2;

import java.util.Map;

import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación del campo tipo cotizante subsistema<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorTipoCotizanteSubsistema extends FieldValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorTipoCotizanteSubsistema.class);

    /**
     * Metodo se encarga de validar el tipo cotizante subsistema
     * @param FieldArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(FieldArgumentDTO)");

        String mensaje = null;
        
        Map<String, String> listaControlCorrecciones = null;
        Boolean esTipoACorreccionesN = false;

        if (arg0.getFieldValue() != null && !arg0.getFieldValue().toString().isEmpty()) {
            Integer tipoCotizante = (Integer) arg0.getFieldValue();
            TipoCotizanteEnum tipoCotizanteEnum = TipoCotizanteEnum.obtenerTipoCotizante(tipoCotizante);

            String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
            String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
            String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);
            
			String valorCampoTipoPlanilla;
			TipoPlanillaEnum tipoPlanilla = null;
			try {
				if (arg0.getContext() != null && arg0.getContext().get(ConstantesContexto.TIPO_PLANILLA) != null) {
	            	valorCampoTipoPlanilla = (String) arg0.getContext().get(ConstantesContexto.TIPO_PLANILLA);
	            	tipoPlanilla = TipoPlanillaEnum.obtenerTipoPlanilla(valorCampoTipoPlanilla);
	            	
	            	if(TipoPlanillaEnum.CORRECIONES.equals(tipoPlanilla)) {
	            		listaControlCorrecciones = (Map<String, String>) arg0.getContext().get(ConstantesContexto.LISTA_CONTROL_CORRECCIONES);
	            		if(listaControlCorrecciones.isEmpty()) {
	            			esTipoACorreccionesN = true;
	            		}
	            	}
	            	
				}
			} catch (Exception e) {
				valorCampoTipoPlanilla = null;
				tipoPlanilla = null;
			}
            
            boolean error = false;
            
            if (tipoCotizanteEnum != null && !esTipoACorreccionesN) {

                switch (tipoCotizanteEnum) {
                    case TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA:
                    case TIPO_COTIZANTE_DESEMPLEADO_SCCF:
                    case TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA:
                    case TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD:
                    case TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL:
                    //case TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN:
                    case TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD:
                    case TIPO_COTIZANTE_PAGO_POR_TERCERO:
                    case TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES:
                    case TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES:
                    case TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE:
                    case TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ:
                    case TIPO_COTIZANTE_PREPENSIONADO_AVS:
                    case TIPO_COTIZANTE_ESTUDIANTE_PRACTICA_LABORAL_SECTOR_PUBLICO: /** CC 224118 - Ingresa nuevo tipo no valido para CCF */
                    case TIPO_COTIZANTE_PROGRAMA_REINCORPORACION:
                        error = true;
                        break;
                        
                    case TIPO_COTIZANTE_BENEFICIARIO_UPC: 
                    	if(!( tipoPlanilla != null && TipoPlanillaEnum.CORRECIONES.equals(tipoPlanilla) )) {
                    		error = true;
                    	}                 
                        break;
                        
                    default:
                        break;
                }
            }
            if (error) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_PERMITIDO_PARA_REPORTE_SUBSISTEMA_CCF.getReadableMessage(idCampo,
                        tipoCotizante.toString(), tipoError, nombreCampo, tipoCotizanteEnum.getDescripcion());

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }

        logger.debug("Finaliza validate(FieldArgumentDTO)");
    }

}
