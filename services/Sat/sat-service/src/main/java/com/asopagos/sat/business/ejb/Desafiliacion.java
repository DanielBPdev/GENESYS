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
import com.asopagos.sat.dto.BusquedaSAT;
import com.asopagos.sat.dto.DesafiliacionDTO;
import com.asopagos.sat.dto.RespuestaEstandar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Maria Cuellar
 */
@Stateless
public class Desafiliacion implements IDesafiliacion,Serializable{

    private static final ILogger logger = LogManager.getLogger(Desafiliacion.class);
    
    @PersistenceContext(unitName = "core_PU")
    private EntityManager entityManager;
    
    
    @Override    
    public List<DesafiliacionDTO> consultarDesafiliacion(BusquedaSAT busquedaSAT){
        logger.info("Comienza llamado servicios consultarAfiliacionEmpleadores");
        List<DesafiliacionDTO> respuesta = new ArrayList<DesafiliacionDTO>();
        try{
            String sq = "select distinct b.*, aud.RESPONSE mensaje from (\n" +
            "	select id\n" +
            "		  ,radicado\n" +
            "		  ,numeroTransaccion\n" +
            "		  ,tipoDocumentoEmpleador\n" +
            "		  ,numeroDocumentoEmpleador\n" +
            "		  ,serialSAT\n" +
            "		  ,fechaSolicitudDesafiliacion, fechaEfectividadDesafiliacion, departamentoAfiliacion\n" +
            "		  ,autorizacionDatosPersonales\n" +
            "		  ,autorizacionEnvioNotificaciones\n" +
            "		  ,pazYSalvo, fechaPazYSalvo, \n" +
            "	case when (estado is null and audi > 0) then 'Error'\n" +
            "	when (estado is null and audi = 0) then 'Pendiente Enviar'\n" +
            "	else estado\n" +
            "	end estado\n" +
            "	from(\n" +
            "		SELECT des_afil.*\n" +
            "		  ,(select count(1) from sat.aud_DESAFILIACION aud where aud.id_desafiliacion = des_afil.id) audi\n" +
            "		  ,case \n" +
            "			when (proceso_enviado_sat = -1) then 'Retirado' \n" +
            "			when (proceso_enviado_sat > 0) then 'Satisfactorio' \n" +
            "		  END estado\n" +
            "		FROM SAT.DESAFILIACION des_afil WITH(NOLOCK)\n" +
            "	  )a\n" +
            "  )b\n" +
            "  left join sat.aud_DESAFILIACION aud WITH(NOLOCK) on aud.id_desafiliacion = b.id";
            String where = " WHERE 1=1";
            if(busquedaSAT.getEstado() != null){
                where = where + "and estado = '"+busquedaSAT.getEstado()+"'";
            }
            if(busquedaSAT.getNumeroDocumentoEmpleador() != null){
                where = where + " and numeroDocumentoEmpleador = '"+busquedaSAT.getNumeroDocumentoEmpleador()+"'";
            }
            if(busquedaSAT.getFechaInicio() != null && busquedaSAT.getFechaFin() != null){
                where = where + " and aud.fecha between '"+busquedaSAT.getFechaInicio()+"' and '"+busquedaSAT.getFechaFin()+"'";
            }
            Query query = entityManager.createNativeQuery(sq + where);
            List<Object[]> listado = query.getResultList();            
            for(Object[] obj:listado){
                try{
                    DesafiliacionDTO desafiliacion = new DesafiliacionDTO();
                    desafiliacion.setId(obj[0] != null ? new Long(obj[0].toString()) : 0L);
                    desafiliacion.setRadicado(obj[1] != null ? obj[1].toString() : "");
                    desafiliacion.setNumeroTransaccion(obj[2] != null ? obj[2].toString() : "");                    
                    desafiliacion.setTipoDocumentoEmpleador(obj[3] != null ? obj[3].toString() : "");
                    desafiliacion.setNumeroDocumentoEmpleador(obj[4] != null ? obj[4].toString() : "");
                    desafiliacion.setSerialSAT(obj[5] != null ? obj[5].toString() : "");
                    desafiliacion.setFechaSolicitudDesafiliacion(obj[6] != null ? obj[6].toString() : "");
                    desafiliacion.setFechaEfectividadDesafiliacion(obj[7] != null ? obj[7].toString() : "");
                    desafiliacion.setDepartamentoAfiliacion(obj[8] != null ? obj[8].toString() : "");                    
                    desafiliacion.setAutorizacionDatosPersonales(obj[9] != null ? obj[9].toString() : "");
                    desafiliacion.setAutorizacionEnvioNotificaciones(obj[10] != null ? obj[10].toString() : "");                    
                    desafiliacion.setPazYSalvo(obj[11] != null ? obj[11].toString() : "");
                    desafiliacion.setFechaPazYSalvo(obj[12] != null ? obj[12].toString() : "");
                    desafiliacion.setEstado(obj[13] != null ? obj[13].toString() : "");
                    desafiliacion.setMensajeAuditoria(obj[14] != null ? responseUtil.obtenerMensaje(obj[14].toString()) : "");
                    desafiliacion.setGlosa(obj[14] != null ? responseUtil.obtenerCodigoGlosa(obj[14].toString()) : "");
                    respuesta.add(desafiliacion);
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
    public List<DesafiliacionDTO> consultarDesafiliacionAuditoria(Long id){
        logger.info("Comienza llamado servicios consultarAfiliacionEmpleadoresAuditoria");
        List<DesafiliacionDTO> respuesta = new ArrayList<DesafiliacionDTO>();
        try{
            String sq = "select \n" +
                "des_afil.id,\n" +
                "radicado\n" +
                ",numeroTransaccion\n" +
                ",tipoDocumentoEmpleador\n" +
                ",numeroDocumentoEmpleador\n" +
                ",serialSAT\n" +
                ",fechaSolicitudDesafiliacion, fechaEfectividadDesafiliacion, departamentoAfiliacion\n" +                
                ",autorizacionDatosPersonales\n" +
                ",autorizacionEnvioNotificaciones\n" +
                ",pazYSalvo, fechaPazYSalvo, \n" +   
                "case when aud.error='SI' then 'Error' when aud.error='NO' then 'Sin error' end estadoAuditoria,\n" +
                "RESPONSE mensaje,\n" +
                "aud.fecha,\n" +
                "aud.id id_auditoria, des_afil.observaciones\n"+
                "from sat.aud_DESAFILIACION aud\n" +
                "join sat.DESAFILIACION des_afil on aud.id_desafiliacion = des_afil.id";
            
            String where = " WHERE des_afil.id="+id;
            
            Query query = entityManager.createNativeQuery(sq + where);
            List<Object[]> listado = query.getResultList();            
            for(Object[] obj:listado){
                try{
                    DesafiliacionDTO desafiliacion = new DesafiliacionDTO();
                    desafiliacion.setId(obj[0] != null ? new Long(obj[0].toString()) : 0L);
                    desafiliacion.setRadicado(obj[1] != null ? obj[1].toString() : "");
                    desafiliacion.setNumeroTransaccion(obj[2] != null ? obj[2].toString() : "");
                    desafiliacion.setTipoDocumentoEmpleador(obj[3] != null ? obj[3].toString() : "");
                    desafiliacion.setNumeroDocumentoEmpleador(obj[4] != null ? obj[4].toString() : "");
                    desafiliacion.setSerialSAT(obj[5] != null ? obj[5].toString() : "");
                    desafiliacion.setFechaSolicitudDesafiliacion(obj[6] != null ? obj[6].toString() : "");
                    desafiliacion.setFechaEfectividadDesafiliacion(obj[7] != null ? obj[7].toString() : "");
                    desafiliacion.setDepartamentoAfiliacion(obj[8] != null ? obj[8].toString() : "");                    
                    desafiliacion.setAutorizacionDatosPersonales(obj[9] != null ? obj[9].toString() : "");
                    desafiliacion.setAutorizacionEnvioNotificaciones(obj[10] != null ? obj[10].toString() : "");                    
                    desafiliacion.setPazYSalvo(obj[11] != null ? obj[11].toString() : "");
                    desafiliacion.setFechaPazYSalvo(obj[12] != null ? obj[12].toString() : "");
                    desafiliacion.setEstadoAuditoria(obj[13] != null ? obj[13].toString() : "");                    
                    desafiliacion.setMensajeAuditoria(obj[14] != null ? responseUtil.obtenerMensaje(obj[14].toString()) : "");
                    desafiliacion.setGlosa(obj[14] != null ? responseUtil.obtenerCodigoGlosa(obj[14].toString()) : "");
                    desafiliacion.setFechaAuditoria(obj[15] != null ? obj[15].toString() : "");     
                    desafiliacion.setIdAuditoria(obj[16] != null ? new Long(obj[16].toString()) : 0L);  
                    desafiliacion.setObservacionesAuditoria(obj[17] != null ? obj[17].toString() : "");  
                    respuesta.add(desafiliacion);
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
    public RespuestaEstandar cambiarEstadoAuditoriaDesafiliacion(
            Long id, Long idAuditoria, String estado, String observaciones){
        
        RespuestaEstandar respuesta = new RespuestaEstandar();
        
        String update = "UPDATE sat.DESAFILIACION SET "
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
