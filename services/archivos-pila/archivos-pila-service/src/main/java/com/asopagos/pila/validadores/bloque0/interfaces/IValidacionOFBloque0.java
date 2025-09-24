package com.asopagos.pila.validadores.bloque0.interfaces;

import java.util.Map;
import javax.ejb.Local;
import com.asopagos.dto.ArchivoPilaDTO;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;

/**
 * <b>Descripción:</b> Interface para el EJB con el procedimiento para la validación del bloque 0 del archivo de Operador Financiero <br>
 * <b>Módulo:</b> ArchivosPILAService - HU 388, 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
@Local
public interface IValidacionOFBloque0 {

    /**
     * Este servicio realiza las validaciones de Bloque 0
     * 
     * @param archivoPila
     *        DTO con la información del archivo recibido
     * @param contexto
     *        Mapa con las variables de trabajo para la validación
     * @return <b>RespuestaValidacionDTO</b>
     *         DTO que contiene la respuesta del validador
     * @throws ErrorFuncionalValidacionException
     *         Cuando no se cuenta con la información básica para registrar el nuevo índice de planilla
     */
    public RespuestaValidacionDTO validarBloqueCero(ArchivoPilaDTO archivoPila, Map<String, Object> contexto)
            throws ErrorFuncionalValidacionException;

}
