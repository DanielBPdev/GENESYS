package com.asopagos.pila.validadores.transversales;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoFEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIPEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoLineaTipoRegistroEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.dto.ControlLoteOFDTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase en la que se actualizan los contadores y sumatorias de acuerdo al tipo de archivo<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
public class ValidadorContadoresYSumatorias extends LineValidator {
    /**
     * Referencia al logger
     */
    private static ILogger logger = LogManager.getLogger(ValidadorCampoContexto.class);

    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(LineArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(FieldArgumentDTO)");
      
        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = arg0.getLineValues();
        Map<String, Object> contexto = arg0.getContext();
        
        String tipoArchivo = (String) contexto.get(getParams().get(ConstantesParametroValidador.LLAVE_TIPO_ARCHIVO));
        TipoArchivoPilaEnum tipoArchivoEnum = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(tipoArchivo);

        if (tipoArchivoEnum != null) {
            // control de registros presentados
            Map<TipoLineaTipoRegistroEnum, Integer> listaControlRegistros = (EnumMap<TipoLineaTipoRegistroEnum, Integer>) contexto
                    .get(ConstantesContexto.LISTA_CONTROL_REGISTROS);

            TipoLineaTipoRegistroEnum tipoLinea = TipoLineaTipoRegistroEnum.obtenerPorIdLineDefinition(this.getLineDefinition().getId());
            if (tipoLinea != null && listaControlRegistros != null) {
                Integer cuentaRegistros = listaControlRegistros.get(tipoLinea);
                if (cuentaRegistros != null) {
                    cuentaRegistros++;
                    listaControlRegistros.replace(tipoLinea, cuentaRegistros);
                }
                contexto.replace(ConstantesContexto.LISTA_CONTROL_REGISTROS, listaControlRegistros);
            }

            if (tipoLinea != null && tipoLinea.getAdmiteMultiplesRegistros()) {             
                gestionarContadoresYSumatorias(tipoArchivoEnum, valoresDeLinea, contexto, tipoLinea, arg0.getLineNumber());
            }
        }

        logger.debug("Finaliza validate(FieldArgumentDTO)");
    }

    private void gestionarContadoresYSumatorias(TipoArchivoPilaEnum tipoArchivoEnum, Map<String, Object> valoresDeLinea,
            Map<String, Object> contexto, TipoLineaTipoRegistroEnum tipoLinea, Long numeroLinea) throws FileProcessingException {
        switch (tipoArchivoEnum) {
            case ARCHIVO_OI_I:
            case ARCHIVO_OI_IR:
                gestionarI(contexto, valoresDeLinea);
                break;
            case ARCHIVO_OI_IP:
            case ARCHIVO_OI_IPR:
                gestionarIP(contexto, valoresDeLinea);
                break;
            case ARCHIVO_OF:
                gestionarF(tipoLinea, valoresDeLinea, contexto, numeroLinea);
                break;
            default:
                break;
        }
    }

    @SuppressWarnings("unchecked")
    private void gestionarI(Map<String, Object> contexto, Map<String, Object> valoresDeLinea) {
        // se valida la presencia de cotizantes independientes
        Object valor = valoresDeLinea.get(EtiquetaArchivoIEnum.I25.getNombreCampo());
        if (valor != null && valor.toString().isEmpty()) {
            valor = null;
        }
        contexto = validarPresenciaIndependientes(contexto, (Integer) valor);

        Set<String> listaTiposCotizante = (HashSet<String>) contexto.get(ConstantesContexto.TIPOS_COTIZANTES_ENCONTRADOS);
        String tipoCotizante = "";

        valor = valoresDeLinea.get(EtiquetaArchivoIEnum.I25.getNombreCampo());
        if (valor != null && !valor.toString().isEmpty() && StringUtils.isNumeric(valor.toString())) {
            tipoCotizante = valor.toString();
            if (tipoCotizante.length() == 1) {
                tipoCotizante = "0" + tipoCotizante;
            }
        }

        listaTiposCotizante.add(tipoCotizante);

        // último número de secuencia registro tipo 2
        contexto.replace(ConstantesContexto.ULTIMA_SECUENCIA_REGISTRO_2, valoresDeLinea.get(EtiquetaArchivoIEnum.I21.getNombreCampo()));

        // contador de registros tipo 2
        contexto.replace(ConstantesContexto.CONTADOR_REGISTROS_2, ((int) contexto.get(ConstantesContexto.CONTADOR_REGISTROS_2)) + 1);

        // todas las tarifas en cero presentan una novedad SNL, IGE, LMA o IRL
        valor = valoresDeLinea.get(EtiquetaArchivoIEnum.I227.getNombreCampo());
        BigDecimal tarifa = null;
        if (valor != null && !valor.toString().isEmpty()) {
            tarifa = (BigDecimal) valor;
        }
        Object novedadSNL = valoresDeLinea.get(EtiquetaArchivoIEnum.I219.getNombreCampo());
        Object novedadIGE = valoresDeLinea.get(EtiquetaArchivoIEnum.I220.getNombreCampo());
        Object novedadLMA = valoresDeLinea.get(EtiquetaArchivoIEnum.I221.getNombreCampo());
        Object novedadIRL = valoresDeLinea.get(EtiquetaArchivoIEnum.I223.getNombreCampo());

        if ((tarifa != null && tarifa.compareTo(new BigDecimal(0)) == 0)
                // sí la tarifa es 0, verifico las novedades
                && (novedadIGE == null && novedadLMA == null && novedadSNL == null)
                // la novedad IRL se mira por aparte como un número
                && (novedadIRL != null && novedadIRL instanceof Integer && ((Integer) novedadIRL).equals(0))) {

            // sí la novedad IRL es por cero días, cambio el valor de la variable contexto
            contexto.replace(ConstantesContexto.TARIFA_CERO_NOVEDAD, false);

        }

        // sumatorias IBC (una general y 2 para registros A y C en planilla tipo N)
        // sumatorias Aporte Obligatorio (una general y 2 para registros A y C en planilla tipo N)
        valor = valoresDeLinea.get(EtiquetaArchivoIEnum.I226.getNombreCampo());
        BigDecimal ibc = null;
        if (valor != null && !valor.toString().isEmpty()) {
            ibc = (BigDecimal) valor;
        }

        valor = valoresDeLinea.get(EtiquetaArchivoIEnum.I228.getNombreCampo());
        BigDecimal aporteObligatorio = null;
        if (valor != null && !valor.toString().isEmpty()) {
            aporteObligatorio = (BigDecimal) valor;
        }

        String tipoPlanilla = (String) contexto.get(ConstantesContexto.TIPO_PLANILLA);
        TipoPlanillaEnum tipoPlanillaEnum = TipoPlanillaEnum.obtenerTipoPlanilla(tipoPlanilla);

        if (tipoPlanillaEnum != null && ibc != null && aporteObligatorio != null) {
            if (TipoPlanillaEnum.CORRECIONES.equals(tipoPlanillaEnum)) {
                String correccion = (String) valoresDeLinea.get(EtiquetaArchivoIEnum.I229.getNombreCampo());

                if (correccion != null && correccion.equals(ConstantesComunesProcesamientoPILA.CORRECCIONES_A)) {

                    contexto.replace(ConstantesContexto.SUMATORIA_IBC_A,
                            ((BigDecimal) contexto.get(ConstantesContexto.SUMATORIA_IBC_A)).add(ibc));
                    contexto.replace(ConstantesContexto.SUMATORIA_AO_A,
                            ((BigDecimal) contexto.get(ConstantesContexto.SUMATORIA_AO_A)).add(aporteObligatorio));

                }
                else if (correccion != null && correccion.equals(ConstantesComunesProcesamientoPILA.CORRECCIONES_C)) {

                    contexto.replace(ConstantesContexto.SUMATORIA_IBC_C,
                            ((BigDecimal) contexto.get(ConstantesContexto.SUMATORIA_IBC_C)).add(ibc));
                    contexto.replace(ConstantesContexto.SUMATORIA_AO_C,
                            ((BigDecimal) contexto.get(ConstantesContexto.SUMATORIA_AO_C)).add(aporteObligatorio));
                }
            }
            else {
                contexto.replace(ConstantesContexto.SUMATORIA_IBC_GENERAL,
                        ((BigDecimal) contexto.get(ConstantesContexto.SUMATORIA_IBC_GENERAL)).add(ibc));
                contexto.replace(ConstantesContexto.SUMATORIA_AO_GENERAL,
                        ((BigDecimal) contexto.get(ConstantesContexto.SUMATORIA_AO_GENERAL)).add(aporteObligatorio));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void gestionarIP(Map<String, Object> contexto, Map<String, Object> valoresDeLinea) {
        // contador de registros tipo 2
        contexto.replace(ConstantesContexto.CONTADOR_REGISTROS_2, ((int) contexto.get(ConstantesContexto.CONTADOR_REGISTROS_2)) + 1);

        // último número de secuencia registro tipo 2
        contexto.replace(ConstantesContexto.ULTIMA_SECUENCIA_REGISTRO_2, valoresDeLinea.get(EtiquetaArchivoIPEnum.IP22.getNombreCampo()));

        // sumatoria de las mesadas pensionales
        Object valor = valoresDeLinea.get(EtiquetaArchivoIPEnum.IP213.getNombreCampo());
        BigDecimal mesadaPensional = null;
        
        if(valoresDeLinea.get(EtiquetaArchivoIPEnum.IP224.getNombreCampo())==null){
        if (valor != null && !valor.toString().isEmpty()) {
            mesadaPensional = (BigDecimal) valoresDeLinea.get(EtiquetaArchivoIPEnum.IP213.getNombreCampo());
        }  

        contexto.replace(ConstantesContexto.SUMATORIA_MESADAS,
                ((BigDecimal) contexto.get(ConstantesContexto.SUMATORIA_MESADAS)).add(mesadaPensional));

        // sumatoria Aporte Obligatorio
        valor = valoresDeLinea.get(EtiquetaArchivoIPEnum.IP212.getNombreCampo());
        BigDecimal aporteObligatorio = null;
        if (valor != null && !valor.toString().isEmpty()) {
            aporteObligatorio = (BigDecimal) valor;
        }
        contexto.replace(ConstantesContexto.SUMATORIA_AO_GENERAL,
                ((BigDecimal) contexto.get(ConstantesContexto.SUMATORIA_AO_GENERAL)).add(aporteObligatorio));
        }else{
            if (valor != null && !valor.toString().isEmpty()) {
                mesadaPensional = (BigDecimal) valoresDeLinea.get(EtiquetaArchivoIPEnum.IP213.getNombreCampo());
            } 
            
            contexto.replace(ConstantesContexto.SUMATORIA_MESADAS,
                ((BigDecimal) contexto.get(ConstantesContexto.SUMATORIA_MESADAS)).add(mesadaPensional));
        
        valor = valoresDeLinea.get(EtiquetaArchivoIPEnum.IP212.getNombreCampo());
        BigDecimal aporteObligatorio = null;
        if (valor != null && !valor.toString().isEmpty()) {
            aporteObligatorio = (BigDecimal) valor;
        }
        if(valoresDeLinea.get(EtiquetaArchivoIPEnum.IP224.getNombreCampo()).equals("A")){
            contexto.replace(ConstantesContexto.SUMATORIA_AO_GENERAL,
                ((BigDecimal) contexto.get(ConstantesContexto.SUMATORIA_AO_GENERAL)).subtract(aporteObligatorio));
        }else{
            contexto.replace(ConstantesContexto.SUMATORIA_AO_GENERAL,
            ((BigDecimal) contexto.get(ConstantesContexto.SUMATORIA_AO_GENERAL)).add(aporteObligatorio));
        }
    }

        // listado de pensionados leídos para control de cantidad de pensionados
        ((HashSet<String>) contexto.get(ConstantesContexto.LISTA_PENSIONADOS))
                .add((String) valoresDeLinea.get(EtiquetaArchivoIPEnum.IP24.getNombreCampo()));
    }

    @SuppressWarnings("unchecked")
    private void gestionarF(TipoLineaTipoRegistroEnum tipoLinea, Map<String, Object> valoresDeLinea, Map<String, Object> contexto,
            Long numeroLinea) throws FileProcessingException{
        Integer numeroLote = null;
        Map<Integer, ControlLoteOFDTO> mapaControlSumatorias = (Map<Integer, ControlLoteOFDTO>) contexto
                .get(ConstantesContexto.MAPA_CONTROL_SUMATORIAS_LOTES_OF);

        // cuando el tipo de línea es un registro tipo 5, se toma el número de lote de la línea y se agrega al contexto
        if (TipoLineaTipoRegistroEnum.F5.equals(tipoLinea)) {
            numeroLote = (Integer) valoresDeLinea.get(EtiquetaArchivoFEnum.F54.getNombreCampo());
            
            ControlLoteOFDTO control = null;
            Integer loteAnterior = (Integer) contexto.get(ConstantesContexto.NUMERO_LOTE_REGISTRO_5_OF);

            if (loteAnterior != null) {
                // se toma el lote anterior para actualizar su línea final de lote
                control = mapaControlSumatorias.get(loteAnterior);
                control.setLineaFinLote(numeroLinea-1);
                
                contexto.replace(ConstantesContexto.NUMERO_LOTE_REGISTRO_5_OF, numeroLote);
            }
            else {
                contexto.put(ConstantesContexto.NUMERO_LOTE_REGISTRO_5_OF, numeroLote);
            }

            // se ubica el DTO con los datos de sumatoria del mapa, sí no se encuentra, se crea y agrega
            control = new ControlLoteOFDTO(numeroLote, numeroLinea);
            mapaControlSumatorias.put(numeroLote, control);
        }
        else if (!TipoLineaTipoRegistroEnum.F9.equals(tipoLinea)){
            // para los demás tipos de línea, se toma el # de lote del contexto y se hace toma de valores
            numeroLote = FuncionesValidador.buscarLoteLinea(numeroLinea, mapaControlSumatorias);

            // se ubica el DTO con los datos de sumatoria del mapa, sí no se encuentra, se crea y agrega
            ControlLoteOFDTO control = mapaControlSumatorias.get(numeroLote);
            
            // las operaciones se hacen de acuerdo al tipo de línea OF
            if (control != null && TipoLineaTipoRegistroEnum.F6.equals(tipoLinea)) {
                control.addContadorPlanillasLote();

                // sumatoria campo 8 registro 6
                Object valor = valoresDeLinea.get(EtiquetaArchivoFEnum.F68.getNombreCampo());
                Integer numeroRegistros6 = null;
                if (valor != null && !valor.toString().isEmpty()) {
                    numeroRegistros6 = (Integer) valor;
                }
                control.addSumatoriaCantidadRegistrosPlanillasLote(numeroRegistros6 != null ? numeroRegistros6 : 0);

                // sumatoria campo 10 registro 6
                valor = valoresDeLinea.get(EtiquetaArchivoFEnum.F610.getNombreCampo());
                BigDecimal valorPlanilla6 = null;
                if (valor != null && !valor.toString().isEmpty()) {
                    valorPlanilla6 = (BigDecimal) valor;
                }
                control.addSumatoriaValorRecaudoPlanillasLote(valorPlanilla6 != null ? valorPlanilla6 : BigDecimal.ZERO);
                
                control.getLineasEnLote().add(numeroLinea);

                // se agrega el número de la planilla en el listado del contexto
                String numeroPlanilla = (String) valoresDeLinea.get(EtiquetaArchivoFEnum.F65.getNombreCampo());
                String codOperador = (String) valoresDeLinea.get(EtiquetaArchivoFEnum.F69.getNombreCampo());
                if (numeroPlanilla != null) {
                    Set<String> listaPlanillas = (Set<String>) contexto.get(ConstantesContexto.MAPA_NUMEROS_PLANILLA_EN_OF);
                    try {
                        listaPlanillas.add(Long.parseLong(numeroPlanilla) + "-" + Short.parseShort(codOperador));
                    } catch (Exception e) {
                        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
                        String mensaje = MensajesValidacionEnum.ERROR_FORMATO_INCORRECTO.getReadableMessage(
                                EtiquetaArchivoFEnum.F69.toString(), codOperador.toString(), tipoError,
                                EtiquetaArchivoFEnum.F69.getNombreCampo(), codOperador.toString());
                        logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                        throw new FileProcessingException(mensaje);
                    }
                    contexto.replace(ConstantesContexto.MAPA_NUMEROS_PLANILLA_EN_OF, listaPlanillas);
                }
            }else if (control != null && TipoLineaTipoRegistroEnum.F8.equals(tipoLinea)) {
                // el registro tipo 8 sólo añade el número de línea al listado del lote
                control.getLineasEnLote().add(numeroLinea);
            }
        }else {
            // el registro tipo 9 marca el final del último lote
            numeroLote = (Integer) contexto.get(ConstantesContexto.NUMERO_LOTE_REGISTRO_5_OF);
            ControlLoteOFDTO control = mapaControlSumatorias.get(numeroLote);
            control.setLineaFinLote(numeroLinea-1);
        }
    }

    /**
     * Función para establecer que el archivo sólo contiene cotizantes independientes
     * 
     * @param contexto
     *        Mapa de valores de contexto
     * @param tipoCotizante
     *        Tipo de cotizante leído en línea
     * @return
     */
    private Map<String, Object> validarPresenciaIndependientes(Map<String, Object> contexto, Integer tipoCotizante) {

        logger.debug("Inicia validarPresenciaIndependientes(Map<String, Object> contexto, Integer tipoCotizante)");

        if (tipoCotizante != null) {
            TipoCotizanteEnum tipoCotizanteEnum = TipoCotizanteEnum.obtenerTipoCotizante(tipoCotizante);

            Boolean independienteMesActual = (Boolean) contexto.get(ConstantesContexto.TIENE_INDEPENDIENTE_MES_ACTUAL);
            Boolean independienteMesVencido = (Boolean) contexto.get(ConstantesContexto.TIENE_INDEPENDIENTE_MES_VENCIDO);

            if (tipoCotizanteEnum != null) {
                switch (tipoCotizanteEnum) {
                    case TIPO_COTIZANTE_INDEPENDIENTE:
                        independienteMesActual = Boolean.TRUE;
                        break;
                    case TIPO_COTIZANTE_IND_VOLUNTARIO_ARL:
                        independienteMesVencido = Boolean.TRUE;
                        break;
                    default:
                        break;
                }
            }
            
            if(contexto.get(ConstantesContexto.TIENE_INDEPENDIENTE_MES_ACTUAL) != null){
                contexto.replace(ConstantesContexto.TIENE_INDEPENDIENTE_MES_ACTUAL, independienteMesActual);
            }else{
                contexto.put(ConstantesContexto.TIENE_INDEPENDIENTE_MES_ACTUAL, independienteMesActual);
            }
            
            if(contexto.get(ConstantesContexto.TIENE_INDEPENDIENTE_MES_VENCIDO) != null){
                contexto.replace(ConstantesContexto.TIENE_INDEPENDIENTE_MES_VENCIDO, independienteMesVencido);
            }else{
                contexto.put(ConstantesContexto.TIENE_INDEPENDIENTE_MES_VENCIDO, independienteMesVencido);
            }
        }

        logger.debug("Finaliza validarPresenciaIndependientes(Map<String, Object> contexto, Integer tipoCotizante)");
        return contexto;
    }
}
