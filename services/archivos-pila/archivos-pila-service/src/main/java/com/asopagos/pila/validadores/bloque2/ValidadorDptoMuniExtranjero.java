package com.asopagos.pila.validadores.bloque2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asopagos.enumeraciones.SubTipoCotizanteEnum;
import com.asopagos.enumeraciones.aportes.ClaseAportanteEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.SubTipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;

import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripcion:</b> Clase que valida el departamento y municipio de un cotizante que labore en el extranjero
 * 
 * CONTROL DE CAMBIOS 224118 - Anexo 2.1.1<br/>
 * <b>Módulo:</b> Asopagos - HU-211-391 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ValidadorDptoMuniExtranjero extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorDptoMuniExtranjero.class);

    /** Constantes para mensajes */
    private static final String TIPO_ARCHIVO = "Tipo de archivo";
    private static final String CLASE_APORTANTE = "Clase de Aportante";
    private static final String TIPO_COTIZANTE = "Tipo de Cotizante";
    private static final String SUBTIPO_COTIZANTE = "Subtipo de Cotizante";

    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");

        String mensaje = null;

        // se cargan los valores de la línea
        Map<String, Object> valoresLinea = args.getLineValues();

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        // se carga el tipo de archivo
        String tipoArchivo = (String) args.getContext().get(ConstantesContexto.NOMBRE_TIPO_ARCHIVO);
        TipoArchivoPilaEnum tipoArchivoEnum = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(tipoArchivo);

        // se cargan el código de departamento y municipio de la CCF del contexto
        String dptoCCF = (String) args.getContext().get(ConstantesContexto.CODIGO_DPTO_CCF);
        String muniCCF = (String) args.getContext().get(ConstantesContexto.CODIGO_MUNI_CCF);

        if (tipoArchivoEnum == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO_MULTIPLE, tipoError, nombreCampo, TIPO_ARCHIVO);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (GrupoArchivoPilaEnum.APORTES_INDEPENDIENTES_DEPENDIENTES.equals(tipoArchivoEnum.getGrupo())
                && SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(tipoArchivoEnum.getSubtipo())) {

            String campoTipoCotizante = getParams().get(ConstantesParametroValidador.CAMPO_TIPO_COTIZANTE);
            String campoSubtipoCotizante = getParams().get(ConstantesParametroValidador.CAMPO_SUBTIPO_COTIZANTE);
            String campoColombianoExterior = getParams().get(ConstantesParametroValidador.CAMPO_COLOMBIANO_EXTERIOR);

            // se cargan los valores de la clase de aportante y tipo y subtipo de cotizante
            String claseAportante = (String) args.getContext().get(ConstantesContexto.CLASE_APORTANTE);
            ClaseAportanteEnum claseAportanteEnum = ClaseAportanteEnum.obtenerClaseAportanteEnum(claseAportante);
            
            Object valor = valoresLinea.get(campoTipoCotizante);

            Integer tipoCotizante = null;
            if(valor != null && !valor.toString().isEmpty()){
                tipoCotizante = (Integer) valor;
            }
            TipoCotizanteEnum tipoCotizanteEnum = TipoCotizanteEnum.obtenerTipoCotizante(tipoCotizante);

            String marcaColombianoExterior = (String) valoresLinea.get(campoColombianoExterior);

            valor = valoresLinea.get(campoSubtipoCotizante);
            
            Integer subTipoCotizante = 0;
            if(valor != null && !valor.toString().isEmpty()){
                subTipoCotizante = (Integer) valor;
            }
            
            SubTipoCotizanteEnum subTipoCotizanteEnum = SubTipoCotizanteEnum.obtenerSubTipoCotizante(subTipoCotizante);

            if (claseAportanteEnum == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO_MULTIPLE, tipoError, nombreCampo, CLASE_APORTANTE);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            if (tipoCotizanteEnum == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO_MULTIPLE, tipoError, nombreCampo, TIPO_COTIZANTE);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            if (subTipoCotizanteEnum == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO_MULTIPLE, tipoError, nombreCampo, SUBTIPO_COTIZANTE);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            // Clase aportante I, tipo cotizante 3 y subtipo cotizante 10, debe presentar dpto y muni de la CCF en ubicación laboral
            // cotizante con marca "Colombiano residente en el exterior" aplica para la misma restricción
            if ((ClaseAportanteEnum.CLASE_I.equals(claseAportanteEnum)
                    && TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE.equals(tipoCotizanteEnum)
                    && SubTipoCotizanteEnum.SUBTIPO_10.equals(subTipoCotizanteEnum))
                    || (marcaColombianoExterior != null && marcaColombianoExterior.equals("X"))) {

                String campoDptoLaboral = getParams().get(ConstantesParametroValidador.CAMPO_DEPARTAMENTO);
                String campoMuniLaboral = getParams().get(ConstantesParametroValidador.CAMPO_MUNICIPIO);

                // se cargan el código de departamento y municipio de ubicación laboral de la línea
                String dptoLaboral = (String) valoresLinea.get(campoDptoLaboral);
                String muniLaboral = (String) valoresLinea.get(campoMuniLaboral);

                
                int departamentoLectura = 0;
                int municipioLectura = 0;
                
                if (null != dptoLaboral  && null != muniLaboral ){
                    try{
                        departamentoLectura = Integer.parseInt(dptoLaboral);
                        municipioLectura = Integer.parseInt(muniLaboral);
                    } catch(Exception e){
                        logger.error("Error al transformar valores a enteros - " + departamentoLectura + " " + municipioLectura);
                    }                 
                }
                
               if ((null != dptoLaboral  && departamentoLectura != 0 && !dptoCCF.equals(dptoLaboral)) || 
                        (null != muniLaboral && municipioLectura != 0 && !muniCCF.equals(muniLaboral))) {

                   Set<String[]> departamentosMunicipiosSet = null;
                   	
                   if(args.getContext().get(ConstantesContexto.DEPARTAMENTOS_MUNICIPIOS) != null && (args.getContext().get(ConstantesContexto.DEPARTAMENTOS_MUNICIPIOS) ) instanceof Set<?>) {
                   	departamentosMunicipiosSet = (Set<String[]>) args.getContext().get(ConstantesContexto.DEPARTAMENTOS_MUNICIPIOS);
                   }
                       
                   Boolean existenciaMunicipioEnDptoCCF = FuncionesValidador.validarExistenciaMunicipioEnDepto(departamentosMunicipiosSet, dptoCCF, muniLaboral);
                   
                   if(!existenciaMunicipioEnDptoCCF) {
                	   mensaje = MensajesValidacionEnum.ERROR_UBICACION_LABORAL_EXTRANJERO.getReadableMessage(idCampo, dptoLaboral,
                               muniLaboral, tipoError, nombreCampo, dptoCCF, muniCCF);

                       logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                       throw new FileProcessingException(mensaje);
                   }
                   
                }
            }

        }
        //        else if (GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(tipoArchivoEnum.getGrupo())
        //                && SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(tipoArchivoEnum.getSubtipo())) {
        //
        //            String campoPensionadoExterior = getParams().get(ConstantesParametroValidador.CAMPO_COLOMBIANO_EXTERIOR);
        //
        //            String campoDptoResidencia = getParams().get(ConstantesParametroValidador.CAMPO_DEPARTAMENTO);
        //            String campoMuniResidencia = getParams().get(ConstantesParametroValidador.CAMPO_MUNICIPIO);
        //
        //            // se cargan los datos de la línea
        //            String pensionadoExterior = (String) valoresLinea.get(campoPensionadoExterior);
        //            String dptoResidencia = (String) valoresLinea.get(campoDptoResidencia);
        //            String muniResidencia = (String) valoresLinea.get(campoMuniResidencia);
        //
        //            // sí se tiene la marca de "Pensionado residente en el exterior, la ubicación de residencia se debe igualar a la de la CCF"
        //            if (pensionadoExterior != null && pensionadoExterior.equalsIgnoreCase("X")) {
        //                if (dptoResidencia == null || !dptoCCF.equals(dptoResidencia) || muniResidencia == null
        //                        || !muniCCF.equals(muniResidencia)) {
        //
        //                    mensaje = MensajesValidacionEnum.ERROR_UBICACION_LABORAL_PENSIONADO_EXTRANJERO.getReadableMessage(idCampo,
        //                            dptoResidencia, muniResidencia, tipoError, nombreCampo, dptoCCF, muniCCF);
        //
        //                    logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
        //                    throw new FileProcessingException(mensaje);
        //                }
        //            }
        //        }

        logger.debug("Finaliza validate(LineArgumentDTO)");
    }

}
