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
import com.asopagos.sat.business.interfaces.IEstadoPagoAportesEmpleadores;
import com.asopagos.sat.business.interfaces.IInicioRelacionLaboral;
import com.asopagos.sat.business.interfaces.ITerminacionRelacionLaboral;
import com.asopagos.sat.dto.BusquedaSAT;
import com.asopagos.sat.dto.DesafiliacionDTO;
import com.asopagos.sat.dto.EstadoPAAportesDTO;
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
public class EstadoPagoAportesEmpleadores implements IEstadoPagoAportesEmpleadores,Serializable{

    private static final ILogger logger = LogManager.getLogger(EstadoPagoAportesEmpleadores.class);
    
    @PersistenceContext(unitName = "core_PU")
    private EntityManager entityManager;
    
    
    @Override    
    public List<EstadoPAAportesDTO> consultarEstadoPagoAportes(BusquedaSAT busquedaSAT){
        logger.info("Comienza llamado servicios consultarAfiliacionEmpleadores");
        List<EstadoPAAportesDTO> respuesta = new ArrayList<EstadoPAAportesDTO>();
        try{
            String sq = "select distinct b.*, aud.RESPONSE mensaje from (\n" +
            "	select id\n" +
            "		  ,radicado\n" +
            "		  ,tipoDocumentoEmpleador\n" +
            "		  ,numeroDocumentoEmpleador\n" +
            "		  ,serialSAT\n" +
            "		  ,estadoPagoAportes,\n" +
            "	case when (estado is null and audi > 0) then 'Error'\n" +
            "	when (estado is null and audi = 0) then 'Pendiente Enviar'\n" +
            "	else estado\n" +
            "	end estado\n" +
            "	from(\n" +
            "		SELECT estado_aportes.*\n" +
            "		  ,(select count(1) from sat.aud_ESTADO_PA_APORTES aud where aud.id_estado_pago_aportes = estado_aportes.id) audi\n" +
            "		  ,case \n" +
            "			when (proceso_enviado_sat = -1) then 'Retirado' \n" +
            "			when (proceso_enviado_sat > 0) then 'Satisfactorio' \n" +
            "		  END estado\n" +
            "		FROM SAT.ESTADO_PA_APORTES estado_aportes WITH(NOLOCK)\n" +
            "	  )a\n" +
            "  )b\n" +
            "  left join sat.aud_ESTADO_PA_APORTES aud WITH(NOLOCK) on aud.id_estado_pago_aportes = b.id";
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
                    EstadoPAAportesDTO estadoAportes = new EstadoPAAportesDTO();
                    estadoAportes.setId(obj[0] != null ? new Long(obj[0].toString()) : 0L);
                    estadoAportes.setRadicado(obj[1] != null ? obj[1].toString() : "");
                    estadoAportes.setTipoDocumentoEmpleador(obj[2] != null ? obj[2].toString() : "");
                    estadoAportes.setNumeroDocumentoEmpleador(obj[3] != null ? obj[3].toString() : "");
                    estadoAportes.setSerialSAT(obj[4] != null ? obj[4].toString() : "");
                    estadoAportes.setEstadoPagoAportes(obj[5] != null ? obj[5].toString() : "");                    
                    estadoAportes.setEstado(obj[6] != null ? obj[6].toString() : "");
                    estadoAportes.setGlosa(obj[7] != null ? responseUtil.obtenerCodigoGlosa(obj[7].toString()) : "");
                    estadoAportes.setMensajeAuditoria(obj[7] != null ? responseUtil.obtenerMensaje(obj[7].toString()) : "");
                    respuesta.add(estadoAportes);
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
    public List<EstadoPAAportesDTO> consultarEstadoPagoAportesAuditoria(Long id){
        logger.info("Comienza llamado servicios consultarInicioRelacionLaboralAuditoria");
        List<EstadoPAAportesDTO> respuesta = new ArrayList<EstadoPAAportesDTO>();
        try{
            String sq = 
                    "	select estado_aportes.id\n" +
            "		  ,radicado\n" +
            "		  ,tipoDocumentoEmpleador\n" +
            "		  ,numeroDocumentoEmpleador\n" +
            "		  ,serialSAT\n" +
            "		  ,estadoPagoAportes,\n" +            
            "case when aud.error='SI' then 'Error' when aud.error='NO' then 'Sin error' end estadoAuditoria,\n" +
            "RESPONSE mensaje,\n" +
            "aud.fecha,\n" +
            "aud.id id_auditoria, estado_aportes.observaciones\n"+
            "from sat.aud_ESTADO_PA_APORTES aud\n" +
            "join sat.ESTADO_PA_APORTES estado_aportes on aud.id_estado_pago_aportes = estado_aportes.id";
            
            String where = " WHERE estado_aportes.id="+id;
            
            Query query = entityManager.createNativeQuery(sq + where);
            List<Object[]> listado = query.getResultList();            
            for(Object[] obj:listado){
                try{
                    EstadoPAAportesDTO estadoAportes = new EstadoPAAportesDTO();
                    estadoAportes.setId(obj[0] != null ? new Long(obj[0].toString()) : 0L);
                    estadoAportes.setRadicado(obj[1] != null ? obj[1].toString() : "");
                    estadoAportes.setTipoDocumentoEmpleador(obj[2] != null ? obj[2].toString() : "");
                    estadoAportes.setNumeroDocumentoEmpleador(obj[3] != null ? obj[3].toString() : "");
                    estadoAportes.setSerialSAT(obj[4] != null ? obj[4].toString() : "");
                    estadoAportes.setEstadoPagoAportes(obj[5] != null ? obj[5].toString() : "");                   
                    estadoAportes.setEstadoAuditoria(obj[6] != null ? obj[6].toString() : "");                    
                    estadoAportes.setMensajeAuditoria(obj[7] != null ? responseUtil.obtenerMensaje(obj[7].toString()) : "");
                    estadoAportes.setGlosa(obj[7] != null ? responseUtil.obtenerCodigoGlosa(obj[7].toString()) : "");
                    estadoAportes.setFechaAuditoria(obj[8] != null ? obj[8].toString() : "");     
                    estadoAportes.setIdAuditoria(obj[9] != null ? new Long(obj[9].toString()) : 0L);  
                    estadoAportes.setObservacionesAuditoria(obj[10] != null ? obj[10].toString() : "");  
                    respuesta.add(estadoAportes);
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
    public RespuestaEstandar cambiarEstadoAuditoriaEstadoPagoAportes(
            Long id, Long idAuditoria, String estado, String observaciones){
        
        RespuestaEstandar respuesta = new RespuestaEstandar();
        
        String update = "UPDATE sat.ESTADO_PA_APORTES SET "
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
