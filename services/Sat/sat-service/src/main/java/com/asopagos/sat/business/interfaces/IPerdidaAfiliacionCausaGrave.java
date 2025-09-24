/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.sat.business.interfaces;

import com.asopagos.sat.dto.AfiliacionEmpleadoresDTO;
import com.asopagos.sat.dto.BusquedaSAT;
import com.asopagos.sat.dto.EstadoPAAportesDTO;
import com.asopagos.sat.dto.InicioRelacionLaboralDTO;
import com.asopagos.sat.dto.PerdidaAfiliacionCausaGraveDTO;
import com.asopagos.sat.dto.RespuestaEstandar;
import com.asopagos.sat.dto.TerminacionRelacionLaboralDTO;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sergio Reyes
 */
@Local
public interface IPerdidaAfiliacionCausaGrave {
    
    public List<PerdidaAfiliacionCausaGraveDTO> consultarPerdidaAfiliacionCausaGrave(
            BusquedaSAT busquedaSAT);
    
    public List<PerdidaAfiliacionCausaGraveDTO> consultarPerdidaAfiliacionCausaGraveAuditoria(
            Long id);
    
    public RespuestaEstandar cambiarEstadoAuditoriaPerdidaAfiliacionCausaGrave(
            Long id, Long idAuditoria, String estado, String observaciones);
}
