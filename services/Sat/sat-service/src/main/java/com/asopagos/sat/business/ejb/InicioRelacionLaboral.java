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
import com.asopagos.sat.dto.BusquedaSAT;
import com.asopagos.sat.dto.DesafiliacionDTO;
import com.asopagos.sat.dto.InicioRelacionLaboralDTO;
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
public class InicioRelacionLaboral implements IInicioRelacionLaboral,Serializable{

    private static final ILogger logger = LogManager.getLogger(InicioRelacionLaboral.class);
    
    @PersistenceContext(unitName = "core_PU")
    private EntityManager entityManager;
    
    
    @Override    
    public List<InicioRelacionLaboralDTO> consultarInicioRelacionLaboral(BusquedaSAT busquedaSAT){
        logger.info("Comienza llamado servicios consultarAfiliacionEmpleadores");
        List<InicioRelacionLaboralDTO> respuesta = new ArrayList<InicioRelacionLaboralDTO>();
        try{
            String sq = "select distinct b.*,aud.RESPONSE mensaje from (\n" +
            "	select id\n" +
            "		  ,radicado\n" +
            "		  ,numeroTransaccion\n" +
            "		  ,tipoDocumentoEmpleador\n" +
            "		  ,numeroDocumentoEmpleador\n" +
            "		  ,serialSAT\n" +
            "		  ,tipoInicio, fechaInicio, tipoDocumentoTrabajador\n" +
            "		  ,numeroDocumentoTrabajador, primerNombreTrabajador, segundoNombreTrabajador\n" +
            "		  ,primerApellidoTrabajador,segundoApellidoTrabajador,sexoTrabajador\n" +                    
            "		  ,fechaNacimientoTrabajador,departamentoCausaSalarios,municipioCausaSalarios\n" +
            "             ,direccionMunicipioCausaSalarios,telefonoMunicipioCausaSalarios,correoElectronicoContactoTrabajador\n" +
            "             ,salario,tipoSalario,horasTrabajoMensuales" +                    
            "		  ,autorizacionDatosPersonales\n" +
            "		  ,autorizacionEnvioNotificaciones,\n" +
            "	case when (estado is null and audi > 0) then 'Error'\n" +
            "	when (estado is null and audi = 0) then 'Pendiente Enviar'\n" +
            "	else estado\n" +
            "	end estado\n" +
            "	from(\n" +
            "		SELECT inicio_relacion.*\n" +
            "		  ,(select count(1) from sat.aud_INICIO_RELACION_LABORAL aud where aud.id_inicio_relacion_laboral = inicio_relacion.id) audi\n" +
            "		  ,case \n" +
            "			when (proceso_enviado_sat = -1) then 'Retirado' \n" +
            "			when (proceso_enviado_sat > 0) then 'Satisfactorio' \n" +
            "		  END estado\n" +
            "		FROM SAT.INICIO_RELACION_LABORAL inicio_relacion WITH(NOLOCK)\n" +
            "	  )a\n" +
            "  )b\n" +
            "  left join sat.aud_INICIO_RELACION_LABORAL aud WITH(NOLOCK) on aud.id_inicio_relacion_laboral = b.id";
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
                    InicioRelacionLaboralDTO inicioRelacionLaboral = new InicioRelacionLaboralDTO();
                    inicioRelacionLaboral.setId(obj[0] != null ? new Long(obj[0].toString()) : 0L);
                    inicioRelacionLaboral.setRadicado(obj[1] != null ? obj[1].toString() : "");
                    inicioRelacionLaboral.setNumeroTransaccion(obj[2] != null ? obj[2].toString() : "");                    
                    inicioRelacionLaboral.setTipoDocumentoEmpleador(obj[3] != null ? obj[3].toString() : "");
                    inicioRelacionLaboral.setNumeroDocumentoEmpleador(obj[4] != null ? obj[4].toString() : "");
                    inicioRelacionLaboral.setSerialSAT(obj[5] != null ? obj[5].toString() : "");
                    inicioRelacionLaboral.setTipoInicio(obj[6] != null ? obj[6].toString() : "");
                    inicioRelacionLaboral.setFechaInicio(obj[7] != null ? obj[7].toString() : "");
                    inicioRelacionLaboral.setTipoDocumentoTrabajador(obj[8] != null ? obj[8].toString() : "");                    
                    inicioRelacionLaboral.setNumeroDocumentoTrabajador(obj[9] != null ? obj[9].toString() : "");
                    inicioRelacionLaboral.setPrimerNombreTrabajador(obj[10] != null ? obj[10].toString() : "");                    
                    inicioRelacionLaboral.setSegundoNombreTrabajador(obj[11] != null ? obj[11].toString() : "");
                    inicioRelacionLaboral.setPrimerApellidoTrabajador(obj[12] != null ? obj[12].toString() : "");
                    inicioRelacionLaboral.setSegundoApellidoTrabajador(obj[13] != null ? obj[13].toString() : "");
                    inicioRelacionLaboral.setSexoTrabajador(obj[14] != null ? obj[14].toString() : "");
                    inicioRelacionLaboral.setFechaNacimientoTrabajador(obj[15] != null ? obj[15].toString() : "");
                    inicioRelacionLaboral.setDepartamentoCausaSalarios(obj[16] != null ? obj[16].toString() : "");
                    inicioRelacionLaboral.setMunicipioCausaSalarios(obj[17] != null ? obj[17].toString() : "");
                    inicioRelacionLaboral.setDireccionMunicipioCausaSalarios(obj[18] != null ? obj[18].toString() : "");
                    inicioRelacionLaboral.setTelefonoMunicipioCausaSalarios(obj[19] != null ? new Long(obj[19].toString()) : 0L);
                    inicioRelacionLaboral.setCorreoElectronicoContactoTrabajador(obj[20] != null ? obj[20].toString() : "");
                    inicioRelacionLaboral.setSalario(obj[21] != null ? obj[21].toString() : "");
                    inicioRelacionLaboral.setTipoSalario(obj[22] != null ? obj[22].toString() : "");
                    inicioRelacionLaboral.setHorasTrabajoMensuales(obj[23] != null ? obj[23].toString() : "");
                    inicioRelacionLaboral.setAutorizacionDatosPersonales(obj[24] != null ? obj[24].toString() : "");
                    inicioRelacionLaboral.setAutorizacionEnvioNotificaciones(obj[25] != null ? obj[25].toString() : "");
                    inicioRelacionLaboral.setEstado(obj[26] != null ? obj[26].toString() : "");
                    inicioRelacionLaboral.setGlosa(obj[27] != null ? responseUtil.obtenerCodigoGlosa(obj[27].toString())  : "");
                    inicioRelacionLaboral.setMensajeAuditoria(obj[27] != null ? responseUtil.obtenerMensaje(obj[27].toString()) : "");
                    respuesta.add(inicioRelacionLaboral);
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
    public List<InicioRelacionLaboralDTO> consultarInicioRelacionLaboralAuditoria(Long id){
        logger.info("Comienza llamado servicios consultarInicioRelacionLaboralAuditoria");
        List<InicioRelacionLaboralDTO> respuesta = new ArrayList<InicioRelacionLaboralDTO>();
        try{
            String sq = 
                    "	select inicio_relacion.id\n" +
            "		  ,radicado\n" +
            "		  ,numeroTransaccion\n" +
            "		  ,tipoDocumentoEmpleador\n" +
            "		  ,numeroDocumentoEmpleador\n" +
            "		  ,serialSAT\n" +
            "		  ,tipoInicio, fechaInicio, tipoDocumentoTrabajador\n" +
            "		  ,numeroDocumentoTrabajador, primerNombreTrabajador, segundoNombreTrabajador\n" +
            "		  ,primerApellidoTrabajador,segundoApellidoTrabajador,sexoTrabajador\n" +                    
            "		  ,fechaNacimientoTrabajador,departamentoCausaSalarios,municipioCausaSalarios\n" +
            "             ,direccionMunicipioCausaSalarios,telefonoMunicipioCausaSalarios,correoElectronicoContactoTrabajador\n" +
            "             ,salario,tipoSalario,horasTrabajoMensuales" +                    
            "		  ,autorizacionDatosPersonales\n" +
            "		  ,autorizacionEnvioNotificaciones,\n" + 
                "case when aud.error='SI' then 'Error' when aud.error='NO' then 'Sin error' end estadoAuditoria,\n" +
                "RESPONSE mensaje,\n" +
                "aud.fecha,\n" +
                "aud.id id_auditoria, inicio_relacion.observaciones\n"+
                "from sat.aud_INICIO_RELACION_LABORAL aud\n" +
                "join sat.INICIO_RELACION_LABORAL inicio_relacion on aud.id_inicio_relacion_laboral = inicio_relacion.id";
            
            String where = " WHERE inicio_relacion.id="+id;
            
            Query query = entityManager.createNativeQuery(sq + where);
            List<Object[]> listado = query.getResultList();            
            for(Object[] obj:listado){
                try{
                    InicioRelacionLaboralDTO inicioRelacionLaboral = new InicioRelacionLaboralDTO();
                    inicioRelacionLaboral.setId(obj[0] != null ? new Long(obj[0].toString()) : 0L);
                    inicioRelacionLaboral.setRadicado(obj[1] != null ? obj[1].toString() : "");
                    inicioRelacionLaboral.setNumeroTransaccion(obj[2] != null ? obj[2].toString() : "");                    
                    inicioRelacionLaboral.setTipoDocumentoEmpleador(obj[3] != null ? obj[3].toString() : "");
                    inicioRelacionLaboral.setNumeroDocumentoEmpleador(obj[4] != null ? obj[4].toString() : "");
                    inicioRelacionLaboral.setSerialSAT(obj[5] != null ? obj[5].toString() : "");
                    inicioRelacionLaboral.setTipoInicio(obj[6] != null ? obj[6].toString() : "");
                    inicioRelacionLaboral.setFechaInicio(obj[7] != null ? obj[7].toString() : "");
                    inicioRelacionLaboral.setTipoDocumentoEmpleador(obj[8] != null ? obj[8].toString() : "");                    
                    inicioRelacionLaboral.setNumeroDocumentoTrabajador(obj[9] != null ? obj[9].toString() : "");
                    inicioRelacionLaboral.setPrimerNombreTrabajador(obj[10] != null ? obj[10].toString() : "");                    
                    inicioRelacionLaboral.setSegundoNombreTrabajador(obj[11] != null ? obj[11].toString() : "");
                    inicioRelacionLaboral.setPrimerApellidoTrabajador(obj[12] != null ? obj[12].toString() : "");
                    inicioRelacionLaboral.setSegundoApellidoTrabajador(obj[13] != null ? obj[13].toString() : "");
                    inicioRelacionLaboral.setSexoTrabajador(obj[14] != null ? obj[14].toString() : "");
                    inicioRelacionLaboral.setFechaNacimientoTrabajador(obj[15] != null ? obj[15].toString() : "");
                    inicioRelacionLaboral.setDepartamentoCausaSalarios(obj[16] != null ? obj[16].toString() : "");
                    inicioRelacionLaboral.setMunicipioCausaSalarios(obj[17] != null ? obj[17].toString() : "");
                    inicioRelacionLaboral.setDireccionMunicipioCausaSalarios(obj[18] != null ? obj[18].toString() : "");
                    inicioRelacionLaboral.setTelefonoMunicipioCausaSalarios(obj[19] != null ? new Long(obj[19].toString()) : 0L);
                    inicioRelacionLaboral.setCorreoElectronicoContactoTrabajador(obj[20] != null ? obj[20].toString() : "");
                    inicioRelacionLaboral.setSalario(obj[21] != null ? obj[21].toString() : "");
                    inicioRelacionLaboral.setTipoSalario(obj[22] != null ? obj[22].toString() : "");
                    inicioRelacionLaboral.setHorasTrabajoMensuales(obj[23] != null ? obj[23].toString() : "");
                    inicioRelacionLaboral.setAutorizacionDatosPersonales(obj[24] != null ? obj[24].toString() : "");
                    inicioRelacionLaboral.setAutorizacionEnvioNotificaciones(obj[25] != null ? obj[25].toString() : "");                   
                    inicioRelacionLaboral.setEstadoAuditoria(obj[26] != null ? obj[26].toString() : "");                    
                    inicioRelacionLaboral.setMensajeAuditoria(obj[27] != null ? obj[27].toString() : "");     
                    inicioRelacionLaboral.setFechaAuditoria(obj[28] != null ? obj[28].toString() : "");     
                    inicioRelacionLaboral.setIdAuditoria(obj[29] != null ? new Long(obj[29].toString()) : 0L);  
                    inicioRelacionLaboral.setObservacionesAuditoria(obj[30] != null ? obj[30].toString() : "");  
                    respuesta.add(inicioRelacionLaboral);
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
    public RespuestaEstandar cambiarEstadoAuditoriaInicioRelacionLaboral(
            Long id, Long idAuditoria, String estado, String observaciones){
        
        RespuestaEstandar respuesta = new RespuestaEstandar();
        
        String update = "UPDATE sat.INICIO_RELACION_LABORAL SET "
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
