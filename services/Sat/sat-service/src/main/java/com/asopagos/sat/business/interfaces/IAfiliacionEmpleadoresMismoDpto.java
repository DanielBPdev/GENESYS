/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.sat.business.interfaces;

import com.asopagos.sat.business.ejb.AfiliacionEmpleadoresMismoDpto;
import com.asopagos.sat.dto.AfiliacionEmpleadoresDTO;
import com.asopagos.sat.dto.AfiliacionEmpleadoresMismoDptoDTO;
import com.asopagos.sat.dto.BusquedaSAT;
import com.asopagos.sat.dto.RespuestaEstandar;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sergio Reyes
 */
@Local
public interface IAfiliacionEmpleadoresMismoDpto {
    
    public List<AfiliacionEmpleadoresMismoDptoDTO> consultarAfiliacionEmpleadoresMismoDpto(
            BusquedaSAT busquedaSAT);
    
    public List<AfiliacionEmpleadoresMismoDptoDTO> consultarAfiliacionEmpleadoresMismoDptoAuditoria(
            Long id);
    
    public RespuestaEstandar cambiarEstadoAuditoriaAfiliacionEmpleadoresMismoDpto(
            Long id, Long idAuditoria, String estado, String observaciones);
}
