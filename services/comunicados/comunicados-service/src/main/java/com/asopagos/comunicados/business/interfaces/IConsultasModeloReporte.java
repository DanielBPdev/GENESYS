package com.asopagos.comunicados.business.interfaces;

import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@Local
public interface IConsultasModeloReporte {

    /**
     * Método que hace la consulta al histórico para obtener si estuvo alguna
     * vez activo como afiliado en la caja
     * 
     * @param idPersona
     *        Identificador de la persona
     * @param empleador
     *        Indica si es como empleador
     * @return booleano que indica si estuvo afiliado
     */
    public boolean estuvoAfiliadoEnCaja(Long idPersona, Boolean empleador);
    
//    /**
//     * Método que hace la consulta al historico para obtener si cuenta con un aporte vigente 
//     * 
//     * @param tipoIdentificacion
//     * @param numeroIdentificacion
//     * @param empleador
//     * @param anio
//     * @return booleano que indica si tuvo aporte vigente 
//     */
//    public boolean aporteRecibidoVigente(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
//            Boolean empleador, Short anio);
    
    /**
     * Método que hace la consulta al historico para verificar cúal es el estado de un dependiente 
     * 
     * @param idPersona
     * @param idEmpleador
     * @param estadoActualAfiliado
     * @param tipoAfiliado
     * @return
     */
    public List<Object[]> validarEstadoAfiliado(String idPersona, Long idEmpleador, String estadoActualAfiliado,
            TipoAfiliadoEnum tipoAfiliado);
    
    /**
     * Método que hace la consulta al historico para conecer las afiliaciones que tiene un trabajador respecto al empleador
     * 
     * @param tipoAfiliado
     * @param tipoIdAfiliado
     * @param numeroIdAfiliado
     * @param tipoIdEmpleador
     * @param numeroIdEmpleador
     * @return
     */
    public List<Object[]> consultarHistoricoAfiliaciones(TipoAfiliadoEnum tipoAfiliado,TipoIdentificacionEnum tipoIdAfiliado,String numeroIdAfiliado,
            TipoIdentificacionEnum tipoIdEmpleador,String numeroIdEmpleador);
    
    public List<Object[]> obtenerHistoricoAfiliacionesEmpleador(Long idPersonaEmpleador);
    
    public List<Object[]> obtenerHistoricoEstadosEmpleador(Long idPersona, TipoIdentificacionEnum tipoIdEmpleador, String numeroIdEmpleador, TipoSolicitanteMovimientoAporteEnum tipoAportante);
    
    public List<Object[]> obtenerEstadoActualEmpleador(Long idPersonaEmpleador);
    
    public void actualizarHistoricoEstadosEmpleador(List<Object[]> estado);
}
