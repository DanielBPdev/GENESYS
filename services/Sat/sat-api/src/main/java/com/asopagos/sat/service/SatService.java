/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.sat.service;

import com.asopagos.sat.dto.AfiliacionEmpleadoresDTO;
import com.asopagos.sat.dto.AfiliacionEmpleadoresMismoDptoDTO;
import com.asopagos.sat.dto.DesafiliacionDTO;
import com.asopagos.sat.dto.EstadoPAAportesDTO;
import com.asopagos.sat.dto.InicioRelacionLaboralDTO;
import com.asopagos.sat.dto.PerdidaAfiliacionCausaGraveDTO;
import com.asopagos.sat.dto.RespuestaEstandar;
import com.asopagos.sat.dto.TerminacionRelacionLaboralDTO;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import javax.ws.rs.POST;
import com.asopagos.sat.dto.RespuestaNotificacionSatDTO;
import com.asopagos.sat.dto.NotificacionSatDTO;
import com.asopagos.sat.dto.AfiliacionEmpleadoresprimeraVezSatDTO;
import com.asopagos.rest.security.dto.UserDTO;
import javax.ws.rs.core.Context;
/**
 *
 * @author Sergio Reyes
 */
@Path("sat")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface SatService {
    
    @GET
    @Path("recolectar")
    public List<RespuestaEstandar> recolectarInformacion();
    
    @GET
    @Path("enviarSAT")
    public List<RespuestaEstandar> enviarSAT();
    
    @GET
    @Path("consultarAfiliacionEmpleadores")
    public List<AfiliacionEmpleadoresDTO> consultarAfiliacionEmpleadores(@QueryParam("estado") String estado,
            @QueryParam("numeroDocumentoEmpleador") String numeroDocumentoEmpleador, @QueryParam("fechaFin") Long fechaFin, @QueryParam("fechaInicio") Long fechaInicio);
    
    @GET
    @Path("consultarAfiliacionEmpleadoresAud")
    public List<AfiliacionEmpleadoresDTO> consultarAfiliacionEmpleadoresAuditoria(@QueryParam("id") Long id);
    
    @GET
    @Path("cambiarEstadoAuditoriaAfiliacionEmpleadores")
    public RespuestaEstandar cambiarEstadoAuditoriaAfiliacionEmpleadores(
            @QueryParam("id") Long id, @QueryParam("idAuditoria") Long idAuditoria, 
            @QueryParam("estado") String estado, 
            @QueryParam("observaciones") String observaciones);
    
    @GET
    @Path("consultarAfiliacionEmpleadoresMismoDpto")
    public List<AfiliacionEmpleadoresMismoDptoDTO> consultarAfiliacionEmpleadoresMismoDpto(@QueryParam("estado") String estado,
            @QueryParam("numeroDocumentoEmpleador") String numeroDocumentoEmpleador, @QueryParam("fechaFin") Long fechaFin, @QueryParam("fechaInicio") Long fechaInicio);
    
    @GET
    @Path("consultarAfiliacionEmpleadoresMismoDptoAud")
    public List<AfiliacionEmpleadoresMismoDptoDTO> consultarAfiliacionEmpleadoresMismoDptoAuditoria(@QueryParam("id") Long id);
    
    @GET
    @Path("cambiarEstadoAuditoriaAfiliacionEmpleadoresMismoDpto")
    public RespuestaEstandar cambiarEstadoAuditoriaAfiliacionEmpleadoresMismoDpto(
            @QueryParam("id") Long id, @QueryParam("idAuditoria") Long idAuditoria, 
            @QueryParam("estado") String estado, 
            @QueryParam("observaciones") String observaciones);
    
    @GET
    @Path("consultarDesafiliacion")
    public List<DesafiliacionDTO> consultarDesafiliacion(@QueryParam("estado") String estado,
            @QueryParam("numeroDocumentoEmpleador") String numeroDocumentoEmpleador, @QueryParam("fechaFin") Long fechaFin, @QueryParam("fechaInicio") Long fechaInicio);
    
    @GET
    @Path("consultarDesafiliacionAud")
    public List<DesafiliacionDTO> consultarDesafiliacionAuditoria(@QueryParam("id") Long id);
    
    @GET
    @Path("cambiarEstadoAuditoriaDesafiliacion")
    public RespuestaEstandar cambiarEstadoAuditoriaDesafiliacion(
            @QueryParam("id") Long id, @QueryParam("idAuditoria") Long idAuditoria, 
            @QueryParam("estado") String estado, 
            @QueryParam("observaciones") String observaciones);
    
    @GET
    @Path("consultarInicioRelacionLaboral")
    public List<InicioRelacionLaboralDTO> consultarInicioRelacionLaboral(@QueryParam("estado") String estado,
            @QueryParam("numeroDocumentoEmpleador") String numeroDocumentoEmpleador, 
            @QueryParam("fechaFin") Long fechaFin, @QueryParam("fechaInicio") Long fechaInicio,
            @QueryParam("numeroDocumentoTrabajador") String numeroDocumentoTrabajador);
    
    @GET
    @Path("consultarInicioRelacionLaboralAuditoria")
    public List<InicioRelacionLaboralDTO> consultarInicioRelacionLaboralAuditoria(@QueryParam("id") Long id);
    
    @GET
    @Path("cambiarEstadoAuditoriaInicioRelacionLaboral")
    public RespuestaEstandar cambiarEstadoAuditoriaInicioRelacionLaboral(
            @QueryParam("id") Long id, @QueryParam("idAuditoria") Long idAuditoria, 
            @QueryParam("estado") String estado, 
            @QueryParam("observaciones") String observaciones);
    
    @GET
    @Path("consultarTerminacionRelacionLaboral")
    public List<TerminacionRelacionLaboralDTO> consultarTerminacionRelacionLaboral(@QueryParam("estado") String estado,
            @QueryParam("numeroDocumentoEmpleador") String numeroDocumentoEmpleador, 
            @QueryParam("fechaFin") Long fechaFin, @QueryParam("fechaInicio") Long fechaInicio,
            @QueryParam("numeroDocumentoTrabajador") String numeroDocumentoTrabajador);
    
    @GET
    @Path("consultarTerminacionRelacionLaboralAuditoria")
    public List<TerminacionRelacionLaboralDTO> consultarTerminacionRelacionLaboralAuditoria(@QueryParam("id") Long id);
    
    @GET
    @Path("cambiarEstadoAuditoriaTerminacionRelacionLaboral")
    public RespuestaEstandar cambiarEstadoAuditoriaTerminacionRelacionLaboral(
            @QueryParam("id") Long id, @QueryParam("idAuditoria") Long idAuditoria, 
            @QueryParam("estado") String estado, 
            @QueryParam("observaciones") String observaciones);
    
    @GET
    @Path("consultarEstadoPAAportes")
    public List<EstadoPAAportesDTO> consultarEstadoPAAportes(@QueryParam("estado") String estado,
            @QueryParam("numeroDocumentoEmpleador") String numeroDocumentoEmpleador, @QueryParam("fechaFin") Long fechaFin, @QueryParam("fechaInicio") Long fechaInicio);
    
    @GET
    @Path("consultarEstadoPAAportesAuditoria")
    public List<EstadoPAAportesDTO> consultarEstadoPAAportesAuditoria(@QueryParam("id") Long id);
    
    @GET
    @Path("cambiarEstadoAuditoriaEstadoPAAportes")
    public RespuestaEstandar cambiarEstadoAuditoriaEstadoPAAportes(
            @QueryParam("id") Long id, @QueryParam("idAuditoria") Long idAuditoria, 
            @QueryParam("estado") String estado, 
            @QueryParam("observaciones") String observaciones);
    
    @GET
    @Path("consultarPerdidaAfiliacionCausaGrave")
    public List<PerdidaAfiliacionCausaGraveDTO> consultarPerdidaAfiliacionCausaGrave(
            @QueryParam("estado") String estado,@QueryParam("numeroDocumentoEmpleador") String numeroDocumentoEmpleador, 
            @QueryParam("fechaFin") Long fechaFin, @QueryParam("fechaInicio") Long fechaInicio);
    
    @GET
    @Path("consultarPerdidaAfiliacionCausaGraveAuditoria")
    public List<PerdidaAfiliacionCausaGraveDTO> consultarPerdidaAfiliacionCausaGraveAuditoria(@QueryParam("id") Long id);
    
    @GET
    @Path("cambiarEstadoAuditoriaPerdidaAfiliacionCausaGrave")
    public RespuestaEstandar cambiarEstadoAuditoriaPerdidaAfiliacionCausaGrave(
            @QueryParam("id") Long id, @QueryParam("idAuditoria") Long idAuditoria, 
            @QueryParam("estado") String estado, 
            @QueryParam("observaciones") String observaciones);

//GLPI 64552 - SAT - EPROCESS

    @POST
    @Path("recibirNotificacionSat")
    public RespuestaNotificacionSatDTO recibirNotificacionSat(NotificacionSatDTO notificacion);


    @POST
    @Path("consultarAfiliacionesSat")    
    public void consultarAfiliacionesSat(NotificacionSatDTO notificacion,@Context UserDTO userDTO);
    
    @GET
    @Path("consultarAfiliacionEmpleadoresPrimeraVez")
    public List<AfiliacionEmpleadoresprimeraVezSatDTO> consultarAfiliacionEmpleadoresPrimeraVez(@QueryParam("estado") String estado,
            @QueryParam("numeroDocumentoEmpleador") String numeroDocumentoEmpleador, @QueryParam("fechaFin") Long fechaFin, @QueryParam("fechaInicio") Long fechaInicio);

    @GET
    @Path("registrarMotivoRechazo")    
    public RespuestaEstandar registrarMotivoRechazo(@QueryParam("id") String id,@QueryParam("motivoRechazo") String motivoRechazo);
    
    @GET
    @Path("enviarASatIndividual")    
    public RespuestaEstandar enviarASatIndividual(@QueryParam("id") String id);
    
    @GET
    @Path("enviarASatMasivo")    
    public RespuestaEstandar enviarASatMasivo(@QueryParam("estado") String estado,
            @QueryParam("numeroDocumentoEmpleador") String numeroDocumentoEmpleador, @QueryParam("fechaFin") Long fechaFin, @QueryParam("fechaInicio") Long fechaInicio);

        @POST
        @Path("consultarSolicitudAfiliacionaSATPrimeravez")
        public void consultarSolicitudAfiliacionaSATPrimeravez(@Context UserDTO userDTO);
}
