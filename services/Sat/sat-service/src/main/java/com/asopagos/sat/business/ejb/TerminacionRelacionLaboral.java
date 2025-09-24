/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.sat.business.ejb;

import co.gov.sed.sace.util.responseUtil;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.sat.business.interfaces.IDesafiliacion;
import com.asopagos.sat.business.interfaces.IInicioRelacionLaboral;
import com.asopagos.sat.business.interfaces.ITerminacionRelacionLaboral;
import com.asopagos.sat.dto.BusquedaSAT;
import com.asopagos.sat.dto.DesafiliacionDTO;
import com.asopagos.sat.dto.InicioRelacionLaboralDTO;
import com.asopagos.sat.dto.RespuestaEstandar;
import com.asopagos.sat.dto.TerminacionRelacionLaboralDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jdk.nashorn.internal.ir.Terminal;

/**
 *
 * @author Maria Cuellar
 */
@Stateless
public class TerminacionRelacionLaboral implements ITerminacionRelacionLaboral,Serializable{

    private static final ILogger logger = LogManager.getLogger(TerminacionRelacionLaboral.class);
    
    @PersistenceContext(unitName = "core_PU")
    private EntityManager entityManager;
    
    
    @Override    
    public List<TerminacionRelacionLaboralDTO> consultarTerminacionRelacionLaboral(BusquedaSAT busquedaSAT){
        logger.info("Comienza llamado servicios consultarAfiliacionEmpleadores");
        List<TerminacionRelacionLaboralDTO> respuesta = new ArrayList<TerminacionRelacionLaboralDTO>();
        try{
            String sq = "select distinct b.*, aud.RESPONSE mensaje from (\n" +
            "	select id\n" +
            "		  ,radicado\n" +
            "		  ,numeroTransaccion\n" +
            "		  ,tipoDocumentoEmpleador\n" +
            "		  ,numeroDocumentoEmpleador\n" +
            "		  ,serialSAT\n" +
            "		  ,tipoTerminacion, fechaTerminacion, tipoDocumentoTrabajador\n" +
            "		  ,numeroDocumentoTrabajador, primerNombreTrabajador\n" +
            "		  ,primerApellidoTrabajador\n" +                    
            "		  ,autorizacionDatosPersonales\n" +
            "		  ,autorizacionEnvioNotificaciones,\n" +
            "	case when (estado is null and audi > 0) then 'Error'\n" +
            "	when (estado is null and audi = 0) then 'Pendiente Enviar'\n" +
            "	else estado\n" +
            "	end estado\n" +
            "	from(\n" +
            "		SELECT terminacion_relacion.*\n" +
            "		  ,(select count(1) from sat.aud_TERMINACION_RELACION_LABORAL aud where aud.id_terminacion_relacion_laboral = terminacion_relacion.id) audi\n" +
            "		  ,case \n" +
            "			when (proceso_enviado_sat = -1) then 'Retirado' \n" +
            "			when (proceso_enviado_sat > 0) then 'Satisfactorio' \n" +
            "		  END estado\n" +
            "		FROM SAT.TERMINACION_RELACION_LABORAL terminacion_relacion WITH(NOLOCK)\n" +
            "	  )a\n" +
            "  )b\n" +
            "  left join sat.aud_TERMINACION_RELACION_LABORAL aud WITH(NOLOCK) on aud.id_terminacion_relacion_laboral = b.id";
            String where = " WHERE 1=1";
            if(busquedaSAT.getEstado() != null){
                where = where + "and estado = '"+busquedaSAT.getEstado()+"'";
            }
            if(busquedaSAT.getNumeroDocumentoEmpleador() != null){
                where = where + " and numeroDocumentoEmpleador = '"+busquedaSAT.getNumeroDocumentoEmpleador()+"'";
            }
            if(busquedaSAT.getNumeroDocumentoTrabajador() != null){
                where = where + " and numeroDocumentoTrabajador = '"+busquedaSAT.getNumeroDocumentoTrabajador()+"'";
            }
            if(busquedaSAT.getFechaInicio() != null && busquedaSAT.getFechaFin() != null){
                where = where + " and aud.fecha between '"+busquedaSAT.getFechaInicio()+"' and '"+busquedaSAT.getFechaFin()+"'";
            }
            Query query = entityManager.createNativeQuery(sq + where);
            List<Object[]> listado = query.getResultList();            
            for(Object[] obj:listado){
                try{
                    TerminacionRelacionLaboralDTO terminacionRelacionLaboral = new TerminacionRelacionLaboralDTO();
                    terminacionRelacionLaboral.setId(obj[0] != null ? new Long(obj[0].toString()) : 0L);
                    terminacionRelacionLaboral.setRadicado(obj[1] != null ? obj[1].toString() : "");
                    terminacionRelacionLaboral.setNumeroTransaccion(obj[2] != null ? obj[2].toString() : "");                    
                    terminacionRelacionLaboral.setTipoDocumentoEmpleador(obj[3] != null ? obj[3].toString() : "");
                    terminacionRelacionLaboral.setNumeroDocumentoEmpleador(obj[4] != null ? obj[4].toString() : "");
                    terminacionRelacionLaboral.setSerialSAT(obj[5] != null ? obj[5].toString() : "");
                    terminacionRelacionLaboral.setTipoTerminacion(obj[6] != null ? obj[6].toString() : "");
                    terminacionRelacionLaboral.setFechaTerminacion(obj[7] != null ? obj[7].toString() : "");
                    terminacionRelacionLaboral.setTipoDocumentoEmpleador(obj[8] != null ? obj[8].toString() : "");                    
                    terminacionRelacionLaboral.setNumeroDocumentoTrabajador(obj[9] != null ? obj[9].toString() : "");
                    terminacionRelacionLaboral.setPrimerNombreTrabajador(obj[10] != null ? obj[10].toString() : "");                    
                    terminacionRelacionLaboral.setPrimerApellidoTrabajador(obj[11] != null ? obj[11].toString() : "");                    
                    terminacionRelacionLaboral.setAutorizacionDatosPersonales(obj[12] != null ? obj[12].toString() : "");
                    terminacionRelacionLaboral.setAutorizacionEnvioNotificaciones(obj[13] != null ? obj[13].toString() : "");
                    terminacionRelacionLaboral.setEstado(obj[14] != null ? obj[14].toString() : "");
                    terminacionRelacionLaboral.setMensajeAuditoria(obj[15] != null ? responseUtil.obtenerMensaje(obj[15].toString()) : "");
                    terminacionRelacionLaboral.setGlosa(obj[15] != null ? responseUtil.obtenerCodigoGlosa(obj[15].toString()) : "");
                    respuesta.add(terminacionRelacionLaboral);
                }catch(Exception e){
                    e.printStackTrace();
                    System.out.println("Ocurrio un error por ++++++++++++++++++++++++++++++++++++++++++++++++++++");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e);
        }
        logger.info("Termina llamado servicios reportarAfiliacionPrimeraVez");
        return respuesta;
    }
    
    @Override    
    public List<TerminacionRelacionLaboralDTO> consultarTerminacionRelacionLaboralAuditoria(Long id){
        logger.info("Comienza llamado servicios consultarInicioRelacionLaboralAuditoria");
        List<TerminacionRelacionLaboralDTO> respuesta = new ArrayList<TerminacionRelacionLaboralDTO>();
        try{
            String sq = 
           "	select terminar_relacion.id\n" +
            "		  ,radicado\n" +
            "		  ,numeroTransaccion\n" +
            "		  ,tipoDocumentoEmpleador\n" +
            "		  ,numeroDocumentoEmpleador\n" +
            "		  ,serialSAT\n" +
            "		  ,tipoTerminacion, fechaTerminacion, tipoDocumentoTrabajador\n" +
            "		  ,numeroDocumentoTrabajador, primerNombreTrabajador\n" +
            "		  ,primerApellidoTrabajador\n" +                         
            "		  ,autorizacionDatosPersonales\n" +
            "		  ,autorizacionEnvioNotificaciones,\n" + 
                "case when aud.error='SI' then 'Error' when aud.error='NO' then 'Sin error' end estadoAuditoria,\n" +
                "RESPONSE mensaje,\n" +
                "aud.fecha,\n" +
                "aud.id id_auditoria, terminar_relacion.observaciones\n"+
                "from sat.aud_TERMINACION_RELACION_LABORAL aud\n" +
                "join sat.TERMINACION_RELACION_LABORAL terminar_relacion on aud.id_terminacion_relacion_laboral = terminar_relacion.id";
            
            String where = " WHERE terminar_relacion.id="+id;
            
            Query query = entityManager.createNativeQuery(sq + where);
            List<Object[]> listado = query.getResultList();            
            for(Object[] obj:listado){
                try{
                    TerminacionRelacionLaboralDTO terminacionRelacionLaboral = new TerminacionRelacionLaboralDTO();
                    terminacionRelacionLaboral.setId(obj[0] != null ? new Long(obj[0].toString()) : 0L);
                    terminacionRelacionLaboral.setRadicado(obj[1] != null ? obj[1].toString() : "");
                    terminacionRelacionLaboral.setNumeroTransaccion(obj[2] != null ? obj[2].toString() : "");                    
                    terminacionRelacionLaboral.setTipoDocumentoEmpleador(obj[3] != null ? obj[3].toString() : "");
                    terminacionRelacionLaboral.setNumeroDocumentoEmpleador(obj[4] != null ? obj[4].toString() : "");
                    terminacionRelacionLaboral.setSerialSAT(obj[5] != null ? obj[5].toString() : "");
                    terminacionRelacionLaboral.setTipoTerminacion(obj[6] != null ? obj[6].toString() : "");
                    terminacionRelacionLaboral.setFechaTerminacion(obj[7] != null ? obj[7].toString() : "");
                    terminacionRelacionLaboral.setTipoDocumentoEmpleador(obj[8] != null ? obj[8].toString() : "");                    
                    terminacionRelacionLaboral.setNumeroDocumentoTrabajador(obj[9] != null ? obj[9].toString() : "");
                    terminacionRelacionLaboral.setPrimerNombreTrabajador(obj[10] != null ? obj[10].toString() : "");                    
                    terminacionRelacionLaboral.setPrimerApellidoTrabajador(obj[11] != null ? obj[11].toString() : "");                    
                    terminacionRelacionLaboral.setAutorizacionDatosPersonales(obj[12] != null ? obj[12].toString() : "");
                    terminacionRelacionLaboral.setAutorizacionEnvioNotificaciones(obj[13] != null ? obj[13].toString() : "");
           
                    terminacionRelacionLaboral.setEstadoAuditoria(obj[14] != null ? obj[14].toString() : "");                    
                    terminacionRelacionLaboral.setMensajeAuditoria(obj[15] != null ? responseUtil.obtenerMensaje(obj[15].toString()) : "");
                    terminacionRelacionLaboral.setGlosa(obj[15] != null ? responseUtil.obtenerCodigoGlosa(obj[15].toString()) : "");
                    terminacionRelacionLaboral.setFechaAuditoria(obj[16] != null ? obj[16].toString() : "");     
                    terminacionRelacionLaboral.setIdAuditoria(obj[17] != null ? new Long(obj[17].toString()) : 0L);  
                    terminacionRelacionLaboral.setObservacionesAuditoria(obj[18] != null ? obj[18].toString() : "");
                    respuesta.add(terminacionRelacionLaboral);
                }catch(Exception e){
                    e.printStackTrace();
                    System.out.println("Ocurrio un error por ++++++++++++++++++++++++++++++++++++++++++++++++++++");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e);
        }
        logger.info("Termina llamado servicios reportarAfiliacionPrimeraVez");
        return respuesta;
    }
    
    @Override
    public RespuestaEstandar cambiarEstadoAuditoriaTerminacionRelacionLaboral(
            Long id, Long idAuditoria, String estado, String observaciones){
        
        RespuestaEstandar respuesta = new RespuestaEstandar();
        
        String update = "UPDATE sat.TERMINACION_RELACION_LABORAL SET "
                + "proceso_enviado_sat=-1, observaciones='"+observaciones+"' WHERE ID= " + id;

        Query query = entityManager.createNativeQuery(update);
        int resul = query.executeUpdate();
        
        if(resul > 0){
            respuesta.setEstado("OK");
            respuesta.adicionarMensaje("Información actualizada correctamente");
        }else{
            respuesta.setEstado("KO");
            respuesta.adicionarMensaje("Se presento un error actualizando la "
                    + "información, por favor vuelva intentar.");
        }
        
        return respuesta;
    }
}
