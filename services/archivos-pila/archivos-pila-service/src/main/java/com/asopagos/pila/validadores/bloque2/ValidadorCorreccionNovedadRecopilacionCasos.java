package com.asopagos.pila.validadores.bloque2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIEnum;
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.SubTipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.dto.CasoRevisionCorrecionNovDTO;
import com.asopagos.pila.dto.ResumenRegistro2DTO;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripcion:</b> CONTROL DE CAMBIOS 219141 - Clase que implementa la primare parte de la validación de
 * correciones en la presentación de novedades de ingreso y retiro en planillas tipo N.
 * 
 * Esta primera parte se encarga en la recopilación de información en el contexto para su comprobación al finalizar
 * la lectura de los registros tipo 2 <br/>
 * <b>Módulo:</b> Asopagos - HU-211-391<br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ValidadorCorreccionNovedadRecopilacionCasos extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorCorreccionNovedadRecopilacionCasos.class);

    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(
     *      co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        // se toma el tipo de planilla a partir del nombre del archivo
        String tipoArchivo = (String) args.getContext().get(ConstantesContexto.NOMBRE_TIPO_ARCHIVO);
        TipoArchivoPilaEnum tipoArchivoPilaEnum = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(tipoArchivo);

        if (tipoArchivoPilaEnum != null && GrupoArchivoPilaEnum.APORTES_INDEPENDIENTES_DEPENDIENTES.equals(tipoArchivoPilaEnum.getGrupo())
                && SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(tipoArchivoPilaEnum.getSubtipo())) {
            String marcaING = null;
            String marcaRET = null;

            // se consulta el valor del campo de correciones, siempre que el tipo de archivo sea detalle aporte dependientes/independientes
            String correcciones = (String) valoresDeLinea.get(EtiquetaArchivoIEnum.I229.getNombreCampo());

            // se consulta el tipo de ID del cotizante
            String tipoId = (String) valoresDeLinea.get(EtiquetaArchivoIEnum.I23.getNombreCampo());
            TipoIdentificacionEnum tipoIdCotizanteEnum = TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(tipoId);

            // se consulta el número de ID del cotizante
            String numIdCotizante = (String) valoresDeLinea.get(EtiquetaArchivoIEnum.I24.getNombreCampo());

            Object valor = valoresDeLinea.get(EtiquetaArchivoIEnum.I226.getNombreCampo());

            // se consulta el IBC
            BigDecimal ibc = null;
            if (valor != null && !valor.toString().isEmpty()) {
                ibc = (BigDecimal) valor;
            }

            valor = valoresDeLinea.get(EtiquetaArchivoIEnum.I224.getNombreCampo());
            
            // se consultan los días cotizados
            Integer diasCotizados = null;
            if(valor != null && !valor.toString().isEmpty()){
                diasCotizados = (Integer) valor;
            }

            valor = valoresDeLinea.get(EtiquetaArchivoIEnum.I25.getNombreCampo());
            
            // se consulta el tipo de cotizante
            Integer tipoCot = null;
            if(valor != null && !valor.toString().isEmpty()){
                tipoCot = (Integer) valoresDeLinea.get(EtiquetaArchivoIEnum.I25.getNombreCampo());
            }
            
            TipoCotizanteEnum tipoCotizanteEnum = TipoCotizanteEnum.obtenerTipoCotizante(tipoCot);

            // se lee el número de la línea
            Long numeroLinea = args.getLineNumber();

            CasoRevisionCorrecionNovDTO casoRevision = null;

            // se agrega el DTO a la lista del contexto
            List<CasoRevisionCorrecionNovDTO> listaCasos = (List<CasoRevisionCorrecionNovDTO>) args.getContext()
                    .get(ConstantesContexto.LISTA_CASOS_CORRECCION);
            
            if (listaCasos == null) {
                listaCasos = new ArrayList<>();
                args.getContext().put(ConstantesContexto.LISTA_CASOS_CORRECCION, listaCasos);
            }

            if (ConstantesComunesProcesamientoPILA.CORRECCIONES_A.equals(correcciones) 
                    || ConstantesComunesProcesamientoPILA.CORRECCIONES_C.equals(correcciones)) {
                // se leen las marcas de novedades de ING y RET
                marcaING = (String) valoresDeLinea.get(EtiquetaArchivoIEnum.I215.getNombreCampo());
                marcaRET = (String) valoresDeLinea.get(EtiquetaArchivoIEnum.I216.getNombreCampo());

                // se consulta sí el cotizante ya se encuentra entre los casos ya cargados
                casoRevision = consultarCasoCotizante(listaCasos, tipoIdCotizanteEnum, numIdCotizante, tipoCotizanteEnum);

                if (casoRevision == null) {
                    // se crea el DTO para la comparación al final de los registros tipo 2
                    casoRevision = new CasoRevisionCorrecionNovDTO();
                    casoRevision.setNumIdCotizante(numIdCotizante);
                    casoRevision.setTipoCotizante(tipoCotizanteEnum);
                    casoRevision.setTipoIdCotizante(tipoIdCotizanteEnum);

                    asignarValoresCaso(casoRevision, numeroLinea, marcaING, marcaRET, correcciones, ibc, diasCotizados);

                    listaCasos.add(casoRevision);
                }
                else {
                    asignarValoresCaso(casoRevision, numeroLinea, marcaING, marcaRET, correcciones, ibc, diasCotizados);
                }
            }
        }

        logger.debug("Finaliza validate(LineArgumentDTO)");
    }

    /**
     * Método para asignar los valores del caso para su revisión
     * @param casoRevision
     *        DTO con la información a comparar al final de la lectura de registros tipo 2
     * @param numeroLinea
     *        Número de la línea en el archivo
     * @param marcaING
     *        Valor del campo 15 del registro tipo 2 del archivo de detalle de aporte para dependientes/independientes
     * @param marcaRET
     *        Valor del campo 16 del registro tipo 2 del archivo de detalle de aporte para dependientes/independientes
     * @param correcciones
     *        Valor del campo 29 del registro tipo 2 del archivo de detalle de aporte para dependientes/independientes
     * @param ibc
     *        Valor del campo 26 del registro tipo 2 del archivo de detalle de aporte para dependientes/independientes
     * @param diasCotizados
     *        Valor del campo 24 del registro tipo 2 del archivo de detalle de aporte para dependientes/independientes
     */
    private void asignarValoresCaso(CasoRevisionCorrecionNovDTO casoRevision, Long numeroLinea, String marcaING, String marcaRET,
            String correcciones, BigDecimal ibc, Integer diasCotizados) {
        logger.debug("Inicia asignarValoresCaso(CasoRevisionCorrecionNovDTO, String, String, String)");

        // se crea el DTO para la nueva línea que se agrega al caso
        ResumenRegistro2DTO linea = new ResumenRegistro2DTO();

        // se agrega el tipo de corrección
        linea.setCorrecciones(correcciones);

        // se agrega el número de línea
        linea.setNumeroLinea(numeroLinea);

        // se asignan las marcas de novedad
        if (marcaING != null && !marcaING.isEmpty()) {
            linea.setPresentaING(true);
        }

        if (marcaRET != null && !marcaRET.isEmpty()) {
            linea.setPresentaRET(true);
        }

        // se asignan los valores de IBC y días cotizados
        linea.setValorIBC(ibc);
        linea.setDiasCotizados(diasCotizados);

        // se agrega al arreglo respectivo de acuerdo al tipo de corrección
        if (ConstantesComunesProcesamientoPILA.CORRECCIONES_A.equals(correcciones)) {
            casoRevision.addLineaA(linea);
        }
        else if (ConstantesComunesProcesamientoPILA.CORRECCIONES_C.equals(correcciones)) {
            casoRevision.addLineaC(linea);
        }
        logger.debug("Finaliza asignarValoresCaso(CasoRevisionCorrecionNovDTO, String, String, String)");
    }

    /**
     * Función para buscar un caso de revisión en el listado del contexto con base en las credenciales del cotizante
     * 
     * @param listaCasos
     *        Listado de casos de corrección en novedades
     * @param tipoIdCotizanteEnum
     *        Tipo de ID del cotizante consultado
     * @param numIdCotizante
     *        Número de ID del cotizante consultado
     * @param tipoCotizanteEnum
     *        Tipo de cotizante
     * @return <b>CasoRevisionCorrecionNovDTO</b>
     *         DTO con el caso encontrado o NULL
     */
    private CasoRevisionCorrecionNovDTO consultarCasoCotizante(List<CasoRevisionCorrecionNovDTO> listaCasos,
            TipoIdentificacionEnum tipoIdCotizanteEnum, String numIdCotizante, TipoCotizanteEnum tipoCotizanteEnum) {
        logger.debug("Inicia consultarCasoCotizante(List<CasoRevisionCorrecionNovDTO>, TipoIdentificacionEnum, String, TipoCotizanteEnum)");

        CasoRevisionCorrecionNovDTO result = null;

        for (CasoRevisionCorrecionNovDTO casoRevision : listaCasos) {
            // Mantis 254905 - Se ajusta que no valide el valor de tipo cotizante
            if (casoRevision.getTipoIdCotizante().equals(tipoIdCotizanteEnum) && casoRevision.getNumIdCotizante().equals(numIdCotizante)) {

                result = casoRevision;
                break;
            }
        }

        logger.debug(
                "Finaliza consultarCasoCotizante(List<CasoRevisionCorrecionNovDTO>, TipoIdentificacionEnum, String, TipoCotizanteEnum)");
        return result;
    }

}
