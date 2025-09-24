/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.sat.service.ejb;

import com.asopagos.sat.business.interfaces.IAfiliacionEmpleadores;
import com.asopagos.sat.business.interfaces.IAfiliacionEmpleadoresMismoDpto;
import com.asopagos.sat.business.interfaces.IDesafiliacion;
import com.asopagos.sat.business.interfaces.IEjecutarProcedimientoRecoleccion;
import com.asopagos.sat.business.interfaces.IEnviarDatosSAT;
import com.asopagos.sat.business.interfaces.IEstadoPagoAportesEmpleadores;
import com.asopagos.sat.business.interfaces.IInicioRelacionLaboral;
import com.asopagos.sat.business.interfaces.IPerdidaAfiliacionCausaGrave;
import com.asopagos.sat.business.interfaces.ITerminacionRelacionLaboral;
import com.asopagos.sat.dto.AfiliacionEmpleadoresDTO;
import com.asopagos.sat.dto.AfiliacionEmpleadoresMismoDptoDTO;
import com.asopagos.sat.dto.BusquedaSAT;
import com.asopagos.sat.dto.DesafiliacionDTO;
import com.asopagos.sat.dto.EstadoPAAportesDTO;
import com.asopagos.sat.dto.InicioRelacionLaboralDTO;
import com.asopagos.sat.dto.PerdidaAfiliacionCausaGraveDTO;
import com.asopagos.sat.dto.RespuestaEstandar;
import com.asopagos.sat.dto.TerminacionRelacionLaboralDTO;
import com.asopagos.sat.service.SatService;
import com.asopagos.util.CalendarUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import com.asopagos.sat.dto.RespuestaNotificacionSatDTO;
import com.asopagos.sat.dto.NotificacionSatDTO;
import com.asopagos.sat.dto.AfiliacionEmpleadoresprimeraVezSatDTO;
import java.util.HashMap;
import java.util.Map;
import com.asopagos.afiliaciones.empleadores.composite.dto.AsignarSolicitudAfiliacionDTO;
import com.asopagos.afiliaciones.empleadores.composite.dto.ProcesoAfiliacionEmpleadoresPresencialDTO;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.sat.constantes.EnumParametros;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.sat.business.interfaces.IParametros;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.util.logging.Level;
import com.asopagos.rest.security.dto.UserDTO;

/**
 *
 * @author Sergio Reyes
 */
@Stateless
public class SatBusiness implements SatService{

    private static final ILogger logger = LogManager.getLogger(SatBusiness.class);

    @Inject
    private IEjecutarProcedimientoRecoleccion ejecutarProcedimientoRecoleccion;
    
    @Inject 
    private IEnviarDatosSAT enviarDatos;
    @Inject 
    private IAfiliacionEmpleadores afiliacionEmpleadores;
    
    @Inject 
    private IAfiliacionEmpleadoresMismoDpto afiliacionEmpleadoresMismoDpto;
    
    @Inject 
    private IDesafiliacion desafiliacion;
    
    @Inject 
    private IInicioRelacionLaboral inicioRelacionLaboral;
    
    @Inject 
    private ITerminacionRelacionLaboral terminacionRelacionLaboral;
    
    @Inject 
    private IEstadoPagoAportesEmpleadores estadoPagoAportesEmpleadores;
    
    @Inject 
    private IPerdidaAfiliacionCausaGrave perdidaAfiliacionCausaGrave;

    @Inject
    private IParametros parametros;
            
    
    @Override
    public List<RespuestaEstandar> recolectarInformacion() {
        List<RespuestaEstandar> respuesta = new ArrayList<RespuestaEstandar>();
        try{
            respuesta = ejecutarProcedimientoRecoleccion.ejecutarProcedimiento();
        }catch(Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }

    @Override
    public List<RespuestaEstandar> enviarSAT() {
        List<RespuestaEstandar> respuesta = new ArrayList<RespuestaEstandar>();
        try{
            respuesta = enviarDatos.enviarInformacionSAT();
        }catch(Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }

    @Override
    public void consultarSolicitudAfiliacionaSATPrimeravez(UserDTO userDTO) {
        logger.info("INICIA **_** consultarSolicitudAfiliacionaSATPrimeravez");
       try{
        List<NotificacionSatDTO> notificaciones = new ArrayList<>();
           notificaciones = afiliacionEmpleadores.consultarSolicitudAfiliacionaSATPrimeravez();
            logger.info("**__**afiliacionEmpleadores.consultarSolicitudAfiliacionaSATPrimeravez");
        for (NotificacionSatDTO dto : notificaciones) {
             logger.info("**__** Finaliza  consultarSolicitudAfiliacionaSATPrimeravez NotificacionSatDTO: "+dto.getNumero_transaccion());
            consultarAfiliacionesSat(dto,userDTO);
        }
        
        }catch(Exception e){
            e.printStackTrace();
        }
        logger.info("**__** Finaliza  consultarSolicitudAfiliacionaSATPrimeravez");
    }
    
    @Override
    public List<AfiliacionEmpleadoresDTO> consultarAfiliacionEmpleadores(String estado,
            String numeroDocumentoEmpleador, Long fechaFin, Long fechaInicio) {
       
        BusquedaSAT busquedaSAT = new BusquedaSAT();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
        Calendar calendar = new GregorianCalendar(1970,0,01);
        
        Date dateFechaInicio = fechaInicio != null ? CalendarUtils.truncarHora(new Date(fechaInicio)) : null;
        Date dateFechaFin = fechaFin != null ? CalendarUtils.truncarHora(new Date(fechaFin)) : null;
                
        busquedaSAT.setEstado(estado);
        busquedaSAT.setFechaFin(dateFechaFin != null ? sdf.format(dateFechaFin) : null);
        busquedaSAT.setFechaInicio(dateFechaInicio != null ? sdf.format(dateFechaInicio) : null);
        busquedaSAT.setNumeroDocumentoEmpleador(numeroDocumentoEmpleador);
        
        List<AfiliacionEmpleadoresDTO> respuesta = new ArrayList<AfiliacionEmpleadoresDTO>();
        try{
            respuesta = afiliacionEmpleadores.consultarAfiliacionEmpleadores(busquedaSAT);
        }catch(Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }
    
    @Override
    public List<AfiliacionEmpleadoresDTO> consultarAfiliacionEmpleadoresAuditoria(Long id) {
       
        List<AfiliacionEmpleadoresDTO> respuesta = new ArrayList<AfiliacionEmpleadoresDTO>();
        try{
            respuesta = afiliacionEmpleadores.consultarAfiliacionEmpleadoresAuditoria(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }
    
    @Override
    public RespuestaEstandar cambiarEstadoAuditoriaAfiliacionEmpleadores(
            Long id,Long idAuditoria, String estado, String observaciones){
        
        RespuestaEstandar respuesta = new RespuestaEstandar();
        try{
            respuesta = afiliacionEmpleadores.
                    cambiarEstadoAuditoriaAfiliacionEmpleadores(id, idAuditoria, 
                            estado, observaciones);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return respuesta;
    }
    
    @Override
    public List<AfiliacionEmpleadoresMismoDptoDTO> consultarAfiliacionEmpleadoresMismoDpto(String estado,
            String numeroDocumentoEmpleador, Long fechaFin, Long fechaInicio) {
       
        BusquedaSAT busquedaSAT = new BusquedaSAT();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
        
        Date dateFechaInicio = fechaInicio != null ? CalendarUtils.truncarHora(new Date(fechaInicio)) : null;
        Date dateFechaFin = fechaFin != null ? CalendarUtils.truncarHora(new Date(fechaFin)) : null;
                
        busquedaSAT.setEstado(estado);
        busquedaSAT.setFechaFin(dateFechaFin != null ? sdf.format(dateFechaFin) : null);
        busquedaSAT.setFechaInicio(dateFechaInicio != null ? sdf.format(dateFechaInicio) : null);
        busquedaSAT.setNumeroDocumentoEmpleador(numeroDocumentoEmpleador);
        
        List<AfiliacionEmpleadoresMismoDptoDTO> respuesta = new ArrayList<AfiliacionEmpleadoresMismoDptoDTO>();
        try{
            respuesta = afiliacionEmpleadoresMismoDpto.consultarAfiliacionEmpleadoresMismoDpto(busquedaSAT);
        }catch(Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }
    
    @Override
    public List<AfiliacionEmpleadoresMismoDptoDTO> consultarAfiliacionEmpleadoresMismoDptoAuditoria(Long id) {
       
        List<AfiliacionEmpleadoresMismoDptoDTO> respuesta = new ArrayList<AfiliacionEmpleadoresMismoDptoDTO>();
        try{
            respuesta = afiliacionEmpleadoresMismoDpto.consultarAfiliacionEmpleadoresMismoDptoAuditoria(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }
    
    @Override
    public RespuestaEstandar cambiarEstadoAuditoriaAfiliacionEmpleadoresMismoDpto(
            Long id,Long idAuditoria, String estado, String observaciones){
        
        RespuestaEstandar respuesta = new RespuestaEstandar();
        try{
            respuesta = afiliacionEmpleadoresMismoDpto.
                    cambiarEstadoAuditoriaAfiliacionEmpleadoresMismoDpto(id, idAuditoria, 
                            estado, observaciones);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return respuesta;
    }
    
    @Override
    public List<DesafiliacionDTO> consultarDesafiliacion(String estado,
            String numeroDocumentoEmpleador, Long fechaFin, Long fechaInicio) {
       
        BusquedaSAT busquedaSAT = new BusquedaSAT();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
        
        Date dateFechaInicio = fechaInicio != null ? CalendarUtils.truncarHora(new Date(fechaInicio)) : null;
        Date dateFechaFin = fechaFin != null ? CalendarUtils.truncarHora(new Date(fechaFin)) : null;
                
        busquedaSAT.setEstado(estado);
        busquedaSAT.setFechaFin(dateFechaFin != null ? sdf.format(dateFechaFin) : null);
        busquedaSAT.setFechaInicio(dateFechaInicio != null ? sdf.format(dateFechaInicio) : null);
        busquedaSAT.setNumeroDocumentoEmpleador(numeroDocumentoEmpleador);
        
        List<DesafiliacionDTO> respuesta = new ArrayList<DesafiliacionDTO>();
        try{
            respuesta = desafiliacion.consultarDesafiliacion(busquedaSAT);
        }catch(Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }
    
    @Override
    public List<DesafiliacionDTO> consultarDesafiliacionAuditoria(Long id) {
       
        List<DesafiliacionDTO> respuesta = new ArrayList<DesafiliacionDTO>();
        try{
            respuesta = desafiliacion.consultarDesafiliacionAuditoria(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }
    
    @Override
    public RespuestaEstandar cambiarEstadoAuditoriaDesafiliacion(
            Long id,Long idAuditoria, String estado, String observaciones){
        
        RespuestaEstandar respuesta = new RespuestaEstandar();
        try{
            respuesta = desafiliacion.
                    cambiarEstadoAuditoriaDesafiliacion(id, idAuditoria, 
                            estado, observaciones);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return respuesta;
    }

    @Override
    public List<InicioRelacionLaboralDTO> consultarInicioRelacionLaboral(String estado, 
            String numeroDocumentoEmpleador, Long fechaFin, Long fechaInicio, 
            String numeroDocumentoTrabajador) {
        
        BusquedaSAT busquedaSAT = new BusquedaSAT();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
        Calendar calendar = new GregorianCalendar(1970,0,01);
        
        Date dateFechaInicio = fechaInicio != null ? CalendarUtils.truncarHora(new Date(fechaInicio)) : null;
        Date dateFechaFin = fechaFin != null ? CalendarUtils.truncarHora(new Date(fechaFin)) : null;
        busquedaSAT.setNumeroDocumentoTrabajador(numeroDocumentoTrabajador != null ? numeroDocumentoTrabajador : null);    
        busquedaSAT.setEstado(estado);
        busquedaSAT.setFechaFin(dateFechaFin != null ? sdf.format(dateFechaFin) : null);
        busquedaSAT.setFechaInicio(dateFechaInicio != null ? sdf.format(dateFechaInicio) : null);
        busquedaSAT.setNumeroDocumentoEmpleador(numeroDocumentoEmpleador);
        
        List<InicioRelacionLaboralDTO> respuesta = new ArrayList<InicioRelacionLaboralDTO>();
        try{
            respuesta = inicioRelacionLaboral.consultarInicioRelacionLaboral(busquedaSAT); 
        }catch(Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }

    @Override
    public List<InicioRelacionLaboralDTO> consultarInicioRelacionLaboralAuditoria(Long id) {
        
        List<InicioRelacionLaboralDTO> respuesta = new ArrayList<InicioRelacionLaboralDTO>();
        try{
            respuesta = inicioRelacionLaboral.consultarInicioRelacionLaboralAuditoria(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }

    @Override
    public RespuestaEstandar cambiarEstadoAuditoriaInicioRelacionLaboral(Long id, Long idAuditoria, String estado, String observaciones) {
        
        RespuestaEstandar respuesta = new RespuestaEstandar();
        try{
            respuesta = inicioRelacionLaboral.cambiarEstadoAuditoriaInicioRelacionLaboral(
                    id, idAuditoria, estado, observaciones);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return respuesta;
    }

    @Override
    public List<TerminacionRelacionLaboralDTO> consultarTerminacionRelacionLaboral(
            String estado, String numeroDocumentoEmpleador, Long fechaFin, 
            Long fechaInicio, String numeroDocumentoTrabajador) {
        
        BusquedaSAT busquedaSAT = new BusquedaSAT();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
        Calendar calendar = new GregorianCalendar(1970,0,01);
        
        Date dateFechaInicio = fechaInicio != null ? CalendarUtils.truncarHora(new Date(fechaInicio)) : null;
        Date dateFechaFin = fechaFin != null ? CalendarUtils.truncarHora(new Date(fechaFin)) : null;
                
        busquedaSAT.setEstado(estado);
        busquedaSAT.setFechaFin(dateFechaFin != null ? sdf.format(dateFechaFin) : null);
        busquedaSAT.setFechaInicio(dateFechaInicio != null ? sdf.format(dateFechaInicio) : null);
        busquedaSAT.setNumeroDocumentoEmpleador(numeroDocumentoEmpleador);
        busquedaSAT.setNumeroDocumentoTrabajador(numeroDocumentoTrabajador);
        
        List<TerminacionRelacionLaboralDTO> respuesta = new ArrayList<TerminacionRelacionLaboralDTO>();
        try{
            respuesta = terminacionRelacionLaboral.consultarTerminacionRelacionLaboral(busquedaSAT);
        }catch(Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }

    @Override
    public List<TerminacionRelacionLaboralDTO> consultarTerminacionRelacionLaboralAuditoria(Long id) {
        
        List<TerminacionRelacionLaboralDTO> respuesta = new ArrayList<TerminacionRelacionLaboralDTO>();
        try{
            respuesta = terminacionRelacionLaboral.consultarTerminacionRelacionLaboralAuditoria(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }

    @Override
    public RespuestaEstandar cambiarEstadoAuditoriaTerminacionRelacionLaboral(Long id, Long idAuditoria, String estado, String observaciones) {
        
        RespuestaEstandar respuesta = new RespuestaEstandar();
        try{
            respuesta = terminacionRelacionLaboral.cambiarEstadoAuditoriaTerminacionRelacionLaboral(
                    id, idAuditoria, estado, observaciones);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return respuesta;
    }

    @Override
    public List<EstadoPAAportesDTO> consultarEstadoPAAportes(String estado, String numeroDocumentoEmpleador, Long fechaFin, Long fechaInicio) {
        
        BusquedaSAT busquedaSAT = new BusquedaSAT();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
        Calendar calendar = new GregorianCalendar(1970,0,01);
        
        Date dateFechaInicio = fechaInicio != null ? CalendarUtils.truncarHora(new Date(fechaInicio)) : null;
        Date dateFechaFin = fechaFin != null ? CalendarUtils.truncarHora(new Date(fechaFin)) : null;
                
        busquedaSAT.setEstado(estado);
        busquedaSAT.setFechaFin(dateFechaFin != null ? sdf.format(dateFechaFin) : null);
        busquedaSAT.setFechaInicio(dateFechaInicio != null ? sdf.format(dateFechaInicio) : null);
        busquedaSAT.setNumeroDocumentoEmpleador(numeroDocumentoEmpleador);
        
        List<EstadoPAAportesDTO> respuesta = new ArrayList<EstadoPAAportesDTO>();
        try{
            respuesta = estadoPagoAportesEmpleadores.consultarEstadoPagoAportes(busquedaSAT);
        }catch(Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }

    @Override
    public List<EstadoPAAportesDTO> consultarEstadoPAAportesAuditoria(Long id) {
        
        List<EstadoPAAportesDTO> respuesta = new ArrayList<EstadoPAAportesDTO>();
        try{
            respuesta = estadoPagoAportesEmpleadores.consultarEstadoPagoAportesAuditoria(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }

    @Override
    public RespuestaEstandar cambiarEstadoAuditoriaEstadoPAAportes(Long id, Long idAuditoria, String estado, String observaciones) {
        
        RespuestaEstandar respuesta = new RespuestaEstandar();
        try{
            respuesta = estadoPagoAportesEmpleadores.cambiarEstadoAuditoriaEstadoPagoAportes(
                    id, idAuditoria, estado, observaciones);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return respuesta;
    }

    @Override
    public List<PerdidaAfiliacionCausaGraveDTO> consultarPerdidaAfiliacionCausaGrave(String estado, String numeroDocumentoEmpleador, Long fechaFin, Long fechaInicio) {
        
        BusquedaSAT busquedaSAT = new BusquedaSAT();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
        Calendar calendar = new GregorianCalendar(1970,0,01);
        
        Date dateFechaInicio = fechaInicio != null ? CalendarUtils.truncarHora(new Date(fechaInicio)) : null;
        Date dateFechaFin = fechaFin != null ? CalendarUtils.truncarHora(new Date(fechaFin)) : null;
                
        busquedaSAT.setEstado(estado);
        busquedaSAT.setFechaFin(dateFechaFin != null ? sdf.format(dateFechaFin) : null);
        busquedaSAT.setFechaInicio(dateFechaInicio != null ? sdf.format(dateFechaInicio) : null);
        busquedaSAT.setNumeroDocumentoEmpleador(numeroDocumentoEmpleador);
        
        List<PerdidaAfiliacionCausaGraveDTO> respuesta = new ArrayList<PerdidaAfiliacionCausaGraveDTO>();
        try{
            respuesta = perdidaAfiliacionCausaGrave.consultarPerdidaAfiliacionCausaGrave(busquedaSAT);
        }catch(Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }

    @Override
    public List<PerdidaAfiliacionCausaGraveDTO> consultarPerdidaAfiliacionCausaGraveAuditoria(Long id) {
        
        List<PerdidaAfiliacionCausaGraveDTO> respuesta = new ArrayList<PerdidaAfiliacionCausaGraveDTO>();
        try{
            respuesta = perdidaAfiliacionCausaGrave.consultarPerdidaAfiliacionCausaGraveAuditoria(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }

    @Override
    public RespuestaEstandar cambiarEstadoAuditoriaPerdidaAfiliacionCausaGrave(Long id, Long idAuditoria, String estado, String observaciones) {
        
        RespuestaEstandar respuesta = new RespuestaEstandar();
        try{
            respuesta = perdidaAfiliacionCausaGrave.cambiarEstadoAuditoriaPerdidaAfiliacionCausaGrave(
                    id, idAuditoria, estado, observaciones);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return respuesta;
    }


    // GLPI 64552 - SAT - EPROCESS

    public RespuestaEstandar enviarASatIndividual(String id){
        
        RespuestaEstandar respuesta = new RespuestaEstandar();
         String tokenGeneradoMinisterioSat = "";
        Map<String,String> param = parametros.parametrosSat_WS_F2();
        try{
              // Se genera un Token de SAT-GENESYS para la comunicación con el ministerio
            logger.info("INICIA PASO 1");
                tokenGeneradoMinisterioSat = enviarDatos.getTokenSatToGenesys(param,EnumParametros.CLIENTID_SAT_RES_AFI_PRIMER_VEZ_WS_F2.getNombre());
            logger.info("FINALIZA PASO 1");
            respuesta = afiliacionEmpleadores.enviarASatIndividual(id,tokenGeneradoMinisterioSat);

        }catch(Exception e){
            e.printStackTrace();
        }
        return respuesta;

    }

    @Override
    public RespuestaEstandar enviarASatMasivo(String estado,String numeroDocumentoEmpleador, Long fechaFin, Long fechaInicio) {
       
        RespuestaEstandar respuesta = new RespuestaEstandar();
        List<String> mensajesGeneral = new ArrayList<>();
        
        BusquedaSAT busquedaSAT = new BusquedaSAT();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Calendar calendar = new GregorianCalendar(1970,0,01);
                
        Date dateFechaInicio = fechaInicio != null ? CalendarUtils.truncarHora(new Date(fechaInicio)) : null;
        Date dateFechaFin = fechaFin != null ? CalendarUtils.truncarHora(new Date(fechaFin)) : null;        
        
        busquedaSAT.setEstado(estado);
        
        busquedaSAT.setFechaFin(dateFechaFin != null ? sdf.format(dateFechaFin) : null);
        busquedaSAT.setFechaInicio(dateFechaInicio != null ? sdf.format(dateFechaInicio) : null); 
        
        busquedaSAT.setNumeroDocumentoEmpleador(numeroDocumentoEmpleador);
        
        List<AfiliacionEmpleadoresprimeraVezSatDTO> listadoParaEnvio = new ArrayList<AfiliacionEmpleadoresprimeraVezSatDTO>();
        try{
            listadoParaEnvio = afiliacionEmpleadores.consultarAfiliacionesEnvioMasivo(busquedaSAT);
        }catch(Exception e){
            e.printStackTrace();
        }
        for (int i = 0; i < listadoParaEnvio.size(); i++) {
                AfiliacionEmpleadoresprimeraVezSatDTO objetoLocal = listadoParaEnvio.get(i);
                RespuestaEstandar respuestaLocal = enviarASatIndividual(String.valueOf(objetoLocal.getId()));
                String mensaje =  respuestaLocal.getMensajes().get(i);
                mensajesGeneral.add(mensaje);
            }
        
        respuesta.setMensajes(mensajesGeneral);
        
        return respuesta;
    }

    @Override
    public RespuestaEstandar registrarMotivoRechazo(String id, String motivoRechazo){

        RespuestaEstandar respuesta = afiliacionEmpleadores.registrarMotivoRechazo(id,motivoRechazo);

        return respuesta;
    }

    @Override
    public RespuestaNotificacionSatDTO recibirNotificacionSat(NotificacionSatDTO notificacion) {
        
        RespuestaNotificacionSatDTO respuesta = new RespuestaNotificacionSatDTO();
        try{
            
            respuesta = afiliacionEmpleadores.recibirNotificacionSat(notificacion);

        }catch(Exception e){
            e.printStackTrace();
        }
        
        return respuesta;
    }

    @Override
    public List<AfiliacionEmpleadoresprimeraVezSatDTO> consultarAfiliacionEmpleadoresPrimeraVez(String estado,
            String numeroDocumentoEmpleador, Long fechaFin, Long fechaInicio) {
       
        BusquedaSAT busquedaSAT = new BusquedaSAT();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Calendar calendar = new GregorianCalendar(1970,0,01);

        Date dateFechaInicio = fechaInicio != null ? CalendarUtils.truncarHora(new Date(fechaInicio)) : null;
        Date dateFechaFin = fechaFin != null ? CalendarUtils.truncarHora(new Date(fechaFin)) : null;        
        
        busquedaSAT.setEstado(estado);
        busquedaSAT.setFechaFin(dateFechaFin != null ? sdf.format(dateFechaFin) : null);
        busquedaSAT.setFechaInicio(dateFechaInicio != null ? sdf.format(dateFechaInicio) : null);    
        busquedaSAT.setNumeroDocumentoEmpleador(numeroDocumentoEmpleador);
        
        List<AfiliacionEmpleadoresprimeraVezSatDTO> respuesta = new ArrayList<AfiliacionEmpleadoresprimeraVezSatDTO>();
        try{
            respuesta = afiliacionEmpleadores.consultarAfiliacionEmpleadoresPrimeraVez(busquedaSAT);
        }catch(Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }

    
@Override
    public void consultarAfiliacionesSat(NotificacionSatDTO notificacion,UserDTO userDTO) {
        try{
            
            Map<String,String> param = parametros.parametrosSat_WS_F2();
            String tokenGeneradoMinisterioSat = "";
            AfiliacionEmpleadoresprimeraVezSatDTO afiliacion = new AfiliacionEmpleadoresprimeraVezSatDTO();
            String idInstanciaProceso = "";
            Map<String, Object> tareaActiva = new HashMap<>();
            SolicitudAfiliacionEmpleador solicitud = new SolicitudAfiliacionEmpleador();
            String resultadoDatosTemporales = "";
            Map<String, Object> informacionGeneralAfiliacion = new HashMap<>();
            String resultado = "";
            AsignarSolicitudAfiliacionDTO asignarSolicitud = new AsignarSolicitudAfiliacionDTO();
            String resultadoAfiliacion = "";
            String resultadoAuditoria = "";
            String idWSAfiliacion = "";

            
            // Se genera un Token de SAT-GENESYS para la comunicación con el ministerio
            logger.info("INICIA PASO 1");
                tokenGeneradoMinisterioSat = enviarDatos.getTokenSatToGenesys(param,EnumParametros.CLIENT_ID_NO_AFIL_PRIM_SAT_WS_F2.getNombre());
            logger.info("FINALIZA PASO 1");

            // Se consulta la información  servicio SAT/MINISTERIO por número de transacción
            // Se obtienen los datos especificos de la afiliación 

            logger.info("INICIA PASO 2");
                afiliacion = afiliacionEmpleadores.consultarAfiliacionesSat(notificacion.getNumero_transaccion(),tokenGeneradoMinisterioSat);
            logger.info("FINALIZA PASO 2");

            // Se realiza la insercción de datos en la base de datos del esquema SAT
            logger.info("INICIA PASO 3");    
                idWSAfiliacion = afiliacionEmpleadores.guardarInformacionSat(afiliacion);
            logger.info("FINALIZA PASO 3");

            // ****************************************************
            // Se realiza la afiliación de un empleador en Genesys
            // ****************************************************
                logger.info("INICIA PASO 4");
                ProcesoAfiliacionEmpleadoresPresencialDTO afiliacionObjeto = afiliacionEmpleadores.mappingAfiliacionGenesys(afiliacion);
                logger.info("FINALIZA PASO 4");

            // Paso 1 - Iniciar Afiliación OK
                //printObjectToJson(afiliacionObjeto);
                logger.info("INICIA PASO 5: ");
                idInstanciaProceso = afiliacionEmpleadores.iniciarAfiliacionGenesys(afiliacionObjeto, userDTO); 
                logger.info("FINALIZA PASO 5");            
            // Completar Objeto Afiliación

                logger.info("INICIA PASO 6");
                afiliacionObjeto = afiliacionEmpleadores.completarObjetoAfiliacion(afiliacionObjeto);
                logger.info("FINALIZA PASO 6");
            // Paso 2 - Obtener Tarea Activa OK
                
                logger.info("INICIA PASO 7");
                tareaActiva = afiliacionEmpleadores.consultarTareaActivaGenesys(idInstanciaProceso);
                logger.info("FINALIZA PASO 7");
                
            // Paso 3 - Solicitud Afiliacion Empleador OK
                logger.info("INICIA PASO 8 - idInstanciaProceso: " + idInstanciaProceso);
                //printObjectToJson(tareaActiva);
                solicitud = afiliacionEmpleadores.consultarSolicitudAfiliacionEmpleadorGenesys(String.valueOf(tareaActiva.get("idSolicitud")));
                logger.info("FINALIZA PASO 8");
                
            // Se almacena la afiliación    
                logger.info("INICIA PASO 9");
                informacionGeneralAfiliacion = afiliacionToMap(afiliacion,solicitud,tareaActiva,idInstanciaProceso);                                
                //printObjectToJson(informacionGeneralAfiliacion);
                logger.info("FINALIZA PASO 9");

            // Se completan datos con CONSTANTES y ANEXO 2
                logger.info("INICIA PASO 10");
                informacionGeneralAfiliacion = completarDatosAfiliacion(informacionGeneralAfiliacion);
                //printObjectToJson(informacionGeneralAfiliacion);
                logger.info("FINALIZA PASO 10");

            // Paso 4 - Datos Temporales - OK
                logger.info("INICIA PASO 11");
                //printObjectToJson(informacionGeneralAfiliacion);
                resultadoDatosTemporales = afiliacionEmpleadores.guardarDatosTemporalesGenesys(informacionGeneralAfiliacion);
                //printObjectToJson(resultadoDatosTemporales);
                logger.info("FINALIZA PASO 11");
    
            // Paso 5 - Construir objeto de radiacion OK
                logger.info("INICIA PASO 12");
                resultado = afiliacionEmpleadores.radicarSolicitudEmpleadoresGenesys(informacionGeneralAfiliacion);
                logger.info("FINALIZA PASO 12");

            // Paso 6 - Consulta de ultimo estado de radicado
                logger.info("INICIA PASO 13");
                tareaActiva = afiliacionEmpleadores.consultarTareaActivaGenesys(idInstanciaProceso);
                logger.info("FINALIZA PASO 13");

            // Paso 8 - Consulta de ultimo estado de radicado
                logger.info("INICIA PASO 15");
                tareaActiva = afiliacionEmpleadores.consultarTareaActivaGenesys(idInstanciaProceso);
                logger.info("FINALIZA PASO 15");
            //Paso 9 - Guardar Información de auditoría:

                logger.info("INICIA PASO 16");
                informacionGeneralAfiliacion.put("numeroRadicado", String.valueOf(tareaActiva.get("numeroRadicado")));
                informacionGeneralAfiliacion.put("ultimaTareaActiva", String.valueOf(tareaActiva.get("idTarea")));
                informacionGeneralAfiliacion.put("idWSAfiliacion", String.valueOf(idWSAfiliacion));

                logger.info(String.valueOf(tareaActiva.get("numeroRadicado")));
                logger.info(String.valueOf(tareaActiva.get("idTarea")));
                logger.info(String.valueOf(idWSAfiliacion));
                
                logger.info("FINALIZA PASO 16");
            
            // Paso 10 - Guardar Auditoria
                logger.info("INICIA PASO 17");
                resultadoAuditoria = afiliacionEmpleadores.insertarAuditoria(informacionGeneralAfiliacion);
                logger.info("FINALIZA PASO 17");
                
            // Paso 11 - Cambiar estado a "ASIGNADO AL BACK"
                logger.info("INICIA PASO 18");
                resultado = afiliacionEmpleadores.cambiarEstado(informacionGeneralAfiliacion,"ASIGNADO AL BACK");    
                logger.info("FINALIZA PASO 18");     

            // Paso 7 - Asignar solicitud afiliacion 
                logger.info("INICIA PASO 14");
               resultadoAfiliacion = afiliacionEmpleadores.asignarSolicitudAfiliacion(informacionGeneralAfiliacion,String.valueOf(tareaActiva.get("idTarea")),String.valueOf(tareaActiva.get("numeroRadicado")));
                logger.info("FINALIZA PASO 14");
                 logger.info("PASO 15 ACTUALIZA EN TABLA DE CONTROL A AFILIADO ");
                afiliacionEmpleadores.actualizarEstadoAfiliacion(notificacion);
                logger.info("finaliza PASO 15 ACTUALIZA EN TABLA DE CONTROL A AFILIADO ");                       

        }catch(Exception e){
            e.printStackTrace();
        }

    }    

    public Map<String, Object> afiliacionToMap(AfiliacionEmpleadoresprimeraVezSatDTO afiliacion, SolicitudAfiliacionEmpleador solicitud, Map<String, Object> tareaActiva, String idInstanciaProceso) {
        
        Map<String, Object> informacionGeneralAfiliacion = new HashMap<>();        
                
        informacionGeneralAfiliacion.put("idTarea", tareaActiva.get("idTarea"));
        informacionGeneralAfiliacion.put("idInstanciaProceso", idInstanciaProceso);
        informacionGeneralAfiliacion.put("idSolicitudAfiliacionEmpleador", solicitud.getIdSolicitudAfiliacionEmpleador());
        informacionGeneralAfiliacion.put("idEmpleador", solicitud.getIdEmpleador());
        informacionGeneralAfiliacion.put("idSolicitud", solicitud.getSolicitudGlobal().getIdSolicitud());        
        informacionGeneralAfiliacion.put("id", afiliacion.getId());
        informacionGeneralAfiliacion.put("radicado", afiliacion.getRadicado());
        informacionGeneralAfiliacion.put("numeroTransaccion", afiliacion.getNumeroTransaccion());
        informacionGeneralAfiliacion.put("tipoPersona", afiliacion.getTipoPersona());
        informacionGeneralAfiliacion.put("naturalezaJuridica", afiliacion.getNaturalezaJuridica());
        informacionGeneralAfiliacion.put("tipoDocumentoEmpleador", afiliacion.getTipoDocumentoEmpleador());
        informacionGeneralAfiliacion.put("numeroDocumentoEmpleador", afiliacion.getNumeroDocumentoEmpleador());
        informacionGeneralAfiliacion.put("serialSAT", afiliacion.getSerialSAT());
        informacionGeneralAfiliacion.put("primerNombreEmpleador", afiliacion.getPrimerNombreEmpleador());
        informacionGeneralAfiliacion.put("segundoNombreEmpleador", afiliacion.getSegundoNombreEmpleador());
        informacionGeneralAfiliacion.put("primerApellidoEmpleador", afiliacion.getPrimerApellidoEmpleador());
        informacionGeneralAfiliacion.put("segundoApellidoEmpleador", afiliacion.getSegundoApellidoEmpleador());
        informacionGeneralAfiliacion.put("fechaSolicitud", afiliacion.getFechaSolicitud());
        informacionGeneralAfiliacion.put("perdidaAfiliacionCausaGrave", afiliacion.getPerdidaAfiliacionCausaGrave());
        informacionGeneralAfiliacion.put("fechaEfectividad", afiliacion.getFechaEfectividad());
        informacionGeneralAfiliacion.put("NombreRazonSocial", afiliacion.getRazonSocialEmpleador());
        informacionGeneralAfiliacion.put("numeroMatriculaMercantil", afiliacion.getNumeroMatriculaMercantil());
        informacionGeneralAfiliacion.put("departamentoCausaSalarios", afiliacion.getDepartamentoCausaSalarios());
        informacionGeneralAfiliacion.put("municipioContacto", afiliacion.getMunicipioContacto());
        informacionGeneralAfiliacion.put("direccionContacto", afiliacion.getDireccionContacto());
        informacionGeneralAfiliacion.put("correoElectronicoContacto", afiliacion.getCorreoElectronicoContacto());
        informacionGeneralAfiliacion.put("tipoDocumentoRepresentanteLegal", afiliacion.getTipoDocumentoRepresentanteLegal());
        informacionGeneralAfiliacion.put("numeroDocumentoRepresentanteLegal", afiliacion.getNumeroDocumentoRepresentanteLegal());
        informacionGeneralAfiliacion.put("primerNombreRepresentanteLegal", afiliacion.getPrimerNombreRepresentanteLegal());
        informacionGeneralAfiliacion.put("segundoNombreRepresentanteLegal", afiliacion.getSegundoNombreRepresentanteLegal());
        informacionGeneralAfiliacion.put("primerApellidoRepresentanteLegal", afiliacion.getPrimerApellidoRepresentanteLegal());
        informacionGeneralAfiliacion.put("segundoApellidoRepresentanteLegal", afiliacion.getSegundoApellidoRepresentanteLegal());        
        informacionGeneralAfiliacion.put("autorizacionDatosPersonales", afiliacion.getAutorizacionDatosPersonales());
        informacionGeneralAfiliacion.put("autorizacionEnvioNotificaciones", afiliacion.getAutorizacionEnvioNotificaciones());
        informacionGeneralAfiliacion.put("manifestacionNoAfiliadoCaja", afiliacion.getManifestacionNoAfiliadoCaja());
        informacionGeneralAfiliacion.put("estado", afiliacion.getEstado());
        informacionGeneralAfiliacion.put("estadoAuditoria", afiliacion.getEstadoAuditoria());
        informacionGeneralAfiliacion.put("mensajeAuditoria", afiliacion.getMensajeAuditoria());
        informacionGeneralAfiliacion.put("fechaAuditoria", afiliacion.getFechaAuditoria());
        informacionGeneralAfiliacion.put("idAuditoria", afiliacion.getIdAuditoria());
        informacionGeneralAfiliacion.put("observacionesAuditoria", afiliacion.getObservacionesAuditoria());
        informacionGeneralAfiliacion.put("telefonoContacto", afiliacion.getTelefonoMunicipioCausaSalarios());
        // informacionGeneralAfiliacion.put("codigoCajaAnterior", afiliacion.getCodigoCajaAnterior());
        //informacionGeneralAfiliacion.put("pazYSalvo", afiliacion.getPazYSalvo());
        //informacionGeneralAfiliacion.put("fechaPazYSalvo", afiliacion.getFechaPazYSalvo());

        return informacionGeneralAfiliacion;
    }

    public Map<String, Object> completarDatosAfiliacion(Map<String, Object> informacionGeneral) {

        //Deberá siempre tomar la fecha de registro en SAT 
        informacionGeneral.put("fechaRecepcionDocumentos", "");

        //Deberá siempre tomar la fecha de registro en SAT, pero debe ser editable para que se pueda modificar en back
        informacionGeneral.put("fechaConstitucion", "");

        //Vacio
        informacionGeneral.put("ARL", "");

        //Este campo deberá estar vacio y editable para que lo puedan diligenciar en Back
        informacionGeneral.put("codigoCIIU", "");

        //Este campo deberá estar vacio y se deberá diligenciar automaticamente en back cuando coloquen el código CIIU
        informacionGeneral.put("descripcionCIIU", "");

        //Vacio- editable en back (debe ser un campo editable con el fin de que sea diligenciado con la información real del empleador)
        informacionGeneral.put("totalTrabajadores", "");

        //Vacio- editable en back 
        informacionGeneral.put("totalUltimaNominaDepto", "");

        //Vacio- editable en back 
        informacionGeneral.put("periodoUltimaNominaDepto", "");

        //Por defecto deberá traer “Ninguna”
        informacionGeneral.put("nombreCajaCompensacion", "Ninguna");

        //Vacio
        informacionGeneral.put("URLPaginaWeb", "");

        //Vacio
        informacionGeneral.put("codigoPostal", "");

        //“SI” por defecto
        informacionGeneral.put("autorizacionEnvioCorreoElectronico", "1");

        //“SI” por defecto
        informacionGeneral.put("datosEnvioCorrespondencia", "1");

        //“SI” por defecto
        informacionGeneral.put("direccionNotificacionJudicial", "1");

        //Vacio
        informacionGeneral.put("datosRepresentanteLegalSuplente", "");

        //"Efectivo" por defecto
        informacionGeneral.put("medioPagoSubsidioMonetario", "Efectivo");

        //“SI” por defecto
        informacionGeneral.put("datosSucursal", "1");

        //“SI” por defecto
        informacionGeneral.put("responsableAfiliacion", "1");

        //“SI” por defecto
        informacionGeneral.put("responsableAportes", "1");

        //Para estos campos deberá tomar aleatoriamente los responsables con los usuarios que encuentre creados en la caja de compensación
        informacionGeneral.put("responsableCaja1", "");

        //Para estos campos deberá tomar aleatoriamente los responsables con los usuarios que encuentre creados en la caja de compensación
        informacionGeneral.put("responsableCaja2", "");

        return informacionGeneral;

    }

    private void printObjectToJson(Object objeto) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String json = ow.writeValueAsString(objeto);
            System.out.println(json);
        } catch (JsonProcessingException ex) {
            LogManager.getLogger(SatBusiness.class).error(ex);
        }
    }

}
