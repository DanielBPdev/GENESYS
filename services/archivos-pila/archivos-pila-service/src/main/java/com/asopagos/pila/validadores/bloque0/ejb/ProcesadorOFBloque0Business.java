package com.asopagos.pila.validadores.bloque0.ejb;

import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.dto.ArchivoPilaDTO;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IGestorEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores;
import com.asopagos.pila.business.interfaces.IPrepararContexto;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.util.FuncionesValidador;
import com.asopagos.pila.validadores.ProcesadorBloque;
import com.asopagos.pila.validadores.bloque0.interfaces.IValidacionOFBloque0;
import com.asopagos.pila.validadores.bloque0.interfaces.ProcesadorOFBloque0Local;

/**
 * Bean de sesión que procesa la lógica de Bloque 0 para archivos OF
 * @author jrico
 *
 */
@Stateless
public class ProcesadorOFBloque0Business extends ProcesadorBloque implements ProcesadorOFBloque0Local {

    @Inject
    private IPrepararContexto preparadorContexto;
    
    @Inject
    private IGestorEstadosValidacion gestorEstados;
    
    @Inject
    private IPersistenciaDatosValidadores persistencia;
    
    @Inject
    private IValidacionOFBloque0 validacionOFBloque0;
    
    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ProcesadorOFBloque0Business.class);
    
    private void init() {
        super.gestorEstados      = this.gestorEstados;
        super.persistencia       = this.persistencia;
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void procesar(Map<String, Object> parametros, Integer intento) {
        String firmaMetodo = "ProcesadorOFBloque0Business.procesar(Map<String, Object>, Integer)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        intento = intento != null ? intento : 1 ;
        init();
        
        ArchivoPilaDTO archivoPila = (ArchivoPilaDTO) parametros.get(ARCHIVO_PILA_KEY);
        
        RespuestaValidacionDTO resultadoBloque = ejecutarBloque (archivoPila);
        parametros.put(RESPUESTA_GENERAL_KEY, resultadoBloque);
        logger.info(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
    
    
    /**
     * Método para la ejecución del Bloque de validación cero (B0) del Operador Financiero
     * 
     * @param archivoPila
     *        DTO con la información de carga del archivo
     * @return RespuestaValidacionDTO
     *         DTO de respuesta de la validación que incluye la instancia del índice de planilla de Operador Financiero generada a partir de
     *         la carga
     */
    private RespuestaValidacionDTO ejecutarBloque(ArchivoPilaDTO archivoPila) {
        String firmaMetodo = "ProcesadorOFBloque0Business.ejecutarBloque(ArchivoPilaDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        RespuestaValidacionDTO result = new RespuestaValidacionDTO();
        BloqueValidacionEnum bloqueActual = BloqueValidacionEnum.BLOQUE_0_OF;
        
        Map<String, Object> parametrosContexto = null;

        try {
            parametrosContexto = preparadorContexto.prepararContexto(BloqueValidacionEnum.BLOQUE_0_OF, null, null);
        } catch (ErrorFuncionalValidacionException e) {
            // falla la preparación del contexto, no es posible que se presente en este bloque
            logger.debug(firmaMetodo + " :: Fallo en preparación contexto");
        }

        try {
            result = validacionOFBloque0.validarBloqueCero(archivoPila, parametrosContexto);
        } catch (ErrorFuncionalValidacionException e) {
            // se presenta un error en la carga relacionado con la información del DTO del archivo entrante
            // se agrega como un error que no quedará relacionado a ningún índice de planilla

            result = FuncionesValidador.agregarError(result, null, bloqueActual, e.getMessage(), TipoErrorValidacionEnum.ERROR_TECNICO,
                    null);
            if (result.getIndicePlanillaOF() == null) {
                result.setIndicePlanillaOF(new IndicePlanillaOF());
                result.getIndicePlanillaOF().setEstado(EstadoProcesoArchivoEnum.INCONSISTENCIA_NOMBRE_ARCHIVO);
            }
        }

        // si se presentaron errores durante la validación, se debe detener el proceso
        if (result.getErrorDetalladoValidadorDTO() != null && !result.getErrorDetalladoValidadorDTO().isEmpty()) {
            // sí el índice de planilla OF fue persistido, se debe actualizar su estado
            if (result.getIndicePlanillaOF().getId() != null) {
                actualizarIndiceYestadoBloqueOF(result, result.getIndicePlanillaOF(),
                        EstadoProcesoArchivoEnum.INCONSISTENCIA_NOMBRE_ARCHIVO, null, 0);
            }

            bloqueActual = BloqueValidacionEnum.FINALIZAR_CICLO;
        }
        
        result.setBloqueSiguente(bloqueActual);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
}
