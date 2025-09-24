package com.asopagos.pila.validadores.bloque2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIEnum;
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.SubTipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoNovedadPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.dto.CasoRevisionNovMultipleDTO;
import com.asopagos.pila.dto.ResumenRegistro2DTO;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripcion:</b>CONTROL DE CAMBIOS 224118 - Clase que implementa la primera parte de la validación de novedades en
 * registros múltiples.
 * 
 * Este paso consiste en la recolección de la información<br/>
 * <b>Módulo:</b> Asopagos - HU-211-391 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ValidadorNovedadesMultiplesRecoleccionDatos extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorNovedadesMultiplesRecoleccionDatos.class);
    private static final String SEPARADOR_LISTA = ",";
    private static final String SEPARADOR_CAMPO = "::";

    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(
     *      co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");

        // se cargan los valores de la línea
        Map<String, Object> valoresLinea = args.getLineValues();

        // se consultan los códigos de campo y tipo de novedad a evaluar de los parámetros
        String codigosTiposNovedades = getParams().get(ConstantesParametroValidador.LISTA_CAMPOS_TIPOS_NOVEDAD);

        // se toman el tipo de archivo y tipo de planilla desde el contexto
        String tipoArchivo = (String) args.getContext().get(ConstantesContexto.NOMBRE_TIPO_ARCHIVO);
        TipoArchivoPilaEnum tipoArchivoEnum = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(tipoArchivo);

        String tipoPlanilla = (String) args.getContext().get(ConstantesContexto.TIPO_PLANILLA);
        TipoPlanillaEnum tipoPlanillaEnum = TipoPlanillaEnum.obtenerTipoPlanilla(tipoPlanilla);

        if (tipoArchivoEnum != null && tipoPlanillaEnum != null) {
            // cuando se lee un archivo de detalle de dependientes/independientes con tipo de planilla diferente a correciones
            if (GrupoArchivoPilaEnum.APORTES_INDEPENDIENTES_DEPENDIENTES.equals(tipoArchivoEnum.getGrupo())
                    && SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(tipoArchivoEnum.getSubtipo())
                    && !TipoPlanillaEnum.CORRECIONES.equals(tipoPlanillaEnum)) {

                // se lee el número de la línea
                Long numeroLinea = args.getLineNumber();
                
                // se leen el salario, IBC, los días cotizados y el tipo de cotizante
                Object valor = valoresLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_SALARIO));
                BigDecimal salario = null;
                if(valor != null && !valor.toString().isEmpty()){
                    salario = (BigDecimal) valor;
                }
                
                valor = valoresLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_IBC));
                BigDecimal ibc = null;
                if(valor != null && !valor.toString().isEmpty()){
                    ibc = (BigDecimal) valor;
                }
                
                valor = valoresLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_DIAS_COTIZADOS));
                Integer diasCotizados = null;
                if(valor != null && !valor.toString().isEmpty()){
                    diasCotizados = (Integer) valor;
                }
                
                valor = valoresLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_COTIZANTE));
                Integer tipoCotizante = null;
                if(valor != null && !valor.toString().isEmpty()){
                    tipoCotizante = (Integer) valor;
                }

                // se consulta la lista de casos del contexto
                List<CasoRevisionNovMultipleDTO> listaCasos = (List<CasoRevisionNovMultipleDTO>) args.getContext()
                        .get(ConstantesContexto.LISTA_CASOS_NOVEDAD_MULTIPLE);
                
                TipoCotizanteEnum tipoCotizanteEnum = TipoCotizanteEnum.obtenerTipoCotizante(tipoCotizante);

                CasoRevisionNovMultipleDTO casoRevision = obtenerInstanciaCaso(valoresLinea, args.getContext(), listaCasos);
                
                if(casoRevision != null){

                    // se crea la línea de registro 2 y se agrega al caso
                    ResumenRegistro2DTO lineaRegistro2 = new ResumenRegistro2DTO();
                    lineaRegistro2.setNumeroLinea(numeroLinea);
                    lineaRegistro2.setValorSalario(salario);
                    lineaRegistro2.setValorIBC(ibc);
                    lineaRegistro2.setDiasCotizados(diasCotizados);
                    lineaRegistro2.setTipoCotizante(tipoCotizanteEnum);
                    
                    casoRevision.addRegistro2(lineaRegistro2);

                    // se leen los campos de novedades
                    String[] listaCampos = codigosTiposNovedades.split(SEPARADOR_LISTA);
                    for (String campo : listaCampos) {
                        // se separan el código del campo y el tipo de novedad
                        String codigoCampo = campo.split(SEPARADOR_CAMPO)[0];
                        String tipoNovedad = campo.split(SEPARADOR_CAMPO)[1];
                        TipoNovedadPilaEnum tipoNovedadEnum = TipoNovedadPilaEnum.obtenerTipoNovedadPila(tipoNovedad);

                        if (codigoCampo != null && tipoNovedadEnum != null) {
                            // se carga el valor de la marca de novedad de la línea
                            EtiquetaArchivoIEnum etiquetaCampo = Enum.valueOf(EtiquetaArchivoIEnum.class, codigoCampo);

                            Object marcaNov = valoresLinea.get(etiquetaCampo.getNombreCampo());
                            Boolean tieneNovedad = false;

                            // se modifica el DTO para representar la presencia de la novedad
                            if (marcaNov != null && marcaNov instanceof String && !((String) marcaNov).isEmpty()) {
                                tieneNovedad = true;
                            }
                            else if (marcaNov != null && marcaNov instanceof Integer && ((Integer) marcaNov) != 0) {
                                tieneNovedad = true;
                            }
                            
                            if(tieneNovedad){
                                switch (tipoNovedadEnum) {
                                    case NOVEDAD_IGE:
                                        lineaRegistro2.setPresentaIGE(tieneNovedad);
                                        break;
                                    case NOVEDAD_ING:
                                        lineaRegistro2.setPresentaING(tieneNovedad);
                                        break;
                                    case NOVEDAD_IRP:
                                        lineaRegistro2.setPresentaIRL(tieneNovedad);
                                        break;
                                    case NOVEDAD_LMA:
                                        lineaRegistro2.setPresentaLMA(tieneNovedad);
                                        break;
                                    case NOVEDAD_RET:
                                        lineaRegistro2.setPresentaRET(tieneNovedad);
                                        break;
                                    case NOVEDAD_SLN:
                                        lineaRegistro2.setPresentaSLN(tieneNovedad);
                                        break;
                                    case NOVEDAD_VAC_LR:
                                        lineaRegistro2.setPresentaVAC_LR(tieneNovedad);
                                        break;
                                    case NOVEDAD_VSP:
                                        lineaRegistro2.setPresentaVSP(tieneNovedad);
                                        break;
                                    case NOVEDAD_VST:
                                        lineaRegistro2.setPresentaVST(tieneNovedad);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }

        logger.debug("Finaliza validate(LineArgumentDTO)");
    }

    /**
     * Método encargado de obtener una instancia del DTO para el caso de revisión
     * @param valoresLinea
     *        Mapa con los valores de la línea del archivo
     * @param listaCasos
     *        Lista de casos de revisión
     * @param contexto
     *        Mapa de variables de contexto
     * @return <b>CasoRevisionNovMultipleDTO</b>
     *         Instancia del DTO, nuevo o ya cargado para el cotizante
     */
    private CasoRevisionNovMultipleDTO obtenerInstanciaCaso(Map<String, Object> valoresLinea, Map<String, Object> contexto,
            List<CasoRevisionNovMultipleDTO> listaCasos) {
        String firmaMetodo = "obtenerInstanciaCaso(Map<String, Object>, Map<String, Object>, List<CasoRevisionNovMultipleDTO>)";
        logger.debug("Inicia " + firmaMetodo);

        CasoRevisionNovMultipleDTO result = null;
        
        List<CasoRevisionNovMultipleDTO> listaCasosTemp = listaCasos;

        // se consulta el tipo de ID del cotizante
        String tipoId = (String) valoresLinea.get(EtiquetaArchivoIEnum.I23.getNombreCampo());
        TipoIdentificacionEnum tipoIdCotizanteEnum = TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(tipoId);

        // se consulta el número de ID del cotizante
        String numIdCotizante = (String) valoresLinea.get(EtiquetaArchivoIEnum.I24.getNombreCampo());
        
        // se valida que se cuente con el tipo de ID de cotizante, sí no se tiene, no se crea el caso
        // sí la lista no existe, se crea
        if(tipoIdCotizanteEnum != null && listaCasosTemp == null) {
            listaCasosTemp = new ArrayList<>();
            contexto.put(ConstantesContexto.LISTA_CASOS_NOVEDAD_MULTIPLE, listaCasosTemp);
        }else if (tipoIdCotizanteEnum != null && listaCasosTemp != null){
            // cuando la lista existe, se busca sí el caso para el cotizante existe
            result = consultarCasoCotizante(listaCasosTemp, tipoIdCotizanteEnum, numIdCotizante);
        }
        
        // sí no se encuentra el caso, se crea siempre que se tenga tipo de ID
        if(tipoIdCotizanteEnum != null && result == null){
            result = new CasoRevisionNovMultipleDTO();
            result.setNumIdCotizante(numIdCotizante);
            result.setTipoIdCotizante(tipoIdCotizanteEnum);
            
            // se agrega el caso a la lista
            listaCasosTemp.add(result);
        }

        logger.debug("Finaliza " + firmaMetodo);
        return result;
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
     * @return <b>CasoRevisionNovMultipleDTO</b>
     *         DTO con el caso encontrado o NULL
     */
    private CasoRevisionNovMultipleDTO consultarCasoCotizante(List<CasoRevisionNovMultipleDTO> listaCasos,
            TipoIdentificacionEnum tipoIdCotizanteEnum, String numIdCotizante) {
        logger.debug("Inicia consultarCasoCotizante(List<CasoRevisionCorrecionNovDTO>, TipoIdentificacionEnum, String)");

        CasoRevisionNovMultipleDTO result = null;

        for (CasoRevisionNovMultipleDTO casoRevision : listaCasos) {
            if (casoRevision.getTipoIdCotizante().equals(tipoIdCotizanteEnum) && casoRevision.getNumIdCotizante().equals(numIdCotizante)) {

                result = casoRevision;
                break;
            }
        }

        logger.debug("Finaliza consultarCasoCotizante(List<CasoRevisionCorrecionNovDTO>, TipoIdentificacionEnum, String)");
        return result;
    }

}
