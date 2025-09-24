/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.sat.business.ejb;

import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.sat.business.interfaces.IEjecutarProcedimientoRecoleccion;
import com.asopagos.sat.dto.RespuestaEstandar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author Sergio Reyes
 */
@Stateless
public class EjecutarProcedimientoRecoleccion implements IEjecutarProcedimientoRecoleccion,Serializable{
    
    private static final ILogger logger = LogManager.getLogger(EjecutarProcedimientoRecoleccion.class);
    
    @PersistenceContext(unitName = "core_PU")
    private EntityManager entityManager;

    @Override
    public List<RespuestaEstandar> ejecutarProcedimiento() {
        List<RespuestaEstandar> respuesta = new ArrayList<RespuestaEstandar>();
        try{
            logger.info("Comienza ejecucion de los procedimientos para recoleccion datos Genesys para SAT");
            RespuestaEstandar resUno = ejecutarRepoAfiliacionEmpleadoPrimeraVezDepartamento();
            RespuestaEstandar resDos = ejecutarRepoDesafiliacionCff();
            RespuestaEstandar resTres = ejecutarRepoAfiliacionEmpleadoMismoDepartamento();
            RespuestaEstandar resCuatro = ejecutarInicioRelacionLaboral();
            RespuestaEstandar resCinco = ejecutarTerminacionRelacionLaboral();
            RespuestaEstandar resSeis = ejecutarPerdidaAfiliacionCausaGrave();
            logger.info("Termina ejecucion de los procedimientos para recoleccion datos Genesys para SAT");
            respuesta.add(resUno);
            respuesta.add(resDos);
            respuesta.add(resTres);
            respuesta.add(resCuatro);
            respuesta.add(resCinco);
            respuesta.add(resSeis);
        }catch(Exception e){
            RespuestaEstandar error = new RespuestaEstandar();
            error.setEstado("ERROR");
            error.setElemento("GENERAL");
            error.adicionarMensaje(e.getMessage());
            respuesta.add(error);
            logger.error(e);
        }
        return respuesta;
    }
    
    private RespuestaEstandar ejecutarRepoAfiliacionEmpleadoMismoDepartamento(){
        logger.info("Comienza ejecucion de procedimiento SP_REPO_AFIL_EMPL_MISM_DEPA");
        RespuestaEstandar respuesta = new RespuestaEstandar();
        try{
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("SAT.SP_REPO_AFIL_EMPL_MISM_DEPA");
            query.registerStoredProcedureParameter("RESULTADO",String.class,ParameterMode.OUT);
            query.registerStoredProcedureParameter("STATUS",String.class,ParameterMode.OUT);
            query.execute();
            String resultado = query.getOutputParameterValue("RESULTADO").toString();
            String estatus = query.getOutputParameterValue("STATUS").toString();
            respuesta.setElemento("SP_REPO_AFIL_EMPL_MISM_DEPA");
            respuesta.setEstado(estatus);
            respuesta.adicionarMensaje(resultado);
        }catch(Exception e){
            logger.error(e);
        }
        logger.info("Termina ejecucion de procedimiento SP_REPO_AFIL_EMPL_MISM_DEPA");
        return respuesta;
    }
    
    private RespuestaEstandar ejecutarRepoAfiliacionEmpleadoPrimeraVezDepartamento(){
        logger.info("Comienza ejecucion de procedimiento SP_REPO_AFIL_EMPL_PRIM_VEZ_DEPA");
        RespuestaEstandar respuesta = new RespuestaEstandar();
        try{
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("SAT.SP_REPO_AFIL_EMPL_PRIM_VEZ_DEPA");
            query.registerStoredProcedureParameter("RESULTADO",String.class,ParameterMode.OUT);
            query.registerStoredProcedureParameter("STATUS",String.class,ParameterMode.OUT);
            query.execute();
            String resultado = query.getOutputParameterValue("RESULTADO").toString();
            String estatus = query.getOutputParameterValue("STATUS").toString();
            respuesta.setElemento("SP_REPO_AFIL_EMPL_PRIM_VEZ_DEPA");
            respuesta.setEstado(estatus);
            respuesta.adicionarMensaje(resultado);
        }catch(Exception e){
            logger.error(e);
        }
        logger.info("Termina ejecucion de procedimiento SP_REPO_AFIL_EMPL_PRIM_VEZ_DEPA");
        return respuesta;
    }
    
    private RespuestaEstandar ejecutarRepoDesafiliacionCff(){
        logger.info("Comienza ejecucion de procedimiento SP_REPO_DESA_CCF");
        RespuestaEstandar respuesta = new RespuestaEstandar();
        try{
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("SAT.SP_REPO_DESA_CCF");
            query.registerStoredProcedureParameter("RESULTADO",String.class,ParameterMode.OUT);
            query.registerStoredProcedureParameter("STATUS",String.class,ParameterMode.OUT);
            query.execute();
            String resultado = query.getOutputParameterValue("RESULTADO").toString();
            String estatus = query.getOutputParameterValue("STATUS").toString();
            respuesta.setElemento("SP_REPO_DESA_CCF");
            respuesta.setEstado(estatus);
            respuesta.adicionarMensaje(resultado);
        }catch(Exception e){
            logger.error(e);
        }
        logger.info("Termina ejecucion de procedimiento SP_REPO_DESA_CCF");
        return respuesta;
    }
    
    private RespuestaEstandar ejecutarInicioRelacionLaboral(){
        logger.info("Comienza ejecucion de procedimiento SP_REPO_INIC_RELA_LABO");
        RespuestaEstandar respuesta = new RespuestaEstandar();
        try{
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("SAT.SP_REPO_INIC_RELA_LABO");
            query.registerStoredProcedureParameter("RESULTADO",String.class,ParameterMode.OUT);
            query.registerStoredProcedureParameter("STATUS",String.class,ParameterMode.OUT);
            query.execute();
            String resultado = query.getOutputParameterValue("RESULTADO").toString();
            String estatus = query.getOutputParameterValue("STATUS").toString();
            respuesta.setElemento("SP_REPO_INIC_RELA_LABO");
            respuesta.setEstado(estatus);
            respuesta.adicionarMensaje(resultado);
        }catch(Exception e){
            logger.error(e);
        }
        logger.info("Termina ejecucion de procedimiento SP_REPO_INIC_RELA_LABO");
        return respuesta;
    }
    
    private RespuestaEstandar ejecutarTerminacionRelacionLaboral(){
        logger.info("Comienza ejecucion de procedimiento SP_REPO_TERM_RELA_LABO");
        RespuestaEstandar respuesta = new RespuestaEstandar();
        try{
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("SAT.SP_REPO_TERM_RELA_LABO");
            query.registerStoredProcedureParameter("RESULTADO",String.class,ParameterMode.OUT);
            query.registerStoredProcedureParameter("STATUS",String.class,ParameterMode.OUT);
            query.execute();
            String resultado = query.getOutputParameterValue("RESULTADO").toString();
            String estatus = query.getOutputParameterValue("STATUS").toString();
            respuesta.setElemento("SP_REPO_TERM_RELA_LABO");
            respuesta.setEstado(estatus);
            respuesta.adicionarMensaje(resultado);
        }catch(Exception e){
            logger.error(e);
        }
        logger.info("Termina ejecucion de procedimiento SP_REPO_TERM_RELA_LABO");
        return respuesta;
    }
    
    private RespuestaEstandar ejecutarPerdidaAfiliacionCausaGrave(){
        logger.info("Comienza ejecucion de procedimiento SP_REPO_PERD_AFIL_GRAV");
        RespuestaEstandar respuesta = new RespuestaEstandar();
        try{
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("SAT.SP_REPO_PERD_AFIL_GRAV");
            query.registerStoredProcedureParameter("RESULTADO",String.class,ParameterMode.OUT);
            query.registerStoredProcedureParameter("STATUS",String.class,ParameterMode.OUT);
            query.execute();
            String resultado = query.getOutputParameterValue("RESULTADO").toString();
            String estatus = query.getOutputParameterValue("STATUS").toString();
            respuesta.setElemento("SP_REPO_PERD_AFIL_GRAV");
            respuesta.setEstado(estatus);
            respuesta.adicionarMensaje(resultado);
        }catch(Exception e){
            logger.error(e);
        }
        logger.info("Termina ejecucion de procedimiento SP_REPO_PERD_AFIL_GRAV");
        return respuesta;
    }
    
    
}
