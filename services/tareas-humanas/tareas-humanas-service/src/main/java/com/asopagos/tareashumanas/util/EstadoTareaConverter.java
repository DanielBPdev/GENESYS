package com.asopagos.tareashumanas.util;

import org.kie.api.task.model.Status;
import com.asopagos.enumeraciones.core.EstadoTareaEnum;

/**
 * <b>Descripción:</b> Clase utulitaria para la conversión de tipos en la 
 * invocación de servicios al BPM
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class EstadoTareaConverter {
    
    
    /**
     * Método que obtiene el <code>EstadoTareaEnum</code> a partir del 
     * <code>Status</code>
     * 
     * @param status
     * @return el EstadoTareaEnum correspondiente
     */    
    public static EstadoTareaEnum toEstadoTareaEnum(Status status) {
        
        switch (status) {
            case Completed:
                return EstadoTareaEnum.COMPLETADA;
            case Created:
                return EstadoTareaEnum.CREADA;
            case Error:
                return EstadoTareaEnum.ERROR;
            case Exited:
                return EstadoTareaEnum.ABANDONADA;
            case Failed:
                return EstadoTareaEnum.FALLIDA;
            case InProgress:
                return EstadoTareaEnum.EN_PROGRESO;
            case Obsolete:
                return EstadoTareaEnum.OBSOLETA;
            case Ready:
                return EstadoTareaEnum.LISTA;
            case Reserved:
                return EstadoTareaEnum.RESERVADA;
            case Suspended:
                return EstadoTareaEnum.SUSPENDIDA;
            default:
                return null;                
        }
    }
    
    /**
     * Método que obtiene el <code>Status</code> a partir del 
     * <code>EstadoTareaEnum</code>
     * 
     * @param estadoTareaEnum
     * @return el Status correspondiente
     */
    public static Status toTaskStatus(EstadoTareaEnum estadoTareaEnum) {
        
        switch (estadoTareaEnum) {
            case COMPLETADA:
                return Status.Completed;
            case CREADA:
                return Status.Created;
            case ERROR:
                return Status.Error;
            case ABANDONADA:
                return Status.Exited;
            case FALLIDA:
                return Status.Failed;
            case EN_PROGRESO:
                return Status.InProgress;
            case OBSOLETA:
                return Status.Obsolete;
            case LISTA:
                return Status.Ready;
            case RESERVADA:
                return Status.Reserved;
            case SUSPENDIDA:
                return Status.Suspended;
            default:
                return null;
        }        
    }
    
}
