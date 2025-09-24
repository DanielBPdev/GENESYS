/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.sat.business.ejb;

import co.gov.sed.sace.util.responseUtil;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.sat.business.interfaces.IAfiliacionEmpleadoresMismoDpto;
import com.asopagos.sat.dto.AfiliacionEmpleadoresMismoDptoDTO;
import com.asopagos.sat.dto.BusquedaSAT;
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
public class AfiliacionEmpleadoresMismoDpto implements IAfiliacionEmpleadoresMismoDpto,Serializable{

    private static final ILogger logger = LogManager.getLogger(AfiliacionEmpleadoresMismoDpto.class);
    
    @PersistenceContext(unitName = "core_PU")
    private EntityManager entityManager;
    
    
    @Override    
    public List<AfiliacionEmpleadoresMismoDptoDTO> consultarAfiliacionEmpleadoresMismoDpto(BusquedaSAT busquedaSAT){
        logger.info("Comienza llamado servicios consultarAfiliacionEmpleadores");
        List<AfiliacionEmpleadoresMismoDptoDTO> respuesta = new ArrayList<AfiliacionEmpleadoresMismoDptoDTO>();
        try{
            String sq = "select distinct b.*, aud.RESPONSE mensaje from (\n" +
            "	select id\n" +
            "		  ,radicado\n" +
            "		  ,numeroTransaccion\n" +
            "		  ,tipoPersona\n" +
            "		  ,naturalezaJuridica\n" +
            "		  ,tipoDocumentoEmpleador\n" +
            "		  ,numeroDocumentoEmpleador\n" +
            "		  ,serialSAT\n" +
            "		  ,primerNombreEmpleador\n" +
            "		  ,segundoNombreEmpleador\n" +
            "		  ,primerApellidoEmpleador\n" +
            "		  ,segundoApellidoEmpleador\n" +
            "		  ,fechaSolicitud\n" +
            "		  ,'' perdidaAfiliacionCausaGrave\n" +
            "		  ,fechaEfectividad\n" +
            "		  ,razonSocialEmpleador\n" +
            "		  ,numeroMatriculaMercantil\n" +
            "		  ,departamentoCausaSalarios\n" +
            "		  ,municipioContacto\n" +
            "		  ,direccionContacto\n" +
            "		  ,telefonoContacto\n" +
            "		  ,correoElectronicoContacto\n" +
            "		  ,tipoDocumentoRepresentanteLegal\n" +
            "		  ,numeroDocumentoRepresentanteLegal\n" +
            "		  ,primerNombreRepresentanteLegal\n" +
            "		  ,segundoNombreRepresentanteLegal\n" +
            "		  ,primerApellidoRepresentanteLegal\n" +
            "		  ,segundoApellidoRepresentanteLegal\n" +
            "		  ,autorizacionDatosPersonales\n" +
            "		  ,autorizacionEnvioNotificaciones\n" +
            "		  ,manifestacionNoAfiliadoCaja \n" +
            "		  ,codigoCajaAnterior, pazYSalvo, fechaPazYSalvo, \n" +
            "	case when (estado is null and audi > 0) then 'Error'\n" +
            "	when (estado is null and audi = 0) then 'Pendiente Enviar'\n" +
            "	else estado\n" +
            "	end estado\n" +
            "	from(\n" +
            "		SELECT afil.*\n" +
            "		  ,(select count(1) from sat.aud_AFILIACION_EMPLEADORES_MISMO_DEPARTAMENTO aud where aud.id_afil_empl_mismo_departamento = afil.id) audi\n" +
            "		  ,case \n" +
            "			when (proceso_enviado_sat = -1) then 'Retirado' \n" +
            "			when (proceso_enviado_sat > 0) then 'Satisfactorio' \n" +
            "		  END estado\n" +
            "		FROM SAT.AFILIACION_EMPLEADORES_MISMO_DEPARTAMENTO afil WITH(NOLOCK)\n" +
            "	  )a\n" +
            "  )b\n" +
            "  left join sat.aud_AFILIACION_EMPLEADORES_MISMO_DEPARTAMENTO aud WITH(NOLOCK) on aud.id_afil_empl_mismo_departamento = b.id";
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
                    AfiliacionEmpleadoresMismoDptoDTO afiliacionEmpleadores = new AfiliacionEmpleadoresMismoDptoDTO();
                    afiliacionEmpleadores.setId(obj[0] != null ? new Long(obj[0].toString()) : 0L);
                    afiliacionEmpleadores.setRadicado(obj[1] != null ? obj[1].toString() : "");
                    afiliacionEmpleadores.setNumeroTransaccion(obj[2] != null ? obj[2].toString() : "");
                    afiliacionEmpleadores.setTipoPersona(obj[3] != null ? obj[3].toString() : "");
                    afiliacionEmpleadores.setNaturalezaJuridica(obj[4] != null ? new Long(obj[4].toString()) : 0L);
                    afiliacionEmpleadores.setTipoDocumentoEmpleador(obj[5] != null ? obj[5].toString() : "");
                    afiliacionEmpleadores.setNumeroDocumentoEmpleador(obj[6] != null ? obj[6].toString() : "");
                    afiliacionEmpleadores.setSerialSAT(obj[7] != null ? obj[7].toString() : "");
                    afiliacionEmpleadores.setPrimerNombreEmpleador(obj[8] != null ? obj[8].toString() : "");
                    afiliacionEmpleadores.setSegundoNombreEmpleador(obj[9] != null ? obj[9].toString() : "");
                    afiliacionEmpleadores.setPrimerApellidoEmpleador(obj[10] != null ? obj[10].toString() : "");
                    afiliacionEmpleadores.setSegundoApellidoEmpleador(obj[11] != null ? obj[11].toString() : "");
                    afiliacionEmpleadores.setFechaSolicitud(obj[12] != null ? obj[12].toString() : null);
                    afiliacionEmpleadores.setPerdidaAfiliacionCausaGrave(obj[13] != null ? obj[13].toString() : "");
                    afiliacionEmpleadores.setFechaEfectividad(obj[14] != null ? obj[14].toString() : null);
                    afiliacionEmpleadores.setRazonSocialEmpleador(obj[15] != null ? obj[15].toString() : "");
                    afiliacionEmpleadores.setNumeroMatriculaMercantil(obj[16] != null ? obj[16].toString() : "");
                    afiliacionEmpleadores.setDepartamentoCausaSalarios(obj[17] != null ? obj[17].toString() : "");
                    afiliacionEmpleadores.setMunicipioContacto(obj[18] != null ? obj[18].toString() : "");
                    afiliacionEmpleadores.setDireccionContacto(obj[19] != null ? obj[19].toString() : "");
                    afiliacionEmpleadores.setTelefonoContacto(obj[20] != null ? new Long(obj[20].toString()) : 0L);
                    afiliacionEmpleadores.setCorreoElectronicoContacto(obj[21] != null ? obj[21].toString() : "");
                    afiliacionEmpleadores.setTipoDocumentoRepresentanteLegal(obj[22] != null ? obj[22].toString() : "");
                    afiliacionEmpleadores.setNumeroDocumentoRepresentanteLegal(obj[23] != null ? obj[23].toString() : "");
                    afiliacionEmpleadores.setPrimerNombreRepresentanteLegal(obj[24] != null ? obj[24].toString() : "");
                    afiliacionEmpleadores.setSegundoNombreRepresentanteLegal(obj[25] != null ? obj[25].toString() : "");
                    afiliacionEmpleadores.setPrimerApellidoRepresentanteLegal(obj[26] != null ? obj[26].toString() : "");
                    afiliacionEmpleadores.setSegundoApellidoRepresentanteLegal(obj[27] != null ? obj[27].toString() : "");
                    afiliacionEmpleadores.setAutorizacionDatosPersonales(obj[28] != null ? obj[28].toString() : "");
                    afiliacionEmpleadores.setAutorizacionEnvioNotificaciones(obj[29] != null ? obj[29].toString() : "");
                    afiliacionEmpleadores.setManifestacionNoAfiliadoCaja(obj[30] != null ? obj[30].toString() : "");
                    afiliacionEmpleadores.setCodigoCajaAnterior(obj[31] != null ? obj[31].toString() : "");
                    afiliacionEmpleadores.setPazYSalvo(obj[32] != null ? obj[32].toString() : "");
                    afiliacionEmpleadores.setFechaPazYSalvo(obj[33] != null ? obj[33].toString() : "");
                    afiliacionEmpleadores.setEstado(obj[34] != null ? obj[34].toString() : "");
                    afiliacionEmpleadores.setMensajeAuditoria(obj[35] != null ? responseUtil.obtenerMensaje(obj[35].toString()) : "");
                    afiliacionEmpleadores.setGlosa(obj[35] != null ? responseUtil.obtenerCodigoGlosa(obj[35].toString()) : "");
                    respuesta.add(afiliacionEmpleadores);
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
    public List<AfiliacionEmpleadoresMismoDptoDTO> consultarAfiliacionEmpleadoresMismoDptoAuditoria(Long id){
        logger.info("Comienza llamado servicios consultarAfiliacionEmpleadoresAuditoria");
        List<AfiliacionEmpleadoresMismoDptoDTO> respuesta = new ArrayList<AfiliacionEmpleadoresMismoDptoDTO>();
        try{
            String sq = "select \n" +
                "afil.id,\n" +
                "radicado\n" +
                ",numeroTransaccion\n" +
                ",tipoPersona\n" +
                ",naturalezaJuridica\n" +
                ",tipoDocumentoEmpleador\n" +
                ",numeroDocumentoEmpleador\n" +
                ",serialSAT\n" +
                ",primerNombreEmpleador\n" +
                ",segundoNombreEmpleador\n" +
                ",primerApellidoEmpleador\n" +
                ",segundoApellidoEmpleador\n" +
                ",fechaSolicitud\n" +
                ",'' perdidaAfiliacionCausaGrave\n" +
                ",fechaEfectividad\n" +
                ",razonSocialEmpleador\n" +
                ",numeroMatriculaMercantil\n" +
                ",departamentoCausaSalarios\n" +
                ",municipioContacto\n" +
                ",direccionContacto\n" +
                ",telefonoContacto\n" +
                ",correoElectronicoContacto\n" +
                ",tipoDocumentoRepresentanteLegal\n" +
                ",numeroDocumentoRepresentanteLegal\n" +
                ",primerNombreRepresentanteLegal\n" +
                ",segundoNombreRepresentanteLegal\n" +
                ",primerApellidoRepresentanteLegal\n" +
                ",segundoApellidoRepresentanteLegal\n" +
                ",autorizacionDatosPersonales\n" +
                ",autorizacionEnvioNotificaciones\n" +
                ",manifestacionNoAfiliadoCaja\n" +
                ",codigoCajaAnterior, pazYSalvo, fechaPazYSalvo, \n" +   
                "case when aud_afil.error='SI' then 'Error' when aud_afil.error='NO' then 'Sin error' end estadoAuditoria,\n" +
                "RESPONSE mensaje,\n" +
                "aud_afil.fecha,\n" +
                "aud_afil.id id_auditoria, afil.observaciones\n"+
                "from sat.aud_AFILIACION_EMPLEADORES_MISMO_DEPARTAMENTO aud_afil\n" +
                "join sat.AFILIACION_EMPLEADORES_MISMO_DEPARTAMENTO afil on aud_afil.id_afil_empl_mismo_departamento = afil.id";
            
            String where = " WHERE afil.id="+id;
            
            Query query = entityManager.createNativeQuery(sq + where);
            List<Object[]> listado = query.getResultList();            
            for(Object[] obj:listado){
                try{
                    AfiliacionEmpleadoresMismoDptoDTO afiliacionEmpleadores = new AfiliacionEmpleadoresMismoDptoDTO();
                    afiliacionEmpleadores.setId(obj[0] != null ? new Long(obj[0].toString()) : 0L);
                    afiliacionEmpleadores.setRadicado(obj[1] != null ? obj[1].toString() : "");
                    afiliacionEmpleadores.setNumeroTransaccion(obj[2] != null ? obj[2].toString() : "");
                    afiliacionEmpleadores.setTipoPersona(obj[3] != null ? obj[3].toString() : "");
                    afiliacionEmpleadores.setNaturalezaJuridica(obj[4] != null ? new Long(obj[4].toString()) : 0L);
                    afiliacionEmpleadores.setTipoDocumentoEmpleador(obj[5] != null ? obj[5].toString() : "");
                    afiliacionEmpleadores.setNumeroDocumentoEmpleador(obj[6] != null ? obj[6].toString() : "");
                    afiliacionEmpleadores.setSerialSAT(obj[7] != null ? obj[7].toString() : "");
                    afiliacionEmpleadores.setPrimerNombreEmpleador(obj[8] != null ? obj[8].toString() : "");
                    afiliacionEmpleadores.setSegundoNombreEmpleador(obj[9] != null ? obj[9].toString() : "");
                    afiliacionEmpleadores.setPrimerApellidoEmpleador(obj[10] != null ? obj[10].toString() : "");
                    afiliacionEmpleadores.setSegundoApellidoEmpleador(obj[11] != null ? obj[11].toString() : "");
                    afiliacionEmpleadores.setFechaSolicitud(obj[12] != null ? obj[12].toString() : null);
                    afiliacionEmpleadores.setPerdidaAfiliacionCausaGrave(obj[13] != null ? obj[13].toString() : "");
                    afiliacionEmpleadores.setFechaEfectividad(obj[14] != null ? obj[14].toString() : null);
                    afiliacionEmpleadores.setRazonSocialEmpleador(obj[15] != null ? obj[15].toString() : "");
                    afiliacionEmpleadores.setNumeroMatriculaMercantil(obj[16] != null ? obj[16].toString() : "");
                    afiliacionEmpleadores.setDepartamentoCausaSalarios(obj[17] != null ? obj[17].toString() : "");
                    afiliacionEmpleadores.setMunicipioContacto(obj[18] != null ? obj[18].toString() : "");
                    afiliacionEmpleadores.setDireccionContacto(obj[19] != null ? obj[19].toString() : "");
                    afiliacionEmpleadores.setTelefonoContacto(obj[20] != null ? new Long(obj[20].toString()) : 0L);
                    afiliacionEmpleadores.setCorreoElectronicoContacto(obj[21] != null ? obj[21].toString() : "");
                    afiliacionEmpleadores.setTipoDocumentoRepresentanteLegal(obj[22] != null ? obj[22].toString() : "");
                    afiliacionEmpleadores.setNumeroDocumentoRepresentanteLegal(obj[23] != null ? obj[23].toString() : "");
                    afiliacionEmpleadores.setPrimerNombreRepresentanteLegal(obj[24] != null ? obj[24].toString() : "");
                    afiliacionEmpleadores.setSegundoNombreRepresentanteLegal(obj[25] != null ? obj[25].toString() : "");
                    afiliacionEmpleadores.setPrimerApellidoRepresentanteLegal(obj[26] != null ? obj[26].toString() : "");
                    afiliacionEmpleadores.setSegundoApellidoRepresentanteLegal(obj[27] != null ? obj[27].toString() : "");
                    afiliacionEmpleadores.setAutorizacionDatosPersonales(obj[28] != null ? obj[28].toString() : "");
                    afiliacionEmpleadores.setAutorizacionEnvioNotificaciones(obj[29] != null ? obj[29].toString() : "");
                    afiliacionEmpleadores.setManifestacionNoAfiliadoCaja(obj[30] != null ? obj[30].toString() : "");
                    afiliacionEmpleadores.setCodigoCajaAnterior(obj[31] != null ? obj[31].toString() : "");
                    afiliacionEmpleadores.setPazYSalvo(obj[32] != null ? obj[32].toString() : "");
                    afiliacionEmpleadores.setFechaPazYSalvo(obj[33] != null ? obj[33].toString() : "");
                    afiliacionEmpleadores.setEstadoAuditoria(obj[34] != null ? obj[34].toString() : "");                    
                    afiliacionEmpleadores.setMensajeAuditoria(obj[35] != null ? responseUtil.obtenerMensaje(obj[35].toString()) : "");
                    afiliacionEmpleadores.setFechaAuditoria(obj[36] != null ? obj[36].toString() : "");     
                    afiliacionEmpleadores.setIdAuditoria(obj[37] != null ? new Long(obj[37].toString()) : 0L);  
                    afiliacionEmpleadores.setObservacionesAuditoria(obj[38] != null ? obj[38].toString() : "");
                    afiliacionEmpleadores.setGlosa(obj[35] != null ? responseUtil.obtenerCodigoGlosa(obj[35].toString()) : "");
                    respuesta.add(afiliacionEmpleadores);
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
    public RespuestaEstandar cambiarEstadoAuditoriaAfiliacionEmpleadoresMismoDpto(
            Long id, Long idAuditoria, String estado, String observaciones){
        
        RespuestaEstandar respuesta = new RespuestaEstandar();
        
        String update = "UPDATE sat.AFILIACION_EMPLEADORES_MISMO_DEPARTAMENTO SET "
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
