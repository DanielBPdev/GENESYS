/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.sat.business.ejb;

import co.gov.sed.sace.util.responseUtil;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.sat.business.interfaces.IAfiliacionEmpleadores;
import com.asopagos.sat.business.interfaces.IPerdidaAfiliacionCausaGrave;
import com.asopagos.sat.dto.AfiliacionEmpleadoresDTO;
import com.asopagos.sat.dto.BusquedaSAT;
import com.asopagos.sat.dto.PerdidaAfiliacionCausaGraveDTO;
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
public class PerdidaAfiliacionCausaGrave implements IPerdidaAfiliacionCausaGrave,Serializable{

    private static final ILogger logger = LogManager.getLogger(AfiliacionEmpleadores.class);
    
    @PersistenceContext(unitName = "core_PU")
    private EntityManager entityManager;
    
    
    @Override    
    public List<PerdidaAfiliacionCausaGraveDTO> consultarPerdidaAfiliacionCausaGrave(BusquedaSAT busquedaSAT){
        logger.info("Comienza llamado servicios consultarPerdidaAfiliacionCausaGrave");
        List<PerdidaAfiliacionCausaGraveDTO> respuesta = new ArrayList<PerdidaAfiliacionCausaGraveDTO>();
        try{
            String sq = "select distinct b.*, aud.RESPONSE mensaje from (\n" +
            "	select id\n" +
            "		  ,radicado\n" +
            "		  ,tipoPersona\n" +
            "		  ,tipoDocumentoEmpleador\n" +
            "		  ,numeroDocumentoEmpleador\n" +
            "		  ,serialSAT\n" +
            "		  ,fechaPerdidaAfiliacion\n" +
            "		  ,razonSocialEmpleador\n" +
            "		  ,departamentoCausaSalarios\n" +
            "		  ,causalRetiro\n" +
            "		  ,primerNombreEmpleador\n" +
            "		  ,primerApellidoEmpleador\n" +
            "		  ,estadoReporte,\n" + 
            "	case when (estado is null and audi > 0) then 'Error'\n" +
            "	when (estado is null and audi = 0) then 'Pendiente Enviar'\n" +
            "	else estado\n" +
            "	end estado\n" +
            "	from(\n" +
            "		SELECT perdida_afil.*\n" +
            "		  ,(select count(1) from sat.aud_PERDIDA_CAUSA_GRAVE aud where aud.id_perdida_causa_grave = perdida_afil.id) audi\n" +
            "		  ,case \n" +
            "			when (proceso_enviado_sat = -1) then 'Retirado' \n" +
            "			when (proceso_enviado_sat > 0) then 'Satisfactorio' \n" +
            "		  END estado\n" +
            "		FROM SAT.PERDIDA_AFILIACION_CAUSA_GRAVE perdida_afil WITH(NOLOCK)\n" +
            "	  )a\n" +
            "  )b\n" +
            "  left join sat.aud_PERDIDA_CAUSA_GRAVE aud WITH(NOLOCK) on aud.id_perdida_causa_grave = b.id";
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
                    PerdidaAfiliacionCausaGraveDTO perdidaAfiliacion = new PerdidaAfiliacionCausaGraveDTO();
                    perdidaAfiliacion.setId(obj[0] != null ? new Long(obj[0].toString()) : 0L);
                    perdidaAfiliacion.setRadicado(obj[1] != null ? obj[1].toString() : "");
                    perdidaAfiliacion.setTipoPersona(obj[2] != null ? obj[2].toString() : "");
                    perdidaAfiliacion.setTipoDocumentoEmpleador(obj[3] != null ? obj[3].toString() : "");
                    perdidaAfiliacion.setNumeroDocumentoEmpleador(obj[4] != null ? obj[4].toString() : "");
                    perdidaAfiliacion.setSerialSAT(obj[5] != null ? obj[5].toString() : "");
                    perdidaAfiliacion.setFechaPerdidaAfiliacion(obj[6] != null ? obj[6].toString() : "");
                    perdidaAfiliacion.setRazonSocialEmpleador(obj[7] != null ? obj[7].toString() : "");
                    perdidaAfiliacion.setDepartamentoCausaSalarios(obj[8] != null ? obj[8].toString() : "");
                    perdidaAfiliacion.setCausalRetiro(obj[9] != null ? obj[9].toString() : "");
                    perdidaAfiliacion.setPrimerNombreEmpleador(obj[10] != null ? obj[10].toString() : "");
                    perdidaAfiliacion.setPrimerApellidoEmpleador(obj[11] != null ? obj[11].toString() : "");
                    perdidaAfiliacion.setEstadoReporte(obj[12] != null ? obj[12].toString() : "");
                    perdidaAfiliacion.setEstado(obj[13] != null ? obj[13].toString() : "");
                    perdidaAfiliacion.setMensajeAuditoria(obj[14] != null ? responseUtil.obtenerMensaje(obj[14].toString()) : "");
                    perdidaAfiliacion.setGlosa(obj[14] != null ? responseUtil.obtenerCodigoGlosa(obj[14].toString()) : "");
                    respuesta.add(perdidaAfiliacion);
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
    public List<PerdidaAfiliacionCausaGraveDTO> consultarPerdidaAfiliacionCausaGraveAuditoria(Long id){
        logger.info("Comienza llamado servicios consultarAfiliacionEmpleadoresAuditoria");
        List<PerdidaAfiliacionCausaGraveDTO> respuesta = new ArrayList<PerdidaAfiliacionCausaGraveDTO>();
        try{
            String sq = "select \n" +
                "perdida_afil.id\n" +
             "		  ,radicado\n" +
            "		  ,tipoPersona\n" +
            "		  ,tipoDocumentoEmpleador\n" +
            "		  ,numeroDocumentoEmpleador\n" +
            "		  ,serialSAT\n" +
            "		  ,fechaPerdidaAfiliacion\n" +
            "		  ,razonSocialEmpleador\n" +
            "		  ,departamentoCausaSalarios\n" +
            "		  ,causalRetiro\n" +
            "		  ,primerNombreEmpleador\n" +
            "		  ,primerApellidoEmpleador\n" +
            "		  ,estadoReporte,\n" + 
                "case when aud.error='SI' then 'Error' when aud.error='NO' then 'Sin error' end estadoAuditoria,\n" +
                "RESPONSE mensaje,\n" +
                "aud.fecha,\n" +
                "aud.id id_auditoria, perdida_afil.observaciones\n"+
                "from sat.aud_PERDIDA_CAUSA_GRAVE aud\n" +
                "join sat.PERDIDA_AFILIACION_CAUSA_GRAVE perdida_afil on aud.id_perdida_causa_grave = perdida_afil.id";
            
            String where = " WHERE perdida_afil.id="+id;
            
            Query query = entityManager.createNativeQuery(sq + where);
            List<Object[]> listado = query.getResultList();            
            for(Object[] obj:listado){
                try{
                    PerdidaAfiliacionCausaGraveDTO perdidaAfiliacion = new PerdidaAfiliacionCausaGraveDTO();
                    perdidaAfiliacion.setId(obj[0] != null ? new Long(obj[0].toString()) : 0L);
                    perdidaAfiliacion.setRadicado(obj[1] != null ? obj[1].toString() : "");
                    perdidaAfiliacion.setTipoPersona(obj[2] != null ? obj[2].toString() : "");
                    perdidaAfiliacion.setTipoDocumentoEmpleador(obj[3] != null ? obj[3].toString() : "");
                    perdidaAfiliacion.setNumeroDocumentoEmpleador(obj[4] != null ? obj[4].toString() : "");
                    perdidaAfiliacion.setSerialSAT(obj[5] != null ? obj[5].toString() : "");
                    perdidaAfiliacion.setFechaPerdidaAfiliacion(obj[6] != null ? obj[6].toString() : "");
                    perdidaAfiliacion.setRazonSocialEmpleador(obj[7] != null ? obj[7].toString() : "");
                    perdidaAfiliacion.setDepartamentoCausaSalarios(obj[8] != null ? obj[8].toString() : "");
                    perdidaAfiliacion.setCausalRetiro(obj[9] != null ? obj[9].toString() : "");
                    perdidaAfiliacion.setPrimerNombreEmpleador(obj[10] != null ? obj[10].toString() : "");
                    perdidaAfiliacion.setPrimerApellidoEmpleador(obj[11] != null ? obj[11].toString() : "");
                    perdidaAfiliacion.setEstadoReporte(obj[12] != null ? obj[12].toString() : "");
                    perdidaAfiliacion.setEstadoAuditoria(obj[13] != null ? obj[13].toString() : "");                    
                    perdidaAfiliacion.setMensajeAuditoria(obj[14] != null ? responseUtil.obtenerMensaje(obj[14].toString()) : "");
                    perdidaAfiliacion.setGlosa(obj[14] != null ? responseUtil.obtenerCodigoGlosa(obj[14].toString()) : "");
                    perdidaAfiliacion.setFechaAuditoria(obj[15] != null ? obj[15].toString() : "");
                    perdidaAfiliacion.setIdAuditoria(obj[16] != null ? new Long(obj[16].toString()) : null);  
                    perdidaAfiliacion.setObservacionesAuditoria(obj[17] != null ? obj[17].toString() : "");

                    respuesta.add(perdidaAfiliacion);
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
    public RespuestaEstandar cambiarEstadoAuditoriaPerdidaAfiliacionCausaGrave(
            Long id, Long idAuditoria, String estado, String observaciones){
        
        RespuestaEstandar respuesta = new RespuestaEstandar();
        
        String update = "UPDATE sat.PERDIDA_AFILIACION_CAUSA_GRAVE SET "
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
