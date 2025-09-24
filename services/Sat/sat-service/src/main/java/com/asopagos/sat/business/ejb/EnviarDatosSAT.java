/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.sat.business.ejb;

import co.gov.sed.sace.util.CorreoUtil;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.sat.business.interfaces.IEnviarDatosSAT;
import com.asopagos.sat.business.interfaces.IParametros;
import com.asopagos.sat.constantes.EnumParametros;
import com.asopagos.sat.dto.RespuestaEstandar;
import com.asopagos.sat.util.HttpUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.jose4j.json.JsonUtil;
import org.jose4j.json.internal.json_simple.JSONObject;

/**
 *
 * @author Sergio Reyes
 */
@Stateless
public class EnviarDatosSAT implements IEnviarDatosSAT,Serializable{

    private static final ILogger logger = LogManager.getLogger(EnviarDatosSAT.class);
    
    @PersistenceContext(unitName = "core_PU")
    private EntityManager entityManager;
    
    @Inject
    private IParametros parametros;
    
    @Override
    public List<RespuestaEstandar> enviarInformacionSAT() {
        List<RespuestaEstandar> respuesta = new ArrayList<>();
        try{
            logger.info("Comienza llamado servicios SAT");
            Map<String,String> param = parametros.parametrosSat();
            RespuestaEstandar res1 = reportarAfiliacionPrimeraVez(param);
            RespuestaEstandar res2 = reportarAfiliacionNoPrimeraVez(param);
            RespuestaEstandar res3 = reportarDesafiliacion(param);
            RespuestaEstandar res4 = reportarInicioRelacionLaboral(param);
            RespuestaEstandar res5 = reportarTerminacionRelacionLaboral(param);
            RespuestaEstandar res6 = reportarPerdidaAfiliacionCausaGrave(param);
            RespuestaEstandar res7 = reportarEstadoPagoEmpleador(param);
            logger.info("Termina llamado servicios SAT");
            respuesta.add(res1);
            respuesta.add(res2);
            respuesta.add(res3);
            respuesta.add(res4);
            respuesta.add(res5);
            respuesta.add(res6);
            respuesta.add(res7);
            logger.info("Comienza Notificacion Resultado");
            //enviarCorreoFinalizacion(respuesta, param);
            logger.info("Termina Notificacion Resultado");
        }catch(Exception e){
            logger.error(e);
            RespuestaEstandar error = new RespuestaEstandar();
            error.setElemento("General");
            error.setEstado("ERROR");
            error.adicionarMensaje(e.getMessage());
            respuesta.add(error);
        }
        return respuesta;
    }
    
    /**
     * FUNCIONES GENERALES
     */
    private Integer insertarEjecucionWs(String servicio,String estado,Integer procesado,Integer consulta,String observacion){
        try{
            Query query = entityManager.createNativeQuery("INSERT INTO sat.execution_ws(fecha_ejecucion,servicio_llamado,estado,cantidad_procesados,cantidad_consulta,observacion)\n" +
                            "values(GETDATE(),'"+servicio+"','"+estado+"',"+procesado+","+consulta+",'"+observacion+"')");
            query.executeUpdate();
            Query qMax = entityManager.createNativeQuery("SELECT MAX(ID) FROM sat.execution_ws");
            Integer id = (Integer) qMax.getSingleResult();
            return id;
        }catch(Exception e){
            logger.error(e);
            return 0;
        }
    }
    
    private void actualizarEjecucionWS(Integer id,Integer procesados,String estado,String observacion){
        try{
            Query query = entityManager.createNativeQuery("UPDATE sat.execution_ws SET estado = '"+estado+"',cantidad_procesados = "+procesados+",observacion = '"+observacion+"' WHERE ID ="+id);
            query.executeUpdate();
        }catch(Exception e){
            logger.error(e);
        }
    }
    
    private String getToken(Map<String,String> parametros,String nombreCLientId){
        try{
            Map<String,Object> urlParams = new HashMap<String,Object>();
            urlParams.put("username", parametros.get(EnumParametros.USUARIO_SAT.getNombre()));
            urlParams.put("password", parametros.get(EnumParametros.PASSWORD_SAT.getNombre()));
            urlParams.put("client_id", parametros.get(nombreCLientId));
            urlParams.put("grant_type", "password");
            Map<String,Object> respuesta = HttpUtil.sendHttpPost(parametros.get(EnumParametros.URL_TOKEN_SAT.getNombre()), urlParams);
            String token = "";
            if(respuesta != null && respuesta.get("status") != null && respuesta.get("status").toString().equals("OK")){
                String json = respuesta.get("mensaje").toString();
                Map<String,Object> retorno = JsonUtil.parseJson(json);
                token = retorno.get("access_token").toString();
                //System.out.println(retorno);
            }else{
                String json = respuesta.get("mensaje").toString();
                Map<String,Object> retorno = JsonUtil.parseJson(json);
                //System.out.println(retorno);
                token = null;
            }
            return token;
        }catch(Exception e){
            logger.error(e);
            return null;
        }
    }
    
    /**
     * FIN FUNCIONES GENERALES
     */
    
    
    /**
     *  REPORTAR AFILIACION PRIMERA VEZ
     */
    
    //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private RespuestaEstandar reportarAfiliacionPrimeraVez(Map<String,String> parametros){
        logger.info("Comienza llamado servicios reportarAfiliacionPrimeraVez");
        RespuestaEstandar respuesta = new RespuestaEstandar();
        try{
            //Se cambia temporalmente la siguiente consulta para la ejecución por paquetes de los registros de SAT -- GLPI 71469
            /*
            String sq = "SELECT id,radicado,numeroTransaccion,tipoPersona," +
                                            "       naturalezaJuridica,tipoDocumentoEmpleador,numeroDocumentoEmpleador," +
                                            "	   serialSAT,primerNombreEmpleador,segundoNombreEmpleador," +
                                            "	   primerApellidoEmpleador,segundoApellidoEmpleador,fechaSolicitud," +
                                            "	   perdidaAfiliacionCausaGrave,fechaEfectividad,razonSocialEmpleador," +
                                            "	   numeroMatriculaMercantil,departamentoCausaSalarios,municipioContacto," +
                                            "	   direccionContacto,telefonoMunicipioCausaSalarios,correoElectronicoContacto," +
                                            "	   tipoDocumentoRepresentanteLegal,numeroDocumentoRepresentanteLegal,primerNombreRepresentanteLegal," +
                                            "	   segundoNombreRepresentanteLegal,primerApellidoRepresentanteLegal,segundoApellidoRepresentanteLegal," +
                                            "	   autorizacionDatosPersonales,autorizacionEnvioNotificaciones,manifestacionNoAfiliadoCaja " +
                                            " FROM SAT.AFILIACION_EMPLEADORES WITH(NOLOCK) WHERE PROCESO_ENVIADO_SAT IS NULL";
            */
            String sq = "SELECT TOP 300 " +
                                "aem.id, aem.radicado, aem.numeroTransaccion, aem.tipoPersona, " +
                                "aem.naturalezaJuridica, aem.tipoDocumentoEmpleador, aem.numeroDocumentoEmpleador, " +
                                "aem.serialSAT, aem.primerNombreEmpleador, aem.segundoNombreEmpleador, " +
                                "aem.primerApellidoEmpleador, aem.segundoApellidoEmpleador, aem.fechaSolicitud, " +
                                "aem.perdidaAfiliacionCausaGrave, aem.fechaEfectividad, aem.razonSocialEmpleador, " +
                                "aem.numeroMatriculaMercantil, aem.departamentoCausaSalarios, aem.municipioContacto, " +
                                "aem.direccionContacto, aem.telefonoMunicipioCausaSalarios, aem.correoElectronicoContacto, " +
                                "aem.tipoDocumentoRepresentanteLegal, aem.numeroDocumentoRepresentanteLegal, aem.primerNombreRepresentanteLegal, " +
                                "aem.segundoNombreRepresentanteLegal, aem.primerApellidoRepresentanteLegal, aem.segundoApellidoRepresentanteLegal, " +
                                "aem.autorizacionDatosPersonales, aem.autorizacionEnvioNotificaciones, aem.manifestacionNoAfiliadoCaja " +
                        "FROM SAT.AFILIACION_EMPLEADORES aem WITH(NOLOCK) " +
                        "LEFT JOIN SAT.AUD_AFILIACION_EMPLEADORES aud WITH(NOLOCK) ON aem.id = aud.id_afiliacion_empleadores " +
                        "WHERE aem.proceso_enviado_sat IS NULL";
            Query query = entityManager.createNativeQuery(sq);
            List<Object[]> listado = query.getResultList();
            Integer idProceso = insertarEjecucionWs(parametros.get(EnumParametros.URL_AFIL_PRIM_SAT.getNombre()), "Iniciando", 0, listado.size(), "No Aplica");
            logger.info("idProceso:"+idProceso);
            Integer exitosos = 0;
            Integer fallidos = 0;
            for(Object[] obj:listado){
                try{
                    Map<String,String> objeto = new HashMap<String,String>();
                    objeto.put("numeroRadicadoSolicitud", obj[1] != null?obj[1].toString():"");
                    objeto.put("numeroTransaccion", obj[2] != null?obj[2].toString():"");
                    objeto.put("tipoPersona", obj[3] != null?obj[3].toString():"");
                    if(obj[4] != null){
                        if(obj[4].toString().equals("0") || obj[4].toString().equals("")){
                            objeto.put("naturalezaJuridicaEmpleador", "");
                        }else{
                            objeto.put("naturalezaJuridicaEmpleador", obj[4].toString());
                        }
                    }else{
                        objeto.put("naturalezaJuridicaEmpleador", "");
                    }
                    objeto.put("tipoDocumentoEmpleador", obj[5] != null?obj[5].toString():"");
                    objeto.put("numeroDocumentoEmpleador", obj[6] != null?obj[6].toString():"");
                    objeto.put("serialSAT", obj[7] != null?obj[7].toString():"");
                    objeto.put("primerNombreEmpleador", obj[8] != null?obj[8].toString():"");
                    objeto.put("segundoNombreEmpleador", obj[9] != null?obj[9].toString():"");
                    objeto.put("primerApellidoEmpleador", obj[10] != null?obj[10].toString():"");
                    objeto.put("segundoApellidoEmpleador", obj[11] != null?obj[11].toString():"");
                    objeto.put("fechaSolicitud", obj[12] != null?obj[12].toString():"");
                    objeto.put("perdidaAfiliacionCausaGrave", "");
                    objeto.put("fechaEfectivaAfiliacion", obj[14] != null?obj[14].toString():"");
                    objeto.put("razonSocial", obj[15] != null?obj[15].toString():"");
                    objeto.put("numeroMatriculaMercantil", obj[16] != null?obj[16].toString():"");
                    objeto.put("departamento", obj[17] != null?obj[17].toString():"");
                    objeto.put("municipio", obj[18] != null?obj[18].toString():"");
                    objeto.put("direccionContacto", obj[19] != null?obj[19].toString():"");
                    objeto.put("numeroTelefono", obj[20] != null?obj[20].toString():"");
                    objeto.put("correoElectronico", obj[21] != null?obj[21].toString():"");
                    objeto.put("tipoDocumentoRepresentante", obj[22] != null?obj[22].toString():"");
                    objeto.put("numeroDocumentoRepresentante", obj[23] != null?obj[23].toString():"");
                    objeto.put("primerNombreRepresentante", obj[24] != null?obj[24].toString():"");
                    objeto.put("segundoNombreRepresentante", obj[25] != null?obj[25].toString():"");
                    objeto.put("primerApellidoRepresentante", obj[26] != null?obj[26].toString():"");
                    objeto.put("segundoApellidoRepresentante", obj[27] != null?obj[27].toString():"");
                    objeto.put("autorizacionManejoDatos", obj[28] != null?obj[28].toString():"");
                    objeto.put("autorizacionNotificaciones", obj[29] != null?obj[29].toString():"");
                    objeto.put("manifestacion", obj[30] != null?obj[30].toString():"");
                    /*objeto.put("Resultado", "");
                    objeto.put("Mensaje", "");
                    objeto.put("Codigo", "");*/

                    String json = JsonUtil.toJson(objeto);
                    String token = getToken(parametros,EnumParametros.CLIENT_ID_AFIL_PRIM_SAT.getNombre());
                    String wsResponse = HttpUtil.sendHttpPostJSON(parametros.get(EnumParametros.URL_AFIL_PRIM_SAT.getNombre()), json, token);
                    if(wsResponse.contains("WS-ERROR -")){
                        fallidos++;
                        guardarBitacoraAfiliacionPrimeraVez(new Integer(obj[0].toString()), "SI",parametros.get(EnumParametros.URL_AFIL_PRIM_SAT.getNombre()),json,wsResponse);
                        logger.info("APV - WS-ERROR - "+ "id: "+ obj[0].toString() +" - Numero fallidos: " + fallidos);
                    }else{
                        Map<String,Object> retorno = JsonUtil.parseJson(wsResponse);
                        if(retorno.get("resultado").toString().equals("200") || retorno.get("codigo").toString().equals("200") || retorno.get("codigo").toString().contains("AN")){
                            actualizarIdEjecucionAfiliacionPrimeraVez(new Integer(obj[0].toString()), idProceso);
                            guardarBitacoraAfiliacionPrimeraVez(new Integer(obj[0].toString()), "NO",parametros.get(EnumParametros.URL_AFIL_PRIM_SAT.getNombre()),json,wsResponse);
                            exitosos++;
                            logger.info("APV - OK 200 - "+ "id: "+ obj[0].toString() +" - Numero exitosos: " + exitosos);
                        }else{
                            guardarBitacoraAfiliacionPrimeraVez(new Integer(obj[0].toString()), "SI",parametros.get(EnumParametros.URL_AFIL_PRIM_SAT.getNombre()),json,wsResponse);
                            fallidos++;
                            logger.info("APV - Error <> 200 - "+ "id: "+ obj[0].toString() +" - Numero fallidos: " + fallidos);
                        }
                    }
                }catch(Exception e){
                    guardarBitacoraAfiliacionPrimeraVez(new Integer(obj[0].toString()), "SI",parametros.get(EnumParametros.URL_AFIL_PRIM_SAT.getNombre()),"",e.getMessage());
                    fallidos++;
                    logger.info("APV - Exception " + e);
                    logger.info("Id: " + obj[15].toString() + " - Numero fallidos: " + fallidos);
                }
            }
            if(exitosos == listado.size()){
                actualizarEjecucionWS(idProceso, exitosos, "Finalizado correctamente", "Finalizado correctamente");
                respuesta.setEstado("OK");
                respuesta.setElemento(EnumParametros.URL_AFIL_PRIM_SAT.getNombre());
                respuesta.adicionarMensaje("Finalizado correctamente");
            }else{
                actualizarEjecucionWS(idProceso, exitosos, "Finalizado con Errores", "Se procesaron:"+exitosos+", con errores:"+fallidos);
                respuesta.setEstado("ADVERTENCIA");
                respuesta.setElemento(EnumParametros.URL_AFIL_PRIM_SAT.getNombre());
                respuesta.adicionarMensaje("Se procesaron:"+exitosos+", con errores:"+fallidos);
            }
            respuesta.setExitosos(exitosos);
            respuesta.setFallidos(fallidos);
        }catch(Exception e){
            logger.error(e);
            respuesta.setEstado("ERROR");
            respuesta.setElemento(EnumParametros.URL_AFIL_PRIM_SAT.getNombre());
            respuesta.adicionarMensaje(e.getMessage());
        }
        logger.info("Termina llamado servicios reportarAfiliacionPrimeraVez");
        return respuesta;
    }
    
    
    private void guardarBitacoraAfiliacionPrimeraVez(Integer idAfiliacionEmpleadores,String error,String url,String request,String response){
        try{
            request = request.replace("'", "''");
            response = response.replace("'", "''");
            Query query = entityManager.createNativeQuery("INSERT INTO SAT.AUD_AFILIACION_EMPLEADORES(fecha,id_afiliacion_empleadores,error,URL,REQUEST,RESPONSE)values(getdate(),"+idAfiliacionEmpleadores+",'"+error+"','"+url+"','"+request+"','"+response+"')");
            query.executeUpdate();
        }catch(Exception e){
            logger.error(e);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private void actualizarIdEjecucionAfiliacionPrimeraVez(Integer idAfiliacionEmpleadores,Integer idEjecucion){
        try{
             Query query = entityManager.createNativeQuery("UPDATE SAT.AFILIACION_EMPLEADORES SET proceso_enviado_sat ="+idEjecucion+" WHERE id ="+idAfiliacionEmpleadores);
            query.executeUpdate();
        }catch(Exception e){
            logger.error(e);
        }
    }
    
    /**
     * REPORTAR AFILIACION NO PRIMERA VEZ
     */
    
    //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private RespuestaEstandar reportarAfiliacionNoPrimeraVez(Map<String,String> parametros){
        logger.info("Comienza llamado servicios reportarAfiliacionNoPrimeraVez");
        RespuestaEstandar respuesta = new RespuestaEstandar();
        try{
            
            //Se cambia temporalmente la siguiente consulta para la ejecución por paquetes de los registros de SAT -- GLPI 71469
            /*
            String sq = "SELECT id,radicado,numeroTransaccion," +
                                "tipoPersona,naturalezaJuridica,tipoDocumentoEmpleador," +
                                "numeroDocumentoEmpleador,serialSAT,primerNombreEmpleador," +
                                "segundoNombreEmpleador,primerApellidoEmpleador,segundoApellidoEmpleador," +
                                "fechaSolicitud,perdidaAfiliacionCausaGrave,fechaEfectividad," +
                                "razonSocialEmpleador,numeroMatriculaMercantil, departamentoCausaSalarios," +
                                "municipioContacto,direccionContacto,telefonoContacto," +
                                "correoElectronicoContacto,tipoDocumentoRepresentanteLegal,numeroDocumentoRepresentanteLegal," +
                                "primerNombreRepresentanteLegal,segundoNombreRepresentanteLegal,primerApellidoRepresentanteLegal," +
                                "segundoApellidoRepresentanteLegal,codigoCajaAnterior,pazySalvo," +
                                "fechaPazYSalvo,autorizacionDatosPersonales,autorizacionEnvioNotificaciones," +
                                "manifestacionNoAfiliadoCaja " +
                                " FROM SAT.AFILIACION_EMPLEADORES_MISMO_DEPARTAMENTO  WITH(NOLOCK) WHERE PROCESO_ENVIADO_SAT IS NULL";
            */
            String sq = "SELECT TOP 300 " +
                                "amd.id, amd.radicado, amd.numeroTransaccion, " +
                                "amd.tipoPersona, amd.naturalezaJuridica, amd.tipoDocumentoEmpleador, " +
                                "amd.numeroDocumentoEmpleador, amd.serialSAT, amd.primerNombreEmpleador, " +
                                "amd.segundoNombreEmpleador, amd.primerApellidoEmpleador, amd.segundoApellidoEmpleador, " +
                                "amd.fechaSolicitud, amd.perdidaAfiliacionCausaGrave, amd.fechaEfectividad, " +
                                "amd.razonSocialEmpleador, amd.numeroMatriculaMercantil, amd.departamentoCausaSalarios, " +
                                "amd.municipioContacto, amd.direccionContacto, amd.telefonoContacto, " +
                                "amd.correoElectronicoContacto, amd.tipoDocumentoRepresentanteLegal, amd.numeroDocumentoRepresentanteLegal, " +
                                "amd.primerNombreRepresentanteLegal, amd.segundoNombreRepresentanteLegal, amd.primerApellidoRepresentanteLegal, " +
                                "amd.segundoApellidoRepresentanteLegal, amd.codigoCajaAnterior, amd.pazySalvo, " +
                                "amd.fechaPazYSalvo, amd.autorizacionDatosPersonales, amd.autorizacionEnvioNotificaciones, " +
                                "amd.manifestacionNoAfiliadoCaja " +
                        "FROM SAT.AFILIACION_EMPLEADORES_MISMO_DEPARTAMENTO amd WITH(NOLOCK) " +
                        "LEFT JOIN SAT.AUD_AFILIACION_EMPLEADORES_MISMO_DEPARTAMENTO aud WITH(NOLOCK) ON amd.id = aud.id_afil_empl_mismo_departamento " +
                        "WHERE amd.proceso_enviado_sat IS NULL";
            Query query = entityManager.createNativeQuery(sq);
            List<Object[]> listado = query.getResultList();
            Integer idProceso = insertarEjecucionWs(parametros.get(EnumParametros.URL_AFIL_NO_PRIM_SAT.getNombre()), "Iniciando", 0, listado.size(), "No Aplica");
            logger.info("idProceso:"+idProceso);
            Integer exitosos = 0;
            Integer fallidos = 0;
            for(Object[] obj:listado){
                try{
                    Map<String,String> objeto = new HashMap<String,String>();
                    objeto.put("numeroRadicadoSolicitud", obj[1] != null?obj[1].toString():"");
                    objeto.put("numeroTransaccion", obj[2] != null?obj[2].toString():"");
                    objeto.put("tipoPersona", obj[3] != null?obj[3].toString():"");
                    if(obj[4] != null){
                        if(obj[4].toString().equals("0") || obj[4].toString().equals("")){
                            objeto.put("naturalezaJuridicaEmpleador", "");
                        }else{
                            objeto.put("naturalezaJuridicaEmpleador", obj[4].toString());
                        }
                    }else{
                        objeto.put("naturalezaJuridicaEmpleador", "");
                    }
                    objeto.put("tipoDocumentoEmpleador", obj[5] != null?obj[5].toString():"");
                    objeto.put("numeroDocumentoEmpleador", obj[6] != null?obj[6].toString():"");
                    objeto.put("serialSAT", obj[7] != null?obj[7].toString():"");
                    objeto.put("primerNombreEmpleador", obj[8] != null?obj[8].toString():"");
                    objeto.put("segundoNombreEmpleador", obj[9] != null?obj[9].toString():"");
                    objeto.put("primerApellidoEmpleador", obj[10] != null?obj[10].toString():"");
                    objeto.put("segundoApellidoEmpleador", obj[11] != null?obj[11].toString():"");
                    objeto.put("fechaSolicitud", obj[12] != null?obj[12].toString():"");
                    objeto.put("perdidaAfiliacionCausaGrave", "");
                    objeto.put("fechaEfectivaAfiliacion", obj[14] != null?obj[14].toString():"");
                    objeto.put("razonSocial", obj[15] != null?obj[15].toString():"");
                    objeto.put("numeroMatriculaMercantil", obj[16] != null?obj[16].toString():"");
                    objeto.put("departamento", obj[17] != null?obj[17].toString():"");
                    objeto.put("municipio", obj[18] != null?obj[18].toString():"");
                    objeto.put("direccionContacto", obj[19] != null?obj[19].toString():"");
                    objeto.put("numeroTelefono", obj[20] != null?obj[20].toString():"");
                    objeto.put("correoElectronico", obj[21] != null?obj[21].toString():"");
                    objeto.put("tipoDocumentoRepresentante", obj[22] != null?obj[22].toString():"");
                    objeto.put("numeroDocumentoRepresentante", obj[23] != null?obj[23].toString():"");
                    objeto.put("primerNombreRepresentante", obj[24] != null?obj[24].toString():"");
                    objeto.put("segundoNombreRepresentante", obj[25] != null?obj[25].toString():"");
                    objeto.put("primerApellidoRepresentante", obj[26] != null?obj[26].toString():"");
                    objeto.put("segundoApellidoRepresentante", obj[27] != null?obj[27].toString():"");
                    objeto.put("codigoCajaCompensacionFamiliarAnterior", obj[28] != null?obj[28].toString():"");
                    //objeto.put("PazYSalvo", obj[29] != null?obj[29].toString():"");
                    objeto.put("pazYSalvo", "");
                    //objeto.put("FechaPazYSalvo", obj[30] != null?obj[30].toString():"");
                    objeto.put("fechaPazYSalvo", "");
                    objeto.put("autorizacionManejoDatos", obj[31] != null?obj[31].toString():"");
                    objeto.put("autorizacionNotificaciones", obj[32] != null?obj[32].toString():"");
                    objeto.put("manifestacion", obj[33] != null?obj[33].toString():"");
                    /*objeto.put("Resultado", "");
                    objeto.put("Mensaje", "");
                    objeto.put("Codigo", "");*/

                    String json = JsonUtil.toJson(objeto);
                    String token = getToken(parametros,EnumParametros.CLIENT_ID_NO_AFIL_PRIM_SAT.getNombre());
                    String wsResponse = HttpUtil.sendHttpPostJSON(parametros.get(EnumParametros.URL_AFIL_NO_PRIM_SAT.getNombre()), json, token);
                    if(wsResponse.contains("WS-ERROR -")){
                        fallidos++;
                        guardarBitacoraAfiliacionNoPrimeraVez(new Integer(obj[0].toString()),"SI",parametros.get(EnumParametros.URL_AFIL_NO_PRIM_SAT.getNombre()),json, wsResponse);
                        logger.info("ANPV - WS-ERROR - "+ "id: "+ obj[0].toString() +" - Numero fallidos: " + fallidos);
                    }else{
                        Map<String,Object> retorno = JsonUtil.parseJson(wsResponse);
                        if(retorno.get("resultado").toString().equals("200") || retorno.get("codigo").toString().equals("200") || retorno.get("codigo").toString().contains("AN")){
                            actualizarIdEjecucionAfiliacionNoPrimeraVez(new Integer(obj[0].toString()), idProceso);
                            guardarBitacoraAfiliacionNoPrimeraVez(new Integer(obj[0].toString()),"NO",parametros.get(EnumParametros.URL_AFIL_NO_PRIM_SAT.getNombre()),json, wsResponse);
                            exitosos++;
                            logger.info("ANPV - OK 200 - "+ "id: "+ obj[0].toString() +" - Numero exitosos: " + exitosos); 
                        }else{
                            guardarBitacoraAfiliacionNoPrimeraVez(new Integer(obj[0].toString()),"SI",parametros.get(EnumParametros.URL_AFIL_NO_PRIM_SAT.getNombre()),json, wsResponse);
                            fallidos++;
                            logger.info("ANPV - Error <> 200 - "+ "id: "+ obj[0].toString() +" - Numero fallidos: " + fallidos);
                        }
                    }
                }catch(Exception e){
                    guardarBitacoraAfiliacionNoPrimeraVez(new Integer(obj[0].toString()),"SI",parametros.get(EnumParametros.URL_AFIL_NO_PRIM_SAT.getNombre()),"", e.getMessage());
                    fallidos++;
                    logger.info("ANPV - Exception " + e);
                    logger.info("Id: " + obj[0].toString() + " - Numero fallidos: " + fallidos);
                }
            }
            if(exitosos == listado.size()){
                actualizarEjecucionWS(idProceso, exitosos, "Finalizado correctamente", "Finalizado correctamente");
                respuesta.setEstado("OK");
                respuesta.setElemento(EnumParametros.URL_AFIL_NO_PRIM_SAT.getNombre());
                respuesta.adicionarMensaje("Finalizado correctamente");
            }else{
                actualizarEjecucionWS(idProceso, exitosos, "Finalizado con Errores", "Se procesaron:"+exitosos+", con errores:"+fallidos);
                respuesta.setEstado("ADVERTENCIA");
                respuesta.setElemento(EnumParametros.URL_AFIL_NO_PRIM_SAT.getNombre());
                respuesta.adicionarMensaje("Se procesaron:"+exitosos+", con errores:"+fallidos);
            }
            respuesta.setExitosos(exitosos);
            respuesta.setFallidos(fallidos);
        }catch(Exception e){
            logger.error(e);
            respuesta.setEstado("ERROR");
            respuesta.setElemento(EnumParametros.URL_AFIL_NO_PRIM_SAT.getNombre());
            respuesta.adicionarMensaje(e.getMessage());
        }
        logger.info("Termina llamado servicios reportarAfiliacionNoPrimeraVez");
        return respuesta;
    }
    
    private void guardarBitacoraAfiliacionNoPrimeraVez(Integer idAfiliacionEmpleadoresMismoDep,String error,String url,String request,String response){
        try{
            request = request.replace("'", "''");
            response = response.replace("'", "''");
            String sq = "INSERT INTO SAT.AUD_AFILIACION_EMPLEADORES_MISMO_DEPARTAMENTO(fecha,id_afil_empl_mismo_departamento,error,URL,REQUEST,RESPONSE)values(getdate(),"+idAfiliacionEmpleadoresMismoDep+",'"+error+"','"+url+"','"+request+"','"+response+"')";
             Query query = entityManager.createNativeQuery(sq);
            query.executeUpdate();
        }catch(Exception e){
            logger.error(e);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private void actualizarIdEjecucionAfiliacionNoPrimeraVez(Integer idAfiliacionEmpleadoresMismoDep,Integer idEjecucion){
        try{
             Query query = entityManager.createNativeQuery("UPDATE SAT.AFILIACION_EMPLEADORES_MISMO_DEPARTAMENTO SET proceso_enviado_sat ="+idEjecucion+" WHERE id ="+idAfiliacionEmpleadoresMismoDep);
            query.executeUpdate();
        }catch(Exception e){
            logger.error(e);
        }
    }
    
    /**
     * REPORTE DESAFILIACION
     */
    //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private RespuestaEstandar reportarDesafiliacion(Map<String,String> parametros){
        logger.info("Comienza llamado servicios reportarDesafiliacion");
        RespuestaEstandar respuesta = new RespuestaEstandar();
        try{
            /*
            String sq = "SELECT id,radicado,numeroTransaccion,tipoDocumentoEmpleador," +
                                    "numeroDocumentoEmpleador,serialSAT,fechaSolicitudDesafiliacion," +
                                    "fechaEfectividadDesafiliacion,departamentoAfiliacion,autorizacionDatosPersonales," +
                                    "autorizacionEnvioNotificaciones,pazYSalvo,fechaPazYSalvo " +
                                    " FROM SAT.DESAFILIACION WITH(NOLOCK) WHERE PROCESO_ENVIADO_SAT IS NULL";
            */
            String sq = "SELECT TOP 300 " +
                                "daf.id, daf.radicado, daf.numeroTransaccion, daf.tipoDocumentoEmpleador, " +
                                "daf.numeroDocumentoEmpleador, daf.serialSAT, daf.fechaSolicitudDesafiliacion, " +
                                "daf.fechaEfectividadDesafiliacion, daf.departamentoAfiliacion, daf.autorizacionDatosPersonales, " +
                                "daf.autorizacionEnvioNotificaciones, daf.pazYSalvo, daf.fechaPazYSalvo " +
                        "FROM SAT.DESAFILIACION daf WITH(NOLOCK) " +
                        "LEFT JOIN SAT.AUD_DESAFILIACION aud WITH(NOLOCK) ON daf.id = aud.id_desafiliacion " +
                        "WHERE daf.proceso_enviado_sat IS NULL";
            Query query = entityManager.createNativeQuery(sq);
            List<Object[]> listado = query.getResultList();
            Integer idProceso = insertarEjecucionWs(parametros.get(EnumParametros.URL_DESAFILIACION_SAT.getNombre()), "Iniciando", 0, listado.size(), "No Aplica");
            logger.info("idProceso:"+idProceso);
            Integer exitosos = 0;
            Integer fallidos = 0;
            for(Object[] obj:listado){
                try{
                    Map<String,String> objeto = new HashMap<String,String>();
                    objeto.put("numeroRadicadoSolicitud", obj[1] != null?obj[1].toString():"");
                    objeto.put("numeroTransaccion", obj[2] != null?obj[2].toString():"");
                    objeto.put("tipoDocumentoEmpleador", obj[3] != null?obj[3].toString():"");
                    objeto.put("numeroDocumentoEmpleador", obj[4] != null?obj[4].toString():"");
                    objeto.put("serialSAT", obj[5] != null?obj[5].toString():"");
                    objeto.put("fechaSolicitud", obj[6] != null?obj[6].toString():"");
                    objeto.put("fechaEfectivaDesafiliacion", obj[7] != null?obj[7].toString():"");
                    objeto.put("departamento", obj[8] != null?obj[8].toString():"");
                    objeto.put("autorizacionManejoDatos", obj[9] != null?obj[9].toString():"");
                    objeto.put("autorizacionNotificaciones", obj[10] != null?obj[10].toString():"");
                    objeto.put("pazSalvo", obj[11] != null?obj[11].toString():"");  
                    objeto.put("fechaPazSalvo", obj[12] != null?obj[12].toString():"");  

                    String json = JsonUtil.toJson(objeto);
                    String token = getToken(parametros,EnumParametros.CLIENT_ID_DESAFILIACION_SAT.getNombre());
                    logger.info("JSON: " + json);
                    logger.info("Token: " + token);
                    String wsResponse = HttpUtil.sendHttpPostJSON(parametros.get(EnumParametros.URL_DESAFILIACION_SAT.getNombre()), json, token);
                    if(wsResponse.contains("WS-ERROR -")){
                        fallidos++;
                        guardarBitacoraDesafiliacion(new Integer(obj[0].toString()),"SI",parametros.get(EnumParametros.URL_DESAFILIACION_SAT.getNombre()),json, wsResponse);
                        logger.info("DES - WS-ERROR - "+ "id: "+ obj[0].toString() +" - Numero fallidos: " + fallidos); 
                    }else{
                        Map<String,Object> retorno = JsonUtil.parseJson(wsResponse);
                        if(retorno.get("resultado").toString().equals("200") || retorno.get("codigo").toString().equals("200") || retorno.get("codigo").toString().contains("AN")){
                            actualizarIdEjecucionDesafiliacion(new Integer(obj[0].toString()), idProceso);
                            guardarBitacoraDesafiliacion(new Integer(obj[0].toString()),"NO",parametros.get(EnumParametros.URL_DESAFILIACION_SAT.getNombre()),json, wsResponse);
                            exitosos++;
                            logger.info("DES - OK 200 - "+ "id: "+ obj[0].toString() +" - Numero exitosos: " + exitosos);
                        }else{
                            fallidos++;
                            guardarBitacoraDesafiliacion(new Integer(obj[0].toString()),"SI",parametros.get(EnumParametros.URL_DESAFILIACION_SAT.getNombre()),json, wsResponse);
                            logger.info("DES - Error <> 200 - "+ "id: "+ obj[0].toString() +" - Numero fallidos: " + fallidos);
                        }
                    }
                }catch(Exception e){
                    guardarBitacoraDesafiliacion(new Integer(obj[0].toString()),"SI",parametros.get(EnumParametros.URL_DESAFILIACION_SAT.getNombre()),"", e.getMessage());
                    fallidos++;
                    logger.info("DES - Exception " + e);
                    logger.info("Id: " + obj[0].toString() + " - Numero fallidos: " + fallidos);
                }
            }
            if(exitosos == listado.size()){
                actualizarEjecucionWS(idProceso, exitosos, "Finalizado correctamente", "Finalizado correctamente");
                respuesta.setEstado("OK");
                respuesta.setElemento(EnumParametros.URL_DESAFILIACION_SAT.getNombre());
                respuesta.adicionarMensaje("Finalizado correctamente");
            }else{
                actualizarEjecucionWS(idProceso, exitosos, "Finalizado con Errores", "Se procesaron:"+exitosos+", con errores:"+fallidos);
                respuesta.setEstado("ADVERTENCIA");
                respuesta.setElemento(EnumParametros.URL_DESAFILIACION_SAT.getNombre());
                respuesta.adicionarMensaje("Se procesaron:"+exitosos+", con errores:"+fallidos);
            }
            respuesta.setExitosos(exitosos);
            respuesta.setFallidos(fallidos);
        }catch(Exception e){
            logger.error(e);
            respuesta.setEstado("ERROR");
            respuesta.setElemento(EnumParametros.URL_DESAFILIACION_SAT.getNombre());
            respuesta.adicionarMensaje(e.getMessage());
        }
        logger.info("Termina llamado servicios reportarDesafiliacion");
        return respuesta;
    }
    
    
    private void guardarBitacoraDesafiliacion(Integer idDesafiliacion,String error,String url,String request,String response){
        try{
            request = request.replace("'", "''");
            response = response.replace("'", "''");
             Query query = entityManager.createNativeQuery("INSERT INTO SAT.AUD_DESAFILIACION(fecha,id_desafiliacion,error,URL,REQUEST,RESPONSE)values(getdate(),"+idDesafiliacion+",'"+error+"','"+url+"','"+request+"','"+response+"')");
            query.executeUpdate();
        }catch(Exception e){
            logger.error(e);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private void actualizarIdEjecucionDesafiliacion(Integer idDesafiliacion,Integer idEjecucion){
        try{
             Query query = entityManager.createNativeQuery("UPDATE SAT.DESAFILIACION SET proceso_enviado_sat ="+idEjecucion+" WHERE id ="+idDesafiliacion);
            query.executeUpdate();
        }catch(Exception e){
            logger.error(e);
        }
    }
    
    private void enviarCorreoFinalizacion(List<RespuestaEstandar> respuestas,Map<String,String> params){
        try{
            Map<String,String> pc = parametros.parametrosCorreo();
            CorreoUtil util = new CorreoUtil(pc.get(ParametrosSistemaConstants.MAIL_SMTP_SENDPARTIAL), 
                    pc.get(ParametrosSistemaConstants.MAIL_SMTP_AUTH), 
                    pc.get(ParametrosSistemaConstants.MAIL_SMTP_SSL), 
                    pc.get(ParametrosSistemaConstants.MAIL_SMTP_HOST),
                    pc.get(ParametrosSistemaConstants.MAIL_SMTP_PORT),
                    pc.get(ParametrosSistemaConstants.MAIL_SMTP_USER),
                    pc.get(ParametrosSistemaConstants.MAIL_SMTP_FROM),
                    pc.get(ParametrosSistemaConstants.MAIL_SMTP_PASSWORD),
                    pc.get(ParametrosSistemaConstants.MAIL_SMTP_FROM_NAME));
            
            // Preparar Notificacion
            Map<String,Object> notificacion = new HashMap<String,Object>();
            notificacion.put("asunto", "Resultado Proceso Envio SAT");
            
            Map<String,List<String>> destinatarios = new HashMap<String,List<String>>();
            String correos = params.get(EnumParametros.CORREO_NOTIFICACION_SAT.getNombre());
            String arrC[] = correos.split(",");
            destinatarios.put("TO", Arrays.asList(arrC));
            notificacion.put("destinatarios", destinatarios);
            
            String msjInicio = "<p>Buen d&iacute;a</p><p>Se realiza consumo de los servicios SAT de acuerdo a la siguiente especificaci&oacute;n:</p><ul>";
            String msjRes = "";
            for(RespuestaEstandar res : respuestas){
                msjRes += "<li>"+res.getElemento() +", Exitoso:"+res.getExitosos()+", Fallidos:"+res.getFallidos()+"</li>";
            }
            String msjFinal = "</ul><p>Cordial Saludo,</p><p>Administrador del Sistema</p>";
            
            notificacion.put("mensaje", msjInicio+msjRes+msjFinal);
            util.enviarCorreoUtil(notificacion);
        }catch(Exception e){
            logger.error(e);
        }
    }
    
    /**
     * REPORTE INICIO RELACION LABORAL
     */
    
    private RespuestaEstandar reportarInicioRelacionLaboral(Map<String,String> parametros){
        logger.info("Comienza llamado servicios reportarInicioRelacionLaboral");
        RespuestaEstandar respuesta = new RespuestaEstandar();
        try{
            //Se cambia temporalmente la siguiente consulta para la ejecución por paquetes de los registros de SAT -- GLPI 71469
            /*
            String sq = "select id,tipoInicio,fechaInicio,segundoNombreTrabajador, " +
                        "                        segundoApellidoTrabajador,sexoTrabajador, '' as fechaNacimientoTrabajador, " +
                        "                        departamentoCausaSalarios,municipioCausaSalarios,direccionMunicipioCausaSalarios, " +
                        "                        telefonoMunicipioCausaSalarios,correoElectronicoContactoTrabajador,salario,tipoSalario,horasTrabajoMensuales, " +
                        "                        radicado,numeroTransaccion,tipoDocumentoEmpleador,numeroDocumentoEmpleador, " +
                        "                        serialSAT,tipoDocumentoTrabajador,numeroDocumentoTrabajador, " +
                        "                        primerNombreTrabajador,primerApellidoTrabajador,autorizacionDatosPersonales,autorizacionEnvioNotificaciones " +
                        "                        from sat.inicio_relacion_laboral with(nolock) WHERE PROCESO_ENVIADO_SAT IS NULL";
            */
            String sq = "SELECT TOP 300 " +
                                "irl.id, irl.tipoInicio, irl.fechaInicio, irl.segundoNombreTrabajador, " +
                                "irl.segundoApellidoTrabajador, irl.sexoTrabajador, '' as fechaNacimientoTrabajador, " +
                                "irl.departamentoCausaSalarios, irl.municipioCausaSalarios, irl.direccionMunicipioCausaSalarios, " +
                                "irl.telefonoMunicipioCausaSalarios, irl.correoElectronicoContactoTrabajador, irl.salario, irl.tipoSalario, irl.horasTrabajoMensuales, " +
                                "irl.radicado, irl.numeroTransaccion, irl.tipoDocumentoEmpleador, irl.numeroDocumentoEmpleador, " +
                                "irl.serialSAT, irl.tipoDocumentoTrabajador, irl.numeroDocumentoTrabajador, " +
                                "irl.primerNombreTrabajador, irl.primerApellidoTrabajador, irl.autorizacionDatosPersonales, irl.autorizacionEnvioNotificaciones " +
                        "FROM sat.inicio_relacion_laboral irl WITH(NOLOCK) " +
                        "LEFT JOIN SAT.AUD_INICIO_RELACION_LABORAL aud WITH(NOLOCK) ON irl.id = aud.id_inicio_relacion_laboral " +
                        "WHERE irl.proceso_enviado_sat IS NULL";
            Query query = entityManager.createNativeQuery(sq);
            List<Object[]> listado = query.getResultList();
            Integer idProceso = insertarEjecucionWs(parametros.get(EnumParametros.URL_INICIO_RELACION_LABORAL_SAT.getNombre()), "Iniciando", 0, listado.size(), "No Aplica");
            logger.info("idProceso:"+idProceso);
            Integer exitosos = 0;
            Integer fallidos = 0;
            for(Object[] obj:listado){
                try{
                    Map<String,String> objeto = new HashMap<String,String>();
                    objeto.put("tipoInicio", obj[1] != null?obj[1].toString():"");
                    objeto.put("fechaInicio", obj[2] != null?obj[2].toString():"");
                    objeto.put("segundoNombre", obj[3] != null?obj[3].toString():"");
                    objeto.put("segundoApellido", obj[4] != null?obj[4].toString():"");
                    objeto.put("sexo", obj[5] != null?obj[5].toString():"");
                    objeto.put("fechaNacimiento", obj[6] != null?obj[6].toString():"");
                    objeto.put("departamento", obj[7] != null?obj[7].toString():"");  
                    objeto.put("municipio", obj[8] != null?obj[8].toString():""); 
                    objeto.put("direccion", obj[9] != null?obj[9].toString():"");
                    objeto.put("telefono", obj[10] != null?obj[10].toString():""); // SE SOLICITA
                    objeto.put("correo", obj[11] != null?obj[11].toString():""); // SE SOLICITA
                    objeto.put("salario", obj[12] != null?obj[12].toString():"");
                    objeto.put("tipoSalario", obj[13] != null?obj[13].toString():"");  
                    objeto.put("horasTrabajo", obj[14] != null?obj[14].toString():"");  
                    objeto.put("numeroRadicadoSolicitud", obj[15] != null?obj[15].toString():"");  
                    objeto.put("numeroTransaccion", obj[16] != null?obj[16].toString():"");  
                    objeto.put("tipoDocumentoEmpleador", obj[17] != null?obj[17].toString():"");  
                    objeto.put("numeroDocumentoEmpleador", obj[18] != null?obj[18].toString():"");  
                    objeto.put("serialSAT", obj[19] != null?obj[19].toString():"");  
                    objeto.put("tipoDocumentoTrabajador", obj[20] != null?obj[20].toString():"");  
                    objeto.put("numeroDocumentoTrabajador", obj[21] != null?obj[21].toString():"");  
                    objeto.put("primerNombre", obj[22] != null?obj[22].toString():"");  
                    objeto.put("primerApellido", obj[23] != null?obj[23].toString():"");   
                    objeto.put("autorizacionManejoDatos", obj[24] != null?obj[24].toString():"");     
                    objeto.put("autorizacionNotificaciones", obj[24] != null?obj[24].toString():"");     

                    String json = JsonUtil.toJson(objeto);
                    logger.info(json);
                    logger.info(parametros.get(EnumParametros.URL_INICIO_RELACION_LABORAL_SAT.getNombre()));
                    
                    String token = getToken(parametros,EnumParametros.CLIENT_ID_INICIO_RELACION_LABORAL_SAT.getNombre());
                    String wsResponse = HttpUtil.sendHttpPostJSON(parametros.get(EnumParametros.URL_INICIO_RELACION_LABORAL_SAT.getNombre()), json, token);
                    if(wsResponse.contains("WS-ERROR -")){
                        fallidos++;
                        guardarBitacoraInicioRelacionLaboral(new Integer(obj[0].toString()),"SI",parametros.get(EnumParametros.URL_INICIO_RELACION_LABORAL_SAT.getNombre()),json, wsResponse);
                        logger.info("IRL - WS-ERROR - "+ "id: "+ obj[0].toString() +" - Numero fallidos: " + fallidos);
                    }else{
                        Map<String,Object> retorno = JsonUtil.parseJson(wsResponse);
                        if(retorno.get("resultado").toString().equals("200") || retorno.get("codigo").toString().equals("200") || retorno.get("codigo").toString().contains("AN")){
                            actualizarIdEjecucionInicioRelacionLaboral(new Integer(obj[0].toString()), idProceso);
                            guardarBitacoraInicioRelacionLaboral(new Integer(obj[0].toString()),"NO",parametros.get(EnumParametros.URL_INICIO_RELACION_LABORAL_SAT.getNombre()),json, wsResponse);
                            exitosos++;
                            logger.info("IRL - OK 200 - "+ "id: "+ obj[0].toString() +" - Numero exitosos: " + exitosos);
                        }else{
                            fallidos++;
                            guardarBitacoraInicioRelacionLaboral(new Integer(obj[0].toString()),"SI",parametros.get(EnumParametros.URL_INICIO_RELACION_LABORAL_SAT.getNombre()),json, wsResponse);
                            logger.info("IRL - Error <> 200 - "+ "id: "+ obj[0].toString() +" - Numero fallidos: " + fallidos);
                        }
                    }
                }catch(Exception e){
                    guardarBitacoraInicioRelacionLaboral(new Integer(obj[0].toString()),"SI",parametros.get(EnumParametros.URL_INICIO_RELACION_LABORAL_SAT.getNombre()),"", e.getMessage());
                    fallidos++;
                    logger.info("IRL - Exception " + e);
                    logger.info("Id: " + obj[0].toString() + " - Numero fallidos: " + fallidos);
                }
            }
            if(exitosos == listado.size()){
                actualizarEjecucionWS(idProceso, exitosos, "Finalizado correctamente", "Finalizado correctamente");
                respuesta.setEstado("OK");
                respuesta.setElemento(EnumParametros.URL_INICIO_RELACION_LABORAL_SAT.getNombre());
                respuesta.adicionarMensaje("Finalizado correctamente");
            }else{
                actualizarEjecucionWS(idProceso, exitosos, "Finalizado con Errores", "Se procesaron:"+exitosos+", con errores:"+fallidos);
                respuesta.setEstado("ADVERTENCIA");
                respuesta.setElemento(EnumParametros.URL_INICIO_RELACION_LABORAL_SAT.getNombre());
                respuesta.adicionarMensaje("Se procesaron:"+exitosos+", con errores:"+fallidos);
            }
            respuesta.setExitosos(exitosos);
            respuesta.setFallidos(fallidos);
        }catch(Exception e){
            logger.error(e);
            respuesta.setEstado("ERROR");
            respuesta.setElemento(EnumParametros.URL_INICIO_RELACION_LABORAL_SAT.getNombre());
            respuesta.adicionarMensaje(e.getMessage());
        }
        logger.info("Termina llamado servicios reportarInicioRelacionLaboral");
        return respuesta;
    }
    
    private void guardarBitacoraInicioRelacionLaboral(Integer idInicioRelacionLaoral,String error,String url,String request,String response){
        try{
            request = request.replace("'", "''");
            response = response.replace("'", "''");
             Query query = entityManager.createNativeQuery("INSERT INTO SAT.AUD_INICIO_RELACION_LABORAL(fecha,id_inicio_relacion_laboral,error,URL,REQUEST,RESPONSE)values(getdate(),"+idInicioRelacionLaoral+",'"+error+"','"+url+"','"+request+"','"+response+"')");
            query.executeUpdate();
        }catch(Exception e){
            logger.error(e);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private void actualizarIdEjecucionInicioRelacionLaboral(Integer idInicioRelacionLaboral,Integer idEjecucion){
        try{
             Query query = entityManager.createNativeQuery("UPDATE SAT.INICIO_RELACION_LABORAL SET proceso_enviado_sat ="+idEjecucion+" WHERE id ="+idInicioRelacionLaboral);
            query.executeUpdate();
        }catch(Exception e){
            logger.error(e);
        }
    }
    
    
    /**
     * REPORTE TERMINACION RELACION LABORAL
     * 
     * 
     */
    
    private RespuestaEstandar reportarTerminacionRelacionLaboral(Map<String,String> parametros){
        logger.info("Comienza llamado servicios reportarTerminacionRelacionLaboral");
        RespuestaEstandar respuesta = new RespuestaEstandar();
        try{
            //Se cambia temporalmente la siguiente consulta para la ejecución por paquetes de los registros de SAT -- GLPI 71469
            /*
            String sq = "select id,tipoTerminacion,fechaTerminacion," +
                        "radicado,'0' as numeroTransaccion,tipoDocumentoEmpleador," +
                        "numeroDocumentoEmpleador,serialSAT,tipoDocumentoTrabajador," +
                        "numeroDocumentoTrabajador,primerNombreTrabajador,primerApellidoTrabajador," +
                        "autorizacionDatosPersonales,autorizacionEnvioNotificaciones " +
                        "from SAT.terminacion_relacion_laboral with(nolock) WHERE PROCESO_ENVIADO_SAT IS NULL";
            */
            String sq = "SELECT TOP 300" +
                                "trl.id, trl.tipoTerminacion, trl.fechaTerminacion, " +
                                "trl.radicado, '0' as numeroTransaccion, trl.tipoDocumentoEmpleador, " +
                                "trl.numeroDocumentoEmpleador, trl.serialSAT, trl.tipoDocumentoTrabajador, " +
                                "trl.numeroDocumentoTrabajador, trl.primerNombreTrabajador, trl.primerApellidoTrabajador, " +
                                "trl.autorizacionDatosPersonales, trl.autorizacionEnvioNotificaciones " +
                        "FROM SAT.terminacion_relacion_laboral trl WITH(NOLOCK) " +
                        "LEFT JOIN SAT.AUD_TERMINACION_RELACION_LABORAL aud WITH(NOLOCK) ON trl.id = aud.id_terminacion_relacion_laboral " +
                        "WHERE trl.proceso_enviado_sat IS NULL";
            Query query = entityManager.createNativeQuery(sq);
            List<Object[]> listado = query.getResultList();
            Integer idProceso = insertarEjecucionWs(parametros.get(EnumParametros.URL_TERMINACION_RELACION_LABORAL_SAT.getNombre()), "Iniciando", 0, listado.size(), "No Aplica");
            logger.info("idProceso:"+idProceso);
            Integer exitosos = 0;
            Integer fallidos = 0;
            for(Object[] obj:listado){
                try{
                    Map<String,String> objeto = new HashMap<String,String>();
                    objeto.put("tipoTerminacion", obj[1] != null?obj[1].toString():"");
                    objeto.put("fechaTerminacion", obj[2] != null?obj[2].toString():"");
                    objeto.put("numeroRadicadoSolicitud", obj[3] != null?obj[3].toString():"");
                    objeto.put("numeroTransaccion", obj[4] != null?obj[4].toString():"");
                    objeto.put("tipoDocumentoEmpleador", obj[5] != null?obj[5].toString():"");
                    objeto.put("numeroDocumentoEmpleador", obj[6] != null?obj[6].toString():"");
                    objeto.put("serialSAT", obj[7] != null?obj[7].toString():"");
                    objeto.put("tipoDocumentoTrabajador", obj[8] != null?obj[8].toString():"");
                    objeto.put("numeroDocumentoTrabajador", obj[9] != null?obj[9].toString():"");  
                    objeto.put("primerNombre", obj[10] != null?obj[10].toString():"");  
                    objeto.put("primerApellido", obj[11] != null?obj[11].toString():"");                      
                    objeto.put("autorizacionManejoDatos", obj[12] != null?obj[12].toString():"");  
                    objeto.put("autorizacionNotificaciones", obj[13] != null?obj[13].toString():"");  

                    String json = JsonUtil.toJson(objeto);
                    String token = getToken(parametros,EnumParametros.CLIENT_ID_TERMINACION_RELACION_LABORAL_SAT.getNombre());
                    String wsResponse = HttpUtil.sendHttpPostJSON(parametros.get(EnumParametros.URL_TERMINACION_RELACION_LABORAL_SAT.getNombre()), json, token);

                    if(wsResponse.contains("WS-ERROR -")){
                        fallidos++;
                        guardarBitacoraTerminacionRelacionLaboral(new Integer(obj[0].toString()),"SI",parametros.get(EnumParametros.URL_TERMINACION_RELACION_LABORAL_SAT.getNombre()),json, wsResponse);
                        logger.info("TRL - WS-ERROR - "+ "id: "+ obj[0].toString() + " - Numeros fallidos: " + fallidos);
                    }else{
                        Map<String,Object> retorno = JsonUtil.parseJson(wsResponse);
                        if(retorno.get("resultado").toString().equals("200") || retorno.get("codigo").toString().equals("200") || retorno.get("codigo").toString().contains("AN")){
                            actualizarIdEjecucionTerminacionRelacionLaboral(new Integer(obj[0].toString()), idProceso);
                            guardarBitacoraTerminacionRelacionLaboral(new Integer(obj[0].toString()),"NO",parametros.get(EnumParametros.URL_TERMINACION_RELACION_LABORAL_SAT.getNombre()),json, wsResponse);
                            exitosos++;
                            logger.info("TRL - OK 200 - "+ "id: "+ obj[0].toString() + " - Numero exitosos: " + exitosos);
                        }else{
                            fallidos++;
                            guardarBitacoraTerminacionRelacionLaboral(new Integer(obj[0].toString()),"SI",parametros.get(EnumParametros.URL_TERMINACION_RELACION_LABORAL_SAT.getNombre()),json, wsResponse);
                            logger.info("TRL - Error <> 200 - "+ "id: "+ obj[0].toString() + " - Numero fallidos: " + fallidos);
                        }
                    }
                }catch(Exception e){
                    guardarBitacoraTerminacionRelacionLaboral(new Integer(obj[0].toString()),"SI",parametros.get(EnumParametros.URL_TERMINACION_RELACION_LABORAL_SAT.getNombre()),"", e.getMessage());
                    fallidos++;
                    logger.info("TRL - Exception " + e);
                }
            }
            if(exitosos == listado.size()){
                actualizarEjecucionWS(idProceso, exitosos, "Finalizado correctamente", "Finalizado correctamente");
                respuesta.setEstado("OK");
                respuesta.setElemento(EnumParametros.URL_TERMINACION_RELACION_LABORAL_SAT.getNombre());
                respuesta.adicionarMensaje("Finalizado correctamente");
            }else{
                actualizarEjecucionWS(idProceso, exitosos, "Finalizado con Errores", "Se procesaron:"+exitosos+", con errores:"+fallidos);
                respuesta.setEstado("ADVERTENCIA");
                respuesta.setElemento(EnumParametros.URL_TERMINACION_RELACION_LABORAL_SAT.getNombre());
                respuesta.adicionarMensaje("Se procesaron:"+exitosos+", con errores:"+fallidos);
            }
            respuesta.setExitosos(exitosos);
            respuesta.setFallidos(fallidos);
        }catch(Exception e){
            logger.error(e);
            respuesta.setEstado("ERROR");
            respuesta.setElemento(EnumParametros.URL_INICIO_RELACION_LABORAL_SAT.getNombre());
            respuesta.adicionarMensaje(e.getMessage());
        }
        logger.info("Termina llamado servicios reportarTerminacionRelacionLaboral");
        return respuesta;
    }
    
    private void guardarBitacoraTerminacionRelacionLaboral(Integer idInicioRelacionLaoral,String error,String url,String request,String response){
        try{
            request = request.replace("'", "''");
            response = response.replace("'", "''");
             Query query = entityManager.createNativeQuery("INSERT INTO SAT.AUD_TERMINACION_RELACION_LABORAL(fecha,id_terminacion_relacion_laboral,error,URL,REQUEST,RESPONSE)values(getdate(),"+idInicioRelacionLaoral+",'"+error+"','"+url+"','"+request+"','"+response+"')");
            query.executeUpdate();
        }catch(Exception e){
            logger.error(e);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private void actualizarIdEjecucionTerminacionRelacionLaboral(Integer idTerminacionRelacionLaboral,Integer idEjecucion){
        try{
             Query query = entityManager.createNativeQuery("UPDATE SAT.terminacion_relacion_laboral SET proceso_enviado_sat ="+idEjecucion+" WHERE id ="+idTerminacionRelacionLaboral);
            query.executeUpdate();
        }catch(Exception e){
            logger.error(e);
        }
    }
    
    /**
     *  Perdida Afiliacion Causa Grave
     */
    
     private RespuestaEstandar reportarPerdidaAfiliacionCausaGrave(Map<String,String> parametros){
        logger.info("Comienza llamado servicios reportarTPerdidaAfiliacionCausaGravie");
        RespuestaEstandar respuesta = new RespuestaEstandar();
        try{
            //Se cambia temporalmente la siguiente consulta para la ejecución por paquetes de los registros de SAT -- GLPI 71469
            /*
            String sq = "select id,radicado,tipoPersona,tipoDocumentoEmpleador, " +
                        "numeroDocumentoEmpleador,serialSAT,fechaPerdidaAfiliacion, " +
                        "razonSocialEmpleador,causalRetiro,departamentoCausaSalarios, " +
                        "primerApellidoEmpleador,primerNombreEmpleador,estadoReporte" +
                        " FROM SAT.PERDIDA_AFILIACION_CAUSA_GRAVE WHERE proceso_enviado_sat is null";
            */
            String sq = "SELECT TOP 300" +
                                "acg.id, acg.radicado, acg.tipoPersona, acg.tipoDocumentoEmpleador, " +
                                "acg.numeroDocumentoEmpleador, acg.serialSAT, acg.fechaPerdidaAfiliacion, " +
                                "acg.razonSocialEmpleador, acg.causalRetiro, acg.departamentoCausaSalarios, " +
                                "acg.primerApellidoEmpleador, acg.primerNombreEmpleador, acg.estadoReporte " +
                        "FROM SAT.PERDIDA_AFILIACION_CAUSA_GRAVE acg WITH(NOLOCK) " +
                        "LEFT JOIN SAT.AUD_PERDIDA_CAUSA_GRAVE aud WITH(NOLOCK) ON acg.id = aud.id_perdida_causa_grave " +
                        "WHERE acg.proceso_enviado_sat IS NULL";
            Query query = entityManager.createNativeQuery(sq);
            List<Object[]> listado = query.getResultList();
            Integer idProceso = insertarEjecucionWs(parametros.get(EnumParametros.URL_PERDIDA_CAUSA_GRAVE_SAT.getNombre()), "Iniciando", 0, listado.size(), "No Aplica");
            logger.info("idProceso:"+idProceso);
            Integer exitosos = 0;
            Integer fallidos = 0;
            for(Object[] obj:listado){
                try{
                    Map<String,String> objeto = new HashMap<String,String>();
                    objeto.put("numeroRadicadoSolicitud", obj[1] != null?obj[1].toString():"");
                    objeto.put("tipoPersona", obj[2] != null?obj[2].toString():"");
                    objeto.put("tipoDocumentoEmpleador", obj[3] != null?obj[3].toString():"");
                    objeto.put("numeroDocumentoEmpleador", obj[4] != null?obj[4].toString():"");
                    objeto.put("serialSAT", obj[5] != null?obj[5].toString():"");
                    objeto.put("fechaPerdidaAfiliacion", obj[6] != null?obj[6].toString():"");
                    objeto.put("razonSocial", obj[7] != null?obj[7].toString():"");
                    objeto.put("causalRetiro", obj[8] != null?obj[8].toString():""); 
                    objeto.put("departamento", obj[9] != null?obj[9].toString():""); 
                    objeto.put("primerApellidoEmpleador", obj[10] != null?obj[10].toString():"");  
                    objeto.put("primerNombreEmpleador", obj[11] != null?obj[11].toString():"");                      
                    objeto.put("estadoReporte", obj[12] != null?obj[12].toString():"");  

                    String json = JsonUtil.toJson(objeto);
                    String token = getToken(parametros,EnumParametros.CLIENT_ID_PERDIDA_CAUSA_GRAVE_SAT.getNombre());
                    String wsResponse = HttpUtil.sendHttpPostJSON(parametros.get(EnumParametros.URL_PERDIDA_CAUSA_GRAVE_SAT.getNombre()), json, token);
                    if(wsResponse.contains("WS-ERROR -")){
                        fallidos++;
                        guardarBitacoraPerdidaCausaGrave(new Integer(obj[0].toString()),"SI",parametros.get(EnumParametros.URL_PERDIDA_CAUSA_GRAVE_SAT.getNombre()),json, wsResponse);
                        logger.info("PCG - WS-ERROR - "+ "id: "+ obj[0].toString() +" - Numero fallidos: " + fallidos);
                    }else{
                        Map<String,Object> retorno = JsonUtil.parseJson(wsResponse);
                        if(retorno.get("resultado").toString().equals("200") || retorno.get("codigo").toString().equals("200") || retorno.get("codigo").toString().contains("AN")){
                            actualizarIdEjecucionPerdidaCausaGrave(new Integer(obj[0].toString()), idProceso);
                            guardarBitacoraPerdidaCausaGrave(new Integer(obj[0].toString()),"NO",parametros.get(EnumParametros.URL_PERDIDA_CAUSA_GRAVE_SAT.getNombre()),json, wsResponse);
                            exitosos++;
                            logger.info("PCG - OK 200 - "+ "id: "+ obj[0].toString() +" - Numero exitosos: " + exitosos);
                        }else{
                            fallidos++;
                            guardarBitacoraPerdidaCausaGrave(new Integer(obj[0].toString()),"SI",parametros.get(EnumParametros.URL_PERDIDA_CAUSA_GRAVE_SAT.getNombre()),json, wsResponse);
                            logger.info("PCG - Error <> 200 - "+ "id: "+ obj[0].toString() +" - Numero fallidos: " + fallidos);
                        }
                    }
                }catch(Exception e){
                    guardarBitacoraPerdidaCausaGrave(new Integer(obj[0].toString()),"SI",parametros.get(EnumParametros.URL_PERDIDA_CAUSA_GRAVE_SAT.getNombre()),"", e.getMessage());
                    fallidos++;
                    logger.info("PCG - Exception " + e);
                    logger.info("Id: " + obj[0].toString() + " - Numero fallidos: " + fallidos); 
                }
            }
            if(exitosos == listado.size()){
                actualizarEjecucionWS(idProceso, exitosos, "Finalizado correctamente", "Finalizado correctamente");
                respuesta.setEstado("OK");
                respuesta.setElemento(EnumParametros.URL_PERDIDA_CAUSA_GRAVE_SAT.getNombre());
                respuesta.adicionarMensaje("Finalizado correctamente");
            }else{
                actualizarEjecucionWS(idProceso, exitosos, "Finalizado con Errores", "Se procesaron:"+exitosos+", con errores:"+fallidos);
                respuesta.setEstado("ADVERTENCIA");
                respuesta.setElemento(EnumParametros.URL_PERDIDA_CAUSA_GRAVE_SAT.getNombre());
                respuesta.adicionarMensaje("Se procesaron:"+exitosos+", con errores:"+fallidos);
            }
            respuesta.setExitosos(exitosos);
            respuesta.setFallidos(fallidos);
        }catch(Exception e){
            logger.error(e);
            respuesta.setEstado("ERROR");
            respuesta.setElemento(EnumParametros.URL_PERDIDA_CAUSA_GRAVE_SAT.getNombre());
            respuesta.adicionarMensaje(e.getMessage());
        }
        logger.info("Termina llamado servicios reportarTPerdidaAfiliacionCausaGravie");
        return respuesta;
    }
     
    private void guardarBitacoraPerdidaCausaGrave(Integer idPerdidaCausaGrave,String error,String url,String request,String response){
        try{
            request = request.replace("'", "''");
            response = response.replace("'", "''");
             Query query = entityManager.createNativeQuery("INSERT INTO SAT.AUD_PERDIDA_CAUSA_GRAVE(fecha,id_perdida_causa_grave,error,URL,REQUEST,RESPONSE)values(getdate(),"+idPerdidaCausaGrave+",'"+error+"','"+url+"','"+request+"','"+response+"')");
            query.executeUpdate();
        }catch(Exception e){
            logger.error(e);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private void actualizarIdEjecucionPerdidaCausaGrave(Integer idPerdidaCausaGrave,Integer idEjecucion){
        try{
             Query query = entityManager.createNativeQuery("UPDATE SAT.PERDIDA_AFILIACION_CAUSA_GRAVE SET proceso_enviado_sat ="+idEjecucion+" WHERE id ="+idPerdidaCausaGrave);
            query.executeUpdate();
        }catch(Exception e){
            logger.error(e);
        }
    }
    
    /**
     * ESTADO PAGO EMPLEADOR
     */
    
    private RespuestaEstandar reportarEstadoPagoEmpleador(Map<String,String> parametros){
        logger.info("Comienza llamado servicios reportarEstadoEmpleador");
        RespuestaEstandar respuesta = new RespuestaEstandar();
        try{
            //Se cambia temporalmente la siguiente consulta para la ejecución por paquetes de los registros de SAT -- GLPI 71469
            /*
            String sq = "SELECT id,radicado,tipoDocumentoEmpleador," +
                        "numeroDocumentoEmpleador,serialSAT,estadoPagoAportes" +
                        " FROM SAT.ESTADO_PA_APORTES WITH(NOLOCK) where proceso_enviado_sat is null";
            */
            String sq = "SELECT TOP 300 " +
                                "epa.id, epa.radicado, epa.tipoDocumentoEmpleador, " +
                                "epa.numeroDocumentoEmpleador, epa.serialSAT, epa.estadoPagoAportes " +
                        "FROM SAT.ESTADO_PA_APORTES epa WITH(NOLOCK) " +
                        "LEFT JOIN SAT.AUD_ESTADO_PA_APORTES aud WITH (NOLOCK) ON epa.id = aud.id_estado_pago_aportes " +
                        "WHERE epa.proceso_enviado_sat IS NULL";
            Query query = entityManager.createNativeQuery(sq);
            List<Object[]> listado = query.getResultList();
            Integer idProceso = insertarEjecucionWs(parametros.get(EnumParametros.URL_ESTADO_PAGO_EMPLEADOR_SAT.getNombre()), "Iniciando", 0, listado.size(), "No Aplica");
            logger.info("idProceso:"+idProceso);
            Integer exitosos = 0;
            Integer fallidos = 0;
            for(Object[] obj:listado){
                try{
                    Map<String,String> objeto = new HashMap<String,String>();
                    objeto.put("numeroRadicado", obj[1] != null?obj[1].toString():"");
                    objeto.put("tipoDocumentoEmpleador", obj[2] != null?obj[2].toString():"");
                    objeto.put("numeroDocumentoEmpleador", obj[3] != null?obj[3].toString():"");
                    objeto.put("serialSAT", obj[4] != null?obj[4].toString():"");
                    objeto.put("estadoPago", obj[5] != null?obj[5].toString():""); 

                    String json = JsonUtil.toJson(objeto);
                    String token = getToken(parametros,EnumParametros.CLIENT_ID_ESTADO_PAGO_EMPLEADOR_SAT.getNombre());
                    String wsResponse = HttpUtil.sendHttpPostJSON(parametros.get(EnumParametros.URL_ESTADO_PAGO_EMPLEADOR_SAT.getNombre()), json, token);
                    if(wsResponse.contains("WS-ERROR -")){
                        fallidos++;
                        guardarBitacoraEstadoPagoEmpleador(new Integer(obj[0].toString()),"SI",parametros.get(EnumParametros.URL_ESTADO_PAGO_EMPLEADOR_SAT.getNombre()),json, wsResponse);
                        logger.info("EPE - WS-ERROR - "+ "id: "+ obj[0].toString() +" - Numero fallidos: " + fallidos);
                    }else{
                        Map<String,Object> retorno = JsonUtil.parseJson(wsResponse);
                        if(retorno.get("resultado").toString().equals("200") || retorno.get("codigo").toString().equals("200") || retorno.get("codigo").toString().contains("AN")){
                            actualizarIdEjecucionEstadoPagoEmpleador(new Integer(obj[0].toString()), idProceso);
                            guardarBitacoraEstadoPagoEmpleador(new Integer(obj[0].toString()),"NO",parametros.get(EnumParametros.URL_ESTADO_PAGO_EMPLEADOR_SAT.getNombre()),json, wsResponse);
                            exitosos++;
                            logger.info("EPE - OK 200 - "+ "id: "+ obj[0].toString() +" - Numero exitosos: " + exitosos); 
                        }else{
                            fallidos++;
                            guardarBitacoraEstadoPagoEmpleador(new Integer(obj[0].toString()),"SI",parametros.get(EnumParametros.URL_ESTADO_PAGO_EMPLEADOR_SAT.getNombre()),json, wsResponse);
                            logger.info("EPE - Error <> 200 - "+ "id: "+ obj[0].toString() +" - Numero fallidos: " + fallidos);
                        }
                    }
                }catch(Exception e){
                    guardarBitacoraEstadoPagoEmpleador(new Integer(obj[0].toString()),"SI",parametros.get(EnumParametros.URL_ESTADO_PAGO_EMPLEADOR_SAT.getNombre()),"", e.getMessage());
                    fallidos++;
                    logger.info("EPE - Exception " + e);
                    logger.info("Id: " + obj[0].toString() + " - Numero fallidos: " + fallidos); 
                }
            }
            if(exitosos == listado.size()){
                actualizarEjecucionWS(idProceso, exitosos, "Finalizado correctamente", "Finalizado correctamente");
                respuesta.setEstado("OK");
                respuesta.setElemento(EnumParametros.URL_ESTADO_PAGO_EMPLEADOR_SAT.getNombre());
                respuesta.adicionarMensaje("Finalizado correctamente");
            }else{
                actualizarEjecucionWS(idProceso, exitosos, "Finalizado con Errores", "Se procesaron:"+exitosos+", con errores:"+fallidos);
                respuesta.setEstado("ADVERTENCIA");
                respuesta.setElemento(EnumParametros.URL_ESTADO_PAGO_EMPLEADOR_SAT.getNombre());
                respuesta.adicionarMensaje("Se procesaron:"+exitosos+", con errores:"+fallidos);
            }
            respuesta.setExitosos(exitosos);
            respuesta.setFallidos(fallidos);
        }catch(Exception e){
            logger.error(e);
            respuesta.setEstado("ERROR");
            respuesta.setElemento(EnumParametros.URL_ESTADO_PAGO_EMPLEADOR_SAT.getNombre());
            respuesta.adicionarMensaje(e.getMessage());
        }
        logger.info("Termina llamado servicios reportarEstadoEmpleador");
        return respuesta;
    }
    
    private void guardarBitacoraEstadoPagoEmpleador(Integer idEstadoPagoEmpleador,String error,String url,String request,String response){
        try{
            request = request.replace("'", "''");
            response = response.replace("'", "''");
             Query query = entityManager.createNativeQuery("INSERT INTO SAT.AUD_ESTADO_PA_APORTES(fecha,id_estado_pago_aportes,error,URL,REQUEST,RESPONSE)values(getdate(),"+idEstadoPagoEmpleador+",'"+error+"','"+url+"','"+request+"','"+response+"')");
            query.executeUpdate();
        }catch(Exception e){
            logger.error(e);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private void actualizarIdEjecucionEstadoPagoEmpleador(Integer idEstadoPagoEmpleador,Integer idEjecucion){
        try{
             Query query = entityManager.createNativeQuery("UPDATE SAT.ESTADO_PA_APORTES SET proceso_enviado_sat ="+idEjecucion+" WHERE id ="+idEstadoPagoEmpleador);
            query.executeUpdate();
        }catch(Exception e){
            logger.error(e);
        }
    }


    @Override
    public String getTokenSatToGenesys(Map<String,String> parametros,String nombreCLientId){
        try{
            Map<String,Object> urlParams = new HashMap<String,Object>();
            
            urlParams.put("username", parametros.get(EnumParametros.USUARIO_SAT_WS_F2.getNombre()));
            urlParams.put("password", parametros.get(EnumParametros.PASSWORD_SAT_WS_F2.getNombre()));
            urlParams.put("client_id", parametros.get(nombreCLientId));
            urlParams.put("grant_type", "password");
            
            /*
            urlParams.put("username", "DESARROLLOS@EPROCESS.COM.CO");
            urlParams.put("password", "Master02*123");
            urlParams.put("client_id", "admin-cli");
            urlParams.put("grant_type", "password");
            */

            Map<String,Object> respuesta = HttpUtil.sendHttpPost(parametros.get(EnumParametros.URL_TOKEN_SAT_WS_F2.getNombre()), urlParams);
            String token = "";
            if(respuesta != null && respuesta.get("status") != null && respuesta.get("status").toString().equals("OK")){
                String json = respuesta.get("mensaje").toString();
                Map<String,Object> retorno = JsonUtil.parseJson(json);
                token = retorno.get("access_token").toString();
                System.out.println("**__** URL_TOKEN_SAT_WS_F2 ok : "+retorno);
            }else{
                String json = respuesta.get("mensaje").toString();
                Map<String,Object> retorno = JsonUtil.parseJson(json);
                      System.out.println("**__** status NO OK: "+retorno);
                token = null;
            }
            return token;
        }catch(Exception e){
            logger.error(e);
            return null;
        }
    }
    
}

