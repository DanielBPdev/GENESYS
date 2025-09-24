package com.asopagos.pila.validadores.bloque0.ejb;

import java.util.Calendar;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.dto.ArchivoPilaDTO;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IGestorEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores;
import com.asopagos.pila.business.interfaces.IPrepararContexto;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.util.FuncionesValidador;
import com.asopagos.pila.validadores.IProcesadorBloque;
import com.asopagos.pila.validadores.ProcesadorBloque;
import com.asopagos.pila.validadores.bloque0.interfaces.IValidacionOIBloque0;
import com.asopagos.pila.validadores.bloque0.interfaces.ProcesadorOIBloque0Local;

/**
 * Bean de sesión que procesa la lógica de Bloque 0 para archivos OI
 * @author jrico
 */
@Stateless
public class ProcesadorOIBloque0Business extends ProcesadorBloque implements ProcesadorOIBloque0Local {

    @Inject
    private IPrepararContexto preparadorContexto;
    
    @Inject
    private IValidacionOIBloque0 validacionOIBloque0;
    
    @Inject
    private IGestorEstadosValidacion gestorEstados;
    
    @Inject
    private IPersistenciaDatosValidadores persistencia;
    
    private void init() {
        super.preparadorContexto = this.preparadorContexto;
        super.gestorEstados      = this.gestorEstados;
        super.persistencia       = this.persistencia;
    }
    
    
    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ProcesadorOIBloque0Business.class);
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void procesar (Map<String, Object> parametros, Integer intento) {
        String firmaMetodo = "ProcesadorOIBloque0Business.procesar(Map<String, Object>, Integer)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        init();
        intento = intento != null ? intento : 1 ;
        
        IndicePlanilla indicePlanilla = (IndicePlanilla) parametros.get(INDICE_PLANILLA_KEY);
        ArchivoPilaDTO archivoPila    = (ArchivoPilaDTO) parametros.get(IProcesadorBloque.ARCHIVO_PILA_KEY);
        
        String usuario = (String) parametros.get(USUARIO_KEY);
        RespuestaValidacionDTO respuestaGeneralTemp = (RespuestaValidacionDTO) parametros.get(RESPUESTA_GENERAL_KEY);
        Boolean validarBloque0 = (Boolean) parametros.get(IProcesadorBloque.VALIDA_BLOQUE_CERO_KEY);
        
        boolean existeIndicePlanilla = false;
        
        if(indicePlanilla!=null) {
            indicePlanilla.setFechaProceso(Calendar.getInstance().getTime());
            indicePlanilla.setUsuarioProceso(usuario);
            existeIndicePlanilla = true;
            
            archivoPila = new ArchivoPilaDTO();
            archivoPila.setIndicePlanilla(indicePlanilla);
            archivoPila.setUsuario(usuario);
        }   
        
        RespuestaValidacionDTO resultadoBloque = ejecutarBloque(archivoPila, validarBloque0);
        
        if(existeIndicePlanilla) {
            procesarBloque(resultadoBloque.getBloqueSiguente(), parametros, intento);
            parametros.put(RESPUESTA_GENERAL_KEY, addErrores(respuestaGeneralTemp, resultadoBloque));
        } else {
            parametros.put(RESPUESTA_GENERAL_KEY, resultadoBloque);
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    
    /**
     * Método para la ejecución del Bloque de validación Cero (B0) del Operador de Información
     * 
     * @param archivoPila
     *        DTO con la información de carga del archivo
     * @param validarBloque0
     *        Indicador para determinar sí se debe ejecutar el B0 de validación luego de la carga del archivo
     * @return <b>RespuestaValidacionDTO</b>
     *         Resultado de la validación
     */
    private RespuestaValidacionDTO ejecutarBloque(ArchivoPilaDTO archivoPila, Boolean validarBloque0) {
        String firmaMetodo = "ProcesadorOIBloque0Business.ejecutarBloque(ArchivoPilaDTO, Boolean)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaValidacionDTO result = new RespuestaValidacionDTO();
        
        Map<String, Object> parametrosContexto = null;

        // se prepara el contexto

        BloqueValidacionEnum bloqueActual = BloqueValidacionEnum.BLOQUE_0_OI;
        try {
            parametrosContexto = preparadorContexto.prepararContexto(bloqueActual, null, null);
        } catch (ErrorFuncionalValidacionException e) {
            // falla la preparación del contexto, no es posible que se presente en este bloque
        }
        
        // se inicia la validación de Bloque 0, esta retorna un índice de planilla
        try {
            if (archivoPila.getIndicePlanilla() == null) {
                result = validacionOIBloque0.registrarIndice(archivoPila, parametrosContexto, archivoPila.getUsuario(), validarBloque0);
            }
            else {
                IndicePlanilla indicePlanilla = archivoPila.getIndicePlanilla();
                result = validacionOIBloque0.validarBloqueCero(indicePlanilla, parametrosContexto, archivoPila.getUsuario());
                // se actualiza el estado del índice
		//MANTIS 266507 Se ejecuta internamente en validarBloqueCero
		//como se hacia antes de mejoras mundo 1
                //result = actualizarIndiceYEstadoBloque(indicePlanilla, indicePlanilla.getEstadoArchivo(), result, 0, null);
            }

            if (result.getErrorDetalladoValidadorDTO() == null || result.getErrorDetalladoValidadorDTO().isEmpty()) {
                bloqueActual = BloqueValidacionEnum.BLOQUE_1_OI;
            } else {
                bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
            }
        } catch (Exception e) {
            // se presenta un error en la carga relacionado con la información del DTO del archivo entrante
            // se agrega como un error que no quedará relacionado a ningún índice de planilla

            result = FuncionesValidador.agregarError(result, null, bloqueActual, e.getMessage(), TipoErrorValidacionEnum.ERROR_TECNICO, null);
            
            bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
        }
        
        result.setBloqueSiguente(bloqueActual);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
}
