/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.sat.business.interfaces;

import com.asopagos.afiliaciones.empleadores.composite.dto.ProcesoAfiliacionEmpleadoresPresencialDTO;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import com.asopagos.sat.dto.AfiliacionEmpleadoresprimeraVezSatDTO;
import com.asopagos.sat.dto.AfiliacionEmpleadoresDTO;
import com.asopagos.sat.dto.RespuestaNotificacionSatDTO;
import com.asopagos.sat.dto.NotificacionSatDTO;
import com.asopagos.sat.dto.BusquedaSAT;
import com.asopagos.sat.dto.RespuestaEstandar;
import javax.ejb.Local;
import java.util.List;
import java.util.Map;
import com.asopagos.rest.security.dto.UserDTO;
/**
 *
 * @author Sergio Reyes
 */
@Local
public interface IAfiliacionEmpleadores {
    
    public List<AfiliacionEmpleadoresDTO> consultarAfiliacionEmpleadores(
            BusquedaSAT busquedaSAT);
    
    public List<AfiliacionEmpleadoresDTO> consultarAfiliacionEmpleadoresAuditoria(
            Long id);
    
    public RespuestaEstandar cambiarEstadoAuditoriaAfiliacionEmpleadores(
            Long id, Long idAuditoria, String estado, String observaciones);

    // GLPI 64552 - SAT - EPROCESS

    public RespuestaNotificacionSatDTO recibirNotificacionSat(
            NotificacionSatDTO notificacion);

    public AfiliacionEmpleadoresprimeraVezSatDTO consultarAfiliacionesSat(
            String numeroTransaccion, String tokenGenerado);
    
    public String iniciarAfiliacionGenesys(
            ProcesoAfiliacionEmpleadoresPresencialDTO afiliacion,UserDTO userDTO);
    
    public ProcesoAfiliacionEmpleadoresPresencialDTO completarObjetoAfiliacion(
            ProcesoAfiliacionEmpleadoresPresencialDTO afiliacion);  
    
    public Map<String, Object> consultarTareaActivaGenesys(
            String idInstanciaProceso);

    public SolicitudAfiliacionEmpleador consultarSolicitudAfiliacionEmpleadorGenesys(
            String idSolicitud);

    public String radicarSolicitudEmpleadoresGenesys(
            Map<String, Object> informacionGeneral);
    
    public String asignarSolicitudAfiliacion(
            Map<String, Object> informacionGeneral, String idTarea, String numeroRadicado);
    
    public ProcesoAfiliacionEmpleadoresPresencialDTO mappingAfiliacionGenesys(
            AfiliacionEmpleadoresprimeraVezSatDTO afiliacionLocal);
    
    public String guardarDatosTemporalesGenesys(
            Map<String, Object> informacionGeneral);

    public String guardarInformacionSat(
            AfiliacionEmpleadoresprimeraVezSatDTO afiliacion);

   //public String generarTokenGenesys();
    
    public List<AfiliacionEmpleadoresprimeraVezSatDTO> consultarAfiliacionEmpleadoresPrimeraVez(
        BusquedaSAT busquedaSAT);
    
    public List<AfiliacionEmpleadoresprimeraVezSatDTO> consultarAfiliacionesEnvioMasivo(
        BusquedaSAT busquedaSAT);

    public RespuestaEstandar registrarMotivoRechazo(
        String id, String motivoRechazo);

    public RespuestaEstandar enviarASatIndividual(
        String id, String token);
    
    public String cambiarEstado(
            Map<String, Object>  informacionGeneralAfiliacion,String resultado);
    
    public String insertarAuditoria(
            Map<String, Object>  informacionGeneralAfiliacion);

     public void actualizarEstadoAfiliacion(NotificacionSatDTO notificacion);

    public  List<NotificacionSatDTO> consultarSolicitudAfiliacionaSATPrimeravez();
}
