package com.asopagos.notificaciones.envio;

import java.util.HashMap;
import java.util.Map;
import com.asopagos.cartera.clients.ActualizarEstadoSolicitudPreventiva;
import com.asopagos.cartera.composite.clients.RegistrarGestionPreventiva;
import com.asopagos.cartera.composite.dto.ResultadoGestionPreventivaDTO;
import com.asopagos.enumeraciones.cartera.EstadoSolicitudPreventivaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.notificaciones.dto.NotificacionDTO;
import com.asopagos.tareashumanas.clients.TerminarTarea;
import com.asopagos.usuarios.clients.GenerarTokenAccesoCore;
import com.asopagos.usuarios.dto.TokenDTO;

/**
 * Clase que contiene la implementación del procesamiento de la gestión
 * preventiva cuando el envío del comunicado es exitoso.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class EnvioComunicadoExitosoGestionPreventiva implements EnvioComunicado {

    /**
     * Constante con el valor PRIMERO, para el primer intento de notificación.
     */
    private static final String PRIMERO = "PRIMERO";
    /**
     * Constante con la llave del parametro idTarea.
     */
    private static final String ID_TAREA = "idTarea";
    /**
     * Constante para enviar parametro de intento.
     */
    private static final String INTENTO = "INTENTO";
    /**
     * Constante con la clave del número de radicado. 
     */
    private static final String NUMERO_RADICADO = "numeroRadicado";
    /** 
     * Referencia al logger
     */
    private static ILogger logger = LogManager.getLogger(EnvioComunicadoExitosoGestionPreventiva.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.notificaciones.envio.EnvioComunicado#procesar(com.asopagos.notificaciones.dto.NotificacionDTO)
     */
    @Override
    public void procesar(NotificacionDTO notificacion) {
        logger.debug("Inicio método EnvioComunicadoExitosoGestionPreventiva.procesar(NotificacionDTO notificacion)");
        String intento = notificacion.getParams().get(INTENTO);
        if (intento != null) {
            String numeroRadicado = notificacion.getParams().get(NUMERO_RADICADO);
            /*primer intento de notificación de gestion preventiva se registra la acción*/
            if (intento.equals(PRIMERO)) {
                ResultadoGestionPreventivaDTO resultadoGestion = new ResultadoGestionPreventivaDTO();
                resultadoGestion.setNumeroRadicacion(numeroRadicado);
                resultadoGestion.setContactoEfectivo(Boolean.TRUE);
                RegistrarGestionPreventiva registrarGestion = new RegistrarGestionPreventiva(resultadoGestion);
                registrarGestion.execute();
            }
            else {
                /*segundo intento de gestión preventiva se finaliza la tarea y se cierra la solicitud exitosamente*/
                String idTarea = notificacion.getParams().get(ID_TAREA);
                terminarTarea(new Long(idTarea), null);
                actualizarEstadoSolicitudPreventiva(numeroRadicado, EstadoSolicitudPreventivaEnum.EXITOSA);
                actualizarEstadoSolicitudPreventiva(numeroRadicado, EstadoSolicitudPreventivaEnum.CERRADA);
            }
        }
        
        logger.debug("Fin método EnvioComunicadoExitosoGestionPreventiva.procesar(NotificacionDTO notificacion)");
    }
    
    /**
     * Método que termina una tarea del BPM
     * @param idTarea,
     *        es el identificador de la tarea
     * @param params,
     *        son los parámetros de la tarea
     */
    private void terminarTarea(Long idTarea, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        TerminarTarea terminarTarea = new TerminarTarea(idTarea, params);
        terminarTarea.execute();
    }
    /**
     * Método encargado de invocar el clietne que actualiza el estado de una
     * solicitud preventiva
     * 
     * @param numeroRadicacion,
     *        número de radicacion por de la solicitud a actualizar
     * @param estadoSolicitudPreventiva,
     *        estado de la solicitud preventiva a actualizar
     */
    private void actualizarEstadoSolicitudPreventiva(String numeroRadicacion, EstadoSolicitudPreventivaEnum estadoSolicitudPreventiva) {
        ActualizarEstadoSolicitudPreventiva actualizarEstadoSolicitud = new ActualizarEstadoSolicitudPreventiva(numeroRadicacion,
                estadoSolicitudPreventiva);
        actualizarEstadoSolicitud.execute();
    }
}
